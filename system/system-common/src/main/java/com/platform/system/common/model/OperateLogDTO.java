package com.platform.system.common.model;

import lombok.Data;

import java.util.Date;

/**
 * @version: 1.0
 */
@Data
public class OperateLogDTO {

    private String idLog;
    private String module;
    private String author;
    private String method;
    private String exception;
    private Date callDate;
    private long interval;
}
