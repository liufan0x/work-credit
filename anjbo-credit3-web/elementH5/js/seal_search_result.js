"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var SealSearch = function () {
	// 类构造方法
	function SealSearch() {
		_classCallCheck(this, SealSearch);

		this.start = 0;
		this.pageSize = 10;
		this.load = true;
		this.type = getQueryParameter("type");
		this.userid = getUserid();
		this.keywords = [];
		var keywordJson = localStorage.getItem(this.type);
		if (keywordJson) {
			this.keywords = JSON.parse(keywordJson);
		}
	}
	// 初始化


	_createClass(SealSearch, [{
		key: "initFunc",
		value: function initFunc() {
			this.requestFunc();
			this.clickFunc();
			this.initFresh();
		}
		// 数据请求

	}, {
		key: "requestFunc",
		value: function requestFunc() {
			var self = this;
			$.ajax({
				type: "post",
				contentType: 'application/x-www-form-urlencoded',
				url: "/credit/element/dingtalk/orderList",
				data: {
					userid: self.userid,
					type: 2,
					start: self.start,
					pageSize: self.pageSize,
					keyword: self.keywords[self.keywords.length - 1]
				},
				success: function success(data) {
					if (data.code == 'SUCCESS') {
						if(data.data.length<self.pageSize){
							self.load =false;
						}
						self.KVCFunc(data.data);
					} else {
						alertFunc(data.msg);
					}
				}
			});
		}
		// 数据验证 筛选

	}, {
		key: "KVCFunc",
		value: function KVCFunc(data) {
			if (data.length < this.pageSize) {
				this.load = false;
			}
			$("input#keyword").val(this.keywords[this.keywords.length - 1]);
			// 封装数据
			var newArray = [];
			if (data.length) {
				for (var i = 0; i < data.length; i++) {
					var obj = {
						id: data[i].orderNo,
						sealDepartment: data[i].sealDepartment ? data[i].sealDepartment : '--', // 部门名称
						boxNumber: data[i].boxNo ? data[i].boxNo : '--', // 箱子编号
						creditType: data[i].creditType ? data[i].creditType : '--', // 信贷系统对应的订单类型
						borrowAmount: data[i].borrowingAmount ? data[i].borrowingAmount : '--', // 贷款金额
						borrowDay: data[i].borrowingDay ? data[i].borrowingDay : '--', // 贷款期限
						channelManagerName: data[i].channelManagerName ? data[i].channelManagerName : '--', // 渠道经理
						acceptMemberName: data[i].acceptMemberName ? data[i].acceptMemberName : '--', // 受理员
						state: data[i].state?data[i].state:"", // 要件状态
						orderStatus: '', // 订单状态
						showButton: data[i].borrowButton == 1 ? false : true,
						sealDescrib: data[i].sealDescrib?data[i].sealDescrib:"暂无公章"
					};

					switch (data[i].orderStatus) {
						case 1:
							obj.orderStatus = '未存入';
							break;
						case 2:
							obj.orderStatus = '已存入';
							break;
						case 3:
							obj.orderStatus = '已借出';
							break;
						case 4:
							obj.orderStatus = '已归还';
							break;
						case 5:
							obj.orderStatus = '超时未还';
							break;
						case 6:
							obj.orderStatus = '已退要件';
							break;

					}
					newArray.push(obj);
				}
			}

			// 遍历添加cell
			for (var _i = 0; _i < newArray.length; _i++) {
				this.appendCellFunc(newArray[_i]);
			}
			if(!$("div.contentPart div.cell").size()){
				toast("没有找到匹配的记录");
			}
		}

		// cell 遍历

	}, {
		key: "appendCellFunc",
		value: function appendCellFunc(data) {
			var temp = "\t\t\t\t\n\t\t\t\t\t<div class=\"cell\"  data-id=\"" + data.id + "\">\n\t\t\t\t\t<div>\n\t\t\t\t\t\t<span></span>\n\t\t\t\t\t\t<p>\n\t\t\t\t\t\t\t<font>" + data.sealDepartment + "</font>\n\t\t\t\t\t\t\t<font class=\"fr\">" + data.boxNumber + "</font>\n\t\t\t\t\t\t</p>\n\t\t\t\t\t</div>\n\n\t\t\t\t\t<div>\n\t\t\t\t\t\t<p>\n\t\t\t\t\t\t\t<font>\u5F53\u524D\u8981\u4EF6\u7BB1\u4E2D\u5B58\u5165\u7684\u516C\u7AE0\uFF1A</font>\n\t\t\t\t\t\t</p>\n\t\t\t\t\t</div>\n\n\t\t\t\t\t<div>\n\t\t\t\t\t\t<p>\n\t\t\t\t\t\t\t<font>" + data.sealDescrib + "</font>\n\t\t\t\t\t\t</p>\n\t\t\t\t\t</div>";

			if (data.showButton) {
				temp += "\t\t\t\t\t\n\t\t\t\t\t\t<div class=\"btn-container\">\n\t\t\t\t\t\t\t<button class=\"borrow\">\u501F</button>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\n\t\t\t\t\t\t<div class=\"doc-state\">\n\t\t\t\t\t\t\t<span class=\"blue\">" + data.orderStatus + "</span>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>";
			} else {
				temp += "\t\n\t\t\t\t\t<div class=\"doc-state\">\n\t\t\t\t\t\t<span class=\"blue\">" + data.orderStatus + "</span>\n\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t\t\t";
			}

			$('div.contentPart').append(temp);
		}
		//页面点击事件

	}, {
		key: "clickFunc",
		value: function clickFunc() {
			var self = this;
			//借按钮点击事件
			$("div.contentPart").on("click", "button.borrow", function (ev) {
				ev.stopPropagation();
				var id = $(this).closest("div.cell").data("id");
				window.location.href = "/elementH5/template/borrow_seal_detail.html?orderNo=" + id;
			});
			//cell点击事件
			$("div.contentPart").on("click", "div.cell", function () {
				var id = $(this).data("id");
				window.location.href = "/elementH5/template/seal_detail.html?orderNo=" + id;
			});
			
			function search(keyword){
				if (keyword) {
					self.keywords.push(keyword);
					localStorage.setItem(self.type, JSON.stringify(self.keywords));
					$("div.contentPart div.cell").remove();
					self.start = 0;
					self.load = true;
					self.requestFunc();
				}
			}
			//搜索按钮点击事件
			$("#search").click(function () {
				var keyword = $("input#keyword").val();
				search(keyword);
			});
			//键盘点击搜索事件
			$("#keyword").on("keydown",function(event){
				var keyword = $("input#keyword").val();
				if(event.keyCode == 13 && keyword){
					search(keyword);
				}
			});
			//取消按钮点击事件
			$("#cancel").click(function () {
				window.history.back();
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
					$("div.contentPart div.cell").remove();
					self.requestFunc(self.keywords[self.keywords.length - 1]);
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
				if (screenHight + $(document).scrollTop() >= $('div#sealSearchController').height() + 60) {
					if (self.load) {
						//缓冲加载菊花
						loading();
						setTimeout(function () {
							loading();
							self.start += self.pageSize;
							self.requestFunc();
							//alert('下拉加载回调');
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

	return SealSearch;
}();

$(function () {
	var seal = new SealSearch();
	seal.initFunc();
});