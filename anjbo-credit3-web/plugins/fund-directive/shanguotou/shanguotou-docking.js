define(function(require, exports, module) {
	exports.extend = function(app) {

		app.directive('shanguotouDockingEdit', function($compile,$cookies, $timeout, $http, $state, $filter, route, box) {
			return {
				restrict: "E",
				templateUrl: '/plugins/fund-directive/shanguotou/shanguotouDocking.html',
				transclude: true,
				link: function($scope) {
                    console.log($scope);
					$scope.idTypeA=[
						{id:"0",name:'身份证'},
						{id:"1",name:'户口簿'},
						{id:"2",name:'护照'},
						{id:"3",name:'军官证'},
						{id:"4",name:'士兵证'},
						{id:"5",name:'港澳居民来往内地通行证'},
						{id:"6",name:'台湾同胞来往内地通行证'},
						{id:"7",name:'临时身份证'},
						{id:"8",name:'外国人居留证'},
						{id:"9",name:'警官证'},
						{id:"A",name:'香港身份证'},
						{id:"B",name:'澳门身份证'},
						{id:"C",name:'台湾身份证'},
						{id:"X",name:'其他证件'}
					];
					$scope.gworkTypeA=[//所有权人职业 
						{id:"0",name:'国家机关、党群组织、企业、事业单位负责人'},
						{id:"1",name:'专业技术人员'},
						{id:"3",name:'办事人员和有关人员'},
						{id:"4",name:'商业、服务业人员'},
						{id:"5",name:'农、林、牧、渔、水利业生产人员'},
						{id:"6",name:'生产、运输设备操作人员及有关人员'},
						{id:"X",name:'军人'},
						{id:"Y",name:'不便分类的其他从业人员'},
						{id:"Z",name:'未知'}
					];
					if(!$scope.borrow){
						$scope.borrow={};
					}
					$scope.borrowInfo={//借款人信息
						idType:'',
						orderNo: $scope.orderNo,
						custName:$scope.borrow.borrowerName?$scope.borrow.borrowerName:'',
						phoneNo:$scope.borrow.phoneNumber?$scope.borrow.phoneNumber:'',
						telNo:$scope.borrow.phoneNumber?$scope.borrow.phoneNumber:'',
						homeArea:$scope.borrow.cityName?$scope.borrow.cityName:''
					};
					$scope.contractInfo={//合同信息
						orderNo: $scope.orderNo,
						pactAmt:$scope.borrow.loanAmount?$scope.borrow.loanAmount:'',
						repayment:$scope.borrow.paidType?$scope.borrow.paidType:'0',
						rateType:'2',
						vouType:'2',
						payType:'01',
						appUse:'1',
						appArea:$scope.borrow.cityName?$scope.borrow.cityName:'',
						termLoan:$scope.borrow.borrowingDays?$scope.borrow.borrowingDays+'天':'',
						termDay:'0',
						lnRate:$scope.borrow.rate
					};
					$scope.mortgagorInfo={//抵押信息
						orderNo: $scope.orderNo,
						gtype:'213',
						glicType:'01',
						ifApp:'1',
						ifPact:'1',
						ifId:'1',
						cardAmt:'0'
					};
					$scope.initSgtData=function(){
						$http({//获取借款人信息
							method: 'POST',
							url: '/credit/third/api/sgtongBorrowerInformation/v/search',
							data: {
								'orderNo': $scope.orderNo
							}
						}).success(function(data) {
							 if(data.code=="SUCCESS"){
								 if(data.data.length>0){
									$scope.borrowInfo=data.data[0];
									if($scope.borrowInfo.pushRepaymentStatus==null){
										$scope.borrowInfo.pushRepaymentStatus = '未推送';
									}
									if($scope.borrowInfo.lendingStatus==null){
										$scope.borrowInfo.lendingStatus = "待放款";
									}
									if($scope.borrowInfo.lendingStatus!='放款成功'||$scope.borrowInfo.pushRepaymentStatus=='处理成功'||$scope.borrowInfo.pushRepaymentStatus=='处理中'){
										$('#tsSgtHkxx').attr('disabled',true);
									}
									if($scope.borrowInfo.sgtLendingTime!=null&&$scope.borrowInfo.sgtLendingTime != undefined){
										$scope.borrowInfo.sgtLendingTime = $scope.borrowInfo.sgtLendingTime.substring(0,10);
									}
									for (item in $scope.borrowInfo) {
										$scope.borrowInfo[item]=$scope.borrowInfo[item]+"";
									}
								 
								 }else{
									 
									 
										$http({//借款信息
											method: 'POST',
											url: '/credit/order/baseCustomer/v/search',
											data: {
												'orderNo': $scope.orderNo
											}
										}).success(function(data) {
											 if(data.code=="SUCCESS"){
												 if(data.data.length>0){
													$scope.borrowInfo.custName=data.data[0].customerName;
													if (data.data[0].customerCardType=='二代身份证') {
														$scope.borrowInfo.idType="0";
													}
													
													$scope.borrowInfo.idNo=data.data[0].customerCardNumber;
													
													var b= data.data[0].customerCardNumber;
													$scope.borrowInfo.birth=b.substring(6,10)+"-"+b.substring(10,12)+"-"+b.substring(12,14);
													
													if (data.data[0].customerSex=='男') {
														$scope.borrowInfo.sex="1";
													}else {
														$scope.borrowInfo.sex="2";
													}
													if (data.data[0].customerMarriageState=='已婚有子女') {
													$scope.borrowInfo.marriage="20";
													}
													
													if (data.data[0].customerMarriageState=='已婚有子女') {
														$scope.borrowInfo.children="1";
														}
													//$scope.borrowInfo.income=data.data[0].customerAnnualIncome/12;
													
													if (data.data[0].customerCulture.indexOf("研究生")>-1) {
														$scope.borrowInfo.edu="10";
														$scope.borrowInfo.degree="3";
													}else if (data.data[0].customerCulture.indexOf("大学本科")>-1) {
														$scope.borrowInfo.edu="20";
														$scope.borrowInfo.degree="4";
													}else if (data.data[0].customerCulture.indexOf("大学专科")>-1) {
														$scope.borrowInfo.edu="30";
													}else if (data.data[0].customerCulture.indexOf("中等专业学校或中等技术学校")>-1) {
														$scope.borrowInfo.edu="40";
													}else if (data.data[0].customerCulture.indexOf("技术学校")>-1) {
														$scope.borrowInfo.edu="50";
													}else if (data.data[0].customerCulture.indexOf("高中")>-1) {
														$scope.borrowInfo.edu="60";
													}else if (data.data[0].customerCulture.indexOf("初中")>-1) {
														$scope.borrowInfo.edu="70";
													}else if (data.data[0].customerCulture.indexOf("小学")>-1) {
														$scope.borrowInfo.edu="80";
													}else if (data.data[0].customerCulture.indexOf("文盲")>-1) {
														$scope.borrowInfo.edu="90";
													}else if (data.data[0].customerCulture.indexOf("未知")>-1) {
														$scope.borrowInfo.edu="99";
													}
													
													
													
													
													
													
													
													
													if(data.data[0].customerAnnualIncome.indexOf("3万元（不含）以下")>-1){
														$scope.borrowInfo.income=Math.round(30000/12);
													}
													if(data.data[0].customerAnnualIncome.indexOf("3～6万元")>-1){
														$scope.borrowInfo.income=Math.round(60000/12);
													}
													if(data.data[0].customerAnnualIncome.indexOf("6～11万元")>-1){
														$scope.borrowInfo.income=Math.round(110000/12);
													}
													if(data.data[0].customerAnnualIncome.indexOf("12～19万元")>-1){
														$scope.borrowInfo.income=Math.round(190000/12);
													}
													if(data.data[0].customerAnnualIncome.indexOf("20～29万元")>-1){
														$scope.borrowInfo.income=Math.round(290000/12);
													}
													if(data.data[0].customerAnnualIncome.indexOf("30万元以上")>-1){
														$scope.borrowInfo.income=Math.round(300000/12);
													}
													
													//$scope.borrowInfo.phoneNo=data.data[0].customerAnnualIncome/12;
												
													if (data.data[0].isHasCar=='是') {
														 $scope.mortgagorInfo.ifCar="1";
													}
													if (data.data[0].isHasCar=='否') {
														 $scope.mortgagorInfo.ifCar="0";
													}
													
													if (data.data[0].isHasCarLoan=='是') {
														 $scope.mortgagorInfo.ifCarCred="1";
													}
													if (data.data[0].isHasCarLoan=='否') {
														 $scope.mortgagorInfo.ifCarCred="0";
													}
													 
													if (data.data[0].isHasRoom=='是') {
														 $scope.mortgagorInfo.ifRoom="1";
													}
													if (data.data[0].isHasRoom=='否') {
														 $scope.mortgagorInfo.ifRoom="0";
													}
													if (data.data[0].isHasRoomLoan=='是') {
														 $scope.mortgagorInfo.ifMort="1";
													}
													if (data.data[0].isHasRoomLoan=='否') {
														 $scope.mortgagorInfo.ifMort="0";
													}
													
													
													
													
												 }
											 }
										})
										
									$http({//借款信息
											method: 'POST',
											url: '/credit/order/baseBorrow/v/search',
											data: {
												'orderNo': $scope.orderNo
											}
										}).success(function(data) {
											 if(data.code=="SUCCESS"){
												 if(data.data.length>0){
													$scope.borrowInfo.phoneNo=data.data[0].phoneNumber;
													$scope.borrowInfo.telNo=data.data[0].phoneNumber;

													$scope.borrowInfo.homeArea=data.data[0].cityName
													
													
													if(data.data[0].customerType=='0'){
														$scope.borrowInfo.custType="99";
													}
													if(data.data[0].customerType=='1'){
														$scope.borrowInfo.custType="2";
													}
													if(data.data[0].customerType=='2'){
														$scope.borrowInfo.custType="3";
													}
													
													 $scope.mortgagorInfo.gbegBal=data.data[0].forwardMortgageBalance;
													
												
												 }
											 }
										})
										
									  $http({//房产抵押信息
											method: 'POST',
											url: '/credit/third/api/sgtongBorrowerInformation/v/searchSgtInfo',
											data: {
												'orderNo': $scope.orderNo
											}
										}).success(function(data) {
											
											$http({
												url: '/credit/third/api/sgtongBusinfo/v/busInfoTypes',
												method: 'POST',
												data: {
													'productCode': '100',
													'orderNo': $scope.orderNo
												}
											}).success(function(data) {
												 $scope.sgtImg = data.data;
												angular.forEach( $scope.sgtImg, function(data) {
													angular.forEach(data.img, function(data1) {
														if(data1.url.indexOf('pdf')>0){
															data1.isPdf = true;
														}else{
															data1.isPdf = false;
														}
													});
												});

											});
										
											
											 if(data.code=="SUCCESS"){
												
												 $scope.borrowInfo.comName=data.data.baseCustomerBorrowerDto.borrowerName;
											     
												 if (data.data.baseCustomerBorrowerDto.borrowerCardType=='二代身份证') {
														 $scope.borrowInfo.comIdtype="0";
													}
												 if (data.data.baseCustomerBorrowerDto.borrowerCardType=='军官证') {
													 $scope.borrowInfo.comIdtype="3";
												}
											     $scope.borrowInfo.comIdno=data.data.baseCustomerBorrowerDto.borrowerCardNumber;
											     $scope.borrowInfo.comTel=data.data.baseCustomerBorrowerDto.borrowerPhone;
											     
											     $scope.borrowInfo.relName=data.data.baseCustomerGuaranteeDto.guaranteeName;
											     
											     if (data.data.baseCustomerGuaranteeDto.guaranteeCardType=='二代身份证') {
											    	 $scope.borrowInfo.relIdtype="0";
												 }
											     if (data.data.baseCustomerGuaranteeDto.guaranteeCardType=='军官证') {
											    	 $scope.borrowInfo.relIdtype="3";
												 }
											     
											     $scope.borrowInfo.relIdno=data.data.baseCustomerGuaranteeDto.guaranteeCardNumber;
											     $scope.borrowInfo.relTel=data.data.baseCustomerGuaranteeDto.guaranteePhone;
											     
											  
											     
											     
											 }
										})
								 }
							 }
						});
						$http({//获取合同信息
							method: 'POST',
							url: '/credit/third/api/sgtongContractInformation/v/search',
							data: {
								'orderNo': $scope.orderNo
							}
						}).success(function(data) {
							 if(data.code=="SUCCESS"){
								 if(data.data.length>0){
									$scope.contractInfo=data.data[0];
									for (item in $scope.contractInfo) {
										$scope.contractInfo[item]=$scope.contractInfo[item]+"";
									}
								 }else{
									 
									 $http({//借款信息
											method: 'POST',
											url: '/credit/order/baseBorrow/v/search',
											data: {
												'orderNo': $scope.orderNo
											}
										}).success(function(data) {
											 if(data.code=="SUCCESS"){
												 if(data.data.length>0){
													$scope.contractInfo.pactAmt=data.data[0].loanAmount;
													$scope.contractInfo.termLoan=data.data[0].borrowingDays;
													$scope.contractInfo.appArea=data.data[0].cityName;
													if (data.data[0].paidType==1) {
														$scope.contractInfo.repayment="1";
														
													}else if(data.data[0].paidType==2){
														
														$scope.contractInfo.repayment="2";
													}
													//$scope.contractInfo.repayment=data.data[0].paidType;
													$scope.contractInfo.termMon=data.data[0].borrowingDays
													$scope.contractInfo.termDay="0";
													$scope.contractInfo.lnRate=data.data[0].rate;

												
												 }
											 }
										})
									 
									 
								 }
							 }
						});
						
						
						//刷新影像资料
					
							$http({
								url: '/credit/third/api/sgtongBusinfo/v/busInfoTypes',
								method: 'POST',
								data: {
									'productCode': '100',
									'orderNo': $scope.orderNo
								}
							}).success(function(data) {
								 $scope.sgtImg = data.data;
								angular.forEach( $scope.sgtImg, function(data) {
									angular.forEach(data.img, function(data1) {
										if(data1.url.indexOf('pdf')>0){
											data1.isPdf = true;
										}else{
											data1.isPdf = false;
										}
									});
								});

							});
						
						
						
						$http({//获取抵押人信息
							method: 'POST',
							url: '/credit/third/api/sgtongMortgagorInformation/v/search',
							data: {
								'orderNo': $scope.orderNo
							}
						}).success(function(data) {
							 if(data.code=="SUCCESS"){
								 if(data.data.length>0){
									$scope.mortgagorInfo=data.data[0];
									for (item in $scope.mortgagorInfo) {
										$scope.mortgagorInfo[item]=$scope.mortgagorInfo[item]+"";
									}
								 }else {
									
									 $http({//房产抵押信息
											method: 'POST',
											url: '/credit/third/api/sgtongBorrowerInformation/v/searchSgtInfo',
											data: {
												'orderNo': $scope.orderNo
											}
										}).success(function(data) {
											 if(data.code=="SUCCESS"){
												
												 $scope.mortgagorInfo.gcustName=data.data.baseHouse.baseHousePropertypeopleDto[0].propertyName;
												 
												 if (data.data.baseHouse.baseHousePropertypeopleDto[0].propertyCardType=='二代身份证') {
													 $scope.mortgagorInfo.gcustIdtype="0";

												}
												 if (data.data.baseHouse.baseHousePropertypeopleDto[0].propertyCardType=='军官证') {
													 $scope.mortgagorInfo.gcustIdtype="3";

												}
												 
												if (data.data.riskEnquiryDto!=null) {
													 $scope.mortgagorInfo.gvalue=Math.round(data.data.riskEnquiryDto.totalPrice/10000);

												}
												 

												 
//												 if (data.data.baseHouse.baseHousePropertypeopleDto[0].propertyPosition.indexOf("大学本科")>-1) {
//													 $scope.mortgagorInfo.gcustIdtype="3";
//
//												}
												 
												 
												 $scope.mortgagorInfo.gcustIdno=data.data.baseHouse.baseHousePropertypeopleDto[0].propertyCardNumber;
												 //$scope.mortgagorInfo.gvalue=
												 $scope.mortgagorInfo.gname=data.data.baseHouse.baseHousePropertyDto[0].houseName;
												 
												 
												 if (data.data.baseHouse.baseHousePropertyDto[0].smallCategory.indexOf('商铺')>-1){
													 $scope.mortgagorInfo.gsmType="04"; 
												 }
												 if (data.data.baseHouse.baseHousePropertyDto[0].smallCategory.indexOf('普通住宅')>-1){
													 $scope.mortgagorInfo.gsmType="01"; 
												 }
												 if (data.data.baseHouse.baseHousePropertyDto[0].smallCategory.indexOf('非普通住宅-别墅')>-1){
													 $scope.mortgagorInfo.gsmType="03"; 
												 }
												 if (data.data.baseHouse.baseHousePropertyDto[0].smallCategory.indexOf('非普通住宅-经济适用房或保障房')>-1){
													 $scope.mortgagorInfo.gsmType="02"; 
												 }
												 
												 
												 if (data.data.creditDto.creditCardYears.indexOf("年")>-1){
													 $scope.mortgagorInfo.ifCard="1";	 
												 }
												 if (data.data.creditDto.creditCardYears.indexOf("无信用卡记录")>-1){
													 $scope.mortgagorInfo.ifCard="0";	 
												 }
												 
													 
											    $scope.mortgagorInfo.glicno=data.data.baseHouse.baseHousePropertyDto[0].housePropertyNumber;
											
				
//											
											    if (data.data.housePropertyPeople[0].propertyPosition.indexOf("银行分行行级干部")>-1) {
													 $scope.mortgagorInfo.gworkType="0";

   											     }
											    if (data.data.housePropertyPeople[0].propertyPosition.indexOf("高级管理人员 ")>-1) {
													 $scope.mortgagorInfo.gworkType="4";

  											     } 
											    if (data.data.housePropertyPeople[0].propertyPosition.indexOf("银行支行行长")>-1) {
													 $scope.mortgagorInfo.gworkType="0";

											     } 
											    if (data.data.housePropertyPeople[0].propertyPosition.indexOf("中级管理人员")>-1) {
													 $scope.mortgagorInfo.gworkType="0";

											     } 
											    if (data.data.housePropertyPeople[0].propertyPosition.indexOf("科技干部")>-1) {
													 $scope.mortgagorInfo.gworkType="0";

 											     } 
											    if (data.data.housePropertyPeople[0].propertyPosition.indexOf("一般管理人员")>-1) {
													 $scope.mortgagorInfo.gworkType="4";

											     } 
											    if (data.data.housePropertyPeople[0].propertyPosition.indexOf("一般员工")>-1) {
													 $scope.mortgagorInfo.gworkType="4";

											     } 
										
											    if (data.data.housePropertyPeople[0].propertyPosition.indexOf("待业人员")>-1) {
													 $scope.mortgagorInfo.gworkType="Y";

											     } 
											 }
										})
									 
									 
									 
									 
									 
								}
							 }
						})
						
						
						
						
						
						$http({//获取推送审核状态
							method: 'POST',
							url: '/credit/third/api/sgtongBorrowerInformation/v/searchPushStatus',
							data: {
								'orderNo': $scope.orderNo
							}
						}).success(function(data) {
							if (data.data.code=='FAIL') {
								$scope.borrowstatus='未推送';
								 $scope.businfostatus='未推送'; 
								
								 return;
							}
							
							 if(data.data.borrowstatus=="SUCCESS"){
									$scope.borrowstatus='审核通过';
									$scope.reasonsMsg="";
							 }else  if(data.data.borrowstatus=="FAIL"){
								 $scope.borrowstatus='审核不通过'; 
								 $scope.reasonsMsg=data.data.borrowstatusMsg;
								
							 }else{
								 
								 $scope.borrowstatus='审核中'; 
								 
							 }
						 
						 if(data.data.businfostatus=="SUCCESS"){
								$scope.businfostatus='审核通过';
								$scope.reasonsMsg2="";
						 }else if(data.data.businfostatus=="FAIL"){
							 $scope.businfostatus='审核不通过'; 
							 $scope.reasonsMsg2=data.data.businfostatusMsg;
						 }else{
							 $scope.businfostatus='审核中';  
						 }
							 
						})
						
					
					

						
						
						
					}
					$scope.initSgtData();
					$scope.ifComLoan=false;//是否有共同借款人
					$scope.ifRelLoan=false;//是否有关联人
                    $scope.ordinaryClose = function() {//关闭弹窗
						$timeout(function() {
							$(angular.element.find("shanguotou-docking-edit")).remove();
						});
					}
					$scope.save=function(type){//保存
					 
						if(type==="borrowInfo"){//保存借款人信息
							if(!$scope.borrowInfo.custName){
								box.boxAlert("客户姓名不能为空");
								return;
							}
							if(!$scope.borrowInfo.idType){
								box.boxAlert("请选择证件类型");
								return;
							}
							if(!$scope.borrowInfo.idNo){
								box.boxAlert("请输入证件号码");
								return;
							}
							if(!$scope.borrowInfo.custType){
								box.boxAlert("请选择客户类型");
								return;
							}
							if(!$scope.borrowInfo.sex){
								box.boxAlert("请选择性别");
								return;
							}
							if(!$scope.borrowInfo.birth){
								box.boxAlert("请选择出生日期");
								return;
							}
							if(!$scope.borrowInfo.edu){
								box.boxAlert("请选择最高学历");
								return;
							}
							if(!$scope.borrowInfo.degree){
								box.boxAlert("请选择最高学位");
								return;
							}
							if(!$scope.borrowInfo.homeArea){
								box.boxAlert("请输入户籍归属地");
								return;
							}
							if(!$scope.borrowInfo.phoneNo){
								box.boxAlert("请输入手机号码");
								return;
							}
							if(!$scope.borrowInfo.telNo){
								box.boxAlert("请输入联系电话");
								return;
							}
							if(!$scope.borrowInfo.income){
								box.boxAlert("请输入月收入");
								return;
							}
							if(!$scope.borrowInfo.marriage){
								box.boxAlert("请选择婚姻状况");
								return;
							}
							if(!$scope.borrowInfo.children){
								box.boxAlert("请选择是否有子女");
								return;
							}
							$http({
								method: 'POST',
								url: '/credit/third/api/sgtongBorrowerInformation/v/add',
								data:  $scope.borrowInfo
							}).success(function(data) {
								if(data.code=="SUCCESS"){
									box.boxAlert("保存成功");
								}else{
									box.boxAlert(data.msg);
								}
							}) 
							return;
						}//保存借款人信息end		
						if(type==="contractInfo"){//合同信息
							if(!$scope.contractInfo.pactAmt){
								box.boxAlert("合同金额不能为空");
								return;
							}
							if(!$scope.contractInfo.lnRate){
								box.boxAlert("利率不能为空");
								return;
							}
							if(!$scope.contractInfo.termLoan){
								box.boxAlert("借款期限不能为空");
								return;
							}
							if(!$scope.contractInfo.appArea){
								box.boxAlert("申请地点不能为空");
								return;
							}
                            if(!$scope.contractInfo.appUse){
								box.boxAlert("申请用途不能为空");
								return;
							}
							if(!$scope.contractInfo.termMon||!$scope.contractInfo.termDay){
								box.boxAlert("合同期限不能为空");
								return;
							}
							$http({
								method: 'POST',
								url: '/credit/third/api/sgtongContractInformation/v/add',
								data:  $scope.contractInfo
							}).success(function(data) {
								if(data.code=="SUCCESS"){
									box.boxAlert("保存成功"); 
								}else{
									box.boxAlert(data.msg);
								}
							}) 
							return;
							 
						}//合同信息end
						if(type==="mortgagorInfo"){//抵押信息
							if(!$scope.mortgagorInfo.gcustName){
								box.boxAlert("押品所有权人名称不能为空");
								return;
							}
							if(!$scope.mortgagorInfo.gcustIdtype){
								box.boxAlert("请选择押品所有权人证件类型");
								return;
							}
							if(!$scope.mortgagorInfo.gcustIdno){
								box.boxAlert("证件号码不能为空");
								return;
							}
							if(!$scope.mortgagorInfo.gworkType){
								box.boxAlert("请选择所有权人职业");
								return;
							}
							if(!$scope.mortgagorInfo.gvalue){
								box.boxAlert("评估价值不能为空");
								return;
							}
							if(!$scope.mortgagorInfo.gname){
								box.boxAlert("押品名称不能为空");
								return;
							}
							if(!$scope.mortgagorInfo.gsmType){
								box.boxAlert("请选择押品小类");
								return;
							}
							if(!$scope.mortgagorInfo.glicno){
								box.boxAlert("权证号码不能为空");
								return;
							}
							if(!$scope.mortgagorInfo.ifCar){
								box.boxAlert("请选择是否有车");
								return;
							}
							if(!$scope.mortgagorInfo.ifCarCred){
								box.boxAlert("请选择是否有按揭车贷");
								return;
							}
							if(!$scope.mortgagorInfo.ifRoom){
								box.boxAlert("请选择是否有房");
								return;
							}
							if(!$scope.mortgagorInfo.ifMort){
								box.boxAlert("请选择是否有按揭房贷");
								return;
							}
							if(!$scope.mortgagorInfo.ifCard){
								box.boxAlert("请选择是否有贷记卡");
								return;
							}
							if(!$scope.mortgagorInfo.cardAmt){
								box.boxAlert("最低额度不能为空");
								return;
							}
							$http({
								method: 'POST',
								url: '/credit/third/api/sgtongMortgagorInformation/v/add',
								data:  $scope.mortgagorInfo
							}).success(function(data) {
								if(data.code=="SUCCESS"){
									box.boxAlert("保存成功");
								}else{
									box.boxAlert(data.msg);
								}
							}) 
							 return;
						}//抵押信息end
					}//保存end

					
					$scope.submit=function(){//提交
						//保存借款人信息
							if(!$scope.borrowInfo.custName){
								box.boxAlert("客户姓名不能为空");
								return;
							}
							if(!$scope.borrowInfo.idType){
								box.boxAlert("请选择证件类型");
								return;
							}
							if(!$scope.borrowInfo.idNo){
								box.boxAlert("请输入证件号码");
								return;
							}
							if(!$scope.borrowInfo.custType){
								box.boxAlert("请选择客户类型");
								return;
							}
							if(!$scope.borrowInfo.sex){
								box.boxAlert("请选择性别");
								return;
							}
							if(!$scope.borrowInfo.birth){
								box.boxAlert("请选择出生日期");
								return;
							}
							if(!$scope.borrowInfo.edu){
								box.boxAlert("请选择最高学历");
								return;
							}
							if(!$scope.borrowInfo.degree){
								box.boxAlert("请选择最高学位");
								return;
							}
							if(!$scope.borrowInfo.homeArea){
								box.boxAlert("请输入户籍归属地");
								return;
							}
							if(!$scope.borrowInfo.phoneNo){
								box.boxAlert("请输入手机号码");
								return;
							}
							if(!$scope.borrowInfo.telNo){
								box.boxAlert("请输入联系电话");
								return;
							}
							if(!$scope.borrowInfo.income){
								box.boxAlert("请输入月收入");
								return;
							}
							if(!$scope.borrowInfo.marriage){
								box.boxAlert("请选择婚姻状况");
								return;
							}
							if(!$scope.borrowInfo.children){
								box.boxAlert("请选择是否有子女");
								return;
							}
					
					//合同信息
							if(!$scope.contractInfo.pactAmt){
								box.boxAlert("合同金额不能为空");
								return;
							}
							if(!$scope.contractInfo.lnRate){
								box.boxAlert("利率不能为空");
								return;
							}
							if(!$scope.contractInfo.termLoan){
								box.boxAlert("借款期限不能为空");
								return;
							}
							if(!$scope.contractInfo.appArea){
								box.boxAlert("申请地点不能为空");
								return;
							}
                            if(!$scope.contractInfo.appUse){
								box.boxAlert("申请用途不能为空");
								return;
							}
							if(!$scope.contractInfo.termMon||!$scope.contractInfo.termDay){
								box.boxAlert("合同期限不能为空");
								return;
							}
						
				           //抵押信息
							if(!$scope.mortgagorInfo.gcustName){
								box.boxAlert("押品所有权人名称不能为空");
								return;
							}
							if(!$scope.mortgagorInfo.gcustIdtype){
								box.boxAlert("请选择押品所有权人证件类型");
								return;
							}
							if(!$scope.mortgagorInfo.gcustIdno){
								box.boxAlert("证件号码不能为空");
								return;
							}
							if(!$scope.mortgagorInfo.gworkType){
								box.boxAlert("请选择所有权人职业");
								return;
							}
							if(!$scope.mortgagorInfo.gvalue){
								box.boxAlert("评估价值不能为空");
								return;
							}
							if(!$scope.mortgagorInfo.gname){
								box.boxAlert("押品名称不能为空");
								return;
							}
							if(!$scope.mortgagorInfo.gsmType){
								box.boxAlert("请选择押品小类");
								return;
							}
							if(!$scope.mortgagorInfo.glicno){
								box.boxAlert("权证号码不能为空");
								return;
							}
							if(!$scope.mortgagorInfo.ifCar){
								box.boxAlert("请选择是否有车");
								return;
							}
							if(!$scope.mortgagorInfo.ifCarCred){
								box.boxAlert("请选择是否有按揭车贷");
								return;
							}
							if(!$scope.mortgagorInfo.ifRoom){
								box.boxAlert("请选择是否有房");
								return;
							}
							if(!$scope.mortgagorInfo.ifMort){
								box.boxAlert("请选择是否有按揭房贷");
								return;
							}
							if(!$scope.mortgagorInfo.ifCard){
								box.boxAlert("请选择是否有贷记卡");
								return;
							}
							if(!$scope.mortgagorInfo.cardAmt){
								box.boxAlert("最低额度不能为空");
								return;
							}
							var rets="";
							angular.forEach( $scope.sgtImg, function(data) {
								if (data.img.length==0) {
									rets+=data.type+",";
								}
								
							});
							
							if (rets!="") {
								box.boxAlert("请上传必传的影像资料！");
								return;
							}
							box.waitAlert();
							$http({
								method: 'POST',
								url: '/credit/third/api/sgtongBorrowerInformation/v/pushBorrowerInformation',
								data:  $scope.borrowInfo
							}).success(function(data) {
								if(data.code=="SUCCESS" ||data.msg=="校验成功"){
									
									$http({//获取推送状态
										method: 'POST',
										url: '/credit/third/api/sgtongBorrowerInformation/v/searchPushStatus',
										data: {
											'orderNo': $scope.orderNo
										}
									}).success(function(data) {
										
										
										
											if (data.data.code=='FAIL') {
												$scope.borrowstatus='未推送';
												 $scope.businfostatus=data.data.businfostatus; 
												 return;
											}
											
											 if(data.data.borrowstatus=="SUCCESS"){
														$scope.borrowstatus='审核通过';
														$scope.reasonsMsg="";
												 }else  if(data.data.borrowstatus=="FAIL"){
													 $scope.borrowstatus='审核不通过'; 
													 
													 $scope.reasonsMsg=data.data.borrowstatusMsg;
													
												 }else{
													 $scope.borrowstatus='审核中'; 
												 }
												 
											 
											 if(data.data.businfostatus=="SUCCESS"){
													$scope.businfostatus='审核通过';
													$scope.reasonsMsg2="";
													
											 }else  if(data.data.businfostatus=="FAIL"){
												 $scope.businfostatus='审核不通过'; 
												 $scope.reasonsMsg2=data.data.businfostatusMsg;
												 
												 
											 }else{
												 $scope.businfostatus='审核中'; 
											 }
											 
											 
												$http({//获取推送状态
													method: 'POST',
													url: '/credit/third/api/sgtongBorrowerInformation/v/searchPushStatus2',
													data: {
														'orderNo': $scope.orderNo
													}
												}).success(function(data) {
													box.closeWaitAlert();
													box.boxAlert("推送成功！");
													 if(data.data.pushstatus=="SUCCESS"){
														
																$scope.pushstatus='已推送';
															
														 }else{
															 $scope.pushstatus='未推送'; 
															 
														 
													 }
												})	 
											 
											 
											 
									})
									
									
									
						
									
									
									
									
								}else{
									box.closeWaitAlert();
									box.boxAlert(data.msg);
								}
							}) 
							 return;
							
					}//提交end
					
					
					
					$scope.submitBusinfo=function(){//提交影像资料
						
						var rets="";
						angular.forEach( $scope.sgtImg, function(data) {
							if (data.img.length==0) {
								rets+=data.type+",";
							}
							
						});
						if (rets!="") {
							box.boxAlert("请上传必传的影像资料！");
							return;
						}
						
						box.waitAlert();
						$http({
							method: 'POST',
							url: '/credit/third/api/sgtongBorrowerInformation/v/pushBusinfo',
							data: {
								'orderNo': $scope.orderNo
							}
						}).success(function(data) {
							
							
							if(data.code=="SUCCESS" ||data.msg=="校验成功"){
								
								//------
								$http({//获取推送状态
									method: 'POST',
									url: '/credit/third/api/sgtongBorrowerInformation/v/searchPushStatus',
									data: {
										'orderNo': $scope.orderNo
									}
								}).success(function(data) {
									
									box.closeWaitAlert();
									box.boxAlert("推送成功！");
									
										if (data.data.code=='FAIL') {
											$scope.borrowstatus='未推送';
											 $scope.businfostatus='未推送'; 
											 return;
										}
										
										 if(data.data.borrowstatus=="SUCCESS"){
													$scope.borrowstatus='审核通过';
													$scope.reasonsMsg="";
											 }else  if(data.data.borrowstatus=="FAIL"){
												 $scope.borrowstatus='审核不通过'; 
												 $scope.reasonsMsg=data.data.borrowstatusMsg;

											 }else{
												 $scope.borrowstatus='审核中'; 
											 }
											 
										 
										 if(data.data.businfostatus=="SUCCESS"){
												$scope.businfostatus='审核通过';
												$scope.reasonsMsg2="";
										 }else  if(data.data.businfostatus=="FAIL"){
											 $scope.businfostatus='审核不通过'; 
											 $scope.reasonsMsg2=data.data.businfostatusMsg;
										 }else{
											 $scope.businfostatus='审核中'; 
										 }
								})
								
								//---
								
								
							}else{
								box.closeWaitAlert();
									box.boxAlert(data.msg);
								
							}
						}) 
						
						
					}
					////
					
					//刷新影像资料
					function sgtNanImgInit() {
				
						
						
						$http({
							url: '/credit/third/api/sgtongBusinfo/v/busInfoTypes',
							method: 'POST',
							data: {
								'productCode': '100',
								'orderNo': $scope.orderNo
							}
						}).success(function(data) {
							 $scope.sgtImg = data.data;
							angular.forEach( $scope.sgtImg, function(data) {
								angular.forEach(data.img, function(data1) {
									if(data1.url.indexOf('pdf')>0){
										data1.isPdf = true;
									}else{
										data1.isPdf = false;
									}
								});
							});

						});
					

					}
					
					
					$scope.stgpushStatusRefresh = function() {
						$http({//获取推送审核状态
							method: 'POST',
							url: '/credit/third/api/sgtongBorrowerInformation/v/searchPushStatus',
							data: {
								'orderNo': $scope.orderNo
							}
						}).success(function(data) {
							 
							 box.boxAlert("刷新成功！");
							if (data.data.code=='FAIL') {
								$scope.borrowstatus='未推送';
								 $scope.businfostatus='未推送'; 
								 return;
							}
							
							 if(data.data.borrowstatus=="SUCCESS"){
										$scope.borrowstatus='审核通过';
										$scope.reasonsMsg="";
								 }else  if(data.data.borrowstatus=="FAIL"){
									 $scope.borrowstatus='审核不通过'; 
									 $scope.reasonsMsg=data.data.borrowstatusMsg;
								 }else{
									 $scope.borrowstatus='审核中'; 
								 }
								 
							 
							 if(data.data.businfostatus=="SUCCESS"){
									$scope.businfostatus='审核通过';
									$scope.reasonsMsg2="";
							 }else  if(data.data.businfostatus=="FAIL"){
								 $scope.businfostatus='审核不通过'; 
								 $scope.reasonsMsg2=data.data.businfostatusMsg;
							 }else{
								 $scope.businfostatus='审核中'; 
							 }
							
						})
					}
					//上传确定保存
					$scope.relSelect =function() {
						
						//alert("rel")
					};
					
					$scope.sgtCheck = function(typeId, type) {
						$scope.sgtTypeId = typeId;
						$scope.stgType = type;
					}
					//上传确定保存
					$scope.sgtImgSave = function(imgs) {

						var imgList = new Array();
						angular.forEach(imgs, function(data, index, a) {
							var tempDate = new Object();
							tempDate.orderNo = $scope.orderNo;
							tempDate.typeId = $scope.sgtTypeId;
							tempDate.type = $scope.stgType;
							
							tempDate.url = data;
							imgList.push(tempDate);
						});

						$http({
							url: '/credit/third/api/sgtongBusinfo/v/stgbatchAddImage',
							
							method: 'POST',
							data: imgList
						}).success(function(data) {
							box.closeWaitAlert();
							if(data.code == "SUCCESS") {
								box.closeAlert();
								sgtNanImgInit();
							}
						});
					}
					
					//打开图片删除选择框
					$scope.delstgImgShow = function(obj) {
						$scope.allSgtCheck = false;
						angular.forEach(obj, function(data, index, array) {
							data.check = false;
							data.isCheck = false;
						});
						$scope.imgSgtObject = obj;
						$scope.isDelSgtImgShow = true;
					}
					//图片删除选择框的取消按钮
					$scope.delSgtImgCancel = function() {
						$scope.isDelSgtImgShow = false;
					}
					//图片删除框的图片显示
					$scope.selectSgtAllImg = function() {
						if(!$scope.allSgtCheck) {
							$scope.allSgtCheck = true;
						} else {
							$scope.allSgtCheck = false;
						}
						angular.forEach( $scope.imgSgtObject, function(obj, index, array) {
							obj.check = $scope.allSgtCheck;
						});
					}

					
					//删除上传资料
					$scope.delSgtImg = function() {
						var isCheck = false;
						var ckeckImg = new Array();
						var tempList = $scope.imgSgtObject.concat();
						var imgs = "";
					
						angular.forEach(tempList, function(obj, index, array) {
							if(obj.check) {
								isCheck = true;
								imgs += obj.id + ",";
							}
						});
					
						if(!isCheck) {
							box.boxAlert("请选择要删除的资料");
							return;
						} else {
							var flags=0;
							angular.forEach(tempList, function(obj, index, array) {
								
								 if(obj.pushStatus=='审核通过' && obj.check==true){
	                                 box.boxAlert("选择资料中包含已经审核通过不能删除,请选择其他要删除的资料");
	                                 flags++;
	                                  return;
	                            }
							});
							
							if (flags==0) {
							imgs = imgs.substring(0, imgs.length - 1);
							$http({
								url: '/credit/third/api/sgtongBusinfo/v/stgbatchDeleteImg',
								method: 'POST',
								data: {
									id: imgs
								}
							}).success(function(data) {
								box.closeWaitAlert();
								box.boxAlert(data.msg, function() {
									if(data.code == "SUCCESS") {
										box.closeAlert();
										sgtNanImgInit();
									}
								});
							});
							$scope.isDelSgtImgShow = false;
							}
						}
						
					}
					
				  function getParams(data){
				        $scope.page.start = data.offset;
				        $scope.page.pageSize = data.limit;
				        $scope.page.orderNo = $scope.orderNo;
				        return $scope.page;
				    }
				  $scope.loanDetail = new Object();
				  $scope.page = $cookies.getObject("pageParams");
				  
				  $http({
						method: 'POST',
						url:"/credit/order/base/v/selectDetailByOrderNo" ,
						data:{'orderNo': $scope.orderNo}
					}).success(function(data){
						$scope.orderListDto=data.data;
						if(data.data.lendingTime!=null&&data.data.lendingTime!=undefined){
							data.data.lendingTime = data.data.lendingTime.substring(0,10);
						}
						
					})
					
					/**
					 * 推送应还款计划
					 */
					$scope.tsYhkxx = function (){
					  $http({
							url: '/credit/third/api/sgtongRepayment/v/tsYhkxx',
							method: 'POST',
							data: {
								'orderNo': $scope.orderNo
							}
						}).success(function(data) {
							if(data.code=="SUCCESS"){
								box.boxAlert("推送成功");
								$http({
									url: '/credit/third/api/sgtongRepayment/v/tsYhkxxResult',
									method: 'POST',
									data: {
										'orderNo': $scope.orderNo
									}                                                              
								}).success(function(data) {
									if("SUCCESS" == data.code) {
										if(data.data!=null&& data.data != undefined){
											$scope.borrowInfo.pushRepaymentStatus = data.data.pushRepaymentStatus;
											if(data.data.pushRepaymentStatus=='处理成功'||data.data.pushRepaymentStatus=='处理中'){
												$('#tsSgtHkxx').attr('disabled',true);
											}
										}
									}else{
										//box.boxAlert(data.msg);
									}
								});
							}else{
								if(data.msg==null||data.msg == undefined){
									data.msg="未走放款、贷后，不能推送还款计划";
								}
								box.boxAlert(data.msg);
							}
						});
				  }
				  
				  /**
				   * 还款计划上传结果
				   */
				  $scope.sgtHkxx = function(type) {
						$http({
							url: '/credit/third/api/sgtongRepayment/v/tsYhkxxResult',
							method: 'POST',
							data: {
								'orderNo': $scope.orderNo
							}                                                              
						}).success(function(data) {
							if("SUCCESS" == data.code) {
								box.boxAlert("刷新成功");
								if(data.data!=null&& data.data != undefined){
									$scope.borrowInfo.pushRepaymentStatus = data.data.pushRepaymentStatus;
									if(data.data.pushRepaymentStatus=='处理成功'||data.data.pushRepaymentStatus=='处理中'){
										$('#tsSgtHkxx').attr('disabled',true);
									}
								}
							}else{
								box.boxAlert(data.msg);
							}
						});
					}
				  
				  /**
				   * 刷新陕国投放款状态
				   */
				  $scope.sgtLending = function(type) {
					  $scope.batNo = new Object();
					  $http({
						  url: '/credit/order/statement/v/processDetails',
							method: 'POST',
							data: {
								'orderNo': $scope.orderNo
							} 
					  }).success(function(data){
						  if("SUCCESS" == data.code) {
							  if(data.data == undefined || data.data.sgtLendingBatNo == undefined){
								  box.boxAlert("等待放款");
							  }else{
								  $scope.batNo = data.data.sgtLendingBatNo;
								  $http({
										url: '/credit/third/api/api/v/sgtLendingResult',
										method: 'POST',
										data: {
											'batNo': $scope.batNo,
											'orderNo': $scope.orderNo
										}                                                              
									}).success(function(data) {
										if("SUCCESS" == data.code) {
											box.boxAlert("刷新成功");
											$scope.borrowInfo.lendingStatus = "放款成功";
											$scope.initSgtData();
										}else{
											box.boxAlert(data.msg);
										}
									});
							  }
						  }
					  });
					}
				  
				  /**
				   * 贷后详细信息
				   */
				  $scope.loanDetails = function(){
					  
					  $http({
							url: '/credit/finance/afterLoanList/v/loanDetail',
							method: 'POST',
							data: {
								'orderNo': $scope.orderNo
							}
						}).success(function(data) {
							if(data.code == "SUCCESS") {
								$scope.loanDetail = data.data;
								 //先息后本
							    if($scope.loanDetail!=null&&$scope.loanDetail.repaymentType!=undefined&&1==$scope.loanDetail.repaymentType){
							        $scope.listUrl = '/credit/finance/afterLoanList/v/firstInterestList';
							        $scope.repaymentUrl = '/credit/finance/afterLoanList/v/firstrepayment';
							        //等额本息
							    } else if($scope.loanDetail!=null&&$scope.loanDetail.repaymentType!=null&&$scope.loanDetail.repaymentType!=undefined&&2==$scope.loanDetail.repaymentType){
							        $scope.listUrl = '/credit/finance/afterLoanList/v/equalInterestList';
							        $scope.repaymentUrl = '/credit/finance/afterLoanList/v/equalrepayment';
							    }
							    
							    $scope.orderList = {
								        options: {
								            method:"post",
								            url:$scope.listUrl,
								            queryParams:getParams,
								            sidePagination:'server',
								            undefinedText:"-",
								            cache: false,
								            striped: true,
								            pagination: true,
								            pageNumber: ($scope.page.start/$scope.page.pageSize)+1,
								            pageSize: $scope.page.pageSize,
								            pageList: ['All'],
								            showColumns: true,
								            showRefresh: false,
								            onClickRow:function(row,$element,field){
								                $element.toggleClass("bule-bg");
								               
								            },
								            columns: [ {
								                title: '期次',
								                field: 'repaymentPeriods',
								                align: 'center',
								                valign: 'bottom'
								            },{
								                title: '应还款日',
								                field: 'repaymentDate',
								                align: 'center',
								                valign: 'bottom',
								                formatter:function(value, row, index){
								                    var repaymentDate = value;
								                    if(repaymentDate){
								                        repaymentDate = $filter('date')(repaymentDate,"yyyy-MM-dd");
								                    }
								                    return repaymentDate;
								                }
								            }, 
								            // {
								            //     title: '剩余本金（元）',
								            //     field: 'surplusPrincipal',
								            //     align: 'center',
								            //     valign: 'bottom',
								            //     formatter:function(value,row,index){
								            //         var html;
								            //         if(value){
								            //             html = $filter('currency')(value,'');
								            //             var index = html.lastIndexOf(".00");
								            //             if(index>0){
								            //                 html = html.substring(0,index);
								            //             }
								            //         }
								            //         return html;
								            //     }
								            // }, 
								            {
								                title: '应还款本金（元）',
								                field: 'repayPrincipal',
								                align: 'center',
								                valign: 'bottom',
								                formatter:function(value,row,index){
								                    var html;
								                    if(value){
								                        html = $filter('currency')(value,'');
								                        var index = html.lastIndexOf(".00");
								                        if(index>0){
								                            html = html.substring(0,index);
								                        }

								                    }
								                    return html;
								                }
								            }, {
								                title: '应还款利息（元）',
								                field: 'repayInterest',
								                align: 'center',
								                valign: 'bottom',
								                formatter:function(value,row,index){
								                    var html;
								                    if(value){
								                        html = $filter('currency')(value,'');
								                        var index = html.lastIndexOf(".00");
								                        if(index>0){
								                            html = html.substring(0,index);
								                        }

								                    }
								                    return html;
								                }
								            },{
								                title: '期供金额（元）',
								                field: 'repayAmount',
								                align: 'center',
								                valign: 'bottom',
								                formatter:function(value,row,index){
								                    var html;
								                    if(value){
								                        html = $filter('currency')(value,'');
								                        var index = html.lastIndexOf(".00");
								                        if(index>0){
								                            html = html.substring(0,index);
								                        }

								                    }
								                    return html;
								                }
								            }]
								        }};
							}
						});
				  }
				  $scope.loanDetails();
					
				}
			}
		});

	};
});