package com.stackstone.study.hystrix.demo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * Product
 *
 * @author LiLei
 * @date 2021/8/24
 * @since 1.0.0
 */
@Data
public class Product {
    private String sku;
    private Long productId;
    private BigDecimal price;

    public Product() {
    }
}
