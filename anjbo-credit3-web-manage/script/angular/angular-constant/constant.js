define(function(require, exports, module) {

	exports.extend = function(app) {

		app.constant('noticeId', "noticeId1");

		app.constant('notice', "  这是个公告这是个公告这是个公告<br/>这是个公告这是个公告这是个公告这是个公告这是个公告这是个公告这是个公告这是个公告这是个公告  ");

		app.constant('menuList', [

			//列表页面配置
			{
				templateUrl: "/template/page/agencyPageList.html",
				state: "agencyPageList",
				ctrl: "agencyPageListCtrl",
				js: "/script/page/agencyPageList.js",
				params: {
					"productCode": "0"
				}
			},
			
			//编辑页面配置
			{
				templateUrl: "/template/page/agencyPageEdit.html",
				state: "agencyPageEdit",
				ctrl: "agencyPageEditCtrl",
				js: "/script/page/agencyPageEdit.js",
				params: {
					"orderNo": "0",
					"productCode": "0",
					"processId": "0",
					"cityCode": "0",
					"state":"0"
				}
			},
			
			//详情页面配置
			{
				templateUrl: "/template/page/agencyPageDetail.html",
				state: "agencyPageDetail",
				ctrl: "agencyPageDetailCtrl",
				js: "/script/page/agencyPageDetail.js",
				params: {
					"orderNo": "0",
					"productCode": "0",
					"processId": "0",
					"cityCode": "0",
					"state":"0"
				}
			},

			//权限列表
			{
				templateUrl: "/template/authority/authority.html",
				state: "authority",
				ctrl: "authorityCtrl",
				js: "/script/authority/authority.js",
				params: {
					"uid": "0",
					"roleId": "0",
					"agencyId": "0",
					"showName": "0"
				}
			},

			//用户管理
			//用户列表
			{
				templateUrl: "/template/user/userList.html",
				state: "userList",
				ctrl: "userListCtrl",
				js: "/script/user/userList.js"
			},
			//用户列表
			{
				templateUrl: "/template/user/userEdit.html",
				state: "userEdit",
				ctrl: "userEditCtrl",
				js: "/script/user/userEdit.js",
				params: {
					"uid": "0"
				}
			},
			//角色列表
			{
				templateUrl: "/template/user/roleList.html",
				state: "roleList",
				ctrl: "roleListCtrl",
				js: "/script/user/roleList.js"
			},

			//机构管理
			//机构列表
			{
				templateUrl: "/template/agency/agencyList.html",
				state: "agencyList",
				ctrl: "agencyListCtrl",
				js: "/script/agency/agencyList.js"
			},
			//添加编辑机构
			{
				templateUrl: "/template/agency/agencyEdit.html",
				state: "agencyEdit",
				ctrl: "agencyEditCtrl",
				js: "/script/agency/agencyEdit.js",
				params: {
					"agencyId": "0"
				}
			},
			//机构类型列表
			{
				templateUrl: "/template/agency/agencyTypeList.html",
				state: "agencyTypeList",
				ctrl: "agencyTypeListCtrl",
				js: "/script/agency/agencyTypeList.js"
			},
			//机构类型收费标准列表
			{
				templateUrl: "/template/agency/agencyTypeFeescaleList.html",
				state: "agencyTypeFeescaleList",
				ctrl: "agencyTypeFeescaleListCtrl",
				js: "/script/agency/agencyTypeFeescaleList.js",
				params: {
					"agencyTypeId": "0",
					"agencyTypeName": "0"
				}
			},
			//添加修改机构类型收费标准
			//{templateUrl:"/template/agency/agencyTypeFeescaleEdit.html", state:"agencyTypeFeescaleEdit", ctrl:"agencyTypeFeescaleEditCtrl" , js:"/script/agency/agencyTypeFeescaleEdit.js", params:{"agencyTypeId":"0","agencyTypeFeescaleId":"0","agencyTypeName":"0"}},
			{
				templateUrl: "/template/agency/agencyTypeFeescaleEdit.html",
				state: "agencyTypeFeescaleEdit",
				ctrl: "agencyTypeFeescaleEditCtrl",
				js: "/script/agency/agencyTypeFeescaleEdit.js",
				params: {
					"agencyTypeId": "0",
					"agencyTypeFeescaleId": "0",
					"agencyTypeName": "0",
					"productionid": "0",
					"agencyId": "0",
					"productId": "0",
					"productCode": "0",
					"orderNo": "0",
					"detail": "0"
				}
			},
			//资金方列表
			{
				templateUrl: "/template/agency/fundList.html",
				state: "fundList",
				ctrl: "fundListCtrl",
				js: "/script/agency/fundList.js"
			},
			//新增编辑资金方
			{
				templateUrl: "/template/agency/fundEdit.html",
				state: "fundEdit",
				ctrl: "fundEditCtrl",
				js: "/script/agency/fundEdit.js",
				params: {
					"fundId": "0"
				}
			},
			//资金成本列表
			{
				templateUrl: "/template/agency/fundCostList.html",
				state: "fundCostList",
				ctrl: "fundCostListCtrl",
				js: "/script/agency/fundCostList.js",
				params: {
					"fundId": "0",
					"fundName": "0"
				}
			},
			//资金方业务模型
			{
				templateUrl: "/template/agency/fundBusinessModelEdit.html",
				state: "fundBusinessModelEdit",
				ctrl: "fundBusinessModelEditCtrl",
				js: "/script/agency/fundBusinessModelEdit.js",
				params: {
					"fundId": "0",
					"fundName": "0"
				}
			},
			//添加编辑资金成本
			{
				templateUrl: "/template/agency/fundCostEdit.html",
				state: "fundCostEdit",
				ctrl: "fundCostEditCtrl",
				js: "/script/agency/fundCostEdit.js",
				params: {
					"prductId": "0",
					"fundId": "0",
					"fundName": "0",
					"fundCostId": "0"
				}
			},
			//机构成员列表
			{
				templateUrl: "/template/agency/memberList.html",
				state: "agencyMemberList",
				ctrl: "agencyMemberListCtrl",
				js: "/script/agency/memberList.js",
				params: {
					"agencyId": "0",
					"agencyName": ""
				}
			},

			//待签约机构
			{
				templateUrl: "/template/agency/list/waitSignAgencyList.html",
				state: "waitSignAgency",
				ctrl: "waitSignAgencyCtrl",
				js: "/script/agency/list/waitSignAgencyList.js",
				params: {
					"productCode": "0",
					"productName": "0"
				}
			},
			//机构申请公共编辑页面
			{
				templateUrl: "/template/agency/edit/agencyPublicEdit.html",
				state: "agencyPublicEdit",
				ctrl: "agencyPublicEditCtrl",
				js: "/script/agency/edit/agencyPublicEdit.js",
				params: {
					"orderNo": "0",
					"productCode": "0",
					"processId": "0",
					"tblName": "0",
					"cityCode": "0"
				}
			},
			//机构详情
			{
				templateUrl: "/template/agency/detail/agencyPublicDetail.html",
				state: "agencyDataDetail",
				ctrl: "agencyDataDetailCtrl",
				js: "/script/agency/detail/agencyDataDetail.js",
				params: {
					"orderNo": "0",
					"productCode": "0",
					"processId": "0",
					"tblName": "0",
					"cityCode": "0"
				}
			},
			//维护机构编辑页
			{
				templateUrl: "/template/agency/edit/agencyMaintainEdit.html",
				state: "agencyMaintainEdit",
				ctrl: "agencyMaintainEditCtrl",
				js: "/script/agency/edit/agencyMaintainEdit.js",
				params: {
					"agencyId": "0",
					"productCode": "0",
					"orderNo": "0",
					"tblName": "0",
					"source": "0"
				}
			},
			//维护机构详情页
			{
				templateUrl: "/template/agency/detail/agencyMaintainDetail.html",
				state: "agencyMaintainDetail",
				ctrl: "agencyMaintainDetailCtrl",
				js: "/script/agency/detail/agencyMaintainDetail.js",
				params: {
					"agencyId": "0",
					"productCode": "0",
					"orderNo": "0",
					"tblName": "0"
				}
			},

			//产品管理
			//产品列表
			{
				templateUrl: "/template/product/productList.html",
				state: "productList",
				ctrl: "productListCtrl",
				js: "/script/product/productList.js"
			},
			//添加编辑产品
			{
				templateUrl: "/template/product/productEdit.html",
				state: "productEdit",
				ctrl: "productEditCtrl",
				js: "/script/product/productEdit.js",
				params: {
					"productId": "0",
					agencyId: "0"
				}
			},

			//添加银行
			{
				templateUrl: "/template/product/bankList.html",
				state: "bankList",
				ctrl: "bankListCtrl",
				js: "/script/product/bankList.js"
			},
			//添加支行
			{
				templateUrl: "/template/product/bankSubList.html",
				state: "bankSubList",
				ctrl: "bankSubListCtrl",
				js: "/script/product/bankSubList.js"
			},
			//添加字段
			{
				templateUrl: "/template/product/dictList.html",
				state: "dictList",
				ctrl: "dictListCtrl",
				js: "/script/product/dictList.js"
			},

			//添加机构
			{
				templateUrl: "/template/organizationAdd.html",
				state: "organizationAdd",
				ctrl: "organizationAddCtrl",
				js: "/script/organizationAdd.js"
			},
			//编辑节点
			{
				templateUrl: "/template/nodeEdit.html",
				state: "nodeEdit",
				ctrl: "nodeEditCtrl",
				js: "/script/nodeEdit.js"
			},
			//添加资金方
			{
				templateUrl: "/template/fundAdd.html",
				state: "fundAdd",
				ctrl: "fundAddCtrl",
				js: "/script/fundAdd.js"
			},
			//添加用户
			{
				templateUrl: "/template/userAdd.html",
				state: "userAdd",
				ctrl: "userAddCtrl",
				js: "/script/userAdd.js"
			},
			//订单列表
			{
				templateUrl: "/template/sl/order/orderList.html",
				state: "orderList1",
				ctrl: "orderListCtrl",
				js: "/script/sl/order/orderList.js"
			},
			//订单详情
			{
				templateUrl: "/template/sl/order/orderDetail.html",
				state: "orderDetail",
				ctrl: "orderDetailCtrl",
				js: "/script/sl/order/orderDetail.js",
				params: {
					"orderNo": "0",
					"view": ""
				}
			},
			//订单编辑
			{
				templateUrl: "/template/sl/order/orderEdit.html",
				state: "orderEdit",
				ctrl: "orderEditCtrl",
				js: "/script/sl/order/orderEdit.js",
				params: {
					"orderNo": "0"
				}
			},
			//公证编辑
			{
				templateUrl: "/template/sl/process/notarizationEdit.html",
				state: "orderEdit.notarizationEdit",
				ctrl: "notarizationEditCtrl",
				js: "/script/sl/process/notarizationEdit.js",
				params: {
					"orderNo": "0"
				}
			},
			//面签编辑
			{
				templateUrl: "/template/sl/process/facesignEdit.html",
				state: "orderEdit.facesignEdit",
				ctrl: "facesignEditCtrl",
				js: "/script/sl/process/facesignEdit.js",
				params: {
					"orderNo": "0"
				}
			},
			//经理审批编辑
			{
				templateUrl: "/template/sl/process/managerAuditEdit.html",
				state: "orderEdit.managerAuditEdit",
				ctrl: "managerAuditEditCtrl",
				js: "/script/sl/process/managerAuditEdit.js",
				params: {
					"orderNo": "0"
				}
			},
			//风控初审编辑
			{
				templateUrl: "/template/sl/risk/auditFirstEdit.html",
				state: "orderEdit.auditFirstEdit",
				ctrl: "auditFirstEditCtrl",
				js: "/script/sl/risk/auditFirstEdit.js",
				params: {
					"orderNo": "0"
				}
			},
			//风控终审编辑
			{
				templateUrl: "/template/sl/risk/auditFinalEdit.html",
				state: "orderEdit.auditFinalEdit",
				ctrl: "auditFinalEditCtrl",
				js: "/script/sl/risk/auditFinalEdit.js",
				params: {
					"orderNo": "0"
				}
			},
			//首席风险官编辑
			{
				templateUrl: "/template/sl/risk/auditOfficerEdit.html",
				state: "orderEdit.auditOfficerEdit",
				ctrl: "auditOfficerEditCtrl",
				js: "/script/sl/risk/auditOfficerEdit.js",
				params: {
					"orderNo": "0"
				}
			},
			//分配资金方编辑
			{
				templateUrl: "/template/sl/risk/allocationFundEdit.html",
				state: "orderEdit.allocationFundEdit",
				ctrl: "allocationFundEditCtrl",
				js: "/script/sl/risk/allocationFundEdit.js",
				params: {
					"orderNo": "0"
				}
			},
			//指派还款专员编辑
			{
				templateUrl: "/template/sl/process/repaymentMemberEdit.html",
				state: "orderEdit.repaymentMemberEdit",
				ctrl: "repaymentMemberEditCtrl",
				js: "/script/sl/process/repaymentMemberEdit.js",
				params: {
					"orderNo": "0"
				}
			},
			//要件校验信息编辑
			{
				templateUrl: "/template/sl/element/elementEdit.html",
				state: "orderEdit.elementEdit",
				ctrl: "elementEditCtrl",
				js: "/script/sl/element/elementEdit.js",
				params: {
					"orderNo": "0"
				}
			},
			//收利息信息编辑
			{
				templateUrl: "/template/sl/finance/lendingHarvestEdit.html",
				state: "orderEdit.lendingHarvestEdit",
				ctrl: "lendingHarvestEditCtrl",
				js: "/script/sl/finance/lendingHarvestEdit.js",
				params: {
					"orderNo": "0"
				}
			},
			//付利息信息编辑
			{
				templateUrl: "/template/sl/finance/lendingPayEdit.html",
				state: "orderEdit.lendingPayEdit",
				ctrl: "lendingPayEditCtrl",
				js: "/script/sl/finance/lendingPayEdit.js",
				params: {
					"orderNo": "0"
				}
			},
			//发放款指令信息编辑
			{
				templateUrl: "/template/sl/finance/lendingInstructionsEdit.html",
				state: "orderEdit.lendingInstructionsEdit",
				ctrl: "lendingInstructionsEditCtrl",
				js: "/script/sl/finance/lendingInstructionsEdit.js",
				params: {
					"orderNo": "0"
				}
			},
			//放款信息编辑
			{
				templateUrl: "/template/sl/finance/lendingEdit.html",
				state: "orderEdit.lendingEdit",
				ctrl: "lendingEditCtrl",
				js: "/script/sl/finance/lendingEdit.js",
				params: {
					"orderNo": "0"
				}
			},
			//回款（首期）编辑
			{
				templateUrl: "/template/sl/finance/receivableForEdit.html",
				state: "orderEdit.receivableForEdit",
				ctrl: "receivableForEditCtrl",
				js: "/script/sl/finance/receivableForEdit.js",
				params: {
					"orderNo": "0"
				}
			},
			//回款（首期）编辑
			{
				templateUrl: "/template/sl/finance/receivableForsEdit.html",
				state: "orderEdit.receivableForsEdit",
				ctrl: "receivableForsEditCtrl",
				js: "/script/sl/finance/receivableForsEdit.js",
				params: {
					"orderNo": "0"
				}
			},
			//要件退还编辑
			{
				templateUrl: "/template/sl/element/elementReturnEdit.html",
				state: "orderEdit.elementReturnEdit",
				ctrl: "elementReturnEditCtrl",
				js: "/script/sl/element/elementReturnEdit.js",
				params: {
					"orderNo": "0"
				}
			},

			{
				templateUrl: "/template/detail_index.html",
				state: "detailIndex",
				ctrl: "detailIndexCtrl",
				js: "/script/detail_index.js",
				params: {
					"orderNo": "0"
				}
			},
			{
				templateUrl: "/template/detail.html",
				state: "detailIndex.detail",
				ctrl: "detailCtrl",
				js: "/script/detail.js",
				params: {
					"orderNo": "0"
				}
			},
			{
				templateUrl: "/template/apply.html",
				state: "detailIndex.apply",
				ctrl: "applyCtrl",
				js: "/script/apply.js"
			},
			{
				templateUrl: "/template/list.html",
				state: "list",
				ctrl: "listCtrl",
				js: "/script/list.js"
			},
			{
				templateUrl: "/template/permission.html",
				state: "permission",
				ctrl: "permissionCtrl",
				js: "/script/permission.js"
			},
			//编辑订单
			{
				templateUrl: "/template/product/orderListBase.html",
				state: "orderListBase",
				ctrl: "orderListBaseCtrl",
				js: "/script/product/orderListBase.js"
			},
			//套打合同
			{
				templateUrl:"/template/tools/contractFieldEdit.html", 
				state:"contractFieldEdit",
				ctrl:"contractFieldEditCtrl" , 
				js:"/script/tools/contractFieldEdit.js"
			},
			{
				templateUrl:"/template/tools/contractFieldShow.html", 
				state:"contractFieldShow",
				ctrl:"contractFieldShowCtrl" , 
				js:"/script/tools/contractFieldShow.js",
				params: {
					"id": "0"
				}
			},
			{
				templateUrl:"/template/tools/contractGroupList.html", 
				state:"contractGroupList",
				ctrl:"contractGroupListCtrl" , 
				js:"/script/tools/contractGroupList.js"
			}
		]);

	};

});