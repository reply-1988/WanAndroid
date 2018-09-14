package reply_1988.wanandroid.data.source;

import java.util.List;

import io.reactivex.Observable;
import reply_1988.wanandroid.data.model.HotKeyData;
import reply_1988.wanandroid.data.model.HotKeyDetailData;

public interface HotKeyDataSource {

    Observable<List<HotKeyDetailData>> getHotKey();
}
