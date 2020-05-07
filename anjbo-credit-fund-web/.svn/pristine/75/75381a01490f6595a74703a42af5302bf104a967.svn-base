define(function(require, exports, module){

	exports.extend = function(app){ 
		
		app.constant('noticeId',"noticeId2");
		app.constant('notice',"  这是个公告这是个公告这是个公告<br/>这是个公告这是个公告这是个公告这是个公告这是个公告这是个公告这是个公告这是个公告这是个公告  ");
		
		app.constant('placeOrderAuthIds',"8892,8917,8938,8951,8976,8997,9010,9035,9056,9069,9094,9115,9128,9153,9174,9187,9212,9233,9612,9637,9658,9760,9812,9856,9908,9952,9980,10032,10076,10104,10156,10200,10228,10280,10324");
	
		app.constant('menuOrderDetail',[
						
			//提单要件校验编辑
			{templateUrl:"/template/sl/order/edit/placeElementEdit.html", state:"placeElementEdit",  ctrl:"placeElementEditCtrl", js:"/script/sl/order/edit/placeElementEdit.js" },
			//提单询价查档诉讼详情
			{templateUrl:"/template/sl/order/edit/placeEnquiryEdit.html", state:"placeEnquiryEdit",  ctrl:"placeEnquiryEditCtrl", js:"/script/sl/order/edit/placeEnquiryEdit.js" },
			//提单影像资料详情
			{templateUrl:"/template/sl/order/edit/placeBusinfoEdit.html", state:"placeBusinfoEdit",  ctrl:"placeBusinfoEditCtrl", js:"/script/sl/order/edit/placeBusinfoEdit.js" },
			//提单征信详情
			{templateUrl:"/template/sl/order/edit/placeCreditEdit.html", state:"placeCreditEdit",  ctrl:"placeCreditEditCtrl", js:"/script/sl/order/edit/placeCreditEdit.js"},
			
		]);
		
		app.constant('menuOrderEdit',[
						
			//订单详情
			{templateUrl:"/template/sl/order/orderDetail.html", state:"orderDetail",  ctrl:"orderDetailCtrl", js:"/script/sl/order/orderDetail.js" ,params:{"orderNo":"0","productCode":"0","processId":"0"}},
			//订单编辑
			{templateUrl:"/template/sl/order/orderEdit.html", state:"orderEdit",  ctrl:"orderEditCtrl", js:"/script/sl/order/orderEdit.js" ,params:{"orderNo":"0","cityCode":"0","productCode":"0","addCangDai":"0"}},
			//公证编辑
			{templateUrl:"/template/sl/process/notarizationEdit.html", state:"orderEdit.notarizationEdit",  ctrl:"notarizationEditCtrl", js:"/script/sl/process/notarizationEdit.js" ,params:{"orderNo":"0"}},
			//面签编辑
			{templateUrl:"/template/sl/process/facesignEdit.html", state:"orderEdit.facesignEdit",  ctrl:"facesignEditCtrl", js:"/script/sl/process/facesignEdit.js" ,params:{"orderNo":"0"}},
			//提单编辑
			{templateUrl:"/template/sl/order/placeOrderEdit.html", state:"orderEdit.placeOrderEdit",  ctrl:"placeOrderEditCtrl", js:"/script/sl/order/placeOrderEdit.js" ,params:{"orderNo":"0","cityCode":"0","productCode":"0","addCangDai":"false"}},
			//提单借款信息编辑
			{templateUrl:"/template/sl/order/edit/placeBorrowEdit.html", state:"orderEdit.placeOrderEdit.placeBorrowEdit",  ctrl:"placeBorrowEditCtrl", js:"/script/sl/order/edit/placeBorrowEdit.js" },
			//提单客户信息编辑
			{templateUrl:"/template/sl/order/edit/placeCustomerEdit.html", state:"orderEdit.placeOrderEdit.placeCustomerEdit",  ctrl:"placeCustomerEditCtrl", js:"/script/sl/order/edit/placeCustomerEdit.js" },
			//提单房产交易信息编辑
			{templateUrl:"/template/sl/order/edit/placeHouseEdit.html", state:"orderEdit.placeOrderEdit.placeHouseEdit",  ctrl:"placeHouseEditCtrl", js:"/script/sl/order/edit/placeHouseEdit.js" },
			//经理审批编辑
			{templateUrl:"/template/sl/process/managerAuditEdit.html", state:"orderEdit.managerAuditEdit", ctrl:"managerAuditEditCtrl", js:"/script/sl/process/managerAuditEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
			//风控初审编辑
			{templateUrl:"/template/sl/risk/auditFirstEdit.html", state:"orderEdit.auditFirstEdit",  ctrl:"auditFirstEditCtrl", js:"/script/sl/risk/auditFirstEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
			//风控终审编辑
			{templateUrl:"/template/sl/risk/auditFinalEdit.html", state:"orderEdit.auditFinalEdit",  ctrl:"auditFinalEditCtrl", js:"/script/sl/risk/auditFinalEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
			//首席风险官编辑
			{templateUrl:"/template/sl/risk/auditOfficerEdit.html", state:"orderEdit.auditOfficerEdit",  ctrl:"auditOfficerEditCtrl", js:"/script/sl/risk/auditOfficerEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
			//法务编辑
			{templateUrl:"/template/sl/risk/auditJusticeEdit.html", state:"orderEdit.auditJusticeEdit",  ctrl:"auditJusticeEditCtrl", js:"/script/sl/risk/auditJusticeEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
			//分配资金方编辑
			{templateUrl:"/template/sl/risk/allocationFundEdit.html", state:"orderEdit.allocationFundEdit",  ctrl:"allocationFundEditCtrl", js:"/script/sl/risk/allocationFundEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
			
		
			
			//待资金审批编辑
			{templateUrl:"/template/sl/risk/allocationFundAduit.html", state:"orderEdit.fundAduitEdit",  ctrl:"allocationFundAduitCtrl", js:"/script/sl/risk/allocationFundAduit.js" ,params:{"orderNo":"0","productCode":"0"}},
			//指派还款专员编辑
			{templateUrl:"/template/sl/process/repaymentMemberEdit.html", state:"orderEdit.repaymentMemberEdit",  ctrl:"repaymentMemberEditCtrl", js:"/script/sl/process/repaymentMemberEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
			//要件校验信息编辑
			{templateUrl:"/template/sl/element/elementEdit.html", state:"orderEdit.elementEdit",  ctrl:"elementEditCtrl", js:"/script/sl/element/elementEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
			//收利息信息编辑
			{templateUrl:"/template/sl/finance/lendingHarvestEdit.html", state:"orderEdit.lendingHarvestEdit",  ctrl:"lendingHarvestEditCtrl", js:"/script/sl/finance/lendingHarvestEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
			//付利息信息编辑
			{templateUrl:"/template/sl/finance/lendingPayEdit.html", state:"orderEdit.lendingPayEdit",  ctrl:"lendingPayEditCtrl", js:"/script/sl/finance/lendingPayEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
			//发放款指令信息编辑
			{templateUrl:"/template/sl/finance/lendingInstructionsEdit.html", state:"orderEdit.lendingInstructionsEdit",  ctrl:"lendingInstructionsEditCtrl", js:"/script/sl/finance/lendingInstructionsEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
			//放款信息编辑
			{templateUrl:"/template/sl/finance/lendingEdit.html", state:"orderEdit.lendingEdit",  ctrl:"lendingEditCtrl", js:"/script/sl/finance/lendingEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
			//回款编辑
			{templateUrl:"/template/sl/finance/receivableForEdit.html", state:"orderEdit.receivableForEdit",  ctrl:"receivableForEditCtrl", js:"/script/sl/finance/receivableForEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
			//回款(首期)编辑
			{templateUrl:"/template/sl/finance/receivableForEdit.html", state:"orderEdit.receivableForFirstEdit",  ctrl:"receivableForEditCtrl", js:"/script/sl/finance/receivableForEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
			//回款(尾期)编辑
			{templateUrl:"/template/sl/finance/receivableForEdit.html", state:"orderEdit.receivableForEndEdit",  ctrl:"receivableForEditCtrl", js:"/script/sl/finance/receivableForEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
			//待付费
			{templateUrl:"/template/sl/finance/payEdit.html", state:"orderEdit.payEdit",  ctrl:"payEditCtrl", js:"/script/sl/finance/payEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
			//要件退还编辑
			{templateUrl:"/template/sl/element/elementReturnEdit.html", state:"orderEdit.elementReturnEdit",  ctrl:"elementReturnEditCtrl", js:"/script/sl/element/elementReturnEdit.js" ,params:{"orderNo":"0","productCode":"0"}},
	
		]);
		
		app.constant('menuList', [

			//订单列表
			{templateUrl:"/template/pageConfig/formConfig.html", state:"pageConfigList", ctrl:"pageConfigCtrl" , js:"/script/pageConfig/pageConfig.js" },
		
			//订单管理
			//订单列表
			{templateUrl:"/template/sl/order/orderList.html", state:"orderList", ctrl:"orderListCtrl" , js:"/script/sl/order/orderList.js" },
			
			 // 要件管理
//			 {templateUrl:"/template/element-box/audit/auditDetail.html", state:"auditDetail",  ctrl:"auditDetailCtrl", js:"/script/element-box/audit/auditDetail.js",params:{"id":"0"} },
//	         {templateUrl:"/template/element-box/audit/auditList.html", state:"auditList",  ctrl:"auditListCtrl", js:"/script/element-box/audit/auditList.js" },
            {templateUrl:"/template/element-box/ele/eleAccessList.html", state:"eleAccessList",  ctrl:"eleAccessListCtrl", js:"/script/element-box/ele/eleAccessList.js" },
            {templateUrl:"/template/element-box/ele/eleAccessDetail.html", state:"eleAccessDetail",  ctrl:"eleAccessDetailCtrl", js:"/script/element-box/ele/eleAccessDetail.js",params:{"id":"0","pid":"0"} },
			//财务管理
			//代扣列表
			{templateUrl:"/template/sl/finance/capitalList.html", state:"capitalList",  ctrl:"capitalListCtrl", js:"/script/sl/finance/capitalList.js" ,params:{"orderNo":"0"}},
			{templateUrl:"/template/sl/finance/capitalDetail.html", state:"capitalDetail",  ctrl:"capitalDetailCtrl", js:"/script/sl/finance/capitalDetail.js" ,params:{"flowNo":"0"}},
			//银企互联账单
			{templateUrl:"/template/sl/finance/icbcQPDList.html", state:"icbcQPDList", ctrl:"icbcQPDListCtrl" , js:"/script/sl/finance/icbcQPDList.js" },

			//风控模型配置
			{templateUrl:"/template/sl/risk/riskModelConfig.html", state:"riskModelEdit", ctrl:"riskModelCtrl" , js:"/script/sl/risk/riskModelEdit.js" },
			//流程审批配置
			{templateUrl:"/template/sl/smartwork/bpmsTempList.html", state:"bpmsTempList", ctrl:"bpmsTempListCtrl" , js:"/script/sl/smartwork/bpmsTempList.js" },
			{templateUrl:"/template/sl/smartwork/bpmsTempEdit.html", state:"bpmsTempEdit", ctrl:"bpmsTempEditCtrl" , js:"/script/sl/smartwork/bpmsTempEdit.js", params:{"id":"0"}},
			{templateUrl:"/template/sl/smartwork/bpmsList.html", state:"bpmsList", ctrl:"bpmsListCtrl" , js:"/script/sl/smartwork/bpmsList.js", params:{"bpmsFrom":"0"}},
			{templateUrl:"/template/sl/smartwork/bpmsDetail.html",  state:"bpmsDetail", ctrl:"bpmsDetailCtrl" , js:"/script/sl/smartwork/bpmsDetail.js", params:{"processInstanceId":"0"} },
		    //房产监测列表
			{templateUrl:"/template/sl/risk/monitorArchiveList.html", state:"monitorArchiveList", ctrl:"monitorArchiveListCtrl" , js:"/script/sl/risk/monitorArchiveList.js" },
			//房产监测详情
			{templateUrl:"/template/sl/risk/monitorArchiveDetail.html", state:"monitorArchiveDetail",  ctrl:"monitorArchiveDetailCtrl", js:"/script/sl/risk/monitorArchiveDetail.js" ,params:{"id":"0"}},
			//房产监测详情
			{templateUrl:"/template/sl/risk/monitorArchiveAdd.html", state:"monitorArchiveAdd",  ctrl:"monitorArchiveAddCtrl", js:"/script/sl/risk/monitorArchiveAdd.js"},
		
			//风险名单列表
			{templateUrl:"/template/sl/risk/baiduRiskList.html", state:"baiduLisklist", ctrl:"baiduRiskListCtrl" , js:"/script/sl/risk/baiduRiskList.js" },
			//风险名单详情
			{templateUrl:"/template/sl/risk/baiduRiskListDetail.html", state:"baiduRiskListDetail",  ctrl:"baiduRiskListDetailCtrl", js:"/script/sl/risk/baiduRiskListDetail.js" ,params:{"id":"0"}},
			
			//客户信息报表
			{templateUrl:"/template/sl/report/customerList.html", state:"customerList", ctrl:"customerCtrl" , js:"/script/sl/report/customerList.js" },
			//订单退回统计表
			{templateUrl:"/template/sl/report/customerList.html", state:"returnBackList", ctrl:"customerCtrl" , js:"/script/sl/report/customerList.js" },
			//风控审批统计
			{templateUrl:"/template/sl/report/customerList.html", state:"managementList", ctrl:"customerCtrl" , js:"/script/sl/report/customerList.js" },
			//出款报备
			{templateUrl:"/template/sl/finance/reportList.html", state:"reportList", ctrl:"reportListCtrl" , js:"/script/sl/finance/reportList.js" },
		]);
		
		
		
		
		//配置
		app.constant('productDataList', [
			//列表
			{templateUrl:"/template/product-data/list/productDataList.html", state:"productDataList", ctrl:"productDataListCtrl" , js:"/script/product-data/list/productDataList.js" , params:{"productCode":"0","productName":"0"} },
			//详情
			{templateUrl:"/template/product-data/detail/productDataDetail.html", state:"productDataDetail", ctrl:"productDataDetailCtrl" , js:"/script/product-data/detail/productDataDetail.js" , params:{"orderNo":"0","productCode":"0","processId":"0","tblName":"0"} },  
			//编辑
			{templateUrl:"/template/product-data/edit/productDataEdit.html", state:"productDataEdit", ctrl:"productDataEditCtrl" , js:"/script/product-data/edit/productDataEdit.js" , params:{"orderNo":"0","productCode":"0" ,"processId":"0","tblName":"0"} },
		]);
			
	};

});