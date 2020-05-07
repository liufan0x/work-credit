angular.module("anjboApp").controller("elementEditCtrl",function($http,$timeout,$scope,$state,box,menuOrderDetail){

	
		
	$timeout(function(){
		$state.go('orderEdit.elementEdit.placeElementEdit');
	});

	$scope.obj = new Object();
	$scope.isDelImgShow = false;
	$scope.allCheck = false;	//所有图片
	$scope.isCheckImg = false;
	$scope.imgListShow = false;
	$scope.submitSupplement = function(){
		var scopeTemp = angular.element(".element-supplement").scope();
		if(!$scope.obj){
			$scope.obj = new Object();
			$scope.obj.remark = scopeTemp.foreclosureType.remark;
		}
		if(!scopeTemp.foreclosureType){
			scopeTemp.foreclosureType = new Object();
		}
		if(!scopeTemp.paymentType){
			scopeTemp.paymentType = new Object();
		}

		$scope.isGreenStatusImgUrl = true;
		if((!$scope.obj.imgUrl||null==$scope.obj.imgUrl||$scope.obj.imgUrl.length<=0)
				&&(!$scope.obj.greenStatusImgUrl||$scope.obj.greenStatusImgUrl.length<0)){
			$scope.isGreenStatusImgUrl = false;

			if(scopeTemp.elementForm.$invalid){
				box.boxAlert("要件不完整，如已通过钉钉特批请上传截屏证明再提交。");
				return;
			}
			var loanAmount = $scope.borrow.loanAmount*10000;
			if(scopeTemp.paymentType.paymentMode=="网银转账"
				&&loanAmount>scopeTemp.paymentType.quota){
				box.boxAlert("限额额度不能小于借款金额");
				return;
			}
		}

		if(null==scopeTemp.foreclosureType.bankSubNameId
			||scopeTemp.foreclosureType.bankSubNameId==undefined
			||"undefined"==scopeTemp.foreclosureType.bankSubNameId
			||""==scopeTemp.foreclosureType.bankSubNameId
			||0==scopeTemp.foreclosureType.bankSubNameId){
			if(!scopeTemp.isGreenStatusImgUrl){
				box.boxAlert("要件不完整，如已通过钉钉特批请上传截屏证明再提交。");
				return;
			}
			scopeTemp.foreclosureType.bankSubNameId = 0;
		}
		if(null==scopeTemp.paymentType.paymentBankSubNameId
			||scopeTemp.paymentType.paymentBankSubNameId==undefined
			||"undefined"==scopeTemp.paymentType.paymentBankSubNameId
			||""==scopeTemp.paymentType.paymentBankSubNameId
			||0==scopeTemp.paymentType.paymentBankSubNameId){
			if(!scopeTemp.isGreenStatusImgUrl){
				box.boxAlert("要件不完整，如已通过钉钉特批请上传截屏证明再提交。");
				return;
			}
			scopeTemp.paymentType.paymentBankSubNameId = 0;
		}

		if(confirm("确定完成要件校验吗？")){
			$scope.obj.foreclosureType = scopeTemp.foreclosureType;
			$scope.obj.paymentType = scopeTemp.paymentType;
			$scope.obj.orderNo = scopeTemp.orderNo;
			$scope.obj.remark = scopeTemp.foreclosureType.remark;
			$http({
				url:'/credit/element/basics/v/supplement',
				method:'POST',
				data:$scope.obj
			}).success(function(data){
				if("SUCCESS"==data.code){
					box.closeAlert();
					$state.go('orderList');
				}else{
					box.boxAlert(data.msg);
				}
			});
		}

	}

	$scope.loadElement = function(){
		$http({
			url:'/credit/element/basics/v/detail',
			data:{"orderNo":$scope.orderNo},
			method:'POST'
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.obj = data.data;
				if($scope.obj&&null!=$scope.obj.imgUrl&&$scope.obj.imgUrl.length>0){
					$scope.getImgs($scope.obj.imgUrl);
				}
			}
		});
	}

	$scope.loadElement();

	$scope.docAddImg = function(url){
		$scope.obj.greenStatusImgUrl = url;
		$scope.obj.orderNo = $scope.orderNo;
		$http({
			url:'/credit/element/basics/v/addImg',
			data:$scope.obj,
			method:'POST'
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.obj = data.data;
				$scope.getImgs($scope.obj.imgUrl);
			}
		});
	}

	$scope.docDelImg = function(){
		if(!$scope.isCheckImg){
			alert("请选择要删除的图片!");
			return;
		}
		$scope.obj.orderNo = $scope.orderNo;
		var param = {
			orderNo:$scope.obj.orderNo,
			greenStatusImgUrl:$scope.tmpImgUrl
		}
		$http({
			url:'/credit/element/basics/v/delImg',
			data:param,
			method:'POST'
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.obj = data.data;
				$scope.getImgs($scope.obj.imgUrl);
				$scope.allCheck = false;
				$scope.isDelImgShow = false;
			}
			box.boxAlert(data.msg);
		});
	}

	$scope.docDelImgShow = function(imgList){
		$scope.allCheck = false;
		$scope.isDelImgShow = true;
	}

	$scope.selectAllImg = function(){
		if(!$scope.allCheck){
			$scope.allCheck = true;
		} else {
			$scope.allCheck = false;
		}
		angular.forEach($scope.docImgList,function(obj,index,array){
			obj.isCheck=$scope.allCheck;
		});
		if($scope.docImgList&&$scope.docImgList.length>0){
			$scope.isCheckImg = $scope.allCheck;
		}
	}
	$scope.docCheckImg = function(imgList){
		$scope.isCheckImg = false;
		var checkLength = 0;
		var imgLength = imgList.length;
		$scope.tmpImgUrl = "";
		angular.forEach(imgList,function(obj,index,array){
			if(obj.isCheck){
				checkLength += 1;
			} else{
				$scope.tmpImgUrl += obj.url+",";
			}
		});
		if(checkLength>0){
			$scope.isCheckImg = true;
		}
		$scope.allCheck = checkLength==imgLength
	}

	$scope.setImgUrl = function(url){
		if(null==$scope.obj){
			$scope.obj = new Object();
			$scope.obj.greenStatusImgUrl = "";
		}
		$scope.obj.greenStatusImgUrl = url;
	}
	$scope.getImgs = function(obj){
		$scope.docImgList = new Array();
		if(!obj||obj.length<=0){
			return;
		}
		var ind = 0;
		angular.forEach(obj,function(data,index,array){
			if("null"!=obj&&null!=obj&&""!=obj){
				$scope.docImgList[ind] = {
					url:data,
					isCheck:false
				};
				ind ++;
			}
		});
		if($scope.docImgList.length>0){
			$(".processuUpdImg").show();
		} else {
			$(".processuUpdImg").hide();
		}
	}

}).directive('elementSupplement',function($http,route){
	return{
		restrict:"E",
		templateUrl: '/template/sl/element/elementSupplement.html',
	};
});

