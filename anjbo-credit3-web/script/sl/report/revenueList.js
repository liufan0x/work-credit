angular.module("anjboApp", ['bsTable']).controller("revenueCtrl", function ($scope, $compile, $http, $state, box, process, parent) {
    var wrap = $('#tableWrap')
    $scope.page = new Object()
    $scope.page.cityCode = ''
    $scope.page.effective = ''
    $scope.showModel = false
    $scope.delete = new Function()
    function yulan() {
        $http({
            method: 'get',
            url: './script/sl/report/x.xlsx',
            responseType: 'arraybuffer'
        }).then(function (data) {
            var wb = XLSX.read(data.data, {
                type: "array"
            });
            var d = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]);
            $scope.data = d;

        }, function (err) {
            console.log(err);
        });
    }
    
    getList()

    function getData(callBack) {
        $http.post('/credit/report/statistics/v/createIncome', {
            "cityCode": $scope.page.cityCode,
            "effective": $scope.page.effective,
            "start": 0,
            "pageSize": 20

        }).then(function (res) {
            var rows = new Array()
            var data = res.data.rows
            var merge = new Array()
            var flag = 0
            data.forEach((val, index) => {
                rows.push({
                    cityName: val.name,
                    deptName: val.deptName,
                    count: val.count,
                    status: val.fileUrl ? '已上传' : '未上传',
                    operrate: "bla",
                    url: val.fileUrl,
                    cityCode: val.cityCode
                })
            })
            callBack(rows, merge)
        })

    }

    function init(rows, merge) {
        $('#downloadTable').bootstrapTable({
            columns: [{
                    field: 'cityName',
                    title: '城市',
                    valign: 'middle'
                }, {
                    field: 'deptName',
                    title: '部门'
                },
                {
                    field: 'count',
                    title: '人数'
                },
                {
                    field: 'status',
                    title: '报表状态'
                },
                {
                    title: '操作',
                    field: 'operate',
                    events: operateEvents,
                    formatter: operateFormatter
                }
            ],
            data: rows
        });

        // merge.forEach((val,index)=>{
        // $('#downloadTable').bootstrapTable('mergeCells', {index: val.index, field: 'cityName',rowspan: val.count});
        // })
        // $('#downloadTable').fixedHeaderTable('show');
    }

    function refresh() {
        wrap.empty()
        wrap.append(`<table   id="downloadTable" data-show-export="false" style="min-width:1000px;"></table>`);
        getData(init)
    }

    function operateFormatter(row, value, index, field) {
        var html = '';
        if (!value.count) {
            return html
        }
        if (value.status == '已上传') {
            html += `
            <a class="delete" href="javascript:void(0)">删除</a>
            <a href="${value.url}">下载</a>`;
            return html;
        } else {
            html += `<input id="upload${index}" style="display:inline-block;width:200px"  type="file" data-options="prompt:'选择文件'" />
            <a class="upload" ">上传</a>
            <a class="download" href="/credit/report/statistics/v/downloadIncome?cityCode=${value.cityCode}">下载</a>`;
            return html;
        }

    }
    $scope.query = function () {
        refresh()
    }
    window.operateEvents = {
        'click .detail': function (e, row, value, index) {
            alert("预览")
        },
        'click .delete': function (e, row, value, index) {

            $scope.$apply(function () {
                $scope.showModel = true
            })
            $scope.delete = function () {
                $http({
                    method: "post",
                    url: '/credit/report/statistics/v/deleteIncome',
                    data: {
                        'cityCode': value.cityCode
                    }
                }).then(function (response) {
                    if (response.status == 200) {
                        $scope.showModel = false
                        refresh()
                    } else {
                        alert("文件删除失败！！！");
                    }
                });
            }
            // refresh()
        },
        'click .download': function (e, row, value, index) {},
        'click .upload': function (e, row, value, index) {
            var formData = new FormData();
            var file = $(`#upload${index}`)[0].files[0]
            if (!file) {
                alert('请上传文件!')
                return
            }
            var fileName = file.name.substring(file.name.lastIndexOf(".") + 1);
            if (fileName == 'xlsx' || fileName == 'xls') {
                formData.append('file', file);
                formData.append('cityCode', value.cityCode);
                $http({
                    method: "post",
                    url: '/credit/report/statistics/v/uploadIncome',
                    data: formData,
                    headers: {
                        'Content-Type': undefined
                    },
                }).then(function (response) {
                    if (response.data.code == "SUCCESS") {

                        refresh()
                    } else {
                        alert("文件上传失败！！！");
                    }
                });
            } else {
                alert('请上传表格文件!');
                return;
            }
        }
    };


    $scope.test = function () {
        console.log(this)
        // yulan()
    }

    $scope.cancel = function () {
        $scope.showModel = false
    }

    function getList() {
        $http({
            method: 'POST',
            url: '/credit/order/base/v/selectionConditions'
        }).success(function (data) {
            $scope.conditions = data.data;
            $http.post(`/credit/report/achievementStatistics/v/city`).then(function (res) {
                $scope.orderCitys = res.data.data
                $scope.page.cityCode = $scope.orderCitys[0].code
                $scope.productList = $scope.conditions.citys[0].productList;
                $scope.productList.pop()
                $scope.productList[0].productName = '全部'
                $scope.stateList = $scope.conditions.citys[0].productList[0].stateList;
                getData(init)
                selSet()
            })
        })
    }
    function selSet(){
        setTimeout(function () {
         $('.selectcity0001')[0].removeChild($('.selectcity0001')[0].firstChild)
        
        return
        for (let i = 0; i < $('.fixed-table-body').length; i++) {
            var tableCont = $('.fixed-table-body')[i]

            function scrollHandle(e) {
                var scrollTop = this.scrollTop;
                $('.fixed-table-body thead')[i].style.transform = 'translateY(' + scrollTop + 'px)';
            }
            tableCont.addEventListener('scroll', scrollHandle)
        }
    }, 100)
    }
    // setTimeout(function () {
    //      $('.selectcity0001')[0].removeChild($('.selectcity0001')[0].firstChild)
        
    //     return
    //     for (let i = 0; i < $('.fixed-table-body').length; i++) {
    //         var tableCont = $('.fixed-table-body')[i]

    //         function scrollHandle(e) {
    //             var scrollTop = this.scrollTop;
    //             $('.fixed-table-body thead')[i].style.transform = 'translateY(' + scrollTop + 'px)';
    //         }
    //         tableCont.addEventListener('scroll', scrollHandle)
    //     }
    // }, 1000)
});