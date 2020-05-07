angular.module("anjboApp").controller("agencyMaintainEditCtrl", function($scope, $http, $state, $timeout,$filter, box, route,file,FileUploader) {
	$scope.showView = 1;
	$scope.buttonTitle = "冻结账号";
	$scope.productCode = route.getParams().productCode;;
	$scope.tblName = route.getParams().tblName;
	$scope.orderNo = route.getParams().orderNo;
	$scope.m = new Object();
	$scope.m.typeDepend = "100903";
	$scope.m.title  = "合作协议";
	//获取受理员
	$http({
		method: 'post',
		url: "/credit/user/user/v/searchByType",
		data:{type:"auth",name:"提单",agencyId:"1"}
	}).success(function(data) {
		$scope.acceptList = data.data;
	});
	
	//获取渠道经理
	$http({
		method: 'post',
		url: "/credit/user/user/v/searchByType",
		data:{type:"role",name:"渠道经理",agencyId:"1"}
	}).success(function(data) {
		$scope.channelManagerList = data.data;
	});
	
	
	//获取受理经理
	$http({
		method: 'post',
		url: "/credit/user/user/v/searchByType",
		data:{type:"role",name:"受理经理",agencyId:"1"}
	}).success(function(data) {
		$scope.acceptManagerList = data.data;
	});
	
	
	//初始化数据
	$http({
		method: 'post',
		url:'/credit/data/dict/v/search',
		data:{type:"incomeMode"}
	}).success(function(data) {
		if("SUCCESS"==data.code){
			$scope.incomeModeList = data.data;
		}
	});
	//初始化数据
	$http({
		method: 'post',
		url:'/credit/data/dict/v/search',
		data:{type:"agencyType"}
	}).success(function(data) {
		if("SUCCESS"==data.code){
			$scope.agencyTypeList = data.data;
		}
	});
	//初始化数据
	$http({
		method: 'post',
		url:'/credit/data/dict/v/search',
		data:{type:"cooperativeMode"}
	}).success(function(data) {
		if("SUCCESS"==data.code){
			$scope.cooperativeModeList = data.data;
		}
	});
	
	
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
			url: "/credit/user/agency/v/find",
			data:{id:agencyId}
		}).success(function(data) {
			if("SUCCESS"==data.code){
				$scope.agencyDto = data.data;
				$scope.agencyDto.oldSurplusQuota = $scope.agencyDto.surplusQuota;

				$scope.agencyDto.oldRiskBearMultiple = $scope.agencyDto.riskBearMultiple;
				$scope.agencyDto.oldBond = $scope.agencyDto.bond;
				$scope.agencyDto.oldCooperativeModeId = $scope.agencyDto.cooperativeModeId;
				agencyData($scope.agencyDto);
				if(!$scope.agencyDto.type){
					$scope.agencyDto.type = "";
				}
				$scope.acceptListValue = new Array();
				angular.forEach($scope.agencyDto.customerAgencyAcceptDtos,function(data,index,array){
					$scope.acceptListValue.push(data.acceptUid);
				})
				$scope.agencyDto.type = String($scope.agencyDto.type);
				if($scope.agencyDto.cooperativeModeId){
					$scope.agencyDto.cooperativeModeId = String($scope.agencyDto.cooperativeModeId);
				} else {
					$scope.agencyDto.cooperativeModeId = "";
				}
				if($scope.agencyDto&&$scope.agencyDto.status==0){
					$scope.buttonTitle = "解冻账号";
				}
				$timeout(function(){
					assignment($scope.agencyDto);
				},1000);
			} else {
				box.boxAlert(data.msg);
			}

		})
	}

	function agencyData(obj){
		/*
		$http({
			method: 'post',
			url: "/credit/product/data/sm/agency/v/getAgencyApplyDate",
			data:{'agencyCode':obj.agencyCode}
		}).success(function(data) {
			if("SUCCESS"==data.code){
				$scope.m = new Object();
				angular.forEach(data.data.data,function(data1){
					if("tbl_sm_agencyWaitSign"==data1.tblName){
						$scope.m.title = "合作协议";
						$scope.m.key = "signEnclosure";
						obj.agencyWaitSign = data1.tblName;
						return;
					}
				});
			}
		});
		*/
		$scope.m = new Object();
		$scope.m.title = "合作协议";
		$scope.m.key = "signEnclosure";
		obj.agencyWaitSign = "tbl_sm_agencyWaitSign";
	}
	function assignment(obj){
		angular.forEach($scope.incomeModeList,function(data){
			angular.forEach(obj.agencyIncomeModeDtos,function(data1){
				if(data.code==data1.incomeMode){
					data.check = true;
				}
			});
		});
	}
	//编辑
	function edit() {
		var accept = new Array();
		angular.forEach($scope.tempAcceptList,function(data,index,array){
			accept.push({
				acceptUid:data,
				agencyId:$scope.agencyDto.id
			});
		});
		$scope.agencyDto.customerAgencyAcceptDtos = accept;
		// 修改机构
		$http({
			method: 'post',
			url: "/credit/user/agency/v/edit",
			data:$scope.agencyDto
		}).success(function(data) {
			box.boxAlert(data.msg);
			$timeout(function(){
				box.closeAlert();
			},3000);
		})

	}

	//取消
	$scope.cancel = function() {
		var cancelMethod = function() {
			$state.go("agencyList");
		}
		box.confirmAlert("提示", "确定取消吗？", cancelMethod);
	}
	/*
	$scope.$watch("agencyDto.isBond", function(newValue, oldValue, scope) {
		if(newValue==1){
			$scope.agencyDto.proportionResponsibility = 0;
			$scope.agencyDto.bond = 0;
			$scope.agencyDto.creditLimit = 0;
		}
	});
	*/
	//冻结与解冻
	$scope.frozenOrRelieve = function(){
		var param;
		if($scope.agencyDto.status==0){
			param = {
				"id":$scope.agencyDto.id,
				"status":1
			};
			box.editAlert($scope,"提示","确认解冻"+$scope.agencyDto.name+"的后台账号吗？",function(){
				box.closeAlert();
				$http({
					url:'/credit/customer/agency/v/update',
					data:param,
					method:'POST'
				}).success(function(data){
					box.closeAlert();
					box.boxAlert(data.msg);
					if("SUCCESS"==data.code){
						if($scope.agencyDto.status==0){
							$scope.agencyDto.status = 1;
							$scope.buttonTitle = "冻结账号";
						} else {
							$scope.agencyDto.status = 0;
							$scope.buttonTitle = "解冻账号";
						}
					}
				});
			});
		} else {
			param = {
				"id":$scope.agencyDto.id,
				"status":0
			};
			box.editAlert($scope,"提示","确认冻结"+$scope.agencyDto.name+"的后台账号吗？",function(){
				box.closeAlert();
				$http({
					url:'/credit/user/agency/v/edit',
					data:param,
					method:'POST'
				}).success(function(data){
					box.closeAlert();
					box.boxAlert(data.msg);
					if("SUCCESS"==data.code){
						if($scope.agencyDto.status==0){
							$scope.agencyDto.status = 1;
							$scope.buttonTitle = "冻结账号";
						} else {
							$scope.agencyDto.status = 0;
							$scope.buttonTitle = "解冻账号";
						}
					}
				});
			});
		}

	}
	//解除合作
	$scope.relieveCooperation = function(){
		var context = "<p>确认解除"+$scope.agencyDto.name+"该机构的合作吗？</p>"+
						"<p><font color='red'>注：解除合作请先在钉钉上提交审批；一旦解除合作后，该机构将变成普通用户.</font></p>";
		box.editAlert($scope,"提示",context,function(){
			box.closeAlert();
			$http({
				url:'/credit/user/agency/v/edit',
				data:{'id':$scope.agencyDto.id,'signStatus':3},
				method:'POST'
			}).success(function(data){
				if("SUCCESS"==data.code){
					$scope.agencyDto.signStatus=3
				}
				box.boxAlert(data.msg);
			});
		})
	}
	//定价配置
	$scope.pricingConfig = function(obj){
		var code = obj.cityCode+obj.productCode

		$state.go("agencyTypeFeescaleEdit", {
			agencyTypeId:$scope.agencyDto.agencyCode,
			agencyTypeFeescaleId:obj.feescaleId,
			agencyTypeName:"-1",
			productionid:code,
			agencyId:$scope.agencyDto.id,
			productId:obj.id,
			productCode:$scope.productCode,
			orderNo:$scope.orderNo,
			detail:0});
	}
	//流程配置
	$scope.processConfig = function(obj){
		var code = obj.cityCode+obj.productCode
		//$state.go("productEdit", {productId:code,agencyId:$scope.agencyDto.id});

		$http({
			method: 'post',
			url: "/credit/product/process/v/list",
			data:{'productId':code, 'showAgency':1, 'pageSize':50}
		}).success(function(res) {
			$scope.listProProcess = res.rows;
			$timeout(function(){
				box.alertPanel2($scope, $("#panelProductProcess").html());
			});
		});
	}
	$scope.$watch('agencyDto.cooperativeModeId',function(newValue,oldValue){
		if(newValue==1&&$scope.agencyDto.oldCooperativeModeId==2){
			getOrderLoan();
		}
		if(newValue&&2==newValue&&newValue!=oldValue){
			$scope.agencyDto.proportionResponsibility = 0;
		} else if(newValue&&1==newValue&&newValue!=oldValue) {
			$scope.agencyDto.proportionResponsibility = 100;
		}
	});
	//授信额度=初始保证金*风险承担倍数
	$scope.$watch('agencyDto.bond',function(newValue,oldValue){
		if(1==$scope.agencyDto.cooperativeModeId){
			if(newValue&&newValue!=oldValue){
				$scope.agencyDto.creditLimit = mul($scope.agencyDto.riskBearMultiple,newValue);
				if($scope.agencyDto.creditLimit){
					$scope.agencyDto.surplusQuota = surplusQuota($scope.agencyDto.creditLimit);
				}
			}
		}

	});
	$scope.$watch('agencyDto.riskBearMultiple',function(newValue,oldValue){
		if(1==$scope.agencyDto.cooperativeModeId) {
			if (newValue && newValue != oldValue) {
				$scope.agencyDto.creditLimit = mul($scope.agencyDto.bond, newValue);
				if ($scope.agencyDto.creditLimit) {
					$scope.agencyDto.surplusQuota = surplusQuota($scope.agencyDto.creditLimit);
				}
			}
		}
	});
	function surplusQuota(surplusQuota){
		var nowSurplusQuota;
		if(1==$scope.agencyDto.cooperativeModeId&&2==$scope.agencyDto.oldCooperativeModeId) {
			nowSurplusQuota = surplusQuota-$scope.tmpSurplusQuota;
		} else{
			//旧的剩余额度
			var oldSurplusQuota = mul($scope.agencyDto.oldRiskBearMultiple,$scope.agencyDto.oldBond)
			var useAmount = oldSurplusQuota - $scope.agencyDto.oldSurplusQuota;
			nowSurplusQuota = surplusQuota-useAmount;
		}
		if(nowSurplusQuota){
			nowSurplusQuota = parseFloat(nowSurplusQuota).toFixed(2)
		}
		return nowSurplusQuota;
	}
	$scope.agencyEdit = function() {
		$scope.incomeModeListErroShow = false;
		//校验
		if($scope.agencyForm.$invalid){
			//$scope.agencyForm.name.$dirty=true;
			$scope.agencyForm.simName.$dirty=true;
			$scope.agencyForm.contactMan.$dirty=true;
			$scope.agencyForm.contactTel.$dirty=true;
			$scope.agencyForm.chanlMan.$dirty=true;
			//$scope.agencyForm.acceptManagerUid.$dirty=true;

			$scope.agencyForm.agencyType.$dirty=true;
			$scope.agencyForm.cooperativeModeId.$dirty=true;
			$scope.agencyForm.chargeStandard.$dirty=true;
			//$scope.agencyForm.isBond.$dirty=true;
			$scope.agencyForm.proportionResponsibility.$dirty=true;
			$scope.agencyForm.minBond.$dirty=true;
			if($scope.agencyDto.cooperativeModeId&&$scope.agencyDto.cooperativeModeId==1){
				$scope.agencyForm.surplusQuotaRemind.$dirty=true;
				$scope.agencyForm.creditLimit.$dirty=true;
				$scope.agencyForm.riskBearMultiple.$dirty=true;
				$scope.agencyForm.bond.$dirty=true;
			}
			box.boxAlert("请检查红色提示信息!");
			return;
		}
		var index = 0;
		var modeList = new Array();
		angular.forEach($scope.incomeModeList,function(data){
			if(data.check){
				modeList.push({
					"agencyId":$scope.agencyDto.id,
					"incomeMode":data.code,
					"name":data.name
				});
				index++;
			}
		});
		if(index<=0){
			$scope.incomeModeListErroShow = true;
			box.boxAlert("请检查红色提示信息!");
			return;
		}
		$scope.agencyDto.agencyIncomeModeDtos = modeList;
		if($scope.agencyDto&&$scope.agencyDto.contactTel){
			$scope.agencyDto.contactTel = $scope.agencyDto.contactTel.trim();
		}
		edit();
	}

	//多选框赋值
	$scope.applyProductCheck = function(c){
		angular.forEach($scope.incomeModeList,function(data,index,array){
			if(data.code== c.code){
				data.check = !data.check;
				return
			}
		});
	}

	$scope.restartPassworld = function(){
		$scope.title = "确认重置"+$scope.agencyDto.name+"机构的登录密码吗？";
		$scope.mobile = $scope.agencyDto.contactTel;

		box.editAlert($scope,"提示","<restart-pass-box-text></restart-pass-box-text>",function(){
			$http({
				url:'/credit/user/user/v/resetPwd',
				method:'POST',
				data:{"account":$scope.agencyDto.manageAccount,"mobile":$scope.mobile}
			}).then(function successCallback(response){
				box.closeAlert();
				if("SUCCESS"==response.data.code){
					box.boxAlert("重置密码成功后，我们将会短信通知机构联系人.");
				} else {
					box.boxAlert(response.data.msg);
				}
			},function errorCallback(response){
				box.boxAlert("请求失败，请联系开发部");
			});
		});
	}
	var upload = file.fileuploader($scope,FileUploader,box);

	function mul(a, b) {
		if(!a||!b){
			return 0;
		}
		var c = 0,
			d = a.toString(),
			e = b.toString();
		try {
			c += d.split(".")[1].length;
		} catch (f) {}
		try {
			c += e.split(".")[1].length;
		} catch (f) {}
		return Number(d.replace(".", "")) * Number(e.replace(".", "")) / Math.pow(10, c);
	}

	function getOrderLoan(){
		$http({
			url:'/credit/order/base/v/getOrderLoan',
			method:'POST',
			data:{"cooperativeAgencyId":$scope.agencyDto.id}
		}).then(function successCallback(response){
			if("SUCCESS"==response.data.code){
				$scope.tmpSurplusQuota = 0;
				if(response.data.data){
					$scope.tmpSurplusQuota = response.data.data.borrowingAmount;
				}
				if(!$scope.tmpSurplusQuota){
					$scope.tmpSurplusQuota = 0;
				}
				if($scope.agencyDto.creditLimit){
					$scope.agencyDto.surplusQuota = $scope.agencyDto.creditLimit-$scope.tmpSurplusQuota;
				}
			}
		},function errorCallback(response){
			console.log("请求失败，请联系开发部");
		});
	}
}).directive("agencyInfoEdit",function($http,route) {
	return {
		restrict: "E",
		templateUrl: '/template/agency/edit/agencyInfoEdit.html',
		transclude: true,
		link: function (scope) {
			scope.obj = new Object();
		}
	};
}).directive("restartPassBoxText",function($http,route) {
	return {
		restrict: "E",
		templateUrl: '/template/agency/common/restartPassBoxText.html',
		transclude: true,
		link: function (scope) {
			scope.obj = new Object();
		}
	};
});