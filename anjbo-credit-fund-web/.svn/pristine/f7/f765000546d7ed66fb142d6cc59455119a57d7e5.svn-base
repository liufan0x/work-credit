
angular.module("anjboApp").controller("auditFinalEditCtrl",function($scope,route,$http,$state,box){


	$scope.obj = new Object();
	$scope.orderNo = route.getParams().orderNo;
	$scope.orderIsBack = false;
	//通过按钮需要通过查询风控模型控制显示或者隐藏
	function loadFinal(){
		$http({
			url:'/credit/risk/final/v/loadFinal',
			method:'POST',
			data:{"orderNo":$scope.orderNo}
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.obj = data.data.auditFinal;
				if(!$scope.obj||null==$scope.obj){
					$scope.obj = new Object();
				}
				$scope.auditFinalShow = data.data.auditFinalShow;
			}
		});
	}
	loadFinal();
	orderIsBack = function(){
		var param = {
			orderNo:$scope.orderNo,
			processId:'auditFinal'
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
		$scope.obj.nextHandleUid = $scope.sumbitDto.uid;//选择的分配资金方Uid
		$scope.obj.orderNo = $scope.orderNo;
		$http({
			method: 'POST',
			url:'/credit/risk/final/v/pass',
			data:$scope.obj
		}).success(function(data){
			if("SUCCESS"==data.code){
				box.closeAlert();
				$state.go('orderList');
			} else{
				alert(data.msg)
			}
		})
	}

	$scope.reportOfficer = function(){
		if(!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid){
			alert("请选择首席风险官");
			return;
		}
		$scope.obj.nextHandleUid = $scope.sumbitDto.uid;//选择的首席风险官Uid
		$scope.obj.orderNo = $scope.orderNo;
		$http({
			method: 'POST',
			url:'/credit/risk/final/v/reportOfficer',
			data:$scope.obj
		}).success(function(data){
			if("SUCCESS"==data.code){
				box.closeAlert();
				$state.go('orderList');
			} else {
				box.boxAlert(data.msg);
			}
		})
	}

	$scope.reportOfficerShow = function(){
		if(!$scope.obj.remark||null==$scope.obj.remark||$scope.obj.remark==""){
			box.boxAlert("请填写终审审批意见!");
			return;
		}
		$scope.personnelType = "首席风险官审批";
		box.editAlert($scope,"上报首席风险官，请选择首席风险官","<submit-box></submit-box>",$scope.reportOfficer);
	}

	$scope.showFinalSubmit = function(title){
		if(!$scope.obj.remark||null==$scope.obj.remark||$scope.obj.remark==""){
			box.boxAlert("请填写终审审批意见!");
			return;
		}
//		var orderBorrowScope =  angular.element('place-borrow-detail').scope();
//		if(orderBorrowScope.borrow.loanAmount>=1000){
			$scope.personnelType = "法务审批";
			box.editAlert($scope,"订单通过审批吗，请选择法务审批","<submit-box></submit-box>",$scope.pass);			
//		}else{
//			$scope.personnelType = "分配资金";
//			box.editAlert($scope,"订单通过审批吗，请选择资金分配员","<submit-box></submit-box>",$scope.pass);
//		}
	}

	//退回
	$scope.showFinalBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
});