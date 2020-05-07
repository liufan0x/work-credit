angular.module("anjboApp", ['bsTable']).controller("orderEditCtrl",function($scope,$http,$state,$compile,box,process,route,$location){

	$scope.isEdit = true;
	
	$scope.orderNo=route.getParams().orderNo;

	if($scope.orderNo){
		var params = {
			orderNo:route.getParams().orderNo
		}
		$http({
			method: 'POST',
			url:'/credit/order/flow/v/selectOrderFlowListRepeat',
			data:params
		}).success(function(data){
			if(data.code == "SUCCESS"){
				$scope.orderFlowList = data.data;
			}
		})
	}

	//显示详情
	$scope.showDetail = function(processId){
		if(processId == "placeOrder"){
			return false;
		}
		var route = process.processIdTransformationDirective(processId);
		if(angular.element("#viewRow").find(route).html()){
			$location.hash(processId);
			return false;
		}
		angular.element("#viewRow").append($compile('<'+route+'></'+route+">")($scope));
		$location.hash(processId);
	}

	//删除详情
	$scope.deleteDetail = function(html){
		angular.element(html).remove();
	}


});