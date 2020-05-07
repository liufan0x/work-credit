angular.module("anjboApp", ['bsTable']).controller("agencyTypeFeescaleListCtrl", function($scope, $http, $state, box, route) {



	var agencyTypeId = route.getParams().agencyTypeId;
	$scope.agencyTypeName = route.getParams().agencyTypeName;
	$scope.page = new Object();
	$scope.page.agencyTypeId = agencyTypeId;
		
	$scope.addShow = function() {
		$state.go("agencyTypeFeescaleEdit", {agencyTypeId:agencyTypeId,agencyTypeFeescaleId:'-1',agencyTypeName:$scope.agencyTypeName});
	}
	
	window.operateEvents = {
		'click .edit': function(e, value, row, index) {
			$state.go("agencyTypeFeescaleEdit", { agencyTypeId:agencyTypeId,'agencyTypeFeescaleId': row.id ,agencyTypeName:$scope.agencyTypeName});
		},
		'click .del': function(e, value, row, index) {
			var delMethod = function() {
				// 删除
				$http({
					method: 'post',
					url: ""
				}).success(function(data) {
					if(data.code=="SUCCESS"){
						$scope.query();
					}
				});
			}
			box.confirmAlert("提示", "确定要删除吗？", delMethod);
		}
	};
	
	function getParams(data) {
		$scope.page.start = data.offset;
		$scope.page.pageSize = data.limit;
		return $scope.page;
	}
	var url = "/credit/customer/agency/feescale/v/page";
	$scope.query = function() {
		$("#table").bootstrapTable('refresh', { url: url,pageNumber:1});
	}

	$scope.agencyTypeFeescaleList = {
		options: {
			method: "post",
			url: url,
			sidePagination: 'server',
			undefinedText: "-",
			queryParams: getParams,
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
				title: '固定服务费',
				field: 'servicefee',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '关外手续费',
				field: 'counterfee',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '其他费用',
				field: 'otherfee',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '操作',
				field: 'operate',
				align: 'center',
				valign: 'bottom',
				events: operateEvents,
				formatter: function(value, row, index) {
					var html = '<a class="edit" href="javascript:void(0)" >编辑</a>&nbsp;&nbsp;';
//					html += '<a class="del" href="javascript:void(0)" >删除</a>&nbsp;&nbsp;';
					return html;
				}
			}]
		}
	};

});