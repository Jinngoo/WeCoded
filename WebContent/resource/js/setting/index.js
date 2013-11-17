$(document).ready(function() {
    $("#mainContent").slideDown("fast");
    $("#saveBtn").click(function() {
        updateBasicInfo();
    });
    $("#nickname").blur(function() {
        validateNickname();
    });
    $("#password").blur(function() {
        validatePassword();
    });
    $("#repassword").blur(function() {
        validatePassword();
    });
});

function updateBasicInfo() {
    var passwordValide = validatePassword();
    var nicknameValide = validateNickname();
    if(!passwordValide || !nicknameValide){
        $("#invalidSubmit").click();
        return;
    }
    
    $("#save-tip").html("");
    var url = contextPath + "/setting/updateBasicInfo";
    var params = {
        "nickname" : $("#nickname").val()
    };
    if (J.isNotEmpty($("#password").val())) {
        $.extend(params, {
            "password" : $.md5($("#password").val())
        });
    }
    $("#saveBtn").button("loading");
    $.post(url, params, function(result, textStatus, jqXHR) {
        $("#saveBtn").button("reset");
        var code = result.code;
        if (code == "success") {
            $("#save-tip").html("保存成功");
            if (J.isNotEmpty($("#nickname").val())) {
                $("#topNickName").html($("#nickname").val()); //更新top nav 上的nickname
            }
        } else if (code == "error") {
            alert(result.message);
        } else {
            alert("Unknown error occured");
        }
    });
}

function validateNickname() {
    return $("#nickname").get(0).checkValidity();;
}

function validatePassword() {
    var password = $("#password").val();
    var repassword = $("#repassword").val();
    if (J.isNotEmpty(password) || J.isNotEmpty(repassword)) {
        if (password != repassword) {
            $("#password").get(0).setCustomValidity('两次密码不一致');
            $("#repassword").get(0).setCustomValidity('两次密码不一致');
            return false;
        }
    }
    $("#password").get(0).setCustomValidity('');
    $("#repassword").get(0).setCustomValidity('');
    return true;
}
function reloadAvatar(){
    $("#avator").attr("src", $("#avator").attr("src"));
}

