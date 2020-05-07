
angular.module("anjboApp").controller("fundDockingEditCtrl", function($scope, $compile, $http, $state, box) {

	function loadFund() {
		$http({
			url: '/credit/order/allocationFund/v/processDetails',
			method: 'POST',
			data: {
				orderNo: $scope.orderNo
			}
		}).success(function(data) {
			if("SUCCESS" == data.code) {
				$scope.dataList = data.data;
				angular.forEach($scope.dataList, function(data, index, array) {
					if(data.fundCode == "114" || data.fundCode == "115") {
						$scope.yunNanAccoumt(data.fundCode);
					}
					if (data.fundCode == "1000") {
						$scope.sgtAccoumt();
					}
				});
			} else {
				box.boxAlert(data.msg);
			}
		});
		
		
		$http({//获取推送状态
			method: 'POST',
			url: '/credit/third/api/sgtongBorrowerInformation/v/searchPushStatus2',
			data: {
				'orderNo': $scope.orderNo
			}
		}).success(function(data) {
			 if(data.data.pushstatus=="SUCCESS"){
				
						$scope.pushstatus='已推送';
					
				 }else{
					 $scope.pushstatus='未推送'; 
					 
				 
			 }
		})
		
		
		
		
	}
	loadFund();

	$scope.uploadInfo = function(code, obj) {
		console.log(code);
		$scope.fundCode = code;
		$scope.ordinaryFundId = obj.fundId
		var htmlstring = "";
		if(code == 110) {
			htmlstring = '<huarong-docking-edit ></huarong-docking-edit>';
		} else if(code == 114 || code == 115) {
			if(code == 114) {
				$scope.ynProductCode = "I16800";
			} else {
				$scope.ynProductCode = "I22500";
			}
			htmlstring = '<yunnan-docking-edit  ></yunnan-docking-edit>';
		}
		else if(code == '1000'){
			htmlstring = "<shanguotou-docking-edit></shanguotou-docking-edit>";
		}
		else if(code == '113'){
			htmlstring = '<erongsuo-docking-edit></erongsuo-docking-edit>';
		}
		else{
			htmlstring = '<ordinary-docking-edit ></ordinary-docking-edit>';
		}
		var el = $compile(htmlstring)($scope);
		$('body').append(el);
	}

	//退回
	$scope.backToSubmit = function() {
		box.editAlert($scope, "订单退回，请选择退回对象。", "<back-box></back-box>", function() {
			$scope.backOrder();
		});
	}

	function orderIsBack() {
		var param = {
			orderNo: $scope.orderNo,
			processId: 'fundDocking'
		}
		$http({
			url: '/credit/risk/base/v/orderIsBack',
			data: param,
			method: 'POST'
		}).success(function(data) {
			if("SUCCESS" == data.code) {
				$scope.orderIsBack = data.data;
			}
		});
	}
	orderIsBack();

	$scope.toFinance = function() {
		var submitFinance = function() {
			var param = {
				orderNo: $scope.orderNo,
				relationOrderNo: $scope.relationOrderNo,
				remark: $("#remark").val()
			}
			$(".lhw-alert-ok").attr("disabled", "disabled");
			box.waitAlert();
			$http({
				url: '/credit/order/auditFundDocking/v/processSubmit',
				method: 'POST',
				data: param
			}).success(function(data) {
				box.closeWaitAlert();
				box.closeAlert();
				box.boxAlert(data.msg, function() {
					if(data.code == "SUCCESS") {
						box.closeAlert();
						$state.go("orderList");
					}
				});
			});
		}
		box.editAlert($scope, "提交", "确定提交资料推送信息吗？", submitFinance);
	}

	$scope.yunnanRefresh = function(fl) {
		$http({
			method: 'POST',
			url: '/credit/third/api/yntrust/v/ynStuats',
			data: {
				'orderNo': $scope.orderNo,
				'ynProductCode': $scope.ynProductCode
			}
		}).success(function(res) {
			$scope.ynStatus = res.data
			/*if(fl) {
				if(res.code == 'SUCCESS') {  同下   需要弹出框提示去掉注释即可  
					box.boxAlert("刷新成功444444");
				} else {
					box.boxAlert("刷新失败");
				}
			}*/
		});
	}

	$scope.huarongRefresh1 = function(fl) {
		$http({
			method: 'POST',
			url: '/credit/third/api/hr/v/allStatus',
			data: {
				'orderNo': $scope.orderNo
			}
		}).success(function(res) {
			/*if(!fl) {

			if(fl) {
				if(res.code == 'SUCCESS') {
					box.boxAlert('刷新成功5555')
				} else {
					box.boxAlert('刷新失败')
				}
			}*/
			$scope.huarongMsg = res.msg;
		});
	}

	$scope.yunNanAccoumt = function(code, type) {
		if(code == 114) {
			$scope.ynProductCode = "I16800";
		} else if(code == 115) {
			$scope.ynProductCode = "I22500";
		}
		$http({
			url: '/credit/third/api/yntrust/v/queryTrustAccount',
			method: 'POST',
			data: {
				'orderNo': $scope.orderNo,
				'ynProductCode': $scope.ynProductCode
			}
		}).success(function(data) {
			$scope.ynAccoumt = new Object();
			if("SUCCESS" == data.code) {
				if(type) {
					box.boxAlert("刷新成功");
				}
				$scope.ynAccoumt = data.data;
			}
		});
	}
	
	$scope.sgtAccoumt = function(type) {
		$http({
			url: '/credit/third/api/sgtongBorrowerInformation/v/text',
			method: 'POST',
			data: {
				'brNo': '0004',
				'cardChno': 'CL0005',
				'acNo':'000003_03_00040004'
			}                                                              
		}).success(function(data) {
			$scope.sgtAccoumts = new Object();
			if("SUCCESS" == data.code) {
				if(type) {
				box.boxAlert("刷新成功");
				}
				$scope.sgtAccoumts = data.data;
			}
		});
	}
	
	

	$scope.getContractFile = function() {
		$http({
			method: 'POST',
			url: "/credit/third/api/yntrust/v/getContractFile",
			data: {
				orderNo: $scope.orderNo
			}
		}).success(function(data) {
			if(data.msg == "合同文件不存在") {
				data.msg = "请先让受理员签署合同";
			}
			if(data.code == 'SUCCESS') {
				window.open("/credit/third/api/yntrust/v/downloadContractFile?orderNo="+$scope.orderNo);
			} else {
				if(data.msg == "没有获取到与该订单号关联的uniqueId的信息") {
					box.boxAlert("该订单已撤回，请退回重新资料推送");
				} else {
					box.boxAlert(data.msg);
				}
			}
		});
	}
	
});

function lookImg() {
	$.openPhotoGallery($(".huarong-img-view").next("img"), "deleteHuarongUrl");
}