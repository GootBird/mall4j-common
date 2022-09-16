package com.xixi.mall.common.core.exception;

import lombok.Getter;

@Getter
public class ProjectException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProjectException(String msg) {
        super(msg);
    }

    public ProjectException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
