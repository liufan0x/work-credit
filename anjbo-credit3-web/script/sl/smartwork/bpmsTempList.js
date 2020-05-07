angular.module("anjboApp", ['bsTable']).controller("bpmsTempListCtrl",function($scope,$filter,$compile,$http,$state,box,process,parent){

    $scope.page = new Object();
    $scope.page.type = "0";
    $scope.isAudit=false;   

    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        return $scope.page;
    }
	function timeFormatter(value,row,index){
    	html = $filter('date')(value, 'yyyy-MM-dd HH:mm:ss');
    	return html
    }

    function operateFormatter(value, row, index) {
        var html = '';
        html += '<a class="bpmsTempEdit" href="javascript:void(0)">配置</a>&nbsp;&nbsp;&nbsp;&nbsp;';
        html += '<a class="bpmsTempTest" href="javascript:void(0)">测试</a>&nbsp;&nbsp;&nbsp;&nbsp;';
        html += '<a class="toBpmsList" href="javascript:void(0)">表单列表</a>';
        return html;    
    }
    window.operateEvents = {
    	'click .bpmsTempEdit': function (e, value, row, index) {
            $state.go("bpmsTempEdit",{'id': row.id});
        },
        'click .bpmsTempTest': function (e, value, row, index) {
        	var record = new Object;
        	record.bpmsFrom = row.code;
        	record.bpmsObjectId = 100;
        	record.formComponentParam = new Array("审批标题", "审批内容描述");
            box.confirmAlert("创建审批", "确定创建【"+row.name+"】审批流程吗？",function(){
				box.waitAlert();
				$http({
			        method: 'POST',
			        url:'/credit/third/api/dingtalk/bpms/createOrderDoc',
			        data:record
			    }).success(function(data){
			       	box.closeWaitAlert();
			       	if(data.code == "SUCCESS"){
			       		$state.go("bpmsList",{});
					}
			    });
			});
        },
        'click .toBpmsList': function (e, value, row, index) {
            $state.go("bpmsList",{'bpmsFrom': row.code});
        }
    };

    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url: '/credit/third/api/dingtalk/bpmsTemp/page'}); 
    }
        
    $scope.dataList = {
            options: {
                method:"post",
                url:"/credit/third/api/dingtalk/bpmsTemp/page",
                queryParams:getParams,
                sidePagination:'server',
                undefinedText:"-",
                cache: false,
                striped: true,
                pagination: false,
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
                    title: '流程编码',
                    field: 'code',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '流程名称',
                    field: 'name',
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
                    title: '修改人',
                    field: 'updateUid',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '修改时间',
                    field: 'updateTime',
                    align: 'center',
                    valign: 'bottom',
                    formatter: timeFormatter
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