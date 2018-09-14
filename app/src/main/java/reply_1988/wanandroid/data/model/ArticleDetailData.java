package reply_1988.wanandroid.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class ArticleDetailData extends RealmObject implements Serializable{

    /**
     * apkLink :
     * author : Jetictors
     * chapterId : 232
     * chapterName : 入门及知识点
     * collect : false
     * courseId : 13
     * desc :
     * envelopePic :
     * fresh : false
     * id : 3226
     * link : http://www.cnblogs.com/Jetictors/tag/Kotlin/
     * niceDate : 2018-08-06
     * origin :
     * projectLink :
     * publishTime : 1533522956000
     * superChapterId : 232
     * superChapterName : Kotlin
     * tags : []
     * title : Kotlin 系列文章
     * type : 0
     * userId : -1
     * visible : 1
     * zan : 0
     */
    @Ignore
    private String apkLink;

    private String author;

    private int chapterId;

    private String chapterName;

    private boolean collect;

    private int courseId;

    private String desc;

    private String envelopePic;

    private boolean fresh;

    @PrimaryKey
    private int id;

    private String link;

    private String niceDate;

    @Ignore
    private String origin;

    private String projectLink;

    private long publishTime;

    private int superChapterId;

    private String superChapterName;

    private String title;

    private boolean readLater = false;

    @Ignore
    private int type;

    @Ignore
    private int userId;

    @Ignore
    private int visible;

    @Ignore
    private int zan;


    private String readLaterData;

    public String getApkLink() {
        return apkLink;
    }

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getSuperChapterId() {
        return superChapterId;
    }

    public void setSuperChapterId(int superChapterId) {
        this.superChapterId = superChapterId;
    }

    public String getSuperChapterName() {
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public boolean isReadLater() {
        return readLater;
    }

    public void setReadLater(boolean readLater) {
        this.readLater = readLater;
    }

    public String getReadLaterData() {
        return readLaterData;
    }

    public void setReadLaterData(String readLaterData) {
        this.readLaterData = readLaterData;
    }
}
