angular.module("anjboApp", ['bsTable']).controller("productListCtrl",function($scope, $http, $state, box, route){ 
    
    window.operateEvents = {
        'click .edit': function (e, value, row, index) {
            $state.go("productEdit",{'productId':row.id});
        }
    };
    
    $scope.page = new Object();
    
    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        return $scope.page;
    }
    
    $http({
		method: 'POST',
		url:'/credit/data/dict/v/search',
		data:{type:"cityList"}
	}).success(function(data){
		$scope.dictList = data.data;
		$scope.dictList.unshift({id:"",name:"请选择"});
	})

	var url = "/credit/data/product/v/page";

    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url:url}); 
    }
 
    $scope.productList = {
            options: {
                method:"post",
                url:url,
                queryParams:getParams,
                sidePagination:'server',
                undefinedText:"-",
                cache: false,
                striped: true,
                pagination: true,
                pageNumber: 1,
                pageSize: 15,
                pageList: ['All'],
                columns: [{
                    title: '序号',
                    field: 'id',
                    align: 'center',
                    valign: 'bottom',
					formatter: function(value, row, index) {
						return index+1;
					}
                }, {
                    title: '产品对应城市',
                    field: 'cityName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '产品名称',
                    field: 'productName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '产品代号',
                    field: 'productCode',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '产品描述',
                    field: 'productDescribe',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '操作',
                    field: 'operate',
                    align: 'center',
                    valign: 'bottom',
                    events: operateEvents,
                    formatter: function(value, row, index){
                    	var html = "";
                    	if($scope.meunAuth.indexOf('auth17auth')>=0){
                        	html = '<a class="edit" href="javascript:void(0)" >编辑</a>(可预览节点页面)&nbsp;&nbsp;';
                    	}
                        return html;
                    }
                }]
            }
    };
    

});