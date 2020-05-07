"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var SealDetail = function () {
	//类构造方法
	function SealDetail() {
		_classCallCheck(this, SealDetail);

		this.orderNo = getQueryParameter("orderNo");
	}
	//初始化


	_createClass(SealDetail, [{
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
			//常规数据绑定
			$("#sealDepartment").html(data.sealDepartment); //公章所属部门
			$("#boxNo").html(data.boxNo);
			//封装公章数据
			var sealArray = [];
			if (data.sealElement && data.sealElement.length > 0) {
				//公章
				for (var i = 0; i < data.sealElement.length; i++) {
					sealArray.push(this.convertEle(data.sealElement[i]));
				}
			}
		}
		//公章对象数据封装

	}, {
		key: "convertEle",
		value: function convertEle(data) {
			
			
			var ss="";
			var zh=false;
			if(data.status==5){
				ss="借用审批中";
				zh=true;
			}else if(data.status==4){
				ss="已借出";
				zh=true;
			}else if(data.status==8){
				    ss='已借出';
					zh=true;
				}
			else if(data.status==9){
				ss="<b>超时未还</b>";
				zh=true;
			}else if(data.status==7){//已取消存入 不显示
				return;
			}
			
			if(zh){
				var temp='<div class="zh">';
			}else{
				var temp='<div>';
			}
			  temp += "<div><p class=\"font_bold\"><span></span>" + data.title  + "<b class='status'>"+ss+"</b></p></div>";
			 $("#seals").append(temp);
		}
	}]);

	return SealDetail;
}();

$(function () {
	var seal = new SealDetail();
	seal.initFunc();
});