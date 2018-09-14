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


public class ArticleDetailFragment extends Fragment {

    private static final String ARTICLE_URL = "url";
    private static final String ARTICLE_TITLE = "title";
    private static final String DIALOG_TITLE = "分享";

    private String url;
    private String title;

    private AgentWeb mAgentWeb;
    private FrameLayout webViewContainer;

    private FloatingActionButton collect;
    private FloatingActionButton readLater;
    private FloatingActionButton copyUrl;
    private FloatingActionButton share;



    public ArticleDetailFragment() {
        // Required empty public constructor
    }


    public static ArticleDetailFragment newInstance(String url, String title) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARTICLE_URL, url);
        args.putString(ARTICLE_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARTICLE_URL);
            title = getArguments().getString(ARTICLE_TITLE);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_article_detail, container, false);
        webViewContainer = v.findViewById(R.id.webView_container);

        copyUrl = v.findViewById(R.id.copyUrl);
        collect = v.findViewById(R.id.collect);
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


}
