package reply_1988.wanandroid.data.model;

import java.util.List;

public class HotKeyData {


    /**
     * data : [{"id":6,"link":"","name":"面试","order":1,"visible":1},{"id":9,"link":"","name":"Studio3","order":1,"visible":1},{"id":5,"link":"","name":"动画","order":2,"visible":1},{"id":1,"link":"","name":"自定义View","order":3,"visible":1},{"id":2,"link":"","name":"性能优化 速度","order":4,"visible":1},{"id":3,"link":"","name":"gradle","order":5,"visible":1}]
     * errorCode : 0
     * errorMsg :
     */

    private int errorCode;

    private String errorMsg;

    private List<HotKeyDetailData> data;

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

    public List<HotKeyDetailData> getData() {
        return data;
    }

    public void setData(List<HotKeyDetailData> data) {
        this.data = data;
    }


}
