angular.module("anjboApp").controller("dataAuditEditCtrl",function($scope,route,$rootScope,$http,$state,box){
	
	$scope.dataAuidt = new Object();
	$scope.dataAuidt.orderNo = route.getParams().orderNo;
	
	$scope.showDetail("auditFirst");
	$http({
		method: 'POST',
		url:"/credit/order/flow/v/selectOrderFlowList" ,
		data:$scope.dataAuidt
	}).success(function(data){
		 if(null!=data.data){
			 var flowList=data.data;
			 for(var i=0;i<flowList.length;i++){
				  if("auditFinal" == flowList[i].currentProcessId){
					  $scope.showDetail("auditFinal");
				  }
				  if("auditOfficer" == flowList[i].currentProcessId){
					  $scope.showDetail("auditOfficer");
				  }
			  }
		 }
	})
	
	$scope.orderIsBack = false;
	//退回
	$scope.backToSubmit = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
	orderIsBack = function(){
		var param = {
			orderNo:$scope.orderNo,
			processId:'dataAudit'
		}
		$http({
			url:'/credit/risk/base/v/orderIsBack',
			data:param,
			method:'POST'
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.orderIsBack = data.data;
			}
		});
	}
	
	orderIsBack();
	$scope.showSubmit = function(title){
		var submit = function(){
				$(".lhw-alert-ok").attr("disabled", "disabled");
				box.waitAlert();
				$http({
					method: 'POST',
					url:"/credit/order/auditDataAudit/v/processSubmit" ,
					data:$scope.dataAuidt
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
		box.editAlert($scope,"提交","确定提交资料审核信息吗？",submit);
	}
	
	
	//退回
	$scope.showBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
	
});