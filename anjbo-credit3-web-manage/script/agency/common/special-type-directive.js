define(function(require, exports, module) {
	exports.extend = function(app) {

		//影像资料开始
		app.directive('specialType1000', function($http,$state, route, box) {
			return {
				restrict: "E",
				templateUrl: '/template/agency/common/common-businfo.html',
				scope: true,
				link: function(scope) {

					function refresh() {
						console.log("业务类型："+route.getParams().productCode);
						$http({
							method: 'POST',
							url: '/credit/config/page/businfo/type/query',
							data: {
								"productCode":route.getParams().productCode,
								"tblName":route.getParams().tblName
							}
						}).success(function(data) {
							scope.busInfo = data.data;
							getBusInfoData();
						})
					}
					
					function getBusInfoData(busInfo){
						$http({
							method: 'POST',
							url: '/credit/product/data/businfo/base/select',
							data: {
								"orderNo":route.getParams().orderNo,
								"tblName":route.getParams().tblName,
								"source":route.getParams().source
							}
						}).success(function(data) {

							angular.forEach(scope.busInfo.parentTypeList,function(data1){
								angular.forEach(data1.sonTypeList,function(data2){
									setData(data2,data.data);
								});
							});
						})
					}
					
					function setData(sType,busInfoData){
						if($state.current.url.indexOf("Detail")>=0){
							sType.operate = false;
						} else if("maintain"==route.getParams().source) {
							sType.operate = true;
						} else {
							sType.operate = busInfoData.operate;
						}
						if(busInfoData.businfo.length == 0){
							sType.imgList = new Array();
						}
						angular.forEach(busInfoData.businfo,function(data){
							if(!sType.imgList){
								sType.imgList = new Array();
							}
							if(sType.id == data.typeId){
								sType.imgList.push(data);
							}
						});
					}
					
					scope.setType = function(typeId){
						scope.typeId = typeId;
					}
					
					scope.upload = function(urls,simg,name,imgarr){

						if(urls&&urls!=''){
							$http({
								method: 'POST',
								url: '/credit/product/data/businfo/base/save',
								data: {
									"orderNo":route.getParams().orderNo,
									"typeId":scope.typeId,
									"urls":urls,
									"images":imgarr,
									"tblName":route.getParams().tblName+route.getParams().processId+"&"+route.getParams().source
								}
							}).success(function(data) {
								if("SUCCESS"==data.code){
									refresh();
								} else {
									alert(data.msg);
								}
							})
						}
					}
					
					scope.imgShow = function(showType,typeId,imgList) {
						scope.showType = showType;
						scope.isDelMoveShow = true;
						scope.toTypeId = "";
						if(showType == 'move') {
							$http({
								method: 'POST',
								url: '/credit/config/page/businfo/type/getAllType',
								data: {
									"productCode":route.getParams().productCode,
									"typeId": typeId
								}
							}).success(function(data) {
								if("SUCCESS" == data.code) {
									scope.typeList = new Array();
									angular.forEach(data.data.typeList,function(data1){
										if(data1.id != typeId){
											scope.typeList.push(data1);
										}
									});
								}
							});
						}
						scope.imgList = imgList;
					}
					
					scope.imgcz = function() {
						
						var businfoIds = new Array();
						$("input[name='ids']:checked").each(function(){
							businfoIds.push($(this).val());
						});
				
						if(businfoIds.length == 0) {
							alert("未选择图片");
							return false;
						}
				
						if(scope.showType == 'del') {
							$http({
								method: 'POST',
								url: '/credit/product/data/businfo/base/deleteIds',
								data: {
									"orderNo": route.getParams().orderNo,
									"businfoIds": businfoIds,
									"tblName":route.getParams().tblName,
									"processId":route.getParams().processId,
									"source":route.getParams().source
								}
							}).success(function(data) {
								if("SUCCESS" == data.code) {
									refresh();
									scope.isDelMoveShow = false;
								} else {
									alert(data.msg);
								}

							});
						} else {
							$http({
								method: 'POST',
								url: '/credit/product/data/businfo/base/move',
								data: {
									"orderNo": route.getParams().orderNo,
									"productCode":route.getParams().productCode,
									"tblName":route.getParams().tblName,
									"businfoIds": businfoIds,
									"toTypeId": scope.toTypeId,
									"processId":route.getParams().processId,
									"source":route.getParams().source
								}
							}).success(function(data) {
								if("SUCCESS" == data.code) {
									refresh();
									scope.isDelMoveShow = false;
								} else {
									alert(data.msg);
								}

							});
						}
					}

					refresh();
					
				}
			}
		});
		//影像资料结束
		app.directive('commonFile', function($http,$state,route,box) {
			return {
				restrict: "EC",
				templateUrl: '/template/agency/common/common-file.html',
				link: function(scope,elemt,attr) {
					var processId = attr.processId;
					var data = {};
					if(!processId||""==processId){
						data = {
							'orderNo':route.getParams().orderNo,
							'tblName':route.getParams().tblName,
							'processId':route.getParams().processId,
							"productCode": route.getParams().productCode
						}
					} else {
						data = {
							'orderNo':route.getParams().orderNo,
							'tblName':processId,
							"productCode": route.getParams().productCode
						}
					}
					scope.uploadOrDownloadOperate = true;
					if($state.current.url.indexOf("Detail")>=0){
						scope.uploadOrDownloadOperate = false;
					}
					function refreshFile(){
						$http({
							url:'/credit/page/businfo/v/selectPageBusinfo',
							method:'POST',
							data:data
						}).then(function successCallback(response){
							if("SUCCESS"==response.data.code){
								scope.files = response.data.data;
								scope.uploader.files = scope.files;
							} else {
								console.log(response.data.msg);
							}
						},function errorCallback(response){
//							box.boxAlert("查询附件信息异常,请联系开发部!");
						});
					}
					refreshFile();
					scope.deleteOrDownloadFileShow = function(flg,downloadOrDelete,tile){
						scope.deleteOrDownloadFileTile = tile;
						scope.downloadOrDelete = downloadOrDelete;
						if(flg){
							$("#deleteOrDownloadFileShow").show();
						} else {
							$("#deleteOrDownloadFileShow").hide();
						}
					}
					scope.fileUploadShow = function(flg){
						if(flg){
							$("#fileUploadShow").show();
						} else {
							$("#fileUploadShow").hide();
							scope.uploader.clearQueue();
						}

					}

					scope.deleteFile = function(){
						var fileIds = new Array();
						$("input[name='fileIds']:checked").each(function(){
							fileIds.push($(this).val());
						});
						if(fileIds.length<=0){
							box.boxAlert("请选择要删除的文件");
							return;
						}
						var ids = null;
						angular.forEach(fileIds,function(data){
							if(null==ids){
								ids = data;
							} else {
								ids += ","+data;
							}
						});

						$http({
							url:'/credit/product/data/file/base/v/deleteFileByIds',
							method:'POST',
							data:{
								"ids":ids,
								"tblName":route.getParams().tblName,
								"orderNo":route.getParams().orderNo,
								"processId":route.getParams().processId
							}
						}).then(function successCallback(response){
							if("SUCCESS"==response.data.code){
								$("#deleteOrDownloadFileShow").hide();
								refreshFile();
							} else {
								box.boxAlert(response.data.msg);
							}
						},function errorCallback(response){
							box.boxAlert("查询附件信息异常,请联系开发部!");
						});

					}
					scope.refresh = function(){
						refreshFile();
					}

					scope.downloadfile = function(obj){
						$http({
							url:'/credit/product/data/sm/agency/v/downloadfile',
							method:'POST',
							data:angular.toJson(obj),
							responseType:'arraybuffer'
						}).then(function successCallback(response, status, headers, config){
							if(200==response.status){
								var blob = new Blob([response.data], { type: "application/octet-stream"});
								var fileName = obj.name;
								var a = document.createElement("a");
								document.body.appendChild(a);
								a.download = fileName;
								a.href = URL.createObjectURL(blob);
								a.click();
							}
						},function errorCallback(response){
							box.boxAlert("附件信息下载异常,请联系开发部!");
						});
					}

				}
			};
		})
		
	};
});