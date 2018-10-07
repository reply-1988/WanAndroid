package reply_1988.wanandroid.login;

import android.content.Context;

import reply_1988.wanandroid.BasePresenter;
import reply_1988.wanandroid.BaseView;
import reply_1988.wanandroid.data.model.LoginDetailData;

public interface LoginContract {

    interface Presenter extends BasePresenter{

        void login(String username, String password, String type);

        void clearCache(Context context);

        void register(String username, String password);
    }

    interface View extends BaseView<Presenter>{

        void showProgress(Boolean show);

        void showMessage(String msg);

        void saveUserMsg(LoginDetailData detailData);

        void startMainActivity();

        void resumeUserMsg();

        void changeLoginState(Boolean isLogin);

    }
}
