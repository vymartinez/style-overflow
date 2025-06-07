package br.com.styleoverflow.styleoverflow.classes;

import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Role;

import java.util.List;

public class User {

    private int id;
    private String name;
    private String email;
    private String cellphone;
    private String cpf;
    private String cep;
    private String address;
    private Gender gender;
    private Role role;
    private List<Order> orders;

    public User(int id, String name, String email, String cellphone, String cpf, String cep, String address, Gender gender, Role role, List<Order> orders) {
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

    public User(int id, String joazinho, String mail, String number) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}