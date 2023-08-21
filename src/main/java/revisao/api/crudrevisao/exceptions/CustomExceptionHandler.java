package revisao.api.crudrevisao.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import revisao.api.crudrevisao.common.ExceptionResponse;
import revisao.api.crudrevisao.exceptions.user.EmailAlreadyUsedException;
import revisao.api.crudrevisao.exceptions.user.LoginException;
import revisao.api.crudrevisao.utils.StringUtils;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EmailAlreadyUsedException.class})
    public ResponseEntity<Object> emailAlreadyUsedExceptionHandler(RuntimeException exc, WebRequest req) {
        ExceptionResponse response = new ExceptionResponse(exc.getMessage(), LocalDateTime.now(),400,StringUtils.convertPath(req.getDescription(true)));
        return handleExceptionInternal(exc,response,new HttpHeaders(), HttpStatus.BAD_REQUEST,req);
    }

    @ExceptionHandler(value = {LoginException.class})
    public ResponseEntity<Object> loginExceptionHandler(RuntimeException exc, WebRequest req) {
        ExceptionResponse response = new ExceptionResponse(exc.getMessage(), LocalDateTime.now(),401, StringUtils.convertPath(req.getDescription(true)));
        return handleExceptionInternal(exc,response,new HttpHeaders(), HttpStatus.UNAUTHORIZED,req);
    }
}
