angular.module("anjboApp", ['bsTable']).controller("afterLoanDetailCtrl",function($scope,$compile,$cookies,$http,$state,$timeout,$filter,route,box,process,parent,file,FileUploader){
    $scope.showView = 1;
    $scope.repaymentShow = false;
    $scope.logShow = false;
    $scope.deleteOrDownloadFileShow = false;
    $scope.detailFileShow = false;
    $scope.downloadFile = false;
    $scope.logRemarkDetailShow = false;
    $scope.repaymentDetailShow = false;
    $scope.page = $cookies.getObject("pageParams");
    $scope.orderNo = route.getParams().orderNo;
    $scope.repaymentType = route.getParams().repaymentType;
    $scope.listUrl = '';
    $scope.repaymentUrl = '';
    $scope.currentRepaymentLog = null;
    $scope.verif = false;
    /**
     * 最后一期 显示申请展期按钮
     * @type {boolean}
     */
    $scope.lastPeriods = false;
    $scope.lastPeriodsPre = false;

    $timeout(function(){
         $(".tooltip-toggle").tooltip({
             html: true
         });
     });
    function Subtr(value1, value2) {
    	var r1, r2, m, n;
    	try {
    		r1 = value1.toString().split(".")[1].length;
    	} catch (e) {
    		r1 = 0;
    	}
    	try {
    		r2 = value2.toString().split(".")[1].length;
    	} catch (e) {
    		r2 = 0;
    	}
    	m = Math.pow(10, Math.max(r1, r2));
    	// 动态控制精度长度
    	n = (r1 >= r2) ? r1 : r2;
    	return ((value1 * m - value2 * m) / m).toFixed(n);
    }


    function add(value1, value2) {
    	var r1, r2, m;
    	try {
    		r1 = value1.toString().split(".")[1].length;
    	} catch (e) {
    		r1 = 0;
    	}
    	try {
    		r2 = value2.toString().split(".")[1].length;
    	} catch (e) {
    		r2 = 0;
    	}
    	m = Math.pow(10, Math.max(r1, r2));
    	return (value1 * m + value2 * m) / m;
    }

    //先息后本
    if(1==$scope.repaymentType){
        $scope.listUrl = '/credit/finance/afterLoanList/v/firstInterestList';
        $scope.repaymentUrl = '/credit/finance/afterLoanList/v/firstrepayment';
        //等额本息
    } else if(2==$scope.repaymentType){
        $scope.listUrl = '/credit/finance/afterLoanList/v/equalInterestList';
        $scope.repaymentUrl = '/credit/finance/afterLoanList/v/equalrepayment';
    }

    if(!$scope.page){
        $scope.page = new Object();
        $scope.page.start = 0;
        $scope.page.pageSize = 15;
        $scope.page.orderNo = $scope.orderNo;
    }

    function loadLoan(){
            $http({
                method: 'POST',
                url: '/credit/finance/afterLoanList/v/loanDetail',
                data:{'orderNo':$scope.orderNo}
            }).then(function successCallback(response) {
                if("SUCCESS"==response.data.code){
                    $scope.afterLoanListDto = response.data.data;
                    if($scope.afterLoanListDto&&$scope.afterLoanListDto.loanAmount){
                        $scope.afterLoanListDto.loanAmountTmp = $filter('currency')($scope.afterLoanListDto.loanAmount,'');
                        var index = $scope.afterLoanListDto.loanAmountTmp.lastIndexOf(".00");
                        if(index>0){
                            $scope.afterLoanListDto.loanAmountTmp = $scope.afterLoanListDto.loanAmountTmp.substring(0,index);
                        }
                    }

                }
                //资金方代号
                $http({
                            method: 'POST',
                            url: '/credit/order/allocationFund/v/processDetails',
                            data:{'orderNo':$scope.afterLoanListDto.orderNo}
                        }).then(function successCallback(response) {
                            if("SUCCESS"==response.data.code){
                                $scope.fundCode=response.data.data[0].fundCode;
                            }
                            
                        }, function errorCallback(response) { 
                           
                           // box.boxAlert("HTTP Status:"+response.status)
                        });
                
                
            }, function errorCallback(response) {
                box.boxAlert("HTTP Status:"+response.status)
            });
            $http({//获取 还款银行信息
                method: 'POST',
                url: '/credit/tools/tblOrderBaseHouseLending/v/search',
                data:{orderNo:$scope.orderNo,repaymentType:$scope.repaymentType}
            }).then(function successCallback(response) {
                if("SUCCESS"==response.data.code){
                    $scope.bankData=response.data.data[0];
                    $timeout(function(){
                        $scope.receivedPrincipalTotal=$scope.bankData.receivedPrincipalTotal?$scope.bankData.receivedPrincipalTotal:0;//实收本金总计
                        $scope.receivedInterestTotal=$scope.bankData.receivedInterestTotal?$scope.bankData.receivedInterestTotal:0;//实收利息总计
                        $scope.receivedDefaultInterestTotal=$scope.bankData.receivedDefaultInterestTotal?$scope.bankData.receivedDefaultInterestTotal:0;//实收罚息总计
                    })
                   
                }
            }, function errorCallback(response) {
                box.boxAlert("HTTP Status:"+response.status)
            });
    }
    loadLoan();

    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        $scope.page.orderNo = $scope.orderNo;
        return $scope.page;
    }

    function selectCurrentRepaymentLog(obj){
        $http({
            method: 'POST',
            url: '/credit/finance/afterLoanList/v/selectCurrentPeriodsLog',
            data:obj
        }).then(function successCallback(response) {
            if("SUCCESS"==response.data.code){
                $scope.currentRepaymentLog = response.data.data;
            }
        }, function errorCallback(response) {
            box.boxAlert("HTTP Status:"+response.status)
        });
    }

    function operateFormatter(value, row, index) {
        var html = "-";
        if(row.statusButton){
            $scope.choseData=row;//获取当期信息
        }
        if(1==row.status){
            if(row.statusButton&&$scope.hasDaiHouListA){
                html="待还款";
                //html = '<span class="audit-add-but repaymentbut">还款</span>';
                selectCurrentRepaymentLog(row);
            } else {
                html = '待还款';
            }
        } else if(2==row.status){
            if(row.statusButton&&$scope.hasDaiHouListA){
               html = '部分还款';
            	// html ='&nbsp;<span class="audit-add-but repaymentbut">还款</span>';
                selectCurrentRepaymentLog(row);
            } else {
                html = "部分还款";
            }
        } else if(3==row.status){
            html = "还款成功";
        } else if(10==row.status){
            html = "结清还款失败";
        }
        else if(8==row.status){
            html = "还款失败";
        }
        else if(9==row.status){
            html = "还款中";
        }
        else if(11==row.status){
            html = "结清还款中";
        }
        else if(4==row.status){
            html = "结清还款成功";
        }
        
        
        if(row.repaymentPeriods==($scope.afterLoanListDto.borrowingPeriods-1)&&3==row.status){
            $scope.lastPeriodsPre = true;
        }
        if(row.lastPeriods
            &&$scope.lastPeriodsPre
            &&1==$scope.repaymentType
            &&3!=row.status
            &&(!row.givePayPrincipal||row.givePayPrincipal<=0)){
            $scope.lastPeriods = true;
        }
        return html;
    }


    var cliceFunType=null;//操作方法类型标示  1，查看正常还款详情， 2，查看提前清贷还款详情    3.线上线下还款
    function operateFormatter2(value, row, index) {//新增操作 20181129
    	//&& dateCom(row.repaymentDateStr.substring(0,10),getNowFormatDate())
        	var rehtml="";
               if (row.status!=1 || row.statusButton ) {
           		rehtml = '<span class="after-loan-butactive repaymentbut"  >查看详情 </span>&nbsp;&nbsp;</span>';

			 }  
               else{
				 rehtml = '-';
				 
			 }
	
        
        return rehtml;
    }
    
    //查看详情 操作（查看还款和处理结果详情）
    window.operateEvents = {
        'click .repaymentbut':function(e,value,row,index){
          $timeout(function(){
            if (  row.status==1 || row.status==2||row.status==3 ||row.status==8||row.status==9)  { //正常还款查询 
        	   var dataJ={ 
                      orderNo:$scope.orderNo,
                      extendField1:route.getUserDto().name,//操作人
                      currentPeriod:row.repaymentPeriods
              }
        	   box.waitAlert();
        	   
           	 $http({
                    method: 'POST',
                    url:"/credit/tools/financeAfterloanLinePayment/v/searchSgtLinePaymentStatus",
                    data:dataJ
                }).then(function successCallback(response) {
               	 var failmsgs= "";
               	    if("FAIL"==response.data.code){
               	    	 failmsgs= response.data.msg;
					     }
               	    if("SUCCESS"==response.data.code){
              	    	 failmsgs= response.data.data;
					     }
               	 
                    $http({//获取还款推送详情
                        method: 'POST',
                        url: '/credit/tools/financeAfterloanLinePayment/v/search',
                        data:{orderNo:$scope.orderNo,currentPeriod:row.repaymentPeriods}
                    }).then(function successCallback(response) {
                     
                   	 $timeout(function(){
                   	 $scope.repaymentONlineSearchs = true;
                    	 box.closeWaitAlert();
                    	
                        if("SUCCESS"==response.data.code){
                        	if (response.data.data.length>0) {
    							
    						
                        	var lineflag=response.data.data[0].lineflag;
                        	   if(lineflag=="1"){
                                   $scope.isOnline = true;
                               }else{
                                   $scope.isOnline = false;
                               }
                        	   $scope.ret = new Object();
                        	   $scope.ret= response.data.data[0];
                           if ($scope.ret.receivablePrincipal==undefined) {
                           	$scope.ret.receivablePrincipal=0;
							}
                           if ($scope.ret.receivableInterest==undefined) {
                           	$scope.ret.receivableInterest=0;
							}
                        	   
                           if ($scope.ret.receivableDefaultInterest==undefined) {
                           	$scope.ret.receivableDefaultInterest=0;
							}
                           if ($scope.ret.totalDeductionAmount==undefined) {
                           	$scope.ret.totalDeductionAmount=0;
							}
                           if ($scope.ret.totalAmountReceived==undefined) {
                           	$scope.ret.totalAmountReceived=0;
							}
                        	   
                           $scope.ret.failMsg=failmsgs;
                           
                        	}   
                        }
                        
                  	  if("SUCCESS"==response.data.code){
                        	$scope.query();
                        	
                        }else {
                        	$scope.query();
                        	 box.boxAlert("还款状态查询失败,请稍后再刷新！");
            			} 
                  	$scope.repaymentONlineSearchs = true; 
                  	  
                   	 });
                  	  
                    }, function errorCallback(response) {
                        box.boxAlert("HTTP Status:"+response.status)
                    });
                  
                });
			}else if (row.status==1 ||row.status==11 ||row.status==10|| row.status==4) { //提前清贷还款 查看详情
				 
				  $http({//获取还款推送详情
	                    method: 'POST',
	                    url: '/credit/tools/financeAfterloanLinePayment/v/search',
	                    data:{orderNo:$scope.orderNo,deductionsType:"2"}
	                }).then(function successCallback(response) {
	                    if("SUCCESS"==response.data.code){
	                    	if (response.data.data.length>0) {
			                    	var lineflag=response.data.data[0].lineflag;
			                    	   if(lineflag=="1"){
			                               $scope.isOnline = true;
			                           }else{
			                               $scope.isOnline = false;
			                           }
			                    	   $scope.ret = new Object();
			                    	   $scope.ret= response.data.data[0];
		
			                    	   
			                    	   var dataJ={ 
			                                   orderNo:$scope.orderNo,
			                                   extendField1:route.getUserDto().name,//操作人
			                                   deductionsType:2
			                           }
			                    	  	 
			                    	   $http({
			                                 method: 'POST',
			                                 url:"/credit/tools/financeAfterloanLinePayment/v/searchSgtLinePaymentStatus",
			                                 data:dataJ
			                             }).then(function successCallback(response) { 
			                            	   $scope.ret.failMsg=response.data.data;
			                            	 
			                             });
	                    	}   
	                    	
	                    }
	                }, function errorCallback(response) {
	                    box.boxAlert("HTTP Status:"+response.status)
	                });
	                $scope.prepayShowDetail = true;
			}
           });
        }
    };
    
    //线上还款 操作
    window.operateEvents2 = {
            'click .repaymentbut2':function(e,value,row,index){
                $timeout(function(){
                   $scope.isOnline = true;
                   $scope.repaymentShow = true;
                });
            }
        };
    
  //线下还款 操作
    window.operateEvents3 = {
            'click .repaymentbut3':function(e,value,row,index){
                $timeout(function(){
                 	$scope.isOnline = false;
                    $scope.repaymentShow = true;
                });
            }
        };
    $scope.reset = function(){
        $scope.verif = false;
        $scope.repayment = new Object();
        $scope.repayment.orderNo = $scope.orderNo;
        $scope.repayment.repaymentPeriods = $scope.repaymentTmp.repaymentPeriods;
        $scope.repayment.repayType = "1";
        $scope.repayment.eventType = 3;
        $scope.repayment.givePayInterest = 0;
        $scope.repayment.amount = 0;
        $scope.repayment.givePayOverdue = 0;
        $scope.repayment.givePayPrincipal = 0;
        $scope.repayment.eventTypeName = "还款记录";
        $scope.repayment.realityPayDate = $filter('date')(new Date(),'yyyy-MM-dd');
        var givePayOverdue = null==$scope.repaymentTmp.givePayOverdue?0:$scope.repaymentTmp.givePayOverdue;
        var repayOverdue = null==$scope.repaymentTmp.repayOverdue?0:$scope.repaymentTmp.repayOverdue;
        if(givePayOverdue>=repayOverdue){
            $scope.repayment.repayOverdue = 0;
        } else {
            computeOverdue();
        }

    }
    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url: $scope.listUrl,pageNumber:1});
    }

    //新增 20181129
    if(!window.tableObj){
        window.tableObj={};
    }
