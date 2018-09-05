package reply_1988.wanandroid.data.model;

public class FavoriteData {


    /**
     * data : null
     * errorCode : 0
     * errorMsg :
     */

    private FavoriteListData data;
    private int errorCode;
    private String errorMsg;

    public FavoriteListData getData() {
        return data;
    }

    public void setData(FavoriteListData data) {
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
