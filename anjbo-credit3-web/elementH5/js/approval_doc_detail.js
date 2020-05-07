localStorage.clear();
sessionStorage.clear();

$(function(){//延长时间
        var id = getQueryParameter("id");
        var uid = getQueryParameter("uid");
        var deviceId = getQueryParameter("deviceId");
		$("#tiaozhuanbut").click(function(){
			window.location.href="borrow_doc_time.html?id="+id+"&uid="+uid+"&deviceId="+deviceId;
		})
})
function setupWebViewJavascriptBridge(callback) {
	if (window.WebViewJavascriptBridge) {return callback(WebViewJavascriptBridge); }
	if (window.WVJBCallbacks) {return window.WVJBCallbacks.push(callback); }
	window.WVJBCallbacks = [callback];
	var WVJBIframe = document.createElement('iframe');
	WVJBIframe.style.display = 'none';
	WVJBIframe.src = 'https://__bridge_loaded__';
	document.documentElement.appendChild(WVJBIframe);
	setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0);
	setTimeout(function() { location.reload();  }, 500);
}

var dialog = new auiDialog({});
function aaF(){//撤销
	dialog.alert({
			title:"提示",
			msg:'你确定要撤销申请吗？',
			buttons:['取消','确定']
	},function(ret){
			if(ret.buttonIndex==2){
					 var aid=getQueryParameter("id");
					 $.ajax({
						type: "POST",
						contentType: 'application/json',
						dataType:'json',
						url: "/credit/element/dingtalk/cancelBorrowAudit",
						data: JSON.stringify({
							dbId: aid+''
						}),
						success: function success(data) {
							 if(data.code=="SUCCESS"){
								 	setupWebViewJavascriptBridge(function(bridge) {
								 		bridge.callHandler('confirmAgree');//调用app方法
								 	});
							 }else {
									alertFunc(data.msg);
									setTimeout(function() { location.reload();  }, 500);
							 }
						}
				 })
			}	 
			
	}) 	 
}
function bbF(){//修改
	//var id = getQueryParameter("id");
	var uid = getQueryParameter("uid");
	var deviceId = getQueryParameter("deviceId");
	var sessionJson=base.getSessionStorage("sessionJson");
	window.location.href="borrow_doc_detail.html?uid="+uid+"&deviceId="+deviceId+"&auditType="+sessionJson.type+"&orderNo="+sessionJson.orderNo+"&id="+sessionJson.id;
}