function elementUpload(a,b,c){
	var scope = angular.element('.element-supplement').scope();
	var length = 0;
	if(!scope.docImgList){
		scope.docImgList = new Array();
	} else {
		length = scope.docImgList.length+1;
	}
	var tmp = a.split(",");
	var img = "";
	angular.forEach(tmp,function(data,index,array){
		if("null"!=data&&null!=data&&""!=data){
			scope.docImgList[length] = {
				url:data,
				isCheck:false
			}
			length ++;
			img += "<img src='"+data+"' class='gallery-pic' style='display:none;'>";
		}
	});
	$(".processuUpdImg").append(img);
	if(null!=scope.obj&&null!=scope.obj.greenStatusImgUrl&&""!=scope.obj.greenStatusImgUrl){
		a = a+scope.obj.greenStatusImgUrl
	}
	$(".processuUpdImg").show();
	scope.setImgUrl(a);
}
function deleteElementUrl(url){
	var scope = angular.element('.element-supplement').scope();
	angular.forEach(scope.docImgList,function(data,index,array){
		if(data.url==url){
			scope.docImgList.splice(scope.docImgList.indexOf(index), 1);
		}
	})
	$(".processuUpdImg").find("img").each(function(){
		if($(this).attr("src")==url){
			$(this).remove();
		}
	})
	var tmpUrl = "";
	$(".processuUpdImg").find("img").each(function(){
		if(""==tmpUrl){
			tmpUrl = $(this).attr("src");
		} else{
			tmpUrl = tmpUrl +","+$(this).attr("src");
		}
	})
	if($(".processuUpdImg").find("img").length>0){
		$(".processuUpdImg").show();
	} else {
		$(".processuUpdImg").hide();
	}
	scope.setImgUrl(tmpUrl);
}

