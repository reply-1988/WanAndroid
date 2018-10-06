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
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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
public class LoginFragment extends Fragment implements LoginContract.View, View.OnClickListener{

    private TextInputEditText mUsernameView;
    private TextInputEditText mPasswordView;
    private TextInputLayout mUsernameInputLayout;
    private TextInputLayout mPasswordLayout;
    private View mProgressView;
    private View mLoginFormView;
    private CheckBox mCheckBoxPassword;
    private CheckBox mCheckBoxLogin;
    private Button mLoginButton;
    private TextView mRegisterView;


    private LoginContract.Presenter mLoginPresenter;

    private boolean mSuccess = false;
    private int userID;
    private String username;
    private String password;
    private Boolean directLogin;
    private Boolean rememberPassword;
    private Boolean firstLogin;
    private Boolean isLogin;
    private Boolean isLogout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isLogout = getArguments().getBoolean(LoginActivity.IS_LOGOUT, false);
        }
        if (isLogout) {
            mLoginPresenter.clearCache(getContext());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        resumeUserMsg();
        initView(v);
        if (isLogin) {
            startMainActivity();
        }
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        mLoginPresenter.unSubscribe();
    }

    public static LoginFragment getInstance(Boolean isLogout) {

        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putBoolean(LoginActivity.IS_LOGOUT, isLogout);
        fragment.setArguments(args);
        return fragment;
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
        mLoginButton = v.findViewById(R.id.sign_in_button);
        mLoginButton.setOnClickListener(this);

        mRegisterView = v.findViewById(R.id.register);
        mRegisterView.setOnClickListener(this);
        mLoginFormView = v.findViewById(R.id.login_form);
        mProgressView = v.findViewById(R.id.register_progress);
        mUsernameInputLayout = v.findViewById(R.id.layout_user);
        mPasswordLayout = v.findViewById(R.id.layout_password);
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

        if (rememberPassword) {
            mPasswordView.setText(password);
        }
        mUsernameView.setText(username);
        if (directLogin) {
            attemptLogin();
        }
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

    /**
     * @param errorMsg 错误信息
     * 显示登录错误
     */
    @Override
    public void showMessage(String errorMsg) {
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
        firstLogin = false;

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        sp.edit().putInt("userID", userID).apply();
        sp.edit().putString("username", username).apply();
        sp.edit().putString("password", password).apply();
        sp.edit().putBoolean("directLogin", directLogin).apply();
        sp.edit().putBoolean("rememberPassword", rememberPassword).apply();
        sp.edit().putBoolean("firstLogin", firstLogin).apply();
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
        firstLogin = sp.getBoolean("firstLogin", true);
        isLogin = sp.getBoolean("isLogin", false);
    }

    /**
     * @param isLogin 是否登陆成功
     *  根据是否登陆状态在shared Preference中存储对应的值从而在其他Activity中使用
     */
    @Override
    public void changeLoginState(Boolean isLogin) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (isLogin) {
            sp.edit().putBoolean("isLogin", true).apply();
        } else {
            sp.edit().putBoolean("isLogin", false).apply();
        }
    }

    /**
     * 转到主页面
     */
    @Override
    public void startMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
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
            mPasswordLayout.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username address.

        if (TextUtils.isEmpty(username)) {
            mUsernameInputLayout.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUsernameInputLayout.setError(getString(R.string.error_invalid_user));
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
    private boolean isUsernameValid(String username) {
        return checkLength(username) > 5;
    }

    /**
     * @param password 输入的密码
     * @return  根据网站API变化而变化
     */
    private boolean isPasswordValid(String password) {
        return checkLength(password) > 5;
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static int checkLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                attemptLogin();
                break;
            case R.id.register:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, RegisterFragment.getInstance())
                        .commit();
                break;
            default:
                break;
        }
    }
}
