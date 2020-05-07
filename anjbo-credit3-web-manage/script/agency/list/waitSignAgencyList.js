angular.module("anjboApp", ['bsTable']).controller("waitSignAgencyCtrl", function($scope, $http, $filter,$state,$cookies, box, route,parent) {

	var productCode = route.getParams().productCode;
	$scope.productName = route.getParams().productName;
	$scope.productDataList = {};
	var postUrl = "";
	//var productCode = 100;
	var cityCode = "104403";
	$http({
		method: 'POST',
		data: {'productId':4403100},
		url: '/credit/data/process/v/search'
	}).success(function(data) {
		var state = new Array();
		var index = 0;
		angular.forEach(data.data,function(data1){
			if("addAgency"!=data1.processId&&"agencyFailToexamine"!=data1.processId){
				state[index++] = data1;
			}
		});
		state = sort(state);
		$scope.stateList = state;
	});
	function sort(sorts){
		var tmp;
		for (var i=0;i<sorts.length;i++){
			for (var j=0;j<i;j++){
				if(sorts[i].sort<sorts[j].sort){
					tmp = sorts[i];
					sorts[i] = sorts[j];
					sorts[j] = tmp;
				}
			}
		}
		return sorts;
	}
	
	//初始化数据
	$http({
		method: 'post',
		url:'/credit/data/dict/v/search',
		data:{type:"cityList"}
	}).success(function(data) {
		if("SUCCESS"==data.code){
			$scope.orderCitys = data.data;
		}
	});
	
	//获取渠道经理
	$http({
		method: 'post',
		url: "/credit/user/user/v/searchByType",
		data:{type:"role",name:"渠道经理",agencyId:"1"}
	}).success(function(data) {
		$scope.channelManagerList = data.data;
	});
	
	//获取尽调经理
	$http({
		method: 'post',
		url: "/credit/user/user/v/searchByType",
		data:{type:"role",name:"尽调经理",agencyId:"1"}
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

	//获取列表页面配置
	function getListPageConfig() {
		$http({
			method: 'POST',
			url: "/credit/page/config/v/pageListConfig",
			data: {
				"productCode": productCode
			}
		}).success(function(data) {
			//获取保存分页参数
			//$scope.page = $cookies.getObject("pageParams");
			//if(!$scope.page) {
				$scope.page = angular.fromJson(data.data.page);
			//}

			//获取保存的显示隐藏列
			var columnSwitch = $cookies.getObject("columnSwitch");
			if(!columnSwitch) {
				columnSwitch = data.data.columnSwitch;
			}
			//表格显示值计算
			angular.forEach(data.data.pageListColumnsConfigDtos,function(data1){
				if(data1.field == 'operate'){
					data1.events = operateEvents;
					data1.formatter = function(value, row, index){
						var html ='';
						var state = "";
						if(row.state == "待分配"){
							row.cancelSate = "取消分配";
//							if(parent.userDto.uid==row.currentHandlerUid||parent.userDto.uid==row.createUid){
								html = "<a class='operation' href='javascript:void(0);'>分配</a>&nbsp;&nbsp;"+
									"<a class='cancel' href='javascript:void(0);'>"+row.cancelSate+"</a>&nbsp;&nbsp;";

//							}
							html +="<a class='lookOrder' href='javasrcipt:void(0);'>查看</a>&nbsp;&nbsp;";
						} else if(row.state.indexOf("待")>-1){
							state = row.state.substring(1,row.state.length);
							row.cancelSate = "取消"+state;
							var html = "";

//							if(row.channelManagerUid==parent.userDto.uid){
								html = "<a class='operation' href='javascript:void(0);'>"+state+"</a>&nbsp;&nbsp;"+
									"<a class='cancel' href='javascript:void(0);'>"+row.cancelSate+"</a>&nbsp;&nbsp;";

								if(parent.userDto.uid==row.previousHandlerUid){
									html += "<a class='withdraw' href='javascript:void(0);'>撤回</a>&nbsp;&nbsp;";
								}
								html += "<a class='lookOrder' href='javasrcipt:void(0);'>查看</a>&nbsp;&nbsp;"+
										"<a class='test' href='javasrcipt:void(0);'>测试账号</a>";
								/*
								var date = new Date().getTime();
								if(!row.indateEnd||row.indateEnd<=date){
									html += "<a class='test' href='javasrcipt:void(0);'>测试账号</a>";
								}*/
//							} else {
								if(parent.userDto.uid==row.previousHandlerUid){
									html += "<a class='withdraw' href='javascript:void(0);'>撤回</a>&nbsp;&nbsp;";
								}
								html +="<a class='lookOrder' href='javasrcipt:void(0);'>查看</a>&nbsp;&nbsp;";
//							}
						} else if(row.state == "已签约"||(row.state.indexOf("取消")>-1)||row.processId == "agencyFailToexamine"){
							html ="<a class='lookOrder' href='javasrcipt:void(0);'>查看</a>&nbsp;&nbsp;";
						}
						return html;
					}
					data1.visible = columnSwitch[data1.field];
					return data1.formatter;
				}else{
					var formatter =  data1.formatter;
					data1.formatter= function(value, row, index){
						if(""==value){
							value = "-";
						} else if(value&&value.length>20){
							var len = value.length;
							var k = len%20;
							var i = (len - k)/20;
							if(k>0){
								i += 1;
							}
							var tmp = "";
							for (var j=1;j<=i;j++){
								if(""==tmp){
									tmp = value.substring(0,20*j);
								} else if((20*j)>value.length){
									tmp += "<br/>"+value.substring(20*(j-1),value.length);
								} else {
									tmp += "<br/>"+value.substring(20*(j-1),20*j);
								}

							}
							value = tmp;
						}
						var functionStr = new Function(formatter);
						return functionStr(value, row, index,$filter);
					}
				}
				data1.visible = columnSwitch[data1.field];
			});
			postUrl = data.data.postUrl;
			postUrl = "/credit/page/data/v/pageListData";
			listConfig(data.data.pageListColumnsConfigDtos, columnSwitch);
		});
	}
	//组装列表
	function listConfig(columns, columnSwitch) {
		$scope.productDataList = {
			options: {
				method: "post",
				url: postUrl,
				queryParams: getParams,
				sidePagination: 'server',
				undefinedText: "-",
				cache: false,
				striped: true,
				pagination: true,
				pageNumber: ($scope.page.start / $scope.page.pageSize) + 1,
				pageSize: $scope.page.pageSize,
				pageList: ['All'],
				showColumns: true,
				showRefresh: false,
				onClickRow: function(row, $element, field) {
					$element.toggleClass("bule-bg");
				},
				onColumnSwitch: function(field, checked) {
					columnSwitch[field] = checked;
					$cookies.putObject("columnSwitch", columnSwitch);
				},
				columns: columns
			}
		}
	}

	//查询
	$scope.query = function() {
		$("#table").bootstrapTable('refresh', {
			url: postUrl,
			pageNumber: 1
		});
	}
	//重置查询条件
	$scope.restart = function(){
		angular.forEach($scope.conditionList,function(data){
			data.value = "";
			data.startValue = "";
			data.endValue = "";
		});
	}
	//组装请求参数
	function getParams(data) {
		$scope.page.start = data.offset;
		$scope.page.pageSize = data.limit;
		$scope.page.condition = $scope.conditionList;
		$cookies.putObject("pageParams", $scope.page);
		return $scope.page;
	}

	//查询
	$scope.query = function() {
		var isDate = false;
		angular.forEach($scope.conditionList,function(data){
			if(4==data.type&&data.startValue&&data.endValue&&data.startValue>data.endValue){
				isDate = true;
			}
		});
		if(isDate){
			box.boxAlert("结束日期不能小于开始日期");
			return;
		}
		$("#table").bootstrapTable('refresh', {
			url: postUrl,
			pageNumber: 1
		});
	}
	getListPageConfig();
	//操作
	window.operateEvents = {
		'click .operation': function(e, value, row, index) {
			var param = {
				'orderNo': row.orderNo,
				'productCode': productCode,
				'processId':row.processId,
				'tblName':"tbl_sm_",
				'cityCode':cityCode
			};
			$scope.go("agencyPublicEdit",param);
		},
		'click .restart': function(e, value, row, index) {
			var state;
			if(row.state.indexOf("待")>-1){
				state = row.state.substring(1,row.state.length);
			} else if(row.state.indexOf("取消")>-1){
				state = row.state.substring(2,row.state.length);
			}
			var title = "确认重启该机构"+row.agencyName+""+state+"流程吗？";
			box.editAlert($scope,"提示",title,function(){
				var param = {
					'orderNo': row.orderNo,
					'productCode': productCode,
					'processId':row.processId,
					'tblName':"tbl_sm_",
					"cityCode":cityCode
				};
				$http({
					method: 'POST',
					url: "/credit/product/data/sm/agency/v/restart",
					data:param
				}).success(function(data) {
					box.boxAlert(data.msg,function(){
						if(data.code == 'SUCCESS'){
							$scope.query();
							box.closeAlert();
						}
					})
				});
			});
		},
		'click .withdraw': function(e, value, row, index) {
			var param = {
				"processId":row.processId,
				"cityCode":cityCode,
				"productCode":productCode
			}
			$http({
				method: 'POST',
				url: "/credit/product/data/sm/agency/v/getPreProcessId",
				data:param
			}).success(function(data) {
				if(data.code == 'SUCCESS'){
					withdraw(data,row);
				}
			});


		},
		'click .lookOrder': function(e, value, row, index) {
			var param = {
				'orderNo': row.orderNo,
				'productCode': productCode,
				'processId':row.processId,
				'tblName':"tbl_sm_",
				'cityCode':cityCode
			};
			$scope.go("agencyDataDetail",param);
		},
		'click .test': function(e, value, row, index) {
			if(!$scope.account){
				$scope.account = new Object();
			}
			$scope.account.agencyId = row.agencyId;
			if(row.isOpen){
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
			box.editAlert2($scope, "提示", "<account-box-text></account-box-text>",testAccount);
		},
		'click .cancel': function(e, value, row, index) {
			$scope.cancelTile = row.cancelSate;
			$scope.beCareful = "";
			if(row.processId=="agencyWaitInvestigation"
				||row.processId=="agencyWaitConfirm"
				||row.processId=="agencyWaitToexamine"
				||row.processId=="agencyWaitSign"){
				$scope.beCareful = "注：我们将会发送短信通知渠道经理该机构已经"+row.cancelSate;
			}
			box.editAlert($scope, "提示", "<cancel-box-text></cancel-box-text>", function () {

				if (!$scope.backReason||"" == $scope.backReason) {
					alert("请填写取消原因");
					return false;
				} else {
					box.closeAlert();
					$scope.beCareful = "";
					var param = {
						'orderNo': row.orderNo,
						'productCode': productCode,
						'processId':row.processId,
						'tblName':"tbl_sm_",
						'backReason':$scope.backReason,
						"cityCode":cityCode
					};
					$http({
						method: 'POST',
						url: "/credit/product/data/sm/agency/v/cancel",
						data:param
					}).success(function(data) {
						box.boxAlert(data.msg,function(){
							if(data.code == 'SUCCESS'){
								$scope.backReason = "";
								$scope.query();
								box.closeAlert();
							}
						})
					});
				}

			});
		}
	}
	function withdraw(obj,row){
		var context = "<p>是否确定将该机构从"+row.state+"状态撤回至"+obj.data.state+"状态？</p>";
		if("agencyWaitDistribution"==obj.data.preProcessId){
			context += "<p><font color='red'>注：撤回成功后需要重新分配渠道经理.</font></p>";
		} else if("agencyWaitConfirm"==obj.data.preProcessId){
			context += "<p><font color='red'>注：撤回成功后需要重新分配尽调经理.</font></p>";
		}
		box.editAlert($scope,"提示",context,function(){
			var param = {
				'orderNo': row.orderNo,
				'productCode': productCode,
				'processId':row.processId,
				'tblName':"tbl_sm_",
				"cityCode":cityCode
			};
			$http({
				method: 'POST',
				url: "/credit/product/data/sm/agency/v/withdraw",
				data:param
			}).success(function(data) {
				box.closeAlert();
				if(data.code == 'SUCCESS'){
					$scope.query();
				} else {
					box.boxAlert(data.msg);
				}

			});
		});
	}
	function testAccount(){
		$scope.isAudit = false;
		if(2==$scope.account.isOpen){
			$scope.account.isEnable = 1;
		} else {
			$scope.account.isEnable = 0;
		}
		if(0==$scope.account.isEnable&&$scope.accountForm.$invalid){
			$scope.isAudit = true;
			alert("请填写必填信息");
		} else if(0==$scope.account.isEnable&&$scope.account.indateStart>$scope.account.indateEnd){
			box.boxAlert("前置时间不能大于后置时间!");
			return;
		} else {
			box.closeAlert();
			 $scope.account.tblName = "tbl_sm_";
			 $http({
			 method: 'POST',
			 url: "/credit/product/data/sm/agency/v/testAgencyAccount",
			 data:$scope.account
			 }).success(function(data) {
				 if(data.code == 'SUCCESS'){
					 box.boxAlert("操作成功!");
					 $scope.query();
				 } else {
					 box.boxAlert(data.msg);
				 }

			 });
		}
	}
	$scope.showPlaceOrder = function(){
		$http({
			method: 'POST',
			url: "/credit/page/data/v/generateOrderNo",
		}).success(function(data) {
			$scope.go("agencyPublicEdit", {
				'orderNo': data.data,
				'productCode': productCode,
				'processId':"addAgency",
				'tblName':'tbl_sm_',
				'cityCode':cityCode
			});
		});
	}

	$scope.go = function(state,params){
		$state.go(state,params);
	}

}).directive('cancelBoxText', function($http, route) {
	return {
		restrict: "E",
		templateUrl: '/template/agency/common/cancelBoxText.html',
		link: function(scope) {

		}
	};
}).directive('accountBoxText', function($http, route) {
	return {
		restrict: "E",
		templateUrl: '/template/agency/common/accountBoxText.html',
		link: function(scope) {

		}
	};
});