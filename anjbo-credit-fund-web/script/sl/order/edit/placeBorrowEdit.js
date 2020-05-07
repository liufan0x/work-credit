angular.module("anjboApp").controller("placeBorrowEditCtrl", function($scope, $timeout,$rootScope,$http, $state, route, box) {

	var params = {
		"orderNo": route.getParams().orderNo
	}

	var orderNo = route.getParams().orderNo;
	$scope.cityCode = route.getParams().cityCode;
	$scope.productCode = route.getParams().productCode;
	$scope.addCangDai = route.getParams().addCangDai;
	$scope.all = false;
	$rootScope.all = false;
	if($scope.addCangDai != 0&&$scope.addCangDai) {
		$("#borrowForm").find("input").prop("disabled","disabled");
		$("#borrowForm").find("textarea").prop("disabled","disabled");
		$("#borrowForm").find("select").prop("disabled", true);
		$scope.all=true;
		$rootScope.all = true;
	}
	
	
	
	//借款信息
	$http({
		method: 'POST',
		url: 'credit/order/borrow/v/query',
		data: params
	}).success(function(data) {
		$scope.data=data.data;
		//查询订单列表
		$http({
			method: 'POST',
			data: params,
			url: '/credit/order/base/v/selectDetailByOrderNo'
		}).success(function(data) {
			$scope.orderList = data.data;
			if($scope.orderList.source&&$scope.orderList.source == '快鸽APP'){
				$("#borrowForm").find(".channelManagerUid").prop("disabled", true); 
			}
		})
		if($scope.data.isChangLoan==1){
			$("#borrowForm").find("input").prop("disabled","disabled");
			$("#borrowForm").find("textarea").prop("disabled","disabled");
			$("#borrowForm").find("select").prop("disabled", true); 
			$scope.all=true;
		}
		if($scope.data.isChangLoan!=1&&$scope.orderList.relationOrderNo){
			$("#changLoanForm").find("input").prop("disabled","disabled");
			$("#changLoanForm").find("textarea").prop("disabled","disabled");
			$("#changLoanForm").find("select").prop("disabled", true); 
		}
	})
	
	//查询订单列表
	$http({
		method: 'POST',
		data: params,
		url: '/credit/order/base/v/selectDetailByOrderNo'
	}).success(function(data) {
		$scope.orderList = data.data;
		if($scope.orderList.source&&$scope.orderList.source == '快鸽APP'){
			$("#borrowForm").find(".channelManagerUid").prop("disabled", true); 
		}
	})


	//查询渠道经理集合
	$http({
		method: 'POST',
		url: '/credit/customer/chanlman/v/findChannelManager'
	}).success(function(data) {
		$scope.listChannel = data.data;
	})

	//改变渠道经理获取机构
	$scope.$watch("order.channelManagerUid", function(newValue, oldValue) {
		if(newValue) {
			$http({
				method: 'POST',
				data: {
					'chanlMan': newValue
				},
				url: "/credit/customer/chanlman/v/findAgencyBychannelManager"
			}).success(function(data) {
				$scope.listAgency = data.data;
				var fl = true;
				angular.forEach($scope.listAgency, function(data) {
					if(data.id == $scope.order.cooperativeAgencyId) {
						fl = false;
					}
				});
				if(fl) {
					$scope.order.cooperativeAgencyId = undefined;
				}
			})
		}
	});

	//根据机构，业务类型，获取风控等级集合
	$scope.$watch("order.cooperativeAgencyId", function(newValue, oldValue) {
		$scope.cooperativeAgencyId(newValue, oldValue);
	});
	
	$scope.cooperativeAgencyId = function(newValue,oldValue){

		$scope.cityCode = route.getParams().cityCode;
		if($scope.order){
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
			if($scope.order){
				$scope.order.customsPoundage = 0;
				$scope.order.otherPoundage = 0;
				//其他费用
				if($scope.order.orderBaseBorrowRelationDto.length > 0) {
					$scope.order.orderBaseBorrowRelationDto[0].otherPoundage = 0;
				}
			}
		}
		//选择了合作机构
		if(newValue && $scope.order.cooperativeAgencyId) {
			$scope.order.agencyTypeName = newValue.agencyTypeName;
			$scope.chargeStandard = newValue.chargeStandard;

			//查询风控等级集合
			$http({
				method: 'POST',
				url: '/credit/customer/risk/v/findRiskGradeList',
				data: {
					"cooperativeAgencyId": $scope.order.cooperativeAgencyId,
					'productId': $scope.cityCode + $scope.productCode
				}
			}).success(function(data) {
				$scope.riskGradeList = data.data;
			});

			//查询畅贷风控等级集合
			$http({
				method: 'POST',
				url: '/credit/customer/risk/v/findRiskGradeList',
				data: {
					"cooperativeAgencyId": $scope.order.cooperativeAgencyId,
					'productId': $scope.cityCode + "03"
				}
			}).success(function(data) {
				$scope.riskGradeListCD = data.data;
			});

			//获取机构类型固定/关外/其他等费用
			$http({
				method: 'POST',
				url: "/credit/customer/risk/v/findAgencyPoundage",
				data: {
					"cooperativeAgencyId": $scope.order.cooperativeAgencyId,
					"productId": $scope.cityCode + $scope.productCode
				}
			}).success(function(data) {
				if("SUCCESS" == data.code) {
					if(data.data != null) {
						$scope.serviceFees = data.data.serviceFees;
						$scope.chargeStandard = data.data.chargeStandard;
						if(oldValue){
							if(!$scope.order.customsPoundage){
								$scope.order.customsPoundage = data.data.poundageFees;
							}
							if(!$scope.order.otherPoundage){
								$scope.order.otherPoundage = data.data.otherFees;
							}
						}
					}
					$scope.rateOnblur();
				}
			});

			//畅贷获取机构类型固定/关外/其他等费用
			$http({
				method: 'POST',
				url: "/credit/customer/risk/v/findAgencyPoundage",
				data: {
					"cooperativeAgencyId": $scope.order.cooperativeAgencyId,
					"productId": $scope.cityCode + "03"
				}
			}).success(function(data) {
				if("SUCCESS" == data.code) {
					if(data.data != null) {
						$scope.serviceFeesCD = data.data.serviceFees;
						$scope.customsPoundageCD = data.data.poundageFees;
						$scope.otherPoundageCD = data.data.otherFees;
						$scope.chargeStandardCD = data.data.chargeStandard;
						//畅贷
						//其他费用
						if(($scope.order.orderBaseBorrowRelationDto.length > 0)&&(oldValue||!$scope.order.orderBaseBorrowRelationDto[0].otherPoundage)) {
							$scope.order.orderBaseBorrowRelationDto[0].otherPoundage = data.data.otherFees;
						}
					}
					getRateCD();
				}
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

			if($scope.order.isOnePay == "2") {
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
		//2.0.4
		if($scope.order.riskGradeId != 0 && $scope.order.riskGradeId) {
			findFund();
		} else {
			$scope.order.rate = null;
			$scope.order.overdueRate = null;
			$scope.order.chargeMoney = null;
		}
	}
	//获取费率
	function findFund() {
		if(!$scope.order || !$scope.order.cooperativeAgencyId) {
			return;
		}
		var riskGradeId = $scope.order.riskGradeId; //风控等级ID
		var loanAmount = $scope.order.loanAmount; //赎楼金额
		var productId = route.getParams().cityCode + '' + $scope.order.productCode; //业务产品
		var cooperativeAgencyId = $scope.order.cooperativeAgencyId; //合作机构
		var borrowingDays = $scope.order.borrowingDays;
		var standardCharge = $scope.order.standardCharge;
//		console.log(riskGradeId + ',' + loanAmount + ',' + productId + ',' + cooperativeAgencyId + ',' + borrowingDays + ',' + standardCharge);
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
				url: "/credit/customer/risk/v/findStageRate"
			}).success(function(msg) { //{overduerate=2.36, rate=2.6, modeid=0}
				if('SUCCESS' == msg.code) {
					var obj = msg.data;
					if(null != obj) {
//						console.log(obj);
						$scope.modeid = obj.modeid;
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
					url: "/credit/customer/risk/v/findStageRate"
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
						//$scope.order.chargeMoney = "0";
					}
				})
			} else {
				var chargeMoney = 0;
				if(!$scope.order.chargeMoney){
					$scope.order.chargeMoney = poundage(chargeMoney);
					isMax($scope.order.chargeMoney);
				}
			}
		} else {
			if($scope.order.rate > 0) {
				$scope.rateComputations();
			} else {
				var chargeMoney = 0;
				$scope.order.chargeMoney = poundage(chargeMoney);
				isMax($scope.order.chargeMoney);
				//$scope.order.chargeMoney = "0";
			}
		}

	}

	//计算费率 modeid 0:按天(费率*借款金额*借款期限),1:按阶段(费率*借款金额 )精确到2位 +(关外手续费+其他金额+固定费用2017.2.20)
	$scope.rateComputations = function() {
		var modeid = $scope.modeid;
		var rate = $scope.order.rate;
		var loanAmount = $scope.order.loanAmount;
		var borrowingDays = $scope.order.borrowingDays;
		if(modeid == 0 && loanAmount > 0 && borrowingDays > 0) {
			var m = parseFloat(loanAmount * 10000 * rate * borrowingDays / 100).toFixed(2);
			m = poundage(m);
			if($scope.chargeStandard) { //机构最低收费标准
				if(m == Math.max($scope.chargeStandard, m)) {
					$scope.order.chargeMoney = m;
				} else {
					$scope.order.chargeMoney = $scope.chargeStandard;
				}
			} else {
				$scope.order.chargeMoney = m;
			}
		} else if(modeid == 1 && loanAmount > 0) {
			var m = parseFloat(loanAmount * 10000 * rate / 100).toFixed(2);
			m = poundage(m);
			if($scope.chargeStandard) { //机构最低收费标准
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

	function poundage(m) {
		/**2017.2.20start**/
		if($scope.order.customsPoundage) {
			m = add(m, $scope.order.customsPoundage);
		}
		if($scope.order.otherPoundage) {
			m = add(m, $scope.order.otherPoundage);
		}
		if($scope.serviceFees) {
			m = add(m, $scope.serviceFees);
		}
		/**2017.2.20end**/
		return m;
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

	$scope.addCD = function() {
		if($scope.order.orderBaseBorrowRelationDto.length == 0) {
			$scope.order.orderBaseBorrowRelationDto = new Array();
			$scope.order.orderBaseBorrowRelationDto.push(new Object());
			$scope.order.orderBaseBorrowRelationDto[0].productCode = '03';
			$scope.cooperativeAgencyId($scope.order.cooperativeAgencyId,'');
		}
	}

	$scope.deleteCangDai = function(index) {
		 box.confirmAlert("删除关联订单","确定删除关联订单吗？",function(){
				$http({
		            method: 'POST',
		            url:'/credit/order/relation/v/deletecd',
		            data:$scope.order
		        }).success(function(data){
		        	if(data.code == "SUCCESS"){
		        		alert('删除成功');
					}
		        })
				$timeout(function(){
					$scope.order.orderBaseBorrowRelationDto.splice(index, 1);
				});
			});
	}

	/***
	 * 畅贷风控等级费率计算
	 */
	//改变是风控等级
	$scope.changeRiskGradeCD = function() {
		if($scope.order.orderBaseBorrowRelationDto[0].riskGradeId != 0 && $scope.order.orderBaseBorrowRelationDto[0].riskGradeId) {
			getRateCD();
		} else {
			$scope.order.orderBaseBorrowRelationDto[0].rate = null;
			$scope.order.orderBaseBorrowRelationDto[0].overdueRate = null;
			$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = null;
		}
	}

	$scope.$watch("order.orderBaseBorrowRelationDto[0].borrowingDays", function(newValue, oldValue) {
		if(newValue) {
			getRateCD();
		} else if(oldValue&&$scope.order.orderBaseBorrowRelationDto[0]) {
			$scope.order.orderBaseBorrowRelationDto[0].rate = 0;
			$scope.order.orderBaseBorrowRelationDto[0].overdueRate = 0;
			$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = 0;
			var chargeMoney = 0;
			$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = poundage(chargeMoney);
			isMax($scope.order.orderBaseBorrowRelationDto[0].chargeMoney);
		}
	});
	//不关联借款金额
	$scope.$watch("order.orderBaseBorrowRelationDto[0].loanAmount", function(newValue, oldValue) {
		if(newValue) {
			getRateCD();
		} else if(oldValue&&$scope.order.orderBaseBorrowRelationDto[0]) {
			$scope.order.orderBaseBorrowRelationDto[0].rate = 0;
			$scope.order.orderBaseBorrowRelationDto[0].overdueRate = 0;
			$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = 0;
			var chargeMoney = 0;
			$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = poundage(chargeMoney);
			isMax($scope.order.orderBaseBorrowRelationDto[0].chargeMoney);
		}
	});

	/**
	 * 监听关外手续费
	 */
	$scope.$watch("order.orderBaseBorrowRelationDto[0].customsPoundage", function(newValue, oldValue) {
		if($scope.order && $scope.order.orderBaseBorrowRelationDto[0] && $scope.order.orderBaseBorrowRelationDto[0].riskGradeId == 0) {
			return;
		}
		if(newValue) {
			getRateCD();
		} else if(oldValue&&$scope.order.orderBaseBorrowRelationDto[0]) {
			$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = sub($scope.order.orderBaseBorrowRelationDto[0].chargeMoney, oldValue);
		}
	});
	/**
	 * 监听其他费用
	 */
	$scope.$watch("order.orderBaseBorrowRelationDto[0].otherPoundage", function(newValue, oldValue) {
		if($scope.order && $scope.order.orderBaseBorrowRelationDto[0] && $scope.order.orderBaseBorrowRelationDto[0].riskGradeId == 0) {
			return;
		}
		if(newValue) {
			getRateCD();
		} else if(oldValue&&$scope.order.orderBaseBorrowRelationDto[0]) {
			$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = sub($scope.order.orderBaseBorrowRelationDto[0].chargeMoney, oldValue);
		}

	});

	//畅贷获取费率
	function getRateCD() {
		if(!$scope.order || !$scope.order.cooperativeAgencyId || !$scope.order.orderBaseBorrowRelationDto || $scope.order.orderBaseBorrowRelationDto.length == 0) {
			return;
		}
		var riskGradeId = $scope.order.orderBaseBorrowRelationDto[0].riskGradeId; //风控等级ID
		var loanAmount = $scope.order.orderBaseBorrowRelationDto[0].loanAmount; //赎楼金额
		var productId = route.getParams().cityCode + '03'; //业务产品
		var cooperativeAgencyId = $scope.order.cooperativeAgencyId; //合作机构
		var borrowingDays = $scope.order.orderBaseBorrowRelationDto[0].borrowingDays;
//		console.log(riskGradeId + ',' + loanAmount + ',' + productId + ',' + cooperativeAgencyId + ',' + borrowingDays);
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
				url: "/credit/customer/risk/v/findStageRate"
			}).success(function(msg) { //{overduerate=2.36, rate=2.6,modeid=0按天1按段}
				if('SUCCESS' == msg.code) {
					var obj = msg.data;
					if(null != obj) {
						console.log(obj);
						$scope.modeidCD = obj.modeid;
						$scope.order.orderBaseBorrowRelationDto[0].rate = obj.rate;
						$scope.order.orderBaseBorrowRelationDto[0].overdueRate = parseFloat(obj.overdueRate);
						if($scope.order.orderBaseBorrowRelationDto[0].rate > 0) {
							$scope.rateComputationsCD();
						} else {
							var chargeMoney = 0;
							$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = poundageCD(chargeMoney);
							isMaxCD($scope.order.orderBaseBorrowRelationDto[0].chargeMoney);
						}
					} else {
						$scope.order.orderBaseBorrowRelationDto[0].rate = 0;
						$scope.order.orderBaseBorrowRelationDto[0].overdueRate = 0;
						var chargeMoney = 0;
						$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = poundageCD(chargeMoney);
						isMaxCD($scope.order.orderBaseBorrowRelationDto[0].chargeMoney);
						$scope.modeid = "";
					}
				}
			})
		} else {
			if($scope.order.orderBaseBorrowRelationDto[0] && $scope.order.orderBaseBorrowRelationDto[0].riskGradeId > 0) {
				var chargeMoney = 0;
				$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = poundageCD(chargeMoney);
				isMaxCD($scope.order.orderBaseBorrowRelationDto[0].chargeMoney);
			}

		}
	}

	//计算费率 modeid 0:按天(费率*借款金额*借款期限),1:按阶段(费率*借款金额 )精确到2位 +(关外手续费+其他金额+固定费用2017.2.20)
	$scope.rateComputationsCD = function() {
		var rate = $scope.order.orderBaseBorrowRelationDto[0].rate;
		var loanAmount = $scope.order.orderBaseBorrowRelationDto[0].loanAmount;
		var borrowingDays = $scope.order.orderBaseBorrowRelationDto[0].borrowingDays;
		if($scope.modeidCD == 0 && loanAmount > 0 && borrowingDays > 0) {
			var m = parseFloat(loanAmount * 10000 * rate * borrowingDays / 100).toFixed(2);
			m = poundageCD(m);
			if($scope.chargeStandardCD) { //机构最低收费标准
				if(m == Math.max($scope.chargeStandardCD, m)) {
					$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = m;
				} else {
					$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = $scope.chargeStandardCD;
				}
			} else {
				$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = m;
			}
		} else if($scope.modeidCD == 1 && loanAmount > 0) {
			var m = parseFloat(loanAmount * 10000 * rate / 100).toFixed(2);
			m = poundageCD(m);
			if($scope.chargeStandardCD) { //机构最低收费标准
				if(m == Math.max($scope.chargeStandardCD, m)) {
					$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = m;
				} else {
					$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = $scope.chargeStandardCD;
				}
			} else {
				$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = m;
			}
		} else {
			$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = 0;
		}
	}

	function isMaxCD(m) {
		if($scope.chargeStandardCD) {
			if(m == Math.max($scope.chargeStandardCD, m)) {
				$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = m;
			} else {
				$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = $scope.chargeStandardCD;
			}
		} else {
			$scope.order.orderBaseBorrowRelationDto[0].chargeMoney = m;
		}
	}

	function poundageCD(m) {
		if($scope.order.orderBaseBorrowRelationDto[0].otherPoundage) {
			m = add(m, $scope.order.orderBaseBorrowRelationDto[0].otherPoundage);
		}
		if($scope.serviceFeesCD) {
			m = add(m, $scope.serviceFeesCD);
		}
		return m;
	}

	//编辑保存借款信息
	$scope.submitBorrow = function(isShow) {
		//$scope.order.isFinish = $scope.borrowForm.$valid?1:2;
//		var scopeTemp = angular.element(".borrowfrom-supplement").scope();
//		$scope.order.isFinish = scopeTemp.borrowForm.$valid?1:2;
//		if($scope.order.orderBaseBorrowRelationDto&&$scope.order.orderBaseBorrowRelationDto.length>0){
//			for(var i=0;i<$scope.order.orderBaseBorrowRelationDto.length;i++){
//				//$scope.order.orderBaseBorrowRelationDto[i].isFinish=$scope.changLoanForm.$valid?1:2;
//				$scope.order.orderBaseBorrowRelationDto[i].isFinish=scopeTemp.changLoanForm.$valid?1:2;
//			}
//		}
		$scope.order.isChangLoan = ($scope.addCangDai != 0&&$scope.addCangDai)?1:2;
		$scope.order.isChangLoan = $scope.all?1:2;
		$http({
			method: 'POST',
			url: "/credit/order/borrow/v/update",
			data: $scope.order
		}).success(function(data) {
//			$rootScope.isSave = true; 
			if(data.code == "SUCCESS" && !isShow) {
				box.boxAlert(data.msg);
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

					$scope.order.isOnePay = String($scope.order.isOnePay);
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
			}
		})
	}
	
//	$rootScope.isSave = true;
	//切换tab时保存房产信息
//	$rootScope.$watch("showView",function(newValue,oldValue){
//		console.log($scope.borrowForm);
//		if(oldValue == 1 && oldValue != newValue && $rootScope.isSave){
//			$rootScope.isSave = false;
//			$scope.submitBorrow(true);
//		}
//	});
	
//	$scope.$on('$destroy', function(){
//		$scope.submitBorrow(true);
//	});

	$scope.$watch(function(){
		return 	$scope.borrowForm.$valid;
	},function(newValue,oldValue){
		$scope.$watch(function(){
			return 	$scope.order;
		},function(newV,oldV){
			if(newV){
				$scope.order.isFinish = newValue?1:2;
			}
		});
	});
	
	$scope.$watch(function(){
		return 	$scope.changLoanForm.$valid;
	},function(newValue,oldValue){
		$scope.$watch(function(){
			return 	$scope.order;
		},function(newV,oldV){
			if(newV && newV.orderBaseBorrowRelationDto && newV.orderBaseBorrowRelationDto.length > 0){
				for(var i=0;i<newV.orderBaseBorrowRelationDto.length;i++){
					newV.orderBaseBorrowRelationDto[i].isFinish=newValue?1:2;
				}
			}
		});
	});

});