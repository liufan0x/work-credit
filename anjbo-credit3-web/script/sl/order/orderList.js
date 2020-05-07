angular.module("anjboApp", ['bsTable']).controller("orderListCtrl", function($scope, $compile, $cookies, $http, $state, $timeout, $filter, box, process, parent) {

	$scope.page = $cookies.getObject("pageParams");
	if(!$scope.page) {
		$scope.page = new Object();
		$scope.page.start = 0;
		$scope.page.pageSize = 15;
		$scope.page.type = "0";
		$scope.isAudit = false;
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
		$scope.productList = $scope.conditions.citys[0].productList;
		$scope.stateList = $scope.conditions.citys[0].productList[0].stateList;
	})

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

	$scope.showPlaceOrder = function() {
		$scope.orderDto = new Object();
		$scope.orderDto.acceptMemberUid = $scope.userDto.uid;
		var placeOrder = function() {
			if(!$scope.orderForm.$valid) {
				$scope.isAudit = true;
				alert("请正确填写所有信息");
				return;
			}
			if("1" != $scope.orderDto.isRelationOrder) {       //是否关联订单
				$scope.orderDto.relationOrderNo = "";
			} else if("1" == $scope.orderDto.isRelationOrder && (undefined == $scope.orderDto.relationOrderNo || $scope.orderDto.relationOrderNo.length < 16)) {
				alert("请正确填写所有信息");
				return;
			}
			box.waitAlert();
			//录单
			$http({
				method: 'POST',
				url: '/credit/order/borrow/v/save',
				data: $scope.orderDto
			}).success(function(data) {
				box.closeWaitAlert();
				if(data.code == "SUCCESS") {

					if($scope.orderDto.productCode == '04' && $scope.orderDto.auditSort == 1) {        //产品为房抵贷时，先审批后面签
						$state.go("orderCommonEdit", {
							'orderNo': data.data,
							'cityCode': $scope.orderDto.cityCode,
							'productCode': $scope.orderDto.productCode,
							'processId': 'placeOrder'
						});
					} else if($scope.orderDto.auditSort == 1 && $scope.orderDto.productCode != '04') {  //产品不是房抵贷时，先审批后面签
						$state.go("orderEdit.placeOrderEdit", {
							'orderNo': data.data,
							'cityCode': $scope.orderDto.cityCode,
							'productCode': $scope.orderDto.productCode,
							'relationOrderNo': $scope.orderDto.relationOrderNo
						});
					} else if($scope.orderDto.auditSort == 2 && $scope.orderDto.productCode == '04') { //产品为房抵贷时，先面签在审批
						$state.go("orderEdit.facesignEdit", {
							'orderNo': data.data,
							'cityCode': $scope.orderDto.cityCode,
							'productCode': $scope.orderDto.productCode,
							'relationOrderNo': $scope.orderDto.relationOrderNo
						});
					}else if($scope.orderDto.productCode == "06"||$scope.orderDto.productCode== "07") {
						$state.go("orderCommonEdit", {
							'orderNo': data.data,
							'cityCode': $scope.orderDto.cityCode,
							'productCode': $scope.orderDto.productCode,
							'processId': "placeOrder"
						});
					}else {
						$state.go("orderEdit.notarizationEdit", {
							'orderNo': data.data,
							'cityCode': $scope.orderDto.cityCode,
							'productCode': $scope.orderDto.productCode,
							'relationOrderNo': $scope.orderDto.relationOrderNo
						});
					}
					box.closeAlert();
				} else if(data.code == "FAIL") {
					alert(data.msg);
				}
			}).error(function() {
				box.closeWaitAlert();
			})
		}

		$scope.$watch("orderDto.cityCode", function(newValue, oldValue) {
			$scope.orderDto.productCode = "";
			if(!$scope.orderDto.cityCode) {
				$scope.orderProductList = new Array();
				return false;
			}
			for(var i = 0; i < $scope.conditions.citys.length; i++) {
				if($scope.conditions.citys[i].cityCode == $scope.orderDto.cityCode) {
					$scope.orderProductList = $scope.conditions.citys[i].productList;
					angular.forEach($scope.orderProductList, function(data, index) {
						if('10000' == data.productCode) {
							$scope.orderProductList.splice(index, 1);
						}
					});
					angular.element("#productCode").html($compile('<option ng-repeat="list in orderProductList" value="{{list.productCode}}">{{list.productName}}</option>')($scope));
					return true;
				}
			}
		});
		$scope.$watch("orderDto.productCode", function(newValue, oldValue) {
			if("03" != newValue) {
				$scope.orderDto.isRelationOrder = '0';
				$scope.relationOrderNoList = null;
			}
			if("04" == newValue) {
				//默认选中
				$scope.orderDto.auditSort="1";
			}
		});
		$scope.$watch("orderDto.isRelationOrder", function(newValue, oldValue) {
			if(1 == newValue && undefined != $scope.orderDto.borrowerName && $scope.orderDto.borrowerName.length > 0) {
				loadRelationOrders($scope.orderDto.borrowerName);
			}
		});
		$scope.$watch("orderDto.borrowerName", function(newValue, oldValue) {
			if("1" != $scope.orderDto.isRelationOrder) {
				return;
			}
			$scope.orderDto.relationOrderNo = "";
			if(!newValue) {
				$scope.relationOrderNoList = null;
				return;
			}
			loadRelationOrders(newValue);
		});
		$scope.$watch("orderDto.relationOrderNo", function(newValue, oldValue) {
			console.debug(newValue + "-relationOrderNo-" + oldValue);
			if(!newValue) {
				return;
			}
			angular.forEach($scope.relationOrderNoList, function(data, index) {
				if(newValue == data.orderNo) {
					$scope.orderDto.notarialUid = data.notarialUid;
					$scope.placeOrderNotarialName = data.notarialName;
					$scope.orderDto.facesignUid = data.facesignUid;
					$scope.orderDto.elementUid = data.elementUid;
					return;
				}
			});
		});

		angular.element("#cityId").html('<option ng-repeat="list in orderCitys" value="{{list.cityCode}}">{{list.cityName}}</option>');
		angular.element("#productCode").html('<option ng-repeat="list in orderProductList" value="{{list.productCode}}">{{list.productName}}</option>');
		angular.element("#relationOrderNo").html('<option ng-repeat="list in relationOrderNoList" value="{{list.id}}">{{list.name}}</option>');
		angular.element("#notarialUid").html('<select class="form-control" choice-personnel="公证" city-code="{{orderDto.cityCode}}" product-code="{{orderDto.productCode}}" name="notarialUid"  ng-model="orderDto.notarialUid" required><option value="">请选择</option></select><span class="inputError" ng-show="isAudit"><error class="text-danger" ng-show="orderForm.notarialUid.$invalid">必填</error></span>');
		angular.element("#facesignUid").html('<select class="form-control" choice-personnel="面签"  city-code="{{orderDto.cityCode}}" product-code="{{orderDto.productCode}}" name="facesignUid"   ng-model="orderDto.facesignUid" required><option value="">请选择</option></select><span class="inputError" ng-show="isAudit"><error class="text-danger" ng-show="orderForm.facesignUid.$invalid">必填</error></span>');
		angular.element("#elementUid").html('<select class="form-control" choice-personnel="要件校验" agency-id="1" city-code="{{orderDto.cityCode}}" product-code="{{orderDto.productCode}}" name="elementUid"  ng-model="orderDto.elementUid" required><option value="">请选择</option></select><span class="inputError" ng-show="isAudit"><error class="text-danger" ng-show="orderForm.elementUid.$invalid">必填</error></span>');
		angular.element("#acceptMemberUid").html('<select class="form-control" choice-personnel="提单" city-code="{{orderDto.cityCode}}" product-code="{{orderDto.productCode}}" name="acceptMemberUid"  ng-model="orderDto.acceptMemberUid" required><option value="">请选择</option></select><span class="inputError" ng-show="isAudit"><error class="text-danger" ng-show="orderForm.acceptMemberUid.$invalid">必填</error></span>');
		//angular.element("#auditSort").html('<select class="form-control"><option value="">请选择</option><option value="1">先审批再面签</option><option value="2">先面签再审批</option></select><span class="inputError" ng-show="isAudit"><error class="text-danger" ng-show="orderForm.auditSort.$invalid">必填</error></span>');
		box.editAlertW500($scope, "提单", $("#placeOrderId").html(), placeOrder);
		$timeout(function() {
			$(".tooltip-toggle").tooltip({
				html: true
			});
		})
	}
	// 关联订单
	function loadRelationOrders(customerName) {
		$http({
			method: 'POST',
			url: '/credit/order/base/v/selectAbleRelationOrder',
			data: {
				'cityCode': $scope.orderDto.cityCode,
				'borrowerName': customerName
			}
		}).success(function(data) {
			if(data.code == "SUCCESS") {
				$scope.relationOrderNoList = data.data;
				if(null != $scope.relationOrderNoList && $scope.relationOrderNoList.length > 0) {
					$scope.orderDto.relationOrderNo = $scope.relationOrderNoList[0].orderNo;
				}
			}
		}).error(function() {
			box.closeWaitAlert();
		});
	}

	$scope.$watch("page.cityCode", function(newValue, oldValue) {
		if(undefined != $scope.conditions) {
			var fl = true;
			for(var i = 0; i < $scope.conditions.citys.length; i++) {
				if($scope.conditions.citys[i].cityCode == $scope.page.cityCode) {
					$scope.productList = $scope.conditions.citys[i].productList;
					angular.forEach($scope.productList, function(data, index) {
						if('10000' == data.productCode) {
							$scope.productList.splice(index, 1);
						}
						if($scope.page.productCode == data.productCode) {
							fl = false;
						}
					});
					return true;
				}
			}
			if(fl) {
				$scope.page.productCode = "";
			}
		}
	});

	$scope.$watch("page.productCode", function(newValue, oldValue) {

		if(undefined != $scope.productList) {
			var fl = true;
			for(var i = 0; i < $scope.productList.length; i++) {
				if($scope.productList[i].productCode == $scope.page.productCode) {
					$scope.stateList = $scope.productList[i].stateList;
					angular.forEach($scope.stateList, function(data, index) {
						if($scope.page.state == data.stateName) {
							fl = false;
						}
					});
					return true;
				}
			}
			if(fl) {
				$scope.page.state = "";
			}
		}
	});

	function getParams(data) {
		$scope.page.start = data.offset;
		$scope.page.pageSize = data.limit;
		$scope.page.sortName = data.sort == 'distancePaymentDay' ? 'planPaymentTime' : data.sort;
		$scope.page.sortOrder = data.order;
		$cookies.putObject("pageParams", $scope.page);
		return $scope.page;
	}

	function relationFormatter(value, row, index) {
		var html = "";
		if(row.relationOrderNo) {
			html += '<a class="lookRelationOrder" href="javascript:void(0)">查看</a>&nbsp;&nbsp;';
		} else {
			html = "--";
		}
		return html;
	}

	function operateFormatter(value, row, index) {
		if(row.processId == 'assignAcceptMember') {
			return "";
		}
		var html = '';

		if(row.previousHandlerUid == parent.userDto.uid && row.processId != 'isBackExpenses' && row.processId != 'financialAudit' &&
			row.processId != 'placeOrder' && row.processId != 'forensics' &&
			row.processId != 'cancellation' && row.processId != 'transfer' && row.processId != 'newlicense' &&
			row.processId != 'receivableFor' && row.processId != 'receivableForFirst' && row.processId != 'receivableForEnd' &&
			row.processId != 'wanjie' && row.processId != 'closeOrder' && !(row.state.indexOf('退回') > 0) &&
			!(row.processId == 'mortgage' && row.productCode == '03') && row.processId != 'fddMortgageRelease' && !(row.processId == 'fddMortgageRelease' && row.productCode == '04') &&
			!(row.processId == 'release' && row.productCode == '04') && !(row.processId == 'rebate' && row.productCode == '04') && !(row.processId == 'wanjie' && row.productCode == '04') &&
			!(row.processId == 'fddForensics' && row.productCode == '04') && !(row.processId == 'fddMortgageStorage' && row.productCode == '04') && !(row.processId == 'isCharge' && row.productCode == '04')) {
			html += '<a class="withdrawOrder" href="javascript:void(0)" >撤回</a>&nbsp;&nbsp;';
		}
		//        if((row.processId == 'placeOrder') && row.productCode != '03' && !(row.state.indexOf('退回')>0) ){
		//	        if(row.notarialUid == parent.userDto.uid){
		//	            html += '<a class="notarization" href="javascript:void(0)" >公证</a>&nbsp;&nbsp;';
		//	        }
		//	        if(row.facesignUid == parent.userDto.uid){
		//	            html += '<a class="facesign" href="javascript:void(0)" >面签</a>&nbsp;&nbsp;';
		//	        }
		//        }

		if(parent.userDto.uid == '123456' || parent.userDto.uid == '1568629191509' || parent.userDto.uid == '1551241216016'   ||  parent.userDto.uid == '1551837580094' || parent.userDto.uid == '1494408235701' || parent.userDto.uid == '1470971683342' || parent.userDto.uid == '1470743791422' || parent.userDto.uid == '1506320435280' || parent.userDto.uid == '1508747262735' || parent.userDto.uid == '1524543617134' || parent.userDto.uid == '1515759394387') {
			html += '<a class="handleOrder1" href="javascript:void(0)">资料推送</a>&nbsp;&nbsp;';
		}

		//      if(parent.userDto.uid == '1508747262735' ){
		//      	html += '<a class="handleOrder2" href="javascript:void(0)">回款</a>&nbsp;&nbsp;';
		//      }

		if(row.currentHandlerUid != parent.userDto.uid) {
			if(row.agencgId == 1 && row.channelManagerUid == parent.userDto.uid && row.processId == 'placeOrder' && !(row.state.indexOf('退回') > 0)) {
				html += '<a class="perfectOrder" href="javascript:void(0)" >完善订单</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="lookOrder" href="javascript:void(0)">查看详情</a>&nbsp;&nbsp;';
			}

		} else if(row.processId == 'placeOrder') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新提交</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="perfectOrder" href="javascript:void(0)" >完善订单</a>&nbsp;&nbsp;';
			}
			html += '<a class="closeOrder text-danger" href="javascript:void(0)" >删除</a>&nbsp;&nbsp;';
		} else if(row.processId == 'managerAudit') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新分配订单</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">分配订单</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'auditFirst') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新风控初审</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">风控初审</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'auditReview') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新复核审批</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">复核审批</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'auditFinal') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新风控终审</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">风控终审</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'auditOfficer') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新首席风险官审批</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">首席风险官审批</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'auditJustice') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新法务审批</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">法务审批</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'signInsurancePolicy') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新签订投保单</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">签订投保单</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'allocationFund') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新推送金融机构</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">推送金融机构</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'notarization') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新公证</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">公证</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'facesign'&&row.sgt!=true) {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新面签</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">面签</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'dataAudit') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新资料审核</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">资料审核</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'fundDocking') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新资料推送</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">资料推送</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'fundAduit') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新资金审批</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">资金审批</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'repaymentMember') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新指派还款专员</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">指派还款专员</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'element') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新要件校验</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">要件校验</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'applyLoan') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新申请放款</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">申请放款</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'isLendingHarvest') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新收利息</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">收利息</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'lendingHarvest') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新核实利息</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">核实利息</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'uploadInsurancePolicy') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新上传电子保单</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">上传电子保单</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'charge') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新收费</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">收费</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'isCharge') {
			if(row.state.indexOf('退回') > 0) {
				html += '<a class="handleOrder" href="javascript:void(0)">重新核实收费</a>&nbsp;&nbsp;';
			} else {
				html += '<a class="handleOrder" href="javascript:void(0)">核实收费</a>&nbsp;&nbsp;';
			}
		} else if(row.processId == 'isBackExpenses') {
			html += '<a class="handleOrder" href="javascript:void(0)">扣回后置费用</a>&nbsp;&nbsp;';
		} else if(row.processId == 'backExpenses') {
			html += '<a class="handleOrder" href="javascript:void(0)">核实后置费用</a>&nbsp;&nbsp;';
		} else if(row.processId == 'lendingPay') {
			html += '<a class="handleOrder" href="javascript:void(0)">付利息</a>&nbsp;&nbsp;';
		} else if(row.processId == 'lendingInstructions') {
			html += '<a class="handleOrder" href="javascript:void(0)">发放款指令</a>&nbsp;&nbsp;';
		} else if(row.processId == 'lending') {
			html += '<a class="handleOrder" href="javascript:void(0)">放款</a>&nbsp;&nbsp;';
		} else if(row.processId == 'financialStatement') {
			html += '<a class="handleOrder" href="javascript:void(0)">财务制单</a>&nbsp;&nbsp;';
		} else if(row.processId == 'receivableFor') {
			html += '<a class="handleOrder" href="javascript:void(0)">回款</a>&nbsp;&nbsp;';
		} else if(row.processId == 'receivableForFirst') {
			html += '<a class="handleOrder" href="javascript:void(0)">回款</a>&nbsp;&nbsp;';
		} else if(row.processId == 'receivableForEnd') {
			html += '<a class="handleOrder" href="javascript:void(0)">回款</a>&nbsp;&nbsp;';
		} else if(row.processId == 'elementReturn') {
			html += '<a class="handleOrder" href="javascript:void(0)">要件退还</a>&nbsp;&nbsp;';
		} else if(row.processId == 'pay') {
			html += '<a class="handleOrder" href="javascript:void(0)">收罚息</a>&nbsp;&nbsp;';
		} else if(row.processId == 'rebate') {
			html += '<a class="handleOrder" href="javascript:void(0)">返佣</a>&nbsp;&nbsp;';
		} else if(row.processId == 'fddMortgageStorage') {
			html += '<a class="handleOrder" href="javascript:void(0)">抵押品入库</a>&nbsp;&nbsp;';
		} else {
			html += '<a class="lookOrder" href="javascript:void(0)">查看详情</a>&nbsp;&nbsp;';
		}
		if(null != row.isUp) {
			html += '<a class="orderDown" href="javascript:void(0)">取消置顶</a>';
		} else {
			html += '<a class="orderUp" href="javascript:void(0)">置顶</a>';
		}

		return html;
	}

	window.relationEvents = {
		'click .lookRelationOrder': function(e, value, row, index) {
			$state.go("orderDetail", {
				'orderNo': row.relationOrderNo,
				'processId': row.processId,
				'productCode': '01',
				'relationOrderNo': '0'
			});
		}
	};
	window.operateEvents = {
		'click .freeFace': function(e, value, row, index) {
			$http({
				method: 'POST',
				url: '/credit/order/base/v/updateIsFace',
				data: {
					"orderNo": row.orderNo,
					"isFace": "2"
				}
			}).success(function(data) {
				if(data.code == "SUCCESS") {
					box.boxAlert(data.msg);
				}
			})
		},
		'click .closeOrder': function(e, value, row, index) {
			var closeOrder = function() {
				$http({
					method: 'POST',
					url: '/credit/order/base/v/closeOrder',
					data: {
						"orderNo": row.orderNo
					}
				}).success(function(data) {
					if(data.code == "SUCCESS") {
						$scope.query();
						box.boxAlert(data.msg);
					}
				})
			}
			box.confirmAlert("删除订单", "确定要删除订单？", closeOrder);
		},
		'click .withdrawOrder': function(e, value, row, index) {
			var withdrawOrder = function() {
				$http({
					method: 'POST',
					url: '/credit/order/flow/v/withdrawOrder',
					data: row
				}).success(function(data) {
					if(data.code == "SUCCESS") {
						$scope.query();
						box.boxAlert(data.msg);
					}
				})
			}
			box.confirmAlert("撤回订单", "确定要撤回订单？", withdrawOrder);
		},
		'click .facesign': function(e, value, row, index) {
			$state.go("orderEdit.facesignEdit", {
				'orderNo': row.orderNo
			});
		},
		'click .notarization': function(e, value, row, index) {
			$state.go("orderEdit.notarizationEdit", {
				'orderNo': row.orderNo
			});
		},
		'click .perfectOrder': function(e, value, row, index) {
			if(row.productCode == "04"||row.productCode == "06"||row.productCode == "07") {
				$state.go("orderCommonEdit", {
					'orderNo': row.orderNo,
					'cityCode': row.cityCode,
					'productCode': row.productCode,
					'processId': row.processId
				});
			} else {
				$state.go("orderEdit.placeOrderEdit", {
					'orderNo': row.orderNo,
					cityCode: row.cityCode,
					productCode: row.productCode,
					relationOrderNo: row.relationOrderNo
				});
			}
		},
		'click .handleOrder': function(e, value, row, index) {
			if((row.productCode == "04"||row.productCode == "06"||row.productCode == "07") && row.processId == 'placeOrder') {
				$state.go("orderCommonEdit", {
					'orderNo': row.orderNo,
					'cityCode': row.cityCode,
					'productCode': row.productCode,
					'processId': row.processId
				});
			} else {
				$state.go("orderEdit." + row.processId + "Edit", {
					'orderNo': row.orderNo,
					"cityCode": row.cityCode,
					"productCode": row.productCode,
					relationOrderNo: row.relationOrderNo
				});
			}

		},
		'click .lookOrder': function(e, value, row, index) {
			$state.go("orderDetail", {
				'orderNo': row.orderNo,
				"cityCode": row.cityCode,
				'processId': row.processId,
				'productCode': row.productCode,
				relationOrderNo: row.relationOrderNo
			});
		},
		'click .handleOrder1': function(e, value, row, index) {
			$state.go("orderEdit.fundDockingEdit", {
				'orderNo': row.orderNo,
				"cityCode": row.cityCode,
				"productCode": row.productCode,
				relationOrderNo: row.relationOrderNo
			});
		},
		'click .handleOrder2': function(e, value, row, index) {
			$state.go("orderEdit.receivableForEdit", {
				'orderNo': row.orderNo,
				"cityCode": row.cityCode,
				"productCode": row.productCode,
				relationOrderNo: row.relationOrderNo
			});
		},
		'click .orderUp': function(e, value, row, index) {
			$http({
				method: 'POST',
				url: '/credit/order/orderUp/v/edit',
				data: {
					"orderNo": row.orderNo
				}
			}).success(function(data) {
				box.boxAlert(data.msg);
				if(data.code == "SUCCESS") {
					$scope.query();
				}
			});
		},
		'click .orderDown': function(e, value, row, index) {
			$http({
				method: 'POST',
				url: '/credit/order/orderUp/v/delete',
				data: {
					"orderNo": row.orderNo
				}
			}).success(function(data) {
				box.boxAlert(data.msg);
				if(data.code == "SUCCESS") {
					$scope.query();
				}
			});
		}
	};

	$scope.query = function() {
		$("#table").bootstrapTable('refresh', {
			url: '/credit/order/base/v/list',
			pageNumber: 1
		});
	}

	var columnSwitchList = $cookies.getObject("columnSwitch");
	if(!columnSwitchList) {
		columnSwitchList = {
			"id": false,
			"createTime": false,
			"contractNo": false,
			"cityName": true,
			"productName": true,
			"customerName": true,
			"borrowingAmount": true,
			"borrowingDay": true,
			"cooperativeAgencyName": true,
			"channelManagerName": true,
			"acceptMemberName": true,
			"planPaymentTime": false,
			"distancePaymentDay": true,
			"previousHandler": false,
			"previousHandleTime": false,
			"state": true,
			"source": false,
			"currentHandler": true,
			"relation": true,
			"operate": true
		};
		$cookies.putObject("columnSwitch", columnSwitchList);
	}

	$scope.orderList = {
		options: {
			method: "post",
			url: "/credit/order/base/v/list",
			queryParams: getParams,
			sidePagination: 'server',
			undefinedText: "-",
			cache: false,
			striped: true,
			pagination: true,
			pageNumber: ($scope.page.start / $scope.page.pageSize) + 1,
			pageSize: $scope.page.pageSize,
			pageList: ['All'],
			showColumns: true,
			showRefresh: false,
			onClickRow: function(row, $element, field) {
				$element.toggleClass("bule-bg");
			},
			onColumnSwitch: function(field, checked) {
				columnSwitchList[field] = checked;
				$cookies.putObject("columnSwitch", columnSwitchList);
			},
			columns: [{
				title: '序号',
				field: 'id',
				align: 'right',
				valign: 'bottom',
				visible: columnSwitchList.id
			}, {
				title: '提单时间',
				field: 'createTime',
				align: 'center',
				valign: 'bottom',
				visible: columnSwitchList.createTime,
				sortable: true,
				formatter: function(value, row, index) {
					return $filter('date')(value, 'yyyy-MM-dd HH:mm:ss');
				}
			}, {
				title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="根据城市代码、实际放款时间、业务类型代号自动生成的合同编号">合同编号 <span class="help"></span></span>',
				field: 'contractNo',
				align: 'center',
				valign: 'bottom',
				visible: columnSwitchList.contractNo
			}, {
				title: '城市',
				field: 'cityName',
				align: 'center',
				valign: 'bottom',
				visible: columnSwitchList.cityName
			}, {
				title: '产品名称',
				field: 'productName',
				align: 'center',
				valign: 'bottom',
				visible: columnSwitchList.productName
			}, {
				title: '客户类型',
				field: 'customerType',
				align: 'center',
				valign: 'bottom',
				formatter: function(value, row, index) {
					switch(value) {
						case 1:
							return '个人';
						case 2:
							return '小微企业';
						default:
							return '-';
					}
				},
				visible: columnSwitchList.customerType
			}, {
				title: '客户姓名',
				field: 'customerName',
				align: 'center',
				valign: 'bottom',
				visible: columnSwitchList.customerName
			}, {
				title: '借款金额（万元）',
				field: 'borrowingAmount',
				align: 'center',
				valign: 'bottom',
				sortable: true,
				visible: columnSwitchList.borrowingAmount
			}, {
				title: '借款期限（天）',
				field: 'borrowingDay',
				align: 'center',
				valign: 'bottom',
				sortable: true,
				formatter: function(value, row, index) {
					if(row.productCode == "04") {
						return value + "（期）"
					}
					return value;
				},
				visible: columnSwitchList.borrowingDay
			}, {
				title: '合作机构',
				field: 'cooperativeAgencyName',
				align: 'center',
				valign: 'bottom',
				visible: columnSwitchList.cooperativeAgencyName
			}, {
				title: '渠道经理',
				field: 'channelManagerName',
				align: 'center',
				valign: 'bottom',
				visible: columnSwitchList.channelManagerName
			}, {
				title: '受理员',
				field: 'acceptMemberName',
				align: 'center',
				valign: 'bottom',
				visible: columnSwitchList.acceptMemberName
			}, {
				title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="放款时间+借款期限（天数）">预计回款时间 <span class="help"></span></span>',
				field: 'planPaymentTime',
				align: 'center',
				valign: 'bottom',
				sortable: true,
				formatter: function(value, row, index) {
					return $filter('limitTo')(row.planPaymentTime, 10);
				},
				visible: columnSwitchList.planPaymentTime
			}, {
				title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="预计回款时间-当前日期">距离回款天数（天） <span class="help"></span></span>',
				field: 'distancePaymentDay',
				align: 'center',
				valign: 'bottom',
				sortable: true,
				visible: columnSwitchList.distancePaymentDay,
				formatter: function(value, row, index) {
					if(row.processId == 'wanjie' || row.processId == 'pay' || row.processId == 'elementReturn') {
						return "已回款";
					} else {
						return row.distancePaymentDay;
					}
				}
			}, {
				title: '预计出款时间',
				field: 'financeOutLoanTime',
				align: 'center',
				valign: 'bottom',
				sortable: true,
				formatter: function(value, row, index) {
					return $filter('limitTo')(row.financeOutLoanTimeStr, 19);
				},
				visible: columnSwitchList.financeOutLoanTimeStr
			}, {
				title: '上一节点处理人',
				field: 'previousHandler',
				align: 'center',
				valign: 'bottom',
				visible: columnSwitchList.previousHandler
			}, {
				title: '上一节点处理时间',
				field: 'previousHandleTime',
				align: 'center',
				valign: 'bottom',
				sortable: true,
				formatter: function(value, row, index) {
					return $filter('limitTo')(row.previousHandleTime, 19);
				},
				visible: columnSwitchList.previousHandleTime
			}, {
				title: '订单状态',
				field: 'state',
				align: 'center',
				valign: 'bottom',
				visible: columnSwitchList.state
			}, {
				title: '订单来源',
				field: 'source',
				align: 'center',
				valign: 'bottom',
				visible: columnSwitchList.source
			}, {
				title: '待处理人',
				field: 'currentHandler',
				align: 'center',
				valign: 'bottom',
				visible: columnSwitchList.currentHandler
			}, {
				title: '<span class="tooltip-toggle" data-toggle="tooltip-toggle" title="" data-original-title="点击查看关联订单">关联订单 <span class="help"></span></span>',
				field: 'relation',
				align: 'center',
				valign: 'bottom',
				events: relationEvents,
				formatter: relationFormatter,
				visible: columnSwitchList.relation
			}, {
				title: '操作',
				field: 'operate',
				align: 'center',
				valign: 'bottom',
				events: operateEvents,
				formatter: operateFormatter,
				visible: columnSwitchList.operate
			}]
		}
	};

	$timeout(function() {
		$(".tooltip-toggle").tooltip({
			html: true
		});
	})

});