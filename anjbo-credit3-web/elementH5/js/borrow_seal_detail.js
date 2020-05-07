 
var idddd=getQueryParameter("id");
if(idddd){
	document.title="修改借公章审批信息"
}

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var BorrowSealDet = function () {
	//类构造方法
	function BorrowSealDet() {
		_classCallCheck(this, BorrowSealDet);
    this.auditType = getQueryParameter("auditType");
		this.id = getQueryParameter("id");
		this.orderNo = getQueryParameter("orderNo");
		this.emplid = getQueryParameter("emplid");
		this.deptid = getQueryParameter("deptid");
		this.uid = getQueryParameter("uid");
		this.deviceId = getQueryParameter("deviceId");
		this.userid = getUserid();
		this.type = "";
		this.city = "";
		this.sealDepartment = "";
	}
	//初始化


	_createClass(BorrowSealDet, [{
		key: "initFunc",
		value: function initFunc() {
			var detStore = sessionStorage.getItem("borrowSealDetail"); //请求数据缓存
			if (!detStore) {
				this.requestFunc();
			} else {
				this.KVCFunc(JSON.parse(detStore));
			}
			this.clickFunc();
			this.initTimePicker();
			this.inputChange();
			this.vericat();
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
				url: "/credit/element/dingtalk/applayPage",
				data: {
					id:self.id,
					userid: self.userid ? self.userid : "",
					orderNo: self.orderNo,
					deviceId: self.deviceId,
					uid: self.uid,
					auditType:self.auditType
				},
				success: function success(data) {
					if (data.code == 'SUCCESS') {
						sessionStorage.setItem("borrowSealDetail", JSON.stringify(data.data));
						if(data.data.elementIds){
								var arr=data.data.elementIds.split(",");
								var arra=[];
								for(var iii=0;iii<arr.length;iii++){
									  arra.push(parseInt(arr[iii]));
								}
								sessionStorage.setItem("sealChoosenIds", JSON.stringify(arra));
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
			if(data.editExtend){	
				 document.title="修改延长借公章审批信息";
			}				
							
			//常规数据绑定
			$("#sealDepartment").html(data.sealDepartment); //公章所属部门
			$("#applierName").html(data.applierName); //申请人姓名
			this.orderNo = data.orderNo;
			this.type = data.type;
			this.city = data.city;
			this.sealDepartment = data.sealDepartment;
			//时间和原因等数据回显
			var reason = sessionStorage.getItem("reason");
			var fileImgUrl = sessionStorage.getItem("fileImgUrl");
			var sealFileCount = sessionStorage.getItem("sealFileCount");
			var fileToSeal = sessionStorage.getItem("fileToSeal");
			var fileType = sessionStorage.getItem("fileType");
			var startTime = sessionStorage.getItem("startTime");
			var endTime = sessionStorage.getItem("endTime");
			$("#startTime").val(startTime);
			$("#endTime").val(endTime);
			if (reason) {
				$("#reason").val(reason);
			}
			if (sealFileCount) {
				$("#sealFileCount").val(sealFileCount);
			}
			if (fileToSeal) {
				$("#fileToSeal").val(fileToSeal);
			}
			$("#uploadImg").html("请上传");
			if(fileImgUrl){
				var imgArr = JSON.parse(fileImgUrl);
				if(imgArr.length){
					$("#uploadImg").html(imgArr.length);
				}
			}
			if (fileType) {
				$("#fileType").val(fileType);
			}
			if (startTime && endTime) {
				this.calculateDay();
			}
			//封装公章数据
			var sealArray = [];
			if (data.sealElement.length > 0) {
				 
				for (var i = 0; i < data.sealElement.length; i++) {
					if(data.sealElement[i].status==7){//已取存入
					
					}
					else{
						 sealArray.push(this.convertEle(data.sealElement[i]));
					}
				}
			}
			for (var _i = 0; _i < sealArray.length; _i++) {
				this.appendSeals(sealArray[_i]);
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
					if(!auditors.length){
						obj.uid = data.auditors[_i2].uid;
						obj.username = data.auditors[_i2].name;
					}
					auditorArray.push(obj);
				}
				if(!auditors.length){
					sessionStorage.setItem("auditors",JSON.stringify(auditorArray));
				}
			}
			this.levelCount = auditorArray.length;
			//设置抄送人
			var copyToStore = getCopyToFromStore();
			if (!copyToStore.length) {
				copyToStore = data.copyTo;
				storeCopyTo(copyToStore);
			}
			if (this.emplid||this.deptid) {
				appendCopyTo(this.emplid,this.deptid, this.addCopyToArray);
			} else {
				this.addCopyToArray(copyToStore);
			}
			for (var _i3 = 0; _i3 < auditorArray.length; _i3++) {
				//迭代审批人
				this.appendAuditor(auditorArray[_i3]);
			}
		}
		//公章对象数据封装

	}, {
		key: "convertEle",
		value: function convertEle(data) {
			var active = false;
			var jsonStr = sessionStorage.getItem("sealChoosenIds");
			if (jsonStr) {
				var sealChoosenIds = JSON.parse(jsonStr);
				if (sealChoosenIds && sealChoosenIds.indexOf(parseInt(data.value)) > -1) {
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
				status:ss,
				title: data.title, //标题
				id: data.value, //id
				active: active ? "active" : "",
				zh : zh
			};
			return obj;
		}
		//添加公章到页面

	}, {
		key: "appendSeals",
		value: function appendSeals(data) {
			if(data.zh){
				var temp='<div class="cell dis">';
			}else{
				var temp='<div class="cell" data-id="'+data.id+'">';
			}
			temp += "<span style=\"top: 1.3rem;\" class='"+data.active+"'></span><div><p class='rel'>" + data.title + "<b class='status'>"+data.status+"</b></p></div></div>";
			$("#seals").append(temp);
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
					var temp1 = '<div class="aui-col-xs-2 userInfo"><div class="head_img_container_div">' + '<span class="heafImgContainer ' + data[i].colorClz + ' head_no_img_span">' + delTemp + '<font class="no_img" style="margin-right:0">' + data[i].headName + '</font></span>' + '</div><p class="copyuid" style="display:none;" data-uid="' + data[i].uid + '"></p>' + '<p class="color_999 font_055" style="padding-left: 0;">' + data[i].name + '</p></div>';
					$("div#copyUsers").append(temp1);
				} else {
					var temp2 = "<div class=\"aui-col-xs-2 userInfo add\"><div><img src=\"../img/add_person.png\" alt=\"\" /></div><p class=\"color_999 font_055\" style=\"padding-left: 0;\">\u6DFB\u52A0</p></div>";
					$("div#copyUsers").append(temp2);
				}
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
			//选中公章点击事件
			$('#seals').on('click', 'div.cell', function () {
				 
				var current = $(this);
				var id = current.data("id");
				if(id){
					current.find("span").toggleClass('active');
					var jsonStr = sessionStorage.getItem("sealChoosenIds");
					var sealChoosenIds = [];
					if (jsonStr) {
						sealChoosenIds = JSON.parse(jsonStr);
					}
					if (current.find("span").hasClass('active')) {
						sealChoosenIds.push(id);
					} else {
						for (var i = sealChoosenIds.length - 1; i >= 0; i--) {
							if (sealChoosenIds[i] == id) {
								sealChoosenIds.splice(i, 1);
							}
						}
					}
					sessionStorage.setItem("sealChoosenIds", JSON.stringify(sealChoosenIds));
					self.vericat();
				}
			});
			//添加审批人事件
			$("#auditors").on("click", "div.userInfo", function () {
				var level = $(this).data("level");
				sessionStorage.setItem("city", self.city);
				window.location.href = "/elementH5/template/choice_approval_person.html?type=" + self.type + "&level=" + level;
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
			//图片上传
			$("#uploadImg").click(function(){
				location.href = "/elementH5/template/upload_img.html";
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
							window.location.href = "/elementH5/template/submit_seal_success.html";
						} else {
							btn.prop("disabled",false);
							btn.addClass("bg_color_0cbbd3");
							alertFunc(data.msg);
							location.reload();
						}
					},
					complete:function(){
						$("#loadingPart").hide();
					}
				});
			});
		}

	},{ //输入改变事件
		key: "inputChange",
		value :function inputChange(){
			var self = this;
			var reason = $("#reason");//补充说明
			reason.on('input',function () {
				  $(this).val($(this).val().replace(/^(.{500}).*$/,'$1')) ;//只能输入18位
					sessionStorage.setItem("reason", reason.val());
			});
			var fileToSeal = $("#fileToSeal");
			fileToSeal.on('input',function () {//文件名称
					$(this).val($(this).val().replace(/^(.{50}).*$/,'$1')) ;//只能输入18位
					sessionStorage.setItem("fileToSeal", fileToSeal.val());
					self.vericat();
			});
			var sealFileCount = $("#sealFileCount");
			sealFileCount.on('input',function () {//用印份数
			    $(this).val($(this).val().replace(/^(.{15}).*$/,'$1')) ;//只能输入18位
				  sessionStorage.setItem("sealFileCount", sealFileCount.val());
			});
			var fileType = $("#fileType");
			fileType.change(function () {//文件类别
					sessionStorage.setItem("fileType", fileType.val());
					self.vericat();
			});
			
			 
			 
		}
	}, {
		//借用时长计算
		key: "calculateDay",
		value: function calculateDay() {
			var begin = $("#startTime").val();
			var end = $("#endTime").val();
			sessionStorage.setItem("startTime", begin);
			sessionStorage.setItem("endTime", end);
			if (begin && end) {
				var ret = subtractTime1(begin, end);
				if(ret){
					$("#day").html(ret);
					$("#day").removeClass("plac");
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

			$("#startTime").focus(function (event) {
				event.preventDefault();
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
		//app抄送人选择(js交互)

	}, {
		key: "submitData",//提交审批数据封装
		value: function submitData(){
			var self = this;
			 
			var json_ = {
				  id:self.id,
					orderNo: self.orderNo,
					uid : self.uid,
					deviceId : self.deviceId,
					type: self.type,
					sealDepartment: self.sealDepartment,
					beginTime: $("#startTime").val().trim(),
					endTime: $("#endTime").val().trim(),
					reason: $("#reason").val().trim(),
					sealFileCount: $("#sealFileCount").val().trim(),
					fileToSeal: $("#fileToSeal").val().trim(),
					fileType: $("#fileType").val().trim(),
					fileImgUrl : sessionStorage.getItem("fileImgUrl")
				};
				//选中的公章
				var sealIdArray = [];
				var sealsDom = $("#seals div.cell>span.active");
				for (var i = 0; i < sealsDom.size(); i++) {
					sealIdArray.push($(sealsDom.get(i)).closest("div.cell").data("id"));
				}
				json_.publicSeal = JSON.stringify(sealIdArray);
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
					for (var _i4 = 0; _i4 < self.levelCount; _i4++) {
						if (!auditorArray[_i4].uid) {
							return "";
						}
						auditorUidList.push(auditorArray[_i4].uid);
					}
					json_.auditorUidListStr = JSON.stringify(auditorUidList);
				}
				//抄送人uid列表
				var copyUidDom = $(".copyuid[data-uid]");
				var copyUidList = [];
				for (var _i5 = 0; _i5 < copyUidDom.size(); _i5++) {
					copyUidList.push($(copyUidDom.get(_i5)).data("uid"));
				}
				json_.copyToUidListStr = JSON.stringify(copyUidList);
				//模拟登陆
				if (self.userid) {
					json_.userid = self.userid;
				}
				return json_;
		}
	},{
		key : "vericat",//参数提交校验控制提交按钮
		value : function vericat(){
			var data = this.submitData();
			var btn = $("div.btnContainer button");
			if(data){
				var verif = verification.auditApply(data);//校验信息
				if(!verif){//校验通过
					btn.addClass("bg_color_0cbbd3");
					btn.prop("disabled",false);
					return ;
				}
			}
			btn.removeClass("bg_color_0cbbd3");
			btn.prop("disabled",true);
		}
	},{
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
			function connectWebViewJavascriptBridge(callback) {
				//安卓初始化处理
				if (window.WebViewJavascriptBridge) {
					callback(WebViewJavascriptBridge);
				} else {
					document.addEventListener('WebViewJavascriptBridgeReady', function () {
						callback(WebViewJavascriptBridge);
					}, false);
				}
			}
			function setupWebViewJavascriptBridge(callback) {
				//IOS初始化处理
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
				//IOS端
				setupWebViewJavascriptBridge(function (bridge) {
					var uniqueId = 1;
					//app回调
					bridge.registerHandler('choosePersonCallBack', function (data) {
						handleCallBackData(data);
					});
					bridge.callHandler('choosePerson'); //调用app方法
				});
			} else {
				//安卓端
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
						handleCallBackData(data);
					});
					bridge.callHandler('choosePerson'); //调用app方法
				});
			}
		}
	}]);

	return BorrowSealDet;
}();

$(function () {
	var sealDet = new BorrowSealDet();
	sealDet.initFunc();

	window.name = "borrowSeal";
	setInterval(function () {
		if (window.name != "borrowSeal") {
			window.name = "borrowSeal";
			location.reload();
		}
	}, 100);
});