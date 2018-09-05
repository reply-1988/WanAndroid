package reply_1988.wanandroid.data.source.remote;

import io.reactivex.Observable;
import reply_1988.wanandroid.Retrofit.RetrofitClient;
import reply_1988.wanandroid.Retrofit.WanAndroidService;
import reply_1988.wanandroid.data.model.FavoriteData;
import reply_1988.wanandroid.data.source.FavoriteDataSource;

public class FavoriteRemoteSource implements FavoriteDataSource{

    @Override
    public Observable<FavoriteData> setFavorite(int id) {
        return RetrofitClient.getInstance()
        .create(WanAndroidService.class)
        .setFavorite(id);
    }

    @Override
    public void setFavoriteFromList() {

    }

    @Override
    public Observable<FavoriteData> cancelFavorite(int id) {
        return RetrofitClient.getInstance()
                .create(WanAndroidService.class)
                .cancelFavorite(id);
    }
}
