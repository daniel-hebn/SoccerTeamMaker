package com.hebn.soccerTeamMaker.infrastructure.util;

import com.google.common.base.Optional;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Created by greg.lee on 2016. 8. 30..
 */
public class LoggingUtils {

    public static String errorLogging(Logger logger, Exception ex) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex) != null ? ExceptionUtils.getRootCause(ex) : ex ;
        String rootExceptionMessage = rootCause.getMessage();

        StringBuffer sb = new StringBuffer(1000);
        sb.append(" Method = [").append(getClassNameFrom(ex)).append(".").append(getMethodFrom(ex)).append("]").append(" is Failed. ");
        sb.append(" Error = [").append(rootCause).append("], ");
        sb.append(" ErrorMessage = [").append(rootExceptionMessage).append("]");
        String errorMessage = sb.toString();
        logger.error(errorMessage);
        return errorMessage;
    }

    public static String errorLogging(Logger logger, Exception ex, Object param) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex) != null ? ExceptionUtils.getRootCause(ex) : ex ;
        String rootExceptionMessage = rootCause.getMessage();

        StringBuffer sb = new StringBuffer(1000);
        sb.append(" Method = [").append(getClassNameFrom(ex)).append(".").append(getMethodFrom(ex)).append("]").append(" is Failed. ");
        sb.append(" Error = [").append(rootCause).append("], ");
        sb.append(" ErrorMessage = [").append(rootExceptionMessage).append("], ");
        sb.append(" Param = [").append(ReflectionToStringBuilder.toString(param, ToStringStyle.SHORT_PREFIX_STYLE)).append("]");
        String errorMessage = sb.toString();
        logger.error(errorMessage);
        return errorMessage;
    }

    public static Optional<String> bindingResultLogging(Logger logger, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuffer sb = new StringBuffer(1000);
            sb.append("잘못된 형식의 입력값이 전달되었습니다. 확인 바랍니다. ");
            if (bindingResult instanceof BindException) {
                BindException ex = (BindException) bindingResult;
                sb.append(" Method = [").append(getClassNameFrom(ex)).append(".").append(getMethodFrom(ex)).append("]").append(" is Failed. ");
                sb.append(" Error = [").append(ex).append("], ");
            }

            sb.append("ErrorMessage = [");
            for (FieldError error : bindingResult.getFieldErrors()) {
                String errorField = error.getField();
                sb.append(errorField).append(":").append(error.getRejectedValue()).append(" / cause :").append(error.getDefaultMessage());
            }
            sb.append("]");
            String errorMessage = sb.toString();
            logger.error(errorMessage);
            return Optional.of(errorMessage);
        } else {
            return Optional.absent();
        }
    }

    private static String getClassNameFrom(Exception ex) {
        return ex.getStackTrace()[0].getClassName();
    }

    private static String getMethodFrom(Exception ex) {
        StackTraceElement stackTraceElement = ex.getStackTrace()[0];
        return stackTraceElement.getMethodName() + ". LineNum: (" + stackTraceElement.getLineNumber() + ")";
    }
}
