package com.example.demooooo.shop.repository;


import com.example.demooooo.shop.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    Optional<Shop> findShopByUserid(Integer userid);
    Optional<Shop> findShopByShopname(String shopname);
}
