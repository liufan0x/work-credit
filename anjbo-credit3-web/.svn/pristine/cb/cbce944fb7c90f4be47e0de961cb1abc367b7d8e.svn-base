var upload_img = {
	imgId : 1,
	blobA:new Array(),
	init : function(){
		this.view = getQueryParameter("view");
		this.height = screen.width/3;
		this.urlArr = [];
		this.initImg();
		this.clickFunc();
		this.chooseImg();
		if(this.view){
			$("div.btnContainer").remove();
		}
		if($("#imgs img").size()>9){
			$("#imgs div[data-id=add]").css("display","none");
		}
	},
	getImgDom : function(url,id){
		var data_id = "";
		var delDiv = "";
		if(id){
			data_id = "data-id="+id;
		}
		if(!this.view){
			delDiv = '<div class="delete" style="color:#fff;position:absolute;top:0;right:0;background:#333;border-radius:50%;width:24px;height:24px;text-align:center;line-height:24px;">X</div>';
		}
		return '<div '+ data_id +' class="imgContainer" style="height:'+this.height+'px;">'+
        '<div><img data-preview-src="" data-preview-group="1" src="'+url+'"/>'+ delDiv +'</div></div>';
	},
	
	appendImg :function(url,id){
		$(this.getImgDom(url,id)).insertBefore("div[data-id='add']");
	},
	
	initImg : function(){
		$("#imgs").html("");
		var urlArrStr = sessionStorage.getItem("fileImgUrl");
		if(urlArrStr&&!this.urlArr.length){
			this.urlArr = JSON.parse(urlArrStr);
		}
		for(var i in this.urlArr){
			$("#imgs").append(this.getImgDom(this.urlArr[i]));
		}
		if(!this.view){
			$("#imgs").append('<div data-id="add" class="imgContainer" style="height:'+this.height+'px;">'+
			        '<div><img id="add_img" src="../img/add_person.png"/><input type="file" accept="image/*" name="file"/></div></div>');
			
		}
	    $("#imgs").append('<div style="clear:both;"></div>');
	},
	//页面点击事件
	clickFunc : function(){
		var self = this;
		//保存按钮
		$("#confirmBtn").click(function(){
			var picA=[];
			$.each($("#imgs img"),function(index,item){
			  var src = $(item).attr("src");
			  if(src.indexOf("http://") == 0){
				  picA.push(src);
			  }
			});
			if(self.blobA.length>0){
				var imgDiv = $("#imgs");
				var count=0;
				var blobCount = 0;
				var errorCount = 0;
				for(var i=0;i<self.blobA.length;i++){
					if(self.blobA[i]){
						blobCount++;
						var formData = new FormData();
						formData.append("file",self.blobA[i],Date.parse(new Date())+".jpg");
						$.ajax({
							type : "POST",
							processData: false,
							contentType: false,
							cache: false,
							async: true,
							url : "http://fs.anjbo.com/fs/img/appUpload",
							data : formData,
							beforeSend:function(){
								$("#loadingPart").show();
							},
							success : function(data){
								if(data.code == "SUCCESS"){
									var url = data.data.imgUrl;
									picA.push(url);
								}else{
									errorCount++;
								}
							},
							complete:function(){
								count++;
								if(blobCount == count){
									$("#loadingPart").hide();
									if(!errorCount){
										$("#imgForm").html("");
										toast("操作成功!");
										sessionStorage.setItem("fileImgUrl",JSON.stringify(picA));
										window.name="";
                				 setTimeout(function() {history.back();},1000);
									}else{
										alertFunc(errorCount+"张图片上传失败!");
									}
								}
							}
						});
						
					}
				}
			}else{
				//toast("操作成功!");
				sessionStorage.setItem("fileImgUrl",JSON.stringify(picA));
				window.name="";
				setTimeout(function() {history.back();},1500);
			}
		});
		//删除按钮
		$("#imgs").on("click","div.delete",function(){
			var container = $(this).closest("div.imgContainer");
			var data_id = container.data("id");
			container.remove();
			self.blobA[data_id]="";
			if($("#imgs img").size()<10){
				$("#imgs div[data-id=add]").css("display","block");
			}
		});
	},
	//图片选择事件
	chooseImg : function(){
		var self = this;
		$("#imgs").on("change","div[data-id=add] input",function(){
			if($("#imgs img").size()>9){
				return;
			}
			if(this.value){
				var start = this.value.indexOf(".");
				var ext = this.value.substring(start+1,this.value.length);
				while(ext.indexOf(".")>-1){
					ext = ext.substring(ext.indexOf(".")+1,ext.length);
				}
				ext = ext.toUpperCase();
				if(ext!="BMP"&&ext!="PNG"&&ext!="GIF"&&ext!="JPG"&&ext!="JPEG"){
					alertFunc("请正确选择图片!");
					return;
				}
			}
			var input = $(this);
			var fileObj = input.get(0).files[0];
			if(fileObj){
				if($("#imgs img").size()>8){
					$("#imgs div[data-id=add]").css("display","none");
				}
				var url = "";
				if(fileObj.size>600000){
					console.log("压缩图片");
					photoCompress(fileObj,1,function(fileObj){
						if(window.URL){
							url = window.URL.createObjectURL(fileObj);
						}else if(window.webkitURL){
							url = window.webkitURL.createObjectURL(fileObj);
						}else{
							alertFunc("浏览器不兼容!")
						}
						self.blobA[self.imgId]=fileObj;
						self.appendImg(url,self.imgId++);
//						input.attr("data-id",self.imgId);
//						input.get(0).files[0]=fileObj;
//						$("#imgForm").append(input);
//						$('<input type="file" accept="image/*" name="file"/>').insertBefore("#add_img");
						
					});
				}else{
					if(window.URL){
						url = window.URL.createObjectURL(fileObj);
					}else if(window.webkitURL){
						url = window.webkitURL.createObjectURL(fileObj);
					}else{
						alertFunc("浏览器不兼容!")
					}
					self.blobA[self.imgId]=fileObj;
					self.appendImg(url,self.imgId++);
				}
				input.val("");
			}
		});
	}
}
$(function(){
	
	upload_img.init();
	//图片查看插件
	mui.previewImage();
});

function photoCompress(obj,quality,fun){
	var reader = new FileReader();
	reader.readAsDataURL(obj);
	reader.onload = function(){
        var path = this.result;
        var img = new Image();
        img.src = path;
        img.onload = function(){
        	var that = this;
        	// 默认按比例压缩
            var w = that.width,
                h = that.height,
                scale = w / h;
            if(w>720){w=720;h=w/scale;}
            
            //生成canvas
            var canvas = document.createElement('canvas');
            var ctx = canvas.getContext('2d');
            // 创建属性节点
            var anw = document.createAttribute("width");
            anw.nodeValue = w;
            var anh = document.createAttribute("height");
            anh.nodeValue = h;
            canvas.setAttributeNode(anw);
            canvas.setAttributeNode(anh);
            ctx.drawImage(that, 0, 0, w, h);
            var base64 = canvas.toDataURL('image/jpeg', 1);
            var blob = convertBase64UrlToBlob(base64);
            fun(blob);
        }
    }
}
/**
 * 将以base64的图片url数据转换为Blob
 * @param urlData
 *            用url方式表示的base64图片数据
 */
function convertBase64UrlToBlob(urlData){
    var arr = urlData.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], {type:mime});
}
