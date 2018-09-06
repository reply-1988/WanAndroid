package reply_1988.wanandroid.data.source.local;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.source.ReadLaterDataSource;
import reply_1988.wanandroid.realm.RealmHelper;

public class ReadLaterLocalSource implements ReadLaterDataSource {



    @Override
    public Observable<List<ArticleDetailData>> getReadLaterList() {

        Realm realm = Realm.getInstance(RealmHelper.getConfiguration("readLater.realm"));
        RealmQuery<ArticleDetailData> query = realm.where(ArticleDetailData.class);
        RealmResults<ArticleDetailData> realmResults = query.findAll();
        //将返回的RealmResults转化成列表
        ArticleDetailData[] detailData = realmResults.toArray(new ArticleDetailData[realmResults.size()]);

        return Observable.just(Arrays.asList(detailData));
    }

    @Override
    public Observable setReadLater(final ArticleDetailData articleDetailData) {


        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                Realm realm = Realm.getInstance(RealmHelper.getConfiguration("readLater.realm"));
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(articleDetailData);

                realm.commitTransaction();
                realm.close();
            }
        });
    }

    @Override
    public Observable cancelReadLater(final int id) {

        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                Realm realm = Realm.getInstance(RealmHelper.getConfiguration("readLater.realm"));
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        RealmResults<ArticleDetailData> realmResults = realm
                                .where(ArticleDetailData.class)
                                .equalTo("id", id).findAll();
                        realmResults.deleteAllFromRealm();
                    }
                });
                realm.close();
            }
        });
    }
}
