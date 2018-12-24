package com.panther.security.core.validate.code;


import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

    /***
     *
     */
    private static final long serialVersionUID = -1217214654526169619L;

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
