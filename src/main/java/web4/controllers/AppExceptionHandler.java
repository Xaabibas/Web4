package web4.controllers;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Map<String, String>> handleConflictError(DuplicateKeyException e) {
        return generateErrorResponse(
                HttpStatus.CONFLICT,
                e.getMessage()
        );
    }

    private ResponseEntity<Map<String, String>> generateErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(
                Map.of("error", status.getReasonPhrase(), "message", message)
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
                "Непредвиденная ошибка сервера. Пожалуйста, поробуйте обратиться позже"
        );
    }
}
