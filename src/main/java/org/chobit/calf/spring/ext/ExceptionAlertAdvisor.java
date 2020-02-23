package org.chobit.calf.spring.ext;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.chobit.calf.constants.AlertType;
import org.chobit.calf.except.CalfAdminException;
import org.chobit.calf.except.CalfArgsException;
import org.chobit.calf.except.CalfRemoteException;
import org.chobit.calf.model.AlertMessage;
import org.chobit.calf.tools.SessionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author robin
 */
@Aspect
@Component
public class ExceptionAlertAdvisor {


    private final Logger logger = LoggerFactory.getLogger(ExceptionAlertAdvisor.class);

    @Around("execution(public * org.chobit.calf.service.*.*(..)) ")
    public Object handle(final ProceedingJoinPoint pjd) throws Throwable {
        try {
            return pjd.proceed();
        } catch (CalfArgsException | CalfAdminException e) {
            SessionHolder.addAlert(new AlertMessage(AlertType.DANGER, e.getMessage()));
            return null;
        } catch (CalfRemoteException cre) {
            logger.error("服务器异常", cre);
            throw cre;
        } catch (Exception e) {
            logger.error("服务器异常", e);
            SessionHolder.addAlert(new AlertMessage(AlertType.DANGER, "服务器异常，请联系管理员"));
            return null;
        }
    }

}
