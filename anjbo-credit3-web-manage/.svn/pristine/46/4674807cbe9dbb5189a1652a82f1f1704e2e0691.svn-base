define(function(require, exports, module){
	exports.extend= function(app){  

		app.config(['$httpProvider', function($httpProvider){
			$httpProvider.defaults.withCredentials = true; 
			$httpProvider.interceptors.push('interceptor');
		}]);
		
		app.config(['$ocLazyLoadProvider',function($ocLazyLoadProvider){
			$ocLazyLoadProvider.config({
				loadedModules: ['anjboApp'],
				jsLoader: seajs, 
				events: true,
				debug: false
			});
		}]);

		app.config(function($stateProvider,menuList) {

			function resovleDep(param){
				var resolves = {
						loadMyCtrl: ["$ocLazyLoad", function($ocLazyLoad) {
							return $ocLazyLoad.load({
								files: param.files
							});
						}]
				};
				return resolves;
			};

			angular.forEach(menuList, function(data,index,array){
				$stateProvider = $stateProvider.state(
					data.state,{
						params:data.params,
						url:"/"+data.state,	
						controller:data.ctrl,
						templateUrl:function ($routeParams){
							angular.forEach($routeParams, function(param,index,array){
								if(param != null){
									if(param != 0){
										document.cookie = "routeParams="+escape(encodeURI(JSON.stringify($routeParams)))+ ";path=/";
										return true;
									}
								}
							});
							return data.templateUrl+ "?v=" + version + new Date();
						},
						resolve:resovleDep({files:[data.js+"?v="+version]}),
						cache: false
					}
				);
			});
		});


		app.run(['$rootScope', '$state', '$stateParams','$http', function($rootScope, $state, $stateParams, $templateCache, $http){
			$rootScope.$on('$stateChangeStart', function ($http) {
				angular.forEach($http.pendingRequests,function(request){  
				    if (request.cancel && request.timeout) {
				        request.cancel.resolve();
				    }
				});
			});
		}]);

	};
});