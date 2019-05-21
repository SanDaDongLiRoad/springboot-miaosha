Login = {

    // 定义模块初始化方法
    init: function() {
        Login.bindEvent();
        Login.hiddenClearLoginName();
        Login.hiddenClearPassword();
    },

    // 定义事件绑定
    bindEvent : function() {

        //登录
        $("#loginForm #login-btn").on("click", Login.login);

        //清空登录名按钮绑定
        $("#loginForm #clear-loginName-btn").on("click", Login.clearLoginName);

        //清空登录名按钮绑定
        $("#loginForm #clear-password-btn").on("click", Login.clearPassword);

        //隐藏清空登录名叉号事件绑定
        $("#loginForm #loginName").bind('input propertychange',Login.hiddenClearLoginName);

        //隐藏清空密码叉号事件绑定
        $("#loginForm #password").bind('input propertychange',Login.hiddenClearPassword);
    },

    //隐藏清空登录名叉号
    hiddenClearLoginName : function(){
        if($("#loginForm #loginName").val()==null || $("#loginForm #loginName").val()=="" || $("#loginForm #loginName").val().length == 0){
            $("#clear-loginName-btn").html("&nbsp;&nbsp;");
        }else{
            $("#clear-loginName-btn").html("×");
        }
    },

    //隐藏清空密码叉号
    hiddenClearPassword : function(){
        if($("#loginForm #password").val()==null || $("#loginForm #password").val()=="" || $("#loginForm #password").val().length == 0){
            $("#clear-password-btn").html("&nbsp;&nbsp;");
        }else{
            $("#clear-password-btn").html("×");
        }
    },

    //清空登录名
    clearLoginName : function(){
        $("#loginName").val("");
        $("#clear-loginName-btn").html(" ");
    },

    //清空密码
    clearPassword : function(){
        $("#password").val("");
        $("#clear-password-btn").html(" ");
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
                mobile:$("#loginName").val(),
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