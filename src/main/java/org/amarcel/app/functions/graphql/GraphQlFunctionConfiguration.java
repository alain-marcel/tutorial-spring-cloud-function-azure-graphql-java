package org.amarcel.app.functions.graphql;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import graphql.servlet.GraphQLConfiguration;

import org.amarcel.lib.graphql.processor.GraphQlProcessor;
import org.amarcel.lib.graphql.GraphQlHttpRequest;
import org.amarcel.lib.graphql.GraphQlHttpOk;

@Configuration
public class GraphQlFunctionConfiguration {

  public static final String FUNCTION_NAME = "graphql";
  
  @Bean
  public GraphQlProcessor graphQlProcessor(GraphQLConfiguration graphQLServletConfiguration) {
    return new GraphQlProcessor(graphQLServletConfiguration);
  }

  @Bean(GraphQlFunctionConfiguration.FUNCTION_NAME)
  public Function<GraphQlHttpRequest, GraphQlHttpOk> graphQl(GraphQlProcessor graphQlProcessor) {
    return graphQlRequest -> graphQlProcessor.process(graphQlRequest);
  }
}
