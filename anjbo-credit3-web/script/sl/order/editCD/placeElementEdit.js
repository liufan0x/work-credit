angular.module("anjboApp").controller("placeElementEditCDCtrl", function($scope, $timeout, $rootScope, $http, $state, process, $compile, route, box) {

	var params = {"orderNo": route.getParams().orderNo};

	//要件信息
	$http({
		method: 'POST',
		url: '/credit/element/basics/v/detail',
		data: params
	}).success(function(data) {
		$scope.foreclosure = data.data;
		if($scope.foreclosure && (null!=$scope.foreclosure.foreclosureType || null!=$scope.foreclosure.paymentType)) {
			$scope.foreclosureType = $scope.foreclosure.foreclosureType;
			if(null!=$scope.foreclosureType){
				$scope.foreclosureType.bankNameId = $scope.foreclosureType.bankNameId ? String($scope.foreclosureType.bankNameId) : null;
				$scope.foreclosureType.bankSubNameId = $scope.foreclosureType.bankSubNameId ? String($scope.foreclosureType.bankSubNameId) : null;
			}
			
			$scope.paymentType = $scope.foreclosure.paymentType;
			if(null!=$scope.paymentType){
				$scope.paymentType.paymentBankNameId = $scope.paymentType.paymentBankNameId ? String($scope.paymentType.paymentBankNameId) : null;
				$scope.paymentType.paymentBankSubNameId = $scope.paymentType.paymentBankSubNameId ? String($scope.paymentType.paymentBankSubNameId) : null;
			}
		} else {
			$scope.foreclosure = new Object();
			$scope.foreclosureType = new Object();
			$scope.paymentType = new Object();
			//初始化值
			$scope.foreclosureType.foreclosureType = "银行自动扣款";
			$scope.foreclosureType.accountType = "产权人";
			$scope.paymentType.paymentMode = "网银操作提取回款";
			$scope.paymentType.paymentaccountType = "产权人";
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
			if(data.code == "SUCCESS" && !isShow) {
				box.boxAlert(data.msg);
			}
		})
	}

	//切换tab时保存房产信息
	$rootScope.$watch("showView", function(newValue, oldValue) {
		if(oldValue == 4 && oldValue != newValue) {
			//$scope.updateElement(true);
		}
	});

});