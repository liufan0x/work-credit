/**
 * Created by Administrator on 2017/6/27.
 */
angular.module("anjboApp",['bsTable']).controller("riskModelCtrl",function($scope,$compile,$http,$sce,box){
    $scope.obj = new Object();
   // $scope.obj.orderNo = route.getParams().orderNo;
    $scope.page = new Object();
    $scope.rows = new Array();
    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        $scope.page.sortOrder = data.order;
        $scope.page.sortName = data.sort;
        return $scope.page;
    }

    window.authorizationevent = {
        'click .authorization': authorization
    };

    
    $scope.riskModel = {
        options: {
            method:"post",
            url:"/credit/risk/riskmodel/v/list",
            queryParams:getParams,
            sidePagination:'server',
            undefinedText:"-",
            cache: false,
            striped: true,
            pagination: true,
            pageNumber: 1,
            pageSize: 15,
            pageList: ['All'],
            showColumns: true,
            showRefresh: false,
            columns: [{
                title: '序号',
                field: 'id',
                align: 'center',
                valign: 'bottom'
            }, {
                title: '风控项目',
                field: 'project',
                align: 'center',
                valign: 'bottom'
            }, {
                title: '风控初审',
                field: 'auditFirstExpression',
                align: 'center',
                valign: 'bottom',
                formatter:function(value, row, index){
                    var html = "";
                    if(row.expression==1){
                        var obj = row.auditFirstResult.split(",");
                        html = value.replace("{0}","<input class='form-control edit"+row.id+"' name='auditFirstResult0' type='text'size='5' disabled value='"+obj[0]+"'/>").replace("{1}","<input class='form-control edit"+row.id+"' type='text' size='5' name='auditFirstResult1'  disabled value='"+obj[1]+"'/>");
                    } else if(row.expression==2){
                        var obj = row.auditFirstExpression.split(",");
                        var option = "";
                        angular.forEach(obj,function(data,index,array){
                            if(data==row.auditFirstResult){
                                option += "<option value='"+data+"' selected>"+data+"</option>"
                            } else{
                                option += "<option value='"+data+"'>"+data+"</option>"
                            }
                        })
                        html = "<select class='form-control edit"+row.id+"' name='auditFirstResult' disabled value='"+row.auditFirstResult+"'>"+option+"</select>";
                    } else if(row.expression==3){
                        var obj = row.auditFirstExpression.split(",");
                        var option = "";
                        angular.forEach(obj,function(data,index,array){
                            if(data==row.auditFirstResult){
                                option += "<option value='"+data+"' selected>"+data+"</option>"
                            } else{
                                option += "<option value='"+data+"'>"+data+"</option>"
                            }
                        })
                        html = "<select class='form-control edit"+row.id+"' name='auditFirstResult' disabled  value='"+row.auditFirstResult+"'>"+option+"</select>";
                    }
                    return html;
                }
            }, {
                title: '风控经理',
                field: 'auditFinalExpression',
                align: 'center',
                valign: 'bottom',
                formatter:function(value, row, index){
                    var html = "";
                    if(row.expression==1){
                        var obj = row.auditFinalResult.split(",");
                        html = value.replace("{0}","<input type='text'size='5' name='auditFinalResult0' class='form-control edit"+row.id+"'  disabled value='"+obj[0]+"'/>").replace("{1}","<input class='form-control edit"+row.id+"' name='auditFinalResult1' type='text' size='5'  disabled value='"+obj[1]+"'/>");

                    } else if(row.expression==2){
                        var obj = row.auditFinalExpression.split(",");
                        var option = "";
                        angular.forEach(obj,function(data,index,array){
                            if(data==row.auditFinalResult){
                                option += "<option value='"+data+"' selected>"+data+"</option>"
                            } else{
                                option += "<option value='"+data+"'>"+data+"</option>"
                            }
                        })
                        html = "<select class='form-control edit"+row.id+"' name='auditFinalResult' disabled value='"+row.auditFinalResult+"'>"+option+"</select>";
                    } else if(row.expression==3){
                        var obj = row.auditFinalExpression.split(",");
                        var option = "";
                        angular.forEach(obj,function(data,index,array){
                            if(data==row.auditFinalResult){
                                option += "<option value='"+data+"' selected>"+data+"</option>"
                            } else{
                                option += "<option value='"+data+"'>"+data+"</option>"
                            }
                        })
                        html = "<select  class='form-control edit"+row.id+"' name='auditFinalResult' disabled value='"+row.auditFinalResult+"'>"+option+"</select>";
                    }
                    return html;
                }
            },{
                title: '操作',
                field: 'operate',
                align: 'center',
                valign: 'bottom',
                events: authorizationevent,
                formatter : function(value, row, index) {
                	if($scope.meunAuth.indexOf('auth21auth')>=0){
                		return "<a class='authorization'  href='javascript:void(0);'>授权</a>";	
                	}else{
                		return "";
                	}
                    
                }
            }]
        }
    };

    function authorization(e,value,row,index){
        if(e.target.text == '授权'){
            e.target.text = '保存';
            $(".edit"+row.id).attr("disabled",false);
        }else{
            update(e,value,row,index);
        }
    }

    function update(e,value,row,index){
        if(row.expression==1){
            var finalTmp = $(".edit"+row.id+"[name='auditFinalResult0']").val() +","+$(".edit"+row.id+"[name='auditFinalResult1']").val();
            var firstTmp = $(".edit"+row.id+"[name='auditFirstResult0']").val() +","+$(".edit"+row.id+"[name='auditFirstResult1']").val();
            row.auditFinalResult = finalTmp;
            row.auditFirstResult = firstTmp;
        } else {
            row.auditFinalResult = $(".edit"+row.id+"[name='auditFinalResult']").val();
            row.auditFirstResult = $(".edit"+row.id+"[name='auditFirstResult']").val();
        }

        $http({
            url:'/credit/risk/riskmodel/v/updateCredit',
            method:'POST',
            data:row
        }).success(function(data){
            if("SUCCESS"==data.code){
                $(".edit"+row.id).attr("disabled",true);
                e.target.text = '授权';
            }
            box.boxAlert(data.msg);
        });
    }

});
