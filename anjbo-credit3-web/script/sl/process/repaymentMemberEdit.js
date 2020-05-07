angular.module("anjboApp").controller("repaymentMemberEditCtrl",function($scope,$http,route,$state,box){

	$scope.rep = new Object();
	$scope.rep.orderNo = route.getParams().orderNo;
	
	$scope.cityCode = route.getParams().cityCode;

	$scope.submit = function(){
		if($scope.rep.foreclosureMemberUid==null || $scope.rep.foreclosureMemberUid==''|| $scope.rep.foreclosureMemberUid=='null'){
			box.boxAlert("请选择还款专员");
			return false;
		}
		var repaymentSubmit = function(){
			$(".lhw-alert-ok").attr("disabled","disabled");
			box.waitAlert();
			$http({
				method: 'POST',
				url:"/credit/order/distributionMember/v/processSubmit" ,
				data:$scope.rep
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
		}
		box.editAlert($scope,"提交","确定提交指派还款专员信息吗？",repaymentSubmit);
	}
	$scope.orderIsBack = false;
	//退回
	$scope.backToSubmit = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
	orderIsBack = function(){
		var param = {
			orderNo:$scope.orderNo,
			processId:'repaymentMember'
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

});