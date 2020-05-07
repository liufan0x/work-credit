angular.module("anjboApp", ['bsTable']).controller("reportListCtrl", function($scope, $http, $state, $filter, box, process, parent) {

	$scope.page = new Object();
	$scope.page.estimateOutLoanTimeOrderBy = "";
	$scope.page.status = "2";

	function getParams(data) {
		$scope.page.start = data.offset;
		$scope.page.pageSize = data.limit;
		$scope.page.sortOrder = data.order;
		$scope.page.sortName = data.sort;
		if($scope.page.outLoanStartTime) {
			$scope.page.outLoanStartTime = $scope.page.outLoanStartTime + " 00:00:00";
		}
		if($scope.page.outLoanEndTime) {
			$scope.page.outLoanEndTime = $scope.page.outLoanEndTime + " 00:00:00";
		}
		if($scope.page.createStartTime) {
			$scope.page.createStartTime = $scope.page.createStartTime + " 00:00:00";
		}
		if($scope.page.createEndTime) {
			$scope.page.createEndTime = $scope.page.createEndTime + " 00:00:00";
		}
		return $scope.page;
	}
	$http({
		method: 'POST',
		url: '/credit/order/base/v/selectionConditions'
	}).success(function(data) {
		$scope.conditions = data.data;
		$scope.orderCitys = new Array();
		angular.forEach($scope.conditions.citys, function(data, index) {
			if(index == 0) {
				data.cityName = '请选择';
			}
			angular.forEach(data.productList, function(data, index) {
				if(index == 0) {
					data.productName = '请选择';
				}
				angular.forEach(data.stateList, function(data, index) {
					if(index == 0) {
						data.stateName = '请选择';
					}
				});
			});
			$scope.orderCitys.push(data);
		});
	})
	var app = angular.module('anjboApp', []);
	window.operateEvents = {
		'click .confirmLoan': function(e, value, row, index) {
			$scope.thisRowObj = row;
			box.editAlert($scope, "确认放款", "确认已放款?", confirmLoan);
		},
		'click .replyRecord': function(e, value, row, index) {
			$scope.thisRowObj = row;
			box.editAlert($scope, "回复受理员", $("#replyRecordShow").html(), replyRecord);
		},
		'click .relationOrderDetail': function(e, value, row, index) {
			//$state.go("orderDetail",{'orderNo':row.relationOrderNo,'processId':row.processId});
			$state.go("orderDetail", {
				'orderNo': row.relationOrderNo,
				'cityCode': row.cityCode,
				'processId': row.processId,
				'productCode': row.productCode,
				relationOrderNo: row.relationOrderNo
			});
		},
		'click .editFund': function(e, value, row, index) {
			$scope.fund = row.fund;
			$scope.thisRowObj = row;
			box.editAlert400($scope, "修改资方", $("#editFunShow").html(), editFun);
		},
		'click .editTime': function(e, value, row, index) {
			$scope.fund = row.fund;
			$scope.thisRowObj = row;
			if(value) {
				$scope.financeOutLoanTime = $filter('date')(value, "yyyy-MM-dd HH:mm");
			}
			box.editAlert400($scope, "预计出款时间", $("#financeOutLoanTimeShow").html(), editOutLoanTime);
		},
		'click .top': function(e, value, row, index) {
			var param = {
				"orderNo": row.orderNo,
				"id": row.id,
				"top": 1
			}
			update(param, true);
		},
		'click .editFundExamine': function(e, value, row, index) {
			$scope.thisRowObj = row;
			$scope.fundExamine = row.fundExamine;
			box.editAlert400($scope, "修改资方审核状态", $("#eidtFundExamineShow").html(), editFundExamine);
		}
	};

	function editFun() {
		if(null == $scope.fund || "" == $scope.fund) {
			box.boxAlert("请填写资金方")
			return;
		}
		var param = {
			"orderNo": $scope.thisRowObj.orderNo,
			"fund": $scope.fund,
			"id": $scope.thisRowObj.id,
			"top": $scope.thisRowObj.top
		}
		update(param, true);

	}

	function editFundExamine() {
		if(null == $scope.fundExamine || "" == $scope.fundExamine) {
			box.boxAlert("资方审核状态")
			return;
		};
		var param = {
			"orderNo": $scope.thisRowObj.orderNo,
			"fundExamine": $scope.fundExamine,
			"id": $scope.thisRowObj.id,
			"top": $scope.thisRowObj.top
		}
		update(param, true);
	}

	function editOutLoanTime() {
		var date = new Date($scope.financeOutLoanTime);
		//设置时差
		var param = {
			"financeOutLoanTime": $filter('date')($scope.financeOutLoanTime, "yyyy-MM-dd HH:mm") + ":00",
			"orderNo": $scope.thisRowObj.orderNo,
			"id": $scope.thisRowObj.id,
			"top": 0
		}
		update(param, true);
		$scope.financeOutLoanTime = null;
	}

	function update(param, isRefresh) {
		$http({
			url: '/credit/tools/loanPreparation/v/edit',
			method: 'POST',
			data: param
		}).success(function(data) {
			box.boxAlert(data.msg);
			if("SUCCESS" == data.code) {
				$scope.thisRowObj = null;
				$scope.replyContent = "";
				$scope.fund = "";
				$scope.fundExamine = "";
				box.closeAlert();
				if(isRefresh) {
					$scope.query();
				}
			}

		});
	}

	function replyRecord() {
		if(!$scope.replyContent || "" == $scope.replyContent || null == $scope.replyContent) {
			box.boxAlert("请填写回复信息");
			return;
		}
		var param = {
			"orderNo": $scope.thisRowObj.orderNo,
			"replyContent": $scope.replyContent,
			"reportId": $scope.thisRowObj.id
		};
		$http({
			url: '/credit/tools/preparationReplyrecord/v/add',
			method: 'POST',
			data: param
		}).success(function(data) {
			alert(data.msg);
			if("SUCCESS" == data.code) {
				$scope.replyContent = "";
				$scope.thisRowObj = null;
				box.closeAlert();
			}
		});
	}

	function confirmLoan() {
		$http({
			url: '/credit/tools/loanPreparation/v/edit',
			method: 'POST',
			data: {
				"orderNo": $scope.thisRowObj.orderNo,
				"status": 1,
				"id": $scope.thisRowObj.id
			}
		}).success(function(data) {
			box.boxAlert(data.msg);
			if("SUCCESS" == data.code) {
				$scope.thisRowObj = null;
				box.closeAlert();
				$scope.query();
			}

		});
	}
	$scope.query = function() {
		$("#table").bootstrapTable('refresh', {
			url: '/credit/tools/loanPreparation/v/page'
		});
	}
	$scope.sort = function(field) {
		if("DESC" == $scope.page.estimateOutLoanTimeOrderBy) {
			$scope.page.estimateOutLoanTimeOrderBy = "ASC";
		} else if("ASC" == $scope.page.estimateOutLoanTimeOrderBy) {
			$scope.page.estimateOutLoanTimeOrderBy = "DESC";
		} else if("" == $scope.page.estimateOutLoanTimeOrderBy) {
			$scope.page.estimateOutLoanTimeOrderBy = "DESC";
		}
	}
	$scope.reportList = {
		options: {
			method: "post",
			url: "/credit/tools/loanPreparation/v/page",
			queryParams: getParams,
			sidePagination: 'server',
			undefinedText: "-",
			cache: false,
			striped: true,
			pagination: true,
			pageNumber: 1,
			pageSize: 15,
			pageList: ['All'],
			showColumns: true,
			showRefresh: false,
			onSort: function(field) {
				$scope.sort(field);
			},
			columns: [{
				title: '序号',
				field: 'id',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '报备时间',
				field: 'createTime',
				align: 'center',
				valign: 'bottom',
				formatter: function(value, row, index) {
					if(value) {
						value = $filter('date')(value, 'yyyy-MM-dd HH:mm');
						return value;
					} else {
						return "-";
					}
				}
			}, {
				title: '报备人',
				field: 'createName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '报备人部门',
				field: 'deptName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '城市',
				field: 'cityName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '业务类型',
				field: 'productName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '客户姓名',
				field: 'customerName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '借款金额 (万元)',
				field: 'loanAmount',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '借款期限(天)',
				field: 'borrowingDays',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '合作机构',
				field: 'cooperativeAgencyName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '渠道经理',
				field: 'channelManagerName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '受理员',
				field: 'acceptMemberName',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '订单状态',
				field: 'state',
				align: 'center',
				valign: 'bottom',
				formatter: function(value, row, index) {
					if(row.relationOrderNo != "") {
						return row.state;
					}
					return "-";
				}
			}, {
				title: '报备出款时间',
				field: 'estimateOutLoanTime',
				align: 'center',
				valign: 'bottom',
				sortable: true,
				formatter: function(value, row, index) {
					if(value) {
						value = $filter('date')(value, 'yyyy-MM-dd HH:mm');
						return value;
					} else {
						return "-";
					}
				}
			}, {
				title: '预计出款时间',
				field: 'financeOutLoanTime',
				align: 'center',
				valign: 'bottom',
				events: operateEvents,
				formatter: function(value, row, index) {
					var html = '-';
					if((null == value || "" == value)) {
						if($scope.haschkbbListA){
							html = '-<span class="editTime"><img src="/images/report_edit.png" alt=""/></span>';
						}else{
							html = '-';
						}
					} else if(null != value && "" != value) {
						value = $filter('date')(value, "yyyy/MM/dd HH:mm");
						if($scope.haschkbbListA){
							html = value + '&nbsp;<span class="editTime"><img src="/images/report_edit.png" alt="" class="editTime"/></span>';
						}else{
							html = value ;
						}
					}
					return html;
				}
			}, {
				title: '出款排队顺序',
				field: 'sort',
				align: 'center',
				valign: 'bottom',
				events: operateEvents
			}, {
				title: '扣款方式',
				field: 'paymentType',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '资方',
				field: 'fund',
				align: 'center',
				valign: 'bottom',
				events: operateEvents,
				formatter: function(value, row, index) {
					var html = '-';
					if((null == value || "" == value) && row.status != 1 && row.status != 3) {
						if($scope.haschkbbListA){
							html = '-<span class="editFund"><img src="/images/report_edit.png" alt=""/></span>';	
						}else{
							html = "-";
						}
					} else if(null != value && "" != value && row.status != 1 && row.status != 3) {
						if($scope.haschkbbListA){
							html = value + '&nbsp;<span class="editFund"><img src="/images/report_edit.png" alt="" class="editFund"/></span>';
						}else{
							html = value;
						}
					} else if(row.status == 1) {
						html = value;
					}
					return html;
				}
			}, {
				title: '资方审核状态',
				field: 'fundExamine',
				align: 'center',
				valign: 'bottom',
				events: operateEvents,
				formatter: function(value, row, index) {
					var html = '';
					if(!value && row.status == 2) {
						if($scope.haschkbbListA){
							html = '<span class="editFundExamine"><img src="/images/report_edit.png" alt=""/></span>';
						}
					} else if((value || "" != value) && row.status == 2) {
						if($scope.haschkbbListA){
							html = value + '<span class="editFundExamine"><img src="/images/report_edit.png" alt=""/></span>';
						}else{
							html = value;
						}
					}
					if('' == html) {
						html = '-';
					}
					return html;
				}
			}, {
				title: '放款状态',
				field: 'status',
				align: 'center',
				valign: 'bottom',
				formatter: function(value, row, index) {
					if(row.status == 1) {
						return "已放款";
					} else if(row.status == 2) {
						return "未放款";
					} else if(row.status == 3) {
						return "已撤销报备";
					}
					return "-";
				}
			}, {
				title: '受理员修改记录',
				field: 'reportEditRecordStr',
				align: 'center',
				valign: 'bottom'
			}, {
				title: '关联订单',
				align: 'center',
				valign: 'bottom',
				events: operateEvents,
				formatter: function(value, row, index) {
					if(row.relationOrderNo != "" && null != row.relationOrderNo) {
						return '<a class="relationOrderDetail" href="javascript:void(0)">查看</a>&nbsp;&nbsp;';
					}
					return "-";
				}
			}, {
				title: '操作',
				field: 'operate',
				align: 'center',
				valign: 'bottom',
				events: operateEvents,
				formatter: function(value, row, index) {
					var html = '';
					if($scope.haschkbbListA){
						if((row.relationOrderNo == "" || null == row.relationOrderNo) && row.status == 2) {
							html = '<a class="confirmLoan" href="javascript:void(0)">确认已放款</a>&nbsp;&nbsp;';
						}
						if(row.status == 2) {
							if(1 != row.top) {
								html += '<a class="replyRecord" href="javascript:void(0)">回复</a>&nbsp;&nbsp;<a class="top" href="javascript:void(0)">置顶</a>&nbsp;&nbsp;';
							} else {
								html += '<a class="replyRecord" href="javascript:void(0)">回复</a>&nbsp;&nbsp;置顶&nbsp;&nbsp;';
							}
						}
					}else{
						html = "-";
					}
					return html;
				}
			}, {
				title: '备注',
				field: 'remark',
				align: 'center',
				valign: 'bottom'
			}]
		}
	};
	$scope.$watch("page.cityCode", function(newValue, oldValue) {
		$scope.page.productCode = "";
		if(!$scope.page.cityCode) {
			$scope.productList = new Array();
			return false;
		}
		for(var i = 0; i < $scope.conditions.citys.length; i++) {
			if($scope.conditions.citys[i].cityCode == $scope.page.cityCode) {
				$scope.productList = $scope.conditions.citys[i].productList;
				return true;
			}
		}
	});
	$scope.$watch("page.productCode", function(newValue, oldValue) {
		$scope.page.state = "";
		if(!$scope.page.productCode) {
			$scope.stateList = new Array();
			return false;
		}
		for(var i = 0; i < $scope.productList.length; i++) {
			if($scope.productList[i].productCode == $scope.page.productCode) {
				$scope.stateList = $scope.productList[i].stateList;
				return true;
			}
		}
	});
	$scope.getAcceptList = function(cityCode) {
		//获取关联受理员列表
		$http({
			method: 'post',
			url: "/credit/user/user/v/searchByType2",
			data: {
				"name": "提单",
				"cityCode": cityCode
			}
		}).success(function(data) {
			if(data.data) {
				$scope.acceptList = data.data;
			}
		})
	}
	$scope.getAcceptList();

});