angular.module("anjboApp").controller("notarizationEditCtrl",function($scope,$http,route,$state,box){

	$scope.obj = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;

	$http({
		method: 'POST',
		url:"/credit/process/notarization/v/init" ,
		data:$scope.obj
	}).success(function(data){
		if(data.code == "SUCCESS"){
			$scope.obj = data.data;
			if($scope.obj!=null){
				$scope.obj.notarizationTime=$scope.obj.notarizationTimeStr;
				$scope.obj.estimatedTime=$scope.obj.estimatedTimeStr;
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
			}	
		}
	})

	$scope.submit = function(){
		var img=$("#img").val();
		$scope.obj.notarizationImg=img;
		if($scope.obj.notarizationTime==null  || $scope.obj.notarizationTime==''){
			alert("公证日期不能为空");
			return false;
		}
		if($scope.obj.notarizationAddressCode==null  || $scope.obj.notarizationAddressCode=='' || $scope.obj.notarizationAddressCode=='null'){
			alert("公证地点不能为空");
			return false;
		}
		if($scope.obj.estimatedTime==null  || $scope.obj.estimatedTime==''){
			alert("预计出款日期不能为空");
			return false;
		}
		if(img==null || img ==''){
			alert("公证图片不能为空");
			return false;
		}
		var notarizationSubmit = function(){
			$(".lhw-alert-ok").attr("disabled","disabled");
			$http({
				method: 'POST',
				url:"/credit/process/notarization/v/add" ,
				data:$scope.obj
			}).success(function(data){
				alert(data.msg);
				 box.closeAlert();
				if(data.code == "SUCCESS"){
					 $state.go("orderList");
				}
			})
		}
		box.editAlert($scope,"提交","确定提交公证信息吗？",notarizationSubmit);
	}

});