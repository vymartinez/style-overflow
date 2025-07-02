package br.com.styleoverflow.styleoverflow.classes;

import java.time.LocalDate;
import java.util.List;

import br.com.styleoverflow.styleoverflow.enums.Payment;
import br.com.styleoverflow.styleoverflow.enums.Status;

public class Order {
    private final int id;
    private final List<ProductOrder> products;
    private final LocalDate date;
    private final Status status;
    private final Payment paymentType;

    public Order(int id, List<ProductOrder> products, LocalDate date, Status status, Payment paymentType) {
        this.id = id;
        this.products = products;
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

    public LocalDate getDate() {
        return date;
    }

    public Status getStatus() {
        return status;
    }

    public Payment getPaymentType() {
        return paymentType;
    }

    public Double calculateTotal() {
        return products.stream().mapToDouble(ProductOrder::calculateSubtotal).sum();
    }
}