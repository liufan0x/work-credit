'use strict';
var detailUri='';

function goHref(type){
	 
	var dd=detailUri+type;
	window.location.href=dd;
}
var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var BrrowDoc = function () {
	//类构造方法
	function BrrowDoc() {
		_classCallCheck(this, BrrowDoc);

		this.userid = getUserid();
		this.start = 0;
		this.pageSize = 10;
		this.load = true;
	}
	//初始化


	_createClass(BrrowDoc, [{
		key: 'initFunc',
		value: function initFunc() {
			this.requestFunc("全部");
			this.clickFunc();
			this.initFresh();
		}
		//数据请求

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
				type: "POST",
				contentType: 'application/x-www-form-urlencoded',
				url: "/credit/element/dingtalk/orderList",
				data: {
					userid: self.userid,
					type: 1,
					orderStatus: orderStatus,
					start: self.start,
					pageSize: self.pageSize
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
		key: 'KVCFunc',
		value: function KVCFunc(data) {
			if (data.length < this.pageSize) {
				this.load = false;
			}
			//封装数据
			var newArray = [];
			if (data.length) {
				for (var i = 0; i < data.length; i++) {
					var typeMap=data[i].typeMap?data[i].typeMap.auditType:'';
					var obj = {
						orderNo: data[i].orderNo, //订单编号
						customerName: data[i].customerName ? data[i].customerName : '--', //用户姓名
						boxNumber: data[i].boxNo ? data[i].boxNo : '', //箱子编号
						creditType: data[i].creditType ? data[i].creditType : '--', //信贷系统对应的订单类型
						borrowAmount: data[i].borrowingAmount ? data[i].borrowingAmount : '--', //贷款金额
						borrowDay: data[i].borrowingDay ? data[i].borrowingDay : '--', //贷款期限
						channelManagerName: data[i].channelManagerName ? data[i].channelManagerName : '--', //渠道经理
						acceptMemberName: data[i].acceptMemberName ? data[i].acceptMemberName : '--', //受理员
						state: data[i].state?data[i].state:'', //要件状态
						orderStatus: '', //订单状态
						showButton: data[i].borrowButton == 1 ? false : true,
						stateColorClz : "blue",
						typeMap:typeMap
					};
					
					if(obj.state&&(obj.state.indexOf('已停止')>-1||obj.state.indexOf('已完结')>-1||obj.state.indexOf('已关闭')>-1)){
						obj.showButton = false;
					}
					
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
							obj.stateColorClz = "red";
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

			//遍历添加cell
			if(newArray.length>3){
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
		key: 'appendCellFunc',
		value: function appendCellFunc(data) {
			 
			var temp = '\t\t\t\t\n\t\t\t\t<div class="cell" data-typemap="'+data.typeMap+'" data-id="' + data.orderNo + '">\n\t\t\t\t\t<div>\n\t\t\t\t\t\t<span></span>\n\t\t\t\t\t\t<p>\n\t\t\t\t\t\t\t<font>' + data.customerName + '</font>\n\t\t\t\t\t\t\t<font>' + data.creditType + '</font>\n\t\t\t\t\t\t\t<font>' + data.boxNumber + '</font>\n\t\t\t\t\t\t</p>\n\t\t\t\t\t</div>\n\n\t\t\t\t\t<div>\n\t\t\t\t\t\t<p>\n\t\t\t\t\t\t\t<font>\u8D37\u6B3E\u91D1\u989D\uFF0F\u671F\u9650\uFF1A</font>\n\t\t\t\t\t\t\t<font>' + data.borrowAmount + '/' + data.borrowDay + '</font>\n\t\t\t\t\t\t</p>\n\t\t\t\t\t</div>\n\n\t\t\t\t\t<div>\n\t\t\t\t\t\t<p>\n\t\t\t\t\t\t\t<font>\u6E20\u9053\u7ECF\u7406\uFF0F\u53D7\u7406\u5458\uFF1A</font>\n\t\t\t\t\t\t\t<font>' + data.channelManagerName + '/' + data.acceptMemberName + '</font>\n\t\t\t\t\t\t</p>\n\t\t\t\t\t</div>\n\t\t\t\t\t\n\t\t\t\t\t<div>\n\t\t\t\t\t\t<p>' + data.state + '</p>\n\t\t\t\t\t</div>';

			if (data.showButton) {
				temp += '\t\t\t\t\t\n\t\t\t\t\t\t<div class="btn-container">\n\t\t\t\t\t\t\t<button class="borrow">\u501F</button>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\n\t\t\t\t\t\t<div class="doc-state">\n\t\t\t\t\t\t\t<span class="'+data.stateColorClz+'">' + data.orderStatus + '</span>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>';
			} else {
				temp += '\n\t\t\t\t\t\t\t<div class="doc-state">\n\t\t\t\t\t\t\t\t<span class="'+data.stateColorClz+'">' + data.orderStatus + '</span>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t';
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
				window.location.href = "/elementH5/template/approval_document.html?userId="+self.userid;
			});
			//借按钮点击事件
			$("div.contentPart").on("click", "button.borrow", function (ev) {
					ev.stopPropagation();
					clearStore(); //清缓存
					var id = $(this).closest("div.cell").data("id");
					detailUri = "/elementH5/template/borrow_doc_detail.html?orderNo=" + id+"&auditType=";
					var typeMap = $(this).closest("div.cell").data("typemap")+'';
					if(typeMap=='1'||typeMap=='4'){
						 goHref(typeMap);
					}else{
						  var typeMapArr=typeMap.split(',');
						  if(typeMapArr.length=2){
						  	 $(".chose-back").show();
						  }
					}
					 
					
					console.log(typeMapArr);
					
					//var borrowUri = "/elementH5/template/borrow_doc_detail.html?orderNo=" + id;
					
					
					//window.location.href = borrowUri;
			});
			//cell点击事件(跳转详情页)
			
			$("div.contentPart").on("click", "div.cell", function () {
				 var id = $(this).data("id");
				 //$(".chose-back").show();
				 var detailUri2 = "/elementH5/template/doc_detail.html?orderNo=" + id+"&auditType=";
				 window.location.href = detailUri2;
			});
			//搜索
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
					self.load = true;
					self.start = 0;
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
				location.href = "/elementH5/template/search.html?type=docSearch";
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
				if (screenHight + $(document).scrollTop() >= $('div#borrowDocController').height() + 60) {
					if (self.load) {
						//缓冲加载菊花
						loading();
						setTimeout(function () {
							loading();
							self.start += self.pageSize;
							self.requestFunc($("#filterText").val());
						}, 700);
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

	return BrrowDoc;
}();

$(function () {
	var Doc = new BrrowDoc();
	Doc.initFunc();
});