package com.example.demooooo.shop.controller;

import com.example.demooooo.shop.model.Shop;
import com.example.demooooo.shop.service.ShopService;
import com.example.demooooo.shop.shopDto.ShopDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ShopController {
  @Autowired private ShopService shopService;

  @GetMapping("shop")
  public String index(@RequestParam("id") Integer id, Model model, RedirectAttributes ra) {
    List<ShopDto> shops = shopService.getAllShop();
    Optional<ShopDto> check = shopService.findShopByUserId(id);
    if (check.isPresent()) {
      for (ShopDto s : shops) {
        if (s.getUserid() == check.get().getUserid()) {
          model.addAttribute("shop", s);
        }
      }
      return "shop";
    } else {
      ra.addAttribute("userid", id);
      return "redirect:/shop/new";
    }
  }

  @RequestMapping("shop/new")
  public String addUser(@Valid @RequestParam("userid") Integer userid, Model model) {
    model.addAttribute("shop", new Shop());
    model.addAttribute("userid", userid);
    model.addAttribute("pageTitle", "Add New Shop");
    return "createShop";
  }

  @RequestMapping(value = "/shop/edit", method = RequestMethod.GET)
  public String editUser(@RequestParam("id") Integer shopId, Model model) {
    Optional<ShopDto> shopEdit = shopService.findShopById(shopId);
    model.addAttribute("userid", shopEdit.get().getUserid());
    shopEdit.ifPresent(shop -> model.addAttribute("shop", shop));
    return "editShop";
  }

  @RequestMapping(value = "/shop/delete")
  public String deleteUser(@RequestParam("id") Integer shopId, RedirectAttributes ra) {
    Optional<ShopDto> s = shopService.findShopById(shopId);
    ra.addAttribute("id", s.get().getUserid());
    shopService.deleteShop(shopId);
    return "redirect:/admin";
  }

  @PostMapping(value = "shop/save")
  public String saveUser(
      @Valid @ModelAttribute("shop") ShopDto shop, Errors errors, RedirectAttributes ra) {
    if (errors.hasErrors()) {
      return "createShop";
    }
    boolean check = false;
    List<ShopDto> shopList = shopService.getAllShop();
    for (ShopDto s : shopList) {
      if (s.getShopname().equals(shop.getShopname())) {
        check = true;
      }
    }
    if (check) {
      ra.addFlashAttribute("errorMessage", "Tên cửa hàng đã tồn tại");
      ra.addAttribute("userid", shop.getUserid());
      return "redirect:/shop/new";
    } else {
      ra.addFlashAttribute("message", "Lưu cửa hàng thành công. ");
      shopService.saveShop(shop);
    }
    ra.addAttribute("id", shop.getUserid());
    return "redirect:/shop";
  }

  @GetMapping("/addproduct")
  public String admin(@RequestParam("id") Integer id, Model model) {
    ShopDto shopDto = shopService.findShopById(id).get();
    model.addAttribute("shop", shopDto);
    return "addproduct";
  }

  @PostMapping(value = "shop/update")
  public String update(
      @Valid @ModelAttribute("shop") ShopDto shop,
      @RequestParam("id") Integer id,
      Errors errors,
      RedirectAttributes ra) {
    if (errors.hasErrors()) {
      return "editShop";
    }
    boolean check = false;
    List<ShopDto> shopList = shopService.getAllShop();
    Optional<ShopDto> currentShop = shopService.findShopById(id);
    for (ShopDto s : shopList) {
      if (s.getShopname().equals(shop.getShopname())
          && (!s.getShopname().equals(currentShop.get().getShopname()))) {
        check = true;
      }
    }
    if (check) {
      ra.addFlashAttribute("errorMessage", "Tên cửa hàng đã tồn tại");
      ra.addAttribute("id", shop.getId());
      return "redirect:/shop/edit";
    } else {
      ra.addFlashAttribute("message", "Lưu cửa hàng thành công. ");
      shopService.saveShop(shop);
    }
    ra.addAttribute("id", shop.getUserid());
    return "redirect:/shop";
  }
}
