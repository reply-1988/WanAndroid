package reply_1988.wanandroid.search;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import reply_1988.wanandroid.data.engine.FavoriteEngine;
import reply_1988.wanandroid.data.engine.HotKeyEngine;
import reply_1988.wanandroid.data.engine.ReadLaterEngine;
import reply_1988.wanandroid.data.engine.SearchEngine;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.model.FavoriteData;
import reply_1988.wanandroid.data.model.HotKeyDetailData;

public class SearchPresenter implements SearchContract.Presenter {

    private SearchEngine mSearchEngine;

    private SearchContract.View mView;
    private CompositeDisposable mCompositeDisposable;
    private FavoriteEngine mFavoriteEngine;
    private ReadLaterEngine mReadLaterEngine;
    private HotKeyEngine mHotKeyEngine;

    SearchPresenter(SearchContract.View view) {

        mSearchEngine = new SearchEngine();
        mView = view;
        mView.setPresenter(this);
        mFavoriteEngine = new FavoriteEngine();
        mHotKeyEngine = new HotKeyEngine();
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
    public void getQueryData(int page, final String searchContent, boolean loadMore) {

        Disposable disposable = mSearchEngine.getQueryData(page, searchContent, loadMore)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<ArticleDetailData>>() {

                    @Override
                    public void onNext(List<ArticleDetailData> searchDetailData) {
                        if (searchDetailData.size() != 0) {
                            for (ArticleDetailData detailData:
                                 searchDetailData) {
                                mReadLaterEngine.checkReadLater(detailData);
                            }
                            mView.showArticles(searchDetailData);
                            mView.showRecycleView();
                        } else {
                            mView.showNoData(true);
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
    public void setCollect(int id) {
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
    public void cancelCollect(int id) {
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
    public void getHotKey() {

        Disposable disposable = mHotKeyEngine.getHotKey()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<HotKeyDetailData>>() {
                    @Override
                    public void onNext(List<HotKeyDetailData> hotKeyDetailData) {
                        mView.getHotKey(hotKeyDetailData);
                        mView.showTabLayout(true);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mView.showTabLayout(true);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void getKSDetailData(int page, int cid, boolean loadMore) {

        Disposable disposable = mSearchEngine.getKSDetailData(page, cid, loadMore)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<ArticleDetailData>>() {
                    @Override
                    public void onNext(List<ArticleDetailData> searchDetailData) {
                        if (searchDetailData.size() != 0) {
                            for (ArticleDetailData detailData:
                                 searchDetailData) {
                                mReadLaterEngine.checkReadLater(detailData);
                            }
                            mView.showArticles(searchDetailData);

                            mView.showRecycleView();
                        } else {
                            mView.showNoData(true);
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


}
