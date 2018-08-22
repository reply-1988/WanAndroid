package reply_1988.wanandroid.Retrofit;

import io.reactivex.Observable;

import reply_1988.wanandroid.data.model.ArticlesData;
import reply_1988.wanandroid.data.model.HotKey;
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

}
