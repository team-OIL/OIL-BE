package com.example.OIL.common.error;

import com.example.OIL.common.error.exception.ErrorCode;
import com.example.OIL.common.error.exception.ErrorProperty;
import com.example.OIL.common.error.exception.OILException;
import com.example.OIL.common.response.ApiResponse;
import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //OILException 처리
    @ExceptionHandler(OILException.class)
    public ResponseEntity<ApiResponse<?>> handleOILException(OILException e) {
        ErrorProperty error = e.getErrorProperty();
        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponse.fail(error.getCode(), error.getMessage(), error.getStatus()));
    }

    // @Valid 실패 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {

        FieldError fieldError = e.getBindingResult().getFieldError();
        String defaultMessage = fieldError != null ? fieldError.getDefaultMessage() : "Validation error";

        ErrorProperty error = ErrorCode.INVALID_INPUT_VALUE;

        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponse.fail(error.getCode(), defaultMessage, error.getStatus()));
    }

    // BindException (@ModelAttribute, @PathVariable 등 바인딩 시)
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ApiResponse<?>> handleBindException(BindException e) {

        FieldError fieldError = e.getBindingResult().getFieldError();
        String defaultMessage = fieldError != null ? fieldError.getDefaultMessage() : "Bind error";

        ErrorProperty error = ErrorCode.INVALID_INPUT_VALUE;

        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponse.fail(error.getCode(), defaultMessage, error.getStatus()));
    }

    // JSON parse 실패
    @ExceptionHandler(JsonParseException.class)
    protected ResponseEntity<ApiResponse<?>> handleJsonParseException(JsonParseException e) {

        ErrorProperty error = ErrorCode.JSON_PARSE_ERROR;

        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponse.fail(error.getCode(), error.getMessage(), error.getStatus()));
    }

    // 허용되지 않은 HTTP 메소드
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResponse<?>> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {

        ErrorProperty error = ErrorCode.METHOD_NOT_ALLOWED;

        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponse.fail(error.getCode(), error.getMessage(), error.getStatus()));
    }

    // Spring Security 인증 실패
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<ApiResponse<?>> handleAuthenticationException(AuthenticationException e) {

        ErrorProperty error = ErrorCode.INVALID_TOKEN;

        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponse.fail(error.getCode(), error.getMessage(), error.getStatus()));
    }

    // 그 외 모든 예외
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        e.printStackTrace();

        ErrorProperty error = ErrorCode.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponse.fail(error.getCode(), error.getMessage(), error.getStatus()));
    }

}
