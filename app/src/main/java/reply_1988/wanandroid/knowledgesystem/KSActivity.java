package reply_1988.wanandroid.knowledgesystem;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import reply_1988.wanandroid.R;

public class KSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fm = getSupportFragmentManager();
        KSFragment ksFragment = (KSFragment) fm.findFragmentById(R.id.fragment_container);

        if (ksFragment == null) {
            ksFragment = KSFragment.newInstance("1","1");
            fm.beginTransaction()
                    .replace(R.id.fragment_container, ksFragment)
                    .commit();
        }
        new KSPresenter(ksFragment);
    }
}
