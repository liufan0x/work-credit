define(function(require, exports, module) {

	var app = require('main');

	app.controller("index",function($scope,$http,$compile,$state,$cookies,box,notice,noticeId,parent){
		$scope.isPlaceOrder = false;

		$scope.hasUserList = false;
		$scope.hasRoleList = false;
		$scope.hasAgencyList = false;
		$scope.hasAgencyTypeList = false;
		$scope.hasFundList = false;
		$scope.hasProductList = false;
		$scope.hasDeployList = false;
		$scope.hasContractList = false;
		$http({
			method: 'POST',
			url:'/credit/user/api/v/getUserDto'
		}).success(function(data){
			$scope.userDto = data;
			parent.userDto = data;
			if(parent.userDto.uid == "123456"){
				$scope.hasDeployList = true;
			}
			$scope.meunAuth = "";
			angular.forEach($scope.userDto.authIds,function(data){
				$scope.meunAuth += "auth"+data+"auth,"
			})
			$scope.hasUserList = $scope.meunAuth.indexOf('auth7auth')>=0 || $scope.meunAuth.indexOf('auth8auth') >=0;
			$scope.hasRoleList = $scope.meunAuth.indexOf('auth9auth')>=0 || $scope.meunAuth.indexOf('auth10auth') >=0;
			$scope.hasAgencyList = $scope.meunAuth.indexOf('auth11auth')>=0 || $scope.meunAuth.indexOf('auth12auth') >=0;
			$scope.hasAgencyTypeList = $scope.meunAuth.indexOf('auth13auth')>=0 || $scope.meunAuth.indexOf('auth14auth') >=0;
			$scope.hasFundList = $scope.meunAuth.indexOf('auth15auth')>=0 || $scope.meunAuth.indexOf('auth16auth') >=0;
			$scope.hasProductList = $scope.meunAuth.indexOf('auth17auth')>=0 || $scope.meunAuth.indexOf('auth18auth') >=0;
			$scope.hasMonitorArchiveList = $scope.meunAuth.indexOf('auth19auth')>=0 || $scope.meunAuth.indexOf('auth20auth') >=0;
			$scope.hasContractList = $scope.meunAuth.indexOf('auth147auth')>=0 || $scope.meunAuth.indexOf('auth148auth') >=0;
			$(".navbar-nav").show();
			$(".qingli").show();
		})
		
		$scope.hasNotice = false;
		if(notice && !$cookies.get(noticeId)){
			$scope.hasNotice = true;
		}
		
		$scope.showNotice = function(){
			$scope.notice = notice;
			$scope.hasNotice = false;
			$cookies.put(noticeId,false);
			box.editAlert($scope,"公告",$("#noticeId").html(),function(){box.closeAlert()});
		}

		$scope.showLogout = function(){
			var logout = function(){
				$http({
					method: 'POST',
					url:'/credit/user/logout'
				}).success(function(data){
					window.location.href = "/";
				})
			}
			box.confirmAlert("提示","确定退出吗？",logout);
		}

		$scope.showUpdatePwd = function(){
			$scope.pwdDto = new Object();
			var updatePwd = function(){
				$http({
					method: 'POST',
					url:'/credit/user/user/v/updataPwd',
					data:$scope.pwdDto
				}).success(function(data){
					if(data.code == 'SUCCESS'){
						box.closeAlert();
						box.boxAlert(data.msg);
						$http({
							method: 'POST',
							url:'/credit/user/base/logout'
						}).success(function(data){
							window.location.href = "/";
						})
					}else{
						$scope.pwdDto.errorInfo = data.msg;
					}
				})
			}
			box.editAlert($scope,"修改密码",$("#editId").html(),updatePwd);
		}

		$scope.goPage = function(state,params){
			$state.go(state,params);
			$cookies.remove("pageParams");
		}
	});


	angular.bootstrap(document,['anjboApp']);

	/**
	 * 鼠标划过就展开子菜单，免得需要点击才能展开
	 */
	$(document).ready(function(){
		var $dropdownLi = $('li.dropdown');
		$dropdownLi.mouseover(function() { $(this).addClass('open');}).mouseout(function() {$(this).removeClass('open');});
	});
});