angular.module("anjboApp", ['bsTable']).controller("productEditCtrl", function($scope, $http, $state , box, route) {

	$scope.isEdit = false;
	$scope.productId = route.getParams().productId;
	$scope.page = new Object();
	$scope.page.productId = $scope.productId;
	
	$http({
		method: 'post',
		url: "/credit/data/product/v/find",
		data: {"id":$scope.productId}
	}).success(function(data) {
		$scope.productDto = data.data;
	});
	
	$scope.edit = function() {
		if(!$scope.isEdit) {
			$scope.isEdit = true;
		} else {
				$http({
					method: 'post',
					url: "/credit/data/product/v/edit",
					data: $scope.productDto
				}).success(function(data) {
					if(data.code == 'SUCCESS'){
						$scope.isEdit = false;	
					}
					box.boxAlert(data.msg);
				});
		}
	}

	window.operateEvents = {
		'click .pc': function(e, value, row, index) {
			alert(row.pcUrl);
		},
		'click .app': function(e, value, row, index) {
			alert(row.appUrl);
		}
	};

	function getParams(data) {
		$scope.page.start = data.offset;
		$scope.page.pageSize = data.limit;
		return $scope.page;
	}

	var url = "/credit/data/process/v/page";
	$scope.query = function() {
		$("#table").bootstrapTable('refresh', { url: url ,pageNumber:1});
	}

	$scope.productNodeList = {
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
			pageSize: 1000,
			pageList: ['All'],
			showColumns: true,
			showRefresh: true,
			columns: [{
				title: '序号',
				field: 'id',
				align: 'center',
				valign: 'bottom',
				formatter: function(value, row, index) {
					return index+1;
				}
			}, {
				title: '节点',
				field: 'processName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '节点所属模块',
				field: 'processModular',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '节点描述',
				field: 'processDescribe',
				align: 'left',
				valign: 'bottom'
			}, {
				title: '操作',
				field: 'operate',
				align: 'center',
				valign: 'bottom',
				events: operateEvents,
				formatter: function(value, row, index) {
					var html = '<a class="pc" href="javascript:void(0)" >系统PC页面</a>&nbsp;&nbsp;';
					html += '<a class="add" href="javascript:void(0)" >小鸽APP页面</a>&nbsp;&nbsp;';
					return html;
				}
			}]
		}
	};

});