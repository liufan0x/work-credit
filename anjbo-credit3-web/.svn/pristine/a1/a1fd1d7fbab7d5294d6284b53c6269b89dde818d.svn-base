angular.module("anjboApp").controller("placeBorrowEditCtrl", function($scope, $timeout, $rootScope, $http, $state, route, box) {

	var params = {
		"orderNo": route.getParams().orderNo
	}
	$scope.cAgencyId = 0;
	var orderNo = route.getParams().orderNo;
	$scope.cityCode = route.getParams().cityCode;
	$scope.productCode = route.getParams().productCode;
	$scope.addCangDai = route.getParams().addCangDai;
	$scope.all = false;
	$rootScope.all = false;
	if($scope.addCangDai != 0 && $scope.addCangDai) {
		$("#borrowForm").find("input").prop("disabled", "disabled");
		$("#borrowForm").find("textarea").prop("disabled", "disabled");
		$("#borrowForm").find("select").prop("disabled", true);
		$scope.all = true;
		$rootScope.all = true;
	}

	//查询渠道经理集合
	$http({
		method: 'POST',
		url: '/credit/user/user/v/searchByType2',
		data: {
			"type": "role",
			"name": "渠道经理",
			"cityCode": $scope.cityCode
		}
	}).success(function(data) {
		$scope.listChannel = data.data;
		//借款信息
		$http({
			method: 'POST',
			url: 'credit/order/borrow/v/query',
			data: params
		}).success(function(data) {
			if(undefined == $scope.order || undefined == $scope.order.orderNo) {
				$scope.order = data.data;
			}
			$scope.order.customerType = String($scope.order.customerType > 0 ? $scope.order.customerType : 1);
			if($scope.order.rate == null || $scope.order.overdueRate == null || $scope.order.chargeMoney == null) {
				$scope.order.chargeMoney = data.data.chargeMoney;
				$scope.order.otherPoundage = data.data.otherPoundage;
				$scope.order.serviceCharge = data.data.serviceCharge;
				$scope.order.customsPoundage = data.data.customsPoundage;
				$scope.order.rate = data.data.rate;
				$scope.order.overdueRate = data.data.overdueRate;
			}
			/*if($scope.order.source && '快鸽APP'==$scope.order.source){
				$("#borrowForm").find(".channelManagerUid").prop("disabled", true); 
			}*/
			//合作机构
			$scope.cAgencyId = data.data.cooperativeAgencyId;
			if(data.data.cooperativeAgencyId) {
				$scope.order.cooperativeAgencyId = String(data.data.cooperativeAgencyId);
				$scope.cooperativeAgencyId(data.data.cooperativeAgencyId, undefined);
			}
			$scope.order.oldLoanBankNameId = String($scope.order.oldLoanBankNameId); //银行下拉框格式转换
			$scope.order.oldLoanBankSubNameId = String($scope.order.oldLoanBankSubNameId);
			$scope.order.loanBankNameId = String($scope.order.loanBankNameId);
			$scope.order.loanSubBankNameId = String($scope.order.loanSubBankNameId);
			$scope.order.rebateBankId = String($scope.order.rebateBankId);
			$scope.order.rebateBankSubId = String($scope.order.rebateBankSubId);
			$scope.order.paymentMethod = data.data.paymentMethod > 0 ? String(data.data.paymentMethod) : null; //费用支付方式及收费类型下拉框格式转换
			$scope.order.riskGradeId = String(data.data.riskGradeId);
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
			if($scope.order.isChangLoan == 1) {
				$("#borrowForm").find("input").prop("disabled", "disabled");
				$("#borrowForm").find("textarea").prop("disabled", "disabled");
				$("#borrowForm").find("select").prop("disabled", true);
				$scope.all = true;
			}

			$scope.obligeeA = [{
				id: "1",
				name: "是"
			}, {
				id: "2",
				name: "否"
			}];
			if(data.data.orderReceivableForDto && data.data.orderReceivableForDto.length > 0) {
				for(var i = 0; i < data.data.orderReceivableForDto.length; i++) {
					$scope.order.orderReceivableForDto[i].payMentAmountDate = data.data.orderReceivableForDto[i].payMentAmountDateStr;
				}
			}
			if($scope.order.orderReceivableForDto.length == 0) {
				$scope.order.orderReceivableForDto = [{
					"payMentAmount": $scope.order.loanAmount
				}];
			}
			$scope.order.financeOutLoanTime = data.data.financeOutLoanTimeStr;
			$scope.payMentAmountSum();

			//改变机构获取费用支付方式
			$timeout(function() {
				$scope.$watch("order.cooperativeAgencyId", function(newValue, oldValue) {
					console.info("获取风控等级集合入口: " + newValue + ",oldValue=" + oldValue);
					if(newValue) {
						$http({
							method: 'POST',
							url: '/credit/user/agencyIncomeMode/v/search',
							data: {
								agencyId: newValue
							}
						}).success(function(data) {
							$scope.listAgencyIncomeMode = data.data;
							if(null != $scope.order.paymentMethod && $scope.order.paymentMethod != 0 && $scope.order.paymentMethod != "0") {
								$scope.order.paymentMethod = String($scope.order.paymentMethod);
							} else {
								$scope.order.paymentMethod = null;
							}
						})
					}
					if(undefined == $scope.cAgencyId) {
						console.info("订单明细正在初始化，请稍后...");
					} else if(undefined != $scope.order && undefined != $scope.order.cooperativeAgencyId) {
						console.info("获取风控等级集合入口:" + $scope.order.cooperativeAgencyId);
						if(undefined != oldValue && newValue != oldValue) {
							$scope.order.riskGradeId = 0;
							$scope.isStandardCharge();
						}
						$scope.cooperativeAgencyId(newValue, oldValue);
					}
				});
			}, 1000);
		})
	});

	// 监控渠道经理，获取机构
	$scope.$watch("order.channelManagerUid", function(newValue, oldValue) {
		if(newValue) {
			$http({
				method: 'POST',
				data: {
					'chanlMan': newValue,
					"isRelation": 1
				},
				url: "/credit/user/agency/v/search2"
			}).success(function(data) {
				$scope.listAgency = data.data;
				if(undefined != oldValue) {
					$scope.order.cooperativeAgencyId = String($scope.listAgency.length > 0 ? $scope.listAgency[0].id : "");
				} else if(undefined == $scope.order.cooperativeAgencyId || $scope.order.cooperativeAgencyId < 1) {
					$scope.order.cooperativeAgencyId = String($scope.listAgency.length > 0 ? $scope.listAgency[0].id : "");
				}
			})
		}
	});

	$scope.cooperativeAgencyId = function(newValue, oldValue) {
		$scope.cityCode = route.getParams().cityCode;
		if($scope.order) {
			$scope.productCode = $scope.order.productCode;
		}
		//没有选择合作机构
		if(!newValue || !$scope.order.cooperativeAgencyId) {
			var riskList = [{
				"riskGradeId": 0,
				"name": "其他"
			}];
			$scope.riskGradeList = riskList;
			$scope.riskGradeListCD = riskList;
			angular.forEach($scope.riskGradeList, function(data) {
				data.riskGradeId = String(data.riskGradeId);
			});
			if($scope.order) {
				$scope.order.customsPoundage = 0;
				$scope.order.otherPoundage = 0;
				//其他费用
				if($scope.order.orderBaseBorrowRelationDto && $scope.order.orderBaseBorrowRelationDto.length > 0) {
					$scope.order.orderBaseBorrowRelationDto[0].otherPoundage = 0;
				}
			}
		}
		//选择了合作机构
		if(newValue && $scope.order.cooperativeAgencyId) {
			$scope.order.agencyTypeName = newValue.agencyTypeName;
			$scope.order.serviceCharge = undefined != newValue.chargeStandard ? newValue.chargeStandard : $scope.order.serviceCharge; //服务费 new
			console.debug("订单存有服务费:" + $scope.order.serviceCharge);
			//查询风控等级集合
			$http({
				method: 'POST',
				url: '/credit/user/agencyFeescale/v/search',
				data: {
					isRelation: 1,
					agencyTypeId: $scope.order.cooperativeAgencyId,
					productionid: $scope.cityCode + $scope.productCode
				}
			}).success(function(data) {
				$scope.riskGradeList = data.data;
				angular.forEach($scope.riskGradeList, function(data) {
					data.riskGradeId = String(data.riskGradeId);
				});
			});
		} else if(!newValue && oldValue) {
			$scope.chargeStandard = 0;
			$scope.serviceFees = 0;
			var chargeMoney = 0;
			$scope.order.chargeMoney = poundage(chargeMoney);
			$scope.riskGradeList = null;
			if($scope.order) {
				$scope.order.rate = 0;
				$scope.order.overdueRate = 0;
			}
			$scope.order.agencyTypeName = null;
		}
		var cAgencyId = $scope.cAgencyId + ""
		if(newValue != cAgencyId) {
			$scope.order.riskGradeId = null;
			$scope.order.chargeMoney = null;
			$scope.order.otherPoundage = null
			$scope.order.serviceCharge = null;
			$scope.order.customsPoundage = null;
			$scope.order.rate = null;
			$scope.order.overdueRate = null;
		}
		findFund();

	}

	//修改借款天数
	$scope.$watch("order.borrowingDays", function(newValue, oldValue) {
		if(newValue) {
			findFund();
		} else if(oldValue) {
			$scope.order.rate = 0;
			$scope.order.overdueRate = 0;
			$scope.order.chargeMoney = 0;
			var chargeMoney = 0;
			$scope.order.chargeMoney = poundage(chargeMoney);
			isMax($scope.order.chargeMoney);
		}
	});

	//不关联借款金额
	$scope.$watch("order.loanAmount", function(newValue, oldValue) {
		if(newValue) {
			findFund();
			if($scope.order.orderReceivableForDto.length < 1) {
				console.debug("无计划回款信息");
			} else {
				$scope.payMentAmountSum();
			}
		} else if(oldValue) {
			$scope.order.rate = 0;
			$scope.order.overdueRate = 0;
			$scope.order.chargeMoney = 0;
			var chargeMoney = 0;
			$scope.order.chargeMoney = poundage(chargeMoney);
			isMax($scope.order.chargeMoney);
			$scope.order.orderReceivableForDto[0].payMentAmount = 0;
			if($scope.order.isOnePay == 2) {
				$scope.order.orderReceivableForDto[1].payMentAmount = 0;
			}
		}
	});

	//改变是风控等级
	$scope.isStandardCharge = function() {
		if(!$scope.order) {
			return;
		}
		//2.0.4
		if($scope.order.riskGradeId != 0 && $scope.order.riskGradeId) {
			findFund(true);
		} else {
			$scope.order.rate = null;
			$scope.order.overdueRate = null;
			$scope.order.chargeMoney = null;
			$scope.order.otherPoundage = null;
			$scope.order.serviceCharge = null;
			$scope.order.customsPoundage = null;
		}
	}
	//获取费率
	function findFund(isFromRiskGrade) {
		if(!$scope.order || !$scope.order.cooperativeAgencyId || !$scope.order.riskGradeId) {
			return;
		}
		// 此处有删除费率计算代码，因代码基本全部等同于rateOnblur()
		$scope.rateOnblur(isFromRiskGrade);
	}

	//费率
	$scope.rateOnblur = function(isFromRiskGrade) {
		console.debug("费率计算rateOnblur(" + isFromRiskGrade + "):");
		var riskGradeId = null != $scope.order.riskGradeId && !isNaN($scope.order.riskGradeId) ? $scope.order.riskGradeId : 0; //风控等级ID
		var productId = $scope.productId; //业务产品
		var cooperativeAgencyId = $scope.order.cooperativeAgencyId; //机构类型
		var borrowingDays = $scope.order.borrowingDays;
		var loanAmount = $scope.order.loanAmount;
		if(null != productId && productId > 0 && null != cooperativeAgencyId && cooperativeAgencyId > 0 && borrowingDays > 0 && riskGradeId > 0 && loanAmount > 0) {
			$http({
				method: 'POST',
				data: {
					"cooperativeAgencyId": cooperativeAgencyId,
					'productId': productId,
					'borrowingDays': borrowingDays,
					"riskGradeId": riskGradeId,
					"loanAmount": loanAmount
				},
				url: "/credit/user/agencyFeescaleRiskcontrol/v/findStageRate"
			}).success(function(msg) {
				if('SUCCESS' == msg.code && null != msg.data) {
					$scope.order.rate = parseFloat(msg.data.rate);
					$scope.order.overdueRate = parseFloat(msg.data.overdueRate);
					$scope.chargeStandard = parseFloat(msg.data.chargeStandard);
					$scope.chargeMoneyBase = parseFloat(msg.data.chargeMoneyBase);
					if(undefined != isFromRiskGrade && isFromRiskGrade) {
						$scope.order.customsPoundage = parseFloat(msg.data.customsPoundage);
						$scope.order.otherPoundage = parseFloat(msg.data.otherPoundage);
						$scope.order.serviceCharge = parseFloat(msg.data.serviceFees);
						$scope.order.chargeMoney = parseFloat(msg.data.chargeMoneyTotalInit);
					}
					$scope.rateComputations();
				} else {
					$scope.modeid = "";
					$scope.order.rate = 0;
					$scope.order.overdueRate = 0;
					$scope.order.chargeMoney = "0";
					$scope.order.otherPoundage = 0;
					$scope.order.serviceCharge = 0;
					$scope.order.customsPoundage = 0;
				}
			})
		}

	}

	//计算费率 modeid 0:按天(费率*借款金额*借款期限),1:按阶段(费率*借款金额 )精确到2位 +(关外手续费+其他金额+固定费用2017.2.20)
	$scope.rateComputations = function() {
		var chargeMoneyTotal = poundage($scope.chargeMoneyBase);
		//机构最低收费标准
		if($scope.chargeStandard) {
			if(chargeMoneyTotal != Math.max($scope.chargeStandard, chargeMoneyTotal)) {
				$scope.order.chargeMoney = $scope.chargeStandard;
				return;
			}
		}
		$scope.order.chargeMoney = chargeMoneyTotal;
	}
	/**
	 * 监听关外手续费
	 */
	$scope.$watch("order.customsPoundage", function(newValue, oldValue) {
		if($scope.order && $scope.order.riskGradeId == 0) {
			return;
		}
		if(newValue) {
			$scope.rateOnblur();
		} else if(oldValue) {
			$scope.order.chargeMoney = sub($scope.order.chargeMoney, oldValue);
		}
	});
	/**
	 * 监听其他费用
	 */
	$scope.$watch("order.otherPoundage", function(newValue, oldValue) {
		if($scope.order && $scope.order.riskGradeId == 0) {
			return;
		}
		if(newValue) {
			$scope.rateOnblur();
		} else if(oldValue) {
			$scope.order.chargeMoney = sub($scope.order.chargeMoney, oldValue);
		}

	});
	/** 监听固定服务费 */
	$scope.$watch("order.serviceCharge", function(newValue, oldValue) {
		if($scope.order && $scope.order.riskGradeId == 0) {
			return;
		}
		if(newValue) {
			$scope.rateOnblur();
		} else if(oldValue) {
			$scope.order.chargeMoney = sub($scope.order.chargeMoney, oldValue);
		}
	});

	/* 合并追加费用(关外手续费+其他金额+固定费用)*/
	function poundage(chargeMoneyBase) {
		if($scope.order.customsPoundage) {
			chargeMoneyBase = add(chargeMoneyBase, $scope.order.customsPoundage);
		}
		if($scope.order.otherPoundage) {
			chargeMoneyBase = add(chargeMoneyBase, $scope.order.otherPoundage);
		}
		if($scope.order.serviceCharge) {
			chargeMoneyBase = add(chargeMoneyBase, $scope.order.serviceCharge);
		}
		return chargeMoneyBase;
	}
	/** 计算公式 */
	function add(a, b) {
		var c, d, e;
		try {
			c = a.toString().split(".")[1].length;
		} catch(f) {
			c = 0;
		}
		try {
			d = b.toString().split(".")[1].length;
		} catch(f) {
			d = 0;
		}
		return e = Math.pow(10, Math.max(c, d)), (a * e + b * e) / e;
	}

	function sub(a, b) {
		var c, d, e;
		try {
			c = a.toString().split(".")[1].length;
		} catch(f) {
			c = 0;
		}
		try {
			d = b.toString().split(".")[1].length;
		} catch(f) {
			d = 0;
		}
		return e = Math.pow(10, Math.max(c, d)), (a * e - b * e) / e;
	}

	function mul(a, b) {
		var c = 0,
			d = a.toString(),
			e = b.toString();
		try {
			c += d.split(".")[1].length;
		} catch(f) {}
		try {
			c += e.split(".")[1].length;
		} catch(f) {}
		return Number(d.replace(".", "")) * Number(e.replace(".", "")) / Math.pow(10, c);
	}

	function div(a, b) {
		var c, d, e = 0,
			f = 0;
		try {
			e = a.toString().split(".")[1].length;
		} catch(g) {}
		try {
			f = b.toString().split(".")[1].length;
		} catch(g) {}
		return c = Number(a.toString().replace(".", "")), d = Number(b.toString().replace(".", "")), c / d * Math.pow(10, f - e);
	}

	function isMax(m) {
		if($scope.chargeStandard) {
			if(m == Math.max($scope.chargeStandard, m)) {
				$scope.order.chargeMoney = m;
			} else {
				$scope.order.chargeMoney = $scope.chargeStandard;
			}
		} else {
			$scope.order.chargeMoney = m;
		}
	}

	//选择是否一次性回款，计算再次回款金额
	$scope.payMentAmountSum = function() {
		if($scope.order.isOnePay == "2") {
			var payMentAmount1 = (parseFloat($scope.order.loanAmount) - parseFloat($scope.order.orderReceivableForDto[0].payMentAmount));
			console.debug("再次回款金额" + payMentAmount1);
			$scope.order.orderReceivableForDto[1].payMentAmount = payMentAmount1;
		} else if($scope.order.isOnePay == "1") {
			$scope.order.orderReceivableForDto[0].payMentAmount = $scope.order.loanAmount
		}
	}
	//监听是否一次性回款
	$scope.$watch("order.isOnePay", function(newValue, oldValue) {
		if(!$scope.order) {
			return false;
		}
		if(!$scope.order.orderReceivableForDto || $scope.order.orderReceivableForDto.length == 0) {
			$scope.order.orderReceivableForDto = new Array();
			$scope.order.orderReceivableForDto.push(new Object());
		}
		if($scope.order.orderReceivableForDto.length == 0) {
			$scope.order.orderReceivableForDto = [{
				"payMentAmount": $scope.order.loanAmount
			}];
		}
		if(newValue == 2 && oldValue) {
			$scope.order.orderReceivableForDto.push(new Object());
			$scope.order.orderReceivableForDto[1].payMentAmount = $scope.order.loanAmount - $scope.order.orderReceivableForDto[0].payMentAmount;
		} else if(newValue == 1) {
			$scope.order.orderReceivableForDto.splice(1);
			if($scope.order.orderReceivableForDto.length > 0) {
				$scope.order.orderReceivableForDto[0].payMentAmount = $scope.order.loanAmount;
			}
		}
	});

	//编辑保存借款信息
	$scope.submitBorrow = function(isShow) {
		//$scope.order.isFinish = $scope.borrowForm.$valid?1:2;
		var scopeTemp = angular.element(".borrowfrom-supplement").scope();
		$scope.order.isFinish = scopeTemp.borrowForm.$valid ? 1 : 2;
		if($scope.order.orderBaseBorrowRelationDto && $scope.order.orderBaseBorrowRelationDto.length > 0) {
			for(var i = 0; i < $scope.order.orderBaseBorrowRelationDto.length; i++) {
				//				$scope.order.orderBaseBorrowRelationDto[i].isFinish=$scope.changLoanForm.$valid?1:2;
				//				$scope.order.orderBaseBorrowRelationDto[i].isFinish=scopeTemp.changLoanForm.$valid?1:2;
				$scope.order.orderBaseBorrowRelationDto[i].isFinish = 1;
			}
		}
		$scope.order.isChangLoan = ($scope.addCangDai != 0 && $scope.addCangDai) ? 1 : 2;
		$scope.order.isChangLoan = $scope.all ? 1 : 2;
		$http({
			method: 'POST',
			url: "/credit/order/borrow/v/update",
			data: $scope.order
		}).success(function(data) {
			//			$rootScope.isSave = true; 
			if(data.code == "SUCCESS" && !isShow) {
				box.boxAlert(data.msg);

			}
		})
	}

	$scope.$watch(function() {
		return $scope.borrowForm.$valid;
	}, function(newValue, oldValue) {
		$scope.$watch(function() {
			return $scope.order;
		}, function(newV, oldV) {
			if(newV) {
				$scope.order.isFinish = newValue ? 1 : 2;
			}
		});
	});

	$scope.$watch(function() {
		//		return 	$scope.changLoanForm.$valid;
	}, function(newValue, oldValue) {
		$scope.$watch(function() {
			return $scope.order;
		}, function(newV, oldV) {
			if(newV && newV.orderBaseBorrowRelationDto && newV.orderBaseBorrowRelationDto.length > 0) {
				for(var i = 0; i < newV.orderBaseBorrowRelationDto.length; i++) {
					newV.orderBaseBorrowRelationDto[i].isFinish = newValue ? 1 : 2;
				}
			}
		});
	});

});