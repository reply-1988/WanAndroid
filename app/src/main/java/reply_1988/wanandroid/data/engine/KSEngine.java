package reply_1988.wanandroid.data.engine;

import io.reactivex.Observable;
import reply_1988.wanandroid.data.model.KnowledgeSystemData;
import reply_1988.wanandroid.data.source.KSDataSource;
import reply_1988.wanandroid.data.source.remote.KSRemoteSource;

public class KSEngine implements KSDataSource {

    private KSRemoteSource mKSRemoteSource;

    public KSEngine() {

        mKSRemoteSource = new KSRemoteSource();
    }

    @Override
    public Observable<KnowledgeSystemData> getKSData() {
        return mKSRemoteSource.getKSData();
    }
}
