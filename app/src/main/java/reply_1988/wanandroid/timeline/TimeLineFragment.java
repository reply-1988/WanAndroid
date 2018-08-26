package reply_1988.wanandroid.timeline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
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

import java.util.List;

import reply_1988.wanandroid.R;
import reply_1988.wanandroid.articleDetail.ArticleDetailActivity;
import reply_1988.wanandroid.data.engine.ArticleEngine;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.source.remote.ArticlesInternetSource;
import reply_1988.wanandroid.interfaces.OnArticleClickedListener;

public class TimeLineFragment extends Fragment implements TimeLineContract.View{


    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String ARTICLE_URL = "articleUrl";

    private int mColumnCount = 1;
    private TimeLineContract.Presenter mPresenter;
    private TimerLineAdapter mAdapter;
    private View mView;
    private int page = 0;

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
        mPresenter = new TimerLinerPresenter(this, ArticleEngine.getArticleEngine(ArticlesInternetSource.getArticlesInternetSource()));
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.timeline_item_list, container, false);

        mPresenter.getArticles(0, false);
        return mView;
    }

    @Override
    public void showArticles(final List<ArticleDetailData> detailDataList) {
        if (mAdapter != null) {
            mAdapter.updateAdapter(detailDataList);
        } else {
            mAdapter = new TimerLineAdapter(detailDataList);
            //设置item被点击
            mAdapter.setOnArticleClickedListener(new OnArticleClickedListener() {
                @Override
                public void onClick(int position) {
                    Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
                    String articleUrl = detailDataList.get(position).getLink();
                    intent.putExtra(ARTICLE_URL, articleUrl);
                    startActivity(intent);
                }
            });
            final RecyclerView recyclerView = mView.findViewById(R.id.list);

            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            //设置refreshLayout
            RefreshLayout refreshLayout = mView.findViewById(R.id.refreshLayout);
            refreshLayout.setEnableAutoLoadMore(false);
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    page = 0;
                    mPresenter.getArticles(page, false);

                    refreshLayout.finishRefresh();
                }
            });

            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    page++;
                    Log.d("当前的页数", String.valueOf(page));
                    mPresenter.getArticles(page, true);

                    refreshLayout.finishLoadMore();
                }
            });
        }
    }


    @Override
    public void setPresenter(TimeLineContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void initView(View view) {

    }
}
