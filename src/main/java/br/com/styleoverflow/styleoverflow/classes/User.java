package br.com.styleoverflow.styleoverflow.classes;

import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Role;
import br.com.styleoverflow.styleoverflow.enums.Status;
import br.com.styleoverflow.styleoverflow.screens.CatalogView;
import br.com.styleoverflow.styleoverflow.screens.LoginAndRegister;
import br.com.styleoverflow.styleoverflow.services.OrderService;
import br.com.styleoverflow.styleoverflow.services.UserService;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final int id;
    private final String email;
    private final String password;
    private final String cellphone;
    private final String name;
    private final String cpf;
    private final String cep;
    private final String address;
    private final Gender gender;
    private final Role role;
    private final List<Order> orders;
    private final Cart currentCart = new Cart(new ArrayList<>());
    private final UserService userService = new UserService();

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

    public Cart getCurrentCart() {
        return currentCart;
    }

    public void login(Stage stage) {
        stage.getScene().setRoot(new CatalogView(stage, this).getView(stage));
    }

    public void logout(Stage stage) {
        stage.getScene().setRoot(new LoginAndRegister().showLogin(stage));
    }

    public void patchOrderStatus(Order order, Status status) {
        new OrderService().updateOrder(status, order.getId());
    }

    public void patchInfo(String email, String password, String cellphone, String cep, String address) {
        userService.updateUser(email, password, cellphone, cep, address, this.id);
    }

    public void deleteAccount() {
        userService.deleteUser(this.id);
    }
}
