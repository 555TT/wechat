package com.whr.chat.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @author: wanghaoran1
 * @create: 2025-03-27
 */
@Data
@Builder
public class Result {

    private boolean flag;
    private String message;
}
