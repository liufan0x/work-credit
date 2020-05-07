angular.module("anjboApp").controller("placeOrderEditCtrl", function($scope,$rootScope,$timeout, $http, $state, process, $compile, route, box) {

	//订单
	var params = {"orderNo":route.getParams().orderNo};
	$scope.inProductTypeCode = route.getParams().productCode; //入参
	$scope.inRelationOrderNo = ""!=route.getParams().relationOrderNo&&route.getParams().relationOrderNo.length>5 ? route.getParams().relationOrderNo : null;
	$scope.productId = route.getParams().cityCode + '' + route.getParams().productCode; //业务产品
	$scope.addCangDai = route.getParams().addCangDai;
	console.debug(params.orderNo +"-(完善订单)订单号/关联订单号-"+ $scope.inRelationOrderNo);

	$scope.isAudit = false;
	$scope.serviceFees;
	$scope.serviceFeesCD;
	$scope.chargeStandard;
	
	//初始化订单信息信息
	function initOrder() {
		//借款信息
		$http({
			method: 'POST',
			url: 'credit/order/borrow/v/query',
			data: params
		}).success(function(data) {
			//初始化页面
			$scope.order = data.data;
			if(!$scope.order.channelManagerUid) {
				$scope.order.cooperativeAgencyId = undefined;
			}
			//回款日期
			if($scope.order.orderReceivableForDto&&$scope.order.orderReceivableForDto.length>0){
				for(var i=0;i<$scope.order.orderReceivableForDto.length;i++){
					$scope.order.orderReceivableForDto[i].payMentAmountDate = $scope.order.orderReceivableForDto[i].payMentAmountDateStr;
				}
			}
			if($scope.order.oldLoanBankSubNameId) {
				$scope.order.oldLoanBankSubNameId = String($scope.order.oldLoanBankSubNameId);
			}
			if($scope.order.oldLoanBankNameId) {
				$scope.order.oldLoanBankNameId = String($scope.order.oldLoanBankNameId);
			}
			if($scope.order.loanBankNameId) {
				$scope.order.loanBankNameId = String($scope.order.loanBankNameId);
			}
			if($scope.order.loanSubBankNameId) {
				$scope.order.loanSubBankNameId = String($scope.order.loanSubBankNameId);
			}
			if($scope.order.rebateBankId) {
				$scope.order.rebateBankId = String($scope.order.rebateBankId);
			}
			if($scope.order.rebateBankSubId) {
				$scope.order.rebateBankSubId = String($scope.order.rebateBankSubId);
			}
			//合作机构
			if($scope.order.cooperativeAgencyId) {
				$scope.order.cooperativeAgencyId = String($scope.order.cooperativeAgencyId);
			}
			if($scope.order.cooperativeAgencyName) {
				$scope.order.cooperativeAgencyName = String($scope.order.cooperativeAgencyName);
			}
			//受理员
			if($scope.order.acceptMemberUid) {
				$scope.order.acceptMemberUid = String($scope.order.acceptMemberUid);
			}
			if($scope.order.acceptMemberName) {
				$scope.order.acceptMemberName = String($scope.order.acceptMemberName);
			}
			if(!$scope.order.isLoanBank || $scope.order.isLoanBank == "null") {
				$scope.order.isLoanBank = "2";
			} else {
				$scope.order.isLoanBank = String($scope.order.isLoanBank);
			}

			if(!$scope.order.isOldLoanBank || $scope.order.isLoanBank == "null") {
				$scope.order.isOldLoanBank = "2";
			} else {
				$scope.order.isOldLoanBank = String($scope.order.isOldLoanBank);
			}
			if(!$scope.order.isRebate || $scope.order.isRebate == "null") {
				$scope.order.isRebate = "2";
			} else {
				$scope.order.isRebate = String($scope.order.isRebate);
			}
			
			if(!$scope.order.isOnePay || $scope.order.isOnePay == "null") {
				$scope.order.isOnePay = "1";
			} else {
				$scope.order.isOnePay = String($scope.order.isOnePay);
			}
			//费用支付方式
			if($scope.order.paymentMethod!=0 && $scope.order.paymentMethod != "0") {
				$scope.order.paymentMethod = $scope.order.paymentMethod+"";
			}else{
				$scope.order.paymentMethod = null;
			}

			//债务置换贷款风控等级
			if($scope.order.riskGradeId) {
				if($scope.order.riskGradeId == "0" || $scope.order.riskGradeId != "") {
					$scope.order.riskGradeId = String($scope.order.riskGradeId);
				}
			} else if($scope.order.riskGradeId == 0) {
				$scope.order.riskGradeId = String($scope.order.riskGradeId);
			}
			//列表点击添加关联订单默认展开畅贷
			if($scope.addCangDai != 0&&$scope.addCangDai) {
				if($scope.order.orderBaseBorrowRelationDto.length == 0) {
					$scope.order.orderBaseBorrowRelationDto = new Array();
					$scope.order.orderBaseBorrowRelationDto.push(new Object());
					$scope.order.orderBaseBorrowRelationDto[0].productCode = '03';
				}
			}
			if($scope.order.orderBaseBorrowRelationDto.length > 0) {
				//畅贷风控等级
				if($scope.order.orderBaseBorrowRelationDto[0].riskGradeId) {
					if($scope.order.orderBaseBorrowRelationDto[0].riskGradeId == "0" || $scope.order.orderBaseBorrowRelationDto[0].riskGradeId != "") {
						$scope.order.orderBaseBorrowRelationDto[0].riskGradeId = String($scope.order.orderBaseBorrowRelationDto[0].riskGradeId);
					}
				} else if($scope.order.orderBaseBorrowRelationDto[0].riskGradeId == 0) {
					$scope.order.orderBaseBorrowRelationDto[0].riskGradeId = String($scope.order.orderBaseBorrowRelationDto[0].riskGradeId);
				}
			}
		})
		// 关联订单编辑、查看融合页
		if(null != $scope.inRelationOrderNo){
			$http({
				method: 'POST',
				url: 'credit/order/borrow/v/query',
				data: {"orderNo":$scope.inRelationOrderNo}
			}).success(function(data) {
				$scope.borrow = data.data;
			});	
			$http({
				method: 'POST',
				url: '/credit/element/basics/v/detail',
				data: {"orderNo":$scope.inRelationOrderNo}
			}).success(function(data) {
				$scope.__foreclosure = data.data;
				if($scope.__foreclosure) {
					$scope._foreclosure = data.data.foreclosureType;
					$scope._paymentType = data.data.paymentType;					
				}
			});
		}		
	}
	
	initOrder();
	
	//切换tab
	$scope.changeView = function(view) {
		$rootScope.showView = view;
		var state = '';
		switch ($scope.inProductTypeCode){
			case "03":
				switch(view){
					case 1: state='orderEdit.placeOrderEdit.placeBorrowEditCD';		break;
					case 2: state='orderEdit.placeOrderEdit.placeCustomerEditCD';	break;
					case 3: state='orderEdit.placeOrderEdit.placeHouseEditCD';		break;
					case 4: state='orderEdit.placeOrderEdit.placeElementEditCD';	break;
					case 5: state='orderEdit.placeOrderEdit.placeEnquiryEditCD';	break;
					case 6: state='orderEdit.placeOrderEdit.placeBusinfoEditCD';	break;
					case 7: state='orderEdit.placeOrderEdit.placeCreditEditCD';		break;
					default: break;
				}
				break;
			default:
				switch(view){
					case 1: state='orderEdit.placeOrderEdit.placeBorrowEdit';		break;
					case 2: state='orderEdit.placeOrderEdit.placeCustomerEdit';		break;
					case 3: state='orderEdit.placeOrderEdit.placeHouseEdit';		break;
					case 4: state='orderEdit.placeOrderEdit.placeElementEdit';		break;
					case 5: state='orderEdit.placeOrderEdit.placeEnquiryEdit';		break;
					case 6: state='orderEdit.placeOrderEdit.placeBusinfoEdit';		break;
					case 7: state='orderEdit.placeOrderEdit.placeCreditEdit';		break;
					default: break;
				}
				break;
		}		
		$state.go(state);
	}
	$scope.changeView(1);
	
	
	//提交审核
	$scope.showSubmitManager = function(showView) {
		$timeout(check(showView),600);
		function check(showView){
			var orderNo = route.getParams().orderNo;
			if(1==$scope.order.isOldLoanBank && ""==$scope.order.oldLoanBankSubNameId){
				box.boxAlert("请完善借款信息，原贷款银行不能为空");
				return;
			}else if(1==$scope.order.isLoanBank && ""==$scope.order.loanSubBankNameId){
				box.boxAlert("请完善借款信息，新贷款银行不能为空");
				return;
			}
			if($scope.addCangDai != 0&&$scope.addCangDai) {				
				//借款信息
				$http({
					method: 'POST',
					url: 'credit/order/borrow/v/query',
					data: params
				}).success(function(data) {
					//初始化页面
					var data = data.data;
					if(!data.orderBaseBorrowRelationDto[0]){
						alert("请先保存畅贷信息");
						return;
					}else{
						orderNo = data.orderBaseBorrowRelationDto[0].orderNo;
						if(!orderNo){
							alert("请先保存畅贷信息");
							return;
						}
					}
					
					box.waitAlert();
					$scope.isAudit = true;
					$rootScope.showView = 0;
					$timeout(function(){
						$rootScope.showView = showView;
						$http({
							method: 'POST',
							url: "/credit/order/borrow/v/check",
							data: {"orderNo":orderNo}
						}).success(function(data) {
							box.closeWaitAlert();
							if(data.code == "SUCCESS"){
								submitManager(data.data=='100');	
							}else{
								box.boxAlert(data.msg,function(){
									if(data.data){
										$scope.changeView(data.data);	
									}
								});
							}
						}).error(function(){
							box.closeWaitAlert();
						})
					});
				})
			}else{
				box.waitAlert();
				$scope.isAudit = true;
				$rootScope.showView = 0;
				$timeout(function(){
					$rootScope.showView = showView;
					$http({
						method: 'POST',
						url: "/credit/order/borrow/v/check",
						data: {"orderNo":orderNo}
					}).success(function(data) {
						box.closeWaitAlert();
						if(data.code == "SUCCESS"){
							submitManager(data.data=='100');	
						}else{
							box.boxAlert(data.msg,function(){
								if(data.data){
									$scope.changeView(data.data);	
								}
							});
						}
					}).error(function(){
						box.closeWaitAlert();
					})
				});
			}
		}
		
	}
	
	
	//提交经理审批
	function submitManager(fl){
		var submit = function() {			
			var orderNo = route.getParams().orderNo;
			if($scope.addCangDai != 0&&$scope.addCangDai) {
				//借款信息
				$http({
					method: 'POST',
					url: 'credit/order/borrow/v/query',
					data: params
				}).success(function(data) {
					//初始化页面
					var data = data.data;
					if(!data.orderBaseBorrowRelationDto[0]){
						box.boxAlert("请先保存畅贷信息");
						return;
					}else{
						orderNo = data.orderBaseBorrowRelationDto[0].orderNo;
						var currentHandlerUid;
						if($scope.sumbitDto&&$scope.sumbitDto.uid){
							currentHandlerUid = $scope.sumbitDto.uid
						}
						$http({
							method: 'POST',
							url: "/credit/order/borrow/v/submitAudit",
							data: {
								"orderNo": orderNo,
								"currentHandlerUid": currentHandlerUid
							}
						}).success(function(data) {
							box.closeWaitAlert();
							box.boxAlert(data.msg,function(){
								if(data.code == "SUCCESS") {
									box.closeAlert();
									$state.go("orderList");
								}
							});
						})
					}
				})
			}else{
				if(fl){  //退回重新提交
					box.waitAlert();
					$http({
						method: 'POST',
						url: "/credit/order/borrow/v/submitAudit",
						data: {
							"orderNo": orderNo,
						}
					}).success(function(data) {
						box.closeWaitAlert();
						box.boxAlert(data.msg,function(){
							if(data.code == "SUCCESS") {
								box.closeAlert();
								$state.go("orderList");
							}
						});
					})
				}else{ 
					if(!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid){
						box.boxAlert("请选择分配订单员");
						return;
					}
					var currentHandlerUid = $scope.sumbitDto.uid
					// 畅贷不选还款专员
					if((undefined!=$scope.inProductTypeCode && "03"==$scope.inProductTypeCode)||"05"==$scope.inProductTypeCode){
						box.waitAlert();
						$http({
							method: 'POST',
							url: "/credit/order/borrow/v/submitAudit",
							data: {
								"orderNo": orderNo,
								"currentHandlerUid": currentHandlerUid,
								"receptionManagerUid":$scope.order.foreclosureMemberUid
							}
						}).success(function(data) {
							box.closeWaitAlert();
							box.boxAlert(data.msg,function(){
								if(data.code == "SUCCESS") {
									box.closeAlert();
									$state.go("orderList");
								}
							});
						});
						return;
					}
					var next=function(){
						if(!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid){
							box.boxAlert("请选择受理经理");
							return;
						}
						box.waitAlert();
						var receptionManagerUid = $scope.sumbitDto.uid;
						$http({
							method: 'POST',
							url: "/credit/order/borrow/v/submitAudit",
							data: {
								"orderNo": orderNo,
								"currentHandlerUid": currentHandlerUid,
								"receptionManagerUid":receptionManagerUid
							}
						}).success(function(data) {
							box.closeWaitAlert();
							box.boxAlert(data.msg,function(){
								if(data.code == "SUCCESS") {
									box.closeAlert();
									$state.go("orderList");
								}
							});
						})
					}
					$scope.personnelType = "指派还款专员";
					box.editAlert($scope, "请选择受理经理", "<submit-box></submit-box>", next);
				}
			}
		}
		
		$http({
			method: 'POST',
			url: "/credit/order/borrow/v/check",
			data: {
				"orderNo": route.getParams().orderNo,
			}
		}).success(function(data) {
				if(data.code == "SUCCESS") {
					if(fl){
						box.confirmAlert("提交审核","确定提交审批吗？",submit);
					}else{
						$scope.personnelType = "分配订单";
						box.editAlert($scope, "订单提交审核吗，请选择分配订单员", "<submit-box></submit-box>", submit);
					}
				}
				else{
					box.boxAlert(data.msg);
				}
		})
	}

});