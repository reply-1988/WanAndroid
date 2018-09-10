package reply_1988.wanandroid.data.model;

public class SearchData {

    private SearchListData data;
    private int errorCode;
    private String errorMsg;

    public SearchListData getData() {
        return data;
    }

    public void setData(SearchListData data) {
        this.data = data;
    }

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
}
