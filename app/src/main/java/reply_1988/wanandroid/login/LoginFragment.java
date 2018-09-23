package reply_1988.wanandroid.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import reply_1988.wanandroid.MainActivity;
import reply_1988.wanandroid.R;
import reply_1988.wanandroid.data.model.LoginDetailData;

/**
 * Author:reply
 * Time:2018/9/23 15:15
 * Description:This is LoginFragment
 */
public class LoginFragment extends Fragment implements LoginContract.View{

    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private CheckBox mCheckBoxPassword;
    private CheckBox mCheckBoxLogin;


    private LoginContract.Presenter mLoginPresenter;

    private boolean mSuccess = false;
    private int userID;
    private String username;
    private String password;
    private Boolean directLogin;
    private Boolean rememberPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        setPresenter(new LoginPresenter(this));
        initView(v);
        resumeUserMsg();
        return v;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mLoginPresenter = presenter;
    }

    @Override
    public void initView(View v) {
        mUsernameView = v.findViewById(R.id.username);
        mPasswordView = v.findViewById(R.id.password);
        mCheckBoxLogin = v.findViewById(R.id.direct_login);
        mCheckBoxPassword = v.findViewById(R.id.remember_password);
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
        resumeUserMsg();
        mUsernameView.setText(username);
        mPasswordView.setText(password);

        Button mEmailSignInButton = v.findViewById(R.id.sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mLoginFormView = v.findViewById(R.id.login_form);
        mProgressView = v.findViewById(R.id.login_progress);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    public void showProgress(final Boolean show) {

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

    @Override
    public void showLoginError(String errorMsg) {

        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 将用户帐号密码保存到SharedPreferences中
     * @param detailData 登录数据
     */
    @Override
    public void saveUserMsg(LoginDetailData detailData) {

        userID = detailData.getId();
        username = detailData.getUsername();
        password = detailData.getPassword();
        rememberPassword = mCheckBoxPassword.isChecked();
        directLogin = mCheckBoxLogin.isChecked();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        sp.edit().putInt("userID", userID).apply();
        sp.edit().putString("username", username).apply();
        sp.edit().putString("password", password).apply();
        sp.edit().putBoolean("directLogin", directLogin).apply();
        sp.edit().putBoolean("rememberPassword", rememberPassword).apply();
    }

    /**
     * 从SharedPreferences中获取保存的数据
     */
    @Override
    public void resumeUserMsg() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        userID = sp.getInt("userID", 0);
        username = sp.getString("username", "");
        password = sp.getString("password", "");
        directLogin = sp.getBoolean("directLogin",false);
        rememberPassword = sp.getBoolean("rememberPassword", false);
    }

    /**
     * 转到主页面
     */
    @Override
    public void startMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public  void getRequestResult(Boolean success) {
        mSuccess = success;
    }

    /**
     *尝试进行登录，并对输入的用户名密码规范进行检查
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

    /**
     * @param username 输入的用户名
     * @return 根据网站的API变化而变化
     */
    private boolean isEmailValid(String username) {
        return true;
    }

    /**
     * @param password 输入的密码
     * @return  根据网站API变化而变化
     */
    private boolean isPasswordValid(String password) {
        return true;
    }
}
