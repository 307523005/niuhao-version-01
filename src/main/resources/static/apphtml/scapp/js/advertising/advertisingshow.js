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
        if (r != null) return decodeURI(r[2]);
        return null;
    }
})(jQuery);
var merchant_id = $.getUrlParam('merchant_id');
var advertisingTypeId = $.getUrlParam('advertisingTypeId');
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
var size = 0;

function advertisinglist(num,query) {
    var advertisinglist = "";
    $.ajax({
        type: "POST",
        async: true,//同步
        dataType: "json",
        cache: false,//不缓存此页面
        url: httpurl + "scapp/scappGetAdvertisingByMerchant_id",
        data: {
            merchant_id: merchant_id,
            advertisingTypeId: advertisingTypeId,
            num: num,
            query:query,
        },
        success: function (r) {
            if (r.code === 0) {
                var advertising = r.msg;
                size = advertising.length;
                for (var i = 0; i < advertising.length; i++) {
                    advertisinglist += " <li>\n" +
                        "                <a href=\"advertising.html?merchant_id=" + merchant_id + "&advertising_id=" + advertising[i].advertisingId + "\">\n" +
                        "                    <div class=\"goods-allocation-txt\">\n" +
                        "                        <h2><span style=\"float:left;width:260px;overflow: hidden;\n" +
                        "            text-overflow: ellipsis;\n" +
                        "            -o-text-overflow: ellipsis;\n" +
                        "            white-space:nowrap;\"></span>" + advertising[i].advertisingTitle + "</h2>\n" +
                        "                        <p>" + advertising[i].advertisingUpdatetime + "</p>\n" +
                        "                    </div>\n" +
                        "                </a>\n" +
                        "            </li>";
                }
                if (num<1){
                    $("#advertisinglist").html("");
                }
                var aa = $("#advertisinglist").html();
                $("#advertisinglist").html(aa + advertisinglist);
                $("#title").html(type);
                $("#titl_hi").html(type);
            }

        },
        error: function () {

        }
    });
    return size;
}

/*footer_ul*/
function footer_ul() {
    var footer_ul = "";
    footer_ul += "      <li ><a href=\"index.html?merchant_id=" + merchant_id + "\" class=\"home\"><i></i><span class=\"full-block\">首页</span></a></li>\n" +
        "        <li class=\"on\"><a  href=\"advertisingshow.html?type=最新资讯&merchant_id=" + merchant_id + "&advertisingTypeId=\"\" class=\"foot-order\"><i></i><span class=\"full-block\">" + type + "</span></a></li>\n" +
        "        <li><a href=\"\" class=\"foot-worker\"><i></i><span class=\"full-block\">活动促销</span></a></li>\n" +
        "        <li><a href=\"\" class=\"my\"><i></i><span class=\"full-block\">敬请期待</span></a></li>";
    $("#footer_ul").html(footer_ul);
}

function query() {
    var query = $("#query").val();
    advertisinglist(0,query.replace(/\s+/g,""));
}