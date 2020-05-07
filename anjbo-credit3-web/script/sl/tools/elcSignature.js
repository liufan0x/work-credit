angular.module("anjboApp", ['bsTable']).controller("elcSignature", function ($scope, $compile, $http, $state, box, process, parent) {
	$scope.huarList = [{}]
   
	$scope.$watch("newContractName", function (newVal, old) {
		$http.post('/credit/order/baseList/v/selectAbleRelationOrder', {
			customerName: newVal
		}).then(function (res) {
			$scope.RelationOrder = res.data.data
		})
	})  
	
	$scope.huarList = new Array();
	$scope.huarList.push(new Object());
	$scope.$watch("RelationOrderNo",function(newVal, old){
		if(newVal){
			$http({
				method: 'POST',
				url: '/credit/order/baseCustomer/v/allCustomerNos',
				data:{
					orderNo: newVal
				}
			}).then(function (res2) {
				$scope.huarList = res2.data;
				if ($scope.huarList=="") {
					$scope.huarList = new Array();
					$scope.huarList.push(new Object());
				}
				angular.forEach($scope.huarList, function (data) {
					if(data.customerType){
						data.customerType = String(data.customerType);
					}else{
						data.customerType = "1";
					}
				});
			})
		}else{
			$scope.huarList = new Array();
			var obj = new Object();
			obj.customerType = "1";
			$scope.huarList.push(obj);
		}
	})


	

	//重置所有
	$scope.reset = function () {
		
		var del=confirm("重置后不可撤回，是否确认？");
		if(del==true){
			$scope.newContractName='';
			$scope.RelationOrder ='无';
			$scope.huarList=[{}];
			$("#dzDel").hide();
			$("#hrSel").hide();
			$("#count").text("0");
			$("#huarongImg").val("");
			$("#hrSel").removeAttr("href");
			$("#hrSel").removeAttr("target");
			location.reload();
		}else{
			return;
		}
	}

	$scope.huarongDel=function(e){
		console.log(1)
		//$scope.huarList.splice(e);
		 for (var i = 0; i <$scope.huarList.length; i++) {
                if ($scope.huarList[i]== $scope.huarList[e]) {
                	$scope.huarList.splice(i,1);
                }
	    }
	}

	$scope.huarongAdd=function(e){
		if($scope.huarList.length<=7){
			var obj = new Object();
			obj.customerType = "1";
			$scope.huarList.push(obj);
		}else{
			box.boxAlert("最多只可添加8个借款人信息");
		}
	}

	$scope.test=function(){
		console.log($scope)
	}

	$scope.setType = function(typeId) {
		$scope.typeId = typeId;
	}

	$scope.toAddSignature = function() {
		if($scope.huarList!=null&&$scope.huarList.length>0 ){
			for(var i=0;i<$scope.huarList.length;i++){
				if($scope.huarList[i].customerName ==null || $scope.huarList[i].customerName ==""){
					if($scope.huarList[i].customerType ==1){
						box.boxAlert("请输入借款人信息"+(i+1)+" 借款人姓名");
					}else{
						box.boxAlert("请输入借款人信息"+(i+1)+" 公司名称");
					}
					return;
				}
				if($scope.huarList[i].customerCardNumber ==null || $scope.huarList[i].customerCardNumber ==""){
					if($scope.huarList[i].customerType ==1){
						box.boxAlert("请输入借款人信息"+(i+1)+" 身份证号码");
					}else{
						box.boxAlert("请输入借款人信息"+(i+1)+" 证件号码");
					}
					return;
				}
				if($scope.huarList[i].customerType ==2 && ($scope.huarList[i].customerCardType ==null || $scope.huarList[i].customerCardType =="" )){
					box.boxAlert("请选择借款人信息"+(i+1)+" 证件类型");
					return;
				}
			}
		}
		var img=$("#huarongImg").val();
		if(typeof(img)=="img" ||img==null ||img==""){
			box.boxAlert("请上传借款合同!");
			return false;
		}
		var param = {
				orderNo: $scope.orderNo,
				cusList:$scope.huarList,
				signatureImg:img
		}
		$(".lhw-alert-ok").attr("disabled", "disabled");
		box.waitAlert();
		$http({
			url: '/credit/third/api/signature/v/stamp',
			method: 'POST',
			data: param
		}).success(function(data) {
			box.closeWaitAlert();
			box.closeAlert();
			box.boxAlert(data.msg, function() {
				if(data.code == "SUCCESS") {
					box.closeAlert();
					if(data.data!=null){
						window.open(data.data.url);     
					}
				}
			});
		});

	}
});