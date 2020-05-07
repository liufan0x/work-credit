angular.module("anjboApp").controller("rebateEditCtrl",function($scope,$http,$state,box,route){

	$scope.rebate = new Object();
	$scope.rebate.orderNo = route.getParams().orderNo;
	
	$http({
		method: 'POST',
		url: "/credit/order/rebate/v/processDetails",
		data: $scope.rebate
	}).success(function(data) {
		$scope.rebate = data.data;
	})
	
	$scope.submit = function(){
		if($scope.rebate.rebateTime==null || $scope.rebate.rebateTime==''){
			box.boxAlert("请选择时间！");
			return false;
		}
		if($scope.rebate.rebateMoney==null || $scope.rebate.rebateMoney==''){
			box.boxAlert("返佣金额不能为空！");
			return false;
		}
		var payForSubmit = function(){
			$(".lhw-alert-ok").attr("disabled","disabled");
			var img=$("#img").val();
			$scope.rebate.rebateImg=img;
			box.waitAlert();
			$http({
				method: 'POST',
				url:"/credit/order/rebate/v/processSubmit" ,
				data:$scope.rebate
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
		box.editAlert($scope,"提交","确定提交返佣息信息吗？",payForSubmit);
	}

});