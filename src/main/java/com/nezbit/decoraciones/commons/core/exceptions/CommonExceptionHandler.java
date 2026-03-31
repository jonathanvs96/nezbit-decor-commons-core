package com.nezbit.decoraciones.commons.core.exceptions;

import com.nezbit.decoraciones.commons.core.errors.BadRequestResponse;
import com.nezbit.decoraciones.commons.core.errors.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CommonExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(HttpServletRequest request, BusinessException ex){
    final var errorResponse = new ErrorResponse(ex.getMessage(), ex.getHttpStatus(), request.getRequestURI());
    return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<BadRequestResponse> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex){

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> {
          errors.put(error.getField(), error.getDefaultMessage());
        });
    final var errorResponse = new BadRequestResponse(errors, request.getRequestURI());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<?> handleEnumParseError(HttpServletRequest request, HttpMessageNotReadableException ex) {

    if (ex.getMessage().contains("UserType")) {
      return ResponseEntity.badRequest().body(
          new BadRequestResponse(Map.of("userType", "userType must be one of: ADVISOR or CLIENT"), request.getRequestURI()));
    }

    return ResponseEntity.badRequest().body(
        new ErrorResponse("Malformed request", HttpStatus.BAD_REQUEST, request.getRequestURI())
    );
  }


  @ExceptionHandler({
      ObjectOptimisticLockingFailureException.class,
      OptimisticLockingFailureException.class
  })
  public ResponseEntity<ErrorResponse> handleOptimisticLockingException(
      HttpServletRequest request,
      Exception ex
  ) {
    var error = new ErrorResponse(
        "El registro fue modificado por otro usuario. Recarga la información e inténtalo de nuevo.",
        HttpStatus.CONFLICT,
        request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(ConcurrentUpdateException.class)
  public ResponseEntity<ErrorResponse> handleConcurrentUpdateException(
      HttpServletRequest request,
      ConcurrentUpdateException ex
  ) {
    var error = new ErrorResponse(
        ex.getMessage(),
        HttpStatus.CONFLICT,
        request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

}
