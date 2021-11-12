package com.example.demooooo.shop.shopDto;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "shop")
public class ShopDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idshop")
    private int id;

    @Column(name = "shopname")
    private String shopname;

    @Column(name = "shopaddress")
    private String shopaddress;

    @Column(name = "shoptel")
    private String shoptel;

    @Column(name = "shopdesc")
    private String shopdesc;

    @Column(name = "userid")
    private int userid;
}
