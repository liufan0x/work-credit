define(function(require, exports, module) {
	exports.extend = function(app) {

		app.directive('ordinaryDockingEdit', function($compile, $timeout, $http, $state, $filter, route, box) {
			return {
				restrict: "E",
				templateUrl: '/plugins/fund-directive/ordinary/ordinaryDocking.html',
				transclude: true,
				link: function(scope) {

					scope.productCode = route.getParams().productCode;
					scope.relationOrderNo = route.getParams().relationOrderNo;
					scope.firstObj = new Object();
					scope.obligeeA = [{
						id: "1",
						name: "标准"
					}, {
						id: "2",
						name: "非标准"
					}];

					scope.obligeeB = [{
						id: "1",
						name: "是"
					}, {
						id: "2",
						name: "否"
					}];

					scope.isImgEdit = true;
					var orderNo = scope.orderNo;
					$http({
						method: 'POST',
						url: "/credit/third/api/ordinary/v/ordinaryDetail",
						data: {
							"orderNo": orderNo,
							"fundId": scope.ordinaryFundId
						}
					}).success(function(data) {

						if(data.data && !data.data.ordinary) {
							$http({
								method: 'POST',
								url: "/credit/order/borrow/v/query",
								data: {
									"orderNo": scope.relationOrderNo != "0" ? scope.relationOrderNo : scope.orderNo
								}
							}).success(function(data) {
								scope.ordinaryObj = data.data;
								scope.ordinaryObj.orderNo = scope.orderNo;
								//回款日期
								if(scope.ordinaryObj.orderReceivableForDto && scope.ordinaryObj.orderReceivableForDto.length > 0) {
									for(var i = 0; i < scope.ordinaryObj.orderReceivableForDto.length; i++) {
										scope.ordinaryObj.orderReceivableForDto[i].payMentAmountDate = scope.ordinaryObj.orderReceivableForDto[i].payMentAmountDateStr;
									}
								}
								if(scope.ordinaryObj.isLoanBank == 0) {
									scope.ordinaryObj.isLoanBank = "";
								} else {
									scope.ordinaryObj.isLoanBank = String(scope.ordinaryObj.isLoanBank);
								}
								if(scope.ordinaryObj.loanBankNameId == 0) {
									scope.ordinaryObj.loanBankNameId = "";
								} else {
									scope.ordinaryObj.loanBankNameId = String(scope.ordinaryObj.loanBankNameId);
								}
								if(scope.ordinaryObj.loanSubBankNameId == 0) {
									scope.ordinaryObj.loanSubBankNameId = "";
								} else {
									scope.ordinaryObj.loanSubBankNameId = String(scope.ordinaryObj.loanSubBankNameId);
								}
								scope.dataList.forEach(function(val, index) {
									if(val.fundId == scope.ordinaryFundId) {
										scope.ordinaryObj.loanAmount = val.loanAmount;
									}
								})
							});
						} else {
							scope.ordinaryObj = data.data.ordinary;
							scope.ordinaryObj.isLoanBank = String(scope.ordinaryObj.isLoanBank);
							scope.ordinaryObj.loanBankNameId = String(scope.ordinaryObj.loanBankNameId);
							scope.ordinaryObj.loanSubBankNameId = String(scope.ordinaryObj.loanSubBankNameId);
						}

						if(data.data) {
							var ordinaryAudit = data.data.ordinaryAudit;
						}

						$http({
							method: 'POST',
							url: "/credit/third/api/ordinary/v/loadOrdinaryBusInfo",
							data: {
								"orderNo": scope.orderNo,
								"ordinaryFund": scope.ordinaryFundId,
								"productCode": route.getParams().productCode
							}
						}).success(function(data) {
							scope.ordinaryimageDatas = data.data;
							scope.isAuditShow = scope.ordinaryimageDatas.isAuditShow;
							if(!ordinaryAudit && scope.isAuditShow) {
								$http({
									url: '/credit/risk/first/v/loadFirst',
									method: 'POST',
									data: {
										"orderNo": route.getParams().orderNo
									}
								}).success(function(data) {
									if("SUCCESS" == data.code) {
										scope.firstObj = data.data.firstDto;
										if(scope.firstObj.foreclosureAuditList != null && scope.firstObj.foreclosureAuditList.length > 0) {
											scope.firstObj.loanName = scope.firstObj.foreclosureAuditList[0].loanName;
											scope.firstObj.loanAccount = scope.firstObj.foreclosureAuditList[0].loanAccount;
											if(scope.firstObj.foreclosureAuditList[0].loanBankId == 0) {
												scope.firstObj.loanBankId = "";
											} else {
												scope.firstObj.loanBankId = String(scope.firstObj.foreclosureAuditList[0].loanBankId);
											}
											if(scope.firstObj.foreclosureAuditList[0].loanBankSubId == 0) {
												scope.firstObj.loanBankSubId = "";
											} else {
												scope.firstObj.loanBankSubId = String(scope.firstObj.foreclosureAuditList[0].loanBankSubId);
											}
										}
										if(scope.firstObj.firstPaymentAuditList != null && scope.firstObj.firstPaymentAuditList.length > 0) {
											scope.firstObj.paymentName = scope.firstObj.firstPaymentAuditList[0].paymentName;
											scope.firstObj.paymentAccount = scope.firstObj.firstPaymentAuditList[0].paymentAccount;
											if(scope.firstObj.firstPaymentAuditList[0].paymentBankId == 0) {
												scope.firstObj.paymentBankId = "";
											} else {
												scope.firstObj.paymentBankId = String(scope.firstObj.firstPaymentAuditList[0].paymentBankId);
											}
											if(scope.firstObj.firstPaymentAuditList[0].paymentBankSubId == 0) {
												scope.firstObj.paymentBankSubId = "";
											} else {
												scope.firstObj.paymentBankSubId = String(scope.firstObj.firstPaymentAuditList[0].paymentBankSubId);
											}
										}
										if(!scope.firstObj.business || scope.firstObj.business == "null") {
											scope.firstObj.business = "1";
										} else {
											scope.firstObj.business = String(scope.firstObj.business);
										}
										$http({
											url: '/credit/order/auditFinal/v/processDetails',
											method: 'POST',
											data: {
												'orderNo': scope.orderNo
											}
										}).success(function(data) {
											if("SUCCESS" == data.code) {
												scope.firstObj.finalRemark = data.data.remark;
											}
										});

										$http({
											url: '/credit/order/auditOfficer/v/processDetails',
											method: 'POST',
											data: {
												'orderNo': scope.orderNo
											}
										}).success(function(data) {
											if("SUCCESS" == data.code) {
												scope.firstObj.officerRemark = data.data.remark;
											}
										});

										scope.dataList.forEach(function(val, index) {
											if(val.fundId == scope.ordinaryFundId) {
												scope.firstObj.loanAmount = val.loanAmount;
											}
										})
									}
								});
							} else {
								scope.firstObj = ordinaryAudit;
								if(scope.firstObj) {
									scope.firstObj.business = String(scope.firstObj.business);
									scope.firstObj.loanBankId = String(scope.firstObj.loanBankId);
									scope.firstObj.loanBankSubId = String(scope.firstObj.loanBankSubId);
									scope.firstObj.paymentBankId = String(scope.firstObj.paymentBankId);
									scope.firstObj.paymentBankSubId = String(scope.firstObj.paymentBankSubId);
								}
							}
						});
					});

					scope.ordinarySave = function() {
						box.waitAlert();
						var receivableForTime = "";
						if(scope.ordinaryObj.orderReceivableForDto && scope.ordinaryObj.orderReceivableForDto.length > 0) {
							for(var i = 0; i < scope.ordinaryObj.orderReceivableForDto.length; i++) {
								receivableForTime += scope.ordinaryObj.orderReceivableForDto[i].payMentAmountDateStr + ",";
							}
						}
						if(scope.ordinaryObj.isLoanBank == null || scope.ordinaryObj.isLoanBank == "null") {
							scope.ordinaryObj.isLoanBank = 0;
						}
						if(scope.ordinaryObj.loanBankNameId == null || scope.ordinaryObj.loanBankNameId == "null") {
							scope.ordinaryObj.loanBankNameId = 0;
						}
						if(scope.ordinaryObj.loanSubBankNameId == null || scope.ordinaryObj.loanSubBankNameId == "null") {
							scope.ordinaryObj.loanSubBankNameId = 0;
						}
						if(scope.firstObj && !scope.firstObj.loanBankId) {
							scope.firstObj.loanBankId = "0"
						}
						if(scope.firstObj && !scope.firstObj.loanBankSubId) {
							scope.firstObj.loanBankSubId = "0"
						}
						if(scope.firstObj && !scope.firstObj.paymentBankId) {
							scope.firstObj.paymentBankId = "0"
						}
						if(scope.firstObj && !scope.firstObj.paymentBankSubId) {
							scope.firstObj.paymentBankSubId = "0"
						}

						if(scope.firstObj && !scope.firstObj.officerRemark) {
							var officerRemark = scope.firstObj.officerRemark;
						}

						if(scope.firstObj && !scope.firstObj.finalRemark) {
							var finalRemark = scope.firstObj.finalRemark;
						}

						var param = {};
						if(scope.isAuditShow) {
							param = {
								kgOrdinary: scope.ordinaryObj,
								kgOrdinaryAudit: scope.firstObj,
								ordinaryFund: scope.ordinaryFundId,
								receivableForTime: receivableForTime.substring(0, receivableForTime.length - 1),
								finalRemark: finalRemark,
								officerRemark: officerRemark,
								productCode: route.getParams().productCode,
								relationOrderNo: route.getParams().relationOrderNo,
							}
						} else {
							param = {
								kgOrdinary: scope.ordinaryObj,
								ordinaryFund: scope.ordinaryFundId,
								receivableForTime: receivableForTime.substring(0, receivableForTime.length - 1),
								finalRemark: finalRemark,
								officerRemark: officerRemark,
								productCode: route.getParams().productCode,
								relationOrderNo: route.getParams().relationOrderNo,
							}
						}

						$http({
							method: 'POST',
							url: "/credit/third/api/ordinary/v/ordinaryApply",
							data: param
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								box.closeWaitAlert();
								/*$(angular.element.find("ordinary-docking-edit")).remove();
								box.boxAlert(data.msg);*/
								var name;
								name = confirm(data.msg);
								if(name == true) {
									$(angular.element.find("ordinary-docking-edit")).remove();
								}

							} else {
								box.boxAlert(data.msg);
							}
							/*box.closeWaitAlert();
							box.boxAlert(data.msg);*/
						});

					}

					scope.selectTypeCheck = function(type) {
						scope.selectTypeId = type;
					}

					scope.addOrdinaryImg = function(url) {
						if(url.length > 0) {
							var param = {
								orderNo: scope.orderNo,
								typeId: scope.selectTypeId,
								url: url,
								fundId: scope.ordinaryFundId,
								productCode: route.getParams().productCode
							}
							$http({
								url: "/credit/third/api/ordinary/v/addOrdinaryImg",
								method: 'POST',
								data: param
							}).success(function(data) {
								if("SUCCESS" == data.code) {
									loadBusinfo();
									box.boxAlert("上传图片成功");
								} else {
									box.boxAlert(data.msg);
								}
							});
						}
					}

					scope.delOrdinaryImgShow = function(imgList) {
						scope.allCheck = false;
						scope.imgList = imgList;
						scope.isDelOrdinaryImgShow = true;
					}

					scope.delOrdinaryImgCancel = function() {
						scope.isDelOrdinaryImgShow = false;
					}

					scope.delOrdinaryImg = function() {
						var ids = "";
						var isCheck = false;
						angular.forEach(scope.imgList, function(obj, index, array) {
							if(obj.check) {
								ids += obj.id + ",";
								isCheck = true;
							}
						});
						if(isCheck) {
							ids = ids.substring(0, ids.length - 1);
						} else {
							box.boxAlert("请选择要删除的图片");
							return;
						}
						$http({
							method: 'POST',
							url: "/credit/third/api/ordinary/v/deleteOrdinaryImg",
							data: {
								"orderNo": scope.orderNo,
								"typeId": scope.imgList[0].typeId,
								"ids": ids,
								"ordinaryFund": scope.ordinaryFundId,
								"productCode": route.getParams().productCode
							}
						}).success(function(result) {
							if("SUCCESS" == result.code) {
								scope.allCheck = false;
								scope.isDelOrdinaryImgShow = false;
								loadBusinfo();
							} else {
								box.boxAlert(result.msg);
							}
						});
					}

					scope.selectAllImg = function() {
						if(!scope.allCheck) {
							scope.allCheck = true;
						} else {
							scope.allCheck = false;
						}
						angular.forEach(scope.imgList, function(obj, index, array) {
							obj.check = scope.allCheck;
						});
					}

					function loadBusinfo() {
						$http({
							method: 'POST',
							url: "/credit/third/api/ordinary/v/loadOrdinaryBusInfo",
							data: {
								"orderNo": scope.orderNo,
								"ordinaryFund": scope.ordinaryFundId,
								"productCode": route.getParams().productCode
							}
						}).success(function(data) {
							scope.ordinaryimageDatas = data.data;
						});
					}

					scope.ordinaryClose = function() {
						$timeout(function() {
							$(angular.element.find("ordinary-docking-edit")).remove();
						});
					}

				}
			}
		});

	};
});