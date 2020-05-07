define(function(require, exports, module) {
	exports.extend = function(app) {

		app.directive('pageDetail', function($http, $state, $timeout, $sce, route, box) {
			return {
				restrict: "E",
				templateUrl: '/plugins/page-directive/pageDetail.html',
				transclude: true,
				link: function(scope) {
					
					scope.isConfigEdit = false;
					
					scope.goBack = function() {
						$state.go(route.getParams().state);
					}

					var processId = route.getParams().processId;
					scope.pageConfigDto = new Object();

					var params = {
						"orderNo": route.getParams().orderNo,
						"processId": route.getParams().processId,
						"productCode": route.getParams().productCode
					}

					function flowList() {
						$http({
							method: 'POST',
							url: '/credit/page/pageFlow/v/search',
							data: {
								"orderNo": route.getParams().orderNo
							}
						}).success(function(data) {
							if(data.code == "SUCCESS") {
								scope.orderFlowList = data.data;
								params.processId = scope.orderFlowList[scope.orderFlowList.length-1].currentProcessId;
								initPageConfig(true);
							}
						});
					}

					function initPageConfig(fl) {
						//获取页面配置
						$http({
							method: 'POST',
							url: "/credit/page/config/v/pageConfig",
							data: params
						}).success(function(data) {
							scope.pageConfigDto = data.data;
							if(fl) {
								scope.changeView(scope.pageConfigDto.pageTabConfigDtos[0]);
							}

							angular.forEach(scope.pageConfigDto.pageTabConfigDtos, function(pageTabConfigDto) {
								angular.forEach(pageTabConfigDto.pageTabRegionConfigDtos, function(pageTabRegionConfigDto) {
									angular.forEach(pageTabRegionConfigDto.valueList, function(data2, index) {
										showHide(data2);
										requestUrl(data2);

										angular.forEach(data2, function(data3) {

											if(26 == data3.type && data3.specialValue) {
												var dataValueArray = data3.specialValue.split(",");
												angular.forEach(data3.dataList, function(datavalue, indexb, arrayb) {
													datavalue.checked = false;
													angular.forEach(dataValueArray, function(d, indexa, arraya) {
														if(d == datavalue.id) {
															datavalue.checked = true;
														}
													});
												});
											} else if(26 == data3.type) {
												var tmp = new Array();
												angular.forEach(data3.dataList, function(datavalue, indexb, arrayb) {
													tmp.push(datavalue);
												});
												angular.forEach(tmp, function(d) {
													d.checked = false;
												})
												data3.dataList = tmp;
											}

										});
									});
								});
							});
						});
					}

					flowList();

					scope.showDetail = function(processId) {
						params.processId = processId;
						initPageConfig(true);
					}

					//切换tab页
					scope.changeView = function(pageTabConfigDto) {
						scope.showView = pageTabConfigDto.title;
						scope.pageTabConfigDto = pageTabConfigDto;

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

					//新增区域
					scope.addList = function(list, regionClass) {
						params.regionClass = regionClass;
						$http({
							method: 'POST',
							url: "/credit/page/config/v/pageTabRegionConfigDto",
							data: params
						}).success(function(data) {
							list.push(data.data);
						});
					}

					//删除区域
					scope.deleteList = function(list, index, deleteUrl) {
						list.splice(index, 1);
					}

					//设置下拉框值
					scope.setSelect = function(data, list) {
						angular.forEach(list, function(data1) {
							if(data1.id == data.specialValue) {
								data.value = data1.name;
							}
							if(data.specialValue == '') {
								data.value = '';
							}
						});
					}
					//单选框赋值
					scope.radioCheck = function(m, specialValue, value) {
						m.value = value;
						m.specialValue = specialValue;
					}

					//单选按钮
					scope.setButton = function(m, data) {
						angular.forEach(m.dataList, function(d) {
							if(d.id == data.id || d.name == data.name) {
								m.value = d.name;
								m.specialValue = d.id;
							}
						});
					}
					//多选按钮
					scope.setButtons = function(m, data, formList) {
						m.value = "";
						m.specialValue = "";
						data.checked = !data.checked;
						angular.forEach(m.dataList, function(d) {
							if(d.checked) {
								if("" == m.value) {
									m.value = d.name;
									m.specialValue = d.id;
								} else {
									m.value += "," + d.name;
									m.specialValue += "," + d.id;
								}
							}
						});
					}
					//设置下拉框值
					scope.setSelectProduct = function(data, list) {
						angular.forEach(list, function(data1) {
							if(data1.code == data.specialValue) {
								data.value = data1.name;
							}
							if(data.specialValue == '') {
								data.value = '';
							}
						});
					};

					//上传图片，需要知道是那个表单的上传图片，故设置临时表单
					scope.showUploadImg = function(formDto) {
						scope.tempFormDto = formDto;
					}

					//上传图片
					scope.upload = function(url, simg, name) {
						//未选择图片时不调用后台
						if(url && url != '') {
							if(!scope.tempFormDto.dataList) {
								scope.tempFormDto.dataList = new Array();
							}
							$timeout(function() {
								angular.forEach(url.split(','), function(data) {
									scope.tempFormDto.dataList.push(data);
									if(!scope.tempFormDto.value) {
										scope.tempFormDto.value = "";
										angular.forEach(scope.tempFormDto.dataList, function(data1, index, array) {
											if(index == array.length - 1) {
												scope.tempFormDto.value += data1;
											} else {
												scope.tempFormDto.value += data1 + ",";
											}
										});
									} else if(scope.tempFormDto.dataList.length == 0) {
										scope.tempFormDto.value = "";
									}
								})
							});
						}
					}

					//显示删除图片
					scope.showDelImg = function(obj) {
						scope.isShowDelImg = true;
						scope.tempObject = obj;
					}

					//删除图片
					scope.delImg = function() {
						var tempList = scope.tempObject.dataList.concat();
						scope.tempObject.dataList = new Array();
						angular.forEach(tempList, function(data) {
							var fl = true;
							$("input[name='imgIds']:checked").each(function() {
								if($(this).val() == data) {
									fl = false;
								}
							});
							if(fl) {
								scope.tempObject.dataList.push(data);
							}
						})
						scope.isShowDelImg = false;
					}

					//多选框赋值
					scope.applyProductCheck = function(m, c) {
						var checkValue = "";
						var specialValue = "";
						c.check = !c.check;
						angular.forEach(m.dataList, function(data, index, array) {
							if(data.check && "" == checkValue) {
								checkValue = data.id;
								specialValue = data.name;
							} else if(data.check) {
								checkValue += "," + data.id;
								specialValue += "," + data.name;
							}
						});
						m.value = specialValue;
						m.specialValue = checkValue;
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
						scope.$watch(function() {
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
											params[data.split('=')[0]] = data.split('=')[1];
										});
									}

									//没有参数，默认请求一遍
									$http({
										method: 'POST',
										url: data.typeDepend,
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
						scope.$watch(function() {
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
									params[data.split('=')[0]] = data.split('=')[1];
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
							}else {
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
											scope.changeView(showView);
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
											scope.changeView(showView);
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
					scope.save = function(fl, switchTab) {
						if(!fl) {
							box.waitAlert();
						}
						var errorInfo = "";
						var params = {
							orderNo: route.getParams().orderNo,
							productCode: route.getParams().productCode,
							cityCode: route.getParams().cityCode,
							processId: processId,
							dataMap: new Object()
						};
						params.dataMap[scope.pageTabConfigDto.tblName] = new Object();
						//组装参数
						angular.forEach(scope.pageTabConfigDto.pageTabRegionConfigDtos, function(data) {
							errorInfo = getRegionParams(data, params.dataMap[scope.pageTabConfigDto.tblName], 0);
						});
						params.tblName = scope.pageTabConfigDto.tblName;
						params.saveButUrl = scope.pageTabConfigDto.saveButUrl;
						return scope.postJSON("/credit/page/pageData/v/savePageTabConfigDto", params, errorInfo, 0, fl, switchTab);
					}

					//提交
					//要保存整个页面
					scope.submit = function() {

						var errorInfo = "";
						var params = {
							orderNo: route.getParams().orderNo,
							productCode: route.getParams().productCode,
							cityCode: route.getParams().cityCode,
							processId: processId,
							dataMap: new Object()
						};
						//组装参数
						angular.forEach(scope.pageConfigDto.pageTabConfigDtos, function(data) {
							params.dataMap[data.tblName] = new Object();
							angular.forEach(data.pageTabRegionConfigDtos, function(data1) {
								if(errorInfo) {
									getRegionParams(data1, params.dataMap[data.tblName], 1, data)
								} else {
									errorInfo = getRegionParams(data1, params.dataMap[data.tblName], 1, data);
								}
							});
						})

						if(!errorInfo) {
							scope.postJSON("/credit/page/pageData/v/submitPageConfigDto", params, errorInfo, 1);
						}
					}

					//请求接口
					scope.postJSON = function(url, params, errorInfo, type, fl, switchTab) {

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
									initPageConfig();
									if(type == 1) {
										box.closeAlert();
										scope.goBack();
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

				}
			};
		});

	};
});