package reply_1988.wanandroid.Retrofit;

import android.util.Log;

import okhttp3.OkHttpClient;
import reply_1988.wanandroid.MyApplication;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//使用单例模式来获取一个

public class RetrofitClient {

    private static class RetrofitBuilder{

        private static OkHttpClient sOkHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieManger(MyApplication.getContext()))
                .build();

        private static Retrofit sRetrofit = new Retrofit.Builder()
                .baseUrl(Url.baseUrl)
                .client(sOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getInstance() {

        Log.d("测试", "获取retrofit");

        return RetrofitBuilder.sRetrofit;
    }

    private RetrofitClient() {
    }
}
