package com.simple.agent.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 项目: class-byte-code
 * <p>
 * 功能描述: 参数处理工具
 *
 * @author: WuChengXing
 * @create: 2022-06-09 23:56
 **/
public class AgentParamUtil {

    /**
     * 处理参数以;组成的字符串
     *
     * @param param
     * @return
     */
    public static List<String> dealParam(String param) {
        if (Objects.isNull(param) || "".equals(param)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(param.split(";")));
    }
}
