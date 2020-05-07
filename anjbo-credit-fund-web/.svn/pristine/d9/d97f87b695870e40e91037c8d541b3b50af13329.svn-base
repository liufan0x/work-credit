//分页插件代码
(function($, window, document, undefined) {
    //定义分页类
    function Paging(element, options) {
        this.element = element;
        //传入形参
        this.options = {
            pageNo: options.pageNo||1,
            totalPage: options.totalPage,
            totalSize:options.totalSize,
            callback:options.callback
        };
        //根据形参初始化分页html和css代码
        this.init();
    }
    //对Paging的实例对象添加公共的属性和方法
    Paging.prototype = {
        constructor: Paging,
        init: function() {
            this.creatHtml();
            this.bindEvent();
        },
        creatHtml: function() {
            var me = this;
            var content = "";
            var current = me.options.pageNo;
            var total = me.options.totalPage;
            var totalNum = me.options.totalSize;
            if(total>1) {
                content += "<a id=\"firstPage\">首页</a><a id='prePage'>上一页</a>";
                //总页数大于6时候
                if (total > 6) {
                    //当前页数小于5时显示省略号
                    if (current < 5) {
                        for (var i = 1; i < 6; i++) {
                            if (current == i) {
                                content += "<a class='current'>" + i + "</a>";
                            } else {
                                content += "<a>" + i + "</a>";
                            }
                        }
                        content += ". . .";
                        content += "<a>" + total + "</a>";
                    } else {
                        //判断页码在末尾的时候
                        if (current < total - 3) {
                            for (var i = current - 2; i < current + 3; i++) {
                                if (current == i) {
                                    content += "<a class='current'>" + i + "</a>";
                                } else {
                                    content += "<a>" + i + "</a>";
                                }
                            }
                            content += ". . .";
                            content += "<a>" + total + "</a>";
                            //页码在中间部分时候
                        } else {
                            content += "<a>1</a>";
                            content += ". . .";
                            for (var i = total - 4; i < total + 1; i++) {
                                if (current == i) {
                                    content += "<a class='current'>" + i + "</a>";
                                } else {
                                    content += "<a>" + i + "</a>";
                                }
                            }
                        }
                    }
                    //页面总数小于6的时候
                } else {
                    for (var i = 1; i < total + 1; i++) {
                        if (current == i) {
                            content += "<a class='current'>" + i + "</a>";
                        } else {
                            content += "<a>" + i + "</a>";
                        }
                    }
                }
                content += "<a id='nextPage'>下一页</a>";
                content += "<a id=\"lastPage\">尾页</a>";
                content += "<span class='totalPages'> 共<span>"+total+"</span>页 </span>";
                content += '<span class="totalSize">/<span>'+totalNum+'</span>条记录 </span>';
                content += '<input type="text"   style="width:40px; margin-left:20px;" onkeyup="base.page(this)" onchange="base.page(this)"> <a href="#" class="current page-tiao" style="margin-left:20px;">1</a> ';
            }else{
                content += "<span class='totalPages'>显示第 1 到第 "+totalNum+" 条记录，总共 "+totalNum+" 条记录</span>";
            }
            me.element.html(content);
        },
        //添加页面操作事件
        bindEvent: function() {
            var me = this;
            me.element.off('click', 'a');
            me.element.on('click', 'a', function() {
                var num = $(this).html();
                if(num>me.options.totalPage){num=me.options.totalPage};
                var id=$(this).attr("id");
                if(id == "prePage") {
                    if(me.options.pageNo == 1) {
                        me.options.pageNo = 1;
                    } else {
                        me.options.pageNo = +me.options.pageNo - 1;
                    }
                } else if(id == "nextPage") {
                    if(me.options.pageNo == me.options.totalPage) {
                        me.options.pageNo = me.options.totalPage
                    } else {
                        me.options.pageNo = +me.options.pageNo + 1;
                    }

                } else if(id =="firstPage") {
                    me.options.pageNo = 1;
                } else if(id =="lastPage") {
                    me.options.pageNo = me.options.totalPage;
                }else{
                    me.options.pageNo = +num;
                }
                me.creatHtml();
                if(me.options.callback) {
                    me.options.callback(me.options.pageNo);
                }
            });
        }
    };
    //通过jQuery对象初始化分页对象
    $.fn.paging = function(options) {
        return new Paging($(this), options);
    }
})(jQuery, window, document);
//插件结束
var  base={
    page:function(obj){//面积规则
        obj.value = obj.value.replace(/[^\d]/g,"");  //清除“数字”和“.”以外的字符
        obj.value = obj.value.replace(/^(\d{2})(\d)*$/,'$1');//2位整数
        if(obj.value !=""){
            obj.value= parseFloat(obj.value);
            $(obj).next("a").text(obj.value);
        }else{
            $(obj).next("a").text(($(obj).prevAll("a.current").text()));
        }
    }
};