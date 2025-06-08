package br.com.styleoverflow.styleoverflow.classes;

import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Size;

public class Product {
    private final Integer id;
    private final String name;
    private final Size size;
    private final Double price;
    private final Gender gender;
    private final String color;
    private final Integer stock;
    private final String photoUrl;

    public Product(Integer id, String name, Size size, Double price, Gender gender, String color, Integer stock, String photoUrl) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.price = price;
        this.gender = gender;
        this.color = color;
        this.stock = stock;
        this.photoUrl = photoUrl;
    }

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

    public String getPhotoUrl() {
        return photoUrl;
    }
}