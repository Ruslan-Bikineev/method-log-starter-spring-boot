package org.logger.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.logger.exceptions.AroundAspectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;


@Aspect
public class LoggingAspect {
    private Level level;
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    public LoggingAspect(String logLevel) {
        this.level = getLevel(logLevel);
    }

    @Before("@annotation(org.logger.aspect.annotations.MethodLogging)")
    public void beforeLogging(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        log.atLevel(level).log("LOG BEFORE METHOD: {}.{}() {}", typeName, methodName, args);
    }

    @AfterReturning(value = "@annotation(org.logger.aspect.annotations.MethodLogging)",
            returning = "object")
    public void afterReturning(JoinPoint joinPoint, Object object) {
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        log.atLevel(level).log("LOG AFTER METHOD: {}.{}() {}", typeName, methodName, object);
    }

    @AfterThrowing(pointcut = "@annotation(org.logger.aspect.annotations.MethodLogging)",
            throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Throwable exception) {
        Object[] args = joinPoint.getArgs();
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        log.atLevel(level).log("LOG EXCEPTION: {}.{}() {} - {} {}",
                typeName, methodName, args, exception.getClass(), exception.getMessage());
    }

    @Around("@annotation(org.logger.aspect.annotations.MethodLogging)")
    public Object aroundLogging(ProceedingJoinPoint joinPoint) {
        Object proceeded;
        String typeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        long start = System.currentTimeMillis();
        try {
            proceeded = joinPoint.proceed();
            log.atLevel(level).log("LOG TIME ELAPSED OF METHOD: {}.{}() - {} ms",
                    typeName, methodName, (System.currentTimeMillis() - start));
        } catch (Throwable e) {
            throw new AroundAspectException("Exception when proceed method %s.%s()"
                    .formatted(typeName, methodName));
        }
        return proceeded;
    }

    /**
     * Parse string to level
     * default level is INFO
     *
     * @param level - string level
     * @return
     */
    private Level getLevel(String level) {
        try {
            return Level.valueOf(level.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Level.INFO;
        }
    }
}
