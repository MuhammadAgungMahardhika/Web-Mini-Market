package com.xa.storeservice.restcontroller;

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

import com.xa.storeservice.models.Categories;
import com.xa.storeservice.repositories.CategoryRepository;

@RestController
@RequestMapping("/storeservice/")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public ResponseEntity<List<Categories>> getUsers() {
        try {
            List<Categories> category = this.categoryRepository.findAll();
            return new ResponseEntity<List<Categories>>(category, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Categories>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long Id) {
        try {
            Categories category = this.categoryRepository.findById(Id).orElse(null);
            if (category != null) {
                return new ResponseEntity<Categories>(category, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("category not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/categories")
    public ResponseEntity<Categories> savecategory(@RequestBody Categories category) {
        try {
            this.categoryRepository.save(category);
            return new ResponseEntity<Categories>(category, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Categories>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Categories> editcategory(
            @RequestBody Categories category, @PathVariable("id") Long id) {
        try {
            category.setId(id);
            this.categoryRepository.save(category);
            return new ResponseEntity<Categories>(category, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Categories>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deletecategory(@PathVariable("id") Long id) {
        try {
            Categories category = this.categoryRepository.findById(id).orElse(null);
            if (category != null) {
                categoryRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body("category deleted");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
