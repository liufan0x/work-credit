angular.module("anjboApp").controller("capitalDetailCtrl",function($scope,$http,$state,$compile,box,process,route){

	$scope.flowNo=route.getParams().flowNo;
	$scope.obj =  new Object();
	
	$http({
		method: 'POST',
		url:"/credit/finance/capital/v/queryDetails" ,
		data:{"flowNo":$scope.flowNo}
	}).success(function(data){
		console.log(data);
		$scope.obj=data.rows;
		if($scope.obj!=null && $scope.obj.length > 0){
			$scope.obj.flowNo = $scope.obj[0].flowNo;
			$scope.obj.name = $scope.obj[0].name;
			$scope.obj.idCard = $scope.obj[0].idCard;
			$scope.obj.phone = $scope.obj[0].phone;
			$scope.obj.bankName = $scope.obj[0].bankName;
			$scope.obj.bankCard = $scope.obj[0].bankCard;
		}
	})
	
});