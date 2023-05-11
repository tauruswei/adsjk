package org.cos.common.exception;

import org.cos.common.result.CodeMsg;
import org.cos.common.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description: 异常处理器
 * @Date: 2019/1/27 19:46
 */

@ControllerAdvice   //该注解一般只有处理异常有用
@ResponseBody
public class GlobalExceptionHandler {
    //声明处理的异常类
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            return Result.error(globalException.getCodeMsg());
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            return Result.error(CodeMsg.PARAMETER_VALID_ERROR.fillArgs(buildMessages(bindException.getBindingResult())));
        } else if(e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException methodArgumentNotValidException= (MethodArgumentNotValidException) e;
            return Result.error(CodeMsg.PARAMETER_VALID_ERROR.fillArgs(buildMessages(methodArgumentNotValidException.getBindingResult())));
        }else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
    private String buildMessages(BindingResult result) {
        StringBuilder resultBuilder = new StringBuilder();

        List<ObjectError> errors = result.getAllErrors();
        if (errors != null && errors.size() > 0) {
            for (ObjectError error : errors) {
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
//                    String fieldName = fieldError.getField();
                    String fieldErrMsg = fieldError.getDefaultMessage();
                    resultBuilder.append(fieldErrMsg).append(";");
                }
            }
        }
        return resultBuilder.toString();
    }
}
