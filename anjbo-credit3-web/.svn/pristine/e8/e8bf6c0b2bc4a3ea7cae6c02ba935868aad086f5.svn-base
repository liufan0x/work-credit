angular.module("anjboApp").controller("fddMortgageStorageEditCtrl",function($scope,$http,route,$state,box){

	$scope.obj = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;
	$scope.obj.productCode = route.getParams().productCode;
	$scope.obj.relationOrderNo = route.getParams().relationOrderNo;
	$scope.productCode = route.getParams().productCode;

	$http({
		method: 'POST',
		url:"/credit/process/fddMortgageStorage/v/detail" ,
		data:$scope.obj
	}).success(function(data){
		if(data.code == "SUCCESS"){
			$scope.obj = data.data;
			if($scope.obj!=null){
				$scope.obj.collateralTime=$scope.obj.collateralTimeStr;
			}
		}
	})
	
	$scope.submit = function(){
		if($scope.obj.housePropertyType==null || $scope.obj.housePropertyType==''){
			 box.boxAlert("产权证类型不能为空");
			 return false;
		}
		if($scope.obj.housePropertyNumber==null || $scope.obj.housePropertyNumber==''){
			 box.boxAlert("产权证号不能为空");
			 return false;
		}
		
		if($scope.obj.region==null || $scope.obj.region==''){
			 box.boxAlert("所在区域不能为空");
			 return false;
		}
		if($scope.obj.houseName==null || $scope.obj.houseName==''){
			 box.boxAlert("房产名称不能为空");
			 return false;
		}
		if($scope.obj.collateralTime==null || $scope.obj.collateralTime==''){
			 box.boxAlert("入库时间不能为空");
			 return false;
		}
		var fddMortgageStorage = function(){
			$(".lhw-alert-ok").attr("disabled","disabled");
			box.waitAlert();
			$http({
				method: 'POST',
				url:"/credit/process/fddMortgageStorage/v/add" ,
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
		box.editAlert($scope,"提交","确定提交抵押品入库信息吗？",fddMortgageStorage);
	}
});