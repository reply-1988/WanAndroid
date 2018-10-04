package reply_1988.wanandroid.data.source.local;


import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Locale;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.source.ReadLaterDataSource;
import reply_1988.wanandroid.realm.RealmHelper;

public class ReadLaterLocalSource implements ReadLaterDataSource {

    @Override
    public Observable<List<ArticleDetailData>> getReadLaterList() {

        Realm realm = Realm.getInstance(RealmHelper.getConfiguration("readLater.realm"));
        RealmQuery<ArticleDetailData> query = realm.where(ArticleDetailData.class);
        //获取全部查询结果，并且将其按照稍后阅读的时间进行排序
        RealmResults<ArticleDetailData> realmResults = query
                .findAllAsync().sort("readLaterData", Sort.DESCENDING);
        //将返回的RealmResults转化成列表
        List<ArticleDetailData> list = realm.copyFromRealm(realmResults);

        return Observable.just(list);
    }

    @Override
    public Observable setReadLater(final ArticleDetailData articleDetailData) {

        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {

                articleDetailData.setReadLaterData(formatDate(new Date()));
                articleDetailData.setReadLater(true);
                Realm realm = Realm.getInstance(RealmHelper.getConfiguration("readLater.realm"));
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(@NonNull Realm realm) {
                        realm.copyToRealmOrUpdate(articleDetailData);
                    }
                });

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
            }
        });
    }

    /**
     * 检查该文章在本地的稍后阅读数据库中是否存在，存在则修改文章数据中的readLater为true。
     * @param detailData 需要进行对比的文章数据
     */
    @Override
    public void checkReadLater(final ArticleDetailData detailData) {
        
                Realm realm = Realm.getInstance(RealmHelper.getConfiguration("readLater.realm"));
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<ArticleDetailData> realmResults = realm.where(ArticleDetailData.class)
                                .equalTo("id", detailData.getId())
                                .findAll();
                        if (realmResults.size() == 0) {
                            detailData.setReadLater(false);
                        } else {
                            detailData.setReadLater(true);
                        }
                    }
                });
            }

    /**
     * 清除本地稍后阅读数据库
     */
    @Override
    public void clearCache() {
        Realm realm = Realm.getInstance(RealmHelper.getConfiguration("readLater.realm"));
        final RealmResults<ArticleDetailData> results = realm.where(ArticleDetailData.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    /**
     * 对添加日期进行格式化处理，以便于显示
     * @param date 添加到稍后阅读的日期
     * @return 规范化日期
     */
    private String formatDate(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return format.format(date);
    }
}
