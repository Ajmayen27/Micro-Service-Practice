package com.ajmayen.shopservice.repository;

import com.ajmayen.shopservice.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

    Shop findByShopName(String shopName);
}
