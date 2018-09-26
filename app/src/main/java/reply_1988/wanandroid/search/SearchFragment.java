package reply_1988.wanandroid.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import reply_1988.wanandroid.R;
import reply_1988.wanandroid.articleDetail.ArticleDetailActivity;
import reply_1988.wanandroid.data.model.HotKeyDetailData;
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

    public static final String ARTICLE_URL = "articleUrl";

    private int mColumnCount = 1;
    private SearchContract.Presenter mPresenter;
    private SearchAdapter mAdapter;
    private int page = 0;
    private String searchContent = "";
    private int ks_cid = -1;
    private int cat_cid = -1;

    private View mView;
    private SearchAdapter mSearchAdapter;
    private RecyclerView mRecyclerView;
    private TagFlowLayout mTagFlowLayout;
    private FrameLayout mFrameLayout;
    private SearchView mSearchView;

    private Toolbar mToolbar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchFragment() {
    }

    public static SearchFragment newInstance(String searchContent, int ks_cid, int cat_cid) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(SearchActivity.ARG_SEARCH_CONTENT, searchContent);
        args.putInt(SearchActivity.ARG_KS_CID, ks_cid);
        args.putInt(SearchActivity.ARG_CATEGORY_CID, cat_cid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            searchContent = getArguments().getString(SearchActivity.ARG_SEARCH_CONTENT);
            ks_cid = getArguments().getInt(SearchActivity.ARG_KS_CID, -1);
            cat_cid = getArguments().getInt(SearchActivity.ARG_CATEGORY_CID, -1);
        }
        mSearchAdapter = new SearchAdapter(new ArrayList<SearchDetailData>(0));
        setHasOptionsMenu(true);
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
        mToolbar = mView.findViewById(R.id.toolBar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        mRecyclerView = mView.findViewById(R.id.list);
        mTagFlowLayout = mView.findViewById(R.id.tag_flowLayout);
        mFrameLayout = mView.findViewById(R.id.NoDataLayout);

        if (ks_cid == -1 && cat_cid == -1) {
            mPresenter.getQueryData(0, searchContent, false);
        } else if(ks_cid != -1 && cat_cid == -1){
            mPresenter.getKSDetailData(0, ks_cid, false);
        } else {
            mPresenter.getKSDetailData(0, cat_cid, false);
        }

        mRecyclerView.setAdapter(mSearchAdapter);
        return mView;
    }

    @Override
    public void showArticles(final List<SearchDetailData> detailDataList) {
        if (mAdapter != null) {
            mAdapter.updateAdapter(detailDataList);
        } else {
            mAdapter = new SearchAdapter(detailDataList);
            setClickListener(mAdapter, detailDataList);

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            searchContent = mSearchView.getQuery().toString();
            //设置refreshLayout
            RefreshLayout refreshLayout = mView.findViewById(R.id.refreshLayout);
            refreshLayout.setEnableAutoLoadMore(false);

            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    page = 0;
                    checkParameter(page, false);
                    //结束刷新画面
                    refreshLayout.finishRefresh();
                }
            });

            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    page++;
                    Log.d("当前的页数", String.valueOf(page));
                    checkParameter(page, true);
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
    public void getHotKey(final List<HotKeyDetailData> detailDataList) {

        mTagFlowLayout.setAdapter(new TagAdapter<HotKeyDetailData>(detailDataList) {
            @Override
            public View getView(FlowLayout parent, int position, HotKeyDetailData detailData) {
                TextView textView = (TextView) LayoutInflater
                        .from(getContext())
                        .inflate(R.layout.item_flowlayout, mTagFlowLayout, false);
                textView.setText(detailDataList.get(position).getName());

                mTagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        String searchContent = detailDataList.get(position).getName();
                        mPresenter.getQueryData(0, searchContent, false);
                        return true;
                    }
                });

                return textView;
            }

        });
    }

    @Override
    public void showTabLayout(boolean a) {
        mTagFlowLayout.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mFrameLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNoData(boolean a) {
        mTagFlowLayout.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mFrameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRecycleView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mTagFlowLayout.setVisibility(View.GONE);
        mFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void setSearchContent(String searchContent) {
        mSearchView.setQuery(searchContent, false);
    }

    @Override
    public void setClickListener(SearchAdapter searchAdapter, final List<SearchDetailData> detailDataList) {
        searchAdapter.setOnArticleClickedListener(new OnArticleClickedListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
                String articleUrl = detailDataList.get(position).getLink();
                intent.putExtra(ARTICLE_URL, articleUrl);
                startActivity(intent);
            }
        });

        //设置收藏按钮被点击
        searchAdapter.setOnCollectClickedListener(new OnCollectClickedListener() {
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
        searchAdapter.setOnCancelCollectClickedListener(new OnCancelCollectClickedListener() {
            @Override
            public void onClick(int position){

                int id = detailDataList.get(position).getId();
                mPresenter.cancelCollect(id);
                detailDataList.get(position).setCollect(false);
                mAdapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    public void checkParameter(int page, boolean loadMore) {
        if (ks_cid == -1 && cat_cid == -1) {
            mPresenter.getQueryData(page, searchContent, loadMore);
        } else if(ks_cid != -1 && cat_cid == -1){
            mPresenter.getKSDetailData(page, ks_cid, loadMore);
        } else {
            mPresenter.getKSDetailData(page, cat_cid, loadMore);
        }
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) menuItem.getActionView();
        mSearchView.clearFocus();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.getQueryData(0, query, false);
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        mSearchView.setQuery(searchContent, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
