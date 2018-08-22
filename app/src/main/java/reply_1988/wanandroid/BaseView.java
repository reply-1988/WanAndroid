package reply_1988.wanandroid;

import android.view.View;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void initView(View view);
}
