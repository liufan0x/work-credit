angular.module("anjboApp").controller("orderDetailCtrl",function($scope,$http,$state,process,$compile,route){

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
			if(route.getParams().processId == "placeOrder"){
				$scope.showDetail(route.getParams().processId);
			}else{
				var state = $state.current.name;
				if(state == "orderDetail"){
					angular.forEach($scope.orderFlowList,function(data,index){
						if($scope.orderFlowList.length == index + 1){
							if(data.backReason){
								$scope.showDetail($scope.orderFlowList[index-1].currentProcessId);
							}else{
								$scope.showDetail(data.currentProcessId);
							}
						}
				    });
				}
			}
		}
	})

	$scope.showDetail = function(processId){
		var view;
		if(processId == 'elementEntrance'){
			view = 'element-entrance-detail';
		}else{
			view = process.processIdTransformationDirective(processId);
		}		
		angular.element("#viewRow").html($compile('<'+view+'></'+view+">")($scope));
		
		$http({
			method: 'POST',
			url:'/credit/element/list/v/receiveElementInfo',
			data:params
		}).success(function(data){
			if(data.code == "SUCCESS"){
				$scope.elementFlag=data.data.state1;
				$scope.elementInfo=data.data.state2;
			}
		})

	}

});