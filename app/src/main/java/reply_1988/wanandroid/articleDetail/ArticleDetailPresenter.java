package reply_1988.wanandroid.articleDetail;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import reply_1988.wanandroid.data.engine.FavoriteEngine;
import reply_1988.wanandroid.data.engine.ReadLaterEngine;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.model.FavoriteData;

public class ArticleDetailPresenter implements ArticleDetailContract.Presenter {

    private FavoriteEngine mFavoriteEngine;

    private ReadLaterEngine mReadLaterEngine;

    private ArticleDetailContract.View mView;
    private CompositeDisposable mCompositeDisposable;

    public ArticleDetailPresenter(ArticleDetailContract.View view) {

        mView = view;
        mFavoriteEngine = new FavoriteEngine();
        mReadLaterEngine = new ReadLaterEngine();
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
    public void setCollect(int id) {
        Disposable disposable = mFavoriteEngine.setFavorite(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FavoriteData>() {
                    @Override
                    public void onNext(FavoriteData favoriteData) {
                        if (favoriteData.getErrorCode() == 0) {
                            mView.setCollect();
                        } else {

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
    }

    @Override
    public void cancelCollect(int id) {
        Disposable disposable = mFavoriteEngine.cancelFavorite(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<FavoriteData>() {
                    @Override
                    public void onNext(FavoriteData data ) {
                        if (data.getErrorCode() == 0) {

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
    }

    @Override
    public void setReadLater(ArticleDetailData detailData) {

    }

    @Override
    public void cancelReadLater(int id) {

    }
}
