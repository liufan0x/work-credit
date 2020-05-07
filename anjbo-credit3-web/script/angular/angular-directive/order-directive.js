define(function(require, exports, module) {
	exports.extend = function(app) {

		//无权限详情
		app.directive('noAuth', function($http, route) {
			return {
				restrict: "E",
				template: '<div class="container-fluid"><div class="row detail-row detail-lhw text-danger" >暂无权限<div></div>',
				scope: true,
				transclude: true,
				link: function(scope) {

				}
			};
		});

		//公证详情
		app.directive('notarizationDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/notarizationDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {

					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/order/appNotarization/v/processDetails',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
						scope.productCode = route.getParams().productCode;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = data.data.notarizationImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
					})
				}
			};
		});

		//面签详情
		app.directive('facesignDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/facesignDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {

					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/process/facesign/v/detail',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
						if(scope.obj != null) {
							if(scope.obj.returnOne.indexOf("成功")>0){
								scope.obj.returnOne="中金支付 成功";
							}
							if(scope.obj.returnTwo.indexOf("成功")>0){
								scope.obj.returnTwo="宝付支付 成功";
							}
							scope.obj.imgs = new Array();
							var img = data.data.faceSignPhoto;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
					})
				}
			};
		});

		//订单信息编辑
		app.directive('placeOrderEdit', function($http, $state, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/order/edit/placeOrderEdit.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					//借款信息
					$http({
						method: 'POST',
						url: 'credit/order/borrow/v/query',
						data: params
					}).success(function(data) {
						scope.borrow = data.data;
						//回款日期
						if($scope.borrow.orderReceivableForDto && $scope.borrow.orderReceivableForDto.length > 0) {
							for(var i = 0; i < $scope.borrow.orderReceivableForDto.length; i++) {
								scope.borrow.orderReceivableForDto[i].payMentAmountDate = scope.borrow.orderReceivableForDto[i].payMentAmountDateStr;
							}
						}
					})
				}
			};
		});

		//提单详情
		app.directive('placeOrderDetail', function($http, $timeout, $state, route,box) {
			return {
				restrict: "E",
				templateUrl: function() {
					var productCode = route.getParams().productCode;
					if(productCode == "04"||productCode == "06"||productCode == "07") {
						return '/template/order-common/orderCommonDetail.html';
					} else {
						return '/template/sl/order/placeOrderDetail.html';
					}

				},
				transclude: true,
				link: function(scope) {
					var productCode = route.getParams().productCode;
					if(productCode == "04"||productCode == "06"||productCode == "07") {
						var params = {
							"orderNo": route.getParams().orderNo,
							"processId": "placeOrder"
						}
						scope.isEnterprise = false;

						//获取页面配置 start
						function getPageConfig() {
							$http({
								method: 'POST',
								url: "/credit/config/page/pc/base/v/pageConfig",
								data: params
							}).success(function(data) {
								if(data.code == "SUCCESS") {
									scope.pageConfigDto = data.data;
									scope.changeView(scope.pageConfigDto.pageTabConfigDtos[0]);
								}
								
								angular.forEach(scope.pageConfigDto.pageTabConfigDtos[0].pageTabRegionConfigDtos[0].valueList[0],function(data1){
									if(data1.specialKey == 'customerType'){
										if(data1.value == "个人"){
											scope.isEnterprise = false;
										}else{
											scope.isEnterprise = true;
										}
									}
								});
							});
						}
						//获取页面配置 end

						//切换tab页 start
						scope.changeView = function(pageTabConfigDto) {
							scope.showView = pageTabConfigDto.title;
							scope.pageTabConfigDto = pageTabConfigDto;
							scope.isConfigEdit = false;

							if(pageTabConfigDto.title == "询价/查档/诉讼" || pageTabConfigDto.title == "影像资料") {
								pageTabConfigDto.isShow = true;
								return false;
							}

							if(pageTabConfigDto.title == "询价/查档/诉讼") {
								scope.isConfigEdit = true;
							}

							angular.forEach(pageTabConfigDto.pageTabRegionConfigDtos, function(pageTabRegionConfigDto) {
								angular.forEach(pageTabRegionConfigDto.valueList, function(data2, index) {
									showHide(data2);
									angular.forEach(data2, function(data, index, array) {

										if(data.type == 5) {
											data.dataList = new Array();
											if(data.value) {
												$timeout(function() {
													angular.forEach(data.value.split(','), function(data1) {
														data.dataList.push(data1)
													})
												});
											}
										}

										if(!data.value) {
											data.value = "-";
										}

									});
								});
							});
						}

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
										angular.forEach(data.dataList, function(data1, index, array) {
											if(index == array.length - 1) {
												data.value += data1;
											} else {
												data.value += data1 + ",";
											}
										});
									}
								} else {
									//特殊值
									if(data.specialKey) {
										params[data.specialKey] = data.specialValue;
									}
								}
								
								if(!(data.type == 2 || data.type == 3)){
									//组装参数
									params[data.key] = data.value;									
								}


								//校验非空，提交校验非空
								if(data.isNeed == 2 && type != 0) {
									if(!data.value) {
										data.errorInfo = "必填";
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
								tblName: route.getParams().tblName,
								productCode: route.getParams().productCode,
								cityCode: route.getParams().cityCode,
							};
							params[scope.pageTabConfigDto.tblName] = new Object();
							//组装参数
							angular.forEach(scope.pageTabConfigDto.pageTabRegionConfigDtos, function(data) {
								errorInfo = getRegionParams(data, params[scope.pageTabConfigDto.tblName], 0);
							});
							params.tabClass = scope.pageTabConfigDto.tabClass;
							params.tblName = scope.pageTabConfigDto.tblName;
							params.saveButUrl = scope.pageTabConfigDto.saveButUrl;
							params[scope.pageTabConfigDto.tblName].createCreditLog = true;
							return scope.postJSON("/credit/config/page/pc/base/v/savePageTabConfigDto", params, errorInfo, 0, fl, switchTab);
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
										if(type == 1) {
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

						//切换tab页 end
						getPageConfig();

					} else {
						scope.obj = new Object();
						var orderNo = route.getParams().orderNo;
						var params = {
							"orderNo": orderNo
						}
						scope.isElementEditShow = false;
						scope.isCreditEditShow = false;
						scope.inRelationOrderNo2Detail = "" != route.getParams().relationOrderNo && route.getParams().relationOrderNo.length > 5 ? route.getParams().relationOrderNo : null;

						scope.changeView = function(view) {
							$timeout(function() {
								$("textarea").txtaAutoHeight();
							});
							scope.showView = view;
							var state = $state.current.name;
							if(state.indexOf('placeElementEdit') >= 0 || state.indexOf('placeEnquiryEdit') >= 0 || state.indexOf('placeBusinfoEdit') >= 0 || state.indexOf('placeCreditEdit') >= 0) {
								state = state.substring(0, state.lastIndexOf('.'));
							}
							if(scope.isElementEditShow && view == 4) {
								if(state.indexOf('orderDetail') < 0) {
									$state.go(state + '.placeElementEdit');
								}
							} else if(view == 5) {
								$state.go(state + (null != scope.inRelationOrderNo2Detail ? '.placeEnquiryEditCD' : '.placeEnquiryEdit'));
							} else if(view == 6) {
								$state.go(state + (null != scope.inRelationOrderNo2Detail ? '.placeBusinfoEditCD' : '.placeBusinfoEdit'));
							} else if(scope.isCreditEditShow && view == 7) {
								$state.go(state + '.placeCreditEdit');
							} else {
								if(state.indexOf('placeElementEdit') < 0) {
									$state.go(state);
								}
							}
						}

						var state = $state.current.name;
						if($state.current.name == 'orderEdit.elementEdit' || $state.current.name == 'orderEdit.elementEdit.placeElementEdit') {
							scope.isElementEditShow = true;
							scope.changeView(4);
						} else {
							if(state.indexOf('placeElementEdit') < 0) {
								scope.changeView(1);
							} else {
								scope.changeView(4);
							}
						}

						scope.showCreditEdit = function() {
							scope.changeView(7);
							scope.isCreditEditShow = true;
						}
					}

				}
			};
		});
		app.directive('placeElementBoxDetail', function($http, $state, route) {
			return {
				restrict: "E",
				templateUrl: function() {
					return '/template/element-box/ele/eleAccessRecordList.html';
				},
				link: function($scope) {
					$scope.orderNo = route.getParams().orderNo;
					//$scope.orderNo='2018020609243400005';
					$scope.collect = function(e) {
						$(e.target).parent().next("div").toggle();
						if($(e.target).hasClass("on")) {
							$(e.target).removeClass("on");
							$(e.target).html("展开 ∨");
						} else {
							$(e.target).addClass("on");
							$(e.target).html("收起 ∧");
						}
					}

					$http({
						method: 'POST',
						data: {
							orderNo: $scope.orderNo
						},
						url: '/credit/element/eleaccess/web/v/getElementOrderDetail'
					}).then(function successCallback(response) {
						if("SUCCESS" === response.data.code) {
							$scope.detail = response.data.data;
							ajaxTest(1);
						}
					}, function errorCallback(response) {
						console.log(response)
					});

					// 模拟ajax数据用以下方法，方便用户更好的掌握用法
					//参数为当前页
					function ajaxTest(pageNum) {
						var pageSize = 5;
						$.ajax({
							url: "/credit/element/eleaccess/web/v/pageByOrderNo",
							type: "POST",
							dataType: 'json',
							contentType: "application/json;charset=utf-8",
							async: false,
							data: JSON.stringify({
								pageSize: pageSize,
								start: (pageNum - 1) * pageSize,
								orderNo: $scope.orderNo
							}),
							success: function(data) {
								if("SUCCESS" === data.code) {
									$scope.page = data.rows;
									console.log($scope.page);
									$scope.$applyAsync();
								}
								//分页
								$("#page").paging({
									pageNo: pageNum,
									totalPage: Math.ceil(data.total / pageSize),
									totalSize: data.total,
									callback: function(pageNum) {
										ajaxTest(pageNum)
									}
								})
							}
						})
					}
				}
			}
		})
		//借款信息详情
		app.directive('placeBorrowDetail', function($http, $state, route) {
			return {
				restrict: "E",
				templateUrl: function() {
					var productCode = route.getParams().productCode;
					if(productCode == "01" || productCode == "02" || productCode == "05") {
						return '/template/sl/order/detail/placeBorrowDetail.html';
					} else {
						return '/template/sl/order/detailCD/placeBorrowDetail.html';
					}
				},
				link: function(scope) {
					var productCode = route.getParams().productCode;
					scope.relationOrderNo = route.getParams().relationOrderNo;
					//债务置换贷款信息
					if(productCode == "01" || productCode == "02" || productCode == "05") {
						//借款信息
						$http({
							method: 'POST',
							url: 'credit/order/borrow/v/query',
							data: {
								"orderNo": route.getParams().orderNo
							}
						}).success(function(data) {
							scope.borrow = data.data;
							//回款日期
							if(scope.borrow.orderReceivableForDto && scope.borrow.orderReceivableForDto.length > 0) {
								for(var i = 0; i < scope.borrow.orderReceivableForDto.length; i++) {
									scope.borrow.orderReceivableForDto[i].payMentAmountDate = scope.borrow.orderReceivableForDto[i].payMentAmountDateStr;
								}
							}
						})
						//畅贷
					} else if(productCode == "03") {

						$http({
							method: 'POST',
							url: 'credit/order/borrow/v/query',
							data: {
								"orderNo": route.getParams().orderNo
							}
						}).success(function(data) {
							if(scope.relationOrderNo == 0) {
								scope.borrow = data.data;
								if(scope.borrow.orderReceivableForDto && scope.borrow.orderReceivableForDto.length > 0) {
									for(var i = 0; i < scope.borrow.orderReceivableForDto.length; i++) {
										scope.borrow.orderReceivableForDto[i].payMentAmountDate = scope.borrow.orderReceivableForDto[i].payMentAmountDateStr;
									}
								}
							} else {
								scope.cdBorrow = data.data;
							}

						})

						//关联的畅贷详情
						if(scope.relationOrderNo != 0) {
							//借款信息
							$http({
								method: 'POST',
								url: 'credit/order/borrow/v/query',
								data: {
									"orderNo": scope.relationOrderNo
								}
							}).success(function(data) {
								scope.borrow = data.data;
								//回款日期
								if(scope.borrow.orderReceivableForDto && scope.borrow.orderReceivableForDto.length > 0) {
									for(var i = 0; i < scope.borrow.orderReceivableForDto.length; i++) {
										scope.borrow.orderReceivableForDto[i].payMentAmountDate = scope.borrow.orderReceivableForDto[i].payMentAmountDateStr;
									}
								}
							})
						}

					}

				}
			}
		});

		//客户信息详情
		app.directive('placeCustomerDetail', function($http, $state, route) {
			return {
				restrict: "E",
				templateUrl: function() {
					var productCode = route.getParams().productCode;
					if(productCode == "01" || productCode == "02" || productCode == "05") {
						return '/template/sl/order/detail/placeCustomerDetail.html';
					} else {
						return '/template/sl/order/detailCD/placeCustomerDetail.html';
					}
				},
				link: function(scope) {
					var productCode = route.getParams().productCode;
					if(productCode == "01" || productCode == "02" || productCode == "05") {
						//客户信息
						$http({
							method: 'POST',
							url: '/credit/order/customer/v/query',
							data: {
								"orderNo": route.getParams().orderNo
							}
						}).success(function(data) {
							scope.customer = data.data;
						})
					} else if(productCode == "03") {
						//客户信息
						scope.relationOrderNo = route.getParams().relationOrderNo;
						if(scope.relationOrderNo != 0) {
							$http({
								method: 'POST',
								url: '/credit/order/customer/v/query',
								data: {
									"orderNo": scope.relationOrderNo
								}
							}).success(function(data) {
								scope.customer = data.data;
							})
						} else {
							$http({
								method: 'POST',
								url: '/credit/order/customer/v/query',
								data: {
									"orderNo": route.getParams().orderNo
								}
							}).success(function(data) {
								scope.customer = data.data;
							})
						}

					}
				}
			}
		});

		//房产交易信息详情
		app.directive('placeHouseDetail', function($http, $state, route) {
			return {
				restrict: "E",
				templateUrl: function() {
					var productCode = route.getParams().productCode;
					if(productCode == "01" || productCode == "02" || productCode == "05") {
						return '/template/sl/order/detail/placeHouseDetail.html';
					} else {
						return '/template/sl/order/detailCD/placeHouseDetail.html';
					}
				},
				link: function(scope) {
					var productCode = route.getParams().productCode;
					scope.relationOrderNo = route.getParams().relationOrderNo;

					if(productCode == "01" || productCode == "02" || (productCode == "03" && scope.relationOrderNo != 0) || productCode == "05") {
						var orderNo = route.getParams().orderNo;
						if(scope.relationOrderNo != 0) {
							orderNo = scope.relationOrderNo;
						}
						//房产交易信息
						$http({
							method: 'POST',
							url: 'credit/order/house/v/query',
							data: {
								"orderNo": orderNo
							}
						}).success(function(data) {
							scope.house = data.data;
						})

						//借款信息
						$http({
							method: 'POST',
							url: 'credit/order/borrow/v/query',
							data: {
								"orderNo": orderNo
							}
						}).success(function(data) {
							//初始化页面
							scope.cityCode = data.data.cityCode;
							scope.productCode = data.data.productCode;
						})
					} else if(productCode == "03") {
						//房产交易信息
						$http({
							method: 'POST',
							url: 'credit/order/house/v/query',
							data: {
								"orderNo": route.getParams().orderNo
							}
						}).success(function(data) {
							scope.house = data.data;
						})

					}

				}
			}
		});

		//要件信息详情
		app.directive('placeElementDetail', function($http, $state, route) {
			return {
				restrict: "E",
				templateUrl: function() {
					var productCode = route.getParams().productCode;
					if(productCode == "01" || productCode == "02" || productCode == "05") {
						return '/template/sl/order/detail/placeElementDetail.html';
					} else {
						return '/template/sl/order/detailCD/placeElementDetail.html';
					}
				},
				link: function(scope) {
					var productCode = route.getParams().productCode;
					scope.relationOrderNo = route.getParams().relationOrderNo;

					if(productCode == "01" || productCode == "02" || productCode == "05") {
						//要件信息
						$http({
							method: 'POST',
							url: '/credit/element/basics/v/detail',
							data: {
								"orderNo": route.getParams().orderNo
							}
						}).success(function(data) {
							scope.foreclosure = data.data;
							if(scope.foreclosure) {
								scope.foreclosureType = scope.foreclosure.foreclosureType;
								scope.paymentType = scope.foreclosure.paymentType;
							} else {
								scope.foreclosure = new Object();
								scope.foreclosureType = new Object();
								scope.paymentType = new Object();
							}
						})
					} else if(productCode == "03") {

						//要件信息
						$http({
							method: 'POST',
							url: '/credit/element/basics/v/detail',
							data: {
								"orderNo": route.getParams().orderNo
							}
						}).success(function(data) {
							scope.foreclosure = data.data;
							if(scope.foreclosure) {
								if(scope.relationOrderNo != 0) {
									scope.cdForeclosureType = scope.foreclosure.foreclosureType;
								} else {
									scope.foreclosureType = scope.foreclosure.foreclosureType;
								}
								scope.paymentType = scope.foreclosure.paymentType;
							} else {
								scope.foreclosure = new Object();
								scope.paymentType = new Object();
								if(scope.relationOrderNo != 0) {
									scope.cdForeclosureType = new Object();
								} else {
									scope.foreclosureType = new Object();
								}
							}
						})

						if(scope.relationOrderNo != 0) {

							//要件信息
							$http({
								method: 'POST',
								url: '/credit/element/basics/v/detail',
								data: {
									"orderNo": scope.relationOrderNo
								}
							}).success(function(data) {
								scope.foreclosure = data.data;
								scope.paymentType = new Object();
								if(scope.foreclosure) {
									scope.foreclosureType = scope.foreclosure.foreclosureType;
									scope.paymentType = scope.foreclosure.paymentType;
								} else {
									scope.foreclosureType = new Object();
								}
							})

						}

					}

				}
			}
		});

		//征信信息详情
		app.directive('placeCreditDetail', function($http, $state, route) {
			return {
				restrict: "E",
				templateUrl: function() {
					var productCode = route.getParams().productCode;
					if(productCode == "01" || productCode == "02" || productCode == "05") {
						return '/template/sl/order/detail/placeCreditDetail.html';
					} else {
						return '/template/sl/order/detailCD/placeCreditDetail.html';
					}
				},
				link: function(scope) {
					var productCode = route.getParams().productCode;
					scope.relationOrderNo = route.getParams().relationOrderNo;
					if(productCode == "01" || productCode == "02" || productCode == "03" || productCode == "05") {
						var orderNo = route.getParams().orderNo;
						if(scope.relationOrderNo != 0) {
							orderNo = scope.relationOrderNo;
						}

						//征信信息
						$http({
							method: 'POST',
							url: '/credit/risk/ordercredit/v/detail',
							data: {
								"orderNo": orderNo
							}
						}).success(function(data) {
							scope.credit = data.data;
						})
					}
				}
			}
		});

		app.directive('enquirySearch', function($timeout) {
			return {
				restrict: "C",
				link: function(scope) {
					$timeout(function() {
						$(".enquirySearch").select2({
							minimumInputLength: 1,
							ajax: {
								url: "/credit/risk/enquiry/v/queryConstruction",
								dataType: 'json',
								data: function(term) {
									return term;
								},
								results: function(data, page) {
									alert(data);
									return {
										results: data.data
									};
								}
							}
						});

					});
				}
			}
		});

		app.directive("anjboAuto", function($http, $timeout) {
			return {
				restrict: "AE",
				template: '<input type="text" id="{{htmlId}}" ng-model="enquiry.propertyName" class="form-control" autocomplete="off"/>',
				scope: {
					htmlId: "@",
					enquiry: "=",
					queryBuilding: "&"
				},
				transclude: true,
				link: function(scope, element) {
					$timeout(function() {
						$("#" + scope.htmlId).bigAutocomplete({
							width: 168,
							url: '/credit/risk/enquiry/v/queryConstruction',
							callback: function(data) {
								$timeout(function() {
									scope.enquiry.propertyName = data.name;
									scope.enquiry.propertyId = data.id;
									scope.queryBuilding(scope.enquiry);
								});
							}
						});
					});
				}
			};
		});

		//经理审批详情
		app.directive('managerAuditDetail', function($http, $timeout, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/managerAuditDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {

					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/order/auditManager/v/processDetails',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
					})
				}
			};
		});

		//风控初审详情
		app.directive('auditFirstDetail', function($http, route, $sce) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/auditFirstDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/order/auditFirst/v/processDetails',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.obj = data.data;
							scope.isProductCode = route.getParams().productCode;
							if(scope.obj.other != null) {
								scope.obj.other = scope.obj.other.replace(/\n/g, "<br>");
								scope.obj.other = $sce.trustAsHtml(scope.obj.other);
								if(scope.obj.remark != null) {
									scope.obj.remark = scope.obj.remark.replace(/\n/g, "<br>");
									scope.obj.remark = $sce.trustAsHtml(scope.obj.remark);
								}
							}
						} else {
							alert(data.msg);
						}
					})
				}
			};
		});

		//风控修改征信日志
		app.directive('riskCreditlogDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/editRiskLogDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var isProductCode = route.getParams().productCode;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/risk/ordercredit/v/selectCreditLog',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.dataList = data.data;
							if(scope.dataList.length > 0) {
								scope.handleName = scope.dataList[0].handleName;
							}
						} else {
							alert(data.msg);
						}

					})
				}
			};
		});

		//风控终审详情
		app.directive('auditFinalDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/auditFinalDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/order/auditFinal/v/processDetails',
						data: params
					}).success(function(data) {
						scope.productCode = route.getParams().productCode;
						if("SUCCESS" == data.code) {
							scope.obj = data.data;
						} else {
							alert(data.msg);
						}
					})
				}
			};
		});
		//风控复核审批详情
		app.directive('auditReviewDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/auditReviewDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/order/auditReview/v/processDetails',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.obj = data.data;
						} else {
							alert(data.msg);
						}
					})
				}
			};
		});
		//风控首席风险官详情
		app.directive('auditOfficerDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/auditOfficerDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/order/auditOfficer/v/processDetails',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.obj = data.data;
						} else {
							alert(data.msg);
						}
					})
				}
			};
		});

		//法务详情
		app.directive('auditJusticeDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/auditJusticeDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/order/auditJustice/v/processDetails',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.obj = data.data;
						} else {
							alert(data.msg);
						}
					})
				}
			};
		});
		
		//签订投保单详情
		app.directive('signInsurancePolicyDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/signInsurancePolicyDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {

					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/order/signInsurance/v/processDetails',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
						scope.productCode = route.getParams().productCode;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = data.data.signImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
					})
				}
			};
		});
		
		//上传电子保单详情
		app.directive('uploadInsurancePolicyDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/uploadInsurancePolicyDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {

					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/order/uploadInsurance/v/processDetails',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
						scope.productCode = route.getParams().productCode;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = data.data.uploadInsurancePdf;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
					})
				}
			};
		});

		//分配资金方详情
		app.directive('allocationFundDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					scope.isHuaanShow = false;
					scope.isHuarongShow = false;
					scope.huarongCode = "110";
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					scope.huaanShow = function(bl) {
						scope.isHuaanShow = bl;
						scope.isHuarongShow = false;
					}
					scope.huarongShow = function(bl) {
						scope.isHuarongShow = bl;
						scope.isHuaanShow = false;
					}

					$http({
						method: 'POST',
						url: '/credit/order/allocationFund/v/processDetails',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.dataList = data.data;
							if(scope.dataList && scope.dataList.length > 0) {
								scope.remark = scope.dataList[0].remark;
							}
						} else {
							alert(data.msg);
						}
					});

				}
			};
		});

		//资料审核
		app.directive('auditDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/dataAuditDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/order/auditDataAudit/v/processDetails',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.obj = data.data;
						} else {
							alert(data.msg);
						}
					});

				}
			};
		});

		//资料推送详情
		app.directive('fundDockingDetail', function($http, route, box) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/fundDockingDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					scope.isHuaanShow = false;
					scope.isHuarongShow = false;
					scope.isOrdinaryShow = false;
					scope.isYntrustShow = false;
					scope.huarongCode = "110";
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					scope.huaanShow = function(bl) {
						scope.isHuaanShow = bl;
						scope.isHuarongShow = false;
						scope.isYntrustShow = false;
					}
					scope.huarongShow = function(bl) {
						scope.isHuarongShow = bl;
						scope.isHuaanShow = false;
						scope.isYntrustShow = false;
					}
					scope.ordinaryShow = function(bl) {
						scope.isOrdinaryShow = bl;
						scope.isHuaanShow = false;
						scope.isHuarongShow = false;
						scope.isYntrustShow = false;
					}
					scope.yntrustShow = function(bl) {
						scope.isYntrustShow = bl;
						scope.isOrdinaryShow = false;
						scope.isHuaanShow = false;
						scope.isHuarongShow = false;
					}
					$http({
						method: 'POST',
						url: '/credit/order/auditFundDocking/v/processDetails',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.dataList = data.data;
							if(scope.dataList && scope.dataList.length > 0) {
								scope.remark = scope.dataList[0].remark;
							}
						} else {
							alert(data.msg);
						}
					});
					scope.productCode = route.getParams().productCode;
					scope.ynCancelLoan = function() {
						if(confirm("是否撤回资料推送")) {
							box.waitAlert();
							var orderNo = route.getParams().orderNo;
							var params = {
								"orderNo": orderNo
								//								  	"orderNo":'2017070710381700000'
							}
							$http({
								method: 'POST',
								url: '/credit/third/api/yntrust/v/cancelLoan',
								data: params
							}).success(function(data) {
								box.closeWaitAlert();
								if(data.msg == "没有获取到与该订单号关联的uniqueId信息") {
									box.boxAlert("您已经撤回过该订单了！");
								} else {
									box.boxAlert(data.msg)
								}
							});
						}
					}
				}
			};
		});
		//资金审批详情
		app.directive('allocationFundAduitDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundAduitDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/risk/allocationfundaduit/v/init',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.obj = data.data.fundCompleteDto;
							scope.audit = data.data.fundAudit;
							scope.isHuaRongShow = data.data.isHuaRongShow;
						} else {
							alert(data.msg);
						}
					});

				}
			};
		});

		//指派还款专员详情
		app.directive('repaymentMemberDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/repaymentMemberDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/order/distributionMember/v/processDetails',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
					})
				}
			};
		});

		//要件校验信息详情
		app.directive('elementDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/element/elementDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/element/basics/v/detail',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.obj = data.data;
							if(data.data.elementUrl!=null&&data.data.elementUrl!=''){
								scope.obj.elementUrls = data.data.elementUrl.split(',');
							}else{
								scope.obj.elementUrls=null;
							}
						} else {
							alert(data.msg);
						}
					})
				}
			};
		});
		//申请放款详情
		app.directive('applyLoanDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/applyLoanDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/order/applyLoan/v/processDetails',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.obj = data.data.loanDto;
							scope.productCode = route.getParams().productCode;
							if(scope.obj != null) {
								scope.obj.imgs = new Array();
								var img = scope.obj.chargesReceivedImg;
								if(img != '' && img != null) {
									scope.obj.imgs = img.split(",");
								}
								scope.obj.ckimgs = new Array();
								var ckimgs = scope.obj.payAccountImg;
								if(ckimgs != '' && ckimgs != null) {
									scope.obj.ckimgs = ckimgs.split(",");
								}
								scope.obj.mimg = new Array();
								var mimg = scope.obj.mortgageImg;
								if(mimg != '' && mimg != null) {
									scope.obj.mimg = mimg.split(",");
								}
							}
							scope.logList = data.data.logDtos;
						} else {
							alert(data.msg);
						}
					})
				}
			};
		});

		//扣回后置费用
		app.directive('isbBackExpensesDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/isBackExpensesDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo,
						"type": 2
					}
					$http({
						method: 'POST',
						url: '/credit/order/lendingInterest/v/processDetails',
						data: params
					}).success(function(data) {
						scope.isProductCode = route.getParams().productCode;
						scope.obj = data.data.interest;
						if(scope.obj.interestTimeStr && scope.obj.interestTimeStr != '') {
							scope.obj.interestTime = scope.obj.interestTimeStr + "";
						}
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = scope.obj.interestImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
					})
				}
			};
		});
		//核实扣回后置费用
		app.directive('backExpensesDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/backExpensesDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo,
						"type": 3
					}
					$http({
						method: 'POST',
						url: '/credit/order/lendingHarvest/v/processDetails',
						data: params
					}).success(function(data) {
						scope.isProductCode = route.getParams().productCode;
						scope.obj = data.data.harvest;
						if(scope.obj != null) {
							$http({
								method: 'POST',
								data: {
									"cooperativeAgencyId": scope.obj.cooperativeAgencyId,
									'productId': scope.obj.cityCode + scope.obj.productCode,
									'borrowingDays': scope.obj.borrowingDays,
									"riskGradeId": scope.obj.riskGradeId,
									"loanAmount": scope.obj.loanAmount
								},
								url: "/credit/user/agencyFeescaleRiskcontrol/v/findStageRate"
							}).success(function(msg) { //{overduerate=2.36, rate=2.6, modeid=0}
								if('SUCCESS' == msg.code) {
									var obj = msg.data;
									if(null != obj) {
										console.log(obj);
										scope.modeid = obj.modeid;
									}
								}
							})
							if(scope.obj.riskGradeId == 0) {
								scope.obj.riskGrade = "其他";
							}
							scope.obj.imgs = new Array();
							var img = scope.obj.interestImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
							scope.obj.rateimgs = new Array();
							var rateimg = scope.obj.rateImg;
							if(rateimg != '' && rateimg != null) {
								scope.obj.rateimgs = rateimg.split(",");
							}
						}
						scope.logList = data.data.logDtos;
					})
				}
			};
		});
		//收利息信息详情
		app.directive('islLendingHarvestDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/isLendingHarvestDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo,
						"type": 1
					}
					$http({
						method: 'POST',
						url: '/credit/order/lendingInterest/v/processDetails',
						data: params
					}).success(function(data) {
						scope.isProductCode = route.getParams().productCode;
						scope.obj = data.data.interest;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = scope.obj.interestImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
						if(scope.obj.interestTimeStr && scope.obj.interestTimeStr != '') {
							scope.obj.interestTime = scope.obj.interestTimeStr + "";
						}
					})
				}
			};
		});
		//收费详情-房抵贷
		app.directive('chargeDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/chargeDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo,
						"type":3
					}
					$http({
						method: 'POST',
						url: '/credit/finance/fddCharge/v/detail',
						data: params
					}).success(function(data) {
						scope.isProductCode = route.getParams().productCode;
						scope.obj = data.data.interest;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = scope.obj.interestImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
						if(scope.obj.interestTimeStr && scope.obj.interestTimeStr!=''){
							scope.obj.interestTime =scope.obj.interestTimeStr+"";
						}
					})
				}
			};
		});
		//核实收利息信息详情
		app.directive('lendingHarvestDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/lendingHarvestDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo,
						"type": 2
					}
					$http({
						method: 'POST',
						url: '/credit/order/lendingHarvest/v/processDetails',
						data: params
					}).success(function(data) {
						scope.obj = data.data.harvest;
						scope.isProductCode = route.getParams().productCode;
						if(scope.obj != null) {
							$http({
								method: 'POST',
								data: {
									"cooperativeAgencyId": scope.obj.cooperativeAgencyId,
									'productId': scope.obj.cityCode + scope.obj.productCode,
									'borrowingDays': scope.obj.borrowingDays,
									"riskGradeId": scope.obj.riskGradeId,
									"loanAmount": scope.obj.loanAmount
								},
								url: "/credit/user/agencyFeescaleRiskcontrol/v/findStageRate"
							}).success(function(msg) { //{overduerate=2.36, rate=2.6, modeid=0}
								if('SUCCESS' == msg.code) {
									var obj = msg.data;
									if(null != obj) {
										console.log(obj);
										scope.modeid = obj.modeid;
									}
								}
							})
							if(scope.obj.riskGradeId == 0) {
								scope.obj.riskGrade = "其他";
							}
							scope.obj.imgs = new Array();
							var img = scope.obj.interestImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
							scope.obj.rateimgs = new Array();
							var rateimg = scope.obj.rateImg;
							if(rateimg != '' && rateimg != null) {
								scope.obj.rateimgs = rateimg.split(",");
							}
						}
						scope.logList = data.data.logDtos;
					})
				}
			};
		});
		//核实收费详情-房抵贷
		app.directive('isfddChargeDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/isChargeDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo,
						"type":3
					}
					$http({
						method: 'POST',
						url: '/credit/finance/fddIsCharge/v/detail',
						data: params
					}).success(function(data) {
						scope.isProductCode = route.getParams().productCode;
						scope.obj = data.data.harvest;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = scope.obj.interestImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
							scope.obj.rateimgs = new Array();
							var rateimg = scope.obj.rateImg;
							if(rateimg != '' && rateimg != null) {
								scope.obj.rateimgs = rateimg.split(",");
							}
						}
						scope.logList=data.data.logDtos;
					})
				}
			};
		});
		//付利息信息详情
		app.directive('lendingPayDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/lendingPayDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/finance/lendingPay/v/detail',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = scope.obj.payImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
					})
				}
			};
		});

		//财务制单
		app.directive('financialStatementDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/statementDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/finance/statement/v/detail',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
					})
				}
			};
		});

		//财务审核
		app.directive('financialAuditDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/auditDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/finance/audit/v/detail',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
					})
				}
			};
		});

		//发放款指令信息详情
		app.directive('lendingInstructionsDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/lendingInstructionsDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/finance/lendingInstruction/v/detail',
						data: params
					}).success(function(data) {
						scope.obj = data.data.instructionsDto;
						//						scope.baseBorrowDto = data.data.baseBorrowDto;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = scope.obj.chargesReceivedImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
					})
				}
			};
		});

		//放款
		app.directive('lendingDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/lendingDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/order/lending/v/processDetails',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
						scope.productCode = route.getParams().productCode;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = data.data.lendingImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
						$http({
							method: 'POST',
							url: '/credit/risk/allocationfundaduit/v/init',
							data: params
						}).success(function(data) {
							console.log(data);
							if("SUCCESS" == data.code) {
								scope.fund = data.data.fundCompleteDto;
								scope.audit = data.data.fundAudit;
								scope.isHuaRongShow = data.data.isHuaRongShow;
								scope.isYunNan = data.data.isYunNanShow;
							}
						});
						$http({
							method: 'POST',
							url: "/credit/third/api/yntrust/v/selectRepaymentPlan",
							data:params
						}).success(function(data) {
							if("SUCCESS"==data.code){
								scope.yunnanAudit = data.data;
							}
						})
					})
				}
			};
		});

		//结清原贷款信息详情
		app.directive('foreclosureDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/foreclosureDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}

					$http({
						method: 'POST',
						url: '/credit/process/foreclosure/v/detail',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = data.data.foreclosureImg;
							var voucherImg = data.data.voucherImg;
							if(img != '' && img != null) {
								if(voucherImg != '' && voucherImg != null) {
									img += "," + voucherImg;
								}
								scope.obj.imgs = img.split(",");
							}
						}
					})
				}
			};
		});

		//取证信息详情
		app.directive('forensicsDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/forensicsDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}

					$http({
						method: 'POST',
						url: '/credit/process/forensics/v/detail',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
						scope.productCode = route.getParams().productCode;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = data.data.forensiceImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
					})
				}
			};
		});
		//取证信息详情-房抵贷
		app.directive('fddForensicsDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/forensicsDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}

					$http({
						method: 'POST',
						url: '/credit/process/forensics/v/detail',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
						scope.productCode = route.getParams().productCode;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = data.data.forensiceImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
					})
				}
			};
		});
		//注销信息详情
		app.directive('cancellationDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/cancellationDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}

					$http({
						method: 'POST',
						url: '/credit/process/cancellation/v/detail',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = data.data.cancelImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
					})
				}
			};
		});

		//过户信息详情
		app.directive('transferDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/transferDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/process/transfer/v/detail',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = data.data.transferImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
					})
				}
			};
		});

		//领新证信息详情
		app.directive('newlicenseDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/newlicenseDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/process/newlicense/v/detail',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = data.data.newlicenseImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
					})
				}

			};
		});

		//回款（首期）详情
		app.directive('receivableForFirstDetail', function($http, route, box, parent) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/receivableForDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo,
						"isFrist": 1 //首期
					}
					$http({
						method: 'POST',
						url: '/credit/finance/receivableFor/v/details',
						data: params
					}).success(function(data) {
						scope.forList = data.data.list;
						scope.customerPaymentTime = data.data.customerPaymentTimeStr;
						scope.remark = data.data.remark;
						scope.huaRong = data.data.auditMap;
						scope.isYNDetail = false;
						if(scope.huaRong) {
							scope.huaRong.repaymentTime = scope.forList[0].payMentAmountDate;
							//								scope.huaRong.repaymentYestime = data.data.auditMap.repaymentYestime*1000;
						} else {
							yunNanInitStatus($http, route, scope, box, parent, orderNo);
						}
						scope.iswq = false;
					})
				}
			};
		});
		//回款（尾期）详情
		app.directive('receivableForDetail', function($http, $filter, route, box, parent) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/receivableForDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo //首期
					}
					$http({
						method: 'POST',
						url: '/credit/finance/receivableFor/v/details',
						data: params
					}).success(function(data) {
						scope.forList = data.data.list;
						scope.customerPaymentTime = data.data.customerPaymentTimeStr;
						scope.remark = data.data.remark;
						scope.huaRong = data.data.auditMap;
						scope.isYNDetail = false;
						if(scope.huaRong) {
							scope.huaRong.repaymentTime = data.data.auditMap.repaymentTime * 1000;
							scope.huaRong.repaymentYestime = data.data.auditMap.repaymentYestime * 1000;
						} else {
							yunNanInitStatus($http, route, scope, box, parent, orderNo, $filter);
						}
						scope.iswq = true;
					})
				}
			};
		});
		
		//抵押信息详情
		app.directive('mortgageDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/mortgageDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/process/mortgage/v/detail',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
						scope.productCode = route.getParams().productCode;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = data.data.mortgageImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
					})
				}
			};
		});
		//抵押信息详情-房抵贷
		app.directive('fddMortgageDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/mortgageDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/process/mortgage/v/detail',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
						scope.productCode = route.getParams().productCode;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = data.data.mortgageImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
					})
				}
			};
		});
		//待付费
		app.directive('payDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/payDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/finance/receivablePay/v/detail',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
						if(scope.obj != null) {
							scope.obj.imgs = new Array();
							var img = scope.obj.payImg;
							if(img != '' && img != null) {
								scope.obj.imgs = img.split(",");
							}
						}
						scope.overdueDay =  -scope.obj.datediff;
					})
					$http({
						method: 'POST',
						url:"/credit/order/borrow/v/query" ,
						data:params
					}).success(function(data){
						scope.overdueRate=data.data.overdueRate;
					})
					
				}
			};
		});

		//要件退还详情
		app.directive('elementReturnDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/element/elementReturnDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/order/documentsReturn/v/processDetails',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.obj = data.data;
							if(scope.obj != null) {
								scope.obj.imgs = new Array();
								var img = scope.obj.returnImgUrl;
								if(img != '' && img != null) {
									scope.obj.imgs = img.split(",");
								}
							}
						} else {
							alert(data.msg);
						}

					})
				}
			};
		});
		//解押-房抵贷
		app.directive('releaseDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/releaseDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/process/release/v/detail',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.obj = data.data;
						} else {
							alert(data.msg);
						}
						
					})
				}
			};
		});
		//返佣
		app.directive('rebateDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/rebateDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/order/rebate/v/processDetails',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.obj = data.data;
							if(scope.obj != null) {
								scope.obj.imgs = new Array();
								var img = scope.obj.rebateImg;
								if(img != '' && img != null) {
									scope.obj.imgs = img.split(",");
								}
							}
						} else {
							alert(data.msg);
						}

					})
				}
			};
		});
		//抵押品入库
		app.directive('fddMortgageStorageDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/fddMortgageStorageDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/process/fddMortgageStorage/v/detail',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.obj = data.data;
						} else {
							alert(data.msg);
						}
					})
				}
			};
		});
		//抵押品出库
		app.directive('fddMortgageReleaseDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/process/fddMortgageReleaseDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					$http({
						method: 'POST',
						url: '/credit/process/fddMortgageRelease/v/detail',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.obj = data.data;
						} else {
							alert(data.msg);
						}
					})
				}
			};
		});
		
		
		// 云南信托回款状态初始化
		function yunNanInitStatus($http, route, scope, box, parent, orderNo, $filter) {
			var param = {
				orderNo: orderNo
			}
			$http({
				url: '/credit/order/allocationFund/v/processDetails',
				method: 'POST',
				data: param
			}).success(function(data) {
				if("SUCCESS" == data.code) {
					scope.receivablePageVo2repay = new Object;
					scope.receivablePageVo2pay = new Object;
					scope.receivablePage2fundCode = data.data[0].fundCode;
					if(114 == Number(scope.receivablePage2fundCode)) {
						scope.overdueDayRate = 0.0;
						$http({
							url: '/credit/third/api/yntrust/v/selectRepaymentPlanMap',
							method: 'POST',
							data: param
						}).success(function(data) {
							if("SUCCESS" == data.code && undefined != data.data.contract.overdueDayRate && null != data.data.contract.overdueDayRate) {
								scope.overdueDayRate = parseFloat(data.data.contract.overdueDayRate).toFixed(4);
							}
							yunNanStatuLoading($http, route, scope, box, parent);
							yunNanEvt($http, route, scope, box, parent, orderNo, $filter);
						});
					}
				}
			});
		}

		function yunNanStatuLoading($http, route, $scope, box, parent) {
			$scope.receivablePageVo2repay = new Object();
			$scope.receivablePageVo2repay.statusName = "--";
			$scope.receivablePageVo2pay = new Object();
			$scope.receivablePageVo2pay.statusName = "--";
			$http({
				url: '/credit/third/api/yntrust/v/queryRepayOrder',
				method: 'POST',
				data: {
					orderNo: route.getParams().orderNo
				}
			}).success(function(data) {
				if("SUCCESS" == data.code) {
					$scope.receivablePageVo2repay = data.data;
					if(null == data.data.statusName || "" == data.data.statusName) {
						$scope.receivablePageVo2repay.statusName = "--";
					}
					if(parent.userDto.uid == "1508747262735") {
						$scope.receivableEnabled = true;
					}
				} else {
					$scope.receivablePageVo2repay.statusName = data.msg;
				}
			});
			$http({
				url: '/credit/third/api/yntrust/v/queryPaymentOrder',
				method: 'POST',
				data: {
					orderNo: route.getParams().orderNo
				}
			}).success(function(data) {
				if("SUCCESS" == data.code) {
					$scope.receivablePageVo2pay = data.data;
					if(null == data.data.statusName || "" == data.data.statusName) {
						$scope.receivablePageVo2pay.statusName = "--";
					}
				}
			});
		}

		function yunNanEvt($http, route, $scope, box, parent, orderNo, $filter) {
			$scope.viewYn = function() {
				$scope.yunNanShow = true;
				$scope.isSubmitReturn = false;
				$scope.isSubmitPay = false;
			};
			$scope.statuYnLoading = function() {
				yunNanStatuLoading($http, route, $scope, box, parent);
			};
			$scope.submitAgainYn = function() {
				$scope.yunNanShow = true;
				$scope.isSubmitReturn = true;
				$scope.isSubmitPay = false;
				$scope.isYNDetail = false;
				$(".yunNanBtns .lhw-alert-ok").removeAttr("disabled");
			};
			$scope.submitPayYn = function() {
				$scope.yunNanShow = true;
				$scope.isSubmitReturn = false;
				$scope.isSubmitPay = true;
				if(!$scope.obj){
					$scope.obj = new Object();
				}
				if(!$scope.obj.pay){
					$scope.obj.pay = new Object();
				}
				if(!$scope.obj.pay.bankTransactionNo){
					$scope.obj.pay.bankTransactionNo = new Date().getTime();
				}
				$(".yunNanBtns .lhw-alert-ok").removeAttr("disabled");
			};
			$scope.obj = new Object();
			$scope.obj.orderNo = orderNo;
			$scope.obj.changeReason = 2;
			$http({
				url: '/credit/third/api/yntrust/v/selectRepaymentInfoAndPay',
				method: 'POST',
				data: {
					orderNo: orderNo
				}
			}).success(function(data) {
				if("SUCCESS" == data.code) {
					$scope.obj.info = data.data.info;
					$scope.obj.info.orderNo = orderNo;
					$scope.obj.pay = null != data.data.pay ? data.data.pay : new Object();
					$scope.obj.pay.orderNo = orderNo;
					if(null == $scope.obj.pay.accountName || "" == $scope.obj.pay.accountName || null == $scope.obj.pay.accountNo) {
						$http({
							url: '/credit/third/api/yntrust/v/selectBorrowContractImg',
							method: 'POST',
							data: {
								orderNo: orderNo
							}
						}).success(function(data) {
							if("SUCCESS" == data.code && null != data.data && null != data.data.contract) {
								$scope.obj.pay.accountName = data.data.contract.paymentsBankAccountName;
								$scope.obj.pay.accountNo = data.data.contract.paymentsBankAccount;
							}
						});
					}
					// 格式转换
					/*
					var date = new Date(data.data.info.repayDate);
					$scope.obj.info.repayDate = date.getFullYear() +"-"+ (date.getMonth()+1) +"-"+ date.getDate();
					date = new Date(data.data.info.realityPayDate);
					$scope.obj.info.realityPayDate = date.getFullYear() +"-"+ (date.getMonth()+1) +"-"+ date.getDate();
					*/
					if($scope.obj.info&&$scope.obj.info.repayDate){
						$scope.obj.info.repayDate = $filter('date')($scope.obj.info.repayDate,'yyyy-MM-dd');
					}
					if($scope.obj.info&&$scope.obj.info.realityPayDate){
						$scope.obj.info.realityPayDate = $filter('date')($scope.obj.info.realityPayDate,'yyyy-MM-dd');
					}
					$scope.dictPaymentMethod = $scope.obj.info.paymentMethod + "@" + $scope.obj.info.paymentMethodName;
					if(undefined != $scope.obj.pay.voucherType && null != $scope.obj.pay.voucherType && "" != $scope.obj.pay.voucherType) {
						$scope.dictVoucherType = $scope.obj.pay.voucherType + "@" + $scope.obj.pay.voucherTypeName;
					}
					// 金额初始化
					if($scope.isYNDetail) {
						yunNanCalculate();
					}
					
					if($scope.obj.info.repayDate > new Date()){
						// 还款总金额 = 应还本金+已还利息总计
						$scope.obj.pay.amount = parseFloat(
							parseFloat(isNaN($scope.obj.info.repayPrincipal) ? 0 : $scope.obj.info.repayPrincipal) +
							parseFloat(isNaN($scope.obj.info.givePayProfit) ? 0 : $scope.obj.info.givePayProfit)
						).toFixed(2);
					}else{
						// 还款总金额 = 应还本金+应还利息+逾期总额
						$scope.obj.pay.amount = parseFloat(
							parseFloat(isNaN($scope.obj.info.repayPrincipal) ? 0 : $scope.obj.info.repayPrincipal) +
							parseFloat(isNaN($scope.obj.info.repayProfit) ? 0 : $scope.obj.info.repayProfit) +
							parseFloat(isNaN($scope.obj.info.overDueFee) ? 0 : $scope.obj.info.overDueFee)
						).toFixed(2);
					}
					
				}
			});
			//云南信托(start)
			function yunNanCalculate() {
				if(!$scope.obj.info || !$scope.obj.pay) {
					return;
				}
				// 计算逾期天数Start---------
				/*
				var days=0;
				var oldTime=$scope.obj.info.repayDate;
				var newTime=$scope.obj.info.realityPayDate;
				if(typeof(newTime)!= 'undefined' && typeof(oldTime)!= 'undefined'){
					newTime=newTime.replace(/-/g,"/");
					oldTime=oldTime.replace(/-/g,"/");
					var startdate=new Date(oldTime);
					var enddate=new Date(newTime);
					var time=enddate.getTime()-startdate.getTime();
					days=parseInt(time/(1000 * 60 * 60 * 24));
					if(days<0){
						days=0;
					}
				}
				*/
				var days = 0;
				var realityDay = 0;
				if($scope.obj.info.realityPayDate && $scope.obj.info.repayDate) {
					var newTime = $filter('date')(new Date($scope.obj.info.realityPayDate), 'yyyy-MM-dd');
					var oldTime = $filter('date')(new Date($scope.obj.info.repayDate), 'yyyy-MM-dd');
					var startdate = new Date(oldTime);
					var enddate = new Date(newTime);
					var time = enddate.getTime() - startdate.getTime();
					days = parseInt(time / (1000 * 60 * 60 * 24));
					realityDay = days;
					if(days < 0) {
						days = 0;
					}
				}

				$scope.obj.info.lateDay = days;
				// 逾期计算		 
				var loanAmount = parseFloat(isNaN($scope.obj.info.repayPrincipal) ? 0 : $scope.obj.info.repayPrincipal);
				$scope.obj.info.penaltyFee = parseFloat(days * loanAmount * $scope.overdueDayRate).toFixed(2); //逾期罚息
				//计算利息
				if(Number(realityDay) < 0){
					realityDay = Math.abs(realityDay);
					realityDay = $scope.obj.info.borrowingDays-Number(realityDay);
					var tmpRepayPrincipal = parseFloat($scope.obj.info.repayPrincipal * realityDay * 0.0005).toFixed(2);
					$scope.obj.info.givePayProfit = tmpRepayPrincipal;
					if(Number($scope.obj.info.givePayProfit)<0){
						$scope.obj.info.givePayProfit = 0;
					}
				}  else {
					$scope.obj.info.givePayProfit = $scope.obj.info.repayProfit;
				}

				if($scope.obj.info.givePayProfit < 0){
					$scope.obj.info.givePayProfit = 0;
				}
				// 费用合计
				yunNanCalculateTotal();
			}

			function yunNanCalculateTotal() {
				if(!$scope.obj.info || !$scope.obj.pay) {
					return;
				}

				// 逾期费用 = 逾期罚息+逾期违约金+逾期服务费+逾期其他费用
				$scope.obj.info.overDueFee = parseFloat(
					parseFloat(isNaN($scope.obj.info.penaltyFee) ? 0 : $scope.obj.info.penaltyFee) +
					parseFloat(isNaN($scope.obj.info.latePenalty) ? 0 : $scope.obj.info.latePenalty) +
					parseFloat(isNaN($scope.obj.info.lateFee) ? 0 : $scope.obj.info.lateFee) +
					parseFloat(isNaN($scope.obj.info.lateOtherCost) ? 0 : $scope.obj.info.lateOtherCost)
				).toFixed(2);
			}
			// 计算公式
			function floadNum(a) {
				var num = a + "";
				var index = num.indexOf('.');
				if(index == -1) {
					return num;
				} else {
					var f = parseFloat(num).toFixed(2);
					return f;
				}
			}

			$scope.forSubmit = function() {
				if($scope.yunNanForm.$invalid) {
					return false;
				}
				var firstForSubmit = function() {
					if(undefined != $scope.dictPaymentMethod && "@请选择" != $scope.dictPaymentMethod) {
						var _dictAry = $scope.dictPaymentMethod.split("@");
						$scope.obj.info.paymentMethod = _dictAry[0];
						$scope.obj.info.paymentMethodName = _dictAry[1];
					}
					if(undefined != $scope.dictVoucherType && "@请选择" != $scope.dictVoucherType) {
						_dictAry = $scope.dictVoucherType.split("@");
						$scope.obj.pay.voucherType = _dictAry[0];
						$scope.obj.pay.voucherTypeName = _dictAry[1];
					}
					$(".yunNanBtns .lhw-alert-ok").attr("disabled", "disabled");
					box.waitAlert();
					if($scope.isSubmitPay) {
						$http({
							method: 'POST',
							url: "/credit/third/api/yntrust/v/paymentOrder",
							data: $scope.obj.pay
						}).success(function(data) {
							$(".yunNanBtns .lhw-alert-ok").removeAttr("disabled");
							box.closeWaitAlert();
							box.boxAlert(data.msg);
							if(data.code == "SUCCESS") {
								$scope.statuYnLoading();
								$scope.yunNanShow = false;
							}
						});
					} else {
						$scope.obj.info.changeReason = 2;
						$http({
							method: 'POST',
							url: "/credit/third/api/yntrust/v/addRepaymentInfoAndPayInfo",
							/* addRepaymentInfoAndPayInfo,updateRepaySchedule   */
							data: $scope.obj.info
						}).success(function(data) {
							$(".yunNanBtns .lhw-alert-ok").removeAttr("disabled");
							box.closeWaitAlert();
							box.boxAlert(data.msg);
							if(data.code == "SUCCESS") {
								$scope.statuYnLoading();
								$scope.yunNanShow = false;
							}
						});
					}
				}
				firstForSubmit();
			}
			$scope.$watch("obj.info.repayDate", function(newValue, oldValue) {
				if(newValue != 'undefined' && $scope.isYNDetail) {
					yunNanCalculate();
				}
			});
			$scope.$watch("obj.info.realityPayDate", function(newValue, oldValue) {
				if(newValue != 'undefined' && ($scope.isYNDetail||$scope.isSubmitReturn)) {
					yunNanCalculate();
				}
			});
			$scope.$watch("obj.info.repayPrincipal", function(newValue, oldValue) {
				if(newValue != 'undefined' && $scope.isYNDetail) {
					yunNanCalculate();
				}
			});
			$scope.$watch("obj.info.repayProfit", function(newValue, oldValue) {
				if(newValue != 'undefined' && $scope.isYNDetail) {
					yunNanCalculate();
				}
			});
			$scope.$watch("obj.info.penaltyFee", function(newValue, oldValue) {
				if(newValue != 'undefined' && $scope.isYNDetail) {
					yunNanCalculateTotal();
				}
			});
			$scope.$watch("obj.info.latePenalty", function(newValue, oldValue) {
				if(newValue != 'undefined' && $scope.isYNDetail) {
					yunNanCalculateTotal();
				}
			});
			$scope.$watch("obj.info.lateFee", function(newValue, oldValue) {
				if(newValue != 'undefined' && $scope.isYNDetail) {
					yunNanCalculateTotal();
				}
			});
			$scope.$watch("obj.info.lateOtherCost", function(newValue, oldValue) {
				if(newValue != 'undefined' && $scope.isYNDetail) {
					yunNanCalculateTotal();
				}
			});
			//云南信托(end)
		}

		
		//提交框
		app.directive('submitBox', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/submitBox.html',
				link: function(scope) {
					var cityCode = 0;
					if(scope.order) {
						cityCode = scope.order.cityCode;
					} else if(scope.borrow) {
						cityCode = scope.borrow.cityCode;
					} else {
						cityCode = route.getParams().cityCode;
					}
					scope.sumbitDto = new Object();
					scope.sumbitDto.agencgId = "1";
					scope.sumbitDto.cityCode = cityCode;
				}
			};
		});

		//退回框
		app.directive('backBox', function($http, $state, route, box) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/backBox.html',
				link: function(scope) {
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo
					}
					scope.processName = "无";
					scope.isDisabled = true;
					$http({
						method: 'POST',
						url: '/credit/order/flow/v/selectOrderFlowList',
						data: params
					}).success(function(data) {
						scope.processList = data.data;
					})

					scope.$watch("processIndex", function(newValue, oldValue) {
						if(scope.processIndex) {
							var tempProcessId = scope.processList[scope.processList.length - 1].nextProcessId;
							scope.backDto = scope.processList[scope.processIndex];
							if(scope.backDto.currentProcessId == "allocationFund"){
								scope.isDisabled = false;
								scope.isNewWalkProcess = "1";
							}else if(scope.backDto.currentProcessId == "placeOrder" && tempProcessId != "dataAudit" && tempProcessId != 'managerAudit' && tempProcessId != 'auditFirst' && tempProcessId != 'auditFinal' && tempProcessId != 'auditOfficer'){
								scope.isDisabled = false;
								scope.isNewWalkProcess = "1";
							}else{
								scope.isDisabled = true;
								scope.isNewWalkProcess = "";
							}
							scope.processName = scope.backDto.handleName;
						} else {
							scope.processName = "无";
						}
					});

					var isBack = true;

					scope.backOrder = function() {
						scope.backDto = scope.processList[scope.processIndex];
						scope.lastBackDto = scope.processList[scope.processList.length - 1];
						if(!scope.returnType) {
							box.boxAlert("退回类型必填");
							return false;
						}
						if(!scope.backReason) {
							box.boxAlert("退回原因必填");
							return false;
						}
						if(!scope.isNewWalkProcess){
							box.boxAlert("是否重走流程必选");
							return false;
						}
						var returnType = "";
						angular.forEach(scope.returnType, function(data, index, array) {
							returnType += data + ",";
						})
						returnType = returnType.substring(0, returnType.length - 1);
						params = {
							"orderNo": orderNo,
							"currentProcessId": scope.lastBackDto.nextProcessId,
							"currentProcessName": scope.lastBackDto.nextProcessName,
							"nextProcessId": scope.backDto.currentProcessId,
							"nextProcessName": scope.backDto.currentProcessName,
							"handleUid": scope.backDto.handleUid,
							"handleName": scope.backDto.handleName,
							"backReason": scope.backReason,
							"returnType": returnType,
							"isNewWalkProcess":scope.isNewWalkProcess
						}
						if(isBack) {
							isBack = false;
							$http({
								method: 'POST',
								url: '/credit/order/flow/v/backOrder',
								data: params
							}).success(function(data) {
								isBack = true;
								if(data.code == "SUCCESS") {
									$state.go("orderList");
									box.closeAlert();
								} else {
									box.boxAlert(data.msg);
								}
							})
						}
					}

				}
			};
		});

		//----------------资料推送信息start-----------------------------
		app.directive('allocationFundOrdinaryPushDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundOrdinaryPushDetail.html',
				link: function(scope) {
					scope.title = "orderOrdinaryShow";
					scope.relationOrderNo = route.getParams().relationOrderNo;
					scope.productCode = route.getParams().productCode;
					var orderNo = route.getParams().orderNo;
					var param = {
						orderNo: orderNo
					}
					$http({
						url: '/credit/order/allocationFund/v/processDetails',
						method: 'POST',
						data: param
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.dataList = data.data;
							scope.ordinaryFundId = null;
							angular.forEach(scope.dataList, function(data, index, array) {
								index++;
								if((data.fundCode != "110" || data.fundCode != 110) && (data.fundCode != "105" || data.fundCode != 105) && (data.fundCode != "001" || data.fundCode != 001)) {
									scope.ordinaryFundId = data.fundId;
								}
							});
							if(scope.ordinaryFundId != null) {
								$http({
									method: 'POST',
									url:"/credit/third/api/ordinary/v/loadOrdinaryBusInfo",
									data: {
										"orderNo": orderNo,
										"ordinaryFund": scope.ordinaryFundId,
										"productCode": route.getParams().productCode,
									}
								}).success(function(data) {
									scope.ordinaryimageDatas = data.data;
									scope.isAuditShow = scope.ordinaryimageDatas.isAuditShow;
								});
							}
						}
					});

					//查询畅贷关联基本信息
					if(scope.relationOrderNo != 0 && scope.relationOrderNo != "0") {
						$http({
							method: 'POST',
							url: 'credit/order/borrow/v/query',
							data: {
								"orderNo": route.getParams().orderNo
							}
						}).success(function(data) {
							scope.cdBorrow = data.data;
						})
						$http({
							method: 'POST',
							url: 'credit/order/borrow/v/query',
							data: {
								"orderNo": scope.relationOrderNo
							}
						}).success(function(data) {
							scope.borrow = data.data;
							//回款日期
							if(scope.borrow.orderReceivableForDto && scope.borrow.orderReceivableForDto.length > 0) {
								for(var i = 0; i < scope.borrow.orderReceivableForDto.length; i++) {
									scope.borrow.orderReceivableForDto[i].payMentAmountDate = scope.borrow.orderReceivableForDto[i].payMentAmountDateStr;
								}
							}
						})
					}
				}
			};
		});
		app.directive('allocationFundHuaanPushDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuaanPushDetail.html',
				link: function(scope) {
					scope.title = "orderShow";
				}
			};
		});
		app.directive('allocationFundHuarongPushDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuarongPushDetail.html',
				link: function(scope) {
					scope.huarongTitle = "huarongOrderShow";
				}
			};
		});
		app.directive('allocationFundYntrustPushDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundYntrustPushDetail.html',
				link: function(scope) {
					scope.yntrustTitle = "yntrustOrderShow";
				}
			};
		});
		//普通资方推送信息
		app.directive('allocationFundOrdinaryOrderDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundOrdinaryOrderDetail.html',
				transclude: true,
				link: function(scope) {
					scope.ordinaryTitle = "orderOrdinaryShow";
					scope.ordinaryObj = new Object();
					scope.relationOrderNo = route.getParams().relationOrderNo;
					var orderNo = route.getParams().orderNo;
					if(scope.productCode == "03" && scope.relationOrderNo != '0'){
						orderNo = scope.relationOrderNo;
					}
					
					$http({
						method: 'POST',
						url: '/credit/risk/fundDocking/v/ordinaryDetail', 
						data: {
							"orderNo": orderNo
						}
					}).success(function(data) {
						scope.ordinaryObj = null;
						if("SUCCESS" == data.code) {
							scope.ordinaryObj = data.data.ordinary;
						}
					})
				}
			};
		});

		//普通资方审批意见
		app.directive('allocationFundOrdinaryAuditDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundOrdinaryAuditDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					scope.firstObj = new Object();
					$http({
						url: '/credit/risk/fundDocking/v/ordinaryDetail',
						method: 'POST',
						data: {
							"orderNo": route.getParams().orderNo
						}
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.firstObj = data.data.ordinaryAudit;
							if(scope.firstObj != null) {
								if(scope.firstObj.loanBankId == 0) {
									scope.firstObj.loanBankId = "";
								} else {
									scope.firstObj.loanBankId = String(scope.firstObj.loanBankId);
								}
								scope.firstObj.loanBankSubId = String(scope.firstObj.loanBankSubId);
								if(scope.firstObj.paymentBankId == 0) {
									scope.firstObj.paymentBankId = "";
								} else {
									scope.firstObj.paymentBankId = String(scope.firstObj.paymentBankId);
								}
								scope.firstObj.paymentBankSubId = String(scope.firstObj.paymentBankSubId);
								if(!scope.firstObj.business || scope.firstObj.business == "null") {
									scope.firstObj.business = "1";
								} else {
									scope.firstObj.business = String(scope.firstObj.business);
								}
							}
						}
					});
				}
			};
		});

		//普通资方影响资料
		app.directive('allocationFundOrdinaryBusinfoDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundOrdinaryBusinfoDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					scope.isImgEdit = true;

				}
			};
		});

		//云南信托借款人信息
		app.directive('allocationFundYntrustOrderDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundYntrustOrderDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					scope.yntrustTitle = "yntrustOrderShow";
					scope.ynObj = new Object();
					$http({
						method: 'POST',
						url: '/credit/third/api/yntrust/v/selectBorrowContractImg',
						data: {
							"orderNo": route.getParams().orderNo
						}
					}).success(function(data) {
						scope.ynObj = null;
						if("SUCCESS" == data.code) {
							scope.ynObj = data.data.borrow;
							scope.ynctObj = data.data.contract;
							scope.kgYunnanImg = data.data.img;
						}
					})
				}
			};
		});
		//云南信托合同信息
		app.directive('allocationFundYntrustContractDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundYntrustContractDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();

				}
			};
		});
		//云南信托影像资料
		app.directive('allocationFundYntrustBusinfoDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundYntrustBusinfoDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();

				}
			};
		});
		//华安借款人信息 
		app.directive('allocationFundHuaanOrderDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuaanOrderDetail.html',
				link: function(scope) {
					var orderNo = route.getParams().orderNo;
					$http({
						url: '/credit/risk/allocationfund/v/loadPushOrder',
						method: 'POST',
						data: {
							"orderNo": orderNo
						}
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.huaan = data.data;
						} else {
							alert(data.msg);
						}
					});
				}
			};
		});
		//华安影响资料
		app.directive("allocationFundHuaanBusinfoDetail", function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuaanBusinfo.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					scope.isImgEdit = false;
					var orderNo = route.getParams().orderNo;
					$http({
						method: 'POST',
						url: "/credit/risk/allocationfund/v/loadBusInfo",
						data: {
							"orderNo": orderNo
						}
					}).success(function(data) {
						scope.imageDatas = data.data;
					});
				}
			};
		});
		//华融借款人信息
		app.directive("allocationFundHuarongOrderDetail", function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuarongOrderDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					$http({
						method: 'POST',
						url: "/credit/third/api/hr/v/queryApply",
						data: {
							"orderNo": orderNo
						}
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.appoint = data.data.lcAppoint;
							scope.applAppt = data.data.lcApplAppt;
							scope.apptIndiv = data.data.lcApptIndiv;
							scope.kgAppoint = data.data.kgAppoint;
							scope.kgIndiv = data.data.kgIndiv;
							scope.kgHouse = data.data.kgHouse;
							scope.kgApproval = data.data.kgApproval;

						}
					});
				}
			};
		});
		app.directive("allocationFundHuarongLoanDetail", function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuarongLoanDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					$http({
						method: 'POST',
						url: "/credit/third/api/hr/v/queryLend",
						data: {
							"orderNo": orderNo
						}
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.kgLoan = data.data;
							console.log(scope.kgLoan);
							if(scope.kgLoan) {
								scope.kgLoan.ifCeOut = scope.kgLoan.ifCeOut == "Y" ? "是" : "否";
							}
						}
					});
				}
			};
		});
		app.directive("allocationFundHuarongAppointDetail", function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuarongAppointDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
				}
			};
		});
		app.directive("allocationFundHuarongBusinfoDetail", function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuarongBusinfoDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					scope.isImgEdit = false;
					var orderNo = route.getParams().orderNo;
					$http({
						method: 'POST',
						url: "/credit/risk/allocationfund/v/loadHuarongBusInfo",
						data: {
							"orderNo": orderNo
						}
					}).success(function(data) {
						console.log(data);
						if("SUCCESS" == data.code) {
							scope.huarongImageDatas = data.data;
							console.log(scope.huarongImageDatas)
						}
					});
				}
			};
		});
		app.directive("allocationFundHuarongApprovalDetail", function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuarongApprovalDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();

				}
			};
		});

		app.directive("allocationFundHuarongHouseDetail", function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuarongHouseDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();

				}
			};
		});

		app.directive("allocationFundHuarongIndivDetail", function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuarongIndivDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();

				}
			};
		});

		//----------------资料推送信息详情end------------------------------
		////添加字典
		app.directive('dictBox', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/submitBox.html',
				link: function(scope) {
					var cityCode = 0;
					if(scope.order) {
						cityCode = scope.order.cityCode;
					} else {
						cityCode = scope.borrow.cityCode;
					}
					scope.sumbitDto = new Object();
					scope.sumbitDto.agencgId = "1";
					scope.sumbitDto.cityCode = cityCode;
				}
			};
		});

		//提单详情
		app.directive('elementEntranceDetail', function($http, $timeout, $state, route) {
			return {
				restrict: "E",
				templateUrl: function() {
					var productCode = route.getParams().productCode;
					if(productCode == "04"||productCode == "06"||productCode == "07") {
						return '/template/order-common/orderCommonDetail.html';
					} else {
						return '/template/sl/order/elementEntranceDetail.html';
					}

				},
				transclude: true,
				link: function(scope) {
					var productCode = route.getParams().productCode;
					if(productCode == "04"||productCode == "06"||productCode == "07") {
						var params = {
							"orderNo": route.getParams().orderNo,
							"processId": "placeOrder"
						}
						scope.isEnterprise = false;
						
						//获取页面配置 start
						function getPageConfig() {
							$http({
								method: 'POST',
								url: "/credit/config/page/pc/base/v/pageConfig",
								data: params
							}).success(function(data) {
								if(data.code == "SUCCESS") {
									scope.pageConfigDto = data.data;
									scope.changeView(scope.pageConfigDto.pageTabConfigDtos[0]);
								}
								
								angular.forEach(scope.pageConfigDto.pageTabConfigDtos[0].pageTabRegionConfigDtos[0].valueList[0],function(data1){
									if(data1.specialKey == 'customerType'){
										if(data1.value == "个人"){
											scope.isEnterprise = false;
										}else{
											scope.isEnterprise = true;
										}
									}
								});
								
							});
						}
						//获取页面配置 end

						//切换tab页 start
						scope.changeView = function(pageTabConfigDto) {
							scope.showView = pageTabConfigDto.title;
							scope.pageTabConfigDto = pageTabConfigDto;
							scope.isConfigEdit = false;

							if(pageTabConfigDto.title == "询价/查档/诉讼" || pageTabConfigDto.title == "影像资料") {
								pageTabConfigDto.isShow = true;
								return false;
							}

							if(pageTabConfigDto.title == "询价/查档/诉讼") {
								scope.isConfigEdit = true;
							}

							angular.forEach(pageTabConfigDto.pageTabRegionConfigDtos, function(pageTabRegionConfigDto) {
								angular.forEach(pageTabRegionConfigDto.valueList, function(data2, index) {
									showHide(data2);
									angular.forEach(data2, function(data, index, array) {

										if(data.type == 5) {
											data.dataList = new Array();
											if(data.value) {
												$timeout(function() {
													angular.forEach(data.value.split(','), function(data1) {
														data.dataList.push(data1)
													})
												});
											}
										}

										if(!data.value) {
											data.value = "-";
										}

									});
								});
							});
						}

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

						//切换tab页 end
						getPageConfig();

					} else {
						scope.obj = new Object();
						var orderNo = route.getParams().orderNo;
						var params = {
							"orderNo": orderNo
						}
						scope.isElementEditShow = false;
						scope.isCreditEditShow = false;
						scope.inRelationOrderNo2Detail = "" != route.getParams().relationOrderNo && route.getParams().relationOrderNo.length > 5 ? route.getParams().relationOrderNo : null;

						scope.changeView = function(view) {
							$timeout(function() {
								$("textarea").txtaAutoHeight();
							});
							scope.showView = view;
							var state = $state.current.name;
							if(state.indexOf('placeElementEdit') >= 0 || state.indexOf('placeEnquiryEdit') >= 0 || state.indexOf('placeBusinfoEdit') >= 0 || state.indexOf('placeCreditEdit') >= 0) {
								state = state.substring(0, state.lastIndexOf('.'));
							}
							if(scope.isElementEditShow && view == 4) {
								if(state.indexOf('orderDetail') < 0) {
									$state.go(state + '.placeElementEdit');
								}
							} else if(view == 5) {
								$state.go(state + (null != scope.inRelationOrderNo2Detail ? '.placeEnquiryEditCD' : '.placeEnquiryEdit'));
							} else if(view == 6) {
								$state.go(state + (null != scope.inRelationOrderNo2Detail ? '.placeBusinfoEditCD' : '.placeBusinfoEdit'));
							} else if(scope.isCreditEditShow && view == 7) {
								$state.go(state + '.placeCreditEdit');
							} else {
								if(state.indexOf('placeElementEdit') < 0) {
									$state.go(state);
								}
							}
						}

						var state = $state.current.name;
						if($state.current.name == 'orderEdit.elementEdit' || $state.current.name == 'orderEdit.elementEdit.placeElementEdit') {
							scope.isElementEditShow = true;
							scope.changeView(4);
						} else {
							if(state.indexOf('placeElementEdit') < 0) {
								scope.changeView(1);
							} else {
								scope.changeView(4);
							}
						}

						scope.showCreditEdit = function() {
							scope.changeView(7);
							scope.isCreditEditShow = true;
						}
					}

				}
			};
		});

		app.directive('yunnanRepaymentPlan', function($http, $filter, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/yunnanRepaymentPlan.html',
				link: function(scope) {

					var orderNo = route.getParams().orderNo;

					scope.isPush = true;

					scope.yunnanInit = function() {
						scope.yunnanRepaymentPlanDto = new Object();
						$http({
							method: 'POST',
							url: "/credit/third/api/yntrust/v/selectRepaymentPlan",
							data: {
								"orderNo": orderNo
							}
						}).success(function(data) {
							if(data.data) {
								scope.yunnanRepaymentPlanDto = data.data;
								scope.yunnanRepaymentPlanDto.repayDate = $filter('date')(scope.yunnanRepaymentPlanDto.repayDate, 'yyyy-MM-dd HH:mm');
							} else {
								$http({
									method: 'POST',
									url: "/credit/third/api/yntrust/v/selectBorrowContractImg",
									data: {
										"orderNo": orderNo
									}
								}).success(function(data1) {
									var tempDate = new Date(scope.yunnanStatusDto.actExcutedTime);
									scope.yunnanRepaymentPlanDto.repayDate = $filter('date')(tempDate.setDate(tempDate.getDate() + data1.data.contract.borrowingDays), 'yyyy-MM-dd HH:mm');
									scope.yunnanRepaymentPlanDto.borrowingDays = data1.data.contract.borrowingDays;
									scope.yunnanRepaymentPlanDto.repayPrincipal = data1.data.contract.amount;
									scope.yunnanRepaymentPlanDto.repayProfit = scope.yunnanRepaymentPlanDto.borrowingDays * scope.yunnanRepaymentPlanDto.repayPrincipal * data1.data.contract.signRate;
								})
							}
						})
					}

					scope.yunnanStatus = function() {
						$http({
							method: 'POST',
							url: "/credit/third/api/yntrust/v/queryTradingStatus",
							data: {
								"orderNo": orderNo
							}
						}).success(function(data) {
							if(data && data.code == 'SUCCESS') {
								scope.yunnanStatusDto = data.data[0];
								if(scope.yunnanStatusDto.processStatus == 1) {
									scope.isPush = true;
									scope.yunnanInit();
								} else {
									alert(scope.yunnanStatusDto.result);
								}
							}
						})
					}

					scope.yunnanStatus();

					scope.yunnanRepaymentPlanDtoSubmit = function() {
						scope.yunnanRepaymentPlanDto.orderNo = orderNo;
						$http({
							method: 'POST',
							url: "/credit/third/api/yntrust/v/addRepaymentPlan",
							data: scope.yunnanRepaymentPlanDto
						}).success(function(data) {
							box.closeAlert();
							box.boxAlert(data.msg, function() {
								if(data.code == "SUCCESS") {
									box.closeAlert();
								}
							});
						})
					}

					scope.yunnanRepaymentPlanDtoCancel = function() {
						scope.isPush = false;
					}

				}
			};
		});

		app.directive('stringToNumber', function() {
			return {
				require: 'ngModel',
				link: function(scope, element, attrs, ngModel) {
					ngModel.$parsers.push(function(value) {
						return '' + value;
					});
					ngModel.$formatters.push(function(value) {
						return parseFloat(value, 10);
					});
				}
			};
		});


		//贷后还款管理
		app.directive("afterLoanDetail", function($compile, $timeout, $http, $state,$cookies,$filter, process, route, box) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/afterLoanDetail.html'
			};
		});
		//贷后日志
		app.directive("afterLoanLog", function($compile, $timeout, $http, $state,$cookies,$filter, process, route, box) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/afterLoanLog.html'
			}
		});

		//文件上传
		app.directive("commonFileUpload", function($compile, $timeout, $http, $state,$cookies,$filter, process, route, box) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/common/common-file-upload.html'
			};
		});
		//文件预览
		app.directive("commonFileDetail", function($compile, $timeout, $http, $state,$cookies,$filter, process, route, box) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/common/common-file-detail.html'
			};
		});

	};
});