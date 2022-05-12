package com.simple.rpc.test.common.starter.spi.balance;

import cn.hutool.core.util.RandomUtil;
import com.simple.rpc.common.interfaces.entity.LoadBalanceParam;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: hash负载算法
 *
 * @author: WuChengXing
 * @create: 2022-05-12 23:41
 **/
public class HashRule extends AbstractLoadBalance {

    private static TreeMap<Integer, String> virtualNodes = new TreeMap();

    /**
     * 设置了800个虚拟节点
     */
    private static final int V_NODES = 800;

    void init(List<String> urls) {
        //初始化哈希环
        //真实节点 添加对应的虚拟节点
        for (String url : urls) {
            for (int i = 0; i < V_NODES; i++) {
                int hash = FNVHash(url + "_" + i);
                virtualNodes.put(hash, url);
            }
        }
    }


    @Override
    public String select(Map<String, LoadBalanceParam> urls) {
        ArrayList<String> strings = new ArrayList<>(urls.keySet());
        init(strings);
        return getServerPath(RandomUtil.randomEle(strings));
    }

    public static String getServerPath(String hashInfo) {
        //这里取请求信息
        int hash = FNVHash(hashInfo);

        //大于该Hash值的红黑树子树
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);
        if (subMap == null) {
            return virtualNodes.get(virtualNodes.firstKey());
        }
        //获取该树的第一个元素，即最小元素
        Integer nodeKey = subMap.firstKey();

        //返回虚拟节点的名称
        return virtualNodes.get(nodeKey);
    }

    /**
     * 32位的 Fowler-Noll-Vo
     * 哈希算法 https://en.wikipedia.org/wiki/Fowler–Noll–Vo_hash_function
     *
     * @param key
     * @return
     */
    private static int FNVHash(String key) {
        final int p = 16777619;
        Long hash = 2166136261L;
        for (int idx = 0, num = key.length(); idx < num; ++idx) {
            hash = (hash ^ key.charAt(idx)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash.intValue();
    }

    public static void main(String[] args) {
        Map<String, LoadBalanceParam> urls = new ConcurrentHashMap<>(4);
        LoadBalanceParam param1 = new LoadBalanceParam();
        param1.setWeights(20);
        LoadBalanceParam param2 = new LoadBalanceParam();
        param2.setWeights(30);
        LoadBalanceParam param3 = new LoadBalanceParam();
        param3.setWeights(50);
        urls.put("127.0.0.1_8081", param1);
        urls.put("127.0.0.1_8082", param2);
        urls.put("127.0.0.1_8083", param3);
        for (int i = 0; i < 10; i++) {
            HashRule rule = new HashRule();
            System.out.println(rule.select(urls));
        }
    }
}
