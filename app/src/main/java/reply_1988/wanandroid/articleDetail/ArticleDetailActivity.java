package reply_1988.wanandroid.articleDetail;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import reply_1988.wanandroid.R;
import reply_1988.wanandroid.timeline.TimeLineFragment;

public class ArticleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Intent intent = getIntent();
        String articleUrl = intent.getStringExtra(TimeLineFragment.ARTICLE_URL);


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        //将fragment放入activity中
        if (fragment == null) {
            fragment = ArticleDetailFragment.newInstance(articleUrl, "1");
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
