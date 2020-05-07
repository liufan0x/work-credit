angular.module("anjboApp").controller("payEditCtrl",function($scope,$http,$state,box,route,$filter){

	$scope.obj = new Object();
	$scope.paramObj = new Object();
	$scope.paramObj.orderNo=route.getParams().orderNo;
	$scope.obj.orderNo = route.getParams().orderNo;
	$scope.isProductCode  =route.getParams().productCode;
	$scope.relationOrderNo =route.getParams().relationOrderNo;
	
	$http({
		method: 'POST',
		url:"/credit/finance/receivablePay/v/detail" ,
		data:$scope.obj
	}).success(function(data){
		$scope.obj=data.data;
		$scope.obj.penaltyPayable=null;
		$scope.obj.rebateMoney=null;
//		$scope.hasDto=data.data.hasDto;
		$scope.obj.payTime="";
		if ($scope.obj.rebateTime != null&&$scope.obj.rebateTime!='') {
			$scope.obj.rebateTime = $filter('date')($scope.obj.rebateTime, "yyyy-MM-dd");
		}
	})

	$http({
		method: 'POST',
		url:"/credit/order/borrow/v/query" ,
		data:$scope.obj
	}).success(function(data){
		$scope.overdueRate=data.data.overdueRate;
	})	

	$http({
		method: 'POST',
		url:"/credit/order/lending/v/processDetails" ,
		data:$scope.obj
	}).success(function(result){
		$scope.lendingTime=new Date(result.data.lendingTime);
		$http({
			method: 'POST',
			url:"/credit/finance/receivableFor/v/detail" ,
			data:$scope.obj
		}).success(function(result){
			$scope.payMentAmountDate=new Date(result.data[result.data.length-1].payMentAmountDate);
			$http({
				method: 'POST',
				url:"/credit/order/base/v/queryOrderBaseList" ,
				data:$scope.obj
			}).success(function(result){
				$scope.borrowingAmount=result.data[0].borrowingAmount;
				$scope.overdueDay= parseInt(($scope.payMentAmountDate-$scope.lendingTime)/ (1000 * 60 * 60 * 24))-result.data[0].borrowingDay*1+2;
				$scope.obj.penaltyReal=($scope.overdueDay*$scope.overdueRate*$scope.borrowingAmount*100).toFixed(2);
			})
		})
	})


	$scope.submit = function(){
		if(!isNotEmpty($scope.overdueRate)){
			box.boxAlert("请填写逾期费率");
			return false;
		}
		if(!isNotEmpty($scope.overdueDay)){
			box.boxAlert("请填写逾期天数");
			return false;
		}
		if($scope.obj.payTime==null || $scope.obj.payTime==''){
			box.boxAlert("请选择收罚息时间");
			return false;
		}
		if(!isNotEmpty($scope.obj.penaltyPayable)){
			box.boxAlert("请填写实收罚息");
			return false;
		}

		if(!isNotEmpty($scope.obj.penaltyReal)||isNaN($scope.obj.penaltyReal)){
			box.boxAlert("请填写应收罚息");
			return false;
		}
		if(!isNotEmpty($scope.obj.rebateMoney)){
			box.boxAlert("请填写返佣金额");
			return false;
		}
		if(typeof($scope.obj.rebateMoney)!='undefined'&&$scope.obj.rebateMoney<0){
			box.boxAlert("返佣金额不正确");
			return false;
		}
		
		//畅贷关联订单
		if($scope.isProductCode && $scope.isProductCode=="03" && $scope.relationOrderNo && $scope.relationOrderNo!="0"){
			$http({
				method: 'POST',
				url:"/credit/order/lendingHarvest/v/processDetails" ,
				data:$scope.obj
			}).success(function(data){
				$scope.harvest=data.data;
				var returnMoney=$scope.harvest.harvest.returnMoney;
				var isNextUser=false;
				if(returnMoney && returnMoney!=0 && returnMoney!=''){
					isNextUser=true;	
				}else{
					if($scope.obj.rebateMoney && $scope.obj.rebateMoney != 0 && $scope.obj.rebateMoney!=''){
						isNextUser=true;
					}
				}
				var payForSubmit = function(){
					if(isNextUser){
						$scope.obj.nextHandleUid=$scope.sumbitDto.uid; //出纳
						if($scope.obj.nextHandleUid==null || $scope.obj.nextHandleUid=='' ||  $scope.obj.nextHandleUid=='undefined'){
							alert("请选择返佣专员");
							return false;
						}
					}
					$(".lhw-alert-ok").attr("disabled","disabled");
					var img=$("#img").val();
					$scope.obj.payImg=img;
					if($scope.rebateSwitch=='1'){
						$scope.obj.rebateTime=null;
						$scope.obj.rebateMoney=null;
						$scope.obj.rebateImg=null;
					}
					$scope.obj.relationOrderNo =$scope.relationOrderNo;
					box.waitAlert();
					$http({
						method: 'POST',
						url:"/credit/order/receivablePay/v/processSubmit" ,
						data:$scope.obj
					}).success(function(data){
						box.closeWaitAlert();
						box.closeAlert();
						box.boxAlert(data.msg,function(){
							if(data.code == "SUCCESS") {
								box.closeAlert();
								$state.go("orderList");
							}
						});
					});
				}
				if(isNextUser){  //选择下一个处理人  返佣
					$scope.personnelType = "返佣";
					box.editAlert($scope,"确定提交收罚息信息吗？请选择返佣专员。","<submit-box></submit-box>",payForSubmit);
				}else{
					box.editAlert($scope,"提交","确定提交收罚息信息吗？",payForSubmit);
				}
			});
		}else{
			var payForSubmit = function(){
				$(".lhw-alert-ok").attr("disabled","disabled");
				var img=$("#img").val();
				$scope.obj.payImg=img;
				if($scope.rebateSwitch=='1'){
					$scope.obj.rebateTime=null;
					$scope.obj.rebateMoney=null;
					$scope.obj.rebateImg=null;
				}
				$scope.obj.relationOrderNo =$scope.relationOrderNo;
				box.waitAlert();
				$http({
					method: 'POST',
					url:"/credit/order/receivablePay/v/processSubmit" ,
					data:$scope.obj
				}).success(function(data){
					box.closeWaitAlert();
					box.closeAlert();
					box.boxAlert(data.msg,function(){
						if(data.code == "SUCCESS") {
							box.closeAlert();
							$state.go("orderList");
						}
					});
				});
			}
			box.editAlert($scope,"提交","确定提交收罚息信息吗？",payForSubmit);
		}
		
	}
	$scope.rebateFun = function(){
		$scope.isShowRebate=!$scope.isShowRebate;
	}
	//逾期费率
	$scope.$watch("overdueRate",function(newValue, oldValue){
		if(typeof(newValue)!='undefined'){
			$scope.obj.penaltyReal=($scope.overdueDay*$scope.overdueRate*$scope.borrowingAmount*100).toFixed(2);
		}
	});
	//逾期费率
	$scope.$watch("overdueDay",function(newValue, oldValue){
		if(typeof(newValue)!='undefined'){
			$scope.obj.penaltyReal=($scope.overdueDay*$scope.overdueRate*$scope.borrowingAmount*100).toFixed(2);
		}
	});
	//应收罚息
	$scope.$watch("obj.penaltyReal",function(newValue, oldValue){
		if(isNotEmpty(newValue) && isNotEmpty($scope.obj.penaltyPayable)){
			$scope.obj.rebateMoney=$scope.obj.penaltyPayable-$scope.obj.penaltyReal;
		}
	});
	//实收罚息
	$scope.$watch("obj.penaltyPayable",function(newValue, oldValue){
		if(isNotEmpty(newValue) && isNotEmpty($scope.obj.penaltyReal)){
			$scope.obj.rebateMoney=$scope.obj.penaltyPayable-$scope.obj.penaltyReal;
		}
   });
   function isNotEmpty(obj){
		if(obj!=null&&typeof(obj)!='undefined'&&obj!==''){
			return true;
		}
		return false;
   }

});