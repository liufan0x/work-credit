
angular.module("anjboApp").controller("auditFinalEditCtrl",function($scope,route,$http,$state,box){

	$scope.showDetail("auditFirst");
	$scope.obj = new Object();
	$scope.orderNo = route.getParams().orderNo;
	$scope.productCode = route.getParams().productCode;
	$scope.relationOrderNo = route.getParams().relationOrderNo;
	$scope.orderIsBack = false;
	$scope.loanAmont=0;
	$scope.isToBack=1;  //退回重新走流程  
	//通过按钮需要通过查询风控模型控制显示或者隐藏
	function loadFinal(){
		$http({
			url:'/credit/risk/final/v/loadFinal',
			method:'POST',
			data:{"orderNo":route.getParams().orderNo}
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.obj = data.data.auditFinal;
				if(!$scope.obj||null==$scope.obj){
					$scope.obj = new Object();
				}
				$scope.auditFinalShow = data.data.auditFinalShow;
			}
		});
		$http({
			method: 'POST',
			url:"/credit/order/flow/v/selectEndOrderFlow" ,
			data:{"orderNo":$scope.orderNo}
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
	loadFinal();
	
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
	
	//-------------审批通过----------------
	$scope.pass = function(){
		if($scope.isToBack==1 && (!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid)){
			box.boxAlert("请选择推送金融机构员");
			return;
		}
		$(".lhw-alert-ok").attr("disabled", "disabled");
		$scope.obj.nextHandleUid = $scope.sumbitDto.uid;//选择的分配资金方Uid
		$scope.obj.orderNo = $scope.orderNo;
		box.waitAlert();
		$http({
			method: 'POST',
			url:'/credit/order/auditFinal/v/processSubmit',
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
		})
	}
	//法务审批
	$scope.selAlloction = function(){
		if(!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid){
			box.boxAlert("请选择法务审批员");
			return;
		}
		var justiceUid = $scope.sumbitDto.uid  //法务审批
		$scope.obj.justiceUid = justiceUid;
		$scope.obj.officerUid = justiceUid;  //法务审批
		$scope.obj.officerUidType = 2;  //法务审批
		$scope.personnelType = "推送金融机构";
		box.editAlert($scope,"订单通过审批吗，请选择推送金融机构员","<submit-box></submit-box>",$scope.pass);
	}
	//审批通过按钮
	$scope.showFinalSubmit = function(title){
		if(!$scope.obj.remark||null==$scope.obj.remark||$scope.obj.remark==""){
			box.boxAlert("请填写终审审批意见!");
			return;
		}
//		var orderBorrowScope =  angular.element('place-borrow-detail').scope();
//		$scope.loanAmont = orderBorrowScope.borrow.loanAmount;
//		if($scope.cdBorrow){
//			$scope.loanAmont=$scope.cdBorrow.loanAmount;
//		}
		$scope.obj.officerUid = null;  //首席审批
		$scope.obj.officerUidType = null;  //首席审批
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
	//-------------审批通过----------------
	//-------------复核审批--------------------
	//复核审批提交并上报首席
	$scope.reportOfficer1 = function(){
		if($scope.isToBack==1 && (!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid)){
			box.boxAlert("请选择首席风险官");
			return;
		}
		if($scope.isToBack==1){
			$scope.obj.officerUid = $scope.sumbitDto.uid;  //首席审批
			$scope.obj.officerUidType = 1;  //首席审批
		}
		$(".lhw-alert-ok").attr("disabled", "disabled");
		$scope.obj.orderNo = $scope.orderNo;
		box.waitAlert();
		$http({
			method: 'POST',
			url:'/credit/order/auditFinal/v/processReportReview',
			data:$scope.obj
		}).success(function(data){
			box.closeWaitAlert();
			box.boxAlert(data.msg,function(){
				if(data.code == "SUCCESS") {
					box.closeAlert();
					$state.go("orderList");
				}
			});
		})
	}
	$scope.reportReviewandOff = function(){
		if($scope.isToBack==1 && (!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid)){
			box.boxAlert("请选择复核审批员");
			return;
		}
		if($scope.isToBack==1){
			$scope.obj.nextHandleUid = $scope.sumbitDto.uid;//选择的复核审批员Uid
		}
		$scope.personnelType = "首席风险官审批";
		box.editAlert($scope,"上报首席风险官，请选择首席风险官","<submit-box></submit-box>",$scope.reportOfficer1);
	}
	//复核审批提交确定
	//上报法务
	$scope.selAlloction1 = function(){
		if($scope.isToBack==1 && !$scope.sumbitDto.uid||""==$scope.sumbitDto.uid){
			box.boxAlert("请选择法务审批员");
			return;
		}
		if($scope.isToBack==1){
			$scope.obj.officerUid = $scope.sumbitDto.uid;  //法务审批
			$scope.obj.officerUidType = 2;  //法务审批
		}
		$(".lhw-alert-ok").attr("disabled", "disabled");
		$scope.personnelType = "推送金融机构";
		box.editAlert($scope,"请选择推送金融机构员","<submit-box></submit-box>",$scope.reviewAdd);
	}
	$scope.reportReview = function(){
		if($scope.isToBack==1 && (!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid)){
			box.boxAlert("请选择复核审批员");
			return;
		}
		$(".lhw-alert-ok").attr("disabled", "disabled");
		if($scope.isToBack==1){
			$scope.obj.nextHandleUid = $scope.sumbitDto.uid;//选择的复核审批员Uid
		}
		if($scope.cdBorrow){
			$scope.loanAmont=$scope.cdBorrow.loanAmount;
		}
		if($scope.isToBack==1 && $scope.loanAmont>=1000){
			$scope.personnelType = "法务审批";
			box.editAlert($scope,"请选择法务审批","<submit-box></submit-box>",$scope.selAlloction1);			
		}else if($scope.isToBack==1){
			$scope.personnelType = "推送金融机构";
			box.editAlert($scope,"请选择推送金融机构员","<submit-box></submit-box>",$scope.reviewAdd);
		}else{
			
			$scope.obj.orderNo = $scope.orderNo;
			box.waitAlert();
			$http({
				method: 'POST',
				url:'/credit/order/auditFinal/v/processReportReview',
				data:$scope.obj
			}).success(function(data){
				box.closeWaitAlert();
				box.boxAlert(data.msg,function(){
					if(data.code == "SUCCESS") {
						box.closeAlert();
						$state.go("orderList");
					}
				});
			})
		}
	}
	//提交后台
	$scope.reviewAdd = function(){
		if($scope.isToBack==1 && !$scope.sumbitDto.uid||""==$scope.sumbitDto.uid){
			box.boxAlert("请选择推送金融机构");
			return;
		}
		if($scope.isToBack==1){
			$scope.obj.allocationFundUid = $scope.sumbitDto.uid;  //推送金融机构uid
		}
		$(".lhw-alert-ok").attr("disabled", "disabled");
		$scope.obj.orderNo = $scope.orderNo;
		box.waitAlert();
		$http({
			method: 'POST',
			url:'/credit/order/auditFinal/v/processReportReview',
			data:$scope.obj
		}).success(function(data){
			box.closeWaitAlert();
			box.boxAlert(data.msg,function(){
				if(data.code == "SUCCESS") {
					box.closeAlert();
					$state.go("orderList");
				}
			});
		})
	}
	
	//复核审批按钮
	$scope.showReviewSubmit =function(){
		$scope.obj.officerUid =null;  
		$scope.obj.officerUidType = 0;  
		if(!$scope.obj.remark||null==$scope.obj.remark||$scope.obj.remark==""){
			box.boxAlert("请填写终审审批意见!");
			return;
		}
		if($scope.isToBack==1){
			$scope.personnelType = "复核审批";
			box.editAlertFinal($scope,"上报复核审批，请选择复核审批员","<submit-box></submit-box>",$scope.reportReview,$scope.reportReviewandOff);
		}else{
			box.editAlert($scope,"提交","确定提交审批信息吗？",$scope.reportReview);
		}
	}
	//------复核审批----------------
	//-----上报首席风险官-------------
	$scope.reportOfficer = function(){
		if($scope.isToBack==1 && (!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid)){
			box.boxAlert("请选择首席风险官");
			return;
		}
		$(".lhw-alert-ok").attr("disabled", "disabled");
		if($scope.isToBack==1){
			$scope.obj.nextHandleUid = $scope.sumbitDto.uid;//选择的首席风险官Uid
		}
		$scope.obj.orderNo = $scope.orderNo;
		box.waitAlert();
		$http({
			method: 'POST',
			url:'/credit/order/auditFinal/v/processReportOfficer',
			data:$scope.obj
		}).success(function(data){
			box.closeWaitAlert();
			box.boxAlert(data.msg,function(){
				if(data.code == "SUCCESS") {
					box.closeAlert();
					$state.go("orderList");
				}
			});
		})
	}
	//首席官按钮
	$scope.reportOfficerShow = function(){
		if(!$scope.obj.remark||null==$scope.obj.remark||$scope.obj.remark==""){
			box.boxAlert("请填写终审审批意见!");
			return;
		}
		if($scope.isToBack==1){
			$scope.personnelType = "首席风险官审批";
			box.editAlert($scope,"上报首席风险官，请选择首席风险官","<submit-box></submit-box>",$scope.reportOfficer);
		}else{
			box.editAlert($scope,"提交","确定提交审批信息吗？",$scope.reportOfficer);
		}
	}
	//-----上报首席风险官-------------
	//退回
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
	$scope.showFinalBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
});