angular.module("anjboApp").controller("agencyTypeFeescaleEditCtrl", function($scope, $http, $state, $filter, $timeout, box, route) {

	//获取当前收费标准ID
	$scope.feescaleId = route.getParams().agencyTypeFeescaleId;
	$scope.agencyTypeId = route.getParams().agencyTypeId;
	$scope.agencyTypeName = route.getParams().agencyTypeName;
	$scope.productionid = route.getParams().productionid;
	$scope.agencyId = route.getParams().agencyId;
	$scope.productId = route.getParams().productId;
	$scope.productCode = route.getParams().productCode;
	$scope.isChandLoan = false;
	if($scope.productionid) {
		var tmp = $scope.productionid.substring($scope.productionid.length - 2, $scope.productionid.length);
		$scope.isChandLoan = "03" == tmp ? true : false;
	}
	$scope.orderNo = route.getParams().orderNo;
	$scope.detail = route.getParams().detail;
	$scope.isDetail = false;
	if(1 == $scope.detail) {
		$scope.isDetail = true;
	}



	if($scope.agencyId == '0' || $scope.productionid == '0') {
		$scope.isAdd = true;
		$scope.agencyTypeFeescaleDto = new Object();
		$scope.agencyTypeFeescaleDto.productionid = $scope.productionid;
		$scope.agencyTypeFeescaleDto.agencyFeescaleRiskcontrolDtos = new Array();
		$scope.agencyTypeFeescaleDto.agencyFeescaleRiskcontrolDtos.push({
			agencyFeescaleSectionDtos: addagencyFeescaleSectionDtos()
		});

	} else {
		$http({
			method: 'POST',
			url: '/credit/user/agencyFeescale/v/search',
			data: {
				"agencyTypeId": $scope.agencyId,
				"productionid": $scope.productionid
			}
		}).then(function successCallback(response) {
			// 请求成功执行代码
			if("SUCCESS" == response.data.code) {
				var data = response.data;
				$scope.agencyTypeFeescaleList = data.data;
				var len = data.data.length;
				if(data.data[0] && data.data[0].agencyFeescaleRiskcontrolDtos.length > 0) {
					$scope.agencyTypeFeescaleDto = data.data[0];
				} else if(len > 1 && data.data[1] && data.data[1].agencyFeescaleRiskcontrolDtos.length > 0) {
					$scope.agencyTypeFeescaleDto = data.data[1];
				} else {
					$scope.isAdd = true;
					$scope.agencyTypeFeescaleDto = new Object();
					$scope.agencyTypeFeescaleDto.productionid = $scope.productionid;
					$scope.agencyTypeFeescaleDto.agencyFeescaleRiskcontrolDtos = new Array();
					$scope.agencyTypeFeescaleDto.agencyFeescaleRiskcontrolDtos.push({
						agencyFeescaleSectionDtos: addagencyFeescaleSectionDtos()
					});
					angular.forEach($scope.agencyTypeFeescaleDto.agencyFeescaleRiskcontrolDtos, function(data) {
						data.grade = "";
						angular.forEach(data.agencyFeescaleSectionDtos, function(d) {
							angular.forEach(d.agencyFeescaleDetailDtos, function(d2) {
								d2.modeid = "";
							});
						});
					});
				}
				if($scope.agencyTypeFeescaleDto) {
					$scope.agencyTypeFeescaleDto.productionid = String($scope.agencyTypeFeescaleDto.productionid);
					angular.forEach($scope.agencyTypeFeescaleDto.agencyFeescaleRiskcontrolDtos, function(data, index, array) {
						data.grade = String(data.grade);
						if(!data.agencyFeescaleSectionDtos || data.agencyFeescaleSectionDtos.length <= 0) {
							data.agencyFeescaleSectionDtos.push(addagencyFeescaleSectionDtos());
						} else {
							angular.forEach(data.agencyFeescaleSectionDtos, function(data1, index, array) {
								$scope.setSection(array, index, data1.section, -1);
								angular.forEach(data1.agencyFeescaleDetailDtos, function(data2, index, array) {
									data2.modeid = String(data2.modeid);
								});
							});
						}
						angular.forEach($scope.agencyTypeFeescaleList, function(listData) {
							if(data.grade == listData.riskGradeId) {
								data.servicefee = listData.servicefee;
								data.counterfee = listData.counterfee;
								data.otherfee = listData.otherfee;
							}
						});
					});
					$scope.cityCode = $scope.agencyTypeFeescaleDto.productionid.substring(0, 4);
					$scope.setCityCode($scope.cityCode);
				}

			} else {
				box.boxAlert(response.data.msg);
			}
		}, function errorCallback(response) {
			box.boxAlert("请求失败，请联系开发部");
		});

	}

	//初始化数据
	$http({
		method: 'post',
		url: '/credit/data/dict/v/search',
		data: {
			type: "cityList"
		}
	}).success(function(data) {
		if("SUCCESS" == data.code) {
			$scope.cityList = data.data;
		}
	});

	$scope.productList = new Array();
	$scope.setCityCode = function(cityCode) {
		$http({
			method: 'post',
			url: '/credit/data/product/v/search',
			data: {
				cityCode: cityCode
			}
		}).success(function(data) {
			$scope.productList = data.data;
		});
	};

	function addagencyFeescaleDetailDtos() {
		var agencyFeescaleDetailDtos = new Array();
		agencyFeescaleDetailDtos.push(new Object());
		agencyFeescaleDetailDtos.push(new Object());
		return agencyFeescaleDetailDtos;
	}

	function addagencyFeescaleSectionDtos() {
		var agencyFeescaleSectionDtos = new Array();
		agencyFeescaleSectionDtos.push({
			agencyFeescaleDetailDtos: addagencyFeescaleDetailDtos()
		});
		agencyFeescaleSectionDtos.push({
			agencyFeescaleDetailDtos: addagencyFeescaleDetailDtos()
		});
		return agencyFeescaleSectionDtos;
	}

	$scope.addRiskcontrol = function(riskcontrolList) {
		riskcontrolList.push({
			agencyFeescaleSectionDtos: addagencyFeescaleSectionDtos()
		});
	}

	$scope.delRiskcontrol = function(index, riskcontrol) {
		riskcontrol.splice(index, 1);

	}

	$scope.addFeescaleSection = function(feescaleSectionList) {
		feescaleSectionList.push({
			agencyFeescaleDetailDtos: addagencyFeescaleDetailDtos()
		});
	}

	$scope.delFeescaleSection = function(index, feescaleSectionList) {
		if(index == 1) {
			feescaleSectionList[2].section = feescaleSectionList[0].section;
		} else if(index == feescaleSectionList.length) {
			feescaleSectionList[feescaleSectionList.length].section = feescaleSectionList[index - 2].sectionTemp;
		} else {
			if(!feescaleSectionList[index - 1].sectionTemp && !feescaleSectionList[index + 1].section) {

			} else if(!feescaleSectionList[index + 1].section) {
				feescaleSectionList[index + 1].section = feescaleSectionList[index - 1].sectionTemp;
			} else if(!feescaleSectionList[index - 1].sectionTemp) {
				feescaleSectionList[index - 1].sectionTemp = feescaleSectionList[index + 1].section;
			} else {
				feescaleSectionList[index - 1].sectionTemp = feescaleSectionList[index + 1].section;
			}
		}
		feescaleSectionList.splice(index, 1);
	}

	$scope.addFeescaleDetai = function(feescaleDetailList) {
		feescaleDetailList.push(new Object());
	}

	$scope.delFeescaleDetai = function(index, feescaleDetailList) {
		if(index == 1) {
			feescaleDetailList[0].maxfield = feescaleDetailList[2].field;
		} else if(index == feescaleDetailList.length - 2) {
			feescaleDetailList[feescaleDetailList.length - 1].field = feescaleDetailList[index - 1].maxfield;
		} else {
			if(!feescaleDetailList[index - 1].maxfield && !feescaleDetailList[index + 1].field) {

			} else if(!feescaleDetailList[index + 1].field) {
				feescaleDetailList[index + 1].field = feescaleDetailList[index - 1].maxfield;
			} else if(!feescaleDetailList[index - 1].field) {
				feescaleDetailList[index - 1].maxfield = feescaleDetailList[index + 1].field;
			} else {
				feescaleDetailList[index - 1].maxfield = feescaleDetailList[index + 1].field;
			}
		}
		feescaleDetailList.splice(index, 1);
	}

	$scope.isShowError = false;

	$scope.addOrEdit = function() {
		//校验
		if($scope.feescaleForm.$invalid) {
			$scope.isShowError = true;
			box.boxAlert("请检查红色错误信息");
			return;
		}

		$scope.agencyTypeFeescaleDto.agencyTypeId = $scope.agencyId;

		var len = $scope.agencyTypeFeescaleDto.agencyFeescaleRiskcontrolDtos.length;
		if(len > 1) {
			for(var i = 0; i < len; i++) {
				for(var j = len - 1; j > 0; j--) {
					var gradei = $scope.agencyTypeFeescaleDto.agencyFeescaleRiskcontrolDtos[i].grade;
					var gradej = $scope.agencyTypeFeescaleDto.agencyFeescaleRiskcontrolDtos[j].grade;
					if(gradei == gradej && i != j) {
						box.boxAlert("不能选择相同收费标准!");
						return;
					}
				}
			}
		}
		var obj = new Array();
		angular.forEach($scope.agencyTypeFeescaleDto.agencyFeescaleRiskcontrolDtos, function(data) {
			var param = {
				"agencyTypeId": $scope.agencyId,
				"productionid": $scope.productionid,
				"riskGradeId": data.grade,
				"servicefee": data.servicefee,
				"counterfee": data.counterfee,
				"otherfee": data.otherfee,
				"agencyFeescaleRiskcontrolDtos": $scope.agencyTypeFeescaleDto.agencyFeescaleRiskcontrolDtos
			}
			obj.push(param);
		});

		$http({
			method: 'post',
			url: '/credit/user/agencyFeescale/v/batchUpdate',
			data: angular.toJson(obj)
		}).success(function(data) {
			if(data.code == 'SUCCESS') {
				box.boxAlert(data.msg, function() {
					$state.go("agencyMaintainEdit", {
						'agencyId': $scope.agencyId,
						"productCode": $scope.productCode,
						"orderNo": $scope.orderNo
					});
				});
			} else {
				box.boxAlert(data.msg);
			}
		});

	}

	function updateProduct(id) {
		$http({
			url: '/credit/customer/agencyProduct/v/update',
			method: 'POST',
			data: {
				"feescaleId": id,
				"id": $scope.productId
			}
		}).success(function(data) {
			if("SUCCESS" == data.code) {
				box.boxAlert(data.msg, function() {
					$state.go("agencyMaintainEdit", {
						'agencyId': $scope.agencyId,
						"productCode": $scope.productCode,
						"orderNo": $scope.orderNo,
						"tblName": "tbl_sm_",
						"source": "maintain"
					});
				})
			} else {
				box.boxAlert(data.msg)
			}
		});

	}
	$scope.setSection = function(list, index, section, i) {
		if(index == 0) {
			list[index + 1].section = section;
		} else if(list.length == index + 1) {
			if(list.length == 2) {
				list[index - 1].section = section;
			} else {
				list[index - 1].sectionTemp = section;
			}
		} else if(index == 1) {
			if(i > 0) {
				list[index + 1].section = section;
			} else {
				list[index - 1].section = section;
			}
		} else {
			if(i > 0) {
				list[index + 1].section = section;
			} else {
				list[index - 1].sectionTemp = section;
			}
		}
	}

	$scope.setField = function(list, index, field, i) {
		if(index == 0) {
			list[index + 1].field = field;
		} else if(list.length == index + 1) {
			if(list.length == 2) {
				list[index - 1].maxfield = field;
			} else {
				list[index - 1].maxfield = field;
			}
		} else if(index == 1) {
			if(i > 0) {
				list[index - 1].maxfield = field;
			} else {
				list[index + 1].field = field;
			}
		} else {
			if(i > 0) {
				list[index - 1].maxfield = field;
			} else {
				list[index + 1].field = field;
			}
		}
	}

	//取消
	$scope.cancel = function() {
		var cancelMethod = function() {
			$state.go("agencyTypeFeescaleList", {
				agencyTypeId: $scope.agencyTypeId
			});
		}
		box.confirmAlert("提示", "确定取消吗？", cancelMethod);
	}

	$scope.restart = function() {
		var i = $scope.agencyTypeFeescaleDto.agencyFeescaleRiskcontrolDtos.length;
		var tmpArray = new Array();
		for(var j = 0; j < i; j++) {
			tmpArray.push({
				agencyFeescaleSectionDtos: addagencyFeescaleSectionDtos()
			});
		}
		$scope.agencyTypeFeescaleDto = new Object();
		$scope.agencyTypeFeescaleDto.productionid = $scope.productionid;
		$scope.agencyTypeFeescaleDto.agencyFeescaleRiskcontrolDtos = tmpArray;
	}
});