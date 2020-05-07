angular.module("anjboApp", ['bsTable']).controller("bpmsListCtrl",function($scope,$filter,$compile,$http,$state,box,process,parent,route){
	var param = route.getpa
    $scope.page = new Object();
    $scope.page.type = "0";
    $scope.isAudit=false;   

    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        return $scope.page;
    }
	function createTimeFormatter(value,row,index){
    	html = $filter('date')(row.createTime, 'yyyy-MM-dd HH:mm:ss');
    	return html
    }

    function operateFormatter(value, row, index) {
        var html = '';
        html += '<a class="detail" href="javascript:void(0)">审批明细</a>&nbsp;&nbsp;';
        return html;    
    }
    window.operateEvents = {
        'click .detail': function (e, value, row, index) {
            $state.go("bpmsDetail",{'processInstanceId': row.processInstanceId});
        }
    };

    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url: '/credit/third/api/dingtalk/bpms/page'}); 
    }    
        
    $scope.dataList = {
            options: {
                method:"post",
                url:"/credit/third/api/dingtalk/bpms/page",
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
                    title: '审批来源',
                    field: 'bpmsFrom',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '关联对象ID',
                    field: 'bpmsFromId',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '发起人',
                    field: 'originatorUserName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '审批人',
                    field: 'approversName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '抄送人',
                    field: 'ccListName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '表单参数',
                    field: 'formComponent',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '审批实例ID',
                    field: 'processInstanceId',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '创建人',
                    field: 'createUid',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '创建时间',
                    field: 'createDate',
                    align: 'center',
                    valign: 'bottom',
                    formatter: createTimeFormatter
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