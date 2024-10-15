package com.ajmayen.shopservice.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "shop_table")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shop_id")
    private Integer shopId;

    @Column(name = "Shop_name")
    private String shopName;

    @Column(name = "category")
    private String category;
}
