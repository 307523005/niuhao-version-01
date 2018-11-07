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
function advertisinglist() {
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
        },
        success: function (r) {
            if (r.code === 0) {
                var advertising = r.msg;
                for (var i = 0; i < advertising.length; i++) {
                    advertisinglist += " <li>\n" +
                        "                <a href=\"advertising.html?merchant_id="+merchant_id+"&advertising_id="+advertising[i].advertisingId+"\">\n" +
                        "                    <div class=\"goods-allocation-txt\">\n" +
                        "                        <h2><span style=\"float:left;width:260px;overflow: hidden;\n" +
                        "            text-overflow: ellipsis;\n" +
                        "            -o-text-overflow: ellipsis;\n" +
                        "            white-space:nowrap;\"></span>"+advertising[i].advertisingTitle+"</h2>\n" +
                        "                        <p>"+advertising[i].advertisingUpdatetime+"</p>\n" +
                        "                    </div>\n" +
                        "                </a>\n" +
                        "            </li>";
                }
                $("#advertisinglist").html(advertisinglist);
                $("#title").html(type);
                $("#titl_hi").html(type);
            }

        },
        error: function () {

        }
    });
}
/*footer_ul*/
function footer_ul() {
    var footer_ul = "";
    footer_ul += "      <li class=\"on\"><a href=\"index.html?merchant_id=" + merchant_id + "\" class=\"home\"><i></i><span class=\"full-block\">首页</span></a></li>\n" +
        "        <li><a href=\"\" class=\"foot-worker\"><i></i><span class=\"full-block\">加盟</span></a></li>\n" +
        "        <li><a href=\"\" class=\"foot-order\"><i></i><span class=\"full-block\">订单</span></a></li>\n" +
        "        <li><a href=\"\" class=\"my\"><i></i><span class=\"full-block\">我的</span></a></li>";
    $("#footer_ul").html(footer_ul);
}


/*上划加载*/
$(function(){
    //创建MeScroll对象
    var mescroll = initMeScroll("mescroll", {
        down:{
            auto:false,//是否在初始化完毕之后自动执行下拉回调callback; 默认true
            callback: downCallback, //下拉刷新的回调
        },
        up: {
            auto:true,//初始化完毕,是否自动触发上拉加载的回调
            isBoth: true, //上拉加载时,如果滑动到列表顶部是否可以同时触发下拉刷新;默认false,两者不可同时触发; 这里为了演示改为true,不必等列表加载完毕才可下拉;
            callback: upCallback, //上拉加载的回调
        }
    });

    /*下拉刷新的回调 */
    function downCallback(){
        //加载轮播数据..
        //...
        //加载列表数据
        getListDataFromNet(0, 1, function(data){
            //联网成功的回调,隐藏下拉刷新的状态
            mescroll.endSuccess();
            //设置列表数据
            setListData(data, false);
            //显示提示
            $("#downloadTip").css("top","44px");
            setTimeout(function () {
                $("#downloadTip").css("top","20px");
            },2000);
        }, function(){
            //联网失败的回调,隐藏下拉刷新的状态
            mescroll.endErr();
        });
    }

    /*上拉加载的回调 page = {num:1, size:10}; num:当前页 从1开始, size:每页数据条数 */
    function upCallback(page){
        //联网加载数据
        console.log("page.num="+page.num);
        getListDataFromNet(page.num, page.size, function(data){
            //联网成功的回调,隐藏上拉加载的状态
            mescroll.endSuccess(data.length);//传参:数据的总数; mescroll会自动判断列表如果无任何数据,则提示空;列表无下一页数据,则提示无更多数据;
            //设置列表数据
            setListData(data, true);
        }, function(){
            //联网失败的回调,隐藏上拉加载的状态
            mescroll.endErr();
        });
    }

    /*设置列表数据*/
    function setListData(data, isAppend) {
        var listDom=document.getElementById("newsList");
        for (var i = 0; i < data.length; i++) {
            var newObj=data[i];

            var str='<p>'+newObj.title+'</p>';
            str+='<p class="new-content">'+newObj.content+'</p>';
            var liDom=document.createElement("li");
            liDom.innerHTML=str;

            if (isAppend) {
                listDom.appendChild(liDom);//加在列表的后面,上拉加载
            } else{
                listDom.insertBefore(liDom, listDom.firstChild);//加在列表的前面,下拉刷新
            }
        }
    }

    /*联网加载列表数据*/
    var downIndex=0;
    function getListDataFromNet(pageNum,pageSize,successCallback,errorCallback) {
        //延时一秒,模拟联网
        setTimeout(function () {
            try{
                var newArr=[];
                if(pageNum==0){
                    //此处模拟下拉刷新返回的数据
                    downIndex++;
                    var newObj={title:"【新增话题"+downIndex+"】 新增话题", content:"新增话题的内容"};
                    newArr.push(newObj);
                }else{
                    //此处模拟上拉加载返回的数据
                    for (var i = 0; i < pageSize; i++) {
                        var upIndex=(pageNum-1)*pageSize+i+1;
                        var newObj={title:"【话题"+upIndex+"】 标题标题标题标题标题标题", content:"内容内容内容内容内容内容内容内容内容内容"};
                        newArr.push(newObj);
                    }
                }
                //联网成功的回调
                successCallback&&successCallback(newArr);
            }catch(e){
                //联网失败的回调
                errorCallback&&errorCallback();
            }
        },2000)
    }

    //禁止PC浏览器拖拽图片,避免与下拉刷新冲突;如果仅在移动端使用,可删除此代码
    document.ondragstart=function() {return false;}
});