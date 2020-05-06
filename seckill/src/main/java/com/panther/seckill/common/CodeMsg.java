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

    //异常    5001XX
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常: %s");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
    public static CodeMsg REQUEST_LIMIT = new CodeMsg(500103, "访问太频繁");
    
    //登录模块  5002XX
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500200, "登录密码不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500201, "手机号码错误");
    public static CodeMsg USER_NOT_EXIST = new CodeMsg(500202, "用户不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500203, "密码错误");
    
    //秒杀部分  6001XX
    public static CodeMsg SECKILL_FAIL = new CodeMsg(600100,"手速慢啦，没有秒杀成功QAQ");
    public static CodeMsg SECKILL_REPEATE = new CodeMsg(600101,"不能重复秒杀");
    
    //订单部分 7001XX
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(700101,"订单不存在");

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
