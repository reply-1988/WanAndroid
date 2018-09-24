package reply_1988.wanandroid.data.model;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LoginDetailData extends RealmObject {

    /**
     * collectIds : [3290]
     * email :
     * icon :
     * id : 8575
     * password :
     * token :
     * type : 0
     * username :
     */

    private String email;
    private String icon;
    @PrimaryKey
    private int id;
    private String password;
    private String token;
    private int type;
    private String username;
    private RealmList<Integer> collectIds;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(RealmList<Integer> collectIds) {
        this.collectIds = collectIds;
    }
}

