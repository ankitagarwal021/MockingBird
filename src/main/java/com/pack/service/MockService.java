package com.pack.service;

import com.pack.repository.mongo.entity.MockServerDocument;
import com.pack.repository.mongo.entity.ProxyDataInformationDao;
import com.pack.service.impl.MockServiceImpl;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.MongoRegexCreator;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * Created By: Ankit Agarwal
 **/

@Service
public class MockService implements MockServiceImpl {

    @Autowired
    @Qualifier("mongoDbReference")
    MongoTemplate mongoTemplate;


    public MultiValueMap<String, String> convertToMultiValuedMap(Map<String, String> header){
        MultiValueMap multiValueMap = new HttpHeaders();
        for(String key: header.keySet()){
            multiValueMap.add(key,header.get(key));
        }
        return multiValueMap;
    }

    @Override
    public Object getMockResponse(HttpServletRequest request, String data, Map<String,String> queryParams,
                Map<String, String> headerParams) {

        Query query = new Query();

        //decrypt the data in encryption is being used for communcation between microservices
        List<Criteria> paramsToQuery = new ArrayList<>();
        Enumeration headerEnum = request.getHeaderNames();      //getting headers
        Enumeration queryEnum = request.getParameterNames();    //getting queryParams

        if (headerEnum!=null){
            while (headerEnum.hasMoreElements()){
                String header = headerEnum.nextElement().toString();
                if (request.getHeader(header)!=null){
                    paramsToQuery.add(Criteria.where("headers."+header).regex(MongoRegexCreator.INSTANCE.toRegularExpression(
                            request.getHeader(header), MongoRegexCreator.MatchMode.REGEX)));
                }
            }
        }

        if (queryEnum!=null){
            while (queryEnum.hasMoreElements()){
                String queryParam = queryEnum.nextElement().toString();
                if (request.getParameter(queryParam)!=null){
                    paramsToQuery.add(Criteria.where("queryParams."+queryParam).regex(MongoRegexCreator.INSTANCE
                            .toRegularExpression(request.getParameter(queryParam), MongoRegexCreator.MatchMode.CONTAINING)));
                }
            }
        }

        if (request.getRequestURI()!=null){
            query.addCriteria(Criteria.where("urlPathPattern").regex(request.getRequestURI()
                    .replace("/mock","")));
        }

        if (request.getMethod()!=null){
            query.addCriteria(Criteria.where("httpMethod").regex(request.getMethod()));
        }
        Criteria existscriteria = Criteria.where("httpMethod").exists(true);
        paramsToQuery.add(existscriteria);
        if (data!=null){
            query.addCriteria(TextCriteria.forDefaultLanguage().matching(data));
        }

        query.addCriteria(new Criteria().orOperator(paramsToQuery.toArray(new Criteria[paramsToQuery.size()])));
        System.out.println(query);

        MockServerDocument mockServerDocument = mongoTemplate.findOne(query,MockServerDocument.class);

        if (mockServerDocument==null){
            Query downstreamURL = new Query();
            downstreamURL.addCriteria(Criteria.where("urlPattern").regex(MongoRegexCreator.INSTANCE.toRegularExpression(
                    request.getRequestURI().replace(
                    "/mock",""), MongoRegexCreator.MatchMode.CONTAINING)));

            //Getting the actual downstream URL, when mock is not found in the database, so that we can get the
            // actual response
            ProxyDataInformationDao proxyDataInformationDao = mongoTemplate.findOne(downstreamURL,ProxyDataInformationDao.class);

            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                    HttpClientBuilder.create().useSystemProperties().build());

            RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
            UriComponentsBuilder componentsBuilder = UriComponentsBuilder.fromHttpUrl(
                    proxyDataInformationDao.getProxyURL()+request.getRequestURI().replace("/mock",""));
            URI uri = componentsBuilder.buildAndExpand(queryParams).encode().toUri();

            HttpEntity<String> requestEntity = new HttpEntity<>(data,convertToMultiValuedMap(headerParams));

            if (request.getMethod().equals("POST")){
                ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST,requestEntity,String.class);
                return responseEntity.getBody();
            }else if (request.getMethod().equals("GET")){
                ResponseEntity<String> responseEntity = restTemplate.exchange(uri,HttpMethod.GET,requestEntity,String.class);
                return responseEntity.getBody();
            }else if (request.getMethod().equals("PUT")){
                ResponseEntity<String> responseEntity = restTemplate.exchange(uri,HttpMethod.PUT,requestEntity,String.class);
                return responseEntity.getBody();
            }else if (request.getMethod().equals("DELETE")){
                ResponseEntity<String> responseEntity = restTemplate.exchange(uri,HttpMethod.DELETE,requestEntity,String.class);
                return responseEntity.getBody();
            }
        }
        return mockServerDocument.getResponse();
    }
}
