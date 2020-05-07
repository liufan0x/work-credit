angular.module("anjboApp").controller("fundCostEditCtrl", function($scope, $http, $state, box, route) {

	
    
	$scope.fundCostDto = new Object();
	var fundId = route.getParams().fundId;
	var fundCostId = route.getParams().fundCostId;
	var fundName = route.getParams().fundName;
	$scope.fundId = fundId;
	$scope.fundName = fundName;
	if(fundCostId == -1) {
		$scope.isAdd = true;
		$scope.fundCostDto.overdueRateHas = "1";
		$scope.fundCostDto.riskProvisionHas = "1";
		$scope.fundCostDto.discountHas = "1";
	} else {
		$scope.isAdd = false;
		// 获取资金方信息
		$http({
			method: 'post',
			url: "/credit/user/fundCost/v/find",
			data:{id:fundCostId}
		}).success(function(data) {
			$scope.fundCostDto = data.data;
			$scope.fundCostDto.overdueRateHas =String($scope.fundCostDto.overdueRateHas);
			$scope.fundCostDto.riskProvisionHas =String($scope.fundCostDto.riskProvisionHas);
			$scope.fundCostDto.discountHas =String($scope.fundCostDto.discountHas);
			$scope.fundCostDto.productId = String($scope.fundCostDto.productId);
			$scope.cityCode = $scope.fundCostDto.productId.substring(0,4)+"";
		})
		
		$http({
			method: 'post',
			url: "/credit/user/fundCostDiscount/v/search",
			data:{fundCostId:fundCostId}
		}).success(function(data) {
			$scope.discountList = data.data;
			if(!$scope.discountList || $scope.discountList.length == 0){
				//优惠数据
				$scope.discountList = new Array();
				$scope.discountList.push(new Object());	
			}
		})

	}
	
	$scope.$watch("fundCostDto.discountHas",function(){
		$scope.discountList = new Array();
		$scope.discountList.push(new Object());
	});
	
	$http({
        method: 'POST',
        url:'/credit/data/dict/v/search',
        data:{type:"cityList"}
    }).success(function(data){
        $scope.cityList = data.data;
		$scope.cityList.unshift({id:"",name:"请选择"});
    })
    
	$http({
        method: 'POST',
        url:'/credit/data/product/v/search',
        data:{}
    }).success(function(data){
        $scope.productList = data.data;
    })

	//添加或新增
	//iscContinue 是否继续添加
	$scope.addOrEdit = function(iscContinue) {
		//校验
		if($scope.fundCostForm.$invalid){
			$scope.fundCostForm.productId.$dirty=true;
			$scope.fundCostForm.dayRate.$dirty=true;
			$scope.fundCostForm.overdueRate.$dirty=true;
			$scope.fundCostForm.riskProvision.$dirty=true;
			$scope.isShowError = true;
			return;
		}
		box.editAlert($scope,"提示","是否对刚编辑的内容进行保存？",function(){
			$scope.fundCostDto.fundCostDiscountDtos = $scope.discountList;
			if(iscContinue) {
				add(iscContinue);
			} else {
				if($scope.isAdd) {
					add(iscContinue);
				} else {
					edit();
				}
			}
		});
	}

	//添加
	function add(iscContinue) {
		// 添加资金方
		$scope.fundCostDto.fundId = fundId;
		$http({
			method: 'post',
			url: "/credit/user/fundCost/v/add",
			data: $scope.fundCostDto
		}).success(function(data) {
			box.boxAlert(data.msg);
			if(data.code == 'SUCCESS'){
				if(iscContinue){
					$state.reload();
				}else{
					$state.go("fundCostList",{fundId:fundId,fundName:fundName});
				}
			}
		})
	}

	//编辑
	function edit(){
		// 修改资金方
		$http({
			method: 'post',
			url: "/credit/user/fundCost/v/edit",
			data: $scope.fundCostDto
		}).success(function(data) {
			box.boxAlert(data.msg);
			if(data.code == 'SUCCESS'){
				$state.go("fundCostList",{fundId:fundId,fundName:fundName});
			}
		});
	}

	//取消
	$scope.cancel = function() {
		var cancelMethod = function() {
			$state.go("fundCostList");
		}
		box.confirmAlert("提示", "确定取消吗？", cancelMethod);
	}


	$scope.jiajian = function(index) {
		if(index == 0) {
			$scope.discountList.push(new Object());
		} else {
			$scope.discountList.splice(index, 1);
		}
	}

});