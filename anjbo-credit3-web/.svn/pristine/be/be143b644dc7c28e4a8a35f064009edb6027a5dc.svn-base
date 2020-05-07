var ifJump = sessionStorage.getItem("ifJump");
var scrollTopN=sessionStorage.getItem("scrollTop");
$(window).scroll(function() {
   var ss=$(window).scrollTop();
	 sessionStorage.setItem("scrollTop",ss);
});
  
if(ifJump==1){
	 sessionStorage.setItem("ifJump",0);
 
	  $(window).scrollTop(scrollTopN);
}

var idddd=getQueryParameter("id");
if(idddd){
	document.title="修改借要件审批信息"
}

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var BorrowDocDet = function () {
	//类构造方法
	function BorrowDocDet() {
		_classCallCheck(this, BorrowDocDet);
		this.id = getQueryParameter("id");
		this.orderNo = getQueryParameter("orderNo");
        this.auditType = getQueryParameter("auditType");
		this.emplid = getQueryParameter("emplid");
		this.deptid = getQueryParameter("deptid");
		this.uid = getQueryParameter("uid");
		this.deviceId = getQueryParameter("deviceId");
		this.userid = getUserid();
		this.city = "";
		this.type = "";
	}
	//初始化


	_createClass(BorrowDocDet, [{
		key: "initFunc",
		value: function initFunc() {
			var detStore = sessionStorage.getItem("borrowDocDetail"); //请求数据缓存
			if (!detStore) {
				this.requestFunc(this.orderNo);
				
			} else {
				this.KVCFunc(JSON.parse(detStore));
				 
			}
			this.clickFunc();
			this.initTimePicker();
			this.vericat();
		}
		//数据请求

	}, {
		key: "requestFunc",
		value: function requestFunc(orderNo) {
			var _this = this;

			var self = this;
			var iddd=getQueryParameter("id");
			$.ajax({
				type: "POST",
				contentType: 'application/x-www-form-urlencoded',
				url: "/credit/element/dingtalk/applayPage",
				data: {
					id:self.id,
					userid: self.userid ? self.userid : "",
					orderNo: self.orderNo,
					deviceId: self.deviceId,
					uid: self.uid,
                    auditType:self.auditType,
					id:iddd
				},
				success: function success(data) {
					if (data.code == 'SUCCESS') {
						sessionStorage.setItem("borrowDocDetail", JSON.stringify(data.data));
						if(data.data.elementIds){
								var arr=data.data.elementIds.split(",");
								var arra=[];
								for(var iii=0;iii<arr.length;iii++){
										arra.push(parseInt(arr[iii]));
								}
								sessionStorage.setItem("docChoosenIds", JSON.stringify(arra));
						}
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
			if(data.editExtend){
				document.title="修改延长借要件审批信息";
				$("#extenreson").text("延长要件借用时间的原因");
				setTimeout(function(){
					    
							
				      $("#riskElements").off("click");
					  $("#receivableElements").off("click");
					  $("#auditors").off("click");
					//  $(".userInfo.add").remove();
					$(".copyDel").hide();
				},1000);
			}
			$("#customerName").html(data.customerName);
			this.customerName = data.customerName; //客户名称
			this.city = data.city; //城市
			this.type = data.type; //审批类型:1借要件普通审批2借要件财务审批3借公章
			this.orderNo = data.orderNo; //订单编号
			//时间和原因回显
			var reason = sessionStorage.getItem("reason");
			var startTime = sessionStorage.getItem("startTime");
			var endTime = sessionStorage.getItem("endTime");
			$("#startTime").val(startTime);
			$("#endTime").val(endTime);
			if (reason) {
				$("#reason").val(reason);
			}
			if (startTime && endTime) {
				this.calculateDay();
			}
			//封装要件数据
			var receivableArray = [];
			if (data.receivableElement.length > 0) {
				//回款要件
				for (var i = 0; i < data.receivableElement.length; i++) {
					if(data.receivableElement[i].status==7)continue;//取消的不显示
					receivableArray.push(this.convertEle(data.receivableElement[i]));
				}
			}
			var riskArray = [];
			if (data.riskElement.length > 0) {
				//风控要件
				for (var _i = 0; _i < data.riskElement.length; _i++) {
					if(data.riskElement[_i].status==7)continue;//取消的不显示
					 
					riskArray.push(this.convertEle(data.riskElement[_i]));
				}
			}
			//封装审批人数据
			var auditorArray = [];
			if (data.auditors.length > 0) {
				var jsonStr = sessionStorage.getItem("auditors");
				var auditors = [];
				if (jsonStr) {
					auditors = JSON.parse(jsonStr);
				}
				for (var _i2 = 0; _i2 < data.auditors.length; _i2++) {
					var obj = {
						level: data.auditors[_i2].level,
						describ: data.auditors[_i2].describ ? data.auditors[_i2].describ : "",
						uid: auditors[_i2] ? auditors[_i2].uid : "",
						username: auditors[_i2] ? auditors[_i2].username : ""
					};
					auditorArray.push(obj);
					if(!auditors.length){
						obj.uid = data.auditors[_i2].uid;
						obj.username = data.auditors[_i2].name;
					}					
				}
				if(!auditors.length){
					sessionStorage.setItem("auditors",JSON.stringify(auditorArray));
				}				
			}
			this.levelCount = auditorArray.length;
			for (var _i3 = 0; _i3 < auditorArray.length; _i3++) {
				//迭代审批人
				this.appendAuditor(auditorArray[_i3]);
			}
			//设置抄送人
			var copyToStore = getCopyToFromStore();
			if (!copyToStore.length) {
				copyToStore = data.copyTo;
				storeCopyTo(copyToStore);
			}
			if (this.emplid||this.deptid) {
				appendCopyTo(this.emplid, this.deptid, this.addCopyToArray);
			} else {
				this.addCopyToArray(copyToStore);
			}
			if (receivableArray.length > 0) {//回款要件迭代
				
				$("#receivableElements").append("<div>请选择需要借用的回款要件</div>");
				for (var _i4 = 0; _i4 < receivableArray.length; _i4++) {
					
					if (receivableArray[_i4].radio) {
						this.appendRadioEle(receivableArray[_i4], 2);
					} else {
						this.appendNoRadioEle(receivableArray[_i4], 2);
					}
				}
			}
			if (riskArray.length > 0) {//风控要件迭代
				
				$("#riskElements").append("<div>请选择需要借用的风控要件</div>");
				for (var _i5 = 0; _i5 < riskArray.length; _i5++) {
					
					if (riskArray[_i5].radio) {
						this.appendRadioEle(riskArray[_i5], 1);
					} else {
						this.appendNoRadioEle(riskArray[_i5], 1);
					}
				}
			}
		}
		//借用时长计算

	}, {
		key: "calculateDay",
		value: function calculateDay() {
			var begin = $("#startTime").val();
			var end = $("#endTime").val();
			sessionStorage.setItem("startTime", begin);
			if (begin && end) {
					var ret = subtractTime1(begin, end);
					if(ret){
						$("#day").html(ret);
					}else{  
						$("#endTime").val('');
						$("#day").html('根据选择的借用时间自动计算');
					}
					sessionStorage.setItem("endTime", $("#endTime").val());
			}
			this.vericat();
		}
		//初始化时间控件

	}, {
		key: "initTimePicker",
		value: function initTimePicker() {
			var self = this;
			//借用时长计算
			function bindCalculateDay() {
				setTimeout(function () {
					$("div.gearDatetime").on("touchstart",function(event){
						event.preventDefault();
					});
					$('div.gearDatetime').find('div.lcalendar_finish').on('touchstart', function () {
						self.calculateDay();
					});
				}, 100);
			}

			$("#startTime").focus(function () {
				bindCalculateDay();
				document.activeElement.blur();
			});

			$("#endTime").focus(function () {
				bindCalculateDay();
				document.activeElement.blur();
			});

			var startTime = new lCalendar();
			var endTime = new lCalendar();
			startTime.init({
				'trigger': '#startTime',
				'type': 'datetime'
			});

			endTime.init({
				'trigger': '#endTime',
				'type': 'datetime'
			});
		}
		//要件对象封装

	}, {
		key: "convertEle",
		value: function convertEle(data, type) {
			var active = false;
			var jsonStr = sessionStorage.getItem("docChoosenIds");
			if (jsonStr) {
				var docChoosenIds = JSON.parse(jsonStr);
				if (docChoosenIds && docChoosenIds.indexOf(parseInt(data.value)) > -1) {
					active = true;
				}
			}
			var ss='';
			var zh=false;
			if(data.status==4||data.status==8){
				ss='已借出';
				zh=true;
			}else if(data.status==5){
				ss='借用审批中';
				zh=true; 
			}else if(data.status==9){
				zh=true; 
				ss='<b>超时未还</b>';
			} else if(data.status==6){
				ss='已修改';
			} 
			var obj = {
				title: data.title, //标题
				id: data.value, //id
				radio: false, //是否单选框
				valueList: [],
				active: active ? "active" : "",
				zh : zh,
				status:ss
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
		//迭代抄送人

	}, {
		key: "addCopyToArray",
		value: function addCopyToArray(data) {
			data.push({}); //抄送人添加按钮
			for (var i = 0; i < data.length; i++) {
				//迭代抄送人
				data[i].colorClz = data[i].name ? "img_bg_color_" + data[i].name.charCodeAt(0) % 3 : "img_bg_color_1";
				if (data[i].name) {
					data[i].headName = data[i].name.substr(data[i].name.length - 2, 2);
				}
				var delTemp = "";
				if(!data[i].defaultCopy){
					delTemp = '<span class="copyDel" data-index="'+i+'"><i class="aui-iconfont aui-icon-close"></i></span>';
				}
				if (data[i].uid) {
					var temp1 = '<div class="aui-col-xs-2 userInfo"><div class="head_img_container_div">' + '<span class="heafImgContainer ' + data[i].colorClz + ' head_no_img_span">'+ delTemp + '<font class="no_img" style="margin-right:0">' + data[i].headName + '</font></span>' + '</div><p class="copyuid" style="display:none;" data-uid="' + data[i].uid + '"></p>' + '<p class="color_999 font_055" style="padding-left: 0;">' + data[i].name + '</p></div>';
					$("div#copyUsers").append(temp1);
				} else {
					var temp2 = "<div class=\"aui-col-xs-2 userInfo add\"><div><img src=\"../img/add_person.png\" alt=\"\" /></div><p class=\"color_999 font_055\" style=\"padding-left: 0;\">\u6DFB\u52A0</p></div>";
					$("div#copyUsers").append(temp2);
				}
			}
		}
		//审批人迭代

	}, {
		key: "appendAuditor",
		value: function appendAuditor(data) {
			data.colorClz = data.username ? "img_bg_color_" + data.username.charCodeAt(0) % 3 : "img_bg_color_1";
			if (data.username) {
				data.headName = data.username.substr(data.username.length - 2, 2);
			}
			var temp1 = "<div class=\"aui-col-xs-2 userInfo\" data-level=\"" + data.level + "\"><div class=\"head_img_container_div\"><span class=\"heafImgContainer " + data.colorClz + " head_no_img_span\"><font class=\"no_img\" style=\"margin-right:0\">" + data.headName + "</font></span></div><p class=\"color_999 font_055\" style=\"padding-left: 0;\">" + data.username + "</p>";
			var temp2 = "<div class=\"aui-col-xs-2 userInfo\"  data-level=\"" + data.level + "\"><div><img src=\"../img/add_person.png\" alt=\"\" /></div>";

			if (data.uid) {
				if (data.describ) {
					temp1 += "<span style=\"padding: .15rem;\" class=\"color_0cbbd3 bg_color_e5fcff font_size_045\">" + data.describ + "</span></div>";
				} else {
					temp1 += "</div>";
				}
				$("div#auditors").append(temp1);
			} else {
				if (data.describ) {
					temp2 += "<span style=\"padding: .15rem;\" class=\"color_0cbbd3 bg_color_e5fcff font_size_045\">" + data.describ + "</span></div>";
				} else {
					temp2 += "<p class=\"color_999 font_055\" style=\"padding-left: 0;\">\u6DFB\u52A0</p></div>";
				}
				$("div#auditors").append(temp2);
			}
		}
		//非单选要件迭代,type为1时风控要件,type为2时回款要件

	}, {
		key: "appendNoRadioEle",
		value: function appendNoRadioEle(data, type) {
			var temp = "<div class=\"cell\" data-id=\"" + data.id + "\"><span style=\"top: 3.2rem;\" class=\"" + data.active + "\"></span><div><p><span></span>" + data.title + "<b class='status'>"+data.status+"</b></p>";
			if(data.zh){
				temp = "<div class=\"cell dis\"><span style=\"top: 3.2rem;\"></span><div><p><span></span>" + data.title + "<b class='status'>"+data.status+"</b></p>";
			}
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
			var temp = "<div class=\"cell\" data-id=\"" + data.id + "\"><span style=\"top: 1.3rem;\" class=\"" + data.active + "\"></span><div><p>" + data.title + "<b class='status'>"+data.status+"</b></p></div></div>";
			if(data.zh){
				temp = "<div class=\"cell dis\"><span style=\"top: 1.3rem;\"></span><div><p>" + data.title + "<b class='status'>"+data.status+"</b></p></div></div>";
			}
			if (type == 1) {
				$("#riskElements").append(temp);
			}
			if (type == 2) {
				$("#receivableElements").append(temp);
			}
		}
		//页面点击事件

	}, {
		key: "clickFunc",
		value: function clickFunc() {
			var self = this;
			//删除抄送人按钮
			$("#copyUsers").on("click","span.copyDel",function(){
				var index = $(this).data("index");
				var copyTo = getCopyToFromStore();
				copyTo.splice(index,1);
				storeCopyTo(copyTo);
				$("#copyUsers").html("");
				self.addCopyToArray(copyTo);
			});
			//要件选中事件
			$("#riskElements,#receivableElements").on('click', 'div.cell', function () {
				var current = $(this);
				var id = current.data("id");
				if(id){
					current.find('span:first').toggleClass('active');
					var jsonStr = sessionStorage.getItem("docChoosenIds");
					var docChoosenIds = [];
					if (jsonStr) {
						docChoosenIds = JSON.parse(jsonStr);
					}
					if (current.find('span:first').hasClass('active')) {
						docChoosenIds.push(id);
					} else {
						for (var i = docChoosenIds.length - 1; i >= 0; i--) {
							if (docChoosenIds[i] == id) {
								docChoosenIds.splice(i, 1);
							}
						}
					}
					sessionStorage.setItem("docChoosenIds", JSON.stringify(docChoosenIds));
					self.vericat();
				}
			});
			//添加审批人事件
			$("#auditors").on("click", "div.userInfo", function () {
				var level = $(this).data("level");
				sessionStorage.setItem("city", self.city);
				window.location.href = "/elementH5/template/choice_approval_person.html?type=" + self.type + "&level=" + level;
			});
			$("#textRea").on("input", "textarea", function () {//提取要件的原因
				 $(this).val( $(this).val().replace(/^(.{500}).*$/,'$1'));//只能输入18位
				 self.vericat();
			});
			//添加抄送人事件
			$("#copyUsers").on("click", "div.add", function () {
				if (self.userid) {
					sessionStorage.setItem("goBack","true");
					var locat = location.href;
					if (locat.indexOf("&emplid") > -1) {
						locat = locat.substr(0, locat.indexOf("&emplid"));
					}
					if(locat.indexOf("&deptid") > -1){
						locat = locat.substr(0, locat.indexOf("&deptid"));
					}
					var uri = encodeURIComponent(locat);
					window.location.href = "http://edu.anjbo.com/api/dingtalk/picker.aspx?appid=2&multiple=1&redirect_uri=" + uri;
				} else {
					try{
						self.chooseAppPerson();
					}catch(e){
						alertFunc(e);
					}
				}
			});
			//提交审批按钮
			$("div.btnContainer button").click(function () {
				var btn = $(this);
				btn.prop("disabled",true);//防止二次提交
				var json_ = self.submitData();
				$.ajax({
					type: "POST",
					contentType: 'application/x-www-form-urlencoded',
					url: "/credit/element/dingtalk/addAudit",
					data: json_,
					beforeSend:function(){
						$("#loadingPart").show();
					},
					success: function success(data) {
						if (data.code == 'SUCCESS') {
							clearStore(); //清缓存
							window.location.href = "/elementH5/template/submit_doc_success.html";
						} else {
							btn.prop("disabled",false);
							btn.addClass("bg_color_0cbbd3");
							alertFunc(data.msg);
							location.reload();
						}
					},
					complete:function(){
						$("#loadingPart").hide();
					},
					error:function(arg){
						btn.prop("disabled",false);
						btn.addClass("bg_color_0cbbd3");
						alertFunc(arg);
					}
				});
			});
		}

	},{
		key : "vericat",//参数提交校验控制提交按钮
		value : function vericat(){
			var data = this.submitData();//bg_color_0cbbd3
			var btn = $("div.btnContainer button");
			if(data){
				var verif = verification.auditApply(data);//校验信息
				if(verif==""){//校验通过
					btn.addClass("bg_color_0cbbd3");
					btn.prop("disabled",false);
					return ;
				}else{
					//toast(verif);
				}
			}
			btn.removeClass("bg_color_0cbbd3");
			btn.prop("disabled",true);
		}
	},{
		key : "submitData",//提交审批参数封装
		value :function submitData(){
			var self = this;
			var json_ = {
				    id:self.id,
					orderNo: self.orderNo,
					type: self.type,
					uid : self.uid,
					deviceId : self.deviceId,
					customerName: self.customerName,
					beginTime: $("#startTime").val().trim(),
					endTime: $("#endTime").val().trim(),
					reason: $("#reason").val().trim()
					//选中的风控要件
				};
				var riskSpan = $("#riskElements div.cell>span.active");
				var riskElement = [];
				for (var i = 0; i < riskSpan.size(); i++) {
					riskElement.push($(riskSpan.get(i)).closest("div.cell").data("id"));
				}
				if (riskElement.length > 0) {
					json_.riskElement = JSON.stringify(riskElement);
				}
				//选中的回款要件
				var receivableSpan = $("#receivableElements div.cell>span.active");
				var receivableElement = [];
				for (var _i6 = 0; _i6 < receivableSpan.size(); _i6++) {
					receivableElement.push($(receivableSpan.get(_i6)).closest("div.cell").data("id"));
				}
				if (receivableElement.length > 0) {
					json_.receivableElement = JSON.stringify(receivableElement);
				}
				//审批人uid列表
				var auditorJson = sessionStorage.getItem("auditors");
				if (!auditorJson) {
					return "";
				} else {
					var auditorArray = JSON.parse(auditorJson);
					if (self.levelCount > auditorArray.length) {
						return "";
					}
					var auditorUidList = [];
					for (var _i7 = 0; _i7 < self.levelCount; _i7++) {
						if (!auditorArray[_i7].uid) {
							return "";
						}
						auditorUidList.push(auditorArray[_i7].uid);
					}
					json_.auditorUidListStr = JSON.stringify(auditorUidList);
				}
				//抄送人uid列表
				var copyUidDom = $(".copyuid[data-uid]");
				var copyUidList = [];
				for (var _i8 = 0; _i8 < copyUidDom.size(); _i8++) {
					copyUidList.push($(copyUidDom.get(_i8)).data("uid"));
				}
				json_.copyToUidListStr = JSON.stringify(copyUidList);
				//钉钉登录时
				if (self.userid) {
					json_.userid = self.userid;
				}
				return json_;
		}
	}, {
		//app抄送人选择(js交互)
		key: "chooseAppPerson",
		value: function chooseAppPerson() {
			var self = this;
			//判断是否是IOS
			function isIos() {
				var userAgentInfo = navigator.userAgent.toLowerCase();
				if (userAgentInfo.indexOf("iphone") > -1 || userAgentInfo.indexOf("ipad") > -1) {
					return true;
				}
				return false;
			}
			//安卓初始化处理
			function connectWebViewJavascriptBridge(callback) {
				if (window.WebViewJavascriptBridge) {
					callback(WebViewJavascriptBridge);
				} else {
					document.addEventListener('WebViewJavascriptBridgeReady', function () {
						callback(WebViewJavascriptBridge);
					}, false);
				}
			}
			//IOS初始化处理
			function setupWebViewJavascriptBridge(callback) {
				if (window.WebViewJavascriptBridge) {
					return callback(WebViewJavascriptBridge);
				}
				if (window.WVJBCallbacks) {
					return window.WVJBCallbacks.push(callback);
				}
				window.WVJBCallbacks = [callback];
				var WVJBIframe = document.createElement('iframe');
				WVJBIframe.style.display = 'none';
				WVJBIframe.src = 'https://__bridge_loaded__';
				document.documentElement.appendChild(WVJBIframe);
				setTimeout(function () {
					document.documentElement.removeChild(WVJBIframe);
				}, 0);
			}
			//回调数据处理
			function handleCallBackData(data) {
				if (data) {
					var copyArray = getCopyToFromStore();
					var obj = {};
					if (typeof data == "string") {
						data = JSON.parse(data);
					}
					obj.uid = data.id;
					obj.name = data.name;
					var flag = true;
					for (var i = 0; i < copyArray.length; i++) {
						if (copyArray[i].uid == obj.uid) {
							flag = false;
							break;
						}
					}
					if (flag) {
						copyArray.push(obj);
						storeCopyTo(copyArray);
						$("div#copyUsers").html("");
						self.addCopyToArray(copyArray);
					}
				}
			}
			if (isIos()) {
				setupWebViewJavascriptBridge(function (bridge) {
					var uniqueId = 1;
					//app回调
					bridge.registerHandler('choosePersonCallBack', function (data) {
						handleCallBackData(data);
					});
					bridge.callHandler('choosePerson'); //调用app方法
				});
			} else {
				//注册回调函数，第一次连接时调用 初始化函数
				connectWebViewJavascriptBridge(function (bridge) {
					try{
						//初始化
						bridge.init(function (message, responseCallback) {
							var data = {
									'Javascript Responds': 'Wee!'
							};
							responseCallback(data);
						});
					}catch(e){
					}
					//接收安卓发来的消息   并返回给安卓通知
					bridge.registerHandler('choosePersonCallBack', function (data) {
						if (typeof data == "string") {
							data = JSON.parse(data);
						}
						handleCallBackData(data);
					});
					bridge.callHandler('choosePerson'); //调用app方法
				});
			}
		}
		//校验相关事件

	}]);

	return BorrowDocDet;
}();

;
$(function () {
	var doc = new BorrowDocDet();
	doc.initFunc();

	var reason = $("#reason");
	reason.change(function () {
		sessionStorage.setItem("reason", reason.val());
	});

	window.name = "borrowDoc";
	setInterval(function () {
		if (window.name != "borrowDoc") {
			location.reload();
			window.name = "borrowDoc";
		}
	}, 100);
});