angular.module("anjboApp").controller("lendingEditCtrl", function($scope, $http, $state, $timeout, $filter, box, route) {

	$scope.obj = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;
	$scope.relationOrderNo = route.getParams().relationOrderNo;
	$scope.fund = new Object();
	$scope.fund.orderNo = route.getParams().orderNo;
	$scope.isProductCode = route.getParams().productCode;
	$scope.isNext = 1; //前置
	$scope.isAgain = true; //是否查询
	$scope.isSubmit = true; //是否提交
	$scope.isBack = false; //是否退回
	$scope.isPush = true; //是否推送
	$scope.isHuaRongShow = false; //是否华融
	$scope.isYunNanShow = false; //是否云南
	$scope.audit = new Object();
	$scope.rongAnhkShow = false;
	$scope.yunNanShow = false; //是否云南
	$http({
		method: 'POST',
		url: "/credit/finance/lending/v/detail",
		data: $scope.obj
	}).success(function(data) {
		$scope.obj = data.data;
		$scope.isNext = $scope.obj.isPaymentMethod;
		//$scope.obj.lendingTime=$scope.obj.lendingTimeStr;
		$scope.obj.lendingTime = "";
		if($scope.isProductCode!="04"){
//			$scope.fundInit();
		}
	})

	$scope.fundInit = function() {
		$http({
			method: 'POST',
			url: "/credit/risk/allocationfundaduit/v/init",
			data: $scope.fund
		}).success(function(data) {
			$scope.isHuaRongShow = data.data.isHuaRongShow;
			$scope.isYunNanShow = data.data.isYunNanShow;
			if($scope.isHuaRongShow) {
				$scope.fund = data.data.fundCompleteDto;
			} else if($scope.isYunNanShow) {
				$scope.queryTrustAccount();
				$scope.yunnanStatus(1);
			}
			if($scope.isHuaRongShow || $scope.isYunNanShow) {
				$scope.isSubmit = false;
			}
		})
	}

	//查询还款计划
	$scope.fundsubmit = function() {
		if($scope.isHuaRongShow) {
			$scope.rongAnhkShow = true;
			if($scope.isAgain) {
				$http({
					method: 'POST',
					url: "/credit/risk/allocationfundaduit/v/selectFund",
					data: $scope.fund
				}).success(function(data) {
					if(data.code == "SUCCESS") {
						$scope.isAgain = false;
						$scope.audit.repaymentTime = data.data.lendingTime; //应还款时间
						var borrowingDays = data.data.borrowingDays;
						var loanAmount = (data.data.loanAmount * 10000);
						$scope.audit.psIntRate = data.data.dayRate; //执行利率
						if(!isNaN(loanAmount)) {
							//						var interest=(loanAmount*borrowingDays*data.data.dayRate);//应还利息（本金*借款期限*利率 ） 
							var interest = (loanAmount * borrowingDays * 0.11 / 360);
							interest = floadNum(interest) * 1;
							//						interest = div(interest,100);
							var repaymentAccount = (loanAmount + interest) * 1
							$scope.audit.repaymentAccount = repaymentAccount; //期供金额（本金+利息）
							$scope.audit.interest = interest; //应还利息（本金*借款期限*利率 ） 
							$scope.audit.capital = loanAmount; // 应还本金
							$scope.audit.psRemPrcp = loanAmount; //剩余本金
						}
					}
				})
			}
		} else {
			$scope.yunnanInit();
			$scope.yunNanShow = true;
		}
	}

	//推送资方
	$scope.auditSubmit = function(type) {
		$scope.isSubmit = true;
		//华融
		if(type == 1) {
			$scope.audit.order = "1";
			$scope.audit.sysbAmt = 0;
			$scope.audit.psFeeAmt = 0;
			$scope.audit.orderNo = $scope.obj.orderNo;
			var paramt = {
				"orderNo": $scope.fund.orderNo,
				"auditDto": $scope.audit,
				"type": type
			}
			$http({
				method: 'POST',
				data: paramt,
				url: "/credit/finance/lending/v/toAddHarong"
			}).success(function(data) {
				box.closeAlert();
				alert(data.msg);
				$scope.rongAnhkShow = false;
				$scope.isYunNanShow = false;
				if(data.code == "SUCCESS") {
					$scope.isSubmit = true;
					$scope.isPush = false;
				} else {
					if(data.msg == "推送融安信息失败！") {
						$scope.isBack = true;
						//						 $scope.isPush=true;
						//						 $scope.isSubmit =false;
					}
				}
			});
			//云南信托
		} else {
			$scope.isSubmit = false;
			$scope.rongAnhkShow = false;
			$scope.yunNanShow = false;
			$scope.isPush = true;
			$scope.isPush = false;
		}
	}

	$scope.submit = function() {

		// if(!$scope.isSubmit) {
		// 	alert("请先推送应还款计划");
		// 	return false;
		// }

		if($scope.obj.lendingTime == null || 　$scope.obj.lendingTime　 == '') {
			box.boxAlert("放款时间不能为空");
			return false;
		}
		var lendingSubmit = function() {
			$scope.obj.receivableForUid = $scope.sumbitDto.uid; //出纳
			if($scope.obj.receivableForUid == null || $scope.obj.receivableForUid == '' || $scope.obj.receivableForUid == 'undefined') {
				box.boxAlert("请选择处理专员");
				return false;
			}
			var img = $("#img").val();
			$scope.obj.lendingImg = img;
			$(".lhw-alert-ok").attr("disabled", "disabled");
			$scope.obj.relationOrderNo = route.getParams().relationOrderNo;
			box.waitAlert();
			$http({
				method: 'POST',
				url: "/credit/finance/lending/v/add",
				data: $scope.obj
			}).success(function(data) {
				box.closeWaitAlert();
				box.closeAlert();
				box.boxAlert(data.msg, function() {
					if(data.code == "SUCCESS") {
						box.closeAlert();
						$state.go("orderList");
					}
				});
			})
		}
		
		box.boxAlert2("提交放款信息后<span style=\"color:red;\">不能执行撤回操作</span></br>请确认您的放款时间是否输入有误！", function() {
			if($scope.isNext == 1 || $scope.isProductCode == "03"||$scope.isProductCode == "06"||$scope.isProductCode == "07") {
				$scope.personnelType = "回款";
				box.editAlert($scope, "确定提交放款信息吗？请选择回款专员。", "<submit-box></submit-box>", lendingSubmit);
			} else if($scope.isProductCode == "04"){ //房抵贷
				var img = $("#img").val();
				$scope.obj.lendingImg = img;
				$(".lhw-alert-ok").attr("disabled", "disabled");
				$scope.obj.relationOrderNo = route.getParams().relationOrderNo;
				box.waitAlert();
				$http({
					method: 'POST',
					url: "/credit/finance/lending/v/add",
					data: $scope.obj
				}).success(function(data) {
					box.closeWaitAlert();
					box.closeAlert();
					box.boxAlert(data.msg, function() {
						if(data.code == "SUCCESS") {
							box.closeAlert();
							$state.go("orderList");
						}
					});
				})
			}else {
				$scope.personnelType = "扣回后置费用";
				box.editAlert($scope, "确定提交放款信息吗？请选择扣回后置费用专员。", "<submit-box></submit-box>", lendingSubmit);
			}
		});
	}

	$scope.showBack = function() {
		box.editAlert($scope, "订单退回，请选择退回对象。", "<back-box></back-box>", function() {
			$scope.backOrder();
		});
	}

	function floadNum(a) {
		var num = a + "";
		var index = num.indexOf('.');
		if(index == -1) {
			return num;
		} else {
			var f = parseFloat(num).toFixed(2);
			return f;
		}
	}

	/**************云南信托start*******************/

	$scope.yunnanInit = function() {
		$scope.yunnanAudit = new Object();
		$http({
			method: 'POST',
			url: "/credit/third/api/yntrust/v/selectRepaymentPlan",
			data: {
				"orderNo": $scope.obj.orderNo
			}
		}).success(function(data) {
			if(data.data) {
				$scope.isSubmit = true;
				$scope.yunnanAudit = data.data;
				$scope.yunnanAudit.repayDate = $filter('date')($scope.yunnanAudit.repayDate, 'yyyy-MM-dd HH:mm');
			} else {
				$http({
					method: 'POST',
					url: "/credit/third/api/yntrust/v/selectBorrowContractImg",
					data: {
						"orderNo": $scope.obj.orderNo
					}
				}).success(function(data1) {
					var tempDate = new Date($scope.yunnanStatusDto.actExcutedTime);
					$scope.yunnanAudit.repayDate = $filter('date')(tempDate.setDate(tempDate.getDate() + data1.data.contract.borrowingDays), 'yyyy-MM-dd HH:mm');
					$scope.yunnanAudit.borrowingDays = data1.data.contract.borrowingDays;
					$scope.yunnanAudit.repayPrincipal = data1.data.contract.amount;
					$scope.yunnanAudit.repayProfit = accMul(accMul($scope.yunnanAudit.borrowingDays, $scope.yunnanAudit.repayPrincipal), data1.data.contract.signDayRate);
				})
				$scope.isSubmit = false;
			}
		})
	}

	function accMul(arg1, arg2) {
		var m = 0,
			s1 = arg1.toString(),
			s2 = arg2.toString();
		try {
			m += s1.split(".")[1].length
		} catch(e) {}
		try {
			m += s2.split(".")[1].length
		} catch(e) {}
		return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
	}

	$scope.yunnanStatus = function(type) {
//		$http({
//			method: 'POST',
//			url: "/credit/third/api/yntrust/v/queryTradingStatus",
//			data: {
//				"orderNo": $scope.obj.orderNo
//			}
//		}).success(function(data) {
//			if(type != 1) {
//				box.boxAlert(data.msg, function() {
//					if(data.code == 'SUCCESS') {
//						$scope.yunnanStatusDto = data.data[data.data.length-1];
//						if($scope.yunnanStatusDto.processStatus == 1) {
//							$scope.yunnanInit();
//							$scope.obj.lendingTime = $filter('date')($scope.yunnanStatusDto.actExcutedTime, 'yyyy-MM-dd');
//						}
//					}
//				});
//			} else {
//				if(data.code == 'SUCCESS') {
//					$scope.yunnanStatusDto = data.data[data.data.length-1];
//					if($scope.yunnanStatusDto.processStatus == 1) {
//						$scope.yunnanInit();
//						$scope.obj.lendingTime = $filter('date')($scope.yunnanStatusDto.actExcutedTime, 'yyyy-MM-dd');
//						$scope.isSubmit = true;
//					}else{
//						$scope.isSubmit = false;
//					}
//				}
//			}
//
//		})
	}
//	$scope.yunnanStatus(1);
	$scope.queryTrustAccount = function() {
		$http({
			method: 'POST',
			url: "/credit/third/api/yntrust/v/queryTrustAccount",
			data: {
				"orderNo": $scope.obj.orderNo
			}
		}).success(function(data) {
			$scope.balance = data.data.Balance;
		})
	}

	$scope.yunnanAuditSubmit = function() {
		$scope.yunnanAudit.orderNo = $scope.obj.orderNo;
		$scope.isSubmit = true;
		var repayDate = $scope.yunnanAudit.repayDate;
		if($scope.yunnanAudit.repayDate) {
			var date = $filter('date')($scope.yunnanAudit.repayDate, 'yyyy-MM-dd HH:mm:ss');
			var fmdate = new Date(date);
			$scope.yunnanAudit.repayDate = fmdate;
		}
		$http({
			method: 'POST',
			url: "/credit/third/api/yntrust/v/addRepaymentPlan",
			data: $scope.yunnanAudit
		}).success(function(data) {
			$scope.yunnanAudit.repayDate = repayDate;
			box.closeAlert();
			if(data.msg.indexOf("对应的还款计划已存在") > 0) {
				box.boxAlert("已推送对应还款计划", function() {
					$timeout(function() {
						$scope.yunNanShow = false;
					});
					box.closeAlert();
				});
			} else {
				box.boxAlert(data.msg, function() {
					if(data.code == "SUCCESS") {
						$timeout(function() {
							$scope.yunNanShow = false;
						});
						box.closeAlert();
					} else {
						$scope.isSubmit = false;
					}
				});
			}
		})
	}

	/**************云南信托end*******************/

});