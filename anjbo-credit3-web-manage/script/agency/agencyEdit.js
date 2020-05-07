angular.module("anjboApp").controller("agencyEditCtrl", function($scope, $http, $state, $timeout, box, route) {
	//获取关联受理员列表
	$http({
		method: 'post',
		url: "/credit/user/base/v/findAccepList",
		data:{"choicePersonnel":"提单"}
	}).success(function(data) {
		if(data.data){
			$scope.acceptList = data.data;
		}
	})
	//获取机构类型列表
	$http({
		method: 'post',
		url: "/credit/customer/agency/type/v/list"
	}).success(function(data) {
		$scope.agencyTypeList = data.data;
	})
	//获取渠道经理
	$http({
		method: 'post',
		url: "/credit/customer/chanlman/v/channelManager"
	}).success(function(data) {
		$scope.channelManagerList = data.data;
	})
	

	$scope.agencyDto = new Object();
	var agencyId = route.getParams().agencyId;
	if(agencyId == -1) {
		$scope.isAdd = true;
		$scope.agencyDto.isBond = "0";
	} else {
		$scope.isAdd = false;
		// 获取机构信息
		$http({
			method: 'post',
			url: "/credit/customer/agency/new/v/selectCustomerAgencyById",
			data:{id:agencyId}
		}).success(function(data) {
			$scope.agencyDto = data.data;
			if(!$scope.agencyDto.type){
				$scope.agencyDto.type = "";
			}
			
			$scope.acceptListValue = new Array();
			angular.forEach($scope.agencyDto.customerAgencyAcceptDtos,function(data,index,array){
				$scope.acceptListValue.push(data.acceptUid);
			})
			$scope.agencyDto.type = String($scope.agencyDto.type);
		})
	}

	//生成管理账号
	$scope.generateAccount = function() {		
		if($scope.agencyDto.simName) {
			$http({
				method: 'post',
				url: "/credit/user/user/v/gennerAccountPwd",
				data:{"name":$scope.agencyDto.simName}
			}).success(function(data) {
				$scope.agencyDto.manageAccount = data.data.account;
				$scope.agencyDto.managePass = data.data.pwd;
			});
		}else{
			box.boxAlert("请输入机构简称");
			$scope.agencyDto.manageAccount = "";
			$scope.agencyDto.managePass = "";
		}
	}

	//添加或新增
	//iscContinue 是否继续添加
	$scope.addOrEdit = function(iscContinue) {
		//校验
		if($scope.agencyForm.$invalid){
			$scope.agencyForm.name.$dirty=true;
			$scope.agencyForm.simName.$dirty=true;
			$scope.agencyForm.type.$dirty=true;
			$scope.agencyForm.chanlMan.$dirty=true;
			$scope.agencyForm.contactMan.$dirty=true;
			$scope.agencyForm.contactTel.$dirty=true;
			$scope.agencyForm.chargeStandard.$dirty=true;
			$scope.agencyForm.isBond.$dirty=true;
			$scope.agencyForm.proportionResponsibility.$dirty=true;
			$scope.agencyForm.bond.$dirty=true;
			$scope.agencyForm.creditLimit.$dirty=true;
			return;
		}
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
		var accept = new Array();
		angular.forEach($scope.tempAcceptList,function(data,index,array){
			accept.push({acceptUid:data});
		});
		$scope.agencyDto.customerAgencyAcceptDtos = accept;
		// 添加机构
		$http({
			method: 'post',
			url: "/credit/customer/agency/new/v/edit",
			data:$scope.agencyDto
		}).success(function(data) {
			box.boxAlert(data.msg,function(){
				if(data.code == 'SUCCESS'){
					if(iscContinue){
						$state.reload();
					}else{
						$state.go("agencyList");	
					}
				}
			});
		})
	}

	//编辑
	function edit() {
		var accept = new Array();
		angular.forEach($scope.tempAcceptList,function(data,index,array){
			accept.push({acceptUid:data,agencyId:$scope.agencyDto.id});
		});
		$scope.agencyDto.customerAgencyAcceptDtos = accept;
		// 修改机构
		$http({
			method: 'post',
			url: "/credit/customer/agency/new/v/edit",
			data:$scope.agencyDto
		}).success(function(data) {
			box.boxAlert(data.msg,function(){
				if(data.code == 'SUCCESS'){
					$state.go("agencyList");	
				}
			});
		})

	}

	//取消
	$scope.cancel = function() {
		var cancelMethod = function() {
			$state.go("agencyList");
		}
		box.confirmAlert("提示", "确定取消吗？", cancelMethod);
	}
	$scope.$watch("agencyDto.isBond", function(newValue, oldValue, scope) {
		if(newValue==1){
			$scope.agencyDto.proportionResponsibility = 0;
			$scope.agencyDto.bond = 0;
			$scope.agencyDto.creditLimit = 0;
		}
	});
});