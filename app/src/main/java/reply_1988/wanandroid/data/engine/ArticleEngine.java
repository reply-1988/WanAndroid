package reply_1988.wanandroid.data.engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.source.ArticleDataSource;
import reply_1988.wanandroid.data.source.remote.ArticlesInternetSource;

public class ArticleEngine implements ArticleDataSource{

    public static ArticleEngine sArticleEngine;

    private ArticleDataSource mArticleDataSource;

    private Map<Integer, ArticleDetailData> articlesCache;

    private ArticleEngine(ArticleDataSource articlesInternetSource){

        this.mArticleDataSource = articlesInternetSource;
    }

    public static ArticleEngine getArticleEngine(ArticleDataSource articlesInternetSource) {

        if (sArticleEngine == null) {
            return new ArticleEngine(articlesInternetSource);
        }
        else {
            return sArticleEngine;
        }
    }

    @Override
    public Observable<List<ArticleDetailData>> getArticles(int page, boolean loadMore) {

        //如果需要加载更多，同时也有缓存旧的数据
        if (loadMore && articlesCache!= null) {
            //获取缓存的数据，将其转换为Observable
            Observable<List<ArticleDetailData>> listBefore = Observable
                    .fromIterable(new ArrayList<>(articlesCache.values()))
                    .toSortedList(new Comparator<ArticleDetailData>() {
                        @Override
                        public int compare(ArticleDetailData o1, ArticleDetailData o2) {
                            if (o1.getPublishTime() > o2.getPublishTime()){
                                return -1;
                            }else {
                                return 1;
                            }
                        }
                    }).toObservable();
            //获取最新的一组数据
            Observable<List<ArticleDetailData>> listAfter = mArticleDataSource.getArticles(page, loadMore);
            return  Observable
                    .concat(listBefore, listAfter)
                    //此处将其转换成集合，是为了利用集合中不能够存在相同元素，从而将可能产生的重复数据剔除
                    //但是也可以使用Observable的其他操作符将相同的剔除
                    .collect(new Callable<List<ArticleDetailData>>() {
                        @Override
                        public List<ArticleDetailData> call() throws Exception {
                            return new ArrayList<>();
                        }
                    }, new BiConsumer<List<ArticleDetailData>, List<ArticleDetailData>>() {
                        @Override
                        public void accept(List<ArticleDetailData> detailDataList, List<ArticleDetailData> detailDataList2) throws Exception {
                            detailDataList.addAll(detailDataList2);
                            addToCache(detailDataList2, false);
                        }
                    }).toObservable();
        }

        return mArticleDataSource.getArticles(page, loadMore)
                .doOnNext(new Consumer<List<ArticleDetailData>>() {
                    @Override
                    public void accept(List<ArticleDetailData> detailDataList) throws Exception {
                        addToCache(detailDataList, true);
                    }
                });


    }

    @Override
    public Observable<List<ArticleDetailData>> getQueryArticles(String query, int page) {
        return null;
    }

    //将数据放入缓存
    private void addToCache(List<ArticleDetailData> detailDataList, boolean clearCache) {

        if (articlesCache == null) {
            articlesCache = new LinkedHashMap<>();
        }
        if (clearCache) {
            articlesCache.clear();
        }

        for (ArticleDetailData detailData: detailDataList) {
            articlesCache.put(detailData.getId(), detailData);
        }

    }
}
