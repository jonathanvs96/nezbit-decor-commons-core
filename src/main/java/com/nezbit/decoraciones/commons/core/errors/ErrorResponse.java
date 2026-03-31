package com.nezbit.decoraciones.commons.core.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Respuesta de error")
public record ErrorResponse(
    @Schema(description = "Mensaje de error")
    String message,

    @Schema(description = "Lista de errores registrados")
    List<String> errors,

    @Schema(description = "Código HTTP")
    int statusCode,

    @Schema(description = "HTTP Status")
    String status,

    @Schema(description = "Path del request")
    String path
) {

  public ErrorResponse(String message, List<String> errors, HttpStatus httpStatus, String path){
    this(message, errors, httpStatus.value(), httpStatus.name(), path);
  }

  public ErrorResponse(String message, HttpStatus httpStatus, String path){
    this(message, null, httpStatus.value(), httpStatus.name(), path);
  }

  public ErrorResponse(String message, HttpStatus httpStatus){
    this(message, null, httpStatus.value(), httpStatus.name(), null);
  }

}