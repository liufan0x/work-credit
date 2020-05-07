angular.module("anjboApp").controller("contractFieldShowCtrl", function($scope, $http, $state, $timeout, box, route) {

	var id = route.getParams().id;

	//获取字段
	$http({
		method: 'POST',
		url: '/credit/tools/fieldGroup/v/search',
		data: {}
	}).success(function(data) {
		if(data.code == 'SUCCESS'){
			$scope.groupList = data.data;
			if($scope.contract){
				$scope.init();
			}
		}
	})
	
	if(id != '-1'){
		//获取合同
		$http({
			method: 'POST',
			url: '/credit/tools/contract/v/find',
			data: {
				id: id
			}
		}).success(function(data) {
			if(data.code == 'SUCCESS'){
				$scope.contract = data.data;
				$scope.contract.groupId = String($scope.contract.groupId);
				if($scope.groupList){
					$scope.init();	
				}
			}
		})
	}else{
		$scope.contract = new Object();
		$scope.contract.groupId = "";
	}


	//获取合同
	$http({
		method: 'POST',
		url: '/credit/tools/contractGroup/v/search',
		data: {}
	}).success(function(data) {
		$scope.contractGroupList = data.data;
	})

	$scope.init = function() {
		if($scope.contract.fieldIds){
			var ids = $scope.contract.fieldIds.split(',');
			angular.forEach($scope.groupList, function(groupDto) {
				angular.forEach(groupDto.fileList, function(fileDto) {
					if($.inArray(String(fileDto.id), ids) != -1) {
						fileDto.gou = String(fileDto.id);
					}
				});
			});
		}
	}

	$scope.save = function() {
		
		if(!$scope.contract.groupId){
			alert("请选择分组");
			return;
		}
		
		if(!$scope.contract.name){
			alert("请填写合同名称");
			return;
		}
		
		var ids = "";
		angular.forEach($scope.groupList, function(groupDto) {
			angular.forEach(groupDto.fileList, function(fileDto) {
				if(fileDto.gou) {
					ids += fileDto.gou + ",";
				}
			});
		});
		$scope.contract.fieldIds = ids.substring(0, ids.length - 1);
		
		var url = '/credit/tools/contract/v/edit';
		if(id == '-1'){
			$scope.contract.isEnable = 1;
			url = '/credit/tools/contract/v/add';
		}
		
		//保存
		$http({
			method: 'POST',
			url: url,
			data: $scope.contract
		}).success(function(data) {
			alert(data.msg);
			if(data.code == 'SUCCESS'){
				$state.go("contractGroupList");
			}
		})
	}

	$scope.setPath = function(url) {
		$timeout(function() {
			$scope.contract.path = url;
		});
	}

	$scope.setNoTextPath = function(url) {
		$timeout(function() {
			$scope.contract.noTextPath = url;
		});
	}

});