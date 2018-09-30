package reply_1988.wanandroid.data.source.remote;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import reply_1988.wanandroid.Retrofit.RetrofitClient;
import reply_1988.wanandroid.Retrofit.WanAndroidService;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.model.ArticlesData;
import reply_1988.wanandroid.data.model.ArticlesDetailData;
import reply_1988.wanandroid.data.model.SearchData;
import reply_1988.wanandroid.data.model.SearchDetailData;
import reply_1988.wanandroid.data.source.SearchDataSource;

public class SearchRemoteSource implements SearchDataSource {

    @Override
    public Observable<List<ArticleDetailData>> getQueryData(int page, final String searchContent, boolean loadMore) {

        return RetrofitClient.getInstance()
                .create(WanAndroidService.class)
                .getSearchArticles(page, searchContent)
                .map(new Function<ArticlesData, List<ArticleDetailData>>() {
                    @Override
                    public List<ArticleDetailData> apply(ArticlesData searchData) throws Exception {
                        return searchData.getData().getDatas();
                    }
                });
    }

    @Override
    public Observable<List<ArticleDetailData>> getKSDetailData(int page, int cid, boolean loadMore) {

        return RetrofitClient.getInstance()
                .create(WanAndroidService.class)
                .getKSDetailData(page, cid)
                .map(new Function<ArticlesData, List<ArticleDetailData>>() {
                    @Override
                    public List<ArticleDetailData> apply(ArticlesData searchData) throws Exception {
                        return searchData.getData().getDatas();
                    }
                });
    }
}
