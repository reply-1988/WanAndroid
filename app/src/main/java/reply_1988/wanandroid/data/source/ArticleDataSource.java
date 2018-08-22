package reply_1988.wanandroid.data.source;

import java.util.List;

import io.reactivex.Observable;
import reply_1988.wanandroid.data.model.ArticleDetailData;

public interface ArticleDataSource {

    Observable<List<ArticleDetailData>> getArticles(int page);

    Observable<List<ArticleDetailData>> getQueryArticles(String query, int page);
}
