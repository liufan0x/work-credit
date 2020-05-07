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
			box.boxAlert("要件退回时间不能为空！");
			return;
		} else if(!$scope.obj.returnHandleName){
			box.boxAlert("要件退回操作人不能 为空！");
			return;
		} else if($(".elementRturnUpdloadImg").find("img").length<=0){
			box.boxAlert("截屏证明必传");
			return;
		}
		
		$scope.obj.orderNo = $scope.orderNo;
		var isNextUser=false;
		$http({
			method: 'POST',
			url:"/credit/finance/receivablePay/v/detail" ,
			data:$scope.obj
		}).success(function(data){
			$scope.pay=data.data;
			var payReturnMoney=$scope.pay.rebateMoney;
			if(payReturnMoney && payReturnMoney!=0 && payReturnMoney!=''){
				isNextUser=true;	
			}
		});
		$http({
			method: 'POST',
			url:"/credit/order/lendingHarvest/v/processDetails" ,
			data:$scope.obj
		}).success(function(data){
			$scope.harvest=data.data;
			var returnMoney=$scope.harvest.harvest.returnMoney;
			if(returnMoney && returnMoney!=0 && returnMoney!=''){
				isNextUser=true;	
			}
			
			var showSubmit = function(){
				if(isNextUser){
					$scope.obj.handleUid=$scope.sumbitDto.uid; //出纳
					if($scope.obj.handleUid==null || $scope.obj.handleUid=='' ||  $scope.obj.handleUid=='undefined'){
						alert("请选择返佣专员");
						return false;
					}
				}
				box.waitAlert();
				$http({
					url:'/credit/order/documentsReturn/v/processSubmit',
					method:'POST',
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
				});
			}
			if(isNextUser){  //选择下一个处理人  返佣
				$scope.personnelType = "返佣";
				box.editAlert($scope,"确定提交要件退回信息吗？请选择返佣专员。","<submit-box></submit-box>",showSubmit);
			}else{
				box.editAlert($scope,"提交","确定要件退回信息吗？",showSubmit);
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
