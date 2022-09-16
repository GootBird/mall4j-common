package com.xixi.mall.common.core.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class CommonFuncUtils {

    private static final String START_DATE_SUFFIX = " 00:00:00";

    private static final String END_DATE_SUFFIX = " 23:59:59";

    /**
     * toString
     */
    public static String mapToStr(Object obj) {
        return Optional.ofNullable(obj)
                .map(Object::toString)
                .orElse(null);
    }

    /**
     * List 归约成指定符号分隔的字符串
     */
    public static String reduceToSymbolStr(Collection<?> list, String symbol) {
        return Optional.ofNullable(list)
                .filter(CollectionUtil::isNotEmpty)
                .flatMap(val -> val.stream()
                        .filter(Objects::nonNull)
                        .map(Object::toString)
                        .reduce((a, b) -> a + symbol + b))
                .orElse(null);
    }

    /**
     * List 归约成逗号分隔的字符串
     */
    public static String reduceToCommaStr(Collection<?> list) {
        return reduceToSymbolStr(list, ",");
    }

    /**
     * 根据指定符号split成list
     *
     * @param str    字符
     * @param symbol 分隔符
     * @return list
     */
    public static List<String> strMapToListBySymbol(String str, String symbol) {
        return Optional.ofNullable(str)
                .filter(StringUtils::isNotBlank)
                .map(val -> Arrays.asList(val.split(symbol)))
                .orElse(null);
    }

    /**
     * 逗号分隔的字符串转成List
     */
    public static List<String> strMapToListByComma(String str) {
        return strMapToListBySymbol(str, ",");
    }

    /**
     * 格式化日期字符串
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return 日期字符串
     */
    public static String formatDate(Date date, String pattern) {
        return Optional.ofNullable(date)
                .map(time -> DateUtil.format(time, pattern))
                .orElse(null);
    }

    /**
     * 格式化日期字符串成 ymd
     *
     * @param date 日期
     * @return yyyy-mm-dd格式字符串
     */
    public static String formatDateByYmd(Date date) {
        return formatDate(date, DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 日期拼时分秒
     *
     * @param date 日期
     * @return 日期
     */
    public static String beginOfDate(String date) {
        return Optional.ofNullable(date)
                .filter(StringUtils::isNotEmpty)
                .map(val -> val + START_DATE_SUFFIX)
                .orElse(null);
    }

    /**
     * 日期拼时分秒
     *
     * @param date 日期
     * @return 日期
     */
    public static String endOfDate(String date) {
        return Optional.ofNullable(date)
                .filter(StringUtils::isNotEmpty)
                .map(val -> val + END_DATE_SUFFIX)
                .orElse(null);
    }

    /**
     * map
     *
     * @param list 数据
     * @param func 函数
     * @param <T>  t
     * @param <V>  v
     * @return list
     */
    public static <T, V> List<V> map(Collection<T> list, Function<T, V> func) {
        return list.stream().map(func).collect(Collectors.toList());
    }

    /**
     * 非空校验
     *
     * @param val       值
     * @param fieldName 字段名称
     * @return val
     */
    public static <T> T nonNullCheck(T val, String fieldName) {
        return Optional.ofNullable(val)
                .orElseThrow(ThrowUtils.getSupErr(fieldName + "不能为空！"));
    }

    public static String nonBlankCheck(String val, String fieldName) {
        return Optional.ofNullable(val)
                .filter(StringUtils::isNotBlank)
                .orElseThrow(ThrowUtils.getSupErr(fieldName + "不能为空！"));
    }

    /**
     * map
     */
    public static <T, V> V map(T val, Function<T, V> mapFunc) {
        return Optional.ofNullable(val)
                .map(mapFunc)
                .orElse(null);
    }

    /**
     * a是否大于b
     *
     * @param a   可以为空, 为空返回false
     * @param b   不可为空
     * @param <T> type
     * @return compareResult
     */
    public static <T extends Comparable<T>> boolean gt(T a, T b) {
        Objects.requireNonNull(b);

        return Optional.ofNullable(a)
                .map(val -> val.compareTo(b) > 0)
                .orElse(Boolean.FALSE);
    }

}
