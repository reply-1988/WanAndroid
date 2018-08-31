package reply_1988.wanandroid.data.source.remote;

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
        }

        assert loginDataObservable != null;
        return loginDataObservable
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<LoginData>() {
                    @Override
                    public void accept(LoginData loginData) throws Exception {
                        //saveToRealm(loginData.getData());
                    }
                });
    }

    @Override
    public Observable<LoginDetailData> getLocalLoginData(int id) {
        //local有独立的类进行获取
        return null;
    }

    private void saveToRealm(LoginDetailData detailData) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(detailData);
        realm.commitTransaction();
        realm.close();
    }
}
