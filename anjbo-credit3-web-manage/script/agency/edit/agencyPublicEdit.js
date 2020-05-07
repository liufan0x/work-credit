angular.module("anjboApp").controller("agencyPublicEditCtrl", function($scope, $http,$state, $timeout,$q,$sce,route, box,file,FileUploader) {

	var processId = route.getParams().processId;
	var cityCode = route.getParams().cityCode;
	var pageClass = route.getParams().tblName + processId + '_page';
	$scope.pageConfigDto = new Object();
	var params = {
		"orderNo": route.getParams().orderNo,
		"pageClass": pageClass
	}

	$scope.goBack = function(){
		$state.go("waitSignAgency");
	}

	//切换tab页
	$scope.changeView = function(pageTabConfigDto) {
		if($scope.pageTabConfigDto){
			if($scope.pageTabConfigDto.title != '机构影像资料'){
				if(!$scope.save(true,true)){
					return false;
				}	
			}
		}
		$scope.showView = pageTabConfigDto.title;
		$scope.pageTabConfigDto = pageTabConfigDto;
	}

	$scope.tblView = function(pageTabConfigDto){
		$scope.showView = pageTabConfigDto.title;
		$scope.pageTabConfigDto = pageTabConfigDto;
	}

	//设置表单值
	function setValue(formList, valueData) {
		angular.forEach(formList, function(data, index, array) {
			if(26==data.type){
				data.value = valueData[data.key];
				data.specialValue = valueData[data.specialKey];
			} else {
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
			//设置多选择框
			if(20==data.type&&data.value){
				var dataValueArray = data.value.split(",");
				angular.forEach(data.dataList,function(datavalue,indexb,arrayb){
					datavalue.check = false;
					angular.forEach(dataValueArray,function(dataa,indexa,arraya){
						if(datavalue.id==dataa||datavalue.name==dataa){
							datavalue.check = true;
						}
						if((indexa+1)==data.value.length){
							return;
						}
					});
				});
			}
			if(26==data.type&&data.specialValue){
				var dataValueArray = data.specialValue.split(",");
				angular.forEach(data.dataList,function(datavalue,indexb,arrayb){
					datavalue.checked = false;
					angular.forEach(dataValueArray,function(d,indexa,arraya){
						if(d==datavalue.id){
							datavalue.checked = true;
						}
					});
				});
			} else if(26==data.type){
				var tmp = new Array();
				angular.forEach(data.dataList,function(datavalue,indexb,arrayb){
					tmp.push(datavalue);
				});
				angular.forEach(tmp,function(d){
					d.checked = false;
				})
				data.dataList = tmp;
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

			if(data.data&&data.data.showText){
				pageTabConfigDto.showText = data.data.showText;
				pageTabConfigDto.isShow = true;
				pageTabConfigDto.pageTabRegionConfigDtos = new Array();
			}else{
				angular.forEach(pageTabConfigDto.pageTabRegionConfigDtos, function(data1) {
					setBr(data1);
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

	function setBr(obj){
		var tmp;
		angular.forEach(obj.valueList[0],function(data){
			if(data.title&&data.title.indexOf("<br>")>0){
				tmp = data.title;
				data.brValue = $sce.trustAsHtml(tmp);
				data.isBr = true;
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
	};
	//单选按钮
	$scope.setButton = function(m,data){
		angular.forEach(m.dataList,function(d){
			if(d.id==data.id||d.name==data.name){
				m.value = d.name;
				m.specialValue = d.id;
			}
		});
	}
	//多选按钮
	$scope.setButtons = function(m,data,formList){
		m.value = "";
		m.specialValue = "";
		data.checked = !data.checked;
		angular.forEach(m.dataList,function(d){
			if(d.checked){
				if(""==m.value){
					m.value = d.name;
					m.specialValue = d.id;
				} else {
					m.value += ","+d.name;
					m.specialValue += ","+d.id;
				}
			}
		});
	}
	//设置下拉框值
	$scope.setSelectProduct = function(data, list) {
		angular.forEach(list, function(data1) {
			if(data1.code == data.specialValue) {
				data.value = data1.name;
			}
			if(data.specialValue == ''){
				data.value = '';
			}
		});
	};


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
			} else if(data.params && data.paramsType.indexOf("4") >= 0){
				data.isParamsHide = false;
				if(data.params.indexOf("&")>=0){
					data.isParamsHide = true;
				}
				var formulaParams;
				var params;
				var values;
				var formulaValues;
				if(data.isParamsHide){
					formulaParams = data.params.split("&")[1];
					params = data.params.split("&")[0];
					values = data.values.split("&")[0];
					formulaValues = data.values.split("&")[1];
					data.params = params;
					data.values = values;
					data.formulaParams = formulaParams;
					data.formulaValues = formulaValues;
					angular.forEach(formList, function(data1, index, array) {
						if((data.params == data1.key || data.params == data1.specialKey)) {
							watchShowHide(data, data1);
						}
						if(data.formulaParams == data1.key || data.formulaParams == data1.specialKey) {
							watcFormula(data, data1,formList);
						}
					});
				} else {
					angular.forEach(formList, function(data1, index, array) {
						if(data.params == data1.key || data.params == data1.specialKey) {
							watcFormula(data, data1,formList);
						}
					});
				}

			} else if(data.params && data.paramsType.indexOf("5") >= 0){
				var formulaParams = data.params.split("&")[1];
				var params = data.params.split("&")[0];
				data.params = params;
				data.formulaParams = formulaParams;
				angular.forEach(formList, function(data1, index, array) {
					if(data.params == data1.key || data.params == data1.specialKey) {
						watchShowHide(data, data1);
					}
					if(data.formulaParams.indexOf("*")>=0) {
						var tmpArray = data.formulaParams.split("*");
						angular.forEach(tmpArray,function(data2){
							if(data2==data1.key || data2 == data1.specialKey){
								calculationFormula(data,data1,tmpArray,formList);
							}
						});
					}
				});
			}
		});
	}
	//计算
	function watcFormula(data, data1,formList){
		$scope.$watch(function() {
			if(data.params.indexOf("special") >= 0) {
				return data1.value;
			} else {
				return data1.specialValue;
			}
		}, function(newValue, odlValue) {
			var formulaValuesArray = new Array();
			if(data.isParamsHide){
				formulaValuesArray = data.formulaValues.split(",");
			} else {
				formulaValuesArray = data.values.split(",");
			}

			equalFormula(data,data1,newValue,odlValue,formulaValuesArray);
		});
	}
	function calculationFormula(data, data1,data2,formList){
		$scope.$watch(function() {
			return data1.value;
		}, function(newValue, odlValue) {
			multiFormula(data,data1,newValue,odlValue,data2,formList)
		});
	}
	/**
	 *例如:数据库配置1=100,2=200,当该字段对应的params的字段值=1时该字段的值=100
	 */
	function equalFormula(data,data1,newValue,odlValue,formulaValuesArray){
		if((!newValue||""==newValue)&&odlValue){
			data.isReadOnly = 1;
			data.value = "";
		}
		angular.forEach(formulaValuesArray,function(datas){
			if(datas.split("=")[0]==newValue){
				data.value = datas.split("=")[1];
				data.isReadOnly = 2;
			}
		});
	}

	/**
	 *
	 *例如:该字段数据库配置的params为 A&B*C 则会监听B与C并计算这两个的值,将计算的值设置为该字段的值
	 * 注:只能连乘
	 */
	function multiFormula(data,data1,newValue,odlValue,data2,formList){
		var val = 0;
		var tmp = 0;
		angular.forEach(formList,function(fdata){
			angular.forEach(data2,function(data3){
				if(fdata.key==data3&&0==tmp){
					tmp = fdata.value;
				} else if(fdata.key==data3){
					val = mul(tmp,fdata.value);
				}
			});

		})
		data.value = val;
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
										url: data.url,
										data: params
									}).success(function(tempData) {
										data.dataList = tempData.data;
									});
								}
							});
						}
					});
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
				if (!data.value&&data.type!=23) {
					data.errorInfo = "必填";
					//跳转到校验不通过页面
					if ((data.formClass == 'waitConfirm'
						|| data.formClass == 'waitInvestigation'
						|| data.formClass == 'waitToexamine'
						|| data.formClass == 'choiceProduct')
						&& ($scope.pageTabConfigDto.title != '机构立项申请')) {
						//$scope.changeView($scope.pageConfigDto.pageTabConfigDtos[0]);
						$scope.tblView($scope.pageConfigDto.pageTabConfigDtos[0]);
					}
					fl = true;
					return true;
				} else if (21 == data.type && !data.specialValue) {
					data.errorInfo = "必填";
					fl = true;
					return true;
				} else if (21 == data.type && (data.specialValue < data.value)) {
					data.errorInfo = "结束时间时间必须大于开始时间";
					fl = true;
					return true;
				} else if (data.type == 1 && data.typeDepend && data.value) {
					var re = eval(data.typeDepend);
					var arr = data.value.match(re);
					var testArr = new Array();
					angular.forEach(arr, function (data) {
						if ("" != data) {
							testArr.push(data);
						}
					});
					if (testArr.length > 1 || testArr.length <= 0) {
						data.errorInfo = "格式有误或超出长度";
						fl = true;
						return true;
					} else {
						data.errorInfo = "";
					}
				}else if(data.type==23){
					var filescope = angular.element('.common-file-refresh').scope();
					if(filescope){
						if(!filescope.files||filescope.files.length<=0){
							data.errorInfo = "请上传附件";
							fl = true;
						} else {
							data.value = filescope.files;
							data.errorInfo = "";
						}
					} else {
						var allscope = angular.element('.all-form-scope').scope();
						if(!allscope.uploader.files||allscope.uploader.files.length<=0){
							data.errorInfo = "请上传附件";
							fl = true;
						} else {
							data.value = allscope.uploader.files;
							data.errorInfo = "";
						}
					}
					if(""!=data.errorInfo){
						//跳转到校验不通过页面
						if ((data.formClass == 'waitConfirm'
							|| data.formClass == 'waitInvestigation'
							|| data.formClass == 'waitToexamine'
							|| data.formClass == 'choiceProduct')
							&& ($scope.pageTabConfigDto.title != '机构立项申请')) {
							//$scope.changeView($scope.pageConfigDto.pageTabConfigDtos[0]);
							$scope.tblView($scope.pageConfigDto.pageTabConfigDtos[0]);
						}
						return true;
					}
				} else {
					data.errorInfo = "";
				}
			} else if(type == 0){
				data.errorInfo = "";
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
					box.closeAlert();
				}
				if(pageTabRegionConfigDto.type == 1) {
					params[pageTabRegionConfigDto.key] = tempParams;
				} else if(pageTabRegionConfigDto.type == 2) {
					params[pageTabRegionConfigDto.key].push(tempParams);
				}
			} else {
				if(getFormPrams(data, params, type)) {
					errorInfo = "请检查红色错误信息";
					box.closeAlert();
				}
			}
		});

		if(pageTabRegionConfigDto.key && pageTabRegionConfigDto.type == 2&&params[pageTabRegionConfigDto.key].length>1) {
			var len = params[pageTabRegionConfigDto.key].length;
			for(var i=0;i<len;i++){
				for(var j=len-1;j>0;j--){
					var keyi = params[pageTabRegionConfigDto.key][i];
					var keyj = params[pageTabRegionConfigDto.key][j];

					if(keyi.finalApplyCity==keyj.finalApplyCity
						&&i!=j){
						var keyitmp = keyi.finalApplyProductCode;
						var keyjtmp = keyj.finalApplyProductCode;
						var keyitmparr = keyitmp.split(",");
						var keyjtmparr = keyjtmp.split(",");
						for(var k=0;k<keyjtmparr.length;k++){
							for (var f=0;f<keyitmparr.length;f++){
								if(keyjtmparr[k]==keyitmparr[f]){
									errorInfo = "同个城市不能选择相同产品";
									return errorInfo;
								}
							}

						}
					}
				}
			}
		}
		return errorInfo;
	}
	$scope.confirm = function(){
		box.editAlert($scope,"提示","是否对刚编辑的内容进行保存？",saveConfirm);
	}

	function saveConfirm (fl,switchTab){
		box.closeAlert();
		box.waitAlert();
		var errorInfo = "";
		var params = {
			orderNo:route.getParams().orderNo,
			tblName:route.getParams().tblName,
			productCode:route.getParams().productCode,
			cityCode:cityCode
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
			cityCode:cityCode
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

	$scope.checkSubmit = function(){
		box.waitAlert();

		var data = submitSetVal();
		var params = data.params;
		var tblName = data.tblName;
		var errorInfo = data.errorInfo;

		if(errorInfo!='false') {
			if (errorInfo) {
				box.closeWaitAlert();
				box.boxAlert(errorInfo);
				return false;
			} else {
				if("addAgency"==processId){
					$http({
						method: 'POST',
						url: '/credit/product/data/sm/agency/v/checkMobile',
						data:{"contactsPhone":params[tblName].contactsPhone,"orderNo":params.orderNo}
					}).then(function successCallback(response) {
						// 请求成功执行代码
						if("SUCCESS"==response.data.code){
							$scope.submit(params,errorInfo,tblName);
						} else {
							box.closeWaitAlert();
							box.boxAlert(response.data.msg);
						}
					}, function errorCallback(response) {
						// 请求失败执行代码
						box.closeWaitAlert();
						box.boxAlert("请求失败，请联系开发部");
					});
				} else {
					$scope.submit(params,errorInfo,tblName);
				}
			}

		}

	}
	//提交
	//要保存整个页面
	$scope.submit = function(params,errorInfo,tblName) {
		if("agencyWaitConfirm"==processId||"agencyWaitSign"==processId){
			checkImg(params,errorInfo,tblName,1);
		} else {
			$scope.postJSON("/credit/config/page/base/v/submitPageConfigDto", params, errorInfo,1);
		}
	}
	//当前节点是待立项时调用
	function WaitConfirm(params,errorInfo,tblName){
		$scope.remarks = "";
		$scope.personnelType = "尽调经理";
		$scope.choicePersonnel = "尽调经理";
		box.editAlert($scope, "请选择尽调经理", "<submit-box-text></submit-box-text>", function () {
			var uid = $scope.sumbitDto.uid;
			var name = "";
			if ("" == uid) {
				alert("请选择尽调经理");
				return false;
			} else {
				box.closeAlert();
				angular.forEach($scope.personnels, function (ud, uindex, uarray) {
					if (uid == ud.uid) {
						name = ud.name;
						return;
					}
				});
				params[tblName].investigationManagerName = name;
				params[tblName].investigationManagerUid = uid;
				params[tblName].remarks = $scope.remarks;
				box.waitAlert();
				$scope.postJSON("/credit/config/page/base/v/submitPageConfigDto", params, errorInfo,1);
			}

		});
	}
	//提交时组装参数
	function submitSetVal(){
		var tblName = "";
		var errorInfo = "";
		var params = {
			orderNo:route.getParams().orderNo,
			tblName:route.getParams().tblName,
			productCode:route.getParams().productCode,
			cityCode:cityCode,
			data: new Object()
		};

		angular.forEach($scope.pageConfigDto.pageTabConfigDtos,function(data,index){
			params[data.tblName] = new Object();
			if(0==index){
				tblName = data.tblName;
			}
			angular.forEach(data.pageTabRegionConfigDtos, function(data1) {
				if(errorInfo){
					getRegionParams(data1, params[data.tblName], 1)
				}else{
					errorInfo = getRegionParams(data1, params[data.tblName], 1);
				}

			});

		});
		params.pageClass = pageClass;
		var data = {
			params:params,
			errorInfo:errorInfo,
			tblName:tblName
		}
		return data;
	}
	//校验影像资料
	function checkImg(params,errorInfo,tblName,type){
		$http({
			method: 'POST',
			url: '/credit/product/data/businfo/base/verificationImgage',
			data: {
				"productCode":route.getParams().productCode,
				"tblName":route.getParams().tblName,
				"orderNo":route.getParams().orderNo
			}
		}).then(function successCallback(response) {
			box.closeWaitAlert();
			// 请求成功执行代码
			if("SUCCESS"==response.data.code&&response.data.data){
				if("tbl_sm_agencyWaitConfirm_page"==params.pageClass) {
					WaitConfirm(params,errorInfo,tblName);
				} else{
					$scope.postJSON("/credit/config/page/base/v/submitPageConfigDto", params, errorInfo,type);
				}
			}  else if("SUCCESS"==response.data.code&&!response.data.data){
				alert("请上传影像资料红色星号必传项!");
			} else {
				alert("校验影像资料异常，请联系开发部");
			}
		}, function errorCallback(response) {
			// 请求失败执行代码
			box.closeWaitAlert();
			box.boxAlert("请求失败，请联系开发部");
		});

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
				box.boxAlert(data.msg);
				if(1==type){
					$timeout(function(){
						box.closeAlert();
						$state.go('waitSignAgency');
					},1000);
				} else {
					$timeout(function(){
						box.closeAlert();
					},3000);
				}
			}else{
				if(!switchTab){
					box.boxAlert(data.msg);
				}
			}
		}).error(function() {
			box.closeWaitAlert();
			box.boxAlert("请求失败，请联系开发部");
		});
		return true;
	}
	//多选框赋值
	$scope.applyProductCheck = function(m,c){
		var checkValue = "";
		var specialValue = "";
		c.check = !c.check;
		angular.forEach(m.dataList,function(data,index,array){
			if(data.check&&""==checkValue){
				checkValue = data.id;
				specialValue = data.name;
			} else if(data.check){
				checkValue += ","+data.id;
				specialValue += ","+data.name;
			}
		});
		m.value = specialValue;
		m.specialValue = checkValue;
	}
	//单选框赋值
	$scope.radioCheck = function(m,specialValue,value){
		m.value = value;
		m.specialValue = specialValue;
	}
	var upload = file.fileuploader($scope,FileUploader,box);

	function mul(a, b) {
		if(!a||!b){
			return 0;
		}
		var c = 0,
			d = a.toString(),
			e = b.toString();
		try {
			c += d.split(".")[1].length;
		} catch (f) {}
		try {
			c += e.split(".")[1].length;
		} catch (f) {}
		return Number(d.replace(".", "")) * Number(e.replace(".", "")) / Math.pow(10, c);
	}
//确认立项
}).directive('submitBoxText', function($http, route) {
	return {
		restrict: "E",
		templateUrl: '/template/agency/common/submitBoxText.html',
		link: function(scope) {
			scope.sumbitDto = new Object();
			scope.sumbitDto.agencgId = "1";
			scope.sumbitDto.cityCode = "4403";
			scope.sumbitDto.uid = "";
			scope.personnels = new Array();
			$http({
				method: 'POST',
				url:'/credit/user/base/v/choicePersonnel',
				data:{"choicePersonnel":scope.choicePersonnel,"type":"roleName"}
			}).success(function(data){
				if("SUCCESS"==data.code){
					scope.personnels = data.data;
				}
			});
		}
	};
});