angular.module("anjboApp").controller("receivableForEditCtrl",function($scope,$http,$state,box,route){
	 
	$scope.obj = new Object();
	$scope.obj.huaRongDto = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;
	$scope.obj.forType=0;
	$scope.payCount=0;
	$scope.le=0;  //回款列表初始长度
	$scope.obj.forName=$state.current.name;
	$scope.obj.isShow=false;   //显示首次
	$scope.rongAnShow = false; //显示推送页面
	$scope.fundCode =false; //判断是否推送华融信息
	$scope.isFirstFor=0;
	
	$http({
		method: 'POST',
		url:"/credit/finance/receivableFor/v/init" ,
		data:$scope.obj
	}).success(function(data){
		$scope.dayRate=data.data.dayRate;
		$scope.overdueRate=data.data.overdueRate;
		$scope.fundCode=data.data.fundCode;
		$scope.borrowingDays=data.data.borrowingDays;
		$scope.obj=data.data.lendingDto;
		$scope.obj.forList=data.data.forList;
		$scope.le = $scope.obj.forList.length;  //判断首期尾期用
		$scope.hasDto=data.data.receivableHasDto;
		if($scope.hasDto!=null){
			if($scope.obj.productCode!='02'){
				$scope.obj.remark = $scope.hasDto.remark;
			}
		}
		if($scope.obj.forList.length == 0 || $scope.obj.forList==null){
		   $scope.obj.forList = new Array();
		   $scope.payForList={"payMentAmountDate":null,"payMentAmount":null,"payMentPic":null};
		   $scope.obj.forList.push($scope.payForList);
		   $scope.obj.remark="";
		   $scope.obj.isShow=true;
		}else{
			$scope.obj.forType=1;  //forList不为null 则尾期
		}
	})
	
	$scope.add = function(index){
		 $scope.payForList={"payMentAmountDate":null,"payMentAmount":null,"payMentPic":null};
		 $scope.obj.forList.push(new Object());
	}
	$scope.del = function(index){
		 $scope.obj.forList.splice(index,1);
	}
	$scope.addPay = function(index){
		 var payCount=0;
		 for(var i=0;i<$scope.obj.forList.length;i++){
			 var payMentAmount=$scope.obj.forList[i].payMentAmount*1;
			 payCount=(payCount+payMentAmount);
		 }
		 $scope.payCount=payCount;
	}
	
	//一次回款
	$scope.submit = function(){
		var lists=new Array();
		lists=$scope.obj.forList[0];
		if(lists.payMentAmountDate==null || lists.payMentAmountDate==''){
			alert("实际回款时间不能为空");
			return false;
		}
		if(lists.payMentAmount!=$scope.obj.loanAmount){
			alert("回款金额与放款金额不一致");
			return false;
		}
		if($scope.obj.refund==1){
			if($scope.obj.penaltyPayable==null ||$scope.obj.penaltyPayable==''){
				alert("退费金额不能为空");
				return false;
			}
		}
		$scope.isFirstFor=1; 
		if($scope.fundCode){  //是否显示推送融安 (畅贷不推送)
			$scope.rongAnShow = true; 
			$scope.forShow = false;	
			$scope.obj.huaRongDto = new Object();
			$scope.obj.huaRongDto.repaymentYestime=lists.payMentAmountDate;  //回款时间
			$scope.obj.huaRongDto.repaymentTime=$scope.obj.customerPaymentTimeStr;
			rongAnVal();
		}else{
			$scope.forSubmit(0); 
		}
	}
	
	//多次回款
	$scope.submit2 = function(){
		var lists=new Array();
		if($scope.fundCode){
			$scope.obj.huaRongDto = new Object();
		}
		var jy=false;
		for ( var i = $scope.le; i < $scope.obj.forList.length; i++) {
			if(i >= $scope.le){
				lists.push($scope.obj.forList[i]);
				if($scope.obj.forList[i].payMentAmountDate==null || $scope.obj.forList[i].payMentAmountDate==''){
					alert("回款时间不能为空");
					return false;
				}
				if($scope.obj.forList[i].payMentAmount==null || $scope.obj.forList[i].payMentAmount==''){
					alert("回款金额不能为空");
					return false;
				}
				if($scope.fundCode){
				 $scope.obj.huaRongDto.repaymentYestime=$scope.obj.forList[i].payMentAmountDate;  //回款时间
				}
			}
		}
		if($scope.obj.refund==1){
			if($scope.obj.penaltyPayable==null ||$scope.obj.penaltyPayable==''){
				alert("退费金额不能为空");
				return false;
			}
		}
		$scope.obj.newForList=lists;
		$scope.isFirstFor=2; 
		if($scope.fundCode && $scope.le==0){  //是否显示推送融安（首期推送）
			$scope.rongAnShow = true; 
			$scope.forShow = false;	
			$scope.obj.huaRongDto.repaymentTime=$scope.obj.customerPaymentTimeStr;
			rongAnVal();
		}else{
			$scope.forSubmit(0); 
		}
	}
	
	//初始化融安数据
	function rongAnVal(){
		var loanAmount=($scope.obj.loanAmount*10000);
		if(!isNaN(loanAmount)){
			$scope.obj.huaRongDto.repaymentAccount=loanAmount;
			//$scope.obj.huaRongDto.repaymentYesaccount=loanAmount;
			$scope.obj.huaRongDto.capital=loanAmount;
			$scope.obj.huaRongDto.setlCapital=loanAmount;
			//计算逾期天数Start---------
			var days=0;
			var newTime=$scope.obj.huaRongDto.repaymentYestime;
			var oldTime=$scope.obj.huaRongDto.repaymentTime;
			if(typeof(newTime)!= 'undefined' && typeof(oldTime)!= 'undefined'){
				newTime=newTime.replace(/-/g,"/");
				oldTime=oldTime.replace(/-/g,"/");
				var startdate=new Date(oldTime);
				var enddate=new Date(newTime);
				var time=enddate.getTime()-startdate.getTime();
				days=parseInt(time/(1000 * 60 * 60 * 24));
				if(days<0){
					days=0;
				}
			}
			//计算逾期天数end-----------
			$scope.obj.huaRongDto.lateDays=days;   //逾期天数
//			var lateInterest=(days*loanAmount*$scope.overdueRate);//逾期利息（逾期天数*本金（借款金额）*利率）
			var lateInterest=(days*loanAmount*0.105/360);
			lateInterest=floadNum(lateInterest);
//			lateInterest = div(lateInterest,100);
			$scope.obj.huaRongDto.lateInterest=lateInterest;  
//			var interest=(loanAmount*$scope.borrowingDays*$scope.dayRate);//应还利息（本金*借款期限*利率 ） 
			var interest=(loanAmount*$scope.borrowingDays*0.105/360);
			interest = floadNum(interest);
//			interest = div(interest,100);
			$scope.obj.huaRongDto.interest=interest;  
			$scope.obj.huaRongDto.setlInterest=interest;  //已还利息 - 应还利息
			$scope.obj.huaRongDto.setlLateInterest=lateInterest;  //已还罚息 -逾期利息
			$scope.obj.huaRongDto.psIntRate=0.0292;//$scope.dayRate;   //贷款执行利率
			$scope.obj.huaRongDto.repaymentYesaccount=(loanAmount*1+lateInterest*1+interest*1);	//实还金额
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
	
	$scope.forSubmit = function(isRongAn){
		   var firstForSubmit = function(){
				    if($scope.hktype==2 || ($scope.hktype==3 && $scope.obj.refund==1)){
				    	 $scope.obj.createUid=$scope.sumbitDto.uid; //出纳
						if($scope.obj.createUid==null || $scope.obj.createUid=='' ||  $scope.obj.createUid=='undefined'){
							alert("请选择付费专员");
							return false;
						}
				    }
				    $scope.obj.isRongAn=isRongAn;
					$(".lhw-alert-ok").attr("disabled","disabled");
					if($scope.isFirstFor==1){//一次回款
						$http({
							method: 'POST',
							url:"/credit/finance/receivableFor/v/add" ,
							data:$scope.obj
						}).success(function(data){
							alert(data.msg);
							box.closeAlert();
							if(data.code == "SUCCESS"){
								 $state.go("orderList");
							}
							if(data.msg=='推送融安信息失败！'){
								 $state.go("orderList");
							}
						})
					}else if($scope.isFirstFor==2){ //多次回款
						$http({
							method: 'POST',
							url:"/credit/finance/receivableFor/v/addToMany" ,
							data:$scope.obj
						}).success(function(data){
							 alert(data.msg);
							 box.closeAlert();
							 if(data.code == "SUCCESS"){
								 $state.go("orderList");
							}
							if(data.msg=='推送融安信息失败！'){
								 $state.go("orderList");
							}
						})
					}
		}
		if($scope.obj.productCode == "01" ){ //交易类订单
			if($scope.le==0 && $scope.obj.oneTimePay!=1){  //首期
				box.editAlert($scope,"提交","确定提交回款信息吗？",firstForSubmit);
			}else{//尾期
				$scope.hktype=$("#hktype").val(); 
				if($scope.hktype==1){ //正常
					box.editAlert($scope,"提交","确定提交回款信息吗？",firstForSubmit);
				}else if($scope.hktype==2){ //逾期
					$scope.personnelType = "付费";
					box.editAlert($scope,"确定提交回款信息吗？请选择付费专员。","<submit-box></submit-box>",firstForSubmit);
				}else if($scope.hktype == 3){ //提前
					if($scope.obj.refund==1){ //是否退费
						$scope.personnelType = "付费";
						box.editAlert($scope,"确定提交回款信息吗？请选择付费专员。","<submit-box></submit-box>",firstForSubmit);
					}else{ //不退费
						box.editAlert($scope,"提交","确定提交回款信息吗？",firstForSubmit);
					}
				}else{
					box.editAlert($scope,"提交","确定提交回款信息吗？",firstForSubmit);
				}
			}
		}else if($scope.obj.productCode == "02" || $scope.obj.productCode == "03"){ //非交易类/畅贷
			$scope.hktype=$("#hktype").val(); 
			if($scope.hktype==1){ //正常
				box.editAlert($scope,"提交","确定提交回款信息吗？",firstForSubmit);
			}else if($scope.hktype==2){ //逾期
				$scope.personnelType = "付费";
				box.editAlert($scope,"确定提交回款信息吗？请选择付费专员。","<submit-box></submit-box>",firstForSubmit);
			}else if($scope.hktype == 3){ //提前
				if($scope.obj.refund==1){ //是否退费
					$scope.personnelType = "付费";
					box.editAlert($scope,"确定提交回款信息吗？请选择付费专员。","<submit-box></submit-box>",firstForSubmit);
				}else{ //不退费
					box.editAlert($scope,"提交","确定提交回款信息吗？",firstForSubmit);
				}
			}
		}
	}
	
	$scope.$watch("obj.huaRongDto.repaymentTime",function(newValue, oldValue){
		if(newValue!='undefined'){
			rongAnVal();
		}
	});
	$scope.$watch("obj.huaRongDto.repaymentYestime",function(newValue, oldValue){
		if(newValue!='undefined'){
			rongAnVal();
		}
	});
	
});