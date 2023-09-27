package com.xa.paymentservice.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long Id;

    @Column(name = "cart_id")
    private Long CartId;

    @Column(name="costumer_id")
    private Long CostumerId;

    @Column(name="total")
    private Double Total;

    @Column(name="pay")
    private Double Pay;

    @Column(name="status")
    private String Status;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getCartId() {
        return CartId;
    }

    public void setCartId(Long cartId) {
        CartId = cartId;
    }

    public Long getCostumerId() {
        return CostumerId;
    }

    public void setCostumerId(Long costumerId) {
        CostumerId = costumerId;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public Double getPay() {
        return Pay;
    }

    public void setPay(Double pay) {
        Pay = pay;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
    
}
