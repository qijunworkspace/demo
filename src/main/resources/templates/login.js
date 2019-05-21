var Login = function(){

    var login = $('#m_login');

    var showErrorMsg = function(form, type, msg) {
        var alert = $('<div class="m-alert m-alert--outline alert alert-' + type + ' alert-dismissible" role="alert">\
			<button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>\
			<span></span>\
		</div>');

        form.find('.alert').remove();
        alert.prependTo(form);
        //alert.animateClass('fadeIn animated');
        mUtil.animateClass(alert[0], 'fadeIn animated');
        alert.find('span').html(msg);
    }

    //== Private Functions
    //显示注册框
    var displaySignUpForm = function() {
        login.removeClass('m-login--signin');

        login.addClass('m-login--signup');
        mUtil.animateClass(login.find('.m-login__signup')[0], 'flipInX animated');
    }
    //显示登录框
    var displaySignInForm = function() {
        login.removeClass('m-login--signup');

        login.addClass('m-login--signin');
        mUtil.animateClass(login.find('.m-login__signin')[0], 'flipInX animated');
        //login.find('.m-login__signin').animateClass('flipInX animated');
    }

    //处理切换输入Form
    var handleFormSwitch = function() {

        $('.alert').remove();

        $('#m_login_signup').click(function(e) {
            e.preventDefault();
            displaySignUpForm();
        });

        $('#m_login_signup_cancel').click(function(e) {
            e.preventDefault();
            displaySignInForm();
        });
    }

    // 处理登录提交
    var handleSignInFormSubmit = function() {
        $('#m_login_signin_submit').click(function(e) {
            e.preventDefault();
            var btn = $(this);
            var form = $(this).closest('form');
            //验证规则
            form.validate({
                rules: {
                    username: {
                        //required: true,
                        //minlength: 5,
                        maxlength: 20,
                        normalizer: function(value) {
                            return $.trim(value);
                        }
                    },
                    password: {
                        required: true,
                        minlength: 5,
                        maxlength: 20,
                        normalizer: function(value) {
                            return $.trim(value);
                        }
                    }
                }
            });

            if (!form.valid()) {
                return;
            }

            btn.addClass('m-loader m-loader--right m-loader--light').attr('disabled', true);
            //提交后台进行验证
            form.ajaxSubmit({
                url: '/auth/login',
                type: 'post',
                success: function(response) {
                    console.log(response)
                    btn.removeClass('m-loader m-loader--right m-loader--light').attr('disabled', false);
                    if (200 != response.code){
                        //更新验证码
                        $("#kaptchaImage").click();
                        //显示错误信息
                        showErrorMsg(form, 'danger', response.message);
                    }else{
                        //服务端跳转页面（后台返回需要跳转的路径）
                        location.href = response.message;
                    }
                },
                error: function (data) {
                    console.log(data);
                    btn.removeClass('m-loader m-loader--right m-loader--light').attr('disabled', false);
                }
                
            });
        });
    }

    //处理注册提交
    var handleSignUpFormSubmit = function() {
        $('#m_login_signup_submit').click(function(e) {
            e.preventDefault();

            var btn = $(this);
            var form = $(this).closest('form');

            form.validate({
                rules: {
                    username: {
                        required: true,
                        minlength: 5,
                        maxlength: 20,
                    },
                    telephone: {
                        required: true,
                    },
                    email: {
                        required: true,
                        email: true
                    },
                    password: {
                        required: true,
                        minlength: 5,
                        maxlength: 20,
                        normalizer: function(value) {
                            return $.trim(value);
                        }
                    },
                    rpassword: {
                        required: true,
                        //需要设置ID来查找
                        equalTo: "#password"
                    },
                    agree: {
                        required: true
                    }
                }
            });

            if (!form.valid()) {
                return;
            }

            btn.addClass('m-loader m-loader--right m-loader--light').attr('disabled', true);
            //提交注册
            form.ajaxSubmit({
                url: '/auth/registration',
                type: 'post',
                dataType: 'json',
                //beforeSerialize:function(){} //序列化提交数据之前的回调函数
                //beforeSubmit:function(){},　　//提交前执行的回调函数
                //clearForm:true,　　　　　　 //提交成功后是否清空表单中的字段值
                //restForm:true,　　　　　　  //提交成功后是否重置表单中的字段值，即恢复到页面加载时的状态
                //timeout:6000 　　　　　 　 //设置请求时间，超过该时间后，自动退出请求，单位(毫秒)。
                //function(response, status, xhr, $form)
                success: function(response) {
                    //判断后台操作是否成功
                    if (200 != response.code){
                        var content = response.message + "<br/>";
                        if (null != response.data){
                            $.each(response.data, function(i, msg){
                                content += msg+"<br/>";
                            });
                        }
                        //提示失败信息
                        showErrorMsg(form, 'danger', content);
                    }else {
                        // 提示成功信息
                        swal({
                            text: response.message,
                            type: 'success',
                            showConfirmButton: true,
                            confirmButtonText: "Back"
                        }).then(function(result) {
                            if (result.value) {
                                console.log(result);
                                form.clearForm();
                                form.validate().resetForm();

                                displaySignInForm();
                                var signInForm = login.find('.m-login__signin form');
                                signInForm.clearForm();
                                signInForm.validate().resetForm();
                            }
                        });

                    }
                    btn.removeClass('m-loader m-loader--right m-loader--light').attr('disabled', false);
                },
                error: function(data){
                    console.log(data);
                    btn.removeClass('m-loader m-loader--right m-loader--light').attr('disabled', false);
                }
            });
        });
    }

    /**
     * 处理验证码
     */
    var handleKaptchaLoad = function(){
        $("#kaptchaImage").click(function(){
            $(this).attr("src","/auth/kaptcha");
        });
    }

    //== Public Functions
    return {
        // public functions
        init: function() {
            handleKaptchaLoad();
            handleFormSwitch();
            handleSignInFormSubmit();
            handleSignUpFormSubmit();
        }
    };

}();


//== Class Initialization
jQuery(document).ready(function() {
    Login.init();
});