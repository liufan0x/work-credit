angular.module("anjboApp").controller("facesignEditCtrl",function($scope,$http,route,$state,box){

	$scope.obj = new Object();
	$scope.obj.orderNo = route.getParams().orderNo;

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

	$scope.submit = function(){
		if($scope.obj.faceSignTime==null || $scope.obj.faceSignTime==''){
			alert("面签时间不能为空");
			return false;
		}
		var img=$("#img").val();
		if(img==null || img==''){
			alert("面签图片不能为空");
			return false;
		}
		$scope.obj.faceSignPhoto=img;
		var facesignSubmit = function(){
			$(".lhw-alert-ok").attr("disabled","disabled");
			$http({
				method: 'POST',
				url:"/credit/process/facesign/v/add" ,
				data:$scope.obj
			}).success(function(data){
				alert(data.msg);
				 box.closeAlert();
				if(data.code == "SUCCESS"){
					 $state.go("orderList");
				}
			})
		}
		box.editAlert($scope,"提交","确定提交面签信息吗？",facesignSubmit);
		
	}

});