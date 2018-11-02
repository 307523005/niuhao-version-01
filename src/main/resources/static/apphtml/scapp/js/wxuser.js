/**
 * 微信h5登陆
 */
document.write("<script language=javascript src='js/deploy.js'></script>");
$(function () {
    var off = true;
    // 对浏览器的UserAgent进行正则匹配，不含有微信独有标识的则为其他浏览器
    var useragent = navigator.userAgent;
    if (useragent.match(/MicroMessenger/i) != 'MicroMessenger') {
        // 这里警告框会阻塞当前页面继续加载
        // alert('已禁止本次访问：您必须使用微信内置浏览器访问本页面！');
        // 以下代码是用javascript强行关闭当前页面
        //   if (confirm("退出当前页面")) {
        //   var opened = window.open('about:blank', '_self');
        //   opened.opener = null;
        //   opened.close();
        //   }

        // var htl = '<div class = "weui_msg"><div class="weui_icon_area"><i class="weui_icon_info weui_icon_msg"></i></div><div class="weui_text_area"><h4 class="weui_msg_title">请在微信扫一扫打开链接</h4></div></div>';
        // $("#dowebok").html(htl);
        off = false;
    }

    // // 封装正则--用来获取url地址后的参数
    function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }

    var content = $('#content');
    var code = GetQueryString('code');
    var userid = '';
    console.log('code');
    console.log(code);
    if (!off) {
        $('#click').css("display", "none");
        $('#cik').css("display", "none");
    }
    $.post(httpurl + '/scapp/H5login', {
        code: code
    }, function (res) {
        var res = JSON.parse(res);
        console.log(res);
        userid = res.userid;
        alert(userid);
    }, 'JSON');


/*    $.post(httpurl + "/scapp/getUserPic", {
        user_uid: uid
    }, function (res) {
        console.log('getUserPic');
        console.log(res);
        //  var res = JSON.parse(res);
        //  if (res.res.Code == 200) {
        if (res) {
            $("#deptpic").attr('src', res.user_deptpic);
        } else {
            alert("获取图片失败");
        }
    })*/
});
/*

    1 获取code 在确保微信公众账号拥有授权作用域（scope参数）的权限的前提下（服务号获得高级接口后，默认拥有scope参数中的snsapi_base和snsapi_userinfo），引导关注者打开如下页面：

    https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxba6f729bc6aa5ddc&redirect_uri=http://192.168.2.109/details.html&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
         */