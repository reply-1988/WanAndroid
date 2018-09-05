package reply_1988.wanandroid.data.engine;

import io.reactivex.Observable;
import reply_1988.wanandroid.data.model.FavoriteData;
import reply_1988.wanandroid.data.source.FavoriteDataSource;
import reply_1988.wanandroid.data.source.remote.FavoriteRemoteSource;

public class FavoriteEngine implements FavoriteDataSource {

    private FavoriteRemoteSource mFavoriteRemoteSource;

    public FavoriteEngine() {
        mFavoriteRemoteSource = new FavoriteRemoteSource();
    }

    @Override
    public Observable<FavoriteData> setFavorite(int id) {

        return mFavoriteRemoteSource.setFavorite(id);
    }

    @Override
    public void setFavoriteFromList() {

    }

    @Override
    public Observable<FavoriteData> cancelFavorite(int id) {

        return mFavoriteRemoteSource.cancelFavorite(id);
    }
}
