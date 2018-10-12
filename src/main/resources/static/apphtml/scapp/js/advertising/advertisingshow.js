/**
 * niuhao-2018-09-28
 * @type {主页}
 */
/**/
//var httpurl="http://www.bantucard.com:9080/";
var httpurl = "http://10.20.11.78:8081/";
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
            if (r.code === 0) {
                footer_ul();
                advertisinglist();
            } else {
                $("#container").html("您访问的链接有问题!!!");
            }

        },
        error: function () {
            $("#container").html("您访问的链接有问题!!!");
        }
    });
}

/*广告列表*/
function advertisinglist() {
    var advertisinglist = "";
    $.ajax({
        type: "POST",
        async: true,//同步
        dataType: "json",
        cache: false,//不缓存此页面
        url: httpurl + "scapp/scappGetAdvertisingByMerchant_id",
        data: {
            merchant_id: merchant_id,
        },
        success: function (r) {
            if (r.code === 0) {
                var advertising = r.msg;
                for (var i = 0; i < advertising.length; i++) {
                    advertisinglist += " <li>\n" +
                        "                <a href=\"advertising.html?merchant_id="+merchant_id+"&advertising_id="+advertising[i].advertising_id+"\">\n" +
                        "                    <div class=\"goods-allocation-txt\">\n" +
                        "                        <h2><span style=\"float:left;width:260px;overflow: hidden;\n" +
                        "            text-overflow: ellipsis;\n" +
                        "            -o-text-overflow: ellipsis;\n" +
                        "            white-space:nowrap;\"></span>"+advertising[i].advertising_title+"</h2>\n" +
                        "                        <p>"+advertising[i].advertising_updatetime+"</p>\n" +
                        "                    </div>\n" +
                        "                </a>\n" +
                        "            </li>";
                }
                $("#advertisinglist").html(advertisinglist);
            }

        },
        error: function () {

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


