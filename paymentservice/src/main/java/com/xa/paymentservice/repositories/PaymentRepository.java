package com.xa.paymentservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xa.paymentservice.models.Payment;

public interface PaymentRepository extends JpaRepository <Payment,Long> {
    
}
