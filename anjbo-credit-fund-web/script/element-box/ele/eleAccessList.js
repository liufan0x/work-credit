/**
 * Created by lichao on 2017/12/27.
 */
angular.module("anjboApp", ['bsTable']).controller("eleAccessListCtrl", function($scope,$filter, $http,$state) {

    $scope.page = new Object();
    $scope.page.type = "0";
    $scope.isAudit=false;

    $http({
        method: 'POST',
        url:'/credit/element/boxbase/web/v/init'
    }).success(function(data){
        $scope.cityList = data.data.cityList;
    })

    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url: '/credit/report/fund/v/eleAdmin'});
    }

    $scope.reset = function(){
        $scope.page.city='';
        $scope.page.orderType='';
        $scope.page.customerName='';
        $scope.page.applierName='';
        $scope.page.orderStatus='';
        $scope.page.boxNo='';
        $scope.page.startTime='';
        $scope.page.endTime='';
        $("#table").bootstrapTable('refresh', {url: '/credit/report/fund/v/eleAdmin'});
    }

    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        $scope.page.city='深圳市';
        return $scope.page;
    }

    function operationTimeFormatter(value,row,index){
        var html = '';
        if(undefined!=value||''!=value)
            html = $filter('date')(value, 'yyyy-MM-dd HH:mm');
        return html
    }

    function orderTypeFormatter(value,row,index){
        var html = '';
        if('1'==value||'2'==value){
            html='要件';
        } else if ('3'==value){
            html='公章';
        }
        return html
    }

    function operationTypeFormatter(value,row,index){
        var html = '';
        var type = '';
        /*if ('3'==row.orderType){
            type='公章';
        }else{
            type='要件';
        }*/
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
        } else {
        	html='改';
        }
        return html
    }
    function orderStatusFormatter(value,row,index){
        var html = '';
        if('1'==value){
            html='未存入';
        } else if ('2'==value){
            html='已存入';
        } else if ('3'==value){
            html='已借出';
        } else if ('4'==value){
            html='已归还';
        } else if ('5'==value){
            html='超时未还';
        } else if ('6'==value){
            html='已退要件';
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
            $state.go("eleAccessDetail",{'id':row.id,'pid':'NaN'});
        }
    };

    $scope.eleAccessList = {
        options: {
            method:"post",
            url:"/credit/report/fund/v/eleAdmin", // /credit/element/eleaccess/web/v/eleAccessPage
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
                title: '序号',
                field: 'id',
                align: 'center',
                valign: 'bottom'
            }, {
                title: '城市',
                field: 'cityName',
                align: 'center',
                valign: 'bottom'
            }, {
                title: '要件类型',
                field: 'orderType',
                align: 'center',
                valign: 'bottom',
                formatter: orderTypeFormatter
            }, {
                title: '要件箱编号',
                field: 'boxNo',
                align: 'center',
                valign: 'bottom'
            }, {
                title: '客户姓名',
                field: 'customerName',
                align: 'center',
                valign: 'bottom'
            }, /*{
                title: '要件箱中的风控要件',
                field: 'sbRisk',
                align: 'center',
                valign: 'bottom'
            }, */{
                title: '要件箱中的回款要件',
                field: 'sbPay',
                align: 'center',
                valign: 'bottom'
            }, /*{
                title: '操作项',
                field: 'operationType',
                align: 'center',
                valign: 'bottom',
                formatter: operationTypeFormatter
            }, {
                title: '操作人',
                field: 'currentHandler',
                align: 'center',
                valign: 'bottom'
            }, {
                title: '申请人',
                field: 'applierName',
                align: 'center',
                valign: 'bottom'
            }, {
                title: '要件状态',
                field: 'orderStatus',
                align: 'center',
                valign: 'bottom',
                formatter: orderStatusFormatter
            }, {
                title: '操作时间',
                field: 'operationTime',
                align: 'center',
                valign: 'bottom',
                formatter: operationTimeFormatter
            },*/ {
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