package reply_1988.wanandroid.data.engine;

import java.util.List;

import io.reactivex.Observable;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.source.ReadLaterDataSource;
import reply_1988.wanandroid.data.source.local.ReadLaterLocalSource;

public class ReadLaterEngine implements ReadLaterDataSource {

    private ReadLaterLocalSource mLocalSource;

    public ReadLaterEngine() {

        mLocalSource = new ReadLaterLocalSource();
    }
    //从数据库中获取稍后阅读文章的list
    @Override
    public Observable<List<ArticleDetailData>> getReadLaterList() {

        return mLocalSource.getReadLaterList();
    }

    @Override
    public Observable setReadLater(ArticleDetailData detailData) {

        return mLocalSource.setReadLater(detailData);
    }

    @Override
    public Observable cancelReadLater(int id) {

        return mLocalSource.cancelReadLater(id);
    }

    @Override
    public void checkReadLater(ArticleDetailData detailData) {

       mLocalSource.checkReadLater(detailData);
    }

    @Override
    public void clearCache() {
        mLocalSource.clearCache();
    }
}
