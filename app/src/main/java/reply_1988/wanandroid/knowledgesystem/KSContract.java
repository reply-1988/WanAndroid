package reply_1988.wanandroid.knowledgesystem;

import reply_1988.wanandroid.BasePresenter;
import reply_1988.wanandroid.BaseView;
import reply_1988.wanandroid.data.model.KnowledgeSystemData;

public interface KSContract {

    interface View extends BaseView<Presenter>{
        void setAdapter(KnowledgeSystemData knowledgeSystemData);
    }

    interface Presenter extends BasePresenter{
        void getKSData();
    }
}
