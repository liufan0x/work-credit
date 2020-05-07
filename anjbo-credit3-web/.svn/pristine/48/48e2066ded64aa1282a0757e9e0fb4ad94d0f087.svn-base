angular.module("anjboApp").controller("placeCustomerEditCDCtrl", function($scope,$timeout,$rootScope, $http, $state, route, box) {

	var params = route.getParams();	
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
		var scopeTemp = angular.element(".customerfrom-supplement").scope();
		if(null==$scope.customer || undefined==scopeTemp.customerForm){
			return;
		}
		$scope.customer.isFinish = scopeTemp.customerForm.$valid?1:2;
		if($scope.customer.customerGuaranteeDto&&$scope.customer.customerGuaranteeDto.length>0){
			for(var i=0;i<$scope.customer.customerGuaranteeDto.length;i++){
				$scope.customer.customerGuaranteeDto[i].isFinish=scopeTemp.customerGuaranteeForm.$valid?1:2;
			}
		}
		if($scope.customer.customerBorrowerDto&&$scope.customer.customerBorrowerDto.length>0){
			for(var i=0;i<$scope.customer.customerBorrowerDto.length;i++){
				$scope.customer.customerBorrowerDto[i].isFinish=scopeTemp.customerBorrowerForm.$valid?1:2;
			}
		}
		box.waitAlert();
		$http({
			method: 'POST',
			url: "/credit/order/customer/v/update",
			data: $scope.customer
		}).success(function(data) {
			box.closeWaitAlert();
			if(data.code == "SUCCESS" && !isShow) {
				box.boxAlert(data.msg);
			}
		})
	}
	
	//切换tab时保存房产信息
	$rootScope.$watch("showView",function(newValue,oldValue){
		if(oldValue == 2 && oldValue != newValue){
//			$scope.submitCustomer(true);
		}
	});
	
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
	
	// 维护股东信息
	$scope.shareholderAdd = function(){
		if(null==$scope.customer.customerShareholderDto || 0==$scope.customer.customerShareholderDto.length){
			$scope.customer.customerShareholderDto = new Array();
			$scope.customer.customerShareholderDto.push(new Object());
		}else{
			$scope.customer.customerShareholderDto.push(new Object());
		}
	}
	$scope.shareholderDelete = function(index){
		box.confirmAlert("删除股东信息","确定删除股东信息吗？",function(){
			$timeout(function(){
				$scope.customer.customerShareholderDto.splice(index,1);
			});
		});
	}
	

});