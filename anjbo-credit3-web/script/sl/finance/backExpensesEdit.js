angular.module("anjboApp").controller("backExpensesEditCtrl",function($scope,$http,$state,box,route){
	$scope.productId = route.getParams().cityCode+''+route.getParams().productCode;//业务产品
	$scope.harvest = new Object();
	$scope.harvest.orderNo = route.getParams().orderNo;
	$scope.isProductCode = route.getParams().productCode;;
	$scope.harvest.type=3;
	$scope.serviceFees=0;
	$scope.isChangLoan = 0;  //是否是畅贷订单
	$scope.isRate = 0;//费率是否修改
	$http({
		method: 'POST',
		url:"/credit/order/lendingHarvest/v/processDetails" ,
		data:$scope.harvest
	}).success(function(data){
		$scope.harvest=data.data.harvest;
		$scope.harvest.riskGradeId = $scope.harvest.riskGradeId+"";
		$scope.isChangLoan  = $scope.harvest.isChangLoan;   //是否是畅贷订单
		$scope.harvest.interestTime ="";
		$scope.customsPoundage = $scope.harvest.customsPoundage; //关外手续费
		$scope.serviceFees = $scope.harvest.serviceCharge; //固定服务费
		$scope.otherPoundage = $scope.harvest.otherPoundage;  //其他费用
		$scope.riskGradeId = $scope.harvest.riskGradeId;  //风控等级
		$scope.chargeMoney = $scope.harvest.chargeMoney;   //收费金额
		$scope.rate=$scope.harvest.rate;
		$scope.overdueRate=$scope.harvest.overdueRate;

		//查询风控等级集合
		$http({
			method: 'POST',
			url: '/credit/user/agencyFeescale/v/search',
			data:{
				agencyTypeId:$scope.harvest.cooperativeAgencyId,
				isRelation:1,
				productionid:$scope.harvest.cityCode + $scope.harvest.productCode
			}
		}).success(function(data) {
			$scope.riskGradeList = data.data;
			angular.forEach($scope.riskGradeList, function(data1) {
				data1.riskGradeId = String(data1.riskGradeId);
			});
		});
		//查询扣回
		$http({
			method: 'POST',
			url:"/credit/order/lendingInterest/v/processDetails" ,
			data:{
				orderNo:$scope.harvest.orderNo,
				type:2
			}
		}).success(function(data){
			var harvest=data.data.interest;
			if(harvest && harvest != null) {
				$scope.harvest.imgs = new Array();
				var img = harvest.interestImg;
				if(img != '' && img != null) {
					$scope.harvest.imgs = img.split(",");
					$("#img").val(img);
				}
				if(harvest.interestTime && harvest.interestTime!=''){
					$scope.harvest.interestTime =harvest.interestTime+"";
				}
				$scope.harvest.backExpensesMoney=harvest.collectInterestMoney;
				//应收利息初始值
				setRate();
			}
		});
	})
	
	$scope.showSubmit = function(){
		var vf =/^([0-9]\d{0,12})([.]?|(\.\d{1,4})?)$/;
		if("undefined" == typeof($scope.harvest.riskGradeId) || $scope.harvest.riskGradeId==''){
			box.boxAlert("风控等级不能为空");
			return false;
		}
		var borrowingDays =$scope.harvest.borrowingDays+"";
		if(borrowingDays=='' || !vf.test(borrowingDays)){
			box.boxAlert("期限不能为空或输入有误！");
			return false;
		}	
		var rate =$scope.harvest.rate+"";
		if(rate=='' || !vf.test(rate)){
			box.boxAlert("费率不能为空或输入有误！");
			return false;
		}
		var overdueRate = $scope.harvest.overdueRate+"";
		if(overdueRate=='' || !vf.test(overdueRate)){
			box.boxAlert("逾期费率不能为空或输入有误！");
			return false;
		}
		if($scope.harvest.chargeMoney=='' || !vf.test($scope.harvest.chargeMoney)){
			box.boxAlert("应收金额不能为空或输入有误！");
			return false;
		}
		var serviceCharge = $scope.harvest.serviceCharge+"";
		if(serviceCharge=='' || !vf.test(serviceCharge)){
			box.boxAlert("服务费不能为空或输入有误！");
			return false;
		}
		var customsPoundage = $scope.harvest.customsPoundage+"";
		if(customsPoundage=='' || !vf.test(customsPoundage)){
			box.boxAlert("关外手续费不能为空或输入有误！");
			return false;
		}
		var otherPoundage = $scope.harvest.otherPoundage+"";
		if(otherPoundage=='' || !vf.test(otherPoundage)){
			box.boxAlert("其他费用不能为空或输入有误！");
			return false;
		}
		if($scope.harvest.interestTime==null  || $scope.harvest.interestTime==''){
			box.boxAlert("扣回后置时间不能为空");
			return false;
		}
		if($scope.harvest.interestTime==null  || $scope.harvest.interestTime==''){
			box.boxAlert("收利息时间不能为空");
			return false;
		}
		var backExpensesMoney=$("#backExpensesMoney").val();
		if(backExpensesMoney==null || backExpensesMoney==''){
			box.boxAlert("扣回后置总费用不能为空");
			return false;
		}
		var returnMoney = $("#returnMoney").val();
		if(returnMoney==null  || returnMoney==''){
			box.boxAlert("返佣金额不能为空");
			return false;
		}
		var receivableInterestMoney = $("#receivableInterestMoney").val();
		if(receivableInterestMoney==null  || receivableInterestMoney=='' || !vf.test(receivableInterestMoney)){
			box.boxAlert("应收利息不能为空或输入有误！");
			return false;
		}
		var rateImg=$("#rateImg").val();
		$scope.harvest.rateImg=rateImg;
		if($scope.isRate==1){  //费率凭证
			if($scope.harvest.rateImg==''){
				box.boxAlert("您修改了费率，请上传审批凭证！");
				return false;
			}
		}
		var harvestSubmit = function(){
				$scope.harvest.uid=$scope.sumbitDto.uid; //出纳
				if($scope.harvest.uid==null || $scope.harvest.uid=='' ||  $scope.harvest.uid=='undefined'){
					box.boxAlert("请选择回款专员");
					return false;
				}
				var img=$("#img").val();
				$scope.harvest.interestImg=img;
			    $(".lhw-alert-ok").attr("disabled","disabled");
			    $scope.harvest.type=2;
			    box.waitAlert();
				$http({
					method: 'POST',
					url:"/credit/order/lendingHarvest/v/processSubmit" ,
					data:$scope.harvest
				}).success(function(data){
					box.closeWaitAlert();
					box.closeAlert();
					box.boxAlert(data.msg,function(){
						if(data.code == "SUCCESS") {
							box.closeAlert();
							$state.go("orderList");
						}
					});
				})
		}
		$scope.personnelType = "回款";
		box.editAlert($scope,"确定提交核实后置费用信息吗？请选择回款专员。","<submit-box></submit-box>",harvestSubmit);
	}
   //退回
	$scope.showBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
	//改变是风控等级
	$scope.isStandardCharge = function() {
		if(!$scope.harvest) {
			return;
		}
		//2.0.4
		if($scope.harvest.riskGradeId != 0 && $scope.harvest.riskGradeId) {
			findFund(true);
		} else {
			$scope.harvest.rate = null;
			$scope.harvest.overdueRate = null;
			$scope.harvest.chargeMoney = null;
			$scope.harvest.otherPoundage = null;
			$scope.harvest.serviceCharge = null;
			$scope.harvest.customsPoundage = null;
		}
	}
	//获取费率
	function findFund(isFromRiskGrade) {
		if(!$scope.harvest || !$scope.harvest.cooperativeAgencyId) {
			return;
		}
		// 此处有删除费率计算代码，因代码基本全部等同于rateOnblur()
		$scope.rateOnblur(isFromRiskGrade);		
	}
	
	//费率
	$scope.rateOnblur = function(isFromRiskGrade) {
		console.debug("费率计算rateOnblur("+isFromRiskGrade+"):");
		var riskGradeId = null!=$scope.harvest.riskGradeId&&!isNaN($scope.harvest.riskGradeId) ? $scope.harvest.riskGradeId : 0; //风控等级ID
		var productId = $scope.productId; //业务产品
		var cooperativeAgencyId = $scope.harvest.cooperativeAgencyId; //机构类型
		var borrowingDays = $scope.harvest.borrowingDays;
		var loanAmount = $scope.harvest.loanAmount;
		if(null!=productId && productId>0 && null!=cooperativeAgencyId && cooperativeAgencyId>0 && borrowingDays>0 && riskGradeId>0 && loanAmount>0) {
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
				if('SUCCESS'==msg.code && null!=msg.data) {		
					$scope.harvest.rate = msg.data.rate;
					$scope.harvest.overdueRate = parseFloat(msg.data.overdueRate);
					$scope.chargeStandard  = parseFloat(msg.data.chargeStandard);
					$scope.chargeMoneyBase = parseFloat(msg.data.chargeMoneyBase);
					if(undefined!=isFromRiskGrade && isFromRiskGrade){							
						$scope.harvest.customsPoundage = parseFloat(msg.data.customsPoundage);
						$scope.harvest.otherPoundage = parseFloat(msg.data.otherPoundage);
						$scope.harvest.serviceCharge = parseFloat(msg.data.serviceFees);
						$scope.harvest.chargeMoney = parseFloat(msg.data.chargeMoneyTotalInit);							
					}
					$scope.rateComputations();
				} else {
					$scope.modeid = "";
					$scope.harvest.rate = 0;
					$scope.harvest.overdueRate = 0;
					$scope.harvest.chargeMoney = "0";	
					$scope.harvest.otherPoundage = 0;
					$scope.harvest.serviceCharge = 0;
					$scope.harvest.customsPoundage = 0;
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
				$scope.harvest.chargeMoney = $scope.chargeStandard;
				return;
			}
		}
		$scope.harvest.chargeMoney = chargeMoneyTotal;
	}
	
	//改变是逾期费率
	$scope.$watch("harvest.overdueRate",function(newValue, oldValue){
		if(typeof(newValue)!='undefined' && typeof(oldValue)!='undefined' && newValue!=$scope.overdueRate){
			  $scope.harvest.isUpdata="1";
		 }
	});
	//改变是收费金额
	$scope.$watch("harvest.chargeMoney",function(newValue, oldValue){
		 if(typeof(newValue)!='undefined' && typeof(oldValue)!='undefined' && newValue!=$scope.chargeMoney){
			  $scope.harvest.isUpdata="1";
		 }
	});
	/**
	 * 监听关外手续费
	 */
	$scope.$watch("harvest.customsPoundage", function(newValue, oldValue) {
		if(typeof(newValue)!='undefined' && typeof(oldValue)!='undefined'){
			$scope.harvest.isUpdata="1";
			$scope.rateOnblur();
		}
	});
	/**
	 * 监听其他费用
	 */
	$scope.$watch("harvest.otherPoundage", function(newValue, oldValue) {
		if(typeof(newValue)!='undefined' && typeof(oldValue)!='undefined'){
			$scope.harvest.isUpdata="1";
			$scope.rateOnblur();
		} 

	});
	/** 监听固定服务费 */
	$scope.$watch("harvest.serviceCharge", function(newValue, oldValue) {
		if(typeof(newValue)!='undefined' && typeof(oldValue)!='undefined'){
			 $scope.harvest.isUpdata="1";
			$scope.rateOnblur();
		} 
	});
	//改变是费率
	$scope.$watch("harvest.rate",function(newValue, oldValue){
		if(typeof(newValue)!='undefined' && typeof(oldValue)!='undefined' && newValue!=$scope.rate){
			 $scope.harvest.isUpdata="1";
			 $scope.isRate =1;
			 $scope.rateOnblur();
		}else{
			 $scope.isRate =0;
		}
	});
	/* 合并追加费用(关外手续费+其他金额+固定费用)*/
	function poundage(chargeMoneyBase) {		
		if($scope.harvest.customsPoundage) {
			chargeMoneyBase = add(chargeMoneyBase, $scope.harvest.customsPoundage);
		}
		if($scope.harvest.otherPoundage) {
			chargeMoneyBase = add(chargeMoneyBase, $scope.harvest.otherPoundage);
		}
		if($scope.harvest.serviceCharge) {
			chargeMoneyBase = add(chargeMoneyBase, $scope.harvest.serviceCharge);
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
	function setRate(){
		$scope.harvest.receivableInterestMoney=($scope.harvest.loanAmount*$scope.harvest.borrowingDays*$scope.harvest.rate*100).toFixed(2);
		//应收金额=应收利息+服务费+关外手续费+其他费用
		$scope.harvest.chargeMoney=($scope.harvest.receivableInterestMoney*1+$scope.harvest.serviceCharge*1+$scope.harvest.customsPoundage*1+$scope.harvest.otherPoundage*1).toFixed(2);
		
	}
		//费率
		$scope.$watch("harvest.rate", function(newValue, oldValue) {
			if(typeof(newValue)!='undefined' && typeof(oldValue)!='undefined' ){
				setRate();
				
			}
		});
		//服务费
		$scope.$watch("harvest.serviceCharge", function(newValue, oldValue) {
			if(typeof(newValue)!='undefined' && typeof(oldValue)!='undefined' ){
				setRate();
				
			}
		});
		//关外手续费
		$scope.$watch("harvest.customsPoundage", function(newValue, oldValue) {
			if(typeof(newValue)!='undefined' && typeof(oldValue)!='undefined' ){
				setRate();
				
			}
		});
		//其它费用
		$scope.$watch("harvest.otherPoundage", function(newValue, oldValue) {
			if(typeof(newValue)!='undefined' && typeof(oldValue)!='undefined' ){
				setRate();
				
			}
		});	
		//应收利息
		$scope.$watch("harvest.receivableInterestMoney", function(newValue, oldValue) {
			if(typeof(newValue)!='undefined' && typeof(oldValue)!='undefined' ){
				$scope.harvest.chargeMoney=($scope.harvest.receivableInterestMoney*1+$scope.harvest.serviceCharge*1+$scope.harvest.customsPoundage*1+$scope.harvest.otherPoundage*1).toFixed(2);
			}
		});	

			//应收金额
		$scope.$watch("harvest.chargeMoney", function(newValue, oldValue) {
			if(typeof(newValue)!='undefined' && typeof(oldValue)!='undefined' ){
				$scope.harvest.returnMoney=($scope.harvest.backExpensesMoney*1-$scope.harvest.chargeMoney*1).toFixed(2);
				if($scope.harvest.returnMoney<0){
					$scope.harvest.returnMoney=0;
				}
				
			}
		});
	

	
})