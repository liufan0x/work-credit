angular.module("anjboApp", ['bsTable']).controller("orderListCtrl",function($scope,$compile,$cookies,$http,$state,$timeout,$filter,box,process,parent){
    
    $scope.page = $cookies.getObject("pageParams");
    if(!$scope.page){
    	$scope.page = new Object();
		$scope.page.start = 0;
		$scope.page.pageSize = 15;
    	$scope.page.type = "0";
    }
    
    $http({
        method: 'POST',
        url:'/credit/order/base/v/selectionConditionsByFund'
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

    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        $scope.page.sortName = data.sort;
        $scope.page.sortOrder = data.order;
        $cookies.putObject("pageParams",$scope.page);
        return $scope.page;
    }
    
    function relationFormatter(value, row, index){
        var html = "";
        if(row.relationOrderNo){
            html += '<a class="lookRelationOrder" href="javascript:void(0)">查看</a>&nbsp;&nbsp;';
        }else{
        	html = "--";
        }
        return html;
    }

    function operateFormatter(value, row, index) {
        return '<a class="lookOrder" href="javascript:void(0)">查看详情</a>&nbsp;&nbsp;';
    }

    window.relationEvents = {
        'click .lookRelationOrder': function (e, value, row, index) {
            $state.go("orderDetail",{'orderNo':row.relationOrderNo,"productCode":row.productCode , 'processId':""});
        }
    };
    
    window.operateEvents = {
        'click .lookOrder': function (e, value, row, index) {
            $state.go("orderDetail",{'orderNo':row.orderNo,"productCode":row.productCode,'processId':""});
        }
    };

    var url = "/credit/report/fund/v/list";
    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url: url,pageNumber:1}); 
    }
    
    var columnSwitchList = $cookies.getObject("columnSwitch");
    if(!columnSwitchList){
    	columnSwitchList = {
    		"id":false,
    		"contractNo":false,
    		"cityName":true,
    		"productName":true,
    		"customerName":true,
    		"borrowingAmount":true,
    		"borrowingDay":true,
    		"cooperativeAgencyName":true,
    		"channelManagerName":true,
    		"acceptMemberName":true,
    		"planPaymentTime":false,
    		"distancePaymentDay":false,
    		"previousHandler":false,
    		"previousHandleTime":false,
    		"state":true,
    		"source":false,
    		"currentHandler":true,
    		"relation":true,
    		"operate":true
    	};
    	$cookies.putObject("columnSwitch",columnSwitchList);
    }
    
    $scope.orderList = {
            options: {
                method:"post",
                url:url,
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
                showRefresh: false,
                onClickRow:function(row,$element,field){
                	$element.toggleClass("bule-bg");
                },
                onColumnSwitch:function(field,checked){
                	columnSwitchList[field] = checked;
                	$cookies.putObject("columnSwitch",columnSwitchList);
                },
                columns: [{
                    title: '城市',
                    field: 'cityName',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.cityName
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
                },  {
                    title: '客户姓名',
                    field: 'customerName',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.customerName
                }, {
                    title: '借款金额（万元）',
                    field: 'borrowingAmount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    visible:columnSwitchList.borrowingAmount
                }, {
                    title: '借款期限（天）',
                    field: 'borrowingDay',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    visible:columnSwitchList.borrowingDay
                }, {
                    title: '订单状态',
                    field: 'state',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.state
                },/* {
                    title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="点击查看关联订单">关联订单 <span class="help"></span></span>',
                    field: 'relation',
                    align: 'center',
                    valign: 'bottom',
                    events: relationEvents,
                    formatter: relationFormatter,
                    visible:columnSwitchList.relation
                },*/ {
                    title: '操作',
                    field: 'operate',
                    align: 'center',
                    valign: 'bottom',
                    events: operateEvents,
                    formatter: operateFormatter,
                    visible:columnSwitchList.operate
                }]
            }};

			 $timeout(function(){
				 $(".tooltip-toggle").tooltip({
			         html: true
		         });
			 })
});