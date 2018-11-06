package me.jessyan.armscomponent.commonservice.launcher.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/8/18.
 */
@Entity
public class Element {
    private String tag;

    private String pkg;
    private String intent;
    private String way;
    private String url;
    private String icon;
    private String type;
    private String changetime;
    private String resurl;

    private int colSpan;
    private int rowSpan;
    @Generated(hash = 1012897949)
    public Element(String tag, String pkg, String intent, String way, String url,
            String icon, String type, String changetime, String resurl, int colSpan,
            int rowSpan) {
        this.tag = tag;
        this.pkg = pkg;
        this.intent = intent;
        this.way = way;
        this.url = url;
        this.icon = icon;
        this.type = type;
        this.changetime = changetime;
        this.resurl = resurl;
        this.colSpan = colSpan;
        this.rowSpan = rowSpan;
    }
    @Generated(hash = 386918278)
    public Element() {
    }
    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getPkg() {
        return this.pkg;
    }
    public void setPkg(String pkg) {
        this.pkg = pkg;
    }
    public String getIntent() {
        return this.intent;
    }
    public void setIntent(String intent) {
        this.intent = intent;
    }
    public String getWay() {
        return this.way;
    }
    public void setWay(String way) {
        this.way = way;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getChangetime() {
        return this.changetime;
    }
    public void setChangetime(String changetime) {
        this.changetime = changetime;
    }
    public String getResurl() {
        return this.resurl;
    }
    public void setResurl(String resurl) {
        this.resurl = resurl;
    }
    public int getColSpan() {
        return this.colSpan;
    }
    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }
    public int getRowSpan() {
        return this.rowSpan;
    }
    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }


}
