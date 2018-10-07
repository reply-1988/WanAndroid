package reply_1988.wanandroid.about;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import reply_1988.wanandroid.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AboutFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {

    private Preference mVersionPreference;
    private Preference mInroductionPreference;
    private Preference mLiZhaoTaiLangPreference;
    private Preference mHongYang;
    private Preference mSourceCodePreference;
    private Preference mApisPreference;
    private Preference mContactPreference;

    public static AboutFragment getInstance() {

        return new AboutFragment();

    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        getPreferenceManager().setSharedPreferencesName("mysetting");
        addPreferencesFromResource(R.xml.preferences);
        initPrefs();
    }

    private void initPrefs() {
        mVersionPreference = findPreference("version");
        mInroductionPreference = findPreference("Introduction");

        mLiZhaoTaiLangPreference = findPreference("thanks1");
        mLiZhaoTaiLangPreference.setOnPreferenceClickListener(this);
        mHongYang = findPreference("thanks2");
        mHongYang.setOnPreferenceClickListener(this);
        mSourceCodePreference = findPreference("Source code");
        mSourceCodePreference.setOnPreferenceClickListener(this);
        mApisPreference = findPreference("api");
        mApisPreference.setOnPreferenceClickListener(this);
        mContactPreference = findPreference("contact");
        mContactPreference.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()){
            case "thanks1":
            case "thanks2":
            case "Source code":
            case "api":
                openUrl(preference.getSummary().toString());
                break;
            case "contact":
                sendEmail(preference.getSummary().toString());
            default:
                break;
        }
        return true;
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "没有可以打开该链接的程序", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmail(String address) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(address));
            intent.putExtra(Intent.EXTRA_SUBJECT, "WanAndroid 建议");
        } catch (Exception e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }


    }
}
