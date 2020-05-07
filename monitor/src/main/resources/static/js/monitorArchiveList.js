 //(1)初始化
    $(function () {
    	var agencyId = $("#agencyId").val();
        var oTable = new TableInit();
        oTable.Init();
        
        //(1)新增
        $("#btn_add").click(function(){
        	location.href = "add";   
        });
        
        //(2)关键字检索
        $("#btn_query").click(function () {
        	var agencyId = $("#agencyId").val();
            //点击查询是 使用刷新 处理刷新参数
            var opt = {
                url: '/'+agencyId+'/page',
                silent: true,
                query: {
                	identityNo: $("#identityNo").val(), //条件1
                	estateNo: $("#estateNo").val()           //条件2 ....
                }
            };
            $('#tb_departments').bootstrapTable('refresh', opt);

        });
        
        $("#btn_edit").click(function(){
        	var i = 0;
            var id;
            $("input[name='btSelectItem']:checked").each(function () {
                i++;
                id = $(this).parents("tr").attr("data-uniqueid");
            })
            if (i > 1) {
                alert("编辑只支持单一操作");
                $("#edit").attr('aria-hidden',true);
                return;
            }else if(i<=0){
           	 alert("请选择要修改的行");
           	$("#edit").attr('aria-hidden',true);
           	 return;
            }
	       	var row = $.map($('#tb_departments').bootstrapTable('getSelections'),function(row){return row;});//获取勾选的数据的值
	       	$("#identityNoE").val(row[0].identityNo);
	       	$("#idE").val(row[0].id);
	       	$("#phoneE").val(row[0].phone);
        });//function的作用是获取勾选的那一行的数据中的数据的值，idE、projectNameE、authorE对应修改弹框上的值
       
        $("#editSave").click(function(){
        	 $.ajax({
            		url:"edit",
        		    type:"POST",
        		    data:{
        		        	id:$("#idE").val(),	
        		    		phone:$("#phoneE").val()
        		        	},
        		     dataType:"json"
            }).done(function(data){ 
            	if(data.code=='SUCCESS'){
            		alert('修改成功');
            		$("#idE").val()
                	$("#projectNameE").val("");
                	$("#authorE").val("");
                	$("#tb_departments").bootstrapTable('refresh');
            	} //收到服务器的返回修改成功的json数据后，清除input中的数据
            }
            );
        });
       //editSave会将修改的数据post到后台，后台进行数据的查询判断

        //(3)修改一、获取编号进入下一页
       /* $("#btn_edit").click(function () {
            var i = 0;
            var id;
            $("input[name='btSelectItem']:checked").each(function () {
                i++;
                id = $(this).parents("tr").attr("data-uniqueid");
            })
            if (i > 1) {
                alert("编辑只支持单一操作")
            } else {
                if (i != 0) {
                    alert("获取选中的id为" + id);
                    window.location.href = "/index/index";
                } else {
                    alert("请选中要编辑的数据");
                }

            }

        })*/


        //(4)删除及批量删除

        $("#btn_delete").click(function () {
        	var i = 0;
            var id;
            $("input[name='btSelectItem']:checked").each(function () {
                i++;
                id = $(this).parents("tr").attr("data-uniqueid");
            })
            if(i<=0){
           	 alert("请选择要删除的行");
           	 return;
            }
            if (confirm("确认要删除吗？")) {
                var idlist = "";
                $("input[name='btSelectItem']:checked").each(function () {
                    idlist += ","+$(this).parents("tr").attr("data-uniqueid");
                })
               if(idlist!=''){
            	   $.ajax({
               		url:"delete",
           		    type:"POST",
           		    data:{
           		        	ids:idlist.substring(1)
           		        	},
           		     dataType:"json"
	               }).done(function(data){ 
	               	if(data.code=='SUCCESS'){
	               		alert('删除成功');
	                   	$("#tb_departments").bootstrapTable('refresh');
	               	} 
	               }
	               );
               }
            }
        });

    });
    var TableInit = function () {
        var oTableInit = new Object();
        var agencyId = $("#agencyId").val();
        oTableInit.Init = function () {
            $('#tb_departments').bootstrapTable({
                url: '/'+agencyId+'/page', //请求后台的URL（*）
                method: 'get', //请求方式（*）
                toolbar: '#toolbar', //工具按钮用哪个容器
                striped: true, //是否显示行间隔色
                cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true, //是否显示分页（*）
                sortable: false, //是否启用排序
                sortOrder: "asc", //排序方式
                queryParams: oTableInit.queryParams,//传递参数（*）
                sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
                pageNumber: 1, //初始化加载第一页，默认第一页
                pageSize: 10, //每页的记录行数（*）
                pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                search: false, //是否显示表格搜索，此搜索是客户端搜索,也可以是服务端检索
                strictSearch: true,
                showColumns: true, //是否显示所有的列
                showRefresh: true, //是否显示刷新按钮
                minimumCountColumns: 2, //最少允许的列数
                clickToSelect: true, //是否启用点击选中行
                height: 800, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id", //每一行的唯一标识，一般为主键列
                showToggle: true, //是否显示详细视图和列表视图的切换按钮
                cardView: false, //是否显示详细视图
                detailView: false, //是否显示父子表
                columns: [{
                    checkbox: true
                }, {
                    title: '序号',
                    field: 'no',
                    align: 'right',
                    valign: 'bottom',
                    formatter:function(value,row,index){
                        //return index+1; //序号正序排序从1开始
                        var pageSize=$('#tb_departments').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
                        var pageNumber=$('#tb_departments').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
                        return pageSize * (pageNumber - 1) + index + 1;    //返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
                    }

                }, {
                    title: '创建时间',
                    field: 'createTimeStr',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '姓名/身份证号',
                    field: 'identityNo',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '房产证号',
                    field: 'estateNo',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '产权证类型',
                    field: 'estateTypeStr',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '手机号',
                    field: 'phone',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '查询时间',
                    field: 'sectionTime',
                    align: 'center',
                    valign: 'bottom',
                    formatter:function(value,row,index){
                    	
                    	return row.startTimeStr+ "至" + row.endTimeStr;
                    }
                }, {
                    title: '查询结果（最新）',
                    field: 'propertyStatus',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '变更记录',
                    field: 'changeRecord',
                    align: 'center',
                    valign: 'bottom'
                }, {
                    title: '详情',
                    field: 'message',
                    align: 'center',
                    valign: 'bottom'
                }],
                //保存的使用
                onEditableSave: function (field, row, oldValue, $el) {
                    //可进行异步操作

                    $.ajax({
                        type: "post",
                        url: "/index/Edit",
                        data: row,
                        dataType: 'JSON',
                        success: function (data, status) {
                            if (status == "success") {
                                alert('提交数据成功');
                            }
                        },
                        error: function () {
                            alert('编辑失败');
                        },
                        complete: function () {

                        }

                    });
                }

            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit, //页面大小
                offset: params.offset, //页码
            };
            return temp;
        };
        return oTableInit;
    };
