package com.xa.lookupservice.restcontrollers;

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

import com.xa.lookupservice.models.LookUp;
import com.xa.lookupservice.repositories.LookupRepository;

@RestController
@RequestMapping("/lookupservice/")
@CrossOrigin("*")
public class LookUpRestController {
    @Autowired
    private LookupRepository LookupRepository;

    @GetMapping("/lookups")
    public ResponseEntity<List<LookUp>> getUsers() {
        try {
            List<LookUp> lookup = this.LookupRepository.findAll();
            return new ResponseEntity<List<LookUp>>(lookup, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<LookUp>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/lookup/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long Id) {
        try {
            LookUp lookup = this.LookupRepository.findById(Id).orElse(null);
            if (lookup != null) {
                return new ResponseEntity<LookUp>(lookup, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("lookup not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/lookups")
    public ResponseEntity<LookUp> savelookup(@RequestBody LookUp lookup) {
        try {
            this.LookupRepository.save(lookup);
            return new ResponseEntity<LookUp>(lookup, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<LookUp>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("lookup/{id}")
    public ResponseEntity<LookUp> editlookup(
            @RequestBody LookUp lookup, @PathVariable("id") Long id) {
        try {
            lookup.setId(id);
            this.LookupRepository.save(lookup);
            return new ResponseEntity<LookUp>(lookup, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<LookUp>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/lookup/{id}")
    public ResponseEntity<?> deletelookup(@PathVariable("id") Long id) {
        try {
            LookUp lookup = this.LookupRepository.findById(id).orElse(null);
            if (lookup != null) {
                LookupRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body("lookup deleted");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
