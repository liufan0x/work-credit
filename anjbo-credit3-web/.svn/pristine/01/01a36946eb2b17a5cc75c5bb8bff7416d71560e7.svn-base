"use strict";
var base={
	 setLocalStorage:function(key, value) {
	     window.localStorage[key] = JSON.stringify(value);//将对象以字符串保存
	},
	getLocalStorage:function(key) {
		  	return JSON.parse(window.localStorage[key] || null);//将对象以字符串保存
	},
	setSessionStorage:function(key, value) {
			window.sessionStorage[key] = JSON.stringify(value);//将对象以字符串保存
	},
	getSessionStorage:function(key) {
				return JSON.parse(window.sessionStorage[key] || null);//将对象以字符串保存
	},
	textCount:function(obj){//只能输入
			   obj.value = obj.value.replace(/^(.{500}).*$/,'$1');//只能输入18位
	},
	sealCount:function(obj){//只能输入
				obj.value = obj.value.replace(/^(.{15}).*$/,'$1');//只能输入18位
	},
	callApp:function(callbackFunStr){
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
		  
		   if (isIos()) {
						setupWebViewJavascriptBridge(function (bridge) {
									bridge.callHandler(callbackFunStr); //调用app方法
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
								}catch(e){}
								bridge.callHandler(callbackFunStr); //调用app方法
						});
		   }
		
	}
}
 
						
// 从url解析请求参数
function getQueryParameter(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) {
		return unescape(r[2]);
	}
	return null;
}
//去除url中userid等参数
function urlWithoutUserid(){
	var url = location.href.replace(/(\?|&)userid=([^&]*)(&|$)/,"$1");
	url = url.replace(/(\?|&)name=([^&]*)(&|$)/,"$1");
	url = url.replace(/(\?|&)mobile=([^&]*)(&|$)/,"$1");
	url = url.replace(/(\?|&)deptid=([^&]*)(&|$)/,"$1");
	if(url.charAt(url.length-1)=="&"||url.charAt(url.length-1)=="?"){
		url = url.substr(0,url.length-1);
	}
	return url;
}
function removeOrderNo(){
	var url = location.href.replace(/(\?|&)orderNo=([^&]*)(&|$)/,"$1");
	if(url.charAt(url.length-1)=="&"||url.charAt(url.length-1)=="?"){
		url = url.substr(0,url.length-1);
	}
	history.replaceState({}, '', url);
}
//钉钉端客户访问时获取回调的用户id
function getUserid() {
	var userid = sessionStorage.getItem("userid");
	if (userid) {
	//	history.replaceState({}, '', urlWithoutUserid());
		return userid;
	}
	userid = getQueryParameter("userid");
	if (userid) {
		//history.replaceState({}, '', urlWithoutUserid());
		userid = decodeURIComponent(userid); 
		sessionStorage.setItem("userid", userid); //缓存用户id
		return userid;
	} else{
		return "";
	}
};
//获取缓存的抄送人
function getCopyToFromStore() {
	var copyTostore = [];
	var json = sessionStorage.getItem("copyTo");
	if (json) {
		copyTostore = JSON.parse(json);
	}
	return copyTostore;
}
//缓存抄送人
function storeCopyTo(obj) {
	sessionStorage.setItem("copyTo", JSON.stringify(obj));
}
//添加抄送人
function appendCopyTo(emlpid, deptid, func) {
	$.ajax({
		type: "POST",
		contentType: 'application/x-www-form-urlencoded',
		url: "/credit/element/dingtalk/getCopyUsers",
		data: {
			emlpid : emlpid,
			deptid : deptid
		},
		success: function success(data) {
			if (data.code == 'SUCCESS') {
				var arr = data.data;
				var copyArray = getCopyToFromStore();
				for (var i = arr.length - 1; i >= 0; i--) {
					for (var j = 0; j < copyArray.length; j++) {
						if (arr[i].uid == copyArray[j].uid) {
							arr.splice(i, 1);
							break;
						}
					}
				}
				copyArray = copyArray.concat(arr);
				storeCopyTo(copyArray);
				//清除钉钉审批人选择的历史
				if(sessionStorage.getItem("goBack")){
					sessionStorage.setItem("goBack","")
					history.go(-2);
				}
				if (func instanceof Function) {
					func(copyArray);
				}
			} else {
				alert(data.msg);
			}
		}
	});
}
//清除缓存
function clearStore() {
	sessionStorage.setItem("copyTo", ""); //抄送人
	sessionStorage.setItem("auditors", ""); //审批人
	sessionStorage.setItem("city", ""); //城市
	sessionStorage.setItem("docChoosenIds", ""); //要件id
	sessionStorage.setItem("sealChoosenIds", ""); //公章id
	sessionStorage.setItem("borrowDocDetail", ""); //借要件请求数据
	sessionStorage.setItem("borrowSealDetail", ""); //借公章请求数据
	sessionStorage.setItem("startTime", ""); //开始时间
	sessionStorage.setItem("endTime", ""); //结束时间
	sessionStorage.setItem("reason", ""); //借用的原因
	sessionStorage.setItem("fileToSeal", ""); //盖公章的文件
	sessionStorage.setItem("fileType", ""); //文件类型
	sessionStorage.setItem("sealFileCount", ""); //盖章文件的份数
	sessionStorage.setItem("fileImgUrl", ""); //盖章文件图片
}
//时间字符串解析
function convertDateFromString(dateString) {
	if (dateString) {
		var arr1 = dateString.split(" ");
		var sdate = arr1[0].split('-');
		var hdate = arr1[1].split(':');
		var date = new Date(sdate[0], sdate[1] - 1, sdate[2], hdate[0], hdate[1]);
		return date;
	}
}
//计算时间差(单位天)
function subtractTime(begin, end) {
		var beginDate = convertDateFromString(begin);
		var endDate = convertDateFromString(end);
		var ret = endDate.getTime() - beginDate.getTime();
		if(ret <= 0){
			toast("结束时间需大于开始时间!");
			return "根据选择的借用时间自动计算";
		}
		ret = (ret / (3600 * 1000)).toFixed(2);
		if(ret>9999){
			toast("借用时长太大,请正确选择时间!");
			return "根据选择的借用时间自动计算";
		}
		return ret = (ret+"").replace(".00","")+"小时";
}//计算时间差(单位天)
function subtractTime1(begin, end) {
		var beginDate = convertDateFromString(begin);
		var endDate = convertDateFromString(end);
		var ret = endDate.getTime() - beginDate.getTime();
		if(ret <= 0){
			toast("结束时间需大于开始时间!");
			return "";
		}
		ret = (ret / (3600 * 1000)).toFixed(2);
		if(ret>9999){
			toast("借用时长太大,请正确选择时间!");
			return "";
		}
		return ret = (ret+"").replace(".00","")+"小时";
}
//计算时间差(单位天)
function subtractTime2(begin, end,end2) {
		var beginDate = convertDateFromString(begin);
		var endDate = convertDateFromString(end);
		var endDate2 = convertDateFromString(end2);
		var ret = endDate.getTime() - beginDate.getTime();
		var ret2 = endDate.getTime() - endDate2.getTime();
		if(ret2 <= 0){
			toast("结束时间需大于上次结束时间!");
			return '';
		}
		ret = (ret / (3600 * 1000)).toFixed(2);
		if(ret>9999){
			toast("借用时长太大,请正确选择时间!");
			return '';
		}
		return ret = (ret+"").replace(".00","")+"小时";
}

