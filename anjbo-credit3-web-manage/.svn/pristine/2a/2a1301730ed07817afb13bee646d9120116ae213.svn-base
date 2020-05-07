angular.module("anjboApp").controller("contractGroupListCtrl", function($scope, $http, $state, $timeout, box, route) {

	$scope.contractGroupList = new Array();

	//添加分组
	$scope.addGroupShow = function(groupList, index) {
		$scope.groupDto = new Object();
		var url = "/credit/tools/contractGroup/v/add";
		if(groupList) {
			url = "/credit/tools/contractGroup/v/edit";
			$scope.groupDto.id = groupList[index].id;
			$scope.groupDto.name = groupList[index].name;
		}
		box.editAlert($scope, "添加分组", $("#groupEditId").html(), function() {
			if(!$scope.frmGroup.$valid) {
				$scope.isAudit = true;
				alert("请完善所有必填信息！");
				return;
			}

			//获取合同分组
			$http({
				method: 'POST',
				url: url,
				data: $scope.groupDto
			}).success(function(data) {
				$timeout(function() {
					$scope.init();
				});
			})
			box.closeAlert();
		});
	}

	//删除分组
	$scope.delGroupShow = function(contractGroup) {
		var title = "确定要删除分组？";
		if(contractGroup.contractList.length>0){
			title = "分组中的合同将被同时删除，确定要删除分组？";
		}
		box.confirmAlert("删除分组", title, function() {
			$http({
				method: 'POST',
				url: "/credit/tools/contractGroup/v/delete",
				data: contractGroup
			}).success(function(data) {
				$timeout(function() {
					$scope.init();
				});
				box.boxAlert(data.msg);
			})
		});
	}

	//上移
	$scope.up = function(contractList, index) {
		var tempSort = contractList[index].sort;
		contractList[index].sort = contractList[index + 1].sort;
		contractList[index + 1].sort = tempSort;
		$http({
			method: 'POST',
			url: "/credit/tools/contract/v/edit",
			data: contractList[index]
		}).success(function(data) {
			$http({
				method: 'POST',
				url: "/credit/tools/contract/v/edit",
				data: contractList[index + 1]
			}).success(function(data) {
				$timeout(function() {
					$scope.init();
				});
			})
		})
	}

	//下移
	$scope.down = function(contractList, index) {
		var tempSort = contractList[index].sort;
		contractList[index].sort = contractList[index - 1].sort;
		contractList[index - 1].sort = tempSort;
		$http({
			method: 'POST',
			url: "/credit/tools/contract/v/edit",
			data: contractList[index]
		}).success(function(data) {
			$http({
				method: 'POST',
				url: "/credit/tools/contract/v/edit",
				data: contractList[index - 1]
			}).success(function(data) {
				$timeout(function() {
					$scope.init();
				});
			})
		})
	}

	$scope.goContract = function(contract) {
		if(contract) {
			$state.go("contractFieldShow", {
				id: contract.id
			});
		} else {
			$state.go("contractFieldShow", {
				"id": "-1"
			});
		}

	}

	//禁用启用合同
	$scope.enableShow = function(contract) {
		if(contract.isEnable == 0) {
			contract.isEnable = 1;
		} else {
			contract.isEnable = 0;
		}
		$http({
			method: 'POST',
			url: "/credit/tools/contract/v/edit",
			data: contract
		}).success(function(data) {
			$timeout(function() {
				$scope.init();
			});
		})
	}

	//删除合同
	$scope.delContractShow = function(contract) {
		box.confirmAlert("删除合同", "确定要删除合同吗？", function() {
			$http({
				method: 'POST',
				url: "/credit/tools/contract/v/delete",
				data: contract
			}).success(function(data) {
				$timeout(function() {
					$scope.init();
				});
				box.boxAlert(data.msg);
			})
		});
	}

	$scope.init = function() {
		//获取合同分组
		$http({
			method: 'POST',
			url: '/credit/tools/contractGroup/v/search',
			data: {}
		}).success(function(data) {
			$scope.contractGroupList = data.data;
		})
	}

	$scope.init();

});