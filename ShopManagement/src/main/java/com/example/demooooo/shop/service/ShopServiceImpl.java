package com.example.demooooo.shop.service;


import com.example.demooooo.shop.mapper.ShopMapper;
import com.example.demooooo.shop.repository.ShopRepository;
import com.example.demooooo.shop.shopDto.ShopDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopServiceImpl implements ShopService{

    @Autowired private ShopRepository shopRepository;

    @Autowired private ShopMapper shopMapper;

    @Override
    public List<ShopDto> getAllShop() {
        return shopMapper.modelsToDtos(shopRepository.findAll());
    }

    @Override
    public void saveShop(ShopDto shop) {
        shopRepository.save(shopMapper.DtoToModel(shop));
    }

    @Override
    public void deleteShop(Integer id) {
        shopRepository.deleteById(id);
    }

    @Override
    public Optional<ShopDto> findShopById(Integer id) {
        return Optional.of(shopMapper.modelToDto(shopRepository.findById(id).get()));
    }

    @Override
    public Optional<ShopDto> findShopByUserId(Integer id) {
        try{   
            return Optional.of(shopMapper.modelToDto(shopRepository.findShopByUserid(id).get()));
        } catch (Exception ex){
            return Optional.ofNullable(null);
        }
    }

    @Override
    public Optional<ShopDto> findShopByShopname(String shopname){
        try{
            return Optional.of(shopMapper.modelToDto(shopRepository.findShopByShopname(shopname).get()));
        } catch (Exception ex){
            return Optional.ofNullable(null);
        }
    }
}
