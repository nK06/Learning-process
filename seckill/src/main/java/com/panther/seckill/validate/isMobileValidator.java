package com.panther.seckill.validate;

import com.panther.seckill.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @version 1.0
 * @ClassName isMobileValidator
 * @Description TODO
 * @date 2019-05-31 15:18
 */
public class isMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;
    
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return ValidatorUtil.isMobile(s);
        }else {
            if (StringUtils.isEmpty(s)){
                return true;
            }else {
                return ValidatorUtil.isMobile(s);
            }
        }
    }
}
