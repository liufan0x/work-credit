
angular.module("anjboApp").controller("allocationFundEditCtrl",function($scope,route,$http,$state,box){

	$scope.obj = new Object();
	$scope.huaan = new Object();
	$scope.huaanTmp = new Object();

	$scope.isPush = false;//是否需要推送

	$scope.obj.orderNo = $scope.orderNo;
	$scope.huanShow = false; //推送页面
	$scope.fundShow = true;	//资金方
	$scope.title = "orderShow"; //推送Tab
	$scope.fund = null; //资金方
	$scope.selectTypeId = 0; //typeId
	$scope.isDelImgShow = false; //删除图片
	$scope.allCheck = false;	//所有图片
	$scope.orderIsBack = false;

	/***********华融start**********/
	$scope.huarongShow = false;//华融
	$scope.huarongCode = 110;
	$scope.huarongTitle = 'huarongOrderShow';
	$scope.isDelHrImgShow = false;
	//申请预约信息

	$scope.appoint = new Object();
	$scope.appoint.mtdCde = "按日计息,利随本清";//还款方式
	$scope.appoint.useHr = "短期周转";//借款用途字典项
	$scope.appoint.applyTnrUnit = "日";//借款期限单位
	$scope.appoint.productId = "KG001";//产品编号
	$scope.appoint.loanCooprCode = "KG";//助贷商编码
	$scope.appoint.orderNo = $scope.orderNo;

	//申请人信息
	$scope.applAppt = new Object();
	$scope.applAppt.orderNo = $scope.orderNo;
	//申请人详细信息
	$scope.apptIndiv = new Object();
	$scope.apptIndiv.orderNo = $scope.orderNo;

	//快鸽提单信息-业务信息
	$scope.kgAppoint = new Object();
	$scope.kgAppoint.orderNo = $scope.orderNo;

	//快鸽提单信息-借款人信息
	$scope.kgIndiv = new Object();
	$scope.kgIndiv.orderNo = $scope.orderNo;

	//快鸽提单信息-房产信息
	$scope.kgHouse = new Object();
	$scope.kgHouse.orderNo = $scope.orderNo;

	//快鸽提单信息-房产信息
	$scope.kgApproval = new Object();
	$scope.kgApproval.orderNo = $scope.orderNo;

	//快鸽提单信息-房产信息
	$scope.kgLoan = new Object();
	$scope.kgLoan.bchCde = "KG";//机构代码
	$scope.kgLoan.bchName = "快鸽按揭";//机构名称
	$scope.kgLoan.applyTnrUnit = "日";//申请期限单位
	$scope.kgLoan.mtdCde = "按日计息,利随本清";//还款方式
	$scope.kgLoan.ifCeOut = "Y";//是否差额打款
	$scope.kgLoan.orderNo = $scope.orderNo;
	$scope.isSubmitShow = true;
	/***********华融end*************/

	//加载资金分配方信息
	loadFund = function(){
		var param = {
			status:1
		}
		$http({
			url:'/credit/customer/fund/v/list',
			method:'POST',
			data:param
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.dataList = data.data;
				angular.forEach($scope.dataList,function(data,index,array){
					data.isCheck = false;
				});
			} else {
				box.boxAlert(data.msg);
			}
		});
	}
	loadFund();

	orderIsBack = function(){
		var param = {
			orderNo:$scope.orderNo,
			processId:'allocationFund'
		}
		$http({
			url:'/credit/risk/base/v/orderIsBack',
			data:param,
			method:'POST'
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.orderIsBack = data.data;
			}
		});
	}
	orderIsBack();

	setTimeout(setHuaanData,3000);

	function setHuaanData(){
		$scope.huaanTmp.phoneNumber = $scope.borrow.phoneNumber;
		$scope.huaanTmp.serviceType = null==$scope.borrow.productCode?"":$scope.borrow.productCode;
		$scope.huaanTmp.productName = $scope.borrow.productName;
		$scope.huaanTmp.loanAmount = $scope.borrow.loanAmount;

		$scope.huaanTmp.isOldLoanBank = $scope.borrow.isOldLoanBank;
		if(null!=$scope.huaanTmp.isOldLoanBank&&$scope.huaanTmp.isOldLoanBank==1){
			$scope.huaanTmp.houseLoanBankId = null==$scope.borrow.loanBankNameId?"":$scope.borrow.loanBankNameId;
			$scope.huaanTmp.houseLoanSubBankId = null==$scope.borrow.loanSubBankNameId?"":$scope.borrow.loanSubBankNameId;
		} else {
			$scope.huaanTmp.houseLoanAddress = $scope.borrow.loanBankName;
		}
		$scope.huaanTmp.customerName = $scope.customer.customerName;
		$scope.huaanTmp.idCardNo = $scope.customer.customerCardNumber;
		$scope.huaanTmp.customerMarriageState = $scope.customer.customerMarriageState;
		$scope.huaanTmp.customerWifeName = $scope.customer.customerWifeName;

		var customerGuaranteeList = $scope.customer.customerGuaranteeDto;

		if(null!=customerGuaranteeList&&customerGuaranteeList.length>0){
			$scope.huaanTmp.guaranteeCardNumber = customerGuaranteeList[0].guaranteeCardNumber;
			$scope.huaanTmp.guaranteeName = customerGuaranteeList[0].guaranteeName;
			$scope.huaanTmp.guaranteeRelationship = customerGuaranteeList[0].guaranteeRelationship;
		}

		$scope.huaanTmp.oldHouseLoanBalance = $scope.house.oldHouseLoanBalance;
		$scope.huaanTmp.houseSuperviseAmount = $scope.house.houseSuperviseAmount;
		$scope.huaanTmp.houseLoanAmount = $scope.house.houseLoanAmount;

		var orderBaseHousePropertyList = $scope.house.orderBaseHousePropertyDto;
		if(null!=orderBaseHousePropertyList&&orderBaseHousePropertyList.length>0){
			$scope.huaanTmp.houseName = orderBaseHousePropertyList[0].houseName;
			$scope.huaanTmp.housePropertyNumber = orderBaseHousePropertyList[0].housePropertyNumber;
		}
		var propertyList = $scope.house.orderBaseHousePropertyPeopleDto;
		if(null!=propertyList&&propertyList.length>0){
			$scope.huaanTmp.propertyName = propertyList[0].propertyName;
			$scope.huaanTmp.cusName = propertyList[0].propertyName;
			$scope.huaanTmp.propertyName = propertyList[0].propertyName;
			$scope.huaanTmp.propertyCardNumber = propertyList[0].propertyCardNumber;
			$scope.huaanTmp.certCode = propertyList[0].propertyCardNumber;
		}
		var purchaserList = $scope.house.orderBaseHousePurchaserDto;
		if(null!=purchaserList&&purchaserList.length>0){
			$scope.huaanTmp.buyName = purchaserList[0].buyName;
			$scope.huaanTmp.buyCardNumber = purchaserList[0].buyCardNumber;
		}

		if($scope.paymentType){
			$scope.huaanTmp.paymentBankCardName = $scope.paymentType.paymentBankCardName;
			$scope.huaanTmp.paymentBankName = $scope.paymentType.paymentBankName;
			$scope.huaanTmp.paymentBankNumber = $scope.paymentType.paymentBankNumber;
		} else {
			$scope.huaanTmp.paymentBankCardName = "";
			$scope.huaanTmp.paymentBankName = "";
			$scope.huaanTmp.paymentBankNumber = "";
		}
		if($scope.huaanTmp.periodType){
			$scope.huaanTmp.periodType = String($scope.huaanTmp.periodType);
		} else{
			$scope.huaanTmp.periodType = "1";
		}
		$scope.huaanTmp.orderNo = $scope.orderNo;
	}

	$scope.huaanInsertOrDetail = function(obj){
		$http({
			url:'/credit/risk/allocationfund/v/huaanInsertOrDetail',
			method:'POST',
			data:obj
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.huaan = data.data;
				$scope.huaan.productName = obj.productName;
			} else {
				$scope.huaan = obj;
			}

			if($scope.huaan.periodType){
				$scope.huaan.periodType = String($scope.huaan.periodType);
			} else{
				$scope.huaan.periodType = "1";
			}
			if($scope.huaan.isOldLoanBank){
				$scope.huaan.isOldLoanBank = String($scope.huaan.isOldLoanBank)
			} else {
				$scope.huaan.isOldLoanBank = "2";
			}
			if($scope.huaan.houseLoanBankId){
				$scope.huaan.houseLoanBankId = String($scope.huaan.houseLoanBankId);
			} else {
				$scope.huaan.houseLoanBankId = "";
			}
			if($scope.huaan.houseLoanSubBankId){
				$scope.huaan.houseLoanSubBankId = String($scope.huaan.houseLoanSubBankId);
			} else {
				$scope.huaan.houseLoanSubBankId = "";
			}
		});
	}

	$scope.checkFund = function(){
		$scope.isPush = false;
		var productCode = "";
		var orderLoanAmount = 0;
		if($scope.borrow.isChangLoan==1&&null!=$scope.borrow.orderBaseBorrowRelationDto&&$scope.borrow.orderBaseBorrowRelationDto.length>0){
			productCode = $scope.borrow.orderBaseBorrowRelationDto[0].productCode;
			orderLoanAmount = $scope.borrow.orderBaseBorrowRelationDto[0].loanAmount;
		} else {
			productCode = $scope.borrow.productCode;
			orderLoanAmount = $scope.borrow.loanAmount
		}

		$scope.fund = new Array();
		var ind = 0;
		var flg = true;
		var isHuaan = false;//华安
		var isHuarong = false;//华融
		var loanamount = 0
		var kgFund = false;
		var checkFundCount = 0;
		angular.forEach($scope.dataList,function(data,index,array){
			data.fundCodeShow = false;
			data.loanAmountShow = false;
			if(data.isCheck&&data.loanAmount&&data.loanAmount>0){
				checkFundCount++;
				$scope.fund[ind] = {
					loanAmount:data.loanAmount,
					fundId:data.id,
					orderNo:$scope.orderNo,
					remark:$scope.remark,
					fundCode:data.fundCode,
					fundDesc:data.fundDesc
				}
				loanamount += Number(data.loanAmount);
				ind++;
				if(("105"==data.fundCode||105==data.fundCode)&&(null!=productCode&&productCode!="03")){
					isHuaan = true;
				}
				if(($scope.huarongCode+""==data.fundCode||$scope.huarongCode==data.fundCode)&&(null!=productCode&&productCode!="03")){
					$scope.huarongLoanAmount = data.loanAmount;
					$scope.kgLoan.applyAmt = data.loanAmount;
					$scope.kgLoan.apprvAmt = data.loanAmount;
					$scope.appoint.applyAmt = data.loanAmount;
					$scope.appoint.apprvAmt = data.loanAmount;
					$scope.kgAppoint.loanAmount = data.loanAmount;
					isHuarong = true;
				}
				if(001==data.fundCode){
					kgFund = true;
				}

			} else if((undefined!=data.isCheck&&data.isCheck)&&(undefined==data.loanAmount||data.loanAmount<=0)){
				data.loanAmountShow = true;
				flg = false;
			} else if((undefined==data.isCheck||!data.isCheck)&&(undefined!=data.loanAmount&&data.loanAmount>0)){
				data.fundCodeShow = true;
				flg = false;
			}
		});
		if(!kgFund&&checkFundCount>1){
			box.boxAlert("只能单选一个资金方");
			return;
		}else if(kgFund&&checkFundCount>2){
			box.boxAlert("选择001资金方之外只能同时选择一个资金方");
			return;
		}
		if(isHuaan&&isHuarong){
			box.boxAlert("105与"+$scope.huarongCode+"资金方只能选择其中一个");
			return;
		}
		if(!flg){
			return;
		} else if(!$scope.fund||$scope.fund.length<=0){
			box.boxAlert("请选择资金方");
			return;
		} else if(loanamount!=orderLoanAmount){
			box.boxAlert("放款金额不能大于或者小于借款金额");
			return;
		}
		if(isHuaan){
			$scope.huaanInsertOrDetail($scope.huaanTmp);
			//$scope.fundShow = false;
			$scope.huanShow = true;
		} else if(isHuarong){
			//$scope.huaanInsertOrDetail($scope.huaanTmp);
			//$scope.fundShow = false;
			$scope.huarongShow = true;
		} else {
			$scope.financeShow();
		}
	}
	//提交给财务
	$scope.toFinance = function(){
		if(!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid){
			alert("请选择发放款指令员");
			return;
		}
		$scope.fund[0].loanDirectiveUid = $scope.sumbitDto.uid;
		var param = angular.toJson($scope.fund);
		$http({
			url:'/credit/risk/allocationfund/v/toFinance',
			method:'POST',
			data:param
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.insertHuaan();
				box.closeAlert();
				$state.go('orderList');
			} else {
				box.boxAlert(data.msg);
			}
		});
	}
	//选择财务
	$scope.financeShow = function(){
		$scope.huanShow=false;
		$scope.huarongShow=false;
		$scope.fundShow=true
		$scope.personnelType = "收利息";
		box.editAlert($scope,"确定选择该资金吗，请选择财务。","<submit-box></submit-box>",$scope.instructionShow);
	}
	//选发放指令员
	$scope.instructionShow = function(){
		if(!$scope.sumbitDto.uid){
			$scope.sumbitDto.uid = $scope.fund[0].financeUid;
		}
		if(!$scope.sumbitDto.uid||""==$scope.sumbitDto.uid){
			alert("请选择财务");
			return;
		}
		$scope.fund[0].financeUid = $scope.sumbitDto.uid;
		$scope.sumbitDto.uid = "";
		$scope.personnelType = "发放款指令";
		box.editAlert($scope,"请选择发放款指令员","<submit-box></submit-box>",$scope.toFinance);
	}

	//退回
	$scope.showBack = function(){
		box.editAlert($scope,"订单退回，请选择退回对象。","<back-box></back-box>",function(){$scope.backOrder();});
	}

	//推送
	$scope.pushShow = function(){
		$scope.isAudit = false;
		$scope.isBankAudit = false;
		if($scope.huaanForm.$invalid){
			box.boxAlert("订单数据不完整");
			$scope.isAudit = true;
			return;
		}
		if($scope.huaan&&$scope.huaan.isOldLoanBank==1&&(!$scope.huaan.houseLoanBankId||!$scope.huaan.houseLoanSubBankId)){
			$scope.isBankAudit = true;
			return;
		}
		$scope.isPush = true;
		$scope.financeShow();
	}
	$scope.insertHuaan = function(){
		if(!$scope.isPush){
			return;
		}
		$scope.huaan.orderNo = $scope.orderNo;
		$http({
			url:'/credit/risk/allocationfund/v/insertHuaan',
			method:'POST',
			data:$scope.huaan
		}).success(function(data){

		});

	}

	$scope.showSubmit = function(){
		box.editAlert($scope,title,"<submit-box></submit-box>",submit);
	}

	/**************影像资料删除图片start******************/
	$scope.delImgShow = function(imgList){
		$scope.allCheck = false;
		$scope.imgList = imgList;
		$scope.isDelImgShow = true;
	}
	$scope.delImg = function(){
		var ids = "";
		var isCheck = false;
		angular.forEach($scope.imgList,function(obj,index,array){
			if(obj.check){
				ids += obj.id+",";
				isCheck = true;
			}
		});
		if(isCheck){
			ids = ids.substring(0,ids.length-1);
		} else {
			box.boxAlert("请选择要删除的图片");
			return;
		}
		$http({
			method:'POST',
			url:'/credit/risk/allocationfund/v/deleteImg',
			data:{
				"orderNo":$scope.orderNo,
				"typeId":$scope.imgList[0].typeId,
				"ids":ids
			}
		}).success(function(result){
			if("SUCCESS"==result.code){
				$scope.imgList = result.data.thisImg;
				$scope.imageDatas = result.data.allImg;
				$scope.allCheck = false;
				if(!$scope.imgList||$scope.imgList.length<=0){
					$scope.isDelImgShow = false;
				}
			} else {
				box.boxAlert(result.msg);
			}
		});
	}

	$scope.selectAllImg = function(){
		if(!$scope.allCheck){
			$scope.allCheck = true;
		} else {
			$scope.allCheck = false;
		}
		angular.forEach($scope.imgList,function(obj,index,array){
				obj.check=$scope.allCheck;
		});
	}
	$scope.checkImg = function(imgList){
		var checkLength = 0;
		var imgLength = imgList.length;
		angular.forEach(imgList,function(obj,index,array){
			console.log(obj);
			if(obj.check){
				checkLength += 1;
			}
		});
		$scope.allCheck = checkLength==imgLength
	}
	/**************影像资料删除图片end********************/

	$scope.selectTypeCheck = function(obj){
		$scope.selectTypeId = obj;
	}

	$scope.addImg = function(url){
		if(url.length>0){
			var param = {
				orderNo:$scope.orderNo,
				typeId:$scope.selectTypeId,
				url:url
			}
			$http({
				url:'/credit/risk/allocationfund/v/addImg',
				method:'POST',
				data:param
			}).success(function(data){
				if("SUCCESS"==data.code){
					$scope.imageDatas = data.data;
					box.boxAlert("上传图片成功");
				}else{
					box.boxAlert(data.msg);
				}
			});
		}

	}
	$scope.delAllImg = function(){
		if(confirm("确定要删除所有影像资料")){
			$http({
				url:'/credit/risk/allocationfund/v/deleteAll',
				method:'POST',
				data:{"orderNo":$scope.orderNo}
			}).success(function(data){
				if("SUCCESS"==data.code){
					$scope.loadHuaanBusinfo();
				}
				box.boxAlert(data.msg);
			});
		}
	}
	
	

	$scope.loadHuaanBusinfo = function(){
		$http({
			method: 'POST',
			url:"/credit/risk/allocationfund/v/loadBusInfo",
			data:{
				"orderNo":$scope.orderNo,
			}
		}).success(function(data){
			$scope.imageDatas = data.data;
		});
	}

	/**************华融保存start*****************/
	$scope.huarongSave = function(){

		$scope.isAudit = false;
		if($scope.huarongAppointForm.$invalid){
			box.boxAlert("申請订单数据不完整");
			$scope.isAudit = true;
			$scope.huarongTitle = 'huarongOrderShow';
			return;
		} else if($scope.huarongKgAppointForm.$invalid){
			box.boxAlert("业务信息数据不完整");
			$scope.isAudit = true;
			$scope.huarongTitle = 'huarongKgAppointShow';
			return;
		} else if($scope.huarongKgIndivForm.$invalid){
			box.boxAlert("借款人信息不完整");
			$scope.isAudit = true;
			$scope.huarongTitle = 'huarongKgIndivShow';
			return;
		} else if($scope.huarongKgHouseForm.$invalid){
			box.boxAlert("房产信息不完整");
			$scope.isAudit = true;
			$scope.huarongTitle = 'huarongKgHouseShow';
			return;
		} else if($scope.huarongKgApprovalForm.$invalid){
			box.boxAlert("审批信息不完整");
			$scope.isAudit = true;
			$scope.huarongTitle = 'huarongKgApprovalShow';
			return;
		} else if($scope.huarongKgLoanForm.$invalid){
			box.boxAlert("放款数据不完整");
			$scope.isAudit = true;
			$scope.huarongTitle = 'huarongLoanShow';
			return;
		}
		$scope.apply();

	}
	$scope.fundAduit = function(){
		var param = angular.toJson($scope.fund);
		$http({
			url:'/credit/risk/allocationfund/v/fundAduit',
			method:'POST',
			data:param
		}).success(function(data){
			box.closeWaitAlert();
			if("SUCCESS"==data.code){
				alert(data.msg);
				$state.go('orderList');
			} else {
				box.boxAlert(data.msg);
			}
		});

	}
	//将图片filePath改Url
	function filePathToUrl(imgList){
		console.log(imgList);
		angular.forEach(imgList,function(parent,index,a){
			angular.forEach(parent,function(sType,sTypeIndex,b){
				angular.forEach(sType.childrenType,function(data,imgIndex,c){
					angular.forEach(data.listImgs,function(img,i,d){
						if(!img.orderId){
							return;
						} else {
							img.url=img.filePath
						}
					});
				});
			});
		});
		return imgList;
	}

	//获取所有影像资料拼接成集合
	$scope.getImgs = function(){
		var imgArray = new Array();
		console.log($scope.huarongImageDatas);
		angular.forEach($scope.huarongImageDatas,function(parent,index,a){
			angular.forEach(parent,function(sType,sTypeIndex,b){
				angular.forEach(sType.childrenType,function(data,imgIndex,c){
					angular.forEach(data.listImgs,function(img,i,d){
						var imgparam;
						if(img.orderId){
							 imgparam = {
								typeId:img.docFileType,
								url:img.filePath,
								orderNo:img.orderId,
								index:img.index
							}
						} else {
							 imgparam = {
								typeId:img.typeId,
								url:img.url,
								orderNo:img.orderNo,
								index:img.index
							}
						}
						imgArray.push(imgparam);
					});
				});
			});
		});
		return imgArray;
	}

	$scope.apply = function(){
		box.waitAlert();
		var param  = {
			lcAppoint:$scope.appoint,
			lcApplAppt:$scope.applAppt,
			lcApptIndiv:$scope.apptIndiv,
			kgAppoint:$scope.kgAppoint,
			kgIndiv:$scope.kgIndiv,
			kgHouse:$scope.kgHouse,
			kgApproval:$scope.kgApproval,
		}
		console.log("申请信息:"+param);
		$http({
			url:'/credit/risk/base/v/allApply',
			method:'POST',
			data:param
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.fund[0].isHuarongPush = 2;
				$scope.huarongImgs();
			} else {
				box.closeWaitAlert();
				$scope.fund[0].isHuarongPush = 3;
				box.boxAlert("申請信息"+data.msg);
			}
		});
	}

	$scope.huarongLoan = function(){
		$http({
			url:'/credit/risk/base/v/kgLoan',
			method:'POST',
			data:$scope.kgLoan
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.fund[0].isHuarongPush = 2;
			} else {
				$scope.fund[0].isHuarongPush = 3;
				alert("放款信息"+data.msg);
			}
			$scope.fundAduit();
		});
	}

	$scope.huarongImgs = function(){
		var imgs = $scope.getImgs();
		console.log(imgs);
		if(!imgs||imgs.length<=0){
			$scope.huarongLoan();
		} else {
			$http({
				url:'/credit/risk/base/v/kgImage',
				method:'POST',
				data:imgs
			}).success(function(data){
				if("SUCCESS"==data.code){
				} else {
					alert("影像資料"+data.msg);
				}
				$scope.huarongLoan();
			});
		}
	}
	function loadHuarongBusInfo(){
		$http({
			url:'/credit/risk/allocationfund/v/loadHuarongBusInfo',
			method:'POST',
			data:{'orderNo':$scope.orderNo}
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.huarongImageDatas = filePathToUrl(data.data);
			}
		});
	}

	function huarongInit(){
		//申请预约信息
		$scope.appoint.custName = $scope.customer.customerName;
		$scope.appoint.idType = "身份证";
		$scope.appoint.idNo = $scope.customer.customerCardNumber;
		$scope.appoint.mobile = $scope.borrow.phoneNumber;
		//$scope.appoint.applyAmt = $scope.borrow.loanAmount;
		$scope.appoint.apr = 0.0292;//$scope.borrow.rate;
		//$scope.appoint.apprvAmt = $scope.borrow.loanAmount;
		$scope.appoint.applyTnr = $scope.borrow.borrowingDays;

		//申请人信息
		$scope.applAppt.idNo = $scope.customer.customerCardNumber;
		$scope.applAppt.idType = "身份证";
		$scope.applAppt.custName = $scope.customer.customerName;

		//申请人详细信息
		$scope.apptIndiv.custName = $scope.customer.customerName;
		$scope.apptIndiv.indivEdt = "本科";

		//快鸽提单-业务信息
		$scope.kgAppoint.businessType = $scope.borrow.productName;
		$scope.kgAppoint.custName = $scope.customer.customerName;
		//$scope.kgAppoint.loanAmount = $scope.borrow.loanAmount;
		$scope.kgAppoint.term = $scope.borrow.borrowingDays;
		$scope.kgAppoint.rate = 0.0292;//$scope.borrow.rate;
		$scope.kgAppoint.overdueTate = 0.0292;//$scope.borrow.overdueRate;

		$scope.kgAppoint.yOriLenAmount = $scope.house.oldHouseLoanAmount;
		$scope.kgAppoint.yLoanBalance = $scope.house.oldHouseLoanBalance;
		$scope.kgAppoint.yIsBank = $scope.house.isOldLoanBank==1?"是":"否";
		if($scope.house.isOldLoanBank==2){
			$scope.kgAppoint.oldAddress = $scope.house.oldLoanBankName;
		} else {
			$scope.kgAppoint.oldLoanBankNameId = String($scope.borrow.oldLoanBankNameId);
			$scope.kgAppoint.oldLoanBankSubNameId = String($scope.borrow.oldLoanBankSubNameId);
		}

		$scope.kgAppoint.xLoanAmount = $scope.house.houseLoanAmount;
		$scope.kgAppoint.xIsBank = $scope.house.isLoanBank==1?"是":"否";
		if($scope.house.isLoanBank==2){
			$scope.kgAppoint.address = $scope.house.loanBankName;
		} else {
			$scope.kgAppoint.loanBankNameId = String($scope.borrow.loanBankNameId);
			$scope.kgAppoint.loanSubBankNameId = String($scope.borrow.loanSubBankNameId);
		}

		if($scope.foreclosureType){
			$scope.kgAppoint.fAccountType = $scope.foreclosureType.accountType;
			$scope.kgAppoint.bankNameId = null==$scope.foreclosureType.bankNameId?"":String($scope.foreclosureType.bankNameId);
			$scope.kgAppoint.bankSubNameId = null==$scope.foreclosureType.bankSubNameId?"":String($scope.foreclosureType.bankSubNameId);
			$scope.kgAppoint.fOpenName = $scope.foreclosureType.bankCardMaster;
			$scope.kgAppoint.fAccountNum = $scope.foreclosureType.bankNo;
			if("个人"==$scope.kgAppoint.fAccountType){
				$scope.kgAppoint.fCaacNo = $scope.foreclosureType.idCard;
			}
		}

		if($scope.paymentType){
			$scope.kgAppoint.hAccountType = $scope.paymentType.paymentaccountType;
			$scope.kgAppoint.paymentBankNameId = null==$scope.paymentType.paymentBankNameId?"":String($scope.paymentType.paymentBankNameId);
			$scope.kgAppoint.paymentBankSubNameId = null==$scope.paymentType.paymentBankSubNameId?"":String($scope.paymentType.paymentBankSubNameId);
			$scope.kgAppoint.hOpenName = $scope.paymentType.paymentBankCardName;
			$scope.kgAppoint.hAccountNum = $scope.paymentType.paymentBankNumber;
			if("个人"==$scope.kgAppoint.hAccountType){
				$scope.kgAppoint.hCaacNo = $scope.paymentType.paymentIdCardNo;
			}
		}

		$scope.kgIndiv.custName = $scope.customer.customerName;
		$scope.kgIndiv.docType = $scope.customer.customerCardType;
		$scope.kgIndiv.docNo = $scope.customer.customerCardNumber;
		$scope.kgIndiv.mobile = $scope.borrow.phoneNumber;
		$scope.kgIndiv.marStatus = $scope.customer.customerMarriageState;

		$scope.kgIndiv.sCustName = $scope.customer.customerWifeName;
		$scope.kgIndiv.sMarStatus = $scope.customer.customerWifeMarriageState;
		$scope.kgIndiv.sMobile = $scope.customer.customerWifePhone;
		$scope.kgIndiv.sDocNo = $scope.customer.customerWifeCardNumber;
		$scope.kgIndiv.sDocType = $scope.customer.customerWifeCardType;

		$scope.kgIndiv.cardLife = $scope.credit.creditCardYears;
		$scope.kgIndiv.totalPremises = $scope.credit.allHouseWorth;
		$scope.kgIndiv.totalCredit = $scope.credit.creditQuota;
		$scope.kgIndiv.debtRatio = $scope.credit.liabilitiesProportion;
		$scope.kgIndiv.overdueCredit = $scope.credit.creditOverdueNumber;
		$scope.kgIndiv.yIsBank = $scope.credit.oldLoanIsBank==1?"是":"否";
		$scope.kgIndiv.loanYear = $scope.credit.loanRecordYears;
		$scope.kgIndiv.houseNum = $scope.credit.allHouseNumber;
		$scope.kgIndiv.quotaUsed = $scope.credit.useQuota;
		$scope.kgIndiv.loanAmount = $scope.credit.loanPercentage;
		$scope.kgIndiv.creditFindNum = $scope.credit.latelyHalfYearSelectNumber;
		$scope.kgIndiv.xIsBank = $scope.credit.newLoanIsBank==1?"是":"否";
		$scope.kgIndiv.defaultRate = $scope.credit.violationProportion;
		$scope.kgIndiv.overdraft = $scope.credit.creditCardOverdraft;
		$scope.kgIndiv.totalCreditLia = $scope.credit.creditLiabilities;
		$scope.kgIndiv.foreclosureRate = $scope.credit.foreclosurePercentage;
		$scope.kgIndiv.companyRight = $scope.credit.isCompanyProperty;
		$scope.kgIndiv.rightMortgage = $scope.credit.propertyMortgage;
		$scope.kgIndiv.remark = $scope.borrow.remark;
		//所有权人
		if($scope.house.orderBaseHousePropertyPeopleDto&&$scope.house.orderBaseHousePropertyPeopleDto.length>0){
			$scope.kgHouse.owner = $scope.house.orderBaseHousePropertyPeopleDto[0].propertyName
		}

		if($scope.house.orderBaseHousePropertyDto&&$scope.house.orderBaseHousePropertyDto.length>0){
			var tmp = $scope.house.orderBaseHousePropertyDto[0];
			$scope.kgHouse.houseAddress = $scope.house.cityName+"-"+tmp.houseRegion;
			$scope.kgHouse.builtArea = tmp.houseArchitectureSize;
			$scope.kgHouse.houseNo = tmp.housePropertyNumber;
		}
		$scope.kgHouse.remark = $scope.house.remark;

		$scope.kgLoan.custName = $scope.customer.customerName;
		//$scope.kgLoan.idType = $scope.customer.customerCardType;
		$scope.kgLoan.idNo = $scope.customer.customerCardNumber;
		//$scope.kgLoan.applyAmt = $scope.borrow.loanAmount;
		//$scope.kgLoan.apprvAmt = $scope.borrow.loanAmount;
		$scope.kgLoan.apr = 0.0292;
		$scope.kgLoan.odIntRate = 0.0292;
		$scope.kgLoan.applyTnr = $scope.borrow.borrowingDays;
		$scope.kgLoan.sysbPct = 0;
		$scope.kgLoan.sysbAmt = 0;
		$scope.kgLoan.hrdPct = 0;
		$scope.kgLoan.hrdAmt = 0;
		$scope.kgLoan.idType = "身份证";
		loadHuarongBusInfo();
	}
	setTimeout(huarongInit,5000);

	function loadArchive(){
		$http({
			url:'/credit/risk/archive/v/detail',
			method:'POST',
			data:{'orderNo':$scope.orderNo}
		}).success(function(data){
			if("SUCCESS"==data.code&&null!=data.data){
				var tmp = data.data[0];
				if(tmp){
					$scope.kgHouse.consult = tmp.message;
				}
			}
		});
	}
	loadArchive();
	function loadEnquiry(){
		$http({
			url:'/credit/risk/enquiry/v/detail',
			method:'POST',
			data:{'orderNo':$scope.orderNo}
		}).success(function(data){
			if("SUCCESS"==data.code&&null!=data.data){
				var tmp = data.data[0];
				if(tmp){
					$scope.kgHouse.totalAssessment = tmp.totalPrice;
					$scope.kgHouse.worthAssessment = tmp.netPrice;
					if(tmp.maxLoanPrice){
						$scope.kgHouse.firstHouseLoan = parseFloat(tmp.maxLoanPrice)*10000;
					}
				}
			}
		});
	}
	loadEnquiry();

	function loadauditFirst(){
		$http({
			url:'/credit/risk/first/v/detail',
			method:'POST',
			data:{'orderNo':$scope.orderNo}
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.kgApproval.trial = data.data.remark;
			}
		});
	}
	loadauditFirst();
	function loadauditFinal(){
		$http({
			url:'/credit/risk/final/v/detail',
			method:'POST',
			data:{'orderNo':$scope.orderNo}
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.kgApproval.judgment = data.data.remark;
			}
		});
	}
	loadauditFinal();
	function loadauditOfficer(){
		$http({
			url:'/credit/risk/officer/v/detail',
			method:'POST',
			data:{'orderNo':$scope.orderNo}
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.kgApproval.chiefRiskOpinion = data.data.remark;
			}
		});
	}
	loadauditOfficer();

	function loadNotarization(){
		$http({
			url:'/credit/process/notarization/v/detail',
			method:'POST',
			data:{'orderNo':$scope.orderNo}
		}).success(function(data){
			if("SUCCESS"==data.code){
				$scope.kgLoan.repaymentTime = data.data.estimatedTimeStr;
			}
		});
	}
	loadNotarization();
	//删除华融所有图片
	$scope.delAllHuarongImg = function(){
		angular.forEach($scope.huarongImageDatas,function(parent,index,a){
			angular.forEach(parent,function(sType,sTypeIndex,b){
				angular.forEach(sType.childrenType,function(data,imgIndex,c){
					data.listImgs = new Array();
				});
			});
		});
	}
	//删除华融
	$scope.delHrImgShow = function(obj){
		$scope.allCheck = false;
		angular.forEach(obj.listImgs,function(data,index,array){
			data.check = false;
			data.isCheck = false;
		});
		$scope.imgObject = obj;
		$scope.isDelHrImgShow = true;
	}
	$scope.delHrImg = function(){
		var isCheck = false;
		var ckeckImg = new Array();
		var tempList = $scope.imgObject.listImgs.concat();
		$scope.imgObject.listImgs = new Array();
		angular.forEach(tempList,function(obj,index,array){
			if(obj.check){
				isCheck = true;
			}else{
				$scope.imgObject.listImgs.push(obj);
			}
		});
		if(!isCheck){
			box.boxAlert("请选择要删除的图片");
			return;
		}else{
			$scope.isDelHrImgShow = false;
		}
	}
	$scope.selectHrAllImg = function(){
		if(!$scope.allCheck){
			$scope.allCheck = true;
		} else {
			$scope.allCheck = false;
		}
		angular.forEach($scope.imgObject.listImgs,function(obj,index,array){
			obj.check=$scope.allCheck;
		});
	}
	/**************华融保存end*******************/

}).directive("allocationFundHuaanBusinfo",function($http,route){
	return{
		restrict:"E",
		templateUrl: '/template/sl/risk/allocationFundHuaanBusinfo.html',
		transclude:true,
		link: function (scope) {
			scope.obj = new Object();
			scope.isImgEdit = true;
			var orderNo = route.getParams().orderNo;

			$http({
				method: 'POST',
				url:"/credit/risk/allocationfund/v/loadBusInfo",
				data:{
					"orderNo":orderNo,
				}
			}).success(function(data){
				scope.imageDatas = data.data;
			});
		}
	};
}).directive("allocationFundHuaanOrder",function($http,route) {
	return {
		restrict: "E",
		templateUrl: '/template/sl/risk/allocationFundHuaanOrder.html',
		transclude: true,
		link: function (scope) {
			scope.obj = new Object();

		}
	};
    //华融申请信息
}).directive("allocationFundHuarongOrder",function($http,route) {
	return {
		restrict: "E",
		templateUrl: '/template/sl/risk/allocationFundHuarongOrder.html',
		transclude: true,
		link: function (scope) {
			scope.obj = new Object();

		}
	};
	//华融影像资料信息
}).directive("allocationFundHuarongBusinfo",function($http,route) {
	return {
		restrict: "E",
		templateUrl: '/template/sl/risk/allocationFundHuarongBusinfo.html',
		transclude: true,
		link: function (scope) {
			scope.obj = new Object();

		}
	};
	//华融业务信息
}).directive("allocationFundHuarongAppoint",function($http,route) {
	return {
		restrict: "E",
		templateUrl: '/template/sl/risk/allocationFundHuarongAppoint.html',
		transclude: true,
		link: function (scope) {
			scope.obj = new Object();

		}
	};
	//华融借款人信息
}).directive("allocationFundHuarongIndiv",function($http,route) {
	return {
		restrict: "E",
		templateUrl: '/template/sl/risk/allocationFundHuarongIndiv.html',
		transclude: true,
		link: function (scope) {
			scope.obj = new Object();

		}
	};
	//华融房产信息
}).directive("allocationFundHuarongHouse",function($http,route) {
	return {
		restrict: "E",
		templateUrl: '/template/sl/risk/allocationFundHuarongHouse.html',
		transclude: true,
		link: function (scope) {
			scope.obj = new Object();

		}
	};
	//华融风控审批信息
}).directive("allocationFundHuarongApproval",function($http,route) {
	return {
		restrict: "E",
		templateUrl: '/template/sl/risk/allocationFundHuarongApproval.html',
		transclude: true,
		link: function (scope) {
			scope.obj = new Object();

		}
	};
	//华融放款信息
}).directive("allocationFundHuarongLoan",function($http,route) {
	return {
		restrict: "E",
		templateUrl: '/template/sl/risk/allocationFundHuarongLoan.html',
		transclude: true,
		link: function (scope) {
			scope.obj = new Object();
		}
	};
});

