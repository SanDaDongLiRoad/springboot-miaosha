GoodsDetail = {

    // 定义模块初始化方法
    init: function() {
        GoodsDetail.bindEvent();
        GoodsDetail.countDown();
    },

    // 定义事件绑定
    bindEvent : function() {

        //执行秒杀
        //$("#goodslist #buy-btn").on("click", GoodsDetail.doMiaosha());
    },

    countDown : function(){
        var remainSeconds = $("#remainSeconds").val();
        var timeout;
        if(remainSeconds > 0){//秒杀还没开始，倒计时
            $("#buy-btn").attr("disabled", true);
            timeout = setTimeout(function(){
                $("#countDown").text(remainSeconds - 1);
                $("#remainSeconds").val(remainSeconds - 1);
                GoodsDetail.countDown();
            },1000);
        }else if(remainSeconds == 0){//秒杀进行中
            $("#buy-btn").attr("disabled", false);
            if(timeout){
                clearTimeout(timeout);
            }
            $("#miaoshaTip").html("秒杀进行中");
        }else{//秒杀已经结束
            $("#buy-btn").attr("disabled", true);
            $("#miaoshaTip").html("秒杀已经结束");
        }
    },

    doMiaosha : function () {
        Common.showLoading();
        var goodsId = $("#goods-detail #goodsId").val();
        $.ajax({
            url: "/miaosha/do_miaosha",
            type: "POST",
            data:{
                goodsId: goodsId
            },
            success:function(data){
                layer.closeAll();
                if(data.code == 0){
                    layer.msg("秒杀成功");
                    window.location.href="/orderInfo/queryOrderInfoDetail?orderIds="+data.data;
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
    GoodsDetail.init();
});