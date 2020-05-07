angular.module("anjboApp").controller("managerAuditEditCtrl",function($scope,$http,$state,box,route){

	$scope.magager = new Object();
	$scope.magager.orderNo = route.getParams().orderNo;
	$scope.isToBack=1;  //退回重新走流程  
	
	$http({
		method: 'POST',
		url:"/credit/order/auditManager/v/processDetails" ,
		data:$scope.magager
	}).success(function(data){
		$scope.magager=data.data;
		$http({
			method: 'POST',
			url:"/credit/order/flow/v/selectEndOrderFlow" ,
			data:{"orderNo":route.getParams().orderNo}
		}).success(function(data){
			$scope.isFlowBack=data.data;
			if($scope.isFlowBack!=null){
				$scope.isToBack=$scope.isFlowBack.isNewWalkProcess;
				if(typeof($scope.isToBack)=='undefined'){
					$scope.isToBack=1;
				}
			}
		})
	})
	
	
	$scope.showSubmit = function(title){
		var submit = function(){
				if($scope.isToBack==1){
					$scope.magager.nextHandleUid=$scope.sumbitDto.uid;
					if($scope.magager.nextHandleUid==null || $scope.magager.nextHandleUid=='' ||  $scope.magager.nextHandleUid=='undefined'){
						box.boxAlert("请选择风控初审人");
						return false;
					}
				}
				$(".lhw-alert-ok").attr("disabled", "disabled");
				$scope.magager.status=1;  //通过
				box.waitAlert();
				$http({
					method: 'POST',
					url:"/credit/order/auditManager/v/processSubmit" ,
					data:$scope.magager
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
			box.closeAlert();
		}
		if($scope.isToBack==1){
			$scope.personnelType = "风控初审";
			box.editAlert($scope,"订单通过审批吗，请选择风控初审人","<submit-box></submit-box>",submit);
		}else{
			box.editAlert($scope,"提交","确定通过审批信息吗？",submit);
		}
		
	}
   //退回
	$scope.showBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}

});