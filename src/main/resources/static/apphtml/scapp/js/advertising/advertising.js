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
        if (r != null) return decodeURI(r[2]);
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
                showMessage();

            } else {
                $("#container").html("您访问的信息已删除!!!");
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
        "        <li class=\"on\"><a  href=\"advertisingshow.html?type=最新资讯&merchant_id=" + merchant_id + "&advertisingTypeId=\"\" class=\"foot-order\"><i></i><span class=\"full-block\">最新资讯</span></a></li>\n" +
        "        <li><a href=\"\" class=\"foot-worker\"><i></i><span class=\"full-block\">敬请期待</span></a></li>\n" +
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

/*评论展示*/
function showMessage() {
    $.ajax({
        type: "POST",
        async: true,//异步
        dataType: "json",
        cache: false,//不缓存此页面
        url: httpurl + "scapp/getAdvertisingMessageByadvertisingId",
        data: {
            advertisingId: advertisingId,
        },
        success: function (r) {
            if (r.code === 0) {
                var advertisingMessage = r.msg;
                var message = "<div style=\"margin-left:20px;float: left;margin-top: 10px;\">精选留言</div>\n" +
                    "        <div style=\"margin-right: 20px;float: right;margin-top: 10px;\"><a\n" +
                    "                href=\"advertising-message.html?type=" + "" + "&merchant_id=" + merchant_id + "&advertising_id=" + advertisingId + "\"><span>留言</span></a>" +
                    "        </div>" +
                    "<br>" +
                    "<br>" +
                    "        <div class=\"add-class\">";
                if (advertisingMessage.length > 0) {
                    for (var i = 0; i < advertisingMessage.length; i++) {
                        message += "<li class=\"clearfix-message\"><span class=\"am-article-tourist\">游客</span><br>" + advertisingMessage[i].advmessageContent + "</li>";
                    }
                }else {
                    message +="<span class=\"am-article-tourist\">暂无留言</span>";
                }


                message += "<li class=\"clearfix-message\"><span class=\"am-article-author\">作者</span><br>感谢您的留言！</li></ul></div>";
                $("#show-message").html(message);
            } else {
                $("#show-message").html("您访问的出现异常!!!");
            }

        },
        error: function () {
            $("#show-message").html("您访问的出现异常!!!");
        }
    });

}

//示例
$('#js-go_top').gotoTop({
    offset : 100, //距离顶部的位置
    speed : 300, //移动到顶部的速度
    /*     iconSpeed : 300, //icon动画样式的速度*/
    animationShow : {
        'transform' : 'translate(0,0)',
        'transition': 'transform .5s ease-in-out'
    }, //icon动画样式显示时
    animationHide : {
        'transform' : 'translate(80px,0)',
        'transition': 'transform .5s ease-in-out'
    } //icon动画样式隐藏时
});