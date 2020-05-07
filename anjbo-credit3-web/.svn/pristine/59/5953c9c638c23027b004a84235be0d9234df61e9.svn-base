'use strict';

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var BrrowSeal = function () {
	// 类构造方法
	function BrrowSeal() {
		_classCallCheck(this, BrrowSeal);

		this.start = 0;
		this.pageSize = 10;
		this.load = true;
		this.userid = getUserid();
		 
	}
	// 初始化


	_createClass(BrrowSeal, [{
		key: 'initFunc',
		value: function initFunc() {
			this.requestFunc("全部");
			this.clickFunc();
			this.initFresh();
		}
		// 数据请求

	}, {
		key: 'requestFunc',
		value: function requestFunc(textInfo) {
			var _this = this;

			var self = this;
			var orderStatus = 0;
			switch (textInfo) {
				case '未存入':
					orderStatus = 1;break;
				case '已存入':
					orderStatus = 2;break;
				case '已借出':
					orderStatus = 3;break;
				case '已归还':
					orderStatus = 4;break;
				case '超时未还':
					orderStatus = 5;break;
				case '已退要件':
					orderStatus = 6;break;
				default:
					; //全部
			}
			$.ajax({
				type: "post",
				contentType: 'application/x-www-form-urlencoded',
				url: "/credit/element/dingtalk/orderList",
				data: {
					userid: self.userid,
					start: self.start,
					pageSize: self.pageSize,
					type: 2,
					orderStatus: orderStatus
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
		// 数据验证 筛选

	}, {
		key: 'KVCFunc',
		value: function KVCFunc(data) {
			if (data.length < this.pageSize) {
				this.load = false;
			}
			// 封装数据
			var newArray = [];
			if (data.length) {
				for (var i = 0; i < data.length; i++) {
					var obj = {
						id: data[i].orderNo,
						sealDepartment: data[i].sealDepartment ? data[i].sealDepartment : '--', // 部门名称
						boxNumber: data[i].boxNo ? data[i].boxNo : '', // 箱子编号
						creditType: data[i].creditType ? data[i].creditType : '--', // 信贷系统对应的订单类型
						borrowAmount: data[i].borrowingAmount ? data[i].borrowingAmount : '--', // 贷款金额
						borrowDay: data[i].borrowingDay ? data[i].borrowingDay : '--', // 贷款期限
						channelManagerName: data[i].channelManagerName ? data[i].channelManagerName : '--', // 渠道经理
						acceptMemberName: data[i].acceptMemberName ? data[i].acceptMemberName : '--', // 受理员
						state: data[i].state?data[i].state:'', // 要件状态
						orderStatus: '', // 订单状态
						showButton: data[i].borrowButton == 1 ? false : true,
						sealDescrib: data[i].sealDescrib?data[i].sealDescrib:"暂无公章",
						stateColorClz : "blue"
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
							obj.stateColorClz = "red";
							break;
						case 6:
							obj.orderStatus = '已退要件';
							break;

					}
					newArray.push(obj);
				}
			}

			// 遍历添加cell
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

		// cell 遍历

	}, {
		key: 'appendCellFunc',
		value: function appendCellFunc(data) {
			var temp = '\t\t\t\t\n\t\t\t\t\t<div class="cell"  data-id="' + data.id + '">\n\t\t\t\t\t<div>\n\t\t\t\t\t\t<span></span>\n\t\t\t\t\t\t<p>\n\t\t\t\t\t\t\t<font>' + data.sealDepartment + '</font>\n\t\t\t\t\t\t\t<font class="fr">' + data.boxNumber + '</font>\n\t\t\t\t\t\t</p>\n\t\t\t\t\t</div>\n\n\t\t\t\t\t<div>\n\t\t\t\t\t\t<p>\n\t\t\t\t\t\t\t<font>\u5F53\u524D\u8981\u4EF6\u7BB1\u4E2D\u5B58\u5165\u7684\u516C\u7AE0\uFF1A</font>\n\t\t\t\t\t\t</p>\n\t\t\t\t\t</div>\n\n\t\t\t\t\t<div>\n\t\t\t\t\t\t<p>\n\t\t\t\t\t\t\t<font>' + data.sealDescrib + '</font>\n\t\t\t\t\t\t</p>\n\t\t\t\t\t</div>';

			if (data.showButton) {
				temp += '\t\t\t\t\t\n\t\t\t\t\t\t<div class="btn-container">\n\t\t\t\t\t\t\t<button class="borrow">\u501F</button>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\n\t\t\t\t\t\t<div class="doc-state">\n\t\t\t\t\t\t\t<span class="'+data.stateColorClz+'">' + data.orderStatus + '</span>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>';
			} else {
				temp += '\t\n\t\t\t\t\t<div class="doc-state">\n\t\t\t\t\t\t<span class="'+data.stateColorClz+'">' + data.orderStatus + '</span>\n\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t\t\t';
			}

			$('div.contentPart').append(temp);
		}
		//页面点击事件

	}, {
		key: 'clickFunc',
		value: function clickFunc() {
			var self = this;
			//记录点击事件
			$("#record").click(function(){
				window.location.href = "/elementH5/template/approval_seal.html?userId="+self.userid;
			});
			//借按钮点击事件
			$("div.contentPart").on("click", "button.borrow", function (ev) {
				ev.stopPropagation();
				clearStore(); //清缓存
				var id = $(this).closest("div.cell").data("id");
				var borrowUri = "/elementH5/template/borrow_seal_detail.html?orderNo=" + id+"&auditType=3";
				window.location.href = borrowUri;
			});
			//cell点击事件
			$("div.contentPart").on("click", "div.cell", function () {
				var id = $(this).data("id");
				var detailUri = "/elementH5/template/seal_detail.html?orderNo=" + id;
				window.location.href = detailUri;
			});

			//过滤搜索
			$('span#filter').on('click', function (ev) {
				ev.stopPropagation();
				$('div.popPart').fadeIn();
			});

			$('div.popPart').on('touchmove', function (ev) {
				ev.preventDefault();
			});

			$('div.popPart>div').on('click', function () {
				var textInfo = $(this).html();
				$(this).parent().fadeOut(function () {
					//在这里请求数据
					$('div.contentPart div.cell').remove();
					self.start = 0;
					self.load = true;
					self.requestFunc(textInfo);
					$('font#filterText').html(textInfo);
				});
			});

			$('div.popPart>div').on('touchstart', function () {
				$(this).css('background', '#eee');
			});

			$('div.popPart>div').on('touchend', function () {
				$(this).css('background', '#fff');
			});

			$('span#search').on('click', function () {
				location.href = "/elementH5/template/search.html?type=sealSearch";
			});
		}
		//初始化下拉刷新与上拉加载

	}, {
		key: 'initFresh',
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
					self.requestFunc($("#filterText").html());
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
				if (screenHight + $(document).scrollTop() >= $('div#borrowSealController').height() + 60) {
					if (self.load) {
						//缓冲加载菊花
						loading();
						setTimeout(function () {
							loading();
							self.start += self.pageSize;
							self.requestFunc($("#filterText").val());
							//							alert('下拉加载回调');
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

	return BrrowSeal;
}();

$(function () {
	var Seal = new BrrowSeal();
	Seal.initFunc();
});