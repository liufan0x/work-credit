angular.module("anjboApp").controller("elementReturnEditCtrl",function($scope,$http,$state,box){

	$scope.obj = new Object();

	$scope.setImgUrl = function(url){
		if(null==$scope.obj){
			$scope.obj = new Object();
			$scope.obj.returnImgUrl = "";
		}
		$scope.obj.returnImgUrl = url;
	}

	$scope.submitElementReturn = function(){
		if(!$scope.obj.returnTime){
			box.boxAlert("请选择要件退回时间");
			return;
		} else if(!$scope.obj.returnHandleName){
			box.boxAlert("请选择要件退回操作人");
			return;
		}
		$scope.obj.orderNo = $scope.orderNo;
		$http({
			url:'/credit/element/return/v/insert',
			method:'POST',
			data:$scope.obj
		}).success(function(data){
			if("SUCCESS"==data.code){
				alert(data.msg);
				$state.go('orderList');
			} else{
				box.boxAlert(data.msg);
			}
		});
	}

});
function elementReturnUpdaload(url,b,c){
	var scope = angular.element('.element-return-scope').scope();

	var tmp = url.split(",");
	var img = "";
	angular.forEach(tmp,function(data,index,array){
		if("null"!=data&&null!=data&&""!=data){
			img += "<img src='"+data+"' class='gallery-pic' style='display:none;'>"
		}
	});
	$(".elementRturnUpdloadImg").append(img);
	if(null!=scope.obj&&null!=scope.obj.returnImgUrl&&""!=scope.obj.returnImgUrl){
		url = url+scope.obj.returnImgUrl
	}
	$(".elementRturnUpdloadImg").show();
	scope.setImgUrl(url);
}

function deleteElementImg(url){
	var scope = angular.element('.element-return-scope').scope();

	angular.forEach(scope.docImgList,function(data,index,array){
		if(data.url==url){
			scope.docImgList.splice(scope.docImgList.indexOf(index), 1);
		}
	})
	$(".elementRturnUpdloadImg").find("img").each(function(){
		if($(this).attr("src")==url){
			$(this).remove();
		}
	})
	var tmpUrl = "";
	$(".elementRturnUpdloadImg").find("img").each(function(){
		if(""==tmpUrl){
			tmpUrl = $(this).attr("src");
		} else{
			tmpUrl = tmpUrl +","+$(this).attr("src");
		}
	})
	if($(".elementRturnUpdloadImg").find("img").length>0){
		$(".elementRturnUpdloadImg").show();
	} else {
		$(".elementRturnUpdloadImg").hide();
	}
	scope.setImgUrl(tmpUrl);
}
