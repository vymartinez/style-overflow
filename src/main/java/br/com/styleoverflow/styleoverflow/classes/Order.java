package br.com.styleoverflow.styleoverflow.classes;

import br.com.styleoverflow.styleoverflow.enums.Payment;
import br.com.styleoverflow.styleoverflow.enums.Status;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private List<ProductOrder> products;
    private User client;
    private Date date;
    private Status status;
    private Payment paymentType;

    public Order() {}

    public Integer getId() {
        return id;
    }
    public List<ProductOrder> getProducts() {
        return products;
    }

    public User getClient() {
        return client;
    }

    public Date getDate() {
        return date;
    }

    public Status getStatus() {
        return status;
    }

    public Payment getPaymentType() {
        return paymentType;
    }

    public double calculateTotal() {
        return products.stream().mapToDouble(ProductOrder::calculateSubtotal).sum();
    }
}