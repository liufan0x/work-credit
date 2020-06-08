angular.module("anjboApp").controller("notarizationEditCtrl",function($scope,$http,route,$state,box){

	$scope.obj = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;
	$scope.obj.productCode = route.getParams().productCode;
	$scope.obj.relationOrderNo = route.getParams().relationOrderNo;
	$scope.productCode = route.getParams().productCode;
	$scope.isdisable=false;
	$http({
		method: 'POST',
		url:"/credit/process/notarization/v/init" ,
		data:$scope.obj
	}).success(function(data){
		if(data.code == "SUCCESS"){
			$scope.obj = data.data;
			if($scope.obj!=null){
				$scope.obj.notarizationTime=$scope.obj.notarizationTimeStr;
				//$scope.obj.estimatedTime=$scope.obj.estimatedTimeStr;
				$scope.obj.notarizationAddressCode = $scope.obj.notarizationAddressCode+"";
				if($scope.obj.notarizationImg!=null && $scope.obj.notarizationImg!=""){
					$scope.obj.imgs = new Array();
					var img = $scope.obj.notarizationImg;
					if(img != '' && img != null) {
						$scope.obj.imgs = img.split(",");
					}
					 $("#img").val($scope.obj.notarizationImg);
					 $(".processuUpdImg").show();
				}
				if(route.getParams().productCode=="03" && route.getParams().relationOrderNo!="0" && $scope.obj.notarizationTimeStr!=""&& $scope.obj.notarizationTimeStr!=null){
					$scope.isdisable=true; 
				}
			}	
		}
	})

	$scope.orderIsBack = false;
	//退回
	$scope.backToSubmit = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
	orderIsBack = function(){
		var param = {
			orderNo:$scope.orderNo,
			processId:'notarization'
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
	
	$scope.submit = function(){
		if($scope.productCode=='04' &&($scope.obj.notarizationType==null  || $scope.obj.notarizationType=='')){
			box.boxAlert("公证类型不能为空");
			return false;
		}
		if($scope.productCode!='04' || ($scope.productCode=='04' && $scope.obj.notarizationType!='不公证')){
			var img=$("#img").val();
			$scope.obj.notarizationImg=img;
			if($scope.obj.notarizationTime==null  || $scope.obj.notarizationTime==''){
				box.boxAlert("公证日期不能为空");
				return false;
			}
//			$scope.obj.notarizationTime += " 00:00:00";
			if($scope.obj.notarizationAddressCode==null  || $scope.obj.notarizationAddressCode=='' || $scope.obj.notarizationAddressCode=='null'){
				box.boxAlert("公证地点不能为空");
				return false;
			}
			/*if($scope.obj.estimatedTime==null  || $scope.obj.estimatedTime==''){
				box.boxAlert("预计出款日期不能为空");
				return false;
			}*/
//			$scope.obj.estimatedTime += " 00:00:00";
			if(img==null || img ==''){
				box.boxAlert("公证图片不能为空");
				return false;
			}
		}
		var notarizationSubmit = function(){
			$(".lhw-alert-ok").attr("disabled","disabled");
			box.waitAlert();
			$http({
				method: 'POST',
				url:"/credit/order/appNotarization/v/processSubmit" ,
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
		box.editAlert($scope,"提交","确定提交公证信息吗？",notarizationSubmit);
	}

});