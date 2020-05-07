angular.module("anjboApp").controller("lendingEditCtrl", function($scope, $http, $state, box, route) {

	$scope.obj = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;

	$http({
		method: 'POST',
		url: "/credit/finance/lending/v/detail",
		data: $scope.obj
	}).success(function(data) {
		$scope.obj = data.data;
		//		$scope.obj.lendingTime=$scope.obj.lendingTimeStr;
		$scope.obj.lendingTime = "";
	})
	
	$scope.submit = function() {
		if($scope.obj.lendingTime == null || 　$scope.obj.lendingTime　 == '') {
			alert("放款时间不能为空");
			return false;
		}
		box.boxAlert("提交放款信息后<span style=\"color:red;\">不能执行撤回操作</span></br>请确认您的放款时间是否输入有误！",function(){
			$scope.personnelType = "回款";
			box.editAlert($scope,"确定提交放款信息吗？请选择回款专员。","<submit-box></submit-box>",lendingSubmit);
			
		});
		var lendingSubmit = function() {
			$scope.obj.receivableForUid=$scope.sumbitDto.uid; //出纳
			if($scope.obj.receivableForUid==null || $scope.obj.receivableForUid=='' ||  $scope.obj.receivableForUid=='undefined'){
				alert("请选择回款");
				return false;
			}
			var img = $("#img").val();
			$scope.obj.lendingImg = img;
			$(".lhw-alert-ok").attr("disabled", "disabled");
			$http({
				method: 'POST',
				url: "/credit/finance/lending/v/add",
				data: $scope.obj
			}).success(function(data) {
				alert(data.msg);
				box.closeAlert();
				if(data.code == "SUCCESS") {
					$state.go("orderList");
				}
			})
		}
		
	}

});