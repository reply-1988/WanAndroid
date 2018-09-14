package reply_1988.wanandroid.data.engine;

import java.util.List;

import io.reactivex.Observable;
import reply_1988.wanandroid.data.model.HotKeyDetailData;
import reply_1988.wanandroid.data.source.HotKeyDataSource;
import reply_1988.wanandroid.data.source.remote.HotKeyRemoteSource;

public class HotKeyEngine implements HotKeyDataSource {


    private HotKeyRemoteSource mHotKeyRemoteSource;
    public HotKeyEngine() {

        mHotKeyRemoteSource = new HotKeyRemoteSource();

    }
    @Override
    public Observable<List<HotKeyDetailData>> getHotKey() {
        return mHotKeyRemoteSource.getHotKey();
    }
}
