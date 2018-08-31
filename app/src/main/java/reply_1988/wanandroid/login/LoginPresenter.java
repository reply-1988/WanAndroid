package reply_1988.wanandroid.login;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import reply_1988.wanandroid.data.engine.LoginEngine;
import reply_1988.wanandroid.data.model.LoginData;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private LoginEngine mLoginEngine;
    private CompositeDisposable mCompositeDisposable;

    public LoginPresenter(LoginContract.View view, LoginEngine loginEngine) {

        mView = view;
        mLoginEngine = loginEngine;
        this.mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void login(String username, String password, String type) {
        getLoginData(username, password, type);
    }

    @Override
    public boolean tryCookieLogin() {

        Disposable disposable = mLoginEngine.getRemoteLoginData("", "", "login")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginData>() {

                    @Override
                    public void onNext(LoginData loginData) {
                        if (loginData.getErrorCode() != 0) {
                            mView.getRequestResult(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        mCompositeDisposable.add(disposable);
        return true;
    }

    private void getLoginData(String username, String password, String type) {

        Disposable disposable = mLoginEngine.getRemoteLoginData(username, password, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<LoginData>() {

                    @Override
                    public void onNext(LoginData loginData) {

                        if (loginData.getErrorCode() != 0) {
                            mView.showProgress(false);
                            mView.showLoginError(loginData.getErrorMsg());

                        } else {
                            mView.saveUserMsg(loginData.getData());
                            mView.startMainActivity();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showProgress(false);
                    }

                    @Override
                    public void onComplete() {
                        mView.showProgress(false);

                    }
                });
        mCompositeDisposable.add(disposable);

    }
}
