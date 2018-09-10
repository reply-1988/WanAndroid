package reply_1988.wanandroid.data.source;

import java.util.List;

import io.reactivex.Observable;
import reply_1988.wanandroid.data.model.SearchData;
import reply_1988.wanandroid.data.model.SearchDetailData;

public interface SearchDataSource {

    Observable<List<SearchDetailData>> getQueryData(int page, String searchContent, boolean loadMore);
}
