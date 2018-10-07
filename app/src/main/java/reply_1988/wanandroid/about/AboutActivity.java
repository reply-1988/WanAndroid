package reply_1988.wanandroid.about;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import reply_1988.wanandroid.R;
import reply_1988.wanandroid.search.SearchFragment;

public class AboutActivity extends AppCompatActivity {

    private AboutFragment mAboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("关于");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            mAboutFragment = (AboutFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, AboutFragment.class.getSimpleName());
        } else {
            mAboutFragment = new AboutFragment();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, mAboutFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAboutFragment.isAdded()) {
            getSupportFragmentManager()
                    .putFragment(outState, AboutFragment.class.getSimpleName(), mAboutFragment);
        }
    }
}
