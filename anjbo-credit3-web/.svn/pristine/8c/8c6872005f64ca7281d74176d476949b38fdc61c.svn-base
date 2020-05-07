angular.module("anjboApp").controller("allocationFundEditCtrl", function($scope, route, $http, $state, box) {

	$scope.obj = new Object();
	$scope.huaan = new Object();
	$scope.huaanTmp = new Object();
	$scope.productCode = route.getParams().productCode;
	$scope.isPush = false; //是否需要推送

	$scope.obj.orderNo = $scope.orderNo;
	$scope.huanShow = false; //推送页面
	$scope.fundShow = true; //资金方
	$scope.title = "orderShow"; //推送Tab
	$scope.fund = null; //资金方
	$scope.selectTypeId = 0; //typeId
	$scope.orderIsBack = false;
	$scope.isToBack = 1; //退回重新走流程  

	//加载资金分配方信息
	loadFund = function() {
		var param = {
			status: 1
		}

		//预匹配资方信息
		$http({
			url: '/credit/order/allocationFund/v/preMatchedFund',
			method: 'POST',
			data: {
				"orderNo": $scope.orderNo
			}
		}).success(function(data) {
			if("SUCCESS" == data.code) {
				$scope.preFundModelList = data.data.preFundModel;
				$scope.fundName = data.data.fundName;
			} else {
				console.log(data.msg);
			}
		});

		$http({
			url: '/credit/user/fund/v/search',
			method: 'POST',
			data: param
		}).success(function(data) {
			if("SUCCESS" == data.code) {
				$scope.dataList = data.data;
				angular.forEach($scope.dataList, function(data, index, array) {
					data.isCheck = false;
					if(data.fundCode == "114" || data.fundCode == "115") {
						$scope.yunNanAccoumt(data.fundCode);
					}
					if (data.fundCode == "1000") {
						$scope.sgtAccoumt();
					}
				});
			} else {
				box.boxAlert(data.msg);
			}
		});
		$http({
			method: 'POST',
			url: "/credit/order/flow/v/selectEndOrderFlow",
			data: {
				"orderNo": route.getParams().orderNo
			}
		}).success(function(data) {
			$scope.isFlowBack = data.data;
			if($scope.isFlowBack != null) {
				$scope.isToBack = $scope.isFlowBack.isNewWalkProcess;
				if(typeof($scope.isToBack) == 'undefined') {
					$scope.isToBack = 1;
				}
			}
		})
	}
	loadFund();
	$scope.yunNanAccoumt = function(code, type) {
		if(code == 114) {
			$scope.ynProductCode = "I16800";
		} else if(code == 115) {
			$scope.ynProductCode = "I22500";
		}
		var param = {
			orderNo: $scope.orderNo,
			ynProductCode: $scope.ynProductCode
		}
		$http({
			url: '/credit/third/api/yntrust/v/queryTrustAccount',
			method: 'POST',
			data: param
		}).success(function(data) {
			$scope.ynAccoumt = new Object();
			if("SUCCESS" == data.code) {
				if(type == 1) {
					box.boxAlert("刷新成功");
				}
				if(code == 114) {
					$scope.ynAccoumt114 = data.data;
				} else if(code == 115) {
					$scope.ynAccoumt115 = data.data;
				}
			}
		});
	}
	
	$scope.sgtAccoumt = function(type) {
		$http({
			url: '/credit/third/api/sgtongBorrowerInformation/v/text',
			method: 'POST',
			data: {
				'brNo': '0004',
				'cardChno': 'CL0005',
				'acNo':'000003_03_00040004'
			}                                                              
		}).success(function(data) {
			$scope.sgtAccoumts = new Object();
			if("SUCCESS" == data.code) {
				if(type) {
				box.boxAlert("刷新成功");
				}
				$scope.sgtAccoumts = data.data;
			}
		});
	}
	

	orderIsBack = function() {
		var param = {
			orderNo: $scope.orderNo,
			processId: 'allocationFund'
		}
		$http({
			url: '/credit/risk/base/v/orderIsBack',
			data: param,
			method: 'POST'
		}).success(function(data) {
			if("SUCCESS" == data.code) {
				$scope.orderIsBack = data.data;
			}
		});
	}
	
	orderIsBack();
	var params = {
		"orderNo": route.getParams().orderNo
	}
	$http({
		method: 'POST',
		url: 'credit/order/borrow/v/query',
		data: params
	}).success(function(data) {
		$scope.borrow = data.data;
		if($scope.borrow != null) {
			$scope.loanAmont = $scope.borrow.loanAmount;
		}
	});
	$scope.checkFund = function() {
		$scope.isPush = false;
		var productCode = "";
		var orderLoanAmount = 0;
		if($scope.cdBorrow) {
			productCode = $scope.cdBorrow.productCode;
		}
		if($scope.cdBorrow) {
			orderLoanAmount = $scope.cdBorrow.loanAmount;
		} else {
			productCode = $scope.borrow.productCode;
			orderLoanAmount = $scope.borrow.loanAmount;
		}
		$scope.fund = new Array();
		var ind = 0;
		var flg = true;
		var isHuaan = false; //华安
		var isHuarong = false; //华融
		var loanamount = 0
		var kgFund = false;
		var checkFundCount = 0;
		angular.forEach($scope.dataList, function(data, index, array) {
			data.fundCodeShow = false;
			data.loanAmountShow = false;
			if(data.isCheck && data.loanAmount >= 0) {
				checkFundCount++;
				$scope.fund[ind] = {
					loanAmount: data.loanAmount,
					fundId: data.id,
					orderNo: $scope.orderNo,
					remark: $scope.remark,
					fundCode: data.fundCode,
					fundDesc: data.fundDesc
				}
				loanamount += Number(data.loanAmount);
				ind++;
				if(("105" == data.fundCode || 105 == data.fundCode) && (null != productCode && productCode != "03")) {
					isHuaan = true;
				}
				if(($scope.huarongCode + "" == data.fundCode || $scope.huarongCode == data.fundCode) && (null != productCode && productCode != "03")) {
					$scope.huarongLoanAmount = data.loanAmount;
					$scope.kgLoan.applyAmt = data.loanAmount;
					$scope.kgLoan.apprvAmt = data.loanAmount;
					$scope.appoint.applyAmt = data.loanAmount;
					$scope.appoint.apprvAmt = data.loanAmount;
					$scope.kgAppoint.loanAmount = data.loanAmount;
					isHuarong = true;
				}
				if(001 == data.fundCode) {
					kgFund = true;
				}

			} else if((undefined != data.isCheck && data.isCheck) && (undefined == data.loanAmount || data.loanAmount < 0)) {
				data.loanAmountShow = true;
				flg = false;
			} else if((undefined == data.isCheck || !data.isCheck) && (undefined != data.loanAmount && data.loanAmount > 0)) {
				data.fundCodeShow = true;
				flg = false;
			}
		});
		//		if(!kgFund&&checkFundCount>1){
		//			// box.boxAlert("只能单选一个资金方");
		//			// return;
		//		}else if(kgFund&&checkFundCount>2){
		//			box.boxAlert("选择001资金方之外只能同时选择一个资金方");
		//			return;
		//		}
		//		if(isHuaan&&isHuarong){
		//			box.boxAlert("105与"+$scope.huarongCode+"资金方只能选择其中一个");
		//			return;
		//		}
		if(!flg) {
			return;
		} else if(!$scope.fund || $scope.fund.length <= 0) {
			box.boxAlert("请选择资金方");
			return;
		} else if(loanamount != orderLoanAmount) {
			box.boxAlert("放款金额不能大于或者小于借款金额");
			return;
		}
		if(isHuarong && $scope.productCode != "04") {
			$scope.fundAduit()
		} else {
			$scope.financeShow();
		}
	}
	//提交给财务
	$scope.toFinance = function() {
		if($scope.isToBack == 1) {
			if(!$scope.sumbitDto.uid) {
				$scope.sumbitDto.uid = $scope.fund[0].financeUid;
			}
			if(!$scope.sumbitDto.uid || "" == $scope.sumbitDto.uid) {
				box.boxAlert("请选择财务");
				return;
			}
			$scope.fund[0].financeUid = $scope.sumbitDto.uid;
		}
		var param = angular.toJson($scope.fund);
		box.waitAlert();
		$http({
			url: '/credit/order/allocationFund/v/processSubmit',
			method: 'POST',
			data: param
		}).success(function(data) {
			box.closeWaitAlert();
			box.closeAlert();
			box.boxAlert(data.msg, function() {
				if(data.code == "SUCCESS") {
					box.closeAlert();
					$state.go("orderList");
				}
			});
		});
	}
	//选择财务
	$scope.financeShow = function() {
		$scope.huanShow = false;
		$scope.huarongShow = false;
		$scope.fundShow = true
		if($scope.isToBack == 1) {
			if($scope.productCode == "04") {
				$scope.personnelType = "收费";
			} else {
				if($scope.borrow.paymentMethod == 1 || $scope.productCode == "03" || $scope.productCode == "05") {
					$scope.personnelType = "收利息";
				} else {
					$scope.personnelType = "放款";
				}

			}
			box.editAlert($scope, "确定选择该资金吗，请选择财务。", "<submit-box></submit-box>", $scope.toFinance);
		} else {
			box.editAlert($scope, "提交", "确定提交分配信息吗？", $scope.toFinance);
		}
	}

	$scope.fundAduit = function() {
		$scope.tohuarongFinance = function() {
			if($scope.isToBack == 1) {
				if(!$scope.sumbitDto.uid) {
					$scope.sumbitDto.uid = $scope.fund[0].financeUid;
				}
				if(!$scope.sumbitDto.uid || "" == $scope.sumbitDto.uid) {
					box.boxAlert("请选择财务");
					return;
				}
				$scope.fund[0].financeUid = $scope.sumbitDto.uid;
			}
			var param = angular.toJson($scope.fund);
			box.waitAlert();
			$http({
				url: '/credit/risk/allocationfund/v/fundAduit',
				method: 'POST',
				data: param
			}).success(function(data) {
				box.closeWaitAlert();
				box.boxAlert(data.msg, function() {
					if(data.code == "SUCCESS") {
						box.closeAlert();
						$state.go("orderList");
					}
				});
			});
		}
		if($scope.isToBack == 1) {
			if($scope.productCode == "04") {
				$scope.personnelType = "收费";
			} else {
				if($scope.borrow.paymentMethod == 1 || $scope.productCode == "03" || $scope.productCode == "05") {
					$scope.personnelType = "收利息";
				} else {
					$scope.personnelType = "放款";
				}
			}
			box.editAlert($scope, "确定选择该资金吗，请选择财务。", "<submit-box></submit-box>", $scope.tohuarongFinance);
		} else {
			box.editAlert($scope, "提交", "确定提交分配信息吗？", $scope.tohuarongFinance);
		}

	}

	//退回
	$scope.showBack = function() {
		box.editAlert($scope, "订单退回，请选择退回对象。", "<back-box></back-box>", function() {
			$scope.backOrder();
		});
	}

	$(".tooltip-toggle").tooltip({
		html: true
	});
})