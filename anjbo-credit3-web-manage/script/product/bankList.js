angular.module("anjboApp", ['bsTable']).controller("bankListCtrl",function($scope, $http, $state, box, route){ 
    $scope.bankDto=new Object();
    $scope.page = new Object();
    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        return $scope.page;
    }
    window.operateEvents = {
            'click .edit': function (e, value, row, index) {
            	$scope.bankDto = row;
            	box.editAlert3($scope,"添加银行","<bank-box></bank-box>",submitBank);
            }
        };

	var url = "/credit/data/bank/v/page";
    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url:url}); 
    }
 
    $scope.bankList = {
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
                    title: '名称',
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
    var submitBank = function(){
    	if(typeof($scope.bankDto.name)=="undefined" || $scope.bankDto.name==""){
			alert("请填写银行名称");
			return false;
		}
		$(".lhw-alert-ok").attr("disabled", "disabled");
		var addurl = "/credit/data/bank/v/add";
		if($scope.bankDto.id){
			addurl = "/credit/data/bank/v/edit";	
		}
		$http({
			method: 'POST',
			url: addurl,
			data: $scope.bankDto
		}).success(function(data) {
			box.closeAlert();
			alert(data.msg);
			if(data.code == 'SUCCESS'){
				 $scope.query();
			}
		})
    }
   
    $scope.addbank=function(){
    	$scope.bankDto=new Object();
    	box.editAlert3($scope,"添加银行","<bank-box></bank-box>",submitBank);
    }

});