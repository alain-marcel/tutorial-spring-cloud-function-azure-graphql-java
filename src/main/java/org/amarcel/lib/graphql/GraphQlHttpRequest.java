package org.amarcel.lib.graphql;

public class GraphQlHttpRequest {
  public String query;

  public GraphQlHttpRequest() {
  }

  @Override
  public String toString() {
    return String.format("GraphQlHttpRequest{query='%s'}", query);
  }
}

