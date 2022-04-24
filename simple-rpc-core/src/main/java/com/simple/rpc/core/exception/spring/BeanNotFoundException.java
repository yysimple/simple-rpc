package com.simple.rpc.core.exception.spring;

import org.springframework.beans.BeansException;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-24 23:47
 **/
public class BeanNotFoundException extends BeansException {

    public BeanNotFoundException(String message) {
        super(message);
    }
}
