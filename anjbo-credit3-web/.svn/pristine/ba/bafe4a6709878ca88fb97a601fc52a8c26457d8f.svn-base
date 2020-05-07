angular.module("anjboApp").controller("signInsurancePolicyEditCtrl",function($scope,$http,route,$state,box){

	$scope.obj = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;
	$scope.obj.productCode = route.getParams().productCode;
	$scope.productCode = route.getParams().productCode;
	$scope.isdisable=false;
	
	$scope.submit = function(){
		$scope.obj.orderNo = route.getParams().orderNo;
		var img=$("#img").val();
		$scope.obj.signImg=img;
		if($scope.obj.signTime==null  || $scope.obj.signTime==''){
			box.boxAlert("签订时间不能为空");
			return false;
		}
		var signInsuranceSubmit = function(){
			$(".lhw-alert-ok").attr("disabled","disabled");
			box.waitAlert();
			$http({
				method: 'POST',
				url:"/credit/order/signInsurance/v/processSubmit" ,
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
			})
		}
		box.editAlert($scope,"提交","确定提交签订投保单吗？",signInsuranceSubmit);
	}

});