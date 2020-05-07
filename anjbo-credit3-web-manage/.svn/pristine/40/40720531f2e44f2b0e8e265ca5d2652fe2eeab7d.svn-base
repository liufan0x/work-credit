angular.module("anjboApp", ['bsTable']).controller("authorityCtrl", function($scope, $http, $timeout, $state, $window, box, route) {

	var params = route.getParams();
	var uid = params.uid;
	var roleId = params.roleId;
	var agencyId = params.agencyId;

	$scope.reflush = function() {
		$scope.authIdList = new Array();
		if(uid != '-1') {
			$scope.state = "userList";
			$scope.stateName = "用户列表";
			$scope.showName = "用户：【" + params.showName + "】";
			$http({
				method: 'post',
				url: "/credit/user/userAuthority/v/find",
				data: {
					"uid": uid
				}
			}).success(function(data) {
				console.debug("reflush.userAuth.success");				
				$scope.authIdList = null!=data.data.authorityId ? data.data.authorityId.split(',') : null;
				$scope.loadAuthAndInit();
			})
		} else if(agencyId != '-1') {/*此节点不确认是否被使用，保留*/
			$scope.state = "agencyList";
			$scope.stateName = "机构列表";
		} else if(roleId != '-1') {
			$scope.state = "roleList";
			$scope.stateName = "角色列表";
			$scope.showName = "角色：【" + params.showName + "】";
			$http({
				method: 'post',
				url: "/credit/user/roleAuthority/v/find",
				data: {
					"roleId": roleId
				}
			}).success(function(data) {
				console.debug("reflush.roleAuth.success");
				$scope.authIdList = null!=data.data.authorityId ? data.data.authorityId.split(',') : null;
				$scope.loadAuthAndInit();
			})
		}
	}
	$scope.reflush();
	$scope.systemType = "1";

	$scope.loadAuthAndInit = function() {
		$http({
			method: 'POST',
			url: '/credit/data/authority/v/selectCityProductAuthority'
		}).success(function(data) {
			$scope.cityList = data.data;
			angular.forEach($scope.cityList, function(data, index, array) {
				angular.forEach(data.productList, function(data, index, array) {
					var nodeList = data.productProcessList.concat();
					angular.forEach(nodeList, function(data1, index, array) {
						angular.forEach(data1.authorityList, function(data2, index, array) {
							if(data2.id) {
								data2.id = String(data2.id);
							}
							console.debug("CityProductAuth.seting");
							angular.forEach($scope.authIdList, function(data3, index, array) {
								if(data2.id == data3) {
									data2.authId = String(data3);
								}
							});
						});
					})
					data.nodeList = nodeList;
				});
			});
			$scope.setCityCode($scope.cityList[0].id, $scope.cityList[0].productList);
		});
		$http({
			method: 'post',
			url: '/credit/data/resource/v/selectResourceAuthority'
		}).success(function(data) {
			$scope.baseList = data.data;
			angular.forEach($scope.baseList, function(data1, index, array) {
				angular.forEach(data1.authorityList, function(data2, index, array) {
					if(data2.id) {
						data2.id = String(data2.id);
					} else {
						data2.id = "";
					}
					console.debug("BaseAuth.seting");
					angular.forEach($scope.authIdList, function(data3, index, array) {
						if(data2.id == data3) {
							data2.authId = String(data3);
						}
					});
				});
			});
			$timeout(function() {
				$(".first-div-left").on("click", function() {
					$(this).parent().next(".nav-hide").toggle();
					$(this).find(".left-arrow").toggleClass("shou");
				})
			});
		});
	}

	$scope.setCityCode = function(cityCode, productList) {
		$scope.cityCode = cityCode;
		$scope.setProductId(productList[0].id);
	};

	$scope.setProductId = function(productId) {
		$scope.productId = productId;
	};

	$scope.submit = function() {
		var ids = "";
		angular.forEach($scope.baseList, function(data, index, array) {
			angular.forEach(data.authorityList, function(data, index, array) {
				if(data.authId) {
					ids += data.authId + ",";
				}
			});
		});

		angular.forEach($scope.cityList, function(data, index, array) {
			angular.forEach(data.productList, function(data, index, array) {
				angular.forEach(data.nodeList, function(data, index, array) {
					angular.forEach(data.authorityList, function(data, index, array) {
						if(data.authId) {
							ids += data.authId + ",";
						}
					});
				});
			});
		});

		if(ids.length > 0) {
			ids = ids.substring(0, ids.length - 1);
		}
		console.log(ids);
		if(uid != '-1') {
			$http({
				method: 'post',
				url: "/credit/user/userAuthority/v/edit",
				data: {
					uid: uid,
					authorityId: ids
				}
			}).success(function(data) {
				box.boxAlert(data.msg, function() {
					if(data.code == 'SUCCESS') {
						$state.go("userList");
					}
				});
			})
		} else if(roleId != '-1') {
			$http({
				method: 'post',
				url: "/credit/user/roleAuthority/v/edit",
				data: {
					roleId: roleId,
					authorityId: ids
				}
			}).success(function(data) {
				box.boxAlert(data.msg, function() {
					if(data.code == 'SUCCESS') {
						$state.go("roleList");
					}
				});
			})
		}

	}
	
	$scope.allChecked = function(pIndex,productId,allFl) {


		angular.forEach($scope.cityList, function(data, index, array) {
			angular.forEach(data.productList, function(data, index, array) {
				if(data.id == productId){
					var nodeList = data.productProcessList.concat();
					angular.forEach(nodeList, function(data1, index, array) {
						angular.forEach(data1.authorityList, function(data2, index, array) {
							if(data2.id) {
								data2.id = String(data2.id);
							}
							if(pIndex == index){
								if(allFl){
									data2.authId = data2.id;
								}else{
									data2.authId = "";
								}
							}
						});
					})
					data.nodeList = nodeList;
				}
			});
		});
	}

	$scope.reset = function() {
		angular.forEach($scope.baseList, function(data1, index, array) {
			angular.forEach(data1.authorityList, function(data2, index, array) {
				if(data2.id) {
					data2.id = String(data2.id);
				} else {
					data2.id = "";
				}
				data2.authId = "";
			});
		});

		angular.forEach($scope.cityList, function(data, index, array) {
			angular.forEach(data.productList, function(data, index, array) {
				var nodeList = data.productProcessList.concat();
				angular.forEach(nodeList, function(data1, index, array) {
					angular.forEach(data1.authorityList, function(data2, index, array) {
						if(data2.id) {
							data2.id = String(data2.id);
						}
						data2.authId = "";
					});
				})
				data.nodeList = nodeList;
			});
		});
	}

});