//    if(!window.tableObj.repaymentOnline){//线上还款
//        window.tableObj.repaymentOnline=function(type,index){
//            $timeout(function(){
//                if(type==1){
//                    $scope.isOnline = true;
//                }else{
//                    $scope.isOnline = false;
//                }
//                
//                $scope.repaymentShow = true;
//            });
//        }
//    }
    
    /*
    if(!window.tableObj.prepayShowDetail){//查看提前清贷推送详情
        window.tableObj.prepayShowDetail=function(type,repaymentPeriods){
            $timeout(function(){
                
                $http({//获取还款推送详情
                    method: 'POST',
                    url: '/credit/tools/financeAfterloanLinePayment/v/search',
                    data:{orderNo:$scope.orderNo,deductionsType:"2"}
                }).then(function successCallback(response) {
                    if("SUCCESS"==response.data.code){
                    	if (response.data.data.length>0) {
						
                    	var lineflag=response.data.data[0].lineflag;
                    	   if(lineflag=="1"){
                               $scope.isOnline = true;
                           }else{
                               $scope.isOnline = false;
                           }
                    	   $scope.ret = new Object();
                    	   $scope.ret= response.data.data[0];

                    	   
                    	   var dataJ={ 
                                   orderNo:$scope.orderNo,
                                   extendField1:route.getUserDto().name,//操作人
                                   deductionsType:2
                           }
                    	  	 $http({
                                 method: 'POST',
                                 url:"/credit/tools/financeAfterloanLinePayment/v/searchSgtLinePaymentStatus",
                                 data:dataJ
                             }).then(function successCallback(response) { 
                            	   $scope.ret.failMsg=response.data.data;
                            	 
                            	 
                             });
                    	   
                    	}   
                    	
                    	
                    	
                    	
                    }
                }, function errorCallback(response) {
                    box.boxAlert("HTTP Status:"+response.status)
                });
                $scope.prepayShowDetail = true;
            });
        }
    }
    
    
    
    if(!window.tableObj.repaymentOnlineSearch){//查看正常还款详情
        window.tableObj.repaymentOnlineSearch=function(type,repaymentPeriods){
      
            $timeout(function(){
             	 $scope.repaymentONlineSearchs = false; 
            	box.waitAlert();
            	
         	   var dataJ={ 
                       orderNo:$scope.orderNo,
                       extendField1:route.getUserDto().name,//操作人
                       currentPeriod:repaymentPeriods
               }
            	
            	 $http({
                     method: 'POST',
                     url:"/credit/tools/financeAfterloanLinePayment/v/searchSgtLinePaymentStatus",
                     data:dataJ
                 }).then(function successCallback(response) {
                	 var failmsgs= "";
                	    if("FAIL"==response.data.code){
                	    	 failmsgs= response.data.msg;
					     }
                	    if("SUCCESS"==response.data.code){
               	    	 failmsgs= response.data.data;
					     }
                	 
                     $http({//获取还款推送详情
                         method: 'POST',
                         url: '/credit/tools/financeAfterloanLinePayment/v/search',
                         data:{orderNo:$scope.orderNo,currentPeriod:repaymentPeriods}
                     }).then(function successCallback(response) {
                      
                    	 $timeout(function(){
                    	 $scope.repaymentONlineSearchs = true;
                     	 box.closeWaitAlert();
                     	
                         if("SUCCESS"==response.data.code){
                         	if (response.data.data.length>0) {
     							
     						
                         	var lineflag=response.data.data[0].lineflag;
                         	   if(lineflag=="1"){
                                    $scope.isOnline = true;
                                }else{
                                    $scope.isOnline = false;
                                }
                         	   $scope.ret = new Object();
                         	   $scope.ret= response.data.data[0];
                            if ($scope.ret.receivablePrincipal==undefined) {
                            	$scope.ret.receivablePrincipal=0;
							}
                            if ($scope.ret.receivableInterest==undefined) {
                            	$scope.ret.receivableInterest=0;
							}
                         	   
                            if ($scope.ret.receivableDefaultInterest==undefined) {
                            	$scope.ret.receivableDefaultInterest=0;
							}
                            if ($scope.ret.totalDeductionAmount==undefined) {
                            	$scope.ret.totalDeductionAmount=0;
							}
                            if ($scope.ret.totalAmountReceived==undefined) {
                            	$scope.ret.totalAmountReceived=0;
							}
                         	   
                            $scope.ret.failMsg=failmsgs;
                            
                         	}   
                         }
                         
                   	  if("SUCCESS"==response.data.code){
                         	$scope.query();
                         	
                         }else {
                         	$scope.query();
                         	 box.boxAlert("还款状态查询失败,请稍后再刷新！");
             			} 
                   	  
                   	  
                    	 });
                   	  
                   	  
                   	  
                     }, function errorCallback(response) {
                         box.boxAlert("HTTP Status:"+response.status)
                     });
                     
                   
                   
                 });
            	
            	
                
                
            });
        }
    }
    */
    
    $scope.prepayShow = false;
    $scope.chargebacks="正常扣款";
    $scope.chargebacks2="提前清贷";
    $scope.choseData=[];
    $scope.isOnline=true;
    $scope.currentPeriod="";//当前期次
    $scope.actualRepayment='';//实际还款日期
    $scope.actualRepayment2='';//实际清贷日期
    $scope.serialNumber='';//银行流水号
    $scope.offlineReceivingTag="1";//线下收款标识
    $scope.bankActualDate="";//线下 银行实际到账日期
   // $scope.receivedPrincipal=0;//实收本金
    //$scope.receivedInterest=0;//实收利息
    $scope.receivedDefaultInterest=0;//实收罚息
 

    $scope.receivedPrincipalTotal=0;//实收本金总计
    $scope.receivedInterestTotal=0;//实收利息总计
    $scope.receivedDefaultInterestTotal=0;//实收罚息总计

    $scope.totalAmountReceived=0;//实收总金额
    $scope.totalDeductionAmount=0;//扣款总金额
    $scope.totalNum=0;//扣款总金额 提前清贷

    $scope.remissionPrincipal=0;//减免本金
    $scope.remissionInterest=0;//减免利息
    $scope.remissionDefaultInterest=0;//减免罚息

    $scope.remissionPrincipal2=0;//减免本金
    $scope.remissionInterest2=0;//减免利息
    $scope.remissionDefaultInterest2=0;//减免罚息

    $scope.debitAccount="";//扣款账号
    $scope.deductionsBan="";//扣款银行
    $scope.deductionsChannels="";//扣款渠道
    $scope.changePop=function(type){
       if(type==0){
         $scope.isOnline=true;
       }else{
         $scope.isOnline=false;
       }
    }
    $scope.receivedValChange=function(name,type){
        $scope[name] = $scope[name].replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符  
        //$scope[name] = $scope[name].replace(/\.{2,}/g,"."); //连续两个点清除一个
        $scope[name] = $scope[name].replace(/(\.\d{0,})\.$/,"$1"); //清除第二个点
        $scope[name] = $scope[name].replace(/^(\d{12})(\d)*$/,'$1');//12位整数
        $scope[name] = $scope[name].replace(/^(\d{1,})\.(\d\d).*$/,'$1.$2');//2位小数
        // if($scope[name].indexOf(".")< 0 && $scope[name] !=""){
        //     $scope[name]= parseFloat($scope[name]);
        // }
        // if($scope[name] =="."){//第一位不能是.
        //     $scope[name]=""
        // }
        if(type==2){
            $scope.setTotalVal2();
           return;
        }
        $scope.setTotalVal();
    }
    $scope.receivedValChange2=function(name,type){
       
        var num=Subtr(add(add( parseFloat($scope.ret.receivedPrincipalTotal !="" ?$scope.ret.receivedPrincipalTotal:0)
		        ,parseFloat($scope.ret.receivedInterestTotal !="" ?$scope.ret.receivedInterestTotal:0 )),
		        parseFloat($scope.ret.receivedDefaultInterestTotal !="" ?$scope.ret.receivedDefaultInterestTotal:0 ))
        , add(add(parseFloat($scope.remissionPrincipal2 ),
        parseFloat($scope.remissionInterest2)),
        parseFloat($scope.remissionDefaultInterest2)));//实收总金额
        $scope.totalNum = num;
    }
    
  
    
    
    $scope.setTotalVal=function(){//正常还款 总回款
        var num= Subtr(add(add(parseFloat($scope.receivedPrincipal),parseFloat($scope.receivedInterest))
        		              ,parseFloat($scope.receivedDefaultInterest))
                , add( add(parseFloat($scope.remissionPrincipal)
        		           ,parseFloat($scope.remissionInterest)),
        		    parseFloat($scope.remissionDefaultInterest)));//实收总金额
        if(isNaN(num)){
             $scope.totalDeductionAmount= $scope.totalAmountReceived = 0;
        }else{
            $scope.totalDeductionAmount=$scope.totalAmountReceived = num;
        }
    }
    $scope.setTotalVal2=function(){//提前清贷 总回款
        var num= parseFloat($scope.receivedPrincipalTotal)+ 
        parseFloat($scope.receivedInterestTotal)+ 
        parseFloat($scope.receivedDefaultInterestTotal)
        -parseFloat($scope.remissionPrincipal2)
        -parseFloat($scope.remissionInterest2)-parseFloat($scope.remissionDefaultInterest2);//实收总金额
        $scope.totalNum = num;
        
    }
    //新增 20181129 end
    
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
                title: '还款期数',
                field: 'repaymentPeriods',
                align: 'center',
                valign: 'bottom'
            },{
            	  title: '还款状态',
            	   field: 'repaymentPeriods',
                   align: 'center',
                   valign: 'bottom',
                   formatter: operateFormatter
            	
            	
            },{
                title: '应还款日期',
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
                title: '应还期供金额（元）',
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
            }, {
                title: '应收本金（元）',
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
                title: '应收利息（元）',
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
            }, {
                title: '应收罚息（元）',
                field: 'repayOverdue',
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
                title: '实收本金（元）',
                field: 'givePayPrincipal',
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
                title: '实收利息（元）',
                field: 'givePayInterest',
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
            },  {
                title: '实收罚息（元）',
                field: 'givePayOverdue',
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
            },  {
                title: '逾期天数',
                field: 'lateDay',
                align: 'center',
                valign: 'bottom'
            },  {
            	 title: '还款详情',
                  align: 'center',
                  valign: 'bottom',
                  events: operateEvents,
                  formatter: operateFormatter2
            },
            {
            	 title: '操作',
                align: 'center',
                valign: 'bottom',
                events: operateEvents2,
                formatter:function(value,row,index){
                	//&& dateCom(row.repaymentDateStr.substring(0,10),getNowFormatDate())
                    if(row.statusButton  ){
                    	var rehtml="";
                    	  rehtml=rehtml+'<span class="after-loan-butactive repaymentbut2">线上还款</span>';
                    }else{
            				 var rehtml='<span>线上还款</span>';
                    }
                    return rehtml;
                }
            }
            ,
            {
                align: 'center',
                valign: 'bottom',
                events: operateEvents3,
                formatter:function(value,row,index){
                	//&& dateCom(row.repaymentDateStr.substring(0,10),getNowFormatDate())
                    if(row.statusButton  ){
                    	var rehtml="";
                    	  rehtml=rehtml+'<span class="after-loan-butactive repaymentbut3">线下还款</span>';
                    }else{
            				 var rehtml='<span>线下还款</span>';
                    }
                    return rehtml;
                }
          }
            
            
            
            ]
        }};
   
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    }
    
    
    function dateCom(t1,t2) {
    var d1 = new Date(t1.replace(/\-/g, "\/"));  
    var d2 = new Date(t2.replace(/\-/g, "\/"));  

     if(t1!=""&&t2!=""&&d1 >d2)  
    {  
     return false;  
    }else{
    		 return true;  	
    }
    }
    
    
       

        $scope.prepayShow2=function (){
        	
     
            $http({//获取提前还款推送详情
                method: 'POST',
                url: '/credit/tools/financeAfterloanLinePayment/v/search',
                data:{orderNo:$scope.orderNo,deductionsType:"2"}
            }).then(function successCallback(response) {
                if("SUCCESS"==response.data.code){
                	 $scope.ret = new Object();
                	if (response.data.data.length>0) {
					
                	var lineflag=response.data.data[0].lineflag;
                	   if(lineflag=="1"){
                           $scope.isOnline = true;
                       }else{
                           $scope.isOnline = false;
                       }
                	  
                	   $scope.ret= response.data.data[0];
//                	   
                	   if (response.data.data[0].extendField5=='处理成功') {
                		   
                		    box.boxAlert("已经结清,不能重复还款!")
                		   $scope.prepayShow=false; 
						return ;
					}
                	   $scope.prepayShow=true;
                	   
                	   
                	} else{
                		
                		
                	     $http({//获取提前清贷还款余额
                             method: 'POST',
                             url: '/credit/tools/financeAfterloanLinePayment/v/getPrepaymentBalance',
                             data:{orderNo:$scope.orderNo,repaymentType:$scope.repaymentType}
                         }).then(function successCallback(response) {
                             if("SUCCESS"==response.data.code){
                             

                        		 $scope.ret.currentPeriod=$scope.choseData.repaymentPeriods;
                        		 
                        		 if ( $scope.choseData.repayPrincipal==null) {
                        			 $scope.ret.receivablePrincipal=0;
                        		 }else{
                        			 $scope.ret.receivablePrincipal=$scope.choseData.repayPrincipal;
                        		 }
                        		 if ( $scope.choseData.repayInterest ==null) {
                        			 $scope.ret.receivableInterest=0;
                        		 }else{
                        			 $scope.ret.receivableInterest=$scope.choseData.repayInterest ;
                        		 }
                        		 
                        		 if ( $scope.choseData.repayOverdue==null) {
                        			 $scope.ret.receivableDefaultInterest=0;
        						 }else{
        							
        							 $scope.ret.receivableDefaultInterest=$scope.choseData.repayOverdue;
        						 }
                        	
                        		 $scope.ret.receivedPrincipalTotal=response.data.data.repayPrincipalTotal;
                        		 $scope.ret.receivedInterestTotal=response.data.data.repayInterestTotal;
                        		 $scope.ret.receivedDefaultInterestTotal=response.data.data.repayOverdueTotal; 
                        		 $scope.totalNum=add(add(response.data.data.repayPrincipalTotal,response.data.data.repayInterestTotal),response.data.data.repayOverdueTotal);
                             		 
                             	}  
                             
                         }, function errorCallback(response) {
                             box.boxAlert("HTTP Status:"+response.status)
                         });
                		
                		//-----------
                		
                	     $scope.prepayShow=true;
                		
                		 
                		 
                		 
                	}  
                }
            }, function errorCallback(response) {
                box.boxAlert("HTTP Status:"+response.status)
            });
        	
          
        	 
        }
        
        $scope.rePush = function(){//重新推送
        	box.waitAlert();
            var dataJ={ 
                    orderNo:$scope.orderNo,
                    extendField1:route.getUserDto().name,//操作人
                    currentPeriod:$scope.ret.currentPeriod,
                    receivedPrincipal:parseFloat($scope.ret.receivedPrincipal),//实收本金
                    receivedInterest:parseFloat($scope.ret.receivedInterest),//实收利息
                    receivedDefaultInterest:parseFloat($scope.ret.receivedDefaultInterest),//实收罚息
                    remissionPrincipal:parseFloat($scope.ret.remissionPrincipal),//减免本金
                    remissionInterest:parseFloat($scope.ret.remissionInterest),//减免利息
                    remissionDefaultInterest:parseFloat($scope.ret.remissionDefaultInterest),//减免罚息
                     totalDeductionAmount:parseFloat($scope.ret.totalDeductionAmount),//减免罚息
                     totalAmountReceived:parseFloat($scope.ret.totalAmountReceived)//减免罚息
            }
        	 $http({
                 method: 'POST',
                 url:"/credit/tools/financeAfterloanLinePayment/v/edit",
                 data:dataJ
             }).then(function successCallback(response) {
                 if("SUCCESS"==response.data.code){
                	   var dataJ2={ 
                               orderNo:$scope.orderNo,
                               currentPeriod:$scope.ret.currentPeriod
                         }
                	 
                	 $http({
                         method: 'POST',
                         url:"/credit/tools/financeAfterloanLinePayment/v/repushPayment",
                         data:dataJ2
                     }).then(function successCallback(response) {
                         if("SUCCESS"==response.data.code){
                        	 box.closeWaitAlert();
                            box.boxAlert("推送成功!");
                            $scope.repaymentONlineSearchs=false;
                            $scope.query();
                         }else {
                        	 box.closeWaitAlert();
                         	 box.boxAlert("还款失败,请核查填写内容！");
             			}   
                     }); 
                 }else {
                	 box.closeWaitAlert();
                 	 box.boxAlert("还款失败,请核查填写内容！");
     			}   
             });
        	
        }
        
        $scope.repaymentStatusRefresh = function(){//刷新状态
        
            var dataJ={ 
                    orderNo:$scope.orderNo,
                    extendField1:route.getUserDto().name,//操作人
                    currentPeriod:$scope.ret.currentPeriod
            }
        	box.waitAlert();
        	 $http({
                 method: 'POST',
                 url:"/credit/tools/financeAfterloanLinePayment/v/searchSgtLinePaymentStatus",
                 data:dataJ
             }).then(function successCallback(response) {
            	 box.closeWaitAlert();
                 if("SUCCESS"==response.data.code){
                	  $scope.ret.extendField5=  response.data.msg;
                	  $scope.ret.failMsg= response.data.data;
                	 box.boxAlert("状态刷新成功！");
                	  $scope.query();
                 }else {
                	 
                	  $scope.ret.failMsg= response.data.msg;
                	  $scope.query();
                 	 box.boxAlert("还款失败,请核查填写内容！");
     			}   
             });
        	
        }
        


    
    $scope.confirmRepayment = function(){//提交 正常还款
        if($scope.isOnline){//线上
        }else{//线下
            if($scope.bankActualDate===""){
                box.boxAlert("银行实际到账日期不能为空!");
                return;
            }else if($scope.serialNumber===""){
                box.boxAlert("专户银行流水号不能为空!");
                return;
           }
        }
        if($scope.actualRepayment===""){
            box.boxAlert("实际还款日期不能为空!");
            return;
        }else if($scope.receivedPrincipal===""){
            box.boxAlert("实收本金不能为空!");
            return;
        }else if($scope.receivedInterest===""){
            box.boxAlert("实收利息不能为空!");
            return;
        }

        var dataJ={ 
            orderNo:$scope.orderNo,
            extendField1:route.getUserDto().name,//操作人
            eventType:3 ,
            eventTypeName:"还款记录",
            currentPeriod:$scope.choseData.repaymentPeriods,//当前期次
            borrowerName:$scope.afterLoanListDto.borrowerName,
            repaymentMode:$scope.afterLoanListDto.repaymentType,//还款方式
            deductionsType:1,//扣款类型 1.正常还款， 2.提前清贷
            lineflag:$scope.isOnline?1:2,//'线上线下标记:1.线上，2.线下'
            deductionsBank:$scope.deductionsBan,//线上 扣款银行
            debitAccount: $scope.debitAccount,//线上 扣款账号
            deductionsChannels:$scope.deductionsChannels,//线上 扣款渠道
            offlineReceivingTag:$scope.offlineReceivingTag,//线下收款标识
            specialAccountBankSerialNumber:$scope.serialNumber,//线下 专户银行流水号
            bankActualDate:$scope.bankActualDate?$scope.bankActualDate+" 00:00:00":$scope.bankActualDate,//线下 银行实际到账日期
            dueDate:$scope.choseData.repaymentDateStr,//应还款日期
            repaymentDate:$scope.actualRepayment+" 00:00:00",//实际还款日期
            receivablePrincipal:parseFloat($scope.choseData.repayPrincipal),//应收本金
            receivableInterest:parseFloat($scope.choseData.repayInterest),//应收利息
            periodAmount:parseFloat($scope.choseData.repayAmount),//期供金额
            receivedPrincipal:parseFloat($scope.receivedPrincipal),//实收本金
            receivedInterest:parseFloat($scope.receivedInterest),//实收利息
            receivedDefaultInterest:parseFloat($scope.receivedDefaultInterest),//实收罚息
            remissionPrincipal:parseFloat($scope.remissionPrincipal),//减免本金
            remissionInterest:parseFloat($scope.remissionInterest),//减免利息
            remissionDefaultInterest:parseFloat($scope.remissionDefaultInterest)//减免罚息
        }
        if($scope.isOnline){
            dataJ.totalDeductionAmount=parseFloat($scope.totalDeductionAmount);//总金额
        }else{
            dataJ.totalAmountReceived=parseFloat($scope.totalAmountReceived);
        }
       
        var fkssbj=Subtr(parseFloat($scope.choseData.repayPrincipal),parseFloat($scope.choseData.givePayPrincipal?$scope.choseData.givePayPrincipal:0));
        var fksslx=Subtr(parseFloat($scope.choseData.repayInterest),parseFloat($scope.choseData.givePayInterest?$scope.choseData.givePayInterest:0));
        var fkssfx=Subtr(parseFloat($scope.choseData.repayOverdue),parseFloat($scope.choseData.givePayOverdue?$scope.choseData.givePayOverdue:0));
        
        if(parseFloat($scope.receivedPrincipal)>fkssbj){
            box.boxAlert("实收本金不能大于应收本金!");
            return;
        }
        if(parseFloat($scope.receivedInterest)>fksslx){
            box.boxAlert("实收利息不能大于应收利息!");
            return;
        }
        if(parseFloat($scope.receivedDefaultInterest)>fkssfx){
            box.boxAlert("实收罚息不能大于应收罚息!");
            return;
        }
        box.waitAlert();
        $http({
            method: 'POST',
            url:"/credit/tools/financeAfterloanLinePayment/v/add",
            data:dataJ
        }).then(function successCallback(response) {
       	 box.closeWaitAlert();
            if("SUCCESS"==response.data.code){
               box.boxAlert("还款成功!");
               $scope.receivedPrincipal=0;
               $scope.receivedInterest=0;
               $scope.receivedDefaultInterest=0;
               $scope.remissionPrincipal=0;
               $scope.remissionInterest=0;
               $scope.remissionDefaultInterest=0;
               $scope.totalDeductionAmount =0;
               $scope.totalAmountReceived =0;
               
               $scope.bankActualDate ="";
               $scope.actualRepayment ="";
               $scope.serialNumber ="";
               
               
               $scope.repaymentShow=false;
               $scope.query();
            }else {
            	if (response.data.msg!=null) {
            		box.boxAlert(response.data.msg);
				}else{
					box.boxAlert("还款失败,请核查填写内容！");
				}
            	 
			}   
        })
        return;
        var givePayInterest = null==$scope.repaymentTmp.givePayInterest?0:$scope.repaymentTmp.givePayInterest;

        if(Number(sub($scope.repaymentTmp.repayInterest,givePayInterest))<Number($scope.repayment.givePayInterest)){
            box.boxAlert("已还利息不能大于当期应还利息!");
            return;
        }
        var givePayOverdue = null==$scope.repayment.givePayOverdue?0:$scope.repayment.givePayOverdue;
        var givePayInterestTmp = sub($scope.repayment.amount,givePayOverdue);
        var givePayPrincipalTmp = sub(givePayInterestTmp,$scope.repayment.givePayInterest);

        if(Number(sub(givePayPrincipalTmp,$scope.repayment.givePayPrincipal))<0){
            box.boxAlert("利息与逾期与本金的和不能大于填写实际还款金额!");
            return;
        }
        var repayOverdue = null==$scope.repayment.repayOverdue?0:$scope.repayment.repayOverdue;
        if(Number(repayOverdue)==0&&Number($scope.repayment.givePayOverdue)>0){
            $scope.repayment.givePayOverdue = 0;
            computeCost($scope.repayment.amount);
        }

        $scope.repaymentShow = false;
        $http({
            method: 'POST',
            url: $scope.repaymentUrl,
            data:$scope.repayment
        }).then(function successCallback(response) {
       	 box.closeWaitAlert();
            if("SUCCESS"==response.data.code){
               box.boxAlert("还款成功!");
               $scope.receivedPrincipal=0;
               $scope.receivedInterest=0;
               $scope.receivedDefaultInterest=0;
               $scope.remissionPrincipal=0;
               $scope.remissionInterest=0;
               $scope.remissionDefaultInterest=0;
               $scope.totalDeductionAmount =0;
               $scope.totalAmountReceived =0;
               $scope.bankActualDate ="";
               $scope.actualRepayment ="";
               $scope.serialNumber ="";
                if($scope.files&&$scope.files.length>0){
                    var log = response.data.data;
                    angular.forEach($scope.files,function(data,index,array){
                        data.logId = log.id;
                    });
                    $scope.upload(false);
                }
                if($scope.lastPeriods){
                    $scope.lastPeriods = false;
                }
                $scope.query();
                $scope.logQuery();
                selectCurrentRepaymentLog($scope.repayment);
            } else {
                box.boxAlert(response.data.msg);
            }
        }, function errorCallback(response) {
            box.boxAlert("HTTP Status:"+response.status)
        });
    }//提交 正常还款 end
    
    
    function checkNumber(obj){ 
    	var reg =  "-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?";
 
    	if(obj!=""&&!reg.test(obj)){ 
    	return false; 
    	}else{
    		
    		return true;
    	} 
    	} 
    
    
    $scope.submitPrepay=function(){//提交 提前清贷
    	
        if($scope.actualRepayment2==""){
            box.boxAlert("实际清贷日期不能为空!");
            return;
        }
        if($scope.ret.receivedPrincipalTotal===""|| $scope.ret.receivedPrincipalTotal==null  ){
            box.boxAlert("实收本金总计不能为空");
            return;
        }
       
        if($scope.ret.receivedInterestTotal==="" ||$scope.ret.receivedInterestTotal==null ){
            box.boxAlert("实收利息总计不能为空!");
            return;
        }
        if($scope.ret.receivedDefaultInterestTotal==="" ||$scope.ret.receivedDefaultInterestTotal==null){
            box.boxAlert("实收罚息总计不能为空!");
            return;
        }
        
        
        
        var dataJ={
            orderNo:$scope.orderNo,
            extendField1:route.getUserDto().name,//操作人
            eventType:3 ,
            eventTypeName:"还款记录",
            borrowerName:$scope.afterLoanListDto.borrowerName,
            repaymentMode:$scope.afterLoanListDto.repaymentType,//还款方式
            deductionsType:2,//扣款类型 1.正常还款， 2.提前清贷
            lineflag:$scope.isOnline?1:2,//'线上线下标记:1.线上，2.线下'
            currentPeriod:$scope.choseData.repaymentPeriods!==undefined ? $scope.choseData.repaymentPeriods :$scope.ret.currentPeriod ,//当前期次
            receivablePrincipal:$scope.choseData.repayPrincipal,//应收本金
            receivableInterest:$scope.choseData.repayInterest,//应收利息
            receivableDefaultInterest:$scope.choseData.repayOverdue,//应收罚息
            repaymentDate:$scope.actualRepayment2+" 00:00:00",//实际还款日期
            receivedPrincipalTotal:parseFloat($scope.ret.receivedPrincipalTotal),//实收本金总计
            receivedInterestTotal:parseFloat($scope.ret.receivedInterestTotal),//实收利息总计
            receivedDefaultInterestTotal:parseFloat($scope.ret.receivedDefaultInterestTotal),//实收罚息总计
            remissionPrincipal:parseFloat($scope.remissionPrincipal2),
            remissionInterest:parseFloat($scope.remissionInterest2),
            remissionDefaultInterest:parseFloat($scope.remissionDefaultInterest2),
            totalAmountReceived:$scope.totalNum,
            totalDeductionAmount:$scope.totalNum
            
        }
        box.waitAlert();
        $http({
            method: 'POST',
            url:"/credit/tools/financeAfterloanLinePayment/v/add",
            data:dataJ
        }).then(function successCallback(response) {
        	 box.closeWaitAlert();
            if("SUCCESS"==response.data.code){
            
            	
               box.boxAlert("还款成功!");
               $scope.receivedPrincipal=0;
               $scope.receivedInterest=0;
               $scope.receivedDefaultInterest=0;
               $scope.remissionPrincipal=0;
               $scope.remissionInterest=0;
               $scope.remissionDefaultInterest=0;
               $scope.totalDeductionAmount =0;
               $scope.totalAmountReceived =0;
               $scope.bankActualDate ="";
               $scope.actualRepayment ="";
               $scope.serialNumber ="";
               $scope.prepayShow=false;
               $scope.query();
            } else{
            	 box.boxAlert("还款失败,请核查填写内容！");
              
            	
            	
            }  
        })

    }//提交 提前清贷 end

    /**
     * 监听还款金额
     */
    $scope.$watch('repayment.amount',function(newVal,oldVal){
        if(newVal&&newVal>0){
            computeCost(newVal);
        } else if(newVal&&Number(newVal)<=0){
            $scope.repayment.givePayOverdue = 0;
        } else if(!newVal&&oldVal){
            $scope.repayment.givePayOverdue = 0;
        }
    });
    /**
     * 监听利息
     */
    $scope.$watch('repayment.givePayInterest',function(newVal,oldVal){
        if(newVal&&oldVal&&$scope.repayment.amount>0){
            var givePayOverdue = null==$scope.repayment.givePayOverdue?0:$scope.repayment.givePayOverdue;
            var amount = sub(sub($scope.repayment.amount,givePayOverdue),newVal);
            /**
             * 当利息发生手动上下波动调整时会实时计算分配的金额与利息的差剩余金额分配给本金多出的则溢出处理
             */
            if(Number($scope.repaymentTmp.repayPrincipal)>0&&Number(amount)>0){
                var givePayPrincipal = null==$scope.repaymentTmp.givePayPrincipal?0:$scope.repaymentTmp.givePayPrincipal;
                var repayPrincipal = null==$scope.repaymentTmp.repayPrincipal?0:$scope.repaymentTmp.repayPrincipal;
                givePayPrincipal = sub(repayPrincipal,givePayPrincipal);
                if(Number(amount)>Number(givePayPrincipal)){
                    $scope.repayment.givePayPrincipal = givePayPrincipal;
                    var tmp = sub(amount,givePayPrincipal);
                    $scope.repayment.overflow = tmp;
                } else {
                    $scope.repayment.givePayPrincipal = amount;
                }
            } else if(Number(amount)<=0){
                $scope.repayment.overflow = 0;
                $scope.repayment.givePayPrincipal = 0;
            }
        }
    });
    /**
     * 监听应还逾期
     */
    $scope.$watch('repayment.repayOverdue',function(newVal,oldVal){

        if(newVal){
            if($scope.repayment&&Number(newVal)<=0){
                $scope.repayment.repayOverdue = 0
                $scope.repayment.givePayOverdue = 0
            }
            if((oldVal||0==oldVal)&&newVal!=oldVal&&$scope.repayment){
                computeCost($scope.repayment.amount);
            }

        }
    });


    /**
     * 监听实际还款时间判断是否逾期
     */
    $scope.$watch('repayment.realityPayDate',function(newVal,oldVal){
        if(newVal){
            var isCompute = true;
            //当前选的实际还款时间 - 当期应还款时间
            var dayTime = datedifference($scope.repaymentTmp.repaymentDate,newVal);
            //大于则逾期
            if(dayTime>0){
                isCompute = false;
                compareOverTimeComputeCost(newVal);
            }
            if(isCompute){
                $scope.repayment.repayOverdue = 0;
                $scope.repayment.givePayOverdue = 0;
                if($scope.repayment&&Number($scope.repayment.amount)>0){
                    computeCost($scope.repayment.amount);
                }
            }
        }
    });
    /**
     * 监听本金变化
     */
    $scope.$watch('repayment.givePayPrincipal',function(newVal,oldVal){
        if(newVal){
            if($scope.repayment&&Number($scope.repayment.amount)>0){
                var givePayInterest = sub($scope.repayment.amount,$scope.repayment.givePayOverdue)
                var givePayPrincipal = sub(givePayInterest,$scope.repayment.givePayInterest);
                var overflow = sub(givePayPrincipal,newVal);
                if(overflow&&Number(overflow)>0){
                    $scope.repayment.overflow = overflow;
                } else {
                    $scope.repayment.overflow = 0;
                }
            } else {
                $scope.repayment.overflow = 0;
            }
        }
    });
    function compareOverTimeComputeCost(thisTime){
        if(!$scope.repaymentTmp||!$scope.repaymentTmp.realityPayDate){
            computeOverdue();
        } else if($scope.repaymentTmp&&$scope.repaymentTmp.realityPayDate){
            /**
             * 最近还款时间与当前选择的还款时间比较,如果最近还款时间大于当前选择时间则表示在最近还款时间内,则计算未还清逾期费用
             * 反之则按逾期天数计算
             * @type {number|*}
             */
            var dayTime = datedifference(thisTime,$scope.repaymentTmp.realityPayDate);
            if(dayTime>0){
                var repayOverdue = null==$scope.repaymentTmp.repayOverdue?0:$scope.repaymentTmp.repayOverdue;
                var givePayOverdue = null==$scope.repaymentTmp.givePayOverdue?0:$scope.repaymentTmp.givePayOverdue;
                var a = sub(repayOverdue,givePayOverdue);
                if(Number(a)>0){
                    $scope.repayment.repayOverdue = a;
                } else {
                    $scope.repayment.repayOverdue = 0;
                    $scope.repayment.givePayOverdue = 0;
                }
                if($scope.repayment&&$scope.repayment.amount>0){
                    computeCost($scope.repayment.amount);
                }
            } else {
                computeOverdue();
            }
        }
    }

    /**
     * 分配填写的还款金额
     * @param obj 还款金额
     */
    function computeCost(obj){

        var amount = Number(obj);
        //1.抵扣逾期费
        if($scope.repayment.repayOverdue&&Number($scope.repayment.repayOverdue)>0){
            if(amount>=Number($scope.repayment.repayOverdue)){
                $scope.repayment.givePayOverdue = $scope.repayment.repayOverdue;
                amount = sub(amount,$scope.repayment.repayOverdue);
            } else {
                $scope.repayment.givePayOverdue = amount;
                amount = 0;
            }
        }
        if(!$scope.repayment||Number($scope.repayment.repayOverdue)<=0){
            $scope.repayment.givePayOverdue = 0;
        }
        amount = Number(amount);
        //2.抵扣利息
        if(amount>0){
            var givePayInterest = null==$scope.repaymentTmp.givePayInterest?0:$scope.repaymentTmp.givePayInterest;
            var repayInterest = sub($scope.repaymentTmp.repayInterest,givePayInterest);
            if(amount>=repayInterest&&repayInterest>0){
                givePayInterest = repayInterest;
                $scope.repayment.givePayInterest = givePayInterest;
                amount = sub(amount,givePayInterest);
            } else if(amount<repayInterest&&repayInterest>0){
                $scope.repayment.givePayInterest = amount;
                amount = 0;
            }
        } else {
            $scope.repayment.givePayInterest = 0;
        }
        amount = Number(amount);
        //3.抵扣本金
        if(amount>0&&(2==$scope.repaymentType||Number($scope.repaymentTmp.repayPrincipal)>0)){
            var givePayPrincipal = null==$scope.repaymentTmp.givePayPrincipal?0:$scope.repaymentTmp.givePayPrincipal;
            if(Number($scope.repaymentTmp.repayPrincipal)>Number(givePayPrincipal)){
                if(Number(amount)>=sub($scope.repaymentTmp.repayPrincipal,givePayPrincipal)){
                    $scope.repayment.givePayPrincipal = sub($scope.repaymentTmp.repayPrincipal,givePayPrincipal);
                    amount = sub(amount,sub($scope.repaymentTmp.repayPrincipal,givePayPrincipal));
                } else {
                    $scope.repayment.givePayPrincipal = amount;
                    amount = 0;
                }

            } else {
                $scope.repayment.givePayPrincipal = 0;
            }
        } else {
            $scope.repayment.givePayPrincipal = 0;
        }
        //4.溢出保存不处理
        $scope.repayment.overflow = amount;
    }

    /**
     * 计算逾期费用
     * 公式为:
     * 等额本息(应还本金*逾期天数*逾期利率)
     * 先息后本(剩余本金*逾期天数*逾期利率。)
     */
    function computeOverdue(){
        var date;
        if($scope.repaymentTmp.realityPayDate
            &&$scope.repaymentTmp.realityPayDate>$scope.repaymentTmp.repaymentDate){
            date = $scope.repaymentTmp.realityPayDate;
        } else  {
            date = $scope.repaymentTmp.repaymentDate;
        }

        var principal;
        //先息后本
        if(1==$scope.repaymentType){
            var givePayPrincipal = null==$scope.repaymentTmp.givePayPrincipal?0:$scope.repaymentTmp.givePayPrincipal;
            principal = sub($scope.repaymentTmp.surplusPrincipal,givePayPrincipal);
        //等额本息
        } else if(2==$scope.repaymentType){
            var givePayPrincipal = null==$scope.repaymentTmp.givePayPrincipal?0:$scope.repaymentTmp.givePayPrincipal;
            principal = sub($scope.repaymentTmp.repayPrincipal,givePayPrincipal);
        }

        //逾期天数 还款日-实际还款时间
        var dayTime = datedifference($scope.repayment.realityPayDate,date);
        if(dayTime>0){
            dayTime = datedifference($scope.repaymentTmp.repaymentDate,$scope.repayment.realityPayDate);
            //当期是否还有未还清的逾期费用并且选择的时间是逾期的 如果移除则需要同步修改监听还款时间的计算逾期的方法
            if(dayTime>0){
                var givePayOverdue = null==$scope.repaymentTmp.givePayOverdue?0:$scope.repaymentTmp.givePayOverdue;
                var repayOverdue = null==$scope.repaymentTmp.repayOverdue?0:$scope.repaymentTmp.repayOverdue;
                repayOverdue = sub(repayOverdue,givePayOverdue);
                $scope.repayment.repayOverdue = repayOverdue;
                if($scope.repayment&&$scope.repayment.amount>0){
                    computeCost($scope.repayment.amount);
                }
                return;
            } else {
                $scope.repayment.repayOverdue = 0;
                return;
            }
        }
        if(!$scope.repayment||Number($scope.repayment.repayOverdue)<=0){
            $scope.repayment.givePayOverdue = 0;
        }
        var day = floor(dayTime);

        var dayTimeTmp = datedifference($scope.repayment.realityPayDate,$scope.repaymentTmp.repaymentDate);
        var dayTmp = floor(dayTimeTmp);


        if(dayTmp==$scope.repaymentTmp.lateDay&&0==day){
            var givePayOverdue = null==$scope.repaymentTmp.givePayOverdue?0:$scope.repaymentTmp.givePayOverdue;
            var repayOverdue = null==$scope.repaymentTmp.repayOverdue?0:$scope.repaymentTmp.repayOverdue;
            if(givePayOverdue>=repayOverdue){
                $scope.repayment.repayOverdue = 0;
            } else {
                //逾期费用=应还-已还
                $scope.repayment.repayOverdue = sub(repayOverdue,givePayOverdue);
                if($scope.repayment.amount){
                    computeCost($scope.repayment.amount);
                }
            }
            return;
        }
        /**
         * 开始计算逾期费用
         */
        var  amount = mul(principal,day);
        var repayOverdue = mul(amount,$scope.repaymentTmp.overdueRate);
        repayOverdue = div(repayOverdue,100);
        $scope.repayment.repayOverdue = repayOverdue;

        if($scope.repaymentTmp.repayOverdue
            &&Number($scope.repaymentTmp.repayOverdue)>0
            &&$scope.currentRepaymentLog
            &&$scope.currentRepaymentLog.length>0){

            //本期逾期费用=上期未还完的逾期费用+本期逾期费用(日期在上期的还款日期基础上计算)
            var lastLog = $scope.currentRepaymentLog[$scope.currentRepaymentLog.length-1];
            var givePayOverdue = null==lastLog.givePayOverdue?0:lastLog.givePayOverdue;
            var repayOverdue = null==lastLog.repayOverdue?0:lastLog.repayOverdue;
            var repayOverdueTmp = sub(repayOverdue,givePayOverdue);
            $scope.repayment.repayOverdue = add(repayOverdueTmp,$scope.repayment.repayOverdue);
        }
        if($scope.repayment.amount){
            computeCost($scope.repayment.amount);
        }
    }

    /**
     * 计算两个时间差
     * @param sDate1
     * @param sDate2
     * @returns {number|*}
     */
    function datedifference(sDate1, sDate2) {
        sDate1 = $filter('date')(sDate1,'yyyy-MM-dd');
        sDate2 = $filter('date')(sDate2,'yyyy-MM-dd');
        var dateSpan;
        sDate1 = Date.parse(sDate1);
        sDate2 = Date.parse(sDate2);
        dateSpan = sDate2 - sDate1;
        return dateSpan
    };
    /**
     * 将long类型的实际转为实际天数
     * @param day
     * @returns {number}
     */
    function floor(day){
        var dateSpan = Math.abs(day);
        var iDays = Math.floor(dateSpan / (24 * 3600 * 1000));
        return iDays;
    }

    /**
     * 减法运算
     * @param a
     * @param b
     * @returns {string}
     */
    function sub(a, b) {
        var c, d, e;
        try {
            c = a.toString().split(".")[1].length;
        } catch(f) {
            c = 0;
        }
        try {
            d = b.toString().split(".")[1].length;
        } catch(f) {
            d = 0;
        }
        e = Math.pow(10, Math.max(c, d));
        var num = (a * e - b * e) / e;
        return num.toFixed(2);
    }

    /**
     * 乘法运算
     * @param a
     * @param b
     * @returns {*}
     */
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
        var num = Number(d.replace(".", "")) * Number(e.replace(".", "")) / Math.pow(10, c);
        return num.toFixed(2);
    }

    /**
     * 除法运算
     * @param a
     * @param b
     * @returns {*}
     */
    function div(a, b) {
        if(!a||!b){
            return 0;
        }
        var c, d, e = 0,
            f = 0;
        try {
            e = a.toString().split(".")[1].length;
        } catch(g) {}
        try {
            f = b.toString().split(".")[1].length;
        } catch(g) {}
        c = Number(a.toString().replace(".", ""));
        d = Number(b.toString().replace(".", ""));
        var num = c / d * Math.pow(10, f - e);
        return num.toFixed(2);
    }

    /**
     * 加法运算
     * @param a
     * @param b
     * @returns {number}
     */
    function add(a, b) {
        var c, d, e;
        try {
            c = a.toString().split(".")[1].length;
        } catch(f) {
            c = 0;
        }
        try {
            d = b.toString().split(".")[1].length;
        } catch(f) {
            d = 0;
        }
        e = Math.pow(10, Math.max(c, d));
        var num = (a * e + b * e) / e;
        return Number(num);
    }
    //===========================贷后日志===============================

    if(!$scope.logPage){
        $scope.logPage = new Object();
        $scope.logPage.start = 0;
        $scope.logPage.pageSize = 15;
        $scope.logPage.orderNo = $scope.orderNo;
    };
    $scope.logPage.operateTimeOrderBy = "DESC";
    $scope.logsort = function(field){
        if("DESC"==$scope.logPage.operateTimeOrderBy){
            $scope.logPage.operateTimeOrderBy = "ASC";
        } else {
            $scope.logPage.operateTimeOrderBy = "DESC";
        }
    }
    function getLogParams(data){
        $scope.logPage.start = data.offset;
        $scope.logPage.pageSize = data.limit;
        $scope.logPage.orderNo = $scope.orderNo;
        return $scope.logPage;
    };

    function logOperateFormatter(value, row, index) {
        var html = "";
        if(row.createUid==route.getUserDto().uid){
            html = '<a href="javascript:void(0);" class="uploadLogImg">上传</a>';
        }
        if(row.file&&row.file.length>0){
            html += '&nbsp;&nbsp;<a href="javascript:void(0);" class="lookLogImg">查看</a>';
        }
        return html;
    };
    window.logOperateEvents = {
        'click .uploadLogImg':function(e,value,row,index){
            $timeout(function(){
                $scope.deleteOrDownloadFileShow = true;
                $scope.log = row;
                $scope.isCheckUpload = false;
                $scope.files = new Array();
            });
        },
        'click .lookLogImg':function(e,value,row,index){
            $timeout(function(){
                $scope.detailFileShow = true;
                $scope.detailFiles = row.file;
            });
        },
        'click .lookRemarkDetail':function(e,value,row,index){
            $timeout(function(){
                $scope.log = row;
                console.log(row);
                // $scope.log.givePayOverdue = null==$scope.log.givePayOverdue?0:$scope.log.givePayOverdue;
                // $scope.log.givePayInterest = null==$scope.log.givePayInterest?0:$scope.log.givePayInterest;
                // $scope.log.repayOverdue = null==$scope.log.repayOverdue?0:$scope.log.repayOverdue;
                // if(row.repayType&&1==row.repayType){
                //     $scope.repaymentDetailShow = true;
                // } else {
                //     $scope.logRemarkDetailShow = true;
                // }
                if(row.eventType==3){
                    $scope.repaymentDetailShow = true;
                    return;
                }
                $scope.logRemarkDetailShow = true;
                
            });
        }
    };
    $scope.logQuery = function(){//搜索
        $scope.logPage.createStartTime=$scope.logPage.createStartTime?$scope.logPage.createStartTime+" 00:00:00":'';
        $scope.logPage.createEndTime=$scope.logPage.createEndTime?$scope.logPage.createEndTime+" 00:00:00":'';
        $("#tableLog").bootstrapTable('refresh', {url: '/credit/tools/financeAfterloanLinePayment/v/page',pageNumber:1});
    };
    $scope.listOperateFun = function(){
        $http({
            method: 'POST',
            url: '/credit/finance/afterLoanList/v/listWhere',
            data:{'orderNo':$scope.orderNo}
        }).then(function successCallback(response) {
            if("SUCCESS"==response.data.code){
                $scope.listOperate = response.data.data.listOperate;
            }
        }, function errorCallback(response) {
            box.boxAlert("HTTP Status:"+response.status)
        });
    }
    function initWhere(){
        $scope.listOperateFun();
        $scope.listEvent = new Array();
        $scope.listEvent.push({
            'eventType':1,
            'eventTypeName':'贷后检查'
        },{
            'eventType':2,
            'eventTypeName':'逾期处理'
        },{
            'eventType':3,
            'eventTypeName':'还款记录'
        },{
            'eventType':0,
            'eventTypeName':'其他'
        });
        $scope.eventList = new Array();
        $scope.eventList.push(
            {
                'id':1,
                'name':'贷后检查'
            },{
                'id':2,
                'name':'逾期处理'
            },{
                'id':0,
                'name':'其他'
            }
        );
    };
    initWhere();

    $scope.logList = {
        options: {
            method:"post",
           // url:'/credit/finance/afterLoanList/v/listLog',
            url:'/credit/tools/financeAfterloanLinePayment/v/page',
            queryParams:getLogParams,
            sidePagination:'server',
            undefinedText:"-",
            cache: false,
            striped: true,
            pagination: true,
            pageNumber: ($scope.logPage.start/$scope.logPage.pageSize)+1,
            pageSize: $scope.logPage.pageSize,
            pageList: ['All'],
            showColumns: true,
            showRefresh: false,
            onSort:function(field){
                $scope.logsort(field);
            },
            onClickRow:function(row,$element,field){
                $element.toggleClass("bule-bg");
            },
            columns: [ {
                title: '事件类型',
                field: 'eventTypeName',
                align: 'center',
                valign: 'bottom'
            }, {
                title: '操作时间',
                field: 'createTime',
                align: 'center',
                valign: 'bottom',
                sortable: true,
                formatter:function(value, row, index){
                    var createTime = value;
                    if(createTime){
                        createTime = $filter('date')(createTime,"yyyy-MM-dd HH:mm:ss");
                    }
                    return createTime;
                }
            }, {
                title: '操作人',
                field: 'extendField1',
                align: 'center',
                valign: 'bottom'
            }, {
                title: '事件内容',
                field: 'remark',
                align: 'center',
                valign: 'bottom',
                events: logOperateEvents,
                formatter:function(value,row,index){
                    var html;
                    var tmp = "";
                    if(value&&value.length>25){
                        tmp = value.substring(0,25);
                        html = tmp+'...';
                    } else {
                        html = value;
                    }
                    if(null==html){
                        html = "";
                    }
                    if(3==row.eventType||tmp.length>=25){
                        html += '<a href="javascript:void(0);" class="lookRemarkDetail">展开全部></a>';
                    }
                    return html;
                }
            }
            // ,{
            //     title: '附件',
            //     field: 'operate',
            //     align: 'center',
            //     valign: 'bottom',
            //     events: logOperateEvents,
            //     formatter: logOperateFormatter
            // }
           ]
        }};

    $scope.addClick = function(){
        $scope.verif = false;
        $scope.logShow = true;
        $scope.log = new Object();
        $scope.log.orderNo = $scope.orderNo;
        $scope.log.eventType = "1";
        $scope.log.operateTime = $filter('date')(new Date(),'yyyy-MM-dd HH:mm');
        $scope.files = new Array();
        $scope.uploader.clearQueue();
    }

    $scope.addLog = function(){
        $scope.verif = false;
        //校验
        if($scope.eventFrom.$invalid){
            $scope.eventFrom.eventType.$dirty=true;
            $scope.eventFrom.operateTime.$dirty=true;
            $scope.eventFrom.remark.$dirty=true;
            $scope.verif = true;
            return;
        }

        $scope.logShow = false;
        $scope.log.operateTime = new Date($scope.log.operateTime+":00");
        angular.forEach($scope.eventList,function(data,index,array){
            if(data.id==$scope.log.eventType){
                $scope.log.eventTypeName = data.name;
                return;
            }
        });

        $http({
            url:'/credit/finance/afterLoanList/v/addLog',
            method:'POST',
            data:$scope.log
        }).then(function successCallback(response) {
            box.boxAlert(response.data.msg);
            if("SUCCESS"==response.data.code){
                $scope.logQuery();
                if($scope.files&&$scope.files.length>0){
                    var log = response.data.data;
                    angular.forEach($scope.files,function(data,index,array){
                        data.logId = log.id;
                    });
                    $scope.upload(false);
                }
                $scope.listOperateFun();
            }
        }, function errorCallback(response) {
            box.boxAlert("HTTP Status:"+response.status)
        });
    }


    var upload = file.fileuploader($scope,FileUploader,box);
    upload.onAfterAddingFile = function(fileItem) {
        if(fileItem.file.type.indexOf("image")<0
            &&fileItem.file.type.indexOf("pdf")<0
            &&fileItem.file.type.indexOf("document")<0){
            alert("不能上传此格式文件!");
            fileItem.remove();
        }
    };

    $scope.cancelUpload = function(){
        $scope.uploader.clearQueue();
        $scope.deleteOrDownloadFileShow = false;
        $scope.files = null;
    }
    $scope.confirmUpload = function(){
        $scope.files = new Array();
        angular.forEach($scope.uploader.queue,function(data,index,array){
            $scope.files.push({
                'name':data.feilData.name,
                'url':data.feilData.url,
                'fileType':data.file.type,
                'orderNo':$scope.orderNo,
                'logId':$scope.log.id
            });
        });
        $scope.deleteOrDownloadFileShow = false;
        $scope.upload($scope.isCheckUpload);
    }
    $scope.upload = function(obj){
        if(!obj){
            $http({
                url:'/credit/finance/afterLoanList/v/uploadLogFile',
                method:'POST',
                data:$scope.files
            }).then(function successCallback(response) {
                if(!$scope.isCheckUpload){
                    box.boxAlert(response.data.msg);
                }
                if("SUCCESS"==response.data.code){
                    $scope.logQuery();
                    $scope.uploader.clearQueue();
                    $scope.files = null;
                }
            }, function errorCallback(response) {
                box.boxAlert("HTTP Status:"+response.status)
            });
        }
    }

    $scope.uploadshow = function(){
        $scope.deleteOrDownloadFileShow = true;
        $scope.isCheckUpload = true;
        if(!$scope.files){
            $scope.files = new Array();
        }
    }

    $scope.showfile = function(file){
        $scope.detailFileShow = true;
        $scope.detailFiles = file;
    }

    $scope.changeView = function(showView){
        if(2==showView){
            $scope.listOperateFun();
            $scope.logQuery();
        }
        $scope.showView = showView;
        $scope.files = null;
    }
});