/**
 * niuhao-2018-09-28
 * @type {主页}
 */
document.write("<script language=javascript src='js/deploy.js'></script>");
/*获取到Url里面的参数*/
(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI (r[2]);
        return null;
    }
})(jQuery);
var merchant_id = $.getUrlParam('merchant_id');
var goodstype_id = $.getUrlParam('goodstype_id');
var type = $.getUrlParam('type');
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
                goodsshow();
            } else {
                $("#container").html("您访问的链接有问题!!!");
            }

        },
        error: function () {
            $("#container").html("您访问的链接有问题!!!");
        }
    });
}

/*goodsshow*/
function goodsshow(query) {
    $.ajax({
        type: "POST",
        async: false,//同步
        dataType: "json",
        cache: false,//不缓存此页面
        url: httpurl + "scapp/scappGetGoods",
        data: {
            merchant_id: merchant_id,
            goodstype_id: goodstype_id,
            query:query,
        },
        success: function (r) {
            if (r.code === 0) {
                var goods = r.msg;
                var goodshtml = "";
                for (var i = 0; i < goods.length; i++) {
                    goodshtml += " <li><a href=\"goodsparticulars.html?merchant_id="+merchant_id+"&goods_id=" + goods[i].goods_id + "\"><img src=\"" +httpurl+ goods[i].goods_littleimge + "\"><span>"+goods[i].goods_name+"</span></a></li>";
                }
                //$("#title").html(goodstype_id);
                $("#goodsshow").html(goodshtml);
                $("#title").html(type);
                $("#title_hi").html(type);


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
    footer_ul += "      <li ><a href=\"index.html?merchant_id=" + merchant_id + "\" class=\"home\"><i></i><span class=\"full-block\">首页</span></a></li>\n" +
        "        <li><a href=\"advertisingshow.html?type=最新资讯&merchant_id=" + merchant_id +  "&advertisingTypeId=\"\" class=\"foot-order\"><i></i><span class=\"full-block\">最新资讯</span></a></li>\n" +
        "        <li class=\"on\"><a href=\"\" class=\"foot-worker\"><i></i><span class=\"full-block\">"+type+"</span></a></li>\n" +
        "        <li><a href=\"\" class=\"my\"><i></i><span class=\"full-block\">敬请期待</span></a></li>";
    $("#footer_ul").html(footer_ul);
}


function query() {
    var query = $("#query").val();
    goodsshow(query.replace(/\s+/g,""));
}