angular.module("anjboApp", ['bsTable']).controller("afterLoanListCtrl",function($scope,$compile,$cookies,$http,$state,$timeout,$filter,box,process,parent){
    
    $scope.page = $cookies.getObject("pageParams");
    if(!$scope.page){
    	$scope.page = new Object();
		$scope.page.start = 0;
		$scope.page.pageSize = 15;
    	$scope.page.type = "0";
    	$scope.isAudit=false;
    }
    
    $http({
        method: 'POST',
        url:'/credit/order/base/v/selectionConditions'
    }).success(function(data){
        $scope.conditions = data.data;        
        $scope.orderCitys = new Array();
        if(!$scope.conditions){
           return;
        }
        angular.forEach($scope.conditions.citys, function(data,index){
            if(index == 0){
                data.cityName = '请选择';
            }
            angular.forEach(data.productList, function(data,index){
                if(index == 0){
                    data.productName = '请选择';
                }
                angular.forEach(data.stateList, function(data,index){
                    if(index == 0){
                        data.stateName = '请选择';
                    }
                });
            });
            $scope.orderCitys.push(data);
        });
        $scope.productList = $scope.conditions.citys[0].productList;
        $scope.stateList = $scope.conditions.citys[0].productList[0].stateList;
    })
         

    

    // 关联订单
    function loadRelationOrders(customerName){    	
		$http({
			method: 'POST',
			url:'/credit/order/base/v/selectAbleRelationOrder',
			data:{'cityCode':$scope.orderDto.cityCode, 'borrowerName':customerName}
		}).success(function(data){
			if(data.code == "SUCCESS"){
				$scope.relationOrderNoList = data.data;
				if(null!=$scope.relationOrderNoList && $scope.relationOrderNoList.length>0){
					$scope.orderDto.relationOrderNo = $scope.relationOrderNoList[0].orderNo;
				}
			}
		}).error(function(){
			box.closeWaitAlert();
		});  
    }


    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        return $scope.page;
    }
    
	function relationFormatter(value, row, index){
        var html = "";
        if(row.relationOrderNo){
            html += '<a class="lookRelationOrder" href="javascript:void(0)">查看</a>&nbsp;&nbsp;';
        }else{
        	html = "--";
        }
        return html;
    }
    function operateFormatter(value, row, index) {
        var html = '<a class="lookAfterLoan" href="javascript:void(0)">查看贷后</a>&nbsp;&nbsp;';
        //测试用要去掉
        //html += '<a class="restartAfterLoan" href="javascript:void(0)">测试数据重置</a>';
        return html;
    }

	window.relationEvents = {
        'click .lookOrder': function (e, value, row, index) {
            //$state.go("orderDetail",{'orderNo':row.relationOrderNo, 'processId':row.processId, 'productCode':'01', 'relationOrderNo':'0'});
            $state.go("orderDetail",{'orderNo':row.orderNo,'cityCode':row.cityCode,'processId':row.processId,'productCode':row.productCode,'relationOrderNo':row.orderNo});
        }
    };
    window.operateEvents = {
        'click .lookAfterLoan':function(e,value,row,index){
        	
            //资金方代号
            $http({
                        method: 'POST',
                        url: '/credit/order/allocationFund/v/processDetails',
                        data:{'orderNo':row.orderNo}
                    }).then(function successCallback(response) {
                        if("SUCCESS"==response.data.code){
                            $scope.fundCode=response.data.data[0].fundCode;
                           
                        }
                        var t=$scope.fundCode;
                    	if ( t!=undefined) {
                    		
                    		if (t=='1000') {
                    			$state.go("afterLoanDetail",{'orderNo':row.orderNo,'repaymentType':row.repaymentType,'cityCode':row.cityCode,'productCode':row.productCode});
            				}
                    		else {
                				$state.go("afterLoanDetail2",{'orderNo':row.orderNo,'repaymentType':row.repaymentType,'cityCode':row.cityCode,'productCode':row.productCode});	
                			}
                        
            			}else {
            				$state.go("afterLoanDetail2",{'orderNo':row.orderNo,'repaymentType':row.repaymentType,'cityCode':row.cityCode,'productCode':row.productCode});	

            			}
                        
                    }, function errorCallback(response) { 
                       
                        $state.go("afterLoanDetail2",{'orderNo':row.orderNo,'repaymentType':row.repaymentType,'cityCode':row.cityCode,'productCode':row.productCode});	
                        
                       // box.boxAlert("HTTP Status:"+response.status)
                    });

        },
        'click .restartAfterLoan':function(e,value,row,index){
            $http(
                {
                    url:'/credit/finance/afterLoanList/v/restart',
                    method:'POST',
                    data:{'orderNo':row.orderNo}
                }
            ).success(function(data){
                if("SUCCESS"==data.code){
                    box.boxAlert("数据重置成功");
                } else {
                    box.boxAlert(data.msg);
                }
                });
        }
    };
    
    $scope.query = function(){
        if($scope.page.startLendingTime&&$scope.page.endLendingTime
        &&$scope.page.startLendingTime>$scope.page.endLendingTime){
            box.boxAlert("放款开始时间不能大于结束时间");
            return;
        }
        if($scope.page.startNewRepayment&&$scope.page.endNewRepayment
            &&$scope.page.startNewRepayment>$scope.page.endNewRepayment){
            box.boxAlert("最新还款开始时间不能大于结束时间");
            return;
        }
        $("#table").bootstrapTable('refresh', {url: '/credit/finance/afterLoanList/v/list',pageNumber:1});
    }

    $scope.sort = function(field){
        if("loanAmount"==field){
            if("DESC" == $scope.page.loanAmountOrderBy){
                $scope.page.loanAmountOrderBy = "ASC";
            } else if("ASC" == $scope.page.loanAmountOrderBy){
                $scope.page.loanAmountOrderBy = "DESC";
            } else if(!$scope.page.loanAmountOrderBy){
                $scope.page.loanAmountOrderBy = "DESC";
            }
            $scope.page.lendingTimeOrderBy = "";
            $scope.page.overdueDayOrderBy = "";
            $scope.page.newRepaymentOrderBy = "";
        } else if("lendingTime"==field){
            if("DESC" == $scope.page.lendingTimeOrderBy){
                $scope.page.lendingTimeOrderBy = "ASC";
            } else if("ASC" == $scope.page.lendingTimeOrderBy){
                $scope.page.lendingTimeOrderBy = "DESC";
            } else {
                $scope.page.lendingTimeOrderBy = "DESC";
            }
            $scope.page.loanAmountOrderBy = "";
            $scope.page.overdueDayOrderBy = "";
            $scope.page.newRepaymentOrderBy = "";
        } else if("overdueDay"==field){
            if("DESC" == $scope.page.overdueDayOrderBy){
                $scope.page.overdueDayOrderBy = "ASC";
            } else if("ASC" == $scope.page.overdueDayOrderBy){
                $scope.page.overdueDayOrderBy = "DESC";
            } else {
                $scope.page.overdueDayOrderBy = "DESC";
            }
            $scope.page.lendingTimeOrderBy = "";
            $scope.page.loanAmountOrderBy = "";
            $scope.page.newRepaymentOrderBy = "";
        } else if("newRepayment"==field){
            if("DESC" == $scope.page.newRepaymentOrderBy){
                $scope.page.newRepaymentOrderBy = "ASC";
            } else if("ASC" == $scope.page.newRepaymentOrderBy){
                $scope.page.newRepaymentOrderBy = "DESC";
            } else {
                $scope.page.newRepaymentOrderBy = "DESC";
            }
            $scope.page.overdueDayOrderBy = "";
            $scope.page.lendingTimeOrderBy = "";
            $scope.page.loanAmountOrderBy = "";
        }
        $scope.query();
    }
    
    var columnSwitchList = $cookies.getObject("columnSwitch");
    if(!columnSwitchList){
    	columnSwitchList = {
    		"id":false,
    		"contractNo":false,
    		"cityName":true,
    		"productName":true,
    		"customerName":true,
    		"borrowingAmount":true,
    		"borrowingDay":true,
    		"cooperativeAgencyName":true,
    		"channelManagerName":true,
    		"acceptMemberName":true,
    		"planPaymentTime":false,
    		"distancePaymentDay":false,
    		"previousHandler":false,
    		"previousHandleTime":false,
    		"state":true,
    		"source":false,
    		"currentHandler":true,
    		"relation":true,
    		"operate":true
    	};
    	$cookies.putObject("columnSwitch",columnSwitchList);
    }
    $scope.orderList = {
            options: {
                method:"post",
                url:"/credit/finance/afterLoanList/v/list",
                queryParams:getParams,
                sidePagination:'server',
                undefinedText:"-",
                cache: false,
                striped: true,
                pagination: true,
				pageNumber: ($scope.page.start/$scope.page.pageSize)+1,
				pageSize: $scope.page.pageSize,
                pageList: ['All'],
                showColumns: true,
                showRefresh: false,
                onSort:function(field){
                    $scope.sort(field);
                },
                onClickRow:function(row,$element,field){
                	$element.toggleClass("bule-bg");
                },
                onColumnSwitch:function(field,checked){
                	columnSwitchList[field] = checked;
                	$cookies.putObject("columnSwitch",columnSwitchList);
                },
                columns: [ {
                    title: '城市',
                    field: 'cityName',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.cityName
                }, {
                    title: '产品名称',
                    field: 'productName',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.productName
                }, {
                    title: '合同编号',
                    field: 'contractNo',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.contractNo
                }, {
                    title: '客户姓名',
                    field: 'borrowerName',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.borrowerName
                }, {
                    title: '借款金额（万元）',
                    field: 'loanAmount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    visible:columnSwitchList.loanAmount
                }, {
                    title: '借款期限（期）',
                    field: 'borrowingPeriods',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.borrowingPeriods
                }, {
                    title: '放款日期',
                    field: 'lendingTime',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(value, row, index){
                        var lendingTime = value;
                        if(lendingTime){
                            lendingTime = $filter('date')(lendingTime,"yyyy-MM-dd");
                        }
                        return lendingTime;
                    },
                    visible:columnSwitchList.lendingTime
                }, {
                    title: '渠道经理',
                    field: 'channelManagerName',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.channelManagerName
                }, {
                    title: '受理员',
                    field: 'acceptMemberName',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.acceptMemberName
                },  {
                    title: '逾期天数',
                    field: 'overdueDay',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(value, row, index){
                        if(7==row.repaymentStatus){
                            return '-';
                        }
                        return value;
                    },
                    visible:columnSwitchList.overdueDay
                }, {
                    title: '最新还款日',
                    field: 'newRepayment',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(value, row, index){
                        if(7==row.repaymentStatus){
                            return '-';
                        }
                        var newRepayment = value;
                        if(newRepayment){
                            newRepayment = $filter('date')(newRepayment,"yyyy-MM-dd");
                        }
                        return newRepayment;
                    },
                    visible:columnSwitchList.newRepayment
                }, {
                    title: '还款状态',
                    field: 'repaymentStatusName',
                    align: 'center',
                    valign: 'bottom',
                    visible:columnSwitchList.repaymentStatusName
                }, {
                    title: '查看订单',
                    field: 'relation',
                    align: 'center',
                    valign: 'bottom',
                    events: relationEvents,
                    formatter: function(value,row,index){
                        return '<a class="lookOrder" href="javascript:void(0)">查看订单</a>';
                    },
                    visible:columnSwitchList.relation
                }, {
                    title: '操作',
                    field: 'operate',
                    align: 'center',
                    valign: 'bottom',
                    events: operateEvents,
                    formatter: operateFormatter,
                    visible:columnSwitchList.operate
                }]
            }};

			 $timeout(function(){
				 $(".tooltip-toggle").tooltip({
			         html: true
		         });
			 })

});