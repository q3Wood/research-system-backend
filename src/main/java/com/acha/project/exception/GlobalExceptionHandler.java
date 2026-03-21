package com.acha.project.exception;

import com.acha.project.common.BaseResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 作用：捕获代码里抛出的异常，统一转成 JSON 返回给前端
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 0. 捕获业务异常 (自定义异常)
    // 业务异常是预期的逻辑分支 (如：密码错误)，不用打 ERROR 日志，以免报警系统误报
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.warn("BusinessException: code={}, message={}", e.getCode(), e.getMessage());
        return BaseResponse.error(e.getCode(), e.getMessage());
    }

    // 1. 捕获 RuntimeException (运行时异常)
    // 只要是未捕获的运行时异常，都算系统故障，必须打 ERROR 日志并通知开发人员
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException: ", e); 
        return BaseResponse.error(500, "系统开小差了，请稍后再试");
    }

    // 2. 捕获 Spring Validation 参数校验异常 (比如：密码太短)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<?> validationExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));
        if (message == null || message.isEmpty()) {
            message = "请求参数校验失败";
        }
        log.warn("validationException: {}", message);
        return BaseResponse.error(400, message);
    }

    // 3. 捕获 GET/表单对象参数绑定与校验异常（@ModelAttribute + @Validated）
    @ExceptionHandler(BindException.class)
    public BaseResponse<?> bindExceptionHandler(BindException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        if (message == null || message.isEmpty()) {
            message = "请求参数绑定失败";
        }
        log.warn("bindException: {}", message);
        return BaseResponse.error(400, message);
    }

    // 4. 捕获方法级参数校验异常（@RequestParam / @PathVariable 上的约束）
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse<?> constraintViolationExceptionHandler(ConstraintViolationException e) {
        String message = e.getConstraintViolations()
                .stream()
                .map(v -> v.getMessage())
                .collect(Collectors.joining("; "));
        if (message == null || message.isEmpty()) {
            message = "请求参数校验失败";
        }
        log.warn("constraintViolationException: {}", message);
        return BaseResponse.error(400, message);
    }

    // 5. 捕获缺失参数异常（仍有使用 required=true 的接口）
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BaseResponse<?> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        String message = e.getParameterName() + " 不能为空";
        log.warn("missingServletRequestParameterException: {}", message);
        return BaseResponse.error(400, message);
    }

    // 6. 捕获所有漏网之鱼 (Checked Exception + 其他所有异常)
    // 比如：文件读写IO异常、数据库连接异常等
    @ExceptionHandler(Exception.class)
    public BaseResponse<?> exceptionHandler(Exception e) {
        log.error("UnknownException: ", e);
        return BaseResponse.error(500, "系统开小差了，请稍后再试");
    }
}