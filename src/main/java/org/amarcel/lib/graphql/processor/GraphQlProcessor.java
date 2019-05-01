//package org.amarcel.lib.graphql.processor
//
//import scala.collection.JavaConverters._
//
//import org.springframework.http.HttpStatus
//import org.springframework.http.HttpStatus.{BAD_REQUEST, INTERNAL_SERVER_ERROR}
//import org.springframework.stereotype.Component
//
//import com.fasterxml.jackson.databind.JsonNode
//import graphql.ErrorType.{ExecutionAborted, ValidationError}
//import graphql.GraphqlErrorBuilder.newError
//import graphql._
//import graphql.servlet._
//import graphql.servlet.internal.GraphQLRequest
//import org.amarcel.lib.nge.NgeCommonExceptionFactory.{invalidGraphQl, unexpectedError}
//import org.amarcel.lib.nge.NgeErrorType
//import org.amarcel.lib.graphql.{GraphQlHttpOk, GraphQlHttpRequest, NgeGraphQlHttpException}
//import org.amarcel.lib.nge.exception.NgeException
//
//@Component
//class GraphQlProcessor(configuration: GraphQLConfiguration) {
//
//  println("ZZA===============================  GraphQlProcessor constructor ")
//
//  /**
//    * Copy / paste from `AbstractGraphQLHttpServlet.doRequest()`.
//    */
//  def process(request: GraphQlHttpRequest): GraphQlHttpOk = {
//    val result =
//      try {
//        handleRequest(request)
//      } catch {
//        case e: Throwable => toFailedExecutionResult(e)
//      }
//
//    if ( result.getErrors != null && !result.getErrors.isEmpty ) {
//      throw handleErrors(result.getErrors.asScala.toList)
//    }
//
//    handleData(result.getData)
//  }
//
//  /**
//    * Copy / paste from `AbstractGraphQLHttpServlet.getHandler` lambda.
//    */
//  private def handleRequest(request: GraphQlHttpRequest): ExecutionResult = {
//    val query: String = request.query
//
//    if ( isBatchedQuery(query) ) {
//      // see `AbstractGraphQLHttpServlet.queryBatched()`
//      // issue to manage: each query might have specific variables
//      throw new NotImplementedError("Batched GraphQL request not supported")
//    }
//
////    val variables = request.variables.map(x => x.mapValues(_.asInstanceOf[AnyRef]).asJava).orNull
////    val graphQLRequest = new GraphQLRequest(query, variables, request.operationName.orNull)
//
//    val graphQLRequest = new GraphQLRequest(query, null, null)
//    handleSingleQuery(configuration.getInvocationInputFactory.create(graphQLRequest))
//  }
//
//  /**
//    * Copy / paste from `AbstractGraphQLHttpServlet.query()`.
//    */
//  private def handleSingleQuery(invocationInput: GraphQLSingleInvocationInput): ExecutionResult = {
//    configuration.getQueryInvoker.query(invocationInput)
//  }
//
//  private def handleData(data: Any): GraphQlHttpOk = {
//    val objectMapper = configuration.getObjectMapper.getJacksonMapper
//
//    val r = new GraphQlHttpOk()
//    r.data = objectMapper.convertValue(data, classOf[JsonNode])
//    r
//  }
//
//  private def handleErrors(errors: List[GraphQLError]): NgeGraphQlHttpException = {
//    val status: HttpStatus =
//      errors
//        .map(error => toHttpStatus(error.getErrorType))
//        .maxBy(x => x.value())
//
//    new NgeGraphQlHttpException(status, errors.asJava)
//  }
//
//  private def toHttpStatus(errorType: ErrorClassification): HttpStatus = {
//    if ( errorType == null || !errorType.isInstanceOf[ErrorType] ) {
//      return INTERNAL_SERVER_ERROR
//    }
//    errorType.asInstanceOf[ErrorType] match {
//      case ErrorType.InvalidSyntax => BAD_REQUEST
//      case ValidationError => BAD_REQUEST
//      case ErrorType.DataFetchingException => BAD_REQUEST
//      case ErrorType.OperationNotSupported => INTERNAL_SERVER_ERROR
//      case ErrorType.ExecutionAborted => INTERNAL_SERVER_ERROR
//      case _ => INTERNAL_SERVER_ERROR
//    }
//  }
//
//  private def toFailedExecutionResult(e: Throwable): ExecutionResult = {
//    val errorInfo: (ErrorType, NgeException) =
//      e match {
//        case e: NgeException =>
//          val errorType: ErrorType =
//            e.errorType match {
//              case NgeErrorType.InvalidData => graphql.ErrorType.ValidationError
//              case NgeErrorType.NotFound => graphql.ErrorType.DataFetchingException
//              case _ => graphql.ErrorType.ExecutionAborted
//            }
//          (errorType, e)
//
//        case e: GraphQLException => (ValidationError, invalidGraphQl(e))
//        case e: Throwable => (ExecutionAborted, unexpectedError(e))
//      }
//
//    new ExecutionResultImpl(
//      newError()
//        .errorType(errorInfo._1)
//        .message(errorInfo._2.error.message)
//        // todo: wrap errorInfo._2 to a case class to have same serialization as described in NgeException_JciCodec.encode_NgeException
//        .extensions(toMap(errorInfo._2))
//        .build()
//    )
//  }
//


