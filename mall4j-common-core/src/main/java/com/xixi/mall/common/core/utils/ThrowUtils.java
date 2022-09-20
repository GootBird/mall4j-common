package com.xixi.mall.common.core.utils;


import com.xixi.mall.common.core.enums.ResponseEnum;
import com.xixi.mall.common.core.exception.ProjectException;
import com.xixi.mall.common.core.webbase.vo.ServerResponse;

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

    public static void throwErr(String code, String msg, Object... args) {
        throw getException(code, msg, args);
    }

    public static void throwErr(String msg, Object... args) {
        throw getException(msg, args);
    }

    public static void throwErr() {
        throw getException(ResponseEnum.SHOW_FAIL);
    }

    public static Supplier<ProjectException> getSupErr(ResponseEnum errMsgEnum, Object... args) {
        return () -> getException(errMsgEnum, args);
    }

    public static Supplier<ProjectException> getSupErr(String msg, Object... args) {
        return () -> getException(msg, args);
    }

    public static Supplier<ProjectException> getSupErr(String code, String msg, Object... args) {
        return () -> getException(code, msg, args);
    }

    public static Supplier<ProjectException> getSupErr() {
        return () -> getException(ResponseEnum.SHOW_FAIL);
    }


    /**
     * 如果bol为true，不抛出异常 false反之
     *
     * @param bol        assertResult
     * @param errMsgEnum 异常信息
     */
    public static void checkIsTrue(boolean bol, ResponseEnum errMsgEnum) {
        if (!bol) {
            throwErr(errMsgEnum);
        }
    }

    /**
     * 如果bol为true，不抛出异常 false反之
     *
     * @param bol    assertResult
     * @param errMsg 异常信息
     * @param args   参数
     */
    public static void checkIsTrue(boolean bol, String errMsg, Object... args) {
        if (!bol) throwErr(errMsg, args);
    }

    /**
     * 如果bol为false不抛出异常 true反之
     *
     * @param bol        assertResult
     * @param errMsgEnum 异常信息
     */
    public static void checkIsFalse(boolean bol, ResponseEnum errMsgEnum) {
        if (bol) throwErr(errMsgEnum);
    }


    /**
     * 如果bol为false不抛出异常 true反之
     *
     * @param bol    assertResult
     * @param errMsg 异常信息
     * @param args   参数
     */
    public static void checkIsFalse(boolean bol, String errMsg, Object... args) {
        if (bol) throwErr(errMsg, args);
    }

    /**
     * 如果response是异常状态则抛出异常
     *
     * @param serverResponse resp
     */
    public static <T> ServerResponse<T> checkResAndGet(ServerResponse<T> serverResponse) {
        if (serverResponse.unSuccess()) {
            throwErr(serverResponse.getCode(), serverResponse.getMsg());
        }
        return serverResponse;
    }

    /**
     * 检查res并获取res数据
     *
     * @param serverResponse resp
     * @param <T>            data
     * @return data
     */
    public static <T> T checkResAndGetData(ServerResponse<T> serverResponse) {
        if (serverResponse.unSuccess()) {
            throwErr(serverResponse.getCode(), serverResponse.getMsg());
        }
        return serverResponse.getData();
    }


}
