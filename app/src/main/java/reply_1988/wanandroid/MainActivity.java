package reply_1988.wanandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import reply_1988.wanandroid.favorite.FavoriteFragment;
import reply_1988.wanandroid.knowledgesystem.KSActivity;
import reply_1988.wanandroid.login.LoginActivity;
import reply_1988.wanandroid.readLater.ReadLaterFragment;
import reply_1988.wanandroid.search.SearchActivity;
import reply_1988.wanandroid.timeline.TimeLineFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Toolbar mToolbar;
    public DrawerLayout mDrawerLayout;
    public NavigationView mNavigationView;
    private SearchView mSearchView;
    public TabLayout mTabLayout;
    public ViewPager mViewPager;
    private Fragment mFragment;

    private SharedPreferences mPreferences;
    private ImageView mHeadImageView;
    private TextView mHeadTextView;
    private TextView mImageTextView;
    private View mHeadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //获取保存登录信息的SharedPreferences对象
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (mPreferences.getInt("userID", -1) != -1) {
            String username = mPreferences.getString("username", "登录");
            mHeadTextView.setText(username);
            mImageTextView.setText(username.substring(0, 1).toUpperCase());
        } else {
            mHeadTextView.setText(null);
            mImageTextView.setText("登陆");
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSearchView != null) {
            mSearchView.clearFocus();
        }
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) searchItem.getActionView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra(SearchActivity.ARG_SEARCH_CONTENT, query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.menu_search) {

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.ks:
                Intent intent = new Intent(this, KSActivity.class);
                startActivity(intent);
                break;
            case R.id.about:
                Intent intent1 = new Intent(this, AboutActivity.class);
                startActivity(intent1);
                break;
            case R.id.log_out:
                Intent intent2 = new Intent(this, LoginActivity.class);
                intent2.putExtra(LoginActivity.IS_LOGOUT, true);
                startActivity(intent2);
                break;
            default:
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    mFragment = TimeLineFragment.newInstance(1);
                    break;
                case 1:
                    mFragment = FavoriteFragment.newInstance(1);
                    break;
                case 2:
                    mFragment = ReadLaterFragment.newInstance(1);
                    break;
                default:
                    break;
            }
            return mFragment;

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            String returnString = null;
            switch (position) {
                case 0:
                    returnString = "时间线";
                    break;
                case 1:
                    returnString = "收藏";
                    break;
                case 2:
                    returnString = "稍后阅读";
                    break;
                default:
                    break;
            }
            return returnString;
        }
    }

    private View getTabView(int position) {

        View view = LayoutInflater.from(this).inflate(R.layout.tab_view, null);
        TextView textView = view.findViewById(R.id.textView2);
        ImageView imageView = view.findViewById(R.id.imageView2);
        switch (position) {
            case 0:
                textView.setText("时间线");
                imageView.setImageResource(R.drawable.ic_inf);
                break;
            case 1:
                textView.setText("收藏");
                imageView.setImageResource(R.drawable.ic_collect);
                break;
            case 2:
                textView.setText("稍后阅读");
                imageView.setImageResource(R.drawable.ic_readlater);
                break;
            default:
                break;
        }
        return view;
    }

    private void initView(){
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mViewPager = findViewById(R.id.view_pager);
        Log.d("测试viewpager", String.valueOf(mViewPager.getId()));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);

        //设置缓存的视图数量为2
        mTabLayout.setupWithViewPager(mViewPager);
        //为tab设置自定义view
        mTabLayout.getTabAt(0).setCustomView(getTabView(0));
        mTabLayout.getTabAt(1).setCustomView(getTabView(1));
        mTabLayout.getTabAt(2).setCustomView(getTabView(2));

        //获取navigationView的headView
        mHeadView = mNavigationView.getHeaderView(0);
        //获取headView中的图片
        mHeadImageView = mHeadView.findViewById(R.id.imageView);
        mHeadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        //获取headView中的文字
        mHeadTextView = mHeadView.findViewById(R.id.textView);
        mImageTextView = mHeadView.findViewById(R.id.imageText);
    }

}
