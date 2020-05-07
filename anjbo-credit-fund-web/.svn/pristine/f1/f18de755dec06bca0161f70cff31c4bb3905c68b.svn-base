angular.module("anjboApp", ['ngCookies'])
	.config(['$httpProvider', function($httpProvider) {
		$httpProvider.defaults.withCredentials = true;
	}]).controller("login", function($scope, $http, $cookies) {

		function checkBrowser() {

			var is360 = navigator.mimeTypes[2].type == 'application/vnd.chromium.remoting-viewer' ? true : false;
			var isChrome = navigator.userAgent.indexOf("Chrome") > -1 && !is360 && navigator.userAgent.indexOf("Edge") < 0;
			if(!isChrome) {
				alert("请下载谷歌浏览器后再执行此操作");
				location.href = 'http://www.google.cn/chrome/browser/desktop/';
			}
		};
		checkBrowser();

		$scope.validateCodeUrl = '/credit/user/auth';
		$scope.changeValidateCode = function() {
			$scope.validateCodeUrl = '/credit/user/auth?' + Math.random();
		}
		$scope.myKeyup = function(e) {
			var keycode = window.event ? e.keyCode : e.which;
			if(keycode == 13) {
				$scope.login();
			}
		};
		$scope.login = function() {
			if($scope.loginForm.$invalid) {
				$scope.loginForm.userAccount.$dirty = true;
				$scope.loginForm.userPassword.$dirty = true;
				$scope.loginForm.validateCode.$dirty = true;
				return false;
			}
			$http({
				method: 'POST',
				url: '/credit/user/login',
				data: {
					"userAccount": $scope.userAccount,
					"userPassword": $scope.userPassword,
					"validateCode": $scope.validateCode,
					"platformCode": "ORG"
				}
			}).success(function(data) {
				if(data.code == "SUCCESS") {
					$cookies.remove("pageParams");
					data.data.authIds = new Array();
					$cookies.putObject("userDto", data.data);
					window.location.href = "index.html#/orderList";
				} else {
					$scope.changeValidateCode();
					$scope.errorTxt = data.msg;
				}
			})
		}
	});