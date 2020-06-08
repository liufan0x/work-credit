define(function(require, exports, module){

	exports.extend = function(app){ 
		
		app.constant('noticeId',"noticeId2");
		app.constant('notice',"  这是个公告这是个公告这是个公告<br/>这是个公告这是个公告这是个公告这是个公告这是个公告这是个公告这是个公告这是个公告这是个公告  ");
		
		app.constant('placeOrderAuthIds',"8892,8917,8938,8951,8976,8997,9010,9035,9056,9069,9094,9115,9128,9153,9174,9187,9212,9233,9246,9271,9292,9305,9330,9351,9364,9389,9410,9423,9448,9469,9482,9507,9528,9541,9566,9587,9856,9908,9952");
	
		app.constant('menuOrderDetail',[
						
			//提单要件校验编辑
			{templateUrl:"/template/sl/order/edit/placeElementEdit.html", state:"placeElementEdit",  ctrl:"placeElementEditCtrl", js:"/script/sl/order/edit/placeElementEdit.js",params:{"orderNo":"0","processId":"0","productCode":"0","relationOrderNo":"0"} },
			//提单询价查档诉讼详情
			{templateUrl:"/template/sl/order/edit/placeEnquiryEdit.html", state:"placeEnquiryEdit",  ctrl:"placeEnquiryEditCtrl", js:"/script/sl/order/edit/placeEnquiryEdit.js" },
			//提单影像资料详情
			{templateUrl:"/template/sl/order/edit/placeBusinfoEdit.html", state:"placeBusinfoEdit",  ctrl:"placeBusinfoEditCtrl", js:"/script/sl/order/edit/placeBusinfoEdit.js" },
			//提单征信详情
			{templateUrl:"/template/sl/order/edit/placeCreditEdit.html", state:"placeCreditEdit",  ctrl:"placeCreditEditCtrl", js:"/script/sl/order/edit/placeCreditEdit.js"},
			
			//畅贷
			{templateUrl:"/template/sl/order/editCD/placeElementEdit.html", state:"placeElementEditCD",  ctrl:"placeElementEditCDCtrl", js:"/script/sl/order/editCD/placeElementEdit.js" },
			{templateUrl:"/template/sl/order/editCD/placeEnquiryEdit.html", state:"placeEnquiryEditCD",  ctrl:"placeEnquiryEditCDCtrl", js:"/script/sl/order/editCD/placeEnquiryEdit.js" },
			{templateUrl:"/template/sl/order/editCD/placeBusinfoEdit.html", state:"placeBusinfoEditCD",  ctrl:"placeBusinfoEditCDCtrl", js:"/script/sl/order/editCD/placeBusinfoEdit.js" },
			{templateUrl:"/template/sl/order/editCD/placeCreditEdit.html", state:"placeCreditEditCD",  ctrl:"placeCreditEditCDCtrl", js:"/script/sl/order/editCD/placeCreditEdit.js"},		
		]);
		
		app.constant('menuOrderEdit',[
						
			//订单详情
			{templateUrl:"/template/sl/order/orderDetail.html", state:"orderDetail",  ctrl:"orderDetailCtrl", js:"/script/sl/order/orderDetail.js" ,params:{"orderNo":"0","cityCode":"0","processId":"0","productCode":"0","relationOrderNo":"0"}},
			//订单编辑
			{templateUrl:"/template/sl/order/orderEdit.html", state:"orderEdit",  ctrl:"orderEditCtrl", js:"/script/sl/order/orderEdit.js" ,params:{"orderNo":"0","cityCode":"0","productCode":"0","addCangDai":"0"}},
			//公证编辑
			{templateUrl:"/template/sl/process/notarizationEdit.html", state:"orderEdit.notarizationEdit",  ctrl:"notarizationEditCtrl", js:"/script/sl/process/notarizationEdit.js" ,params:{"orderNo":"0","relationOrderNo":"0","productCode":"0"}},
			//面签编辑
			{templateUrl:"/template/sl/process/facesignEdit.html", state:"orderEdit.facesignEdit",  ctrl:"facesignEditCtrl", js:"/script/sl/process/facesignEdit.js" ,params:{"orderNo":"0","relationOrderNo":"0"}},
			//提单编辑
			{templateUrl:"/template/sl/order/placeOrderEdit.html", state:"orderEdit.placeOrderEdit",  ctrl:"placeOrderEditCtrl", js:"/script/sl/order/placeOrderEdit.js" ,params:{"orderNo":"0","cityCode":"0","productCode":"0","addCangDai":"false","relationOrderNo":""}},
			//提单借款信息编辑
			{templateUrl:"/template/sl/order/edit/placeBorrowEdit.html", state:"orderEdit.placeOrderEdit.placeBorrowEdit",  ctrl:"placeBorrowEditCtrl", js:"/script/sl/order/edit/placeBorrowEdit.js" },
			//提单客户信息编辑
			{templateUrl:"/template/sl/order/edit/placeCustomerEdit.html", state:"orderEdit.placeOrderEdit.placeCustomerEdit",  ctrl:"placeCustomerEditCtrl", js:"/script/sl/order/edit/placeCustomerEdit.js" },
			//提单房产交易信息编辑
			{templateUrl:"/template/sl/order/edit/placeHouseEdit.html", state:"orderEdit.placeOrderEdit.placeHouseEdit",  ctrl:"placeHouseEditCtrl", js:"/script/sl/order/edit/placeHouseEdit.js" },
			//畅贷orderEdit.placeOrderEdit
			{templateUrl:"/template/sl/order/editCD/placeBorrowEdit.html", state:"orderEdit.placeOrderEdit.placeBorrowEditCD",  ctrl:"placeBorrowEditCDCtrl", js:"/script/sl/order/editCD/placeBorrowEdit.js" },
			{templateUrl:"/template/sl/order/editCD/placeCustomerEdit.html", state:"orderEdit.placeOrderEdit.placeCustomerEditCD",  ctrl:"placeCustomerEditCDCtrl", js:"/script/sl/order/editCD/placeCustomerEdit.js" },
			{templateUrl:"/template/sl/order/editCD/placeHouseEdit.html", state:"orderEdit.placeOrderEdit.placeHouseEditCD",  ctrl:"placeHouseEditCDCtrl", js:"/script/sl/order/editCD/placeHouseEdit.js" },
			
			//经理审批编辑
			{templateUrl:"/template/sl/process/managerAuditEdit.html", state:"orderEdit.managerAuditEdit", ctrl:"managerAuditEditCtrl", js:"/script/sl/process/managerAuditEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//风控初审编辑
			{templateUrl:"/template/sl/risk/auditFirstEdit.html", state:"orderEdit.auditFirstEdit",  ctrl:"auditFirstEditCtrl", js:"/script/sl/risk/auditFirstEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//风控终审编辑
			{templateUrl:"/template/sl/risk/auditFinalEdit.html", state:"orderEdit.auditFinalEdit",  ctrl:"auditFinalEditCtrl", js:"/script/sl/risk/auditFinalEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//风控复核审批编辑
			{templateUrl:"/template/sl/risk/auditReviewEdit.html", state:"orderEdit.auditReviewEdit",  ctrl:"auditReviewEditCtrl", js:"/script/sl/risk/auditReviewEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//首席风险官编辑
			{templateUrl:"/template/sl/risk/auditOfficerEdit.html", state:"orderEdit.auditOfficerEdit",  ctrl:"auditOfficerEditCtrl", js:"/script/sl/risk/auditOfficerEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//法务审批编辑
			{templateUrl:"/template/sl/risk/auditJusticeEdit.html", state:"orderEdit.auditJusticeEdit",  ctrl:"auditJusticeEditCtrl", js:"/script/sl/risk/auditJusticeEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//签订投保单编辑
			{templateUrl:"/template/sl/process/signInsurancePolicyEdit.html", state:"orderEdit.signInsurancePolicyEdit",  ctrl:"signInsurancePolicyEditCtrl", js:"/script/sl/process/signInsurancePolicyEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//上传电子保单编辑
			{templateUrl:"/template/sl/process/uploadInsurancePolicyEdit.html", state:"orderEdit.uploadInsurancePolicyEdit",  ctrl:"uploadInsurancePolicyEditCtrl", js:"/script/sl/process/uploadInsurancePolicyEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//分配资金方编辑
			{templateUrl:"/template/sl/risk/allocationFundEdit.html", state:"orderEdit.allocationFundEdit",  ctrl:"allocationFundEditCtrl", js:"/script/sl/risk/allocationFundEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//资料审核编辑
			{templateUrl:"/template/sl/risk/dataAuditEdit.html", state:"orderEdit.dataAuditEdit",  ctrl:"dataAuditEditCtrl", js:"/script/sl/risk/dataAuditEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//资料推送编辑
			{templateUrl:"/template/sl/risk/fundDockingEdit.html", state:"orderEdit.fundDockingEdit",  ctrl:"fundDockingEditCtrl", js:"/script/sl/risk/fundDockingEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//待资金审批编辑
			{templateUrl:"/template/sl/risk/allocationFundAduit.html", state:"orderEdit.fundAduitEdit",  ctrl:"allocationFundAduitCtrl", js:"/script/sl/risk/allocationFundAduit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//指派还款专员编辑
			{templateUrl:"/template/sl/process/repaymentMemberEdit.html", state:"orderEdit.repaymentMemberEdit",  ctrl:"repaymentMemberEditCtrl", js:"/script/sl/process/repaymentMemberEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//要件校验信息编辑
			{templateUrl:"/template/sl/element/elementEdit.html", state:"orderEdit.elementEdit",  ctrl:"elementEditCtrl", js:"/script/sl/element/elementEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//申请放款
			{templateUrl:"/template/sl/finance/applyLoanEdit.html", state:"orderEdit.applyLoanEdit",  ctrl:"applyLoanEditCtrl", js:"/script/sl/finance/applyLoanEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//收利息信息编辑
			{templateUrl:"/template/sl/finance/lendingHarvestEdit.html", state:"orderEdit.lendingHarvestEdit",  ctrl:"lendingHarvestEditCtrl", js:"/script/sl/finance/lendingHarvestEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//审核利息
			{templateUrl:"/template/sl/finance/isLendingHarvestEdit.html", state:"orderEdit.isLendingHarvestEdit",  ctrl:"isLendingHarvestEditCtrl", js:"/script/sl/finance/isLendingHarvestEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//收费信息编辑
			{templateUrl:"/template/sl/finance/chargeEdit.html", state:"orderEdit.chargeEdit",  ctrl:"chargeEditCtrl", js:"/script/sl/finance/chargeEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//审核收费
			{templateUrl:"/template/sl/finance/isChargeEdit.html", state:"orderEdit.isChargeEdit",  ctrl:"isChargeEditCtrl", js:"/script/sl/finance/isChargeEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//付利息信息编辑
			{templateUrl:"/template/sl/finance/lendingPayEdit.html", state:"orderEdit.lendingPayEdit",  ctrl:"lendingPayEditCtrl", js:"/script/sl/finance/lendingPayEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//发放款指令信息编辑
			{templateUrl:"/template/sl/finance/lendingInstructionsEdit.html", state:"orderEdit.lendingInstructionsEdit",  ctrl:"lendingInstructionsEditCtrl", js:"/script/sl/finance/lendingInstructionsEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//放款信息编辑
			{templateUrl:"/template/sl/finance/lendingEdit.html", state:"orderEdit.lendingEdit",  ctrl:"lendingEditCtrl", js:"/script/sl/finance/lendingEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//放款信息编辑
			{templateUrl:"/template/sl/finance/financialStatementEdit.html", state:"orderEdit.financialStatementEdit",  ctrl:"financialStatementEditCtrl", js:"/script/sl/finance/financialStatementEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//扣回后置费用
			{templateUrl:"/template/sl/finance/isBackExpensesEdit.html", state:"orderEdit.isBackExpensesEdit",  ctrl:"isBackExpensesEditCtrl", js:"/script/sl/finance/isBackExpensesEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//审核后置费用
			{templateUrl:"/template/sl/finance/backExpensesEdit.html", state:"orderEdit.backExpensesEdit",  ctrl:"backExpensesEditCtrl", js:"/script/sl/finance/backExpensesEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//回款编辑
			{templateUrl:"/template/sl/finance/receivableForEdit.html", state:"orderEdit.receivableForEdit",  ctrl:"receivableForEditCtrl", js:"/script/sl/finance/receivableForEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//回款(首期)编辑
			{templateUrl:"/template/sl/finance/receivableForEdit.html", state:"orderEdit.receivableForFirstEdit",  ctrl:"receivableForEditCtrl", js:"/script/sl/finance/receivableForEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//回款(尾期)编辑
			{templateUrl:"/template/sl/finance/receivableForEdit.html", state:"orderEdit.receivableForEndEdit",  ctrl:"receivableForEditCtrl", js:"/script/sl/finance/receivableForEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//待收罚息（付费）
			{templateUrl:"/template/sl/finance/payEdit.html", state:"orderEdit.payEdit",  ctrl:"payEditCtrl", js:"/script/sl/finance/payEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//要件退还编辑
			{templateUrl:"/template/sl/element/elementReturnEdit.html", state:"orderEdit.elementReturnEdit",  ctrl:"elementReturnEditCtrl", js:"/script/sl/element/elementReturnEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//返佣
			{templateUrl:"/template/sl/finance/rebateEdit.html", state:"orderEdit.rebateEdit",  ctrl:"rebateEditCtrl", js:"/script/sl/finance/rebateEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
			//抵押品入库
			{templateUrl:"/template/sl/process/fddMortgageStorageEdit.html", state:"orderEdit.fddMortgageStorageEdit",  ctrl:"fddMortgageStorageEditCtrl", js:"/script/sl/process/fddMortgageStorageEdit.js" ,params:{"orderNo":"0","productCode":"0","relationOrderNo":"0"}},
		]);
		
		app.constant('menuList', [

			//订单列表
			{templateUrl:"/template/pageConfig/formConfig.html", state:"pageConfigList", ctrl:"pageConfigCtrl" , js:"/script/pageConfig/pageConfig.js" },
		
			//订单管理
			//订单列表
			{templateUrl:"/template/sl/order/orderList.html", state:"orderList", ctrl:"orderListCtrl" , js:"/script/sl/order/orderList.js" },
					
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
		    //查档
			{templateUrl:"/template/sl/risk/archive.html", state:"archive", ctrl:"archiveCtrl" , js:"/script/sl/risk/archive.js" },
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
			
			//业绩报表
			{templateUrl:"/template/sl/report/performanceList.html", state:"performanceList", ctrl:"performanceCtrl" , js:"/script/sl/report/performanceList.js" },
			//业绩报表详情
			{templateUrl:"/template/sl/report/orderDetailList.html", state:"orderDetailList", ctrl:"orderDetailListCtrl" , js:"/script/sl/report/orderDetailList.js" ,params:{"cityName":"0","productCode":"0","startTime":"0","endTime":"0","lendingTime":"0"}},
			//风控报表
			{templateUrl:"/template/sl/report/riskControlList.html", state:"riskControlList", ctrl:"riskControlCtrl" , js:"/script/sl/report/riskControlList.js" },
			//运行报表
			{templateUrl:"/template/sl/report/runningList.html", state:"runningList", ctrl:"runningCtrl" , js:"/script/sl/report/runningList.js" },
				

			//实时报表
			{templateUrl:"/template/sl/report/currentList.html", state:"currentList", ctrl:"currentCtrl" , js:"/script/sl/report/currentList.js" },
			//目标报表
			{templateUrl:"/template/sl/report/revenueList.html", state:"revenueList", ctrl:"revenueCtrl" , js:"/script/sl/report/revenueList.js" },
			//财务报表
			{templateUrl:"/template/sl/report/financialList.html", state:"financialList", ctrl:"financialCtrl" , js:"/script/sl/report/financialList.js" },
			




			
			//订单退回统计表
			{templateUrl:"/template/sl/report/customerList.html", state:"returnBackList", ctrl:"customerCtrl" , js:"/script/sl/report/customerList.js" },
			//风控审批统计
			{templateUrl:"/template/sl/report/customerList.html", state:"managementList", ctrl:"customerCtrl" , js:"/script/sl/report/customerList.js" },
			//出款报备
			{templateUrl:"/template/sl/finance/reportList.html", state:"reportList", ctrl:"reportListCtrl" , js:"/script/sl/finance/reportList.js" },
			//回款报备
			{templateUrl:"/template/sl/finance/paymentReportList.html", state:"paymentreportList", ctrl:"paymentreportListCtrl" , js:"/script/sl/finance/paymentReportList.js" },
  
            // 智能要件柜
            // 要件柜管理
            {templateUrl:"/template/element-box/box/boxList.html", state:"boxList",  ctrl:"boxListCtrl", js:"/script/element-box/box/boxList.js" },
            {templateUrl:"/template/element-box/box/openBoxList.html", state:"openBoxList",  ctrl:"openBoxListCtrl", js:"/script/element-box/box/openBoxList.js",params:{"pid":"0"} },
          //保单录入
			{templateUrl:"/template/sl/placing/liftingPlacing.html", state:"liftingPlacing",  ctrl:"liftingPlacingCtrl", js:"/script/sl/placing/liftingPlacing.js",params:{"orderNo":"0"} },
            // 要件管理
            {templateUrl:"/template/element-box/ele/eleAccessList.html", state:"eleAccessList",  ctrl:"eleAccessListCtrl", js:"/script/element-box/ele/eleAccessList.js" },
            {templateUrl:"/template/element-box/ele/eleAccessDetail.html", state:"eleAccessDetail",  ctrl:"eleAccessDetailCtrl", js:"/script/element-box/ele/eleAccessDetail.js",params:{"id":"0","pid":"0"} },
            // 审批设置
            {templateUrl:"/template/element-box/audit/auditList.html", state:"auditList",  ctrl:"auditListCtrl", js:"/script/element-box/audit/auditList.js" },
            {templateUrl:"/template/element-box/audit/auditDetail.html", state:"auditDetail",  ctrl:"auditDetailCtrl", js:"/script/element-box/audit/auditDetail.js",params:{"id":"0"} },
            //贷后列表
			{templateUrl:"/template/sl/finance/afterLoanList.html", state:"afterLoanList",  ctrl:"afterLoanListCtrl", js:"/script/sl/finance/afterLoanList.js",params:{"orderNo":"0"} },
			//贷后详情 -新的陕国投
			{templateUrl:"/template/sl/finance/afterLoanDetailIndex.html", state:"afterLoanDetail",  ctrl:"afterLoanDetailCtrl", js:"/script/sl/finance/afterLoanDetail.js",params:{"orderNo":"0","repaymentType":"0","cityCode":"0","productCode":"0"} },
			//贷后详情-旧的
			{templateUrl:"/template/sl/finance/afterLoanDetailIndex2.html", state:"afterLoanDetail2",  ctrl:"afterLoanDetailCtrl2", js:"/script/sl/finance/afterLoanDetail2.js",params:{"orderNo":"0","repaymentType":"0","cityCode":"0","productCode":"0"} }
			 
			]);
		

		
		
		//配置
		app.constant('productDataList', [
			//列表
			{templateUrl:"/template/product-data/list/productDataList.html", state:"productDataList", ctrl:"productDataListCtrl" , js:"/script/product-data/list/productDataList.js" , params:{"productCode":"0","productName":"0"} },
			//详情
			{templateUrl:"/template/product-data/detail/productDataDetail.html", state:"productDataDetail", ctrl:"productDataDetailCtrl" , js:"/script/product-data/detail/productDataDetail.js" , params:{"orderNo":"0","productCode":"0","processId":"0","tblName":"0"} },  
			//编辑
			{templateUrl:"/template/product-data/edit/productDataEdit.html", state:"productDataEdit", ctrl:"productDataEditCtrl" , js:"/script/product-data/edit/productDataEdit.js" , params:{"orderNo":"0","productCode":"0" ,"processId":"0","tblName":"0"} },
			
			{templateUrl:"/template/order-common/orderCommonEdit.html", state:"orderCommonEdit", ctrl:"orderCommonEditCtrl" , js:"/script/order-common/orderCommonEdit.js" , params:{"orderNo":"0","cityCode":"0","productCode":"0" ,"processId":"0"} },
		]);
		
		//套打合同
		app.constant('toolsList', [
			{templateUrl:"/template/sl/tools/elcSignature.html", state:"elcSignature", ctrl:"elcSignature" , js:"/script/sl/tools/elcSignature.js" },
			{templateUrl:"/template/sl/tools/contractList.html", state:"contractList", ctrl:"contractListCtrl" , js:"/script/sl/tools/contractList.js" },
			{templateUrl:"/template/sl/tools/contractEdit.html", state:"contractEdit", ctrl:"contractEditCtrl" , js:"/script/sl/tools/contractEdit.js",params:{"id":"0"} }
		]);
		
	};

});