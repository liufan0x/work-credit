define(function(require, exports, module) {

	var app = require('main');

	app.controller("index", function($scope, $http, $compile, $cookies, $state, $websocket, $timeout, box, notice, noticeId, parent, placeOrderAuthIds) {
		$scope.isPlaceOrder = false;
		//		if(localStorage.getItem('userDto')){
		//			parent.userDto = JSON.parse(localStorage.getItem('userDto'));
		//		}

		function websocketText() {
			ws = $websocket("wss://" + document.domain + "/pushWebSocket/" + $scope.userDto.uid);
			ws.onMessage(function(e) {
				Notification.requestPermission(function(status) {
					var n = new Notification('你有新订单需要处理', {
						body: e.data,
						icon: 'images/icon.png',
					});
				});
			});
			ws.onClose(function(e) {
				websocketText();
			});
		}
		
		$http({
			method: 'POST',
			url: '/credit/user/api/v/getUserDto'
		}).success(function(data) {
			$scope.userDto = data;
			parent.userDto = data;
			//$cookies.putObject("userDto",JSON.stringify(parent.userDto));
			localStorage.setItem('userDto', JSON.stringify(parent.userDto));
			var authIds = placeOrderAuthIds.split(',');
			$scope.meunAuth = "";
			angular.forEach(parent.userDto.authIds, function(data) {
				if($.inArray(data, authIds) >= 0) {
					$scope.isPlaceOrder = true;
				}
				$scope.meunAuth += "auth" + data + "auth,";
			})
			$scope.hasKuaige = $scope.userDto.agencyId==1;
			$scope.hasMonitorArchiveList = $scope.meunAuth.indexOf('auth19auth') >= 0 || $scope.meunAuth.indexOf('auth20auth') >= 0;
			$scope.hasRiskModelList = $scope.meunAuth.indexOf('auth21auth') >= 0 || $scope.meunAuth.indexOf('auth22auth') >= 0;
			$scope.hasCapitalList = $scope.meunAuth.indexOf('auth23auth') >= 0 || $scope.meunAuth.indexOf('auth24auth') >= 0;
			$scope.hasCustomerList = $scope.meunAuth.indexOf('auth25auth') >= 0 || $scope.meunAuth.indexOf('auth26auth') >= 0;
			$scope.hasRiskContolList = $scope.meunAuth.indexOf('auth80auth') >= 0 || $scope.meunAuth.indexOf('auth81auth') >= 0;
			$scope.hasRunningList = $scope.meunAuth.indexOf('auth82auth') >= 0 || $scope.meunAuth.indexOf('auth83auth') >= 0;
			$scope.hasCurrentList = $scope.meunAuth.indexOf('auth84auth') >= 0 || $scope.meunAuth.indexOf('auth85auth') >= 0;
			$scope.hasrevenueList = $scope.meunAuth.indexOf('auth86auth') >= 0 || $scope.meunAuth.indexOf('auth87auth') >= 0;
			$scope.hasFinancialList = $scope.meunAuth.indexOf('auth88auth') >= 0 || $scope.meunAuth.indexOf('auth89auth') >= 0;
			$scope.hasCeoRight = $scope.meunAuth.indexOf('auth88auth') >= 0 || $scope.meunAuth.indexOf('auth89auth') >= 0;
			$scope.hasFundList = $scope.meunAuth.indexOf('auth5auth') >= 0 || $scope.meunAuth.indexOf('auth6auth') >= 0;
			$scope.hasImgList = $scope.meunAuth.indexOf('auth3auth') >= 0 || $scope.meunAuth.indexOf('auth4auth') >= 0;
			$scope.hasBoxList = $scope.meunAuth.indexOf('auth29auth') >= 0 || $scope.meunAuth.indexOf('auth30auth') >= 0;
			$scope.hasEleAccessList = $scope.meunAuth.indexOf('auth31auth') >= 0 || $scope.meunAuth.indexOf('auth32auth') >= 0;
			$scope.hasAuditList = $scope.meunAuth.indexOf('auth33auth') >= 0 || $scope.meunAuth.indexOf('auth34auth') >= 0;
			$scope.haschkbbListA = $scope.meunAuth.indexOf('auth180auth') >= 0;
			$scope.haschkbbListB = $scope.meunAuth.indexOf('auth181auth') >= 0;
			$scope.hasDaiHouListA = $scope.meunAuth.indexOf('auth141auth') >= 0;
			$scope.hasDaiHouListB = $scope.meunAuth.indexOf('auth142auth') >= 0;
			$scope.hasTaoDaListA = $scope.meunAuth.indexOf('auth143auth') >= 0;
			$scope.hasTaoDaListB = $scope.meunAuth.indexOf('auth144auth') >= 0;
			$scope.hasDianZiQianZhangList = $scope.meunAuth.indexOf('auth145auth') >= 0 || $scope.meunAuth.indexOf('auth146auth') >= 0;
			console.log($scope)
			$(".navbar-nav").show();
			$(".lhw-alert-bg").show();

			/**
			 * 鼠标划过就展开子菜单，免得需要点击才能展开
			 */
			$timeout(function() {
				var $dropdownLi = $('li.dropdown');
				var $statistical = $('#statistical');
				$dropdownLi.mouseover(function() {
					$(this).addClass('open');
				}).mouseout(function() {
					$(this).removeClass('open');
				});
			});

			websocketText();

		})

		$scope.noticeShow = false;
		$scope.hasNotice = false;
		if(notice && !$cookies.get(noticeId)) {
			$scope.hasNotice = true;
		}

		$scope.showNotice = function() {
			$scope.noticeShow = true;
			$scope.notice = notice;
			$scope.hasNotice = false;
			$cookies.put(noticeId, false);
		}

		$scope.showLogout = function() {
			var logout = function() {
				$http({
					method: 'POST',
					url: '/credit/user/logout'
				}).success(function(data) {
					window.location.href = "/";
				})
			}
			box.confirmAlert("提示", "确定退出吗？", logout);
		}

		$scope.showUpdatePwd = function() {
			$scope.pwdDto = new Object();
			var updatePwd = function() {
				$http({
					method: 'POST',
					url: '/credit/user/user/v/updataPwd',
					data: $scope.pwdDto
				}).success(function(data) {
					if(data.code == 'SUCCESS') {
						box.closeAlert();
						box.boxAlert(data.msg);
						$http({
							method: 'POST',
							url: '/credit/user/logout'
						}).success(function(data) {
							window.location.href = "/";
						})
					} else {
						$scope.pwdDto.errorInfo = data.msg;
					}
				})
			}
			box.editAlert($scope, "修改密码", $("#editId").html(), updatePwd);
		}

		$scope.goPage = function(state, params) {
			$state.go(state, params);
			$cookies.remove("pageParams");
		}

	});

	angular.bootstrap(document, ['anjboApp']);

});