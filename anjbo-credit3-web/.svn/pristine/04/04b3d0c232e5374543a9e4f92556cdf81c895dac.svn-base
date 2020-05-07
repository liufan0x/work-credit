/**
 * Created by Administrator on 2017/12/27.
 */
angular.module("anjboApp", ['bsTable']).controller("openBoxListCtrl", function($scope,$filter,$compile,$cookies,$http,$state,route,box,process,parent) {

    $scope.id=route.getParams().pid;
    $scope.page = $cookies.getObject("pageParams");
    if(!$scope.page){
        $scope.page = new Object();
        $scope.page.start = 0;
        $scope.page.pageSize = 15;
        $scope.page.type = "0";
        $scope.isAudit=false;
    }

    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url: '/credit/element/boxbase/web/v/openPage'});
    }

    $scope.reset = function(){
        $scope.page.startTime='';
        $scope.page.endTime='';
        $scope.page.applierName='';
        $scope.page.currentHandler='';
        $("#table").bootstrapTable('refresh', {url: '/credit/element/boxbase/web/v/openPage'});
    }

    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        $scope.page.id=$scope.id;
        return $scope.page;
    }

    function operationTimeFormatter(value,row,index){
        var html = '';
        if(undefined!=value||''!=value)
            html = $filter('date')(value, 'yyyy-MM-dd HH:mm');
        return html
    }

    function operationTypeFormatter(value,row,index){
        var html = '';
        var type = '';
        if ('3'==row.orderType){
            type='公章';
        }else{
            type='要件';
        }

        if('1'==value){
            html='存'+type;
        } else if ('2'==value){
            html='取'+type;
        } else if ('3'==value){
            html='借'+type;
        } else if ('4'==value){
            html='还'+type;
        } else if ('5'==value){
            html='退'+type;
        } else if ('6'==value){
            html='开箱';
        } else if ('7'==value){
            html='开箱';
        }
        return html
    }

    function operateFormatter() {
        var html = '';
        html += '<a class="detail" href="javascript:void(0)">查看详情</a>';
        return html;
    }

    window.operateEvents = {
        'click .detail': function (e, value, row, index) {
            $state.go("eleAccessDetail",{'id':row.id,'pid':$scope.id});
        }
    };

    $scope.openBoxList = {
        options: {
            method:"post",
            url:"/credit/element/boxbase/web/v/openPage",
            queryParams:getParams,
            sidePagination:'server',
            undefinedText:"--",
            cache: false,
            striped: true,
            pagination: true,
            pageNumber: 1,
            pageSize: 15,
            pageList: ['All'],
            showColumns: true,
            showRefresh: false,
            columns: [{
                title: '开箱时间',
                field: 'operationTime',
                align: 'center',
                valign: 'bottom',
                formatter: operationTimeFormatter
            }, {
                title: '要件操作',
                field: 'operationType',
                align: 'center',
                valign: 'bottom',
                formatter: operationTypeFormatter
            }, {
                title: '申请人',
                field: 'applierName',
                align: 'center',
                valign: 'bottom'
            }, {
                title: '操作人',
                field: 'currentHandler',
                align: 'center',
                valign: 'bottom'
            }, {
                title: '操作',
                field: 'operate',
                align: 'center',
                valign: 'bottom',
                events: operateEvents,
                formatter: operateFormatter
            }]
        }
    };
});