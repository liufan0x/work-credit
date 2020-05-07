angular.module("anjboApp", ['bsTable']).controller("monitorArchiveDetailCtrl",function($scope,$compile,$http,$state,box,process,parent,route){

	$scope.flag2=true;
	$scope.flag=false;
	$scope.isAudit=false;
	
	
	var params = {"id": route.getParams().id}
	
	function init(){
		//详情
		$http({
			method: 'POST',
			url:'/credit/tools/monitorArchive/v/find',
			data:params
		}).success(function(data){
			$scope.archive=data.data;
		})
	}
	
	init();
	
    $http({
        method: 'POST',
        url:'/credit/data/product/v/search',
        data:{cityCode:"4403"}
    }).success(function(data){
        $scope.productdto = data.data;
    })
	
	$scope.edit = function(){
		$scope.flag2=false;
		$scope.flag=true;
	}
	
	$scope.save = function(){
		if(!$scope.archiveAddForm.$valid){
			$scope.isAudit=true;
			return;
		}
		$http({
			method: 'POST',
			url:'/credit/tools/monitorArchive/v/edit',
			data:$scope.archive
		}).success(function(data){
			if(data.code == "SUCCESS"){
				$scope.flag2=true;
				$scope.flag=false;
        		init();
        		alert("修改成功");
			}
		})
	}
});