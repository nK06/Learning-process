package com.panther.security.core.validate.code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class ValidateCodeController {

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;

    public static final String SESSION_KEY = "SESSION_KEY";

    /**
     * 创建验证码，根据验证码不同，调用不同的{@link ValidateCodeProcessor} 接口实现
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @RequestMapping("/code/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        validateCodeProcessors.get(type+"ValidateCodeProcessor").create(new ServletWebRequest(request,response));
    }


}
