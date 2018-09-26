package reply_1988.wanandroid.timeline;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import reply_1988.wanandroid.R;
import reply_1988.wanandroid.articleDetail.ArticleDetailActivity;
import reply_1988.wanandroid.data.engine.ArticleEngine;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.source.remote.ArticlesRemoteSource;
import reply_1988.wanandroid.interfaces.OnArticleClickedListener;
import reply_1988.wanandroid.interfaces.OnCancelCollectClickedListener;
import reply_1988.wanandroid.interfaces.OnCancelReadLaterClickedListener;
import reply_1988.wanandroid.interfaces.OnCategoryClickedListener;
import reply_1988.wanandroid.interfaces.OnCollectClickedListener;
import reply_1988.wanandroid.interfaces.OnReadLaterClickedListener;
import reply_1988.wanandroid.search.SearchActivity;

import static reply_1988.wanandroid.articleDetail.ArticleDetailActivity.ARTICLE_TITLE;
import static reply_1988.wanandroid.articleDetail.ArticleDetailActivity.ARTICLE_URL;

public class TimeLineFragment extends Fragment implements TimeLineContract.View{


    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private TimeLineContract.Presenter mPresenter;
    private TimerLineAdapter mAdapter;
    private View mView;
    private int page = 0;
    private TimerLineAdapter mTimerLineAdapter;
    private RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TimeLineFragment() {
    }

    public static TimeLineFragment newInstance(int columnCount) {
        TimeLineFragment fragment = new TimeLineFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        mPresenter = new TimerLinerPresenter(this, ArticleEngine.getArticleEngine(ArticlesRemoteSource.getArticlesRemoteSource()));
        mTimerLineAdapter = new TimerLineAdapter(new ArrayList<ArticleDetailData>(0));
    }

    @Override
    public void onResume() {

        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            return mView;
        }
        mView = inflater.inflate(R.layout.timeline_item_list, container, false);
        mRecyclerView = mView.findViewById(R.id.list);

        mPresenter.getArticles(0, false);

        Log.d("测试！！！", "TimeLineFragment");
        mRecyclerView.setAdapter(mTimerLineAdapter);
        return mView;
    }

    @Override
    public void showArticles(final List<ArticleDetailData> detailDataList) {
        if (mAdapter != null) {
            mAdapter.updateAdapter(detailDataList);
        } else {
            mAdapter = new TimerLineAdapter(detailDataList);
            //设置item被点击
            setOnClick(detailDataList);

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            //设置refreshLayout
            RefreshLayout refreshLayout = mView.findViewById(R.id.refreshLayout);
            refreshLayout.setEnableAutoLoadMore(false);
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    page = 0;
                    mPresenter.getArticles(page, false);
                    //结束刷新画面
                    refreshLayout.finishRefresh();
                }
            });

            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    page++;
                    Log.d("当前的页数", String.valueOf(page));
                    mPresenter.getArticles(page, true);
                    //结束刷新画面
                    refreshLayout.finishLoadMore();
                }
            });
        }
    }

    private void setOnClick(final List<ArticleDetailData> detailDataList) {
        mAdapter.setOnArticleClickedListener(new OnArticleClickedListener() {
            @Override
            public void onClick(int position) {

                Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
                ArticleDetailData detailData = detailDataList.get(position);
                intent.putExtra("data", detailData);

                startActivity(intent);
            }
        });

        //设置收藏按钮被点击
        mAdapter.setOnCollectClickedListener(new OnCollectClickedListener() {
            @Override
            public void onClick(int position) {
                int id = detailDataList.get(position).getId();
                mPresenter.setFavorite(id);
                Log.d("修改图标", "修改11");
                detailDataList.get(position).setCollect(true);
                mAdapter.notifyItemChanged(position);
            }
        });
        //收藏按钮再次点击取消
        mAdapter.setOnCancelCollectClickedListener(new OnCancelCollectClickedListener() {
            @Override
            public void onClick(int position){

                int id = detailDataList.get(position).getId();
                mPresenter.cancelFavorite(id);
                detailDataList.get(position).setCollect(false);
                mAdapter.notifyItemChanged(position);
            }
        });

        //设置稍后阅读
        mAdapter.setOnReadLaterClickedListener(new OnReadLaterClickedListener() {
            @Override
            public void onClick(int position) {

                detailDataList.get(position).setReadLater(true);
                mPresenter.setReadLater(detailDataList.get(position));
                mAdapter.notifyItemChanged(position);

            }
        });

        //设置取消稍后阅读
        mAdapter.setOnCancelReadLaterClickedListener(new OnCancelReadLaterClickedListener() {
            @Override
            public void onClick(int position) {

                int id = detailDataList.get(position).getId();
                detailDataList.get(position).setReadLater(false);
                mPresenter.cancelReadLater(id);
                mAdapter.notifyItemChanged(position);

            }
        });

        //设置分类点击
        mAdapter.setOnCategoryClickedListener(new OnCategoryClickedListener() {
            @Override
            public void onClick(int position) {
                int cid = detailDataList.get(position).getChapterId();
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra(SearchActivity.ARG_CATEGORY_CID, cid);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setPresenter(TimeLineContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }
}
