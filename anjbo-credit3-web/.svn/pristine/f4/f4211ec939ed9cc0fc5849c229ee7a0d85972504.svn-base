angular.module("anjboApp").controller("productDataEditCtrl", function($scope, $http,$state, $timeout, route, box) {

	var processId = route.getParams().processId;
	var pageClass = route.getParams().tblName + processId + '_page';
	$scope.pageConfigDto = new Object();

	var params = {
		"orderNo": route.getParams().orderNo,
		"pageClass": pageClass
	}

	$scope.goBack = function(){
		$state.go("productDataList",{"productCode":"10000","productName":"云按揭"});
	}

	//切换tab页
	$scope.changeView = function(pageTabConfigDto) {
		if($scope.pageTabConfigDto){
			if($scope.pageTabConfigDto.title != '影像资料'){
				if(!$scope.save(true,true)){
					return false;
				}	
			}
		}
		$scope.showView = pageTabConfigDto.title;
		$scope.pageTabConfigDto = pageTabConfigDto;
	}

	//设置表单值
	function setValue(formList, valueData) {
		angular.forEach(formList, function(data, index, array) {
			console.log(valueData[data.key]);
			//渠道经理选定后不可修改
			if(data.key=='channelManagerName'){
				var scopeFlow =  angular.element('common-flow').scope();
				if(scopeFlow.orderFlowList.length > 0&&valueData[data.key]&&data.formClass!='repaymentChannelManager'){
					data.isReadOnly = 2;
				}
			}
			
			if(!data.value){
				data.value = valueData[data.key];
			}else if(valueData[data.key]){
				data.value = valueData[data.key];
			}else if(typeof valueData[data.key]==typeof null&&typeof valueData[data.key]!=typeof undefined){//null则替换，undefined不替换，因为有默认值
				data.value = valueData[data.key];
			}
			
			if(!data.specialValue){
				data.specialValue = valueData[data.specialKey];
			}else if(valueData[data.specialKey]){
				data.specialValue = valueData[data.specialKey];
			}else if(typeof valueData[data.specialKey]==typeof null&&typeof valueData[data.specialKey]!=typeof undefined){
				data.specialValue = valueData[data.specialKey];
			}
			
			if(data.type == 5) {
				data.dataList = new Array();
				if(data.value){
					$timeout(function() {
						angular.forEach(data.value.split(','), function(data1) {
							data.dataList.push(data1)
						})
					});
				}
			}
		});
	}

	//设置表单值
	function setData(pageTabConfigDto) {
		$http({
			method: 'POST',
			url: "/credit/product/data/base/v/select",
			data: {
				orderNo : params.orderNo,
				tblName : pageTabConfigDto.tblName
			}
		}).success(function(data) {
//			data.showText = "<span class='text-danger'>此客户为建行存量客户，只需完善贷款信息和影像资料。</span>";
			if(data.data.showText){
				pageTabConfigDto.showText = data.data.showText;
				pageTabConfigDto.isShow = true;
				pageTabConfigDto.pageTabRegionConfigDtos = new Array();
			}else{
				angular.forEach(pageTabConfigDto.pageTabRegionConfigDtos, function(data1) {
					if(data1.key && data.data) {
						var tempData = angular.fromJson(data.data.data)[data1.key];
						angular.forEach(tempData, function(data2, index) {
							var tempList = new Array();
							if(index > 0) {
								angular.forEach(data1.valueList[0].concat(), function(data3, index) {
									tempList.push(angular.copy(data3));
								});
								data1.valueList.push(tempList);
							} else {
								tempList = data1.valueList[0].concat();
							}
							setValue(tempList, data2);
							showHide(tempList);
							enableDisable(tempList);
							requestUrl(tempList);
						});
					} else {
						angular.forEach(data1.valueList, function(data2, index) {
							if(data.data) {
								setValue(data2, angular.fromJson(data.data.data));
							}
							showHide(data2);
							enableDisable(data2);
							requestUrl(data2);
						});
					}
				});
			}
		});
	}

	//获取页面配置
	$http({
		method: 'POST',
		url: "/credit/config/page/base/v/pageConfig",
		data: params
	}).success(function(data) {
		$scope.pageConfigDto = data.data;
		$scope.changeView($scope.pageConfigDto.pageTabConfigDtos[0]);
		//指派渠道经理，加评估对象详情
		console.log(processId);
		if(processId=='repaymentChannelManager'){
			$scope.addDetail("assess");
		}
		//将表单赋予事件
		angular.forEach($scope.pageConfigDto.pageTabConfigDtos, function(data) {
			setData(data);
		});

	});

	//新增区域
	$scope.addList = function(list, regionClass) {
		params.regionClass = regionClass;
		$http({
			method: 'POST',
			url: "/credit/config/page/base/v/getPageTabRegionConfigDto",
			data: params
		}).success(function(data) {
			list.push(data.data);
		});
	}

	//删除区域
	$scope.deleteList = function(list, index, deleteUrl) {
		list.splice(index, 1);
		console.log(deleteUrl);
	}

	//设置下拉框值
	$scope.setSelect = function(data, list) {
		angular.forEach(list, function(data1) {
			if(data1.id == data.specialValue) {
				data.value = data1.name;
			}
			if(data.specialValue == ''){
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
		if(url&&url!=''){
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
			if(data.params && data.paramsType.indexOf("1") >= 0) {
				angular.forEach(formList, function(data1, index, array) {
					if(data.params == data1.key || data.params == data1.specialKey) {
						watchShowHide(data, data1);
					}
				});
			}
		});
	}

	//监控显示隐藏
	function watchShowHide(data, data1) {
		$scope.$watch(function() {
			if(data.params.indexOf("special") >= 0) {
				return data1.value;
			} else {
				return data1.specialValue;
			}
		}, function(newValue, odlValue) {
			var values = data.values.split("|.|")[0]
			var tempShowValues = values.split("|,|");
			data.isHide = !($.inArray(newValue, tempShowValues) >= 0);
		});
	}

	//启用禁用
	function enableDisable(formList) {
		angular.forEach(formList, function(data, index, array) {
			if(data.params && data.paramsType.indexOf("2") >= 0) {
				angular.forEach(formList, function(data1, index, array) {
					if(data.params == data1.key || data.params == data1.specialKey) {
						watchEnableDisable(data,data1);
					}
				});
			}
		});
	}
	
	//监控启用禁用
	function watchEnableDisable(data,data1) {
		$scope.$watch(function() {
			if(data.params.indexOf("special") >= 0) {
				return data1.value;
			} else {
				return data1.specialValue;
			}
		}, function(newValue, odlValue) {
			var values = data.values.split("|.|")[1]
			var tempShowValues = values.split("|,|");
			data.isDisable = !($.inArray(newValue, tempShowValues) >= 0);
		});
	}

	//请求参数填充下拉框
	function requestUrl(formList) {
		angular.forEach(formList, function(data, index, array) {
			if(data.params && data.paramsType.indexOf("3") >= 0) {
				var tempParams = data.params.split("|,|");
				angular.forEach(tempParams, function(data1, index, array) {
					angular.forEach(formList, function(data2, index, array) {
						if(data1 == data2.key) {
							$scope.$watch(function() {
								return data2.value;
							}, function(newValue, odlValue) {
								if(newValue) {
									var params = {};
									angular.forEach(formList, function(data3, index, array) {
										if($.inArray(data3.key, tempParams) >= 0) {
											params[data3.key] = data3.value;
										}
									});
									$http({
										method: 'POST',
										url: data.url,
										data: params
									}).success(function(tempData) {
										data.dataList = tempData.data;
									});
								}
							});
						} else if(data1 == data2.specialKey) {
							$scope.$watch(function() {
								return data2.specialValue;
							}, function(newValue, odlValue) {
								if(newValue) {
									var params = {};
									angular.forEach(formList, function(data3, index, array) {
										if($.inArray(data3.specialKey, tempParams) >= 0) {
											params[data3.specialKey] = data3.specialValue;
										}
									});
									$http({
										method: 'POST',
										url: data.typeDepend,
										data: params
									}).success(function(tempData) {
										data.dataList = tempData.data;
									});
								}
							});
						}
					});
				});
			}else if(data.type == 3){
				$http({
					method: 'POST',
					url: data.typeDepend
				}).success(function(tempData) {
						data.dataList = tempData.data;
				});
			}
		});
	}

	//type=0 保存
	//type=1 提交
	function getFormPrams(formList, params, type) {
		var fl = false;
		angular.forEach(formList, function(data, index, array) {
			if(data.isHide) {
				return false;
			}
			if(data.type == 5) {
				data.value = "";
				angular.forEach(data.dataList, function(data1, index, array) {
					if(index == array.length - 1) {
						data.value += data1;
					} else {
						data.value += data1 + ",";
					}
				});
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
					if((data.formClass=='loanApplicant'||data.formClass=='marriageAndFamily'||data.formClass=='workAndIncome')&&$scope.pageTabConfigDto.title!='客户信息'){
						$scope.changeView($scope.pageConfigDto.pageTabConfigDtos[0]);
					}else if((data.formClass=='loanInfo'||data.formClass=='houseSupplementInfo'||data.formClass=='sellAccountInfo'||data.formClass=='relationCustomerInfo')&&$scope.pageTabConfigDto.title!='贷款信息'){
						$scope.changeView($scope.pageConfigDto.pageTabConfigDtos[1]);
					}else if(data.formClass=='imageData'&&$scope.pageTabConfigDto.title!='影像资料'){
						$scope.changeView($scope.pageConfigDto.pageTabConfigDtos[2]);
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
					if((data.formClass=='loanApplicant'||data.formClass=='marriageAndFamily'||data.formClass=='workAndIncome')&&$scope.pageTabConfigDto.title!='客户信息'){
						$scope.changeView($scope.pageConfigDto.pageTabConfigDtos[0]);
					}else if((data.formClass=='loanInfo'||data.formClass=='houseSupplementInfo'||data.formClass=='sellAccountInfo'||data.formClass=='relationCustomerInfo')&&$scope.pageTabConfigDto.title!='贷款信息'){
						$scope.changeView($scope.pageConfigDto.pageTabConfigDtos[1]);
					}else if(data.formClass=='imageData'&&$scope.pageTabConfigDto.title!='影像资料'){
						$scope.changeView($scope.pageConfigDto.pageTabConfigDtos[2]);
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
	function getRegionParams(pageTabRegionConfigDto, params, type) {
		var errorInfo = "";
		if(pageTabRegionConfigDto.key && pageTabRegionConfigDto.type == 2) {
			params[pageTabRegionConfigDto.key] = new Array();
		}
		angular.forEach(pageTabRegionConfigDto.valueList, function(data) {
			//区域有可能是单独的一个对象,这时就需要一个key
			if(pageTabRegionConfigDto.key) {
				var tempParams = {};
				if(getFormPrams(data, tempParams, type)) {
					errorInfo = "请检查红色错误信息";
				}
				if(pageTabRegionConfigDto.type == 1) {
					params[pageTabRegionConfigDto.key] = tempParams;
				} else if(pageTabRegionConfigDto.type == 2) {
					params[pageTabRegionConfigDto.key].push(tempParams);
				}
			} else {
				if(getFormPrams(data, params, type)) {
					errorInfo = "请检查红色错误信息";
				}
			}
		});
		return errorInfo;
	}

	//保存
	//只保存当前tab
	$scope.save = function(fl,switchTab) {
		if(!fl){
			box.waitAlert();	
		}
		var errorInfo = "";
		var params = {
			orderNo:route.getParams().orderNo,
			tblName:route.getParams().tblName,
			productCode:route.getParams().productCode,
			cityCode:"4403"
		};
		params[$scope.pageTabConfigDto.tblName] = new Object();
		//组装参数
		angular.forEach($scope.pageTabConfigDto.pageTabRegionConfigDtos, function(data) {
			errorInfo = getRegionParams(data, params[$scope.pageTabConfigDto.tblName], 0);
		});
		params.pageClass = pageClass;
		params.tabClass = $scope.pageTabConfigDto.tabClass;
		return $scope.postJSON("/credit/config/page/base/v/savePageTabConfigDto", params, errorInfo,0,fl,switchTab);
	}

	//提交
	//要保存整个页面
	$scope.submit = function() {
		box.waitAlert();
		var errorInfo = "";
		var params = {
			orderNo:route.getParams().orderNo,
			tblName:route.getParams().tblName,
			productCode:route.getParams().productCode,
			cityCode:"4403",
			data: new Object()
		};
		//组装参数
		angular.forEach($scope.pageConfigDto.pageTabConfigDtos,function(data){
			params[data.tblName] = new Object();
			var personIncome;
			var familyIncome;
			var maritalCode;
			angular.forEach(data.pageTabRegionConfigDtos, function(data1) {
				if(errorInfo){
					getRegionParams(data1, params[data.tblName], 1)
				}else{
					errorInfo = getRegionParams(data1, params[data.tblName], 1);
				}
				angular.forEach(data1.valueList, function(data) {
					angular.forEach(data, function(dataConfig) {
						//校验个人月收入和家庭月收入start
						if(dataConfig.key == 'maritalCode'){
							maritalCode = dataConfig.value;
						}
						if(dataConfig.key == 'personIncome'){
							personIncome = dataConfig.value;
						}
						if(dataConfig.key == 'familyIncome'){
							familyIncome = dataConfig.value;
						}
						//校验个人月收入和家庭月收入end
					})
				})
			});
			
			if(maritalCode == '已婚' ){
				if(familyIncome&&personIncome&&familyIncome<personIncome){
					alert('当婚姻状况为已婚时，家庭月收入需大于等于本人月收入');
					box.closeWaitAlert();
					errorInfo = 'false';
				}
			}
			if(maritalCode == '未婚' ){
				if(familyIncome&&personIncome&&familyIncome!=personIncome){
					alert('当婚姻状况为未婚时，家庭月收入需等于本人月收入');
					box.closeWaitAlert();
					errorInfo = 'false';
				}
			}
		})
		params.pageClass = pageClass;
		if(errorInfo!='false'){
			$scope.postJSON("/credit/config/page/base/v/submitPageConfigDto", params, errorInfo,1);
		}
	}

	//请求接口
	$scope.postJSON = function(url, params, errorInfo,type,fl,switchTab) {


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
			if(data.code == "SUCCESS" && !fl){
				box.boxAlert(data.msg,function(){
					if(type == 1){
						$state.go('productDataList',{'productCode':'10000','productName':'云按揭'});	
					}
				});
			}else{
				if(!switchTab){
					box.boxAlert(data.msg);
				}
			}
			console.log("success：" + data);
		}).error(function() {
			box.closeWaitAlert();
			box.boxAlert("请求失败，请联系开发部");
		});
		return true;
	}

});