package com.nezbit.decoraciones.commons.core.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BusinessException {
  public NotFoundException(){
    super(HttpStatus.NOT_FOUND, "No se encontró el objeto solicitado");
  }

  public NotFoundException(String message){
    super(HttpStatus.NOT_FOUND, message);
  }
}
