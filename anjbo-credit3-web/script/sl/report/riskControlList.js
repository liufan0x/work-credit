angular.module("anjboApp", ['bsTable']).controller("riskControlCtrl", function ($scope, $compile, $http, $state, box, process, parent) {
    // 定义查询条件对象
    // $scope.page0 = new Object();
    // $scope.page0.productCode = ''
    // $scope.page1 = new Object();
    // $scope.page2 = new Object();

    $scope.page0 = new Object();
    $scope.page0.productCode = '';
    $scope.page0.startTime = '';
    $scope.page0.endTime = '';
    $scope.page0.cityCode = '';
    $scope.page0.createTime = '1';

    $scope.page1 = new Object();
    $scope.page1.cityCode = '';
    $scope.page1.startTime = '';
    $scope.page1.endTime = '';
    $scope.page1.createTime = '1';
    $scope.page1.productCode = '';

    $scope.page2 = new Object();
    $scope.page2.cityCode = '';
    $scope.page2.startTime = '';
    $scope.page2.endTime = '';
    $scope.page2.createTime = '1';
    $scope.page2.productCode = '';
    $scope.riskAuditTable = {};
    $scope.auditFirstTable = {};
    $scope.auditFinaltTable = {};

    $scope.onblueClick = 1;
    $scope.auditType = '';
    $scope.paramsMap= {
        cityCode: "",
        auditType: "",
        lendTime: "",
        createTime: "",
        startTime: "",
        endTime: "",
        productCode: ""
    };



    //获取城市列表
    getList();
    $scope.reportType = "0";

    function getParams0(data) {
        // 查询条件
        $scope.page0.start = data.offset;
        $scope.page0.pageSize = data.limit;
        return $scope.page0;
    }

        function getRiskAuditRate(data) {
        // 查询条件
        $scope.page0.start = data.offset;
        $scope.page0.pageSize = data.limit;
        return $scope.page0;
    }
     $scope.queryRiskAuditRate=function(flag,refresh){
        if(flag!='undefined'&& flag!="" &&flag !=1&&flag !=2&&flag !=3&&flag !=4) {
            flag==''?$scope.onblueClick = 1:flag == 1 ? $scope.onblueClick = 1 : flag == 2 ? $scope.onblueClick = 2 : $scope.onblueClick = 3;
            flag==''?$scope.auditType = 'auditFirst':flag == 1 ? $scope.auditType = 'auditFirst' : flag == 2 ? $scope.auditType = 'auditFinal' : $scope.auditType = 'auditOfficer';
        }else if(flag!=""){
            $scope.onblueClick = flag;
            flag ==1?$scope.auditType='auditFirst':flag ==2?$scope.auditType = 'auditFinal': $scope.auditType = 'auditOfficer';
        }
         $scope.paramsMap.auditType = $scope.auditType;
         $scope.paramsMap.startTime= $scope.page0.startTime;
         $scope.paramsMap.endTime = $scope.page0.endTime;
         $scope.paramsMap.productCode = $scope.page0.productCode;
         $scope.paramsMap.createTime = $scope.page0.createTime;
         $scope.paramsMap.cityCode = $scope.page0.cityCode;
         $scope.paramsMap.cityName=angular.element("#page0_cityId option:selected").text();
         if (refresh==""){
             $("#riskAuditTotalTable").bootstrapTable('destroy');
             $("#riskAuditTotalTable").bootstrapTable({
                 method: "POST",
                 url: "/credit/report/riskAudit/v/queryRiskAuditRate",
                 queryParams: JSON.stringify($scope.paramsMap),
                 sidePagination: 'server',
                 undefinedText: "0",
                 cache: false,                        //是否使用缓存
                 showColumns: true,                  //是否显示所有的列
                 showRefresh: true,                  //是否显示刷新按钮
                 pagination: true,                    //启用分页
                 pageNumber: 1,                        //初始化加载第一页，默认第一页
                 pageSize: 30,                        //每页的记录行数
                 pageList: [30, 50],
                 columns: [{
                     title: '分公司',
                     field: 'cityName',
                     align: 'center',
                     valign: 'bottom'
                 }, {
                     title: '补充资料退单数',
                     field: 'addInformation',
                     align: 'center',
                     valign: 'bottom'
                 }, {
                     title: '拒单退单数',
                     field: 'refusal',
                     align: 'center',
                     valign: 'bottom'
                 }, {
                     title: '系统修改退单数',
                     field: 'modifySys',
                     align: 'center',
                     valign: 'bottom'
                 }, {
                     title: '其他原因退单数',
                     field: 'other',
                     align: 'center',
                     valign: 'bottom'
                 }],
                 responseHandler: function (data) {
                     return {"rows": data.rows, "total": data.total};
                 }
             });

         }else {
             //订单风控
             $scope.riskAuditTotalTable = {
                 options: {
                     method: "POST",
                     url: "/credit/report/riskAudit/v/queryRiskAuditRate",
                     queryParams: JSON.stringify($scope.paramsMap),
                     sidePagination: 'server',
                     undefinedText: "",
                     cache: false,                        //是否使用缓存
                     showColumns: true,                  //是否显示所有的列
                     showRefresh: true,                  //是否显示刷新按钮
                     pagination: true,                    //启用分页
                     pageNumber: 1,                        //初始化加载第一页，默认第一页
                     pageSize: 30,                        //每页的记录行数
                     pageList: [30, 50],
                     columns: [{
                         title: '分公司',
                         field: 'cityName',
                         align: 'center',
                         valign: 'bottom'
                     }, {
                         title: '补充资料退单数',
                         field: 'addInformation',
                         align: 'center',
                         valign: 'bottom'
                     }, {
                         title: '拒单退单数',
                         field: 'refusal',
                         align: 'center',
                         valign: 'bottom'
                     }, {
                         title: '系统修改退单数',
                         field: 'modifySys',
                         align: 'center',
                         valign: 'bottom'
                     }, {
                         title: '其他原因退单数',
                         field: 'other',
                         align: 'center',
                         valign: 'bottom'
                     }],
                     responseHandler: function (data) {
                         return {"rows": data.rows, "total": data.total};
                     }
                 }
             };
         }
    }

    function getParams1(data) {
        // 查询条件
        var obj = new Object()
        obj.start = data.offset
        obj.pageSize = data.limit
        obj.startTime = $scope.page1.startTime
        obj.endTime = $scope.page1.endTime
        obj.productCode = $scope.page1.productCode
        obj.createTime = $scope.page1.createTime
        obj.cityCode = $scope.page1.cityCode
        obj.cityName=angular.element("#page1_cityId option:selected").text();
        if (data.sort) {
            obj[data.sort] = data.order
        }
        return obj;
    }

    function getParams2(data) {
        // 查询条件
        var obj = new Object()
        obj.start = data.offset
        obj.pageSize = data.limit
        obj.startTime = $scope.page2.startTime
        obj.endTime = $scope.page2.endTime
        obj.productCode = $scope.page2.productCode
        obj.createTime = $scope.page2.createTime
        obj.cityCode = $scope.page2.cityCode
        obj.cityName=angular.element("#page2_cityId option:selected").text();
        if (data.sort) {
            obj[data.sort] = data.order
        }
        return obj;
    }
    $scope.clearDate = function (event, obj) {
        $(event.target).prev(".form_date").val("");
        if (obj == 'start') {
            $scope.page0.startTime = '';
        } else if (obj == 'end') {
            $scope.page0.endTime = '';
        }
    }
    // watch()
    function watch() {
        $scope.$watch("page0.createTime", function (newVal, old) {

            // if(newVal == old){
            //     return
            // }
            $scope.queryRiskAuditRate("","");
            $scope.query('clear');
        });
        $scope.$watch("page0.cityCode", function (newVal, old) {

            if (newVal == old) {
                return
            }
            $scope.query();
        });
        $scope.$watch("page0.productCode", function (newVal, old) {

            if (newVal == old) {
                return
            }
            $scope.query();
        });
        $scope.$watch("page1.createTime", function (newVal, old) {


            if (newVal == old) {
                return
            }
            $scope.query();
        });
        $scope.$watch("page1.cityCode", function (newVal, old) {

            if (newVal == old) {
                return
            }
            $scope.query();
        });
        $scope.$watch("page1.productCode", function (newVal, old) {

            if (newVal == old) {
                return
            }
            $scope.query();
        });
        $scope.$watch("page2.createTime", function (newVal, old) {
            if (newVal == old) {
                return
            }
            $scope.query();
        });
        $scope.$watch("page2.cityCode", function (newVal, old) {

            if (newVal == old) {
                return
            }
            $scope.query();
        });
        $scope.$watch("page2.productCode", function (newVal, old) {

            if (newVal == old) {
                return
            }
            $scope.query();
        });

    }
    $scope.test = function () {
        console.log($scope)
    }

    // 刷新表格
    $scope.query = function (mes) {
        if (mes) {
            $scope.page0.startTime = ''
            $scope.page0.endTime = ''
        }else if(typeof (mes)=="undefined"){
            $scope.queryRiskAuditRate("","");
        }
        if ($scope.reportType == 0) { //订单风控
            $("#riskAuditTable").bootstrapTable('refresh', {
                url: '/credit/report/riskAudit/v/query'
            });

        } else if ($scope.reportType == 1) { //初审
            $("#auditFirstTable").bootstrapTable('refresh', {
                url: '/credit/report/auditFirst/v/query',
                pageNumber: 1,
                pageSize: 10
            });

        } else if ($scope.reportType == 2) { //终审
            $("#auditFinaltTable").bootstrapTable('refresh', {
                url: '/credit/report/auditFinal/v/query',
                pageNumber: 1,
                pageSize: 10
            });

        }
    }



    function getList() {
        $http({
            method: 'POST',
            url: '/credit/order/base/v/selectionConditions'
        }).success(function (data) {
            $scope.conditions = data.data;
            $http.post(`/credit/report/achievementStatistics/v/city`).then(function (res) {
                $scope.orderCitys = res.data.data
                $scope.page0.cityCode = $scope.orderCitys[0].code
                $scope.page1.cityCode = $scope.orderCitys[0].code
                $scope.page2.cityCode = $scope.orderCitys[0].code
                $scope.productList = $scope.conditions.citys[0].productList;
                $scope.productList.pop()
                $scope.productList[0].productName = '全部'
                $scope.stateList = $scope.conditions.citys[0].productList[0].stateList;
                //订单风控
                $scope.riskAuditTable = {
                    options: {
                        method: "post",
                        url: "/credit/report/riskAudit/v/query",
                        queryParams: getParams0,
                        sidePagination: 'server',
                        undefinedText: "-",
                        cache: false,
                        striped: true,
                        pagination: true,
                        pageNumber: 1,
                        pageSize: 20,
                        pageList: [],
                        showColumns: true,
                        //exportDataType:'all',
                        exportTypes: ['excel'],
                        showRefresh: false,
                        columns: [{
                            title: '分公司',
                            field: 'cityName',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '已初审订单（笔）',
                            field: 'firstCount',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '已终审订单（笔）',
                            field: 'finalCount',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '已首席审批订单（笔）',
                            field: 'officerCount',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '审批通过订单（笔）',
                            field: 'auditCount',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '已退回订单（笔）',
                            field: 'backCount',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '已否决订单（笔）',
                            field: 'closeCount',
                            align: 'center',
                            valign: 'bottom'
                        }]
                    }
                };

                //初审
                $scope.auditFirstTable = {
                    options: {
                        method: "post",
                        url: "/credit/report/auditFirst/v/query",
                        queryParams: getParams1,
                        sidePagination: 'server',
                        undefinedText: "0",
                        cache: false,
                        striped: true,
                        pagination: true,
                        sortName: 'orderCount',
                        sortOrder: 'desc',
                        pageNumber: 1,
                        pageSize: 10,
                        pageList: [10, 20, 'all'],
                        showColumns: true,
                        // exportDataType:'all',
                        exportTypes: ['excel'],
                        columns: [{
                            title: '序号',
                            field: 'createUid',
                            align: 'center',
                            valign: 'bottom',
                            formatter: function (row, value, index, field) {
                                return index + 1
                            }
                        }, {
                            title: '初审人姓名',
                            field: 'firstName',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="统计初审时的订单数，多次退回的订单只统计一次">订单数 <span class="help"></span></span>',
                            field: 'orderCount',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="统计初审时的订单金额，多次退回的订单金额只统计一次">订单金额（万） <span class="help"></span></span>',
                            field: 'orderAmount',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="统计初审时的平均审批总时长，多次退回的订单平均审批时长会累积统计">平均审批时长（分） <span class="help"></span></span>',
                            field: 'timeNumAvg',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true,
                            formatter: function (row, value, index, field) {
                                return row ? parseInt(row) : "0"
                            }
                        }]
                    }
                };


                //终审
                $scope.auditFinaltTable = {
                    options: {
                        method: "post",
                        url: "/credit/report/auditFinal/v/query",
                        queryParams: getParams2,
                        sidePagination: 'server',
                        undefinedText: "0",
                        cache: false,
                        striped: true,
                        pagination: true,
                        sortName: 'orderCount',
                        sortOrder: 'desc',
                        pageNumber: 1,
                        pageSize: 10,
                        pageList: [10, 20, 'all'],
                        showColumns: true,
                        // exportDataType:'all',
                        exportTypes: ['excel'],
                        showRefresh: false,
                        columns: [{
                            title: '序号',
                            field: 'createUid',
                            align: 'center',
                            valign: 'bottom',
                            formatter: function (row, value, index, field) {
                                return index + 1
                            }
                        }, {
                            title: '终审人姓名',
                            field: 'firstName',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="统计终审时的订单数，多次退回的订单只统计一次">订单数 <span class="help"></span></span>',
                            field: 'orderCount',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="统计终审时的订单金额，多次退回的订单金额只统计一次">订单金额（万） <span class="help"></span></span>',
                            field: 'orderAmount',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="统计终审时的平均审批总时长，多次退回的订单平均审批时长会累积统计">平均审批时长（分） <span class="help"></span></span>',
                            field: 'timeNumAvg',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true,
                            formatter: function (row, value, index, field) {
                                return row ? parseInt(row) : "0"
                            }
                        }]
                    }
                };
                setTimeout(() => {
                    watch()
                    selSet()
                }, 100);
            })

        })
    }

    function selSet() {
        setTimeout(function () {
            $(".tooltip-toggle").tooltip({
                html: true
            });
            $('.selectcity001')[0].removeChild($('.selectcity001')[0].firstChild)
            $('.selectcity002')[0].removeChild($('.selectcity002')[0].firstChild)
            $('.selectcity003')[0].removeChild($('.selectcity003')[0].firstChild)
        }, 100)
    }

    // setTimeout(function () {
    //     return
    //     for (let i = 0; i < $('.fixed-table-body').length; i++) {
    //         var tableCont = $('.fixed-table-body')[i]

    //         function scrollHandle(e) {
    //             var scrollTop = this.scrollTop;
    //             $('.fixed-table-body thead')[i].style.transform = 'translateY(' + scrollTop + 'px)';
    //         }
    //         tableCont.addEventListener('scroll', scrollHandle)
    //     }
    // }, 500)
    $scope.queryRiskAuditRate("");
});