//校验函数封装对象
var verification = {
	//提交审批校验(返回校验提示信息)
	auditApply: function auditApply(obj) {
		var type = obj.type;
		if (!type || type < 1 || type > 4) {
			return "审批类型不正确!";
		}
		if (!obj.beginTime || !obj.endTime) {
			return "请将借用时间填写完整!";
		}
		var begin = convertDateFromString(obj.beginTime).getTime();
		var end = convertDateFromString(obj.endTime).getTime();
		if (end <= begin) {
			return "结束时间需大于开始时间!";
		}
		if((end - begin)/(3600*1000) > 9999){
			return "借用时长过大!";
		}
		if (type == 3) {
			//借公章审批
			var publicSeal = obj.publicSeal?JSON.parse(obj.publicSeal):[];
			if (!publicSeal.length) return "请选择要借用的公章!";
			if(!obj.fileToSeal)return "请填写用印的文件名称!";
			if(!obj.fileType||obj.fileType == "请选择") return "请选择文件类别!";
			var fileImgUrl = obj.fileImgUrl ? JSON.parse(obj.fileImgUrl) : [];
			if(!fileImgUrl.length) return "请上传文件图片!";
		} else {
				//借要件审批
				var receivableElement = obj.receivableElement?JSON.parse(obj.receivableElement):[];
				var riskElement = obj.riskElement?JSON.parse(obj.riskElement):[];
				if (!receivableElement.length && !riskElement.length) return "请选择要借用的要件!";
				if(obj.reason==""){
					return "请填写提取要件原因"
				}
		}
		return ""; //通过校验
	}
};


