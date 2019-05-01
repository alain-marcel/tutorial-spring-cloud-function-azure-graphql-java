package org.amarcel.lib.graphql;

import com.fasterxml.jackson.databind.JsonNode;

public class GraphQlHttpOk {
    public JsonNode data;

    public GraphQlHttpOk() {}

    @Override
    public String toString() {
        return String.format("GraphQlHttpOk{data='%s'}", data);
    }
}
