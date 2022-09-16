package com.xixi.mall.common.core.utils;


import com.xixi.mall.common.core.enums.ResponseEnum;
import com.xixi.mall.common.core.exception.ProjectException;

import java.util.function.Supplier;

/**
 * web 异常抛出工具类
 */
public class ThrowUtils {

    public static ProjectException getException(String code, String msg, Object... args) {
        return new ProjectException(code + "_" + (args.length > 0
                ? String.format(msg, args)
                : msg));
    }

    public static ProjectException getException(ResponseEnum errMsgEnum, Object... args) {
        return getException(errMsgEnum.value(), errMsgEnum.getMsg(), args);
    }

    public static ProjectException getException(String msg, Object... args) {
        return getException(ResponseEnum.SHOW_FAIL.value(), msg, args);
    }

    public static void throwErr(ResponseEnum errMsgEnum, Object... args) {
        throw getException(errMsgEnum, args);
    }

    public static Supplier<ProjectException> getSupErr(ResponseEnum errMsgEnum, Object... args) {
        return () -> getException(errMsgEnum, args);
    }

    public static Supplier<ProjectException> getSupErr(String msg, Object... args) {
        return () -> getException(msg, args);
    }


    public static void throwErr() {
        throw getException(ResponseEnum.SHOW_FAIL);
    }

    public static void throwErr(String msg, Object... args) {
        throw getException(msg, args);
    }


    public static void checkState(boolean bol, ResponseEnum errMsgEnum) {
        if (!bol) {
            throwErr(errMsgEnum);
        }
    }

    public static void checkState(boolean bol, String errMsg, Object... args) {
        if (bol) throwErr(errMsg, args);
    }
}