var alertFunc = function(str ,func){
	var temp = 
		'<div id="dialog" style="position:fixed;width:100%;height:100%;background:rgba(0,0,0,.6);top:0;z-index:9;">'+
		'<div style="padding:1rem 2rem;background:#fff;border-radius:.4rem;margin:9rem 3rem 0;">'+
       	'<p style="text-align:center;">'+str+'</p><div style="margin-top:2rem;text-align:center;">'+
        '<button style="background:#3396fb;border:0;color:#fff;position:relative;z-index:10;">确定</button></div></div></div>';
	$("body").append(temp);
	var dialog = $("#dialog");

	dialog.on("touchmove",function(event){
		event.preventDefault();
	});
	dialog.find("button").on("click",function(event){
		if(typeof func == 'function'){
			func();
		}
		dialog.remove();
	});
}
var alertWithTwoBtnFunc = function(str ,func){
	var temp = 
		'<div id="dialog" style="position:fixed;width:100%;height:100%;background:rgba(0,0,0,.6);top:0;z-index:9;">'+
		'<div style="padding:1rem 2rem;background:#fff;border-radius:.4rem;margin:9rem 3rem 0;">'+
		'<p style="text-align:center;">'+str+'</p><div style="margin-top:2rem;text-align:center;">'+
		'<span style="margin-right:1.5rem;"><button class="ok" style="background:#3396fb;border:0;color:#fff;position:relative;z-index:10;">确定</button></span>'+
		'<span><button class="cancel" style="background:#3396fb;border:0;color:#fff;position:relative;z-index:10;">取消</button></span></div></div></div>';
	$("body").append(temp);
	var dialog = $("#dialog");
	
	dialog.on("touchmove",function(event){
		event.preventDefault();
	});
	dialog.find("button.ok").on("click",function(event){
		dialog.remove();
		if(typeof func == 'function'){
			func(true);
		}
	});
	dialog.find("button.cancel").on("click",function(event){
		dialog.remove();
		if(typeof func == 'function'){
			func(false);
		}
	});
}
var toast = function(str){
	$("body").append('<div class="toast">'+str+'</div>');
	var toast = $("div.toast");
	//toast.fadeOut(2000);
	setTimeout(function(){
		toast.remove();
	},2000);
}
function setTitle(pTitle) {
    document.title =pTitle;
    var $body = $('body');
    var $iframe = $("<iframe style='display:none;' src='./favicon.ico'></iframe>");
    $iframe.on('load',function() {
      setTimeout(function() {
        $iframe.off('load').remove();
      }, 0);
    }).appendTo($body);
 }



//按钮点击效果
$('button').on('touchstart',function(){
	if(!$(this).prop("disabled")){
		$(this).addClass("active");
	}
});
$('button').on('touchend',function(){
	if(!$(this).prop("disabled")){
		$(this).removeClass("active");
	}
});

