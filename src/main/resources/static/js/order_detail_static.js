OrderDetailStatic = {

    // 定义模块初始化方法
    init: function() {
        OrderDetailStatic.getOrderDetail();
    },

    getOrderDetail : function(){
        var orderId = Common.getQueryString("orderId");
        $.ajax({
            url:"/orderInfo/queryOrderInfoDetail2",
            type:"GET",
            data:{
                orderIds:orderId
            },
            success:function(data){
                if(data.code == 0){
                    OrderDetailStatic.render(data.data);
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
        var goods = detail.miaoshaGoodsVO;
        var order = detail.orderInfo;
        $("#goodsName").text(goods.goodsName);
        $("#goodsImg").attr("src", goods.goodsImg);
        $("#orderPrice").text(order.goodsPrice);
        $("#createDate").text(new Date(order.createDate).format("yyyy-MM-dd hh:mm:ss"));
        var status = "";
        if(order.orderStatus == 0){
            status = "未支付"
        }else if(order.orderStatus == 1){
            status = "待发货";
        }
        $("#orderStatus").text(status);
    },
};
$(function() {
    OrderDetailStatic.init();
});