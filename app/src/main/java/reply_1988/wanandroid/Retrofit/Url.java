package reply_1988.wanandroid.Retrofit;

public class Url {

    /**
     * Base Url
     */
    static final String baseUrl = "http://www.wanandroid.com/";

    /**
     * 首页上面文章列表
     */
    static final String MainArticlesList = "article/list/";

    /**
     * 热词
     */
    static final String HotKey = "hotkey/json";

    /**
     * 搜索
     */
    static final String Search = "article/query/";

    /**
     * 登陆
     */
    static final String Login = "user/login";

    /**
     * 注册
     */
    static final String register = "user/register";

    /**
     * 注销
     */
    public static final String Logout = "user/logout/json";

    /**
     * 获取收藏列表
     */
    static final String favorite = "lg/collect/list/";

    /**
     * 进行收藏操作
     */
    public static final String setFavorite = "lg/collect/";

    /**
     * 取消收藏
     */
    public static final String cancelFavorite = "lg/uncollect_originId/";

    /**
     * 从收藏列表取消收藏
     */
    static final String cancelFavoriteFromList = "lg/uncollect/";

    /**
     * 获取体系数据
     */
    static final String KS_DATA = "tree/json";

    /**
     * 获取某一体系类别下的文章列表数据
     */
    static final String KS_DETAIL_DATA = "article/list/";

    /**
     * 获取某一项目分类下的文章列表数据
     */
    static final String CATEGORY_DATA = "project/list";
}
