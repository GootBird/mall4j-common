package com.xixi.mall.common.core.webbase.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页查询请求参数协议
 *
 * @param <T> result
 */
@Setter
@Getter
public class BasePageReqBodyVo<T> {

    private T param;

    private PageVo pageVo;

}
