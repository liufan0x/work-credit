angular.module("anjboApp").controller("agencyMaintainDetailCtrl", function($scope, $http, $state, $timeout, $filter, box, route) {
	$scope.showView = 1;
	$scope.productCode = route.getParams().productCode;
	$scope.orderNo = route.getParams().orderNo;
	$scope.m = new Object();
	$scope.m.typeDepend = "100903";
	$scope.m.title  = "合作协议";

	$scope.agencyDto = new Object();
	var agencyId = route.getParams().agencyId;
	if(agencyId == -1) {
		$scope.isAdd = true;
		$scope.agencyDto.isBond = "0";
	} else {
		$scope.isAdd = false;
		// 获取机构信息
		$http({
			method: 'post',
			url: "/credit/user/agency/v/find",
			data: {
				id: agencyId
			}
		}).success(function(data) {
			if("SUCCESS" == data.code) {
				$scope.agencyDto = data.data;
				//获取渠道经理
				$http({
					method: 'post',
					url: "/credit/user/user/v/searchByType",
					data: {
						type: "auth",
						name: "提单",
						agencyId: "1"
					}
				}).success(function(data) {
					$scope.acceptList = data.data;
				});

				//获取渠道经理
				$http({
					method: 'post',
					url: "/credit/user/user/v/searchByType",
					data: {
						type: "role",
						name: "渠道经理",
						agencyId: "1"
					}
				}).success(function(data) {
					$scope.channelManagerList = data.data;
					angular.forEach($scope.channelManagerList, function(data) {
						if(data.uid == $scope.agencyDto.chanlMan) {
							$scope.agencyDto.chanManName = data.name;
							return;
						}
					});
				});

				//获取受理经理
				$http({
					method: 'post',
					url: "/credit/user/user/v/searchByType",
					data: {
						type: "role",
						name: "受理经理",
						agencyId: "1"
					}
				}).success(function(data) {
					$scope.acceptManagerList = data.data;
				});

				//初始化数据
				$http({
					method: 'post',
					url: '/credit/data/dict/v/search',
					data: {
						type: "incomeMode"
					}
				}).success(function(data) {
					if("SUCCESS" == data.code) {
						$scope.incomeModeList = data.data;
					}
				});
				//初始化数据
				$http({
					method: 'post',
					url: '/credit/data/dict/v/search',
					data: {
						type: "agencyType"
					}
				}).success(function(data) {
					if("SUCCESS" == data.code) {
						$scope.agencyTypeList = data.data;
						angular.forEach($scope.agencyTypeList, function(data) {
							if(data.code == $scope.agencyDto.agencyType) {
								$scope.agencyDto.typeName = data.name;
							}
						});
					}
				});
				//初始化数据
				$http({
					method: 'post',
					url: '/credit/data/dict/v/search',
					data: {
						type: "cooperativeMode"
					}
				}).success(function(data) {
					if("SUCCESS" == data.code) {
						$scope.cooperativeModeList = data.data;
						angular.forEach($scope.cooperativeModeList, function(data) {
							if(data.code == $scope.agencyDto.cooperativeModeId) {
								$scope.agencyDto.cooperativeMode = data.name;
							}
						});
					}
				});
			} else {
				box.boxAlert(data.msg);
			}

		})
	}

	$timeout(function() {
		var customerAgencyAcceptListName;
		angular.forEach($scope.agencyDto.agencyAcceptDtos, function(data) {
			angular.forEach($scope.acceptList, function(data1) {
				if(data.acceptUid == data1.id) {
					if(!customerAgencyAcceptListName || "" == customerAgencyAcceptListName) {
						customerAgencyAcceptListName = data1.name;
					} else if(customerAgencyAcceptListName) {
						customerAgencyAcceptListName += "," + data1.name;
					}
				}
			});
		});
		$scope.agencyDto.customerAgencyAcceptListName = customerAgencyAcceptListName;
	}, 1000);
	//定价配置
	$scope.pricingConfig = function(obj) {
		var code = obj.cityCode + obj.productCode
		$state.go("agencyTypeFeescaleEdit", {
			agencyTypeId: $scope.agencyDto.agencyCode,
			agencyTypeFeescaleId: obj.feescaleId,
			agencyTypeName: "-1",
			productionid: code,
			agencyId: $scope.agencyDto.id,
			productId: obj.id,
			productCode: $scope.productCode,
			orderNo: $scope.orderNo,
			detail: 1
		});
	}
	//流程配置
	$scope.processConfig = function(obj) {
		var code = obj.cityCode + obj.productCode
		//$state.go("productEdit", {productId:code,agencyId:$scope.agencyDto.id});
		$http({
			method: 'post',
			url: "/credit/data/process/v/search",
			data: {
				'productId': code,
				'showAgency': 1,
				'pageSize': 50
			}
		}).success(function(res) {
			$scope.listProProcess = res.data;
			$timeout(function() {
				box.alertPanel2($scope, $("#panelProductProcess").html());
			});
		});
	}

	//多选框赋值
	$scope.applyProductCheck = function(c) {
		angular.forEach($scope.incomeModeList, function(data, index, array) {
			if(data.code == c.code) {
				data.check = !data.check;
				return
			}
		});
	}
	//成员管理
	$scope.memberManager = function() {
		$state.go("agencyMemberList", {
			'agencyId': $scope.agencyDto.id,
			'agencyName': $scope.agencyDto.name
		});
	}
}).directive("agencyInfoDetail", function($http, route) {
	return {
		restrict: "E",
		templateUrl: '/template/agency/detail/agencyInfoDetail.html',
		transclude: true,
		link: function(scope) {
			scope.obj = new Object();
		}
	};
});