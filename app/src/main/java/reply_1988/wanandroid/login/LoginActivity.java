package reply_1988.wanandroid.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import reply_1988.wanandroid.MainActivity;
import reply_1988.wanandroid.R;
import reply_1988.wanandroid.data.engine.LoginEngine;
import reply_1988.wanandroid.data.model.LoginDetailData;
import reply_1988.wanandroid.data.source.local.LoginLocalSource;
import reply_1988.wanandroid.data.source.remote.LoginRemoteSource;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginContract.View{

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    private LoginContract.Presenter mLoginPresenter;
    private LoginRemoteSource mLoginRemoteSource;
    private LoginLocalSource mLoginLocalSource;

    private boolean mSuccess = false;
    private int userID;
    private String username;
    private String password;
    private Boolean skipLoginPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        mLoginRemoteSource = LoginRemoteSource.getInstance();
        mLoginLocalSource = LoginLocalSource.getInstance();
        setPresenter(new LoginPresenter(this, new LoginEngine(mLoginRemoteSource, mLoginLocalSource)));

        setContentView(R.layout.activity_login);
//        setupActionBar();

        resumeUserMsg();

        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        //将保存的用户名以及密码自动填写到对应的view上
        resumeUserMsg();
        mUsernameView.setText(username);
        mPasswordView.setText(password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isEmailValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_email));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mLoginPresenter.login(username, password, "login");

        }
    }

    private boolean isEmailValid(String username) {

        return true;
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 4;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mLoginPresenter = presenter;
    }

    @Override
    public void initView(View view) {

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    public void showProgress(final Boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    public void showLoginError(String errorMsg) {

        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    //将用户帐号密码保存到SharedPreferences中
    @Override
    public void saveUserMsg(LoginDetailData detailData) {

        userID = detailData.getId();
        username = detailData.getUsername();
        password = detailData.getPassword();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putInt("userID", userID).apply();
        sp.edit().putString("username", username).apply();
        sp.edit().putString("password", password).apply();
        sp.edit().putBoolean("skipLoginPage", true).apply();

    }

    //从SharedPreferences中获取保存的数据
    @Override
    public void resumeUserMsg() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        userID = sp.getInt("userID", 0);
        username = sp.getString("username", "");
        password = sp.getString("password", "");
        skipLoginPage = sp.getBoolean("skipLoginPage",false);

    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public  void getRequestResult(Boolean success) {
        mSuccess = success;
    }


}

