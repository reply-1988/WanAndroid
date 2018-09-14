package reply_1988.wanandroid.articleDetail;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.print.PrinterId;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.github.clans.fab.FloatingActionButton;
import com.just.agentweb.AgentWeb;

import reply_1988.wanandroid.R;
import reply_1988.wanandroid.data.model.ArticleDetailData;

import static reply_1988.wanandroid.MyApplication.getContext;


public class ArticleDetailFragment extends Fragment implements ArticleDetailContract.View {

    private static final String ARTICLE_URL = "url";
    private static final String ARTICLE_TITLE = "title";
    private static final String ARTICLE_COLLECT = "collect";
    private static final String ARTICLE_READLATER = "readLater";
    private static final String DIALOG_TITLE = "分享";
    private static final String ARTICLE_ID = "id";

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

    private ArticleDetailContract.Presenter mPresenter;

    public ArticleDetailFragment() {
        // Required empty public constructor
    }

    public static ArticleDetailFragment newInstance(ArticleDetailData detailData) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
//        args.putString(ARTICLE_URL, url);
//        args.putString(ARTICLE_TITLE, title);
//        args.putBoolean(ARTICLE_COLLECT, collect);
//        args.putBoolean(ARTICLE_READLATER, readLater);
//        args.putInt(ARTICLE_ID, id);
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
//            title = getArguments().getString(ARTICLE_TITLE);
//            isCollect = getArguments().getBoolean(ARTICLE_COLLECT);
//            isReadLater = getArguments().getBoolean(ARTICLE_READLATER);
//            id = getArguments().getInt(ARTICLE_ID);
        }
        setPresenter(new ArticleDetailPresenter(this));
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_article_detail, container, false);
        webViewContainer = v.findViewById(R.id.webView_container);

        copyUrl = v.findViewById(R.id.copyUrl);
        collect = v.findViewById(R.id.collect);
        collect.setLabelText(isCollect ? "取消收藏" : "收藏");
        collect.setImageResource(isCollect ? R.drawable.ic_favorite_border_black_24dp : R.drawable.ic_favorite_black_24dp);

        readLater = v.findViewById(R.id.readLater);
        share = v.findViewById(R.id.share);

        copyUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyUrl();
            }
        });

        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCollect) {
                    mPresenter.setCollect(id);
                    setCollect();
                } else {

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

            }
        });

        readLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
        collect.setImageResource(R.drawable.ic_favorite_black_24dp);
        collect.setLabelText("取消收藏");
    }

    @Override
    public void cancelCollect() {

    }

    @Override
    public void initView(View view) {

    }
}
