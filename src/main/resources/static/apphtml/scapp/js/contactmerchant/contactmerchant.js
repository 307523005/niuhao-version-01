/**
 * niuhao-2018-09-28
 * @type {主页}
 */
/**/
var httpurl="http://www.niuxinghao.top:9080/";
//var httpurl = "http://10.20.11.78:8081/";
/*获取到Url里面的参数*/
(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
})(jQuery);
var merchant_id = $.getUrlParam('merchant_id');
window.onload = function () {
    merchant();
}

/*验证商户*/
function merchant() {
    $.ajax({
        type: "POST",
        async: false,//同步
        dataType: "json",
        cache: false,//不缓存此页面
        url: httpurl + "scapp/getmerchant",
        data: {
            merchant_id: merchant_id,
        },
        success: function (r) {
            footer_ul();
            var merchant = r.msg;
            if (r.code === 0) {
                var contactmerchant = "";
                contactmerchant += " <div class=\"order-details-tit\"><h2>长按识别二维码联系商家</h2></div>\n" +
                    "                    <li><i><img src=\"" + httpurl + merchant.merchant_wximge + "\"></i></li>\n" +
                    "                    <li><span>&nbsp;</span></li>\n" +
                    "                    <li><span></span></li>\n" +
                    "                    <li><span></span></li>";
                $("#contactmerchant").html(contactmerchant);


            } else {
                $("#container").html("您访问的链接有问题!!!");
            }

        },
        error: function () {
            $("#container").html("您访问的链接有问题!!!");
        }
    });
}
/*footer_ul*/
function footer_ul() {
    var footer_ul = "";
    footer_ul += "      <li class=\"on\"><a href=\"index.html?merchant_id=" + merchant_id + "\" class=\"home\"><i></i><span class=\"full-block\">首页</span></a></li>\n" +
        "        <li><a href=\"\" class=\"foot-worker\"><i></i><span class=\"full-block\">加盟</span></a></li>\n" +
        "        <li><a href=\"\" class=\"foot-order\"><i></i><span class=\"full-block\">订单</span></a></li>\n" +
        "        <li><a href=\"\" class=\"my\"><i></i><span class=\"full-block\">我的</span></a></li>";
    $("#footer_ul").html(footer_ul);
}