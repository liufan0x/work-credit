angular.module("anjboApp", ['bsTable']).controller("customerCtrl",function($scope,$compile,$http,$state,box,process,parent){

    $scope.page = new Object();
    
    $scope.reportType = "0";
    function getParams(data){
    	$scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        return $scope.page;
    }
    
    $scope.$watch("reportType",function(newValue, oldValue){
    	$scope.query();
    });
    
    $scope.clearDate=function(event){
	    $(event.target).prev(".form_date").val("");
	}
    
    $scope.query = function(){
    	if($scope.reportType==0){  //客户信息统计
    		$("#customerTable").bootstrapTable('refresh', {url: '/credit/report/statistics/v/customer'}); 
    		$("#timeShow").html("放款时间：");
    		$("#zdyShow").html("放款");
    	}else if($scope.reportType==1){   //风控审批统计
    		$("#managementTable").bootstrapTable('refresh', {url: '/credit/report/statistics/v/managementExamination'});
    		$("#timeShow").html("初审时间：");
    		$("#zdyShow").html("初审");
    	}else if($scope.reportType == 2){  //订单退回统计
    		$("#returnBackTable").bootstrapTable('refresh', {url: '/credit/report/statistics/v/returnBack'});
    		$("#timeShow").html("退回时间：");
    		$("#zdyShow").html("退回");	
    	}
    }
    //客户信息
    $scope.customerList = {
            options: {
                method:"post",
                url:"/credit/report/statistics/v/customer",
                queryParams:getParams,
                sidePagination:'server',
                undefinedText:"-",
                cache: false,
                striped: true,
                pagination: true,
                pageNumber: 1,
                pageSize: 15,
                pageList: [20,"all"],
                showColumns: true,
                //exportDataType:'all',
                showRefresh: false,
                columns: [{
                    title: '城市',
                    field: 'cityName',
                    align: 'right',
                    valign: 'bottom'
                }, {
                    title: '业务类型',
                    field: 'productName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '资金方代号',
                    field: 'fundCodes',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '分公司（营业部）',
                    field: 'branchCompany',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '客户姓名',
                    field: 'customerName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '借款金额（万元）',
                    field: 'borrowingAmount',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '借款期限（天）',
                    field: 'borrowingDay',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '费率',
                    field: 'rate',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '合作机构',
                    field: 'cooperativeAgencyName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '渠道经理',
                    field: 'channelManagerName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '受理员',
                    field: 'acceptMemberName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '新贷款银行-支行',
                    field: 'loanBankName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '原贷款银行-支行',
                    field: 'oldLoanBankName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '放款时间',
                    field: 'lendingTime',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '预计回款时间',
                    field: 'planPaymentTime',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '距离回款天数（天）',
                    field: 'distancePaymentDay',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '当前状态',
                    field: 'state',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '当前订单处理人',
                    field: 'currentHandler',
                    align: 'center',
                    valign: 'bottom'
                }]
            }};

      //退回统计
      $scope.returnBackList = {
            options: {
                method:"post",
                url:"/credit/report/statistics/v/returnBack",
                queryParams:getParams,
                sidePagination:'server',
                undefinedText:"-",
                cache: false,
                striped: true,
                pagination: true,
                pageNumber: 1,
                pageSize: 15,
                pageList: [20,'all' ],
                showColumns: true,
               // exportDataType:'all',
                showRefresh: false,
                columns: [{
                    title: '城市',
                    field: 'cityName',
                    align: 'right',
                    valign: 'bottom'
                }, {
                    title: '业务类型',
                    field: 'productName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '客户姓名',
                    field: 'customerName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '退回时间',
                    field: 'bankTimeStr',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '退回原因',
                    field: 'backReason',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '退回人',
                    field: 'source',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '重新处理时间',
                    field: 'beginHandleTimeStr',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '受理员',
                    field: 'acceptMemberName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '分公司（营业部）',
                    field: 'branchCompany',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '当前状态',
                    field: 'state',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '当前订单处理人',
                    field: 'currentHandler',
                    align: 'center',
                    valign: 'bottom'
                }]
            }};

      
    //风控审批统计
      $scope.managementList = {
            options: {
                method:"post",
                url:"/credit/report/statistics/v/managementExamination",
                queryParams:getParams,
                sidePagination:'server',
                undefinedText:"-",
                cache: false,
                striped: true,
                pagination: true,
                pageNumber: 1,
                pageSize: 15,
                pageList: [20,'all'],
                showColumns: true,
               // exportDataType:'all',
                showRefresh: false,
                columns: [{
                    title: '城市',
                    field: 'cityName',
                    align: 'right',
                    valign: 'bottom'
                }, {
                    title: '业务类型',
                    field: 'productName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '客户姓名',
                    field: 'customerName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '借款金额（万元）',
                    field: 'borrowingAmount',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '借款期限（天）',
                    field: 'borrowingDay',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '受理员',
                    field: 'acceptMemberName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '初审操作时间',
                    field: 'auditTimeStr',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '初审处理耗费时长（小时）',
                    field: 'firstAuditTimeStr',
                    align: 'center',
                    valign: 'bottom'
                },{
                    title: '终审处理时间（小时）',
                    field: 'finalAuditTimeStr',
                    align: 'center',
                    valign: 'bottom'
                }]
            }};
    
});