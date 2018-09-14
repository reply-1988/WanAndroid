package reply_1988.wanandroid.articleDetail;

import reply_1988.wanandroid.BasePresenter;
import reply_1988.wanandroid.BaseView;
import reply_1988.wanandroid.data.model.ArticleDetailData;

public interface ArticleDetailContract {

    interface Presenter extends BasePresenter{

        void setCollect(int id);

        void cancelCollect(int id);

        void setReadLater(ArticleDetailData detailData);

        void cancelReadLater(int id);
    }

    interface View extends BaseView<Presenter>{

        void setPresenter(Presenter presenter);

        void setCollect();

        void cancelCollect();
    }

}
