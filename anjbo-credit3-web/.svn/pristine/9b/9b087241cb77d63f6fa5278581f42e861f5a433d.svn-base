angular.module("anjboApp", ['bsTable']).controller("performanceCtrl", function ($scope, $compile, $http, $state, box, process, $timeout, parent) {
    // bootastraptable 初始化，否则会报警告
    $scope.achievement = {}
    $scope.cityachievement = {}
    $scope.achievementStatistics = {}
    $scope.personalView = {}
    $scope.receivable = {}
    $scope.overDate = {}
    // 定义查询条件对象
    $scope.page0 = new Object();
    $scope.page0.productCode = ''
    $scope.page0.lendingTime = '本月'
    $scope.page0.data = '放款量'
    $scope.page1 = new Object();
    $scope.page1.productCode = ''
    $scope.page1.cityCode = ''
    $scope.page1.timeWhere = 'thisMonth'
    $scope.page1.selTime = ""
    $scope.page2 = new Object();
    $scope.page2.productCode = ''
    $scope.page2.cityCode = ''
    $scope.reportType = "0";
    getList()

    function getParams1(data) {
        // 时间表查询条件
        $scope[`page${$scope.reportType}`].start = data.offset;
        $scope[`page${$scope.reportType}`].pageSize = data.limit;
        return $scope.page0.startTime || $scope.page0.endTime ? {
            "startTime": $scope.page0.startTime,
            "endTime": $scope.page0.endTime,
            "cityName": $scope.page0.cityName == '全国' ? '不限' : $scope.page0.cityName,
            "productCode": $scope.page0.productCode,
            "start": $scope.page0.start,
            "pageSize": $scope.page0.pageSize,
            "groupBy": "时间",
            "sortOrder": data.order,
            "sortName": data.sort
        } : {
            "cityName": $scope.page0.cityName == '全国' ? '不限' : $scope.page0.cityName,
            "productCode": $scope.page0.productCode,
            "start": $scope.page0.start,
            "pageSize": $scope.page0.pageSize,
            "lendingTime": $scope.page0.lendingTime,
            "groupBy": "时间",
            "sortOrder": data.order,
            "sortName": data.sort
        }
    }

    function getParams2(data) {
        // 城市表查询条件
        $scope[`page${$scope.reportType}`].start = data.offset;
        $scope[`page${$scope.reportType}`].pageSize = data.limit;
        return $scope.page0.startTime || $scope.page0.endTime ? {
            "startTime": $scope.page0.startTime,
            "endTime": $scope.page0.endTime,
            "cityName": $scope.page0.cityName == '全国' ? '不限' : $scope.page0.cityName,
            "productCode": $scope.page0.productCode,
            "start": $scope.page0.start,
            "pageSize": $scope.page0.pageSize,
            "groupBy": "城市",
            "sortOrder": data.order,
            "sortName": data.sort
        } : {
            "cityName": $scope.page0.cityName == '全国' ? '不限' : $scope.page0.cityName,
            "productCode": $scope.page0.productCode,
            "start": $scope.page0.start,
            "pageSize": $scope.page0.pageSize,
            "lendingTime": $scope.page0.lendingTime,
            "groupBy": "城市",
            "sortOrder": data.order,
            "sortName": data.sort
        }
    }

    function getParams3(data) {
        // 城市表查询条件
        $scope[`page${$scope.reportType}`].start = data.offset;
        $scope[`page${$scope.reportType}`].pageSize = data.limit;
        return $scope.page0.startTime || $scope.page0.endTime ? {
            "startTime": $scope.page0.startTime,
            "endTime": $scope.page0.endTime,
            "productCode": $scope.page0.productCode,
            "start": $scope.page0.start,
            "pageSize": $scope.page0.pageSize,
            "sortOrder": data.order,
            "sortName": data.sort,
            "cityName": $scope.page0.cityName == '全国' ? '不限' : $scope.page0.cityName
        } : {

            "productCode": $scope.page0.productCode,
            "lendingTime": $scope.page0.lendingTime,
            "start": $scope.page0.start,
            "pageSize": $scope.page0.pageSize,
            "sortOrder": data.order,
            "sortName": data.sort,
            "cityName": $scope.page0.cityName == '全国' ? '不限' : $scope.page0.cityName
        }
    }

    function getParams4(data) {
        // 订单概览表查询条件
        $scope.page2.start = data.offset;
        $scope.page2.pageSize = data.limit;
        return $scope.page2
    }

    function getParams5(data) {
        // 城市表查询条件
        var obj = new Object()
        obj = $scope.page1.selTime ? {
            "cityCode": $scope.page1.cityCode,
            "productCode": $scope.page1.productCode,
            "startTime": $scope.page1.selTime,
            "start": data.offset,
            "pageSize": data.limit,
        } : {
            "cityCode": $scope.page1.cityCode,
            "productCode": $scope.page1.productCode,
            "timeWhere": $scope.page1.timeWhere,
            "start": data.offset,
            "pageSize": data.limit,
        }
        obj[`${data.sort}Sort`] = data.order
        return obj
    }
    //清空时间选择器
    $scope.clearDate = function (event, obj, index) {
        $(event.target).prev(".form_date").val("");
        if (obj == 'start') {
            $scope[`page${index}`].startTime = null;
        } else if (obj == 'end') {
            $scope[`page${index}`].endTime = null;
        }
    }
    $scope.clearDateSel = function(){
        $scope.page1.selTime = ''
    }
    //测试参数
    $scope.test = function () {
        console.log($scope)
    }
    //监听参数变化刷新所有视图
    function watch() {
        $scope.$watch("page0.cityName", function (newVal, old) {
            if (newVal == old) {
                return
            }
            $scope.query1();
        });
        $scope.$watch("page0.data", function (newVal, old) {
            if (newVal == old) {
                return
            }
            $scope.query1();
        });
        $scope.$watch("page0.productCode", function (newVal, old) {
            if (newVal == old) {
                return
            }
            $scope.query1();
        });

        $scope.$watch("page1.cityCode", function (newVal, old) {
            if (newVal == old) {
                return
            }
            $scope.query2();
        });
        $scope.$watch("page1.productCode", function (newVal, old) {
            if (newVal == old) {
                return
            }
            $scope.query2();
        });
        //监听订单参数刷新数据
        $scope.$watch("page2.cityCode", function (newVal, old) {
            if (newVal == old) {
                return
            }
            $scope.query3();
        });
        $scope.$watch("page2.productCode", function (newVal, old) {
            if (newVal == old) {
                return
            }
            $scope.query3();
        });

    }
    //获取城市，产品列表
    function getList() {
        $http({
            method: 'POST',
            url: '/credit/order/base/v/selectionConditions'
        }).success(function (data) {
            $scope.conditions = data.data;
            $http.post(`/credit/report/achievementStatistics/v/city`).then(function (res) {
                $scope.orderCitys = res.data.data
                $scope.page0.cityName = $scope.orderCitys[0].name
                $scope.page1.cityCode = $scope.orderCitys[0].code
                $scope.page2.cityCode = $scope.orderCitys[0].code
                $http.post('/credit/data/dict/v/search', {
                    "type": "branchCompany"
                }).then(function (res) {
                    $scope.productList = $scope.conditions.citys[0].productList;
                    $scope.productList.pop()
                    $scope.productList[0].productName = '全部'
                    $scope.branchCompanys = res.data.data
                    setTimeout(() => {
                        $scope.refreshEcharts0()
                        $scope.refreshEcharts1()
                        initTable()
                        watch()
                        setTimeout(() => {
                            selSet()
                        }, 500);
                    }, 100);
                })
            })
        })
    }
    var myChart0 = echarts.init(document.getElementById('echartsContainer0'), 'macarons');
    var myChart1 = echarts.init(document.getElementById('echartsContainer1'), 'macarons');
    //刷新时间趋势图
    $scope.refreshEcharts0 = function () {
        //  获取数据
        $http({
            method: 'POST',
            url: '/credit/report/achievementStatistics/v/achievement',
            data: $scope.page0.startTime || $scope.page0.endTime ? {
                "startTime": $scope.page0.startTime,
                "endTime": $scope.page0.endTime,
                "cityName": $scope.page0.cityName == '全国' ? '不限' : $scope.page0.cityName,
                "productCode": $scope.page0.productCode,
                "groupBy": "时间",
            } : {
                "cityName": $scope.page0.cityName == '全国' ? '不限' : $scope.page0.cityName,
                "productCode": $scope.page0.productCode,
                "lendingTime": $scope.page0.lendingTime,
                "groupBy": "时间",
            }
        }).success(function (res) {
            var time = new Array()
            var amount = new Array()
            var dataSource = res.rows
            var ymax = ''
            var title = ''
            var ystr = ''
            var unit = ''
            switch ($scope.page0.data) {
                case '放款量':
                    dataSource.forEach((val, index) => {
                        if (index > 0) {
                            time.push(val.lendingTimeStr)
                            amount.push(val.lendingAmount?val.lendingAmount:0)
                        }
                    });
                    ymax = Math.ceil(Math.max.apply(null, amount) / 1000) * 1000
                    title = '放款量'
                    ystr = "单位：万"
                    unit = "万"
                    break;
                case '单量':
                    dataSource.forEach((val, index) => {
                        if (index > 0) {
                            time.push(val.lendingTimeStr)
                            amount.push(val.orderCount?val.orderCount:0)
                        }
                    });
                    ymax = Math.max.apply(null, amount)
                    title = '单量'
                    ystr = "单量：笔"
                    unit = "笔"
                    break;
                case '创收':
                    dataSource.forEach((val, index) => {
                        if (index > 0) {
                            time.push(val.lendingTimeStr)
                            amount.push(val.income?val.income:0)
                        }
                    });
                    ymax = Math.ceil(Math.max.apply(null, amount) / 1000) * 1000
                    title = '创收'
                    ystr = "创收：万"
                    unit = "万"
                    break;
            }
            // 格式化数据    
            option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross'
                    },
                    formatter: function (params, ticket, callback) {
                        return `${params[0].axisValueLabel}<br><span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:#4da75f;"></span>${params[0].seriesName
                       }: ${params[0].value}${unit}`
                    }
                },
                title: {
                    x: 'left',
                    y: 'top'
                },
                grid: {
                    right: '10%'
                },
                toolbox: {
                    feature: {
                        dataView: {
                            show: false,
                            readOnly: false
                        },
                        restore: {
                            show: false
                        },
                        saveAsImage: {
                            show: false
                        },

                    }
                },
                legend: {
                    data: []
                },
                xAxis: [{
                    type: 'category',
                    name: '日期',
                    axisTick: {
                        alignWithLabel: true
                    },
                    data: time
                }],
                yAxis: [{
                    type: 'value',
                    name: ystr,
                    min: 0,
                    max: ymax,
                    position: 'left',
                    axisLine: {
                        lineStyle: {
                            color: '#000'
                        }
                    },
                    axisLabel: {
                        formatter: '{value}'
                    }
                }],
                dataZoom: [{
                        show: dataSource.length > 60 ? true : false
                    }
                ],
                series: [
                    {
                        name: title,
                        type: 'line',
                        smooth: true,
                        data: amount,
                        itemStyle: {
                            normal: {
                                color: '#4da75f'
                            }
                        },

                    },
                ]
            };
            myChart0.setOption(option);
        })
    }
    //刷新城市趋势图
    $scope.refreshEcharts1 = function () {
        //  获取数据
        $http({
            method: 'POST',
            url: '/credit/report/achievementStatistics/v/achievement',
            data: $scope.page0.startTime || $scope.page0.endTime ? {
                "startTime": $scope.page0.startTime,
                "endTime": $scope.page0.endTime,
                "cityName": $scope.page0.cityName == '全国' ? '不限' : $scope.page0.cityName,
                "productCode": $scope.page0.productCode,
                "groupBy": "城市",
            } : {
                "cityName": $scope.page0.cityName == '全国' ? '不限' : $scope.page0.cityName,
                "productCode": $scope.page0.productCode,
                "lendingTime": $scope.page0.lendingTime,
                "groupBy": "城市",
            }
        }).success(function (res) {
            var citys = new Array()
            var amount = new Array()
            var dataSource = res.rows
            var ymax = ''
            var title = ''
            var ystr = ''
            var unit = ''
            switch ($scope.page0.data) {
                case '放款量':
                    dataSource.forEach((val, index) => {
                        if (index > 0) {
                            citys.push(val.cityName)
                            amount.push(val.lendingAmount?val.lendingAmount:0)
                        }
                    });
                    ymax = Math.ceil(Math.max.apply(null, amount) / 1000) * 1000
                    title = '放款量'
                    ystr = "单位：万"
                    unit = "万"
                    break;
                case '单量':
                    dataSource.forEach((val, index) => {
                        if (index > 0) {
                            citys.push(val.cityName)
                            amount.push(val.orderCount?val.orderCount:0)
                        }
                    });
                    ymax = Math.max.apply(null, amount)
                    title = '单量'
                    ystr = "单量：笔"
                    unit = "笔"
                    break;
                case '创收':
                    dataSource.forEach((val, index) => {
                        if (index > 0) {
                            citys.push(val.cityName)
                            amount.push(val.income?val.income:0)
                        }
                    });
                    ymax = Math.ceil(Math.max.apply(null, amount) / 1000) * 1000
                    title = '创收'
                    ystr = "创收：万"
                    unit = "万"
                    break;
            }
            // 格式化数据    
            option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross'
                    },
                    formatter: function (params, ticket, callback) {
                        return `${params[0].axisValueLabel}<br><span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:#4da75f;"></span>${params[0].seriesName
                       }: ${params[0].value}${unit}`
                    }
                },
                title: {
                    // text: '全国城市放款量'
                },
                grid: {
                    right: '10%'
                },
                toolbox: {
                    feature: {
                        dataView: {
                            show: false,
                            readOnly: false
                        },
                        restore: {
                            show: false
                        },
                        saveAsImage: {
                            show: false
                        }
                    }
                },
                legend: {
                    data: []
                },
                xAxis: [{
                    type: 'category',
                    name: '城市',
                    axisTick: {
                        alignWithLabel: true
                    },
                    data: citys
                }],
                yAxis: [{
                    type: 'value',
                    name: ystr,
                    min: 0,
                    max: ymax,
                    position: 'left',
                    axisLine: {
                        lineStyle: {
                            color: '#000'
                        }
                    },
                    axisLabel: {
                        formatter: '{value}'
                    }
                }],
                dataZoom: [{
                    show: false
                }],
                series: [
                    {
                        name: title,
                        type: 'line',
                        smooth: true,
                        data: amount,
                        itemStyle: {
                            normal: {
                                color: '#4da75f'
                            }
                        },

                    },
                ]
            };
            myChart1.setOption(option);
        })
    }
    
    function operateFormatter(value, row, index) {  
    	html = '<a class="lookOrder" href="javascript:void(0)">查看详情</a>&nbsp;&nbsp;';
    	return html;
    }
    
    window.operateEvents = {
		 'click .lookOrder': function (e, value, row, index) {
			 var startTime="";
			 var endTime="";
			 if(row.lendingTimeStr.indexOf("总计")!=-1){
				 startTime = $scope.page0.startTime
				 endTime = $scope.page0.endTime
			 }else if(row.lendingTimeStr.length>=10){//某天详情
				 startTime = row.lendingTimeStr;
				 endTime = row.lendingTimeStr;
			 }else{//某月详情
				 startTime = row.lendingTimeStr+"-01";
				 var year = String(row.lendingTimeStr).substring(0,4);
				 var month = String(row.lendingTimeStr).substring(5,7);
				 if(month.indexOf("0")!=-1){
					 month = String(row.lendingTimeStr).substring(6,7);
				 }
				 var  day = new Date(year,month,0);
				 endTime = row.lendingTimeStr+"-"+day.getDate();//某月最后一天
			 }
	          $state.go("orderDetailList",{"cityName":$scope.page0.cityName,
	        	  'productCode':$scope.page0.productCode,
	        	  'startTime':startTime,
	        	  'endTime':endTime,
	        	  'lendingTime':$scope.page0.lendingTime//本月，本年等
	        	  });
	     }
    }
    
    function initTable() {
        //时间业绩报表
        $scope.achievement = {
            options: {
                method: "post",
                url: "/credit/report/achievementStatistics/v/achievement",
                queryParams: getParams1,
                sidePagination: 'server',
                undefinedText: "-",
                cache: false,
                striped: true,
                pagination: true,
                sortName: '',
                sortOrder: '',
                pageNumber: 1,
                pageSize: 10,
                pageList: [10, 20, 'all'],
                showColumns: true,
                // exportDataType:'all',
                exportTypes: ['excel'],
                showRefresh: false,
                onLoadSuccess: function (res) {
                    if (res.total == 0) {
                        $('#achievement .fixed-table-pagination').hide()
                    } else {
                        $('#achievement .fixed-table-pagination').show()
                    }
                },
                showRefresh: false,
                columns: [{
                    title: '时间',
                    field: 'lendingTimeStr',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '放款量（万）',
                    field: 'lendingAmount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '单量（笔）',
                    field: 'orderCount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '返佣（万）',
                    field: 'rebateMoney',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '关外手续费（万）',
                    field: 'customsPoundage',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '其他金额（万）',
                    field: 'otherPoundage',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '利息（万）',
                    field: 'interest',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '罚息（万）',
                    field: 'fine',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '服务费（万）',
                    field: 'serviceCharge',
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
                    title: '操作',
                    field: 'operate',
                    align: 'center',
                    valign: 'bottom',
                    events: operateEvents,
                    formatter: operateFormatter,
                    visible:true
                }]

            }
        };
        //城市业绩表
        $scope.cityachievement = {
            options: {
                method: "post",
                url: "/credit/report/achievementStatistics/v/achievement",
                queryParams: getParams2,
                sidePagination: 'server',
                undefinedText: "-",
                cache: false,
                striped: true,
                sortName: 'lendingAmount',
                sortOrder: 'desc',
                pagination: true,
                pageNumber: 1,
                pageSize: 20,
                pageList: [20, "all"],
                showColumns: true,
                //exportDataType:'all',
                exportTypes: ['excel'],
                showRefresh: false,
                onLoadSuccess: function (res) {
                    if (res.total == 0) {
                        $('#cityachievement .fixed-table-pagination').hide()
                    } else {
                        $('#cityachievement .fixed-table-pagination').show()
                    }
                },
                columns: [{
                    title: '序号',
                    field: 'cityName',
                    align: 'center',
                    valign: 'bottom',
                    formatter: function (row, value, index, field) {
                        return row == "总计" ? '' : index
                    }
                },{
                    title: '城市',
                    field: 'cityName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: `本月累计放款（万）`,
                    field: 'lendingAmount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: `本月累计单量（笔）`,
                    field: 'orderCount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: `本月累计返佣（万）`,
                    field: 'rebateMoney',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: `本月累计关外手续费（万）`,
                    field: 'customsPoundage',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: `本月累计其他金额（万）`,
                    field: 'otherPoundage',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: `本月累计利息（万）`,
                    field: 'interest',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: `本月累计罚息（万）`,
                    field: 'fine',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: `本月累计服务费（万）`,
                    field: 'serviceCharge',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: `本月累计创收（万）`,
                    field: 'income',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }]
            }
        };
        // 城市完成率
        $scope.achievementStatistics = {
            options: {
                method: "post",
                url: "/credit/report/achievementStatistics/v/aim",
                queryParams: getParams3,
                sidePagination: 'server',
                undefinedText: "-",
                sortName: 'lendingAmount',
                sortOrder: 'desc',
                cache: false,
                striped: true,
                pagination: true,
                pageNumber: 1,
                pageSize: 20,
                pageList: [20, "all"],
                showColumns: true,
                //exportDataType:'all',
                exportTypes: ['excel'],
                showRefresh: false,
                onLoadSuccess: function (res) {
                    if (res.total == 0) {
                        $('#achievementStatistics .fixed-table-pagination').hide()
                    } else {
                        $('#achievementStatistics .fixed-table-pagination').show()
                    }
                },
                columns: [{
                    title: '序号',
                    field: 'cityName',
                    align: 'center',
                    valign: 'bottom',
                    formatter: function (row, value, index, field) {
                        return row == "总计" ? '' : index
                    }
                }, {
                    title: '城市',
                    field: 'cityName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: `${$scope.page0.lendingTime}订单量`,
                    field: 'orderCount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(row, value, index, field){
                        return row?row:"-"
                    }
                }, {
                    title: `${$scope.page0.lendingTime}目标订单量`,
                    field: 'orderAim',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(row, value, index, field){
                        return row?row:"-"
                    }
                }, {
                    title: '完成率（%）',
                    field: 'orderAimRate',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(row, value, index, field){
                        return row?row:"-"
                    }
                }, {
                    title: `${$scope.page0.lendingTime}放款量（万）`,
                    field: 'lendingAmount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(row, value, index, field){
                        return row?row:"-"
                    }
                }, {
                    title: `${$scope.page0.lendingTime}放款量目标（万）`,
                    field: 'loanAim',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(row, value, index, field){
                        return row?row:"-"
                    }
                }, {
                    title: '完成率（%）',
                    field: 'loanAimRate',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(row, value, index, field){
                        return row?row:"-"
                    }
                }, {
                    title: `${$scope.page0.lendingTime}创收（万）`,
                    field: 'income',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(row, value, index, field){
                        return row?row:"-"
                    }
                }, {
                    title: `${$scope.page0.lendingTime}创收目标（万）`,
                    field: 'incomeAim',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(row, value, index, field){
                        return row?row:"-"
                    }
                }, {
                    title: '完成率（%）',
                    field: 'incomeAimRate',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(row, value, index, field){
                        return row?row:"-"
                    }
                }]
            }
        };
        $scope.personalView = {
            options: {
                method: "post",
                url: "/credit/report/statistics/v/selectPersonalView",
                queryParams: getParams5,
                sidePagination: 'server',
                undefinedText: "-",
                sortName: 'income',
                sortOrder: 'desc',
                cache: false,
                striped: true,
                pagination: true,
                pageNumber: 1,
                pageSize: 10,
                pageList: [10, 20, "all"],
                showColumns: true,
                //exportDataType:'all',
                exportTypes: ['excel'],
                columns: [{
                        title: '序号',
                        field: 'cityName',
                        align: 'center',
                        valign: 'bottom',
                        formatter: function (row, value, index, field) {
                            return index + 1
                        }
                    }, {
                        title: '分公司',
                        field: 'branchCompany',
                        align: 'center',
                        valign: 'bottom'
                    }, {
                        title: '姓名',
                        field: 'channelManagerName',
                        align: 'center',
                        valign: 'bottom'
                    }, {
                        title: '业务单量（笔）',
                        field: 'orderCount',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true
                    },{
                        title: '目标单量（笔）',
                        field: 'orderAim',
                        align: 'center',
                        valign: 'bottom'
                    },{
                        title: '完成率',
                        field: 'orderAimPercentage',
                        align: 'center',
                        valign: 'bottom',
                        formatter: function (row, value, index, field) {
                            return `<span style="color:#00f">${row!='--'?`${row}%`:'-'}</span>`
                        }
                    },
                     {
                        title: '放款量（万）',
                        field: 'lendingAmount',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true
                    }, {
                        title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="个人设定的本月目标放款量">目标放款量（万） <span class="help"></span></span>',
                        field: 'loanAim',
                        align: 'center',
                        valign: 'bottom',
                        formatter: function (row, value, index, field) {
                            return `<span style="color:#00f">${row?row:'-'}</span>`
                        }
                    },
                    {
                        title: '目标完成率',
                        field: 'loanAimPercentage',
                        align: 'center',
                        valign: 'bottom',
                        formatter: function (row, value, index, field) {
                            return `<span style="color:#00f">${row!='--'?`${row}%`:'-'}</span>`
                        }
                    },
                    {
                        title: '罚息（万）',
                        field: 'fine',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true
                    },
                    {
                        title: '利息（万）',
                        field: 'interest',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true
                    },
                    {
                        title: '服务费（万）',
                        field: 'serviceCharge',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true
                    },
                    {
                        title: '创收（万）',
                        field: 'income',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true,
                        formatter: function (row, value, index, field) {
                            return row?row:0
                        }
                    },
                    {
                        title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="个人设定的本月目标创收值">目标创收（万） <span class="help"></span></span>',
                        field: 'incomeAim',
                        align: 'center',
                        valign: 'bottom',
                        formatter: function (row, value, index, field) {
                            return `<span style="color:#00f">${row?row:'-'}</span>`
                        }
                    },
                    {
                        title: '目标完成率',
                        field: 'incomeAimPercentage',
                        align: 'center',
                        valign: 'bottom',
                        formatter: function (row, value, index, field) {
                            return `<span style="color:#00f">${row!='--'?`${row}%`:'-'}</span>`
                        }
                    }
                ]

            }
        }
        $scope.receivable = {
            options: {
                method: "post",
                url: "/credit/report/receivable/v/query",
                queryParams: getParams4,
                sidePagination: 'server',
                undefinedText: "-",
                cache: false,
                striped: true,
                pagination: true,
                pageNumber: 1,
                pageSize: 20,
                pageList: [20, "all"],
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
                        title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="提前回款或者正常回款的订单数">正常回款订单（笔） <span class="help"></span></span>',
                        field: 'receivableCount',
                        align: 'center',
                        valign: 'bottom'
                    }, {
                        title: '正常回款额（万）',
                        field: 'receivableAmount',
                        align: 'center',
                        valign: 'bottom'
                    }, {
                        title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="超出计划回款日期回款的订单总笔数，包括部分逾期和全部逾期.">逾期回款订单（笔） <span class="help"></span></span>',
                        field: 'overdueCount',
                        align: 'center',
                        valign: 'bottom'
                    }, {
                        title: '逾期回款额（万）',
                        field: 'overdueAmount',
                        align: 'center',
                        valign: 'bottom'
                    }, {
                        title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="未放款的订单总笔数.">未出款订单（笔） <span class="help"></span></span>',
                        field: 'notLendingCount',
                        align: 'center',
                        valign: 'bottom'
                    }, {
                        title: '未出款额（万）',
                        field: 'notLendingAmount',
                        align: 'center',
                        valign: 'bottom'
                    },
                    {
                        title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="已经放款的订单总笔数.">已放款订单（笔） <span class="help"></span></span>',
                        field: 'lendingCount',
                        align: 'center',
                        valign: 'bottom'
                    },
                    {
                        title: '已放款额（万）',
                        field: 'lendingAmount',
                        align: 'center',
                        valign: 'bottom'
                    },
                    {
                        title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="未超过计划回款日期的订单总数.">正常未回款订单（笔） <span class="help"></span></span>',
                        field: 'notReceivableCount',
                        align: 'center',
                        valign: 'bottom'
                    },
                    {
                        title: '正常未回款额（万）',
                        field: 'notReceivableAmount',
                        align: 'center',
                        valign: 'bottom'
                    },
                    {
                        title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="超出计划回款日期未回款的订单总笔数，包括部分逾期和全部逾期.">逾期未回款订单（笔） <span class="help"></span></span>',
                        field: 'notOverdueCount',
                        align: 'center',
                        valign: 'bottom'
                    },
                    {
                        title: '逾期未回款额（万）',
                        field: 'notOverdueAmount',
                        align: 'center',
                        valign: 'bottom'
                    },
                    {
                        title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="正常未回款订单和逾期未回款订单总笔数.">总未回款订单（笔） <span class="help"></span></span>',
                        field: 'notReceivableSum',
                        align: 'center',
                        valign: 'bottom'
                    },
                    {
                        title: '总未回款额（万）',
                        field: 'notReceivableAmountSum',
                        align: 'center',
                        valign: 'bottom'
                    }
                ]

            }
        }
        $scope.overDate = {
            options: {
                method: "post",
                url: "/credit/report/receivable/v/query",
                queryParams: getParams4,
                sidePagination: 'server',
                undefinedText: "-",
                cache: false,
                striped: true,
                pagination: true,
                pageNumber: 1,
                pageSize: 20,
                pageList: [20, "all"],
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
                        title: '未回款金额（万）',
                        field: 'notReceivableAmountSum',
                        align: 'center',
                        valign: 'bottom'
                    }, {
                        title: '逾期未回款额（万）',
                        field: 'notOverdueAmount',
                        align: 'center',
                        valign: 'bottom'
                    }, {
                        title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="计算公式：逾期未回款金额/未回款金额*100%.">逾期未回款额占比 <span class="help"></span></span>',
                        field: 'overdueAmountRatio',
                        align: 'center',
                        valign: 'bottom',
                        formatter: function (row, value, index, field) {
                            return `${row}%`
                        }
                    }, {
                        title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="计算公式：逾期未回款金额/未回款总金额*100%.">逾期占总未回款比 <span class="help"></span></span>',
                        field: 'overdueAmountRatioByCount',
                        align: 'center',
                        valign: 'bottom',
                        formatter: function (row, value, index, field) {
                            return `${row}%`
                        }
                    }, {
                        title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="计算公式：逾期未回款金额/逾期未回款总金额*100%.">逾期未回款额占总逾期未回款额比 <span class="help"></span></span>',
                        field: 'overdueAmountRatioBySum',
                        align: 'center',
                        valign: 'bottom',
                        formatter: function (row, value, index, field) {
                            return `${row}%`
                        }
                    }, {
                        title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="正常未回单订单笔数和逾期未回款订单笔数之和.">未回款数量（笔） <span class="help"></span></span>',
                        field: 'notReceivableSum',
                        align: 'center',
                        valign: 'bottom'
                    },
                    {
                        title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="超出计划回款日期的订单笔数.">逾期数量（笔） <span class="help"></span></span>',
                        field: 'overdueNumber',
                        align: 'center',
                        valign: 'bottom'
                    },
                    {
                        title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="计算公式：订单逾期总天数/订单逾期总订单量.">平均逾期天数（天） <span class="help"></span></span>',
                        field: 'overdueDay',
                        align: 'center',
                        valign: 'bottom'
                    },
                    {
                        title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="近期逾期天数最多的订单天数的值.">逾期最大天数（天） <span class="help"></span></span>',
                        field: 'overdueBigDay',
                        align: 'center',
                        valign: 'bottom'
                    }
                ]

            }
        }
    }

    // 刷新表格
    $scope.query1 = function (mes) {
        if (mes == "clear") {
            $scope.page0.startTime = ''
            $scope.page0.endTime = ''
        }
        // else if(mes == 'clearTime'){
        //     $scope.page0.lendingTime = ''
        // }
        //刷新时间表格
        $("#achievementTable").bootstrapTable('refresh', {
            url: '/credit/report/achievementStatistics/v/achievement',
            pageNumber: 1,
            pageSize: 10
        });
        //刷新时间趋势图
        $scope.refreshEcharts0()
        //刷新城市趋势图
        if ($scope.page0.cityName == '全国') {
            //具体城市不刷新和显示趋势图
            $scope.refreshEcharts1()
        }
        //刷新城市表
        if ($scope.page0.startTime) {
            //有开始时间
            if ($scope.page0.endTime) {
                //如果有结束时间
                if ($scope.page0.startTime.substring(0, 7) == $scope.page0.endTime.substring(0, 7)) {
                    $("#cityachievementTable").bootstrapTable('refreshOptions', {
                        url: '/credit/report/achievementStatistics/v/achievement',
                        pageNumber: 1,
                        columns: [{
                            title: '序号',
                            field: 'cityName',
                            align: 'center',
                            valign: 'bottom',
                            formatter: function (row, value, index, field) {
                                return row == "总计" ? '' : index
                            }
                        },{
                            title: '城市',
                            field: 'cityName',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: `累计放款（万）`,
                            field: 'lendingAmount',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计单量（笔）`,
                            field: 'orderCount',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计返佣（万）`,
                            field: 'rebateMoney',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计关外手续费（万）`,
                            field: 'customsPoundage',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计其他金额（万）`,
                            field: 'otherPoundage',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计利息（万）`,
                            field: 'interest',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计罚息（万）`,
                            field: 'fine',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计服务费（万）`,
                            field: 'serviceCharge',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计创收（万）`,
                            field: 'income',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }]
                    });
                } else {
                    $("#cityachievementTable").bootstrapTable('refreshOptions', {
                        url: '/credit/report/achievementStatistics/v/achievement',
                        pageNumber: 1,
                        columns: [{
                            title: '序号',
                            field: 'cityName',
                            align: 'center',
                            valign: 'bottom',
                            formatter: function (row, value, index, field) {
                                return row == "总计" ? '' : index
                            }
                        },{
                            title: '城市',
                            field: 'cityName',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: `累计放款（万）`,
                            field: 'lendingAmount',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计单量（笔）`,
                            field: 'orderCount',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计返佣（万）`,
                            field: 'rebateMoney',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计关外手续费（万）`,
                            field: 'customsPoundage',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计其他金额（万）`,
                            field: 'otherPoundage',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计利息（万）`,
                            field: 'interest',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计罚息（万）`,
                            field: 'fine',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计服务费（万）`,
                            field: 'serviceCharge',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计创收（万）`,
                            field: 'income',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }]
                    });
                }
            } else {
                if ($scope.page0.startTime.substring(0, 7) == formatDate(new Date()).substring(0, 7)) {
                    $("#cityachievementTable").bootstrapTable('refreshOptions', {
                        url: '/credit/report/achievementStatistics/v/achievement',
                        pageNumber: 1,
                        columns: [{
                            title: '序号',
                            field: 'cityName',
                            align: 'center',
                            valign: 'bottom',
                            formatter: function (row, value, index, field) {
                                return row == "总计" ? '' : index
                            }
                        },{
                            title: '城市',
                            field: 'cityName',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: `累计放款（万）`,
                            field: 'lendingAmount',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计单量（笔）`,
                            field: 'orderCount',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计返佣（万）`,
                            field: 'rebateMoney',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计关外手续费（万）`,
                            field: 'customsPoundage',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计其他金额（万）`,
                            field: 'otherPoundage',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计利息（万）`,
                            field: 'interest',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计罚息（万）`,
                            field: 'fine',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计服务费（万）`,
                            field: 'serviceCharge',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计创收（万）`,
                            field: 'income',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }]
                    });
                } else {
                    $("#cityachievementTable").bootstrapTable('refreshOptions', {
                        url: '/credit/report/achievementStatistics/v/achievement',
                        pageNumber: 1,
                        columns: [{
                            title: '序号',
                            field: 'cityName',
                            align: 'center',
                            valign: 'bottom',
                            formatter: function (row, value, index, field) {
                                return row == "总计" ? '' : index
                            }
                        },{
                            title: '城市',
                            field: 'cityName',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: `累计放款（万）`,
                            field: 'lendingAmount',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计单量（笔）`,
                            field: 'orderCount',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计返佣（万）`,
                            field: 'rebateMoney',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计关外手续费（万）`,
                            field: 'customsPoundage',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计其他金额（万）`,
                            field: 'otherPoundage',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计利息（万）`,
                            field: 'interest',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计罚息（万）`,
                            field: 'fine',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计服务费（万）`,
                            field: 'serviceCharge',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `累计创收（万）`,
                            field: 'income',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }]
                    });
                }
            }
        } else {
            //没有开始时间
            if ($scope.page0.lendingTime == '上月') {
                var last = `${new Date().getMonth()}月`
                var llast = `${new Date().getMonth()-1}月`
                console.log(last, llast)
                $("#cityachievementTable").bootstrapTable('refreshOptions', {
                    url: '/credit/report/achievementStatistics/v/achievement',
                    pageNumber: 1,
                    columns: [{
                        title: '序号',
                        field: 'cityName',
                        align: 'center',
                        valign: 'bottom',
                        formatter: function (row, value, index, field) {
                            return row == "总计" ? '' : index
                        }
                    },{
                            title: '城市',
                            field: 'cityName',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: `${last}放款量(万)`,
                            field: 'lendingAmount',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `${llast}放款量(万)`,
                            field: 'lendingAmountLast',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `环比`,
                            field: 'lendingAmountContrast',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `去年同期${last}`,
                            field: 'lendingAmountLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                            
                        }, {
                            title: `同比`,
                            field: 'lendingAmountContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `${last}单量(笔)`,
                            field: 'orderCount',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `${llast}单量(笔)`,
                            field: 'orderCountLast',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `环比`,
                            field: 'orderCountContrast',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true,
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `去年同期${last}`,
                            field: 'orderCountLastY',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: `同比`,
                            field: 'orderCountContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `${last}返佣（万）`,
                            field: 'rebateMoney',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `${llast}返佣（万）`,
                            field: 'rebateMoneyLast',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `环比`,
                            field: 'rebateMoneyContrast',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `去年同期${last}`,
                            field: 'rebateMoneyLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `同比`,
                            field: 'rebateMoneyContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `${last}关外手续费（万）`,
                            field: 'customsPoundage',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `${llast}关外手续费（万）`,
                            field: 'customsPoundageLast',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `环比`,
                            field: 'customsPoundageContrast',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `去年同期${last}`,
                            field: 'customsPoundageLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `同比`,
                            field: 'customsPoundageContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `${last}其他金额（万）`,
                            field: 'otherPoundage',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `${llast}其他金额（万）`,
                            field: 'otherPoundageLast',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `环比`,
                            field: 'otherPoundageContrast',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `去年同期${last}`,
                            field: 'otherPoundageLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `同比`,
                            field: 'otherPoundageContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `${last}利息（万）`,
                            field: 'interest',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `${llast}利息（万）`,
                            field: 'interestLast',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `环比`,
                            field: 'interestContrast',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `去年同期${last}`,
                            field: 'interestLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `同比`,
                            field: 'interestContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `${last}罚息（万）`,
                            field: 'fine',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `${llast}罚息（万）`,
                            field: 'fineLast',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `环比`,
                            field: 'fineContrast',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `去年同期${last}`,
                            field: 'fineLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `同比`,
                            field: 'fineContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `${last}服务费（万）`,
                            field: 'serviceCharge',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `${llast}务费（万）`,
                            field: 'serviceChargeLast',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `环比`,
                            field: 'serviceChargeContrast',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `去年同期${last}`,
                            field: 'serviceChargeLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `同比`,
                            field: 'serviceChargeContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `${last}总创收（万）`,
                            field: 'income',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `${llast}总创收（万）`,
                            field: 'incomeLast',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `环比`,
                            field: 'incomeContrast',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `去年同期${last}`,
                            field: 'incomeLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `同比`,
                            field: 'incomeContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }

                    ]
                });
            } else if ($scope.page0.lendingTime == '本年') {
                $("#cityachievementTable").bootstrapTable('refreshOptions', {
                    url: '/credit/report/achievementStatistics/v/achievement',
                    pageNumber: 1,
                    columns: [{
                        title: '序号',
                        field: 'cityName',
                        align: 'center',
                        valign: 'bottom',
                        formatter: function (row, value, index, field) {
                            return row == "总计" ? '' : index
                        }
                    },{
                            title: '城市',
                            field: 'cityName',
                            align: 'center',
                            valign: 'bottom'
                        }, {
                            title: `本年放款量(万)`,
                            field: 'lendingAmount',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `去年放款量(万)`,
                            field: 'lendingAmountLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `同比`,
                            field: 'lendingAmountContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `本年单量(笔)`,
                            field: 'orderCount',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `去年单量(笔)`,
                            field: 'orderCountLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `同比`,
                            field: 'orderCountContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `本年返佣（万）`,
                            field: 'rebateMoney',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `去年返佣（万）`,
                            field: 'rebateMoneyLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `同比`,
                            field: 'rebateMoneyContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `本年关外手续费（万）`,
                            field: 'customsPoundage',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `去年关外手续费（万）`,
                            field: 'customsPoundageLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `同比`,
                            field: 'customsPoundageContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `本年其他金额（万）`,
                            field: 'otherPoundage',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `去年其他金额（万）`,
                            field: 'otherPoundageLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `同比`,
                            field: 'otherPoundageContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `本年利息（万）`,
                            field: 'interest',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `去年利息（万）`,
                            field: 'interestLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `同比`,
                            field: 'interestContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `本年罚息（万）`,
                            field: 'fine',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `去年罚息（万）`,
                            field: 'fineLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `同比`,
                            field: 'fineContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `本年服务费（万）`,
                            field: 'serviceCharge',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `去年服务费（万）`,
                            field: 'serviceChargeLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `同比`,
                            field: 'serviceChargeContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }, {
                            title: `本年总创收（万）`,
                            field: 'income',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `去年总创收（万）`,
                            field: 'incomeLastY',
                            align: 'center',
                            valign: 'bottom',
                            sortable: true
                        }, {
                            title: `同比`,
                            field: 'incomeContrastY',
                            align: 'center',
                            valign: 'bottom',
                            formatter:function(row, value, index, field){
                                return row?row:"-"
                            }
                        }

                    ]
                });
            } else {
                $("#cityachievementTable").bootstrapTable('refreshOptions', {
                    url: '/credit/report/achievementStatistics/v/achievement',
                    pageNumber: 1,
                    columns: [{
                        title: '序号',
                        field: 'cityName',
                        align: 'center',
                        valign: 'bottom',
                        formatter: function (row, value, index, field) {
                            return row == "总计" ? '' : index
                        }
                    },{
                        title: '城市',
                        field: 'cityName',
                        align: 'center',
                        valign: 'bottom'
                    }, {
                        title: `${$scope.page0.lendingTime}累计放款（万）`,
                        field: 'lendingAmount',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true
                    }, {
                        title: `${$scope.page0.lendingTime}累计单量（笔）`,
                        field: 'orderCount',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true
                    }, {
                        title: `${$scope.page0.lendingTime}累计返佣（万）`,
                        field: 'rebateMoney',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true
                    }, {
                        title: `${$scope.page0.lendingTime}累计关外手续费（万）`,
                        field: 'customsPoundage',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true
                    }, {
                        title: `${$scope.page0.lendingTime}累计其他金额（万）`,
                        field: 'otherPoundage',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true
                    }, {
                        title: `${$scope.page0.lendingTime}累计利息（万）`,
                        field: 'interest',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true
                    }, {
                        title: `${$scope.page0.lendingTime}累计罚息（万）`,
                        field: 'fine',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true
                    }, {
                        title: `${$scope.page0.lendingTime}累计服务费（万）`,
                        field: 'serviceCharge',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true
                    }, {
                        title: `${$scope.page0.lendingTime}累计创收（万）`,
                        field: 'income',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true
                    }]
                });
            }
        }

        //刷新完成率
        if ($scope.page0.startTime || $scope.page0.endTime) {
            $("#achievementStatisticsTable").bootstrapTable('refreshOptions', {
                url: '/credit/report/achievementStatistics/v/aim',
                pageNumber: 1,
                columns: [{
                    title: '序号',
                    field: 'cityName',
                    align: 'center',
                    valign: 'bottom',
                    formatter: function (row, value, index, field) {
                        return row == "总计" ? '' : index
                    }
                }, {
                    title: '城市',
                    field: 'cityName',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: `订单量`,
                    field: 'orderCount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: `目标订单量`,
                    field: 'orderAim',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '完成率（%）',
                    field: 'orderAimRate',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(row, value, index, field){
                        return row?row:"-"
                    }
                }, {
                    title: `放款量（万）`,
                    field: 'lendingAmount',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: `放款量目标（万）`,
                    field: 'loanAim',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '完成率（%）',
                    field: 'loanAimRate',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(row, value, index, field){
                        return row?row:"-"
                    }
                }, {
                    title: `创收（万）`,
                    field: 'income',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: `创收目标（万）`,
                    field: 'incomeAim',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true
                }, {
                    title: '完成率（%）',
                    field: 'incomeAimRate',
                    align: 'center',
                    valign: 'bottom',
                    sortable: true,
                    formatter:function(row, value, index, field){
                        return row?row:"-"
                    }
                }]
            });
        } else {
            console.log($scope.page0.lendingTime)
            setTimeout(() => {
                $("#achievementStatisticsTable").bootstrapTable('refreshOptions', {
                    url: '/credit/report/achievementStatistics/v/aim',
                    pageNumber: 1,
                    columns: [{
                        title: '序号',
                        field: 'cityName',
                        align: 'center',
                        valign: 'bottom',
                        formatter: function (row, value, index, field) {
                            return row == "总计" ? '' : index
                        }
                    }, {
                        title: '城市',
                        field: 'cityName',
                        align: 'center',
                        valign: 'bottom'
                    }, {
                        title: `${$scope.page0.lendingTime}订单量`,
                        field: 'orderCount',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true,
                        formatter:function(row, value, index, field){
                            return row?row:"-"
                        }
                    }, {
                        title: `${$scope.page0.lendingTime}目标订单量`,
                        field: 'orderAim',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true,
                        formatter:function(row, value, index, field){
                            return row?row:"-"
                        }
                    }, {
                        title: '完成率（%）',
                        field: 'orderAimRate',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true,
                        formatter:function(row, value, index, field){
                            return row?row:"-"
                        }
                    }, {
                        title: `${$scope.page0.lendingTime}放款量（万）`,
                        field: 'lendingAmount',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true,
                        formatter:function(row, value, index, field){
                            return row?row:"-"
                        }
                    }, {
                        title: `${$scope.page0.lendingTime}放款量目标（万）`,
                        field: 'loanAim',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true,
                        formatter:function(row, value, index, field){
                            return row?row:"-"
                        }
                    }, {
                        title: '完成率（%）',
                        field: 'loanAimRate',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true,
                        formatter:function(row, value, index, field){
                            return row?row:"-"
                        }
                    }, {
                        title: `${$scope.page0.lendingTime}创收（万）`,
                        field: 'income',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true,
                        formatter:function(row, value, index, field){
                            return row?row:"-"
                        }
                    }, {
                        title: `${$scope.page0.lendingTime}创收目标（万）`,
                        field: 'incomeAim',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true,
                        formatter:function(row, value, index, field){
                            return row?row:"-"
                        }
                    }, {
                        title: '完成率（%）',
                        field: 'incomeAimRate',
                        align: 'center',
                        valign: 'bottom',
                        sortable: true,
                        formatter:function(row, value, index, field){
                            return row?row:"-"
                        }
                    }]
                });
            }, 10);
        }

    }
    $scope.query2 = function (mes) {
        if (mes) {
            $scope.page1.selTime = ''
        }
        //刷新表格
        $("#personalViewTable").bootstrapTable('refresh', {
            url: '/credit/report/statistics/v/selectPersonalView',
            pageNumber: 1,
            pageSize: 10
        });

    }
    $scope.query3 = function () {
        //刷新订单概览表格
        $("#receivableTable").bootstrapTable('refresh', {
            url: '/credit/report/receivable/v/query',
            pageNumber: 1,
            pageSize: 15
        });
        $("#overDateTable").bootstrapTable('refresh', {
            url: '/credit/report/receivable/v/query',
            pageNumber: 1,
            pageSize: 15
        });
    }


    function formatDate(date) {
        var d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2) month = '0' + month;
        if (day.length < 2) day = '0' + day;

        return [year, month, day].join('-');
    }
    // 特殊 dom 处理
    function selSet(){
        $(".tooltip-toggle").tooltip({
            html: true
        });
        $('.selectcity1')[0].removeChild($('.selectcity1')[0].firstChild)
        $('.selectcity2')[0].removeChild($('.selectcity2')[0].firstChild)
        for (let i = 0; i < 1; i++) {
            var tableCont = $('.fixed-table-body')[i]

            function scrollHandle(e) {
                var scrollTop = this.scrollTop;
                $('.fixed-table-body thead')[i].style.transform = 'translateY(' + scrollTop + 'px)';
            }
            // console.log(tableCont)
            tableCont.addEventListener('scroll', scrollHandle)
        }
        $('#personalViewTable thead tr th:eq(5)').css({
            color: "#00f"
        })
        $('#personalViewTable thead tr th:eq(6)').css({
            color: "#00f"
        })
        $('#personalViewTable thead tr th:eq(11)').css({
            color: "#00f"
        })
        $('#personalViewTable thead tr th:eq(12)').css({
            color: "#00f"
        })
    }
});