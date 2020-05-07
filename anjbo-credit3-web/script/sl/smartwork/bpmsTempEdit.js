angular.module("anjboApp", ['bsTable']).controller("bpmsTempEditCtrl",function($scope,$timeout,$compile,$http,$state,box,process,parent,route){
   	var params =  route.getParams();   	
   	$scope.isAudit=false;
   	$scope.record = {id:params.id};   	
   	
   	$http({
		method: 'GET',
		url:'/credit/third/api/dingtalk/bpmsTemp/get?id='+params.id,
		data: ''
	}).success(function(data){
		if(data.code == "SUCCESS"){
			$scope.record = data.data;
			// 加载审批人		
			$scope.userListApproversValue = new Array();
			angular.forEach(data.data.approvers.split(","), function(data,index,array){				
				$scope.userListApproversValue.push(data);
			});
			// 加载抄送人
			if (null != data.data.ccList) {
				$scope.userListCcValue = new Array();
				angular.forEach(data.data.ccList.split(","), function(data,index,array){				
					$scope.userListCcValue.push(data);
				});
			}			
		}
	});
	$http({
		method: 'GET',
		url: "/credit/user/base/choiceDingtalkPersonnel",
		data:{}
	}).success(function(data) {
		if(data.data){
			$scope.userList = data.data;			
		}
	})
   
    $scope.save = function(){
    	if(!$scope.editForm.$valid){
    		$scope.isAudit=true;
			return;
		}
    	if (null == $scope.record.approvers) {alert("ddd");
    		$scope.isAuditApprovers=true;
    		return;
    	}
	    if($scope.record) {
	    	//转换审批人ID/Name
	    	$scope.record.approvers = $scope.record.approvers.toString();  	
	    	var lstName = new Array;
	    	angular.forEach($scope.userList, function(data,index,array){
	    		if($scope.record.approvers.indexOf(data.dingtalkUid)>-1){
	    			lstName.push(data.name);
	    		}					
			});
			$scope.record.approversName = lstName.toString();
			// 转换抄送人ID/Name
	    	if(null != $scope.record.ccList){
	    		$scope.record.ccList = $scope.record.ccList.toString();	  
	    		lstName = new Array;
		    	angular.forEach($scope.userList, function(data,index,array){
		    		if($scope.record.ccList.indexOf(data.dingtalkUid)>-1){
		    			lstName.push(data.name);
		    		}					
				});
				$scope.record.ccListName = lstName.toString();
	    	}else{
	    		$scope.record.ccListName = "";
	    	}
	    	
			
			box.waitAlert();
			$http({
		           method: 'POST',
		           url:'/credit/third/api/dingtalk/bpmsTemp/edit',
		           data:$scope.record
		    }).success(function(data){
		       	box.closeWaitAlert();
		       	if(data.code == "SUCCESS"){
		       		$state.go("bpmsTempList",{});
				}
		    })
		}
    }
    
});