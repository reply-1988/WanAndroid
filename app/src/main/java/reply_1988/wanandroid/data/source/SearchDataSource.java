package reply_1988.wanandroid.data.source;

import java.util.List;

import io.reactivex.Observable;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.model.ArticlesData;
import reply_1988.wanandroid.data.model.ArticlesDetailData;
import reply_1988.wanandroid.data.model.SearchData;
import reply_1988.wanandroid.data.model.SearchDetailData;

public interface SearchDataSource {

    Observable<List<ArticleDetailData>> getQueryData(int page, String searchContent, boolean loadMore);

    /**
     * 获取某一体系下的数据
     * @param page 页码：从0开始
     * @param cid  知识体系的二级目录的id
     * @param loadMore 是否加载更多
     * @return  查询的文章数据
     */
    Observable<List<ArticleDetailData>> getKSDetailData(int page, int cid, boolean loadMore);
}
