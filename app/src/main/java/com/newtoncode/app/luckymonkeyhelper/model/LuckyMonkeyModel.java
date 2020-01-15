package com.newtoncode.app.luckymonkeyhelper.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jeanboy on 2016/12/9.
 */
@Entity
public class LuckyMonkeyModel {

    @Id(autoincrement = true)
    private Long id;
    private String userNick;
    private Integer money;
    private Long createTime;
    @Generated(hash = 911673099)
    public LuckyMonkeyModel(Long id, String userNick, Integer money,
            Long createTime) {
        this.id = id;
        this.userNick = userNick;
        this.money = money;
        this.createTime = createTime;
    }
    @Generated(hash = 2026345509)
    public LuckyMonkeyModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserNick() {
        return this.userNick;
    }
    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }
    public Integer getMoney() {
        return this.money;
    }
    public void setMoney(Integer money) {
        this.money = money;
    }
    public Long getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

   

}
