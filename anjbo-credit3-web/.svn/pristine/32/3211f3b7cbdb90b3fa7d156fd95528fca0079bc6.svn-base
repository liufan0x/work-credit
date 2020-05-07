angular.module("anjboApp", ['bsTable']).controller("currentCtrl", function ($scope, $compile, $http, $state, box, process, parent) {
    // 定义查询条件对象
    $scope.inpaymentList = {}
    $scope.outpaymentList = {}
    $scope.page0 = new Object();
    $scope.page0.productCode = ''
    $scope.page0.cityCode = ''
    $scope.page1 = new Object();
    $scope.page1.productCode = ''
    $scope.page1.cityCode = ''
    $scope.riskList = new Object();
    $scope.showRisk = $scope.$parent.hasCeoRight
    //获取城市列表
    getList()
    //声明报表类型，默认为出回款报表
    $scope.reportType = "0";
    //生成出回款请求参数
    function getParams(data) {
        $scope[`page${$scope.reportType}`].start = data.offset;
        $scope[`page${$scope.reportType}`].pageSize = data.limit;
        return {
            cityCode: $scope.page0.cityCode,
            productCode: $scope.page0.productCode,
            start: $scope.page0.start,
            pageSize: $scope.page0.pageSize

        }
    }
    // 监听出回款参数刷新出回款表格
    watch()

    function watch() {
        $scope.$watch("page0.data", function (newVal, old) {
            $scope.query();
        });
        $scope.$watch("page0.cityCode", function (newVal, old) {
            $scope.query();
        });
        $scope.$watch("page0.productCode", function (newVal, old) {
            $scope.query();
        });
        //监听风控参数刷新数据
        $scope.$watch("page1.cityCode", function (newVal, old) {
            if (newVal == old) {
                return
            }
            getRiskList($scope.page1);
        });
        $scope.$watch("page1.productCode", function (newVal, old) {
            if (newVal == old) {
                return
            }
            getRiskList($scope.page1);
        });

    }
    //测试数据
    $scope.test = function () {
        console.log($scope)
    }
    //获取城市列表方法
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
                $scope.productList = $scope.conditions.citys[0].productList;
                $scope.productList.pop()
                $scope.productList[0].productName = '全部'
                $scope.stateList = $scope.conditions.citys[0].productList[0].stateList;
                //回款表格
                $scope.inpaymentList = {
                    options: {
                        method: "post",
                        url: "/credit/report/statistics/v/selectInPayment",
                        queryParams: getParams,
                        sidePagination: 'server',
                        undefinedText: "0",
                        cache: false,
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
                            title: '分公司',
                            field: 'name',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '今日预计回款（万）',
                            field: 'loanAmount',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '明日预计回款（万）',
                            field: 'tomorrowLoanAmount',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="包括今日预计回款、明日预计回款的订单数">三日内预计回款（万） <span class="help"></span></span>',
                            field: 'tridLoanAmount',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="包括今日预计回款、明日预计回款的订单数、七日内预计回款的订单数">七日内预计回款（万） <span class="help"></span></span>',
                            field: 'sevenLoanAmount',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="包括今日预计回款、明日预计回款的订单数、七日内预计回款、15日内预计回款的订单数">十五日内预计回款（万） <span class="help"></span></span>',
                            field: 'fifteenLoanAmount',
                            align: 'center',
                            valign: 'bottom'
                        }]
                    }
                };

                //出款表格
                $scope.outpaymentList = {
                    options: {
                        method: "post",
                        url: "/credit/report/statistics/v/selectOutPayment",
                        queryParams: getParams,
                        sidePagination: 'server',
                        undefinedText: "0",
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
                            title: '分公司',
                            field: 'name',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '今日预计出款（万）',
                            field: 'loanAmount',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '明日预计出款（万）',
                            field: 'tomorrowLoanAmount',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '三日内预计出款（万）',
                            field: 'tridLoanAmount',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '七日内预计出款（万）',
                            field: 'sevenLoanAmount',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: '十五日内预计出款（万）',
                            field: 'fifteenLoanAmount',
                            align: 'center',
                            valign: 'bottom'
                        }]
                    }
                };
                setTimeout(() => {
                    // watch()
                    getRiskList($scope.page1)
                    selSet()
                }, 100);
            })
        })
    }

    // 刷新表格
    $scope.query = function () {
        if ($scope.page0.data == "in") {
            $("#inpaymentList").bootstrapTable('refresh', {
                url: '/credit/report/statistics/v/selectInPayment'
            });
        } else if ($scope.page0.data == 'out') {
            $("#outpaymentList").bootstrapTable('refresh', {
                url: '/credit/report/statistics/v/selectOutPayment'
            });
        }
    }



    //获取风控列表方法
    function getRiskList(data) {
        $http({
            method: 'POST',
            url: '/credit/report/statistics/v/selectToDayOrder',
            data: data
        }).success(function (data) {
            $scope.riskList = data.data
            // watch()
        })
    }
    
    function selSet(){
        setTimeout(function () {
            $('.selectcity01')[0].removeChild($('.selectcity01')[0].firstChild)
            if ($scope.showRisk) {
                $('.selectcity02')[0].removeChild($('.selectcity02')[0].firstChild)
            }
            $(".tooltip-toggle").tooltip({
                html: true
            });
        }, 100)
    }
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