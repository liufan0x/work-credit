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
						url: '/credit/process/notarization/v/detail',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
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

		//提单详情
		app.directive('placeOrderDetail', function($http, $timeout, $state, route, box) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/order/placeOrderDetail.html',
				transclude: true,
				link: function(scope) {
					scope.huarongTitle = "huarongOrderShow";
					$timeout(function() {
						$("textarea").txtaAutoHeight();
					}, 1000);

					var params = route.getParams();
					scope.cityCode = params.cityCode;
					scope.orderNo = route.getParams().orderNo;
					scope.QGAuthCode = "";
					//借款信息
					$http({
						method: 'POST',
						url: 'credit/order/borrow/v/query',
						data: {
							"orderNo": route.getParams().orderNo
						}
					}).success(function(data) {
						scope.borrow = data.data;
						scope.cityCode = scope.borrow.cityCode;
						scope.productCode = scope.borrow.productCode;
					})

					//查档诉讼信息
					$http({
						method: 'POST',
						url: '/credit/risk/lawsuit/v/loadInquiryAndArchiveInit',
						data: params
					}).success(function(data) {
						if(data.data) {
							scope.enquiryList = data.data.enquiryData;
						}
						if(!scope.enquiryList || scope.enquiryList.length == 0) {
							scope.enquiryList = new Array();
							addNewEnquiry();
						} else {
							scope.showImgs(scope.enquiryList[0].enquiryImgs, "enquiry");
						}

						scope.archiveList = data.data.archiveData;
						if(!scope.archiveList || scope.archiveList.length == 0) {
							scope.archiveList = new Array();
							addNewArchive();
						} else {
							scope.showImgs(scope.archiveList[0].archiveImgs, "archive");
						}

						if(data.data.lawsuitData) {
							if(data.data.lawsuitData.id) {
								getCountryListData(data.data.lawsuitData.id);
								getSzListData(data.data.lawsuitData.id);
							}
							scope.showImgs(data.data.lawsuitData.lawsuitImgs, "lawsuit");
						}

					})

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
						//						scope.queryBuilding(enquiry);
						if(enquiry.id) {
							enquiry.id = null;
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
						var params;
						if(scope.borrow.productCode == "02") {
							params = {
								"area": parseFloat(enquiry.area),
								"buildingId": enquiry.building.id,
								"buildingName": enquiry.building.name,
								"consumerloans": enquiry.consumerloans,
								"isGetNetPriceTax": 0,
								"obligee": enquiry.obligee,
								"orderNo": scope.orderNo,
								"propertyId": enquiry.propertyId,
								"propertyName": enquiry.propertyName,
								"range": enquiry.range,
								"registerPrice": parseFloat(enquiry.registerPrice),
								"roomId": enquiry.room.id,
								"roomName": enquiry.room.name,
								"enquiryid": enquiry.enquiryId
							};
						} else {
							params = {
								"area": parseFloat(enquiry.area),
								"buildingId": enquiry.building.id,
								"buildingName": enquiry.building.name,
								"isGetNetPriceTax": enquiry.isGetNetPriceTax,
								"orderNo": scope.orderNo,
								"propertyId": enquiry.propertyId,
								"propertyName": enquiry.propertyName,
								"registerPrice": parseFloat(enquiry.registerPrice),
								"roomId": enquiry.room.id,
								"roomName": enquiry.room.name,
								"enquiryid": enquiry.enquiryId
							};
						}
						/*
						var params = {
							"orderNo": scope.orderNo,
							"propertyId": enquiry.propertyId,
							"propertyName": enquiry.propertyName,
							"buildingId": enquiry.building.id,
							"buildingName": enquiry.building.name,
							"roomId": enquiry.room.id,
							"roomName": enquiry.room.name,
							"area": parseFloat(enquiry.area),
							"registerPrice": parseFloat(enquiry.registerPrice),
							"consumerloans": parseFloat(enquiry.consumerloans),
							"dealPrice": parseFloat(!enquiry.transactionprice ? 0 : enquiry.transactionprice),
							"obligee": enquiry.obligee,
							"range": enquiry.range,
							"enquiryId": enquiry.enquiryId
						}
						*/
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
						box.closeWaitAlert();
						var date = new Date();
						var seperator1 = "-";
						var seperator2 = ":";
						var month = date.getMonth() + 1;
						var strDate = date.getDate();
						if(month >= 1 && month <= 9) {
							month = "0" + month;
						}
						if(strDate >= 0 && strDate <= 9) {
							strDate = "0" + strDate;
						}
						var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate +
							" " + date.getHours() + seperator2 + date.getMinutes();

						archive.id = "xxxxxxxxxx";
						archive.createTime = currentdate;
						archive.message = null;

						/*$http({
							method: 'POST',
							data: params,
							url: "/credit/risk/archive/v/getArchive"
						}).success(function(data) {
							box.closeWaitAlert();
							if("SUCCESS" == data.code||"SAME_ROOM_NUMBER"==data.code) {
								archive.id = data.data.archiveId;
								archive.archiveId = data.data.archiveId;
								archive.createTime = data.data.createTime;
								archive.message = data.data.message;
//								scope.getArchiveById(archive);
							} else {
								box.boxAlert(data.msg);
							}
						}).error(function(){
							box.closeWaitAlert();
							box.boxAlert("操作异常！");
						});*/
					}

					/*scope.getArchiveById = function(obj){
						$http({
							url:'/credit/risk/archive/v/getArchiveById',
							data:{'archiveId':obj.archiveId,"orderNo":scope.orderNo,"productCode":scope.productCode},
							method:'POST'
						}).success(function(data){
							if("SUCCESS"==data.code){
								obj.message = data.data.message;
							} else {
								box.boxAlert(data.msg);
							}
						});
					}*/

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

					//询价查档诉讼
					//上传
					//诉讼
					scope.lawsuitUploadImg = function(imgs) {
						imgs = String(imgs);
						var params = {
							"orderNo": scope.orderNo,
							"imgUrl": imgs,
							"imgType": "lawsuit"
						};
						$http({
							method: 'POST',
							url: '/credit/risk/base/v/addImg',
							data: params
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.lawsuitImgShow(data.data);
								box.boxAlert("上传成功")
							} else {
								box.boxAlert(data.msg);
							}
						});
					}
					scope.lawsuitImgShow = function(list) {
						$(".lawsuitUpdImg").find("img").remove();
						scope.lawsuitImgList = list;
						if(null == scope.lawsuitImgList || scope.lawsuitImgList.length <= 0) {
							$(".lawsuitUpdImg").hide();
							$(".closeWin").trigger("click");
							return;
						}
						/*
						var imgs = "";
						angular.forEach(list,function(data,index,array){
							imgs += "<img src='"+data.imgUrl+"' class='gallery-pic' style='display:none;'>";
						});
						$(".lawsuitUpdImg").append(imgs);*/
						$(".lawsuitUpdImg").show();
					}

					//询价
					scope.enquiryUploadImg = function(imgs) {
						imgs = String(imgs);
						scope.tmpObj = new Object();
						scope.tmpObj.orderNo = scope.orderNo;
						scope.tmpObj.imgUrl = imgs;
						scope.tmpObj.imgType = "enquiry";

						var params = angular.fromJson(scope.tmpObj);

						$http({
							method: 'POST',
							url: '/credit/risk/base/v/addImg',
							data: params
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.enquiryImgShow(data.data);
								box.boxAlert("上传成功");
							} else {
								box.boxAlert(data.msg);
							}

						})
					}

					scope.enquiryImgShow = function(list) {
						$(".enquiryUpdImg").find("img").remove();
						scope.enquiryImgList = list;
						if(null == scope.enquiryImgList || scope.enquiryImgList.length <= 0) {
							$(".enquiryUpdImg").hide();
							$(".closeWin").trigger("click");
							return;
						}
						/*
						var imgs = "";
						angular.forEach(list,function(data,index,array){
							imgs += "<img src='"+data.imgUrl+"' class='gallery-pic' style='display:none;'>";
						});
						$(".enquiryUpdImg").append(imgs);
						*/
						$(".enquiryUpdImg").show();
					}

					//查档
					scope.archiveUploadImg = function(imgs) {
						imgs = String(imgs);
						var params = {
							"orderNo": scope.orderNo,
							"imgUrl": imgs,
							"imgType": "archive"
						};
						$http({
							method: 'POST',
							url: '/credit/risk/base/v/addImg',
							data: params
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.archiveImgShow(data.data);
								box.boxAlert("上传成功");
							} else {
								box.boxAlert(data.msg);
							}
						})
					}
					scope.archiveImgShow = function(list) {
						$(".archiveUpdImg").find("img").remove();
						scope.archiveImgList = list;
						if(null == scope.archiveImgList || scope.archiveImgList.length <= 0) {
							$(".archiveUpdImg").hide();
							$(".closeWin").trigger("click");
							return;
						}
						/*
						var imgs = "";
						angular.forEach(list,function(data,index,array){
							imgs += "<img src='"+data.imgUrl+"' class='gallery-pic' style='display:none;'>";
						});
						$(".archiveUpdImg").append(imgs);*/
						$(".archiveUpdImg").show();
					}

					scope.showImgs = function(obj, className) {
						if(null != obj && obj.length > 0) {
							if("lawsuit" == className) {
								scope.lawsuitImgList = obj;
							} else if("archive" == className) {
								scope.archiveImgList = obj;
							} else if("enquiry" == className) {
								scope.enquiryImgList = obj;
							}
							$("." + className + "UpdImg").show();
						}
					}
					scope.delEnquiryImgs = function(ids, imgType) {
						$http({
							url: '/credit/risk/base/v/delImg',
							data: {
								"id": ids,
								"imgType": imgType,
								"orderNo": scope.orderNo
							},
							method: 'POST'
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								if(imgType == "archive") {
									scope.archiveImgShow(data.data);
								} else if(imgType == "enquiry") {
									scope.enquiryImgShow(data.data);
								} else if(imgType == "lawsuit") {
									scope.lawsuitImgShow(data.data);
								}
							}
							box.boxAlert(data.msg);
						});
					}

				}
			};
		});

		//询价房产名称
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

		/*要件详情*/
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

									$scope.isShow = true;
									angular.forEach($scope.page, function(data) {
										angular.forEach(data.handleEle.eleFilePayList, function(newData) {
											if("" == newData.title) {
												$scope.isShow = false;
											}
										});

									});

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

		//提单详情
		app.directive('elementEntranceDetail', function($http, $timeout, $state, route) {
			return {
				restrict: "E",
				templateUrl: function() {
					var productCode = route.getParams().productCode;
					if(productCode == "04") {
						return '/template/order-common/orderCommonDetail.html';
					} else {
						return '/template/sl/order/elementEntranceDetail.html';
					}

				},
				transclude: true,
				link: function(scope) {
					var productCode = route.getParams().productCode;
					if(productCode == "04") {
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

								angular.forEach(scope.pageConfigDto.pageTabConfigDtos[0].pageTabRegionConfigDtos[0].valueList[0], function(data1) {
									if(data1.specialKey == 'customerType') {
										if(data1.value == "个人") {
											scope.isEnterprise = false;
										} else {
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
						//scope.inRelationOrderNo2Detail = "" != route.getParams().relationOrderNo && route.getParams().relationOrderNo.length > 5 ? route.getParams().relationOrderNo : null;

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
						url: '/credit/process/managerAudit/v/detail',
						data: params
					}).success(function(data) {
						scope.obj = data.data;
					})
				}
			};
		});

		//风控初审详情
		app.directive('auditFirstDetail', function($http, route, parent) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/auditFirstDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var fundId = parent.userDto.fundId;
					var orderNo = route.getParams().orderNo;
					var params = {
						"orderNo": orderNo,
						"fundId": fundId
					}
					scope.fundId = fundId;
					if(fundId == 31) {
						$http({
							method: 'POST',
							url: "/credit/risk/base/v/queryApply",
							data: {
								"orderNo": orderNo
							}
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.kgApproval = data.data.kgApproval;
							}
						});
					} else {
						$http({ //其他资方
							method: 'POST',
							url: '/credit/third/api/ordinary/v/ordinaryDetail',
							data: params
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.obj = data.data.ordinaryAudit;
							} else {
								alert(data.msg);
							}

							$http({
								method: 'POST',
								url: '/credit/risk/first/v/detail',
								data: params
							}).success(function(data) {
								if("SUCCESS" == data.code) {
									if(scope.obj == null) {
										scope.obj = data.data;
									} else {
										scope.obj.handleName = data.data.handleName;
										scope.obj.auditStatus = data.data.auditStatus;
									}
								} else {
									alert(data.msg);
								}
							})
						})
					}

				}
			};
		});

		//风控终审详情
		app.directive('auditFinalDetail', function($http, route, parent) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/auditFinalDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var fundId = parent.userDto.fundId;
					var params = {
						"orderNo": orderNo,
						"fundId": fundId
					}
					scope.fundId = fundId;
					if(fundId == 31) {
						$http({
							method: 'POST',
							url: "/credit/risk/base/v/queryApply",
							data: {
								"orderNo": orderNo
							}
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.kgApproval = data.data.kgApproval;
							}
						});
					} else {
						$http({
							method: 'POST',
							url: '/credit/risk/final/v/detail',
							data: params
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.obj = data.data;
							} else {
								alert(data.msg);
							}
							$http({ //其他资方
								method: 'POST',
								url: '/credit/third/api/ordinary/v/ordinaryDetail',
								data: params
							}).success(function(data) {
								if("SUCCESS" == data.code) {
									if(data.data != null && data.data.ordinaryAudit != null && data.data.ordinaryAudit.spareField1 != null) {
										scope.obj.remark = data.data.ordinaryAudit.spareField1;
									}
								} else {
									alert(data.msg);
								}
							})
						})
					}
				}
			};
		});

		//风控首席风险官详情
		app.directive('auditOfficerDetail', function($http, route, parent) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/auditOfficerDetail.html',
				scope: true,
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var fundId = parent.userDto.fundId;
					var params = {
						"orderNo": orderNo,
						"fundId": fundId
					}
					scope.fundId = fundId;
					if(fundId == 31) {
						$http({
							method: 'POST',
							url: "/credit/risk/base/v/queryApply",
							data: {
								"orderNo": orderNo
							}
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.kgApproval = data.data.kgApproval;
							}
						});
					} else {
						$http({
							method: 'POST',
							url: '/credit/risk/officer/v/detail',
							data: params
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.obj = data.data;
							} else {
								alert(data.msg);
							}
						})
						$http({ //其他资方
							method: 'POST',
							url: '/credit/third/api/ordinary/v/ordinaryDetail',
							data: params
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								if(data.data != null && data.data.ordinaryAudit != null && data.data.ordinaryAudit.spareField2 != null) {
									scope.obj.remark = data.data.ordinaryAudit.spareField2;
								}
							} else {
								alert(data.msg);
							}
						})
					}
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
						url: '/credit/risk/allocationfund/v/fundDetail',
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
						url: '/credit/risk/dataAudit/v/detail',
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
						url: '/credit/process/distributionMember/v/detail',
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
						url: '/credit/finance/applyLoan/v/detail',
						data: params
					}).success(function(data) {
						if("SUCCESS" == data.code) {
							scope.obj = data.data.loanDto;
							//借款信息
							$http({
								method: 'POST',
								url: 'credit/report/fund/v/borrow',
								data: {
									"orderNo": orderNo
								}
							}).success(function(data1) {
								if(data1.data != null && data1.data.borrowingDays != null && data1.data.loanAmount != null) {
									scope.obj.borrowingDays = data1.data.borrowingDays;
									scope.obj.loanAmount = data1.data.loanAmount;
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
									}
								}
							})
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
						url: '/credit/finance/lendingInterest/v/detail',
						data: params
					}).success(function(data) {
						scope.isProductCode = scope.obj.productCode;
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
						url: '/credit/finance/lendingHarvest/v/detail',
						data: params
					}).success(function(data) {
						scope.isProductCode = scope.obj.productCode;
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
								url: "/credit/customer/risk/v/findStageRate"
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
						url: '/credit/finance/lendingInterest/v/detail',
						data: params
					}).success(function(data) {
						scope.isProductCode = scope.obj.productCode;
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
						url: '/credit/finance/lendingHarvest/v/detail',
						data: params
					}).success(function(data) {
						scope.isProductCode = scope.obj.productCode;
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
								url: "/credit/customer/risk/v/findStageRate"
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
		app.directive('lendingDetail', function($http, route, parent) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/finance/lendingDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var fundId = parent.userDto.fundId;
					scope.fundId = fundId;
					if(fundId == 31) {
						$http({
							method: 'POST',
							url: "/credit/report/fund/v/lending",
							data: {
								"orderNo": orderNo
							}
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.obj = data.data;
							}
						});
					} else {
						$http({
							method: 'POST',
							url: '/credit/finance/lending/v/detail',
							data: {
								"orderNo": orderNo
							}
						}).success(function(data) {
							scope.obj = data.data;
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
								data: {
									"orderNo": orderNo
								}
							}).success(function(data) {
								if("SUCCESS" == data.code) {
									scope.fund = data.data.fundCompleteDto;
									scope.audit = data.data.fundAudit;
									scope.isHuaRongShow = data.data.isHuaRongShow;
								}
							});
						})
					}
				}
			};
		});

		//结清原贷款信息详情
		app.directive('foreclosureDetail', function($http, route, parent) {
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
					var fundId = parent.userDto.fundId;
					scope.fundId = fundId;
					if(fundId == 31) {
						$http({
							method: 'POST',
							url: '/credit/report/fund/v/foreclosure',
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
					} else {
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

		//回款详情
		app.directive('receivableForDetail', function($http, route) {
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
						scope.huaRong = data.data.auditMap;
					})
				}
			};
		});

		//要件退还详情
		app.directive('elementReturnDetail', function($http, route, parent) {
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
					var fundId = parent.userDto.fundId;
					scope.fundId = fundId;
					$http({
						method: 'POST',
						url: '/credit/element/return/v/detail',
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
		app.directive('allocationFundHuaanOrderDetail', function($http, route) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuaanOrderDetail.html',
				link: function(scope) {
					//					var orderNo = route.getParams().orderNo;
					//					$http({
					//						url: '/credit/risk/allocationfund/v/loadPushOrder',
					//						method: 'POST',
					//						data: {
					//							"orderNo": orderNo
					//						}
					//					}).success(function(data) {
					//						if("SUCCESS" == data.code) {
					//							scope.huaan = data.data;
					//						} else {
					//							alert(data.msg);
					//						}
					//					});
				}
			};
		});
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

		app.directive("allocationFundHuarongOrderDetail", function($http, route, parent) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuarongOrderDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var fundId = parent.userDto.fundId;
					scope.fundId = fundId;
					scope.borrow = new Object();
					if(fundId == 31) {
						$http({
							method: 'POST',
							url: "/credit/risk/base/v/queryApply",
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
					} else {
						$http({
							method: 'POST',
							url: 'credit/order/base/v/selectDetailByOrderNo',
							data: {
								"orderNo": orderNo
							}
						}).success(function(data) {
							//初始化页面
							scope.cityCode = data.data.cityCode;
							scope.productCode = data.data.productCode;
							scope.relationOrderNo = data.data.relationOrderNo;
							if(scope.productCode == '03') {
								$http({
									method: 'POST',
									url: 'credit/order/borrow/v/query',
									data: {
										"orderNo": route.getParams().orderNo
									}
								}).success(function(data) {
									if(scope.relationOrderNo == null || scope.relationOrderNo == '') {
										scope.borrow = data.data;
										if(scope.borrow.orderReceivableForDto && scope.borrow.orderReceivableForDto.length > 0) {
											for(var i = 0; i < scope.borrow.orderReceivableForDto.length; i++) {
												scope.borrow.orderReceivableForDto[i].payMentAmountDate = scope.borrow.orderReceivableForDto[i].payMentAmountDateStr;
											}
										}
										//借款信息
										$http({
											method: 'POST',
											url: 'credit/report/fund/v/borrow',
											data: {
												"orderNo": orderNo
											}
										}).success(function(data) {
											if(data.data != null && data.data.borrowingDays != null && data.data.loanAmount != null) {
												scope.borrow.borrowingDays = data.data.borrowingDays;
												scope.borrow.loanAmount = data.data.loanAmount;
												if(data.data.isLoanBank != null && data.data.loanBankNameId != null) { //设置新贷款是否银行
													scope.borrow.isLoanBank = data.data.isLoanBank;
													scope.borrow.loanBankNameId = data.data.loanBankNameId;
													scope.borrow.loanSubBankNameId = data.data.loanSubBankNameId;
													scope.borrow.loanBankName = data.data.loanBankName;
													scope.borrow.loanSubBankName = data.data.loanSubBankName;
												}
											}
										})
									} else {
										scope.cdBorrow = data.data;
										//借款信息
										$http({
											method: 'POST',
											url: 'credit/report/fund/v/borrow',
											data: {
												"orderNo": orderNo
											}
										}).success(function(data) {
											if(data.data != null && data.data.borrowingDays != null && data.data.loanAmount != null) {
												scope.cdBorrow.borrowingDays = data.data.borrowingDays;
												scope.cdBorrow.loanAmount = data.data.loanAmount;
												if(data.data.isLoanBank != null && data.data.loanBankNameId != null) { //设置新贷款是否银行
													scope.cdBorrow.isLoanBank = data.data.isLoanBank;
													scope.cdBorrow.loanBankNameId = data.data.loanBankNameId;
													scope.cdBorrow.loanSubBankNameId = data.data.loanSubBankNameId;
													scope.cdBorrow.loanBankName = data.data.loanBankName;
													scope.cdBorrow.loanSubBankName = data.data.loanSubBankName;
												}
											}
										})
									}

									//关联的畅贷详情
									if(scope.relationOrderNo != null && scope.relationOrderNo != '') {
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
								})

							} else {
								//借款信息
								$http({
									method: 'POST',
									url: 'credit/order/borrow/v/query',
									data: {
										"orderNo": orderNo
									}
								}).success(function(data) {
									scope.borrow = data.data;
									//回款日期
									if(scope.borrow.orderReceivableForDto && scope.borrow.orderReceivableForDto.length > 0) {
										for(var i = 0; i < scope.borrow.orderReceivableForDto.length; i++) {
											scope.borrow.orderReceivableForDto[i].payMentAmountDate = scope.borrow.orderReceivableForDto[i].payMentAmountDateStr;
										}
									}
									$http({
										method: 'POST',
										url: 'credit/report/fund/v/borrow',
										data: {
											"orderNo": orderNo
										}
									}).success(function(data) {
										if(data.data != null && data.data.borrowingDays != null && data.data.loanAmount != null) {
											scope.borrow.borrowingDays = data.data.borrowingDays;
											scope.borrow.loanAmount = data.data.loanAmount;
											if(data.data.isLoanBank != null && data.data.loanBankNameId != null) { //设置新贷款是否银行
												scope.borrow.isLoanBank = data.data.isLoanBank;
												scope.borrow.loanBankNameId = data.data.loanBankNameId;
												scope.borrow.loanSubBankNameId = data.data.loanSubBankNameId;
												scope.borrow.loanBankName = data.data.loanBankName;
												scope.borrow.loanSubBankName = data.data.loanSubBankName;
											}
										}
									})
								})
							}
						})
					}
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
						url: "/credit/risk/base/v/queryLend",
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
		app.directive("allocationFundHuarongAppointDetail", function($http, route, parent) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuarongAppointDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					var orderNo = route.getParams().orderNo;
					var fundId = parent.userDto.fundId;
					scope.fundId = fundId;
					scope.relationOrderNo = '';
					if(fundId != 31) {
						$http({
							method: 'POST',
							url: 'credit/order/base/v/selectDetailByOrderNo',
							data: {
								"orderNo": orderNo
							}
						}).success(function(data) {
							//初始化页面
							scope.cityCode = data.data.cityCode;
							scope.productCode = data.data.productCode;
							scope.relationOrderNo = data.data.relationOrderNo;
							if(scope.relationOrderNo != null && scope.relationOrderNo != '') {
								orderNo = scope.relationOrderNo;
							}
							//客户信息
							$http({
								method: 'POST',
								url: '/credit/order/customer/v/query',
								data: {
									"orderNo": orderNo
								}
							}).success(function(data) {
								scope.customer = data.data;
							})
						})
					}
				}
			};
		});
		app.directive("allocationFundHuarongBusinfoDetail", function($http, route, parent, box) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuarongBusinfoDetail.html',
				transclude: true,
				link: function(scope) {
					
					scope.download=function(){
						var custName = null;
		                  if (scope.borrow) {
		                    custName = scope.borrow.borrowerName;
		                  } else if (scope.applAppt) {
		                    custName = scope.applAppt.custName;
		                  }
		                  box.waitAlert();
						$http({
							url: '/credit/order/businfo/v/getBusinfoAndTypeNames',
							method: 'POST',
							data:{ 
								"topType":scope.topTypes,
								"custName":custName
							}
						}).success(function(data1) {
							if ("SUCCESS" == data1.code) {
								window.open(data1.data);
							}
							 box.closeWaitAlert();
						});
					}
					
					
					scope.obj = new Object();
					scope.isImgEdit = false;
					var orderNo = route.getParams().orderNo;
					var fundId = parent.userDto.fundId;
					scope.fundId = fundId;
					if(fundId == 31) {
						$http({
							method: 'POST',
							url: "/credit/risk/allocationfund/v/loadHuarongBusInfo",
							data: {
								"orderNo": orderNo
							}
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.huarongImageDatas = data.data;
								scope.topTypes = data.data.topType;
							}
						});
					} else {
						$http({
							method: 'POST',
							url: "/credit/third/api/ordinary/v/loadOrdinaryBusInfo",
							data: {
								"orderNo": orderNo,
								"ordinaryFund": fundId,
								//"orderNo": scope.orderNo,
								//"ordinaryFund": scope.ordinaryFundId,
								"productCode": route.getParams().productCode
							}
						}).success(function(data) {
							scope.imageDatas = data.data;
								scope.huarongImageDatas = data.data;
								scope.topTypes = data.data.topType;
							
						});
					}
				}
			};
		});
		
		
		app.directive("allocationFundHuarongApprovalDetail", function($http, route, $timeout) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuarongApprovalDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					$timeout(function() {
						$("textarea").txtaAutoHeight();
					});
				}
			};
		});

		app.directive("allocationFundHuarongHouseDetail", function($http, route, $timeout) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuarongHouseDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					$timeout(function() {
						$("textarea").txtaAutoHeight();
					});
					var orderNo = route.getParams().orderNo;
					$http({
						method: 'POST',
						url: '/credit/order/base/v/selectDetailByOrderNo',
						data: {
							"orderNo": orderNo
						}
					}).success(function(data) {
						scope.obj = data.data;
						scope.cityCode = data.data.cityCode;
					})
					//非华融资金方查档诉讼详情

					//查档诉讼信息
					$http({
						method: 'POST',
						url: '/credit/risk/lawsuit/v/loadInquiryAndArchiveInit',
						data: {
							"orderNo": orderNo
						}
					}).success(function(data) {
						if(data.data) {
							scope.enquiryList = data.data.enquiryData;
						}
						if(!scope.enquiryList || scope.enquiryList.length == 0) {
							scope.enquiryList = new Array();
							addNewEnquiry();
						} else {
							scope.showImgs(scope.enquiryList[0].enquiryImgs, "enquiry");
						}

						scope.archiveList = data.data.archiveData;
						if(!scope.archiveList || scope.archiveList.length == 0) {
							scope.archiveList = new Array();
							addNewArchive();
						} else {
							scope.showImgs(scope.archiveList[0].archiveImgs, "archive");
						}

						if(data.data.lawsuitData) {
							if(data.data.lawsuitData.id) {
								getCountryListData(data.data.lawsuitData.id);
								getSzListData(data.data.lawsuitData.id);
							}
							scope.showImgs(data.data.lawsuitData.lawsuitImgs, "lawsuit");
						}

					})

					scope.showImgs = function(obj, className) {
						if(null != obj && obj.length > 0) {
							if("lawsuit" == className) {
								scope.lawsuitImgList = obj;
							} else if("archive" == className) {
								scope.archiveImgList = obj;
							} else if("enquiry" == className) {
								scope.enquiryImgList = obj;
							}
							$("." + className + "UpdImg").show();
						}
					}
				}
			};
		});

		app.directive("allocationFundHuarongIndivDetail", function($http, route, parent, $timeout) {
			return {
				restrict: "E",
				templateUrl: '/template/sl/risk/allocationFundHuarongIndivDetail.html',
				transclude: true,
				link: function(scope) {
					scope.obj = new Object();
					$timeout(function() {
						$("textarea").txtaAutoHeight();
					});
					var orderNo = route.getParams().orderNo;
					var fundId = parent.userDto.fundId;
					scope.fundId = fundId;
					if(fundId != 31) {
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
							url: 'credit/order/base/v/selectDetailByOrderNo',
							data: {
								"orderNo": orderNo
							}
						}).success(function(data) {
							//初始化页面
							scope.cityCode = data.data.cityCode;
							scope.productCode = data.data.productCode;
							scope.relationOrderNo = data.data.relationOrderNo;
							if(scope.relationOrderNo != null && scope.relationOrderNo != '') {
								$http({
									method: 'POST',
									url: 'credit/order/base/v/selectDetailByOrderNo',
									data: {
										"orderNo": scope.relationOrderNo
									}
								}).success(function(data) {
									//初始化页面
									scope.slProductCode = data.data.productCode;
								})
							}
						})
					}
				}
			};
		});
	};
});