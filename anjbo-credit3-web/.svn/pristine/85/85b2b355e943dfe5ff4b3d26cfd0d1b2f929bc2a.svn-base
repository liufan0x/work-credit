angular.module("anjboApp", ['bsTable']).controller("liftingPlacingCtrl", function($scope, $http, $state, $filter, box, process, parent) {
	$scope.platformDto = new Object();
	$scope.platformDto.idCardType="CER";
	$scope.showSave = function(){
		$scope.isAudit = false;
		var count = $("#count").text();
		if (count == '0' || count == 0) {
			box.boxAlert('文件不能为空');
			return;
		}
		$scope.platformDto.insuranceFile= $("#huarongImg").val();
		$("#huarongImg").val("");
		$("#dzDel").hide();
		 $http({
				method: 'POST',
				url: "/credit/third/api/platform/v/getInsertFile",
				data: $scope.platformDto
			}).success(function(data) {
				if(data.code == "SUCCESS") {
					box.boxAlert('操作成功');
					$("#count").text("0");
					$("#customerName").val("");
					$("#idCardNumber").val("");
					$("#idCardType").val("");
				     }else{
				    		box.boxAlert(data.msg);
				     }
			})

	    }
	
});