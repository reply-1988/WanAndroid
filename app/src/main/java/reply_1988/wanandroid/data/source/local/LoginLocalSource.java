package reply_1988.wanandroid.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Objects;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import reply_1988.wanandroid.Retrofit.AddCookiesInterceptor;
import reply_1988.wanandroid.data.model.LoginData;
import reply_1988.wanandroid.data.model.LoginDetailData;
import reply_1988.wanandroid.data.source.LoginDataSource;

public class LoginLocalSource implements LoginDataSource {

    public static LoginLocalSource getInstance() {

        return new LoginLocalSource();
    }

    @Override
    public Observable<LoginData> getRemoteLoginData(String username, String password, String type) {
        return null;
    }

    /**
     * 清除SharedPreference中存储的用户信息
     * @param context 用于获取SharedPreference的上下文
     */
    @Override
    public void clearCache(Context context) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
    }
}
