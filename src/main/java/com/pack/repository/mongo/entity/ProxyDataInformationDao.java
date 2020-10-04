package com.pack.repository.mongo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created By: Ankit Agarwal
 **/

@Document("proxyUrlDocument")
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProxyDataInformationDao {

    @Indexed
    private String urlPattern;

    @Indexed
    private String proxyURL;

    @Indexed
    private String className;

    @Indexed
    private String methodToInvoke;

}