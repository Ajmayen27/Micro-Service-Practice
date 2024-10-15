package com.ajmayen.apigateway.controller;

import com.ajmayen.apigateway.service.ShopApiService;
import com.ajmayen.apigateway.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/shop-api")
public class ShopController {
   private final ShopApiService shopApiService;

    public ShopController(ShopApiService shopApiService) {
        this.shopApiService = shopApiService;
    }

    @GetMapping("/{shopName}")
    public ResponseEntity<?> getShopData(@PathVariable String shopName)  {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, shopApiService.callGetShopDataApi(shopName));
    }

    @PostMapping
    public ResponseEntity<?> getShopData(@RequestBody Map<String,Object> shopData)  {
        return ResponseBuilder.buildSuccessResponse(HttpStatus.OK, shopApiService.callSaveShopDataApi(shopData));
    }
}
