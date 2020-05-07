define(function(require, exports, module) {
	exports.extend = function(app) {
		
		//匹配客户经理信息
		app.directive('specialType100', function($http, route, box) {
			return {
				restrict: "E",
				scope: true,
				link: function(scope) {
					scope.getMatchCusManager = function(custManagerMobile,list){
						if(custManagerMobile){
							angular.forEach(list,function(data){
								if(data.key == 'custManagerName' || data.key == 'subBankName' ){
									data.value = "";
									data.specialValue = "";
								}
							})
							$http({
								method: 'POST',
								url: "/credit/product/data/cm/assess/v/getMatchCusManager",
								data: {custManagerMobile:custManagerMobile}
							}).success(function(data) {
								if(data.code=='FAIL'){
									box.boxAlert(data.msg);
								}else{
									angular.forEach(list,function(data1){
										if(data1.key == 'custManagerName'){
											data1.value = data.data.RspbPsn_Name;
										}else if(data1.key == 'subBankName'){
											data1.value = data.data.RspbPsn_BnkNo;
											data1.specialValue = data.data.RspbPsn_BnkNm;
										}
									})
								}
							});
						}else{
							box.boxAlert("请填写客户经理手机号!");
						}
					}
					
					//监听手机框
					scope.changePhone = function(custManagerMobile,list){
						if(custManagerMobile.length==11){
							scope.getMatchCusManager(custManagerMobile,list);
						}else{
							angular.forEach(list,function(data1){
								if(data1.key == 'custManagerName'){
									data1.value = '';
								}else if(data1.key == 'subBankName'){
									data1.value = '';
									data1.specialValue = '';
								}
							})
						}
					}
				}
			}
		});
		
		//查询应还款计划的接口
		app.directive('specialType1008', function($http, route, box,$filter) {
			return {
				restrict: "E",
				templateUrl: '/template/product-data/directive/query-loan-success.html',
				scope: true,
				link: function(scope) {
					scope.showPlay = false;
					scope.selectRepaymentPlan = function(show){
						if(show){
							$http({
								method: 'POST',
								url: "/credit/product/data/cm/assess/v/selectRepaymentPlan",
								data: {"orderNo":route.getParams().orderNo}
							}).success(function(data) {
								console.log(data);
								scope.play = data.data;
								scope.play.totalInfos = angular.fromJson(data.data.totalInfos);
								var dateStr = scope.play.maturityDate.substr(0,4) + '-' + scope.play.maturityDate.substr(4,2) + '-' + scope.play.maturityDate.substr(6,2);
								angular.forEach(scope.play.totalInfos, function(data,index,array){
									var date = new Date(dateStr);
									data.indexDate = $filter("date")(date.setMonth(date.getMonth()+data.index),'yyyy年MM月');
								});
							});
						}
						scope.showPlay = show;
					}
				}
			}
		});
		
		//影像资料开始
		app.directive('specialType1000', function($http, route, box) {
			return {
				restrict: "E",
				templateUrl: '/template/product-data/common/common-businfo.html',
				scope: true,
				link: function(scope) {
					
					function refresh() {
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
								"tblName":route.getParams().tblName
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
						sType.operate = busInfoData.operate;
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
									"tblName":route.getParams().tblName
								}
							}).success(function(data) {
								refresh();
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
					
					var isAll=false;
					scope.selectImg = function(e){
						isAll=!isAll;
						$("input[name='ids']").prop("checked",isAll);
					}
					
					scope.imgcz = function() {
						
						var businfoIds = new Array();
						$("input[name='ids']:checked").each(function(){
							businfoIds.push($(this).val());
						});
				
						if(businfoIds.length == 0) {
							box.boxAlert("未选择图片");
							return false;
						}
				
						if(scope.showType == 'del') {
							$http({
								method: 'POST',
								url: '/credit/product/data/businfo/base/deleteIds',
								data: {
									"orderNo": route.getParams().orderNo,
									"businfoIds": businfoIds,
									"tblName":route.getParams().tblName
								}
							}).success(function(data) {
								if("SUCCESS" == data.code) {
									refresh();
									scope.isDelMoveShow = false;
								} else {
									box.boxAlert(data.msg);
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
									"toTypeId": scope.toTypeId
								}
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
					
					refresh();
					
				}
			}
		});
		//影像资料结束
		
	};
});