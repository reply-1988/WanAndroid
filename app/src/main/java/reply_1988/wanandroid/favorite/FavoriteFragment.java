package reply_1988.wanandroid.favorite;

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

import java.util.List;

import reply_1988.wanandroid.R;
import reply_1988.wanandroid.articleDetail.ArticleDetailActivity;
import reply_1988.wanandroid.data.engine.ArticleEngine;
import reply_1988.wanandroid.data.model.FavoriteDetailData;
import reply_1988.wanandroid.data.source.remote.ArticlesInternetSource;

import reply_1988.wanandroid.interfaces.OnArticleClickedListener;
import reply_1988.wanandroid.interfaces.OnCancelCollectClickedListener;
import reply_1988.wanandroid.interfaces.OnCollectClickedListener;

public class FavoriteFragment extends Fragment implements FavoriteContract.View  {

    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String ARTICLE_URL = "articleUrl";

    private int mColumnCount = 1;
    private FavoriteContract.Presenter mPresenter;
    private FavoriteAdapter mAdapter;
    private View mView;
    private int page = 0;

    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FavoriteFragment() {
    }


    public static FavoriteFragment newInstance(int columnCount) {
        FavoriteFragment fragment = new FavoriteFragment();
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
        mPresenter = new FavoritePresenter(this, ArticleEngine.getArticleEngine(ArticlesInternetSource.getArticlesInternetSource()));
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
        mView = inflater.inflate(R.layout.favroite_item_list, container, false);
        recyclerView = mView.findViewById(R.id.list);
        mPresenter.getArticles(0, false);
        return mView;
    }

    @Override
    public void showArticles(final List<FavoriteDetailData> detailDataList) {
        if (mAdapter != null) {
            mAdapter.updateAdapter(detailDataList);
        } else {
            mAdapter = new FavoriteAdapter(detailDataList);
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

            //收藏
            mAdapter.setOnCollectClickedListener(new OnCollectClickedListener() {
                @Override
                public void onClick(int position) {

                    int originId = detailDataList.get(position).getOriginId();
                    mPresenter.setFavorite(originId);
                    Log.d("修改图标", "修改11");
                    detailDataList.get(position).setCollect(true);
                    mAdapter.notifyItemChanged(position);
                }
            });
            //取消收藏
            mAdapter.setOnCancelCollectClickedListener(new OnCancelCollectClickedListener() {
                @Override
                public void onClick(int position){

                    int id = detailDataList.get(position).getId();
                    int originId = detailDataList.get(position).getOriginId();
                    mPresenter.cancelFavorite(id, originId);
                    detailDataList.get(position).setCollect(false);
                    mAdapter.notifyItemChanged(position);
                }
            });

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
    public void setPresenter(FavoriteContract.Presenter presenter) {
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
