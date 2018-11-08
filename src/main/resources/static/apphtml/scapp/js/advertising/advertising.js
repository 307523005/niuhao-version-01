/**
 * niuhao-2018-09-28
 * @type {广告详情}
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
var advertisingId = $.getUrlParam('advertising_id');
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
                showadvertising(merchant.merchant_name);

            } else {
                $("#container").html("您访问的链接有问题!!!");
            }

        },
        error: function () {
            $("#container").html("您访问的链接有问题!!!");
        }
    });
}

/*showadvertising*/
function showadvertising(merchant_name) {
    $.ajax({
        type: "POST",
        async: false,//同步
        dataType: "json",
        cache: false,//不缓存此页面
        url: httpurl + "scapp/scappgetadvertising",
        data: {
            advertisingId: advertisingId,
        },
        success: function (r) {
            if (r.code === 0) {

                var advertising = r.msg;
                var num = intraday();
                var advertisinghtml = "<h1 class=\"am-article-title\" >" + advertising.advertisingTitle + "</h1>\n" +
                    "        <p class=\"am-article-meta\" >" + merchant_name + "&nbsp;&nbsp;&nbsp;&nbsp;" + advertising.advertisingUpdatetime + "&nbsp;&nbsp;&nbsp;&nbsp;阅读量:" + num + "</p>";
                $("#advertisingshowtitle").html(advertisinghtml);
                var arrEntities = {'lt': '<', 'gt': '>', 'nbsp': ' ', 'amp': '&', 'quot': '"'};
                var advertisingContent = advertising.advertisingContent.replace(/&(lt|gt|nbsp|amp|quot);/ig, function (all, t) {
                    return arrEntities[t];
                });
                $("#advertisingshow").html(advertisingContent);
                $("#title").html(advertising.advertisingTitle);

            } else {
                $("#container").html("您访问的链接有问题!!!");
            }

        },
        error: function () {
            $("#container").html("您访问的链接有问题!!!");
        }
    });
}

function footer_ul() {
    var footer_ul = "";
    footer_ul += "      <li ><a href=\"index.html?merchant_id=" + merchant_id + "\" class=\"home\"><i></i><span class=\"full-block\">首页</span></a></li>\n" +
        "        <li class=\"on\"><a  href=\"advertisingshow.html?type=最新资讯&merchant_id=" + merchant_id +  "&advertisingTypeId=\"\" class=\"foot-worker\"><i></i><span class=\"full-block\">最新资讯</span></a></li>\n" +
        "        <li><a href=\"\" class=\"foot-order\"><i></i><span class=\"full-block\">活动促销</span></a></li>\n" +
        "        <li><a href=\"\" class=\"my\"><i></i><span class=\"full-block\">敬请期待</span></a></li>";
    $("#footer_ul").html(footer_ul);
}

function intraday() {

    var cip = returnCitySN["cip"];
    var cname = returnCitySN["cname"];
    //var allcookies = document.cookie;
    var advertisingnum = 0;
    $.ajax({
        type: "POST",
        async: false,//tong
        dataType: "json",
        cache: false,//不缓存此页面
        url: httpurl + "scapp/addHitsAdvertising",
        data: {
            advertising_id: advertisingId,
            cip: cip + cname,
        },
        success: function (r) {
            advertisingnum = r.advertising_intraday;

        },
        error: function () {
            $("#container").html("您访问的链接有问题!!!");
        }
    });
    return advertisingnum;
}