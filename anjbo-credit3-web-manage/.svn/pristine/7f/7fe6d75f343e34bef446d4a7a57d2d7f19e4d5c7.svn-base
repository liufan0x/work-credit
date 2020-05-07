angular.module("anjboApp", ['bsTable']).controller("roleListCtrl", function($scope, $state, $http, box, route) {

	
	$scope.page = new Object();
	$scope.page.count = 1;

	function getParams(data) {
		$scope.page.start = data.offset;
		$scope.page.pageSize = data.limit;
		return $scope.page;
	}

	function operateFormatter(value, row, index) {
		var html = "";
		if($scope.meunAuth.indexOf('auth9auth')>=0){
			html = '<a class="edit" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
			html += '<a class="auth" href="javascript:void(0)">权限</a>&nbsp;&nbsp;';
		}
		return html;
	}

	$scope.addRole = function() {
		$scope.roleDto = new Object();
		box.editAlert($scope, "新增角色", $("#roleEditId").html(), function() {
			if(!$scope.frmRole.$valid) {
				$scope.isAudit = true;
				//alert("请完善所有必填信息！");
				return;
			}
			$http({
				method: 'post',
				url: "/credit/user/role/v/add",
				data: $scope.roleDto
			}).success(function(data) {
				if(data.code == "SUCCESS") {
					$scope.query();
					box.closeAlert("新增成功");
					
				}
			})
		});
	}

	window.operateEvents = {
		'click .edit': function(e, value, row, index) {
			$scope.roleDto = row;
			box.editAlert($scope, "修改角色", $("#roleEditId").html(), function() {
				if(!$scope.frmRole.$valid) {
					$scope.isAudit = true;
					//alert("请完善所有必填信息！");
					return;
				}
				$http({
					method: 'post',
					url: "/credit/user/role/v/edit",
					data: $scope.roleDto
				}).success(function(data) {
					if(data.code == "SUCCESS") {
						$scope.query();
						box.boxAlert("修改成功");
					}
				})
			});
		},
		'click .auth': function(e, value, row, index) {
			// 跳权限
			$state.go("authority", { 'uid': '-1', 'agencyId': '-1', 'roleId': row.id ,'showName':row.name});
		}
	};
	
	var url = "/credit/user/role/v/page";
	$scope.query = function() {
		$("#table").bootstrapTable('refresh', { url: url,pageNumber:1 });
	}

	$scope.roleList = {
		options: {
			method: "post",
			url: url,
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
				valign: 'bottom'
			}, {
				title: '操作',
				field: 'operate',
				align: 'center',
				valign: 'bottom',
				events: operateEvents,
				formatter: operateFormatter
			}]
		}
	};

});