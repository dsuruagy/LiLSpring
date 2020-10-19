package com.frankmoley.lil.fid.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(Loggable)") // the pointcut expression
    public void executeLogging() { // the pointcut signature, used by advice annotations for reference

    }

    @Before("executeLogging()") // an advice annotation, referring to the pointcut signature
    public void logMethodCall(JoinPoint joinPoint) {
        StringBuilder message = new StringBuilder("Method: ");
        message.append(joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();

        if(null != args && args.length > 0){
            message.append(" args[ | ");
            Arrays.asList(args).forEach(arg ->
                    message.append(arg).append(" | "));
            message.append("]");
        }
        LOGGER.info(message.toString());
    }

    // an advice annotation, referring to the pointcut signature
    @AfterReturning(value = "executeLogging()", returning = "returnValue")
    public void logMethodReturning(JoinPoint joinPoint, Object returnValue) {
        StringBuilder message = new StringBuilder();
        if(returnValue instanceof Collection) {
            message.append("Returning: ")
                    .append(((Collection) returnValue).size()).append(" instances.");
        } else {
            message.append("Returning: ").append(returnValue.toString());
        }
        LOGGER.info(message.toString());
    }
}
