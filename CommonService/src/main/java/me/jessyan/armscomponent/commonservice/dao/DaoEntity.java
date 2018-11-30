package me.jessyan.armscomponent.commonservice.dao;

import com.google.gson.annotations.Expose;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhou on 2018/11/28.
 */
@Entity
public class DaoEntity {
    @Id
    @Expose
    private String id;

    @Expose private String url;
    @Expose private String path;
    @Expose private String pkg;
    @Expose private String action;
    @Expose private String isAct;
    @Expose private String createTime;
    @Expose private String isNet;
    @Generated(hash = 1230264649)
    public DaoEntity(String id, String url, String path, String pkg, String action,
            String isAct, String createTime, String isNet) {
        this.id = id;
        this.url = url;
        this.path = path;
        this.pkg = pkg;
        this.action = action;
        this.isAct = isAct;
        this.createTime = createTime;
        this.isNet = isNet;
    }
    @Generated(hash = 1100100161)
    public DaoEntity() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getPkg() {
        return this.pkg;
    }
    public void setPkg(String pkg) {
        this.pkg = pkg;
    }
    public String getAction() {
        return this.action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public String getIsAct() {
        return this.isAct;
    }
    public void setIsAct(String isAct) {
        this.isAct = isAct;
    }
    public String getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getIsNet() {
        return this.isNet;
    }
    public void setIsNet(String isNet) {
        this.isNet = isNet;
    }
}
