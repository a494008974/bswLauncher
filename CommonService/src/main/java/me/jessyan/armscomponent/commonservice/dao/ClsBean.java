package me.jessyan.armscomponent.commonservice.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhou on 2018/11/29.
 */
@Entity
public class ClsBean {
    /**
     * title : 推荐
     * screen : 1
     * display : 1
     */
    @Id
    private String screen;
    private String title;
    private String display;
    @Generated(hash = 1156449202)
    public ClsBean(String screen, String title, String display) {
        this.screen = screen;
        this.title = title;
        this.display = display;
    }
    @Generated(hash = 1176202357)
    public ClsBean() {
    }
    public String getScreen() {
        return this.screen;
    }
    public void setScreen(String screen) {
        this.screen = screen;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDisplay() {
        return this.display;
    }
    public void setDisplay(String display) {
        this.display = display;
    }

}
