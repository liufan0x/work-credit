angular.module("anjboApp").controller("isLendingHarvestEditCtrl",function($scope,$http,$state,box,route){
	$scope.productId = route.getParams().cityCode+''+route.getParams().productCode;//业务产品
	$scope.harvest = new Object();
	$scope.harvest.orderNo = route.getParams().orderNo;
	$scope.isProductCode = route.getParams().productCode;;
	$scope.harvest.type=1;
	$scope.isToBack=1;  //退回重新走流程  
	$http({
		method: 'POST',
		url:"/credit/order/lendingInterest/v/processDetails" ,
		data:{"orderNo":route.getParams().orderNo}
	}).success(function(data){
		$scope.harvest=data.data.interest;
		if($scope.harvest.collectInterestMoney==0){
			$scope.harvest.collectInterestMoney=null;
		}
		if($scope.harvest != null) {
			$scope.harvest.imgs = new Array();
			var img = $scope.harvest.interestImg;
			if(img != '' && img != null) {
				$scope.harvest.imgs = img.split(",");
			}
		}
		if($scope.harvest && $scope.harvest.interestTime && $scope.harvest.interestTime!=''){
			$scope.harvest.interestTime =$scope.harvest.interestTime+"";
		}
		if($scope.harvest.interestImg && $scope.harvest.interestImg!=''){
			returnDatafinance($scope.harvest.interestImg+',','','')
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
			processId:'isLendingHarvest'
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
			box.boxAlert("收利息时间不能为空");
			return false;
		}
		var collectInterestMoney=$("#collectInterestMoney").val();
		if(collectInterestMoney==null || collectInterestMoney=='' || !vf.test(collectInterestMoney)){
			box.boxAlert("实收利息不能为空或输入有误！");
			return false;
		}
		var img=$("#img").val();
		if(img==null || img==''){
			box.boxAlert("截屏照片不能为空！");
			return false;			
		}
		var harvestSubmit = function(){
				if($scope.isToBack==1 ){
					$scope.harvest.uid=$scope.sumbitDto.uid; //出纳
				}
				if($scope.isToBack==1 && ($scope.harvest.uid==null || $scope.harvest.uid=='' ||  $scope.harvest.uid=='undefined')){
					box.boxAlert("请选择核实利息专员");
					return false;
				}
				var img=$("#img").val();
				$scope.harvest.interestImg=img;
			    $(".lhw-alert-ok").attr("disabled","disabled");
			    $scope.harvest.type=1;
			    $scope.harvest.orderNo = route.getParams().orderNo;
			    box.waitAlert();
				$http({
					method: 'POST',
					url:"/credit/order/lendingInterest/v/processSubmit" ,
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
			$scope.personnelType = "核实利息";
			box.editAlert($scope,"确定提交收利息信息吗？请选择核实利息专员。","<submit-box></submit-box>",harvestSubmit);
		}else{
			box.editAlert($scope,"提交","确定提交收利息信息吗？",harvestSubmit);
		}
		
	}
   //退回
	$scope.showBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
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
	
})