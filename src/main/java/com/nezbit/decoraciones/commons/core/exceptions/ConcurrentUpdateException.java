package com.nezbit.decoraciones.commons.core.exceptions;

public class ConcurrentUpdateException extends RuntimeException {
  public ConcurrentUpdateException(String message) {
    super(message);
  }
}
