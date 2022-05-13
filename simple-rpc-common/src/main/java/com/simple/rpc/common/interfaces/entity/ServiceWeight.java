package com.simple.rpc.common.interfaces.entity;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 服务权重
 *
 * @author: WuChengXing
 * @create: 2022-05-12 19:24
 **/
public class ServiceWeight {

    public String url;
    public Integer weight;
    public Integer curWeight;

    public ServiceWeight(String url, Integer weight, Integer curWeight) {
        this.url = url;
        this.weight = weight;
        this.curWeight = curWeight;
    }

    public ServiceWeight() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getCurWeight() {
        return curWeight;
    }

    public void setCurWeight(Integer curWeight) {
        this.curWeight = curWeight;
    }
}
