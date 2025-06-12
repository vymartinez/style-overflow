package br.com.styleoverflow.styleoverflow.classes;

import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Role;
import java.util.List;

public class User {

    private final int id;
    private final String name;
    private final String email;
    private final String cellphone;
    private final String cpf;
    private final String cep;
    private final String address;
    private final Gender gender;
    private final Role role;
    private final List<Order> orders;

    public User(Integer id, String name, String email, String cellphone, String cpf, String cep, String address, Gender gender, Role role, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cellphone = cellphone;
        this.cpf = cpf;
        this.cep = cep;
        this.address = address;
        this.gender = gender;
        this.role = role;
        this.orders = orders;
    }

    public Integer getId() {
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
}
