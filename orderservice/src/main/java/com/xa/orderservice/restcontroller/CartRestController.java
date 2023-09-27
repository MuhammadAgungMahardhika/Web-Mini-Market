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

import com.xa.orderservice.models.Cart;

import com.xa.orderservice.repositories.CartRepository;

@RestController
@RequestMapping("/orderservice/")
@CrossOrigin("*")
public class CartRestController {
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("carts")
    public ResponseEntity<List<Cart>> getCart() {
        try {
            List<Cart> cart = this.cartRepository.findAll();
            return new ResponseEntity<List<Cart>>(cart, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Cart>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("cart/{id}")
    public ResponseEntity<?> getCartById(@PathVariable("id") Long Id) {
        try {
            Cart Cart = this.cartRepository.findById(Id).orElse(null);
            if (Cart != null) {
                return new ResponseEntity<Cart>(Cart, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cart not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("carts")
    public ResponseEntity<Cart> saveCart(@RequestBody Cart Cart) {
        try {
            this.cartRepository.save(Cart);
            return new ResponseEntity<Cart>(Cart, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Cart>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("cart/{id}")
    public ResponseEntity<Cart> editCart(
            @RequestBody Cart cart, @PathVariable("id") Long id) {
        try {
            cart.setId(id);
            this.cartRepository.save(cart);
            return new ResponseEntity<Cart>(cart, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Cart>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("cart/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable("id") Long id) {
        try {
            Cart cart = this.cartRepository.findById(id).orElse(null);
            if (cart != null) {
                cartRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body("Cart deleted");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("getCartFromUserId/{costumer_id}")
    public ResponseEntity<List<Cart>> getCartFromUserId(@PathVariable("costumer_id") Long costumer_id) {
        try {
            List<Cart> cart = this.cartRepository.getCartFromCostumerId(costumer_id);
            return new ResponseEntity<List<Cart>>(cart, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Cart>>(HttpStatus.NO_CONTENT);
        }
    }
}
