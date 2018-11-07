/**
 * niuhao-2018-09-28
 * @type {主页}
 */
/**/
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
var goods_id = $.getUrlParam('goods_id');
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
            var merchant = r.msg;
            if (r.code === 0) {
                goodsparticulars();
                footer();
            } else {
                $("#container").html("您访问的链接有问题!!!");
            }

        },
        error: function () {
            $("#container").html("您访问的链接有问题!!!");
        }
    });
}

/*goodsparticulars*/
function goodsparticulars() {
    $.ajax({
        type: "POST",
        async: false,//同步
        dataType: "json",
        cache: false,//不缓存此页面
        url: httpurl + "scapp/scappGetGoodsBygoods_id",
        data: {
            goods_id: goods_id,
        },
        success: function (r) {
            if (r.code === 0) {
                var goods = r.msg;
                var goodshtml = "<div class=\"details-img\" ><img src=\""+httpurl+goods.goods_bigimge+"\"></div>\n" +

                    "    <div class=\"min-price\">\n" +
                    "        <div class=\"min-price-tit\"><span>"+goods.goods_name+"</span></div>\n" +
                    "    </div>\n" +
                    "    <ul class=\"details-price clearfix\">\n" +
                    "        <li><em>"+goods.goods_price+"</em>"+goods.goods_units+"</li>\n" +
                    "    </ul>\n" +
                    "    <div class=\"min-price\">\n" +
                    "        <div class=\"min-price-tit\"><h2>活动</h2></div>\n" +
                    "        <div class=\"min-price-con\">"+goods.goods_activity+"</div>\n" +
                    "    </div>\n" +
                    "    <div class=\"min-price\">\n" +
                    "        <div class=\"min-price-tit\"><h2>商品介绍</h2></div>\n" +
                    "        <div class=\"min-price-con\">"+goods.goods_intro+"</div>\n" +
                    "    </div>\n<div class=\"min-price-bot\" id=\"footer\">\n" +
                    "    </div><div style=\"display: none;\" id=\"copyMy\">"+goods.goods_name+"<br>"+goods.goods_price+goods.goods_units+"<br>"+goods.goods_activity+"</div>";

                $("#container").html(goodshtml);
                $("#title").html(goods.goods_name);

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
/*function footer_ul() {
    var footer_ul = "";
    footer_ul += "<li class=\"on\"><a href=\"index.html?merchant_id=" + merchant_id + "\" class=\"home\"><i></i><span class=\"full-block\">首页</span></a></li>" +
        "<li><a href=\"worker-join.html\" class=\"foot-worker\"><i></i><span class=\"full-block\">加盟</span></a></li>" +
        "<li><a href=\"my-order.html\" class=\"foot-order\"><i></i><span class=\"full-block\">订单</span></a></li>" +
        "<li><a href=\"my.html\" class=\"my\"><i></i><span class=\"full-block\">我的</span></a></li>";
    $("#footer_ul").html(footer_ul);
}*/
function footer() {
    var footer = "";
    footer += "<div class=\"min-price-tel\"><a href=\"javascript:copyFn();\" >点击复制</a></div>" +
        "        <div class=\"min-price-order\"><a href=\"contactmerchant.html?merchant_id="+merchant_id+"\">微信联系</a></div>";
    $("#footer").html(footer);
}

function copyFn(){
    document.getElementById("copyMy").style.display="";//显
    var val = document.getElementById('copyMy');
    window.getSelection().selectAllChildren(val);
    document.execCommand ("Copy");
    document.getElementById("copyMy").style.display="none";//隐藏
    new TipBox({type:'success',str:'复制商品信息成功',hasBtn:true});
}
