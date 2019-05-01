package org.amarcel.lib.graphql;

import graphql.GraphQLError;

import java.util.List;

public class GraphQlHttpError {
  List<GraphQLError>  errors;

  GraphQlHttpError() {
  }

  GraphQlHttpError(List<GraphQLError> errors) {
    this.errors = errors;
  }
}
