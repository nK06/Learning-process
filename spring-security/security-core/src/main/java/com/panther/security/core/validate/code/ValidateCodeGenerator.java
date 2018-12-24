package com.panther.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {

    ValidateCode generateCode(ServletWebRequest request);
}
