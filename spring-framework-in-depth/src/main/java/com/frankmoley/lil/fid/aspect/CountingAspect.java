package com.frankmoley.lil.fid.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Aspect
public class CountingAspect {
    private Map<String, Long> methodMap = new HashMap<>();
    private final Logger LOGGER = LoggerFactory.getLogger(CountingAspect.class);

    @Pointcut("within(com.frankmoley.lil.fid..*)")
    public void executeCounting() {}

    @Before("executeCounting()")
    public void incrementCount(JoinPoint joinPoint) {
        StringBuilder message = new StringBuilder();
        String methodName = joinPoint.getSignature().toShortString();
        Long counter = Optional.ofNullable(methodMap.get(methodName)).orElse(0L);
        counter++;

        methodMap.put(methodName, counter);

        message.append("Method name: ").append(methodName);
        message.append(", called ").append(counter).append(" times.");

        LOGGER.info(message.toString());
    }
}
