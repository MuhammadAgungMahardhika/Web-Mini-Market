package com.xa.paymentservice.restcontroller;

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

import com.xa.paymentservice.models.Payment;
import com.xa.paymentservice.repositories.PaymentRepository;

@RestController
@RequestMapping("/paymentservice/")
@CrossOrigin("*")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("payments")
    public ResponseEntity<List<Payment>> getUsers() {
        try {
            List<Payment> payment = this.paymentRepository.findAll();
            return new ResponseEntity<List<Payment>>(payment, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Payment>>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("payment/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long Id) {
        try {
            Payment payment = this.paymentRepository.findById(Id).orElse(null);
            if (payment != null) {
                return new ResponseEntity<Payment>(payment, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("payment not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("payments")
    public ResponseEntity<Payment> savepayment(@RequestBody Payment payment) {
        try {
            this.paymentRepository.save(payment);
            return new ResponseEntity<Payment>(payment, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Payment>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("payment/{id}")
    public ResponseEntity<Payment> editPayment(
            @RequestBody Payment payment, @PathVariable("id") Long id) {
        try {
            payment.setId(id);
            this.paymentRepository.save(payment);
            return new ResponseEntity<Payment>(payment, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Payment>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("payment/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable("id") Long id) {
        try {
            Payment payment = this.paymentRepository.findById(id).orElse(null);
            if (payment != null) {
                paymentRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body("payment deleted");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
