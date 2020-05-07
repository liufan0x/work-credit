/**
 * Created by lichao on 2017/12/27.
 */
angular.module("anjboApp", ['bsTable']).controller("boxListCtrl", function($scope,$filter,$compile,$cookies,$http,$state) {

    $scope.page = new Object();
    $scope.page.type = "0";
    $scope.isAudit=false;

    $http({
        method: 'POST',
        url:'/credit/element/boxbase/web/v/init'
    }).success(function(data){
        $scope.cityList = data.data.cityList;
        $("#customerName").bigAutocomplete({data:data.data.customerList,ifShow:true});
    })

    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url: '/credit/element/boxbase/web/v/page'});
    }

    $scope.reset = function(){
        $scope.page.city='';
        $scope.page.boxNo='';
        $scope.page.useStatus='';
        $scope.page.deviceStatus='';
        $scope.page.customerName='';
        $("#table").bootstrapTable('refresh', {url: '/credit/element/boxbase/web/v/page'});
    }

    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        return $scope.page;
    }

    function useStatusFormatter(value,row,index){
        var html = '';
        if('0'==value){
            html='空闲中';
        } else if ('1'==value){
            html='使用中';
        }
        return html
    }

    function deviceStatusFormatter(value,row,index){
        var html = '';
        if('0'==value){
            html='离线';
        } else if ('1'==value){
            html='正常';
        }
        return html
    }

    function customerNameFormatter(value,row,index){
        var html = '';
        if(undefined==value||''==value||'0'==row.useStatus){
            html='--';
        } else{
            html=value;
        }
        return html
    }

    function operateFormatter() {
        var html = '';
        html += '<a class="detail" href="javascript:void(0)">开箱记录</a>';
        return html;
    }

    window.operateEvents = {
        'click .detail': function (e, value, row, index) {
            $state.go("openBoxList",{'pid':row.id});
        }
    };

    $scope.boxList = {
        options: {
            method:"post",
            url:"/credit/element/boxbase/web/v/page",
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
                title: '城市',
                field: 'city',
                align: 'center',
                valign: 'bottom'
            }, {
                title: '要件柜所在地区',
                field: 'subsidiary',
                align: 'center',
                valign: 'bottom',
            }, {
                title: '要件箱编号',
                field: 'boxNo',
                align: 'center',
                valign: 'bottom'
            }, {
                title: '使用情况',
                field: 'useStatus',
                align: 'center',
                valign: 'bottom',
                formatter: useStatusFormatter
            }, {
                title: '设备状况',
                field: 'deviceStatus',
                align: 'center',
                valign: 'bottom',
                formatter: deviceStatusFormatter
            }, {
                title: '当前存入要件的客户',
                field: 'customerName',
                align: 'center',
                valign: 'bottom',
                formatter: customerNameFormatter
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