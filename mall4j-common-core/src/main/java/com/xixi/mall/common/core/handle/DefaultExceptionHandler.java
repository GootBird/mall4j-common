package com.xixi.mall.common.core.handle;

import cn.hutool.core.util.StrUtil;
import com.xixi.mall.common.core.enums.ResponseEnum;
import com.xixi.mall.common.core.webbase.vo.ServerResponse;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义错误处理器，除了授权只要请求服务器成功，就返回200，错误根据错误码前端进行处理
 */
@Slf4j
@RestController
@RestControllerAdvice
public class DefaultExceptionHandler {


    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ServerResponse<List<String>>> methodArgumentNotValidExceptionHandler(Exception e) {

        log.error("methodArgumentNotValidExceptionHandler", e);
        List<FieldError> fieldErrors = null;

        if (e instanceof MethodArgumentNotValidException) {
            fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
        }

        if (e instanceof BindException) {
            fieldErrors = ((BindException) e).getBindingResult().getFieldErrors();
        }

        if (fieldErrors == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ServerResponse.fail(ResponseEnum.METHOD_ARGUMENT_NOT_VALID));
        }

        List<String> defaultMessages = new ArrayList<>(fieldErrors.size());
        for (FieldError fieldError : fieldErrors) {
            defaultMessages.add(fieldError.getField() + ":" + fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(ServerResponse.fail(ResponseEnum.METHOD_ARGUMENT_NOT_VALID, defaultMessages));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ServerResponse<List<FieldError>>> methodArgumentNotValidExceptionHandler(
            HttpMessageNotReadableException e) {

        log.error("methodArgumentNotValidExceptionHandler", e);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ServerResponse.fail(ResponseEnum.HTTP_MESSAGE_NOT_READABLE));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerResponse<Object>> exceptionHandler(Exception e) throws TransactionException {

        String XID = RootContext.getXID();

        log.error("exceptionHandler", e);
        log.info("RootContext.getXID(): " + XID);

        if (StrUtil.isNotBlank(XID)) {
            GlobalTransactionContext.reload(XID).rollback();
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(ServerResponse.fail(ResponseEnum.EXCEPTION));
    }

}
