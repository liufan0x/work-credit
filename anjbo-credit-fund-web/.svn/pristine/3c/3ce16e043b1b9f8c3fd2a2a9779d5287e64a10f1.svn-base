angular.module("anjboApp", ['bsTable']).controller("monitorArchiveListCtrl",function($scope,$filter,$compile,$http,$state,box,process,parent){

    $scope.page = new Object();
    $scope.page.type = "0";
    $scope.isAudit=false;
    $http({
        method: 'POST',
        url:'/credit/risk/monitor/v/init'
    }).success(function(data){
        $scope.productdto = data.data.productdto;
    })
    

    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        return $scope.page;
    }

    function operateFormatter(value, row, index) {
        var html = '';
        html += '<a class="detail" href="javascript:void(0)">详情</a>&nbsp;&nbsp;';
        if($scope.meunAuth.indexOf('auth19auth')>=0){
        	html += '<a class="closeOrder" href="javascript:void(0)" >删除</a>&nbsp;&nbsp;';
        }
        if(row.orderNo){
        	html += '<a class="lookOrder" href="javascript:void(0)">查看关联订单</a>&nbsp;&nbsp;';
        }
        return html;    
    }
    
    function createTimeFormatter(value,row,index){
    	html = $filter('date')(row.createTime, 'yyyy-MM-dd HH:mm:ss');
    	return html
    }

    window.operateEvents = {
        'click .closeOrder': function (e, value, row, index) {
            var closeOrder = function(){
                $http({
                    method: 'POST',
                    url:'/credit/risk/monitor/v/deleteMonitorArchiveById',
                    data:{"id":row.id}
                }).success(function(data){
                    if(data.code == "SUCCESS"){
                        $scope.query();
                        box.boxAlert(data.msg);
                    }
                })
            }
            box.confirmAlert("删除监测","确定要删除房产监测？",closeOrder);
        },
        'click .lookOrder': function (e, value, row, index) {
        	$state.go("orderDetail",{'orderNo':row.orderNo});
        },
        'click .detail': function (e, value, row, index) {
            $state.go("monitorArchiveDetail",{'id':row.id});
        }
    };

    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url: '/credit/risk/monitor/v/page'}); 
    }
    
    $scope.add = function(){
    	$state.go("monitorArchiveAdd");
    }
        
    $scope.archiveList = {
            options: {
                method:"post",
                url:"/credit/risk/monitor/v/page",
                queryParams:getParams,
                sidePagination:'server',
                undefinedText:"-",
                cache: false,
                striped: true,
                pagination: true,
                pageNumber: 1,
                pageSize: 15,
                pageList: ['All'],
                showColumns: true,
                showRefresh: false,
                columns: [{
                    title: '序号',
                    field: 'id',
                    align: 'right',
                    valign: 'bottom'
                }, {
                    title: '创建时间',
                    field: 'createTime',
                    align: 'center',
                    valign: 'bottom',
                    formatter: createTimeFormatter
                }, {
                    title: '创建人',
                    field: 'name',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '姓名/身份证号',
                    field: 'identityNo',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '房产证号',
                    field: 'estateNo',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '产权证类型',
                    field: 'estateTypeStr',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '查档用途',
                    field: 'queryUsageStr',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '查询时间',
                    field: 'sectionTime',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '查询频率',
                    field: 'queryFrequency',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '查询结果（最新）',
                    field: 'propertyStatus',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '变更记录',
                    field: 'changeRecord',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '操作',
                    field: 'operate',
                    align: 'center',
                    valign: 'bottom',
                    events: operateEvents,
                    formatter: operateFormatter
                }]
            }};

    
/*    $(".form_date_end").datetimepicker({
        format: "dd MM yyyy",
        autoclose: true,
        todayBtn: true,
        startDate: $scope.page.startTime,
        minuteStep: 10
    });
    
    $(".form_date_start").datetimepicker({
        format: "dd MM yyyy",
        autoclose: true,
        todayBtn: true,
        endDate: $scope.page.endTime,
        minuteStep: 10
    });*/
});