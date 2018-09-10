package reply_1988.wanandroid.search;

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
import reply_1988.wanandroid.data.model.SearchDetailData;
import reply_1988.wanandroid.interfaces.OnArticleClickedListener;
import reply_1988.wanandroid.interfaces.OnCancelCollectClickedListener;
import reply_1988.wanandroid.interfaces.OnCollectClickedListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements SearchContract.View{
    private static final String ARG_SEARCH_CONTENT = "search_content";
    public static final String ARTICLE_URL = "articleUrl";

    private int mColumnCount = 1;
    private SearchContract.Presenter mPresenter;
    private SearchAdapter mAdapter;
    private View mView;
    private int page = 0;
    private String searchContent = "";
    private SearchAdapter mSearchAdapter;
    private RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchFragment() {
    }

    public static SearchFragment newInstance(String searchContent) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SEARCH_CONTENT, searchContent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            searchContent = getArguments().getString(ARG_SEARCH_CONTENT);
        }
        mPresenter = new SearchPresenter(this);
        mSearchAdapter = new SearchAdapter(new ArrayList<SearchDetailData>(0));
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
        mView = inflater.inflate(R.layout.fragment_search, container, false);
        mRecyclerView = mView.findViewById(R.id.list);

        mPresenter.getQueryData(0, searchContent, false);

        mRecyclerView.setAdapter(mSearchAdapter);
        return mView;
    }

    @Override
    public void showArticles(final List<SearchDetailData> detailDataList) {
        if (mAdapter != null) {
            mAdapter.updateAdapter(detailDataList);
        } else {
            mAdapter = new SearchAdapter(detailDataList);
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

            //设置收藏按钮被点击
            mAdapter.setOnCollectClickedListener(new OnCollectClickedListener() {
                @Override
                public void onClick(int position) {
                    int id = detailDataList.get(position).getId();
                    mPresenter.setCollect(id);
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
                    mPresenter.cancelCollect(id);
                    detailDataList.get(position).setCollect(false);
                    mAdapter.notifyItemChanged(position);
                }
            });

            //设置稍后阅读
//            mAdapter.setOnReadLaterClickedListener(new OnReadLaterClickedListener() {
//                @Override
//                public void onClick(int position) {
//
//                    detailDataList.get(position).setReadLater(true);
//                    mPresenter.setReadLater(detailDataList.get(position));
//                    mAdapter.notifyItemChanged(position);
//
//                }
//            });

            //设置取消稍后阅读
//            mAdapter.setOnCancelReadLaterClickedListener(new OnCancelReadLaterClickedListener() {
//                @Override
//                public void onClick(int position) {
//
//                    int id = detailDataList.get(position).getId();
//                    detailDataList.get(position).setReadLater(false);
//                    mPresenter.cancelReadLater(id);
//                    mAdapter.notifyItemChanged(position);
//
//                }
//            });

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            //设置refreshLayout
            RefreshLayout refreshLayout = mView.findViewById(R.id.refreshLayout);
            refreshLayout.setEnableAutoLoadMore(false);
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    page = 0;
                    mPresenter.getQueryData(page, searchContent, false);
                    //结束刷新画面
                    refreshLayout.finishRefresh();
                }
            });

            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    page++;
                    Log.d("当前的页数", String.valueOf(page));
                    mPresenter.getQueryData(page, searchContent, true);
                    //结束刷新画面
                    refreshLayout.finishLoadMore();
                }
            });
        }
    }

    @Override
    public void setFavoriteButton() {

    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
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
