package com.example.domain;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class Order {
    private Long id;
    private Long orderNo;
    private Long buyerId;
    private Long sellerId;
    private BigDecimal amount;
    private Integer status;
}