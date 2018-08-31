package reply_1988.wanandroid.data.source;


import io.reactivex.Observable;
import reply_1988.wanandroid.data.model.LoginData;
import reply_1988.wanandroid.data.model.LoginDetailData;

public interface LoginDataSource {

    Observable<LoginData> getRemoteLoginData(String username, String password, String type);

    Observable<LoginDetailData> getLocalLoginData(int id);
}
