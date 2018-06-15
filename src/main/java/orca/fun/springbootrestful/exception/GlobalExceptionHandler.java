package orca.fun.springbootrestful.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author orca
 * @date 2018/6/14
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 响应码 500 内部错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public VndErrors onException(Exception e) {
        log.error("Caught exception while handling a request", e);
        String logRef = e.getClass().getSimpleName();
        String msg = getExceptionMessage(e);
        return new VndErrors(logRef, msg);
    }

    /**
     * 响应码 409 对象已经存在，冲突错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AllReadyExistsStudentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public VndErrors onAllReadyExistsException(Exception e) {
        String logRef = logWarnLevelExceptionMessage(e);
        if (log.isTraceEnabled()) {
            logTraceLevelStrackTrace(e);
        }
        String msg = getExceptionMessage(e);
        return new VndErrors(logRef, msg);
    }


    /**
     * 响应码 404 找不到
     *
     * @param e
     * @return
     */
    @ExceptionHandler({NoSuchStudentException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public VndErrors onNotFoundException(Exception e) {
        String logRef = logWarnLevelExceptionMessage(e);
        if (log.isTraceEnabled()) {
            logTraceLevelStrackTrace(e);
        }
        String msg = getExceptionMessage(e);
        return new VndErrors(logRef, msg);
    }

    private String logWarnLevelExceptionMessage(Exception e) {
        log.warn("Caught exception while handling a request: " + getExceptionMessage(e));
        return e.getClass().getSimpleName();
    }

    private String logTraceLevelStrackTrace(Throwable t) {
        log.trace("Caught exception while handling a request", t);
        return t.getClass().getSimpleName();
    }

    private String getExceptionMessage(Exception e) {
        return StringUtils.hasText(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName();
    }

}
