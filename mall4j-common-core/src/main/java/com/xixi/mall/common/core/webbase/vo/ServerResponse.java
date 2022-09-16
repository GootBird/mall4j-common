package com.xixi.mall.common.core.webbase.vo;

import com.xixi.mall.common.core.enums.ResponseEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;

/**
 * 统一的返回数据
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ServerResponse<T> implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(ServerResponse.class);

    /**
     * 状态码
     */
    private String code;

    /**
     * 信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    public boolean isSuccess() {
        return Objects.equals(ResponseEnum.OK.value(), this.code);
    }

    public boolean unSuccess() {
        return !isSuccess();
    }

    public static <T> ServerResponse<T> success(T data) {
        return new ServerResponse<T>()
                .setData(data)
                .setCode(ResponseEnum.OK.value());
    }

    public static <T> ServerResponse<T> success() {
        return new ServerResponse<T>()
                .setCode(ResponseEnum.OK.value())
                .setMsg(ResponseEnum.OK.getMsg());
    }

    /**
     * 前端显示失败消息
     *
     * @param msg 失败消息
     * @return resp
     */
    public static <T> ServerResponse<T> showFailMsg(String msg) {
        return new ServerResponse<T>()
                .setMsg(msg)
                .setCode(ResponseEnum.SHOW_FAIL.value());
    }

    public static <T> ServerResponse<T> fail(ResponseEnum responseEnum) {
        return new ServerResponse<T>()
                .setMsg(responseEnum.getMsg())
                .setCode(responseEnum.value());
    }

    public static <T> ServerResponse<T> fail(ResponseEnum responseEnum, T data) {
        return new ServerResponse<T>()
                .setMsg(responseEnum.getMsg())
                .setCode(responseEnum.value())
                .setData(data);
    }

    public static <T> ServerResponse<T> fail(String code, String msg) {
        return new ServerResponse<T>()
                .setMsg(msg)
                .setCode(code);
    }

    public static <T> ServerResponse<T> transform(ServerResponse<?> oldServerResponse) {
        return new ServerResponse<T>()
                .setMsg(oldServerResponse.getMsg())
                .setCode(oldServerResponse.getCode());
    }

}