var _createClass = function () { 
	function defineProperties(target, props) { 
		for (var i = 0; i < props.length; i++) { 
			var descriptor = props[i]; 
			descriptor.enumerable = descriptor.enumerable || false; 
			descriptor.configurable = true; 
			if ("value" in descriptor) descriptor.writable = true; 
			Object.defineProperty(target, descriptor.key, descriptor); 
			} 
			} return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var AuditDetail = function () {
	//类构造方法
	function AuditDetail() {
		_classCallCheck(this, AuditDetail);

		this.id = getQueryParameter("id");
		this.orderNo = getQueryParameter("orderNo");
		this.uid = getQueryParameter("uid");
		this.deviceId = getQueryParameter("deviceId");
		
		this.msgid = getQueryParameter("msgId");
		this.userid = getUserid();
	}
	//初始化


	_createClass(AuditDetail, [{
		key: "initFunc",
		value: function initFunc() {
			this.requestFunc();
			this.clickFunc();
		}
		//数据请求

	}, {
		key: "requestFunc",
		value: function requestFunc() {
			var self = this;
			$.ajax({
				type: "POST",
				contentType: 'application/x-www-form-urlencoded',
				url: "/credit/element/dingtalk/auditDetail?"+new Date().getTime(),
				data: {
					id: self.id,
					userid: self.userid ? self.userid : "",
					uid: self.uid,
					deviceId: self.deviceId,
					orderNo: self.orderNo,
					msgid: self.msgid
				},
				success: function success(data) {
					if (data.code == 'SUCCESS') {
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
			if(data.newEndTime){
				 $(".yanchang").show();
				 $("#timetit").text("原要件借用时间");
				 $("#timetit2").text("原要件借用原因");
				 $("#timetit3").text("原审批意见");
				 $("#startTime2").text(data.beginTime);
				 $("#endTime2").text(data.newEndTime);
				 $("#borrowDay2").text(subtractTime(data.beginTime,data.newEndTime));
				 if(data.extendReason){
				 	$('#reason2').html(data.extendReason);
				 }
				  
			}
			if(data.newEndTime){
				var endend=data.newEndTime;
			}else{
				var endend=data.endTime;
			}
			if((data.auditList[0].current)&&data.state==0){//v1.01 新增撤销 修改功能
				$("#twobut").html('<span id="cancelOrder" onclick="aaF()">撤销</span><span onclick="bbF()">修改</span>');
				sessionStorage.setItem("startTime", data.beginTime);
				sessionStorage.setItem("endTime", endend);
				if(data.extendReason){
					sessionStorage.setItem("reason", data.extendReason);
				}else{
					sessionStorage.setItem("reason", data.reason);
				}
				
				if(data.type&&data.orderNo){ 
					 var sessionJson={
						 type:data.type,
						 orderNo:data.orderNo,
						 id:data.id
					 }
					 base.setSessionStorage("sessionJson",sessionJson);
				}
			}
			if((data.auditList[0].current)&&data.state==1&&!data.newEndTime){//v1.01 延长时间
			    $("#tiaozhuanbut").html('<div>延长借用时间</div>');
				
				var lastTime={
					startTime:data.beginTime,
					endTime:endend,
					borrowDay:data.borrowDay
				}
				base.setLocalStorage('lastTime',lastTime);
				if(data.type){ 
					base.setLocalStorage('type',data.type);
				}
				if(data.city){ 
					//base.setSessionStorage("city", data.city);
					sessionStorage.setItem("city", data.city);
				}
				if(data.orderNo){ 
					sessionStorage.setItem("orderNo", data.orderNo);
				}
				if(data.copysList){//抄送人
					base.setSessionStorage('copyTo',data.copysList);
				}
				if(data.auditorsList){//审批人
				    data.auditorsList.forEach(function(item){
						 item.username=item.name;
						 return item;
					})
					base.setSessionStorage('auditors',data.auditorsList);
				}
			}
			 
			
			
			if(data.id){
				if(!this.id){
					history.replaceState({}, '', location.href+"&id="+data.id);
				}	
				this.id = data.id;
			}
			if(data.title){
				setTitle(data.title.replace("审批","申请"));
			}
			//常规数据绑定
			this.uid = data.uid;
			this.level = data.level;
			var applierName = data.applierName;
			$('#applierName').html(data.applierName);
			if (applierName) {
				var headName = applierName.substr(applierName.length - 2, 2);
				var applierHead = $("#applierHead");
				applierHead.find("font").html(headName);
				applierHead.addClass("img_bg_color_" + applierName.charCodeAt(0) % 3);
			}
			$('#customerName').html(data.customerName);
			if(data.reason){
				$('#reason').html(data.reason);
			}
			$('#startTime').html(data.beginTime);
			$('#endTime').html(data.endTime);
			$('#borrowDay').html(data.borrowDay ? data.borrowDay.toFixed(2) + "小时" : "小时");
			
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
			//审批人迭代
			if(data.state==3){
				 data.auditList.push({
					 name:data.auditList[0].name,
				     current:data.auditList[0].current,
					 stateStr:"已撤销"
				 })
			}
			var flaggg=false;
			for (var _i4 = 0; _i4 < data.auditList.length; _i4++) {
				if(data.auditList[_i4].hasOwnProperty("auditLevel")){//第一级审批人已经审批 不可以修改了
					 if(data.auditList[_i4].auditLevel==1&&data.auditList[_i4].stateStr=="审批中"){
						flaggg=true;
					 }	 
				 }
				this.appendAuditor(data.auditList[_i4],"#auditors");
			}
			
			
			if(data.extendAuditList){
				 for (var _i44 = 0; _i44 < data.extendAuditList.length; _i44++) {
					 if(data.extendAuditList[_i44].hasOwnProperty("auditLevel")){//第一级审批人已经审批 不可以修改了
					 	if(data.extendAuditList[_i44].auditLevel==1&&data.extendAuditList[_i44].stateStr=="审批中"){
					 		flaggg=true;
					 	}	 
					 }
				 	  this.appendAuditor(data.extendAuditList[_i44],"#auditors3");
				 }
			}
			if(!flaggg){
				$("#twobut").remove();
			}
			
			
			//抄送人迭代
			if (data.copyList) {
				for (var _i5 = 0; _i5 < data.copyList.length; _i5++) {
					this.appendCopyTo(data.copyList[_i5]);
				}
			} else {
				$("#copyContainer").html("");
			}
			//审批按钮显示
			if (data.auditing) {
				$("#auditors2").append('<div style="padding-left:0;" class="btn-group text_center"><span style="position:relative;" class="color_0cbbd3 audit">同意</span><span style="position:relative;" class="color_0cbbd3 audit">拒绝</span><span style="position:relative;" class="color_0cbbd3 audit">转交</span></div>');
			}
			//我的审批状态
			
			 
			if (!data.myState) {
				switch (data.state) {
					case 0:
						data.myState = data.currentAuditor + "审批中";break; //审批中
					case 1:
						data.myState = "已审批通过";break; //审批已被同意
					case 2:
						data.myState = "审批未通过";break; //审批已被拒绝
					case 3:
						data.myState = "已撤销";break; //审批已撤销
					default:
						;
				}
			}
			$("#myState").html(data.myState);
			if (data.myState.indexOf('待') > -1 || data.myState.indexOf('审批中') > -1||data.myState.indexOf('已转交') > -1) {
				$("#myState").addClass('color_wait');
			} else if (data.myState.indexOf('已同意') > -1||data.myState.indexOf('审批通过') > -1) {
				$("#myState").addClass('color_succ');
			} else {
				$("#myState").addClass('color_fail');
			}
			//整个审批的状态
			switch (data.state) {
				case 0:
					break; //待审批
				case 1:
					$("div.headerPart").append('<span class="succ_icon"></span>');break; //审批通过
				case 2:
					$("div.headerPart").append('<span class="fail_icon"></span>');break; //审批拒绝
				case 3:
					$("div.headerPart").append('<span class="cx_icon"></span>');break; //审批撤销
				default:;
			}
		}
		//要件对象封装

	}, {
		key: "convertEle",
		value: function convertEle(data, type) {
			var obj = {
				title: data.title, //标题
				id: data.value, //id
				radio: false, //是否单选框
				valueList: [],
				flag : data.flag
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
			var colorClz = "";
			if(!data.flag){
				colorClz = " class='dis'";
			}
			var temp = "<div"+colorClz+"><p class=\"font_bold\"><span></span>" + data.title + "</p>";
			for (var i = 0; i < data.valueList.length; i++) {
				temp += "<p><font>" + data.valueList[i].title + "</font><font>" + data.valueList[i].value + "</font></p>";
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
			var colorClz = "";
			if(!data.flag){
				colorClz = " class='dis'";
			}
			var temp = "<div"+colorClz+"><p class=\"font_bold\"><span></span>" + data.title + "</p></div>";
			if (type == 1) {
				$("#riskElements").append(temp);
			}
			if (type == 2) {
				$("#receivableElements").append(temp);
			}
		}
		//添加审批人

	}, {
		key: "appendAuditor",
		value: function appendAuditor(data,elem) {
			 
			data.colorClz = data.name ? "img_bg_color_" + data.name.charCodeAt(0) % 3 : "img_bg_color_1";
			if (data.name) {
				data.headName = data.name.substr(data.name.length - 2, 2);
			}
			if(data.current){
				data.name = "我";
			}
			//发起审批
			var temp1 = "<div><span class=\"heafImgContainer " + data.colorClz + "\"><font class=\"no_img\" style=\"margin-right:0;top: .3rem;\">" + data.headName + "</font></span><font>" + data.name + "</font><font style=\"padding-left:.25rem\">"+ data.stateStr +"</font></div>";
			//审批中
			var temp2 = "<div><span class=\"heafImgContainer " + data.colorClz + "\"><font class=\"no_img\" style=\"margin-right:0;top: .3rem;\">" + data.headName + "</font></span><font>" + data.name + "</font><font class=\"color_wait\">"+ data.stateStr +"</font></div>";
			//已同意
			var temp3 = "<div><span class=\"heafImgContainer " + data.colorClz + "\"><font class=\"no_img\" style=\"margin-right:0;top: .3rem;\">" + data.headName + "</font></span><font>" + data.name + "</font><font class=\"color_succ\">"+ data.stateStr +"</font></div>";
			//已拒绝
			var temp4 = "<div><span class=\"heafImgContainer " + data.colorClz + "\"><font class=\"no_img\" style=\"margin-right:0;top: .3rem;\">" + data.headName + "</font></span><font>" + data.name + "</font><font class=\"color_fail\">"+ data.stateStr +"</font></div>";
			var temp = "";
			switch (data.stateStr) {
				case '发起审批':
					temp = temp1;break;
					
				case '审批中':
					temp = temp2;break;
				case '已同意':
					temp = temp3;break;
				case '已拒绝':
					temp = temp4;break;
				default:
					temp = temp2;break;
			}
			$(elem).append(temp);
			if(data.remark){
				$(elem).append('<div style="margin-top:-0.8rem;padding-right:0.8rem;margin-bottom:-0.6rem"><font style="font-size:0.6rem;">'+data.remark+'</font></div>');
			}
		}
		//添加审批人

	}, {
		key: "appendCopyTo",
		value: function appendCopyTo(data) {
			data.colorClz = data.name ? "img_bg_color_" + data.name.charCodeAt(0) % 3 : "img_bg_color_1";
			if (data.name) {
				data.headName = data.name.substr(data.name.length - 2, 2);
			}
			var temp = "<div class=\"aui-col-xs-2 userInfo\"><div><span class=\"heafImgContainer " + data.colorClz + " head_no_img_span\"><font class=\"no_img\" style=\"margin-right:0;\">" + data.headName + "</font></span></div><p class=\"color_999 font_055\" style=\"padding-left: 0;\">" + data.name + "</p></div>";
			$("#copyList").append(temp);
		}
		//页面点击事件

	}, {
		key: "clickFunc",
		value: function clickFunc() {
			var self = this;
			$("#auditors2").on("click", "span.audit", function () {
				var auditUri = "/elementH5/template/approval_option.html";
				if ($(this).html() == '同意') {
					window.location.href = auditUri + "?pass=1&id=" + self.id + "&userid=" + self.userid + "&uid=" + self.uid + "&deviceId=" + self.deviceId;
				} else if ($(this).html() == '拒绝') {
					window.location.href = auditUri + "?pass=0&id=" + self.id + "&userid=" + self.userid + "&uid=" + self.uid + "&deviceId=" + self.deviceId;
				}else if($(this).html() == '转交'){
					window.location.href = "/elementH5/template/choice_approval_person.html?auditing=true&id=" + self.id + "&uid=" + self.uid + "&level=" + self.level;
				}
			});
		}
	}]);

	return AuditDetail;
}();

$(function () {

	//$("html,body").animate({scrollTop:0}, 500);
	
	var det = new AuditDetail();
	det.initFunc();
	
	 
	window.name = "approvalDoc";
	setInterval(function () {
		if (window.name != "approvalDoc") {
			window.name = "approvalDoc";
			location.reload();
		}
	}, 100);
	 
});
//app刷新调用
// 
// if(sessionStorage.getItem("callApp")){
// 	try{
// 		setupWebViewJavascriptBridge(function(bridge) {
// 			bridge.callHandler('confirmAgree');//调用app方法
// 			sessionStorage.setItem("callApp","");
// 		});
// 	}catch(e){
// 	}
// }

