"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var DocDetail = function () {
	//类构造方法
	function DocDetail() {
		_classCallCheck(this, DocDetail);

		this.orderNo = getQueryParameter("orderNo");
	}
	//初始化


	_createClass(DocDetail, [{
		key: "initFunc",
		value: function initFunc() {
			this.requestFunc();
		}
		//数据请求

	}, {
		key: "requestFunc",
		value: function requestFunc() {
			var _this = this;

			var self = this;
			$.ajax({
				type: "POST",
				contentType: 'application/x-www-form-urlencoded',
				url: "/credit/element/dingtalk/orderDetail",
				data: {
					orderNo: self.orderNo
				},
				success: function success(data) {
					if (data.code == 'SUCCESS') {
						_this.KVCFunc(data.data);
					} else {
						alertFunc(data.msg);
					}
				}
			});
		}
		//数据验证 筛选

	}, {
		key: "KVCFunc",
		value: function KVCFunc(data) {
			var json = {
				customerName: data.customerName ? data.customerName : "--",
				borrowingAmount: data.borrowingAmount ? data.borrowingAmount : "--",
				borrowingDay: data.borrowingDay ? data.borrowingDay : "--",
				channelManagerName: data.channelManagerName ? data.channelManagerName : "--",
				acceptMemberName: data.acceptMemberName ? data.acceptMemberName : "--",
				creditType: data.creditType ? data.creditType : "--",
				boxNo: data.boxNo ? data.boxNo : "--"
				//常规数据绑定
			};$("#customerName").html(json.customerName);
			$("#borrowInfo").html(json.borrowingAmount + "万/" + json.borrowingDay + "天");
			$("#businessInfo").html(json.channelManagerName + "/" + json.acceptMemberName);
			$("#creditType").html(json.creditType);
			$("#boxNo").html(json.boxNo);
			//封装要件数据
			var receivableArray = [];
			if (data.receivableElement && data.receivableElement.length > 0) {
				//回款要件
				for (var i = 0; i < data.receivableElement.length; i++) {
					if(data.receivableElement[i].status==7){
						 continue; 
					}
					receivableArray.push(this.convertEle(data.receivableElement[i]));
				}
			}
			var riskArray = [];
			if (data.riskElement && data.riskElement.length > 0) {
				//风控要件
				
				for (var _i = 0; _i < data.riskElement.length; _i++) {
					if(data.riskElement[_i].status==7){
						 continue; 
					}
					riskArray.push(this.convertEle(data.riskElement[_i]));
				}
			}
			if (receivableArray.length > 0) {
				//回款要件迭代
				for (var _i2 = 0; _i2 < receivableArray.length; _i2++) {
					 
					if (receivableArray[_i2].radio) {
						this.appendRadioEle(receivableArray[_i2], 2);
					} else {
						this.appendNoRadioEle(receivableArray[_i2], 2);
					}
				}
			} else {
				$("#receivableElementContainer").html("");
			}
			if (riskArray.length > 0) {
				//风控要件迭代
				for (var _i3 = 0; _i3 < riskArray.length; _i3++) {
					if (riskArray[_i3].radio) {
						this.appendRadioEle(riskArray[_i3], 1);
					} else {
						this.appendNoRadioEle(riskArray[_i3], 1);
					}
				}
			} else {
				$("#riskElementsContainer").html("");
			}
		}
		//要件对象封装

	}, {
		key: "convertEle",
		value: function convertEle(data, type) {
			var ss='';
			var zhihui=false;
 
			if(data.status){
				
				if(data.status==3){
					ss='';
				}else if(data.status==4){
				    ss='已借出';
					zhihui=true;
				}else if(data.status==8){
				    ss='已借出';
					zhihui=true;
				}else if(data.status==5){
				    ss='借用审批中';
					zhihui=true;
				}else if(data.status==6){
				    ss='<b>已修改</b>';
				}
				else if(data.status==9){
					ss='<b>超时未还</b>';
					zhihui=true;
				}
				
			}
			var obj = {
				title: data.title, //标题
				id: data.value, //id
				radio: false, //是否单选框
				valueList: [],
				status:ss,
				zhihui:zhihui,
				//returnTimeOut : data.flag?'<font class="noReturn">超时未还</font>':''
			};
			var formClass = data.formClasses;
			if (formClass.indexOf("Radio") > -1) {
				obj.radio = true;
			}
			for (var i = 0; i < data.data.length; i++) {
				var valueObj = {
					title: data.data[i].title,
					value: data.data[i].value
				};
				obj.valueList.push(valueObj);
			}
			return obj;
		}
		//非单选要件迭代,type为1时风控要件,type为2时回款要件

	}, {
		key: "appendNoRadioEle",
		value: function appendNoRadioEle(data, type) {
			if(data.zhihui){
				var temp="<div class='zh'>"
			}else{
				var temp="<div>";
			}
			
			 temp += "<p class=\"font_bold rel\"><span></span>" + data.title  +"<b class='status'>"+data.status+"</b></p>";
			for (var i = 0; i < data.valueList.length; i++) {
				temp += "<p><font class=\"color_999\">" + data.valueList[i].title + "</font><font>" + data.valueList[i].value + "</font></p>";
			}
			temp += "</div></div>";
			if (type == 1) {
				$("#riskElements").append(temp);
			}
			if (type == 2) {
				$("#receivableElements").append(temp);
			}
		}
		//单选框要件迭代,type为1时风控要件,type为2时回款要件

	}, {
		key: "appendRadioEle",
		value: function appendRadioEle(data, type) {
			if(data.zhihui){
				var temp="<div class='zh'>"
			}else{
				var temp="<div>";
			}
			temp += "<p class=\"font_bold rel\"><span></span>" + data.title  + "<b class='status'>"+data.status+"</b></p></div>";
			if (type == 1) {
				$("#riskElements").append(temp);
			}
			if (type == 2) {
				$("#receivableElements").append(temp);
			}
		}
	}]);

	return DocDetail;
}();

$(function () {
	var doc = new DocDetail();
	doc.initFunc();
});