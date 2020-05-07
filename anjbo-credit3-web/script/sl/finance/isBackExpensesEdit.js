angular.module("anjboApp").controller("isBackExpensesEditCtrl",function($scope,$http,$state,box,route){
	$scope.productId = route.getParams().cityCode+''+route.getParams().productCode;//业务产品
	$scope.harvest = new Object();
	$scope.harvest.orderNo = route.getParams().orderNo;
	$scope.isProductCode = route.getParams().productCode;;
	$scope.harvest.type=2;
	$http({
		method: 'POST',
		url:"/credit/order/lendingInterest/v/processDetails" ,
		data:$scope.harvest
	}).success(function(data){
		$scope.harvest=data.data.interest;
	})
	
	$scope.showSubmit = function(){
		var vf =/^([0-9]\d{0,12})([.]?|(\.\d{1,4})?)$/;
		if($scope.harvest.interestTime==null  || $scope.harvest.interestTime==''){
			box.boxAlert("收后置费用时间不能为空");
			return false;
		}
		var collectInterestMoney=$("#collectInterestMoney").val();
		if(collectInterestMoney==null || collectInterestMoney=='' || !vf.test(collectInterestMoney)){
			box.boxAlert("实收利息不能为空或输入有误！");
			return false;
		}
		var img=$("#img").val();
		if(img==null || img==''){
			box.boxAlert("截屏照片不能为空！");
			return false;			
		}
		var harvestSubmit = function(){
				$scope.harvest.uid=$scope.sumbitDto.uid; //出纳
				if($scope.harvest.uid==null || $scope.harvest.uid=='' ||  $scope.harvest.uid=='undefined'){
					box.boxAlert("请选择核实后置费用专员");
					return false;
				}
				var img=$("#img").val();
				$scope.harvest.interestImg=img;
			    $(".lhw-alert-ok").attr("disabled","disabled");
			    $scope.harvest.type=2;
			    $scope.harvest.orderNo = route.getParams().orderNo;
			    box.waitAlert();
				$http({
					method: 'POST',
					url:"/credit/order/lendingInterest/v/processSubmit" ,
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
		$scope.personnelType = "核实后置费用";
		box.editAlert($scope,"确定提交扣回后置费用信息吗？请选择核实后置费用专员。","<submit-box></submit-box>",harvestSubmit);
	}
   //退回
	$scope.showBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
	
})