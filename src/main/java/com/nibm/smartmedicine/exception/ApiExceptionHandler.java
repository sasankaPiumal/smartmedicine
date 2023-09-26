package com.nibm.smartmedicine.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;


@ControllerAdvice
public class ApiExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    /**
     * resource not found
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {
        return this.getExceptionResponse(exception.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * unauthorized
     *
     * @param exception
     * @return
     */
    @Order(1)
    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception, HttpServletRequest request) {
        return this.getExceptionResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED, request);
    }

    /**
     * conflict
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {ConflictException.class})
    public ResponseEntity<Object> handleConflictException(ConflictException exception, HttpServletRequest request) {
        return this.getExceptionResponse(exception.getMessage(), HttpStatus.CONFLICT, request);
    }

    /**
     * http message not readable
     * example: bad request body
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        return this.getExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * access denied
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        return this.getExceptionResponse(exception.getMessage(), HttpStatus.FORBIDDEN, request);
    }

    /**
     * validation exception
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(BindException exception, HttpServletRequest request) {
        StringBuilder messageBuilder = new StringBuilder();
        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
                messageBuilder.append(",").append(fieldError.getDefaultMessage())
        );
        return this.getExceptionResponse(messageBuilder.toString().substring(1), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * runtime exception
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception, HttpServletRequest request) {
        return this.getExceptionResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * bad request exception
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException exception, HttpServletRequest request) {
        return this.getExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * request binding exception(ex: null required request param)
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value = ServletRequestBindingException.class)
    public ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException exception, HttpServletRequest request) {
        return this.getExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Constraint Violation Exception
     * When add same value to unique column, this exception will throw
     *
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(value = NonTransientDataAccessException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(NonTransientDataAccessException exception, HttpServletRequest request) {
        return this.getExceptionResponse(Objects.requireNonNull(exception.getRootCause()).getMessage(), HttpStatus.CONFLICT, request);
    }

    /**
     * generate Custom exception CustomerResponse Entity
     *
     * @param message
     * @param status
     * @param request
     * @return
     */
    private ResponseEntity<Object> getExceptionResponse(String message, HttpStatus status, HttpServletRequest request) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                status.getReasonPhrase(),
                message,
                status.value(),
                ZonedDateTime.now(ZoneId.of("Z")),
                request.getContextPath().concat(request.getServletPath()));
        return new ResponseEntity<>(apiExceptionResponse, status);
    }
}
