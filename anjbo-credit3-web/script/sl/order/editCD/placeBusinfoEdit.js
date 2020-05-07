angular.module("anjboApp").controller("placeBusinfoEditCDCtrl", function($scope, $timeout,$http, $state, process, $compile, route,box) {

	var params = {"orderNo": route.getParams().orderNo}
	$scope.isDelMoveShow = false;
	
	//业务资料信息
	function refresh() {
		$http({
			method: 'POST',
			url: '/credit/order/businfo/v/query',
			data: params
		}).success(function(data) {
			$scope.busInfo = data.data;
			$scope.isImg = $scope.busInfo.operate;
			$scope.cOperate = $scope.busInfo.cOperate;
			$scope.finshed = $scope.busInfo.finshed;
			$scope.cityName = data.data.cityName;
			$scope.productName = data.data.productName;
			$scope.progressId = data.data.progressId;
			$scope.setProgressId(data.data.progressId);
			console.info("关联债务影像资料："+ $scope.busInfo.changRelationLoan);
		})
	}
	$timeout(refresh,300);

	$scope.interviewOperateOrderNo = undefined;
	//设置typeId
	$scope.setType = function(typeId, relationOrderNo) {
		$scope.typeId = typeId;
		$scope.interviewOperateOrderNo = relationOrderNo;
	}
	
	//设置progressId
	$scope.setProgressId = function(progressId) {
		$scope.progressId = progressId;
	}

	//上传
	$scope.uploadifyImg = function(imgs) {
		$http({
			method: 'POST',
			url: '/credit/order/businfo/v/save',
			data: {
				"orderNo": undefined!=$scope.interviewOperateOrderNo ? $scope.interviewOperateOrderNo : params.orderNo,
				"typeId":$scope.typeId,
				"urls":imgs,
				"progressId":$scope.progressId
			}
		}).success(function(data) {
			refresh();
		})
	}

	$scope.imgShow = function(showType, typeId, relationOrderNo) {
		$scope.showType = showType;
		$scope.isDelMoveShow = true;
		$scope.interviewOperateOrderNo = relationOrderNo;
		if(showType == 'move') {
			$http({
				method: 'POST',
				url: '/credit/order/businfo/v/getAllType',
				data: {
					"orderNo": undefined!=$scope.interviewOperateOrderNo ? $scope.interviewOperateOrderNo : params.orderNo,
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
				"orderNo": undefined!=$scope.interviewOperateOrderNo ? $scope.interviewOperateOrderNo : params.orderNo,
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
					"orderNo": undefined!=$scope.interviewOperateOrderNo ? $scope.interviewOperateOrderNo : params.orderNo,
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