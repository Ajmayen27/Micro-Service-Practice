package com.ajmayen.apigateway.service;

import com.ajmayen.commonmodule.model.CustomHttpRequest;
import com.ajmayen.commonmodule.model.CustomHttpResponse;
import com.ajmayen.commonmodule.util.HttpCallLogic;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShopApiService {

    private static final String SHOP_SERVICE_BASE_URL = "lb://shop-service/shop";

    private final HttpCallLogic httpCallLogic;

    public ShopApiService(HttpCallLogic httpCallLogic) {
        this.httpCallLogic = httpCallLogic;
    }

    public Map<String,Object> callGetShopDataApi(String shopName)  {
        CustomHttpRequest customHttpRequest = new CustomHttpRequest();
        customHttpRequest.setUrl(SHOP_SERVICE_BASE_URL+"/"+shopName);
        customHttpRequest.setMethodType(HttpMethod.GET);
        ResponseEntity<CustomHttpResponse> responseEntity = httpCallLogic.executeRequest(customHttpRequest);
        return responseEntity.getBody().getResponsebody() != null ? responseEntity.getBody().getResponsebody() : responseEntity.getBody().getErrorbody();
    }

   public Map<String,Object> callSaveShopDataApi(Map<String,Object> shopData) {
       ResponseEntity<CustomHttpResponse> responseEntity = httpCallLogic.executeRequest(null);
        return null;
   }
}
