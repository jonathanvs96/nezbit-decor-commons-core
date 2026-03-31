package com.nezbit.decoraciones.commons.core.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Schema(description = "Respuesta BadRequest")
public record BadRequestResponse(
    @Schema(description = "Errores en la petición")
    Map<String, String> errors,

    @Schema(description = "Código HTTP")
    int statusCode,

    @Schema(description = "HTTP Status")
    String status,

    @Schema(description = "Path del request")
    String path
) {
  public BadRequestResponse(Map<String,String> errors, String path){
    this(errors, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), path);
  }
}
