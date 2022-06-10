package com.simple.agent.trace;

/**
 * 项目: class-byte-code
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-03 00:47
 **/
public class TrackContext {

    private static final ThreadLocal<String> trackLocal = new ThreadLocal<>();

    public static void clear(){
        trackLocal.remove();
    }

    public static String getTraceId(){
        return trackLocal.get();
    }

    public static void setTraceId(String traceId){
        trackLocal.set(traceId);
    }
}
