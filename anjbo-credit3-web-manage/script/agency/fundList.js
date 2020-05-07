angular.module("anjboApp", ['bsTable']).controller("fundListCtrl",function($scope,$http,$state,box,route){ 
	
    $scope.page = new Object();
    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        return $scope.page;
    }
	
	var url="/credit/user/fund/v/page";
	
    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url:url}); 
    }
 
 	window.operateEvents = {
        'click .edit': function (e, value, row, index) {
            $state.go("fundEdit",{'fundId':row.id});
        },'click .disable': function (e, value, row, index) {
            chgStatus(row.id, 0);	
        },'click .enable': function (e, value, row, index) {
        	chgStatus(row.id, 1);
        },'click .setFundCost': function (e, value, row, index) {
            $state.go("fundCostList",{'fundId':row.id,"fundName":row.fundName});
        },'click .resetPwd': function (e, value, row, index) {
            box.confirmAlert("重置密码", "是否重置'"+row.fundName+"'资金方账号密码？<br/><span style='display:inline-block;padding:0 9px;text-align:left!important;color:#6666;'>注：我们将会把重置后的密码发送至资方联系人的手机号，请通知相关联系人查收短信。</span>", function() {
				box.waitAlert();
				$http({
					method: 'post',
					url: "/credit/user/user/v/resetPwd",
					data: {"agencyId":10, "uid":row.managerUid, "mobile":row.contactTel}
				}).success(function(data) {					
					box.closeWaitAlert();
					box.boxAlert(data.msg);
				}).error(function(xhr,status,error){
					box.boxAlert("出错啦，请重试！");
					box.closeWaitAlert();
				});
			});
        },'click .disableAccount': function (e, value, row, index) {
        	box.confirmAlert("冻结账号", "确认冻结'"+row.fundName+"'的资方账号吗？", function() {
				chgManagerStatus(row, 1);	
			});			            		
        },'click .enableAccount': function (e, value, row, index) {
        	box.confirmAlert("解冻账号", "确认解冻'"+row.fundName+"'的资方账号吗？", function() {
				chgManagerStatus(row, 0);
			});	        	
        },'click .fundBusinessModelEdit': function (e, value, row, index) {
            $state.go("fundBusinessModelEdit",{'fundId':row.id,'fundName':row.fundName});
        }
    };
    function chgStatus(id, status){
	    $http({
			method: 'post',
			url: "/credit/user/fund/v/edit",
			data: {"id":id, "status":status}
		}).success(function(data) {
			if("SUCCESS"==data.code){
				$scope.query();
			}else{
				box.boxAlert(data.msg);
			}			
		});
    }
    function chgManagerStatus(row, status){
    	box.waitAlert();
	    $http({
			method: 'post',
			url: "/credit/user/fund/v/changeManagerStatus",
			data: {"id":row.id, "contactTel":row.contactTel, "status":row.status, "managerUid":row.managerUid, "managerStatus":status}
		}).success(function(data) {
			box.closeWaitAlert();
			if("SUCCESS"==data.code){
				$scope.query();
			}else{
				box.boxAlert(data.msg);
			}
		}).error(function(xhr,status,error){
			box.boxAlert("出错啦，请重试！");
			box.closeWaitAlert();
		});
    }
    function formatterStatus(value, row, index){    	
	    switch (value){			
			case 0:		return "禁用";
			case 1:	    return "启用";
			default:	return "--";
		}
    }
    function formatterMStatus(value, row, index){    	
	    switch (value){			
			case 0:		return "正常";
			case 1:	    return "冻结";
			default:	return "--";
		}
    }
    $scope.fundList = {
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
                    field: 'index',
                    align: 'center',
                    valign: 'bottom',
                    formatter: function(value, row, index){
                    	return index+1;
                    }
                }, {
                    title: '资金方',
                    field: 'fundName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '代号',
                    field: 'fundCode',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '描述',
                    field: 'fundDesc',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '资方登录账号',
                    field: 'contactTel',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '联系人',
                    field: 'contactMan',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '资方状态',
                    field: 'status', formatter:formatterStatus,
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '账号状态',
                    field: 'managerStatus', formatter:formatterMStatus,
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '操作',
                    field: 'operate',
                    align: 'center',
                    valign: 'bottom',
                    events: operateEvents,
                    formatter: function(value, row, index){
                    	var html= "";
                    	if($scope.meunAuth.indexOf('auth15auth')>=0){
	                        html = '<a class="edit" href="javascript:void(0)" >编辑</a>&nbsp;&nbsp;';
	                        if(row.status==0){
	                            html += '<a class="enable" href="javascript:void(0)" >启用资方</a>&nbsp;&nbsp;';
	                        }else{
	                            html += '<a class="disable" href="javascript:void(0)" >禁用资方</a>&nbsp;&nbsp;';
	                        }
	                        html += '<a class="setFundCost" href="javascript:void(0)" title="Like">设置资金成本</a>&nbsp;&nbsp;';
	                        if(null!=row.managerUid && row.managerUid.length>0){
	                        	html += '<a class="resetPwd" href="javascript:void(0)">重置密码</a>&nbsp;&nbsp;';
	                        	if(row.managerStatus==0){
		                        	html += '<a class="disableAccount" href="javascript:void(0)" >冻结账号</a>&nbsp;&nbsp;';	                            
		                        }else{
		                        	html += '<a class="enableAccount" href="javascript:void(0)" >解冻账号</a>&nbsp;&nbsp;';		                                                    
		                        }
	                        }else{
	                        	html += '<a style="color:#666!important;" href="javascript:void(0)">重置密码</a>&nbsp;&nbsp;';
	                        	html += '<a style="color:#666!important;" href="javascript:void(0)" >解冻账号</a>&nbsp;&nbsp;';
	                        }	
	                        html += '<a class="fundBusinessModelEdit" href="javascript:void(0)" >准入标准</a>&nbsp;&nbsp;';
                    	}
                        return html;
                    }
                }]
            }
    };
    

});