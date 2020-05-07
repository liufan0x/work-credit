angular.module("anjboApp").controller("lendingInstructionsEditCtrl", function($scope, $http, $state, box, route) {
	$scope.productId = route.getParams().cityCode + '' + route.getParams().productCode; //业务产品
	$scope.order = new Object();
	$scope.order.orderNo = route.getParams().orderNo;
	$scope.serviceFees = 0;
	$scope.isChangLoan = 0;
	$http({
		method: 'POST',
		url: "/credit/finance/lendingInstruction/v/detail",
		data: $scope.order
	}).success(function(data) {
		$scope.order = data.data.instructionsDto;
		$scope.fundCode = data.data.fundCode;
		$scope.order.riskGradeId = $scope.order.riskGradeId + "";
		if($scope.order != null && $scope.order.lendingBankId == 0) {
			$scope.order.lendingBankId = "";
		} else {
			$scope.order.lendingBankId = $scope.order.lendingBankId + "";
		}
		$scope.order.openingBankId = $scope.order.openingBankId + "";
		$scope.borrowingDays = $scope.order.borrowingDays;
		$scope.loanAmount = $scope.order.loanAmount;
		$scope.riskGradeId = $scope.order.riskGradeId;
		$scope.chargeMoney = $scope.order.chargeMoney;
		$scope.isChangLoan = $scope.order.isChangLoan;
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
		//获取机构类型固定/关外/其他等费用
		$http({
			method: 'POST',
			url: "/credit/customer/risk/v/findAgencyPoundage",
			data: {
				"cooperativeAgencyId": $scope.order.cooperativeAgencyId,
				"productId": $scope.productId
			}
		}).success(function(data) {
			if("SUCCESS" == data.code) {
				if(data.data != null) {
					$scope.serviceFees = data.data.serviceFees; //固定服务费
					$scope.order.customsPoundage = data.data.poundageFees //关外手续费
					$scope.order.otherPoundage = data.data.otherFees; //其他费用
					$scope.chargeStandard = data.data.chargeStandard;
					console.log($scope.chargeStandard);
				}
			}
		});
	})

	$scope.submit = function() {
		if($scope.order.riskGradeId == '0') {
			if($scope.order.rate == null || $scope.order.rate == '') {
				box.boxAlert("费率不能为空");
				return false;
			}
			if($scope.order.overdueRate == null || $scope.order.overdueRate == '') {
				box.boxAlert("逾期费率不能为空");
				return false;
			}
			if($scope.order.chargeMoney == null || $scope.order.chargeMoney == '' || $scope.order.chargeMoney == '0') {
				box.boxAlert("收费金额不能为空并且大于0");
				return false;
			}

		}
		if($scope.order.lendingBankId == null || $scope.order.lendingBankId == '' || $scope.order.openingBankId == null || $scope.order.openingBankId == '') {
			box.boxAlert("放款银行或支行不能为空");
			return false;
		}
		if($scope.order.bankName == null || $scope.order.bankName == '') {
			box.boxAlert("银行卡户名不能为空");
			return false;
		}
		var bankAccount = $("#bankAccount").val();
		if(bankAccount == null || bankAccount == '') {
			box.boxAlert("银行卡账号不能为空");
			return false;
		}
		var img = $("#img").val();
		$scope.order.chargesReceivedImg = img;
		if($scope.order.chargesReceivedImg == null || $scope.order.chargesReceivedImg == '') {
			box.boxAlert("请上传已收取费用图片");
			return false;
		}
		var instructionSubmit = function() {
			$scope.order.createUid = $scope.sumbitDto.uid; //出纳
			if($scope.order.createUid == null || $scope.order.createUid == '' || $scope.order.createUid == 'undefined') {
				box.boxAlert("请选择放款专员");
				return false;
			}
			$(".lhw-alert-ok").attr("disabled", "disabled");
			$http({
				method: 'POST',
				url: "/credit/finance/lendingInstruction/v/add",
				data: $scope.order
			}).success(function(data) {
				box.closeAlert();
				box.boxAlert(data.msg, function() {
					if(data.code == "SUCCESS") {
						box.closeAlert();
						$state.go("orderList");
					}
				});
			})
		}
		$scope.personnelType = "放款";
		box.editAlert($scope, "确定提交发放款指令信息吗？请选择放款专员。", "<submit-box></submit-box>", instructionSubmit);
		//		box.editAlert($scope,"提交","确定提交发放款指令信息吗？",instructionSubmit);

	}

	//修改借款天数
	$scope.$watch("order.borrowingDays", function(newValue, oldValue) {
		if(newValue != 'undefined' && ($scope.borrowingDays != "" && newValue != $scope.borrowingDays)) {
			$scope.order.type = "1";
			findFund();
		} else {
			$scope.order.type = "0";
			$scope.order.chargeMoney = $scope.chargeMoney;
		}
	});

	//不关联借款金额
	$scope.$watch("order.loanAmount", function(newValue, oldValue) {
		if(newValue != 'undefined' && ($scope.loanAmount != "" && newValue != $scope.loanAmount)) {
			$scope.order.type = "1";
			findFund();
		} else {
			$scope.order.type = "0";
			$scope.order.chargeMoney = $scope.chargeMoney;
		}
	});
	//改变是风控等级
	$scope.$watch("order.riskGradeId", function(newValue, oldValue) {
		if(newValue != 'undefined' && ($scope.riskGradeId != "")) {
			$scope.order.type = "1";
			//			if($scope.order.riskGradeId != 0&&$scope.order.riskGradeId){
			findFund();
			//			}
		} else {
			$scope.order.type = "0";
			//			 $scope.order.chargeMoney=$scope.order;
		}
	});

	//获取费率
	function findFund() {
		if(!$scope.order || !$scope.order.cooperativeAgencyId) {
			return;
		}
		var riskGradeId = $scope.order.riskGradeId; //风控等级ID
		var loanAmount = $scope.order.loanAmount; //赎楼金额
		var productId = route.getParams().cityCode + '' + route.getParams().productCode; //业务产品
		var cooperativeAgencyId = $scope.order.cooperativeAgencyId; //合作机构
		var borrowingDays = $scope.order.borrowingDays;
		console.log(riskGradeId + ',' + loanAmount + ',' + productId + ',' + cooperativeAgencyId + ',' + borrowingDays);
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
			}).success(function(msg) { //{overduerate=2.36, rate=2.6, modeid=0}
				if('SUCCESS' == msg.code) {
					var obj = msg.data;
					if(null != obj) {
						console.log(obj);
						$scope.baseCharge = obj.modeid;
						$scope.order.rate = obj.rate;
						$scope.order.overdueRate = parseFloat(obj.overdueRate);
						$scope.rateOnblur();
					} else {
						$scope.order.rate = 0;
						$scope.order.overdueRate = 0;
						var chargeMoney = 0;
						$scope.order.chargeMoney = poundage(chargeMoney);
						isMax($scope.order.chargeMoney);
						$scope.modeid = "";
					}
				}
			})
		} else {
			if($scope.order && $scope.order.riskGradeId > 0) {
				var chargeMoney = 0;
				$scope.order.chargeMoney = poundage(chargeMoney);
				isMax($scope.order.chargeMoney);
			}

		}
	}

	//费率
	$scope.rateOnblur = function() {
		if(!$scope.modeid) {
			var riskGradeId = $scope.order.riskGradeId; //风控等级ID
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
					if('SUCCESS' == msg.code) {
						var obj = msg.data;
						if(null != obj) {
							$scope.modeid = obj.modeid;
						}
					} else {
						$scope.order.rate = 0;
						$scope.order.overdueRate = 0;
						$scope.order.chargeMoney = "0";
						$scope.modeid = "";
					}
					if($scope.order.rate > 0) {
						$scope.rateComputations();
					} else {
						var chargeMoney = 0;
						$scope.order.chargeMoney = poundage(chargeMoney);
						isMax($scope.order.chargeMoney);
					}
				})
			} else {
				var chargeMoney = 0;
				$scope.order.chargeMoney = poundage(chargeMoney);
				isMax($scope.order.chargeMoney);
			}
		} else {
			if($scope.order.rate > 0) {
				$scope.rateComputations();
			} else {
				var chargeMoney = 0;
				$scope.order.chargeMoney = poundage(chargeMoney);
				isMax($scope.order.chargeMoney);
			}
		}

	}

	//计算费率 baseCharge 0:按天(费率*借款金额*借款期限),1:按阶段(费率*借款金额 )精确到2位 +(关外手续费+其他金额+固定费用2017.2.20)
	$scope.rateComputations = function() {
		var modeid = $scope.modeid;
		var rate = $scope.order.rate;
		var loanAmount = $scope.order.loanAmount;
		var borrowingDays = $scope.order.borrowingDays;
		if(modeid == 1) {
			if(loanAmount > 0) {
				var m = parseFloat(loanAmount * 10000 * rate / 100).toFixed(2);
				m = poundage(m);
				//机构最低收费标准
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
		} else if(modeid == 0) {
			if(loanAmount > 0 && borrowingDays > 0) {
				var m = parseFloat(loanAmount * 10000 * rate * borrowingDays / 100).toFixed(2);
				m = poundage(m);
				if($scope.chargeStandard) {
					if(m == Math.max($scope.chargeStandard, m)) {
						$scope.order.chargeMoney = m;
					} else {
						$scope.order.chargeMoney = $scope.chargeStandard;
					}
				} else {
					$scope.order.chargeMoney = m;
				}
			} else {
				$scope.order.chargeMoney = 0;
			}
		}
	}
	//计算固定服务费+关外手续费+其他费用
	function poundage(m) {
		if($scope.isChangLoan != 1 && $scope.order.customsPoundage) { //畅贷不包含关外手续费
			m = add(m, $scope.order.customsPoundage);
		}
		if($scope.order.otherPoundage) {
			m = add(m, $scope.order.otherPoundage);
		}
		if($scope.serviceFees) {
			m = add(m, $scope.serviceFees);
		}
		return m;
	}

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
	//最低收费标准
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

});