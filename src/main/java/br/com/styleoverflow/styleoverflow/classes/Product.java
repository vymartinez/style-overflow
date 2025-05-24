package br.com.styleoverflow.styleoverflow.classes;

import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Size;

public class Product {
    private Integer id;
    private String name;
    private Size size;
    private Double price;
    private Gender gender;
    private String color;
    private Integer stock;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Size getSize() {
        return size;
    }

    public Double getPrice() {
        return price;
    }

    public Gender getGender() {
        return gender;
    }

    public String getColor() {
        return color;
    }

    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
