package com.example.demooooo.shop.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopid")
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
