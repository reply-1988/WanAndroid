package reply_1988.wanandroid.data.source.remote;

import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.realm.Realm;
import reply_1988.wanandroid.Retrofit.RetrofitClient;
import reply_1988.wanandroid.Retrofit.WanAndroidService;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.model.ArticlesData;
import reply_1988.wanandroid.data.source.ArticleDataSource;

public class ArticlesInternetSource implements ArticleDataSource {

    private static ArticlesInternetSource sArticlesInternetSource;

    public static ArticlesInternetSource getArticlesInternetSource() {
        if (sArticlesInternetSource == null) {
            sArticlesInternetSource = new ArticlesInternetSource();
        }
        return sArticlesInternetSource;
    }

    private ArticlesInternetSource() {

    }

    @Override
    public Observable<List<ArticleDetailData>> getArticles(int page, boolean loadMore) {
        return RetrofitClient.getInstance()
                .create(WanAndroidService.class)
                .getArticles(page)
                //获取成功返回的数据，其ErrorCode值为0
                .filter(new Predicate<ArticlesData>() {
                    @Override
                    public boolean test(ArticlesData articlesData) throws Exception {
                        Log.d("测试", "" + articlesData.getData().getDatas().get(0).getAuthor());
                        return articlesData.getErrorCode() == 0;
                    }
                })
                //将Articles中的Article作为List取出来
                .concatMap(new Function<ArticlesData, ObservableSource<List<ArticleDetailData>>>() {
                    @Override
                    public ObservableSource<List<ArticleDetailData>> apply(ArticlesData articlesData) throws Exception {
                        return Observable.fromIterable(articlesData.getData().getDatas()).toList().toObservable();
                    }
                })
                .doOnNext(new Consumer<List<ArticleDetailData>>() {
                    @Override
                    public void accept(List<ArticleDetailData> articleDetailData) throws Exception {
                        for (ArticleDetailData item : articleDetailData) {
                            saveToRealm(item);
                        }
                    }
                });
    }

    @Override
    public Observable<List<ArticleDetailData>> getQueryArticles(String query, int page) {

        //因为在WanAndroidService中返回的Articles数据都是同一种类型，所以代码与第一个方法一致。

        return RetrofitClient.getInstance()
                .create(WanAndroidService.class)
                .getSearchArticles(page, query)
                //获取成功返回的数据，其ErrorCode值为0
                .filter(new Predicate<ArticlesData>() {
                    @Override
                    public boolean test(ArticlesData articlesData) throws Exception {
                        return articlesData.getErrorCode() == 0;
                    }
                })
                //将Articles中的Article作为List取出来
                .concatMap(new Function<ArticlesData, ObservableSource<List<ArticleDetailData>>>() {
                    @Override
                    public ObservableSource<List<ArticleDetailData>> apply(ArticlesData articlesData) throws Exception {
                        return Observable.fromIterable(articlesData.getData().getDatas()).toList().toObservable();
                    }
                });
    }

    private void saveToRealm(ArticleDetailData detailData) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(detailData);
        realm.commitTransaction();
        realm.close();
    }
}
