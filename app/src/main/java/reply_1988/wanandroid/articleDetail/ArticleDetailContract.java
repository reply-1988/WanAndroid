package reply_1988.wanandroid.articleDetail;

import reply_1988.wanandroid.BasePresenter;
import reply_1988.wanandroid.BaseView;

public interface ArticleDetailContract {

    interface Presenter extends BasePresenter{

        void setCollect(int id);

        void cancelCollect(int id);

        void setReadLater(int id);

        void cancelReadLAter(int id);
    }

    interface View extends BaseView<Presenter>{}

}
