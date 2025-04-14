package com.odin.job.aspect;

import com.odin.job.model.User;
import com.odin.job.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    public SecurityAspect(UserService userService) {
        this.userService = userService;
    }

    @Before("execution(* com.odin.job.controller.JobController.*(..))")
    public void validateJobAccess(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.getCurrentUser(username);

        // Get the job ID from the method arguments if present
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Integer) {
                int jobId = (Integer) arg;
                logger.info("Validating access for job ID: {} by user: {}", jobId, username);
                // Additional security checks can be added here
            }
        }
    }

    @Before("execution(* com.odin.job.controller.UserController.updateUser(..))")
    public void validateUserUpdate(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = userService.getCurrentUser(username);

        // Get the user ID from the method arguments
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof Integer) {
            int userId = (Integer) args[0];
            logger.info("Validating user update for user ID: {} by user: {}", userId, username);
            
            // Check if user is updating their own profile or is an admin
            if (userId != currentUser.getId() && 
                !authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                throw new SecurityException("Unauthorized access: User can only update their own profile");
            }
        }
    }
}
