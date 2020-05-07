angular.module("anjboApp").controller("placeBorrowEditCDCtrl", function($scope, $timeout, $rootScope, $http, $state, route, box, parent) {

	var params = {
		"orderNo": route.getParams().orderNo
	}
	var orderNo = route.getParams().orderNo;
	$scope.cityCode = route.getParams().cityCode;
	$scope.productCode = route.getParams().productCode;
	$scope.all = false;
	$rootScope.all = false;

	//查询渠道经理集合
	//查询渠道经理集合
	$http({
		method: 'POST',
		url: '/credit/user/user/v/searchByType2',
		data: {
			"type": "role",
			"name": "渠道经理"
		}
	}).success(function(data) {
		$scope.listChannel = data.data;
		// 借款信息
		$http({
			method: 'POST',
			url: 'credit/order/borrow/v/query',
			data: params
		}).success(function(data01) {
			if(undefined == $scope.order || undefined == $scope.order.orderNo) {
				$scope.order = data01.data;
			}
			if(undefined == $scope.inRelationOrderNo || null == $scope.inRelationOrderNo) {
				$scope.order.customerType = String($scope.order.customerType > 0 ? $scope.order.customerType : 1);
			}
			/*if($scope.order.source && '快鸽APP'==$scope.order.source){
				$("#borrowForm").find(".channelManagerUid").prop("disabled", true); 
			}*/
			//合作机构
			$scope.curAgencyId = data01.data.cooperativeAgencyId;
			if(data01.data.cooperativeAgencyId) {
				$scope.order.cooperativeAgencyId = String(data01.data.cooperativeAgencyId);
				$scope.cooperativeAgencyId(data01.data.cooperativeAgencyId, undefined);
			}
			$scope.order.borrowerName = data01.data.borrowerName;
			$scope.order.oldLoanBankNameId = String($scope.order.oldLoanBankNameId); //银行下拉框格式转换
			$scope.order.oldLoanBankSubNameId = String($scope.order.oldLoanBankSubNameId);
			$scope.order.loanBankNameId = String($scope.order.loanBankNameId);
			$scope.order.loanSubBankNameId = String($scope.order.loanSubBankNameId);
			$scope.order.rebateBankId = String($scope.order.rebateBankId);
			$scope.order.rebateBankSubId = String($scope.order.rebateBankSubId);
			$scope.order.riskGradeId = String(data01.data.riskGradeId); //收费类型下拉框格式转换
			$scope.order.financeOutLoanTime = data01.data.financeOutLoanTimeStr;

			//改变机构获取费用支付方式
			$timeout(function() {
				$scope.$watch("order.cooperativeAgencyId", function(newValue, oldValue) {
					console.info("获取风控等级集合入口: " + newValue + ",oldValue=" + oldValue);
					if(undefined == $scope.curAgencyId) {
						console.info("订单明细正在初始化，请稍后...");
					} else if(undefined != $scope.order && undefined != $scope.order.cooperativeAgencyId) {
						if(undefined != oldValue && newValue != oldValue) {
							$scope.order.riskGradeId = 0;
							$scope.isStandardCharge();
						}
						$scope.cooperativeAgencyId(newValue, oldValue);
					}
				});
			}, 1000);
		});
	})

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
			angular.forEach($scope.riskGradeListCD, function(data) {
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
		findFund();
	}

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
			} else if($scope.order.isOnePay == "2") {
				$scope.order.orderReceivableForDto[1].payMentAmount = $scope.order.loanAmount - $scope.order.orderReceivableForDto[0].payMentAmount;
			} else if($scope.order.isOnePay == "1") {
				$scope.order.orderReceivableForDto[0].payMentAmount = $scope.order.loanAmount;
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
		// 非其它，加载费率等信息
		if($scope.order.riskGradeId != 0 && $scope.order.riskGradeId) {
			findFund(true);
		} else {
			$scope.order.rate = null;
			$scope.order.overdueRate = null;
			$scope.order.chargeMoney = null;
			$scope.order.serviceCharge = null;
			$scope.order.customsPoundage = null;
			$scope.order.otherPoundage = null;
		}
	}
	//获取费率
	function findFund(isFromRiskGrade) {
		if(!$scope.order || !$scope.order.cooperativeAgencyId) {
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
				}
			})
		}
	}
	//计算费率
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

	/** 监听关外手续费 */
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
	/** 监听其他费用 */
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
		if($scope.order.isOnePay == "2" && $scope.order.orderReceivableForDto.length == 2) {
			$scope.order.orderReceivableForDto[1].payMentAmount = $scope.order.loanAmount - $scope.order.orderReceivableForDto[0].payMentAmount;
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
		var scopeTemp = angular.element(".borrowfrom-supplement").scope();
		$scope.order.isFinish = scopeTemp.borrowForm.$valid ? 1 : 2;
		if(null != $scope.inRelationOrderNo && scopeTemp.borrowForm.borrowingDays.$valid &&
			scopeTemp.borrowForm.loanAmount.$valid && scopeTemp.borrowForm.riskGradeId.$valid &&
			scopeTemp.borrowForm.rate.$valid && scopeTemp.borrowForm.overdueRate.$valid &&
			scopeTemp.borrowForm.otherPoundage.$valid && scopeTemp.borrowForm.chargeMoney.$valid) {
			$scope.order.isFinish = 1;
		}
		if($scope.order.orderBaseBorrowRelationDto && $scope.order.orderBaseBorrowRelationDto.length > 0) {
			for(var i = 0; i < $scope.order.orderBaseBorrowRelationDto.length; i++) {
				$scope.order.orderBaseBorrowRelationDto[i].isFinish = scopeTemp.changLoanForm.$valid ? 1 : 2;
			}
		}
		if(undefined != $scope.inRelationOrderNo && null != $scope.inRelationOrderNo && undefined != $scope.borrow && undefined != $scope.borrow.customerType && $scope.order.customerType < 1) {
			console.debug("重置[关联置换贷]畅贷客户类型：" + $scope.borrow.customerType);
			$scope.order.customerType = $scope.borrow.customerType;
		}
		box.waitAlert();
		$http({
			method: 'POST',
			url: "/credit/order/borrow/v/update",
			data: $scope.order
		}).success(function(data) {
			box.closeWaitAlert();
			if(data.code == "SUCCESS" && !isShow) {
				box.boxAlert(data.msg);
				if(null == $scope.inRelationOrderNo && undefined != $scope.customer) {
					$scope.customer.customerName = $scope.order.borrowerName;
				}
			}
		})
	}

	//切换tab时保存房产信息
	$rootScope.$watch("showView", function(newValue, oldValue) {
		if(oldValue == 1 && oldValue != newValue) {
			//$scope.submitBorrow(true);
		}
	});
});