package com.simple.rpc.common.interfaces.entity;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 负载相关的配置类
 *
 * @author: WuChengXing
 * @create: 2022-05-11 20:37
 **/
public class LoadBalanceParam {
    /**
     * 权重数值
     */
    private Integer weights;

    public Integer getWeights() {
        return weights;
    }

    public void setWeights(Integer weights) {
        this.weights = weights;
    }
}
