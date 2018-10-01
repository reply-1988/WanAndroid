package reply_1988.wanandroid.articleDetail;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.just.agentweb.AgentWeb;

import reply_1988.wanandroid.R;
import reply_1988.wanandroid.data.model.ArticleDetailData;


public class ArticleDetailFragment extends Fragment implements ArticleDetailContract.View {

    private static final String DIALOG_TITLE = "分享";

    private String url;
    private String title;
    private boolean isCollect;
    private boolean isReadLater;
    private int id;
    private ArticleDetailData mArticleDetailData;

    private AgentWeb mAgentWeb;
    private FrameLayout webViewContainer;

    private FloatingActionButton collect;
    private FloatingActionButton readLater;
    private FloatingActionButton copyUrl;
    private FloatingActionButton share;

    private FloatingActionMenu mFloatingActionMenu;

    private ArticleDetailContract.Presenter mPresenter;

    public ArticleDetailFragment() {
        // Required empty public constructor
    }

    public static ArticleDetailFragment newInstance(ArticleDetailData detailData) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();

        args.putSerializable("data", detailData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            mArticleDetailData = (ArticleDetailData) getArguments().getSerializable("data");
            url = mArticleDetailData.getLink();
            title = mArticleDetailData.getTitle();
            isCollect = mArticleDetailData.isCollect();
            isReadLater = mArticleDetailData.isReadLater();
            id = mArticleDetailData.getId();
        }
        setPresenter(new ArticleDetailPresenter(this));
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_article_detail, container, false);
        webViewContainer = v.findViewById(R.id.webView_container);

        initView(v);
        openArticle(url);
        return v;
    }

    private void openArticle(String url) {

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(webViewContainer, new FrameLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);

        WebView webView =mAgentWeb.getWebCreator().getWebView();
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroyView();
    }

    private void copyUrl() {
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("simpleText", url);
        clipboardManager.setPrimaryClip(clipData);
    }


    @Override
    public void setPresenter(ArticleDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    //设置collect的按钮和文字为收藏状态
    @Override
    public void setCollect() {

        mFloatingActionMenu.close(true);
        collect.setImageResource(R.drawable.ic_favorite_black_24dp);
        collect.setLabelText("取消收藏");
    }

    //设置collect的按钮和文字为未收藏状态
    @Override
    public void cancelCollect() {

        mFloatingActionMenu.close(true);
        collect.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        collect.setLabelText("收藏");
    }

    @Override
    public void setReadLater() {
        readLater.setLabelText("从稍后阅读中删除");
        mFloatingActionMenu.close(true);
    }

    @Override
    public void cancelReadLater() {
        readLater.setLabelText("添加到稍后阅读");
        mFloatingActionMenu.close(true);
    }

    //对view进行相应的初始化及onClick处理
    @Override
    public void initView(View v) {
        copyUrl = v.findViewById(R.id.copyUrl);

        collect = v.findViewById(R.id.collect);
        collect.setLabelText(isCollect ? "取消收藏" : "收藏");
        collect.setImageResource(isCollect ? R.drawable.ic_favorite_black_24dp : R.drawable.ic_favorite_border_black_24dp);

        readLater = v.findViewById(R.id.readLater);
        readLater.setLabelText(isReadLater ? "取消稍后阅读" : "添加到稍后阅读");
        share = v.findViewById(R.id.share);

        mFloatingActionMenu = v.findViewById(R.id.float_menu);

        copyUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyUrl();
                mFloatingActionMenu.close(true);
                Snackbar.make(mFloatingActionMenu, "已经把网址复制到剪切板了", Snackbar.LENGTH_SHORT).show();
            }
        });

        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCollect) {
                    mPresenter.setCollect(id);
//                    setCollect();
                    isCollect = !isCollect;
                    Snackbar.make(mFloatingActionMenu, "已经成功收藏", Snackbar.LENGTH_SHORT).show();
                } else {
                    mPresenter.cancelCollect(id);
//                    cancelCollect();
                    isCollect = !isCollect;
                    Snackbar.make(mFloatingActionMenu, "已经成功取消收藏", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TITLE, title);
                intent.putExtra(Intent.EXTRA_TEXT, title + "\n" + url);

                intent = Intent.createChooser(intent, DIALOG_TITLE);
                startActivity(intent);
                mFloatingActionMenu.close(true);
            }
        });

        readLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadLater) {
                    mPresenter.cancelReadLater(id);
                    isReadLater = !isReadLater;
                    Snackbar.make(mFloatingActionMenu, "已经从稍后阅读删除了", Snackbar.LENGTH_SHORT).show();
                } else {
                    mPresenter.setReadLater(mArticleDetailData);
                    isReadLater = !isReadLater;
                    Snackbar.make(mFloatingActionMenu, "已经把添加到稍后阅读了", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }
}
