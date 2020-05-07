angular.module("anjboApp").controller("userEditCtrl", function($scope, $state, $http, box, route) {

	
	var uid = route.getParams().uid
	if(uid == '-1'){
		$scope.user = new Object();
		$scope.user.identity = 0;
		$scope.isAdd = true;
	}else{
		$scope.isAdd = false;
		$http({
			method: 'post',
			url: "/credit/user/user/v/find",
			data:{'uid':uid}
		}).success(function(data) {
			$scope.user = data.data;
			$scope.user.roleId = String($scope.user.roleId);
			$scope.user.deptId = String($scope.user.deptId);
		})
	}
	
	$http({
		method: 'post',
		url: "/credit/user/role/v/search",
		data:{'agencyId':1}
	}).success(function(data) {
		$scope.roleList = data.data;
	})
	
    $http({
		method: 'POST',
		url:'/credit/data/dict/v/search',
		data:{type:"cityList"}
	}).success(function(data){
		$scope.cityList = data.data;
		$scope.cityList.unshift({id:"",name:"请选择"});
	})

	$http({
		method: 'post',
		url: "/credit/user/dept/v/search",
		data:{'agencyId':1}
	}).success(function(data) {
		$scope.deptList = data.data;
//		var deptIdArray = $scope.user.deptIdArray.split(",");
//		angular.forEach(data.data, function(row) {
//		  	angular.forEach(deptIdArray, function(data) {
//		  		if(row.id == data){
////		  			console.log(row.id +">>"+ data);
//		  			$scope.deptNames = (undefined==$scope.deptNames?"":$scope.deptNames+"/")+row.name;		  			
//		  		}
//			});
//		});
	})


	//生成管理账号
	$scope.generateAccount = function() {
		//校验
		if($scope.user.name) {
			$http({
				method: 'post',
				url: "/credit/user/user/v/gennerAccountPwd",
				data:{"name":$scope.user.name}
			}).success(function(data) {
				$scope.user.account = data.data.account;
				$scope.user.password = data.data.pwd;
			});
		}else{
			box.boxAlert("请输入姓名");
			$scope.user.account = "";
			$scope.user.password = "";
		}
		return;
	}

	//添加或新增
	//iscContinue 是否继续添加
	$scope.addOrEdit = function(iscContinue) {
		//校验
		if($scope.userForm.$invalid) {
			$scope.userForm.name.$dirty = true;
			$scope.userForm.idCard.$dirty = true;
			$scope.userForm.mobile.$dirty = true;
			$scope.userForm.roleId.$dirty = true;
			$scope.userForm.deptId.$dirty = true;
			$scope.userForm.cityCode.$dirty = true;
			$scope.userForm.account.$dirty = true;
			$scope.userForm.password.$dirty = true;
			return false;
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
		//添加用户
		$http({
			method: 'POST',
			url: "/credit/user/base/v/insertUser",
			data: $scope.user
		}).success(function(data) {
			box.boxAlert(data.msg);
			if(data.code == 'SUCCESS'){
				if(iscContinue){
					$state.reload();
				}else{
					$state.go("userList");
				}
			}
		})
	}

	//编辑
	function edit() {
		//修改用户
		$http({
			method: 'post',
			url: "/credit/user/user/v/edit",
			data:$scope.user
		}).success(function(data) {
			if(data.code == 'SUCCESS'){
				$state.go("userList");
			}
			box.boxAlert(data.msg);
		})
	}

	//取消
	$scope.cancel = function() {
		var cancelMethod = function() {
			$state.go("userList");
		}
		box.confirmAlert("提示", "确定取消吗？", cancelMethod);
	}

});