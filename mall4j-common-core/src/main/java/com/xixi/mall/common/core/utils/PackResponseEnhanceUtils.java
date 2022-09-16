package com.xixi.mall.common.core.utils;

import com.xixi.mall.common.core.exception.ProjectException;
import com.xixi.mall.common.core.webbase.vo.ServerResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PackResponseEnhanceUtils {

    public static <T> ServerResponse<T> enhance(PackEnhanceMethod<T> method) {
        try {
            return ServerResponse.success(method.exec());
        } catch (Throwable throwable) {
            log.error("系统异常！", throwable);
            return getErrResponse(throwable);
        }
    }

    public static <T> ServerResponse<T> getErrResponse(Throwable throwable) {
        String[] codeMsg;
        if (
                throwable instanceof ProjectException
                        && (codeMsg = throwable.getMessage().split("_")).length == 2
        ) {
            return ServerResponse.fail(codeMsg[0], codeMsg[1]);
        }
        return ServerResponse.fail("500", "系统异常！");
    }

    public interface PackEnhanceMethod<T> {

        T exec() throws Throwable;

    }
}
