angular.module("anjboApp", ['bsTable']).controller("capitalListCtrl",function($scope,$http,$state,box,process,parent){ 

    $scope.page = new Object();

    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        $scope.page.sortOrder = data.order;
        $scope.page.sortName = data.sort;
        return $scope.page;
    }
    var app = angular.module('anjboApp', []);
    window.operateEvents = {
        'click .lookDetail': function (e, value, row, index) {
            $state.go("capitalDetail",{'flowNo':row.flowNo });
        },
        'click .facesign': function (e, value, row, index) {
            $scope.daikou(row.flowNo,row.withholdState);
        },
        'click .unbind': function (e, value, row, index) {
            $scope.unbind(row.flowNo,row.unbind);
        }
    };
   
    $http({
		method: 'POST',
		url: "/credit/finance/capital/v/queryBalance",
	}).success(function(data) {
		$scope.balanceAmt = data.data.balanceAmt;
	})

    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url: '/credit/finance/capital/v/list'}); 
    }
 
    $scope.capitalList = {
            options: {
                method:"post",
                url:"/credit/finance/capital/v/list",
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
                    title: '流水号',
                    field: 'flowNo',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '姓名',
                    field: 'name',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '身份证号',
                    field: 'idCard',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '银行卡号',
                    field: 'bankCard',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '手机号',
                    field: 'phone',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '开户银行',
                    field: 'bankName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '代扣金额 (元)',
                    field: 'withholdMoney',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '代扣时间',
                    field: 'withholdDate',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '绑定状态',
                    field: 'bindState',
                    align: 'center',
                    valign: 'bottom',
                    formatter:function(value, row, index){
                        if(row.bindState == 0){
                            return "初始化"
                        }else if(row.bindState == 1){
                            return "已绑定";
                        }else if (row.bindState == 2){
                            return "已解绑"
                        }
                        return "-"
                    }
                }, {
                    title: '成功代扣次数',
                    field: 'successCount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '最近代扣状态',
                    field: 'withholdState',
                    align: 'center',
                    valign: 'bottom',
                    formatter:function(value, row, index){
                        if(row.withholdState == 0){
                            return "初始化";
                        }else if(row.withholdState == 1){
                            return "未代扣";
                        }else if(row.withholdState == 2){
                            return "代扣中";
                        }else if(row.withholdState == 3){
                            return "成功";
                        }else if (row.withholdState == 4){
                            return "失败";
                        }
                        return "-"
                    }
                }, {
                    title: '操作',
                    field: 'operate',
                    align: 'center',
                    valign: 'bottom',
                    events: operateEvents,
                    formatter: function(value, row, index){
                        var html = '';
                        if($scope.meunAuth.indexOf('auth23auth')>=0){
	                        if(row.bindState == 1){
	                        	if(row.unbind==1){
	                            	html+='<a class="unbind" href="javascript:void(0)">禁止解绑</a>&nbsp;&nbsp;';
	                            }else if(row.unbind == 2){
	                            	html+='<a class="unbind" href="javascript:void(0)">允许解绑</a>&nbsp;&nbsp;';
	                            }
	                             html += '<a class="facesign" href="javascript:void(0)">发起代扣</a>&nbsp;&nbsp;';
	                        }
                        }
                        html += '<a class="lookDetail" href="javascript:void(0)">查看详情</a>&nbsp;&nbsp;';
                        return html;  
                    }
                }]
            }
    };
    
    
    $scope.daikou = function(flowNo,withholdState){
		$scope.obj = new Object();
		$scope.obj.flowNo = flowNo;
		$scope.obj.withholdState = withholdState;
		var dk = function(){
			$(".lhw-alert-ok").attr("disabled","disabled");
			$http({
				method: 'POST',
				url:'/credit/finance/capital/v/inCapital',
				data:$scope.obj
			}).success(function(data){
				if(data.code == 'SUCCESS'){
					box.closeAlert();
					box.boxAlert(data.msg);
					window.location.reload();
				}else{
					box.boxAlert(data.msg);
				}
			})
		}
		box.editAlert($scope,"确认提交代扣信息吗",$("#capitalId").html(),dk);
	}
    
    $scope.unbind = function(flowNo,unbind){
		$scope.obj = new Object();
		$scope.obj.flowNo = flowNo;
		if(unbind==1){
			$scope.obj.unbind = 2;
		}else if(unbind == 2){
			$scope.obj.unbind = 1;
		}
		
		var jieb = function(){
			$http({
				method: 'POST',
				url:'/credit/finance/capital/v/unbind',
				data:$scope.obj
			}).success(function(data){
				box.closeAlert();
				box.boxAlert(data.msg);
				if(data.code == "SUCCESS"){
					$scope.query();
				}
			})
		}
		if(unbind==1){
			box.editAlert($scope,"解绑","确定禁止用户在快鸽按揭APP解除银行卡绑定吗？",jieb);
		}else if(unbind == 2){
			box.editAlert($scope,"解绑","确定允许用户在快鸽按揭APP解除银行卡绑定吗？",jieb);
		}
		
	}
    
    $scope.withdrawals = function(){
		var outCapital = function(){
			$(".lhw-alert-ok").attr("disabled","disabled");
			$http({
				method: 'POST',
				url:"/credit/finance/capital/v/withdrawals" ,
				data:$scope.obj
			}).success(function(data){
				if(data.code == "SUCCESS"){
					alert("提现成功！");
					box.closeAlert();
					window.location.reload();
				}else{
					alert(data.msg);
				}
			})
		}
		box.editAlert($scope,"提现",$("#outCapital").html(),outCapital);
	}
    

});