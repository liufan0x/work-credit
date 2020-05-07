define(function(require, exports, module) {
	exports.extend = function(app) {

		app.directive('yunnanDockingEdit', function($compile, $timeout, $http, $state, $filter, process, route, box) {
			return {
				restrict: "E",
				templateUrl: '/plugins/fund-directive/yunnan/yunnanDocking.html',
				transclude: true,
				link: function(scope) {

					scope.isImgEdit = true;
					scope.ynStatus = new Object();
					scope.YNZFXX = new Object();
					scope.YNHKInfo = new Object();
					scope.kgYunnanOrder = new Object();
					scope.kgYunnanContract = new Object();
					scope.kgYunnanImg = new Object();
					scope.audit_delay = new Object();
					scope.bankcardAttributionProvinceList = new Array();
					scope.bankcardAttributionCityList = new Array();
					scope.bankcardAttributionCountyList = new Array();
					scope.fBankcardAttributionCityList = new Array();
					scope.pBankcardAttributionCityList = new Array();

					if(scope.fundCode == '114') {
						scope.kgYunnanOrder.ynProductCode = 'I16800';
						scope.kgYunnanOrder.ynProductName = '一期';

					} else if(scope.fundCode == '115') {
						scope.kgYunnanOrder.ynProductCode = 'I22500'
						scope.kgYunnanOrder.ynProductName = '二期';
					}
					scope.yunBankShow = true;
					

					//关闭窗口
					scope.yunnanClose = function() {
						scope.yunBankShow = false;
						$timeout(function() {
							$(angular.element.find("yunnan-docking-edit")).remove();
						});
					}

					//刷新状态
					scope.yunnanRefresh = function(fl) {
						$http({
							method: 'POST',
							url: '/credit/third/api/yntrust/v/ynStuats',
							data: {
								'orderNo': scope.orderNo,
								'ynProductCode': scope.kgYunnanOrder.ynProductCode
							}
						}).success(function(res) {
							scope.ynStatus = res.data
							if(fl) {
								if(res.code == 'SUCCESS') {
									box.boxAlert("刷新成功");
								} else {
									box.boxAlert("刷新失败");
								}
							}
							//初始化应还款计划
							if(scope.ynStatus.processStatus) {
								yunNanAuditInit();
							}
						});
					}

					//撤回
					scope.yunnanCancel = function() {
						box.waitAlert();
						$http({
							method: 'POST',
							url: '/credit/third/api/yntrust/v/cancelLoan',
							data: {
								'orderNo': scope.orderNo
							}
						}).success(function(data) {
							box.closeWaitAlert();
							box.boxAlert(data.msg);
							scope.yunnanRefresh();
						});
					}

					//提交借款信息，合同信息，影像资料
					scope.yunnanSave = function(type) {
						scope.kgYunnanOrder.orderNo = scope.orderNo;
						scope.kgYunnanContract.orderNo = scope.orderNo;
						scope.isAudit = false;
						if(scope.kgYunnanOrderForm.$invalid) {
							box.boxAlert("借款人信息数据不完整");
							scope.isAudit = true;
							scope.yunnanTitle = 'yunNanOrderShow';
							return;
						} else if(scope.kgYunnanContractForm.$invalid) {
							box.boxAlert("合同信息数据不完整");
							scope.isAudit = true;
							scope.yunnanTitle = 'yunNanContractShow';
							return;
						}
						box.waitAlert();
						$http({
							url: '/credit/third/api/yntrust/v/addBorrowAndContract',
							method: 'POST',
							data: {
								"borrow": scope.kgYunnanOrder,
								"contract": scope.kgYunnanContract,
								"type":type
							}
						}).success(function(data) {
							box.closeWaitAlert();
							box.boxAlert(data.msg)
							scope.yunnanRefresh();
						});
					}
 
					//保存借款信息
					scope.submitBorrow = function(type) {
						scope.kgYunnanOrder.orderNo = scope.orderNo;
						scope.kgYunnanContract.orderNo = scope.orderNo;
						scope.isAudit = false;
						box.waitAlert();
						$http({
							url: '/credit/third/api/yntrust/v/addBorrowAndContract',
							method: 'POST',
							data: {
								"borrow": scope.kgYunnanOrder,
								"contract": scope.kgYunnanContract,
								"type":type
							}
						}).success(function(data) {
							box.closeWaitAlert();
							box.boxAlert(data.msg)
							scope.yunnanRefresh();
						});
					}
					
					//保存合同信息
					scope.submitContract = function(type) {
						scope.kgYunnanOrder.orderNo = scope.orderNo;
						scope.kgYunnanContract.orderNo = scope.orderNo;
						scope.isAudit = false;
						box.waitAlert();
						$http({
							url: '/credit/third/api/yntrust/v/addBorrowAndContract',
							method: 'POST',
							data: {
								"borrow": scope.kgYunnanOrder,
								"contract": scope.kgYunnanContract,
								"type":type
							}
						}).success(function(data) {
							box.closeWaitAlert();
							box.boxAlert(data.msg)
							scope.yunnanRefresh();
						});
					}
                    /* scope.submitBorrow = function(type) {
                    	 scope.kgYunnanOrder.orderNo = scope.orderNo;
                    	 box.waitAlert();
 						$http({
 							url: '/credit/third/api/yntrust/v/addBorrowAndContract',
 							method: 'POST',
 							data: {
 								"borrow": scope.kgYunnanOrder,
								"contract": scope.kgYunnanContract,
 								"type": type
 							}
 						}).success(function(data) {
 							box.closeWaitAlert();
 							box.boxAlert(data.msg)
 							scope.yunnanRefresh();
 						});
					}*/
					
					scope.yunnanImgs = function() {
						box.waitAlert();
						$http({
							url: '/credit/third/api/yntrust/v/pushImgs',
							method: 'POST',
							data: {
								"orderNo": scope.orderNo,
								"iDCardNo": scope.kgYunnanOrder.iDCardNo
							}
						}).success(function(data) {
							box.closeWaitAlert();
							if(data.msg == "请求数据为空") {
								data.msg = "请上传新的影像资料";
							}
							box.boxAlert(data.msg)
							yunNanImgInit();
						});
					}

					//刷新影像资料
					function yunNanImgInit() {
						$http({
							url: '/credit/third/api/yntrust/v/selectBusinfo',
							method: 'POST',
							data: {
								"orderNo": scope.orderNo,
								'ynProductCode': scope.kgYunnanOrder.ynProductCode
							}
						}).success(function(data) {
							scope.kgYunnanImg = data.data;
							angular.forEach(scope.kgYunnanImg, function(data) {
								angular.forEach(data.img, function(data1) {
									if(data1.url.indexOf('pdf')>0){
										data1.isPdf = true;
									}else{
										data1.isPdf = false;
									}
								});
							});

						});
					}
                   
					
					
					//推送应还款计划
					scope.yunnanRepaySchedule = function() {
						box.waitAlert()
						$http({
							method: 'POST',
							data: {
								order: "1",
								orderNo: scope.orderNo,
								repayDate: new Date(scope.yunnanAudit.repayDate),
								borrowingDays: scope.yunnanAudit.borrowingDays,
								repayPrincipal: scope.yunnanAudit.repayPrincipal,
								repayProfit: scope.yunnanAudit.repayProfit,
								partnerScheduleNo: '',
								ynProductCode: scope.kgYunnanOrder.ynProductCode,
								ynProductName: scope.kgYunnanOrder.ynProductName
							},
							url: "/credit/third/api/yntrust/v/addRepaymentPlan"
						}).success(function(data) {
							box.closeWaitAlert();
							box.boxAlert(data.msg);
							scope.yunnanRefresh();
						});
					}

					//推送展期
					scope.yunnanUpdateRepaySchedule = function() {
						box.waitAlert();
						scope.audit_delay.orderNo = scope.orderNo;
						scope.audit_delay.ynProductCode = scope.kgYunnanOrder.ynProductCode;
						scope.audit_delay.ynProductName = scope.kgYunnanOrder.ynProductName;
						scope.audit_delay.order = '1'
						scope.audit_delay.partnerScheduleNo = ''
						$http({
							method: "POST",
							url: "/credit/third/api/yntrust/v/updateRepaySchedule",
							data: scope.audit_delay
						}).success(function(res) {
							box.closeWaitAlert();
							box.boxAlert(res.msg, function() {
								if(res.code == 'SUCCESS') {
									scope.yunnanRefresh();
									scope.showZhanqi1 = false;
								}
							});
						})
					}

					//推送回款计划
					scope.yunnanRepayment = function() {
						box.waitAlert()
						$http({
							method: 'POST',
							url: '/credit/third/api/yntrust/v/addRepaymentInfoAndPayInfo',
							data: {
								orderNo: scope.orderNo,
								repayDate: new Date(scope.YNHKInfo.repayDate),
								realityPayDate: new Date(scope.YNHKInfo.realityPayDate),
								repayPrincipal: scope.YNHKInfo.repayPrincipal,
								givePayPrincipal: scope.YNHKInfo.givePayPrincipal,
								repayProfit: scope.YNHKInfo.repayProfit,
								givePayProfit: scope.YNHKInfo.givePayProfit,
								borrowingDays: scope.YNHKInfo.borrowingDays,
								lateDay: scope.YNHKInfo.lateDay,
								penaltyFee: scope.YNHKInfo.penaltyFee,
								latePenalty: scope.YNHKInfo.latePenalty,
								lateFee: scope.YNHKInfo.lateFee,
								lateOtherCost: scope.YNHKInfo.lateOtherCost,
								paymentMethodName: scope.YNHKInfo.dictPaymentMethod.slice(5),
								paymentMethod: scope.YNHKInfo.dictPaymentMethod.slice(0, 4),
								ynProductCode: scope.kgYunnanOrder.ynProductCode,
								ynProductName: scope.kgYunnanOrder.ynProductName,
								overDueFee: scope.YNHKInfo.overDueFee
							}
						}).success(function(res) {
							box.closeWaitAlert();
							box.boxAlert(res.msg);
							$timeout(function() {
								scope.yunnanRefresh();
							});
						})
					}

					//推送支付信息
					scope.yunnanPay = function() {
						box.waitAlert()
						scope.YNZFXX.repayDate = scope.YNHKInfo.repayDate;
						scope.YNZFXX.realityPayDate = scope.YNHKInfo.realityPayDate;
						scope.YNZFXX.orderNo = scope.orderNo;
						scope.YNZFXX.ynProductCode = scope.kgYunnanOrder.ynProductCode;
						scope.YNZFXX.ynProductName = scope.kgYunnanOrder.ynProductName;
						scope.YNZFXX.voucherType = scope.YNZFXX.dictVoucherType.slice(0, 1);
						$http({
							method: "POST",
							url: "/credit/third/api/yntrust/v/paymentOrder",
							data: scope.YNZFXX
						}).success(function(res) {
							box.closeWaitAlert();
							box.boxAlert(res.msg);
							$timeout(function() {
								scope.yunnanRefresh();
							});
						})
					}

					scope.$watch("kgYunnanContract.bankcardAttributionProvinceCode", function(newValue, oldValue) {
						if(newValue) {
							fProvince();
						}
					});

					scope.$watch("kgYunnanContract.bankcardAttributionCityCode", function(newValue, oldValue) {
						if(newValue) {
							fCity();
						}
					});

					function fProvince() {
						var fl = false;
						scope.fBankcardAttributionCityList = new Array();
						angular.forEach(scope.bankcardAttributionCityList, function(data) {
							if(data.parentCode == scope.kgYunnanContract.bankcardAttributionProvinceCode) {
								scope.fBankcardAttributionCityList.push(data);
								if(data.code == scope.kgYunnanContract.bankcardAttributionCityCode) {
									fl = true;
								}
							}
						});

						if(!fl) {
							scope.kgYunnanContract.bankcardAttributionCityCode = "";
						}
					}

					function fCity() {
						if(!scope.bankcardAttributionCountyList) {
							return;
						}
						var fl = false;
						scope.fBankcardAttributionCountyList = new Array();
						angular.forEach(scope.bankcardAttributionCountyList, function(data) {
							if(data.parentCode == scope.kgYunnanContract.bankcardAttributionCityCode) {
								scope.fBankcardAttributionCountyList.push(data);
								if(data.code == scope.kgYunnanContract.bankcardAttributionCountyCode) {
									fl = true;
								}
							}
						});
						if(!fl) {
							scope.kgYunnanContract.bankcardAttributionCountyCode = "";
						}
					}

					scope.$watch("kgYunnanContract.paymentsBankAttributionProvinceCode", function(newValue, oldValue) {
						if(newValue) {
							pProvince();
						}
					});

					scope.$watch("kgYunnanContract.paymentsBankAttributionCityCode", function(newValue, oldValue) {
						if(newValue) {
							pCity();
						}
					});

					function pProvince() {
						var fl = false;
						scope.pBankcardAttributionCityList = new Array();
						angular.forEach(scope.bankcardAttributionCityList, function(data) {
							if(data.parentCode == scope.kgYunnanContract.paymentsBankAttributionProvinceCode) {
								scope.pBankcardAttributionCityList.push(data);
								if(data.code == scope.kgYunnanContract.paymentsBankAttributionCityCode) {
									fl = true;
								}
							}
						});

						if(!fl) {
							scope.kgYunnanContract.paymentsBankAttributionCityCode = "";
						}
					}

					function pCity() {
						var fl = false;
						scope.pBankcardAttributionCountyList = new Array();
						angular.forEach(scope.bankcardAttributionCountyList, function(data) {
							if(data.parentCode == scope.kgYunnanContract.paymentsBankAttributionCityCode) {
								scope.pBankcardAttributionCountyList.push(data);
								if(data.code == scope.kgYunnanContract.paymentsBankAttributionCountyCode) {
									fl = true;
								}
							}
						});
						if(!fl) {
							scope.kgYunnanContract.paymentsBankAttributionCountyCode = "";
						}
					}

					scope.yunnanCheck = function(typeId, type) {
						scope.yunnanTypeId = typeId;
						scope.yunnanType = type;
					}

					scope.yunnanImgSave = function(imgs) {

						var imgList = new Array();
						angular.forEach(imgs, function(data, index, a) {
							var tempDate = new Object();
							tempDate.orderNo = scope.orderNo;
							tempDate.typeId = scope.yunnanTypeId;
							tempDate.type = scope.yunnanType;
							tempDate.url = data;
							tempDate.ynProductCode = scope.kgYunnanOrder.ynProductCode;
							tempDate.ynProductName = scope.kgYunnanOrder.ynProductName;
							imgList.push(tempDate);
						});

						$http({
							url: '/credit/third/api/yntrust/v/batchAddImage',
							method: 'POST',
							data: imgList
						}).success(function(data) {
							box.closeWaitAlert();
							if(data.code == "SUCCESS") {
								box.closeAlert();
								yunNanImgInit();
							}
						});
					}
					

					scope.delYunnanImgShow = function(obj) {
						scope.allYunnanCheck = false;
						angular.forEach(obj, function(data, index, array) {
							data.check = false;
							data.isCheck = false;
						});
						scope.imgYunnanObject = obj;
						scope.isDelYunnanImgShow = true;
					}

					scope.delYunnanImgCancel = function() {
						scope.isDelYunnanImgShow = false;
					}

					scope.selectYunnanAllImg = function() {
						if(!scope.allYunnanCheck) {
							scope.allYunnanCheck = true;
						} else {
							scope.allYunnanCheck = false;
						}
						angular.forEach(scope.imgYunnanObject, function(obj, index, array) {
							obj.check = scope.allYunnanCheck;
						});
					}

					scope.delYunnanImg = function() {
						var isCheck = false;
						var ckeckImg = new Array();
						var tempList = scope.imgYunnanObject.concat();
						var imgs = "";
						angular.forEach(tempList, function(obj, index, array) {
							if(obj.check) {
								isCheck = true;
								imgs += obj.id + ",";
							}
						});
						if(!isCheck) {
							box.boxAlert("请选择要删除的图片");
							return;
						} else {
							imgs = imgs.substring(0, imgs.length - 1);
							$http({
								url: '/credit/third/api/yntrust/v/batchDeleteImg',
								method: 'POST',
								data: {
									ids: imgs
								}
							}).success(function(data) {
								box.closeWaitAlert();
								box.boxAlert(data.msg, function() {
									if(data.code == "SUCCESS") {
										box.closeAlert();
										yunNanImgInit();
									}
								});
							});
							scope.isDelYunnanImgShow = false;
						}
					}

					scope.yunNanInit = function() {
						$http({
							url: '/credit/data/administrationDivide/v/search',
							method: 'POST',
							data: {}
						}).success(function(data) {
							angular.forEach(data.data, function(data1) {
								if(data1.grade == 1) {
									scope.bankcardAttributionProvinceList.push(data1);
								} else if(data1.grade == 2) {
									scope.bankcardAttributionCityList.push(data1);
								} else if(data1.grade == 3) {
									scope.bankcardAttributionCountyList.push(data1);
								}
							});
							fProvince();
							pProvince();
						});

						//初始化借款信息，合同信息，影像资料

						var borrowDto = new Object();
						var contractDto = new Object();

						$http({
							method: 'POST',
							url: '/credit/third/api/yntrust/v/selectBorrowContractImg',
							data: {
								'orderNo': scope.orderNo,
								'ynProductCode': scope.kgYunnanOrder.ynProductCode
							}
						}).success(function(data) {
							if(!data.data || !data.data.borrow) {
								if(scope.house && scope.house.orderBaseHousePropertyPeopleDto.length > 0) {
									scope.kgYunnanOrder.shortName = scope.house.orderBaseHousePropertyPeopleDto[0].propertyName;
									scope.kgYunnanOrder.iDCardNo = scope.house.orderBaseHousePropertyPeopleDto[0].propertyCardNumber;
								} else {
									scope.kgYunnanOrder.shortName = scope.borrow.borrowerName;
									scope.kgYunnanOrder.iDCardNo = scope.customer.customerCardNumber;
								}
								scope.kgYunnanOrder.cardType = "1";
								scope.kgYunnanOrder.telephoneNo = scope.borrow.phoneNumber;
								scope.kgYunnanOrder.roleType = "1";
								if(scope.borrow.roleType != null && scope.borrow.roleType != undefined && scope.borrow.roleType!=''){
									scope.kgYunnanOrder.roleType=scope.borrow.roleType;     //新加
								}
								scope.kgYunnanOrder.jobType=scope.borrow.jobType;       //新加
								if(scope.customer.customerMarriageState == "未婚") {
									scope.kgYunnanOrder.maritalStatus = "10";
								} else if(scope.customer.customerMarriageState == "已婚有子女" || scope.customer.customerMarriageState == "已婚无子女") {
									scope.kgYunnanOrder.maritalStatus = "20";
								} else if(scope.customer.customerMarriageState == "丧偶") {
									scope.kgYunnanOrder.maritalStatus = "30";
								} else if(scope.customer.customerMarriageState == "离异") {
									scope.kgYunnanOrder.maritalStatus = "40";
								}
							} else {
								borrowDto = data.data.borrow;
								scope.kgYunnanOrder = data.data.borrow;
								scope.kgYunnanOrder.cardType = String(scope.kgYunnanOrder.cardType);
								scope.kgYunnanOrder.maritalStatus = String(scope.kgYunnanOrder.maritalStatus);
							}
							if(!data.data || !data.data.contract) {
								if(scope.customer.customerPosition == "银行分行行级干部、厅局级干部、大校及以上" || scope.customer.customerPosition == "银行支行行长、处级干部、中校、上校") {
									scope.kgYunnanOrder.jobType = "3";
								} else if(scope.customer.customerPosition == "高级管理人员、总公司总经理" || scope.customer.customerPosition == "一般管理人员、部门经理、私营企业主、村民委员会管理人员、中尉、少尉" || scope.customer.customerPosition == "中级管理人员、公司或分公司总经理") {
									scope.kgYunnanOrder.jobType = "4";
								} else if(scope.customer.customerPosition == "科级干部、上尉、少校" || scope.customer.customerPosition == "一般员工、工人、科员；国家机关、事业单位和国有企业离退休人员、士兵、深圳原住地一般村民") {
									scope.kgYunnanOrder.jobType = "1";
								} else if(scope.customer.customerPosition == "待业人员、学生和职位不确定的其他人员") {
									scope.kgYunnanOrder.jobType = "8";
								}

								var scopeTemp = angular.element('.detail-right-tit').scope();
								angular.forEach(scopeTemp.dataList, function(data, index, array) {
									if(("114" == data.fundCode || '115' == data.fundCode)) {
										scope.kgYunnanContract.amount = data.loanAmount * 10000;
									}
								});

								var overdueDayRate = 0;
								if(!scope.cdBorrow) {
									scope.kgYunnanContract.borrowingDays = scope.borrow.borrowingDays;
									overdueDayRate = scope.borrow.overdueRate * 100;
								} else {
									scope.kgYunnanContract.borrowingDays = scope.cdBorrow.borrowingDays;
									overdueDayRate = scope.cdBorrow.overdueRate * 100;
								}
								if(overdueDayRate > 0.001) {
									overdueDayRate = 0.001;
								}
								scope.kgYunnanContract.overdueDayRate = overdueDayRate;
								if(scope.house && scope.house.orderBaseHousePropertyDto.length > 0) {
									scope.kgYunnanContract.address = scope.house.orderBaseHousePropertyDto[0].cityName + scope.house.orderBaseHousePropertyDto[0].houseRegion;
								}
								if(scope.house && scope.house.orderBaseHousePropertyPeopleDto.length > 0) {
									scope.kgYunnanContract.shortName = scope.house.orderBaseHousePropertyPeopleDto[0].propertyName;
								} else {
									scope.kgYunnanContract.shortName = scope.borrow.borrowerName;
								}
								scope.kgYunnanOrder.cityName = scope.borrow.cityName;
								scope.kgYunnanOrder.city = scope.borrow.cityCode;
								scope.kgYunnanContract.signDayRate = "0.0005";
								scope.kgYunnanContract.signDate = $filter('date')(new Date(), 'yyyy-MM-dd');
								scope.kgYunnanContract.beginDate = $filter('date')(new Date(), 'yyyy-MM-dd');
								scope.kgYunnanContract.repaymentCycle = "0";
								scope.kgYunnanContract.repaymentMode = "3";
								scope.kgYunnanContract.repaymentPeriod = "1";
								if(scope.foreclosureType) {
									scope.kgYunnanContract.accountname = scope.foreclosureType.bankCardMaster;
									scope.kgYunnanContract.accountno = scope.foreclosureType.bankNo;
									scope.kgYunnanContract.branchbankname = scope.foreclosureType.bankSubName;
								}
								scope.kgYunnanContract.type = "0";
								if(scope.paymentType) {
									scope.kgYunnanContract.paymentsBankAccountName = scope.paymentType.paymentBankCardName;
									scope.kgYunnanContract.paymentsSubBankName = scope.paymentType.paymentBankSubName;
								}
							} else {
								contractDto = data.data.contract;
								scope.kgYunnanContract = data.data.contract;
								scope.kgYunnanContract.repaymentPeriod = String(scope.kgYunnanContract.repaymentPeriod);
								scope.kgYunnanContract.signDate = $filter('date')(scope.kgYunnanContract.signDate, 'yyyy-MM-dd');
								scope.kgYunnanContract.beginDate = $filter('date')(scope.kgYunnanContract.beginDate, 'yyyy-MM-dd');
							}
							if(scope.foreclosureType) {
								scope.kgForeclosureTypeBank = scope.foreclosureType.bankName + "-" + scope.foreclosureType.bankSubName;
							}
							if(scope.paymentType) {
								scope.kgPaymentTypeBank = scope.paymentType.paymentBankName + "-" + scope.paymentType.paymentBankSubName;
							}
							scope.yunnanRefresh();
						})
						yunNanImgInit();
					}

					function yunNanAuditInit() {
						scope.yunnanAudit = new Object();
						$http({
							method: 'POST',
							url: "/credit/third/api/yntrust/v/selectRepaymentPlan",
							data: {
								"orderNo": scope.orderNo
							}
						}).success(function(data) {
							if(data.data) {
								scope.yunnanAudit = data.data;
								scope.yunnanAudit.repayDate = $filter('date')(scope.yunnanAudit.repayDate, 'yyyy-MM-dd');
							} else {
								var tempDate = new Date();
								if(scope.ynStatus.processStatus.actExcutedTime) {
									tempDate = new Date(scope.ynStatus.processStatus.actExcutedTime);
								}
								scope.yunnanAudit.repayDate = $filter('date')(tempDate.setDate(tempDate.getDate() + scope.kgYunnanContract.borrowingDays), 'yyyy-MM-dd');
								scope.yunnanAudit.borrowingDays = scope.kgYunnanContract.borrowingDays;
								scope.yunnanAudit.repayPrincipal = scope.kgYunnanContract.amount;
								scope.yunnanAudit.repayProfit = accMul(accMul(scope.yunnanAudit.borrowingDays, scope.yunnanAudit.repayPrincipal), scope.kgYunnanContract.signDayRate);
							}
							scope.audit_delay = angular.copy(scope.yunnanAudit);
							scope.audit_delay.repayDate = $filter('date')(new Date(scope.audit_delay.repayDate).getTime(), 'yyyy-MM-dd');
							scope.audit_delay.changeReason = "2";

							//初始化回款计划
							if(scope.ynStatus.repaymentStatus) {
								yunnanRepaymentInit();
							}
						})
					}

					function yunnanRepaymentInit() {
						$http({
							method: 'POST',
							url: '/credit/third/api/yntrust/v/queryRepayOrder',
							data: {
								'orderNo': scope.orderNo,
								'ynProductCode': scope.kgYunnanOrder.ynProductCode
							}
						}).success(function(res) {
							if(res.code == "SUCCESS" && res.data) {
								scope.YNHKInfo = res.data;
								scope.postLoanUrl = res.data.postLoanUrl;
							} else {
								scope.YNHKInfo.repayDate = $filter('date')(new Date(scope.audit_delay.repayDate).getTime(), 'yyyy-MM-dd');
								scope.YNHKInfo.realityPayDate = $filter('date')(scope.audit_delay.repayDate, 'yyyy-MM-dd');
								scope.YNHKInfo.borrowingDays = scope.audit_delay.borrowingDays;
								scope.YNHKInfo.givePayPrincipal = scope.audit_delay.repayPrincipal;
								scope.YNHKInfo.repayProfit = scope.audit_delay.repayProfit;
								scope.YNHKInfo.repayPrincipal = scope.audit_delay.repayPrincipal;
							}
							scope.YNHKInfo.latePenalty = 0
							scope.YNHKInfo.lateFee = 0
							scope.YNHKInfo.lateOtherCost = 0

							calcAll();

							scope.$watch('YNHKInfo.realityPayDate', function(val) {
								calcAll()
							})

							scope.$watch('YNHKInfo.lateDay', function(val, old) {
								if(val) {
									scope.YNHKInfo.realityPayDate = $filter('date')(new Date(scope.YNHKInfo.repayDate).getTime() + val * 24 * 3600000, 'yyyy-MM-dd');
								} else if(val == 0 && old != 0) {
									scope.YNHKInfo.realityPayDate = $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
								}
							})

							scope.$watch('YNHKInfo.latePenalty', function() {
								calcAll()
							})

							scope.$watch('YNHKInfo.lateFee', function() {
								calcAll()
							})

							scope.$watch('YNHKInfo.lateOtherCost', function() {
								calcAll()
							})

							//初始化支付信息
							if(scope.ynStatus.payStatus) {
								yunnanPayInit();
							}

						})
					}

					function yunnanPayInit() {
						scope.YNZFXX.accountName = scope.kgYunnanContract.paymentsBankAccountName;
						scope.YNZFXX.accountNo = scope.kgYunnanContract.paymentsBankAccount;
						scope.YNZFXX.bankTransactionNo = scope.random = Math.ceil(Math.random() * 10000000000000000);
					}

					function calcAll() {
						//逾期时间
						var overDue = calOvderDue(scope.YNHKInfo.repayDate, scope.YNHKInfo.realityPayDate, scope.YNHKInfo.borrowingDays);
						scope.YNHKInfo.lateDay = overDue > 0 ? overDue : 0;
						scope.YNHKInfo.givePayProfit = parseInt(overDue > 0 ? givePayProfit(0, scope.YNHKInfo.borrowingDays, scope.YNHKInfo.givePayPrincipal) : givePayProfit(overDue, scope.YNHKInfo.borrowingDays, scope.YNHKInfo.givePayPrincipal));
						scope.YNHKInfo.penaltyFee = overDue > 0 ? givePayProfit(overDue, scope.YNHKInfo.borrowingDays, scope.YNHKInfo.givePayPrincipal) : 0;
						scope.YNHKInfo.overDueFee = scope.YNHKInfo.penaltyFee + scope.YNHKInfo.latePenalty + scope.YNHKInfo.lateFee + scope.YNHKInfo.lateOtherCost;
						scope.YNZFXX.amount = scope.YNHKInfo.givePayPrincipal + scope.YNHKInfo.givePayProfit + scope.YNHKInfo.overDueFee;
					}
					//逾期天数
					function calOvderDue(plan, real, borrowingDays) {
						return(new Date(real).getTime() - new Date(plan).getTime()) / 24 / 3600000
					}

					//计算云南利息
					function givePayProfit(overDue, borrowingDays, principal) {
						if(overDue > 0) {
							return overDue * principal * 0.001
						} else {
							return(borrowingDays + overDue) * principal * 0.0005
						}
					}

					function accMul(arg1, arg2) {
						var m = 0,
							s1 = arg1.toString(),
							s2 = arg2.toString();
						try {
							m += s1.split(".")[1].length
						} catch(e) {}
						try {
							m += s2.split(".")[1].length
						} catch(e) {}
						return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
					}

					scope.checkPic = function() {
						$.openPhotoGallery($(".processuView-picx").next("img"), "delZijinPic");
					}

					scope.yunNanInit();

				}
			}
		});

	};
});