package web4.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import web4.exceptions.ConflictException;
import web4.exceptions.UnprocessableEntityException;

import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {
    private ResponseEntity<Map<String, String>> generateErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(
                Map.of("error", status.getReasonPhrase(), "message", message)
        );
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<Map<String, String>> handleUnprocessableEntity(UnprocessableEntityException e) {
        return generateErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                e.getMessage()
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, String>> handleConflictError(ConflictException e) {
        return generateErrorResponse(
                HttpStatus.CONFLICT,
                e.getMessage()
        );
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<Map<String, String>> handleMediaTypeException(HttpMediaTypeException e) {
        return generateErrorResponse(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                "Неверный Content-Type. Ожидаемый application/json"
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(AuthenticationException e) {
        return generateErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Неверное имя пользователя или пароль"
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleIternalServerError(Exception e) {
        return generateErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
//                "Непредвиденная ошибка сервера. Пожалуйста, поробуйте обратиться позже"
                e.getMessage()
        );
    }
}
