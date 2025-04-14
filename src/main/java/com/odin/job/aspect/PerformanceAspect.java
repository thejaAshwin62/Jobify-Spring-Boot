package com.odin.job.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* com.odin.job.controller.*.*(..)) || execution(* com.odin.job.service.*.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            if (executionTime > 1000) { // Log warning if execution time > 1 second
                logger.warn("Method: {} took {} ms to execute", 
                    joinPoint.getSignature().toShortString(),
                    executionTime);
            } else {
                logger.info("Method: {} executed in {} ms", 
                    joinPoint.getSignature().toShortString(),
                    executionTime);
            }
            
            return result;
        } catch (Throwable e) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.error("Method: {} failed after {} ms with error: {}", 
                joinPoint.getSignature().toShortString(),
                executionTime,
                e.getMessage());
            throw e;
        }
    }
}
