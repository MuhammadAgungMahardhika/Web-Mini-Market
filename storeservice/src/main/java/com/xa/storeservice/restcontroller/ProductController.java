package com.xa.storeservice.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xa.storeservice.models.Products;
import com.xa.storeservice.repositories.ProductRepository;

@RestController
@RequestMapping("/storeservice")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public ResponseEntity<List<Products>> getUsers() {
        try {
            List<Products> product = this.productRepository.findAll();
            return new ResponseEntity<List<Products>>(product, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Products>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long Id) {
        try {
            Products product = this.productRepository.findById(Id).orElse(null);
            if (product != null) {
                return new ResponseEntity<Products>(product, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/getValidate/{sku}/{name}")
    public ResponseEntity<Long> getValidate(
            @PathVariable("sku") String sku,
            @PathVariable("name") String name) {
        try {
            Long product = this.productRepository.getValidate(sku, name);
            return new ResponseEntity<Long>(product, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String address, String desc) throws Exception {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(address);
        msg.setSubject("New product registered");
        msg.setText("New product has been added to system " + desc);
        javaMailSender.send(msg);
    }

    @PostMapping("/products")
    public ResponseEntity<Products> saveproduct(@RequestBody Products product) {
        try {
            Long validate = this.productRepository.getValidate(product.getSku(), product.getName());
            if (validate == 0) {
                this.productRepository.save(product);
                sendMail("m.agungmahardika12@gmail.com", product.getName());
                return new ResponseEntity<Products>(product, HttpStatus.OK);
            } else {
                return new ResponseEntity<Products>(product, HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Products>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Products> editproduct(
            @RequestBody Products product, @PathVariable("id") Long id) {
        try {
            product.setId(id);
            this.productRepository.save(product);
            return new ResponseEntity<Products>(product, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Products>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteproduct(@PathVariable("id") Long id) {
        try {
            Products product = this.productRepository.findById(id).orElse(null);
            if (product != null) {
                productRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body("product deleted");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/products/search/{param}")
    public ResponseEntity<List<Products>> getProduct(@PathVariable("param") String param) {
        try {
            List<Products> product = this.productRepository.productByName(param);

            if (product != null) {
                return new ResponseEntity<List<Products>>(product, HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Products>>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Products>>(HttpStatus.NO_CONTENT);
        }
    }

    @PatchMapping("/product/{id}/{stock}")
    public ResponseEntity<Products> reduceStock(
            @PathVariable("id") Long id,
            @PathVariable("stock") Integer stock) {
        try {
            Products product = productRepository.findById(id).get();
            Integer st = product.getStock();
            product.setStock(st - stock);
            return new ResponseEntity<Products>(productRepository.save(product), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/product/increase/{id}/{stock}")
    public ResponseEntity<Products> increaseStock(
            @PathVariable("id") Long id,
            @PathVariable("stock") Integer stock) {
        try {
            Products product = productRepository.findById(id).get();
            Integer st = product.getStock();
            product.setStock(st + stock);
            return new ResponseEntity<Products>(productRepository.save(product),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
