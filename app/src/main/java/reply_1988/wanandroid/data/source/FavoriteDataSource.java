package reply_1988.wanandroid.data.source;


import io.reactivex.Observable;
import reply_1988.wanandroid.data.model.FavoriteData;

public interface FavoriteDataSource {

    Observable<FavoriteData> setFavorite(int id);

    void setFavoriteFromList();

    Observable<FavoriteData> cancelFavorite(int id);
}
