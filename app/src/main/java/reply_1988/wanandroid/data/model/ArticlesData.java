package reply_1988.wanandroid.data.model;

public class ArticlesData {

    //使用GsonFormat插件一键将其处理
    //因为Realm的实体类中不能够镶嵌实体类，所以将其分解为ArticlesDetailData、ArticleData、ArticleDetailData、ArticlesDetailTagData
    //search返回的articles数据类型与此一致，所以不重复创建searchArticles了

    private ArticlesDetailData data;

    private int errorCode;

    private String errorMsg;

    public ArticlesDetailData getData() {
        return data;
    }

    public void setData(ArticlesDetailData data) {
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
