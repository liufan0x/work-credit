angular.module("anjboApp").controller("allocationFundAduitCtrl",function($scope,route,$http,$state,box){
	$scope.obj = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;
	$scope.flag2=false;
	$scope.flag=true;
	$scope.rongAnhkShow = false;
	$scope.isAgain = true;
	$scope.audit = new Object();
	$scope.isHuaRongShow =false;
	$http({
		method: 'POST',
		url:"/credit/risk/allocationfundaduit/v/init" ,
		data:$scope.obj
	}).success(function(data){
		$scope.obj=data.data.fundCompleteDto;
		if($scope.obj.queryFrequency==0){
			$scope.obj.queryFrequency='';
		}
		if($scope.obj.id>0){
			$scope.flag2=true;
			$scope.flag=false;
			$scope.obj.startTime=$scope.obj.startTimeStr;
	    	$scope.obj.endTime=$scope.obj.endTimeStr;
		}
		$scope.isHuaRongShow=data.data.isHuaRongShow;
	});
	$scope.orderIsBack = false;
	//退回
	$scope.backToSubmit = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
	orderIsBack = function(){
		var param = {
			orderNo:$scope.orderNo,
			processId:'fundAduit'
		}
		$http({
			url:'/credit/risk/base/v/orderIsBack',
			data:param,
			method:'POST'
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.orderIsBack = data.data;
			}
		});
	}
	orderIsBack();
	//非华融保存
	$scope.saveFundAudit=function(){
		if($scope.obj.remark=="" || $scope.obj.remark ==null){
			box.boxAlert("审批意见不能为空");
			return false;
		}
		var toSave = function() {
			box.waitAlert();
			$http({
				method: 'POST',
				url:"/credit/risk/allocationfundaduit/v/saveFundAudit" ,
				data:$scope.obj
			}).success(function(data){
				box.closeWaitAlert();
				box.boxAlert(data.msg,function(){
					if(data.code == "SUCCESS") {
						box.closeAlert();
						$state.go("orderList");
					}
				});
				
			});
		}
		box.editAlert($scope,"提交","确定提交资金审批信息吗？",toSave);
	}
	//华融保存
	$scope.save = function(){
		if($scope.obj.startTime==null || $scope.obj.endTime ==null){
			box.boxAlert("查询时间不能为空");
			return false;
		}
		if($scope.obj.queryFrequency==""){
			box.boxAlert("查询频率不能为空");
			return false;
		}
		if($scope.obj.phone == null || $scope.obj.phone==""){
			box.boxAlert("短信通知不能为空");
			return false;
		}
		$http({
			method: 'POST',
			url:"/credit/risk/allocationfundaduit/v/add" ,
			data:$scope.obj
		}).success(function(data){
			box.boxAlert(data.msg,function(){
				if(data.code == "SUCCESS") {
					$scope.flag2=true;
					$scope.flag=false;
					$http({
						method: 'POST',
						url:"/credit/risk/allocationfundaduit/v/init" ,
						data:$scope.obj
					}).success(function(data){
						$scope.obj=data.data.fundCompleteDto;
						$scope.obj.startTime=$scope.obj.startTimeStr;
				    	$scope.obj.endTime=$scope.obj.endTimeStr;
				    	if($scope.obj.id>0){
							$scope.flag2=true;
						}
					});
				}
			});
			
		});
	}
	$scope.edit = function(){
		$scope.flag2=false;
		$scope.flag=true;
	}
	//出现还款计划
	$scope.submit = function(){
		$scope.rongAnhkShow=true;
		if($scope.isAgain){
			$http({
				method: 'POST',
				url:"/credit/risk/allocationfundaduit/v/selectFund" ,
				data:$scope.obj
			}).success(function(data){
				if(data.code == "SUCCESS"){
					$scope.isAgain=false;
					$scope.audit.repaymentTime=data.data.lendingTime;//应还款时间
					var borrowingDays= data.data.borrowingDays;  	
					var loanAmount=(data.data.loanAmount*10000);
					$scope.audit.psIntRate = data.data.dayRate; //执行利率
					if(!isNaN(loanAmount)){
//						var interest=(loanAmount*borrowingDays*data.data.dayRate);//应还利息（本金*借款期限*利率 ） 
						var interest=(loanAmount*borrowingDays*0.105/360);
						interest = floadNum(interest)*1;
//						interest = div(interest,100);
						var repaymentAccount = (loanAmount+interest)*1
						$scope.audit.repaymentAccount = repaymentAccount;//期供金额（本金+利息）
						$scope.audit.interest=interest;  //应还利息（本金*借款期限*利率 ） 
						$scope.audit.capital = loanAmount; // 应还本金
						$scope.audit.psRemPrcp=loanAmount; //剩余本金
					}
				}
			})
		}
	}
	function floadNum(a){
		var num=a+"";
		var index = num.indexOf('.');    
	    if(index == -1){    
	        return num;  
	    }else{    
	    	var f = parseFloat(num).toFixed(2);
	    	return f;
	    }    
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
	
	$scope.auditSubmit = function(type){
		$scope.type=type;
//		$scope.financeShow();
		$scope.toFinance();
	}
//	//选择财务
//	$scope.financeShow = function(){
//		$scope.personnelType = "收利息";
//		box.editAlert($scope,"确定选择该审批吗，请选择财务。","<submit-box></submit-box>",$scope.instructionShow);
//	}
//	//选发放指令员
//	$scope.instructionShow = function(){
//		if(!$scope.sumbitDto.uid){
//			$scope.sumbitDto.uid = $scope.financeUid;
//		}
//		if(!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid){
//			alert("请选择财务");
//			return;
//		}
//		$scope.financeUid = $scope.sumbitDto.uid;//财务
//		$scope.sumbitDto.uid = "";
//		$scope.personnelType = "发放款指令";
//		box.editAlert($scope,"请选择发放款指令员","<submit-box></submit-box>",$scope.toFinance);
//	}
	//提交给财务
	$scope.toFinance = function(){
//		if(!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid){
//			alert("请选择发放款指令员");
//			return;
//		}
//		$scope.loanDirectiveUid = $scope.sumbitDto.uid;//放款指令
		if($scope.obj.startTime==null || $scope.obj.endTime ==null){
			box.boxAlert("查询时间不能为空");
			return false;
		}
		if($scope.obj.queryFrequency==""){
			box.boxAlert("查询频率不能为空");
			return false;
		}
		if($scope.obj.phone == null || $scope.obj.phone==""){
			box.boxAlert("短信通知不能为空");
			return false;
		}
		if(!$scope.flag2){
			box.boxAlert("请先保持数据");
			return false;
		}
		$scope.audit.order="1";
		$scope.audit.sysbAmt = 0;
		$scope.audit.psFeeAmt =0;
		$scope.audit.orderNo=$scope.obj.orderNo;
		var paramt={
				 "orderNo":$scope.obj.orderNo,
				 "auditDto":$scope.audit,
				 "type":$scope.type
//				 "financeUid":$scope.financeUid,
//				 "loanDirectiveUid":$scope.loanDirectiveUid
		 }
		 box.waitAlert();
		 $http({
		 method: 'POST',
		 data:paramt,
		 url:"/credit/risk/allocationfundaduit/v/toAddFinance"
		 }).success(function(data){
			 box.closeWaitAlert();
			 box.closeAlert();
			 box.boxAlert(data.msg,function(){
				 if(data.code == "SUCCESS"){
					 box.closeAlert();
					 $state.go("orderList");
				 }else{
					 if(data.msg=="推送融安信息失败！"){
						 $state.go("orderList");
					 }
				 }
			 });
		 });
	}
	
	$scope.showBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
})