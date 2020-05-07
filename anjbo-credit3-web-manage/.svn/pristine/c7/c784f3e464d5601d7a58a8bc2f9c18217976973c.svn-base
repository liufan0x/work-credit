/**
 * Created by Administrator on 2017/6/27.
 */
angular.module("anjboApp",['bsTable']).controller("fundBusinessModelEditCtrl",function($scope,$compile,$http,$sce,box,route){
	
	var fundId = route.getParams().fundId;
	var fundName = route.getParams().fundName;
	$scope.fundName = fundName;
    $scope.obj = new Object();
   // $scope.obj.orderNo = route.getParams().orderNo;
    $scope.page = new Object();
    $scope.rows = new Array();
    function getParams(data){
        $scope.page.start = data.offset;
        $scope.page.pageSize = data.limit;
        $scope.page.sortOrder = data.order;
        $scope.page.sortName = data.sort;
        if(fundId==26||fundId==31||fundId==37||fundId==34){//天津信托
        	$scope.page.fundId = fundId;
        }else{
        	$scope.page.fundId = -1;
        }
        return $scope.page;
    }

    var url="/credit/tools/modelConfig/v/page";
    
    window.authorizationevent = {
        'click .disable': disable,
        'click .enable': enable,
        'click .edits': edits
    };

    
    $scope.query = function(){
        $("#table").bootstrapTable('refresh', {url: url}); 
    }
    
    /**
     * 全部禁用
     */
    $scope.disableModel = function(state){
    	if(fundId==26||fundId==31||fundId==37||fundId==34){//天津信托
			$scope.fundId = fundId;
	    }else{
	    	$scope.fundId = -1;
	    }
    	$http({
            url:'/credit/tools/modelConfig/v/updateByFundId',
            method:'POST',
            data:{
            	fundId:$scope.fundId,
            	state:state
            }
        }).success(function(data){
            if("SUCCESS"==data.code){
            	$scope.query();
            	box.boxAlert(data.msg);
            }else{
            	box.boxAlert(data.msg);
            }
        });
    }
    $scope.fundBusinessModel = {
        options: {
            method:"post",
            url:url,
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
                field: '',
                align: 'center',
                valign: 'bottom',
                formatter:function(value,row,index){
                	return index+1;
                }
            }, {
                title: '项目',
                field: 'project',
                align: 'center',
                valign: 'bottom'
            }, {
                title: '准入标准',
                field: 'auditFirstExpression',
                align: 'center',
                valign: 'bottom',
                formatter:function(value, row, index){
                    var html = "";
                    if(row.expression==1){
                        var obj = row.auditFirstResult.split(",");
                        html = value.replace("{0}","<input class='form-control edit edit"+row.id+"' name='auditFirstResult0' type='text'size='5' disabled value='"+obj[0]+"'/>").replace("{1}","<input class='form-control edit"+row.id+"' type='text' size='5' name='auditFirstResult1'  disabled value='"+obj[1]+"'/>");
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
                        html = "<select class='form-control edit edit"+row.id+"' name='auditFirstResult' disabled value='"+row.auditFirstResult+"'>"+option+"</select>";
                    } else if(row.expression==3){
                        html = "<span>"+row.auditFirstResult+"</span>";
                    }
                    return html;
                }
            }, {
                title: '操作',
                field: 'operate',
                align: 'center',
                valign: 'bottom',
                events: authorizationevent,
                formatter : function(value, row, index) {
                	if(row.state==1){
                		return "<a class='disable disable"+row.id+"'  href='javascript:void(0);'>禁用</a> " +
                				"<a class='enable enable"+row.id+"' style='display:none'  href='javascript:void(0);'>启用</a> " +
                				"<a class='edits edits"+row.id+"' href='javascript:void(0);'>编辑</a>";	
                	}else{
                		return "<a class='disable disable"+row.id+"' style='display:none'   href='javascript:void(0);'>禁用</a> " +
        				"<a class='enable enable"+row.id+"' href='javascript:void(0);'>启用</a> " +
        				"<a class='edits edits"+row.id+"' style='display:none'  href='javascript:void(0);'>编辑</a>";	
                	}
                    
                }
            }]
        }
    };
    
    //禁用
    function disable(e,value,row,index){
    	if(!$scope.isEdit){
    		update(e,value,row,index);
    	}
    	$(".disable"+row.id).css("display","none");
    	$(".enable"+row.id).css("display","");
        $(".edit"+row.id).attr("disabled",true);
        $(".edits"+row.id).css("display","none");
        $http({
            url:'/credit/tools/modelConfig/v/edit',
            method:'POST',
            data:{
            	id:row.id,
            	state:2,
            	expression:row.expression
            }
        }).success(function(data){
            if("SUCCESS"==data.code){
                $scope.query();
            }
            box.boxAlert(data.msg);
        });
    }
    
    //启用
    function enable(e,value,row,index){
    	$(".disable"+row.id).css("display","");
    	$(".enable"+row.id).css("display","none");
        $(".edit"+row.id).attr("disabled",true);
        $(".edits"+row.id).css("display","");
        $http({
            url:'/credit/tools/modelConfig/v/edit',
            method:'POST',
            data:{
            	id:row.id,
            	state:1,
            	expression:row.expression
            }
        }).success(function(data){
            if("SUCCESS"==data.code){
                $(".edit"+row.id).attr("disabled",true);
            }else{
            	box.boxAlert(data.msg);
            }
        });
    }
    
    $scope.isEdit = false;
    
    //编辑,保存
    function edits(e,value,row,index){
        if(e.target.text == '编辑'&&row.expression==3){
        	var editsReturn = function(){
        		var auditFirstResult="";
        		$('input[name="auditFirstResults"]:checked').each(function(){ 
        		      var sfruit=$(this).val(); 
        		      if(auditFirstResult.indexOf(sfruit)==-1){
        		    	  auditFirstResult+=sfruit+","
        		      }
        	    });
        		if(auditFirstResult!=""){
        			auditFirstResult = auditFirstResult.substring(0,auditFirstResult.length-1);
        		}
            	//编辑
        		$http({
                    url:'/credit/tools/modelConfig/v/edit',
                    method:'POST',
                    data:{
                    	id:row.id,
                    	auditFirstResult:auditFirstResult,
                    	expression:row.expression
                    }
                }).success(function(data){
                    if("SUCCESS"==data.code){
                    	$scope.query();
                        $(".edit"+row.id).attr("disabled",true);
                    }
                    box.boxAlert(data.msg);
                });
            }
        	 var obj = row.auditFirstExpression.split(",");
             var option = "";
             option += " <input type='checkbox'  ng-checked='false' ng-click='allAuditFirstResults($event)'/>全选";
             angular.forEach(obj,function(data,index,array){
                 if(row.auditFirstResult.indexOf(data)==-1){
                     option += " <input type='checkbox'  ng-checked='false' value="+data+"  name='auditFirstResults'/>"+data
                 } else{
                	 option += " <input type='checkbox'  ng-checked='true' value="+data+"  name='auditFirstResults'/>"+data
                 }
                 if(index!=0&&index%8==0){
                	 option += "<br/>"
                 }
             })
        	angular.element("#business").html(option);
        	box.editAlertW500($scope,row.project,$("#editBusinessModel").html(),editsReturn);
        }else if(e.target.text == '编辑'){
    		e.target.text = '保存';
    		$scope.isEdit = false;
    		$(".edit"+row.id).attr("disabled",false);
    	}else{
    		e.target.text = '编辑';
    		$scope.isEdit = true;
    		$(".edit"+row.id).attr("disabled",true);
    		update(e,value,row,index);
    	}
    }
    
    $scope.allAuditFirstResults = function($event){
    	var bischecked=$($event.target).is(':checked');
    	var fruit=$('input[name="auditFirstResults"]'); 
        fruit.prop('checked',bischecked);
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
            url:'/credit/tools/modelConfig/v/edit',
            method:'POST',
            data:row
        }).success(function(data){
            if("SUCCESS"==data.code){
                $(".edit"+row.id).attr("disabled",true);
                e.target.text = '编辑';
            }
            box.boxAlert(data.msg);
        });
    }

});
