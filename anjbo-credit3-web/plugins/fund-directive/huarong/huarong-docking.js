define(function(require, exports, module) {
	exports.extend = function(app) {

		app.directive('huarongDockingEdit', function($compile, $timeout, $http, $state, $filter, process, route, box) {
			return {
				restrict: "E",
				templateUrl: '/plugins/fund-directive/huarong/huarongDocking.html',
				transclude: true,
				link: function(scope) {

					scope.isImgEdit = true;

					//申请信息
					scope.huaRongDto = new Object();

					scope.huarongClose = function() {
						$timeout(function() {
							$(angular.element.find("huarong-docking-edit")).remove();
						});
					}

					scope.huarongRefresh = function(fl) {
						$http({
							method: 'POST',
							url: '/credit/third/api/hr/v/allStatus',
							data: {
								'orderNo': scope.orderNo
							}
						}).success(function(res) {
							if(fl) {
								if(res.code == 'SUCCESS') {
									box.boxAlert('刷新成功')
								} else {
									box.boxAlert('未进行资料推送')
								}
							}
							if(res.msg != '待推送') {
								huarongrepaymentPlanInit();
								if(res.data.dealStatus == '997') {
									scope.dealStatus = "审批通过";
								} else if(res.data.dealStatus == '998') {
									scope.dealStatus = "审批失败";
								} else if(res.data.dealStatus == '992') {
									scope.dealStatus = "已打回";
								} else {
									scope.dealStatus = "待审批";
								}
								if(res.data.grantStatus == '997') {
									scope.grantStatus = "勾兑成功";
									scope.realLoanTime = res.data.realLoanTime;
									huarongrepaymentInfoInit();
								} else if(res.data.grantStatus == '998') {
									scope.grantStatus = "人工否决";
								} else {
									scope.grantStatus = "待勾兑";
								}
							}
						});
					}

					scope.huarongRefresh();

					scope.delHrImgCancel = function() {
						scope.isDelHrImgShow = false;
					}

					scope.huarongSave = function(type) {
						scope.isAudit = false;
						sle = scope.huarongAppointForm.appointMobile.$$lastCommittedViewValue.length;
						sle2=scope.huarongKgIndivForm.kgIndivMobile.$$rawModelValue.length;
						if(scope.huarongAppointForm.$invalid) {
							box.boxAlert("申請订单数据不完整");
							scope.isAudit = true;
							scope.huarongTitle = 'huarongOrderShow';
							return;
						} else if(scope.huarongKgAppointForm.$invalid) {
							box.boxAlert("业务信息数据不完整");
							scope.isAudit = true;
							scope.huarongTitle = 'huarongKgAppointShow';
							return;
						} else if(scope.huarongKgIndivForm.$invalid) {
							box.boxAlert("借款人信息不完整");
							scope.isAudit = true;
							scope.huarongTitle = 'huarongKgIndivShow';
							return;
						} else if(scope.huarongKgHouseForm.$invalid) {
							box.boxAlert("房产信息不完整");
							scope.isAudit = true;
							scope.huarongTitle = 'huarongKgHouseShow';
							return;
						} else if(scope.huarongKgApprovalForm.$invalid) {
							box.boxAlert("审批信息不完整");
							scope.isAudit = true;
							scope.huarongTitle = 'huarongKgApprovalShow';
							return;
						} else if(scope.huarongKgLoanForm.$invalid) {
							box.boxAlert("放款数据不完整");
							scope.isAudit = true;
							scope.huarongTitle = 'huarongLoanShow';
							return;
						} else if(sle>11 || sle<11) {
							box.boxAlert("申请表电话格式不对");
							return;
						} 
						else if(sle2>11 || sle2<11){
							box.boxAlert("借款表电话格式不对");
							return;
						}
						box.waitAlert();
						if(scope.huaRongDto.lcAppoint.productId == 'KG001') {
							scope.huaRongDto.lcAppoint.loanCooprCode = "KG";
						} else if(scope.huaRongDto.lcAppoint.productId == 'KG002') {
							scope.huaRongDto.lcAppoint.loanCooprCode = "KG2";
						}
						scope.huaRongDto.orderNo = scope.orderNo;
						scope.huaRongDto.types=5;
						$http({
							url: '/credit/third/api/hr/v/apply',
							method: 'POST',
							data: scope.huaRongDto
						}).success(function(data) {
							box.closeWaitAlert();
							if("SUCCESS" == data.code) {
								box.boxAlert('操作成功')
								scope.huarongRefresh();
//								huarongInit();
								scope.huarongImgs();
							}else if("ERROR" == data.code){
								box.boxAlert(data.msg)
							} 
							else {
								box.closeWaitAlert();
								alert("申請信息" + data.msg);
							}
						});
					}
					scope.saveA = function(type) {		
						sl = scope.huarongAppointForm.appointMobile.$$lastCommittedViewValue.length;
						sl2=scope.huarongKgIndivForm.kgIndivMobile.$$rawModelValue.length;
						if(sl>11  || sl<11) {
							box.boxAlert("申请表电话格式不对");
							return;
						} 
						if(sl2>11 || sl2<11){
							box.boxAlert("借款表电话格式不对");
							return;
						}
						box.waitAlert();
						if(scope.huaRongDto.lcAppoint.productId == 'KG001') {
							scope.huaRongDto.lcAppoint.loanCooprCode = "KG";
						} else if(scope.huaRongDto.lcAppoint.productId == 'KG002') {
							scope.huaRongDto.lcAppoint.loanCooprCode = "KG2";
						}
						scope.huaRongDto.orderNo = scope.orderNo;
						scope.huaRongDto.types=1;
						$http({
							url: '/credit/third/api/hr/v/apply',
							method: 'POST',
							data: scope.huaRongDto
						}).success(function(data) {
							box.closeWaitAlert();
							if("SUCCESS" == data.code) {
								box.boxAlert('操作成功')
								//scope.huarongRefresh();
								//huarongInit();
							}
							else {
								box.closeWaitAlert();
								alert("申請信息" + data.msg);
							}
						});
					}

					//推送影像资料
					scope.huarongImgs = function(fl) {
						if(fl) {
							box.waitAlert();
						}
						var datamsg = "";
						var imgs = scope.getImgs();
						$http({
							url: '/credit/third/api/hr/v/fileApply',
							method: 'POST',
							data: {
								"orderNo": scope.orderNo,
								"imgs": imgs
							}
						}).success(function(data) {
							if("SUCCESS" != data.code || fl) {
								box.closeWaitAlert();
								alert("影像資料正在后台推送");
							}
						});
					}

					//获取所有影像资料拼接成集合
					scope.getImgs = function() {
						var imgArray = new Array();
						angular.forEach(scope.huarongImageDatas, function(parent, index, a) {
							angular.forEach(parent, function(sType, sTypeIndex, b) {
								angular.forEach(sType.childrenType, function(data, imgIndex, c) {
									angular.forEach(data.listImgs, function(img, i, d) {
										var imgparam = {
											typeId: img.typeId,
											url: img.url,
											orderNo: scope.orderNo,
											index: img.index
										}
										imgArray.push(imgparam);
									});
								});
							});
						});
						return imgArray;
					}

					//推送应还款计划
					scope.huarongRepayment = function() {
						box.waitAlert()
						scope.huaRongDto.repaymentPlan.orderNo = scope.orderNo;
						scope.huaRongDto.repaymentPlan.order = "1";
						scope.huaRongDto.repaymentPlan.sysbAmt = 0;
						scope.huaRongDto.repaymentPlan.psFeeAmt = 0;
						scope.huaRongDto.repaymentPlan.repaymentAccount = scope.huaRongDto.repaymentPlan.interest * 1 + scope.huaRongDto.repaymentPlan.capital * 1;
						$http({
							method: 'POST',
							url: "/credit/third/api/hr/v/repayment",
							data: scope.huaRongDto.repaymentPlan
						}).success(function(data) {
							box.closeWaitAlert();
							box.boxAlert(data.msg)
							if(data.code == "SUCCESS") {
								scope.huarongRefresh();
							}
						})
					}

					//华融回款信息推送
					scope.huarongRepaymentInfo = function() {
						box.waitAlert();
						scope.huaRongDto.repaymentInfo.orderNo = scope.orderNo
						scope.huaRongDto.repaymentInfo.sysbAmt = '0'
						scope.huaRongDto.repaymentInfo.setlSysbAmt = '0'
						scope.huaRongDto.repaymentInfo.setlFeeAmt = '0'
						scope.huaRongDto.repaymentInfo.psRemPrcp = '0'
						scope.huaRongDto.repaymentInfo.order = '1'
						$http({
							method: 'POST',
							url: "/credit/third/api/hr/v/rayMentPlan",
							data: scope.huaRongDto.repaymentInfo
						}).success(function(data) {
							box.closeWaitAlert();
							box.boxAlert(data.msg, function() {
								if(data.code == "SUCCESS") {
									scope.huarongRefresh();
								}
							});
						})
					}

					//初始化华融信息
					function huarongInit() {
						$http({
							url: '/credit/third/api/hr/v/queryApply',
							method: 'POST',
							data: {
								'orderNo': scope.orderNo
							}
						}).success(function(res) {
							if(res.code == "SUCCESS") {
								scope.huaRongDto = res.data;
								if(scope.huaRongDto.kgAppoint) {
									if(scope.huaRongDto.kgAppoint.yOriBank) {
										var yOriBanks = scope.huaRongDto.kgAppoint.yOriBank.split("-");
										if(yOriBanks.length == 2) {
											scope.huaRongDto.kgAppoint.oldLoanBankName = yOriBanks[0];
											scope.huaRongDto.kgAppoint.oldLoanBankSubName = yOriBanks[1];
										}
									}
									if(scope.huaRongDto.kgAppoint.xLoanBank) {
										var xLoanBanks = scope.huaRongDto.kgAppoint.xLoanBank.split("-");
										if(xLoanBanks.length == 2) {
											scope.huaRongDto.kgAppoint.loanBankName = xLoanBanks[0];
											scope.huaRongDto.kgAppoint.loanSubBankName = xLoanBanks[1];
										}
									}
									if(scope.huaRongDto.kgAppoint.fOpenBank) {
										var fOpenBanks = scope.huaRongDto.kgAppoint.fOpenBank.split("-");
										if(fOpenBanks.length == 2) {
											scope.huaRongDto.kgAppoint.bankName = fOpenBanks[0];
											scope.huaRongDto.kgAppoint.bankSubName = fOpenBanks[1];
										}
									}
									if(scope.huaRongDto.kgAppoint.hOpenBank) {
										var hOpenBanks = scope.huaRongDto.kgAppoint.hOpenBank.split("-");
										if(hOpenBanks.length == 2) {
											scope.huaRongDto.kgAppoint.paymentBankName = hOpenBanks[0];
											scope.huaRongDto.kgAppoint.paymentBankSubName = hOpenBanks[1];
										}
									}
								}
							}

							//申请预约信息
							var mark;
							scope.dataList.forEach(function(val, index) {
								if(val.fundCode == 110) {
									mark = val
								}
							})

							if(!scope.huaRongDto.lcAppoint) {
								scope.huaRongDto.lcAppoint = new Object();
								scope.huaRongDto.lcAppoint.loanCooprCode = "KG"; //助贷商编码
								scope.huaRongDto.lcAppoint.mtdCde = "按日计息,利随本清"; //还款方式
								scope.huaRongDto.lcAppoint.useHr = "短期周转"; //借款用途字典项
								scope.huaRongDto.lcAppoint.applyTnrUnit = "日"; //借款期限单位
								scope.huaRongDto.lcAppoint.productId = "KG001"; //产品编号
								scope.huaRongDto.lcAppoint.custName = scope.customer.customerName;
								scope.huaRongDto.lcAppoint.idType = "身份证";
								scope.huaRongDto.lcAppoint.idNo = scope.customer.customerCardNumber;
								scope.huaRongDto.lcAppoint.mobile = scope.borrow.phoneNumber;
								if(mark) {
									scope.huaRongDto.lcAppoint.applyAmt = mark.loanAmount;
									scope.huaRongDto.lcAppoint.apprvAmt = mark.loanAmount;
								}
								scope.huaRongDto.lcAppoint.apr = 0.0306; //scope.borrow.rate;
								scope.huaRongDto.lcAppoint.applyTnr = scope.borrow.borrowingDays;
							}

							if(!scope.huaRongDto.kgLoan) {
								scope.huaRongDto.kgLoan = new Object();
								scope.huaRongDto.kgLoan.bchCde = "KG"; //机构代码
								scope.huaRongDto.kgLoan.bchName = "快鸽按揭"; //机构名称
								scope.huaRongDto.kgLoan.applyTnrUnit = "日"; //申请期限单位
								scope.huaRongDto.kgLoan.mtdCde = "按日计息,利随本清"; //还款方式
								scope.huaRongDto.kgLoan.ifCeOut = "Y"; //是否差额打款
								scope.huaRongDto.kgLoan.custName = scope.customer.customerName;
								scope.huaRongDto.kgLoan.idNo = scope.customer.customerCardNumber;
								if(mark) {
									scope.huaRongDto.kgLoan.applyAmt = mark.loanAmount;
									scope.huaRongDto.kgLoan.apprvAmt = mark.loanAmount;
								}
								scope.huaRongDto.kgLoan.apr = 0.0306;
								scope.huaRongDto.kgLoan.odIntRate = 0.0306;
								scope.huaRongDto.kgLoan.applyTnr = scope.borrow.borrowingDays;
								scope.huaRongDto.kgLoan.sysbPct = 0;
								scope.huaRongDto.kgLoan.sysbAmt = 0;
								scope.huaRongDto.kgLoan.hrdPct = 0;
								scope.huaRongDto.kgLoan.hrdAmt = 0;
								scope.huaRongDto.kgLoan.idType = "身份证";
								loadNotarization();
							}

							//申请人信息
							if(!scope.huaRongDto.lcApplAppt) {
								scope.huaRongDto.lcApplAppt = new Object();
								scope.huaRongDto.lcApplAppt.idNo = scope.customer.customerCardNumber;
								scope.huaRongDto.lcApplAppt.idType = "身份证";
								scope.huaRongDto.lcApplAppt.custName = scope.customer.customerName;
							}

							//申请人详细信息
							if(!scope.huaRongDto.lcApptIndiv) {
								scope.huaRongDto.lcApptIndiv = new Object();
								scope.huaRongDto.lcApptIndiv.custName = scope.customer.customerName;
								scope.huaRongDto.lcApptIndiv.indivEdt = "本科";
							}

							//快鸽提单-业务信息
							if(!scope.huaRongDto.kgAppoint || !res.data) {
								scope.huaRongDto.kgAppoint = new Object();
								scope.huaRongDto.kgAppoint.businessType = scope.borrow.productName;
								scope.huaRongDto.kgAppoint.custName = scope.customer.customerName;
								if(mark) {
									scope.huaRongDto.kgAppoint.loanAmount = mark.loanAmount;
								}
								scope.huaRongDto.kgAppoint.term = scope.borrow.borrowingDays;
								scope.huaRongDto.kgAppoint.rate = 0.0306;
								scope.huaRongDto.kgAppoint.overdueTate = 0.0306;
								scope.huaRongDto.kgAppoint.yOriLenAmount = scope.house.oldHouseLoanAmount;
								scope.huaRongDto.kgAppoint.yLoanBalance = scope.house.oldHouseLoanBalance;
								scope.huaRongDto.kgAppoint.yIsBank = scope.house.isOldLoanBank == 1 ? "是" : "否";
								if(scope.house.isOldLoanBank == 2) {
									scope.huaRongDto.kgAppoint.yOriBank = scope.house.oldLoanBankName;
								} else {
									scope.huaRongDto.kgAppoint.oldLoanBankNameId = String(scope.borrow.oldLoanBankNameId);
									scope.huaRongDto.kgAppoint.oldLoanBankSubNameId = String(scope.borrow.oldLoanBankSubNameId);
								}
								scope.huaRongDto.kgAppoint.xLoanAmount = scope.house.houseLoanAmount;
								scope.huaRongDto.kgAppoint.xIsBank = scope.house.isLoanBank == 1 ? "是" : "否";
								if(scope.house.isLoanBank == 2) {
									scope.huaRongDto.kgAppoint.xLoanBank = scope.house.loanBankName;
								} else {
									scope.huaRongDto.kgAppoint.loanBankNameId = String(scope.borrow.loanBankNameId);
									scope.huaRongDto.kgAppoint.loanSubBankNameId = String(scope.borrow.loanSubBankNameId);
								}
								if(scope.foreclosureType) {
									scope.huaRongDto.kgAppoint.fAccountType = scope.foreclosureType.accountType;
									scope.huaRongDto.kgAppoint.bankNameId = null == scope.foreclosureType.bankNameId ? "" : String(scope.foreclosureType.bankNameId);
									scope.huaRongDto.kgAppoint.bankSubNameId = null == scope.foreclosureType.bankSubNameId ? "" : String(scope.foreclosureType.bankSubNameId);
									scope.huaRongDto.kgAppoint.fOpenName = scope.foreclosureType.bankCardMaster;
									scope.huaRongDto.kgAppoint.fAccountNum = scope.foreclosureType.bankNo;
									if("产权人" == scope.huaRongDto.kgAppoint.fAccountType || "债务人" == scope.huaRongDto.kgAppoint.fAccountType) {
										scope.huaRongDto.kgAppoint.fCaacNo = scope.foreclosureType.idCard;
									}
								}

								if(scope.paymentType) {
									scope.huaRongDto.kgAppoint.hAccountType = scope.paymentType.paymentaccountType;
									scope.huaRongDto.kgAppoint.paymentBankNameId = null == scope.paymentType.paymentBankNameId ? "" : String(scope.paymentType.paymentBankNameId);
									scope.huaRongDto.kgAppoint.paymentBankSubNameId = null == scope.paymentType.paymentBankSubNameId ? "" : String(scope.paymentType.paymentBankSubNameId);
									scope.huaRongDto.kgAppoint.hOpenName = scope.paymentType.paymentBankCardName;
									scope.huaRongDto.kgAppoint.hAccountNum = scope.paymentType.paymentBankNumber;
									if("产权人" == scope.huaRongDto.kgAppoint.hAccountType || "债务人" == scope.huaRongDto.kgAppoint.hAccountType) {
										scope.huaRongDto.kgAppoint.hCaacNo = scope.paymentType.paymentIdCardNo;
									}
								}
							}

							if(!scope.huaRongDto.kgIndiv) {
								scope.huaRongDto.kgIndiv = new Object();
								scope.huaRongDto.kgIndiv.custName = scope.customer.customerName;
								scope.huaRongDto.kgIndiv.docType = scope.customer.customerCardType;
								scope.huaRongDto.kgIndiv.docNo = scope.customer.customerCardNumber;
								scope.huaRongDto.kgIndiv.mobile = scope.borrow.phoneNumber;
								scope.huaRongDto.kgIndiv.marStatus = scope.customer.customerMarriageState;
								scope.huaRongDto.kgIndiv.sCustName = scope.customer.customerWifeName;
								scope.huaRongDto.kgIndiv.sMarStatus = scope.customer.customerWifeMarriageState;
								scope.huaRongDto.kgIndiv.sMobile = scope.customer.customerWifePhone;
								scope.huaRongDto.kgIndiv.sDocNo = scope.customer.customerWifeCardNumber;
								scope.huaRongDto.kgIndiv.sDocType = scope.customer.customerWifeCardType;
								scope.huaRongDto.kgIndiv.cardLife = scope.credit.creditCardYears;
								scope.huaRongDto.kgIndiv.totalPremises = scope.credit.allHouseWorth;
								scope.huaRongDto.kgIndiv.totalCredit = scope.credit.creditQuota;
								scope.huaRongDto.kgIndiv.debtRatio = scope.credit.liabilitiesProportion;
								scope.huaRongDto.kgIndiv.overdueCredit = scope.credit.creditOverdueNumber;
								scope.huaRongDto.kgIndiv.yIsBank = scope.credit.oldLoanIsBank == 1 ? "是" : "否";
								scope.huaRongDto.kgIndiv.loanYear = scope.credit.loanRecordYears;
								scope.huaRongDto.kgIndiv.houseNum = scope.credit.allHouseNumber;
								scope.huaRongDto.kgIndiv.quotaUsed = scope.credit.useQuota;
								scope.huaRongDto.kgIndiv.loanAmount = scope.credit.loanPercentage;
								scope.huaRongDto.kgIndiv.creditFindNum = scope.credit.latelyHalfYearSelectNumber;
								scope.huaRongDto.kgIndiv.xIsBank = scope.credit.newLoanIsBank == 1 ? "是" : "否";
								scope.huaRongDto.kgIndiv.defaultRate = scope.credit.violationProportion;
								scope.huaRongDto.kgIndiv.overdraft = scope.credit.creditCardOverdraft;
								scope.huaRongDto.kgIndiv.totalCreditLia = scope.credit.creditLiabilities;
								scope.huaRongDto.kgIndiv.foreclosureRate = scope.credit.foreclosurePercentage;
								scope.huaRongDto.kgIndiv.companyRight = scope.credit.isCompanyProperty;
								scope.huaRongDto.kgIndiv.rightMortgage = scope.credit.propertyMortgage;
								scope.huaRongDto.kgIndiv.remark = scope.borrow.remark;
							}

							if(!scope.huaRongDto.kgHouse) {
								scope.huaRongDto.kgHouse = new Object();
								//所有权人
								if(scope.house.orderBaseHousePropertyPeopleDto && scope.house.orderBaseHousePropertyPeopleDto.length > 0) {
									scope.huaRongDto.kgHouse.owner = scope.house.orderBaseHousePropertyPeopleDto[0].propertyName
								}
								if(scope.house.orderBaseHousePropertyDto && scope.house.orderBaseHousePropertyDto.length > 0) {
									var tmp = scope.house.orderBaseHousePropertyDto[0];
									scope.huaRongDto.kgHouse.houseAddress = scope.house.cityName + "-" + tmp.houseRegion;
									scope.huaRongDto.kgHouse.builtArea = tmp.houseArchitectureSize;
									scope.huaRongDto.kgHouse.houseNo = tmp.housePropertyNumber;
								}
								scope.huaRongDto.kgHouse.remark = scope.house.remark;
								loadEnquiry();
								loadArchive();
							}

							if(!scope.huaRongDto.kgApproval) {
								scope.huaRongDto.kgApproval = new Object();
								loadauditFirst();
								loadauditFinal();
								loadauditOfficer();
							}

							loadHuarongBusInfo();
						});
					}

					function loadHuarongBusInfo() {
						$http({
							url: 'credit/risk/allocationfund/v/loadHuarongBusInfo',
							method: 'POST',
							data: {
								'orderNo': scope.orderNo
							}
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.huarongImageDatas = filePathToUrl(data.data);
							} else {
								$http({
									url: '/credit/risk/fundDocking/v/loadHuarongBusInfo',
									method: 'POST',
									data: {
										'orderNo': scope.orderNo
									}
								}).success(function(data) {
									if("SUCCESS" == data.code) {
										scope.huarongImageDatas = filePathToUrl(data.data);
									}
								});
							}
						});
					}

					function loadEnquiry() {
						$http({
							url: '/credit/risk/enquiry/v/detail',
							method: 'POST',
							data: {
								'orderNo': scope.orderNo
							}
						}).success(function(data) {
							if("SUCCESS" == data.code && null != data.data) {
								var tmp = data.data[0];
								if(tmp) {
									scope.huaRongDto.kgHouse.totalAssessment = tmp.totalPrice;
									scope.huaRongDto.kgHouse.worthAssessment = tmp.netPrice;
									if(tmp.maxLoanPrice) {
										scope.huaRongDto.kgHouse.firstHouseLoan = parseFloat(tmp.maxLoanPrice) * 10000;
									}
								}
							}
						});
					}

					function loadauditFirst() {
						$http({
							url: '/credit/risk/first/v/appDetail',
							method: 'POST',
							data: {
								'orderNo': scope.orderNo
							}
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.huaRongDto.kgApproval.trial = data.data.remark;
							}
						});
					}

					function loadauditFinal() {
						$http({
							url: '/credit/order/auditFinal/v/processDetails',
							method: 'POST',
							data: {
								'orderNo': scope.orderNo
							}
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.huaRongDto.kgApproval.judgment = data.data.remark;
							}
						});
					}

					function loadauditOfficer() {
						$http({
							url: '/credit/order/auditOfficer/v/processDetails',
							method: 'POST',
							data: {
								'orderNo': scope.orderNo
							}
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.huaRongDto.kgApproval.chiefRiskOpinion = data.data.remark;
							}
						});
					}

					function loadNotarization() {
						$http({
							url: '/credit/order/appNotarization/v/processDetails',
							method: 'POST',
							data: {
								'orderNo': scope.orderNo
							}
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.huaRongDto.kgLoan.repaymentTime = data.data.estimatedTime;
							}
						});
					}

					function loadArchive() {
						$http({
							url: '/credit/risk/archive/v/detail',
							method: 'POST',
							data: {
								'orderNo': scope.orderNo
							}
						}).success(function(data) {
							if("SUCCESS" == data.code && null != data.data) {
								var tmp = data.data[0];
								if(tmp) {
									scope.kgHouse.consult = tmp.message;
								}
							}
						});
					}

					//将图片filePath改Url
					function filePathToUrl(imgList) {
						angular.forEach(imgList, function(parent, index, a) {
							angular.forEach(parent, function(sType, sTypeIndex, b) {
								angular.forEach(sType.childrenType, function(data, imgIndex, c) {
									angular.forEach(data.listImgs, function(img, i, d) {
										if(!img.orderNo || !img.filePath) {
											return;
										} else {
											img.url = img.filePath
										}
										if(img.url) {
											img.isPdf = img.url.indexOf('pdf') > 0;
										}
									});
								});
							});
						});
						return imgList;
					}

					scope.selectTypeCheck = function(type) {
						scope.selectTypeId = type;
					}

					scope.addHuarongImg = function(arr) {
						var imgIndex = 0;
						angular.forEach(scope.huarongImageDatas, function(parent, index, a) {
							angular.forEach(parent, function(sType, sTypeIndex, b) {
								angular.forEach(sType.childrenType, function(data, imgIndex, c) {
									if(!data.listImgs || null == data.listImgs) {
										data.listImgs = new Array();
									}
									if(data.id == scope.selectTypeId) {
										imgIndex = data.listImgs.length;
										angular.forEach(arr, function(arrData, arrInd, d) {
											var param = {
												url: arrData,
												typeId: scope.selectTypeId,
												orderNo: scope.orderNo,
												isPdf: arrData.indexOf('pdf') > 0,
												index: imgIndex++
											}
											data.listImgs.push(param);
										})
									}
								});
							});
						});
						scope.$apply();
					}

					//删除华融所有图片
					scope.delAllHuarongImg = function() {
						angular.forEach(scope.huarongImageDatas, function(parent, index, a) {
							angular.forEach(parent, function(sType, sTypeIndex, b) {
								angular.forEach(sType.childrenType, function(data, imgIndex, c) {
									data.listImgs = new Array();
								});
							});
						});
					}

					//删除华融
					scope.delHrImgShow = function(obj) {
						scope.allCheck = false;
						angular.forEach(obj.listImgs, function(data, index, array) {
							data.check = false;
							data.isCheck = false;
						});
						scope.imgObject = obj;
						scope.isDelHrImgShow = true;
					}
					scope.delHrImg = function() {
						var isCheck = false;
						var ckeckImg = new Array();
						var tempList = scope.imgObject.listImgs.concat();
						scope.imgObject.listImgs = new Array();
						angular.forEach(tempList, function(obj, index, array) {
							if(obj.check) {
								isCheck = true;
							} else {
								scope.imgObject.listImgs.push(obj);
							}
						});
						if(!isCheck) {
							box.boxAlert("请选择要删除的图片");
							return;
						} else {
							scope.isDelHrImgShow = false;
						}
					}
					scope.selectHrAllImg = function() {
						if(!scope.allCheck) {
							scope.allCheck = true;
						} else {
							scope.allCheck = false;
						}
						angular.forEach(scope.imgObject.listImgs, function(obj, index, array) {
							obj.check = scope.allCheck;
						});
					}

					//初始化应还款计划
					function huarongrepaymentPlanInit() {
						$http({
							method: 'POST',
							url: "/credit/third/api/hr/v/queryRepayment",
							data: {
								orderNo: scope.orderNo,
							}
						}).success(function(res) {
							scope.huaRongDto.repaymentPlan = new Object();
							if(res.code == "SUCCESS" && res.data) {
								scope.huaRongDto.repaymentPlan = res.data;
								scope.huaRongDto.repaymentPlan.orderHr = scope.huaRongDto.lcAppoint.applyTnr;
								scope.HTStaus = "待推送回款计划";
								huarongrepaymentInfoInit();
							} else {
								scope.huaRongDto.repaymentPlan.psIntRate = 0.0306;
								scope.huaRongDto.repaymentPlan.orderHr = scope.huaRongDto.kgAppoint.term;
								scope.huaRongDto.repaymentPlan.capital = scope.huaRongDto.lcAppoint.applyAmt * 10000;
								scope.huaRongDto.repaymentPlan.interest = (scope.huaRongDto.repaymentPlan.capital * (0.11 / 360) * scope.huaRongDto.repaymentPlan.orderHr).toFixed(2);
								scope.huaRongDto.repaymentPlan.repaymentTime = scope.realLoanTime ? $filter('date')(new Date(new Date(scope.realLoanTime).getTime() + 3600000 * 24 * scope.huaRongDto.repaymentPlan.orderHr), 'yyyy-MM-dd') : "";
								scope.huaRongDto.repaymentPlan.repaymentAccount = (scope.huaRongDto.repaymentPlan.capital / 1 + scope.huaRongDto.repaymentPlan.interest / 1).toFixed(2);
								scope.huaRongDto.repaymentPlan.psRemPrcp = scope.huaRongDto.repaymentPlan.capital;
							}

						})
					}

					//初始化回款信息
					function huarongrepaymentInfoInit() {
						$http({
							method: 'POST',
							url: "/credit/third/api/hr/v/queryRayMentPlan",
							data: {
								orderNo: scope.orderNo
							}
						}).success(function(res) {
							scope.huaRongDto.repaymentInfo = new Object();
							if(res.code == 'SUCCESS' && res.data) {
								scope.HTStaus = "推送完成";
								scope.huaRongDto.repaymentInfo = res.data;
								scope.huaRongDto.repaymentInfo.repaymentTime = $filter('date')(scope.huaRongDto.repaymentInfo.repaymentTime, 'yyyy-MM-dd');
								scope.huaRongDto.repaymentInfo.repaymentYestime = $filter('date')(scope.huaRongDto.repaymentInfo.repaymentYestime, 'yyyy-MM-dd');
							} else {
								scope.huaRongDto.repaymentInfo.repaymentTime = scope.huaRongDto.repaymentPlan.repaymentTime;
								scope.huaRongDto.repaymentInfo.capital = scope.huaRongDto.repaymentInfo.setlCapital = scope.huaRongDto.repaymentPlan.capital;
								scope.huaRongDto.repaymentInfo.repaymentYestime = $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
								scope.huaRongDto.repaymentInfo.setlInterest = scope.huaRongDto.repaymentInfo.interest = (scope.huaRongDto.repaymentPlan.capital * 0.11 / 360 * scope.huaRongDto.repaymentPlan.orderHr).toFixed(2);
								scope.huaRongDto.repaymentInfo.repaymentAccount = (scope.huaRongDto.repaymentInfo.interest / 1 + scope.huaRongDto.repaymentInfo.capital / 1).toFixed(2);
								scope.huaRongDto.repaymentInfo.lateDays = calOvderDue(scope.huaRongDto.repaymentInfo.repaymentTime, scope.huaRongDto.repaymentInfo.repaymentYestime, scope.huaRongDto.repaymentPlan.orderHr) > 0 ? calOvderDue(scope.huaRongDto.repaymentInfo.repaymentTime, scope.huaRongDto.repaymentYestime, scope.huaRongDto.repaymentPlan.orderHr) : 0;
								scope.huaRongDto.repaymentInfo.setlLateInterest = scope.huaRongDto.repaymentInfo.lateInterest = (scope.huaRongDto.repaymentInfo.lateDays * scope.huaRongDto.repaymentInfo.capital * 0.11 / 360).toFixed(2);
								scope.huaRongDto.repaymentInfo.repaymentYesaccount = (scope.huaRongDto.repaymentInfo.capital / 1 + scope.huaRongDto.repaymentInfo.interest / 1 + scope.huaRongDto.repaymentInfo.lateInterest / 1).toFixed(2);
								scope.huaRongDto.repaymentInfo.psIntRate = "0.0306";

								calcAllHr();
								scope.$watch('huaRongDto.repaymentInfo.repaymentTime', function(val, old) {
									calcAllHr();
								})
								scope.$watch('huaRongDto.repaymentInfo.repaymentYestime', function(val, old) {
									calcAllHr();
								})

							}

						})
					}

					function calcAllHr() {
						$timeout(function() {
							scope.huaRongDto.repaymentInfo.lateDays = calOvderDue(scope.huaRongDto.repaymentInfo.repaymentTime, scope.huaRongDto.repaymentInfo.repaymentYestime) > 0 ? calOvderDue(scope.huaRongDto.repaymentInfo.repaymentTime, scope.huaRongDto.repaymentInfo.repaymentYestime) : 0
							scope.huaRongDto.repaymentInfo.setlLateInterest = scope.huaRongDto.repaymentInfo.lateInterest = (scope.huaRongDto.repaymentInfo.lateDays * scope.huaRongDto.repaymentInfo.capital * 0.11 / 360).toFixed(2)
							scope.huaRongDto.repaymentInfo.repaymentYesaccount = (scope.huaRongDto.repaymentInfo.capital / 1 + scope.huaRongDto.repaymentInfo.interest / 1 + scope.huaRongDto.repaymentInfo.lateInterest / 1).toFixed(2)
						});
					}

					//逾期天数
					function calOvderDue(plan, real) {
						return(Date.parse(real) - Date.parse(plan)) / 3600 / 1000 / 24;
						//						return(new Date(real).getTime() - new Date(plan).getTime()) / 24 / 3600000
					}

					huarongInit();

				}
			}
		});

	};
});