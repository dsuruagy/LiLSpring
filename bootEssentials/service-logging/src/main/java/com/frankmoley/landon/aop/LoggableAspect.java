package com.frankmoley.landon.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggableAspect {

    private final Logger log;

    public LoggableAspect(String loggerName){
        super();
        log = LoggerFactory.getLogger(loggerName);
    }

    @Pointcut("@annotation(Timed)")
    public void executeTiming(){}

    @Around("executeTiming()")
    public Object logMethodTiming(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object returnValue = proceedingJoinPoint.proceed();
        long totalTime = System.currentTimeMillis() - startTime;
        StringBuilder message = new StringBuilder("Method: ");
        message.append(proceedingJoinPoint.getSignature().getName());
        message.append(" | ");
        message.append(totalTime).append("ms");
        log.info(message.toString());
        return returnValue;
    }


}
