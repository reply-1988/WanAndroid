package reply_1988.wanandroid.data.source;

import java.util.List;

import io.reactivex.Observable;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.model.FavoriteDetailData;

public interface ArticleDataSource {

    Observable<List<ArticleDetailData>> getArticles(int page, boolean loadMore);

    Observable<List<FavoriteDetailData>> getFavoriteArticles(boolean refresh, int page);

    Observable<List<ArticleDetailData>> getReadLaterArticles();

}
