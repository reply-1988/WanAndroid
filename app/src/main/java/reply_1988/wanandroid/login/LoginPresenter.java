package reply_1988.wanandroid.login;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import reply_1988.wanandroid.data.engine.LoginEngine;
import reply_1988.wanandroid.data.engine.ReadLaterEngine;
import reply_1988.wanandroid.data.model.LoginData;
import reply_1988.wanandroid.data.source.local.LoginLocalSource;
import reply_1988.wanandroid.data.source.remote.LoginRemoteSource;

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private LoginEngine mLoginEngine;
    private ReadLaterEngine mReadLaterEngine;
    private CompositeDisposable mCompositeDisposable;

    public LoginPresenter(LoginContract.View view) {

        mView = view;
        mLoginEngine = new LoginEngine(LoginRemoteSource.getInstance(), LoginLocalSource.getInstance());
        mReadLaterEngine = new ReadLaterEngine();
        mView.setPresenter(this);
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
    public void clearCache(Context context) {
        mLoginEngine.clearCache(context);
        mReadLaterEngine.clearCache();
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
                            mView.showError(loginData.getErrorMsg());
                            mView.changeLoginState(false);
                        } else {
                            mView.saveUserMsg(loginData.getData());
                            mView.showProgress(true);
                            mView.changeLoginState(true);
                            mView.startMainActivity();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showProgress(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        mCompositeDisposable.add(disposable);

    }
}
