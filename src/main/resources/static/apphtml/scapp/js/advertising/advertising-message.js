/**
 * niuhao-2018-09-28
 * @type {广告评论}
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
            footer_ul();
            var merchant = r.msg;
            if (r.code === 0) {
                $("#title").html(type);
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
        "        <li class=\"on\"><a  href=\"advertisingshow.html?type=最新资讯&merchant_id=" + merchant_id + "&advertisingTypeId=\"\" class=\"foot-order\"><i></i><span class=\"full-block\">最新资讯</span></a></li>\n" +
        "        <li><a href=\"\" class=\"foot-worker\"><i></i><span class=\"full-block\">敬请期待</span></a></li>\n" +
        "        <li><a href=\"\" class=\"my\"><i></i><span class=\"full-block\">敬请期待</span></a></li>";
    $("#footer_ul").html(footer_ul);
}

function message() {
    window.location.href = "/apphtml/scapp/advertisingmessage.html?merchant_id=" + merchant_id + "&advertising_id=" + advertisingId;
}

/*提交留言*/
function upmessage() {
    $("#upmessage").attr("disabled", true);
    var messages = $("#message-content").val();
    if (messages.trim()!= "") {
        $.ajax({
            type: "POST",
            async: true,//异步
            dataType: "json",
            cache: false,//不缓存此页面
            url: httpurl + "scapp/addAdvertisingMessage",
            data: {
                merchantId: merchant_id, advertisingId: advertisingId, advmessageContent: messages,
            },
            success: function (r) {
                if (r.code === 0) {
                    new TipBox({type: 'success', str: '留言提交成功', hasBtn: true});
                    message();
                } else {
                    $("#container").html("您访问的链接有问题!!!");
                }

            },
            error: function () {
                $("#container").html("您访问的链接有问题!!!");
            }
        });
    }else {
        new TipBox({type: 'error', str: '请输入内容！', hasBtn: true});
        $("#upmessage").attr("disabled", false);

    }
}
