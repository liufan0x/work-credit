"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var approvalDoc = function () {
	//类构造方法
	function approvalDoc() {
		_classCallCheck(this, approvalDoc);

		this.start = 0;
		this.pageSize = 10;
		this.load = true;
		this.userid = getUserid();
	}
	//初始化


	_createClass(approvalDoc, [{
		key: "initFunc",
		value: function initFunc() {
			this.requestFunc();
			this.clickFunc();
			this.initFresh();
		}
		//数据请求

	}, {
		key: "requestFunc",
		value: function requestFunc() {
			var self = this;
			$.ajax({
				type: "POST",
				contentType: 'application/x-www-form-urlencoded',
				url: "/credit/element/dingtalk/borrowRecord",
				data: {
					userid: self.userid,
					type: 1,
					start: self.start,
					pageSize: self.pageSize
				},
				success: function success(data) {
					if (data.code == 'SUCCESS') {
						if(data.data.length<self.pageSize){
							self.load = false;
						}
						self.KVCFunc(data.data);
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
			if (data.length < this.pageSize) {
				this.load = false;
			}
			//封装数据
			var newArray = [];
			if (data.length) {
				for (var i = 0; i < data.length; i++) {
					var obj = {
						id: data[i].id,
						beginTime: data[i].beginTime,
						createTime: data[i].createTime,
						customerName: data[i].customerName,
						endTime: data[i].endTime,
						publicSeal: data[i].publicSeal,
						receivableElement: data[i].receivableElement,
						riskElement: data[i].riskElement,
						sealDepartment: data[i].sealDepartment,
						title: data[i].title,
						applierName: data[i].applierName
						//状态(0待审批,1审批通过,2审批拒绝)
					};
					switch (data[i].state) {
						case 0:
							obj.stateStr = "待审批";
							obj.fontcolorClz = "color_wait";break;
						case 1:
							obj.stateStr = "审批通过";
							obj.fontcolorClz = "color_succ";break;
						case 2:
							obj.stateStr = "审批未通过";
							obj.fontcolorClz = "color_fail";break;
						default:
							break;
					}
					newArray.push(obj);
				}
			}
			if(newArray.length){
				$('div.contentPart').find(".notice").remove();
				for (var _i = 0; _i < newArray.length; _i++) {
					this.appendCellFunc(newArray[_i]);
				}
			}
			if(!$("div.contentPart div.cell").size()){
				$('div.contentPart').find(".notice").remove();
				$('div.contentPart').append('<div class="notice" style="text-align:center;padding-top:1.5rem">暂无记录</div>');
			}
		}

		//cell 遍历

	}, {
		key: "appendCellFunc",
		value: function appendCellFunc(data) {
			var applierName = data.applierName;
			if (applierName) {
				data.headName = applierName.substr(applierName.length - 2, 2);
			}
			data.colorClz = applierName ? "img_bg_color_" + applierName.charCodeAt(0) % 3 : "img_bg_color_1";
			//只有风控要件
			var temp1 = "\t\t\t\t\t\t\n\t\t<div class=\"cell\" data-id=\"" + data.id + "\">\n\t\t\t<div>\n\t\t\t\t<p>\n\t\t\t\t\t<font>" + data.title + "</font>\n\t\t\t\t\t<font class=\"fr\" style=\"padding-top: .1rem;\">" + data.createTime + "</font>\n\t\t\t\t</p>\n\t\t\t\t<p>\u5BA2\u6237\u59D3\u540D\uFF1A" + data.customerName + "</p>\n\t\t\t\t<p>\u7533\u8BF7\u501F\u7528\u7684\u98CE\u63A7\u8981\u4EF6\uFF1A" + data.riskElement + "</p>\n\t\t\t\t<p>\u8981\u4EF6\u501F\u7528\u65F6\u95F4\uFF1A" + data.beginTime + "\u81F3" + data.endTime + "</p>\n\t\t\t\t<p class=\""+data.fontcolorClz+"\">" + data.stateStr + "</p>\n\t\t\t\t<span class=\"heafImgContainer " + data.colorClz + "\">\n\t\t\t\t\t<font class=\"no_img\">" + data.headName + "</font>\n\t\t\t\t</span>\n\t\t\t</div>\n\t\t</div>";
			//只有回款要件
			var temp2 = "\t\t\t\t\n\t\t<div class=\"cell\" data-id=\"" + data.id + "\">\n\t\t\t<div>\n\t\t\t\t<p>\n\t\t\t\t\t<font>" + data.title + "</font>\n\t\t\t\t\t<font class=\"fr\" style=\"padding-top: .1rem;\">" + data.createTime + "</font>\n\t\t\t\t</p>\n\t\t\t\t<p>\u5BA2\u6237\u59D3\u540D\uFF1A" + data.customerName + "</p>\n\t\t\t\t<p>\u7533\u8BF7\u501F\u7528\u7684\u56DE\u6B3E\u8981\u4EF6\uFF1A" + data.receivableElement + "</p>\n\t\t\t\t<p>\u8981\u4EF6\u501F\u7528\u65F6\u95F4\uFF1A" + data.beginTime + "\u81F3" + data.endTime + "</p>\n\t\t\t\t<p class=\""+data.fontcolorClz+"\">" + data.stateStr + "</p>\n\t\t\t\t<span class=\"heafImgContainer " + data.colorClz + "\">\n\t\t\t\t\t<font class=\"no_img\">" + data.headName + "</font>\n\t\t\t\t</span>\n\t\t\t</div>\n\t\t</div>";
			//有回款要件和风控要件
			var temp3 = "\t\t\t\t\n\t\t<div class=\"cell\" data-id=\"" + data.id + "\">\n\t\t\t<div>\n\t\t\t\t<p>\n\t\t\t\t\t<font>" + data.title + "</font>\n\t\t\t\t\t<font class=\"fr\" style=\"padding-top: .1rem;\">" + data.createTime + "</font>\n\t\t\t\t</p>\n\t\t\t\t<p>\u5BA2\u6237\u59D3\u540D\uFF1A" + data.customerName + "</p>\n\t\t\t\t<p>\u7533\u8BF7\u501F\u7528\u7684\u56DE\u6B3E\u8981\u4EF6\uFF1A" + data.receivableElement + "</p>\n\t\t\t\t<p>\u8981\u4EF6\u501F\u7528\u65F6\u95F4\uFF1A" + data.beginTime + "\u81F3" + data.endTime + "</p>\n\t\t\t\t<p class=\""+data.fontcolorClz+"\">" + data.stateStr + "</p>\n\t\t\t\t<span class=\"heafImgContainer " + data.colorClz + "\">\n\t\t\t\t\t<font class=\"no_img\">" + data.headName + "</font>\n\t\t\t\t\t</span>\n\t\t\t</div>\n\t\t</div>";
			var i = 1; //只有风控要件
			if (data.receivableElement) {
				i = 2; //只有回款要件
				if (data.riskElement) {
					i = 3;
				}
			}
			switch (i) {
				case 1:
					$('div.contentPart').append(temp1);break;
				case 2:
					$('div.contentPart').append(temp2);break;
				case 3:
					$('div.contentPart').append(temp3);break;
				default:
					break;
			}
		}
		//页面点击事件

	}, {
		key: "clickFunc",
		value: function clickFunc() {
			var self = this;
			$("div.contentPart").on("click", "div.cell", function () {
				var id = $(this).data("id");
				window.location.href = "/elementH5/template/approval_doc_detail.html?id=" + id + "&userid=" + self.userid;
			});
		}

		//初始化下拉刷新与上拉加载

	}, {
		key: "initFresh",
		value: function initFresh() {
			var self = this;
			//下拉刷新
			var pullRefresh = new auiPullToRefresh({
				container: document.querySelector('.aui-refresh-content'), //下拉容器
				triggerDistance: 100 //下拉高度
			}, function (ret) {
				if (ret.status == "success") {
					self.start = 0;
					self.load = true;
					$("div.contentPart div.cell").remove();
					self.requestFunc();
					setTimeout(function () {
						pullRefresh.cancelLoading(); //刷新成功后调用此方法隐藏
						if ($("div.contentPart div.cell").size() > 0) {
							toast('刷新成功！');
						}
					}, 500);
				}
			}, 1500);
			//上拉加载部分
			function loading() {
				$('div.loadingPart').fadeToggle();
			}
			var screenHight = window.screen.height;
			window.onscroll = function () {
				if (screenHight + $(document).scrollTop() >= $('div#approDocController').height() + 60) {
					if (self.load) {
						//缓冲加载菊花
						loading();
						setTimeout(function () {
							loading();
							self.start += self.pageSize;
							self.requestFunc();
						}, 500);
					} else {
						toast("已加载完毕!");
					}
				}
			};
			$('div.loadingPart').on('touchmove', function (ev) {
				ev.preventDefault();
			});
		}
	}]);

	return approvalDoc;
}();

$(function () {
	var Doc = new approvalDoc();
	Doc.initFunc();
});