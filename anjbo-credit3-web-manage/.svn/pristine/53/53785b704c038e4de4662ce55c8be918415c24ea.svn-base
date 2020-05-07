angular.module("anjboApp", ['bsTable']).controller("agencyTypeListCtrl", function($scope, $http, $state, box, route) {


	$scope.addShow = function() {
		$scope.agencyTypeDto = new Object();
		var add = function() {
			// 添加机构类型
			var params = {
				"name":$scope.agencyTypeDto.name,
				"describe":$scope.agencyTypeDto.describe
			}
			$http({
				method: 'post',
				url: "/credit/customer/agency/type/v/edit",
				data: params
			}).success(function(data) {
				box.boxAlert(data.msg,function(){
					if(data.code == 'SUCCESS'){
						$scope.query();
					}
				});
				
			})
		}
		box.editAlert($scope, "添加机构类型", $("#editId").html(), add);
	}

	window.operateEvents = {
		'click .edit': function(e, value, row, index) {
			// 获取机构机构类型
			$scope.agencyTypeDto = row;
			var edit = function() {
				//修改机构类型
				var params = {
					"id":$scope.agencyTypeDto.id,
					"name":$scope.agencyTypeDto.name,
					"describe":$scope.agencyTypeDto.describe
				}
				$http({
					method: 'post',
					url: "/credit/customer/agency/type/v/edit",
					data: params
				}).success(function(data) {
					box.boxAlert(data.msg);
					if(data.code == 'SUCCESS'){
						$scope.query();
					}
				})
			}
			box.editAlert($scope, "编辑机构类型", $("#editId").html(), edit);
		},
		'click .configureFeescale': function(e, value, row, index) {
			$state.go("agencyTypeFeescaleList", { 'agencyTypeId': row.id,'agencyTypeName':row.name });
		}
	};


	$scope.page = new Object();

	function getParams(data) {
		$scope.page.start = data.offset;
		$scope.page.pageSize = data.limit;
		return $scope.page;
	}

	var url = "/credit/customer/agency/type/v/page";
	$scope.query = function() {
		$("#table").bootstrapTable('refresh', { url: url,pageNumber:1 });
	}

	$scope.agencyTypeList = {
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
				title: '序号',
				field: 'id',
				align: 'center',
				valign: 'bottom',
				formatter: function(value, row, index) {
					return index+1;
				}
			}, {
				title: '机构类型',
				field: 'name',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '机构描述',
				field: 'describe',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '机构数量',
				field: 'typeCount',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '操作',
				field: 'operate',
				align: 'center',
				valign: 'bottom',
				events: operateEvents,
				formatter: function(value, row, index) {
					var html="";
					if($scope.meunAuth.indexOf('auth13auth')>=0){
						html = '<a class="edit" href="javascript:void(0)" >编辑</a>&nbsp;&nbsp;';
						html += '<a class="configureFeescale" href="javascript:void(0)" >配置收费标准</a>&nbsp;&nbsp;';
					}
					return html;
				}
			}]
		}
	};

});