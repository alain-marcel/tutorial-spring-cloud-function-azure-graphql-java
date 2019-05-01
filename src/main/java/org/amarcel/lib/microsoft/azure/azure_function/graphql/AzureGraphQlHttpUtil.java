package org.amarcel.lib.microsoft.azure.azure_function.graphql;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;

import org.amarcel.lib.graphql.GraphQlHttpOk;
import org.amarcel.lib.graphql.NgeGraphQlHttpException;

public class AzureGraphQlHttpUtil {

  private static HttpResponseMessage buildHttpResponse(HttpStatus status, HttpRequestMessage<?> azureRequest, Object body) {
    com.microsoft.azure.functions.HttpStatus azureHttpStatus = com.microsoft.azure.functions.HttpStatus.valueOf(status.value());
    HttpResponseMessage.Builder azureResponse = azureRequest.createResponseBuilder(azureHttpStatus);
    azureResponse.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString());
    azureResponse.body(body);
    return azureResponse.build();
  }

  public static HttpResponseMessage okHttp(HttpRequestMessage<?> azureRequest, GraphQlHttpOk body) {
    return buildHttpResponse(HttpStatus.OK, azureRequest, body);
  }

  public static HttpResponseMessage handleException(Throwable exception, HttpRequestMessage<?> azureRequest) {
    NgeGraphQlHttpException ex = NgeGraphQlHttpException.wrapToGraphQlHttpException(exception);
    return buildHttpResponse(ex.status, azureRequest, ex.toExecutionResult());
  }
}
