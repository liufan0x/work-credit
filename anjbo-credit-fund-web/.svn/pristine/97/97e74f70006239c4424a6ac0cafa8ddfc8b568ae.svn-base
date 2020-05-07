define(function(require, exports, module) {

	var app = require('main');

	app.controller("index",function($scope,$http,$compile,$cookies,$state,$timeout,box,notice,noticeId,parent,placeOrderAuthIds){
		$scope.isPlaceOrder = false;
		
//		if($cookies.getObject("userDto")){
//			parent.userDto = $cookies.getObject("userDto");
//		}
		
		$http({
			method: 'POST',
			url:'/credit/user/base/v/getUser'
		}).success(function(data){
			$scope.userDto = data.data;
			$scope.isShow=false;
			if ($scope.userDto.fundId==31) {
				$scope.isShow=true;
			}
			parent.userDto = data.data;
//			$cookies.putObject("userDto",parent.userDto);
			$scope.hasImgList = true;
			$(".navbar-nav").show();
			$(".lhw-alert-bg").show();
			
			/**
			 * 鼠标划过就展开子菜单，免得需要点击才能展开
			 */
			$timeout(function(){
				var $dropdownLi = $('li.dropdown');
				$dropdownLi.mouseover(function() { $(this).addClass('open');}).mouseout(function() {$(this).removeClass('open');});
			});
			
		})
		
		
		$scope.noticeShow = false;
		$scope.hasNotice = false;
		if(notice && !$cookies.get(noticeId)){
			$scope.hasNotice = true;
		}
		
		$scope.showNotice = function(){
			$scope.noticeShow = true;
			$scope.notice = notice;
			$scope.hasNotice = false;
			$cookies.put(noticeId,false);
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
					url:'/credit/user/base/v/updataPwd',
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

	
});