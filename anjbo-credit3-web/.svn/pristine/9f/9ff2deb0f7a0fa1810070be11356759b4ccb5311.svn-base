
angular.module("anjboApp").controller("auditFirstEditCtrl",function($scope,route,$timeout,$rootScope,$http,$state,box){
	$scope.showDetail("managerAudit");
	$scope.obj = new Object();
	$scope.orderNo = route.getParams().orderNo;
	$scope.relationOrderNo = route.getParams().relationOrderNo;
	$scope.isProductCode = route.getParams().productCode;
	$scope.inProductTypeCode = $scope.isProductCode;
	$scope.orderIsBack = false;
	$scope.isAudit=false;
	$scope.obligeeA=[{id:"1",name:"标准"},{id:"2",name:"非标准"}];
	$scope.isToBack=1;  //退回重新走流程  
	
	//通过按钮需要通过查询风控模型控制显示或者隐藏
	function loadFirst(){
		if($scope.isProductCode && $scope.isProductCode!="03"){  //非畅贷
			$http({
				url:'/credit/risk/first/v/loadFirst',
				method:'POST',
				data:{"orderNo":$scope.orderNo}
			}).success(function(data){
				if("SUCCESS"==data.code){
					$scope.obj=data.data.firstDto;
					if(!$scope.obj||null==$scope.obj){
						$scope.obj = new Object();
					}
					$scope.productName = $scope.obj.productName;
					$scope.auditFirstShow = data.data.auditFirstShow; //是否显示通过审批按钮
					$scope.dataList = data.data.listLog;
//					if($scope.obj.loanBankId == 0){
//						$scope.obj.loanBankId ="";
//					}else{
//						$scope.obj.loanBankId = String($scope.obj.loanBankId);
//					}
//					$scope.obj.loanBankSubId = String($scope.obj.loanBankSubId);
//					if($scope.obj.paymentBankId == 0){
//						$scope.obj.paymentBankId ="";
//					}else{
//						$scope.obj.paymentBankId = String($scope.obj.paymentBankId);
//					}
//					$scope.obj.paymentBankSubId = String($scope.obj.paymentBankSubId);
					if($scope.obj.foreclosureAuditList!=null&&$scope.obj.foreclosureAuditList.length>0){
						for(var i=0;i<$scope.obj.foreclosureAuditList.length;i++){
							if($scope.obj.foreclosureAuditList[i].loanAccountType ==0){
								$scope.obj.foreclosureAuditList[i].loanAccountType ="";
							}else{
								$scope.obj.foreclosureAuditList[i].loanAccountType = String($scope.obj.foreclosureAuditList[i].loanAccountType);
							}
							if($scope.obj.foreclosureAuditList[i].loanBankId ==0){
								$scope.obj.foreclosureAuditList[i].loanBankId ="";
							}else{
								$scope.obj.foreclosureAuditList[i].loanBankId = String($scope.obj.foreclosureAuditList[i].loanBankId);
							}
							if($scope.obj.foreclosureAuditList[i].loanBankSubId ==0){
								$scope.obj.foreclosureAuditList[i].loanBankSubId="";
							}else{
								$scope.obj.foreclosureAuditList[i].loanBankSubId=String($scope.obj.foreclosureAuditList[i].loanBankSubId);
							}
						}
					}
					if($scope.obj.firstPaymentAuditList!=null&&$scope.obj.firstPaymentAuditList.length>0 ){
						for(var i=0;i<$scope.obj.firstPaymentAuditList.length;i++){
							if($scope.obj.firstPaymentAuditList[i].paymentAccountType ==0){
								$scope.obj.firstPaymentAuditList[i].paymentAccountType="";
							}else{
								$scope.obj.firstPaymentAuditList[i].paymentAccountType = String($scope.obj.firstPaymentAuditList[i].paymentAccountType);
							}
							if($scope.obj.firstPaymentAuditList[i].paymentBankId ==0){
								$scope.obj.firstPaymentAuditList[i].paymentBankId="";
							}else{
								$scope.obj.firstPaymentAuditList[i].paymentBankId = String($scope.obj.firstPaymentAuditList[i].paymentBankId);
							}
							if($scope.obj.firstPaymentAuditList[i].paymentBankSubId ==0){
								$scope.obj.firstPaymentAuditList[i].paymentBankSubId="";
							}else{
								$scope.obj.firstPaymentAuditList[i].paymentBankSubId=String($scope.obj.firstPaymentAuditList[i].paymentBankSubId);
							}
						}
					}	
					if(!$scope.obj.business || $scope.obj.business == "null") {
						$scope.obj.business = "1";
					} else {
						$scope.obj.business = String($scope.obj.business);
					}
					if($scope.obj.foreclosureAuditList==null || $scope.obj.foreclosureAuditList.length == 0){
						   $scope.obj.foreclosureAuditList = new Array();
						   $scope.foreclosurelist={"loanAccountType":null,"loanName":null,"loanAccount":null,"loanBankId":null,"loanBankName":null,"loanBankSubId":null,"loanBankSubName":null};
						   $scope.obj.foreclosureAuditList.push($scope.foreclosurelist);
					}
					if($scope.obj.firstPaymentAuditList==null || $scope.obj.firstPaymentAuditList.length == 0){
						   $scope.obj.firstPaymentAuditList = new Array();
						   $scope.paymentList={"paymentAccountType":null,"paymentName":null,"paymentAccount":null,"paymentBankId":null,"paymentBankName":null,"paymentBankSubId":null,"paymentBankSubName":null};
						   $scope.obj.firstPaymentAuditList.push($scope.paymentList);
					}
					
				}
			});
		}
		
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
	loadFirst();
	$scope.auditAdd=function(e){
		 $scope.foreclosurelist={"loanAccountType":null,"loanName":null,"loanAccount":null,"loanBankId":null,"loanBankName":null,"loanBankSubId":null,"loanBankSubName":null};
		 $scope.obj.foreclosureAuditList.push(new Object());
    }
	$scope.auditDel=function(e){
	   	 $scope.obj.foreclosureAuditList.splice(e);
	}
    $scope.auditAdd2=function(e){
	   	$scope.paymentList={"paymentAccountType":null,"paymentName":null,"paymentAccount":null,"paymentBankId":null,"paymentBankName":null,"paymentBankSubId":null,"paymentBankSubName":null};
	   	$scope.obj.firstPaymentAuditList.push(new Object());
    }
    $scope.auditDel2=function(e){
   		$scope.obj.firstPaymentAuditList.splice(e);
    }
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
		if($scope.isToBack==1 && (!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid)){
			box.boxAlert("请选择资金分配员");
			return;
		}
		$(".lhw-alert-ok").attr("disabled", "disabled");
		if($scope.isToBack==1){
			$scope.obj.nextHandleUid = $scope.sumbitDto.uid;//资金分配员uid
		}
		$scope.obj.orderNo = $scope.orderNo;
		if($scope.productName){
			$scope.obj.productName = $scope.productName;
		}
		box.waitAlert();
		$http({
			method: 'POST',
			url:'/credit/risk/first/v/pass',
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
	
	/**
	 * 保存
	 */
	$scope.saveBorrow = function(){
		$scope.obj.orderNo = $scope.orderNo;
		$http({
			method: 'POST',
			url:'/credit/risk/first/v/savaAuditFirst',
			data:$scope.obj
		}).success(function(data){
			box.closeAlert();
			box.boxAlert(data.msg,function(){
				if(data.code == "SUCCESS") {
					box.closeAlert();
				}
			});
		})
		
	}

	//上报终审
	$scope.reportFinal = function(){
		if($scope.isToBack==1 && (!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid)&&$scope.isProductCode!='06'&&$scope.isProductCode!='07'){
			box.boxAlert("请选择风控经理");
			return;
		}else if($scope.isToBack==1 && (!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid)&&($scope.isProductCode=='06'||$scope.isProductCode=='07')){
			box.boxAlert("请选择复核审批员");
			return;
		}
		$(".lhw-alert-ok").attr("disabled", "disabled");
		if($scope.isToBack==1){
			$scope.obj.nextHandleUid = $scope.sumbitDto.uid;//资金分配员uid
		}else if($scope.isToBack==2){
			$scope.obj.nextHandleUid = $scope.isFlowBack.handleUid;
		}
		$scope.obj.orderNo = $scope.orderNo;
		if($scope.productName){
			$scope.obj.productName = $scope.productName;
		}
		box.waitAlert();
		$http({
			method: 'POST',
			url:'/credit/order/auditFirst/v/processSubmit',
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

	//修改征信
	$scope.riskEditCredit = function(){
		if($scope.isProductCode == '04'){
			
			
			$timeout(function() {
				$scope.isConfigEdit = true;
			});
			$scope.changeView($scope.pageConfigDto.pageTabConfigDtos[6]);
		}else{
			$scope.isCreditEditShow = true;
			$scope.credit.createCreditLog = true;
			$scope.changeView(7);
			$(".detail-right").animate({
	              scrollTop:0
	        }, 200);
		}
		
	}
	
	//退回
	$scope.showFirstBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
	//通过
	$scope.showFirstSubmit = function(title){
		if(!$scope.auditForm.$valid){
    		$scope.isAudit=true;
    		box.boxAlert("请正确填写所有信息");
    		return;
    	}
		if($scope.isProductCode && $scope.isProductCode!="03" && $scope.isProductCode!="04" && $scope.isProductCode!="06" && $scope.isProductCode!="07"){
			for(var i=0;i<$scope.obj.foreclosureAuditList.length;i++){
				if($scope.obj.foreclosureAuditList[i].loanBankId ==null || $scope.obj.foreclosureAuditList[i].loanBankId ==""){
					box.boxAlert("请选择出款银行或支行");
					return;
				}
				if($scope.obj.foreclosureAuditList[i].loanBankSubId ==null || $scope.obj.foreclosureAuditList[i].loanBankSubId ==""){
					box.boxAlert("请选择出款银行或支行");
					return;
				}
			}
			for(var i=0;i<$scope.obj.firstPaymentAuditList.length;i++){
				if($scope.obj.firstPaymentAuditList[i].paymentBankId  ==null || $scope.obj.firstPaymentAuditList[i].paymentBankId  ==""){
					box.boxAlert("请选择回款银行或支行");
					return;
				}
				if($scope.obj.firstPaymentAuditList[i].paymentBankSubId ==null || $scope.obj.firstPaymentAuditList[i].paymentBankSubId ==""){
					box.boxAlert("请选择回款银行或支行");
					return;
				}
			}
//			if(null==$scope.obj.loanBankId||""==$scope.obj.loanBankId ||null == $scope.obj.loanBankSubId || ""==$scope.obj.loanBankSubId){
//				box.boxAlert("请选择出款银行或支行");
//				return;
//			}
//			if(null==$scope.obj.paymentBankId||""==$scope.obj.paymentBankId || null == $scope.obj.paymentBankSubId || ""==$scope.obj.paymentBankSubId){
//				box.boxAlert("请选择回款银行或支行");
//				return;
//			}
		}
		if($scope.isProductCode && ($scope.isProductCode=="03" || $scope.isProductCode=="04") &&  (typeof($scope.obj.remark)=='undefined' || $scope.obj.remark=="")){
				box.boxAlert("请填写审批意见");
				return;
		}
		if($scope.isToBack==1){
			$scope.personnelType = "推送金融机构";
			box.editAlert($scope,"订单通过审批吗，请选择资金分配员","<submit-box></submit-box>",$scope.pass);
		}else{
			box.editAlert($scope,"提交","确定提交审批信息吗？",$scope.pass);
		}
		
	}
	//上报
	$scope.reportFinalShow = function(){
		if(!$scope.auditForm.$valid){
    		$scope.isAudit=true;
    		box.boxAlert("请正确填写所有信息");
    		return;
    	}
		if($scope.isProductCode && $scope.isProductCode!="03" && $scope.isProductCode!="04"&& $scope.isProductCode!="06"&& $scope.isProductCode!="07"){
			for(var i=0;i<$scope.obj.foreclosureAuditList.length;i++){
				if($scope.obj.foreclosureAuditList[i].loanBankId ==null || $scope.obj.foreclosureAuditList[i].loanBankId ==""){
					box.boxAlert("请选择出款银行或支行");
					return;
				}
				if($scope.obj.foreclosureAuditList[i].loanBankSubId ==null || $scope.obj.foreclosureAuditList[i].loanBankSubId ==""){
					box.boxAlert("请选择出款银行或支行");
					return;
				}
			}
			for(var i=0;i<$scope.obj.firstPaymentAuditList.length;i++){
				if($scope.obj.firstPaymentAuditList[i].paymentBankId  ==null || $scope.obj.firstPaymentAuditList[i].paymentBankId  ==""){
					box.boxAlert("请选择回款银行或支行");
					return;
				}
				if($scope.obj.firstPaymentAuditList[i].paymentBankSubId ==null || $scope.obj.firstPaymentAuditList[i].paymentBankSubId ==""){
					box.boxAlert("请选择回款银行或支行");
					return;
				}
			}
//			if(null==$scope.obj.loanBankId||""==$scope.obj.loanBankId ||null == $scope.obj.loanBankSubId || ""==$scope.obj.loanBankSubId){
//				box.boxAlert("请选择出款银行或支行");
//				return;
//			}
//			if(null==$scope.obj.paymentBankId||""==$scope.obj.paymentBankId || null == $scope.obj.paymentBankSubId || ""==$scope.obj.paymentBankSubId){
//				box.boxAlert("请选择回款银行或支行");
//				return;
//			}
		}
		if($scope.isProductCode && ($scope.isProductCode=="03" || $scope.isProductCode=="04") &&  (typeof($scope.obj.remark)=='undefined' || $scope.obj.remark=="" || $scope.obj.remark==null)){
			box.boxAlert("请填写审批意见");
			return;
		}
		if($scope.isToBack==1&&$scope.isProductCode!="06"&&$scope.isProductCode!="07"){
			$scope.personnelType = "风控终审";
			box.editAlert($scope,"上报终审，请选择风控经理","<submit-box></submit-box>",$scope.reportFinal);
		}else if($scope.isProductCode=="06"||$scope.isProductCode=="07"){
			$scope.personnelType = "复核审批";
			box.editAlert($scope,"上报复核审批，请选择复核审批员","<submit-box></submit-box>",$scope.reportFinal);
		}else{
			box.editAlert($scope,"提交","确定提交审批信息吗？",$scope.reportFinal);
		}
	}

});