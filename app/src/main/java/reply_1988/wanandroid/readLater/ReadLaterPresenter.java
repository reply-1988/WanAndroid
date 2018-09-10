package reply_1988.wanandroid.readLater;

import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import reply_1988.wanandroid.data.engine.ArticleEngine;
import reply_1988.wanandroid.data.engine.FavoriteEngine;
import reply_1988.wanandroid.data.engine.ReadLaterEngine;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.model.FavoriteData;
import reply_1988.wanandroid.data.model.FavoriteDetailData;

public class ReadLaterPresenter implements ReadLaterContract.Presenter {

    private ReadLaterContract.View mView;
    private ReadLaterEngine mReadLaterEngine;
    private FavoriteEngine mFavoriteEngine;
    private CompositeDisposable mCompositeDisposable;

    public ReadLaterPresenter(ReadLaterContract.View view) {
        this.mView = view;
        this.mReadLaterEngine = new ReadLaterEngine();
        this.mView.setPresenter(this);
        this.mFavoriteEngine = new FavoriteEngine();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getArticles(int page, boolean loadMore) {

        Disposable disposable = mReadLaterEngine.getReadLaterList()
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //使用subscribeOn返回当前订阅的观察者
                .subscribeWith(new DisposableObserver<List<ArticleDetailData>>() {
                    @Override
                    public void onNext(List<ArticleDetailData> articleDetailData) {
                        articleDetailData.get(0).setApkLink("www.baidu.com");
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

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }
}
