package cc.mrbird.common.util;

public class FebsConstant {

    private FebsConstant() {

    }

    static final String XLSX_SUFFIX = ".xlsx";

    static final String XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    //魅族天气获取
    public static final String MEIZU_WEATHER_URL = "http://aider.meizu.com/app/weather/listWeather";
    //每日一文
    public static final String MRYW_TODAY_URL = "https://interface.meiriyiwen.com/article/today";
    //每日一文，按日期
    public static final String MRYW_DAY_URL = "https://interface.meiriyiwen.com/article/day";
    //获取上映电影信息
    public static final String TIME_MOVIE_HOT_URL = "https://api-m.mtime.cn/Showtime/LocationMovies.api";
    //获取影片详情
    public static final String TIME_MOVIE_DETAIL_URL = "https://ticket-api-m.mtime.cn/movie/detail.api";
    //获取即将上映影片信息
    public static final String TIME_MOVIE_COMING_URL = "https://api-m.mtime.cn/Movie/MovieComingNew.api";
    //获取影片评论
    public static final String TIME_MOVIE_COMMENTS_URL = "https://ticket-api-m.mtime.cn/movie/hotComment.api";
    //获取idList
    public static final String ONE_ID_LIST_URL = "http://v3.wufazhuce.com:8000/api/onelist/idlist/";
    //获取oneList
    public static final String ONE_LIST_URL = "http://v3.wufazhuce.com:8000/api/onelist/";
    //获取语文信息
    public static final String ONE_ESSAY_URL = "http://v3.wufazhuce.com:8000/api/essay/";
    //获取语文评论
    public static final String ONE_ESSAY_COMMENT_URL = "http://v3.wufazhuce.com:8000/api/comment/praiseandtime/essay/";
    //新闻头条
    public static final String HEADLINE_NEWS_URL = "http://v.juhe.cn/toutiao/index";
/*http://v.juhe.cn/toutiao/index?type=top&key=APPKEY*//*	类型,,top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)*/
}
