package com.ajmayen.shopservice.controller;

import com.ajmayen.commonmodule.model.CustomHttpResponse;
import com.ajmayen.shopservice.model.Shop;
import com.ajmayen.shopservice.service.ShopService;
import com.ajmayen.commonmodule.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/{shopName}")
    public ResponseEntity<CustomHttpResponse> getShop(@PathVariable String shopName){
        try{
            Shop shop = shopService.getShopData(shopName);
            return ResponseBuilder.buildSuccessResponse(HttpStatus.OK,Map.of("shop",shop));
        }
        catch (Exception ex){

              return ResponseBuilder.buildFailureResponse (HttpStatus.NOT_FOUND,"404", ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<CustomHttpResponse> addShop(@RequestBody Shop shop){
        try {
            shopService.addNewShop(shop);
            return ResponseBuilder.buildSuccessResponse(HttpStatus.OK,Map.of("Message:","Shop added successfully"));
        }
        catch (Exception ex){
            return ResponseBuilder.buildFailureResponse(HttpStatus.BAD_REQUEST, "404",ex.getMessage());
        }
    }
}
