package org.amarcel.app.azure;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.amarcel.app.functions.graphql.GraphQlFunctionConfiguration;
import org.amarcel.lib.graphql.GraphQlHttpOk;
import org.amarcel.lib.graphql.GraphQlHttpRequest;
import org.amarcel.lib.microsoft.azure.azure_function.graphql.AzureGraphQlHttpUtil;
import org.springframework.cloud.function.adapter.azure.AzureSpringBootRequestHandler;

public class GraphQlAzureFunction extends AzureSpringBootRequestHandler<GraphQlHttpRequest, GraphQlHttpOk> {

  @FunctionName(GraphQlFunctionConfiguration.FUNCTION_NAME)
  public HttpResponseMessage execute(
    @HttpTrigger(
      name = "graphQlRequest",
      methods = HttpMethod.POST,
      authLevel = AuthorizationLevel.ANONYMOUS
    )
      HttpRequestMessage<GraphQlHttpRequest> azureRequest,
    ExecutionContext context
  ) {

    System.out.println("====  GraphQlAzureFunction.execute ");

    try {
      return AzureGraphQlHttpUtil.okHttp(
        azureRequest,

        handleRequest(
          azureRequest.getBody(),
          context
        )
      );
    } catch (Throwable ex) {
      return AzureGraphQlHttpUtil.handleException(ex, azureRequest);
    }
  }
}



