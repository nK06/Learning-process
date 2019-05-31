package com.panther.seckill.exception;

import com.panther.seckill.common.CodeMsg;

/**
 * @version 1.0
 * @ClassName GlobleException
 * @Description TODO
 * @date 2019-05-31 16:35
 */
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 503469502091741431L;
    
    private CodeMsg codeMsg;
    
    public GlobalException(CodeMsg codeMsg){
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
