package com.xixi.mall.common.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Controller  Response打包
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PackResponse {

    /**
     * hasRes 业务代码中是否已经对HttpResponse进行过填充
     * 若程序运行顺利，将不会对返回数据进行打包处理，若运行报错，将会对报错信息进行打包返回
     *
     * @return res
     */
    boolean hasRes() default false;
}
