package com.simple.test;

import com.simple.test.hello.HelloWorld;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-06-11 01:44
 **/
public class AgentMainPluginsTest {

    public static void main(String[] args) {
        AgentMainPluginsTest test = new AgentMainPluginsTest();
        test.http_lt1();
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.helloWorld();

    }


    public void http_lt1() {
        System.out.println("测试结果：hi1");
        http_lt2();
    }

    public void http_lt2() {
        System.out.println("测试结果：hi2");
        http_lt3();
        hashCode();
        toString();
    }

    public void http_lt3() {
        System.out.println("测试结果：hi3");
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "AgentMainTraceTest{}";
    }

    public String helloWorld(){
        return "111";
    }
}
