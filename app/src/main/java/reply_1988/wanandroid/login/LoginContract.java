package reply_1988.wanandroid.login;

import reply_1988.wanandroid.BasePresenter;
import reply_1988.wanandroid.BaseView;
import reply_1988.wanandroid.data.model.LoginDetailData;

public interface LoginContract {

    interface Presenter extends BasePresenter{

        void login(String username, String password, String type);

    }

    interface View extends BaseView<Presenter>{

        void showProgress(Boolean show);

        void showLoginError(String errorMsg);

        void saveUserMsg(LoginDetailData detailData);

        void startMainActivity();

        void getRequestResult(Boolean success);

        void resumeUserMsg();

    }
}
