/**
 * Created by Administrator on 2018/1/10.
 */
angular.module("anjboApp").controller("auditDetailCtrl", function($scope,$http,$state,$compile,box,process,route) {

    $scope.obj = new Object();

    if("NaN"==route.getParams().id){
        $scope.id='';
        $scope.isAdd=true;
    }else{
        $scope.id=route.getParams().id;
        $scope.isAdd=false;
    }
    $scope.isAudit=false;

    $http({
        method: 'POST',
        data:{id:$scope.id},
        url:'/credit/element/auditconfig/web/v/initDetail'
    }).success(function(data){
        if ($scope.isAdd){
            $scope.cityList = data.data.cityList;
            $scope.typeList = new Array();
        }else{
            $scope.obj.id=data.data.id;
            $scope.obj.city=data.data.city;
            $scope.obj.type=data.data.type;
            initYjg(data.data);
        }
    })

    $scope.$watch("obj.city", function(newValue, oldValue) {
        if ($scope.isAdd){
            $scope.obj.type = "";
            if(!$scope.obj.city){
                $scope.typeList = new Array();
                return false;
            }
            $http({
                method: 'POST',
                url:'/credit/element/auditconfig/web/v/getAuditConfigTypeList',
                data:{"city":$scope.obj.city}
            }).success(function(data){
                $scope.typeList = data.data.typeList;
            })
        }
    });

    $scope.save = function(flag){
        $scope.isAudit=true;
        if(!flag){
            box.boxAlert("请完善审批信息后再试");
            return;
        }
        console.log($scope.approve);
        angular.forEach($scope.approve, function(item, key) {
            $scope.obj['degree'+(key+1)]=angular.toJson(item);
        });
        angular.forEach($scope.shenpiRoleA, function(item, key) {
            $scope.obj['describ'+(key+1)]=item;
        });
        $scope.obj.other=angular.toJson($scope.CC);

        $http({
            method: 'POST',
            url:'/credit/element/auditconfig/web/v/saveAuditConfig',
            data:$scope.obj,
        }).success(function(data){
            if('SUCCESS'==data.code){
                $state.go("auditList");
            }else {
                alert(data.msg)
            }
        })
    }

    /*编辑 初始化插件*/
    function initYjg(rData){
        var degree1=rData.degree1;
        var degree2=rData.degree2;
        var degree3=rData.degree3;
        var degree4=rData.degree4;
        var degree5=rData.degree5;
        var describ1=rData.describ1;
        var describ2=rData.describ2;
        var describ3=rData.describ3;
        var describ4=rData.describ4;
        var describ5=rData.describ5;
        var other=rData.other;
        var approve=[];
        var shenpiRoleA=[];
        if(degree1!=""&&degree1!=undefined){
            approve[0]=JSON.parse(degree1);
            shenpiRoleA[0]=describ1;
            if(degree2!=""&&undefined!=degree2){
                approve[1]=JSON.parse(degree2);
                shenpiRoleA[1]=describ2;
                if(degree3!=""&&undefined!=degree3){
                    approve[2]=JSON.parse(degree3);
                    shenpiRoleA[2]=describ3;
                    if(degree4!=""&&undefined!=degree4){
                        approve[3]=JSON.parse(degree4);
                        shenpiRoleA[3]=describ4;
                        if(degree5!=""&&undefined!=degree5){
                            approve[4]=JSON.parse(degree5);
                            shenpiRoleA[4]=describ5;
                        }
                    }
                }
            }
            $scope.shenpiRoleA=shenpiRoleA.slice(0);;
            $scope.approve=approve.slice(0);

        }
        if(other!=""&&other!=undefined){
            $scope.CC=JSON.parse(other);;
        }
        if($scope.CC.length>0){
            var el=$compile('<span class="con" ng-click="openYjg(openIndex,2)"><span>抄送人</span>(<span ng-bind="CC.length"></span>) <span class="x" ng-click="removeT($event,2)">x</span></span>')($scope);
            $(".shenpi-ins2").append(el);
            $scope.ifCC2=false;
        }
        for(var i=0; i<$scope.approve.length;i++){
            if(i==$scope.approve.length-1){
                var el=$compile('<span class="con" ng-click="openYjg('+i+')"><span ng-bind="shenpiRoleA['+i+']" ng-if="shenpiRoleA['+i+']"></span><span ng-bind="shenpiRoleA2['+i+']" ng-if="!shenpiRoleA['+i+']"></span>(<span ng-bind="approve['+i+'].length"></span>) <span class="x" ng-click="removeT($event)" >x</span></span>')($scope);
            }else{
                var el=$compile('<span class="con" ng-click="openYjg('+i+')"><span ng-bind="shenpiRoleA['+i+']" ng-if="shenpiRoleA['+i+']"></span><span ng-bind="shenpiRoleA2['+i+']" ng-if="!shenpiRoleA['+i+']"></span>(<span ng-bind="approve['+i+'].length"></span>)</span><span class="arrow"></span>')($scope);
            }
            $(".shenpi-ins").append(el);
        }
        if($scope.approve.length<5){
            if($scope.approve.length>0){
                var el2=$compile('<span class="arrow"></span>')($scope);
                $(".shenpi-ins").append(el2);
            }
        }else{
            $scope.ifAdd=false;
        }
        $scope.openIndex=$scope.approve.length;

    }




//插件代码开始
    $scope.approveA=[];//插件数据
    $scope.approve=[];//外部初始化数据
    $scope.shenpiRoleA=[];//外部数据
    $scope.shenpiRoleA2=["第一审批人","第二审批人","第三审批人","第四审批人","第五审批人"];
    $scope.CC=[];//抄送人外部数据
    $scope.ifCC2=true;
    $scope.ifAdd=true;
    $scope.openIndex=0;

    $scope.okYjg=function(){//确定
        if($scope.openType==2){
            $(".yjg-alert").hide();
            $scope.CC=$scope.approveA.slice(0);
            var len=$(".shenpi-ins2").find(".con").length;
            if(len>=1){}
            else{
                var el=$compile('<span class="con" ng-click="openYjg(openIndex,2)"><span>抄送人</span>(<span ng-bind="CC.length"></span>) <span class="x" ng-click="removeT($event,2)">x</span></span>')($scope);
                $(".shenpi-ins2").append(el);
            }
            if($scope.CC.length>0){
                $scope.ifCC2=false;
            }else{
                $scope.ifCC2=true;
                $(".shenpi-ins2").find(".con").remove();
            }
        }else{
            if($("#shenpiRole").val()==""){
                var ii=$scope.openIndex+1;
                $scope.shenpiRoleA[$scope.openIndex]="";
            }else{
                $scope.shenpiRoleA[$scope.openIndex]=$("#shenpiRole").val();
            }
            if($scope.approveA.length==0){
                alert("请选择审批人");
            }else{
                $scope.approve[$scope.openIndex]=$scope.approveA.slice(0);
                $(".yjg-alert").hide();

                var len=$(".shenpi-ins").find(".con").length;
                if($scope.openIndex>=len){//新增
                    $(".con").find(".x").remove();
                    if(len>=4){
                        $scope.ifAdd=false;
                        var el=$compile('<span class="con" ng-click="openYjg('+len+')"><span ng-bind="shenpiRoleA['+$scope.openIndex+']" ng-if="shenpiRoleA['+$scope.openIndex+']"></span><span ng-bind="shenpiRoleA2['+$scope.openIndex+']" ng-if="!shenpiRoleA['+$scope.openIndex+']"></span>(<span ng-bind="approve['+$scope.openIndex+'].length"></span>) <span class="x" ng-click="removeT($event)">x</span></span>')($scope);
                        $(".shenpi-ins").append(el);

                    }else{
                        var el=$compile('<span class="con" ng-click="openYjg('+len+')"><span ng-bind="shenpiRoleA['+$scope.openIndex+']" ng-if="shenpiRoleA['+$scope.openIndex+']"></span><span ng-bind="shenpiRoleA2['+$scope.openIndex+']" ng-if="!shenpiRoleA['+$scope.openIndex+']"></span>(<span ng-bind="approve['+$scope.openIndex+'].length"></span>) <span class="x" ng-click="removeT($event)">x</span></span><span class="arrow"></span>')($scope);
                        $(".shenpi-ins").append(el);
                        $scope.ifAdd=true;
                    }
                    $scope.openIndex=len+1;

                }else{//编辑之前
                    $scope.openIndex=len;
                }
            }
        }

    }
    $scope.removeT=function(e,id){//删除最后一个数据
        e.stopPropagation();
        if(id==2){
            $(e.target).parent(".con").remove();
            $scope.ifCC2=true;
            $scope.CC=[];
        }else{
            $scope.shenpiRoleA.pop();
            $scope.approve.pop();
            var el2=$compile('<span class="x" ng-click="removeT($event)">x</span>')($scope);
            $(e.target).parent(".con").prev().prev(".con").append(el2);
            $(e.target).parent(".con").next(".arrow").remove();
            $(e.target).parent(".con").remove();
            $scope.openIndex--;
            if($scope.openIndex<=4){
                $scope.ifAdd=true;
            };
        }
    }
    $scope.openYjg=function(id,b){//打开
        $scope.searchKey="";//清空搜索数据
        $scope.openType=b;
        $scope.searchUser();
        if(b==2){//抄送人
            $scope.ifCC=true;
            $(".yjg-alert-box").find(".tit").html("添加抄送人");
            $(".yjg-alert-box").find(".tip3").html("温馨提示：请从左侧列表中选择抄送人。");
            if($scope.CC==undefined){
                $scope.approveA=[];
            }else{
                $scope.approveA=$scope.CC.slice(0);
            }
        }else{//非抄送人
            $scope.ifCC=false;
            $scope.openIndex=id;//打开第几个
            if($scope.approve[$scope.openIndex]==undefined){
                $scope.approveA=[];
                $("#shenpiRole").val("");
            }else{
                $scope.approveA=$scope.approve[$scope.openIndex].slice(0);
                $("#shenpiRole").val($scope.shenpiRoleA[$scope.openIndex]);
            }
        }
        $(".yjg-alert").show();
    }
    $scope.closeYjg=function(){//关闭取消
        $scope.approveA=[];
        $("#shenpiRole").val("");
        var len=$(".shenpi-ins").find(".con").length;
        $scope.openIndex=len;
        $(".yjg-alert").hide();
    }
    $scope.addRole=function(uid,name){//添加用户
        var flag=true;
        if($scope.approveA.length==0){}else{
            for(var i=0; i<$scope.approveA.length;i++){
                if(uid==$scope.approveA[i].uid){ flag=false;break; }
            }
        }
        if(flag){
            $scope.approveA.push({"uid":uid,"name":name});
        }
    }
    $scope.delApprove=function(uid){//删除用户
        var index=base.jsonSearch2($scope.approveA,"uid",uid);
        $scope.approveA.del(index);
    }
    $scope.searchUser=function(){//搜索
        if($scope.searchKey==""){
            $("#yjg-nav").show();$("#yjg-nav2").hide();
        }else{
            $http({
                method: 'POST',
                url: '/credit/element/auditconfig/web/v/getUserListByName',
                data:JSON.stringify({name:$scope.searchKey }),
            }).then(function successCallback(response) {
                var aa=response.data.data;
                var len=aa.length;
                $("#yjg-nav2").show();$("#yjg-nav").hide();
                $("#yjg-nav2").find("ul").html("");
                if(len==0){}else{
                    for(var k=0;k<len;k++){
                        var el=$compile('<li class="son" uid='+aa[k].uid+' ng-click="addRole(\''+aa[k].uid+'\',\''+aa[k].name+'\')" ><p><span></span>'+aa[k].name+'</p></li>')($scope);
                        $("#yjg-nav2").find("ul").append(el);
                    }
                }

            }, function errorCallback(response) {
                // 请求失败执行代码
            });
        }

    }
    $http({//获取部门信息
        method: 'POST',
        url: '/credit/element/auditconfig/web/v/getDeptList',
        data:JSON.stringify({

        }),
    }).then(function successCallback(response) {
        $scope.rows=response.data.data;
        $scope.depA=base.jsonSearch($scope.rows,10000002);//初始化部门数据
        $scope.getUser=function(e,id){//获取用户
            var _this=$(e.target);
            if($(e.target).is("p")){}else{
                _this=$(e.target).parent("p");
            }
            _this.find(".arrow").toggleClass("shou");
            _this.next("ul").toggle();
            if(_this.hasClass("o")){}
            else{
                $http({
                    method: 'POST',
                    url: '/credit/element/auditconfig/web/v/getUserListByDept',
                    data:JSON.stringify({deptId:id }),
                }).then(function successCallback(response) {
                    var aa=response.data.data;
                    var len=aa.length;
                    _this.addClass("o");
                    for(var k=0;k<len;k++){
                        var el=$compile('<li class="son" uid='+aa[k].uid+' ng-click="addRole(\''+aa[k].uid+'\',\''+aa[k].name+'\')" ><p><span></span>'+aa[k].name+'</p></li>')($scope);
                        _this.next("ul").append(el);
                    }
                }, function errorCallback(response) {
                    // 请求失败执行代码
                });
            }
        }
        //console.log($scope.depA);
    }, function errorCallback(response) {
        // 请求失败执行代码
    });


//插件代码结束

    //通用js
    Array.prototype.del=function(index){
        for(var i =0;i <this.length;i++){
            var temp = this[i];
            if(!isNaN(index)){  temp=i; }
            if(temp == index){
                for(var j = i;j <this.length;j++){
                    this[j]=this[j+1];
                }
                this.length = this.length-1;
            }
        }
    }
    var base={
        //json数组遍历
        jsonSearch:function(arr,id){//arr:json 数组 id:搜索字段
            if(arr){
                var len=arr.length;
                var reA=[];
                for(var i=0;i<len;i++){
                    var item=arr[i];
                    if(item.pid==id){
                        var newNode={id:item.id,userCount:item.userCount,name:item.name,menu:base.jsonSearch(arr,item.id)};
                        reA.push(newNode);
                    }
                }
                return reA;
            }
        },
        //json数组遍历
        jsonSearch2:function(arr,s,key){//arr:json 数组 s:搜索字段
            var l=arr.length;
            for(var i=0;i<l;i++){
                if(key==arr[i][s]){ return i;  break; }
            }
        }
    };
});