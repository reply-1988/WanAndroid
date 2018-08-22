package reply_1988.wanandroid.data.engine;

import java.util.List;

import io.reactivex.Observable;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.source.ArticleDataSource;
import reply_1988.wanandroid.data.source.remote.ArticlesInternetSource;

public class ArticleEngine implements ArticleDataSource{

    public static ArticleEngine sArticleEngine;

    private ArticleDataSource mArticleDataSource;

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
    public Observable<List<ArticleDetailData>> getArticles(int page) {
        return mArticleDataSource.getArticles(page);
    }

    @Override
    public Observable<List<ArticleDetailData>> getQueryArticles(String query, int page) {
        return null;
    }
}
