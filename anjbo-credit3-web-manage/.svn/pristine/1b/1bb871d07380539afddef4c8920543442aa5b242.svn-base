angular.module("anjboApp", ['bsTable']).controller("dictListCtrl",function($scope, $http, $state, box, route){ 
    
    $scope.dictDto=new Object();
    $scope.page = new Object();
    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        return $scope.page;
    }
    window.operateEvents = {
            'click .edit':function (e, value, row, index) {
            	$scope.dictDto=row;
        		box.editAlert3($scope,"添加字典","<dict-box></dict-box>",submitDict);
            }
        };
	var url = "/credit/data/dict/v/page";
    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url:url}); 
    }
 
    $scope.dictList = {
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
                    title: '编码',
                    field: 'code',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: 'pcode',
                    field: 'pcode',
                    align: 'center',
                    valign: 'bottom'
                },  {
                    title: '名称',
                    field: 'name',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '类型',
                    field: 'type',
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
                    	if($scope.meunAuth.indexOf('auth17auth')>=0){
                        	html = '<a class="edit" href="javascript:void(0)" >编辑</a>&nbsp;&nbsp;';
                    	}
                        return html;
                    }
                }]
            }
    };
    
    var submitDict = function(){
		if(typeof($scope.dictDto.type)=="undefined" || $scope.dictDto.type==""){
			alert("请填写字典类型");
			return false;
		}
		if(typeof($scope.dictDto.name)=="undefined" || $scope.dictDto.name==""){
			alert("请填写字典名称");
			return false;
		}
		$(".lhw-alert-ok").attr("disabled", "disabled");
		var addurl = "/credit/data/dict/v/add";
		if($scope.dictDto.id){
			addurl = "/credit/data/dict/v/edit";	
		}
		$http({
			method: 'POST',
			url: addurl,
			data: $scope.dictDto
		}).success(function(data) {
			box.closeAlert();
			alert(data.msg);
			if(data.code == 'SUCCESS'){
				 $scope.query();
			}
		})
	}
    
    $scope.addDict=function(){
    	$scope.dictDto =new Object();
    	box.editAlert3($scope,"添加字典","<dict-box></dict-box>",submitDict);
    }

});