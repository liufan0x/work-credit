angular.module("anjboApp", ['bsTable']).controller("productDataListCtrl", function($scope,$http, $filter,$cookies,$state, $timeout, box, route) {

	var productCode = route.getParams().productCode;
	$scope.productName = route.getParams().productName;
	$scope.productDataList = {};
	var postUrl = "";
	
	$http({
		method: 'POST',
		url: "/credit/product/data/flow/base/v/allList",
		data: {
			"productCode": productCode,
			"cityCode":"4403"
		}
	}).success(function(data) {
		var state = new Array();
		angular.forEach(data.data,function(data1){
			if(data1.processName.indexOf('关闭')<0&&data1.processName.indexOf('失败')<0&&data1.processName.indexOf('审批通过')<0&&data1.processName.indexOf('审批不通过')<0&&
					data1.processName.indexOf('已放款')<0&&data1.processName.indexOf('买卖双方信息')<0&&data1.processName.indexOf('已完结')<0){
				data1.processName = "待"+data1.processName;
			}
			if(data1.processName=='买卖双方信息'){
				data1.processName = "待完善"+data1.processName;
			}
			if(data1.processName!='审批通过'&&data1.processName!='已放款'){
				state.push(data1);
			}
		});
		$scope.stateList = state;
	});
	
	//刷新条件
	$scope.conditionList = new Array();
	var condition = new Object();
	condition.title = "贷款申请人";
	condition.key = "name";
	condition.type = 2;
	$scope.conditionList.push(condition);
	condition = new Object();
	condition.title = "订单状态";
	condition.key = "processId";
	condition.type = 1;
	$scope.conditionList.push(condition);
	condition = new Object();
	condition.title = "合作结构";
	condition.key = "cooperativeAgencyId";
	condition.type = 1;
	$scope.conditionList.push(condition);
	condition = new Object();
	condition.title = "渠道经理";
	condition.key = "channelManagerName";
	condition.type = 2;
	$scope.conditionList.push(condition);
	
	//排序条件
	var orderByList = new Array();
	
	//获取列表页面配置
	function getListPageConfig() {
		$http({
			method: 'POST',
			url: "/credit/config/page/list/v/pageListConfig",
			data: {
				"productCode": productCode
			}
		}).success(function(data) {
			//获取保存分页参数
			$scope.page = $cookies.getObject("pageParams");
			if(!$scope.page) {
				$scope.page = angular.fromJson(data.data.page);
			}
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
						if(row.state=='待提交评估'&&route.getUserDto().uid==row.repaymentMemberUid){
							html = '<a class="withdraw" href="javascript:void(0)" >撤回</a>';
						}
						if(row.state!='待提交评估'&&row.state!='待指派渠道经理'){
							html += '<a class="lookOrder" href="javascript:void(0)" >查看详情</a>';
						}
						if(route.getUserDto() &&(route.getUserDto().uid == row.createUid||route.getUserDto().uid == row.channelManagerUid||route.getUserDto().uid == row.currentHandlerUid)){
							if(row.state=='待提交评估'){
								html += '&nbsp;&nbsp;<a class="handleOrder" href="javascript:void(0)" >完善订单</a>';
							}else{
								if(row.state.indexOf('房产过户和抵押')<0&&row.state!='待客户经理审核'&&row.state!='待审批前材料准备'&&row.state!='待审批'&&row.state!='待评估'&&row.state!='待放款'&&row.state.indexOf('待')>=0){
									html += '&nbsp;&nbsp;<a class="handleOrder" href="javascript:void(0)" >'+row.state.replace('待','')+'</a>';
								}
							}
							if(row.state=='待提交评估'||row.state=='待提交申请按揭'||row.state=='待指派渠道经理'){
								html += '&nbsp;&nbsp;<a class="closeOrder" href="javascript:void(0)" >关闭订单</a>';
							}
							if(row.state=='已关闭'&&row.reopen){
								html += '<a class="reopen" href="javascript:void(0)" > 重新开启</a>';
							}
						}else if(html.indexOf('查看详情')<0){
							html += '&nbsp;&nbsp;<a class="lookOrder" href="javascript:void(0)" >查看详情</a>';
						}
						return html;
					}
					return data1.formatter;
				}else{
					var formatter =  data1.formatter;
					data1.formatter= function(value, row, index){
						var functionStr = new Function(formatter);
						return functionStr(value, row, index,$filter);
					}
				}
				data1.visible = columnSwitch[data1.field];
			});
			
			postUrl = data.data.postUrl;
			listConfig(data.data.pageListColumnsConfigDtos, columnSwitch);
		});
	}

	//组装请求参数
	function getParams(data) {
		//排序
		var orderByTempList = new Array();
		var orderBy = new Object();
		orderBy.sortName = data.sort;
		orderBy.sortRule = data.order;
		orderByTempList = orderByList.concat();
		orderByTempList.push(orderBy);
		
		$scope.page.start = data.offset;
		$scope.page.pageSize = data.limit;
		$scope.page.condition = $scope.conditionList;
		$scope.page.orderBy = orderByTempList;
		$cookies.putObject("pageParams", $scope.page);
		return $scope.page;
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

	//操作
	window.operateEvents = {
		'click .closeOrder': function(e, value, row, index) {
			$scope.closeReason = "";
			box.editAlert($scope,"确定要关闭此订单吗？",'<form class="form-inline"><textarea ng-model="closeReason" class="form-control" rows="5" cols="45" placeholder="请输入关闭理由，限200字（必填）"/></form>',function(){
				if(!$scope.closeReason){
					box.boxAlert("关闭理由必填！");
				}else if($scope.closeReason.length>200){
					box.boxAlert("限200字！");
				}else{
					$http({
						method: 'POST',
						url: "/credit/product/data/list/base/v/close",
						data:{
							'tblName':"tbl_cm_closeOrder",
							'orderNo': row.orderNo,
							'closeReason':$scope.closeReason,
							"state":"已关闭",
							"processId":"closeOrder"
						}
					}).success(function(data) {
						box.boxAlert(data.msg,function(){
							if(data.code == 'SUCCESS'){
								$scope.query();
								box.closeAlert();
							}
						})
					});
				}
			});
		},
		'click .reopen': function(e, value, row, index) {
			box.editAlert($scope,"开启",'确定要重新开启此订单吗？',function(){
				$http({
					method: 'POST',
					url: "/credit/product/data/list/base/v/reopen",
					data:{
						'tblName':"tbl_cm_reopen",
						'orderNo': row.orderNo
					}
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
			box.editAlert($scope,"撤回",'确定要撤回此订单吗？',function(){
				$http({
					method: 'POST',
					url: "/credit/product/data/list/base/v/withdraw",
					data:{
						'tblName':"tbl_cm_withdraw",
						'orderNo': row.orderNo
					}
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
		'click .handleOrder': function(e, value, row, index) {
			$state.go("productDataEdit", {
				'orderNo': row.orderNo,
				'productCode': row.productCode,
				'processId':row.processId,
				'tblName':$scope.page.tblName
			});
		},
		'click .lookOrder': function(e, value, row, index) {
			$state.go("productDataDetail", {
				'orderNo': row.orderNo,
				'productCode': row.productCode,
				'processId':row.processId,
				'tblName':$scope.page.tblName
			});
		}
	}
	
	$scope.showPlaceOrder = function(){
		
		$http({
			method: 'POST',
			url: "/credit/config/page/list/v/generateOrderNo",
		}).success(function(data) {
			$state.go("productDataEdit", {
				'orderNo': data.data,
				'productCode': productCode,
				'processId':"assess",
				'tblName':$scope.page.tblName
			});
		});
		
	}

	getListPageConfig();

});