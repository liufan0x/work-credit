angular.module("anjboApp", ['bsTable']).controller("runningCtrl", function ($scope, $compile, $http, $state, box, process, parent) {
    // 定义查询条件对象
    $scope.page0 = new Object();
    $scope.page0.timeWhere = 'thisMonth'
    $scope.page0.productCode = ''
    // $scope.page0.cooperativeModeId='1'
    $scope.page1 = new Object();
    $scope.page1.timeWhere = 'thisMonth'
    $scope.page1.productCode = ''
    $scope.page1.cooperativeModeId = '1'
    // $scope.page3 = new Object();
    $scope.reportType = "0";

    function getParams1(data) {
        // 查询条件
        var obj = $scope.page0.startTime || $scope.page0.endTime ? {
            "productCode": $scope.page0.productCode, //产品code
            // "cooperativeModeId":$scope.page0.cooperativeModeId,	//1兜底,2非兜底    
            "startTime": $scope.page0.startTime, //开始时间
            "endTime": $scope.page0.endTime, //结束时间
            "start": data.offset,
            "pageSize": data.limit
        } : {
            "productCode": $scope.page0.productCode, //产品code
            // "cooperativeModeId":$scope.page0.cooperativeModeId,	//1兜底,2非兜底
            "timeWhere": $scope.page0.timeWhere, //timeWhere=lastWeek(检索条件：上周),timeWhere=lastMonth(检索条件：上月),timeWhere=yesterday(检索条件：昨日), timeWhere=thisMonth(检索条件：当月)
            "start": data.offset,
            "pageSize": data.limit
        }
        obj[`${data.sort}Sort`] = data.order
        return obj
    }

    function getParams2(data) {
        // 查询条件
        var obj = $scope.page1.startTime || $scope.page1.endTime ? {
            "productCode": $scope.page1.productCode, //产品code
            "cooperativeModeId": $scope.page1.cooperativeModeId,
            // "cooperativeModeId":$scope.page0.cooperativeModeId,	//1兜底,2非兜底    
            "startTime": $scope.page1.startTime, //开始时间
            "endTime": $scope.page1.endTime, //结束时间
            "start": data.offset,
            "pageSize": data.limit
        } : {
            "productCode": $scope.page1.productCode, //产品code
            "cooperativeModeId": $scope.page1.cooperativeModeId,
            // "cooperativeModeId":$scope.page0.cooperativeModeId,	//1兜底,2非兜底
            "timeWhere": $scope.page1.timeWhere, //timeWhere=lastWeek(检索条件：上周),timeWhere=lastMonth(检索条件：上月),timeWhere=yesterday(检索条件：昨日), timeWhere=thisMonth(检索条件：当月)
            "start": data.offset,
            "pageSize": data.limit
        }
        obj[`${data.sort}Sort`] = data.order
        return obj
    }
    //清空自定义时间
    $scope.clearDate = function (event, obj, index) {
        $(event.target).prev(".form_date").val("");
        if (obj == 'start') {
            $scope[`page${index}`].startTime = null;
        } else if (obj == 'end') {
            $scope[`page${index}`].endTime = null;
        }
    }
    $scope.test = function () {
        console.log($scope)
    }
    watch()
    //监听参数刷新表格
    function watch() {
        // $scope.$watch("page0.timeWhere",function(newVal,old){    
        //     if(newVal == old){
        //         return
        //     }        
        //     $scope.query0('clear');
        // })
        $scope.$watch("page0.productCode", function (newVal, old) {
            if (newVal == old) {
                return
            }
            $scope.query0();
        })
        $scope.$watch("page0.cooperativeModeId", function (newVal, old) {
            if (newVal == old) {
                return
            }
            $scope.query0();
        })
        // $scope.$watch("page1.timeWhere",function(newVal,old){    
        //     if(newVal == old){
        //         return
        //     }        
        //     $scope.query1('clear');
        // })
        $scope.$watch("page1.productCode", function (newVal, old) {
            if (newVal == old) {
                return
            }
            $scope.query1();
        })
        $scope.$watch("page1.cooperativeModeId", function (newVal, old) {
            if (newVal == old) {
                return
            }
            $scope.query1();
        })
    }
    // 刷新资方表格
    $scope.query0 = function (mes) {
        if (mes) {
            $scope.page0.startTime = ''
            $scope.page0.endTime = ''
        }
        $("#fundTable").bootstrapTable('refresh', {
            url: '/credit/report/statistics/v/fund',
            pageNumber: 1,
            pageSize: 10
        });
    }
    // 刷新机构表格
    $scope.query1 = function (mes) {
        if (mes) {
            $scope.page1.startTime = ''
            $scope.page1.endTime = ''
        }
        // console.log($scope.page1.cooperativeModeId)
        if($scope.page1.cooperativeModeId == 2){
            $("#agencyTable").bootstrapTable('refreshOptions', {
                url: '/credit/report/statistics/v/agency',
                pageNumber: 1,
                pageSize: 10,
                columns:[{
                    title: '序号',
                    field: 'agencyName',
                    align: 'center',
                    valign: 'bottom',
                    formatter: function (row, value, index, field) {
                        var pagesizer = $('#agencywrap .page-number.active a').html()
                        if (pagesizer > 1) {
                            return row == "总计" ? '' : index + 1
    
                        } else {
                            return row == "总计" ? '' : index
                        }
                    }
                }, {
                    title: '机构名称',
                    field: 'agencyName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '放款额（万）',
                    field: 'loanAmount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '业务单量（笔）',
                    field: 'orderCount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '回款额（万）',
                    field: 'payMentAmount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '回款量（笔）',
                    field: 'payCount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '创收（万）',
                    field: 'income',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }]
            });
        }else{
            $("#agencyTable").bootstrapTable('refreshOptions', {
                url: '/credit/report/statistics/v/agency',
                pageNumber: 1,
                pageSize: 10,
                columns:[{
                    title: '序号',
                    field: 'agencyName',
                    align: 'center',
                    valign: 'bottom',
                    formatter: function (row, value, index, field) {
                        var pagesizer = $('#agencywrap .page-number.active a').html()
                        if (pagesizer > 1) {
                            return row == "总计" ? '' : index + 1
    
                        } else {
                            return row == "总计" ? '' : index
                        }
                    }
                }, {
                    title: '机构名称',
                    field: 'agencyName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '放款额（万）',
                    field: 'loanAmount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '业务单量（笔）',
                    field: 'orderCount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '回款额（万）',
                    field: 'payMentAmount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '回款量（笔）',
                    field: 'payCount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '创收（万）',
                    field: 'income',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '授信额度（万）',
                    field: 'creditLimit',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '剩余额度（万）',
                    field: 'surplusQuota',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }]
            });
        }
        
    }
    //资方
    $scope.fund = {
        options: {
            method: "post",
            url: "/credit/report/statistics/v/fund",
            queryParams: getParams1,
            sidePagination: 'server',
            undefinedText: "-",
            cache: false,
            sortName: 'fundLoanAmount',
            sortOrder: 'desc',
            striped: true,
            pagination: true,
            pageNumber: 1,
            pageSize: 10,
            pageList: [10, 20, "all"],
            showColumns: true,
            //exportDataType:'all',
            exportTypes: ['excel'],
            showRefresh: false,

            columns: [{
                title: '序号',
                field: 'fundName',
                align: 'center',
                valign: 'bottom',
                formatter: function (row, value, index, field) {
                    var pagesizer = $('#fundwrap .page-number.active a').html()
                    if (pagesizer > 1) {
                        return row == "总计" ? '' : index + 1

                    } else {
                        return row == "总计" ? '' : index
                    }
                }
            }, {
                title: '资方名称',
                field: 'fundName',
                align: 'center',
                valign: 'bottom'
            }, {
                title: '放款额（万）',
                field: 'fundLoanAmount',
                align: 'center',
                valign: 'bottom',
                sortable: true
            }, {
                title: '业务单量（笔）',
                field: 'orderNoCount',
                align: 'center',
                valign: 'bottom',
                sortable: true
            }, {
                title: '回款额（万）',
                field: 'payMentAmount',
                align: 'center',
                valign: 'bottom',
                sortable: true
            }, {
                title: '回款量（笔）',
                field: 'payCount',
                align: 'center',
                valign: 'bottom',
                sortable: true
            }]
        }
    };

    //机构
    $scope.agency = {
        options: {
            method: "post",
            url: "/credit/report/statistics/v/agency",
            queryParams: getParams2,
            sidePagination: 'server',
            undefinedText: "-",
            sortName: 'loanAmount',
            sortOrder: 'desc',
            cache: false,
            striped: true,
            pagination: true,
            pageNumber: 1,
            pageSize: 10,
            pageList: [10, 20, 'all'],
            showColumns: true,
            // exportDataType:'all',
            exportTypes: ['excel'],
            showRefresh: false,
            columns: [{
                title: '序号',
                field: 'agencyName',
                align: 'center',
                valign: 'bottom',
                formatter: function (row, value, index, field) {
                    if ($scope.agency.state.pageNumber > 1) {
                        return row == "总计" ? '' : index + 1

                    } else {
                        return row == "总计" ? '' : index
                    }
                }
            }, {
                title: '机构名称',
                field: 'agencyName',
                align: 'center',
                valign: 'bottom'
            }, {
                title: '放款额（万）',
                field: 'loanAmount',
                align: 'center',
                valign: 'bottom',
                sortable: true
            }, {
                title: '业务单量（笔）',
                field: 'orderCount',
                align: 'center',
                valign: 'bottom',
                sortable: true
            }, {
                title: '回款额（万）',
                field: 'payMentAmount',
                align: 'center',
                valign: 'bottom',
                sortable: true
            }, {
                title: '回款量（笔）',
                field: 'payCount',
                align: 'center',
                valign: 'bottom',
                sortable: true
            }, {
                title: '创收（万）',
                field: 'income',
                align: 'center',
                valign: 'bottom',
                sortable: true
            }, {
                title: '授信额度（万）',
                field: 'creditLimit',
                align: 'center',
                valign: 'bottom',
                sortable: true
            }, {
                title: '剩余额度（万）',
                field: 'surplusQuota',
                align: 'center',
                valign: 'bottom',
                sortable: true
            }]
        }
    }
    setTimeout(function () {
        $(".tooltip-toggle").tooltip({
            html: true
        });
    }, 1000)
    setTimeout(function () {
        return
        for (let i = 0; i < $('.fixed-table-body').length; i++) {
            var tableCont = $('.fixed-table-body')[i]

            function scrollHandle(e) {
                var scrollTop = this.scrollTop;
                $('.fixed-table-body thead')[i].style.transform = 'translateY(' + scrollTop + 'px)';
            }
            tableCont.addEventListener('scroll', scrollHandle)
        }
    }, 500)
});