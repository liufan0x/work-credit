angular.module("anjboApp").controller("repaymentMemberEditCtrl",function($scope,$http,route,$state,box){

	$scope.rep = new Object();
	$scope.rep.orderNo = route.getParams().orderNo;
	
	$scope.cityCode = route.getParams().cityCode;

	$scope.submit = function(){
		if($scope.rep.foreclosureMemberUid==null || $scope.rep.foreclosureMemberUid==''|| $scope.rep.foreclosureMemberUid=='null'){
			alert("请选择还款专员");
			return false;
		}
		var repaymentSubmit = function(){
			$(".lhw-alert-ok").attr("disabled","disabled");
			$http({
				method: 'POST',
				url:"/credit/process/distributionMember/v/add" ,
				data:$scope.rep
			}).success(function(data){
				alert(data.msg);
				 box.closeAlert();
				if(data.code == "SUCCESS"){
					 $state.go("orderList");
				}
			})
		}
		box.editAlert($scope,"提交","确定提交指派还款专员信息吗？",repaymentSubmit);
	}

});