angular.module("anjboApp").controller("chargeEditCtrl",function($scope,$http,$state,box,route){
	$scope.productId = route.getParams().cityCode+''+route.getParams().productCode;//业务产品
	$scope.harvest = new Object();
	$scope.harvest.orderNo = route.getParams().orderNo;
	$scope.isProductCode = route.getParams().productCode;;
	$scope.harvest.type=3;
	$scope.isToBack=1;  //退回重新走流程  
	$http({
		method: 'POST',
		url:"/credit/finance/fddCharge/v/detail" ,
		data:{"orderNo":route.getParams().orderNo}
	}).success(function(data){
		$scope.harvest=data.data.interest;
		if($scope.harvest && $scope.harvest.interestTimeStr && $scope.harvest.interestTimeStr!=''){
			$scope.harvest.interestTime =$scope.harvest.interestTimeStr+"";
		}
		if($scope.harvest.collectInterestMoney==0 || $scope.harvest.collectInterestMoney=='0'){
			$scope.harvest.collectInterestMoney="";
		}
		$http({
			method: 'POST',
			url:"/credit/order/flow/v/selectEndOrderFlow" ,
			data:{
				orderNo:$scope.harvest.orderNo
			}
		}).success(function(data){
			$scope.isFlowBack=data.data;
			if($scope.isFlowBack!=null){
				$scope.isToBack=$scope.isFlowBack.isNewWalkProcess;
				if(typeof($scope.isToBack)=='undefined'){
					$scope.isToBack=1;
				}
			}
		})
	})
	
	$scope.orderIsBack = false;
	//退回
	$scope.backToSubmit = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
	orderIsBack = function(){
		var param = {
			orderNo:$scope.orderNo,
			processId:'charge'
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
	
	$scope.showSubmit = function(){
		var vf =/^([0-9]\d{0,12})([.]?|(\.\d{1,4})?)$/;
		if($scope.harvest.interestTime==null  || $scope.harvest.interestTime==''){
			box.boxAlert("收手续费时间不能为空");
			return false;
		}
		var collectInterestMoney=$("#collectInterestMoney").val();
		if(collectInterestMoney==null || collectInterestMoney=='' || !vf.test(collectInterestMoney)){
			box.boxAlert("实收手续费不能为空或输入有误！");
			return false;
		}
		var harvestSubmit = function(){
				if($scope.isToBack==1 ){
					$scope.harvest.uid=$scope.sumbitDto.uid; //出纳
				}
				if($scope.isToBack==1 && ($scope.harvest.uid==null || $scope.harvest.uid=='' ||  $scope.harvest.uid=='undefined')){
					box.boxAlert("请选择核实收费专员");
					return false;
				}
				var img=$("#img").val();
				$scope.harvest.interestImg=img;
			    $(".lhw-alert-ok").attr("disabled","disabled");
			    $scope.harvest.type=3;
			    $scope.harvest.orderNo = route.getParams().orderNo;
			    box.waitAlert();
				$http({
					method: 'POST',
					url:"/credit/finance/fddCharge/v/add" ,
					data:$scope.harvest
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
		if($scope.isToBack==1){
			$scope.personnelType = "核实收费";
			box.editAlert($scope,"确定提交收费信息吗？请选择核实收费专员。","<submit-box></submit-box>",harvestSubmit);
		}else{
			box.editAlert($scope,"提交","确定提交收费信息吗？",submit);
		}
	}
   //退回
	$scope.showBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
	
})