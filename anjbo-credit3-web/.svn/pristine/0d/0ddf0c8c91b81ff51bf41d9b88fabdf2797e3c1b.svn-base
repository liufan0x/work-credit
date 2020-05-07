angular.module("anjboApp", ['bsTable']).controller("icbcQPDListCtrl",function($scope,$filter,$compile,$http,$state,box,process,parent){

    $scope.page = new Object();
    $scope.page.type = "0";
    $scope.isAudit=false;   

    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        return $scope.page;
    }
    function drcrfFormatter(value,row,index){
    	var retValue = "-";
    	switch (value){
    		case 1:
    			retValue = "借";
    			break;
    		case 2:
    			retValue = "贷";
    			break;
    		default:
    			break;
    	}
    	return retValue;
    }
	function timeFormatter(value,row,index){
    	html = $filter('date')(value, 'yyyy-MM-dd HH:mm:ss');
    	return html
    }

    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url: '/credit/third/api/icbc/qpd/page'}); 
    }
        
    $scope.dataList = {
            options: {
                method:"post",
                url:"/credit/third/api/icbc/qpd/page",
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
                    title: '公司账号',
                    field: 'accNo',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '借贷标志',
                    field: 'drcrf',
                    align: 'center',
                    valign: 'bottom',
                    formatter:drcrfFormatter
                }, {
                    title: '对方账号',
                    field: 'recipAccNo',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '对方户名',
                    field: 'recipName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '金额',
                    field: 'amount',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '币种',
                    field: 'currType',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '凭证号',
                    field: 'vouhNo',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '摘要',
                    field: 'summary',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '用途',
                    field: 'useCN',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '交易时间截',
                    field: 'timeStamp',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '备用字段3',
                    field: 'repReserved3',
                    align: 'center',
                    valign: 'bottom'
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
                    formatter: timeFormatter
                }]
    		}
    };

});