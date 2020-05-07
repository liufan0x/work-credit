angular.module("anjboApp").controller("placeCustomerEditCtrl", function($scope,$timeout,$rootScope, $http, $state, route, box) {

	var params = route.getParams();

	$scope.addCangDai = route.getParams().addCangDai;
	$scope.all = false;
	if($scope.addCangDai != 0&&$scope.addCangDai) {
		$("#customerForm").find("input").prop("disabled","disabled");
		$("#customerForm").find("textarea").prop("disabled","disabled");
		$("#customerForm").find("select").prop("disabled", true);
		$scope.all=true;
	}
	
	//是否畅贷
	$http({
		method: 'POST',
		url: 'credit/order/borrow/v/isChangLoan',
		data: params
	}).success(function(data) {
		$scope.data=data.data;
		if($scope.data.isChangLoan==1){
			$("#customerForm").find("input").prop("disabled","disabled");
			$("#customerForm").find("textarea").prop("disabled","disabled");
			$("#customerForm").find("select").prop("disabled", true);
			$scope.all=true;
		}
	})
	
	if(!$scope.customer){
		//客户信息
		$http({
			method: 'POST',
			url: 'credit/order/customer/v/query',
			data: params
		}).success(function(data) {
			$scope.customer = data.data;
		})
	}

	//编辑保存客户信息
	$scope.submitCustomer = function(isShow) {
//		var scopeTemp = angular.element(".customerfrom-supplement").scope();
//		//$scope.customer.isFinish = $scope.customerForm.$valid?1:2;
//		$scope.customer.isFinish = scopeTemp.customerForm.$valid?1:2;
//		if($scope.customer.customerGuaranteeDto&&$scope.customer.customerGuaranteeDto.length>0){
//			for(var i=0;i<$scope.customer.customerGuaranteeDto.length;i++){
//				$scope.customer.customerGuaranteeDto[i].isFinish=scopeTemp.customerGuaranteeForm.$valid?1:2;
//			}
//		}
//		if($scope.customer.customerBorrowerDto&&$scope.customer.customerBorrowerDto.length>0){
//			for(var i=0;i<$scope.customer.customerBorrowerDto.length;i++){
//				$scope.customer.customerBorrowerDto[i].isFinish=scopeTemp.customerBorrowerForm.$valid?1:2;
//			}
//		}
		$http({
			method: 'POST',
			url: "/credit/order/customer/v/update",
			data: $scope.customer
		}).success(function(data) {
			$rootScope.isSave = true;
			if(data.code == "SUCCESS" && !isShow) {
				box.boxAlert(data.msg);
			}
		})
	}
	
	$scope.$watch(function(){
		return 	$scope.customerForm.$valid;
	},function(newValue,oldValue){
		$scope.$watch(function(){
			return 	$scope.customer;
		},function(newV,oldV){
			if(newV){
				$scope.customer.isFinish = newValue?1:2;
			}
		});
	});
	
	$scope.$watch(function(){
		return 	$scope.customerBorrowerForm.$valid;
	},function(newValue,oldValue){
		$scope.$watch(function(){
			return 	$scope.customer;
		},function(newV,oldV){
			if(newV && newV.customerBorrowerDto && newV.customerBorrowerDto.length > 0){
				for(var i=0;i<newV.customerBorrowerDto.length;i++){
					newV.customerBorrowerDto[i].isFinish=newValue?1:2;
				}
			}
		});
	});
	
	$scope.$watch(function(){
		return 	$scope.customerGuaranteeForm.$valid;
	},function(newValue,oldValue){
		$scope.$watch(function(){
			return 	$scope.customer;
		},function(newV,oldV){
			if(newV && newV.customerGuaranteeDto && newV.customerGuaranteeDto.length > 0){
				for(var i=0;i<newV.customerGuaranteeDto.length;i++){
					newV.customerGuaranteeDto[i].isFinish=newValue?1:2;
				}
			}
		});
	});
	
	$rootScope.isSave = true; 
	
	//切换tab时保存房产信息
//	$rootScope.$watch("showView",function(newValue,oldValue){
//		if(oldValue == 2 && oldValue != newValue && $rootScope.isSave){
//			$rootScope.isSave = false;
//			$scope.submitCustomer(true);
//		}
//	});
	
	//添加担保人
	$scope.addDanBao = function(){
		if($scope.customer.customerGuaranteeDto.length == 0){
			$scope.customer.customerGuaranteeDto = new Array();
			$scope.customer.customerGuaranteeDto.push(new Object());
		}else{
			$scope.customer.customerGuaranteeDto.push(new Object());
		}
	}
	
	//删除担保人
	$scope.deleteDanBao = function(index){
		box.confirmAlert("删除担保人","确定删除担保人吗？",function(){
			$timeout(function(){
				$scope.customer.customerGuaranteeDto.splice(index,1);
			});
		});		
	}
	
	//添加共同借款人
	$scope.addBorrower = function(){
		if($scope.customer.customerBorrowerDto.length == 0){
			$scope.customer.customerBorrowerDto = new Array();
			$scope.customer.customerBorrowerDto.push(new Object());
		}else{
			$scope.customer.customerBorrowerDto.push(new Object());
		}
	}
	
	//删除共同借款人
	$scope.deleteBorrower = function(index){
		box.confirmAlert("删除共同借款人","确定删除共同借款人吗？",function(){
			$timeout(function(){
				$scope.customer.customerBorrowerDto.splice(index,1);
			});
		});
	}

});