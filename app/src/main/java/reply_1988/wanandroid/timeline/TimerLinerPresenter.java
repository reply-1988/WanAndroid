package reply_1988.wanandroid.timeline;

import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import reply_1988.wanandroid.data.engine.ArticleEngine;
import reply_1988.wanandroid.data.model.ArticleDetailData;


public class TimerLinerPresenter implements TimeLineContract.Presenter {


    private TimeLineContract.View mView;
    private ArticleEngine mEngine;
    private CompositeDisposable mCompositeDisposable;

    public TimerLinerPresenter(TimeLineContract.View view, ArticleEngine engine) {
        this.mView = view;
        this.mEngine = engine;
        this.mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getArticles(int page) {
        Log.d("测试", "huoqule了信息");
        Disposable disposable = mEngine.getArticles(page)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //使用subscribeOn返回当前订阅的观察者
                .subscribeWith(new DisposableObserver<List<ArticleDetailData>>() {
                    @Override
                    public void onNext(List<ArticleDetailData> articleDetailData) {
                        Log.d("测试", "使用了onNext方法");
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
