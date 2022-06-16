package com.simple.rpc.common.interfaces;

import com.simple.rpc.common.annotation.SimpleRpcSPI;
import com.simple.rpc.common.interfaces.entity.CollectData;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 数据收集接口
 *
 * @author: WuChengXing
 * @create: 2022-06-13 23:56
 **/
@SimpleRpcSPI
public interface DataCollection {

    /**
     * 数据收集
     *
     * @param collectData
     * @return
     */
    Boolean collect(CollectData collectData);
}
