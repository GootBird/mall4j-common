package com.xixi.mall.common.core.webbase.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 分页查询返回参数协议
 *
 * @param <T> result
 */
@Setter
@Getter
@Accessors(chain = true)
public class BasePageRespBodyVo<T> {

    private List<T> result;

    private PageVo pageInfo;

}
