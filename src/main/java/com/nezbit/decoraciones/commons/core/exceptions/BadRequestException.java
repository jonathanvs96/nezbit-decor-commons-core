package com.nezbit.decoraciones.commons.core.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BusinessException {

  public BadRequestException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }

}
