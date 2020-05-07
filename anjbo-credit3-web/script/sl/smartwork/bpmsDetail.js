angular.module("anjboApp", ['bsTable']).controller("bpmsDetailCtrl",function($scope,$filter,$compile,$http,$state,box,process,parent,route){

    $scope.page = new Object();
	
	var params =  route.getParams();
    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        $scope.page.processInstanceId = params.processInstanceId;
        return $scope.page;
    }
	function createTimeFormatter(value,row,index){
    	html = $filter('date')(row.createTime, 'yyyy-MM-dd HH:mm:ss');
    	return html
    }
        
    $scope.dataList = {
            options: {
                method:"post",
                url:"/credit/third/api/dingtalk/bpmsDetails/page",
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
                    title: '审批实例ID',
                    field: 'processInstanceId',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '审批状态',
                    field: 'status',
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
                    field: 'createTime',
                    align: 'center',
                    valign: 'bottom',
                    formatter: createTimeFormatter
                }, {
                    title: '操作',
                    field: 'operate',
                    align: 'center',
                    valign: 'bottom'
                }]
    		}
    };

});