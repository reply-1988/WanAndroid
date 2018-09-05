package reply_1988.wanandroid.readLater;

import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import reply_1988.wanandroid.data.engine.ArticleEngine;
import reply_1988.wanandroid.data.model.ArticleDetailData;

public class ReadLaterPresenter implements ReadLaterContract.Presenter {

    private ReadLaterContract.View mView;
    private ArticleEngine mEngine;
    private CompositeDisposable mCompositeDisposable;

    public ReadLaterPresenter(ReadLaterContract.View view, ArticleEngine engine) {
        this.mView = view;
        this.mEngine = engine;
        this.mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getArticles(int page, boolean loadMore) {

        Disposable disposable = mEngine.getFavoriteArticles(loadMore, page)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //使用subscribeOn返回当前订阅的观察者
                .subscribeWith(new DisposableObserver<List<ArticleDetailData>>() {
                    @Override
                    public void onNext(List<ArticleDetailData> articleDetailData) {
                        mView.showArticles(articleDetailData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("测试", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }
}
