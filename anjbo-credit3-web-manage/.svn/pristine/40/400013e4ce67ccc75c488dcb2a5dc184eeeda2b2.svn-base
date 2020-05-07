angular.module("anjboApp", ['bsTable']).controller("orderListBaseCtrl",function($scope,$http,$state,box,route,$cookies,$timeout){ 
	
    $scope.page = $cookies.getObject("pageParams");
    if(!$scope.page){
    	$scope.page = new Object();
		$scope.page.start = 0;
		$scope.page.pageSize = 15;
    	$scope.page.type = "0";
    	$scope.isAudit=false;
    }
    
    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        $scope.page.sortName = data.sort == 'distancePaymentDay' ? 'planPaymentTime' : data.sort;
        $scope.page.sortOrder = data.order;
        $cookies.putObject("pageParams",$scope.page);
        return $scope.page;
    }
    
    window.operateEvents = {
            'click .editOrder': function (e, value, row, index) {
            	 $scope.showPlaceOrder(value, row, index);
            }
     }
    
    $scope.showPlaceOrder = function(value, row, index){
        $scope.order = new Object();
        $scope.order.orderNo = row.orderNo;
        $scope.order.updateUid = row.updateUid;
        $scope.order.cityCode = row.cityCode;
        $scope.order.productCode = row.productCode;
        $scope.order.currentHandlerUid = row.currentHandlerUid;
        $scope.order.currentHandler = row.currentHandler;
        $scope.order.state = row.state;
        $scope.order.processId = row.processId;
        var editOrder = function(){
        	if(!$scope.orderForm.$valid){
        		$scope.isAudit=true;
        		alert("请正确填写所有信息");
        		return;
        	}
        	//修改订单
			$http({
				method: 'POST',
				url:'/credit/order/baseList/v/edit',
				data:$scope.order
			}).success(function(data){
				if(data.code == "SUCCESS"){
					$scope.query();
			        box.closeAlert();
				}
			}).error(function(){
				box.closeWaitAlert();
			})
        }
        
        angular.element("#processId").html('<select class="form-control" select2 style="width:150px;"  ng-model="order.processId" ng-model-text="order.state" name="processId" required ><option value="{{process.processId}}" ng-repeat="process in processList">{{process.processName}}</option></select><span class="inputError" ng-show="isAudit"><error class="text-danger" ng-show="orderForm.processId.$error.required"  >必选</error></span>');
        angular.element("#currentHandlerUid").html('<select class="form-control" select2 style="width:150px;"  ng-model="order.currentHandlerUid" ng-model-text="order.currentHandler" name="currentHandlerUid" required ><option value="{{user.uid}}" ng-repeat="user in userList">{{user.name}}</option></select><span class="inputError" ng-show="isAudit"><error class="text-danger" ng-show="orderForm.currentHandlerUid.$error.required"  >必选</error></span>');
        box.editAlertW500($scope,"修改订单",$("#editOrderId").html(),editOrder);
        $timeout(function(){
			 $(".tooltip-toggle").tooltip({
		         html: true
	         });
		 })
		
		$http({
			method: 'POST',
			data: {'productId':row.cityCode+row.productCode},
			url: '/credit/data/process/v/search'
		}).success(function(data) {
			$scope.processList = data.data;
		})
    }
    
    $http({
		method: 'POST',
		url: '/credit/user/user/v/search',
		data:{agencyId:"1"}
	}).success(function(data) {
		$scope.userList = data.data;
	})
   
    function operateFormatter(value, row, index) {    	
        return '<a class="editOrder" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';    
    }
    
	var url="/credit/order/baseList/v/page";
	
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
    $scope.fundList = {
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
                    title: '序号',
                    field: 'id',
                    align: 'right',
                    valign: 'bottom',
                    visible:columnSwitchList.id
                }, {
                    title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="根据城市代码、实际放款时间、业务类型代号自动生成的合同编号">合同编号 <span class="help"></span></span>',
                    field: 'contractNo',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.contractNo
                }, {
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
                }, {
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
                    title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="放款时间+借款期限（天数）">预计回款时间 <span class="help"></span></span>',
                    field: 'planPaymentTime',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(value, row, index){
                    	return $filter('limitTo')(row.planPaymentTime,10);
                    },
                    visible:columnSwitchList.planPaymentTime
                }, {
                    title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="预计回款时间-当前日期">距离回款天数（天） <span class="help"></span></span>',
                    field: 'distancePaymentDay',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    visible:columnSwitchList.distancePaymentDay,
                    formatter:function(value, row, index){
                    	if(row.processId == 'wanjie' || row.processId == 'pay'  || row.processId == 'elementReturn'){
                    		return "已回款";
                    	}else{
                    		return row.distancePaymentDay;
                    	}
                    }
                }, {
                    title: '上一节点处理人',
                    field: 'previousHandler',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.previousHandler
                }, {
                    title: '上一节点处理时间',
                    field: 'previousHandleTime',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(value, row, index){
                    	return $filter('limitTo')(row.previousHandleTime,19);
                    },
                    visible:columnSwitchList.previousHandleTime
                }, {
                    title: '订单状态',
                    field: 'state',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.state
                }, {
                    title: '订单来源',
                    field: 'source',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.source
                }, {
                    title: '待处理人',
                    field: 'currentHandler',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.currentHandler
                }, {
                    title: '操作',
                    field: 'operate',
                    align: 'center',
                    valign: 'bottom',
                    events: operateEvents,
                    formatter: operateFormatter,
                    visible:columnSwitchList.operate
                }]
            }}

			 $timeout(function(){
				 $(".tooltip-toggle").tooltip({
			         html: true
		         });
			 })
    

});