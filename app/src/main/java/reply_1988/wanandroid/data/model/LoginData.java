package reply_1988.wanandroid.data.model;

import java.util.List;

public class LoginData {

    /**
     * data :
     * errorCode : 0
     * errorMsg :
     */

    private LoginDetailData data;
    private int errorCode;
    private String errorMsg;

    public LoginDetailData getData() {
        return data;
    }

    public void setData(LoginDetailData data) {
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
