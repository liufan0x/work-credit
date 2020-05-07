angular.module("anjboApp").controller("facesignEditCtrl",function($scope,$http,route,$state,box){

	$scope.obj = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;
	$scope.relationOrderNo = route.getParams().relationOrderNo;
	$scope.isProductCode = route.getParams().productCode;
	$http({
		method: 'POST',
		url:"/credit/process/facesign/v/detail" ,
		data:$scope.obj
	}).success(function(data){
		if(data.code == "SUCCESS"){
			$scope.obj = data.data;
			if($scope.obj!=null){
				$scope.obj.faceSignTime=$scope.obj.faceSignTimeStr;
				if($scope.obj.faceSignPhoto!=null && $scope.obj.faceSignPhoto!=""){
					$scope.obj.imgs = new Array();
					var img = $scope.obj.faceSignPhoto;
					if(img != '' && img != null) {
						$scope.obj.imgs = img.split(",");
					}
					 $("#img").val($scope.obj.faceSignPhoto);
					 $(".processuUpdImg").show();
				}
			}
		}
	})
//	$scope.addImg = function(){
//		$scope.inRelationOrderNo2Detail=route.getParams().orderNo;
//		if($scope.isProductCode=="03" && $scope.relationOrderNo!="0"){
//			$scope.inRelationOrderNo2Detail=$scope.relationOrderNo;
//		}
//		$scope.changeView(6);
//	}
	$scope.orderIsBack = false;
	//退回
	$scope.backToSubmit = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}
	orderIsBack = function(){
		var param = {
			orderNo:$scope.orderNo,
			processId:'facesign'
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
		if($scope.obj.faceSignTime==null || $scope.obj.faceSignTime==''){
			box.boxAlert("面签时间不能为空");
			return false;
		}
		var img=$("#img").val();
//		if(img==null || img==''){
//			box.boxAlert("面签图片不能为空");
//			return false;
//		}
		$scope.obj.faceSignPhoto=img;
		$scope.obj.relationOrderNo=$scope.relationOrderNo;
		var facesignSubmit = function(){
			$(".lhw-alert-ok").attr("disabled","disabled");
			 box.waitAlert();
			$http({
				method: 'POST',
				url:"/credit/order/appFacesign/v/processSubmit" ,
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
		box.editAlert($scope,"提交","确定提交面签信息吗？",facesignSubmit);
		
	}

});