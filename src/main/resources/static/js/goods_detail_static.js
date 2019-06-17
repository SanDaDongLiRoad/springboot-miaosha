GoodsDetailStatic = {

    // 定义模块初始化方法
    init: function() {
        GoodsDetailStatic.bindEvent();
        GoodsDetailStatic.getDetail();
    },

    // 定义事件绑定
    bindEvent : function() {

        //获取秒杀地址
        $("#goods-detail #buy-btn").on("click", GoodsDetailStatic.getMiaoshaPath);

        //刷新验证码
        $("#goods-detail #verifyCodeImg").on("click", GoodsDetailStatic.refreshVerifyCode);
    },

    getDetail : function(){
        var goodsId = Common.getQueryString("goodsId");
        $.ajax({
            url:"/miaoShaGoods/to_detail3/"+goodsId,
            type:"GET",
            success:function(data){
                if(data.code == 0){
                    GoodsDetailStatic.render(data.data);
                }else{
                    layer.msg(data.msg);
                }
            },
            error:function(){
                layer.msg("客户端请求有误");
            }
        });
    },

    render : function(detail){
        var remainSeconds = detail.remainSeconds;
        var goods = detail.miaoshaGoodsVO;
        if(detail.miaoshaUser){
            $("#userTip").hide();
        }
        $("#goodsName").text(goods.goodsName);
        $("#goodsImg").attr("src", goods.goodsImg);
        $("#startTime").text(new Date(goods.startDate).format("yyyy-MM-dd hh:mm:ss"));
        $("#remainSeconds").val(remainSeconds);
        $("#goodsId").val(goods.goodsId);
        $("#goodsPrice").text(goods.goodsPrice);
        $("#miaoshaPrice").text(goods.miaoshaPrice);
        $("#stockCount").text(goods.stockCount);
        GoodsDetailStatic.countDown();
    },

    countDown : function(){
        var remainSeconds = $("#remainSeconds").val();
        var timeout;
        if(remainSeconds > 0){//秒杀还没开始，倒计时
            $("#goods-detail #buy-btn").attr("disabled", true);
            $("#miaoshaTip").html("秒杀倒计时："+remainSeconds+"秒");
            timeout = setTimeout(function(){
                $("#countDown").text(remainSeconds - 1);
                $("#remainSeconds").val(remainSeconds - 1);
                GoodsDetailStatic.countDown();
            },1000);
        }else if(remainSeconds == 0){//秒杀进行中
            $("#goods-detail #buy-btn").attr("disabled", false);
            if(timeout){
                clearTimeout(timeout);
            }
            $("#miaoshaTip").html("秒杀进行中");
            // $("#verifyCodeImg").attr("src", "/miaosha/verifyCode?goodsId="+$("#goodsId").val());
            // $("#verifyCodeImg").show();
            // $("#verifyCode").show();
        }else{//秒杀已经结束
            $("#goods-detail #buy-btn").attr("disabled", true);
            $("#miaoshaTip").html("秒杀已经结束");
            // $("#verifyCodeImg").hide();
            // $("#verifyCode").hide();
        }
    },

    getMiaoshaPath : function(){
        var goodsId = $("#goodsId").val();
        Common.showLoading();
        $.ajax({
            url:"/miaosha/getMiaoShaPath",
            type:"GET",
            data:{
                goodsId:goodsId,
                verifyCode:$("#verifyCode").val()
            },
            success:function(data){
                if(data.code == 0){
                    var path = data.data;
                    GoodsDetailStatic.doMiaosha(path);
                }else{
                    layer.msg(data.msg);
                }
            },
            error:function(){
                layer.msg("客户端请求有误");
            }
        });
    },

    doMiaosha : function (path){
        $.ajax({
            url:"/miaosha/do_miaosha2",
            type:"POST",
            data:{
                goodsId:$("#goodsId").val()
            },
            success:function(data){
                if(data.code == 0){
                    // window.location.href="/order_detail.htm?orderId="+data.data;
                    GoodsDetailStatic.getMiaoshaResult($("#goodsId").val());
                }else{
                    layer.msg(data.msg);
                }
            },
            error:function(){
                layer.msg("客户端请求有误");
            }
        });
    },

    getMiaoshaResult : function(goodsId){
        Common.showLoading();
        $.ajax({
            url:"/miaosha/result",
            type:"GET",
            data:{
                goodsId:$("#goodsId").val(),
            },
            success:function(data){
                if(data.code == 2000){
                    layer.confirm("恭喜你，秒杀成功！查看订单？", {btn:["确定","取消"]},
                    function(){
                        window.location.href="/order_detail.htm?orderId="+data.data;
                    },
                    function(){
                        layer.closeAll();
                    });
                }else if(data.code == 2001){//秒杀失败
                    layer.msg(data.msg);
                }else if(data.code == 2005){//继续轮询
                    setTimeout(function(){
                        GoodsDetailStatic.getMiaoshaResult(goodsId);
                    }, 200);
                }
                else{
                    layer.msg(data.msg);
                }
            },
            error:function(){
                layer.msg("客户端请求有误");
            }
        });
    },

    refreshVerifyCode : function (){
        $("#verifyCodeImg").attr("src", "/miaosha/verifyCode?goodsId="+$("#goodsId").val()+"&timestamp="+new Date().getTime());
    }
};
$(function() {
    GoodsDetailStatic.init();
});