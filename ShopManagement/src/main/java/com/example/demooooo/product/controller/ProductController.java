package com.example.demooooo.product.controller;

import com.example.demooooo.product.dto.ProductDTO;
import com.example.demooooo.product.model.Product;
import com.example.demooooo.product.service.ProductService;

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
@RequestMapping("/")
public class ProductController {
    @Autowired
    private ProductService productService;


    @RequestMapping("/product")
    public String getAllProduct(@RequestParam("id") Integer id, @RequestParam Integer userid ,Model model, RedirectAttributes ra) {
        List<ProductDTO> check = productService.findProductByShopId(id);
        if (check != null) {
            model.addAttribute("product", check);
            model.addAttribute("idshop", id);
            model.addAttribute("userid", userid);
            return "product";
        } else {
            return "loi";
        }

    }

    @RequestMapping("product/addproduct")
    public String addProduct(@RequestParam("id") Integer idshop, @RequestParam Integer userid, Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("idshop", idshop);
        model.addAttribute("userid", userid);
        return "addproduct";
    }


//    @PostMapping("product/save")
//    public String saveProduct(ProductDTO productDTO, RedirectAttributes redirectAttributes) {
//        productService.saveProduct(productDTO);
//        redirectAttributes.addAttribute("id", productDTO.getIdshop());
//        return "redirect:/product";
//    }

    @PostMapping("product/save")
    public String saveProduct(
            @ModelAttribute("product") ProductDTO productDTO,
            Errors error,
            @RequestParam Integer userid,
            RedirectAttributes redirectAttributes) {
        if (error.hasErrors()) {
            return "addproduct";
        }
        Optional<ProductDTO> checknameProduct =
                productService.findProductByProductname(productDTO.getProductname());
        if (checknameProduct.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm đã tồn tại");
            redirectAttributes.addAttribute("id", productDTO.getIdshop());
            return "redirect:/product/addproduct";
        } else {
            productService.saveProduct(productDTO);
            redirectAttributes.addFlashAttribute("message", "successfull");
            redirectAttributes.addAttribute("id", productDTO.getIdshop());
            redirectAttributes.addAttribute("userid", userid);
            return "redirect:/product";
        }
    }

    @RequestMapping("product/delete")
    public String deleteProduct(@RequestParam("id") Integer id, @RequestParam Integer userid, RedirectAttributes redirectAttributes) {
        Optional<ProductDTO> productDTO = productService.findProductById(id);
        redirectAttributes.addAttribute("id", productDTO.get().getIdshop());
        productService.deleteProduct(id);
        redirectAttributes.addAttribute("userid", userid);
        return "redirect:/product";
    }

    @RequestMapping(value = "product/update", method = RequestMethod.GET)
    public String updateProduct(@RequestParam("id") Integer productid, @RequestParam Integer userid,Model model) {
        Optional<ProductDTO> productUpdate = productService.findProductById(productid);
        model.addAttribute("idshop", productUpdate.get().getIdshop());
        productUpdate.ifPresent(product -> model.addAttribute("product", product));
        model.addAttribute("userid", userid);
        return "editProduct";
    }

    @RequestMapping(value = "product/update", method = RequestMethod.POST)
    public String updateProduct( @ModelAttribute("product") ProductDTO productDTO, @RequestParam("id") Integer productId, @RequestParam Integer userid, Model model, RedirectAttributes redirectAttributes, Errors errors) {
        if (errors.hasErrors()) {
            return "editProduct";
        }
        Optional<ProductDTO> current = productService.findProductById(productId);
        Optional<ProductDTO> namecheck = productService.findProductByProductname(productDTO.getProductname());
        if (namecheck.isPresent() && !current.get().getProductname().equals(namecheck.get().getProductname())) {
            redirectAttributes.addFlashAttribute("errorMessage", "da ton tai");
            redirectAttributes.addAttribute("productid", productDTO.getId());
            redirectAttributes.addAttribute("userid", userid);
            return "redirect:/product/update";
        } else {
            productService.saveProduct(productDTO);
            redirectAttributes.addFlashAttribute("message", "luu thanh cong");
            redirectAttributes.addAttribute("id", productDTO.getIdshop());
            redirectAttributes.addAttribute("userid", userid);
            model.addAttribute("userid",userid);
            return "redirect:/product";
        }
    }

}
