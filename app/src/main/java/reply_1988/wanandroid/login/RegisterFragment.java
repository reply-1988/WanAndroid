package reply_1988.wanandroid.login;
/**
 * @Author:reply
 * @Time:2018/9/23 15:18
 * @Description:This is RegisterFragment
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import reply_1988.wanandroid.MainActivity;
import reply_1988.wanandroid.R;
import reply_1988.wanandroid.data.model.LoginDetailData;

public class RegisterFragment extends Fragment implements LoginContract.View, View.OnClickListener{

    private TextInputEditText mUsernameView;
    private TextInputEditText mPasswordView;
    private TextInputEditText mRePasswordView;

    private View mProgressView;
    private View mRegisterFormView;
    private Button mRegisterButton;
    private TextView mHasAccount;

    private TextInputLayout mUsernameLayout;
    private TextInputLayout mPasswordLayout;
    private TextInputLayout mRePasswordLayout;

    private LoginContract.Presenter mRegisterPresenter;

    private boolean mSuccess = false;
    private int userID;
    private String username;
    private String password;
    private boolean rememberPassword;
    private boolean directLogin;
    private boolean firstLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        setPresenter(new LoginPresenter(this));
        initView(v);
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        mRegisterPresenter.unSubscribe();
    }

    public static RegisterFragment getInstance() {

        return new RegisterFragment();
    }

    @Override
    public void showProgress(final Boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
     * @param msg 显示相关信息
     * 显示信息
     */
    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param detailData 服务器返回的注册数据
     * 保存数据到sharedPreference中
     */
    @Override
    public void saveUserMsg(LoginDetailData detailData) {
        userID = detailData.getId();
        username = detailData.getUsername();
        password = detailData.getPassword();
        rememberPassword = true;
        directLogin = true;
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
     * 重新回到登陆界面，并直接登陆
     */
    @Override
    public void startMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void getRequestResult(Boolean success) {

    }

    @Override
    public void resumeUserMsg() {

    }

    @Override
    public void changeLoginState(Boolean isLogin) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (isLogin) {
            sp.edit().putBoolean("isLogin", true).apply();
        } else {
            sp.edit().putBoolean("isLogin", false).apply();
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mRegisterPresenter = presenter;
    }

    /**
     * @param view
     */
    @Override
    public void initView(View view) {
        mUsernameView = view.findViewById(R.id.username);
        mPasswordView = view.findViewById(R.id.password);
        mRePasswordView = view.findViewById(R.id.re_password);
        mRegisterButton = view.findViewById(R.id.register_button);
        mRegisterFormView = view.findViewById(R.id.register_form);
        mProgressView = view.findViewById(R.id.register_progress);
        mUsernameLayout = view.findViewById(R.id.layout_user);
        mPasswordLayout = view.findViewById(R.id.layout_password);
        mRePasswordLayout = view.findViewById(R.id.layout_rePassword);
        mHasAccount = view.findViewById(R.id.login);

        mRegisterButton.setOnClickListener(this);
        mHasAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                attemptRegister();
                break;
            case R.id.login:
                login();
                break;
            default:
                break;
        }
    }

    /**
     * 尝试进行注册，并对输入的信息是否规范进行验证
     */
    private void attemptRegister() {
        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the register attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String rePassword = mRePasswordView.getText().toString();

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
            mUsernameLayout.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUsernameLayout.setError(getString(R.string.error_invalid_user));
            focusView = mUsernameView;
            cancel = true;
        }

        if (!password.equals(rePassword)) {
            mRePasswordLayout.setError(getString(R.string.password_different));
            focusView = mRePasswordView;
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
            mRegisterPresenter.register(username, password);
        }
    }

    private boolean isUsernameValid(String username) {
        return checkLength(username) >= 6;
    }

    private boolean isPasswordValid(String password) {
        return checkLength(password) >=6;
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

    private void login() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.fragment_container, LoginFragment.getInstance(false))
                .commit();
    }
}
