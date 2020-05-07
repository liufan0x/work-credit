angular.module("anjboApp").controller("managerAuditEditCtrl",function($scope,$http,$state,box,route){

	$scope.magager = new Object();
	$scope.magager.orderNo = route.getParams().orderNo;

	$http({
		method: 'POST',
		url:"/credit/process/managerAudit/v/detail" ,
		data:$scope.magager
	}).success(function(data){
		$scope.magager=data.data;
	})
	
	$scope.showSubmit = function(title){
		var submit = function(){
				$scope.magager.uid=$scope.sumbitDto.uid;
				if($scope.magager.uid==null || $scope.magager.uid=='' ||  $scope.magager.uid=='undefined'){
					alert("请选择风控初审人");
					return false;
				}
				$scope.magager.status=1;  //通过
				$http({
					method: 'POST',
					url:"/credit/process/managerAudit/v/add" ,
					data:$scope.magager
				}).success(function(data){
					if(data.code == "SUCCESS"){
						alert("保存成功！");
						$state.go("orderList");
					}else{
						alert("操作失败，请稍后重试！");
					}
				})
			box.closeAlert();
		}
		$scope.personnelType = "风控初审";
		box.editAlert($scope,"订单通过审批吗，请选择风控初审人","<submit-box></submit-box>",submit);
	}
   //退回
	$scope.showBack = function(){
		var back = function(){
			$(".lhw-alert-ok").attr("disabled","disabled");
			$http({
				method: 'POST',
				url:"/credit/process/managerAudit/v/updwithdraw" ,
				data:$scope.magager
			}).success(function(data){
				if(data.code == "SUCCESS"){
					alert("退回成功！");
					$state.go("orderList");
				}else{
					alert("操作失败，请稍后重试！");
				}
			})
			box.closeAlert();
		}
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}

});