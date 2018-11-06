package me.jessyan.armscomponent.commonservice.zhou.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhou on 2018/11/5.
 */
@Entity
public class User {
    private String username;
    private String password;
    private String email;
    @Generated(hash = 1172673984)
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
