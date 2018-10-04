package reply_1988.wanandroid.data.engine;

import android.content.Context;

import io.reactivex.Observable;
import reply_1988.wanandroid.data.model.LoginData;
import reply_1988.wanandroid.data.model.LoginDetailData;
import reply_1988.wanandroid.data.source.LoginDataSource;
import reply_1988.wanandroid.data.source.local.LoginLocalSource;
import reply_1988.wanandroid.data.source.remote.LoginRemoteSource;

public class LoginEngine implements LoginDataSource{

    private LoginRemoteSource mLoginRemoteSource;
    private LoginLocalSource mLoginLocalSource;

    public LoginEngine(LoginRemoteSource loginRemoteSource, LoginLocalSource loginLocalSource) {

        mLoginLocalSource = loginLocalSource;
        mLoginRemoteSource = loginRemoteSource;
    }

    @Override
    public Observable<LoginData> getRemoteLoginData(String username, String password, String type) {
        return mLoginRemoteSource.getRemoteLoginData(username, password, type);
    }

    @Override
    public void clearCache(Context context) {
        mLoginLocalSource.clearCache(context);
    }
}
