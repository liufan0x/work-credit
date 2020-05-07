angular.module("anjboApp", ['bsTable']).controller("userListCtrl", function($scope,$timeout, $state, $http, box, route) {

	
	$http({
		method: 'post',
		url: "/credit/user/dept/v/search",
		data:{'agencyId':1}
	}).success(function(data) {
		$scope.deptList = data.data;
		$timeout(function(){
			$(".left-arrow").each(function(){
				$(this).toggleClass("shou");
				$(this).parent().next("ul").toggle();
			});
		});
	})

	
	$http({
		method: 'post',
		url: "/credit/user/role/v/search",
		data:{'agencyId':1}
	}).success(function(data) {
		$scope.roleList = data.data;
	})

	$scope.refresh = function(event) {
		$(event.target).toggleClass("shou");
		$(event.target).parent().next("ul").toggle();
		event.stopPropagation();
	}

	$(function() {
		$(".nav-box-top").on("click", function() {
			$(this).children(".nav-box-arrow").toggleClass("up");
			$(this).next(".nav-box").toggle();
			$scope.query();
		});
		$(".left-arrow").on("click", function() {
			$(this).toggleClass("shou");
			$(this).parent().next("ul").toggle();
		});
		$(".more-span").click(function() {
			var topnow = $(this).offset().top + 10;
			var leftnow = $(this).offset().left + 40;
			$(".nav-small").css({ "top": topnow, "left": leftnow });
			$(".nav-small").toggle();
		});
	});

	$scope.page = new Object();

	function getParams(data) {
		$scope.page.start = data.offset;
		$scope.page.pageSize = data.limit;
		return $scope.page;
	}
	
	function statusFormatter(value, row, index) {
		return 0==row.isEnable ? "启用" : "禁用";
	}
	function dingtalkFormatter(value, row, index) {
		return null!=value ? "是" : "否";
	}
	function operateFormatter(value, row, index) {
		var html = "";
		if($scope.meunAuth.indexOf('auth7auth')>=0){
			html = '<a class="edit" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
			if(row.isEnable == 0) {
				html += '<a class="disable" href="javascript:void(0)">禁用</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="enable" href="javascript:void(0)">启用</a>&nbsp;&nbsp;';
			}
			html += '<a class="auth" href="javascript:void(0)">权限</a>&nbsp;&nbsp;';
			html += '<a class="resetPwd" href="javascript:void(0)">重置密码</a>&nbsp;&nbsp;';
		}
		return html;
	}

	window.operateEvents = {
		'click .edit': function(e, value, row, index) {
			$state.go("userEdit", { 'uid': row.uid });
		},
		'click .disable': function(e, value, row, index) {
			box.confirmAlert("禁用用户", "确定禁用该用户吗？", function() {
				//禁用
				var userDto = {
					"uid":row.uid,
					"mobile":row.mobile,
					"isEnable":1
				};
				$http({
					method: 'post',
					url: "/credit/user/user/v/edit",
					data:userDto
				}).success(function(data) {
					if(data.code == 'SUCCESS'){
						$scope.query();
					}
					box.boxAlert(data.msg);
				})
			});

		},
		'click .enable': function(e, value, row, index) {
			//启用
			box.confirmAlert("启用用户", "确定启用该用户吗？", function() {
				//禁用
				var userDto = {
					"uid":row.uid,
					"mobile":row.mobile,
					"isEnable":0
				};
				$http({
					method: 'post',
					url: "/credit/user/user/v/edit",
					data:userDto
				}).success(function(data) {
					if(data.code == 'SUCCESS'){
						$scope.query();
					}
					box.boxAlert(data.msg);
				})
			});
		},
		'click .auth': function(e, value, row, index) {
			// 跳权限
			$state.go("authority", { 'uid': row.uid, 'agencyId': '-1', 'roleId': '-1' ,'showName':row.name});
		},
		'click .resetPwd': function(e, value, row, index) {
			// 重置密码
			box.confirmAlert("重置密码", "确定要重置该用户密码吗？", function() {
				$http({
					method: 'post',
					url: "/credit/user/user/v/resetPwd",
					data:row
				}).success(function(data) {
					box.boxAlert(data.msg);
				})
			});
		}
	};
	
	var url = "/credit/user/user/v/page";
	$scope.query = function(obj) {
		if(obj != null) {
			$scope.page.deptId = String(obj.id);
		}
		$("#table").bootstrapTable('refresh', { url: url,pageNumber:1});
	}

	$scope.userList = {
		options: {
			method: "post",
			url: url,
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
				title: '账号',
				field: 'account',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '职务',
				field: 'position',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '状态',
				field: 'isEnable',
				align: 'center',
				valign: 'bottom',
				formatter: statusFormatter
			}, {
				title: '角色',
				field: 'roleName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '部门',
				field: 'deptName',
				width: '35%', 
				align: 'left',
				valign: 'bottom'
			}, {
				title: '已同步钉钉',
				field: 'dingtalkUid',
				align: 'center',
				valign: 'bottom',
				formatter: dingtalkFormatter
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