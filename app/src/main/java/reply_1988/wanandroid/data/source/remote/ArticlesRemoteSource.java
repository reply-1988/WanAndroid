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
import reply_1988.wanandroid.data.model.FavoriteData;
import reply_1988.wanandroid.data.model.FavoriteDetailData;
import reply_1988.wanandroid.data.source.ArticleDataSource;

public class ArticlesRemoteSource implements ArticleDataSource {

    private static ArticlesRemoteSource sArticlesRemoteSource;

    public static ArticlesRemoteSource getArticlesRemoteSource() {
        if (sArticlesRemoteSource == null) {
            sArticlesRemoteSource = new ArticlesRemoteSource();
        }
        return sArticlesRemoteSource;
    }

    private ArticlesRemoteSource() {

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
    public Observable<List<FavoriteDetailData>> getFavoriteArticles(boolean refresh, int page) {

        return RetrofitClient.getInstance()
                .create(WanAndroidService.class)
                .getFavoriteArticles(page)
                //获取成功返回的数据，其ErrorCode值为0
                .filter(new Predicate<FavoriteData>() {
                    @Override
                    public boolean test(FavoriteData articlesData) throws Exception {
                        return articlesData.getErrorCode() == 0;
                    }
                })
                //将Articles中的Article作为List取出来
                .concatMap(new Function<FavoriteData, ObservableSource<List<FavoriteDetailData>>>() {
                    @Override
                    public ObservableSource<List<FavoriteDetailData>> apply(FavoriteData articlesData) throws Exception {

                        return Observable.fromIterable(articlesData.getData().getDatas()).toList().toObservable();
                    }
                });
    }

    @Override
    public Observable<List<ArticleDetailData>> getReadLaterArticles() {
        return null;
    }


    private void saveToRealm(ArticleDetailData detailData) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(detailData);
        realm.commitTransaction();
        realm.close();
    }
}
