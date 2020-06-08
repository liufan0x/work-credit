angular.module("anjboApp").controller("placeBusinfoEditCtrl", function($scope, $timeout,$http, $state, process, $compile, route,box) {
	$scope.relationOrderNo = route.getParams().relationOrderNo;
	$scope.isProductCode = route.getParams().productCode;
	var params = {"orderNo": route.getParams().orderNo}
	if($scope.isProductCode=='03' && $scope.relationOrderNo!="0"){
		 params = {"orderNo": route.getParams().relationOrderNo}
	}
	$scope.isDelMoveShow = false;
	//业务资料信息
	function refresh() {
		
		try {
			$scope.showImgAuth = true;
			var listScope = angular.element('.detail-left').scope();
			var orderFlow = listScope.orderFlowList[listScope.orderFlowList.length - 1];
			if(orderFlow.currentProcessId == 'wanjie') {
				var handleDate= orderFlow.handleTime + 1000*60*60*24*10;
				var myDate = new Date().getTime();
				if(handleDate < myDate){
					if(!$scope.hasImgList){
						$scope.showImgAuth = false;
					}
				}
			}else if(orderFlow.currentProcessId == 'elementReturn'){
				var handleDate= orderFlow.handleTime;
				if(handleDate < new Date('2017-11-01').getTime()){
					if(!$scope.hasImgList){
						$scope.showImgAuth = false;
					}
				}
			}
		} catch(e) {
			
		}
		
		$http({
			method: 'POST',
			url: '/credit/order/businfo/v/query',
			data: params
		}).success(function(data) {
			$scope.busInfo = data.data;
			
			angular.forEach($scope.busInfo.creditLoan,function(data){
				angular.forEach(data.sonTypes,function(data1){
					angular.forEach(data1.listMap,function(data2){
						if(data2.url.indexOf('pdf')>0){
							data2.isPdf = true;
						}else{
							data2.isPdf = false;
						}
					})
				})
			})
			
			$scope.isImg = $scope.busInfo.operate;
			$scope.cOperate = $scope.busInfo.cOperate;
			$scope.finshed = $scope.busInfo.finshed;
			$scope.cityName = data.data.cityName;
			$scope.productName = data.data.productName;
			$scope.progressId = data.data.progressId;
			$scope.setProgressId(data.data.progressId);
		})
	}
	$timeout(refresh,300);
	
	$scope.download = function(){
		
		var list = $scope.busInfo.creditLoan;
		
		angular.forEach(list,function(data){
			angular.forEach(data.sonTypes,function(data1){
				data1.listImgs = data1.listMap;
			})
			data.childrenType = data.sonTypes;
		})
		
		
		/*$http({
			url: '/credit/order/businfo/v/getBusinfoAndTypeNames',
			method: 'POST',
			data:{
				"topType":list,
				"custName":$scope.borrow.borrowerName
			}
		}).success(function(data1) {
			if ("SUCCESS" == data1.code) {
				window.open(data1.data);
			}
			box.closeWaitAlert();
		});*/						
		$http({
			method: 'POST',
			url: 'credit/order/borrow/v/query',
			data: params
		}).success(function(data) {
			$scope.borrow = data.data;
			$http({
				url: '/credit/order/businfo/v/getBusinfoAndTypeNames',
				method: 'POST',
				data:{
					"topType":list,
					"custName":$scope.borrow.borrowerName
				}
			}).success(function(data1) {
				if ("SUCCESS" == data1.code) {
					window.open(data1.data);
				}
				box.closeWaitAlert();
			});
		});
	}

	//设置typeId
	$scope.setType = function(typeId) {
		$scope.typeId = typeId;
	}
	
	//设置typeId
	$scope.setProgressId = function(progressId) {
		$scope.progressId = progressId;
	}

	//上传
	$scope.uploadifyImg = function(imgs,imgarr) {
		angular.forEach(imgarr,function(data){
			data.orderNo = params.orderNo;
			data.typeId = $scope.typeId;
			data.progressId = $scope.progressId;
		});
		if(!imgarr||imgarr.length<=0){
			$http({
				method: 'POST',
				url: '/credit/order/businfo/v/save',
				data: {
					"orderNo":params.orderNo,
					"typeId":$scope.typeId,
					"urls":imgs,
					"progressId":$scope.progressId
				}
			}).success(function(data) {
				refresh();
			});
		} else {
			$http({
				method: 'POST',
				url: '/credit/order/businfo/v/batchBusinfo',
				data: angular.toJson(imgarr)
			}).success(function(data) {
				refresh();
			});
		}

	}

	$scope.imgShow = function(showType, typeId) {
		$scope.showType = showType;
		$scope.isDelMoveShow = true;
		if(showType == 'move') {
			$http({
				method: 'POST',
				url: '/credit/order/businfo/v/getAllType',
				data: {
					"orderNo": params.orderNo,
					"typeId": typeId
				}
			}).success(function(data) {
				if("SUCCESS" == data.code) {
					$scope.typeList = data.data.listMap;
				}
			});
		}

		$http({
			method: 'POST',
			url: '/credit/order/businfo/v/lookOver',
			data: {
				"orderNo": params.orderNo,
				"typeId": typeId
			}
		}).success(function(data) {
			if("SUCCESS" == data.code) {
				$scope.imgList = data.data.listMap;
			} else {
				$scope.isDelMoveShow = false;
			}
		});
	}
	
	var isAll=false;
	$scope.selectImg = function(e){
		isAll=!isAll;
		$("input[name='ids']").prop("checked",isAll);
	}
	
	$scope.imgcz = function() {
		var ids = "";
		$("input[name='ids']:checked").each(function() {
			ids += $(this).val() + ",";
		});

		if(ids.length != 0) {
			ids = ids.substring(0, ids.length - 1);
		} else {
			box.boxAlert("未选择图片");
			return false;
		}

		if($scope.showType == 'del') {
			$http({
				method: 'POST',
				url: '/credit/order/businfo/v/deleteIds',
				data: {
					"orderNo": $scope.imgList[0].orderNo,
					"typeId": $scope.imgList[0].typeId,
					"ids": ids
				}
			}).success(function(data) {
				if("SUCCESS" == data.code) {
					refresh();
					$scope.isDelMoveShow = false;
				} else {
					box.boxAlert(data.msg);
				}
			});
		} else {
			$http({
				method: 'POST',
				url: '/credit/order/businfo/v/move',
				data: {
					"orderNo": params.orderNo,
					"businfoIds": ids,
					"toTypeId": $scope.toTypeId
				}
			}).success(function(data) {
				if("SUCCESS" == data.code) {
					refresh();
					$scope.isDelMoveShow = false;
				} else {
					box.boxAlert(data.msg);
				}
			});
		}
	}

});
