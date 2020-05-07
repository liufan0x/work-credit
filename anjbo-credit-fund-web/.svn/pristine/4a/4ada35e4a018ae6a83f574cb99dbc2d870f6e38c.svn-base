/**
 * Created by lichao on 2017/12/27.
 */
angular.module("anjboApp", ['bsTable']).controller("auditListCtrl", function($scope,$filter,$compile,$cookies,$http,$state) {

    $scope.page = new Object();
    $scope.page.type = "0";
    $scope.isAudit=false;

    $http({
        method: 'POST',
        url:'/credit/element/auditconfig/web/v/init'
    }).success(function(data){
        $scope.cityList = data.data.cityList;
    })

    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url: '/credit/element/auditconfig/web/v/page'});
    }

    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        return $scope.page;
    }

    function typeFormatter(value,row,index){
        var html = '';
        if('1'==value){
            //要件审批（普通申请）
            html='非业务流程审批';
        } else if ('2'==value){
            html='要件审批（财务申请）';
        } else if ('3'==value){
            html='公章审批';
        }else if('4'==value){
            html='业务流程审批'
        }
        return html
    }
    function nameFormatter(value,row,index){
        var html="";
        if (value!=''&&value!=undefined){
            var array = JSON.parse(value);
            angular.forEach(array, function(a, key) {
                if (key>0) html += "、";
                html+=a.name;

            });

            /*angular.forEach(array, function(a, key) {
                this.push(a.name);
            }, html);*/
        }else{
            html="--";
        }
        return html;
    }

    function operateFormatter(value, row, index) {
        var html = '';
        if(row.state==1){
        	html += '<a class="detail" href="javascript:void(0)">编辑</a>&nbsp;&nbsp;';
        	html += '<a class="disable" href="javascript:void(0)">禁用</a>';
        }else{
        	html += '<a class="enable" href="javascript:void(0)">启用</a>';
        }
        return html;
    }

    function chgStatus(id, state){
	    $http({
			method: 'post',
			url: "/credit/element/auditconfig/web/v/editSate",
			data: {"id":id, "state":state}
		}).success(function(data) {
			if("SUCCESS"==data.code){
				$scope.query();
			}else{
				box.boxAlert(data.msg);
			}			
		});
    }
    
    window.operateEvents = {
        'click .detail': function (e, value, row, index) {
            $state.go("auditDetail",{'id':row.id});
        },
	    'click .disable': function (e, value, row, index) {
	        chgStatus(row.id, 2);	
	    },'click .enable': function (e, value, row, index) {
	    	chgStatus(row.id, 1);
	    }
    };
    $scope.goHref = function(){
        $state.go("auditDetail",{'id':'NaN'});
    }

    $scope.boxList = {
        options: {
            method:"post",
            url:"/credit/element/auditconfig/web/v/page",
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
                title: '序号',
                field: 'id',
                align: 'center',
                valign: 'middle'
            }, {
                title: '城市',
                field: 'city',
                align: 'center',
                valign: 'middle'
            }, {
                title: '审批类型',
                field: 'type',
                align: 'center',
                valign: 'middle',
                formatter: typeFormatter
            }, {
                title: '一级审批人',
                field: 'degree1',
                align: 'center',
                valign: 'middle',
                width: 250,
                formatter: nameFormatter
            }, {
                title: '二级审批人',
                field: 'degree2',
                align: 'center',
                valign: 'middle',
                width: 250,
                formatter: nameFormatter
            }, {
                title: '三级审批人',
                field: 'degree3',
                align: 'center',
                valign: 'middle',
                width: 250,
                formatter: nameFormatter
            }, {
                title: '四级审批人',
                field: 'degree4',
                align: 'center',
                valign: 'middle',
                width: 250,
                formatter: nameFormatter
            }, {
                title: '五级审批人',
                field: 'degree5',
                align: 'center',
                valign: 'middle',
                width: 250,
                formatter: nameFormatter
            }, {
                title: '抄送人',
                field: 'other',
                align: 'center',
                valign: 'middle',
                width: 250,
                formatter: nameFormatter
            }, {
                title: '操作',
                field: 'operate',
                align: 'center',
                valign: 'middle',
                events: operateEvents,
                formatter: operateFormatter
            }]
        }
    };


});