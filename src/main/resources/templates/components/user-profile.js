var UserProfile = function(){

    var loadCurrentUserInfo = function() {
        $.ajax({
            url: "/user/getcurrentUser",
            type: 'POST',
            dataType: "json",
            success: function(result) {
                if (null == result.data){
                    swal("USER NOT FOUND", "Please refresh page", "error");
                    return;
                }
                if (result.code == 200){
                    $("#userForm").find("[name=id]").val(result.data.id);
                    $("#userForm").find("[name=username]").val(result.data.username);
                    $("#userForm").find("[name=telephone]").val(result.data.telephone);
                    $("#userForm").find("[name=email]").val(result.data.email);
                    $("#userForm").find("[name=password]").val(result.data.password);
                    $("#userForm").find("[name=rPassword]").val(result.data.password);
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
            }
        });
    }

    var handlePasswordBtn =function() {
        $("#passwordBtn").click(function(){
            $(".pass-toggle").toggleClass("m--hide");
        });
    };

    var handleUpdateBtn = function(){

        $("#updateBtn").click(function(e){
            e.preventDefault();
            //验证form
            var form = $("#userForm");
            var btn = $(this);

            form.validate({
                rules: {
                    password:{
                        required: true,
                        minlength: 5,
                        maxlength: 20,
                        normalizer: function(value) {
                            return $.trim(value);
                        }
                    },
                    telephone: {
                        required: true,
                    },
                    email: {
                        required: true,
                        email: true
                    },
                    rpassword: {
                        required: true,
                        //需要设置ID来查找
                        equalTo: "#newPassword"
                    },
                },
                messages: {}
            });
            if (!form.valid()) {
                return;
            }

            //添加禁止点击
            btn.addClass('m-loader m-loader--right m-loader--light').attr('disabled', true);
            //提交更改
            form.ajaxSubmit({
                url: '/user/updateCurrentUser',
                type: 'post',
                data: form.serialize(),
                success: function(response) {
                    if (200 == response.code){
                        swal("OK",response.message,"success");
                    }
                    btn.removeClass('m-loader m-loader--right m-loader--light').attr('disabled', false);
                },
                error: function (data) {
                    btn.removeClass('m-loader m-loader--right m-loader--light').attr('disabled', false);
                }

            });

        });
    }

    return {
        init: function(){
            handlePasswordBtn();
            loadCurrentUserInfo();
            handleUpdateBtn();
        }
    }
}();


jQuery(document).ready(function(){
    UserProfile.init();
});