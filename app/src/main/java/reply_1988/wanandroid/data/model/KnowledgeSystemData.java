package reply_1988.wanandroid.data.model;

import java.util.List;

public class KnowledgeSystemData {


    /**
     * data ::[],"Id"
     * errorMsg :
     */

    private int errorCode;
    private String errorMsg;
    private List<KSListData> data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<KSListData> getData() {
        return data;
    }

    public void setData(List<KSListData> data) {
        this.data = data;
    }

}
