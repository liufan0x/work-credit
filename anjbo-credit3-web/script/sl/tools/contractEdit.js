angular.module("anjboApp", ['bsTable']).controller("contractEditCtrl", function($scope, $compile, $http, $timeout, $state, box, route, process, parent) {

	var id = route.getParams().id;

	$scope.contractList = new Object();
	$scope.selectedOrder = new Object();
	$scope.taodahetong = true;

	$scope.qieTab = function(fl) {
		$scope.taodahetong = fl;
	}

	$timeout(function() {
		$(".tooltip-toggle").tooltip({
			html: true
		});
	}, 1000);

	$scope.checkAll = function(contractGroup) {
		angular.forEach(contractGroup.contractList, function(contract) {
			contract.isCheck = contractGroup.isCheck;
		});
		$scope.initFieldGroup();
	}

	$scope.showRelatedContract = function() {
		$state.go("orderDetail", {
			'orderNo': $scope.selectedOrder.orderNo,
			"cityCode": $scope.selectedOrder.cityCode,
			'processId': "placeOrder",
			'productCode': $scope.selectedOrder.productCode,
			relationOrderNo: $scope.selectedOrder.relationOrderNo
		});
	}

	$scope.initinit = function() {
		if(id != '-1') {
			//获取套打信息
			$http({
				method: 'POST',
				url: '/credit/tools/contractList/v/find',
				data: {
					id: id
				}
			}).success(function(data) {
				$scope.contractList = data.data;
				if($scope.contractList.data) {
					$scope.contractList.jsonOject = JSON.parse($scope.contractList.data);
				}
				$scope.init();
			})
		} else {
			$scope.init();
		}
	}

	$scope.init = function() {
		if($scope.contractList && $scope.contractList.orderNo) {
			//获取关联订单信息
			$http({
				method: 'POST',
				url: '/credit/order/baseList/v/find',
				data: {
					"orderNo": $scope.contractList.orderNo
				}
			}).success(function(data) {
				$scope.selectedOrder = data.data;
			})
		} else {
			$scope.selectedOrder = undefined;
		}

		//获取合同分组
		$http({
			method: 'POST',
			url: '/credit/tools/contractGroup/v/search',
			data: {
				"isEnable": 1
			}
		}).success(function(data) {
			$scope.contractGroupList = data.data;
			if($scope.contractList.contractIds) {
				var contractIds = $scope.contractList.contractIds.split(",");
				angular.forEach($scope.contractGroupList, function(contractGroupDto) {
					angular.forEach(contractGroupDto.contractList, function(contractDto) {
						if($.inArray(String(contractDto.id), contractIds) != -1) {
							contractDto.isCheck = true;
						}
					});
				});
			}
			$scope.initFieldGroup();
		})

		//获取字段
		$http({
			method: 'POST',
			url: '/credit/tools/fieldGroup/v/search',
			data: {}
		}).success(function(data) {
			if(data.code == 'SUCCESS') {
				$scope.groupList = data.data;
				if($scope.contractList.jsonOject) {
					angular.forEach($scope.groupList, function(groupDto) {
						angular.forEach(groupDto.fileList, function(filed) {
							angular.forEach(filed.inputs, function(input) {
								input.modelValue = $scope.contractList.jsonOject[input.value];
								if(!input.modelValue && input.modelValue != 0) {
									input.modelValue = $scope.contractList.jsonOject[input.source];
								}
							});
						});
					});
				}
				$scope.initFieldGroup();
			}
		})
	}

	$scope.initFieldGroup = function(contractGroup) {
		if(contractGroup) {
			contractGroup.isCheck = true;
			angular.forEach(contractGroup.contractList, function(contract) {
				if(!contract.isCheck) {
					contractGroup.isCheck = false;
				}
			});
		}

		var ids = new Array();
		angular.forEach($scope.contractGroupList, function(contractGroupDto) {
			angular.forEach(contractGroupDto.contractList, function(contractDto) {
				if(contractDto.fieldIds && contractDto.isCheck) {
					var fieldIds = contractDto.fieldIds.split(",");
					angular.forEach(fieldIds, function(id) {
						ids.push(id);
					});
				}
			});
		});

		angular.forEach($scope.groupList, function(groupDto) {
			groupDto.isShow = false;
			angular.forEach(groupDto.fileList, function(filedDto) {
				if($.inArray(String(filedDto.id), ids) != -1) {
					filedDto.isShow = true;
					groupDto.isShow = true;
				} else {
					filedDto.isShow = false;
				}
			});
		});
	}

	$scope.saveShow = function(fl) {
		$scope.tempType = new Object();
		$scope.tempType.type = 1;
		if(fl == 2 || fl == 3 || fl == 4) {
			var ids = "";
			angular.forEach($scope.contractGroupList, function(contractGroupDto) {
				angular.forEach(contractGroupDto.contractList, function(contractDto) {
					if(contractDto.isCheck) {
						ids += contractDto.id + ",";
					}
				});
			});

			if(!ids) {
				alert("请先选择合同！");
				return;
			}
		}

		box.editAlert($scope, "选择模板", $("#saveShow").html(), function() {
			$scope.save(fl, $scope.tempType.type);
			box.closeAlert();
		});
	}

	$scope.save = function(fl, type) {
		var ids = ""; //所选合同类型
		var contractNames = ""; //所选合同名称(如借款合同、承诺函之类的)
		var paths = "";   //**
		var noTextPaths = "";
		angular.forEach($scope.contractGroupList, function(contractGroupDto) {
			angular.forEach(contractGroupDto.contractList, function(contractDto) {
				if(contractDto.isCheck) {
					ids += contractDto.id + ",";
					contractNames += contractDto.name + ",";
					if(contractDto.path) {
						paths += contractDto.path + ",";
					}
					if(contractDto.noTextPath) {
						noTextPaths += contractDto.noTextPath + ",";
					}
				}
			});
		});
		if(ids) {
			ids = ids.substring(0, ids.length - 1);
			contractNames = contractNames.substring(0, contractNames.length - 1);
			if(paths.length > 0) {
				paths = paths.substring(0, paths.length - 1);
			}
			if(noTextPaths.length > 0) {
				noTextPaths = noTextPaths.substring(0, noTextPaths.length - 1);
			}
		}
		var pdfObj = new Object();
		if(type == 1) {
			pdfObj.paths = paths; //普通模板
		} else {
			pdfObj.paths = noTextPaths; //无文本模板
		}
		if(!pdfObj.paths && (fl == 2 || fl == 3 || fl == 4)) {
			alert("合同暂无模板");
			return;
		}
		var obj = new Object();
		var tempObj = new Object();
		obj.id = id;
		obj.jsonOject = new Object();
		angular.forEach($scope.groupList, function(groupDto) {
			angular.forEach(groupDto.fileList, function(filed) {
				angular.forEach(filed.inputs, function(input) {
					if(input.modelValue || input.modelValue == 0) {
						obj.jsonOject[input.value] = input.modelValue;
					}
					if(filed.isShow) {
						if(input.modelValue || input.modelValue == 0) {
							tempObj[input.value] = input.modelValue;
						}
					}
				});
			});
		});
		obj.contractIds = ids;
		obj.data = JSON.stringify(obj.jsonOject);
		var url = '/credit/tools/contractList/v/add';
		if(url != '-1') {
			url = '/credit/tools/contractList/v/edit';
		}
		//获取字段
		$http({
			method: 'POST',
			url: url,
			data: obj
		}).success(function(data) {
			if(data.code == 'SUCCESS') {
				pdfObj.customerName = $scope.contractList.customerName;
				pdfObj.jsonOject = tempObj;
				pdfObj.type = fl;
				pdfObj.contractNames = contractNames;
				pdfObj.fl = type;
				if(fl != 1) {
					var recordDto = new Object();
					recordDto.type = fl;
					recordDto.contractNames = contractNames;
					recordDto.contractListId = id;
					$scope.getPdf(recordDto, pdfObj);
				} else {
					alert(data.msg);
				}
			}
		})
	}

	//获取pdf地址
	$scope.getPdf = function(recordDto, pdfObj) {
		box.waitAlert();
		$http({
			method: 'POST',
			url: '/credit/tools/contractList/v/showPDF',
			data: pdfObj
		}).success(function(data) {
			recordDto.filePath = data.data;
			if(pdfObj.type == 2) {
				window.open(recordDto.filePath);
			} else if(pdfObj.type == 3) {
				window.open(recordDto.filePath);
			} else if(pdfObj.type == 4) {
				$scope.pdf = recordDto.filePath;
				$timeout(function() {
					$("#printIframe")[0].contentWindow.print();
				}, 300);
			}
			box.closeWaitAlert();
			$scope.addRecord(recordDto);
		})
	}

	//生成记录
	$scope.addRecord = function(recordDto) {
		//获取字段
		$http({
			method: 'POST',
			url: '/credit/tools/contractListRecord/v/add',
			data: recordDto
		}).success(function(data) {})
	}

	$scope.reset = function() {
		box.confirmAlert("重置", "确定重置订单吗？", function() {
			//获取字段
			$http({
				method: 'POST',
				url: '/credit/tools/contractList/v/reset',
				data: $scope.contractList
			}).success(function(data) {
				alert(data.msg);
				if(data.code == 'SUCCESS') {
					$timeout(function() {
						$scope.initinit();
					});
				}
			})
		});

	}

	$scope.initinit();

	/** 套打合同记录开始 */

	$scope.page = new Object();

	function getParas(data) {
		$scope.page.start = data.offset;
		$scope.page.pageSize = data.limit;
		$scope.page.contractListId = id;
		$scope.page.sortOrder = data.order;
		$scope.page.sortName = 'createTime';
		return $scope.page
	}

	$scope.query = function() {
		$("#testTable").bootstrapTable('refresh', {
			url: "/credit/tools/contractListRecord/v/page",
			pageNumber: 1,
			pageSize: 10000
		});
	}

	window.relationEvents = {
		'click .showDetail': function(e, value, row, index) {
			var files = value.split(",");
			angular.forEach(files, function(data) {
				window.open(data);
			})
		}
	};

	$scope.testTable = {
		options: {
			method: "post",
			url: "/credit/tools/contractListRecord/v/page",
			queryParams: getParas,
			sidePagination: 'server',
			undefinedText: "-",
			cache: false,
			striped: true,
			pagination: true,
			pageNumber: 1,
			pageSize: 1000,
			pageList: ["all"],
			showColumns: true,
			sortName: 'createTime',
			sortOrder: 'desc',
			showRefresh: false,
			columns: [{
				title: '时间',
				field: 'createTime',
				align: 'center',
				valign: 'bottom',
				formatter: function(row, value, index, field) {
					return row;
				}
			}, {
				title: '操作人',
				field: 'createName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '操作类型',
				field: 'type',
				align: 'center',
				valign: 'bottom',
				formatter: function(row, value, index, field) {
					if(value.type == 2) {
						return "预览";
					} else if(value.type == 3) {
						return "下载";
					} else if(value.type == 4) {
						return "打印";
					}
				}
			}, {
				title: '合同名称',
				field: 'contractNames',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '操作',
				field: 'filePath',
				align: 'center',
				valign: 'bottom',
				events: relationEvents,
				formatter: function(row, value, index, field) {
					return '<a class="showDetail" href="javascript:void(0)">查看</a>'
				}
			}]

		}
	}

	/** 套打合同记录结束 */

});