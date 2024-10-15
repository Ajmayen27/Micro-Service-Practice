package com.ajmayen.shopservice.service;

import com.ajmayen.shopservice.model.Shop;
import com.ajmayen.shopservice.repository.ShopRepository;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public void addNewShop(Shop shop) throws Exception {
        Shop existingshop = shopRepository.findByShopName(shop.getShopName());
        if (existingshop != null) {
            throw new Exception("Shop with this name already exists");
        }
        shopRepository.save(shop);
    }

    public Shop getShopData(String shopName) throws Exception {
       Shop existingshop = shopRepository.findByShopName(shopName);

       if(existingshop == null){
         throw new Exception("No Shop exist with this name");
       }
       return existingshop;
    }
}
