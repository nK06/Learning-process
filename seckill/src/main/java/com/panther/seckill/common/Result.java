package com.panther.seckill.common;

/**
 * @version 1.0
 * @ClassName Result
 * @Description TODO
 * @date 2019-05-28 15:27
 */
public class Result<T> {
    private int code;
    private String message;
    private T data;
    
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }
    
    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }
    
    private Result(T data) {
        this.code = 0;
        this.message = "success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg) {
       if(codeMsg == null){
           return;
       }
       this.code = codeMsg.getCode();
       this.message = codeMsg.getMessage();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}
