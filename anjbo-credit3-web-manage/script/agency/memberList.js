angular.module("anjboApp", ['bsTable']).controller("agencyMemberListCtrl", function($scope, $http, $timeout, $state, $window, box, route) {
	$scope.pageTabViewIndex = 1;
		
	$scope.page = new Object();
	$scope.page.count = 1;
	$scope.page.agencyId = route.getParams().agencyId;
	$scope.agencyName = route.getParams().agencyName;
	function getParams(data) {
		$scope.page.start = data.offset;
		$scope.page.pageSize = data.limit;
		return $scope.page;
	}
	
	function roleFormatter(value, row, index) {
		return row.admin ? "超级管理员" : value;
	}
	function statusFormatter(value, row, index) {
		/*-1待审批, 0启用 ，1未启用，2不通过, 3已解绑*/
		switch (value){			
			case 0:		return "启用";
			case 1:	    return "禁用";
			case 2:	    return "未通过";
			case 3:	    return "已解绑";
			default:	return "待审批";
		}
	}
	
	// 机构角色
	$scope.roleList = {
		options: {
			method: "post",
			url: "/credit/user/role/v/page ",
			queryParams: getParams,
			sidePagination: 'server',
			undefinedText: "-",
			sortName: "borrowingAmount",
			cache: false,
			striped: true,
			pagination: true,
			pageNumber: 1,
			pageSize: 15,
			pageList: ['All'],
			columns: [{
				title: '序号',
				field: 'id',
				align: 'center',
				valign: 'bottom',
				formatter: function(value, row, index) {
					return index+1;
				}
			}, {
				title: '角色',
				field: 'name',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '角色描述',
				field: 'describe',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '用户数量',
				field: 'count',
				align: 'center',
				valign: 'bottom',
				sortable: true
			}]
		}
	};
	
	// 机构用户
	$scope.userList = {
		options: {
			method: "post",
			url: "/credit/user/user/v/page",
			cookie: true,
			queryParams: getParams,
			sidePagination: 'server',
			undefinedText: "-",
			sortName: "borrowingAmount",
			cache: false,
			striped: true,
			pagination: true,
			pageNumber: 1,
			pageSize: 15,
			pageList: ['All'],
			columns: [{
				title: '序号',
				field: 'id',
				align: 'center',
				valign: 'bottom',
				formatter: function(value, row, index) {
					return index+1;
				}
			}, {
				title: '姓名',
				field: 'name',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '城市',
				field: 'cityName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '手机号码',
				field: 'mobile',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '职务',
				field: 'position',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '角色',
				field: 'roleName',
				align: 'center',
				valign: 'bottom',
				formatter: roleFormatter
			}, {
				title: '部门',
				field: 'deptName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '账号状态',
				field: 'isEnable',
				align: 'center',
				valign: 'bottom',
				formatter: statusFormatter
			}]
		}
	};
});