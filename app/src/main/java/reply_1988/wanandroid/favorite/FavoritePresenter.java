package reply_1988.wanandroid.favorite;

import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import reply_1988.wanandroid.data.engine.ArticleEngine;
import reply_1988.wanandroid.data.engine.FavoriteEngine;
import reply_1988.wanandroid.data.model.FavoriteData;
import reply_1988.wanandroid.data.model.FavoriteDetailData;


public class FavoritePresenter implements FavoriteContract.Presenter{

    private FavoriteContract.View mView;
    private ArticleEngine mEngine;
    private FavoriteEngine mFavoriteEngine;
    private CompositeDisposable mCompositeDisposable;

    public FavoritePresenter(FavoriteContract.View view, ArticleEngine engine) {
        this.mView = view;
        this.mEngine = engine;
        this.mView.setPresenter(this);
        mFavoriteEngine = new FavoriteEngine();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getArticles(int page, boolean loadMore) {

        Disposable disposable = mEngine.getFavoriteArticles(loadMore, page)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //使用subscribeOn返回当前订阅的观察者
                .subscribeWith(new DisposableObserver<List<FavoriteDetailData>>() {
                    @Override
                    public void onNext(List<FavoriteDetailData> articleDetailData) {
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
    public void setFavorite(int originId) {

        Disposable disposable = mFavoriteEngine.setFavorite(originId)
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
    public void cancelFavorite(int id, int originId) {

        Disposable disposable = mFavoriteEngine.cancelFavoriteFromList(id, originId)
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
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }
}
