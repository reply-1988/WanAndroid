package reply_1988.wanandroid.readLater;

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

import reply_1988.wanandroid.CommonAdapter;
import reply_1988.wanandroid.R;
import reply_1988.wanandroid.articleDetail.ArticleDetailActivity;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.interfaces.OnArticleClickedListener;
import reply_1988.wanandroid.interfaces.OnCancelCollectClickedListener;
import reply_1988.wanandroid.interfaces.OnCancelReadLaterClickedListener;
import reply_1988.wanandroid.interfaces.OnCategoryClickedListener;
import reply_1988.wanandroid.interfaces.OnCollectClickedListener;
import reply_1988.wanandroid.interfaces.OnReadLaterClickedListener;
import reply_1988.wanandroid.search.SearchActivity;


public class ReadLaterFragment extends Fragment implements ReadLaterContract.View{

    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String ARTICLE_URL = "articleUrl";

    private int mColumnCount = 1;
    private ReadLaterContract.Presenter mPresenter;
    private CommonAdapter mAdapter;
    private View mView;
    private int page = 0;

    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReadLaterFragment() {
    }


    public static ReadLaterFragment newInstance(int columnCount) {
        ReadLaterFragment fragment = new ReadLaterFragment();
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
        mPresenter = new ReadLaterPresenter(this);

    }

    @Override
    public void onResume() {

        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //判断view是否从Fragment中销毁，没有则返回这个view，并将其从parentView中删除
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            return mView;
        }
        mView = inflater.inflate(R.layout.readlater_item_list, container, false);
        recyclerView = mView.findViewById(R.id.list);

        mPresenter.getArticles(0, false);

        Log.d("测试！！！！", "onCreateView Favorite");
        recyclerView.setAdapter(mAdapter);
        return mView;
    }

    @Override
    public void showArticles(final List<ArticleDetailData> detailDataList) {
        if (mAdapter != null) {
            mAdapter.updateAdapter(detailDataList);
        } else {
            mAdapter = new CommonAdapter(detailDataList);
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
                    String title = detailDataList.get(position).getChapterName();
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(SearchActivity.ARG_CATEGORY_CID, cid);
                    intent.putExtra(SearchActivity.ARG_TITLE, title);
                    startActivity(intent);
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
    public void setPresenter(ReadLaterContract.Presenter presenter) {
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
