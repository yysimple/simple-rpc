package com.simple.rpc.test.common.starter.spi.collect;

import com.simple.rpc.common.interfaces.DataCollection;
import com.simple.rpc.common.interfaces.entity.CollectData;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-16 23:54
 **/
public class LogCollectData implements DataCollection {
    @Override
    public Boolean collect(CollectData collectData) {
        System.out.println("收集的数据：==> " + collectData);
        return null;
    }
}
