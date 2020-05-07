angular.module("anjboApp").controller("financialStatementEditCtrl", function($scope, $http, $compile, $state, $filter, box, route) {

	if('04'!=route.getParams().productCode){
		$scope.showDetail("applyLoan");
	}
	$scope.obj1 = new Object();
	$scope.obj1.orderNo = route.getParams().orderNo;
	$scope.queryTrustAccount = function(type) {
		$http({
			method: 'POST',
			url: "/credit/third/api/yntrust/v/queryTrustAccount",
			data: {
				"orderNo": $scope.obj1.orderNo,
				'ynProductCode': "I16800"
			}
		}).success(function(data) {
			if(data.code == "SUCCESS") {
				if(type == 1) {
					box.boxAlert("刷新成功");
				}
				$scope.balance = data.data.Balance;
			}
		})

		$http({
			method: 'POST',
			url: "/credit/third/api/yntrust/v/queryTrustAccount",
			data: {
				"orderNo": $scope.obj1.orderNo,
				'ynProductCode': "I22500"
			}
		}).success(function(data) {
			if(data.code == "SUCCESS") {
				if(type == 1) {
					box.boxAlert("刷新成功");
				}
				$scope.secondBalance = data.data.Balance;
			}
		})
	}
	
	$scope.sgtAccoumt = function(type) {
		$http({
			url: '/credit/third/api/sgtongBorrowerInformation/v/text',
			method: 'POST',
			data: {
				'brNo': '0004',
				'cardChno': 'CL0005',
				'acNo':'000003_03_00040004'
			}                                                              
		}).success(function(data) {
			$scope.sgtAccoumts = new Object();
			if("SUCCESS" == data.code) {
				if(type) {
				box.boxAlert("刷新成功");
				}
				$scope.sgtAccoumts = data.data;
			}
		});
	}

	$scope.queryTrustAccount();
	$scope.sgtAccoumt();

	$scope.lendingSubmit = function() {
		$scope.obj1.uid = $scope.sumbitDto.uid;
		$http({
			method: 'POST',
			url: "/credit/finance/statement/v/add",
			data: $scope.obj1
		}).success(function(data) {
			box.closeAlert();
			box.boxAlert(data.msg, function() {
				if(data.code == "SUCCESS") {
					box.closeAlert();
					$state.go("orderList");
				}
			});
		})
	}

	$scope.submit = function() {
		if($scope.order.productCode!='04'){
			$http({
				method: 'POST',
				url: "/credit/third/api/yntrust/v/getContractFile",
				data: {
					orderNo: $scope.obj1.orderNo
				}
			}).success(function(data) {
				if(data.msg == "合同文件不存在") {
					data.msg = "请先让受理员签署合同";
				}
				
				if(data.Status){
					if(data.Status.ResponseMessage){
						if(data.Status.ResponseMessage == "合同文件不存在"){
							data.msg = "请先让受理员签署合同";
							data.code == 'FAIL'
						}
					}
				}
				
				if(data.code == 'SUCCESS') {
					$scope.personnelType = "财务审核";
					box.editAlert($scope, "确定提交放款信息吗？请选择财务审核员。", "<submit-box></submit-box>", function() {
						$scope.lendingSubmit();
					});
				} else {
					if(data.msg == "没有获取到与该订单号关联的uniqueId的信息") {
						box.boxAlert("该订单已撤回，请退回重新资料推送");
					} else {
						box.boxAlert(data.msg);
					}
				}

			});
		}else{
			$scope.personnelType = "财务审核";
			box.editAlert($scope, "请选择财务审核员。", "<submit-box></submit-box>", function() {
				$scope.lendingSubmit();
			});
		}
	}

	$scope.showBack = function() {
		box.editAlert($scope, "订单退回，请选择退回对象。", "<back-box></back-box>", function() {
			$scope.backOrder();
		});
	}
	
	//借款信息
	$http({
		method: 'POST',
		url: 'credit/order/borrow/v/query',
		data: {"orderNo": $scope.obj1.orderNo}
	}).success(function(data) {
		$scope.order = data.data;
	})
	
	//申请放款信息
	$scope.applyLoanInfo = new Object();
	$http({
		method: 'POST',
		url: '/credit/order/applyLoan/v/processDetails',
		data: {"orderNo": $scope.obj1.orderNo}
	}).success(function(data) {
		if("SUCCESS" == data.code) {
			$scope.applyLoanInfo = data.data.loanDto;
			$scope.productCode = route.getParams().productCode;
			if($scope.applyLoanInfo != null) {
				$scope.applyLoanInfo.imgs = new Array();
				var img = $scope.applyLoanInfo.chargesReceivedImg;
				if(img != '' && img != null) {
					$scope.applyLoanInfo.imgs = img.split(",");
				}
				$scope.applyLoanInfo.ckimgs = new Array();
				var ckimgs = $scope.applyLoanInfo.payAccountImg;
				if(ckimgs != '' && ckimgs != null) {
					$scope.applyLoanInfo.ckimgs = ckimgs.split(",");
				}
				$scope.applyLoanInfo.mimg = new Array();
				var mimg = $scope.applyLoanInfo.mortgageImg;
				if(mimg != '' && mimg != null) {
					$scope.applyLoanInfo.mimg = mimg.split(",");
				}
			}
			$scope.logList = data.data.logDtos;
		} else {
			alert(data.msg);
		}
	})
	
	$http({//获取合同信息
		method: 'POST',
		url: '/credit/third/api/sgtongContractInformation/v/search',
		data: {
			'orderNo': $scope.obj1.orderNo
		}
	}).success(function(data) {
		if(data.code=="SUCCESS"){
			 if(data.data.length>0){
				$scope.contractInfo=data.data[0];
				for (item in $scope.contractInfo) {
					$scope.contractInfo[item]=$scope.contractInfo[item]+"";
				}
			 }
		}
	})
	
	$scope.idTypeA=[
						{id:"0",name:'身份证'},
						{id:"1",name:'户口簿'},
						{id:"2",name:'护照'},
						{id:"3",name:'军官证'},
						{id:"4",name:'士兵证'},
						{id:"5",name:'港澳居民来往内地通行证'},
						{id:"6",name:'台湾同胞来往内地通行证'},
						{id:"7",name:'临时身份证'},
						{id:"8",name:'外国人居留证'},
						{id:"9",name:'警官证'},
						{id:"A",name:'香港身份证'},
						{id:"B",name:'澳门身份证'},
						{id:"C",name:'台湾身份证'},
						{id:"X",name:'其他证件'}
					];
	
	$http({//获取借款人信息
		method: 'POST',
		url: '/credit/third/api/sgtongBorrowerInformation/v/search',
		data: {
			'orderNo': $scope.obj1.orderNo
		}
	}).success(function(data) {
		 if(data.code=="SUCCESS"){
			 if(data.data.length>0){
				$scope.borrowInfo=data.data[0];
				for (item in $scope.borrowInfo) {
					$scope.borrowInfo[item]=$scope.borrowInfo[item]+"";
				}
			 
			 }
		 }
	})

});