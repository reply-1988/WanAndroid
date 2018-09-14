package reply_1988.wanandroid.data.source.remote;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import reply_1988.wanandroid.Retrofit.RetrofitClient;
import reply_1988.wanandroid.Retrofit.WanAndroidService;
import reply_1988.wanandroid.data.model.HotKeyData;
import reply_1988.wanandroid.data.model.HotKeyDetailData;
import reply_1988.wanandroid.data.source.HotKeyDataSource;

public class HotKeyRemoteSource implements HotKeyDataSource {

    @Override
    public Observable<List<HotKeyDetailData>> getHotKey() {
        return RetrofitClient.getInstance()
                .create(WanAndroidService.class)
                .getHotKey()
                .map(new Function<HotKeyData, List<HotKeyDetailData>>() {
                    @Override
                    public List<HotKeyDetailData> apply(HotKeyData hotKeyData) throws Exception {
                        return hotKeyData.getData();
                    }
                });
    }
}