package org.amarcel.lib.graphql.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.*;
import graphql.servlet.GraphQLConfiguration;
import graphql.servlet.GraphQLSingleInvocationInput;
import graphql.servlet.internal.GraphQLRequest;
import graphql.validation.ValidationError;
import org.amarcel.lib.graphql.GraphQlHttpOk;
import org.amarcel.lib.graphql.GraphQlHttpRequest;
import org.amarcel.lib.graphql.NgeGraphQlHttpException;
import org.amarcel.lib.nge.NgeCommonExceptionFactory;
import org.amarcel.lib.nge.NgeErrorType;
import org.amarcel.lib.nge.exception.NgeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import graphql.ErrorType;

@Component
public class GraphQlProcessor {

  public GraphQLConfiguration configuration;

  public GraphQlProcessor(GraphQLConfiguration configuration) {
    System.out.println("====  GraphQlProcessor constructor ");
    this.configuration = configuration;
  }


  /**
   * Copy / paste from `AbstractGraphQLHttpServlet.doRequest()`.
   */
  public GraphQlHttpOk process(GraphQlHttpRequest request) {

    ExecutionResult result;
    try {
      result = handleRequest(request);
    } catch (Throwable ex) {
      result = toFailedExecutionResult(ex);
    }

    if (result.getErrors() != null && !result.getErrors().isEmpty()) {
      throw handleErrors(result.getErrors());
    }

    return handleData(result.getData());
  }

  /**
   * Copy / paste from `AbstractGraphQLHttpServlet.getHandler` lambda.
   */
  private ExecutionResult handleRequest(GraphQlHttpRequest request) {
    String query = request.query;

    if (isBatchedQuery(query)) {
      // see `AbstractGraphQLHttpServlet.queryBatched()`
      // issue to manage: each query might have specific variables
      throw new UnsupportedOperationException("Batched GraphQL request not supported");
    }

//    val variables = request.variables.map(x => x.mapValues(_.asInstanceOf[AnyRef]).asJava).orNull
//    val graphQLRequest = new GraphQLRequest(query, variables, request.operationName.orNull)

    GraphQLRequest graphQLRequest = new GraphQLRequest(query, null, null);
    return handleSingleQuery(configuration.getInvocationInputFactory().create(graphQLRequest));
  }

  /**
   * Copy / paste from `AbstractGraphQLHttpServlet.query()`.
   */
  private ExecutionResult handleSingleQuery(GraphQLSingleInvocationInput invocationInput) {
    return configuration.getQueryInvoker().query(invocationInput);
  }

  private GraphQlHttpOk handleData(Object data) {
    ObjectMapper objectMapper = configuration.getObjectMapper().getJacksonMapper();

    GraphQlHttpOk r = new GraphQlHttpOk();
    r.data = objectMapper.convertValue(data, JsonNode.class);
    return r;
  }

  private NgeGraphQlHttpException handleErrors(List<GraphQLError> errors) {
    Optional<HttpStatus> statusOpt = errors
      .stream()
      .map(error -> toHttpStatus(error.getErrorType()))
      .max(Comparator.comparing(HttpStatus::value));

    return new NgeGraphQlHttpException(statusOpt.get(), errors);
  }

  private HttpStatus toHttpStatus(ErrorClassification errorType) {
    if (errorType == null || !(errorType instanceof ErrorType)) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    switch ((ErrorType) errorType) {
      case InvalidSyntax:
        return HttpStatus.BAD_REQUEST;
      case ValidationError:
        return HttpStatus.BAD_REQUEST;
      case DataFetchingException:
        return HttpStatus.INTERNAL_SERVER_ERROR;
      case OperationNotSupported:
        return HttpStatus.INTERNAL_SERVER_ERROR;
      case ExecutionAborted:
        return HttpStatus.INTERNAL_SERVER_ERROR;
      default:
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
  }

  private ExecutionResult toFailedExecutionResult(Throwable ex) {
    class Res {
      ErrorType errorType;
      NgeException ngeException;

      Res(ErrorType errorType, NgeException ngeException) {
        this.errorType = errorType;
        this.ngeException = ngeException;
      }
    }

    // Set res
    Res res;
    if (ex instanceof NgeException) {
      NgeException nex = (NgeException)ex;
      ErrorType errorType ;
      switch (nex.errorType ) {
        case InvalidData :
          errorType = ErrorType.ValidationError;
        case NotFound :
          errorType = ErrorType.DataFetchingException;
        default :
          errorType = ErrorType.ExecutionAborted;
      }
      res = new Res(errorType, nex);

    } else if (ex instanceof GraphQLException) {
      res = new Res(ErrorType.ValidationError, NgeCommonExceptionFactory.invalidGraphQl(ex));
    } else {
      res = new Res(ErrorType.ExecutionAborted, NgeCommonExceptionFactory.unexpectedError(ex));
    }

    // End
    return new ExecutionResultImpl(
      GraphqlErrorBuilder.newError()
        .errorType(res.errorType)
        .message(res.ngeException.error.message)
        // todo: wrap errorInfo._2 to a case class to have same serialization as described in NgeException_JciCodec.encode_NgeException
        .extensions(toMap(res.ngeException))
        .build()
    );
  }


  private java.util.Map<String, Object> toMap(Object obj) {
    Map res = configuration
      .getObjectMapper()
      .getJacksonMapper()
      .convertValue(obj, Map.class);

    return res;
  }

  /**
   * @return true if the first non whitespace character is the beginning of an array
   */
  private Boolean isBatchedQuery(String query) {
    for (int i = 0; i < query.length(); i++) {
      char ch = query.charAt(i);
      if (!Character.isWhitespace(ch))
        return ch == '[';
    }

    return false;
  }
}
