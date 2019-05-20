//定义命名空间根据模块名和页面名称定义
$.namespace("miaosha.Common");
miaosha.Common = {
    //salt
    passsword_salt : "1a2b3c4d",
    /**
     * 展示loading
     * @returns {*}
     */
    showLoading : function (){
        var idx = layer.msg('处理中...', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false,offset: '0px', time:100000});
        return idx;
    },
    /**
     * 获取url参数
     */
    getQueryString : function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r != null) return unescape(r[2]);
        return null;
    }
};

