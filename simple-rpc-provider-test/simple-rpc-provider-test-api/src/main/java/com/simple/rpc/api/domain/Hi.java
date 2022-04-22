package com.simple.rpc.api.domain;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-22 09:42
 **/
public class Hi {

    private String userName;
    private String sayMsg;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSayMsg() {
        return sayMsg;
    }

    public void setSayMsg(String sayMsg) {
        this.sayMsg = sayMsg;
    }
}
