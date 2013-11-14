var valid_name = false;
var valid_password = false;
var valid_nickname = false;

$(document).ready(function(){
	$("#submit").click(function(){
		var username = $("#username-login").val();
		var password = $("#password-login").val();
		if(!$.trim(username)){
			return;
		}
		if(!$.trim(password)){
			return;
		}
		$("#login-tip").html("登录中...");
		$("#loginModal").modal("show");
	});
	$("#btn-signup").click(function(){
		signup();
	});
	$("#name").blur(function(){
		validateName();
	});
	$("#password").blur(function(){
		validatePassword(true);
	});
	$("#repassword").blur(function(){
		validatePassword(false);
	});
	$("#nickname").blur(function(){
		validateNickname();
	});
	$("#loginModal").on("shown.bs.modal", function(){
		setTimeout(function(){
			login();
		}, 200);
	});
});

function validateInput(input){
    var value = $(input).val();
    value = stripscript(value);
    $(input).val(value);
    //value=value.replace(/[\u4E00-\u9FA5]/g,'')
}
function stripscript(s) { 
    var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？\u4E00-\u9FA5]") 
    var rs = ""; 
    for (var i = 0; i < s.length; i++) { 
        rs = rs+s.substr(i, 1).replace(pattern, ''); 
    } 
    return rs; 
}

function validateNickname(){
	if($.trim($("#nickname").val())){
		$("#nickname-group").removeClass("error");
		$("#nickname-group").addClass("success");
		valid_nickname = true;
	}else{
		$("#nickname-group").removeClass("success");
		$("#nickname-group").addClass("error");
		valid_nickname = false;
	}
}

function validateName(callback){
	var name = $("#name").val();
	if(!$.trim(name)){
		$("#name-group").removeClass("success");
		$("#name-group").addClass("error");
		$("#name-tip").html("登录名咋是空呢?");
		valid_name = false;
		if(callback){
			callback.call(this);
		}
	}else{
		var url = 'login/validateName';
		var params = {"name":name};
		$.post(url, params, function(data, textStatus, jqXHR){
			var access = data;
			if(!!access){
				$("#name-group").removeClass("error");
				$("#name-group").addClass("success");
				$("#name-tip").html("");
				valid_name = true;
				
			}else{
				$("#name-group").removeClass("success");
				$("#name-group").addClass("error");
				$("#name-tip").html("登录名有人使用~");
				valid_name = false;
			}
			if(callback){
				callback.call(this);
			}
		});
	}
}

function validatePassword(canBeNull){
	var password = $("#password").val();
	var repassword = $("#repassword").val();
	if($.trim(password) && ($.trim(repassword) || !canBeNull)){
		if(password != repassword){
			$("#password-group").removeClass("success");
			$("#repassword-group").removeClass("success");
			$("#repassword-tip").html("两次密码不一致");
			$("#repassword-group").addClass("error");
			valid_password = false;
		}else{
			$("#password-group").addClass("success");
			$("#repassword-tip").html("");
			$("#repassword-group").removeClass("error");
			$("#repassword-group").addClass("success");
			valid_password = true;
		}
	}
}

function signup(){
	var doSubmit = function(){
		var name = $("#name").val();
		var password = $.md5($("#password").val());
		var nickname = encodeURIComponent(encodeURIComponent($("#nickname").val()));
		
		var url = 'login/signup';
		var params = {"name":name, "password":password, "nickname":nickname};
		$("#btn-signup").button("loading");
		$.post(url, params, function(data, textStatus, jqXHR){
			$("#btn-signup").button("reset");
			var code = data;
			if(code == "success"){
				$("#username-login").val($("#name").val());
				$("#password-login").val($("#password").val());
				$('#signupModal').modal('hide');
				clearForm("signup-form");
				$("#login-tip").html("注册成功!<br/>登录中...");
				$("#loginModal").modal("show");
			}else if(code == "duplicate"){
				alert("There is duplicate name");
			}else if(code == "error"){
				alert("A database error occured");
			}else{
				alert("Unknown error occured on signup");
			}
		});
	};
	validateName(function(){
		if(valid_name){
			validatePassword(false);
			if(valid_password){
				validateNickname();
				if(valid_nickname){
					doSubmit();
				}
			}
		}
	});
}

function clearForm(id){
	$("#"+id).find(':input').each(function(){
		 switch(this.type){  
			 case 'checkbox':  
			 case 'radio':  
				 this.checked = false;  
				 break;  
	         case 'passsword':  
	         case 'select-multiple':  
	         case 'select-one':  
	         case 'text':  
	         case 'textarea':  
	             $(this).val('');  
	             break;
	         default:
	        	 $(this).val('');  
		 }
	});
	$("#"+id).find('.control-group').each(function(){
		$(this).removeClass("success");
	});
}

function login(){
	var url = 'login/signin';
    var params = {
    		"username":$("#username-login").val(),
    		"password":$.md5($("#password-login").val())
    };
    $.post(url, params, function(data, textStatus, jqXHR){
    	var code = data;
    	if (code == "success") {
			window.location.reload();
		} else if (code == "nouser") {
			alert("User not found");
			$("#loginModal").modal("hide");
		} else if (code == "wrongpass") {
			alert("Wrong password");
			$("#loginModal").modal("hide");
		} else {
			alert("Unknown error occured on login");
			$("#loginModal").modal("hide");
		}
    });
}


