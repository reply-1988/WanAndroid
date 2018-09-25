package reply_1988.wanandroid.knowledgesystem;

import android.view.View;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import reply_1988.wanandroid.data.engine.KSEngine;
import reply_1988.wanandroid.data.model.KnowledgeSystemData;

public class KSPresenter implements KSContract.Presenter{

    private KSEngine mKSEngine;
    private CompositeDisposable mCompositeDisposable;
    private KSContract.View mView;

    public KSPresenter(KSContract.View view) {

        mKSEngine = new KSEngine();
        mCompositeDisposable = new CompositeDisposable();
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void getKSData() {
        Disposable disposable = mKSEngine.getKSData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<KnowledgeSystemData>() {
                    @Override
                    public void onNext(KnowledgeSystemData knowledgeSystemData) {
                        mView.setAdapter(knowledgeSystemData);
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
