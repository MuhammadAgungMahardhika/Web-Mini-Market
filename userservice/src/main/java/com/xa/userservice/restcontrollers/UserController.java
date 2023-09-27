package com.xa.userservice.restcontrollers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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

import com.xa.userservice.models.User;
import com.xa.userservice.repositories.UserRepository;

@RestController
@RequestMapping("/userservice/")
@CrossOrigin("*")
public class UserController {

    // ambil adata dari table users
    // sambungkan ke repository (membuat query)

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        try {
            List<User> user = this.userRepository.findAll();
            return new ResponseEntity<List<User>>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long Id) {
        try {
            User user = this.userRepository.findById(Id).orElse(null);
            if (user != null) {
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("user not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveuser(@RequestBody User user) {
        try {
            String pass = user.getPassword();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodehash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
            user.setPassword(bytesToHex(encodehash));
            this.userRepository.save(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("user/{id}")
    public ResponseEntity<User> edituser(
            @RequestBody User user, @PathVariable("id") Long id) {
        try {
            user.setId(id);
            this.userRepository.save(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteuser(@PathVariable("id") Long id) {
        try {
            User user = this.userRepository.findById(id).orElse(null);
            if (user != null) {
                userRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body("User deleted");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/checklogin")
    public ResponseEntity<List<User>> checkLogin(@RequestBody User user) {
        try {
            String pass = user.getPassword();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodehash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));

            List<User> getuser = this.userRepository.checkAccount(user.getEmail(), bytesToHex(encodehash));
            return new ResponseEntity<List<User>>(getuser, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

}
