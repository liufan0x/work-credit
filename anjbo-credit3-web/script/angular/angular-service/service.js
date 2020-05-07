define(function(require, exports, module) {

	exports.extend = function(app) {

		app.service('route', function($cookies) {
			var service = {};

			service.getParams = function() {
				try {
					return angular.fromJson(decodeURI($cookies.get("routeParams")));
				} catch(e) {
					return null;
				}
			}
			
			service.getUserDto = function(){
				return JSON.parse(localStorage.getItem('userDto'));
			}

			return service;
		});

		app.service('parent', function() {
			var service = {};
			return service;
		});
		
		app.service('process', function() {
			var service = {};
			
			service.processIdTransformationDirective = function(processId) {
				//机构只能看提单公证面签
				if(JSON.parse(localStorage.getItem('userDto')).agencyId!=1
						&&processId!='placeOrder'&&processId!='notarization'&&processId!='facesign'){
					return "no-auth";
				}
				if(processId == "noAuth"){
					return "no-auth";
				}else if(processId == "placeOrder") {
					return "place-order-detail";
				} else if(processId == "managerAudit") {
					return "manager-audit-detail";
				} else if(processId == "auditFirst") {
					return "audit-first-detail";
				} else if(processId == "auditFinal") {
					return "audit-final-detail";
				} else if(processId == "auditReview"){
					return "audit-review-detail";
				} else if(processId == "auditOfficer") {
					return "audit-officer-detail";
				} else if(processId == "signInsurancePolicy") {
					return "sign-insurance-policy-detail";
				} else if(processId == "uploadInsurancePolicy") {
					return "upload-insurance-policy-detail";
				} else if(processId == "allocationFund") {
					return "allocation-fund-detail";
				} else if(processId == "auditJustice") {
					return "audit-justice-detail";
				} else if(processId == "dataAudit") {
					return "audit-detail";
				}else if(processId == "fundDocking") {
					return "fund-docking-detail";
				}else if(processId == "fundAduit") {
					return "allocation-fund-aduit-detail";
				} else if(processId == "repaymentMember") {
					return "repayment-member-detail";
				} else if(processId == "applyLoan") {
					return "apply-loan-detail";
				}else if(processId == "isLendingHarvest") {
					return "isl-lending-harvest-detail";
				}else if(processId == "lendingHarvest") {
					return "lending-harvest-detail";
				}else if(processId == "isBackExpenses") {
					return "isb-back-expenses-detail";
				} else if(processId == "backExpenses") {
					return "back-expenses-detail";
				} else if(processId == "financialStatement") {
					return "financial-statement-detail";
				} else if(processId == "financialAudit") {
					return "financial-audit-detail";
				} else if(processId == "lendingPay") {
					return "lending-pay-detail";
				} else if(processId == "lendingInstructions") {
					return "lending-instructions-detail";
				} else if(processId == "receivableFor") {
					return "receivable-for-detail";
				} else if(processId == "receivableForEnd") {
					return "receivable-for-detail";
				} else if(processId == "receivableForFirst") {
					return "receivable-for-first-detail";
				} else if(processId == "elementReturn") {
					return "element-return-detail";
				} else if(processId == "rebate") {
					return "rebate-detail";
				}else if(processId == "wanjie"){
					return "place-order-detail";
				} else if(processId == "wanjie"){
					return "financial-statement-detail";
				} else if(processId=="fddMortgage"){ //抵押-房抵贷
					return "fdd-mortgage-detail";
				} else if(processId=="fddForensics"){ //取证-房抵贷
					return "fdd-forensics-detail";
				}else if(processId=="isCharge"){ //核实收费
					return "isfdd-charge-detail";
				}else if(processId=="fddMortgageStorage"){ //抵押品入库
					return "fdd-mortgage-storage-detail";
				}else if(processId=="fddMortgageRelease"){ //抵押品出库
					return "fdd-mortgage-release-detail";
				}else {
					return processId + "-detail";
				}
			}

			return service;
		});

		app.service('box', function($compile) {
			var service = {};

			service.waitAlert = function() {
				$('body').append('<div class="loading" ng-show="isWaitSumbit"><img src="images/loading_gif.gif" width="40" ></div>');
			}

			service.closeWaitAlert = function() {
				$(".loading").remove();
			}

			service.boxAlert = function(title, aa) {
				var htmlstring = '<div class="lhw-alert-bg"><div class="lhw-alert-box"><div class="lhw-alert-con">' + title + '</div><div class="lhw-alert-but"><button type="button" class="btn btn-primary lhw-alert-ok alert-ok">确  定</button></div></div>';
				$('body').append(htmlstring);
				$(".alert-ok").bind("click", function() {
					$(this).parent().parent().parent(".lhw-alert-bg").remove();
					if("function" == typeof aa) {
						aa();
					}

				});

			}
			service.boxAlert2 = function(title, aa) {
				var htmlstring = '<div class="lhw-alert-bg"><div class="lhw-alert-box"><div class="lhw-alert-con">' + title + '</div><div class="lhw-alert-but"><button type="button" class="btn btn-primary lhw-alert-ok alert-ok">确  定</button><button type="button" class="btn btn-default lhw-alert-cancel">取  消</button></div></div>';
				$('body').append(htmlstring);
				$(".alert-ok").bind("click", function() {
					$(this).parent().parent().parent(".lhw-alert-bg").remove();
					if("function" == typeof aa) {
						aa();
					}

				});
				$(".lhw-alert-cancel").bind("click", function() {
					$(this).parent().parent().parent(".lhw-alert-bg").remove();
				});

			}

			service.confirmAlert = function(title, content, aa) {
				var htmlstring = '<div class="lhw-alert-bg"><div class="lhw-alert-box"><div class="lhw-alert-tit">' + title + '<div class="x-box"></div></div><div class="lhw-alert-con">' + content + '</div><div class="lhw-alert-but"><button type="button" class="btn btn-primary lhw-alert-ok" style="margin-right:80px;">确  定</button><button type="button" class="btn btn-default lhw-alert-cancel">取  消</button></div></div>';
				$('body').append(htmlstring);
				$(".lhw-alert-ok").bind("click", function() {
					$(this).parent().parent().parent(".lhw-alert-bg").remove();
					if("function" == typeof aa) {
						aa();
					}
				});
				$(".x-box").bind("click", function() {
					$(this).parent().parent().parent(".lhw-alert-bg").remove();
				});
				$(".lhw-alert-cancel").bind("click", function() {
					$(this).parent().parent().parent(".lhw-alert-bg").remove();
				});
			}

//			service.editAlert = function(scope, title, content, aa) {
//				var htmlstring = '<div class="lhw-alert-bg"><div class="lhw-alert-box" style="width:600px"><div class="lhw-alert-tit">' + title + '</div><div class="lhw-alert-con">' + content + '</div><div class="lhw-alert-but"><button type="button" class="btn btn-primary lhw-alert-ok" style="margin-right:80px;">确  定</button><button type="button" class="btn btn-default lhw-alert-cancel">取  消</button></div></div>';
//				var el = $compile(htmlstring)(scope);
//				$("body").append(el);
//				$(".lhw-alert-ok").bind("click", function() {
//					if("function" == typeof aa) {
//						aa();
//					}
//				});
//				$(".lhw-alert-cancel").bind("click", function() {
//					$(this).parent().parent().parent(".lhw-alert-bg").remove();
//				});
//			}
			service.editAlertFinal = function(scope, title, content, aa,bb) {
				var htmlstring = '<div class="lhw-alert-bg"><div class="lhw-alert-box" style="width:600px"><div class="lhw-alert-tit"><div class="x-box"></div>' + title + '</div><div class="lhw-alert-con">' + content + '</div><div class="lhw-alert-but"><button type="button" class="btn btn-primary lhw-alert-ok" style="margin-right:80px;">确  定</button><button type="button" class="btn btn-primary lhw-alert-ok1" style="margin-right:80px;">提交并上报首席风险官</button><button type="button" class="btn btn-default lhw-alert-cancel">取  消</button></div></div>';
				var el = $compile(htmlstring)(scope);
				$("body").append(el);
				$(".lhw-alert-ok").bind("click", function() {
					if("function" == typeof aa) {
						aa();
					}
				});
				$(".lhw-alert-ok1").bind("click", function() {
					if("function" == typeof bb) {
						bb();
					}
				});
				$(".lhw-alert-cancel").bind("click", function() {
					$(angular.element.find(".lhw-alert-bg")).remove();
				});
				$(".x-box").bind("click", function() {
					$(angular.element.find(".lhw-alert-bg")).remove();
				});
			}
			service.editAlert400 = function(scope, title, content, aa) {
				var htmlstring = '<div class="lhw-alert-bg"><div class="lhw-alert-box" style="width:400px"><div class="lhw-alert-tit"><div class="x-box"></div>' + title + '</div><div class="lhw-alert-con">' + content + '</div><div class="lhw-alert-but"><button type="button" class="btn btn-primary lhw-alert-ok" style="margin-right:80px;">确  定</button><button type="button" class="btn btn-default lhw-alert-cancel">取  消</button></div></div>';
				var el = $compile(htmlstring)(scope);
				$("body").append(el);
				$(".lhw-alert-ok").bind("click", function() {
					if("function" == typeof aa) {
						aa();
					}
				});
				$(".lhw-alert-cancel").bind("click", function() {
//					$(this).parent().parent().parent(".lhw-alert-bg").remove();
					$(angular.element.find(".lhw-alert-bg")).remove();
				});
				$(".x-box").bind("click", function() {
//					$(this).parent().parent().parent(".lhw-alert-bg").remove();
					$(angular.element.find(".lhw-alert-bg")).remove();
				});
			}
			service.editAlert = function(scope, title, content, aa) {
				var htmlstring = '<div class="lhw-alert-bg"><div class="lhw-alert-box" style="width:600px"><div class="lhw-alert-tit"><div class="x-box"></div>' + title + '</div><div class="lhw-alert-con">' + content + '</div><div class="lhw-alert-but"><button type="button" class="btn btn-primary lhw-alert-ok" style="margin-right:80px;" ng-click="aaa($event )">确  定</button><button type="button" class="btn btn-default lhw-alert-cancel">取  消</button></div></div>';
				var el = $compile(htmlstring)(scope);
				$("body").append(el);
				scope.aaa=function(e){
					if("function" == typeof aa) {
 					  aa();
 					}
					   
				}
//				$(".lhw-alert-ok").bind("click", function() {
//					if("function" == typeof aa) {
//						aa();
//					}
//				});
				$(".lhw-alert-cancel").bind("click", function() {
//					$(this).parent().parent().parent(".lhw-alert-bg").remove();
					$(angular.element.find(".lhw-alert-bg")).remove();
				});
				$(".x-box").bind("click", function() {
//					$(this).parent().parent().parent(".lhw-alert-bg").remove();
					$(angular.element.find(".lhw-alert-bg")).remove();
				});
			}
			service.editAlertW500 = function(scope, title, content, aa) {
				var htmlstring = '<div class="lhw-alert-bg"><div class="lhw-alert-box" style="width:663px"><div class="lhw-alert-tit">' + title + '</div><div class="lhw-alert-con" style="text-align:left;margin: 0 0 0 14%">' + content + '</div><div class="lhw-alert-but"><button type="button" class="btn btn-primary lhw-alert-ok" style="margin-right:80px;">确  定</button><button type="button" class="btn btn-default lhw-alert-cancel">取  消</button></div></div>';
				var el = $compile(htmlstring)(scope);
				$("body").append(el);
				$(".lhw-alert-ok").bind("click", function() {
					if("function" == typeof aa) {
						aa();
					}
				});
				$(".lhw-alert-cancel").bind("click", function() {
					$(this).parent().parent().parent(".lhw-alert-bg").remove();
				});
			}
			
			service.closeAlert = function() {
				$(".lhw-alert-ok").parent().parent().parent(".lhw-alert-bg").remove();
			}

			return service;
		});

		app.service('dict', function($http) {
			var service = {};

			var dictList = new Array();

			service.initData = function() {
				jQuery.ajax({
					type: "post",
                    dataType:"json",
					url: '/credit/data/dict/v/search',
        			contentType: "application/json; charset=utf-8",
					data: JSON.stringify(new Object()),
					async: false,
					success: function(data) {
						dictList = data.data;
					}
				});
			}

			service.getData = function() {
				return dictList;
			}

			service.choiceDict = function(type, pcode) {
				var dictListTemp = new Array();
				var tempList = Array();

				angular.forEach(dictList, function(data, index, array) {
					if(data.type == type) {
						dictListTemp.push(data);
					}
				});

				if(!pcode) {
					angular.forEach(dictListTemp, function(data, index, array) {
						if(!data.pcode) {
							tempList.push(data);
						}
					});
					return tempList;
				} else if(pcode == "") {
					return dictListTemp;
				} else {
					angular.forEach(dictListTemp, function(data, index, array) {
						if(data.pcode) {
							if(data.pcode == pcode) {
								tempList.push(data);
							}
						}
					});
					return tempList;
				}
			}

			return service;
		});

		app.service('bank', function($http) {
			var service = {};

			var bankList = new Array();

			var fl = true;

			service.initData = function() {
				jQuery.ajax({
					type: "post",
                    dataType:"json",
					url: '/credit/data/bank/v/search',
        			contentType: "application/json; charset=utf-8",
					data: JSON.stringify(new Object()),
					async: false,
					success: function(data) {
						bankList = data.data;
					}
				});
			}

			service.getData = function() {
				return bankList;
			}

			service.choiceSubBank = function(bankId) {
				var tempList = Array();
				angular.forEach(bankList, function(data, index, array) {
					if(data.id == bankId) {
						tempList = data.subBankDtos.concat();
						return true;
					}
				});
				return tempList;
			}

			return service;
		});

		app.service('file',function($compile){
			var service = {};
			service.fileuploader = function(scope,FileUploader,box){
				var uploader = scope.uploader = new FileUploader({
					url: 'https://fs.anjbo.com/fs/file/upload'
				});
				uploader.filters.push({
					name: 'syncFilter',
					fn: function(item /*{File|FileLikeObject}*/, options) {
						console.log('syncFilter');
						return this.queue.length < 10;
					}
				});

				uploader.filters.push({
					name: 'asyncFilter',
					fn: function(item /*{File|FileLikeObject}*/, options, deferred) {
						console.log('asyncFilter');
						setTimeout(deferred.resolve, 1e3);
					}
				});

				//uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
					//console.info('onWhenAddingFileFailed', item, filter, options);
				//};
				/*
				uploader.onAfterAddingAll = function(addedFileItems) {
					console.info('onAfterAddingAll', addedFileItems);
				};
				uploader.onBeforeUploadItem = function(item) {
					console.info('onBeforeUploadItem', item);
				};
				*/
				uploader.onCompleteItem = function(fileItem, response, status, headers) {
					if(200==status&&"SUCCESS"==response.code){
						fileItem.feilData = response.data;
					} else {
						box.boxAlert(fileItem.file.name+"上传失败!");
					}
				};
				return uploader;
			}
			return service;
		});

	};
});