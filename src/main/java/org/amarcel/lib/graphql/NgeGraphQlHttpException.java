package org.amarcel.lib.graphql;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static graphql.ErrorType.ExecutionAborted;

public class NgeGraphQlHttpException extends RuntimeException {

  public HttpStatus status;
  public List<GraphQLError> errors;

  public NgeGraphQlHttpException(HttpStatus status, List<GraphQLError> errors) {
    super(errors.stream().findFirst().map(x -> x.getMessage()).orElse(null));
    this.status = status;
    this.errors = errors;
  }

  public GraphQlHttpError toExecutionResult() {
    return new GraphQlHttpError(errors);
  }

  public static NgeGraphQlHttpException wrapToGraphQlHttpException(Throwable exception) {

    if (exception instanceof NgeGraphQlHttpException) {
      return (NgeGraphQlHttpException) exception;
    } else {
      GraphQLError error = GraphqlErrorBuilder.newError()
        .errorType(ExecutionAborted)
        .message(exception.toString())
        .build();

      ArrayList<GraphQLError> errors = new ArrayList<>();
      errors.add(error);

      return new NgeGraphQlHttpException(
        HttpStatus.INTERNAL_SERVER_ERROR,
        errors
      );
    }
  }
}
