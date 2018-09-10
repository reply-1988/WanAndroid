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
import reply_1988.wanandroid.data.model.SearchDetailData;
import reply_1988.wanandroid.data.source.SearchDataSource;
import reply_1988.wanandroid.data.source.remote.SearchRemoteSource;

public class SearchEngine implements SearchDataSource {

    private SearchRemoteSource mQueryRemoteSource;

    private Map<Integer, SearchDetailData> searchDataCache;

    public SearchEngine() {

        this.mQueryRemoteSource = new SearchRemoteSource();
        searchDataCache = new LinkedHashMap<>();
    }

    @Override
    public Observable<List<SearchDetailData>> getQueryData(int page, String searchContent, boolean loadMore) {

        if (page != 0 && loadMore) {
            Observable<List<SearchDetailData>> listBefore = Observable
                    .fromIterable(new ArrayList<>(searchDataCache.values()))
                    .toSortedList(new Comparator<SearchDetailData>() {
                        @Override
                        public int compare(SearchDetailData o1, SearchDetailData o2) {
                            if (o1.getPublishTime() > o2.getPublishTime()) {

                                return -1;
                            } else {
                                return 1;
                            }
                        }
                    }).toObservable();

            Observable<List<SearchDetailData>> listAfter = mQueryRemoteSource.getQueryData(page, searchContent, loadMore);

            Observable<List<SearchDetailData>> list = Observable
                    .concat(listBefore, listAfter)
                    .collect(new Callable<List<SearchDetailData>>() {
                        @Override
                        public List<SearchDetailData> call() throws Exception {
                            return new ArrayList<>();
                        }
                    }, new BiConsumer<List<SearchDetailData>, List<SearchDetailData>>() {
                        @Override
                        public void accept(List<SearchDetailData> searchDetailData, List<SearchDetailData> searchDetailData2) throws Exception {
                            searchDetailData.addAll(searchDetailData2);
                            addToCache(searchDetailData, true);
                        }
                    }).toObservable();
            return list;

        }
        return mQueryRemoteSource.getQueryData(page, searchContent, false)
                .doOnNext(new Consumer<List<SearchDetailData>>() {
                    @Override
                    public void accept(List<SearchDetailData> searchDetailData) throws Exception {
                        addToCache(searchDetailData, false);
                    }
                });
    }

    private void addToCache(List<SearchDetailData> detailsData, boolean clearCache) {

        if (searchDataCache == null) {
            searchDataCache = new LinkedHashMap<>();
        }

        if (clearCache){
            searchDataCache.clear();
        }

        for (SearchDetailData detailData :
                detailsData) {
            searchDataCache.put(detailData.getId(), detailData);
        }
    }
}
