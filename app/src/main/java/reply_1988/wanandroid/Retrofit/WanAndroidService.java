package reply_1988.wanandroid.Retrofit;

import io.reactivex.Observable;

import reply_1988.wanandroid.data.model.ArticlesData;
import reply_1988.wanandroid.data.model.FavoriteData;
import reply_1988.wanandroid.data.model.HotKey;
import reply_1988.wanandroid.data.model.LoginData;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WanAndroidService {


    //获取网站首页上的文章列表 方法：GET参数：页码，拼接在连接中，从0开始
    @GET(Url.MainArticlesList + "{page}/json")
    Observable<ArticlesData> getArticles(@Path("page") int page);

    //获取搜索热词
    @GET(Url.HotKey)
    Observable<HotKey> getHotKey();

    //搜索
    //搜索获得的json数据可以用ArticleData来解析
    //方法：POST
    //参数：
    //	页码：拼接在链接上，从0开始。
    //	k ： 搜索关键词
    @FormUrlEncoded
    @POST(Url.Search + "{page}/json")
    Observable<ArticlesData> getSearchArticles(@Path("page") int page, @Field("k") String SearchContent);

    //登录
    //http://www.wanandroid.com/user/login
    //方法：POST参数：username，password
    @FormUrlEncoded
    @POST(Url.Login)
    Observable<LoginData> getLoginData(@Field("username") String username, @Field("password") String password);

    //注册
    //http://www.wanandroid.com/user/register
    //方法：POST username,password,repassword
    @FormUrlEncoded
    @POST(Url.register)
    Observable<LoginData> getRegisterData(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    //获取收藏的文章
    //http://www.wanandroid.com/lg/collect/list/0/json
    //方法：GET
    //参数： 页码：拼接在链接中，从0开始。
    @GET(Url.favorite + "{page}/json")
    Observable<FavoriteData> getFavoriteArticles(@Path("page") int page);

    //进行收藏文章的操作
    //http://www.wanandroid.com/lg/collect/1165/json
    //方法：POST 参数： 文章id，拼接在链接中。
    @POST(Url.setFavorite + "{id}/json")
    Observable<FavoriteData> setFavorite(@Path("id") int id);

    //进行取消收藏的操作
    //http://www.wanandroid.com/lg/uncollect_originId/2333/json
    //方法：POST
    //参数：id:拼接在链接上
    @POST(Url.cancelFavorite + "{id}/json")
    Observable<FavoriteData> cancelFavorite(@Path("id") int id);

    //进行取消收藏的操作
    //http://www.wanandroid.com/lg/uncollect/2805/json
    //方法：POST 参数：id:拼接在链接上 originId:列表页下发，无则为-1
    @FormUrlEncoded
    @POST(Url.cancelFavoriteFromList + "{id}/json")
    Observable<FavoriteData> cancelFavoriteFromList(@Path("id") int id, @Field("originId") int originId);
}