package reply_1988.wanandroid.search;

import java.util.List;

import reply_1988.wanandroid.BasePresenter;
import reply_1988.wanandroid.BaseView;
import reply_1988.wanandroid.CommonAdapter;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.model.HotKeyDetailData;
import reply_1988.wanandroid.data.model.SearchDetailData;

public interface SearchContract {

    interface Presenter extends BasePresenter{

        void getQueryData(int page, String searchContent, boolean loadMore);

        void setCollect(int id);

        void cancelCollect(int id);

        void getHotKey();

        void getKSDetailData(int page, int cid, boolean loadMore);

        void setReadLater(ArticleDetailData detailData);

        void cancelReadLater(int id);
    }

    interface View extends BaseView<Presenter>{


        void showArticles(List<ArticleDetailData> detailDataList);

        void setFavoriteButton();

        void getHotKey(List<HotKeyDetailData> detailDataList);

        void showTabLayout(boolean a);

        void showNoData(boolean a);

        void showRecycleView();

        void setSearchContent(String searchContent);

        void setClickListener(CommonAdapter searchAdapter, List<ArticleDetailData> detailDataList);

        void checkParameter(int page, boolean loadMore);
    }
}
