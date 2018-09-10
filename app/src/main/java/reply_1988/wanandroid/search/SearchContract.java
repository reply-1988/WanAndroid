package reply_1988.wanandroid.search;

import java.util.List;

import reply_1988.wanandroid.BasePresenter;
import reply_1988.wanandroid.BaseView;
import reply_1988.wanandroid.data.model.SearchDetailData;

public interface SearchContract {

    interface Presenter extends BasePresenter{

        void getQueryData(int page, String searchContent, boolean b);

        void setCollect(int id);

        void cancelCollect(int id);


    }

    interface View extends BaseView<Presenter>{


        void showArticles(List<SearchDetailData> detailDataList);

        void setFavoriteButton();

        void setPresenter(SearchContract.Presenter presenter);
    }
}
