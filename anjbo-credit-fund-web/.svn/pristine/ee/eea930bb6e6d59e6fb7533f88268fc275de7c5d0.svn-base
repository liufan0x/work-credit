define(function(require, exports, module){

	exports.extend= function(app){
		
		app.factory('interceptor', ['$q', function($q) {  
		    return {
		        request: function(config) {
		            var defered = $q.defer();
		            config.timeout = defered.promise;
		            config.cancel = defered;
		            return config;
		        },
		        response :function(response){
		        	if(response.data.code == 'LOGIN_OVER_TIME'){
		        		window.location.href = "login.html";
			            return $q.reject(response);
		        	}
		        	return response;
		        },
		        responseError: function(response) {
		        	if(response.status == -1){
		        		// alert("网络异常");
		        	}else if(response.status == 404){
		        		//alert("请求异常");
		        	}else if(response.status == 500){
		        		// alert("系统异常");
		        	}
		        	return $q.reject(response);
		        }
		    }
		}]);
		
	};
});