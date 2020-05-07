angular.module("anjboApp").controller("auditReviewEditCtrl",function($scope,route,$http,$state,box){
	$scope.obj = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;
	$scope.productCode = route.getParams().productCode;
	$scope.relationOrderNo = route.getParams().relationOrderNo;
	$scope.orderIsBack = false;
	$scope.showDetail("auditFirst");
	if($scope.productCode!='06'&&$scope.productCode!='07'){
		$scope.showDetail("auditFinal");
	}
	$scope.isToBack=1;  //退回重新走流程  
	$scope.type=2; //推送
	function loadReview(){
		$http({
			url:'/credit/risk/review/v/init',
			method:'POST',
			data:{"orderNo":route.getParams().orderNo}
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.obj = data.data;
				$scope.type = $scope.obj.type;
				$scope.rate=$scope.obj.rate;
				$scope.overdueRate = $scope.obj.overdueRate;
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
	loadReview();

	$scope.pass = function(){
		if(($scope.productCode=='06'||$scope.productCode=='07')&&$scope.isToBack==1 && (!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid)){
			box.boxAlert("请选择推送金融机构员");
			return;
		}
		$(".lhw-alert-ok").attr("disabled", "disabled");
		if($scope.isToBack==1&&($scope.productCode=='06'||$scope.productCode=='07')){
			$scope.obj.nextHandleUid = $scope.sumbitDto.uid;//资金分配员uid
		}
		$scope.obj.orderNo = $scope.orderNo;
		box.waitAlert();
		$http({
			url:'/credit/order/auditReview/v/processSubmit',
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

	$scope.reportOfficer = function(){
		$(".lhw-alert-ok").attr("disabled", "disabled");
		$scope.obj.orderNo = $scope.orderNo;
		box.waitAlert();
		$http({
			url:'/credit/risk/review/v/reportOfficer',
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
	
	$scope.showReviewSubmit = function(){
		if(!$scope.obj.rate||null==$scope.obj.rate||$scope.obj.rate==""){
			box.boxAlert("请填写建议费率");
			return;
		}
//		if($scope.obj.rate<$scope.rate){
//			box.boxAlert("建议费率不能低于初始费率");
//			return;
//		}
//		if(!$scope.obj.overdueRate||null==$scope.obj.overdueRate||$scope.obj.overdueRate==""){
//			box.boxAlert("请填写建议逾期费率");
//			return;
//		}
//		if($scope.obj.overdueRate<$scope.overdueRate){
//			box.boxAlert("建议逾期费率不能低于初始建议逾期费率");
//			return;
//		}
//		if(!$scope.obj.remark||null==$scope.obj.remark||$scope.obj.remark==""){
//			box.boxAlert("请填写复核审批意见");
//			return;
//		}
		 
		if($scope.type==1 &&$scope.isToBack==1){
			box.editAlert($scope,"提交","确定提交审批信息吗？",$scope.reportOfficer);
		}else{
			if($scope.productCode=='06'||$scope.productCode=='07'){
				$scope.personnelType = "推送金融机构";
				box.editAlert($scope,"订单通过审批吗，请选择推送金融机构员","<submit-box></submit-box>",$scope.pass);
			}else{
				box.editAlert($scope,"提交","确定提交审批信息吗？",$scope.pass);
			}
		}
		
	}

	//退回
	orderIsBack = function(){
		var param = {
			orderNo:$scope.orderNo,
			processId:'auditReview'
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
	$scope.showReviewBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
	
});