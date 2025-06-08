package br.com.styleoverflow.styleoverflow.classes;

import br.com.styleoverflow.styleoverflow.enums.Payment;
import br.com.styleoverflow.styleoverflow.enums.Status;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private final int id;
    private final List<ProductOrder> products;
    private final User client;
    private final LocalDate date;
    private final Status status;
    private final Payment paymentType;

    public Order(int id, List<ProductOrder> products, User client, LocalDate date, Status status, Payment paymentType) {
        this.id = id;
        this.products = products;
        this.client = client;
        this.date = date;
        this.status = status;
        this.paymentType = paymentType;
    }

    public Integer getId() {
        return id;
    }

    public List<ProductOrder> getProducts() {
        return products;
    }

    public User getClient() {
        return client;
    }

    public LocalDate getDate() {
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