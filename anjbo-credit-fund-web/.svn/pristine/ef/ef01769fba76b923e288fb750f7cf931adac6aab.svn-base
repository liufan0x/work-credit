angular.module("anjboApp").controller("orderDetailCtrl",function($scope,$http,$state,process,$compile,route){

	var params = {
		orderNo:route.getParams().orderNo
	}

	$http({
		method: 'POST',
		url:'/credit/report/fund/v/selectOrderFlowListRepeat',
		data:params
	}).success(function(data){
		if(data.code == "SUCCESS"){
			$scope.flowList = data.data.flowList;
			$scope.riskShow = data.data.riskShow;
			$scope.orderFlowList = new Array();
			var fundId = route.getUserDto().fundId; 
			$scope.fundId = fundId;
			if(route.getParams().processId == "placeOrder"){
				$scope.showDetail(route.getParams().processId);
			}else{
				var state = $state.current.name;
				if(state == "orderDetail"){
					angular.forEach($scope.flowList,function(data,index){
						if($scope.flowList.length == index + 1){
							if(data.backReason||data.currentProcessId=='backExpenses'||data.currentProcessId=='rebate'){
								$scope.showDetail($scope.flowList[index-1].currentProcessId);
							}else if(data.currentProcessId=='receivableForFirst'||data.currentProcessId=='receivableFor'||data.currentProcessId=='receivableForEnd'
								||data.currentProcessId=='pay'){
								$scope.showDetail('newlicense');
							}else{
								$scope.showDetail(data.currentProcessId);
							}
						}
						//华融不显示面签节点
						//隐藏节点
						if((route.getUserDto().fundId==31
								&&$scope.flowList[index].currentProcessId!='facesign'
								&&$scope.flowList[index].currentProcessId!='dataAudit'
								&&$scope.flowList[index].currentProcessId!='fundDocking'
								&&$scope.flowList[index].currentProcessId!='auditJustice'
								&&$scope.flowList[index].currentProcessId!='isLendingHarvest'
								&&$scope.flowList[index].currentProcessId!='applyLoan'
								&&$scope.flowList[index].currentProcessId!='backExpenses'
								&&$scope.flowList[index].currentProcessId!='isBackExpenses')
								||(route.getUserDto().fundId!=31
								&&$scope.flowList[index].currentProcessId!='managerAudit'
								&&$scope.flowList[index].currentProcessId!='fundDocking'
								&&$scope.flowList[index].currentProcessId!='auditJustice'
								&&$scope.flowList[index].currentProcessId!='isLendingHarvest'
								&&$scope.flowList[index].currentProcessId!='isBackExpenses'
								&&$scope.flowList[index].currentProcessId!='receivableFor'
								&&$scope.flowList[index].currentProcessId!='receivableForFirst'
								&&$scope.flowList[index].currentProcessId!='receivableForEnd'
								&&$scope.flowList[index].currentProcessId!='mortgage'
								&&$scope.flowList[index].currentProcessId!='pay'
								&&$scope.flowList[index].currentProcessId!='backExpenses')
								){ 
							if(($scope.flowList[index].currentProcessId!='auditFirst'&&$scope.flowList[index].currentProcessId!='auditFinal'
								&&$scope.flowList[index].currentProcessId!='auditOfficer')||$scope.riskShow){
								$scope.orderFlowList.push($scope.flowList[index]);
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