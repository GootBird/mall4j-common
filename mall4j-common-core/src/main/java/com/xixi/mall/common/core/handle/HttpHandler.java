package com.xixi.mall.common.core.handle;

import cn.hutool.core.util.CharsetUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xixi.mall.common.core.exception.ProjectException;
import com.xixi.mall.common.core.utils.XssUtil;
import com.xixi.mall.common.core.webbase.vo.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class HttpHandler {

    private static ObjectMapper objectMapper;

    @Resource
    public void setObjectMapper(ObjectMapper objectMapper) {
        HttpHandler.objectMapper = objectMapper;
    }

    public static <T> void printServerResponseToWeb(ServerResponse<T> serverResponse) {
        if (serverResponse == null) {
            log.info("print obj is null");
            return;
        }

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        if (requestAttributes == null) {
            log.error("requestAttributes is null, can not print to web");
            return;
        }

        HttpServletResponse response = requestAttributes.getResponse();
        if (response == null) {
            log.error("httpServletResponse is null, can not print to web");
            return;
        }

        log.error("response error: " + serverResponse.getMsg());
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        PrintWriter printWriter;
        try {
            printWriter = response.getWriter();
            printWriter.write(XssUtil.clean(objectMapper.writeValueAsString(serverResponse)));
        } catch (IOException e) {
            throw new ProjectException("io 异常", e);
        }
    }

}