function fundUpdateFile(a,b,c){
	if(""==a){
		return;
	}
	var scope = angular.element('.update-file').scope();
	scope.addImg(a);
}
function deleteHuarongUrl(url){
	var scope = angular.element('.huarong-view-windon').scope();
}
function huarongUpdateFile(url,b,c){
	if(""==url){
		return;
	}
	var scope = angular.element('.huarong-view-windon').scope();
	var listTmp = scope.huarongImageDatas;
	var index = url.lastIndexOf(",");
	if(index>-1){
		url = url.substring(0,url.length-1);
	}
	var arr = url.split(",");
	var imgIndex = 0;
	angular.forEach(listTmp,function(parent,index,a){
		angular.forEach(parent,function(sType,sTypeIndex,b){
			angular.forEach(sType.childrenType,function(data,imgIndex,c){
				if(!data.listImgs||null==data.listImgs){
					data.listImgs = new Array();
				}
				if(data.id==scope.selectTypeId){
					imgIndex = data.listImgs.length;
					angular.forEach(arr,function(arrData,arrInd,d){
						var param = {
							url:arrData,
							typeId:scope.selectTypeId,
							orderNo:scope.orderNo,
							index:imgIndex++
						}
						data.listImgs.push(param);
					})
				}
			});
		});
	});

	scope.huarongImageDatas = listTmp;
	scope.$apply();
}
function deleteHuarongUrl(url){
	var scope = angular.element('.huarong-view-windon').scope();
	var listTmp = scope.huarongImageDatas;
	var index = url.lastIndexOf(",");
	if(index>-1){
		url = url.substring(0,url.length-1);
	}
	angular.forEach(scope.huarongImageDatas,function(parent,index,a){
		angular.forEach(parent,function(sType,sTypeIndex,b){
			angular.forEach(sType.childrenType,function(data,imgIndex,c){
				angular.forEach(data.listImgs,function(img,i,d){
					if(img.url==url){
						data.listImgs.splice(img,1)
					}
				});
			});
		});
	});
	scope.$apply();
}

function lookImg(){
	$.openPhotoGallery($(".huarong-img-view").next("img"),"deleteHuarongUrl");
}

