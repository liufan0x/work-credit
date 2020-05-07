angular.module("anjboApp").controller("lendingPayEditCtrl",function($scope,$http,$state,box,route){

	$scope.pay = new Object();
	$scope.pay.orderNo = route.getParams().orderNo;
	
	$http({
		method: 'POST',
		url:"/credit/finance/lendingPay/v/detail" ,
		data:$scope.pay
	}).success(function(data){
		$scope.pay=data.data;
		if($scope.pay!=null){
//			var date = new Date($scope.pay.payTime);
//			var str=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();  
//			$scope.pay.payTime=str;	
			$scope.pay.payTime='';
		}
	})
	
	$scope.submit = function(){
		if($scope.pay.payTime==null  || $scope.pay.payTime==''){
			alert("付利息时间不能为空");
			return false;
		}
		var paysubmit=function(){
			var img=$("#img").val();
			$scope.pay.payImg=img;
			$(".lhw-alert-ok").attr("disabled","disabled");
			$http({
				method: 'POST',
				url:"/credit/finance/lendingPay/v/add" ,
				data:$scope.pay
			}).success(function(data){
					alert(data.msg);
					box.closeAlert();
					if(data.code == "SUCCESS"){
						 $state.go("orderList");
					}
			})
		}
		box.editAlert($scope,"提交","确定提交付利息信息吗？",paysubmit);
	}
});