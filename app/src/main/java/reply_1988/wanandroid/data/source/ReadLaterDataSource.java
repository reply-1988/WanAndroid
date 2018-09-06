package reply_1988.wanandroid.data.source;

import java.util.List;

import io.reactivex.Observable;
import reply_1988.wanandroid.data.model.ArticleDetailData;

public interface ReadLaterDataSource {

    Observable<List<ArticleDetailData>> getReadLaterList();

    Observable setReadLater(ArticleDetailData detailData);

    Observable cancelReadLater(int id);

}
