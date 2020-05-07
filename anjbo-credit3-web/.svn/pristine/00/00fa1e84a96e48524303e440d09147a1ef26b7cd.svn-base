angular.module("anjboApp").controller("uploadInsurancePolicyEditCtrl",function($scope,$http,route,$state,box){

	$scope.obj = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;
	$scope.obj.productCode = route.getParams().productCode;
	$scope.productCode = route.getParams().productCode;
	$scope.isdisable=false;
	
	$scope.submit = function(){
		$scope.obj.orderNo = route.getParams().orderNo;
		var img=$("#img").val();
		$scope.obj.uploadInsurancePdf=img;
		if(img==null || img ==''){
			box.boxAlert("电子保单不能为空");
			return false;
		}
		var uploadInsuranceSubmit = function(){
			$(".lhw-alert-ok").attr("disabled","disabled");
			box.waitAlert();
			$http({
				method: 'POST',
				url:"/credit/order/uploadInsurance/v/processSubmit" ,
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
		box.editAlert($scope,"提交","确定提交上传电子保单吗？",uploadInsuranceSubmit);
	}

});