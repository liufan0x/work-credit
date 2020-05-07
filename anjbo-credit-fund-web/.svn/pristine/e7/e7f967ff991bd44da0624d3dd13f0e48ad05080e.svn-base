angular.module("anjboApp", ['bsTable']).controller("baiduRiskListCtrl",function($scope,$filter,$compile,$http,$state,box,process,parent,$timeout){

    $scope.page = new Object();
    $scope.page.type = "0";
    $scope.isAudit=false;

    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        return $scope.page;
    }

    function operateFormatter(value, row, index) {
        var html = '';
        html += '<a class="detail" href="javascript:void(0)">详情</a>&nbsp;&nbsp;';
        html += '<a class="closeOrder" href="javascript:void(0)" >删除</a>&nbsp;&nbsp;';
        if(row.orderNo){
        	html += '<a class="lookOrder" href="javascript:void(0)">查看关联订单</a>&nbsp;&nbsp;';
        }
        return html;    
    }
    
    function createTimeFormatter(value,row,index){
    	html = $filter('date')(row.createTime, 'yyyy-MM-dd HH:mm:ss');
    	return html
    }
    
    //添加风险名单
    $scope.showBaiduRiskList = function(){
        $scope.baiduRiskListDto = new Object();
        var addBaiduRiskList = function(){
        	
        	if(!$scope.baiduRiskListForm.$valid){
        		$scope.isAudit=true;
        		alert("三项都必填");
        		return;
        	}
        	box.waitAlert();
        	//添加风险名单
			$http({
				method: 'POST',
				url:'/credit/risk/riskList/v/addRiskList',
				data:$scope.baiduRiskListDto
			}).success(function(data){
				box.closeWaitAlert();
				if(data.code == "SUCCESS"){
						alert('添加成功');
					 //$state.go("orderEdit.placeOrderEdit",{orderNo:data.data,cityCode:$scope.orderDto.cityCode,productCode:$scope.orderDto.productCode});
			            box.closeAlert();
			            //刷新当前页
			            location.reload();
				}else{
					alert(data.msg);
					box.closeAlert();
				}
			}).error(function(){
				box.closeWaitAlert();
			})
        }

        box.editAlert($scope,"新增风险名单",$("#riskListId").html(),addBaiduRiskList);
        $timeout(function(){
			 $(".tooltip-toggle").tooltip({
		         html: true
	         });
		 })

    }

    window.operateEvents = {
        'click .closeOrder': function (e, value, row, index) {
            var closeOrder = function(){
                $http({
                    method: 'POST',
                    url:'/credit/risk/riskList/v/deleteRiskListById',
                    data:{"id":row.id}
                }).success(function(data){
                    if(data.code == "SUCCESS"){
                        $scope.query();
                        box.boxAlert(data.msg);
                    }
                })
            }
            box.confirmAlert("删除风险名单","确定要删除风险名单？",closeOrder);
        },
        'click .lookOrder': function (e, value, row, index) {
        	$state.go("orderDetail",{'orderNo':row.orderNo});
        },
        'click .detail': function (e, value, row, index) {
            $state.go("baiduRiskListDetail",{'id':row.id});
        }
    };

    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url: '/credit/risk/riskList/v/page'}); 
    }
    
        
    $scope.baiduRiskList = {
            options: {
                method:"post",
                url:"/credit/risk/riskList/v/page",
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
                    title: '姓名',
                    field: 'name',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '身份证号',
                    field: 'identity',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '手机号码',
                    field: 'phone',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '查询时间',
                    field: 'createTime',
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
            }};
    
});