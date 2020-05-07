var userid = getUserid();
$("#close").click(function(){
	if(userid){
		window.history.go(2-history.length);
	}else{
		setupWebViewJavascriptBridge(function(bridge) {
			bridge.callHandler('go2index');//调用app方法
		});
	}
});
$("#back2list").click(function(){
	if(userid){
		window.history.go(2-history.length);
	}else{
		setupWebViewJavascriptBridge(function(bridge) {
			bridge.callHandler('back2list');//调用app方法
		});
	}
});
function setupWebViewJavascriptBridge(callback) {
       if (window.WebViewJavascriptBridge) { return callback(WebViewJavascriptBridge); }
       if (window.WVJBCallbacks) { return window.WVJBCallbacks.push(callback); }
       window.WVJBCallbacks = [callback];
       var WVJBIframe = document.createElement('iframe');
       WVJBIframe.style.display = 'none';
       WVJBIframe.src = 'https://__bridge_loaded__';
       document.documentElement.appendChild(WVJBIframe);
       setTimeout(function() { document.documentElement.removeChild(WVJBIframe) }, 0);
}