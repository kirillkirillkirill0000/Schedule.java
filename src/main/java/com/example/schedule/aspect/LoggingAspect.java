package com.example.schedule.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.example.schedule.controller..*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("==> Вызов метода: {}.{}()",
                joinPoint.getTarget().getClass().getSimpleName(),
                methodName);

        try {
            Object result = joinPoint.proceed();
            LOGGER.info("<== Успешное выполнение: {}.{}()",
                    joinPoint.getTarget().getClass().getSimpleName(),
                    methodName);
            return result;
        } catch (Exception ex) {
            LOGGER.error("<== Ошибка в {}.{}(): {}",
                    joinPoint.getTarget().getClass().getSimpleName(),
                    methodName,
                    ex.getMessage());
            throw ex;
        }
    }

    @Around("execution(* com.example.schedule.service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("==> Вызов метода: {}.{}()",
                joinPoint.getTarget().getClass().getSimpleName(),
                methodName);

        try {
            Object result = joinPoint.proceed();
            LOGGER.info("<== Успешное выполнение: {}.{}()",
                    joinPoint.getTarget().getClass().getSimpleName(),
                    methodName);
            return result;
        } catch (Exception ex) {
            LOGGER.error("<== Ошибка в {}.{}(): {}",
                    joinPoint.getTarget().getClass().getSimpleName(),
                    methodName,
                    ex.getMessage());
            throw ex;
        }
    }
}
