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
                var merchant = r.msg;
                var merchanthtml = "<em>" + merchant.merchant_region + "</em>\n" +
                    "<h2>" + merchant.merchant_name + "</h2>\n<a href=\"tel:" + merchant.merchant_phone + "\"><span class=\"tel\"></span></a>"
                $("#merchant").html(merchanthtml);
                bannerimglist();
                goodstypelist();
                footer_ul();
            } else {
                $("#container").html("您访问的链接有问题!!!");
            }

        },
        error: function () {

        }
    });
}

/*轮播图*/

/*
sc_List = "<li><a href=\"#\"><img src=\"images/ind-banner.jpg\" alt=\"\"/><\/a><\/li>";
sc_List += "<li><a href=\"#\"><img src=\"images/ind-banner2.jpg\" alt=\"\"/><\/a><\/li>";
sc_List += "<li><a href=\"#\"><img src=\"images/ind-banner4.jpg\" alt=\"\"/><\/a><\/li>";
sc_List += "<li><a href=\"#\"><img src=\"images/ind-banner4.jpg\" alt=\"\"/><\/a><\/li>";
*/
function bannerimglist() {
    var sc_List = "";
    $.ajax({
        type: "POST",
        async: false,//同步
        dataType: "json",
        cache: false,//不缓存此页面
        //url: "http://localhost:9080/" + "scapp/getBannerimgTop3",
        url: httpurl + "scapp/getBannerimgTop3",
        data: {
            merchant_id: merchant_id,
        },
        success: function (r) {
            if (r.code === 0) {
                var merchant = r.msg;
                for (var i = 0; i < merchant.length; i++) {
                    sc_List += "<li><a href=\"#\"><img src=\"" + httpurl + merchant[i].bannerimg_imgurl + "\" alt=\"\"/><\/a><\/li>";
                }
                $("#sc_List").html(sc_List);
            }

        },
        error: function () {

        }
    });

//"<li class=\"newLi\"><span>" + _userName + "<\/span><span>" + userEamil + "<\/span><span>" + userPhone + "<\/span><span><input type=\"button\" value=\"删除\" onclick=\"this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)\" \/><\/span><\/li>";
    TouchSlide({
        slideCell: "#index-banner",
        titCell: ".banner-tit li",//开启自动分页 autoPage:true ，此时设置 titCell 为导航元素包裹层
        mainCell: ".banner-pics-list",
        effect: "left",
        autoPlay: true,//自动播放
        //autoPage:true, //自动分页
    })
}

/*商品类型列表*/
function goodstypelist() {
    var goodstype_List = "";
    $.ajax({
        type: "POST",
        async: true,//同步
        dataType: "json",
        cache: false,//不缓存此页面
        url: httpurl + "scapp/scappGetGoodstype",
        data: {
            merchant_id: merchant_id,
        },
        success: function (r) {
            if (r.code === 0) {
                var merchant = r.msg;
                /*goodstype_htmlurl,goodstype_imageurl*/
                for (var i = 0; i < merchant.length; i++) {
                    //if (i<7) {
                    goodstype_List += "<li><div><a href=\"" + merchant[i].goodstype_htmlurl + "?merchant_id="+merchant_id+"&goodstype_id="+merchant[i].goodstype_id+"\"><i><img src=\"" + merchant[i].goodstype_imageurl + "\"><\/i><em>" + merchant[i].goodstype_name + "<\/em><\/a><\/div><\/li>";
                    // }else {
                    //  goodstype_List += "  <li><div><a href=\"add-class.html\"><i><img src=\"images\/xczx.png\"><\/i><em>"+merchant[i].goodstype_name+"<\/em><\/a><\/div><\/li>";

                    // }
                }
                goodstype_List += " <li><div><a href=\"javascript:void(0)\" class=\"more\"><i><img src=\"images\/more.png\"><\/i><em>更多<\/em><\/a><\/div><\/li>";

                $("#goodstype_List").html(goodstype_List);
                more();
            }


        },
        error: function () {

        }
    });
}

/*栏目更多*/
function more() {
    $(".ind-nav").each(function () {
        var self = $(this);
        var n = self.find("ul li").length;
        if (n <= 8) {
            self.find(".more").addClass("hide-more");
        } else {
            self.find(".more").removeClass("hide-more");
        }
        self.find(".more").click(function () {
            self.find("ul").toggleClass("intro");
            self.find(".more").toggleClass("add-more");
        });
    });

}

/*广告类型列表*/
function advertisinglist() {

}

/*footer_ul*/
function footer_ul() {
    var footer_ul = "";
    footer_ul += "      <li class=\"on\"><a href=\"index.html?merchant_id="+merchant_id+"\" class=\"home\"><i></i><span class=\"full-block\">首页</span></a></li>\n" +
        "        <li><a href=\"worker-join.html\" class=\"foot-worker\"><i></i><span class=\"full-block\">加盟</span></a></li>\n" +
        "        <li><a href=\"my-order.html\" class=\"foot-order\"><i></i><span class=\"full-block\">订单</span></a></li>\n" +
        "        <li><a href=\"my.html\" class=\"my\"><i></i><span class=\"full-block\">我的</span></a></li>";
    $("#footer_ul").html(footer_ul);
}

