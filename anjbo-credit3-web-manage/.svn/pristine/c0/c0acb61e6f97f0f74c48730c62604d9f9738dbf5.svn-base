angular.module("anjboApp", ['bsTable']).controller("fundCostListCtrl", function($scope, $http, $state, box, route) {
	
	$scope.fundId = route.getParams().fundId;
	$scope.fundName = route.getParams().fundName;

	window.operateEvents = {
		'click .edit': function(e, value, row, index) {
			$state.go("fundCostEdit", { 'prductId': row.prductId,'fundId':$scope.fundId,'fundName':$scope.fundName,'fundCostId':row.id });
		},
		'click .del': function(e, value, row, index) {
			box.editAlert($scope,"提示","是否删除？",function(){
				box.closeAlert();
				// 删除
				$http({
					method: 'post',
					url: "/credit/user/fundCost/v/delete",
					data:{id:row.id}
				}).success(function(data) {
					box.boxAlert(data.msg);
					if(data.code == 'SUCCESS'){
						$scope.query();
					}
				})
			});
		}
	};
	$scope.page = new Object();
	$scope.page.fundId = $scope.fundId;
	function getParams(data) {
		$scope.page.start = data.offset;
		$scope.page.pageSize = data.limit;
		return $scope.page;
	}
	
	var url = "/credit/user/fundCost/v/page";
	$scope.query = function() {
		$("#table").bootstrapTable('refresh', { url: url,pageNumber:1 });
	}

	$scope.fundCostList = {
		options: {
			method: "post",
			url: url,
			queryParams: getParams,
			sidePagination: 'server',
			undefinedText: "-",
			cache: false,
			striped: true,
			pagination: true,
			pageNumber: 1,
			pageSize: 15,
			pageList: ['All'],
			columns: [{
				title: '业务产品',
				field: 'productName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '日费率',
				field: 'dayRate',
				align: 'center',
				valign: 'bottom',
				formatter:function(value, row, index){
					return row.dayRate + "%";
				}
			}, {
				title: '逾期费率（天）',
				field: 'overdueRate',
				align: 'center',
				valign: 'bottom',
				formatter: function(value, row, index) {
					if(row.overdueRateHas == 0) {
						return "无";
					} else {
						return row.overdueRate+"%";
					}
				}
			}, {
				title: '优 惠',
				field: 'discountHas',
				align: 'center',
				valign: 'bottom',
				formatter: function(value, row, index) {
					if(row.discountHas == 0) {
						return "无";
					} else {
						return row.discountHasStr;
					}
				}
			}, {
				title: '风险拨备',
				field: 'riskProvision',
				align: 'center',
				valign: 'bottom',
				formatter: function(value, row, index) {
					if(row.riskProvisionHas == 0) {
						return "无";
					} else {
						return row.riskProvision+"%";
					}
				}
			}, {
				title: '操作',
				field: 'operate',
				align: 'center',
				valign: 'bottom',
				events: operateEvents,
				formatter: function(value, row, index) {
					var html = '<a class="edit" href="javascript:void(0)" >编辑</a>&nbsp;&nbsp;';
					html += '<a class="del" href="javascript:void(0)" title="Like">删除</a>&nbsp;&nbsp;';
					return html;
				}
			}]
		}
	};

});