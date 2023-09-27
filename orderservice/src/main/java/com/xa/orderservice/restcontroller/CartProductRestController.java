package com.xa.orderservice.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xa.orderservice.models.CartProduct;
import com.xa.orderservice.repositories.CartProductRepository;

@RestController
@RequestMapping("/orderservice/")
@CrossOrigin("*")
public class CartProductRestController {
    @Autowired
    private CartProductRepository cartProductRepository;

    @GetMapping("cartProducts")
    public ResponseEntity<List<CartProduct>> getCartProduct() {
        try {
            List<CartProduct> cartProduct = this.cartProductRepository.findAll();
            return new ResponseEntity<List<CartProduct>>(cartProduct, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CartProduct>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("cartProduct/{id}")
    public ResponseEntity<?> getCartProductById(@PathVariable("id") Long Id) {
        try {
            CartProduct cartProduct = this.cartProductRepository.findById(Id).orElse(null);
            if (cartProduct != null) {
                return new ResponseEntity<CartProduct>(cartProduct, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CartProduct not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("cartProducts")
    public ResponseEntity<CartProduct> saveCartProduct(@RequestBody CartProduct cartProduct) {
        try {
            this.cartProductRepository.save(cartProduct);
            return new ResponseEntity<CartProduct>(cartProduct, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CartProduct>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("cartProduct/{id}")
    public ResponseEntity<CartProduct> editCartProduct(
            @RequestBody CartProduct cartProduct, @PathVariable("id") Long id) {
        try {
            cartProduct.setId(id);
            this.cartProductRepository.save(cartProduct);
            return new ResponseEntity<CartProduct>(cartProduct, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<CartProduct>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("cartProduct/{id}")
    public ResponseEntity<?> deleteCartProduct(@PathVariable("id") Long id) {
        try {
            CartProduct cartProduct = this.cartProductRepository.findById(id).orElse(null);
            if (cartProduct != null) {
                cartProductRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body("CartProduct deleted");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("getProductFromCart/{cart_id}")
    public ResponseEntity<List<CartProduct>> getProductFromCart(@PathVariable("cart_id") Long cart_id) {
        try {
            List<CartProduct> cartProduct = this.cartProductRepository.getProductFromCart(cart_id);
            return new ResponseEntity<List<CartProduct>>(cartProduct, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CartProduct>>(HttpStatus.NO_CONTENT);
        }
    }
}
