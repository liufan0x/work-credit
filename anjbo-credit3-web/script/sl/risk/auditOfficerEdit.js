angular.module("anjboApp").controller("auditOfficerEditCtrl",function($scope,route,$http,$state,box){
	$scope.obj = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;
	$scope.productCode = route.getParams().productCode;
	$scope.relationOrderNo = route.getParams().relationOrderNo;
	$scope.orderIsBack = false;
	$scope.showDetail("auditFirst");
	$scope.showDetail("auditFinal");
	$scope.loanAmont=0;
	$scope.isToBack=1;  //退回重新走流程  
	function loadOfficer(){
		$http({
			url:'/credit/order/auditOfficer/v/processDetails',
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
		if($scope.isToBack==1 && (!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid)){
			box.boxAlert("请选择推送金融机构员");
			return;
		}
		if($scope.isToBack==1 && ($scope.loanAmont>=3000 && (!$scope.obj.justiceUid || ""==$scope.obj.justiceUid))){
			box.boxAlert("请先选择法务审批员");
			return;
		}
		$(".lhw-alert-ok").attr("disabled", "disabled");
		if($scope.isToBack==1){
			$scope.obj.nextHandleUid = $scope.sumbitDto.uid;//资金分配员uid
		}
		$scope.obj.orderNo = $scope.orderNo;
		box.waitAlert();
		$http({
			url:'/credit/order/auditOfficer/v/processSubmit',
			method:'POST',
			data:$scope.obj
		}).success(function(data){
			box.closeWaitAlert();
			box.closeAlert();
			box.boxAlert(data.msg,function(){
				if(data.code == "SUCCESS") {
					box.closeAlert();
					$state.go("orderList");
				}
			});
		});
	}

	$scope.selAlloction = function(){
		
		if($scope.isToBack==1 && (!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid)){
			box.boxAlert("请选择法务审批员");
			return;
		}
		if($scope.isToBack==1){
			var justiceUid = $scope.sumbitDto.uid  //法务审批
			$scope.obj.justiceUid = justiceUid;
		}
		$scope.personnelType = "推送金融机构";
		box.editAlert($scope,"订单通过审批吗，请选择推送金融机构员","<submit-box></submit-box>",$scope.pass);
	}
	
	var params = {"orderNo": route.getParams().orderNo}
	$http({
		method: 'POST',
		url: 'credit/order/borrow/v/query',
		data: params
	}).success(function(data) {
		$scope.borrow=data.data;
		if($scope.borrow!=null){
			$scope.loanAmont = $scope.borrow.loanAmount;
		}
	});
	$scope.showCeoSubmit = function(){
		if(!$scope.obj.remark||null==$scope.obj.remark||$scope.obj.remark==""){
			box.boxAlert("请填写首席风险官审批意见");
			return;
		}
		
//		var orderBorrowScope =  angular.element('place-borrow-detail').scope();
//		$scope.loanAmont = orderBorrowScope.borrow.loanAmount;
		if($scope.cdBorrow){
			$scope.loanAmont=$scope.cdBorrow.loanAmount;
		}
		if($scope.isToBack==1 && $scope.loanAmont>=3000){
			$scope.personnelType = "法务审批";
			box.editAlert($scope,"订单通过审批吗，请选择法务审批","<submit-box></submit-box>",$scope.selAlloction);			
		}else if($scope.isToBack==1){
			$scope.personnelType = "推送金融机构";
			box.editAlert($scope,"订单通过审批吗，请选择推送金融机构员","<submit-box></submit-box>",$scope.pass);
		}else{
			box.editAlert($scope,"提交","确定提交审批信息吗？",$scope.pass);
		}
		
	}

	//退回
	$scope.showCeoBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
	
	//关闭订单
	$scope.showBack = function(){
			var closeLoan = function(){
			$(".lhw-alert-ok").attr("disabled", "disabled");
			$scope.obj.orderNo = $scope.orderNo;
			$scope.obj.state = "订单已停止";
			box.waitAlert();
			$http({
				method: 'POST',
				url: "/credit/order/base/v/closeOrder",
				data: $scope.obj
			}).success(function(data) {
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
		box.editAlert($scope,"提交","确定关闭订单吗",closeLoan);
	}
});