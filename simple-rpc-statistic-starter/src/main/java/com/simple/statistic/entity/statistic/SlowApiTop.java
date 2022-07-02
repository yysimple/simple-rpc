package com.simple.statistic.entity.statistic;

import lombok.Data;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 慢接口出现次数top
 *
 * @author: WuChengXing
 * @create: 2022-07-02 10:07
 **/
@Data
public class SlowApiTop {

    private String entryClazzName;

    private Long slowNum;
}
