define(function(require, exports, module) {
	exports.extend = function(app) {

		app.directive('fileEdit', function($timeout, $http, $state, route, box, file, FileUploader) {
			return {
				restrict: "E",
				templateUrl: '/plugins/page-directive/file/common-file.html',
				transclude: true,
				link: function(scope) {
					scope.uploader = new Object();
					scope.uploadOrDownloadOperate = true;
					if($state.current.url.indexOf("Detail") >= 0) {
						scope.uploadOrDownloadOperate = false;
					}

					var upload = file.fileuploader(scope, FileUploader, box);

					function getParams() {
						var params = {
							"orderNo": route.getParams().orderNo,
							"productCode": route.getParams().productCode,
							"typeId": scope.m.typeDepend
						}
						return params;
					}

					function refreshFile() {
						$http({
							url: '/credit/page/businfo/v/selectPageBusinfo',
							method: 'POST',
							data: getParams()
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.files = data.data;
								scope.uploader.files = scope.files;
								if(scope.files && scope.files.length > 0){
									scope.m.value =	scope.files.length;
								}else{
									scope.m.value  = "";
								}
							} else {
								console.log(data.msg);
							}
						});
					}

					scope.fileUploadShow = function(flg) {
						scope.isFileUploadShow = flg
					}

					scope.deleteOrDownloadFileShow = function(flg, downloadOrDelete, tile) {
						scope.deleteOrDownloadFileTile = tile;
						scope.downloadOrDelete = downloadOrDelete;
						scope.isdDeleteOrDownloadFileShow = flg;
					}

					scope.deleteFile = function() {
						var ids = "";
						$("input[name='fileIds']:checked").each(function() {
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
						$http({
							url: '/credit/page/businfo/v/pageBusinfoDelete',
							method: 'POST',
							data: params
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								scope.isdDeleteOrDownloadFileShow = false;
								refreshFile();
							} else {
								box.boxAlert(data.msg);
							}
						});
					}
					scope.confirmUpload = function() {
						var imgs = "";
						angular.forEach(scope.uploader.queue, function(data) {
							if(data.isSuccess) {
								imgs += data.feilData.url + ",";
							}
						});
						if(imgs) {
							var params = getParams();
							params.urls = imgs.substring(0, imgs.length - 1);
							$http({
								url: '/credit/page/businfo/v/pageBusinfoSave',
								method: 'POST',
								data: params
							}).success(function(data) {
								if("SUCCESS" == data.code) {
									refreshFile();
								} else {
									box.boxAlert(data.msg);
								}
							});
						} else {
							box.boxAlert("请上传文件");
						}
					}

					refreshFile();

				}
			};
		});

	};
});