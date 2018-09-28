/**
 * niuhao-2018-09-28
 * @type {主页}
 */
/**/

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

    $.ajax({
        type: "POST",
        async: false,//同步
        dataType: "json",
        cache: false,//不缓存此页面
        url: "http://localhost:8081/" + "scapp/getmerchant",
        data: {
            merchant_id: merchant_id,
        },
        success: function (r) {
            if (r.code === 0) {
                var merchant = r.msg;
                var merchanthtml = "<em>" + merchant.merchant_region + "</em>\n" +
                    "<h2>" + merchant.merchant_name + "</h2>\n<a href=\"tel:" + merchant.merchant_phone + "\"><span class=\"tel\"></span></a>"
                $("#merchant").html(merchanthtml);
            }

        },
        error: function () {

        }
    });
}

/*轮播图*/
var sc_List = "<li><a href=\"#\"><img src=\"images/ind-banner.jpg\" alt=\"\"/><\/a><\/li>";
sc_List += "<li><a href=\"#\"><img src=\"images/ind-banner2.jpg\" alt=\"\"/><\/a><\/li>";
sc_List += "<li><a href=\"#\"><img src=\"images/ind-banner4.jpg\" alt=\"\"/><\/a><\/li>";
sc_List += "<li><a href=\"#\"><img src=\"images/ind-banner4.jpg\" alt=\"\"/><\/a><\/li>";
$("#sc_List").html(sc_List);
//"<li class=\"newLi\"><span>" + _userName + "<\/span><span>" + userEamil + "<\/span><span>" + userPhone + "<\/span><span><input type=\"button\" value=\"删除\" onclick=\"this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)\" \/><\/span><\/li>";
TouchSlide({
    slideCell: "#index-banner",
    titCell: ".banner-tit li",//开启自动分页 autoPage:true ，此时设置 titCell 为导航元素包裹层
    mainCell: ".banner-pics-list",
    effect: "left",
    autoPlay: true,//自动播放
    //autoPage:true, //自动分页
})