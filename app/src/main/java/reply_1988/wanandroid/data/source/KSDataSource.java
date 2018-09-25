package reply_1988.wanandroid.data.source;

import io.reactivex.Observable;
import reply_1988.wanandroid.data.model.KnowledgeSystemData;

public interface KSDataSource {

    Observable<KnowledgeSystemData> getKSData();
}
