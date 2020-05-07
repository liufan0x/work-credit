angular.module("anjboApp", ['bsTable']).controller("monitorArchiveDetailCtrl",function($scope,$compile,$http,$state,box,process,parent,route){

	$scope.flag2=true;
	$scope.flag=false;
	$scope.isAudit=false;
	
	var id = route.getParams().id;
	
	var params = {
			"id": id
		}
	
	//详情
	$http({
		method: 'POST',
		url:'/credit/risk/monitor/v/detail',
		data:params
	}).success(function(data){
		$scope.archive=data.data.archive;
		$scope.productdto = data.data.productdto;
		$scope.message=data.data.message;
		$scope.archive.startTime=$scope.archive.startTimeStr;
    	$scope.archive.endTime=$scope.archive.endTimeStr;
	})
	
    $http({
        method: 'POST',
        url:'/credit/risk/monitor/v/init'
    }).success(function(data){
        $scope.productdto = data.data.productdto;
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
			url:'/credit/risk/monitor/v/updateMonitorArchive',
			data:$scope.archive
		}).success(function(data){
			if(data.code == "SUCCESS"){
				$scope.flag2=true;
				$scope.flag=false;
        		alert("修改成功");
        		//详情
        		$http({
        			method: 'POST',
        			url:'/credit/risk/monitor/v/detail',
        			data:params
        		}).success(function(data){
        			$scope.archive=data.data.archive;
        			$scope.productdto = data.data.productdto;
        			$scope.message=data.data.message;
        			$scope.archive.startTime=$scope.archive.startTimeStr;
        	    	$scope.archive.endTime=$scope.archive.endTimeStr;
        		})
			}
		})
	}
});