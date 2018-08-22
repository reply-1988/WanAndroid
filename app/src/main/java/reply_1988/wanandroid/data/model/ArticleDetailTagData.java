package reply_1988.wanandroid.data.model;

import io.realm.RealmObject;

public class ArticleDetailTagData extends RealmObject{


    /**
     * name : 项目
     * url : /project/list/1?cid=400
     */

    private String name;

    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
