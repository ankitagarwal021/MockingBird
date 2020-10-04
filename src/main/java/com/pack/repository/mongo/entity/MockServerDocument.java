package com.pack.repository.mongo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

/**
 * Created By: Ankit Agarwal
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "mockServer")
public class MockServerDocument {

    @Indexed
    private String Id;

    @Indexed
    @Field("urlPathPattern")
    private String urlPathPattern;

    @Indexed
    @Field("Content-Type")
    private String contentType;

    @Indexed
    @Field("httpMethod")
    private String httpMethod;

    @Indexed
    @Field("headers")
    private Map<String, String> headers;

    @Indexed
    @Field("queryParams")
    private Map<String, String> queryParams;

    @Indexed
    @Field("request")
    private String request;

    @Indexed
    @Field("requestPatternMatch")
    private String requestPatternMatch;

    @Indexed
    @Field("response")
    private String response;

    @Indexed
    @Field("responseSetMethod")
    private String responseSetMethod;

    @Indexed
    @Field("proxyPassUrl")
    private String proxyPassUrl;

}
