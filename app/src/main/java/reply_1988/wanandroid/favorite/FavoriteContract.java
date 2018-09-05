package reply_1988.wanandroid.favorite;

import java.util.List;

import reply_1988.wanandroid.BasePresenter;
import reply_1988.wanandroid.BaseView;
import reply_1988.wanandroid.data.model.FavoriteDetailData;

public interface FavoriteContract {

    interface Presenter extends BasePresenter{

        void getArticles(int page, boolean loadMore);

        void setFavorite(int originId);

        void cancelFavorite(int id, int originId);
    }

    interface View extends BaseView<Presenter> {

        void showArticles(List<FavoriteDetailData> articleDetailDataList);
    }
}
