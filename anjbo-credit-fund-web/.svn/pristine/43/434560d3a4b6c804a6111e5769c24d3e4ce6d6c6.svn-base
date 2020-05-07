/**
 * Created by Administrator on 2017/12/29.
 */
angular.module("anjboApp").controller("eleAccessDetailCtrl", function($scope,$http,$state,$compile,box,process,route) {

    $("#hhh").css("height",($(window).height()-107));
    $scope.id=route.getParams().id;
    $scope.pid=route.getParams().pid;
    if("NaN"===$scope.pid){
        $scope.isElement=true;
    }else{
        $scope.isElement=false;
    }
    $http({
        method: 'POST',
        data:{id:$scope.id},
        url:'/credit/element/eleaccess/web/v/accessFlowDetail'
    }).then(function successCallback(response) {
        if("SUCCESS"===response.data.code){
            $scope.obj=response.data.data;
            $scope.name = $scope.obj.handleEle;
			$scope.isShow =false;
			  if ($scope.name.hasOwnProperty("eleFilePayList")) {
			            $scope.isShow = true;
					} 
            
            if("3"==$scope.obj.orderType){
                $scope.obj.thing="公章";
                $scope.obj.isSeal=true;
            }else{
                $scope.obj.thing="要件";
                $scope.obj.isSeal=false;
            }
            if("1"==$scope.obj.operationType){
                $scope.obj.handleOne="存"+$scope.obj.thing;
                $scope.obj.handleTwo="存入的";
                $scope.obj.handleThree="存入";
            }else if("2"==$scope.obj.operationType){
                $scope.obj.handleOne="借"+$scope.obj.thing;
                $scope.obj.handleTwo="申请借用的";
                $scope.obj.handleThree="开箱";
            }else if("3"==$scope.obj.operationType){
                $scope.obj.handleOne="借"+$scope.obj.thing;
                $scope.obj.handleTwo="申请借用的";
                $scope.obj.handleThree="开箱";
            }else if("4"==$scope.obj.operationType){
                $scope.obj.handleOne="还"+$scope.obj.thing;
                $scope.obj.handleTwo="归还的";
                $scope.obj.handleThree="归还";
            }else if("5"==$scope.obj.operationType){
                $scope.obj.handleOne="退"+$scope.obj.thing;
                $scope.obj.handleTwo="退还的";
                $scope.obj.handleThree="退还";
            }else if("6"==$scope.obj.operationType){
                $scope.obj.handleOne="开箱";
                $scope.obj.handleTwo="申请借用的";
                $scope.obj.handleThree="开箱";
            }else if("7"==$scope.obj.operationType){
                $scope.obj.handleOne="开箱";
            }else if("10"==$scope.obj.operationType){
                $scope.obj.handleOne="修改存要件";
            }else if("11"==$scope.obj.operationType){
                $scope.obj.handleOne="修改还要件";
            }
        }
    }, function errorCallback(response) {
        console.log(response)
    });

    $scope.goHref=function(){
        $state.go("openBoxList",{'pid':$scope.pid});
    }
    
    $scope.goDetailHref = function(){
        $state.go("eleAccessDetail",{'id':$scope.obj.operationDetail.operationAccessFlowId,'pid':'NaN'});
    }
});