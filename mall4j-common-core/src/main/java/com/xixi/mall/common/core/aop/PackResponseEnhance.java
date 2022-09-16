package com.xixi.mall.common.core.aop;

import com.xixi.mall.common.core.annotations.PackResponse;
import com.xixi.mall.common.core.exception.ProjectException;
import com.xixi.mall.common.core.webbase.vo.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Controller Response 打包切面
 */
@Slf4j
@Aspect
@Component
public class PackResponseEnhance {

    @Pointcut("@annotation(com.xixi.mall.common.core.annotations.PackResponse)")
    public void PackResCut() {
    }

    @Around("com.xixi.mall.common.core.aop.PackResponseEnhance.PackResCut()")
    public Object doPackResCut(ProceedingJoinPoint point) {

        PackResponse packResponse = Optional.ofNullable(point.getSignature())
                .filter(signature -> signature instanceof MethodSignature)
                .map(signature -> (MethodSignature) signature)
                .map(MethodSignature::getMethod)
                .map(method -> method.getAnnotation(PackResponse.class))
                .orElseThrow(() -> new ProjectException("系统异常"));

        Object resData = enhance(point::proceed);
        return packResponse.hasRes() ? null : resData;
    }

    public static <T> ServerResponse<T> enhance(PackEnhanceMethod<T> method) {
        try {
            return ServerResponse.success(method.exec());
        } catch (Throwable throwable) {
            log.error("系统异常！", throwable);
            String[] codeMsg;
            if (
                    throwable instanceof ProjectException
                            && (codeMsg = throwable.getMessage().split("_")).length == 2
            ) {
                return ServerResponse.fail(codeMsg[0], codeMsg[1]);
            }
            throw new RuntimeException(throwable); //交给spring 统一异常处理层解决
        }
    }

}
