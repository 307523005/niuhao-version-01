<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>消息通知</title>
</head>

<body>
<div>
    <h2>邮件消息通知</h2>
    <table width="800" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#ffffff"
           style="font-family:'Microsoft YaHei';">
        <tbody id="tbody">
        <tr>
            <td>
                <table width="800" border="0" align="center" cellpadding="0" cellspacing="0" height="40"></table>
            </td>
        </tr>

        <tr>
            <td>

                <table width="800" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#373d41"
                       height="48" style="font-family:'Microsoft YaHei';">
                    <tbody>
                    <tr>

                        <td  width="703" height="48" colspan="2" align="left" valign="middle"
                            style="font-size:16px;color:#ffffff; padding-right:20px;">
                            &nbsp;&nbsp;<a href="http://www.niuxinghao.top:9080/login"
                                           target="_blank"
                                           style="color:#ffffff;text-decoration:none;font-family:'Microsoft YaHei';"
                                           rel="noopener">广商管理系统</a>
                            &nbsp; <span style="color:#6c7479;">|</span>&nbsp;&nbsp;

                            <a href=""
                               target="_blank" style="color:#ffffff;text-decoration:none;font-family:'Microsoft YaHei';"
                               rel="noopener">帮助中心</a>
                        </td>
                    </tr>
                    </tbody>
                </table>

            </td>
        </tr>

        <tr>
            <td>

                <table width="800" border="0" align="left" cellpadding="0" cellspacing="0"
                       style=" border:1px solid #edecec; border-top:none; border-bottom:none; padding:0 20px;font-size:14px;color:#333333;">
                    <tbody>
                    <tr>
                        <td width="760" height="56" border="0" align="left" colspan="2"
                            style=" font-size:16px;vertical-align:bottom;">尊敬的${(params.merchant_mail)!""}：
                        </td>
                    </tr>
                    <tr>
                        <td width="760" height="30" border="0" align="left" colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td width="40" height="32" border="0" align="left" valign="middle"
                            style=" width:40px; text-align:left;vertical-align:middle; line-height:32px; float:left;">
                            <img width="32" height="32" border="0"
                                 src="http://gtms03.alicdn.com/tps/i3/T1mFqoFpheXXa94Hfd-32-32.png"></td>
                        <td width="720" height="32" border="0" align="left" valign="middle"
                            style="font-size:14px; width:720px; text-align:left;vertical-align:middle;line-height:32px;">
                        ${(params.message)!""}。请登录<a
                                href=""
                                target="_blank" rel="noopener">广商管理系统</a>查看及管理。
                        </td>
                    </tr>
                    <tr>
                        <td width="720" height="32" colspan="2" style="padding-left:40px;"></td>
                    </tr>
                    <tr>
                        <td width="720" height="32" colspan="2" style="font-size:14px;padding-left:40px;">商城页面：</td>
                    </tr>
                    <tr>
                        <td width="720" height="32" colspan="2" style="padding-left:40px;"><a
                                href="http://www.niuxinghao.top:9080/apphtml/scapp/index.html?merchant_id=c5c05b29ee4c"
                                target="_blank" rel="noopener">http://www.niuxinghao.top:9080/apphtml/scapp/contactmerchant.html?merchant_id=${(params.merchant_id)!""}</a>
                        </td>
                    </tr>
                    <tr>
                        <td width="720" height="32" colspan="2" style="padding-left:40px;">
                            &nbsp;如果上述链接无效，请将链接复制并粘贴到浏览器中
                        </td>
                    </tr>
                    <tr>
                        <td width="720" height="32" colspan="2" style="padding-left:40px;">&nbsp;</td>
                    </tr>
                    <tr>
                        <td width="720" height="32" colspan="2" style="padding-left:40px;">&nbsp;</td>
                    </tr>

                    <tr>
                        <td width="720" height="14" colspan="2"
                            style="padding-bottom:16px; border-bottom:1px dashed #e5e5e5;font-family:'Microsoft YaHei';">
                            发件人：NIUHAO广商管理系统平台
                        </td>
                    </tr>
                    <tr>
                        <td width="720" height="14" colspan="2"
                            style="padding:8px 0 28px;color:#999999; font-size:12px;font-family:'Microsoft YaHei';">
                            此为系统邮件请勿回复，如有问题请微信联系。
                        </td>
                    </tr>
                    </tbody>
                </table>

            </td>
        </tr>

        <tr>
            <td>
                <table width="800" height="100" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tbody>
                    <tr>
                        <td width="800" height="100" align="center" valign="middle">
                            <img border="0" height="100"
                                 src="http://www.niuxinghao.top:9080//merchant_wximge/20181013/1/merchant_wximge/20181013190102_152.jpg">
                        </td>
                    </tr>
                    </tbody>
                </table>
            </td>
        </tr>

        <tr>
            <td>
                <table align="center" border="0" width="100%" cellpadding="0" cellspacing="0" bgcolor="#fff">
                    <tbody>
                    <tr>
                        <td>
                            <p style="line-height: 22px; font-family: 'Microsoft YaHei'; font-size: 12px; color: #999; text-align: center;">
                                如果您不想再接收信息通知邮件，或者需要设置其他人接收，点此<a
                                    href=""
                                    target="_blank" style="text-decoration:none; color:#00a2ca;"
                                    rel="noopener">设置订阅。</a>
                            </p>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </td>
        </tr>

        </tbody>
    </table>
</div>
<#--<SCRIPT>

    (function ($) {
        var tbody = ""
        $("#tbody").html(tbody);
        /*$.ajax({
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
                    $("#title").html(merchant.merchant_name);
                    bannerimglist();
                    goodstypelist();
                    advertisinglist();
                    footer_ul();
                    //bb();
                } else {
                    $("#container").html("您访问的链接有问题!!!");
                }

            },
            error: function () {

            }
        });*/
    })(jQuery);
</SCRIPT>-->
</body>
</html>