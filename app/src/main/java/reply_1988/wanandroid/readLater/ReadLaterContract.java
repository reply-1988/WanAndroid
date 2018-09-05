package reply_1988.wanandroid.readLater;

import java.util.List;

import reply_1988.wanandroid.BasePresenter;
import reply_1988.wanandroid.BaseView;
import reply_1988.wanandroid.data.model.ArticleDetailData;

public interface ReadLaterContract {

    interface View extends BaseView<Presenter>{
        void showArticles(List<ArticleDetailData> articleDetailDataList);
    }

    interface Presenter extends BasePresenter{
        void getArticles(int page, boolean loadMore);
    }
}
