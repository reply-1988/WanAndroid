package reply_1988.wanandroid.search;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import reply_1988.wanandroid.data.engine.FavoriteEngine;
import reply_1988.wanandroid.data.engine.SearchEngine;
import reply_1988.wanandroid.data.model.FavoriteData;
import reply_1988.wanandroid.data.model.SearchDetailData;

public class SearchPresenter implements SearchContract.Presenter {

    private SearchEngine mSearchEngine;

    private SearchContract.View mView;
    private CompositeDisposable mCompositeDisposable;
    private FavoriteEngine mFavoriteEngine;

    public  SearchPresenter(SearchContract.View view) {

        this.mSearchEngine = new SearchEngine();
        this.mView = view;
        this.mFavoriteEngine = new FavoriteEngine();
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
    public void getQueryData(int page, String searchContent, boolean loadMore) {

        Disposable disposable = mSearchEngine.getQueryData(page, searchContent, loadMore)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<SearchDetailData>>() {

                    @Override
                    public void onNext(List<SearchDetailData> searchDetailData) {
                        mView.showArticles(searchDetailData);
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
}
