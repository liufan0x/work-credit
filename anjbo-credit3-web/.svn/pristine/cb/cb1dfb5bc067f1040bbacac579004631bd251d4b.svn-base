angular.module("anjboApp").controller("orderCommonEditCtrl", function($scope, $http, $state, $timeout, route, box, $compile, process) {

	var processId = route.getParams().processId;
	$scope.cityCode = route.getParams().cityCode;
	$scope.productCode = route.getParams().productCode;

	$scope.pageConfigDto = new Object();
	$scope.isEnterprise = false;
	var params = {
		"orderNo": route.getParams().orderNo,
		"processId": processId
	}

	$http({
		method: 'POST',
		url: '/credit/order/flow/v/selectOrderFlowListRepeat',
		data: params
	}).success(function(data) {
		if(data.code == "SUCCESS") {
			$scope.orderFlowList = data.data;
		}
	})

	$scope.showDetail = function(processId) {
		var view = process.processIdTransformationDirective(processId);
		angular.element("#viewRow").html($compile('<' + view + '></' + view + ">")($scope));

	}

	$scope.goBack = function() {
		$state.go("orderList");
	}

	//切换tab页
	$scope.changeView = function(pageTabConfigDto) {
		//		if($scope.pageTabConfigDto) {
		//			if($scope.pageTabConfigDto.title != '影像资料') {
		//				if(!$scope.save(true, true)) {
		//					return false;
		//				}
		//			}
		//		}

		$scope.showView = pageTabConfigDto.title;
		$scope.pageTabConfigDto = pageTabConfigDto;
		if(pageTabConfigDto.title == "询价/查档/诉讼" || pageTabConfigDto.title == "影像资料") {
			pageTabConfigDto.isShow = true;
			return false;
		}

		//设置事件
		angular.forEach(pageTabConfigDto.pageTabRegionConfigDtos, function(pageTabRegionConfigDto) {
			angular.forEach(pageTabRegionConfigDto.valueList, function(data2, index) {
				showHide(data2);
				requestUrl(data2);
			});
		});
	}

	
	//计算负债比列
	$scope.calculationLiabilitiesProportion = function() {
		var allHouseWorthValue = 0;
		var creditLiabilitiesValue = 0;
		angular.forEach($scope.pageConfigDto.pageTabConfigDtos[6].pageTabRegionConfigDtos[0].valueList[0], function(data3) {
			if(data3.key == 'allHouseWorth') {
				allHouseWorthValue = data3.value;
			} else if(data3.key == 'creditLiabilities') {
				creditLiabilitiesValue = data3.value;
			} else if(data3.key == 'liabilitiesProportion') {
				if(creditLiabilitiesValue && allHouseWorthValue && !$scope.liabilitiesProportion) {
					data3.value = div(mul(creditLiabilitiesValue, 100), allHouseWorthValue).toFixed(2);
				}
			}
		});
	}

	function div(a, b) {
		var c, d, e = 0,
			f = 0;
		try {
			e = a.toString().split(".")[1].length;
		} catch(g) {}
		try {
			f = b.toString().split(".")[1].length;
		} catch(g) {}
		return c = Number(a.toString().replace(".", "")), d = Number(b.toString().replace(".", "")), c / d * Math.pow(8, f - e);
	}

	function mul(a, b) {
		var c = 0,
			d = a.toString(),
			e = b.toString();
		try {
			c += d.split(".")[1].length;
		} catch(f) {}
		try {
			c += e.split(".")[1].length;
		} catch(f) {}
		return Number(d.replace(".", "")) * Number(e.replace(".", "")) / Math.pow(8, c);
	}

	$scope.initPageConfig = function(fl) {
		//获取页面配置
		$http({
			method: 'POST',
			url: "/credit/config/page/pc/base/v/pageConfig",
			data: params
		}).success(function(data) {
			$scope.pageConfigDto = data.data;
			if(fl) {
				$scope.changeView($scope.pageConfigDto.pageTabConfigDtos[0]);
			}
			//指派渠道经理，加评估对象详情

			angular.forEach($scope.pageConfigDto.pageTabConfigDtos, function(pageTabConfigDto) {
				angular.forEach(pageTabConfigDto.pageTabRegionConfigDtos, function(pageTabRegionConfigDto) {
					angular.forEach(pageTabRegionConfigDto.valueList, function(data2, index) {
						showHide(data2);
						requestUrl(data2);

						angular.forEach(data2, function(formData, index) {
							if(formData.key == 'allHouseWorth') {
								$scope.$watch(function() {
									return formData.value;
								}, function(newValue, odlValue) {
									$scope.calculationLiabilitiesProportion();
								});
							} else if(formData.key == 'creditLiabilities') {
								$scope.$watch(function() {
									return formData.value;
								}, function(newValue, odlValue) {
									$scope.calculationLiabilitiesProportion();
								});
							} else if(formData.key == 'liabilitiesProportion') {
								$scope.liabilitiesProportion = formData.value;
							}
						});

						angular.forEach(data2, function(data3) {
							if(data3.type == 5 && data3.value) {
								data3.dataList = new Array();
								angular.forEach(data3.value.split(','), function(data) {
									data3.dataList.push(data);
								})
							}

							if(data3.specialKey == 'customerType') {
								if(data3.value == "个人") {
									$scope.isEnterprise = false;
								} else {
									$scope.isEnterprise = true;
								}
							}

						});
					});
				});
			});
		});
	}

	$scope.initPageConfig(true);

	//新增区域
	$scope.addList = function(list, regionClass) {
		params.regionClass = regionClass;
		$http({
			method: 'POST',
			url: "/credit/config/page/pc/base/v/getPageTabRegionConfigDto",
			data: params
		}).success(function(data) {
			list.push(data.data);
			//设置事件
			showHide(data.data);
			requestUrl(data.data);
		});
	}

	//删除区域
	$scope.deleteList = function(list, index, deleteUrl) {
		list.splice(index, 1);
	}

	//设置下拉框值
	$scope.setSelect = function(data, list) {
		angular.forEach(list, function(data1) {
			if(data1.id == data.specialValue) {
				data.value = data1.name;
			}
			if(data.specialValue == '') {
				data.value = '';
			}
		});
	}

	//上传图片，需要知道是那个表单的上传图片，故设置临时表单
	$scope.showUploadImg = function(formDto) {
		$scope.tempFormDto = formDto;
	}

	//上传图片
	$scope.upload = function(url, simg, name) {
		//未选择图片时不调用后台
		if(url && url != '') {
			if(!$scope.tempFormDto.dataList) {
				$scope.tempFormDto.dataList = new Array();
			}
			$timeout(function() {
				angular.forEach(url.split(','), function(data) {
					$scope.tempFormDto.dataList.push(data);
				})
			});
		}
	}

	//显示删除图片
	$scope.showDelImg = function(obj) {
		$scope.isShowDelImg = true;
		$scope.tempObject = obj;
	}

	//删除图片
	$scope.delImg = function() {
		var tempList = $scope.tempObject.dataList.concat();
		$scope.tempObject.dataList = new Array();
		angular.forEach(tempList, function(data) {
			var fl = true;
			$("input[name='imgIds']:checked").each(function() {
				if($(this).val() == data) {
					fl = false;
				}
			});
			if(fl) {
				$scope.tempObject.dataList.push(data);
			}
		})
		$scope.isShowDelImg = false;
	}

	//显示隐藏
	function showHide(formList) {
		angular.forEach(formList, function(data, index, array) {
			if(data.paramsValuesJosn && data.paramsValuesJosn.show) {
				angular.forEach(formList, function(data1, index, array) {
					angular.forEach(data.paramsValuesJosn.show, function(showValue, showKey, array) {
						if(showKey == data1.key) {
							watchShowHide(formList, data, data1);
						} else if(showKey == data1.specialKey) {
							watchShowHide(formList, data, data1, 'specialValue');
						}
					});
				});
			}
		});
	}

	//监控显示隐藏
	function watchShowHide(formList, data, watchData, specialStr) {
		$scope.$watch(function() {
			if(specialStr == 'specialValue') {
				return watchData.specialValue;
			} else {
				return watchData.value;
			}
		}, function(newValue, odlValue) {
			var fl = false;
			angular.forEach(data.paramsValuesJosn.show, function(showValue, showKey, array) {
				fl = false;
				angular.forEach(formList, function(data1, index, array) {
					var values = showValue.split(",");
					if(data1.specialKey == showKey) {
						fl = !($.inArray(data1.specialValue, values) >= 0);
					} else if(data1.key == showKey) {
						fl = !($.inArray(data1.value, values) >= 0);
					}
				});
			});
			data.isHide = fl;
		});
	}

	//请求参数填充下拉框
	function requestUrl(formList) {
		angular.forEach(formList, function(data, index, array) {
			if(data.type == 3) {
				if(data.paramsValuesJosn && data.paramsValuesJosn.params) {
					var tempParams = data.paramsValuesJosn.params.split(",");
					angular.forEach(formList, function(data1, index, array) {
						angular.forEach(tempParams, function(params) {
							if(tempParams == data1.key) {
								watchRequestUrl(formList, data, data1tempParams);
							} else if(tempParams == data1.specialKey) {
								watchRequestUrl(formList, data, data1, tempParams, 'specialValue');
							}
						});
					});
				} else {
					var params = {};
					var url = data.typeDepend;
					if(data.typeDepend.indexOf("?") != -1) {
						url = data.typeDepend.split('?')[0];
						var str = data.typeDepend.split('?')[1];
						var tempStr = str.split('&');
						angular.forEach(tempStr, function(data) {
							var val = data.split('=')[1];
							if(val.indexOf("{") != -1 && val.indexOf("}") != -1) {
								val = val.replace("{", "").replace("}", "");
								params[data.split('=')[0]] = $scope[val];
							} else {
								params[data.split('=')[0]] = val;
							}
						});
					}

					//没有参数，默认请求一遍
					$http({
						method: 'POST',
						url: url,
						data: params
					}).success(function(tempData) {
						data.dataList = tempData.data;
					});
				}
			}
		});
	}

	//监控请求
	function watchRequestUrl(formList, data, watchData, tempParams, specialStr) {
		$scope.$watch(function() {
			if(specialStr == 'specialValue') {
				return watchData.specialValue;
			} else {
				return watchData.value;
			}
		}, function(newValue, odlValue) {

			if(!newValue) {
				data.dataList = new Array();
				data.specialValue = "";
			}

			var params = {};
			angular.forEach(formList, function(data1, index, array) {
				if($.inArray(data1.specialKey, tempParams) >= 0) {
					params[data1.specialKey] = data1.specialValue;
				} else if($.inArray(data1.key, tempParams) >= 0) {
					params[data1.key] = data1.value;
				}
			});

			//截取参数
			var url = data.typeDepend;
			if(data.typeDepend.indexOf("?") != -1) {
				url = data.typeDepend.split('?')[0];
				var str = data.typeDepend.split('?')[1];
				var tempStr = str.split('&');
				angular.forEach(tempStr, function(data) {
					var val = data.split('=')[1];
					if(val.indexOf("{") != -1 && val.indexOf("}") != -1) {
						val = val.replace("{", "").replace("}", "");
						if($scope[val]) {
							params[data.split('=')[0]] = $scope[val];
						} else {
							params[data.split('=')[0]] = params[val];
						}
					} else {
						params[data.split('=')[0]] = val;
					}
				});
			}

			$http({
				method: 'POST',
				url: url,
				data: params
			}).success(function(tempData) {
				if(tempData.code == 'SUCCESS') {
					data.dataList = tempData.data;
					var fl = true;
					angular.forEach(data.dataList, function(data1, index, array) {
						if(data1.id == data.specialValue) {
							fl = false;
						}
					});
					if(fl) {
						data.specialValue = "";
					}
				}
			});
		});
	}

	//type=0 保存
	//type=1 提交
	function getFormPrams(formList, params, type, showView) {
		var fl = false;
		angular.forEach(formList, function(data, index, array) {
			if(data.isHide) {
				return false;
			}

			if(data.type == 5) {
				if(!data.value) {
					data.value = "";
					angular.forEach(data.dataList, function(data1, index, array) {
						if(index == array.length - 1) {
							data.value += data1;
						} else {
							data.value += data1 + ",";
						}
					});
				} else if(data.dataList.length == 0) {
					data.value = "";
				}
			} else {
				//特殊值
				if(data.specialKey) {
					params[data.specialKey] = data.specialValue;
				}
			}

			//组装参数
			params[data.key] = data.value;

			//校验非空，提交校验非空
			if(data.isNeed == 2 && type != 0) {
				if(!data.value) {
					data.errorInfo = "必填";
					//跳转到校验不通过页面
					if(showView) {
						$timeout(function() {
							$scope.changeView(showView);
						});
					}
					fl = true;
					return true;
				} else {
					data.errorInfo = "";
				}
			} else if(type == 0) {
				//如果是保存,则清空非空校验错误信息
				data.errorInfo = "";
			}

			//校验正确性
			if(data.value && data.regular) {
				var reg = eval(data.regular);
				if(!reg.test(data.value)) {
					data.errorInfo = "错误";
					//跳转到校验不通过页面
					if(showView) {
						$timeout(function() {
							$scope.changeView(showView);
						});
					}
					fl = true;
					return true;
				} else {
					data.errorInfo = "";
				}
			}
		});
		return fl;
	}

	//type=0 保存
	//type=1 提交
	function getRegionParams(pageTabRegionConfigDto, params, type, showView) {
		var errorInfo = "";
		if(pageTabRegionConfigDto.key && pageTabRegionConfigDto.type == 2) {
			params[pageTabRegionConfigDto.key] = new Array();
		}
		angular.forEach(pageTabRegionConfigDto.valueList, function(data) {
			//区域有可能是单独的一个对象,这时就需要一个key
			if(pageTabRegionConfigDto.key) {
				var tempParams = {};
				if(getFormPrams(data, tempParams, type, showView)) {
					errorInfo = "请检查红色错误信息";
				}
				if(!errorInfo) {
					tempParams.isFinish = 1;
				} else {
					tempParams.isFinish = 2;
				}
				if(pageTabRegionConfigDto.type == 1) {
					params[pageTabRegionConfigDto.key] = tempParams;
				} else if(pageTabRegionConfigDto.type == 2) {
					params[pageTabRegionConfigDto.key].push(tempParams);
				}
			} else {
				if(getFormPrams(data, params, type, showView)) {
					errorInfo = "请检查红色错误信息";
				}
			}
			if(!errorInfo) {
				params.isFinish = 1;
			} else {
				params.isFinish = 2;
			}
		});
		return errorInfo;
	}

	//保存
	//只保存当前tab
	$scope.save = function(fl, switchTab) {
		if(!fl) {
			box.waitAlert();
		}
		var errorInfo = "";
		var params = {
			orderNo: route.getParams().orderNo,
			tblName: route.getParams().tblName,
			productCode: route.getParams().productCode,
			cityCode: route.getParams().cityCode,
		};
		params[$scope.pageTabConfigDto.tblName] = new Object();
		//组装参数
		angular.forEach($scope.pageTabConfigDto.pageTabRegionConfigDtos, function(data) {
			errorInfo = getRegionParams(data, params[$scope.pageTabConfigDto.tblName], 0);
		});
		params.tabClass = $scope.pageTabConfigDto.tabClass;
		params.tblName = $scope.pageTabConfigDto.tblName;
		params.saveButUrl = $scope.pageTabConfigDto.saveButUrl;
		return $scope.postJSON("/credit/config/page/pc/base/v/savePageTabConfigDto", params, errorInfo, 0, fl, switchTab);
	}

	//提交
	//要保存整个页面
	$scope.submit = function() {

		var errorInfo = "";
		var params = {
			orderNo: route.getParams().orderNo,
			tblName: route.getParams().tblName,
			productCode: route.getParams().productCode,
			cityCode: route.getParams().cityCode,
			data: new Object()
		};
		//组装参数
		angular.forEach($scope.pageConfigDto.pageTabConfigDtos, function(data) {
			params[data.tblName] = new Object();
			angular.forEach(data.pageTabRegionConfigDtos, function(data1) {
				if(errorInfo) {
					getRegionParams(data1, params[data.tblName], 1, data)
				} else {
					errorInfo = getRegionParams(data1, params[data.tblName], 1, data);
				}
			});
		})

		if(!errorInfo) {
			$http({
				method: 'POST',
				url: "/credit/order/borrow/v/check",
				data: {
					"orderNo": route.getParams().orderNo,
				}
			}).success(function(data) {
				if(data.code == "SUCCESS") {
					box.closeAlert();
					if($scope.orderFlowList && $scope.orderFlowList.length > 0) {
						box.confirmAlert("提交审核", "确定提交审批吗？", function() {
							box.waitAlert();
							$http({
								method: 'POST',
								url: "/credit/order/borrow/v/submitAudit",
								data: {
									"orderNo": route.getParams().orderNo,
								}
							}).success(function(data) {
								box.closeWaitAlert();
								box.boxAlert(data.msg, function() {
									if(data.code == "SUCCESS") {
										box.closeAlert();
										$state.go("orderList");
									}
								});
							})
						});
					} else {
						$scope.personnelType = "分配订单";
						box.editAlert($scope, "订单提交审核吗，请选择分配订单员", "<submit-box></submit-box>", function() {
							box.waitAlert();
							if(!$scope.sumbitDto.uid) {
								box.closeWaitAlert();
								box.boxAlert("请选择分配订单员！");
								return false;
							}
							$scope.save(true, true);
							var dataParams = {
								'currentHandlerUid': $scope.sumbitDto.uid,
								'orderNo': route.getParams().orderNo
							}
							$scope.postJSON("/credit/config/page/pc/base/v/submitPageConfigDto", dataParams, errorInfo, 1);
						});
					}
				} else {
					box.boxAlert(data.msg,function(){
						if($scope.productCode=='06'||$scope.productCode=='07'){
							$scope.changeView($scope.pageConfigDto.pageTabConfigDtos[3]);
						}
					});
					//box.boxAlert(data.msg);
				}
			})

		}

	}

	//请求接口
	$scope.postJSON = function(url, params, errorInfo, type, fl, switchTab) {

		if(errorInfo) {
			box.closeWaitAlert();
			box.boxAlert(errorInfo);
			return false;
		}

		//保存tab
		$http({
			method: 'POST',
			url: url,
			data: params
		}).success(function(data) {
			box.closeWaitAlert();
			if(data.code == "SUCCESS" && !fl) {
				box.boxAlert(data.msg, function() {
					$scope.initPageConfig();
					if(type == 1) {
						box.closeAlert();
						$state.go("orderList");
					}
				});
			} else {
				if(!switchTab) {
					box.boxAlert(data.msg);
				}
			}
		}).error(function() {
			box.closeWaitAlert();
			box.boxAlert("请求失败，请联系开发部");
		});
		return true;
	}

});