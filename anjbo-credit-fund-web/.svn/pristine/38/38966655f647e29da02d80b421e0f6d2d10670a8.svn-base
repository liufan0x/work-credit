angular.module("anjboApp", ['bsTable']).controller("baiduRiskListDetailCtrl",function($scope,$compile,$http,$state,box,process,parent,route){

	
	var id = route.getParams().id;
	
	var params = {
			"id": id
		}
	
	//详情
	$http({
		method: 'POST',
		url:'/credit/risk/riskList/v/detail',
		data:params
	}).success(function(data){
		$scope.baiduRiskListDto=data.data.baiduRiskListDto;
		$scope.baiduRiskListDto.blackDetails=newLineBySign($scope.baiduRiskListDto.blackDetails);
	})
	
	function newLineBySign(value){
		var result='';

		if(value!=null&&value!=''&&value.indexOf("<br>")>-1) { 

		result=value.replace(/(<br>)/g,"$1\n");

		result=result.replace(/(<br>)/g,"");

		} 

		return result; 

		}
	
});