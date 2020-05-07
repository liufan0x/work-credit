angular.module("anjboApp").controller("applyLoanEditCtrl", function($scope, $http, $state, box, route) {

	$scope.obj = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;
	$scope.obj.relationOrderNo = route.getParams().relationOrderNo;
	$scope.productCode = route.getParams().productCode;;
	$scope.isYunNan=false;
	$http({
		method: 'POST',
		url: "/credit/finance/applyLoan/v/init",
		data: $scope.obj
	}).success(function(data) {
		$scope.order = data.data;
		if($scope.order!=null && $scope.order.lendingBankId==0){
			$scope.order.lendingBankId="";
		}else{
			$scope.order.lendingBankId=$scope.order.lendingBankId+"";
		}
		$scope.order.lendingBankSubId = $scope.order.lendingBankSubId+"";
		if($scope.order.chargesReceivedImg&&$scope.order.chargesReceivedImg!=''){
			returnDatafinance($scope.order.chargesReceivedImg+',','','');
		}
		if($scope.order.payAccountImg&&$scope.order.payAccountImg!=''){
			returnDatafinance2($scope.order.payAccountImg+',','','');
		}
	})
	
	$http({
			url:'/credit/order/allocationFund/v/processDetails',
			method:'POST',
			data:{orderNo:$scope.obj.orderNo}
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.dataList = data.data;
				angular.forEach($scope.dataList,function(data,index,array){
					if(("114"==data.fundCode||114==data.fundCode)){
						$scope.isYunNan = true;
					}
				});
			}
	});
	$http({
		method: 'POST',
		url: 'credit/order/borrow/v/query',
		data:{orderNo:$scope.obj.orderNo}
	}).success(function(data){
			$scope.orderObj = data.data;				
	});
	
	$scope.orderIsBack = false;
	//退回
	$scope.backToSubmit = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
	orderIsBack = function(){
		var param = {
			orderNo:$scope.orderNo,
			processId:'applyLoan'
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
	
	$scope.submit = function() {
		if($scope.order.borrowerName==''){
			box.boxAlert("客户姓名不能为空");
			return false;
		}
		if($scope.order.borrowingDays=='' || $scope.order.borrowingDays=='0'){
			box.boxAlert("借款期限不能为空且大于0");
			return false;
		}
		if($scope.order.bankAccount=='' || $scope.order.bankAccount==null){
			box.boxAlert("银行卡账号不能为空");
			return false;
		}
		if($scope.order.bankName=='' || $scope.order.bankName==null){
			box.boxAlert("银行卡户名不能为空");
			return false;
		}
		if($scope.order.lendingBankId=="" || $scope.order.lendingBankSubId==""||$scope.order.lendingBankId=="null"||$scope.order.lendingBankSubId=="null"){
			box.boxAlert("放款银行或支行不能为空");
			return false;
		}
		
		var img = $("#img").val();
		$scope.order.chargesReceivedImg = img;
		if($scope.order.chargesReceivedImg==null  || $scope.order.chargesReceivedImg==''){
			box.boxAlert("请上传已收取费用图片");
			return false;
		}
		
		var ckimg = $("#ckimg").val();
		$scope.order.payAccountImg = ckimg;
		if($scope.order.payAccountImg==null  || $scope.order.payAccountImg==''){
			box.boxAlert("请上传出款账号图片");
			return false;
		}
		var mortgageImg = $("#ckimg3").val();
		if(typeof(mortgageImg)!="undefined"){
			$scope.order.mortgageImg = mortgageImg;
			if($scope.order.mortgageImg==null  || $scope.order.mortgageImg==''){
				box.boxAlert("请上传抵押图片");
				return false;
			}
	    }
		var loanSubmit = function() {
			$(".lhw-alert-ok").attr("disabled", "disabled");
			box.waitAlert();
			$http({
				method: 'POST',
				url: "/credit/order/applyLoan/v/processSubmit",
				data: $scope.order
			}).success(function(data) {
				box.closeWaitAlert();
				box.closeAlert();
				box.boxAlert(data.msg,function(){
					if(data.code == "SUCCESS" || data.code=='APPLYLOAN_SUCCESS') {
						box.closeAlert();
						$state.go("orderList");
					}
				});
			})
		}
		var findByName = function(){
//				if($scope.isYunNan && ($scope.orderObj.paymentMethod==2||$scope.orderObj.paymentMethod=='2')){
//					$scope.obj.uid=$scope.sumbitDto.uid; //出纳
//					if($scope.obj.uid==null || $scope.obj.uid=='' ||  $scope.obj.uid=='undefined'){
//						box.boxAlert("请选择执行专员");
//						return false;
//					}
//				}
				$(".lhw-alert-ok").attr("disabled", "disabled");
				$http({
					method: 'POST',
					url: "/credit/order/applyLoan/v/processValidBankCardName",
					data: $scope.order
				}).success(function(data) {
					box.closeAlert();
					if(data.code == "SUCCESS") {
						loanSubmit();
					}else{
						box.editAlert($scope,"提交","你修改的银行卡户名异常，确定修改吗？",loanSubmit);
					}
				})
		}
		
//		if($scope.isYunNan && ($scope.orderObj.paymentMethod==2||$scope.orderObj.paymentMethod=='2')){
//			$scope.personnelType = "财务制单";
//			box.editAlert($scope,"确定提交核实利息信息吗？请选择财务制单专员。","<submit-box></submit-box>",findByName);
//		}else{
		if($scope.productCode=='06'||$scope.productCode=='07'){
			box.editAlert($scope,"提交","确定要提交申请放款信息吗？",loanSubmit);
		}else{
			box.editAlert($scope,"提交","确定提交申请放款信息吗？",findByName);
		}
//	    }
	}
	
	//关闭订单
	$scope.showBack = function(){
			var closeLoan = function(){
			$(".lhw-alert-ok").attr("disabled", "disabled");
			$scope.order.state = "订单已停止";
			box.waitAlert();
			$http({
				method: 'POST',
				url: "/credit/order/base/v/closeOrder",
				data: $scope.order
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
	function returnDatafinance2(url,smallUrl,name){
		var img=$("#ckimg").val();
		if(url!=''){
			 url=url.substring(0,url.length-1);
			 var urls= new Array();
			 var urls=url.split(",");	
			 var h="";
			 for (var i=0;i<urls.length ;i++ )  { 
				 h+="<img src='"+urls[i]+"' class='gallery-pic' style='display:none;'>";
			 }   
			 $(".processuUpdImg2").append(h);
			 if(img!=''){
				 url=img+","+url;
			 }
			 $("#ckimg").val(url); 
			 $(".processuUpdImg2").show();
		}
	}
});