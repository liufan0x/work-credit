angular.module("anjboApp", ['bsTable']).controller("agencyPageListCtrl", function($scope, $http, $timeout, $state, $window, parent, box, route) {

	//初始化数据
	$http({
		method: 'post',
		url: '/credit/data/dict/v/search',
		data: {
			type: "cityList"
		}
	}).success(function(data) {
		if("SUCCESS" == data.code) {
			$scope.orderCitys = data.data;
		}
	});

	//获取渠道经理
	$http({
		method: 'post',
		url: "/credit/user/user/v/searchByType",
		data: {
			type: "role",
			name: "渠道经理",
			agencyId: "1"
		}
	}).success(function(data) {
		$scope.channelManagerList = data.data;
	});

	//获取尽调经理
	$http({
		method: 'post',
		url: "/credit/user/user/v/searchByType",
		data: {
			type: "role",
			name: "尽调经理",
			agencyId: "1"
		}
	}).success(function(data) {
		$scope.investigationList = data.data;
	});

	//刷新条件
	$scope.conditionList = new Array();
	var condition = new Object();
	condition.title = "机构名称";
	condition.key = "agencyName";
	condition.type = 2;
	$scope.conditionList.push(condition);
	var condition = new Object();
	condition.title = "申请日期";
	condition.key = "createTime";
	condition.type = 4;
	$scope.conditionList.push(condition);
	condition = new Object();
	condition.title = "机构状态";
	condition.key = "processId";
	condition.type = 1;
	$scope.conditionList.push(condition);
	condition = new Object();
	condition.title = "城市";
	condition.key = "applyCity";
	condition.type = 5;
	$scope.conditionList.push(condition);
	condition = new Object();
	condition.title = "渠道经理";
	condition.key = "channelManagerUid";
	condition.type = 1;
	$scope.conditionList.push(condition);
	condition = new Object();
	condition.title = "尽调经理";
	condition.key = "investigationManagerUid";
	condition.type = 1;
	$scope.conditionList.push(condition);
	condition = new Object();
	condition.title = "联系人电话";
	condition.key = "contactsPhone";
	condition.type = 2;
	$scope.conditionList.push(condition);

	//排序条件
	var orderByList = new Array();

	//查询
	$scope.query = function() {
		var isDate = false;
		angular.forEach($scope.conditionList, function(data) {
			if(4 == data.type && data.startValue && data.endValue && data.startValue > data.endValue) {
				isDate = true;
			}
		});
		if(isDate) {
			box.boxAlert("结束日期不能小于开始日期");
			return;
		}
		$("#table").bootstrapTable('refresh', {
			url: postUrl,
			pageNumber: 1
		});
	}

	$scope.showPlaceOrder = function() {
		$http({
			method: 'POST',
			url: "/credit/page/list/v/generateOrderNo",
		}).success(function(data) {
			var param = {
				'orderNo': data.data,
				'productCode': "100",
				'processId': "agencyWaitDistribution",
				'cityCode': '4403',
				'state': "agencyPageList"
			};
			$state.go("agencyPageEdit", param);
		});
	}

	$scope.formatter = function(data1) {
		data1.events = operateEvents;
		data1.formatter = function(value, row, index) {
			var html = '';
			var state = "";
			if(row.state.indexOf("待") > -1 && (parent.userDto.uid == row.currentHandlerUid || parent.userDto.uid == row.createUid)) {
				state = row.state.substring(1, row.state.length);
				html = "<a class='operation' href='javascript:void(0);'>"+state+"</a>&nbsp;&nbsp;";
				html += "<a class='cancel' href='javasrcipt:void(0);'>取消</a>&nbsp;&nbsp;";
				if(row.state != "待分配"){
					html += "<a class='test' href='javasrcipt:void(0);'>测试账号&nbsp;&nbsp;</a>";
				}
			}else{
				html = "<a class='lookOrder' href='javasrcipt:void(0);'>查看</a>&nbsp;&nbsp;";
			}
			if(parent.userDto.uid == row.previousHandlerUid) {
				html += "<a class='withdraw' href='javascript:void(0);'>撤回</a>&nbsp;&nbsp;";
			}
			return html;
		}
	}

	window.operateEvents = {
		'click .operation': function(e, value, row, index) {
			var param = {
				'orderNo': row.orderNo,
				'productCode': row.productCode,
				'processId': row.processId,
				'cityCode': row.cityCode,
				'state': "agencyPageList"
			};
			$state.go("agencyPageEdit", param);
		},
		'click .withdraw': function(e, value, row, index) {
			var param = {
				"cityCode": row.cityCode,
				"productCode": row.productCode,
				"orderNo": row.orderNo,
				"processId": "agencyWaitDistribution",
				"state": "待分配",
			}
			$http({
				method: 'POST',
				url: "/credit/page/list/v/withdraw",
				data: param
			}).success(function(data) {
				if(data.code == 'SUCCESS') {
					$scope.query();
				}
			});

		},
		'click .lookOrder': function(e, value, row, index) {
			var param = {
				'orderNo': row.orderNo,
				'productCode': row.productCode,
				'processId': row.processId,
				'cityCode': row.cityCode,
				'state': "agencyPageList"
			};
			$state.go("agencyPageDetail", param);
		},
		'click .test': function(e, value, row, index) {
			if(!$scope.account) {
				$scope.account = new Object();
			}
			$scope.account.agencyId = row.agencyId;
			if(row.isOpen) {
				$scope.account.isOpen = row.isOpen;
			} else {
				$scope.account.isOpen = 2;
			}

			$scope.account.name = row.contactsName;
			$scope.account.mobile = row.contactsPhone;
			$scope.account.uid = row.accountUid;
			$scope.account.orderNo = row.orderNo;
			$scope.account.indateStart = $filter('date')(row.indateStart, 'yyyy-MM-dd');
			$scope.account.indateEnd = $filter('date')(row.indateEnd, 'yyyy-MM-dd');
			$scope.isAudit = false;
			box.editAlert2($scope, "提示", "<account-box-text></account-box-text>", testAccount);
		},
		'click .cancel': function(e, value, row, index) {
			var param = {
				'orderNo': row.orderNo,
				'productCode': row.productCode,
				"cityCode": row.cityCode
			};
			$http({
				method: 'POST',
				url: "/credit/page/list/v/cancel",
				data: param
			}).success(function(data) {
				box.boxAlert(data.msg, function() {
					if(data.code == 'SUCCESS') {
						$scope.backReason = "";
						$scope.query();
						box.closeAlert();
					}
				})
			});
		}
	}

});