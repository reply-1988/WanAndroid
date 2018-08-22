package reply_1988.wanandroid.timeline;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import reply_1988.wanandroid.R;
import reply_1988.wanandroid.data.engine.ArticleEngine;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.data.source.remote.ArticlesInternetSource;

public class TimeLineFragment extends Fragment implements TimeLineContract.View{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private TimeLineContract.Presenter mPresenter;
    private TimerLineAdapter mAdapter;
    private View mView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TimeLineFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
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
        Log.d("测试", "使用了get方法");
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.timeline_item_list, container, false);

        // Set the adapter
        if (mView instanceof RecyclerView) {
            Context context = mView.getContext();
            RecyclerView recyclerView = (RecyclerView) mView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }
        mPresenter.getArticles(0);
        return mView;
    }

    @Override
    public void showArticles(List<ArticleDetailData> detailDataList) {
        if (mAdapter != null) {
            Log.d("测试", "没有设置");
        } else {
            mAdapter = new TimerLineAdapter(detailDataList);
            RecyclerView recyclerView = (RecyclerView) mView;
            recyclerView.setAdapter(mAdapter);
            Log.d("测试", "设置了Adapter");
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
