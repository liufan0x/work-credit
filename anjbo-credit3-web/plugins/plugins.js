define(function(require, exports, module){
	exports.extend= function(app){  
		
		require('/plugins/order-directive/businfo/businfoEdit-directive.js').extend(app);
		
		require('/plugins/order-directive/enquiry/enquiryEdit-directive.js').extend(app);
		
		require('/plugins/fund-directive/yunnan/yunnan-docking.js').extend(app);
		
		require('/plugins/fund-directive/ordinary/ordinary-docking.js').extend(app);
		
		require('/plugins/fund-directive/huarong/huarong-docking.js').extend(app);
		
		require('/plugins/fund-directive/shanguotou/shanguotou-docking.js').extend(app);
		
		
	};
});