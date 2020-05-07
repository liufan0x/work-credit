angular.module("anjboApp").controller("contractFieldEditCtrl", function($scope, $http, $compile, $state, $timeout, box, route) {

	$scope.groupList = new Array();
	$scope.sourceList = new Array();
	//来源List
	$http({
		method: 'POST',
		url: '/credit/data/dict/v/search',
		data: {
			"type": "sourceList"
		}
	}).success(function(data) {
		$scope.sourceList = data.data;
	})

	$scope.$watch("inputDto.pSource", function(newValue, oldValue) {
		$scope.getSourceList(newValue);
	});

	$scope.getSourceList = function(newValue) {
		if(newValue) {
			$http({
				method: 'POST',
				url: '/credit/data/dict/v/search',
				data: {
					"type": "sourceList",
					"pcode": newValue
				}
			}).success(function(data) {
				$scope.childrenSourceList = data.data;
				var htmlstring = '<select class="form-control" select2 ng-model="inputDto.source"><option value="">无</option><option value="{{source.code}}" ng-repeat="source in childrenSourceList" >{{source.name}}</option></select>';
				var el = $compile(htmlstring)($scope);
				$(".sourceList:eq(1)").html(el);
			})
		} else {
			var htmlstring = '<select class="form-control" select2 ng-model="inputDto.source"><option value="">无</option></select>';
			var el = $compile(htmlstring)($scope);
			$timeout(function() {
				$(".sourceList:eq(1)").html(el);
			});
		}
	}

	$scope.init = function() {
		//获取字段
		$http({
			method: 'POST',
			url: '/credit/tools/fieldGroup/v/search',
			data: {}
		}).success(function(data) {
			$scope.groupList = data.data;
		})
	}

	$scope.init();

	//添加分组
	$scope.addGroupShow = function(groupList, index) {
		$scope.groupDto = new Object();
		var url = "/credit/tools/fieldGroup/v/add";
		if(groupList) {
			url = "/credit/tools/fieldGroup/v/edit";
			$scope.groupDto.id = groupList[index].id;
			$scope.groupDto.name = groupList[index].name;
		}
		box.editAlert($scope, "添加分组", $("#groupEditId").html(), function() {
			if(!$scope.frmGroup.$valid) {
				$scope.isAudit = true;
				alert("请完善所有必填信息！");
				return;
			}
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
	$scope.delGroupShow = function(groupList, index) {
		box.confirmAlert("删除分组", "分组下的字段将会被同时删除，确认删除分组？", function() {
			$http({
				method: 'POST',
				url: "/credit/tools/fieldGroup/v/delete",
				data: groupList[index]
			}).success(function(data) {
				$timeout(function() {
					$scope.init();
				});
				box.boxAlert(data.msg);
			})
		});
	}

	//添加字段
	$scope.addFieldShow = function(group, fileList, index) {
		$scope.fieldDto = new Object();
		var url = "/credit/tools/field/v/add";
		if(fileList) {
			url = "/credit/tools/field/v/edit";
			$scope.fieldDto.id = fileList[index].id;
			$scope.fieldDto.name = fileList[index].name;
			$scope.fieldDto.type = fileList[index].type;
			$scope.fieldDto.value = fileList[index].inputs[0].value;
		} else {
			$scope.fieldDto.groupId = group.id;
			$scope.fieldDto.type = "1";
		}
		box.editAlert($scope, "添加字段", $("#fieldEditId").html(), function() {
			
			if(!$scope.fieldDto.name){
				alert("请填写字段名称！");
				return;
			}
			
			if(!fileList) {
				var inputs = new Array();
				var input = new Object();
				input.value = $scope.fieldDto.value;
				inputs.push(input);
				$scope.fieldDto.inputs = inputs;
			}

			$http({
				method: 'POST',
				url: url,
				data: $scope.fieldDto
			}).success(function(data) {
				if(fileList) {
					fileList[index].inputs[0].value = $scope.fieldDto.value;
					$http({
						method: 'POST',
						url: "/credit/tools/fieldInput/v/edit",
						data: fileList[index].inputs[0]
					}).success(function(data) {
						$timeout(function() {
							$scope.init();
						});
					})
				} else {
					$timeout(function() {
						$scope.init();
					});
				}
			})
			box.closeAlert();
		});
	}

	//删除字段
	$scope.delFieldShow = function(group, fileList, index) {
		box.confirmAlert("删除字段", "确认要删除字段吗？", function() {
			$http({
				method: 'POST',
				url: "/credit/tools/field/v/delete",
				data: fileList[index]
			}).success(function(data) {
				$timeout(function() {
					$scope.init();
				});
				box.boxAlert(data.msg);
			})
		});
	}

	//编辑输入框
	$scope.editInputShow = function(inputs, index) {
		$scope.inputDto = new Object();
		$scope.inputDto.id = inputs[index].id;
		$scope.inputDto.value = inputs[index].value;
		$scope.inputDto.source = inputs[index].source;
		$scope.inputDto.pSource = inputs[index].pSource;
		
		
		var htmlstring = '<select class="form-control" ng-model="inputDto.pSource"><option value="">无</option><option value="{{source.code}}" ng-repeat="source in sourceList">{{source.name}}</option></select>';
		var el = $compile(htmlstring)($scope);
		$timeout(function() {
			$(".pSourceList:eq(1)").html(el);
		});

		$scope.getSourceList($scope.inputDto.pSource);

		box.editAlert($scope, "编辑输入框", $("#inputEditId").html(), function() {
			
			if(!$scope.inputDto.value){
				alert("请填写代号！");
				return;
			}
			
			if($scope.inputDto.source == ''){
				$scope.inputDto.source = "无";
			}
			
			$http({
				method: 'POST',
				url: "/credit/tools/fieldInput/v/edit",
				data: $scope.inputDto
			}).success(function(data) {
				$timeout(function() {
					inputs[index] = $scope.inputDto;
				});
			})
			box.closeAlert();
		});
	}

	//减
	$scope.jian = function(field) {
		if(field.inputs[field.inputs.length - 1].id) {
			$http({
				method: 'POST',
				url: "/credit/tools/fieldInput/v/delete",
				data: field.inputs[field.inputs.length - 1]
			}).success(function(data) {

			})
		}
		$timeout(function() {
			field.inputs.splice(field.inputs.length - 1, 1);
			field.isShowGou = false;
		});
	}

	//勾
	$scope.gou = function(field) {
		field.inputs[field.inputs.length - 1].fieldId = field.id;
		$http({
			method: 'POST',
			url: "/credit/tools/fieldInput/v/add",
			data: field.inputs[field.inputs.length - 1]
		}).success(function(data) {
			$timeout(function() {
				field.inputs[field.inputs.length - 1].type = undefined;
				field.inputs[field.inputs.length - 1].id = data.data.id;
				field.isShowGou = false;
			});
		})
	}

	//加
	$scope.jia = function(field) {
		var tempObj = new Object();
		tempObj.type = true;
		tempObj.fieldId = field.id;
		field.inputs[field.inputs.length - 1].type = undefined;
		field.inputs.push(tempObj);
		field.isShowGou = true;
	}
	
	$scope.up = function(groupList,index){
		var tempSort = groupList[index].sort;
		groupList[index].sort = groupList[index + 1].sort;
		groupList[index + 1].sort = tempSort;
		$http({
			method: 'POST',
			url: "/credit/tools/fieldGroup/v/edit",
			data: groupList[index]
		}).success(function(data) {
			$http({
				method: 'POST',
				url: "/credit/tools/fieldGroup/v/edit",
				data: groupList[index + 1]
			}).success(function(data) {
				$timeout(function() {
					$scope.init();
				});
			})
		})
	}
	
	$scope.down = function(groupList,index){
		var tempSort = groupList[index].sort;
		groupList[index].sort = groupList[index - 1].sort;
		groupList[index - 1].sort = tempSort;
		$http({
			method: 'POST',
			url: "/credit/tools/fieldGroup/v/edit",
			data: groupList[index]
		}).success(function(data) {
			$http({
				method: 'POST',
				url: "/credit/tools/fieldGroup/v/edit",
				data: groupList[index - 1]
			}).success(function(data) {
				$timeout(function() {
					$scope.init();
				});
			})
		})
	}
	
});