angular.module("anjboApp", ['bsTable']).controller("monitorArchiveAddCtrl",function($scope,$timeout,$compile,$http,$state,box,process,parent){


 $scope.$watch('archive.startTime',function(newValue,oldValue, scope){
	 var startDate = newValue;
	 if(newValue){
		 $('#form_datetime').datetimepicker('remove');
		 $("#form_datetime").datetimepicker({
	        format: "yyyy-mm-dd",
	        autoclose: true,
	        todayBtn: true,
	        minView: "month",
	        language: 'zh-CN',
	        startDate: startDate
	     });
	 }
     

});

	$scope.isAudit=false;
    $http({
        method: 'POST',
        url:'/credit/risk/monitor/v/init'
    }).success(function(data){
        $scope.productdto = data.data.productdto;
    })
    
    $scope.monitorArchiveAdd = function(){
    	if(!$scope.archiveAddForm.$valid){
			$scope.isAudit=true;
			return;
		}
    	var reg=/^([1-5]?)$/; 
    	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
    	var queryFrequency = $scope.archive.queryFrequency;
    	var phone = $scope.archive.phone;
    	if(!reg.test(queryFrequency)){
    		alert("查询频率必须为1-5的整数!");
    		return;
    	}
    	if(!myreg.test(phone)){
    		alert("请输入有效的手机号码!");
    		return;
    	}
	    box.confirmAlert("创建任务","确定创建任务吗？",function(){
	    	if(!$scope.archiveAddForm.$valid){
				$scope.isAudit=true;
				return;
			}
			if($scope.archive) {
				box.waitAlert();
				$http({
		            method: 'POST',
		            url:'/credit/risk/monitor/v/addMonitorArchive',
		            data:$scope.archive
		        }).success(function(data){
		        	box.closeWaitAlert();
		        	if(data.code == "SUCCESS"){
		        		alert("添加成功");
		        		$state.go("monitorArchiveList",{});
					}
		        })
			}
		});
    }
    
});