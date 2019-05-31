package com.panther.seckill.common;

/**
 * @version 1.0
 * @ClassName CodeMsg
 * @Description TODO
 * @date 2019-05-28 15:34
 */
public class CodeMsg {
    private int code;
    private String message;
    
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");

    //异常
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常: %s");
    
    //登录模块
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500100, "登录密码不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500101, "手机号码错误");
    public static CodeMsg USER_NOT_EXIST = new CodeMsg(500102, "用户不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500103, "密码错误");

    private CodeMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CodeMsg fillArgs(Object...args) {
        int code = this.code;
        String message = String.format(this.message, args);
        return new CodeMsg(code,message);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    
}
