define(function(require, exports, module) {
	exports.extend = function(app) {

		app.directive('enquiryEdit', function($compile, $timeout, $http, $state, process, route, box) {
			return {
				restrict: "E",
				templateUrl: '/plugins/order-directive/enquiry/enquiryEdit.html',
				transclude: true,
				link: function(scope) {
					var params = route.getParams();
					scope.cityCode = params.cityCode;
					scope.productCode = params.productCode;
					scope.orderNo = params.orderNo;
					scope.QGAuthCode = "";

					//查档诉讼信息
					$http({
						method: 'POST',
						url: '/credit/risk/lawsuit/v/loadInquiryAndArchiveInit',
						data: params
					}).success(function(data) {
						if(data.data) {
							scope.enquiryList = data.data.enquiryData;
							angular.forEach(scope.enquiryList,function(data1){
								data1.showQuery = !!data1.enquiryId;
							});
						}
						if(!scope.enquiryList || scope.enquiryList.length == 0) {
							scope.enquiryList = new Array();
							addNewEnquiry();
						}

//						scope.archiveList = data.data.archiveData;
//						if(!scope.archiveList || scope.archiveList.length == 0) {
							scope.archiveList = new Array();
							addNewArchive();
//						}

						scope.lawsuitList = data.data.lawsuitData;
						if(data.data.lawsuitData) {
							if(data.data.lawsuitData.id) {
								getCountryListData(data.data.lawsuitData.id);
								getSzListData(data.data.lawsuitData.id);
							}
						}
					});

					function addNewEnquiry() {
						var enquiry = new Object();
						enquiry.consumerloans = "0"
						enquiry.obligee = "单位";
						enquiry.range = "0";
						scope.enquiryList.push(enquiry);
					}

					scope.addEnquiry = function() {
						addNewEnquiry();
					}

					scope.delEnquiry = function(index) {
						box.confirmAlert("删除询价", "确定删除该询价吗？", function() {
							if(scope.enquiryList[index].enquiryId) {
								$http({
									method: 'POST',
									data: {
										"orderNo": scope.orderNo,
										"enquiryId": scope.enquiryList[index].enquiryId
									},
									url: "/credit/risk/enquiry/v/delete"
								}).success(function(data) {
									if(data.code == 'SUCCESS') {
										scope.enquiryList.splice(index, 1);
									}
								});
							} else {
								$timeout(function() {
									scope.enquiryList.splice(index, 1);
								});
							}
						});
					}
					/**
					 * 楼栋
					 */
					scope.queryBuilding = function(enquiry) {
						var params = {
							"propertyId": enquiry.propertyId
						};
						enquiry.buildingList = null;
						$http({
							method: 'POST',
							data: params,
							url: "/credit/risk/enquiry/v/queryBuilding"
						}).success(function(data) {
							enquiry.buildingList = data.data;
						});
					}

					scope.queryRoom = function(enquiry) {
						var params = {
							"buildingId": enquiry.building.id
						};
						enquiry.area = null;
						enquiry.roomList = null;
						$http({
							method: 'POST',
							data: params,
							url: "/credit/risk/enquiry/v/queryHouse"
						}).success(function(data) {
							enquiry.roomList = data.data;
						});
					}

					scope.setArea = function(enquiry) {
						if(enquiry.room) {
							enquiry.area = enquiry.room.buildArea;
						}
					}

					scope.queryEnquiry = function(enquiry) {
						//初始化楼栋
						//scope.queryBuilding(enquiry);
						if(enquiry.showQuery) {
							scope.queryBuilding(enquiry);
							enquiry.showQuery = false;
							return false;
						}

						if(!enquiry.building) {
							box.boxAlert("请选择楼栋");
							return false;
						}

						if(!enquiry.room) {
							box.boxAlert("请选择房号");
							return false;
						}

						if(!enquiry.area) {
							box.boxAlert("请选择面积");
							return false;
						}

						if(!enquiry.registerPrice) {
							box.boxAlert("请选择登记价格");
							return false;
						}

						if(!enquiry.transactionprice && enquiry.consumerloans == '1' && params.productCode == '02') {
							box.boxAlert("请选择成交价格");
							return false;
						}

						enquiry.orderNo = scope.orderNo;
						enquiry.buildingId = enquiry.building.id;
						enquiry.buildingName = enquiry.building.name;
						enquiry.roomId = enquiry.room.id;
						enquiry.roomName = enquiry.room.name;

						var reg = /^[0-9]+$/;
						if(enquiry.consumerloans && reg.test(enquiry.consumerloans)) {
							enquiry.consumerloans = Number(enquiry.consumerloans);
						}
						var params = {
							"area": parseFloat(enquiry.area),
							"buildingId": enquiry.building.id,
							"buildingName": enquiry.building.name,
							"orderNo": scope.orderNo,
							"propertyId": enquiry.propertyId,
							"propertyName": enquiry.propertyName,
							"registerPrice": parseFloat(enquiry.registerPrice),
							"roomId": enquiry.room.id,
							"roomName": enquiry.room.name,
							"enquiryId": enquiry.enquiryId
						};
						params.isGetNetPriceTax = enquiry.isGetNetPriceTax;

						if(scope.productCode == "02") {
							params.consumerloans = enquiry.consumerloans;
							params.isGetNetPriceTax = 0;
							params.obligee = enquiry.obligee;
							params.range = enquiry.range;
						}

						box.waitAlert();

						$http({
							method: 'POST',
							data: params,
							url: "/credit/risk/enquiry/v/createEnquiry"
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								box.closeWaitAlert();
								enquiry.id = data.data.enquiryid;
								enquiry.enquiryid = data.data.enquiryid;
								showEnquiry(enquiry);
							} else {
								box.boxAlert(data.msg);
							}
						});
					}

					function showEnquiry(enquiry) {
						$http({
							method: 'POST',
							data: {
								enquiryId: enquiry.enquiryid,
								orderNo: enquiry.orderNo
							},
							url: "/credit/risk/enquiry/v/getEnquiryById"
						}).success(function(data) {
							enquiry.showQuery = true;
							enquiry.createtime = data.data.createtime;
							enquiry.totalPrice = data.data.totalPrice;
							enquiry.netPrice = data.data.netPrice;
							enquiry.maxLoanPrice = data.data.maxLoanPrice;
						});
					}

					function addNewArchive() {
						var archive = new Object();
						archive.estateType = "0";
						scope.archiveList.push(archive);
					}

					scope.addArchive = function() {
						addNewArchive();
					}

					scope.delArchive = function(index) {
						box.confirmAlert("删除查档", "确定删除该查档吗？", function() {
							if(scope.archiveList[index].archiveId) {
								$http({
									method: 'POST',
									data: {
										"orderNo": scope.orderNo,
										"archiveId": scope.archiveList[index].archiveId
									},
									url: "/credit/risk/archive/v/delete"
								}).success(function(data) {
									if(data.code == 'SUCCESS') {
										scope.archiveList.splice(index, 1);
									}
								});
							} else {
								$timeout(function() {
									scope.archiveList.splice(index, 1);
								});
							}
						});
					}

					scope.queryArchive = function(archive) {

						archive.estateType = String(archive.estateType);
						if(archive.id) {
							//archive.archiveId = archive.id;
							archive.id = null;
							return false;
						}

						if(archive.estateType != null && (archive.estateType != "0" || archive.estateType != 0)) {
							scope.yearNo = archive.estateType;
							archive.estateType = "1";
						} else {
							scope.yearNo = "2015";
						}
						var params = {
							"estateNo": archive.estateNo,
							"estateType": archive.estateType,
							"identityNo": archive.identityNo,
							"orderNo": scope.orderNo,
							"type": 1,
							"yearNo": scope.yearNo,
							"archiveId": archive.archiveId,
							"productCode": scope.productCode
						};

						if(archive.estateType != null && archive.estateType != "0") {
							archive.estateType = scope.yearNo;
						}

						box.waitAlert("正在查询...");

						$http({
							method: 'POST',
							data: params,
							url: "/credit/risk/archive/v/getArchive"
						}).success(function(data) {
							box.closeWaitAlert();
							if("SUCCESS" == data.code || "SAME_ROOM_NUMBER" == data.code) {
								archive.id = data.data.archiveId;
								archive.archiveId = data.data.archiveId;
								archive.createTime = data.data.createTime;
								archive.message = data.data.message;
//								scope.getArchiveById(archive);
							} else {
								box.boxAlert(data.msg);
							}
						}).error(function() {
							box.closeWaitAlert();
							box.boxAlert("操作异常！");
						});
					}

//					scope.getArchiveById = function(obj) {
//						$http({
//							url: '/credit/risk/archive/v/getArchiveById',
//							data: {
//								'archiveId': obj.archiveId,
//								"orderNo": scope.orderNo,
//								"productCode": scope.productCode
//							},
//							method: 'POST'
//						}).success(function(data) {
//							if("SUCCESS" == data.code) {
//								obj.message = data.data.message;
//							} else {
//								box.boxAlert(data.msg);
//							}
//						});
//					}

					//刷新全国验证码
					scope.refreshCountryValidateCode = function() {
						$http({
							method: 'POST',
							url: '/credit/risk/lawsuit/v/getQGAuthCode',
							data: params
						}).success(function(data) {
							scope.QGAuthCode = data.QGAuthCode;
							scope.countryValidateCodeSrc = data.QGAuthCode + "?" + Math.random();
							var str = scope.countryValidateCodeSrc;
							var index = str.indexOf("captchaId=");
							if((str.indexOf("captchaId=")) != -1 && (str.indexOf("&") != -1)) {
								str = str.substring(index + ("captchaId=".length), str.indexOf("&"));
							}
							scope.captchaId = str;
						})
					}

					//全国诉讼
					scope.showQueryCountry = function() {
						if(scope.pname || scope.cardNum) {} else {
							box.boxAlert("姓名和号码必须填一个");
							return false;
						}
						var queryCountry = function() {

							var params = {
								"captchaId": scope.captchaId,
								"code": scope.countryValidateCode,
								"cardNum": scope.cardNum,
								"pname": scope.pname,
								"orderNo": scope.orderNo
							};

							$http({
								method: 'POST',
								url: '/credit/risk/lawsuit/v/getLawsuitId',
								data: params
							}).success(function(data) {
								if("SUCCESS" == data.code) {
									getCountryListData(data.lawsuitId);
								} else {
									box.boxAlert(data.msg);
								}

							})
						}
						scope.refreshCountryValidateCode();
						var html = '<input type="text" style="width:100px;" ng-model="countryValidateCode" />&nbsp;<img ng-click="refreshCountryValidateCode()" src="{{countryValidateCodeSrc}}" ><a ng-click="refreshCountryValidateCode()">刷新</a>';
						box.editAlert(scope, "请输入查询验证码", html, queryCountry);
					}

					//全国诉讼
					scope.showDetailQueryCountry = function(obj) {

						var queryCountry = function() {
							var params = {
								"id": obj.detailId,
								"j_captcha": scope.countryValidateCode,
								"captchaId": scope.captchaId
							};
							console.log(params);
							selectQGDetail(params);
						}
						scope.refreshCountryValidateCode();
						var html = '<input type="text" style="width:100px;" ng-model="countryValidateCode" />&nbsp;<img ng-click="refreshCountryValidateCode()" src="{{countryValidateCodeSrc}}" ><a ng-click="refreshCountryValidateCode()">刷新</a>';
						box.editAlert(scope, "请输入查询验证码", html, queryCountry);
					}

					window.operateEvents = {
						'click .enquiryDetail': function(e, value, row, index) {
							qgDetail(row);
						}
					};

					function qgDetail(obj) {
						if(!scope.countryValidateCode || "" == scope.countryValidateCode || !scope.captchaId) {
							scope.showDetailQueryCountry(obj);
						} else {
							var params = {
								"id": obj.detailId,
								"j_captcha": scope.countryValidateCode,
								"captchaId": scope.captchaId
							};
							selectQGDetail(params, obj);
						}

					}

					function selectQGDetail(params, obj) {
						var close = function() {
							box.closeAlert();
						}
						$http({
							method: 'POST',
							url: '/credit/risk/lawsuit/v/getQGDetail',
							data: params
						}).success(function(data) {
							console.log(data);
							if("SUCCESS" == data.code) {
								box.closeAlert();
								var html = "<table>" +
									"<tr><td>被执行人姓名/名称：</td><td>" + data.data.pname + "</td></tr>" +
									"<tr><td>身份证号码/组织机构代码：</td><td>" + data.data.partyCardNum + "</td></tr>" +
									"<tr><td>执行法院：</td><td>" + data.data.execCourtName + "</td></tr>" +
									"<tr><td>立案时间：</td><td>" + data.data.caseCreateTime + "</td></tr>" +
									"<tr><td>案号：</td><td>" + data.data.caseCode + "</td></tr>" +
									"<tr><td>执行标的：</td><td>" + data.data.execMoney + "</td></tr>" +
									"</table>";
								box.editAlert(scope, "全国诉讼详情", html, close);
							} else {
								scope.countryValidateCode = "";
								box.boxAlert(data.msg);
							}

						});
					}

					function getCountryListData(lawsuitId) {
						$http({
							method: 'POST',
							url: '/credit/risk/lawsuit/v/getQGListByLawsuitId',
							data: {
								"lawsuitId": lawsuitId,
								"orderNo": scope.orderNo
							}
						}).success(function(data) {
							console.log(data);
							scope.countryShow = true;
							scope.countryListData = data.data;
							scope.countryList = {
								options: {
									data: scope.countryListData,
									columns: [{
										title: '序号',
										field: 'id',
										align: 'right',
										valign: 'bottom'
									}, {
										title: '被执行人/组织名称',
										field: 'name',
										align: 'center',
										valign: 'bottom'
									}, {
										title: '立案时间',
										field: 'date',
										align: 'center',
										valign: 'bottom'
									}, {
										title: '案号',
										field: 'caseNo',
										align: 'center',
										valign: 'bottom'
									}, {
										title: '操作',
										field: 'operate',
										align: 'center',
										valign: 'bottom',
										events: operateEvents,
										formatter: function(value, row, index) {
											var html = '-';
											if(row.detailId || null != row.detailId) {
												html = "<span class='enquiryDetail'><a href='javasrcipt:void(0);'>[查看]</a></span>";
											}
											return html;
										}
									}]
								}
							};
							box.closeAlert();
						})

					}

					//刷新深圳验证码
					scope.refreshSzValidateCode = function() {
						$http({
							method: 'POST',
							url: '/credit/risk/lawsuit/v/getSZAuthCode'
						}).success(function(data) {
							scope.szValidateCodeSrc = data.SZAuthCode + "?" + Math.random();
						})
					}

					scope.querySz = function() {
						var params = {
							"appliers": scope.pname,
							"cardNum": scope.cardNum,
							"code": scope.szValidateCode,
							"orderNo": scope.orderNo
						};
						$(".lhw-alert-ok").attr("disabled", "disabled");
						$http({
							method: 'POST',
							url: '/credit/risk/lawsuit/v/searchSZ',
							data: params
						}).success(function(data) {
							getSzListData(data.data.lawsuitId);
						})
					}
					//深圳诉讼
					scope.showQuerySz = function() {
						if(!scope.pname) {
							box.boxAlert("姓名必须填");
							return false;
						}
						scope.refreshSzValidateCode();
						var html = '<input type="text" style="width:100px;" ng-model="szValidateCode" />&nbsp;<img ng-click="refreshSzValidateCode()" src="{{szValidateCodeSrc}}" ><a ng-click="refreshSzValidateCode()">刷新</a>';
						box.editAlert(scope, "请输入查询验证码", html, scope.querySz);
					}

					function getSzListData(lawsuitId) {
						$http({
							method: 'POST',
							url: '/credit/risk/lawsuit/v/getSZListByLawsuitId',
							data: {
								"lawsuitId": lawsuitId,
								"orderNo": scope.orderNo
							}
						}).success(function(data) {
							var szListData = data.data;
							scope.szList = {
								options: {
									data: szListData,
									columns: [{
										title: '序号',
										field: 'id',
										align: 'right',
										valign: 'bottom'
									}, {
										title: '承办法官/法官助理',
										field: 'judge',
										align: 'center',
										valign: 'bottom'
									}, {
										title: '当事人',
										field: 'litigant',
										align: 'center',
										valign: 'bottom'
									}, {
										title: '立案时间',
										field: 'registerDate',
										align: 'center',
										valign: 'bottom'
									}, {
										title: '结案时间',
										field: 'closeCourtDate',
										align: 'center',
										valign: 'bottom'
									}, {
										title: '案件状态',
										field: 'status',
										align: 'center',
										valign: 'bottom'
									}]
								}
							};
							scope.szShow = true;
							box.closeAlert();
						});
					}

					scope.uploadImg = function(imgs, imgType) {
						var params = {
							"orderNo": scope.orderNo,
							"imgUrl": String(imgs),
							"imgType": imgType
						};

						$http({
							method: 'POST',
							url: '/credit/risk/base/v/addImg',
							data: params
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								if(imgType == 'lawsuit') {
									scope.lawsuitList.lawsuitImgs = data.data;
								} else if(imgType == 'enquiry') {
									scope.enquiryList[0].enquiryImgs = data.data;
								} else if(imgType == 'archive') {
									scope.archiveList[0].archiveImgs = data.data;
								}
								box.boxAlert("上传成功")
							} else {
								box.boxAlert(data.msg);
							}
						});
					}

				}
			};
		});

	};
});

function returnDataLawsuit(url, smallUrl, name) {
	var scope = angular.element('enquiry-edit').scope();
	$("#lawsuitImg").val(url);
	scope.uploadImg(url, 'lawsuit');
}

function returnDataEnquir(url, smallUrl, name) {
	var scope = angular.element('enquiry-edit').scope();
	$("#enquiryImg").val(url);
	scope.uploadImg(url, 'enquiry');
}

function returnDataArchive(url, smallUrl, name) {
	var scope = angular.element('enquiry-edit').scope();
	$("#archiveImg").val(url);
	scope.uploadImg(url, 'archive');
}