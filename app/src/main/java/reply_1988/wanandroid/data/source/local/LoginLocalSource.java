package reply_1988.wanandroid.data.source.local;

import java.util.Objects;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import reply_1988.wanandroid.data.model.LoginData;
import reply_1988.wanandroid.data.model.LoginDetailData;
import reply_1988.wanandroid.data.source.LoginDataSource;

public class LoginLocalSource implements LoginDataSource {

    public static LoginLocalSource getInstance() {

        return new LoginLocalSource();
    }

    //从remote获取设置了一个单独的类进行处理
    @Override
    public Observable<LoginData> getRemoteLoginData(String username, String password, String type) {
        return null;
    }

    @Override
    public Observable<LoginDetailData> getLocalLoginData(int id) {

        Realm realm = Realm.getInstance(new RealmConfiguration.Builder()
                .name("login_message")
                .deleteRealmIfMigrationNeeded()
                .build());
        LoginDetailData loginDetailData = realm.copyFromRealm(
                Objects.requireNonNull(realm.where(LoginDetailData.class)
                        .equalTo("id", id)
                        .findFirst())
        );
        return Observable.just(loginDetailData);
    }
}
