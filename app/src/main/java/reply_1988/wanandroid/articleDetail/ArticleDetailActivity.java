package reply_1988.wanandroid.articleDetail;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import reply_1988.wanandroid.R;
import reply_1988.wanandroid.data.model.ArticleDetailData;
import reply_1988.wanandroid.timeline.TimeLineFragment;

public class ArticleDetailActivity extends AppCompatActivity {

    public static final String ARTICLE_URL = "articleUrl";
    public static final String ARTICLE_TITLE = "articleTitle";
    public static final String ARTICLE_COLLECT = "articleCollect";
    public static final String ARTICLE_READlATER = "articleReadLater";
    public static final String ARTICLE_ID = "articleId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Intent intent = getIntent();
//        String articleUrl = intent.getStringExtra(ARTICLE_URL);
//        String articleTitle = intent.getStringExtra(ARTICLE_TITLE);
//        boolean collect = intent.getBooleanExtra(ARTICLE_COLLECT, false);
//        boolean readLater = intent.getBooleanExtra(ARTICLE_READlATER, false);
//        int id = intent.getIntExtra(ARTICLE_ID, -1);
        ArticleDetailData detailData = (ArticleDetailData) intent.getSerializableExtra("data");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        //将fragment放入activity中
        if (fragment == null) {
            fragment = ArticleDetailFragment.newInstance(detailData);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }
}
