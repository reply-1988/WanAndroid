package reply_1988.wanandroid.timeline;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import reply_1988.wanandroid.data.engine.ArticleEngine;
import reply_1988.wanandroid.data.engine.FavoriteEngine;
import reply_1988.wanandroid.data.engine.ReadLaterEngine;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.model.FavoriteData;


public class TimerLinerPresenter implements TimeLineContract.Presenter {


    private TimeLineContract.View mView;
    private ArticleEngine mEngine;
    private FavoriteEngine mFavoriteEngine;
    private CompositeDisposable mCompositeDisposable;
    private ReadLaterEngine mReadLaterEngine;

    public TimerLinerPresenter(TimeLineContract.View view, ArticleEngine engine) {
        this.mView = view;
        this.mEngine = engine;
        this.mFavoriteEngine = new FavoriteEngine();
        this.mReadLaterEngine = new ReadLaterEngine();
        this.mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getArticles(int page, boolean loadMore) {

        Disposable disposable = mEngine.getArticles(page, loadMore)
                .doOnNext(new Consumer<List<ArticleDetailData>>() {
                    @Override
                    public void accept(List<ArticleDetailData> detailDataList) throws Exception {
                        for (ArticleDetailData detailData:
                             detailDataList) {
                            mReadLaterEngine.checkReadLater(detailData);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //使用subscribeWith返回当前订阅的观察者
                .subscribeWith(new DisposableObserver<List<ArticleDetailData>>() {
                    @Override
                    public void onNext(List<ArticleDetailData> articleDetailData) {

                        mView.showArticles(articleDetailData);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void setFavorite(int id) {
        Disposable disposable = mFavoriteEngine.setFavorite(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FavoriteData>() {
                    @Override
                    public void onNext(FavoriteData favoriteData) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void cancelFavorite(int id) {
        Disposable disposable = mFavoriteEngine.cancelFavorite(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FavoriteData>() {
                    @Override
                    public void onNext(FavoriteData favoriteData) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void setReadLater(ArticleDetailData detailData) {

        Disposable disposable = (Disposable) mReadLaterEngine.setReadLater(detailData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver() {
                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void cancelReadLater(int id) {

        Disposable disposable = (Disposable) mReadLaterEngine.cancelReadLater(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver() {
                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void subscribe() {
        getArticles(-1, false);
        Log.d("恢复！！！", "huifu1!!!!!");
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }
}
