package me.jessyan.armscomponent.commonservice.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhou on 2018/11/29.
 */
@Entity
public class InfoBean {
    /**
     * title : 爱奇艺
     * way : 0
     * way_val : cibntv_yingshi://homei_v5?tabld=355
     * img_url : 2017-11/09/9bd8301618373d0d41cbf64ba2212035.png
     * effect_url :
     * tag : 101
     * is_lock : 1
     * is_hold : 1
     * update_time : 1530929224
     * url : 2017-09/29/0eb1538df661253be1e19dab2918d1c3
     * ico : 2017-09/29/fc61debc87aae82f638473235ac55b6f.png
     * pkg : com.gitvdemobsw.video
     * act : com.qiyi.video.home.HomeActivity
     * is_link : 0
     * link :
     * screen : 1
     */
    @Id
    private String tag;
    private String title;
    private String way;
    private String way_val;
    private String img_url;
    private String effect_url;
    private String is_lock;
    private String is_hold;
    private String update_time;
    private String url;
    private String ico;
    private String pkg;
    private String act;
    private String is_link;
    private String link;
    private String screen;
    @Generated(hash = 2054943191)
    public InfoBean(String tag, String title, String way, String way_val,
            String img_url, String effect_url, String is_lock, String is_hold,
            String update_time, String url, String ico, String pkg, String act,
            String is_link, String link, String screen) {
        this.tag = tag;
        this.title = title;
        this.way = way;
        this.way_val = way_val;
        this.img_url = img_url;
        this.effect_url = effect_url;
        this.is_lock = is_lock;
        this.is_hold = is_hold;
        this.update_time = update_time;
        this.url = url;
        this.ico = ico;
        this.pkg = pkg;
        this.act = act;
        this.is_link = is_link;
        this.link = link;
        this.screen = screen;
    }
    @Generated(hash = 134777477)
    public InfoBean() {
    }
    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getWay() {
        return this.way;
    }
    public void setWay(String way) {
        this.way = way;
    }
    public String getWay_val() {
        return this.way_val;
    }
    public void setWay_val(String way_val) {
        this.way_val = way_val;
    }
    public String getImg_url() {
        return this.img_url;
    }
    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
    public String getEffect_url() {
        return this.effect_url;
    }
    public void setEffect_url(String effect_url) {
        this.effect_url = effect_url;
    }
    public String getIs_lock() {
        return this.is_lock;
    }
    public void setIs_lock(String is_lock) {
        this.is_lock = is_lock;
    }
    public String getIs_hold() {
        return this.is_hold;
    }
    public void setIs_hold(String is_hold) {
        this.is_hold = is_hold;
    }
    public String getUpdate_time() {
        return this.update_time;
    }
    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getIco() {
        return this.ico;
    }
    public void setIco(String ico) {
        this.ico = ico;
    }
    public String getPkg() {
        return this.pkg;
    }
    public void setPkg(String pkg) {
        this.pkg = pkg;
    }
    public String getAct() {
        return this.act;
    }
    public void setAct(String act) {
        this.act = act;
    }
    public String getIs_link() {
        return this.is_link;
    }
    public void setIs_link(String is_link) {
        this.is_link = is_link;
    }
    public String getLink() {
        return this.link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getScreen() {
        return this.screen;
    }
    public void setScreen(String screen) {
        this.screen = screen;
    }


}
