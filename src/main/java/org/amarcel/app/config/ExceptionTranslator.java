package org.amarcel.app.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.amarcel.lib.graphql.NgeGraphQlHttpException;
import org.amarcel.lib.graphql.GraphQlHttpError;

@ControllerAdvice
public class ExceptionTranslator {

  @ExceptionHandler(NgeGraphQlHttpException.class)
  public ResponseEntity<GraphQlHttpError> ngeGraphQlHttpException(NgeGraphQlHttpException ex) {
    return ResponseEntity.status(ex.status)
      .body(ex.toExecutionResult());
  }

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<GraphQlHttpError> otherExceptions(Throwable ex) {
    return ngeGraphQlHttpException(NgeGraphQlHttpException.wrapToGraphQlHttpException(ex));
  }
}
