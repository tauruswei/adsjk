package org.cos.common.exception;

import org.cos.common.tool.LogTool;
import org.cos.common.result.CodeMsg;
import org.cos.common.result.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.NoPermissionException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;


/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description: 异常处理器
 * @Date: 2019/1/27 19:46
 */

@ControllerAdvice   //该注解一般只有处理异常有用
@Order(-1)
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //声明处理的异常类
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        // 打印堆栈
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            logger.error(LogTool.failLog(globalException.getCodeMsg()));
            return Result.error(globalException.getCodeMsg());
        } else if (e instanceof BindException) {
            //    绑定失败，如表单对象参数违反约束
            BindException bindException = (BindException) e;
            logger.error(LogTool.failLog(CodeMsg.PARAMETER_VALID_ERROR.fillArgs(buildMessages(bindException.getBindingResult()))));
            return Result.error(CodeMsg.PARAMETER_VALID_ERROR.fillArgs(buildMessages(bindException.getBindingResult())));
        } else if(e instanceof MethodArgumentNotValidException) {
            //    参数无效，如JSON请求参数违反约束
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            logger.error(LogTool.failLog(CodeMsg.PARAMETER_VALID_ERROR.fillArgs(buildMessages(methodArgumentNotValidException.getBindingResult()))));
            return Result.error(CodeMsg.PARAMETER_VALID_ERROR.fillArgs(buildMessages(methodArgumentNotValidException.getBindingResult())));
        }else if(e instanceof HttpMessageNotReadableException){
            //  参数为null，json解析异常，比如 id= , 不同于id=""
            logger.error(LogTool.failLog(CodeMsg.PARAMETER_VALID_ERROR.fillArgs("请求参数不能为 null")));
            return Result.error(CodeMsg.PARAMETER_VALID_ERROR.fillArgs("request parameter can not be null"));
        }else if(e instanceof NoPermissionException){
            logger.error(LogTool.failLog(CodeMsg.PARAMETER_VALID_ERROR.fillArgs("没有权限")));
            return Result.error(CodeMsg.PARAMETER_VALID_ERROR.fillArgs("you do not permissionless"));
//        }else if(e instanceof VerifyException){
//            logger.error(LogTool.failLog(CodeMsg.ADMIN_VERIFY_ERROR.setMsg(e.getMessage())));
//            return Result.error(CodeMsg.ADMIN_VERIFY_ERROR.setMsg(e.getMessage()));
        }else if(e instanceof MissingServletRequestParameterException){
            logger.error(LogTool.failLog(CodeMsg.PARAMETER_VALID_ERROR.fillArgs(e.getMessage())));
            return Result.error(CodeMsg.PARAMETER_VALID_ERROR.fillArgs(e.getMessage()));
        } else {
            logger.error(LogTool.failLog(CodeMsg.SERVER_ERROR));
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
