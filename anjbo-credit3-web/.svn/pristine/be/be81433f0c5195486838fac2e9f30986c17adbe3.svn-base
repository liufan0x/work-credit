"use strict";
window.name = "";
var pass = getQueryParameter("pass");
var uid = getQueryParameter("uid");
var deviceId = getQueryParameter("deviceId");
var id = getQueryParameter("id");
var userid = getUserid();
$("#auditBtn").html(pass == 1 ? "确认同意" : "确认拒绝");
var remark = $("#remark").get(0);
//键盘事件绑定,字长校验
/*
remark.onkeydown = function () {
	var value = $("#remark").val();
	if (value && value.length >= 200) {
		$(remark).val(value.substr(0, 200));
	}
};
*/
//校验审批的要件/公章是否还在箱子
if(pass == 1){
	$.ajax({
		type: "POST",
		async: false,
		contentType: 'application/x-www-form-urlencoded',
		url: "/credit/element/dingtalk/checkAuditEleState",
		data: {auditId: id},
		success : function(data){
			if(data.code == 'SUCCESS'){
				if(data.data == 1){
					alertFunc("申请借用的公章或要件已部分或全部被取走,请拒绝此次审批!",function(){
						history.replaceState({}, '', location.href.replace("pass=1","pass=0"));
						sessionStorage.setItem("defaultRemark",1);
						window.location.reload();
					});
				}else if(data.data == 2){
					alertFunc("订单已结束,请拒绝此次审批!",function(){
						history.replaceState({}, '', location.href.replace("pass=1","pass=0"));
						sessionStorage.setItem("defaultRemark",2);
						window.location.reload();
					});
				}
			}else{
				alertFunc(data.msg);
			}
		}
	});
}
var defaultRemark = sessionStorage.getItem("defaultRemark");
if(defaultRemark){
	var note = "申请借用的公章或要件已部分或全部被取走,若仍要借用,请调整借用物品,重新提交审批!";
	if(defaultRemark == 2){
		note = "订单已结束,要件或公章不能继续借出!";
	}
	sessionStorage.setItem("defaultRemark","");
	$(remark).html(note);
}
//审批按钮点击事件
$("#auditBtn").click(function () {
	var btn = $(this);
	btn.prop("disabled",true);//防止二次提交
	$.ajax({
		type: "POST",
		contentType: 'application/x-www-form-urlencoded',
		url: "/credit/element/dingtalk/audit",
		data: {
			auditId: id,
			userid: userid,
			deviceId: deviceId,
			uid: uid,
			pass: pass == 1,
			remark: $(remark).val()
		},
		beforeSend:function(){
			$("#loadingPart").show();
		},
		success: function success(data) {
			if (data.code == 'SUCCESS') {
					setupWebViewJavascriptBridge(function(bridge) {
						bridge.callHandler('confirmAgree');//调用app方法 
					});	
			} else {
				btn.prop("disabled",false);
				btn.addClass("bg_color_0cbbd3");
				alertFunc(data.msg,function(){
					window.location.reload();
				});
			}
		},
        complete:function(){
        	$("#loadingPart").hide();
		}
	});
});
function setupWebViewJavascriptBridge(callback) {
	    setTimeout(function(){ window.history.back();  }, 1000);
		if (window.WebViewJavascriptBridge) {return callback(WebViewJavascriptBridge); }
		if (window.WVJBCallbacks) {return window.WVJBCallbacks.push(callback); }
		window.WVJBCallbacks = [callback];
		var WVJBIframe = document.createElement('iframe');
		WVJBIframe.style.display = 'none';
		WVJBIframe.src = 'https://__bridge_loaded__';
		document.documentElement.appendChild(WVJBIframe);
		setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0);
		
}


