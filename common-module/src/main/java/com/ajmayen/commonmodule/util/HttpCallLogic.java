package com.ajmayen.commonmodule.util;

import com.ajmayen.commonmodule.model.CustomHttpRequest;
import com.ajmayen.commonmodule.model.CustomHttpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Component
public class HttpCallLogic {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public HttpCallLogic(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public ResponseEntity<CustomHttpResponse> executeRequest(CustomHttpRequest customHttpRequest){
      try {
          HttpMethod httpMethod = customHttpRequest.getMethodType();
          URI uri = prepareRequestUri(customHttpRequest);
          HttpHeaders requesrtHeaders = prepareRequestHeaders(customHttpRequest);
          Map<String, ?> requestBody = prepareRequestBody(customHttpRequest);
          HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, requesrtHeaders);
          return restTemplate.exchange(uri, httpMethod, null, CustomHttpResponse.class);
      } catch (HttpStatusCodeException ex) {
          return handle4xx5xxErrorResponse(ex.getStatusCode(),ex.getResponseBodyAsString());
      }catch (Exception ex){
          throw new RuntimeException(ex);
      }

    }

    private ResponseEntity<CustomHttpResponse> handle4xx5xxErrorResponse(HttpStatusCode httpStatusCode, String errorResponseBody) {
        try {
            CustomHttpResponse customHttpResponse = objectMapper.readValue(errorResponseBody,CustomHttpResponse.class);
            if(customHttpResponse.getHttpStatus() == null || customHttpResponse.getErrorbody() == null){
                //errorcase
               Map errorBody = objectMapper.readValue(errorResponseBody,Map.class);
               Integer status = (Integer) errorBody.get("status");
               String error = (String)errorBody.get("error");
               String path = (String)errorBody.get("path");
               customHttpResponse.setHttpStatus(HttpStatus.valueOf(status));
               customHttpResponse.setErrorbody(Map.of("code",status,"message",error+"-"+path));
            }
            return new ResponseEntity<>(customHttpResponse,httpStatusCode);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }

    }

    private URI prepareRequestUri(CustomHttpRequest customHttpRequest) throws URISyntaxException {
        String url = customHttpRequest.getUrl();
        Map<String,String> queryParameterMap = customHttpRequest.getQueryParametarMap();
        if(queryParameterMap != null && !queryParameterMap.isEmpty()){
            String queryParameters = buildQueryParameters(queryParameterMap);
            return new URI(url+ "?" + queryParameters);
        }
        return new URI(url);
    }

    private String buildQueryParameters(Map<String,String> queryParameterMap){
        StringBuilder queryParameters = new StringBuilder();
        for(Map.Entry<String,String> queryParameter : queryParameterMap.entrySet()){
            queryParameters
                    .append(queryParameter.getKey())
                    .append("=")
                    .append(queryParameter.getValue())
                    .append("&");
        }
        return queryParameters.substring(0,queryParameters.length() - 1);
    }

    private HttpHeaders prepareRequestHeaders(CustomHttpRequest customHttpRequest){
        Map<String,String> headerParameterMap = customHttpRequest.getHeadParametarMap();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Request-Id",customHttpRequest.getRequestId());
        if(headerParameterMap != null && !headerParameterMap.isEmpty()){
           for(Map.Entry<String,String> header : headerParameterMap.entrySet()){
               httpHeaders.add(header.getKey(),header.getValue());
           }
        }
        return  httpHeaders;
    }

    private Map<String,?> prepareRequestBody(CustomHttpRequest customHttpRequest){
        Map<String, Object> bodyParametarMap = customHttpRequest.getBodyParametarMap();
        if(bodyParametarMap != null && !bodyParametarMap.isEmpty()){
            if(isMultipartFormDataPresent(customHttpRequest)){
                return prepareMultiValueRequestBody(bodyParametarMap);
            }
            return bodyParametarMap;
        }
        return new HashMap<>();
    }

    private boolean isMultipartFormDataPresent(CustomHttpRequest customHttpRequest) {
        Map<String,String> headerParameterMap = customHttpRequest.getHeadParametarMap();
        if(headerParameterMap != null && !headerParameterMap.containsKey("Content-Type")){
            return headerParameterMap.get("Content-Type").equals("multipart/form-data");
        }
        return false;
    }

    private MultiValueMap<String,Object> prepareMultiValueRequestBody(Map<String, Object> bodyParametarMap) {
          MultiValueMap<String,Object> requestBody = new LinkedMultiValueMap<>();
          for(Map.Entry<String,Object> bodyParameter : bodyParametarMap.entrySet()){
             if(bodyParameter.getValue() instanceof MultipartFile[] multipartFiles){
                 for(MultipartFile multipartFile : multipartFiles){
                     requestBody.add(bodyParameter.getKey(),multipartFile.getResource());
                 }
             }
             else{
                 requestBody.add(bodyParameter.getKey(),bodyParameter.getValue());
             }
          }
          return requestBody;
    }

}




