package org.nahuelrodriguez.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLogger {
    private static final Logger logger = LoggerFactory.getLogger(ControllerLogger.class);

    @Around("execution(public * *(..)) && within(org.nahuelrodriguez.controllers.*)")
    public Object logControllerMethodExecution(final ProceedingJoinPoint pjp) throws Throwable {
        logger.debug("Executing method {}", pjp.getSignature());
        final Object retVal;

        try {
            retVal = pjp.proceed();
            logger.debug("Method {} executed correctly", pjp.getSignature());
        } catch (final Throwable throwable) {
            logger.error("Method {} has failed with exception message {}",
                    pjp.getSignature(),
                    throwable.getMessage());
            throw throwable;
        }
        return retVal;
    }
}
