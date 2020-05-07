define(function(require, exports, module){

	exports.extend= function(app){  

		app.service('route', function($cookies) {
			var service = {};

			service.getParams = function(){
				try {
					return angular.fromJson(decodeURI($cookies.get("routeParams")));
				} catch (e) {
					return null;
				}
			}

			return service;
		});

		app.service('parent', function() {
			var service = {};

			return service;
		});

		app.service('box', function($compile) {
			var service = {};

			service.boxAlert = function(title,aa){	
				var htmlstring='<div class="lhw-alert-bg lhw-alert-bg-d"><div class="lhw-alert-box"><div class="lhw-alert-con">'+title+'</div><div class="lhw-alert-but"><button type="button" class="btn btn-primary lhw-alert-ok">确  定</button></div></div>';
        		$('body').append(htmlstring);
        		$(".lhw-alert-ok").bind("click",function(){   $(".lhw-alert-ok").parent().parent().parent(".lhw-alert-bg-d").remove(); if("function"==typeof aa){aa();}});
			}

			service.confirmAlert = function(title,content,aa){	
			   var htmlstring='<div class="lhw-alert-bg lhw-alert-bg-d"><div class="lhw-alert-box"><div class="lhw-alert-tit">'+title+'</div><div class="lhw-alert-con">'+content+'</div><div class="lhw-alert-but"><button type="button" class="btn btn-primary lhw-alert-ok" style="margin-right:80px;">确  定</button><button type="button" class="btn btn-default lhw-alert-cancel">取  消</button></div></div>';
               $('body').append(htmlstring);
	           $(".lhw-alert-ok").bind("click",function(){   $(this).parent().parent().parent(".lhw-alert-bg-d").remove();  if("function"==typeof aa){  aa(); }  });
			   $(".lhw-alert-cancel").bind("click",function(){   $(this).parent().parent().parent(".lhw-alert-bg-d").remove();   });
			}

			service.editAlert = function(scope,title,content,aa){	
			   var htmlstring='<div class="lhw-alert-bg lhw-alert-bg-d"><div class="lhw-alert-box"><div class="lhw-alert-tit">'+title+'</div><div class="lhw-alert-con">'+content+'</div><div class="lhw-alert-but"><button type="button" class="btn btn-primary lhw-alert-ok" style="margin-right:80px;">确  定</button><button type="button" class="btn btn-default lhw-alert-cancel">取  消</button></div></div>';
               var el=$compile(htmlstring)(scope);
			   $("body").append(el);
	           $(".lhw-alert-ok").bind("click",function(){  if("function"==typeof aa){ aa(); }  });
			   $(".lhw-alert-cancel").bind("click",function(){   $(this).parent().parent().parent(".lhw-alert-bg-d").remove();});
			}
			service.editAlert2 = function(scope,title,content,aa){
				var htmlstring='<div class="lhw-alert-bg lhw-alert-bg-d"><div class="lhw-alert-box" style="width:486px;"><div class="lhw-alert-tit">'+title+'</div><div class="lhw-alert-con">'+content+'</div><div class="lhw-alert-but"><button type="button" class="btn btn-primary lhw-alert-ok" style="margin-right:80px;">确  定</button><button type="button" class="btn btn-default lhw-alert-cancel">取  消</button></div></div>';
				var el=$compile(htmlstring)(scope);
				$("body").append(el);
				$(".lhw-alert-ok").bind("click",function(){  if("function"==typeof aa){ aa(); }  });
				$(".lhw-alert-cancel").bind("click",function(){   $(this).parent().parent().parent(".lhw-alert-bg-d").remove();});
			}
			service.editAlert3 = function(scope,title,content,aa){	
				   var htmlstring='<div class="lhw-alert-bg lhw-alert-bg-d"><div class="lhw-alert-box"><div class="lhw-alert-tit">'+title+'</div>';
				   if("<bank-box></bank-box>"== content){
					   htmlstring+='<div class="lhw-alert-con" style="height:156px">'+content+'</div>';
				   }else if("<bank-sub-box></bank-sub-box>"== content){
					   htmlstring+='<div class="lhw-alert-con" style="height:200px">'+content+'</div>';
				   }else{
					   htmlstring+='<div class="lhw-alert-con" style="height:264px">'+content+'</div>';
				   }
				   htmlstring+='<div class="lhw-alert-but"><button type="button" class="btn btn-primary lhw-alert-ok" style="margin-right:80px;">确定</button><button type="button" class="btn btn-default lhw-alert-cancel">取消</button></div></div>';
	              
				   var el=$compile(htmlstring)(scope);
				   $("body").append(el);
		           $(".lhw-alert-ok").bind("click",function(){  if("function"==typeof aa){ aa(); }  });
				   $(".lhw-alert-cancel").bind("click",function(){   $(this).parent().parent().parent(".lhw-alert-bg-d").remove();});
				}
			service.waitAlert = function() {
				$('body').append('<div class="loading" ng-show="isWaitSumbit"><img src="images/loading_gif.gif" width="40" ></div>');
			}
			service.closeWaitAlert = function() {
				$(".loading").remove();
			}
			service.closeAlert = function(){	
			   $(".lhw-alert-ok").parent().parent().parent(".lhw-alert-bg-d").remove();
			}
			service.alertPanel = function(scope, content, fun) {
				var htmlstring = '<div class="lhw-alert-bg lhw-alert-bg-d"><div class="lhw-alert-box"><div class="lhw-alert-con">' + content + '</div><div class="lhw-alert-but"><button type="button" class="btn btn-default lhw-alert-cancel">关闭窗口</button></div></div>';
				$("body").append(htmlstring);
				$(".lhw-alert-ok").bind("click", function() {
					if("function" == typeof fun) {
						fun();
					}
				});
				$(".lhw-alert-cancel").bind("click", function() {
					$(this).parent().parent().parent(".lhw-alert-bg-d").remove();
				});
			}
			service.alertPanel2 = function(scope, content, fun) {
				var htmlstring = '<div class="lhw-alert-bg lhw-alert-bg-d"><div class="lhw-alert-box" style="width:600px;position:relative"><img src="/images/x.png"  class="lhw-alert-x" style="position: absolute;top:5px; right:5px;cursor: pointer;">  <div class="lhw-alert-con">' + content + '</div><div class="lhw-alert-but"><button type="button" class="btn btn-default lhw-alert-cancel">关闭窗口</button></div></div>';
				$("body").append(htmlstring);
				$(".lhw-alert-ok").bind("click", function() {
					if("function" == typeof fun) {
						fun();
					}
				});
				$(".lhw-alert-cancel").bind("click", function() {
					$(this).parent().parent().parent(".lhw-alert-bg-d").remove();
				});
				$(".lhw-alert-x").bind("click", function() {
					$(this).parent().parent(".lhw-alert-bg-d").remove();
				});
			}
			
			service.editAlertW500 = function(scope, title, content, aa) {
				var htmlstring = '<div class="lhw-alert-bg lhw-alert-bg-d"><div class="lhw-alert-box" style="width:663px"><div class="lhw-alert-tit">' + title + '</div><div class="lhw-alert-con" style="text-align:left;margin: 0 0 0 14%">' + content + '</div><div class="lhw-alert-but"><button type="button" class="btn btn-primary lhw-alert-ok" style="margin-right:80px;">确  定</button><button type="button" class="btn btn-default lhw-alert-cancel">取  消</button></div></div>';
				var el = $compile(htmlstring)(scope);
				$("body").append(el);
				$(".lhw-alert-ok").bind("click", function() {
					if("function" == typeof aa) {
						aa();
					}
				});
				$(".lhw-alert-cancel").bind("click", function() {
					$(this).parent().parent().parent(".lhw-alert-bg").remove();
				});
			}
			return service;
		});

		app.service('file',function($compile){
			var service = {};
			service.fileuploader = function(scope,FileUploader,box){
				var uploader = scope.uploader = new FileUploader({
					//url: 'http://127.0.0.1:8078/fs/file/upload'
					url: 'http://fs.anjbo.com/fs/file/upload'
				});
				uploader.filters.push({
					name: 'syncFilter',
					fn: function(item /*{File|FileLikeObject}*/, options) {
						console.log('syncFilter');
						return this.queue.length < 10;
					}
				});

				uploader.filters.push({
					name: 'asyncFilter',
					fn: function(item /*{File|FileLikeObject}*/, options, deferred) {
						console.log('asyncFilter');
						setTimeout(deferred.resolve, 1e3);
					}
				});
				uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
					console.info('onWhenAddingFileFailed', item, filter, options);
				};
				uploader.onAfterAddingFile = function(fileItem) {
					var name = fileItem.file.name;
					if (name.indexOf(".jar")>=0
						||name.indexOf(".exe")>=0
						||name.indexOf(".iso")>=0) {
						box.boxAlert("不能上传此格式文件!");
						fileItem.remove();
					}
				};
				uploader.onAfterAddingAll = function(addedFileItems) {
					console.info('onAfterAddingAll', addedFileItems);
				};
				uploader.onBeforeUploadItem = function(item) {
					console.info('onBeforeUploadItem', item);
				};
				uploader.onCompleteItem = function(fileItem, response, status, headers) {
					if(200==status&&"SUCCESS"==response.code){
						fileItem.feilData = response.data;
					} else {
						box.boxAlert(fileItem.file.name+"上传失败!");
					}
				};
				return uploader;
			}
			return service;
		});

	};
});