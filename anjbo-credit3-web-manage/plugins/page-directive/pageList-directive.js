define(function(require, exports, module) {
	exports.extend = function(app) {

		app.directive('pageList', function($http, $filter, $cookies, route) {
			return {
				restrict: "E",
				templateUrl: '/plugins/page-directive/pageList.html',
				transclude: true,
				link: function(scope) {

					var productCode = route.getParams().productCode;

					//获取列表页面配置
					function getListPageConfig() {
						$http({
							method: 'POST',
							url: "/credit/page/config/v/pageListConfig",
							data: {
								"productCode": productCode
							}
						}).success(function(data) {
							//获取保存分页参数
							scope.page = angular.fromJson(data.data.page);

							//获取保存的显示隐藏列
							var columnSwitch = $cookies.getObject("columnSwitch");
							if(!columnSwitch) {
								columnSwitch = data.data.columnSwitch;
							}
							//表格显示值计算
							angular.forEach(data.data.pageListColumnsConfigDtos, function(data1) {
								if(data1.field == 'operate') {
									scope.formatter(data1);
									return data1.formatter;
								} else {
									var formatter = data1.formatter;
									data1.formatter = function(value, row, index) {
										var functionStr = new Function(formatter);
										return functionStr(value, row, index, $filter);
									}
								}
								data1.visible = columnSwitch[data1.field];
							});
							postUrl = data.data.postUrl;
							postUrl = "/credit/page/list/v/pageListData";
							listConfig(data.data.pageListColumnsConfigDtos, columnSwitch);
						});
					}

					//组装请求参数
					function getParams(data) {
						scope.page.start = data.offset;
						scope.page.pageSize = data.limit;
						scope.page.condition = scope.conditionList;
						$cookies.putObject("pageParams", scope.page);
						return scope.page;
					}

					//组装列表
					function listConfig(columns, columnSwitch) {
						scope.pageList = {
							options: {
								method: "post",
								url: postUrl,
								queryParams: getParams,
								sidePagination: 'server',
								undefinedText: "-",
								cache: false,
								striped: true,
								pagination: true,
								pageNumber: (scope.page.start / scope.page.pageSize) + 1,
								pageSize: scope.page.pageSize,
								pageList: ['All'],
								showColumns: true,
								showRefresh: false,
								onClickRow: function(row, $element, field) {
									$element.toggleClass("bule-bg");
								},
								onColumnSwitch: function(field, checked) {
									columnSwitch[field] = checked;
									$cookies.putObject("columnSwitch", columnSwitch);
								},
								columns: columns
							}
						}
					}

					getListPageConfig();

				}
			};

		});
	};
});