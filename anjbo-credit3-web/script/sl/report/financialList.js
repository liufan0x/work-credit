angular.module("anjboApp", ['bsTable']).controller("financialCtrl", function ($scope, $http) {
	$scope.page = new Object();
	

    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        return $scope.page;
    }
    
    getList();
    
    $scope.query = function(){
    	if ($scope.page.cityCode=='全国') {
    		$scope.page.cityCode='';
		}
        $("#financialList").bootstrapTable('refresh', {url: '/credit/report/financial/v/query',pageNumber:1});
    }
    
  //清空时间选择器
    $scope.clearDate = function (event, obj) {
        $(event.target).prev(".form_date").val("");
        if (obj == 'start') {
            $scope.page.startLendingTime = null;
        } else if (obj == 'end') {
            $scope.page.endLendingTime= null;
        }
    }
    $scope.clearDate1 = function (event, obj) {
        $(event.target).prev(".form_date").val("");
        if (obj == 'start') {
            $scope.page.startPlanPaymentTime = null;
        } else if (obj == 'end') {
            $scope.page.endPlanPaymentTime= null;
        }
    }
    $scope.clearDate2 = function (event, obj) {
        $(event.target).prev(".form_date").val("");
        if (obj == 'start') {
            $scope.page.startPayMentAmountDate = null;
        } else if (obj == 'end') {
            $scope.page.endPayMentAmountDate= null;
        }
    }
    $scope.clearDate3 = function (event, obj) {
        $(event.target).prev(".form_date").val("");
        if (obj == 'start') {
            $scope.page.startPayTime = null;
        } else if (obj == 'end') {
            $scope.page.endPayTime= null;
        }
    }
    
    $scope.clearDateSel = function(){
        $scope.page1.selTime = ''
    }
    
    function getList(){
    	$http({
    		method:'POST',
    		url:'/credit/order/base/v/selectionConditions'
    	}).success(function(data){
    		 $scope.conditions = data.data;
    		 $http.post(`/credit/report/achievementStatistics/v/city`).then(function(res){
    			 $scope.orderCitys = res.data.data
                 $scope.page.cityCode = $scope.orderCitys[0].code
                 $scope.productList = $scope.conditions.citys[0].productList;
                 $scope.productList.pop()
                 $scope.productList[0].productName = '全部'
                 $scope.stateList = $scope.conditions.citys[0].productList[0].stateList;
    		 })
    	})
    }
    
	$scope.financialList = {
            options: {
                method: "post",
                url: "/credit/report/financial/v/query",    ///credit/report/statistics/v/selectOutPayment
                queryParams: getParams,
                sidePagination: 'server',
                undefinedText: "0",
                cache: false,
                striped: true,
                pagination: true,
                pageNumber: 1,
                pageSize: 10,
                pageList: [10, 20, "all"],
                showColumns: true,
                exportTypes: ['excel'],
                showRefresh: false,
                columns: [{
                    title: '分配金融机构',
                    field: 'fundName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '客户名',
                    field: 'customerName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '借款金额(万元)',
                    field: 'borrowingAmount',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '借款期限(天)',
                    field: 'borrowingDay',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '业务类型',
                    field: 'productName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '出款时间',
                    field: 'lendingTime',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '应还款时间',
                    field: 'planPaymentTime',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '实际还款时间',
                    field: 'payMentAmountDate',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '费用率(%/天)',
                    field: 'rate',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '收取费用金额(元)',
                    field: 'receivableInterestMoney',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '收取费用时间',
                    field: 'interestTime',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '客户逾期利率(%/天)',
                    field: 'overdueRate',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '客户逾期天数(天)',
                    field: 'datediff',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '罚息金额(元)',
                    field: 'penaltyPayable',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '罚息收取日期(天)',
                    field: 'payTime',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '服务费(元)',
                    field: 'serviceCharge',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '返佣金额(元)',
                    field: 'rebateMoney',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '返佣时间',
                    field: 'rebateTime',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '城市',
                    field: 'cityName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '分公司',
                    field: 'branchCompany',
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
                }]
            }
        };
	
});