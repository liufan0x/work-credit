define(function(require, exports, module) {
	exports.extend = function(app) {

		app.directive('businfoEdit', function($timeout, $http , route, box) {
			return {
				restrict: "E",
				templateUrl: '/plugins/page-directive/businfo/businfoEdit.html',
				transclude: true,
				link: function(scope) {
					scope.isDelMoveShow = false;
					
					function getParams(){
						var params = {
							"orderNo": route.getParams().orderNo,
							"productCode": route.getParams().productCode
						}
						return params;
					}

					//业务资料信息
					function refresh() {
						$http({
							method: 'POST',
							url: '/credit/page/businfo/v/pageBusinfoConfig',
							data: getParams()
						}).success(function(data) {
							scope.busInfo = data.data;
						})
					}

					$timeout(refresh, 300);

					scope.imgShow = function(showType, typeId) {
						scope.showType = showType;
						scope.isDelMoveShow = true;
						scope.toTypeId = "";
						scope.typeList = new Array();
						angular.forEach(scope.busInfo, function(data) {
							angular.forEach(data.sonTypes, function(data1) {
								if(data1.id == typeId) {
									scope.imgList = data1.listMap;
								} else {
									scope.typeList.push(data1);
								}
							});
						});
					}

					//设置typeId
					scope.setType = function(typeId) {
						scope.typeId = typeId;
					}

					//上传
					scope.uploadifyImg = function(imgs) {
						var list = new Array();
						angular.forEach(imgs.split(','), function(data) {
							var params = {
								orderNo : getParams().orderNo,
								typeId : scope.typeId,
								url : data
							}
							list.push(params);
						});
						businfoPost('/credit/page/businfo/v/pageBusinfoSave',list);
					}

					var isAll = false;
					scope.selectImg = function(e) {
						isAll = !isAll;
						$("input[name='ids']").prop("checked", isAll);
					}

					scope.imgcz = function() {
						var ids = "";
						$("input[name='ids']:checked").each(function() {
							ids += $(this).val() + ",";
						});
						if(ids.length != 0) {
							ids = ids.substring(0, ids.length - 1);
						} else {
							box.boxAlert("未选择图片");
							return false;
						}
						var params = getParams();
						params.ids = ids;
						if(scope.showType == 'del') {
							businfoPost("/credit/page/businfo/v/pageBusinfoDelete",params);
						} else {
							params.typeId = scope.toTypeId;
							businfoPost("/credit/page/businfo/v/pageBusinfoMove",params);
						}
					}
					
					function businfoPost(url,params){
						$http({
							method: 'POST',
							url: url,
							data: params
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								refresh();
								scope.isDelMoveShow = false;
							} else {
								box.boxAlert(data.msg);
							}
						});
					}
					
				}
			};
		});
	};
});