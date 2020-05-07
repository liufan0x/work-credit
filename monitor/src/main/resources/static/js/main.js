jQuery(document).ready(function($){
	if( $('.floating-labels').length > 0 ) floatLabels();

	function floatLabels() {
		var inputFields = $('.floating-labels .cd-label').next();
		inputFields.each(function(){
			var singleInput = $(this);
			//check if user is filling one of the form fields 
			checkVal(singleInput);
			singleInput.on('change keyup', function(){
				checkVal(singleInput);	
			});
		});
	}

	function checkVal(inputField) {
		( inputField.val() == '' ) ? inputField.prev('.cd-label').removeClass('float') : inputField.prev('.cd-label').addClass('float');
	}
	
	function submit(){
	}
	
	$('.submit').click(function(){
		if($("#estateType").val()==null||$("#estateType").val()==''){
			alert('房产证号不能为空');
			return;
		}
		if($("#identityNo").val()==null||$("#identityNo").val()==''){
			alert('姓名/身份证号不能为空');
			return;
		}
		if($("#phone").val()==null||$("#phone").val()==''){
			alert('手机号不能为空');
			return;
		}
		$('.submit').attr("disabled", true);
		var agencyId = $("#agencyId").val();
		$.ajax({
       		url:'/'+agencyId+'/save',
       		timeout : 300000,
   		    type:"POST",
   		    data:{
   		    	"estateType":$("#estateType").val(),
   		    	"estateNo":$("#estateNo").val(),
   		    	"identityNo":$("#identityNo").val(),
   		    	"phone":$("#phone").val()
   		    },
   		     dataType:"json"
           }).done(function(data){ 
           	if(data.code=='SUCCESS'){
           		alert('添加成功');
           		location.href = "index"; 
           	}else{
           		alert(data.msg);
           		$('.submit').attr("disabled", false);
           	} 
           }
           );
	});
	
});