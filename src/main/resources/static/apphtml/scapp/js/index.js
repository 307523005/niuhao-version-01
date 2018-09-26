var sc_List = "<li><a href=\"#\"><img src=\"images/ind-banner.jpg\" alt=\"\"/><\/a><\/li>";
sc_List += sc_List;
sc_List += sc_List;
$("#sc_List").html(sc_List);
//"<li class=\"newLi\"><span>" + _userName + "<\/span><span>" + userEamil + "<\/span><span>" + userPhone + "<\/span><span><input type=\"button\" value=\"删除\" onclick=\"this.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode)\" \/><\/span><\/li>";
TouchSlide({
    slideCell:"#index-banner",
    titCell:".banner-tit li",//开启自动分页 autoPage:true ，此时设置 titCell 为导航元素包裹层
    mainCell:".banner-pics-list",
    effect:"left",
    autoPlay:true,//自动播放
    //autoPage:true, //自动分页
})