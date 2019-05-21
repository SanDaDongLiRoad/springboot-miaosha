Login = {

    // 定义模块初始化方法
    init: function() {
        Login.bindEvent();
    },

    // 定义事件绑定
    bindEvent : function() {

        //登录
        $("#loginForm #login-btn").on("click", Login.login);
    },

    login : function(){
        $("#loginForm").validate({
            submitHandler:function(form){
                Login.doLogin();
            }
        });
    },
    doLogin : function(){
        Common.showLoading();
        var inputPass = $("#password").val();
        var salt = Common.passsword_salt;
        var str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
        var password = md5(str);
        $.ajax({
            url: "/login/do_login",
            type: "POST",
            data:{
                mobile:$("#mobile").val(),
                password: password
            },
            success:function(data){
                layer.closeAll();
                if(data.code == 0){
                    layer.msg("登录成功");
                    window.location.href="/miaoShaGoods/to_list";
                }else{
                    layer.msg(data.msg);
                }
            },
            error:function(){
                layer.closeAll();
            }
        });
    }
};

$(function() {
    Login.init();
});