package br.com.styleoverflow.styleoverflow.classes;

import java.time.LocalDate;
import java.util.List;

import br.com.styleoverflow.styleoverflow.enums.Payment;
import br.com.styleoverflow.styleoverflow.enums.Status;

public class Order {
    private Integer id;
    private List<ProductOrder> products;
    private LocalDate date;
    private Status status;
    private Payment paymentType;

    public Order(Integer id, List<ProductOrder> products, LocalDate date, Status status, Payment paymentType) {
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