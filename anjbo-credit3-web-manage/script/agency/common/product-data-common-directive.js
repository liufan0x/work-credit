define(function(require, exports, module) {
	exports.extend = function(app) {
		
		
		//流水指令 start
		app.directive('commonFlow', function($http, $state,route) {
			return {
				restrict: "E",
				templateUrl: '/template/agency/common/common-flow.html',
				link: function(scope) {
					scope.detailList = new Array();
					//获取流水
					$http({
						method: 'POST',
						url: "/credit/page/data/v/pageFlowData",
						data: route.getParams()
					}).success(function(data) {
						scope.orderFlowList = data.data;

						var tmp  = new Array();
						angular.forEach(scope.orderFlowList,function(data,index,array){
							if(data.currentProcessName!="待签约"&&data.currentProcessId!="addAgency"){
								data.currentProcessName = data.currentProcessName.replace("已","");
								data.currentProcessName = data.currentProcessName.replace("待","");
								tmp.push(data);
							}
						});
						scope.isDistribution = false;
						if(tmp.length==0&&scope.orderFlowList&&scope.orderFlowList.length>0){
							scope.isDistribution = true;
						}
						scope.orderFlowList = tmp;

						if(scope.orderFlowList && scope.orderFlowList.length>0){
							scope.processId = scope.orderFlowList[scope.orderFlowList.length-1].currentProcessId;
							if(scope.processId.indexOf("Fail")<0&&scope.processId.indexOf("Cancel")<0&&scope.processId!="agencySignSuccess"){
								scope.lastOrderFlowList = scope.orderFlowList[scope.orderFlowList.length-1];
							}

						}else{
							scope.processId = route.getParams().processId;
							scope.lastOrderFlowList = new Object();
							scope.lastOrderFlowList.nextProcessName = "待分配";
							scope.lastOrderFlowList.nextProcessId = "agencyWaitDistribution";
						}


					});
					
					scope.addDetail = function(processId){
						if(processId==scope.orderFlowList[scope.orderFlowList.length-1].nextProcessId){
							return;
						}
						scope.processId = processId;
						if($.inArray(processId, scope.detailList)!= 0){
							scope.detailList.push(processId);	
						}
						
					}
					
					scope.deleteDetail = function(processId){
						angular.forEach(scope.detailList,function(data,index){
							if(data == processId){
								scope.detailList.splice(index,1)
							}
						});
					}
					
				}
			}
		});
		//流水指令 end
	
		//详情指令 start
		app.directive('commonDetail', function($http,$state,$timeout,$sce,route) {
			return {
				restrict: "E",
				templateUrl: '/template/agency/common/common-detail.html',
				scope:{
					processId:"@",
					deleteDetail:"&"
				},
				link: function(scope) {
					if($state.current.name.indexOf('Detail')>=0){
						scope.isEdit = false;
					}else if($state.current.name.indexOf('Edit')>=0){
						scope.isEdit = true;
					}
					var params = {
						'orderNo':route.getParams().orderNo,
						'pageClass':route.getParams().tblName + route.getParams().processId + '_page'
					}

					//赋值，显示隐藏 start
					function showHide(formList,valueData) {
						angular.forEach(formList, function(data, index, array) {
							console.log(data.params);
							console.log(data.paramsType);
							if(data.params
								&& (data.paramsType.indexOf("4") >= 0||data.paramsType.indexOf("5") >= 0)&&data.params.indexOf("&")>=0){
								data.params = data.params.split("&")[0];
								data.values = data.values.split("&")[0];
								data.paramsType = "1";
							}
							if(data.params && data.paramsType.indexOf("1") >= 0) {
								angular.forEach(formList, function(data1, index, array) {
									if(data.params == data1.key || data.params == data1.specialKey) {
										scope.$watch(function() {
											if(data.params.indexOf("special") >= 0) {
												return data1.value;
											} else {
												return data1.specialValue;
											}
										}, function(newValue, odlValue) {
											var values = data.values.split("|.|")[0]
											var tempShowValues = values.split("|,|");
											data.isHide = !($.inArray(newValue, tempShowValues) >= 0);
										});
									}
								});
							}
						});
					}
					//赋值，显示隐藏 end
					
					//设置表单值
					function setValue(formList, valueData) {
						angular.forEach(formList, function(data, index, array) {
							if(index == 9){
								console.log(index);
							}
							data.value = valueData[data.key];
							var showValue = valueData[data.key];
							data.showValue = showValue;
							try{
								if(showValue){
									showValue = showValue.replace(/\n/g,"<br>");
									data.showValue = $sce.trustAsHtml(showValue);
								}
								//if(showValue&&showValue.match(/\n*/g).length>0){
								//	showValue = showValue.replace(/\n/g,"<br>");
								//	data.contextBrVal = $sce.trustAsHtml(showValue);
								//	data.contextIsBr = true;
								//}
								var tmp;
								if(data.title&&""!=data.title&&data.title.indexOf("<br>")>0){
									tmp = data.title;
									data.brValue = $sce.trustAsHtml(tmp);
									data.isBr = true;
								}
							}catch(e){
								console.log(e);
							}
							//$scope.remark = $scope.remark.replace(/\n/g,"<br>");

							if(data.specialKey){
								data.specialValue = valueData[data.specialKey];
							};
							if(!data.showValue&&data.showValue!=0){
								data.showValue = "-";
							};
							if(data.type == 5) {
								data.dataList = new Array();
								if(data.value){
									$timeout(function() {
										angular.forEach(data.value.split(','), function(data1) {
											data.dataList.push(data1)
										})
									});
								}
							};

							//设置多选择框
							if(20==data.type&&data.value){
								var dataValueArray = data.value.split(",");
								angular.forEach(data.dataList,function(datavalue,indexb,arrayb){
									datavalue.check = false;
									angular.forEach(dataValueArray,function(dataa,indexa,arraya){
										if(datavalue.id==dataa||datavalue.name==dataa){
											datavalue.check = true;
										}
										if((indexa+1)==data.value.length){
											return;
										}
									});
								});
							};
						});
					}
					
					//设置表单值 start
					function setData(pageTabConfigDto) {
						if(!pageTabConfigDto.tblName||""==pageTabConfigDto.tblName){
							return;
						}
						$http({
							method: 'POST',
							url: "/credit/page/data/v/pageData",
							data: {
								orderNo : params.orderNo,
								tblName : pageTabConfigDto.tblName
							}
						}).success(function(data) {
								angular.forEach(pageTabConfigDto.pageTabRegionConfigDtos, function(data1,index) {
									if(data1.key && data.data) {
										var tempData = angular.fromJson(data.data.data)[data1.key];
										if(!tempData && data1.key == 'assessHistoryList'){
											pageTabConfigDto.pageTabRegionConfigDtos.splice(index,1);
											return false;
										}
										angular.forEach(tempData, function(data2, index) {
											var tempList = new Array();
											if(index > 0) {
												angular.forEach(data1.valueList[0].concat(), function(data3, index) {
													tempList.push(angular.copy(data3));
												});
												data1.valueList.push(tempList);
											} else {
												tempList = data1.valueList[0].concat();
											}
											setValue(tempList, data2);
											showHide(tempList,data2);
										});
									} else {
										angular.forEach(data1.valueList, function(data2, index) {
											if(data.data) {
												setValue(data2, angular.fromJson(data.data.data));
											}
											showHide(data2,angular.fromJson(data.data.data));
										});
									}
								});
								
						});
					}
					//设置表单值 end		
					
					//获取页面配置 start
					function getPageConfig(){
						$http({
							method: 'POST',
							url: "/credit/page/config/v/pageConfig",
							data: params
						}).success(function(data) {
							if(data.code  == "SUCCESS"){
								scope.pageConfigDto = data.data;
								scope.changeView(scope.pageConfigDto.pageTabConfigDtos[0]);
								
								angular.forEach(scope.pageConfigDto.pageTabConfigDtos, function(data) {
									setData(data);
								});
							}
						});
					}
					//获取页面配置 end
					
					//切换tab页 start
					scope.changeView = function(pageTabConfigDto) {
						scope.showView = pageTabConfigDto.title;
						scope.pageTabConfigDto = pageTabConfigDto;
					}
					//切换tab页 end
					
					//监控processId改变
					scope.$watch("processId",function(){
						if(!scope.processId){
							scope.processId = route.getParams().processId;
						}
						params = {
							'orderNo':route.getParams().orderNo,
							'pageClass':route.getParams().tblName + scope.processId + '_page'
						}
						getPageConfig();
					})
					//监控processId改变
					
					scope.goBack = function(){
						$state.go("waitSignAgency");
					};
					scope.save = function(){
						alert("");
					};
				
				}
			}
		});
		//详情指令 end


	};
});