package reply_1988.wanandroid.login;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import reply_1988.wanandroid.R;


public class LoginActivity extends AppCompatActivity {

    public static final String IS_LOGOUT = "IS_LOGOUT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //设置状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        Boolean isRegister = getIntent().getBooleanExtra(IS_LOGOUT, false);

        FragmentManager fm = getSupportFragmentManager();
        LoginFragment fragment = (LoginFragment) fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = LoginFragment.getInstance(isRegister);
            fm.beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
        }
        new LoginPresenter(fragment);
    }
}

