package com.simple.statistic.entity.response;

import lombok.Data;

/**
 * @author chengxingwu
 */
@Data
public class AirResponse<T> {
    protected Boolean status = true;
    protected Integer errorCode;
    protected String errorMessage;
    protected T data;
}