package com.panther.code;

import com.panther.security.core.validate.code.ValidateCodeGenerator;
import com.panther.security.core.validate.code.image.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/***
 * 功能开发阶段，此处自定义实现暂时关闭
 */
//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

    @Override
    public ImageCode generateCode(ServletWebRequest request) {
        System.out.println("更高级的图形验证码生成代码 ==== 自定义的图形验证码代码块");
        return null;
    }
}
