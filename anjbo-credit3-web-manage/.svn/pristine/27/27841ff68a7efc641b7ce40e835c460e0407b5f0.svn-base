angular.module("anjboApp", ['bsTable']).controller("bankSubListCtrl",function($scope, $http, $state, box, route){ 
    $scope.bankSubDto=new Object();
    $scope.page = new Object();
    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        return $scope.page;
    }
    window.operateEvents = {
            'click .edit': function (e, value, row, index) {
            	$scope.bankSubDto = row;
            	if($scope.bankSubDto!=null && $scope.bankSubDto.pid!=null){
            		$scope.bankSubDto.pid = $scope.bankSubDto.pid+"";
            	}
            	box.editAlert3($scope,"添加支行","<bank-sub-box></bank-sub-box>",submitbankSub);
            }
     };

    $http({
		method: 'POST',
		url:'/credit/data/bank/v/search',
		data:{}
	}).success(function(data){
		$scope.bankList = data.data;
		$scope.bankList.unshift({id:"",name:"请选择"});
	})
	
	var url = '/credit/data/bankSub/v/page';
    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url:url}); 
    }
 
    $scope.bankSubList = {
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
                    title: '银行名称',
                    field: 'bankName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '支行名称',
                    field: 'name',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '排序',
                    field: 'sort',
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
                    	if($scope.meunAuth && $scope.meunAuth.indexOf('auth17auth')>=0){
                        	html = '<a class="edit" href="javascript:void(0)" >编辑</a>&nbsp;&nbsp;';
                    	}
                        return html;
                    }
                }]
            }
    };
    var submitbankSub = function(){
    	if(typeof($scope.bankSubDto.pid) == "undefined" || $scope.bankSubDto.pid ==""){
    		alert("请填写银行名称");
			return false;
    	}
    	if(typeof($scope.bankSubDto.name)=="undefined" || $scope.bankSubDto.name==""){
			alert("请填写支行名称");
			return false;
		}
		$(".lhw-alert-ok").attr("disabled", "disabled");
		var addurl = "/credit/data/bankSub/v/add";
		if($scope.bankSubDto.id){
			addurl = "/credit/data/bankSub/v/edit";	
		}
		$http({
			method: 'POST',
			url: addurl,
			data: $scope.bankSubDto
		}).success(function(data) {
			box.closeAlert();
			alert(data.msg);
			if(data.code == 'SUCCESS'){
				 $scope.query();
			}
		})
    }
   
    $scope.addbankSub=function(){
    	$scope.bankSubDto=new Object();
    	box.editAlert3($scope,"添加支行","<bank-sub-box></bank-sub-box>",submitbankSub);
    }

});