angular.module("anjboApp").controller("placeEnquiryEditCDCtrl", function($scope,$timeout,$http, $state, process, $compile, route, box) {
	
	var params = route.getParams();
	$scope.cityCode = params.cityCode;
	if(undefined!=$scope.inRelationOrderNo && null!=$scope.inRelationOrderNo){
		params.orderNo = $scope.inRelationOrderNo;
	}else if(undefined!=$scope.inRelationOrderNo2Detail && null!=$scope.inRelationOrderNo2Detail){
		params.orderNo = $scope.inRelationOrderNo2Detail;
	}
	console.debug(route.getParams().orderNo +"-(询价查档)订单号/关联订单号-"+ params.orderNo);
	$scope.orderNo = params.orderNo;
	
	//借款信息
	$http({
		method: 'POST',
		url: 'credit/order/borrow/v/query',
		data: {
			"orderNo": $scope.orderNo
		}
	}).success(function(data) {
		$scope.borrow = data.data;
		$scope.cityCode = $scope.borrow.cityCode;
		$scope.productCode = $scope.borrow.productCode;
	})
	
	//查档诉讼信息
	$http({
		method: 'POST',
		url: '/credit/risk/lawsuit/v/loadInquiryAndArchiveInit',
		data: params
	}).success(function(data) {
		if(data.data){
			$scope.enquiryList = data.data.enquiryData;
		}
		if(!$scope.enquiryList || $scope.enquiryList.length == 0) {
			$scope.enquiryList = new Array();
			addNewEnquiry();
		} else {
			$scope.showImgs($scope.enquiryList[0].enquiryImgs,"enquiry");
		}

		$scope.archiveList = data.data.archiveData;
		if(!$scope.archiveList || $scope.archiveList.length == 0) {
			$scope.archiveList = new Array();
			addNewArchive();
		} else {
			$scope.showImgs($scope.archiveList[0].archiveImgs,"archive");
		}

		if(data.data.lawsuitData) {
			getCountryListData(data.data.lawsuitData.id);
			getSzListData(data.data.lawsuitData.id);
			$scope.showImgs(data.data.lawsuitData.lawsuitImgs,"lawsuit");
		}

	})

	function addNewEnquiry() {
		var enquiry = new Object();
		enquiry.consumerloans = "0"
		enquiry.obligee = "单位";
		enquiry.range = "0";
		$scope.enquiryList.push(enquiry);
	}

	$scope.addEnquiry = function() {
		addNewEnquiry();
	}

	$scope.delEnquiry = function(index) {
		box.confirmAlert("删除询价","确定删除该询价吗？",function(){
			if($scope.enquiryList[index].enquiryId) {
				$http({
					method: 'POST',
					data: {
						"orderNo": $scope.orderNo,
						"enquiryId": $scope.enquiryList[index].enquiryId
					},
					url: "/credit/risk/enquiry/v/delete"
				}).success(function(data) {
					if(data.code == 'SUCCESS') {
						$scope.enquiryList.splice(index, 1);
					}
				});
			}else{
				$timeout(function(){
					$scope.enquiryList.splice(index, 1);
				});
			}
		});
	}
	/**
	 * 楼栋
	 */
	$scope.queryBuilding = function(enquiry) {
		var params = {
			"propertyId": enquiry.propertyId
		};
		enquiry.buildingList = null;
		enquiry.roomList=null;
		enquiry.area =null;		
		$http({
			method: 'POST',
			data: params,
			url: "/credit/risk/enquiry/v/queryBuilding"
		}).success(function(data) {					
			enquiry.buildingList = data.data;	
			if(enquiry.againQueryArea){
				var building = new Object();
				building.id = enquiry.buildingId;
				building.name = enquiry.buildingName;
				enquiry.building = building;
				$scope.queryRoom(enquiry);
			}
		});		
	}

	$scope.queryRoom = function(enquiry) {
		var params = {
			"buildingId": enquiry.building.id
		};
		enquiry.roomList=null;
		enquiry.area =null;
		$http({
			method: 'POST',
			data: params,
			url: "/credit/risk/enquiry/v/queryHouse"
		}).success(function(data) {
			enquiry.roomList = data.data;
			if(enquiry.againQueryArea){
				var room = new Object();
				room.id = enquiry.roomId;
				room.name = enquiry.roomName;
				enquiry.room = room;
				enquiry.area = enquiry.againQueryArea;
				enquiry.againQueryArea = null;
			}
		});
	}

	$scope.setArea = function(enquiry) {
		enquiry.area = enquiry.room ? enquiry.room.buildArea : null;
	}

	$scope.queryEnquiry = function(enquiry) {		
		if(enquiry.id) {
			//初始化楼栋
			enquiry.againQueryArea = enquiry.area;
			$scope.queryBuilding(enquiry);
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

		enquiry.orderNo = $scope.orderNo;
		var reg = /^[0-9]+$/;
		if(enquiry.consumerloans&&reg.test(enquiry.consumerloans)){
			enquiry.consumerloans = Number(enquiry.consumerloans);
		}
		var params;
		if($scope.borrow.productCode=="02"){
			params = {
				"area":parseFloat(enquiry.area),
				"buildingId":enquiry.building.id,
				"buildingName":enquiry.building.name,
				"consumerloans":enquiry.consumerloans,
				"isGetNetPriceTax":0,
				"obligee":enquiry.obligee,
				"orderNo":$scope.orderNo,
				"propertyId":enquiry.propertyId,
				"propertyName":enquiry.propertyName,
				"range":enquiry.range,
				"registerPrice":parseFloat(enquiry.registerPrice),
				"roomId":enquiry.room.id,
				"roomName":enquiry.room.name,
				"enquiryid":enquiry.enquiryId
			};
		}else{
			params = {
				"area":parseFloat(enquiry.area),
				"buildingId":enquiry.building.id,
				"buildingName":enquiry.building.name,
				"isGetNetPriceTax":enquiry.isGetNetPriceTax,
				"orderNo":$scope.orderNo,
				"propertyId":enquiry.propertyId,
				"propertyName":enquiry.propertyName,
				"registerPrice":parseFloat(enquiry.registerPrice),
				"roomId":enquiry.room.id,
				"roomName":enquiry.room.name,
				"enquiryid":enquiry.enquiryId
			};
		}
		box.waitAlert();
		$http({
			method: 'POST',
			data: params,
			url: "/credit/risk/enquiry/v/createEnquiry"
		}).success(function(data) {
			if("SUCCESS"==data.code){
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
		$scope.archiveList.push(archive);
	}

	$scope.addArchive = function() {
		addNewArchive();
	}

	$scope.delArchive = function(index) {
		box.confirmAlert("删除查档","确定删除该查档吗？",function(){
			if($scope.archiveList[index].archiveId) {
				$http({
					method: 'POST',
					data: {
						"orderNo": $scope.orderNo,
						"archiveId": $scope.archiveList[index].archiveId
					},
					url: "/credit/risk/archive/v/delete"
				}).success(function(data) {
					if(data.code == 'SUCCESS') {
						$scope.archiveList.splice(index, 1);
					}
				});
			}else{
				$timeout(function(){
					$scope.archiveList.splice(index, 1);
				});
			}
		});
		
	}

	$scope.queryArchive = function(archive) {

		archive.estateType = String(archive.estateType);
		if(archive.id) {
			//archive.archiveId = archive.id;
			archive.id = null;
			return false;
		}
		
		
		if(archive.estateType != null && (archive.estateType != "0" || archive.estateType != 0)) {
			$scope.yearNo = archive.estateType;
			archive.estateType = "1";
		} else {
			$scope.yearNo = "2015";
		}
		var params = {
			"estateNo": archive.estateNo,
			"estateType": archive.estateType,
			"identityNo": archive.identityNo,
			"orderNo": $scope.orderNo,
			"type": 1,
			"yearNo": $scope.yearNo,
			"archiveId":archive.archiveId,
			"productCode":$scope.productCode
		};

		if(archive.estateType != null && archive.estateType != "0") {
			archive.estateType = $scope.yearNo;
		}

		box.waitAlert("正在查询...");

		$http({
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
				//$scope.getArchiveById(archive);
			} else {
				box.boxAlert(data.msg);
			}
		}).error(function(){
			box.closeWaitAlert();
			box.boxAlert("操作异常！");
		});
	}

//	$scope.getArchiveById = function(obj){
//
//		$http({
//			url:'/credit/risk/archive/v/getArchiveById',
//			data:{'archiveId':obj.archiveId,"orderNo":$scope.orderNo,"productCode":$scope.productCode},
//			method:'POST'
//		}).success(function(data){
//			if("SUCCESS"==data.code){
//				obj.message = data.data.message;
//			} else {
//				box.boxAlert(data.msg);
//			}
//		});
//	}

	//刷新全国验证码
	$scope.refreshCountryValidateCode = function() {
		$http({
			method: 'POST',
			url: '/credit/risk/lawsuit/v/getQGAuthCode',
			data: params
		}).success(function(data) {
			$scope.countryValidateCodeSrc = data.QGAuthCode + "?" + Math.random();
			var str = $scope.countryValidateCodeSrc;
			var index = str.indexOf("captchaId=");
			if((str.indexOf("captchaId=")) != -1 && (str.indexOf("&") != -1)) {
				str = str.substring(index + ("captchaId=".length), str.indexOf("&"));
			}
			$scope.captchaId = str;
		})
	}

	//全国诉讼
	$scope.showQueryCountry = function() {
		if($scope.pname || $scope.cardNum) {} else {
			box.boxAlert("姓名和号码必须填一个");
			return false;
		}
		var queryCountry = function() {

			var params = {
				"captchaId": $scope.captchaId,
				"code": $scope.countryValidateCode,
				"cardNum": $scope.cardNum,
				"pname": $scope.pname,
				"orderNo": $scope.orderNo
			};

			$http({
				method: 'POST',
				url: '/credit/risk/lawsuit/v/getLawsuitId',
				data: params
			}).success(function(data) {
				getCountryListData(data.lawsuitId);
			})
		}
		$scope.refreshCountryValidateCode();
		var html = '<input type="text" style="width:100px;" ng-model="countryValidateCode" />&nbsp;<img ng-click="refreshCountryValidateCode()" src="{{countryValidateCodeSrc}}" ><a ng-click="refreshCountryValidateCode()">刷新</a>';
		box.editAlert($scope, "请输入查询验证码", html, queryCountry);
	}

	function getCountryListData(lawsuitId) {
		$http({
			method: 'POST',
			url: '/credit/risk/lawsuit/v/getQGListByLawsuitId',
			data: {
				"lawsuitId": lawsuitId,
				"orderNo": $scope.orderNo
			}
		}).success(function(data) {
			$scope.countryShow = true;
			$scope.countryListData = data.data;
			$scope.countryList = {
				options: {
					data: $scope.countryListData,
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
						field: 'detailId',
						align: 'center',
						valign: 'bottom'
					}, {
						title: '查看',
						field: 'operate',
						align: 'center',
						valign: 'bottom'
					}]
				}
			};
			box.closeAlert();
		})

	}

	//刷新深圳验证码
	$scope.refreshSzValidateCode = function() {
		$http({
			method: 'POST',
			url: '/credit/risk/lawsuit/v/getSZAuthCode'
		}).success(function(data) {
			$scope.szValidateCodeSrc = data.SZAuthCode + "?" + Math.random();
		})
	}

	//深圳诉讼
	$scope.showQuerySz = function() {
		if(!$scope.pname) {
			box.boxAlert("姓名必须填");
			return false;
		}
		var querySz = function() {
			var params = {
				"appliers": $scope.pname,
				"cardNum": $scope.cardNum,
				"code": $scope.szValidateCode,
				"orderNo": $scope.orderNo
			};
			$http({
				method: 'POST',
				url: '/credit/risk/lawsuit/v/searchSZ',
				data: params
			}).success(function(data) {
				getSzListData(data.data.lawsuitId);
			})
		}
		$scope.refreshSzValidateCode();
		var html = '<input type="text" style="width:100px;" ng-model="szValidateCode" />&nbsp;<img ng-click="refreshSzValidateCode()" src="{{szValidateCodeSrc}}" ><a ng-click="refreshSzValidateCode()">刷新</a>';
		box.editAlert($scope, "请输入查询验证码", html, querySz);
	}

	function getSzListData(lawsuitId) {
		$http({
			method: 'POST',
			url: '/credit/risk/lawsuit/v/getSZListByLawsuitId',
			data: {
				"lawsuitId": lawsuitId,
				"orderNo": $scope.orderNo
			}
		}).success(function(data) {
			var szListData = data.data;
			$scope.szList = {
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
			$scope.szShow = true;
			box.closeAlert();
		});

	}
	
	
	//询价查档诉讼
	//上传
	//诉讼
	$scope.lawsuitUploadImg = function(imgs) {
		imgs = String(imgs);
		var params = {
			"orderNo": $scope.orderNo,
			"imgUrl": imgs,
			"imgType":"lawsuit"
		};
		$http({
			method: 'POST',
			url: '/credit/risk/base/v/addImg',
			data: params
		}).success(function(data) {
			if("SUCCESS"==data.code){
				$scope.lawsuitImgShow(data.data);
				box.boxAlert("上传成功")
			} else{
				box.boxAlert(data.msg);
			}
		});
	}
	$scope.lawsuitImgShow = function(list){
		$(".lawsuitUpdImg").find("img").remove();
		$scope.lawsuitImgList = list;
		if(null==$scope.lawsuitImgList||$scope.lawsuitImgList.length<=0){
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
	$scope.enquiryUploadImg = function(imgs) {
		imgs = String(imgs);
		$scope.tmpObj = new Object();
		$scope.tmpObj.orderNo = $scope.orderNo;
		$scope.tmpObj.imgUrl = imgs;
		$scope.tmpObj.imgType = "enquiry";

		var params = angular.fromJson($scope.tmpObj);

		$http({
			method: 'POST',
			url: '/credit/risk/base/v/addImg',
			data: params
		}).success(function(data) {
			if("SUCCESS"==data.code){
				$scope.enquiryImgShow(data.data);
				box.boxAlert("上传成功");
			} else {
				box.boxAlert(data.msg);
			}

		})
	}

	$scope.enquiryImgShow = function(list){
		$(".enquiryUpdImg").find("img").remove();
		$scope.enquiryImgList = list;
		if(null==$scope.enquiryImgList||$scope.enquiryImgList.length<=0){
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
	$scope.archiveUploadImg = function(imgs) {
		imgs = String(imgs);
		var params = {
			"orderNo": $scope.orderNo,
			"imgUrl": imgs,
			"imgType":"archive"
		};
		$http({
			method: 'POST',
			url: '/credit/risk/base/v/addImg',
			data: params
		}).success(function(data) {
			if("SUCCESS"==data.code){
				$scope.archiveImgShow(data.data);
				box.boxAlert("上传成功");
			} else {
				box.boxAlert(data.msg);
			}
		})
	}
	$scope.archiveImgShow = function(list){
		$(".archiveUpdImg").find("img").remove();
		$scope.archiveImgList = list;
		if(null==$scope.archiveImgList||$scope.archiveImgList.length<=0){
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

	$scope.showImgs = function(obj,className){
		if(null!=obj&&obj.length>0){
			if("lawsuit"==className){
				$scope.lawsuitImgList = obj;
			} else if("archive"==className){
				$scope.archiveImgList = obj;
			} else if("enquiry"==className){
				$scope.enquiryImgList = obj;
			}
			$("."+className+"UpdImg").show();
		}
	}
	$scope.delEnquiryImgs = function(ids,imgType){
		$http({
			url:'/credit/risk/base/v/delImg',
			data:{"id":ids,"imgType":imgType,"orderNo":$scope.orderNo},
			method:'POST'
		}).success(function(data){
			if("SUCCESS"==data.code){
				if(imgType=="archive"){
					$scope.archiveImgShow(data.data);
				} else if(imgType=="enquiry"){
					$scope.enquiryImgShow(data.data);
				} else if(imgType=="lawsuit"){
					$scope.lawsuitImgShow(data.data);
				}
			}
			box.boxAlert(data.msg);
		});
	}


});

function returnDataLawsuit(url,smallUrl,name){
	var scope=angular.element('.place-Inquiry-edit').scope();
	$("#lawsuitImg").val(url);
	scope.lawsuitUploadImg(url);
}
function returnDataEnquir(url,smallUrl,name){
	var scope=angular.element('.place-Inquiry-edit').scope();
	$("#enquiryImg").val(url);
	scope.enquiryUploadImg(url);
}
function returnDataArchive(url,smallUrl,name){
	var scope=angular.element('.place-Inquiry-edit').scope();
	$("#archiveImg").val(url);
	scope.archiveUploadImg(url);
}
function deleteArchiveUrl(url){
	var scope=angular.element('.place-Inquiry-edit').scope();
	var id = "";
	angular.forEach(scope.archiveImgList,function(data,index,array){
		if(url==data.imgUrl){
			id = data.id;
			scope.archiveImgList.splice(scope.archiveImgList.indexOf(index), 1);
		}
	});
	scope.delEnquiryImgs(id,"archive");
}
function deleteLawsuitUrl(url){
	var scope=angular.element('.place-Inquiry-edit').scope();
	var id = "";
	angular.forEach(scope.lawsuitImgList,function(data,index,array){
		if(url==data.imgUrl){
			id = data.id;
			scope.lawsuitImgList.splice(scope.lawsuitImgList.indexOf(index), 1);
		}
	});
	scope.delEnquiryImgs(id,"lawsuit");
}
function deleteEnquiryUrl(url){
	var scope=angular.element('.place-Inquiry-edit').scope();
	var id = "";
	angular.forEach(scope.enquiryImgList,function(data,index,array){
		if(url==data.imgUrl){
			id = data.id;
			scope.enquiryImgList.splice(scope.enquiryImgList.indexOf(index), 1);
		}
	});
	scope.delEnquiryImgs(id,"enquiry");
}