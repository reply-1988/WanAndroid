package reply_1988.wanandroid.data.engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.source.SearchDataSource;
import reply_1988.wanandroid.data.source.remote.SearchRemoteSource;

public class SearchEngine implements SearchDataSource {

    private SearchRemoteSource mQueryRemoteSource;

    private Map<Integer, ArticleDetailData> searchDataCache;

    public SearchEngine() {

        this.mQueryRemoteSource = new SearchRemoteSource();
        searchDataCache = new LinkedHashMap<>();
    }

    @Override
    public Observable<List<ArticleDetailData>> getQueryData(int page, String searchContent, boolean loadMore) {

        if (page != 0 && loadMore) {
            Observable<List<ArticleDetailData>> listBefore = Observable
                    .fromIterable(new ArrayList<>(searchDataCache.values()))
                    .toSortedList(new Comparator<ArticleDetailData>() {
                        @Override
                        public int compare(ArticleDetailData o1, ArticleDetailData o2) {
                            if (o1.getPublishTime() > o2.getPublishTime()) {

                                return -1;
                            } else {
                                return 1;
                            }
                        }
                    }).toObservable();

            Observable<List<ArticleDetailData>> listAfter = mQueryRemoteSource.getQueryData(page, searchContent, loadMore);

            Observable<List<ArticleDetailData>> list = Observable
                    .concat(listBefore, listAfter)
                    .collect(new Callable<List<ArticleDetailData>>() {
                        @Override
                        public List<ArticleDetailData> call() throws Exception {
                            return new ArrayList<>();
                        }
                    }, new BiConsumer<List<ArticleDetailData>, List<ArticleDetailData>>() {
                        @Override
                        public void accept(List<ArticleDetailData> searchDetailData, List<ArticleDetailData> searchDetailData2) throws Exception {
                            searchDetailData.addAll(searchDetailData2);
                            addToCache(searchDetailData, true);
                        }
                    }).toObservable();
            return list;

        }
        return mQueryRemoteSource.getQueryData(page, searchContent, false)
                .doOnNext(new Consumer<List<ArticleDetailData>>() {
                    @Override
                    public void accept(List<ArticleDetailData> searchDetailData) throws Exception {
                        addToCache(searchDetailData, false);
                    }
                });
    }

    @Override
    public Observable<List<ArticleDetailData>> getKSDetailData(int page, int cid, boolean loadMore) {
        if (page != 0 && loadMore) {
            Observable<List<ArticleDetailData>> listBefore = Observable
                    .fromIterable(new ArrayList<>(searchDataCache.values()))
                    .toSortedList(new Comparator<ArticleDetailData>() {
                        @Override
                        public int compare(ArticleDetailData o1, ArticleDetailData o2) {
                            if (o1.getPublishTime() > o2.getPublishTime()) {

                                return -1;
                            } else {
                                return 1;
                            }
                        }
                    }).toObservable();

            Observable<List<ArticleDetailData>> listAfter = mQueryRemoteSource.getKSDetailData(page, cid, loadMore);

            Observable<List<ArticleDetailData>> list = Observable
                    .concat(listBefore, listAfter)
                    .collect(new Callable<List<ArticleDetailData>>() {
                        @Override
                        public List<ArticleDetailData> call() throws Exception {
                            return new ArrayList<>();
                        }
                    }, new BiConsumer<List<ArticleDetailData>, List<ArticleDetailData>>() {
                        @Override
                        public void accept(List<ArticleDetailData> searchDetailData, List<ArticleDetailData> searchDetailData2) throws Exception {
                            searchDetailData.addAll(searchDetailData2);
                            addToCache(searchDetailData, true);
                        }
                    }).toObservable();
            return list;

        }
        return mQueryRemoteSource.getKSDetailData(page, cid, false)
                .doOnNext(new Consumer<List<ArticleDetailData>>() {
                    @Override
                    public void accept(List<ArticleDetailData> searchDetailData) throws Exception {
                        addToCache(searchDetailData, false);
                    }
                });
    }

    private void addToCache(List<ArticleDetailData> detailsData, boolean clearCache) {

        if (searchDataCache == null) {
            searchDataCache = new LinkedHashMap<>();
        }

        if (clearCache){
            searchDataCache.clear();
        }

        for (ArticleDetailData detailData :
                detailsData) {
            searchDataCache.put(detailData.getId(), detailData);
        }
    }
}
