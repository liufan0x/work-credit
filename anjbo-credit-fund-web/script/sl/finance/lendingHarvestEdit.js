angular.module("anjboApp").controller("lendingHarvestEditCtrl",function($scope,$http,$state,box,route){

	$scope.harvest = new Object();
	$scope.harvest.orderNo = route.getParams().orderNo;

	$scope.showSubmit = function(){
		
		if($scope.harvest.interestTime==null  || $scope.harvest.interestTime==''){
			alert("收利息时间不能为空");
			return false;
		}
		var collectInterestMoney=$("#collectInterestMoney").val();
		if(collectInterestMoney==null || collectInterestMoney==''){
			alert("实收利息不能为空");
			return false;
		}
		var returnMoney = $("#returnMoney").val();
		if(returnMoney==null  || returnMoney==''){
			alert("返佣金额不能为空");
			return false;
		}
		var receivableInterestMoney = $("#receivableInterestMoney").val();
		if(receivableInterestMoney==null  || receivableInterestMoney==''){
			alert("应收利息不能为空");
			return false;
		}
		var payInterestMoney = $("#payInterestMoney").val();
		if(payInterestMoney==null  || payInterestMoney==''){
			alert("应付利息不能为空");
			return false;
		}
		var harvestSubmit = function(){
				$scope.harvest.uid=$scope.sumbitDto.uid; //出纳
				if($scope.harvest.uid==null || $scope.harvest.uid=='' ||  $scope.harvest.uid=='undefined'){
					alert("请选择出纳");
					return false;
				}
				var img=$("#img").val();
				$scope.harvest.interestImg=img;
			    $(".lhw-alert-ok").attr("disabled","disabled");
				$http({
					method: 'POST',
					url:"/credit/finance/lendingHarvest/v/add" ,
					data:$scope.harvest
				}).success(function(data){
					 alert(data.msg);
					 box.closeAlert();
					 if(data.code == "SUCCESS"){
						 $state.go("orderList");
					}
				})
		}
		$scope.personnelType = "付利息";
		box.editAlert($scope,"确定提交收利息信息吗？请选择出纳。","<submit-box></submit-box>",harvestSubmit);
	}
   //退回
	$scope.showBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
})