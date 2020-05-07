angular.module("anjboApp", ['bsTable']).controller("contractListCtrl", function($scope, $timeout, $compile, $http, $state, box, process, parent) {

	$scope.page = new Object();

	$http({
		method: 'POST',
		url: '/credit/data/dict/v/search',
		data: {
			"type": "cityList"
		}
	}).success(function(data) {
		$scope.citys = data.data;
	})

	$scope.productListAll = new Array();
	$scope.productList = new Array();

	$http({
		method: 'POST',
		url: '/credit/data/product/v/search',
		data: {}
	}).success(function(data) {
		$scope.productListAll = data.data;
		$scope.page.cityCode = "";
	})

	$scope.$watch("page.cityCode", function(newValue, oldValue) {
		angular.forEach($scope.productListAll, function(data) {
			if(data.productCode == '100') {
				return;
			}
			if(newValue) {
				if(data.cityCode == newValue) {
					$scope.productList.push(data);
				}
			} else {
				$scope.productList.push(data);
			}
		});
		$scope.page.productCode = "";
	})

	function getParas(data) {
		$scope.page.start = data.offset
		$scope.page.pageSize = data.limit
		$scope.page.sortOrder = data.order
		$scope.page.sortName = 'createTime'
		return $scope.page
	}
	$scope.query = function() {
		$("#testTable").bootstrapTable('refresh', {
			url: "/credit/tools/contractList/v/page",
			pageNumber: 1,
			pageSize: 10
		});
	}

	$scope.addNewContract = function() {
		$scope.contractListDto = new Object();
		$scope.orderList = new Array();
		$scope.$watch("contractListDto.customerName", function(newVal, old) {
			if(newVal) {
				$http({
					method: 'POST',
					url: '/credit/order/baseList/v/selectAbleRelationOrder',
					data: {
						customerName: newVal
					}
				}).success(function(data) {
					$scope.relationOrderNoList = data.data;
					var htmlstring = '<select class="form-control cleanOrders " ng-model="contractListDto.orderNo" style="width: 180px;" ><option value="">无</option><option ng-repeat="order in relationOrderNoList" ng-value="order.orderNo">{{order.name}}</option></select>';
					var el = $compile(htmlstring)($scope);
					$(".orderListId:eq(1)").html(el);
					$scope.contractListDto.orderNo = "";
					$scope.contractListDto.relationOrderNo = "";
					$scope.contractListDto.info = undefined;
					$scope.contractListDto.id = undefined;
				})
			}
		})

		$scope.$watch("contractListDto.orderNo", function(newVal, old) {
			if(newVal) {
				$http({
					method: 'POST',
					url: '/credit/tools/contractList/v/find',
					data: {
						orderNo: newVal
					}
				}).success(function(data) {
					if(data.code == "SUCCESS" && data.data) {
						$scope.contractListDto.id = data.data.id;
						$scope.contractListDto.info = "列表中已有该订单,可点击确定直接查看详情";
					}else{
						$scope.contractListDto.info = undefined;
						$scope.contractListDto.id = undefined;
					}
				})
			}
		})

		box.editAlert($scope, "新增套打", $("#addShow").html(), function() {
			if($scope.contractListDto.id) {
				$state.go("contractEdit", {
					'id': $scope.contractListDto.id
				})
			}else{
				if(!$scope.contractListDto.customerName) {
					alert("客户姓名必填");
					return;
				}
				$http({
					method: 'POST',
					url: '/credit/tools/contractList/v/add',
					data: $scope.contractListDto
				}).success(function(data) {
					alert(data.msg);
					if(data.code == 'SUCCESS') {
						$state.go("contractEdit", {
							'id': data.data.id
						})
					}
				})
			}
			box.closeAlert();
		});
	}

	window.relationEvents = {
		'click .showDetail': function(e, value, row, index) {
			$state.go("contractEdit", {
				'id': row.id
			})
		}
	};

	$scope.show=function(){
   	 $http.post('/credit/third/api/platform/getInsuranceFile', {   ///credit/third/api/sgtongBorrowerInformation/v/showSftp    
   		channelCode:"CCB", 
   	   serviceName:"getInsuranceFile", 
   	   timestamp:"1494773255",
   	   sign:"675ba4813e62bc9ecffa203363637ac9",
   	   data:{"idCardType":"CER", "idCardNumber":"430621199903292715", "customerName":"李岳"}
   	 }).then(function (res) {
        	if(newVal){
            $scope.RelationOrder = res.data.data
        	}else{
        		$("#hid").empty();
        		$("#shows").empty();
        		$("#shows").append($html);
        	}
        })
   }
	
	
	$scope.testTable = {
		options: {
			method: "post",
			url: "/credit/tools/contractList/v/page",
			queryParams: getParas,
			sidePagination: 'server',
			undefinedText: "-",
			cache: false,
			striped: true,
			pagination: true,
			pageNumber: 1,
			pageSize: 10,
			pageList: [10, 20, "all"],
			showColumns: true,
			sortName: 'createTime',
			sortOrder: 'desc',
			showRefresh: false,
			columns: [{
				title: '创建时间',
				field: 'createTime',
				align: 'center',
				valign: 'bottom',
				sortable: true,
				formatter: function(row, value, index, field) {
					return row;
				}
			}, {
				title: '城市',
				field: 'cityName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '产品名称',
				field: 'productName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '客户姓名',
				field: 'customerName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '借款金额（万元）',
				field: 'borrowingAmount',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '借款期限（天）',
				field: 'borrowingDay',
				align: 'center',
				valign: 'bottom',
				formatter: function(row, value, index, field) {
					return row ? row : '-'
				}
			}, {
				title: '关联订单',
				field: 'notLendingAmount',
				align: 'center',
				valign: 'bottom',
				formatter: function(row, value, index, field) {
					if(value.orderNo) {
						return '<a class="showRelatedContract" href="javascript:void(0)">查看</a>';
					}
					return "";
				},
				events: {
					'click .showRelatedContract': function(e, row, value, index) {
						$state.go("orderDetail", {
							'orderNo': value.orderNo,
							"cityCode": value.cityCode,
							'processId': "placeOrder",
							'productCode': value.productCode,
							'relationOrderNo': value.relationOrderNo
						});
					},
				},
			}, {
				title: '操作',
				align: 'center',
				valign: 'bottom',
				events: relationEvents,
				formatter: function(row, value, index, field) {
					return '<a class="showDetail" href="javascript:void(0)">查看详情</a>'
				}
			}]

		}
	}

});