angular.module("anjboApp").controller("placeCreditEditCtrl",function($scope,$timeout,$rootScope,$http,$state,route,box){
	$scope.relationOrderNo = route.getParams().relationOrderNo;
	$scope.isProductCode = route.getParams().productCode;
	var params = {"orderNo": route.getParams().orderNo}
	if($scope.isProductCode=='03' && $scope.relationOrderNo!="0"){
		 params = {"orderNo": route.getParams().relationOrderNo}
	}
	$scope.addCangDai = route.getParams().addCangDai;
	$scope.all = false;
	
	if($scope.addCangDai != 0&&$scope.addCangDai) {
		$("#creditForm").find("input").prop("disabled","disabled");
		$("#creditForm").find("textarea").prop("disabled","disabled");
		$("#creditForm").find("select").prop("disabled", true);
		$scope.all=true;
	}
	
	/*//是否畅贷
	$http({
		method: 'POST',
		url: 'credit/order/borrow/v/isChangLoan',
		data: params
	}).success(function(data) {
		$scope.data=data.data;
		if($scope.data.isChangLoan==1){
			$("#creditForm").find("input").prop("disabled","disabled");
			$("#creditForm").find("textarea").prop("disabled","disabled");
			$("#creditForm").find("select").prop("disabled", true); 
			$scope.all=true;
		}
	})*/
	
	//征信信息
	function refresh() {
		$http({
			method: 'POST',
			url: '/credit/risk/ordercredit/v/detail',
			data: params
		}).success(function(data) {
			$scope.credit = data.data;
			$scope.liabilitiesProportion = data.data.liabilitiesProportion;
			if(!$scope.credit){
				$scope.credit = new Object();
			}
			//征信
			$scope.$watch("credit.creditLiabilities", function(newValue, oldValue) {
				if(!newValue){
					return;
				}
				var allHouseWorth=$scope.credit.allHouseWorth;
				var creditLiabilities=newValue;
				if(allHouseWorth!=null&&allHouseWorth!=0&&creditLiabilities!=null&&!$scope.liabilitiesProportion){
					var liabilitiesProportion= div(mul(creditLiabilities,100),allHouseWorth).toFixed(2);
					if(oldValue||!$scope.credit.liabilitiesProportion){
						$scope.credit.liabilitiesProportion = liabilitiesProportion;
					}
				}
			});
			
			$scope.$watch("credit.allHouseWorth", function(newValue, oldValue) {
				if(!newValue){
					return;
				}
				var allHouseWorth=newValue;
				var creditLiabilities=$scope.credit.creditLiabilities;
				if(allHouseWorth!=null&&allHouseWorth!=0&&creditLiabilities!=null&&!$scope.liabilitiesProportion){
					var liabilitiesProportion= div(mul(creditLiabilities,100),allHouseWorth).toFixed(2);
					if(oldValue||!$scope.credit.liabilitiesProportion){
						$scope.credit.liabilitiesProportion = liabilitiesProportion;
					}
				}
			});
		})
	}
	$timeout(refresh,300);
	
	//编辑保存征信信息
	$scope.submitCredit = function(isShow) {
		//$scope.credit.isFinish = $scope.creditForm.$valid?1:2;
		$scope.credit.orderNo=route.getParams().orderNo;
		if($scope.isProductCode=='03' && $scope.relationOrderNo!="0"){
			$scope.credit.orderNo=route.getParams().relationOrderNo;
		}
		if($scope.isCreditEditShow){
			$scope.credit.createCreditLog = true;
		}else{
			$scope.credit.createCreditLog = false;
		}
		box.waitAlert();
		$http({
			method: 'POST',
			url: "/credit/risk/ordercredit/v/updateCredit",
			data: $scope.credit
		}).success(function(data) {
			box.closeWaitAlert();
			$rootScope.isSave = true; 
			if(data.code == "SUCCESS" && !isShow) {
				box.boxAlert(data.msg);
			}else if(isShow){
				
			}
		})
	}
	
	$scope.$watch(function(){
		return 	$scope.creditForm.$valid;
	},function(newValue,oldValue){
		$scope.$watch(function(){
			return 	$scope.credit;
		},function(newV,oldV){
			if(newV){
				$scope.credit.isFinish = newValue?1:2;
			}
		});
	});
				
	$rootScope.isSave = true; 
	//切换tab时保存房产信息
//	$rootScope.$watch("showView",function(newValue,oldValue){
//		if(oldValue ==7 && oldValue != newValue && $rootScope.isSave){
//			$rootScope.isSave = false;
//			$scope.submitCredit(true);
//		}
//	});
	
	
	function div(a, b) {
		var c, d, e = 0,
		f = 0;
		try {
			e = a.toString().split(".")[1].length;
		} catch (g) {}
		try {
			f = b.toString().split(".")[1].length;
		} catch (g) {}
		return c = Number(a.toString().replace(".", "")), d = Number(b.toString().replace(".", "")), c / d * Math.pow(8, f - e);
	}
	
	function mul(a, b) {
		var c = 0,
		d = a.toString(),
		e = b.toString();
		try {
			c += d.split(".")[1].length;
		} catch (f) {}
		try {
			c += e.split(".")[1].length;
		} catch (f) {}
		return Number(d.replace(".", "")) * Number(e.replace(".", "")) / Math.pow(8, c);
	}
	
});