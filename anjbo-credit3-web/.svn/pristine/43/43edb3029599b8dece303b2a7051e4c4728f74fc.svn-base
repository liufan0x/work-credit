function chooseAppPerson() {
	console.log(22);
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
						//$("div#copyUsers").html("");
						//self.addCopyToArray(copyArray);
						appendCopyTo(copyArray);
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
//选择抄送人end

var lastTime=base.getLocalStorage("lastTime");
$("#lastStart").text(lastTime.startTime);
$("#startTime").val(lastTime.startTime);
$("#lastEnd").text(lastTime.endTime);
$("#lastLong").text(lastTime.borrowDay+"小时");
$("#endTime").val(sessionStorage.getItem("endTime"));
var userid = getUserid();
 

function calculateDay() {//计算时长
		var begin = lastTime.startTime;
		var end2 = lastTime.endTime;
		var end = $("#endTime").val();
		 
		if (begin && end) {
				var ret = subtractTime2(begin,end,end2);
				if(ret){
					$("#day").html(ret);
				}else{  
					$("#endTime").val('');
					$("#day").html('根据选择的借用时间自动计算');
				}
				sessionStorage.setItem("endTime", $("#endTime").val());
		}
}//计算时长end
		
//时间插件		
function initTimePicker() {
	function bindCalculateDay() {
		setTimeout(function () {
				$("div.gearDatetime").on("touchstart",function(event){
					event.preventDefault();
				});
				$('div.gearDatetime').find('div.lcalendar_finish').on('touchstart', function () {
						calculateDay();
						vericat();
				});
		}, 100);
	}
	$("#endTime").focus(function () {
		bindCalculateDay();
		document.activeElement.blur();
	});
	var endTime = new lCalendar();
	endTime.init({
			'trigger': '#endTime',
			'type': 'datetime'
	});
}		
initTimePicker();
//时间插件	end

function vericat(){//监听
	 var endTime=$("#endTime").val();
	 var reason=$("#reason").val();
	 if(endTime==""||$.trim(reason)==''){
		 return;
	 }else{
				$("#submitBtn").addClass("bg_color_0cbbd3");
				$("#submitBtn").prop("disabled",false);
	 }
	 
}
$("#textRea").on("input", "textarea", function () {//提取要件的原因
		 $(this).val( $(this).val().replace(/^(.{500}).*$/,'$1'));//只能输入18位
		 if($(this).val()!=""){
				vericat();
		 }else{
			 $("#submitBtn").removeClass("bg_color_0cbbd3");
       $("#submitBtn").prop("disabled",true);
		 }
});

//抄送人
function appendCopyTo(data) {
	 $("#copyUsers").html("");
	  data.push({});
	 
	 for (var i = 0; i < data.length; i++) { 
			data[i].colorClz = data[i].name ? "img_bg_color_" + data[i].name.charCodeAt(0) % 3 : "img_bg_color_1";
			if (data[i].name) {
				data[i].headName = data[i].name.substr(data[i].name.length - 2, 2);
			}
			var delTemp = "";
			if(!data[i].defaultCopy){
				// delTemp = '<span class="copyDel" data-index="'+i+'"><i class="aui-iconfont aui-icon-close"></i></span>';
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

function copyerHtml(){
	  var copysList=base.getSessionStorage("copyTo");		
	  if (copysList) {
	     appendCopyTo(copysList);	 
	  } else {
	  	$("#copyContainer").html("");
	  }
}
copyerHtml();
//抄送人 end

//审批人迭代
function appendAuditor(data) {
	data.colorClz = data.username ? "img_bg_color_" + data.username.charCodeAt(0) % 3 : "img_bg_color_1";
	if (data.username) {
		data.headName = data.username.substr(data.username.length - 2, 2);
	}
	var temp1 = "<div class=\"aui-col-xs-2 userInfo\" data-level=\"" + data.level + "\"><div class=\"head_img_container_div\"><span class=\"heafImgContainer " + data.colorClz + " head_no_img_span\"><font class=\"no_img\" style=\"margin-right:0\">" + data.headName + "</font></span></div><p class=\"color_999 font_055\" style=\"padding-left: 0;\">" + data.username + "</p>";
	var temp2 = "<div class=\"aui-col-xs-2 userInfo\"  data-level=\"" + data.level + "\"><div><img src=\"../img/add_person.png\"  /></div>";

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
if(base.getSessionStorage("auditors")){
	var auditList=base.getSessionStorage("auditors");
}
 
for (var _i4 = 0; _i4 < auditList.length; _i4++) {
		appendAuditor(auditList[_i4]);
	}
	
/*修改审批人事件
$("#auditors").on("click", "div.userInfo", function () {
	var level = $(this).data("level");
	var type=base.getLocalStorage("type");
	window.location.href = "/elementH5/template/choice_approval_person.html?type=" +type + "&level=" + level;
});
*/
//审批人end

//删除抄送人按钮
$("#copyUsers").on("click","span.copyDel",function(){
		var index = $(this).data("index");
		var copyTo = getCopyToFromStore();
		copyTo.splice(index,1);
		storeCopyTo(copyTo);
		copyerHtml();
});
//添加抄送人事件
$("#copyUsers").on("click", "div.add", function () {
		if (userid) {
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
				chooseAppPerson();
		}
});

//提交审批按钮
var id=getQueryParameter("id");
var uid=getQueryParameter("uid");
//var deviceId=getQueryParameter("deviceId");
//var orderNo=sessionStorage.getItem("orderNo");
var copysList=base.getSessionStorage("copyTo");
var copyId='';
copysList.forEach(function(item,index){
	 if(index==(copysList.length-1)){
		 copyId+=item.uid;
		 return;
	 }
	 copyId+=item.uid+',';
});
var auditors=base.getSessionStorage("auditors");
var auditorsId='';
auditors.forEach(function(item,index){
	 if(index==(auditors.length-1)){
		 auditorsId+=item.uid;
		 return;
	 }
	 auditorsId+=item.uid+',';
})

			$("div.btnContainer button").click(function () {
				var btn = $(this);
				btn.prop("disabled",true);//防止二次提交
				
				var json_ = {
					  uid:uid,
						id:id,
						endTime: $("#endTime").val().trim()+'',
						reason: $("#reason").val().trim()+'',
						copyToUidListStr:copyId+'',
						auditorUidListStr:auditorsId
						 
					};
				$.ajax({
					type: "POST",
					contentType: 'application/x-www-form-urlencoded',
					url: "/credit/element/dingtalk/extendBorrowingTime",
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

 