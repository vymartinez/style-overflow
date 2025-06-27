package br.com.styleoverflow.styleoverflow.classes;

import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Role;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final int id;
    private String email;
    private final String password;
    private String cellphone;
    private final String name;
    private final String cpf;
    private String cep;
    private String address;
    private final Gender gender;
    private final Role role;
    private final List<Order> orders;

    public User(int id, String name, String email, String password, String cellphone, String cpf, String cep, String address, Gender gender, Role role, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cellphone = cellphone;
        this.cpf = cpf;
        this.cep = cep;
        this.address = address;
        this.gender = gender;
        this.role = role;
        this.orders = (orders != null) ? new ArrayList<>(orders) : new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCep() {
        return cep;
    }

    public String getAddress() {
        return address;
    }

    public Gender getGender() {
        return gender;
    }

    public Role getRole() {
        return role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {this.email = email;}

    public void setCep(String cep) {this.cep = cep;}

    public void setCellphone(String cellphone) {this.cellphone = cellphone;}

    public void setAddress(String address) {this.address = address;}
    
}
