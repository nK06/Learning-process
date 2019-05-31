package com.panther.seckill.exception;

import com.panther.seckill.common.CodeMsg;
import com.panther.seckill.common.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author makai
 * @version 1.0
 * @ClassName GlobleException
 * @Description TODO
 * @date 2019-05-31 15:28
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
        
        if(e instanceof BindException){
            BindException bindException = (BindException) e;
            List<ObjectError> allErrors = bindException.getAllErrors();
            ObjectError error = allErrors.get(0);
            String message = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(message));
        }else if (e instanceof GlobalException){
            GlobalException globalException = (GlobalException)e;
            return Result.error(globalException.getCodeMsg());
        }else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
    
}
