define(function(require, exports, module) {
	exports.extend = function(app) {

		app.directive('erongsuoDockingEdit', function($compile, $timeout, $http, $state, $filter, route, box) {
			return {
				restrict: "E",
				templateUrl: '/plugins/fund-directive/erongsuo/erongsuoDocking.html',
				transclude: true,
				link: function(scope) {
					
					scope.kgErongsuoImg = new Object();
					
					scope.productCode = route.getParams().productCode;
					scope.relationOrderNo = route.getParams().relationOrderNo;
					
					//初始化影像资料
					erongsuoImgInit();
					erongsuoInit();
					//关闭窗口
					scope.yunnanClose = function() {
						scope.yunBankShow = false;
						$timeout(function() {
							$(angular.element.find("erongsuo-docking-edit")).remove();
						});
					}
					
					//刷新影像资料
					function erongsuoImgInit() {
						$http({
							url: '/credit/third/api/yntrust/v/selectBusinfo',///credit/third/api/erongsuoBusinfo/v/erongsuoBusInfoTypes
							method: 'POST',
							data: {
								"orderNo": scope.orderNo
							}
						}).success(function(data) {
							scope.kgErongsuoImg = data.data;
							angular.forEach(scope.kgErongsuoImg, function(data) {
								angular.forEach(data.img, function(data1) {
									if(data1.url.indexOf('pdf')>0){
										data1.isPdf = true;
									}else{
										data1.isPdf = false;
									}
								});
							});

						});
					}
					
					//初始化
					function erongsuoInit () {
						//借款信息
						scope.erongsuoOrder = new Object();
						$http({
							method: 'POST',
							url: "/credit/third/api/erongsuoBorrow/v/find",
							data: {
								"orderNo": scope.orderNo
							}
						}).success(function(data) {
							if(data.data) {
								scope.erongsuoOrder = data.data;
							}
						})
						
						//客户信息
						scope.erongsuoCustomer = new Object();
						$http({
							method: 'POST',
							url: "/credit/third/api/erongsuoCustomer/v/find",
							data: {
								"orderNo": scope.orderNo
							}
						}).success(function(data) {
							if(data.data) {
								scope.erongsuoCustomer = data.data;
							}
						})
					}
				}
			}
		});

	};
});