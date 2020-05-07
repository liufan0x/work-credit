angular.module("anjboApp").controller("isChargeEditCtrl",function($scope,$http,$state,box,route){
	$scope.productId = route.getParams().cityCode+''+route.getParams().productCode;//业务产品
	$scope.harvest = new Object();
	$scope.harvest.orderNo = route.getParams().orderNo;
	$scope.isProductCode = route.getParams().productCode;;
	$scope.harvest.type=3;
	$scope.serviceFees=0;
	$scope.isRate = 0;//费率是否修改
	$scope.isYunNan=false;
	$scope.isToBack=1;  //退回重新走流程  
	$http({
		method: 'POST',
		url:"/credit/finance/fddIsCharge/v/detail" ,
		data:{"orderNo":route.getParams().orderNo}
	}).success(function(data){
		$scope.harvest=data.data.harvest;
		$scope.harvest.interestTime ="";
		$scope.customsPoundage = $scope.harvest.customsPoundage; //关外手续费
		$scope.serviceFees = $scope.harvest.serviceCharge; //固定服务费
		$scope.otherPoundage = $scope.harvest.otherPoundage;  //其他费用
		$scope.riskGradeId = $scope.harvest.riskGradeId;  //风控等级
		$scope.chargeMoney = $scope.harvest.chargeMoney;   //收费金额
		$scope.rate=$scope.harvest.rate;
		$scope.overdueRate=$scope.harvest.overdueRate;
		//查询收利息信息
		$scope.harvest.type=3;
		$http({
			method: 'POST',
			url:"/credit/finance/fddCharge/v/detail" ,
			data:$scope.harvest
		}).success(function(data){
			var harvest=data.data.interest;
			if(harvest && harvest != null) {
				$scope.harvest.imgs = new Array();
				var img = harvest.interestImg;
				if(img != '' && img != null) {
					$scope.harvest.imgs = img.split(",");
					$("#img").val(img);
				}
				if(harvest.interestTimeStr && harvest.interestTimeStr!=''){
					$scope.harvest.interestTime =harvest.interestTimeStr+"";
				}
				$scope.harvest.collectInterestMoney = harvest.collectInterestMoney;
			}
		});
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
	});
	
	$scope.orderIsBack = false;
	//退回
	$scope.backToSubmit = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
	orderIsBack = function(){
		var param = {
			orderNo:$scope.orderNo,
			processId:'isCharge'
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
//	$http({
//			url:'/credit/order/allocationFund/v/processDetails',
//			method:'POST',
//			data:{orderNo:$scope.harvest.orderNo}
//		}).success(function(data){
//			if("SUCCESS"==data.code){
//				$scope.dataList = data.data;
//				angular.forEach($scope.dataList,function(data,index,array){
//					if(("114"==data.fundCode||114==data.fundCode)){
//						$scope.isYunNan = true;
//					}
//				});
//			}
//	});
	
	$scope.showSubmit = function(){
		var vf =/^([0-9]\d{0,12})([.]?|(\.\d{1,4})?)$/;
		var rate =$scope.harvest.rate+"";
		if(rate=='' || !vf.test(rate)){
			box.boxAlert("费率不能为空或输入有误！");
			return false;
		}
		var overdueRate = $scope.harvest.overdueRate+"";
		if(overdueRate=='' || !vf.test(overdueRate)){
			box.boxAlert("逾期费率不能为空或输入有误！");
			return false;
		}
		if($scope.harvest.chargeMoney=='' || !vf.test($scope.harvest.chargeMoney)){
			box.boxAlert("收费金额不能为空或输入有误！");
			return false;
		}
		if($scope.harvest.interestTime==null  || $scope.harvest.interestTime==''){
			box.boxAlert("收手续费时间不能为空");
			return false;
		}
		var collectInterestMoney=$("#collectInterestMoney").val();
		if(collectInterestMoney==null || collectInterestMoney=='' || !vf.test(collectInterestMoney)){
			box.boxAlert("实收手续费不能为空或输入有误！");
			return false;
		}
		var returnMoney = $("#returnMoney").val();
		if(returnMoney==null  || returnMoney=='' || !vf.test(returnMoney)){
			box.boxAlert("返佣金额不能为空或输入有误！");
			return false;
		}
		var rateImg=$("#rateImg").val();
		$scope.harvest.rateImg=rateImg;
		if($scope.isRate==1){  //费率凭证
			if($scope.harvest.rateImg==''){
				box.boxAlert("您修改了费率，请上传审批凭证！");
				return false;
			}
		}
		var harvestSubmit = function(){
				/*if($scope.isToBack==1 ){
					$scope.harvest.uid=$scope.sumbitDto.uid; //出纳
				}
				if($scope.isToBack==1 && ($scope.harvest.uid==null || $scope.harvest.uid=='' ||  $scope.harvest.uid=='undefined')){
					box.boxAlert("请选择放款专员");
					return false;
				}*/
				var img=$("#img").val();
				$scope.harvest.interestImg=img;
			    $(".lhw-alert-ok").attr("disabled","disabled");
			    $scope.harvest.type=3;
			    box.waitAlert();
				$http({
					method: 'POST',
					url:"/credit/finance/fddIsCharge/v/add" ,
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
		var returnMoneyUid = function(){
			if($scope.isToBack==1 ){
				$scope.harvest.rebateUid=$scope.sumbitDto.uid; //出纳
			}
			if($scope.harvest.rebateUid==null || $scope.harvest.rebateUid=='' ||  $scope.harvest.rebateUid=='undefined'){
				box.boxAlert("请选择返佣专员");
				return false;
			}
//			$scope.personnelType = "放款";
//			box.editAlert($scope,"确定提交核实收费信息吗？请选择放款专员。","<submit-box></submit-box>",harvestSubmit);
			box.editAlert($scope,"提交","确定提交核实收费信息吗？",harvestSubmit);
		}
		if($scope.isToBack==1){
			if($scope.harvest.returnMoney!=0){
				$scope.personnelType = "返佣";
				box.editAlert($scope,"确定提交核实收费信息吗？请选择返佣专员。","<submit-box></submit-box>",returnMoneyUid);
			}else{
	    //		if($scope.isYunNan){
		//			$scope.personnelType = "财务制单";
		//			box.editAlert($scope,"确定提交核实利息信息吗？请选择财务制单专员。","<submit-box></submit-box>",harvestSubmit);
		//		}else{
//					$scope.personnelType = "放款";
//					box.editAlert($scope,"确定提交核实收费信息吗？请选择放款专员。","<submit-box></submit-box>",harvestSubmit);
				box.editAlert($scope,"提交","确定提交核实收费信息吗？",harvestSubmit);
		//		}
			}
		}else{
			box.editAlert($scope,"提交","确定提交核实收费信息吗？",harvestSubmit);
		}

		
	}
	
	//改变是费率
	$scope.$watch("harvest.rate",function(newValue, oldValue){
		if(typeof(newValue)!='undefined' && typeof(oldValue)!='undefined' && newValue!=$scope.rate){
			 $scope.harvest.isUpdata="1";
			 $scope.isRate =1;
		}else{
			 $scope.isRate =0;
		}
	});
	//改变是逾期费率
	$scope.$watch("harvest.overdueRate",function(newValue, oldValue){
		if(typeof(newValue)!='undefined' && typeof(oldValue)!='undefined' && newValue!=$scope.overdueRate){
			  $scope.harvest.isUpdata="1";
		 }
	});
//	/** 监听固定服务费 */
//	$scope.$watch("harvest.serviceCharge", function(newValue, oldValue) {
//		if(typeof(newValue)!='undefined' && typeof(oldValue)!='undefined'){
//			 $scope.harvest.isUpdata="1";
//			$scope.rateOnblur();
//		} 
//	});
   //退回
	$scope.showBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}

})
function returnDatafinance(url,smallUrl,name){
	var img=$("#img").val();
	if(url!=''){
		 url=url.substring(0,url.length-1);
		 var urls= new Array();
		 var urls=url.split(",");	
		 var h="";
		 for (var i=0;i<urls.length ;i++ )  { 
			 h+="<img src='"+urls[i]+"' class='gallery-pic' style='display:none;'>";
		 }   
		 $(".processuUpdImg").append(h);
		 if(img!=''){
			 url=img+","+url;
		 }
		 $("#img").val(url); 
		 $(".processuUpdImg").show();
	}
}