package com.panther.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.swing.plaf.synth.SynthSpinnerUI;

@Aspect
// 暂时移除该组件，日志内容太多
//@Component
public class TimeAspect {

    // 处理UserController 下的任何方法，任何参数。 第一个* 是任何返回值
    @Around("execution(* com.panther.web.controller.UserController.*(..))")
    public  Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {

        System.out.println("time aspect start");

        long start = System.currentTimeMillis();

        // 方法参数数组
        Object[] args = pjp.getArgs();
        for (Object arg : args){
            System.out.println("arg is "+arg);
        }

        // 这里得到的是方法的返回值
        Object object = pjp.proceed();

        System.out.println("time aspect 耗时" + (System.currentTimeMillis() - start));
        System.out.println("time aspect end");
        return null;
    }

}
