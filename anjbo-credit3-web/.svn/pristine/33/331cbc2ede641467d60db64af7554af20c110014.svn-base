"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

var Auditor = function () {
	//类构造方法
	function Auditor() {
		_classCallCheck(this, Auditor);
		this.auditing=getQueryParameter("auditing");//是否审批转交,true表示是
		if(!this.auditing){//不是审批转交
			this.city = sessionStorage.getItem("city");
			this.level = getQueryParameter("level");
			this.type = getQueryParameter("type");
			var auditorStr = sessionStorage.getItem("auditors");
			if(auditorStr){
				this.auditor = JSON.parse(auditorStr)[this.level-1];
			}
		}else{
			this.id=getQueryParameter("id");
			this.level=getQueryParameter("level");
			this.uid=getQueryParameter("uid");
		}
	}
	//初始化


	_createClass(Auditor, [{
		key: "initFunc",
		value: function initFunc() {
			this.requestFunc();
			this.clickFunc();
		}
	}, {
		key: "searchFunc",
		value: function searchFunc(userName) {
			this.requestFunc(userName);
		}
		//数据请求

	}, {
		key: "requestFunc",
		value: function requestFunc(userName) {
			var _this = this;
			var json_ = {};
			if(this.auditing){
				json_ = {
						id : this.id,
						level : this.level,
						uid : this.uid
				};
			}else{
				json_ = {
						city: this.city,
						level: this.level,
						type: this.type
				};
			}
			if ((this.auditing && this.level && this.uid && this.id)||(this.city && this.level && this.type)) {
				$.ajax({
					type: "POST",
					contentType: 'application/json',
					url: "/credit/element/audit/base/pickPerson",
					data: JSON.stringify(json_),
					success: function success(data) {
						if (data.code == 'SUCCESS') {
							_this.KVCFunc(data.data, userName);
						} else {
							alertFunc(data.msg);
						}
					}
				});
			}
		}
		//数据验证 筛选

	}, {
		key: "KVCFunc",
		value: function KVCFunc(data, userName) {
			var userArray = [];
			if (data.length) {
				for (var i = 0; i < data.length; i++) {
					if (!userName || data[i].name.indexOf(userName) > -1) {
						if(data[i].uid == this.uid){
							continue;
						}
						var obj = {
							uid: data[i].uid,
							name: data[i].name
						};
						userArray.push(obj);
					}
				}
			}
			$("div.contentPart").html("");
			if (userArray.length) {
				for (var _i = 0; _i < userArray.length; _i++) {
					this.appendUser(userArray[_i]);
				}
			}else{
				toast("没有符合的审批人!");
			}
		}
		//添加审批人DOM

	}, {
		key: "appendUser",
		value: function appendUser(data) {
			var clz = "";
			if(this.auditor&&this.auditor.uid==data.uid){
				clz = " active";
			}
			var temp = "\n\t\t<div class=\"user\">\n\t\t<div class=\"fl\" data-uid=\"" + data.uid + "\">" + data.name + "</div>\n\t\t<div class=\"fr chioce"+clz+"\"></div>\n\t\t<div class=\"clear\"></div>\n\t\t</div>";
			$("div.contentPart").append(temp);
		}
		//页面点击选择审批人

	}, {
		key: "clickFunc",
		value: function clickFunc() {
			var self = this;
			$('div.contentPart').on('click', '.user', function () {
				var current = $(this);
				var uid = current.find('div.fl').data("uid");
				sessionStorage.setItem("name",current.find('div.fl').html());
				if(self.auditor && self.auditor.uid == uid){
					window.history.back();
					return;
				}
				current.find('div.chioce').toggleClass('active');
				if(self.auditing){//转交审批人
					alertWithTwoBtnFunc("您确定要转交此次审批吗?",function(ok){
						if(ok){
							window.location.href="deliver_option.html?auditId="+self.id+"&toUid="+ uid +"&uid="+self.uid;
						}else{
							if(current.find('div.chioce').hasClass("active")){
								current.find('div.chioce').toggleClass('active');
							}
						}
					});
					return;
				}
				//选择审批人后退回
				var name = current.find('div.fl').html();
				var level = self.level;
				var json_ = {
					level: level,
					uid: uid,
					username: name,
					type: 1 //类型1表示审批人

					//审批人存数据
				};var auditors = sessionStorage.getItem("auditors");
				if (!auditors) {
					auditors = [];
				} else {
					auditors = JSON.parse(auditors);
				}
				//审批人重复判断
				var flag = true;
				for (var i = 0; i < auditors.length; i++) {
					if (auditors[i] && auditors[i].uid == uid) {
						flag = false;
					}
				}
				if (flag) {
					auditors[level - 1] = json_;
				}else{
					toast("您选择了重复的审批人,请另选一个审批人!");
					if(current.find('div.chioce').hasClass("active")){
						current.find('div.chioce').toggleClass('active');
					}
					return;
				}
				sessionStorage.setItem('auditors', JSON.stringify(auditors));
				window.name = "";
				sessionStorage.setItem('ifJump', 1);
                 //  window.history.back();
				 document.location.href = document.referrer ;
			});
			//点击搜索
			$("#search").click(function () {
				self.searchFunc($("#keyword").val());
			});
			//键盘点击搜索
			$("#keyword").keyup(function(event){
				if(event.keyCode == 13){
					self.searchFunc($("#keyword").val());
				}
			});
			
			//输入框输入触发搜索
			var flag = false;
			$('#keyword').on('compositionstart',function(){
	            flag = false;
	        })
	        $('#keyword').on('compositionend',function(){
	            flag = true;
	        })
	        $('#keyword').on('input',function(){
	            var _this = this;
	            setTimeout(function(){
	                if(flag){
	                	self.searchFunc($("#keyword").val());
	                }
	            },0)
	        })
	        //页面取消点击事件
	        $('#cancel').click(function(){
	        	window.history.back();
	        });
			//输入框清空点击事件
			$("#delete").click(function(){
				$('#keyword').val("");
				self.searchFunc($("#keyword").val());
			});
		}
	}]);

	return Auditor;
}();

$(function () {
	var auditor = new Auditor();
	auditor.initFunc();
});

