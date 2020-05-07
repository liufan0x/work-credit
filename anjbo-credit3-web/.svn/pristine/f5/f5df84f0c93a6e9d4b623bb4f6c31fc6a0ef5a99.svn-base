angular.module("anjboApp", ['bsTable']).controller("orderDetailListCtrl",function($scope,$compile,$cookies,$http,$state,$timeout,$filter,box,process,parent,route){
    
	$scope.page = new Object();
	$scope.page.start = 0;
	$scope.page.pageSize = 15;
    $scope.page.lendingTime = null;
    console.log(route.getParams());
    $scope.page.cityName = route.getParams().cityName!='全国'?route.getParams().cityName:null;
    $scope.page.productCode = route.getParams().productCode!=0?String(route.getParams().productCode):null;
    $scope.page.startTime = route.getParams().startTime!=0?route.getParams().startTime:null;
    $scope.page.endTime = route.getParams().endTime!=0?route.getParams().endTime:null;
    $scope.page.lendingTimeStr = route.getParams().lendingTime!=0?route.getParams().lendingTime:null;
    $scope.page.searchTimeType = "1";

    $http({
        method: 'POST',
        url:'/credit/order/base/v/selectionConditions'
    }).success(function(data){
        $scope.conditions = data.data;        
        $scope.orderCitys = new Array();
        angular.forEach($scope.conditions.citys, function(data,index){
            if(index == 0){
                data.cityName = '请选择';
            }
            angular.forEach(data.productList, function(data,index){
                if(index == 0){
                    data.productName = '请选择';
                }
                angular.forEach(data.stateList, function(data,index){
                    if(index == 0){
                        data.stateName = '请选择';
                    }
                });
            });
            $scope.orderCitys.push(data);
        });
        $scope.productList = $scope.conditions.citys[0].productList;
        $scope.stateList = $scope.conditions.citys[0].productList[0].stateList;
    })
    
    $scope.getAcceptList = function(cityCode){
	    //获取关联受理员列表
		$http({
			method: 'post',
			url: "/credit/user/user/v/searchByType2",
			data:{"name":"提单","cityCode":cityCode}
		}).success(function(data) {
			if(data.data){
				$scope.acceptList = data.data;
			}
		})
    }
    $scope.getAcceptList();
    
    $scope.getBranchCompanyList = function(){
    	 //获取分公司
		$http({
			method: 'post',
			url: "/credit/data/dict/v/search",
			data:{"type":"branchCompany"}
		}).success(function(data) {
			if(data.data){
				$scope.branchCompanyList = data.data;
			}
		})
    }
    $scope.getBranchCompanyList();
    
    $scope.getAgencyList = function(){
   	 //获取合作机构
		$http({
			method: 'post',
			url: "/credit/user/agency/v/search",
			data:{"status":1,"signStatus":2}
		}).success(function(data) {
			if(data.data){
				$scope.agencyList = data.data;
			}
		})
   }
   $scope.getAgencyList();
    
    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        $scope.page.sortName = data.sort == 'distancePaymentDay' ? 'planPaymentTime' : data.sort;
        $scope.page.sortOrder = data.order;
        $cookies.putObject("pageParams",$scope.page);
        return $scope.page;
    }
    
    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url: '/credit/report/achievementStatistics/v/selectOrderDetailList',pageNumber:1}); 
    }
    
    var columnSwitchList = $cookies.getObject("columnSwitch");
    if(!columnSwitchList){
    	columnSwitchList = {
    		"id":false,
    		"cityName":true,
    		"branchCompany":true,
    		"source":true,
    		"cooperativeAgencyName":true,
    		"channelManagerName":true,
    		"acceptMemberName":true,
    		"customerName":true,
    		"productName":true,
    		"customerTypeName":true,
    		"businessName":true,
    		"borrowingAmount":true,
    		"borrowingDay":true,
    		"lendingTime":true,
    		"planPaymentTime":true,
    		"payMentAmountDate":true,
    		"rebateMoney":true,
    		"otherPoundage":true,
    		"customsPoundage":true,
    		"rate":true,
    		"interest":true,
    		"serviceCharge":true,
    		"actualLoanDay":true,
    		"tqDatediff":true,
    		"yqDatediff":true,
    		"overdueRate":true,
    		"fine":true,
    		"interestTime":true,
    		"payTime":true,
    		"rebateTime":true,
    		"oldLoanBankAndSub":true,
    		"newLoanBankAndSub":true,
    		"state":true,
    		"currentHandler":true
    	};
    	$cookies.putObject("columnSwitch",columnSwitchList);
    }
    
    $scope.orderList = {
            options: {
                method:"post",
                url:"/credit/report/achievementStatistics/v/selectOrderDetailList",
                queryParams:getParams,
                sidePagination:'server',
                undefinedText:"-",
                cache: false,
                striped: true,
                pagination: true,
				pageNumber: ($scope.page.start/$scope.page.pageSize)+1,
				pageSize: $scope.page.pageSize,
                pageList: ['All'],
                showColumns: true,
                exportTypes: ['excel'],
                showRefresh: false,
                onClickRow:function(row,$element,field){
                	$element.toggleClass("bule-bg");
                },
                onColumnSwitch:function(field,checked){
                	columnSwitchList[field] = checked;
                	$cookies.putObject("columnSwitch",columnSwitchList);
                },
                columns: [{
                    title: '序号',
                    field: 'id',
                    align: 'right',
                    valign: 'bottom',
                    visible:columnSwitchList.id
                }, {
                    title: '城市',
                    field: 'cityName',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.cityName
                }, {
                    title: '分公司',
                    field: 'branchCompany',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.branchCompany
                }, {
                    title: '订单来源',
                    field: 'source',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '合作机构',
                    field: 'cooperativeAgencyName',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.cooperativeAgencyName
                }, {
                    title: '渠道经理',
                    field: 'channelManagerName',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.channelManagerName
                }, {
                    title: '受理员',
                    field: 'acceptMemberName',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.acceptMemberName
                }, {
                    title: '借款人',
                    field: 'customerName',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.customerName
                }, {
                    title: '产品名称',
                    field: 'productName',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.productName
                }, {
                    title: '客户类型',
                    field: 'customerType',
                    align: 'center',
                    valign: 'bottom',
                    formatter:function(value, row, index){
                    	switch (value){
                    		case 1:  return '个人';
                    		case 2:  return '小微企业';
                    		default: return '-';
                    	}
                    },
                    visible:columnSwitchList.customerType
                }, {
                    title: '业务标准',
                    field: 'business',
                    align: 'center',
                    valign: 'bottom',
                    formatter:function(value, row, index){
                    	switch (value){
                    		case 1:  return '标准';
                    		case 2:  return '非标准';
                    		default: return '-';
                    	}
                    },
                    visible:columnSwitchList.business
                }, {
                    title: '借款金额（万）',
                    field: 'borrowingAmount',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.borrowingAmount
                }, {
                    title: '借款期限（天）',
                    field: 'borrowingDay',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.borrowingDay
                }, {
                    title: '放款时间',
                    field: 'lendingTime',
                    align: 'center',
                    valign: 'bottom',
                    formatter:function(value, row, index){
                    	return $filter('limitTo')(row.lendingTime,10);
                    },
                    visible:columnSwitchList.lendingTime
                }, {
                    title: '计划回款时间',
                    field: 'planPaymentTime',
                    align: 'center',
                    valign: 'bottom',
                    formatter:function(value, row, index){
                    	return $filter('limitTo')(row.planPaymentTime,10);
                    }
                }, {
                    title: '实际回款时间',
                    field: 'payMentAmountDate',
                    align: 'center',
                    valign: 'bottom',
                    formatter:function(value, row, index){
                    	return $filter('limitTo')(row.payMentAmountDate,10);
                    },
                    visible:columnSwitchList.payMentAmountDate
                }, {
                    title: '返佣额（元）',
                    field: 'rebateMoney',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.rebateMoney
                }, {
                    title: '其他金额（元）',
                    field: 'otherPoundage',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.otherPoundage
                }, {
                    title: '关外手续费（元）',
                    field: 'customsPoundage',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.customsPoundage
                }, {
                    title: '费率（%）',
                    field: 'rate',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.rate
                }, {
                    title: '利息（元）',
                    field: 'interest',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.interest
                }, {
                    title: '服务费（元）',
                    field: 'serviceCharge',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.serviceCharge
                }, {
                    title: '实际用款天数',
                    field: 'actualLoanDay',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.actualLoanDay
                }, {
                    title: '提前还款天数',
                    field: 'tqDatediff',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.tqDatediff
                }, {
                    title: '客户逾期天数',
                    field: 'yqDatediff',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.yqDatediff
                }, {
                    title: '逾期费率（%）',
                    field: 'overdueRate',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.overdueRate
                }, {
                    title: '罚息（元）',
                    field: 'fine',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.fine
                }, {
                    title: '收息日期',
                    field: 'interestTime',
                    align: 'center',
                    valign: 'bottom',
                    formatter:function(value, row, index){
                    	return $filter('limitTo')(row.interestTime,10);
                    },
                    visible:columnSwitchList.interestTime
                }, {
                    title: '罚息收取日期',
                    field: 'payTime',
                    align: 'center',
                    valign: 'bottom',
                    formatter:function(value, row, index){
                    	return $filter('limitTo')(row.payTime,10);
                    },
                    visible:columnSwitchList.payTime
                }, {
                    title: '返佣收取日期',
                    field: 'rebateTime',
                    align: 'center',
                    valign: 'bottom',
                    formatter:function(value, row, index){
                    	return $filter('limitTo')(row.rebateTime,10);
                    },
                    visible:columnSwitchList.rebateTime
                }, {
                    title: '原贷款银行-支行',
                    field: 'oldLoanBankAndSub',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.oldLoanBankAndSub
                }, {
                    title: '新贷款银行-支行',
                    field: 'newLoanBankAndSub',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.newLoanBankAndSub
                }, {
                    title: '当前订单状态',
                    field: 'state',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.state
                }, {
                    title: '当前处理人',
                    field: 'currentHandler',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.currentHandler
                }]
            }};

			 $timeout(function(){
				 $(".tooltip-toggle").tooltip({
			         html: true
		         });
			 })

});