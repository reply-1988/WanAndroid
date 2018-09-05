package reply_1988.wanandroid.timeline;

import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import reply_1988.wanandroid.data.engine.ArticleEngine;
import reply_1988.wanandroid.data.engine.FavoriteEngine;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.model.FavoriteData;


public class TimerLinerPresenter implements TimeLineContract.Presenter {


    private TimeLineContract.View mView;
    private ArticleEngine mEngine;
    private FavoriteEngine mFavoriteEngine;
    private CompositeDisposable mCompositeDisposable;

    public TimerLinerPresenter(TimeLineContract.View view, ArticleEngine engine) {
        this.mView = view;
        this.mEngine = engine;
        this.mFavoriteEngine = new FavoriteEngine();
        this.mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getArticles(int page, boolean loadMore) {

        Disposable disposable = mEngine.getArticles(page, loadMore)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //使用subscribeWith返回当前订阅的观察者
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
    public void subscribe() {
        getArticles(-1, false);
        Log.d("恢复！！！", "huifu1!!!!!");
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }
}
