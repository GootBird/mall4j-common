package com.xixi.mall.common.database.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xixi.mall.common.core.webbase.vo.PageVo;

import java.util.Optional;

public class PageObjConvertUtil {

    public static <T> IPage<T> getIPageByPageVo(PageVo pageVo) {
        Optional<PageVo> pageOpl = Optional.ofNullable(pageVo);

        IPage<T> iPage = new Page<>();

        iPage.setCurrent(
                pageOpl.map(PageVo::getPageNum).orElse(1L)
        );

        iPage.setSize(
                pageOpl.map(PageVo::getPageSize).orElse(10L)
        );

        return iPage;
    }

    public static PageVo getPageVoByIPage(IPage<?> iPage) {
        return new PageVo()
                .setPageSize(iPage.getSize())
                .setPageNum(iPage.getCurrent())
                .setPages(iPage.getPages())
                .setTotal(iPage.getTotal());
    }

}
