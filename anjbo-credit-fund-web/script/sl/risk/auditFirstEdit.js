
angular.module("anjboApp").controller("auditFirstEditCtrl",function($scope,route,$rootScope,$http,$state,box){

	$scope.obj = new Object();
	$scope.orderNo = route.getParams().orderNo;
	$scope.orderIsBack = false;

	//通过按钮需要通过查询风控模型控制显示或者隐藏

	function loadFirst(){
		$http({
			url:'/credit/risk/first/v/loadFirst',
			method:'POST',
			data:{"orderNo":$scope.orderNo}
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.obj = data.data.auditFirs;
				if(!$scope.obj||null==$scope.obj){
					$scope.obj = new Object();
				}
				$scope.auditFirstShow = data.data.auditFirstShow;
				$scope.dataList = data.data.listLog;
			}
		});
	}
	loadFirst();
	orderIsBack = function(){
		var param = {
			orderNo:$scope.orderNo,
			processId:'auditFirst'
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
	//通过
	$scope.pass = function(){
		if(!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid){
			alert("请选择资金分配员");
			return;
		}
		$scope.obj.nextHandleUid = $scope.sumbitDto.uid;//资金分配员uid
		$scope.obj.orderNo = $scope.orderNo;
		$http({
			method: 'POST',
			url:'/credit/risk/first/v/pass',
			data:$scope.obj
		}).success(function(data){
			if("SUCCESS"==data.code){
				box.closeAlert();
				$state.go('orderList');
			} else{
				box.boxAlert(data.msg);
			}
		})
		
	}

	//上报终审
	$scope.reportFinal = function(){
		if(!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid){
			alert("请选择风控经理");
			return;
		}
		$scope.obj.nextHandleUid = $scope.sumbitDto.uid;//终审uid
		$scope.obj.orderNo = $scope.orderNo;
		$http({
			method: 'POST',
			url:'/credit/risk/first/v/reportFinal',
			data:$scope.obj
		}).success(function(data){
			if("SUCCESS"==data.code){
				box.closeAlert();
				$state.go('orderList');
			} else{
				box.boxAlert(data.msg);
			}
		})
	}

	//修改征信
	$scope.riskEditCredit = function(){
		$scope.isCreditEditShow = true;
		$scope.credit.createCreditLog = true;
		$scope.changeView(7);
	}


	$scope.showFirstSubmit = function(title){
		if(!$scope.obj.remark||null==$scope.obj.remark||$scope.obj.remark==""){
			box.boxAlert("请填写初审审批意见");
			return;
		}
		$scope.personnelType = "分配资金";
		box.editAlert($scope,"订单通过审批吗，请选择资金分配员","<submit-box></submit-box>",$scope.pass);
	}

	//退回
	$scope.showFirstBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}

	$scope.reportFinalShow = function(){
		if(!$scope.obj.remark||null==$scope.obj.remark||$scope.obj.remark==""){
			box.boxAlert("请填写初审审批意见");
			return;
		}
		$scope.personnelType = "风控终审";
		box.editAlert($scope,"上报终审，请选择风控经理","<submit-box></submit-box>",$scope.reportFinal);
	}

});