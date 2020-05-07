angular.module("anjboApp").controller("receivableForEditCtrl", function ($scope, $http, $timeout, $state, $filter, box, route) {

	$scope.obj = new Object();
	$scope.obj.huaRongDto = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;
	$scope.isProductCode = route.getParams().productCode;
	$scope.relationOrderNo = route.getParams().relationOrderNo;
	$scope.obj.forType = 0;
	$scope.payCount = 0;
	$scope.le = 0; //回款列表初始长度
	$scope.obj.forName = $state.current.name;
	$scope.obj.isShow = false; //显示首次
	$scope.rongAnShow = false; //显示推送页面
	$scope.fundCode = false; //判断是否推送华融信息
	$scope.fundCodeYNXT = false; //判断是否推送云南信托
	$scope.isFirstFor = 0;
	$scope.isNextUser = false;

	// 初始化信息
	$http({
		method: 'POST',
		url: "/credit/finance/receivableFor/v/init",
		data: $scope.obj
	}).success(function (data) {
		$scope.dayRate = data.data.dayRate;
		$scope.overdueRate = data.data.overdueRate;
		$scope.fundCode = data.data.fundCode;
		$scope.borrowingDays = data.data.borrowingDays;
		$scope.obj = data.data.lendingDto;
		$scope.obj.forList = data.data.forList;
		$scope.le = $scope.obj.forList.length; //判断首期尾期用
		$scope.hasDto = data.data.receivableHasDto;
		if ($scope.hasDto != null) {
			if ($scope.obj.productCode != '02') {
				$scope.obj.remark = $scope.hasDto.remark;
			}
		}
		if ($scope.obj.forList.length == 0 || $scope.obj.forList == null) {
			$scope.obj.forList = new Array();
			$scope.payForList = {
				"payMentAmountDate": null,
				"payMentAmount": null,
				"payMentPic": null
			};
			$scope.obj.forList.push($scope.payForList);
			$scope.obj.remark = "";
			$scope.obj.isShow = true;
		} else {
			$scope.obj.forType = 1; //forList不为null 则尾期
		}
//		loadFund();
	})
	//加载资金分配方信息
	loadFund = function () {
		var param = {
			orderNo: $scope.orderNo
		}
		$http({
			url: '/credit/order/allocationFund/v/processDetails',
			method: 'POST',
			data: param
		}).success(function (data) {
			if ("SUCCESS" == data.code) {
				$scope.riskAllocationFundDto = data.data[0];
				if (114 == Number($scope.riskAllocationFundDto.fundCode)) {
					$scope.fundCodeYNXT = true;
					loadFundYnxt();
				}
			}
		});
	}

	$scope.loadHuarong = function () {
		var lists = new Array();
		lists = $scope.obj.forList[0];
		if (lists.payMentAmountDate == null || lists.payMentAmountDate == '') {
			box.boxAlert("实际回款时间不能为空");
			return false;
		}
		$scope.rongAnShow = true;
		$scope.forShow = false;
		$scope.obj.huaRongDto = new Object();
		$scope.obj.huaRongDto.repaymentYestime = lists.payMentAmountDate; //回款时间
		$scope.obj.huaRongDto.repaymentTime = $scope.obj.customerPaymentTimeStr;
		if ($scope.obj.customerPaymentTimeStr) {
			var date = new Date($scope.obj.customerPaymentTimeStr);
			date.setDate(date.getDate() + 1);
			var month = date.getMonth() + 1;
			var day = date.getDate();
			var times = date.getFullYear() + '-' + getFormatDate(month) + '-' + getFormatDate(day);
			$scope.obj.huaRongDto.repaymentTime = times;
		}
		rongAnVal();
	}
	// 加载云南信托
	loadFundYnxt = function () {
		var param = {
			orderNo: $scope.orderNo
		}
		$http({
			url: '/credit/third/api/yntrust/v/selectRepaymentPlan',
			method: 'POST',
			data: param
		}).success(function (data) {
			$scope.yunnanAudit = new Object();
			if ("SUCCESS" == data.code) {
				$scope.thirdYntrustRepaymentPlan = data.data;
				$scope.thirdYntrustRepaymentPlan.orderNo = $scope.orderNo;
				$scope.thirdYntrustRepaymentPlan.givePayPrincipal = data.data.repayPrincipal;
				$scope.thirdYntrustRepaymentPlan.overdueDayRate = 0.0;
				$http({
					url: '/credit/third/api/yntrust/v/selectRepaymentPlanMap',
					method: 'POST',
					data: param
				}).success(function (data) {
					if ("SUCCESS" == data.code && undefined != data.data.contract.overdueDayRate && null != data.data.contract.overdueDayRate) {
						$scope.thirdYntrustRepaymentPlan.overdueDayRate = parseFloat(data.data.contract.overdueDayRate).toFixed(2);
					}
				});

				if (data.data) {
					$scope.yunnanAudit = data.data;
					$scope.yunnanAudit.changeReason = "2";
					$scope.yunnanAudit.repayDate = $filter('date')($scope.yunnanAudit.repayDate, 'yyyy-MM-dd HH:mm');
				}
			}
		});
	}

	$scope.add = function (index) {
		$scope.payForList = {
			"payMentAmountDate": null,
			"payMentAmount": null,
			"payMentPic": null
		};
		$scope.obj.forList.push(new Object());
	}
	$scope.del = function (index) {
		$scope.obj.forList.splice(index, 1);
	}
	$scope.addPay = function (index) {
		var payCount = 0;
		for (var i = 0; i < $scope.obj.forList.length; i++) {
			var payMentAmount = $scope.obj.forList[i].payMentAmount * 1;
			payCount = (payCount + payMentAmount);
		}
		$scope.payCount = payCount;
	}

	//一次回款
	$scope.submit = function () {
		$scope.isFirstFor = 1;

		var lists = new Array();
		lists = $scope.obj.forList[0];
		$scope.fundCodeYNXT = false;
		if ($scope.fundCodeYNXT) { //是否显示推送云南信托
			if (lists.payMentAmount != $scope.obj.loanAmount) {
				box.boxAlert("回款金额与放款金额不一致");
				return false;
			}
			yunNanAlertInit(lists);
			return;
		}

		// 老资方逻辑
		if (lists.payMentAmountDate == null || lists.payMentAmountDate == '') {
			box.boxAlert("实际回款时间不能为空");
			return false;
		}
		if (lists.payMentAmount != $scope.obj.loanAmount) {
			box.boxAlert("回款金额与放款金额不一致");
			return false;
		}
		var hktype = $("#hktype").val();
		if (typeof (hktype) != "undefined" && hktype == 2) {
			if ($scope.obj.penaltyPayable == null || $scope.obj.penaltyPayable === '') {
				box.boxAlert("罚息金额不能为空");
				return false;
			}
		}
		//		if($scope.fundCode){  //是否显示推送融安 (畅贷不推送)
		//			$scope.rongAnShow = true; 
		//			$scope.forShow = false;	
		//			$scope.obj.huaRongDto = new Object();
		//			$scope.obj.huaRongDto.repaymentYestime=lists.payMentAmountDate;  //回款时间
		//			$scope.obj.huaRongDto.repaymentTime=$scope.obj.customerPaymentTimeStr;
		//			if($scope.obj.customerPaymentTimeStr){
		//				var date = new Date($scope.obj.customerPaymentTimeStr);
		//	            date.setDate(date.getDate() + 1);
		//	            var month = date.getMonth() + 1;
		//	            var day = date.getDate();
		//	            var times= date.getFullYear() + '-' + getFormatDate(month) + '-' + getFormatDate(day);
		//	            $scope.obj.huaRongDto.repaymentTime= times;
		//			}
		//			rongAnVal();
		//		}else{
		if($scope.isProductCode=='06'||$scope.isProductCode=='07'){
			$scope.dataForSubmit06();
		}else{
			$scope.forSubmit(0);
		}
		
		//		}
	}

	function getFormatDate(arg) {
		if (arg == undefined || arg == '') {
			return '';
		}
		var re = arg + '';
		if (re.length < 2) {
			re = '0' + re;
		}
		return re;
	}
	//多次回款
	$scope.submit2 = function () {
		var lists = new Array();
		if ($scope.fundCode) {
			$scope.obj.huaRongDto = new Object();
		}
		var jy = false;
		for (var i = $scope.le; i < $scope.obj.forList.length; i++) {
			if (i >= $scope.le) {
				lists.push($scope.obj.forList[i]);
				if ($scope.obj.forList[i].payMentAmountDate == null || $scope.obj.forList[i].payMentAmountDate == '') {
					box.boxAlert("回款时间不能为空");
					return false;
				}
				if ($scope.obj.forList[i].payMentAmount == null || $scope.obj.forList[i].payMentAmount == '') {
					box.boxAlert("回款金额不能为空");
					return false;
				}
				if ($scope.fundCode) {
					$scope.obj.huaRongDto.repaymentYestime = $scope.obj.forList[i].payMentAmountDate; //回款时间
				}
			}
		}
		var hktype = $("#hktype").val();
		if (typeof (hktype) != "undefined" && hktype == 2) {
			if ($scope.obj.penaltyPayable == null || $scope.obj.penaltyPayable === '') {
				box.boxAlert("罚息金额不能为空");
				return false;
			}
		}
		//		if($scope.obj.refund==1){
		//			if($scope.obj.penaltyPayable==null ||$scope.obj.penaltyPayable==''){
		//				box.boxAlert("退费金额不能为空");
		//				return false;
		//			}
		//		}
		$scope.obj.newForList = lists;
		$scope.isFirstFor = 2;
		//		if($scope.fundCode && $scope.le==0){  //是否显示推送融安（首期推送）
		//			$scope.rongAnShow = true; 
		//			$scope.forShow = false;	
		//			$scope.obj.huaRongDto.repaymentTime=$scope.obj.customerPaymentTimeStr;
		////			rongAnVal();
		//		}else 
		if ($scope.fundCodeYNXT && $scope.le > 0) { // 云南尾期	
			var payMentAmountTotal = 0.0;
			for (var i = 0; i < $scope.obj.forList.length; i++) {
				payMentAmountTotal += $scope.obj.forList[i].payMentAmount
			}
			if (payMentAmountTotal != $scope.obj.loanAmount) {
				box.boxAlert("回款金额与放款金额不一致");
				return false;
			}
			lists = $scope.obj.forList[$scope.obj.forList.length - 1];
			yunNanAlertInit(lists);
		} else {
			$scope.forSubmit(0);
		}
	}

	//初始化融安数据
	function rongAnVal() {
		var loanAmount = ($scope.obj.loanAmount * 10000);
		if (!isNaN(loanAmount)) {
			$scope.obj.huaRongDto.repaymentAccount = loanAmount;
			$scope.obj.huaRongDto.capital = loanAmount;
			$scope.obj.huaRongDto.setlCapital = loanAmount;
			//计算逾期天数Start---------
			var days = 0;
			var newTime = $scope.obj.huaRongDto.repaymentYestime;
			var oldTime = $scope.obj.huaRongDto.repaymentTime;
			if (typeof (newTime) != 'undefined' && typeof (oldTime) != 'undefined') {
				newTime = newTime.replace(/-/g, "/");
				oldTime = oldTime.replace(/-/g, "/");
				var startdate = new Date(oldTime);
				var enddate = new Date(newTime);
				var time = enddate.getTime() - startdate.getTime();
				days = parseInt(time / (1000 * 60 * 60 * 24));
				if (days < 0) {
					days = 0;
				}
			}
			//计算逾期天数end-----------
			$scope.obj.huaRongDto.lateDays = days; //逾期天数
			//			var lateInterest=(days*loanAmount*$scope.overdueRate);//逾期利息（逾期天数*本金（借款金额）*利率）
			var lateInterest = (days * loanAmount * 0.11 / 360);
			lateInterest = floadNum(lateInterest);
			//			lateInterest = div(lateInterest,100);
			$scope.obj.huaRongDto.lateInterest = lateInterest;
			//			var interest=(loanAmount*$scope.borrowingDays*$scope.dayRate);//应还利息（本金*借款期限*利率 ） 
			var interest = (loanAmount * $scope.borrowingDays * 0.11 / 360);
			interest = floadNum(interest);
			//			interest = div(interest,100);
			$scope.obj.huaRongDto.interest = interest;
			$scope.obj.huaRongDto.setlInterest = interest; //已还利息 - 应还利息
			$scope.obj.huaRongDto.setlLateInterest = lateInterest; //已还罚息 -逾期利息
			$scope.obj.huaRongDto.psIntRate = 0.0306; //$scope.dayRate;   //贷款执行利率
			$scope.obj.huaRongDto.repaymentYesaccount = (loanAmount * 1 + lateInterest * 1 + interest * 1); //实还金额
		}
	}
	// 云南信托
	function yunNanAlertInit(lists) {
		if (!$scope.thirdYntrustRepaymentPlan) {
			box.boxAlert("资方信息不匹配或有误(云南信托)");
			return false;
		}
		if (lists.payMentAmountDate == null || lists.payMentAmountDate == '') {
			box.boxAlert("实际回款时间不能为空");
			return false;
		}
		var hktype = $("#hktype").val();
		if (typeof (hktype) != "undefined" && hktype == 2) {
			if ($scope.obj.penaltyPayable == null || $scope.obj.penaltyPayable === '') {
				box.boxAlert("罚息金额不能为空");
				return false;
			}
		}
		$scope.yunNanShow = true;
		$scope.forShow = false;
		$scope.obj.info = $scope.thirdYntrustRepaymentPlan;
		//		$scope.obj.pay = new Object();		
		// 回款时间
		var date = new Date($scope.thirdYntrustRepaymentPlan.repayDate);
		//date.setDate(date.getDate() + 1);
		var month = date.getMonth() + 1;
		var day = date.getDate();
		var hour = date.getHours();
		var minute = date.getMinutes();
		var times = date.getFullYear() + '-' + getFormatDate(month) + '-' + getFormatDate(day) + ' ' + getFormatDate(hour) + ":" + getFormatDate(minute);
		$scope.obj.info.repayDate = times;
		$scope.obj.info.repayDate = $filter('date')(date, 'yyyy-MM-dd HH:mm');
		$scope.obj.info.realityPayDate = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm');
		//应还本金
		$scope.obj.info.repayPrincipal = ($scope.thirdYntrustRepaymentPlan.repayPrincipal);

		// 逾期罚息+逾期违约金+逾期服务费+逾期其他费用
		$scope.obj.info.latePenalty = 0;
		$scope.obj.info.lateFee = 0;
		$scope.obj.info.lateOtherCost = 0;
		$scope.obj.info.overDueFee = 0;

		yunNanCalculate();
	}

	function yunNanCalculate() {
		if (!$scope.obj.info) { // || !$scope.obj.pay
			return;
		}
		// 计算逾期天数Start---------
		var days = 0,
			daysRepay = 0;
		if (typeof ($scope.obj.info.realityPayDate) != 'undefined' && typeof ($scope.obj.info.repayDate) != 'undefined') {
			//newTime=newTime.replace(/-/g,"/");
			//oldTime=oldTime.replace(/-/g,"/");
			var newTime = $filter('date')(new Date($scope.obj.info.realityPayDate), 'yyyy-MM-dd');
			var oldTime = $filter('date')(new Date($scope.obj.info.repayDate), 'yyyy-MM-dd');
			var startdate = new Date(oldTime);
			var enddate = new Date(newTime);
			var time = enddate.getTime() - startdate.getTime();
			days = parseInt(time / (1000 * 60 * 60 * 24));
			daysRepay = days
			if (days < 0) {
				days = 0;
			}
		}
		$scope.obj.info.lateDay = days;
		// 已还利息：提前=应还利息-本金*提前天数*0.0003，非提前=应还利息
		if (Number(daysRepay) < 0) {
			daysRepay = Math.abs(daysRepay);
			daysRepay = $scope.obj.info.borrowingDays - Number(daysRepay);
			var tmpRepayPrincipal = parseFloat($scope.obj.info.repayPrincipal * daysRepay * 0.0005).toFixed(2);
			$scope.obj.info.givePayProfit = tmpRepayPrincipal;
			if (Number($scope.obj.info.givePayProfit) < 0) {
				$scope.obj.info.givePayProfit = 0;
			}
			//$scope.obj.info.givePayProfit = $scope.obj.info.repayProfit - parseFloat($scope.obj.info.repayPrincipal * (daysRepay+1) * 0.0005).toFixed(2);
		} else {
			$scope.obj.info.givePayProfit = $scope.obj.info.repayProfit;
		}

		if ($scope.obj.info.givePayProfit < 0) {
			$scope.obj.info.givePayProfit = 0;
		}
		// 逾期计算		 
		var loanAmount = parseFloat(isNaN($scope.obj.info.repayPrincipal) ? 0 : $scope.obj.info.repayPrincipal);
		$scope.obj.info.penaltyFee = parseFloat(days * loanAmount * $scope.thirdYntrustRepaymentPlan.overdueDayRate).toFixed(2); //逾期罚息：动态=$scope.thirdYntrustRepaymentPlan.overdueDayRate，固定=0.105/360
		// 费用合计
		yunNanCalculateTotal();
	}

	function yunNanCalculateTotal() {
		if (!$scope.obj.info) { // || !$scope.obj.pay
			return;
		}
		// 逾期费用 = 逾期罚息+逾期违约金+逾期服务费+逾期其他费用
		$scope.obj.info.overDueFee = parseFloat(
			parseFloat(isNaN($scope.obj.info.penaltyFee) ? 0 : $scope.obj.info.penaltyFee) +
			parseFloat(isNaN($scope.obj.info.latePenalty) ? 0 : $scope.obj.info.latePenalty) +
			parseFloat(isNaN($scope.obj.info.lateFee) ? 0 : $scope.obj.info.lateFee) +
			parseFloat(isNaN($scope.obj.info.lateOtherCost) ? 0 : $scope.obj.info.lateOtherCost)
		).toFixed(2);
		// 还款总金额 = 应还本金+应还利息+逾期总额
		//		$scope.obj.pay.amount = parseFloat(
		//				parseFloat(isNaN($scope.obj.info.repayPrincipal) ? 0 : $scope.obj.info.repayPrincipal) 
		//				+ parseFloat(isNaN($scope.obj.info.repayProfit) ? 0 : $scope.obj.info.repayProfit)
		//				+ parseFloat(isNaN($scope.obj.info.overDueFee) ? 0 : $scope.obj.info.overDueFee)
		//			).toFixed(2);
	}
	// 计算公式
	function floadNum(a) {
		var num = a + "";
		var index = num.indexOf('.');
		if (index == -1) {
			return num;
		} else {
			var f = parseFloat(num).toFixed(2);
			return f;
		}
	}

	function div(a, b) {
		var c, d, e = 0,
			f = 0;
		try {
			e = a.toString().split(".")[1].length;
		} catch (g) {}
		try {
			f = b.toString().split(".")[1].length;
		} catch (g) {}
		return c = Number(a.toString().replace(".", "")), d = Number(b.toString().replace(".", "")), c / d * Math.pow(10, f - e);
	}
	
	$scope.dataForSubmit06 = function(){
		$http({
			method: 'POST',
			url: "/credit/finance/receivableFor/v/add",
			data: $scope.obj
		}).success(function (data) {
			box.closeWaitAlert();
			box.boxAlert(data.msg);
			if (data.code == "SUCCESS") {
				box.closeAlert();
				box.boxAlert("操作成功!");
				$state.go("orderList");
			}
			if (data.msg == '推送融安信息失败！') {
				box.closeAlert();
				$state.go("orderList");
			}
		})
	}

	$scope.forSubmit = function (isRongAn) {
		if ($scope.fundCodeYNXT && $scope.yunNanForm.$invalid && (($scope.isFirstFor == 1) || ($scope.isFirstFor != 1 && $scope.le > 0))) {
			return false;
		}
		var firstForSubmit = function () {
			if ($scope.hktype == 2) { //逾期  || ($scope.hktype==3 && $scope.obj.refund==1) //提前
				$scope.obj.createUid = $scope.sumbitDto.uid; //出纳
				if ($scope.obj.createUid == null || $scope.obj.createUid == '' || $scope.obj.createUid == 'undefined') {
					box.boxAlert("请选择收罚息专员");
					return false;
				}
			}
			if ($scope.isNextUser && $scope.obj.productCode == "03" && $scope.relationOrderNo && $scope.relationOrderNo != "0") {
				$scope.obj.createUid = $scope.sumbitDto.uid; //出纳
				if ($scope.obj.createUid == null || $scope.obj.createUid == '' || $scope.obj.createUid == 'undefined') {
					box.boxAlert("请选择返佣息专员");
					return false;
				}
			}
			if (undefined != $scope.obj.info && undefined != $scope.dictPaymentMethod && "@请选择" != $scope.dictPaymentMethod) {
				var _dictAry = $scope.dictPaymentMethod.split("@");
				$scope.obj.info.paymentMethod = _dictAry[0];
				$scope.obj.info.paymentMethodName = _dictAry[1];
			}
			//				    if(undefined!=$scope.dictVoucherType && "@请选择"!=$scope.dictVoucherType){
			//				    	_dictAry = $scope.dictVoucherType.split("@");
			//					    $scope.obj.pay.voucherType = _dictAry[0];
			//					    $scope.obj.pay.voucherTypeName = _dictAry[1];
			//				    }
			//						    
			$scope.obj.relationOrderNo = $scope.relationOrderNo;
			$(".yunNanBtns .lhw-alert-ok").attr("disabled", "disabled");
			var repayDate;
			var realityPayDate;
			if ($scope.obj.info && $scope.obj.info.repayDate) {
				repayDate = $scope.obj.info.repayDate;
				var date = $filter('date')($scope.obj.info.repayDate, 'yyyy-MM-dd HH:mm');
				var fmdate = new Date(date);
				$scope.obj.info.repayDate = fmdate;
			}
			if ($scope.obj.info && $scope.obj.info.realityPayDate) {
				realityPayDate = $scope.obj.info.realityPayDate;
				var date = $filter('date')($scope.obj.info.realityPayDate, 'yyyy-MM-dd HH:mm');
				var fmdate = new Date(date);
				$scope.obj.info.realityPayDate = fmdate;
			}
			box.waitAlert();
			// if ($scope.fundCodeYNXT && (($scope.isFirstFor == 1) || ($scope.isFirstFor != 1 && $scope.le > 0))) {
			// 	$http({
			// 		method: 'POST',
			// 		url: "/credit/third/api/yntrust/v/addRepaymentInfoAndPayInfo",
			// 		data: $scope.obj.info
			// 	}).success(function (data) {
			// 		if ($scope.obj.info && $scope.obj.info.repayDate) {
			// 			$scope.obj.info.repayDate = repayDate;
			// 		}
			// 		if ($scope.obj.info && $scope.obj.info.realityPayDate) {
			// 			$scope.obj.info.realityPayDate = realityPayDate;
			// 		}

			// 		$(".yunNanBtns .lhw-alert-ok").removeAttr("disabled");
			// 		if (data.code == "SUCCESS") {
			// 			dataForSubmit();
			// 		} else if (data.msg.indexOf("不能重复推送") > -1) {
			// 			dataForSubmit();
			// 		} else {
			// 			box.closeWaitAlert();
			// 			box.boxAlert(data.msg);
			// 		}
			// 	});
			// } else {
			// 	$scope.obj.isRongAn = isRongAn;
				dataForSubmit();
			// }
		}
		var dataForSubmit = function () {
			if ($scope.isFirstFor == 1||$scope.isProductCode=='06'||$scope.isProductCode=='07') { //一次回款
				$http({
					method: 'POST',
					url: "/credit/finance/receivableFor/v/add",
					data: $scope.obj
				}).success(function (data) {
					box.closeWaitAlert();
					box.boxAlert(data.msg);
					if (data.code == "SUCCESS") {
						box.closeAlert();
						$state.go("orderList");
					}
					if (data.msg == '推送融安信息失败！') {
						box.closeAlert();
						$state.go("orderList");
					}
				})
			} else if ($scope.isFirstFor == 2) { //多次回款
				$http({
					method: 'POST',
					url: "/credit/finance/receivableFor/v/addToMany",
					data: $scope.obj
				}).success(function (data) {
					box.closeWaitAlert();
					box.closeAlert();
					box.boxAlert(data.msg);
					if (data.code == "SUCCESS") {
						box.closeAlert();
						$state.go("orderList");
					}
					if (data.msg == '推送融安信息失败！') {
						box.closeAlert();
						$state.go("orderList");
					}
				})
			}
		}

		if ($scope.obj.productCode == "01") { //交易类订单
			if ($scope.le == 0 && $scope.obj.oneTimePay != 1) { //首期
				box.editAlert($scope, "提交", "确定提交回款信息吗？", firstForSubmit);
			} else { //尾期
				$scope.hktype = $("#hktype").val();
				if ($scope.hktype == 1) { //正常
					box.editAlert($scope, "提交", "确定提交回款信息吗？", firstForSubmit);
				} else if ($scope.hktype == 2) { //逾期
					$scope.personnelType = "收罚息";
					box.editAlert($scope, "确定提交回款信息吗？请选择收罚息专员。", "<submit-box></submit-box>", firstForSubmit);
				} else if ($scope.hktype == 3) { //提前
					if ($scope.obj.refund == 1) { //是否退费
						$scope.personnelType = "收罚息";
						box.editAlert($scope, "确定提交回款信息吗？请选择收罚息专员。", "<submit-box></submit-box>", firstForSubmit);
					} else { //不退费						 
						box.editAlert($scope, "提交", "确定提交回款信息吗？", firstForSubmit);
					}
				} else {
					box.editAlert($scope, "提交", "确定提交回款信息吗？", firstForSubmit);
				}
			}
		} else if ($scope.obj.productCode == "02" || ($scope.obj.productCode == "03" && $scope.relationOrderNo && $scope.relationOrderNo == "0")) { //非交易类/畅贷
			$scope.hktype = $("#hktype").val();
			if ($scope.hktype == 1) { //正常
				box.editAlert($scope, "提交", "确定提交回款信息吗？", firstForSubmit);
			} else if ($scope.hktype == 2) { //逾期
				$scope.personnelType = "收罚息";
				box.editAlert($scope, "确定提交回款信息吗？请选择收罚息专员。", "<submit-box></submit-box>", firstForSubmit);
			} else if ($scope.hktype == 3) { //提前
				if ($scope.obj.refund == 1) { //是否退费
					$scope.personnelType = "收罚息";
					box.editAlert($scope, "确定提交回款信息吗？请选择收罚息专员。", "<submit-box></submit-box>", firstForSubmit);
				} else { //不退费
					box.editAlert($scope, "提交", "确定提交回款信息吗？", firstForSubmit);
				}
			}
		} else if ($scope.obj.productCode == "03" && $scope.relationOrderNo && $scope.relationOrderNo != "0") { //畅贷关联置换贷
			$scope.hktype = $("#hktype").val();
			if ($scope.hktype == 2) { //逾期
				$scope.personnelType = "收罚息";
				box.editAlert($scope, "确定提交回款信息吗？请选择收罚息专员。", "<submit-box></submit-box>", firstForSubmit);
			} else { //正常
				$scope.obj.orderNo = $scope.orderNo;
				$http({
					method: 'POST',
					url: "/credit/finance/lendingHarvest/v/detail",
					data: $scope.obj
				}).success(function (data) {
					$scope.harvest = data.data;
					var returnMoney = $scope.harvest.harvest.returnMoney;
					if (returnMoney && returnMoney != 0 && returnMoney != '') {
						$scope.isNextUser = true;
					}
					if ($scope.isNextUser) {
						$scope.personnelType = "返佣";
						box.editAlert($scope, "确定提交回款信息吗？请选择返佣专员。", "<submit-box></submit-box>", firstForSubmit);
					} else {
						box.editAlert($scope, "提交", "确定提交回款信息吗？", firstForSubmit);
					}
				});
			}
		}
	}

	// 表单监控
	$scope.$watch("obj.huaRongDto.repaymentTime", function (newValue, oldValue) {
		if (newValue != 'undefined') {
			rongAnVal();
		}
	});
	$scope.$watch("obj.huaRongDto.repaymentYestime", function (newValue, oldValue) {
		if (newValue != 'undefined') {
			rongAnVal();
		}
	});
	// 云南
	$scope.$watch("obj.info.repayDate", function (newValue, oldValue) {
		if (newValue != 'undefined') {
			yunNanCalculate();
		}
	});
	$scope.$watch("obj.info.realityPayDate", function (newValue, oldValue) {
		if (newValue != 'undefined') {
			yunNanCalculate();
		}
	});
	$scope.$watch("obj.info.repayPrincipal", function (newValue, oldValue) {
		if (newValue != 'undefined') {
			yunNanCalculate();
		}
	});
	$scope.$watch("obj.info.repayProfit", function (newValue, oldValue) {
		if (newValue != 'undefined') {
			yunNanCalculate();
		}
	});
	$scope.$watch("obj.info.penaltyFee", function (newValue, oldValue) {
		if (newValue != 'undefined') {
			yunNanCalculateTotal();
		}
	});
	$scope.$watch("obj.info.latePenalty", function (newValue, oldValue) {
		if (newValue != 'undefined') {
			yunNanCalculateTotal();
		}
	});
	$scope.$watch("obj.info.lateFee", function (newValue, oldValue) {
		if (newValue != 'undefined') {
			yunNanCalculateTotal();
		}
	});
	$scope.$watch("obj.info.lateOtherCost", function (newValue, oldValue) {
		if (newValue != 'undefined') {
			yunNanCalculateTotal();
		}
	});

	$scope.showYunnan1 = function () {
		$scope.yunNanShow1 = true;
	}

	$scope.yunnanAuditSubmit = function () {
		$scope.yunnanAudit.orderNo = $scope.obj.orderNo;
		var repayDate = $scope.yunnanAudit.repayDate;
		if ($scope.yunnanAudit.repayDate) {
			var date = $filter('date')($scope.yunnanAudit.repayDate, 'yyyy-MM-dd HH:mm:ss');
			var fmdate = new Date(date);
			$scope.yunnanAudit.repayDate = fmdate;
		}
		$http({
			method: 'POST',
			url: "/credit/third/api/yntrust/v/updateRepaySchedule",
			data: $scope.yunnanAudit
		}).success(function (data) {
			$scope.yunnanAudit.repayDate = repayDate;
			box.closeAlert();
			box.boxAlert(data.msg, function () {
				if (data.code == "SUCCESS") {
					$timeout(function () {
						$scope.yunNanShow1 = false;
					});
					box.closeAlert();
				}
			});
		})
	}

	//推送华融
	$scope.huarongAuditSubmit = function () {
		$http({
			method: 'POST',
			url: "/credit/finance/receivableFor/v/toHuarong",
			data: $scope.obj
		}).success(function (data) {
			box.closeWaitAlert();
			box.boxAlert(data.msg, function () {
				if (data.code == "SUCCESS") {
					box.closeAlert();
				}
			});
		})
	}
});