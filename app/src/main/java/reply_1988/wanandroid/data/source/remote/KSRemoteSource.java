package reply_1988.wanandroid.data.source.remote;

import io.reactivex.Observable;
import reply_1988.wanandroid.Retrofit.RetrofitClient;
import reply_1988.wanandroid.Retrofit.WanAndroidService;
import reply_1988.wanandroid.data.model.KnowledgeSystemData;
import reply_1988.wanandroid.data.source.KSDataSource;

public class KSRemoteSource implements KSDataSource{

    @Override
    public Observable<KnowledgeSystemData> getKSData() {
        return RetrofitClient.getInstance()
                .create(WanAndroidService.class)
                .getKSData();
    }
}
