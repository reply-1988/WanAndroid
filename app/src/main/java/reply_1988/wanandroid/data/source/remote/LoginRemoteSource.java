package reply_1988.wanandroid.data.source.remote;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import reply_1988.wanandroid.Retrofit.RetrofitClient;
import reply_1988.wanandroid.Retrofit.WanAndroidService;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.model.LoginData;
import reply_1988.wanandroid.data.model.LoginDetailData;
import reply_1988.wanandroid.data.source.LoginDataSource;

import static reply_1988.wanandroid.MyApplication.getContext;

/**
 *
 */
public class LoginRemoteSource implements LoginDataSource {

    private static LoginRemoteSource sLoginRemoteSource;

    private LoginRemoteSource() {

    }

    public static LoginRemoteSource getInstance() {

        if (sLoginRemoteSource == null) {
            sLoginRemoteSource = new LoginRemoteSource();
        }

        return sLoginRemoteSource;
    }

    @Override
    public Observable<LoginData> getRemoteLoginData(String username, String password, String type) {
        Observable<LoginData> loginDataObservable = null;
        switch (type) {
            case "login":
                loginDataObservable = RetrofitClient.getInstance()
                        .create(WanAndroidService.class)
                        .getLoginData(username, password);
                break;
            case "register":
                loginDataObservable = RetrofitClient.getInstance()
                        .create(WanAndroidService.class)
                        .getRegisterData(username, password, password);
                break;
            default:
                break;
        }

        assert loginDataObservable != null;
        return loginDataObservable
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void clearCache(Context context) {

    }


}
