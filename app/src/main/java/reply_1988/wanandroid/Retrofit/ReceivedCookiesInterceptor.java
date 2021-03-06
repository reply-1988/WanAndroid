package reply_1988.wanandroid.Retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {

    private Context mContext;

    public ReceivedCookiesInterceptor(Context context) {

        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(mContext).getStringSet("PREF_COOKIES", new HashSet<String>());

            cookies.addAll(originalResponse.headers("Set-Cookie"));

            SharedPreferences.Editor memes = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
            memes.putStringSet("PREF_COOKIES", cookies).apply();
            memes.commit();
        }

        return originalResponse;
    }
}
