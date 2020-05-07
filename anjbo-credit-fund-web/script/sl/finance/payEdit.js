angular.module("anjboApp").controller("payEditCtrl",function($scope,$http,$state,box,route){

	$scope.obj = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;
	
	$http({
		method: 'POST',
		url:"/credit/finance/receivablePay/v/detail" ,
		data:$scope.obj
	}).success(function(data){
		$scope.obj=data.data;
//		$scope.hasDto=data.data.hasDto;
		$scope.obj.payTime="";
	})
	
	$scope.submit = function(){
		if($scope.obj.payTime==null || $scope.obj.payTime==''){
			alert("请选择时间");
			return false;
		}
		var payForSubmit = function(){
			$(".lhw-alert-ok").attr("disabled","disabled");
			var img=$("#img").val();
			$scope.obj.payImg=img;
			$http({
				method: 'POST',
				url:"/credit/finance/receivablePay/v/add" ,
				data:$scope.obj
			}).success(function(data){
				alert(data.msg);
				box.closeAlert();
				if(data.code == "SUCCESS"){
					 $state.go("orderList");
				}
			})
		}
		box.editAlert($scope,"提交","确定提交付费信息吗？",payForSubmit);
	}

});