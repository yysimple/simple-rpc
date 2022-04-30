package com.simple.rpc.spring.exception;

import org.springframework.beans.BeansException;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-29 17:11
 **/
public class BeanNotFoundException extends BeansException {

    public BeanNotFoundException(String message) {
        super(message);
    }
}
