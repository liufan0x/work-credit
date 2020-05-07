angular.module("anjboApp").controller("placeElementEditCtrl", function($scope, $timeout, $rootScope, $http, $state, process, $compile, route, box) {

	var params = {
		"orderNo": route.getParams().orderNo
	};

	$scope.addCangDai = route.getParams().addCangDai;
	$scope.all = false;
	if($scope.addCangDai != 0 && $scope.addCangDai) {
		$("#elementForm").find("input").prop("disabled", "disabled");
		$("#elementForm").find("textarea").prop("disabled", "disabled");
		$("#elementForm").find("select").prop("disabled", true);
		$scope.all = true;
	}

	//是否畅贷
	$http({
		method: 'POST',
		url: 'credit/order/borrow/v/isChangLoan',
		data: params
	}).success(function(data) {
		$scope.data = data.data;
		if($scope.data.isChangLoan == 1) {
			$("#elementForm").find("input").prop("disabled", "disabled");
			$("#elementForm").find("textarea").prop("disabled", "disabled");
			$("#elementForm").find("select").prop("disabled", true);
			$scope.all = true;
		}
	})

	//要件信息
	$http({
		method: 'POST',
		url: '/credit/element/basics/v/detail',
		data: params
	}).success(function(data) {
		$scope.foreclosure = data.data;
		if($scope.foreclosure) {
			$scope.foreclosureType = $scope.foreclosure.foreclosureType;
			$scope.paymentType = $scope.foreclosure.paymentType;
			if($scope.foreclosureType.bankNameId) {
				$scope.foreclosureType.bankNameId = String($scope.foreclosureType.bankNameId);
			}
			if($scope.foreclosureType.bankSubNameId) {
				$scope.foreclosureType.bankSubNameId = String($scope.foreclosureType.bankSubNameId);
			}
			if($scope.paymentType.paymentBankNameId) {
				$scope.paymentType.paymentBankNameId = String($scope.paymentType.paymentBankNameId);
			}
			if($scope.paymentType.paymentBankSubNameId) {
				$scope.paymentType.paymentBankSubNameId = String($scope.paymentType.paymentBankSubNameId);
			}
		} else {
			$scope.foreclosure = new Object();
			$scope.foreclosureType = new Object();
			$scope.paymentType = new Object();
			//初始化值
			$scope.paymentType.paymentMode = "网银转账";
			$scope.paymentType.paymentaccountType = "个人";
			$scope.foreclosureType.foreclosureType = "银行自动扣款";
			$scope.foreclosureType.accountType = "个人";
		}
	})

	//更新要件信息
	$scope.updateElement = function(isShow) {
		$scope.foreclosure = new Object();
		if(!$scope.foreclosureType) {
			$scope.foreclosureType = new Object();
			$scope.foreclosureType.bankNameId = 0;
			$scope.foreclosureType.bankSubNameId = 0;
		}
		if(!$scope.paymentType) {
			$scope.paymentType = new Object();
			$scope.paymentType.paymentBankNameId = 0;
			$scope.paymentType.paymentBankSubNameId = 0;
		}
		$scope.foreclosure.orderNo = params.orderNo;
		$scope.foreclosure.foreclosureType = $scope.foreclosureType;
		$scope.foreclosure.paymentType = $scope.paymentType;
		$scope.foreclosure.remark = $scope.foreclosureType.remark;
		$http({
			method: 'POST',
			url: "/credit/element/basics/v/update",
			data: $scope.foreclosure
		}).success(function(data) {
			$rootScope.isSave = true;
			if(data.code == "SUCCESS" && !isShow) {
				box.boxAlert(data.msg);
			}
		})
	}

	$rootScope.isSave = true;
	//切换tab时保存房产信息
//	$rootScope.$watch("showView", function(newValue, oldValue) {
//		if(oldValue == 4 && oldValue != newValue && $rootScope.isSave) {
//			$rootScope.isSave = false;
//			$scope.updateElement(true);
//		}
//	});

});