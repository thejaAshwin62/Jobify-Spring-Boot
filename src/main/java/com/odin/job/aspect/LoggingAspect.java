package com.odin.job.aspect;

import com.odin.job.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.odin.job.controller.*.*(..)) || execution(* com.odin.job.service.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        
        // Log method entry with sanitized arguments
        StringBuilder logMessage = new StringBuilder("Entering method: " + className + "." + methodName);
        if (args.length > 0) {
            logMessage.append(" with args: ");
            for (Object arg : args) {
                if (arg instanceof User) {
                    User user = (User) arg;
                    logMessage.append("User(id=").append(user.getId())
                            .append(", name=").append(user.getName())
                            .append(", email=").append(user.getEmail())
                            .append(", role=").append(user.getRole())
                            .append(")");
                } else {
                    logMessage.append(arg);
                }
                logMessage.append(", ");
            }
            logMessage.setLength(logMessage.length() - 2); // Remove trailing comma and space
        }
        logger.info(logMessage.toString());
    }

    @AfterReturning(pointcut = "execution(* com.odin.job.controller.*.*(..)) || execution(* com.odin.job.service.*.*(..))", 
                    returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        // Log method exit with sanitized return value
        if (result instanceof User) {
            User user = (User) result;
            logger.info("Method: {}.{} returned with value: User(id={}, name={}, email={}, role={})", 
                className, methodName, user.getId(), user.getName(), user.getEmail(), user.getRole());
        } else {
            logger.info("Method: {}.{} returned with value: {}", className, methodName, result);
        }
    }

    @AfterThrowing(pointcut = "execution(* com.odin.job.controller.*.*(..)) || execution(* com.odin.job.service.*.*(..))", 
                   throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.error("Exception in method: {}.{} with message: {}", className, methodName, ex.getMessage());
    }
}
