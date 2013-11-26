var valid_name = false;
var valid_password = false;
var valid_nickname = false;
var cookie_username = "jinn_wecoded_username";

$(document).ready(function(){
    bindEvent();
    cookieControl();
    setFocus();
});

function setFocus(){
	if (!$("#username-login").val()) {
		$("#username-login").focus();
	} else if (!$("#password-login").val()) {
		$("#password-login").focus();
	}
}

function cookieControl(){
    var username = $.cookie(cookie_username);
    if (!!username) {
        $("#username-login").val(username);
    }
}
function setCookie(){
    $.cookie(cookie_username, $("#username-login").val(), {expires: 7, path : "/"});
}

function bindEvent(){
    $("#submit").click(function(){
        var username = $("#username-login").val();
        var password = $("#password-login").val();
        if(!$.trim(username)){
            return;
        }
        if(!$.trim(password)){
            return;
        }
        $("#modal-tip").html("登录中...");
        $("#loginModal").modal("show");
        $("#hide-modal").hide();
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
    $("#username-login").keydown(function(event){
        if(event.keyCode == "13"){
            $("#password-login").select();
        }
    });
    $("#password-login").keydown(function(event){
        if(event.keyCode == "13"){
            $("#submit").click();
        }
    });
}


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
		var url = contextPath + '/login/validateName';
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
		
		var url = contextPath + '/login/signup';
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
				$("#modal-tip").html("注册成功!<br/>登录中...");
				$("#loginModal").modal("show");
			}else if(code == "duplicate"){
				$("#modal-tip").html("There is duplicate name");
	            $("#hide-modal").show();
	            $("#loginModal").modal("show");
			}else if(code == "error"){
			    $("#modal-tip").html("A database error occured");
			    $("#hide-modal").show();
			    $("#loginModal").modal("show");
			}else{
			    $("#modal-tip").html("Unknown error occured on signup");
			    $("#hide-modal").show();
			    $("#loginModal").modal("show");
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
	var url = contextPath + '/login/signin';
    var params = {
    		"username" : $("#username-login").val(),
    		"password" : $.md5($("#password-login").val())
    };
    $.post(url, params, function(data, textStatus, jqXHR){
    	var code = data;
    	if (code == "success") {
    	    setCookie();
            var redirect = $("#redirect").val();
            if (redirect) {
                window.location.href = contextPath + decodeURIComponent(redirect);
            } else {
                window.location.reload();
            }
		} else if (code == "nouser") {
		    $("#modal-tip").html("User not found");
		    $("#hide-modal").show();
		} else if (code == "wrongpass") {
		    $("#modal-tip").html("Wrong password ( try default 12345 )");
            $("#hide-modal").show();
		} else {
		    $("#modal-tip").html("Unknown error occured on sign in");
		    $("#hide-modal").show();
		}
    });
}


