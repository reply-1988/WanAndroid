package reply_1988.wanandroid.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import reply_1988.wanandroid.R;

public class SearchActivity extends AppCompatActivity {

    private SearchFragment mSearchFragment;

    public static final String ARG_SEARCH_CONTENT = "SEARCH_CONTENT";
    public static final String ARG_KS_CID = "ARG_KS_CID";
    public static final String ARG_CATEGORY_CID = "ARG_CATEGORY_CID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        String searchContent = intent.getStringExtra(ARG_SEARCH_CONTENT);
        int ks_cid = intent.getIntExtra(ARG_KS_CID, -1);
        int cat_cid = intent.getIntExtra(ARG_CATEGORY_CID, -1);

        if (savedInstanceState != null) {
            mSearchFragment = (SearchFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, SearchFragment.class.getSimpleName());
        } else {
            mSearchFragment = SearchFragment.newInstance(searchContent, ks_cid, cat_cid);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, mSearchFragment)
                .commit();
        new SearchPresenter(mSearchFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mSearchFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, SearchFragment.class.getSimpleName(), mSearchFragment);
        }
    }
}
