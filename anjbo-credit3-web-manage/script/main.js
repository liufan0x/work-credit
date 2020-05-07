define(function(require, exports, module) {

	
	require('common/angular-ui-router.min.js');

	require('common/ocLazyLoad.min.js');

	require('common/angular-cookies.min.js');

	var app = angular.module("anjboApp",["ngCookies","ui.router","oc.lazyLoad","angularFileUpload"]);

	require('angular/angular-constant/constant.js').extend(app);

	require('angular/angular-directive/common-directive.js').extend(app);
	
	//插件
	require('/plugins/plugins.js').extend(app);

	require('angular/angular-interceptor/interceptor.js').extend(app);

	require('angular/angular-service/service.js').extend(app);
	
	require('angular/angular-config/config.js').extend(app);

	require('agency/common/product-data-common-directive.js').extend(app);

	require('agency/common/special-type-directive.js').extend(app);

	module.exports = app;

});

