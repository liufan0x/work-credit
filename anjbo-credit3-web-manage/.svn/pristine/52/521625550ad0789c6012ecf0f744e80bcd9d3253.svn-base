angular.module("anjboApp").controller("fundEditCtrl", function($scope, $http, $state, box, route) {
	
	
	var fundId = route.getParams().fundId;
	$scope.authRisk = 0;
	$scope.authRisk1501 = -1;
	$scope.authRisk1502 = -2;
	$scope.selectAuths = new Array(4);	
	if(fundId == -1) {
		$scope.fundDto = new Object();
		$scope.isAdd = true;		
	} else {
		$scope.isAdd = false;
		// 获取资金方信息
		$http({
			method: 'post',
			url: "/credit/user/fund/v/find",
			data:{id:fundId}
		}).success(function(data) {
			$scope.fundDto = data.data;
			if(null != $scope.fundDto.auths){
				$scope.authRisk     = $scope.fundDto.auths.indexOf("1500")>-1&&$scope.fundDto.auths.indexOf("1500")<11 ? 1500 : 0;
				$scope.authRisk1501 = $scope.fundDto.auths.indexOf("1501")>-1&&$scope.fundDto.auths.indexOf("1501")<11 ? 1501 : -1;
				$scope.authRisk1502 = $scope.fundDto.auths.indexOf("1502")>-1&&$scope.fundDto.auths.indexOf("1502")<11 ? 1502 : -2;
				$scope.listAuths = $scope.fundDto.auths.split(",");
				$scope.selectAuths[0] = $scope.fundDto.auths;
				$scope.initSelectedBusinfoType("02");
				$scope.initSelectedBusinfoType("03");
				$scope.initSelectedBusinfoType("07");
			}
			$scope.loadingBusinfoType("01");
		});	
	}
	
	// 构建产品页面级权限串,切换的前一面板(即当前产品面板)
	$scope.buildPreAuth = function() {	
		if(!$scope.productCode){
			return;
		}
		var ids = "";
		angular.forEach($scope.listBusinfoType, function(data, index, array) {
			angular.forEach(data.childs, function(data, index, array) {
				if(data.authId) {
					ids += data.authId + ",";
				}
			});
		});
		if(ids.length > 0) {
			ids = ids.substring(0, ids.length - 1);
		}
		console.debug(ids);
		// 临时存储产品权限
		switch ($scope.productCode){
			case "02":
				$scope.selectAuths[1] = ids;
				break;
			case "03":
				$scope.selectAuths[2] = ids;
				break;
			case "07":
				$scope.selectAuths[3] = ids;
				break;	
			default:
				$scope.selectAuths[0] = ids;
				break;
		}		
	}
	// 加载影像资料项
	$scope.loadingBusinfoType = function(productCode) {		
		$scope.chgCheckAllModel = false;
		$scope.buildPreAuth();
		$http({
			method: 'post',
//			url: "/credit/page/businfo/v/pageBusinfoConfig",
			url: "/credit/order/businfo/v/searchByProductCode_"+productCode,
			data:{productCode:productCode}
		}).success(function(data) {		
			$scope.productCode = productCode;
			$scope.listBusinfoType = data.data;
			// 初始化加载选中
			var tabIndex = 0;
			if("02" == productCode){
				tabIndex = 1;
			}else if("03" == productCode){
				tabIndex = 2;
			}else if("07" == productCode){
				tabIndex = 3;
			}
			if(null!=$scope.selectAuths[tabIndex] && $scope.selectAuths[tabIndex].length>0){
				var _listAuths = $scope.selectAuths[tabIndex].split(",");
				angular.forEach($scope.listBusinfoType, function(parent, index, array) {
					angular.forEach(parent.childs, function(childs, index, array) {
						angular.forEach(_listAuths, function(authCheckedId, index, array) {
							if(childs.id == Number(authCheckedId)) {
								childs.authId = Number(authCheckedId);
							}
						});
					});
				});
			}			
		});		
	}
	if($scope.isAdd){$scope.loadingBusinfoType("01");}
	// 初始化选中影像资料(编辑时初始化第2/3个Tab有用)
	$scope.initSelectedBusinfoType = function(productCode) {
		if($scope.isAdd){
			return;
		}		
		var tabIndex = 0;
		if("02" == productCode){
			tabIndex = 1;
		}else if("03" == productCode){
			tabIndex = 2;
		}else if("07" == productCode){
			tabIndex = 3;
		}
		$scope.selectAuths[tabIndex] = "";
		// 数据重组
		var _listAuths = $scope.fundDto.auths.split(",").splice(4);			
		var ids = "";
		if(null!=_listAuths && _listAuths.length>0){
			$http({
				method:'post', 
				url: "/credit/page/businfo/v/pageBusinfoConfig",
				data:{productCode:productCode}
			}).success(function(data) {
				angular.forEach(data.data, function(data, index, array) {
					angular.forEach(data.sonTypes, function(child, index, array) {
						angular.forEach(_listAuths, function(authCheckedId, index, array) {
							if(child.id==Number(authCheckedId)) {
								ids += authCheckedId + ",";
							}
						});
					});
				});
				if(ids.length > 0) {
					ids = ids.substring(0, ids.length - 1);
				}	
				console.debug("selectAuths["+tabIndex+"]重组:"+ ids);
				$scope.selectAuths[tabIndex] = ids;
			});	
		}						
	}

	//添加或新增
	//iscContinue 是否继续添加
	$scope.addOrEdit = function(iscContinue) {
		if($scope.fundForm.$invalid) {
			$scope.fundForm.fundName.$dirty = true;
			$scope.fundForm.fundDesc.$dirty = true;
			$scope.fundForm.fundCode.$dirty = true;
			$scope.fundForm.contactMan.$dirty = true;
			$scope.fundForm.contactTel.$dirty = true;
			return false;
		}
		$scope.buildPreAuth();	
		$scope.fundDto.auths = $scope.authRisk+","+$scope.authRisk1501+","+$scope.authRisk1502+","+$scope.selectAuths.toString();
		
		box.waitAlert();
		if(iscContinue) {
			add(iscContinue);
		} else {
			if($scope.isAdd) {
				add(iscContinue);
			} else {
				edit();
			}
		}		
	}

	//添加
	function add(iscContinue) {
		// 添加资金方
		$http({
			method: 'post',
			url: "/credit/user/fund/v/add",
			data: $scope.fundDto
		}).success(function(data) {			
			if(data.code == 'SUCCESS'){
				if(iscContinue){
					$state.reload();
				}else{
					$state.go("fundList");
				}
			}else{
				box.boxAlert(data.msg);
			}
			box.closeWaitAlert();
		}).error(function(xhr,status,error){
			box.boxAlert("出错啦，请重试！");
			box.closeWaitAlert();
		});
	}

	//编辑
	function edit() {
		// 修改资金方
		$http({
			method: 'post',
			url: "/credit/user/fund/v/edit",
			data: $scope.fundDto
		}).success(function(data) {
			if(data.code == 'SUCCESS'){
				$state.go("fundList");
			}else{		
				box.boxAlert(data.msg);
			}
			box.closeWaitAlert();
		}).error(function(xhr,status,error){
			box.boxAlert("出错啦，请重试！");
			box.closeWaitAlert();
		});
	}

	//取消
	$scope.cancel = function() {
		var cancelMethod = function() {
			$state.go("fundList");
		}
		box.confirmAlert("提示", "确定取消吗？", cancelMethod);
	}

	$scope.chgCheckAll = function(){
		angular.forEach($scope.listBusinfoType, function(parent, index, array) {
			angular.forEach(parent.childs, function(childs, index, array) {				
				childs.authId = $scope.chgCheckAllModel ? childs.id : 0;
			});
		});
	}
	/*$scope.chgCheckAll = function(){
		angular.forEach($scope.listBusinfoType, function(parent, index, array) {
			angular.forEach(parent.sonTypes, function(sonTypes, index, array) {				
				sonTypes.authId = $scope.chgCheckAllModel ? sonTypes.id : 0;
			});
		});
	}*/
});