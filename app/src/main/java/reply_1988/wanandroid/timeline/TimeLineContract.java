package reply_1988.wanandroid.timeline;

import android.view.View;

import java.util.List;

import reply_1988.wanandroid.BasePresenter;
import reply_1988.wanandroid.BaseView;
import reply_1988.wanandroid.data.model.ArticleDetailData;

//契约接口主要用来存放相关联的Presenter和View的接口
public interface TimeLineContract {

    interface Presenter extends BasePresenter {

        void getArticles(int page, boolean loadMore);
    }

    interface View extends BaseView<Presenter> {

        void showArticles(List<ArticleDetailData> articleDetailDataList);
    }
}
