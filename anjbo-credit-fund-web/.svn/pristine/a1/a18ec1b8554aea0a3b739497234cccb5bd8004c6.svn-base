angular.module("anjboApp").controller("auditJusticeEditCtrl",function($scope,route,$http,$state,box){
	$scope.obj = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;
	$scope.orderIsBack = false;

	function loadOfficer(){
		$http({
			url:'/credit/risk/justice/v/detail',
			method:'POST',
			data:{"orderNo":$scope.orderNo}
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.obj = data.data;
				if(!$scope.obj||null==$scope.obj){
					$scope.obj = new Object();
				}
			}
		});
	}
	loadOfficer();

	orderIsBack = function(){
		var param = {
			orderNo:$scope.orderNo,
			processId:'auditOfficer'
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

	$scope.pass = function(){
		if(!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid){
			alert("请选择资金分配员");
			return;
		}
		$scope.obj.nextHandleUid = $scope.sumbitDto.uid;//分配资金方uid
		$scope.obj.orderNo = $scope.orderNo;
		$http({
			url:'/credit/risk/justice/v/pass',
			method:'POST',
			data:$scope.obj
		}).success(function(data){
			if("SUCCESS"==data.code){
				box.closeAlert();
				$state.go('orderList');
			} else{
				box.boxAlert(data.msg);
			}
		});
	}

	$scope.showCeoSubmit = function(){
		if(!$scope.obj.remark||null==$scope.obj.remark||$scope.obj.remark==""){
			box.boxAlert("请填写法务审批意见");
			return;
		}
		$scope.personnelType = "分配资金";
		box.editAlert($scope,"订单通过审批吗，请选择资金分配员","<submit-box></submit-box>",$scope.pass);
	}

	//退回
	$scope.showCeoBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
});