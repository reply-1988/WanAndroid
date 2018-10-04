package reply_1988.wanandroid.login;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import reply_1988.wanandroid.R;


public class LoginActivity extends AppCompatActivity {

    public static final String IS_LOGOUT = "IS_LOGOUT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Boolean isRegister = getIntent().getBooleanExtra(IS_LOGOUT, false);

        FragmentManager fm = getSupportFragmentManager();
        LoginFragment fragment = (LoginFragment) fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = LoginFragment.getInstance(isRegister);
            fm.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }
        new LoginPresenter(fragment);
    }
}

