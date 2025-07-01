package br.com.styleoverflow.styleoverflow.classes;

import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Role;
import br.com.styleoverflow.styleoverflow.enums.Size;
import br.com.styleoverflow.styleoverflow.screens.AdminDashboard;
import br.com.styleoverflow.styleoverflow.services.AdminService;
import javafx.stage.Stage;

import java.util.List;

public class Admin extends User {

    final AdminService adminService = new AdminService();

    public Admin(int id, String name, String email, String password,String cellphone, String cpf, String cep, String address, Gender gender, Role role, List<Order> orders) {
        super(id, name, email, password, cellphone, cpf, cep, address, gender, role, orders);
    }

    public void login(Stage stage) {
        stage.getScene().setRoot(new AdminDashboard(this).getView(stage));
    }

    public void registerProduct(String name, Size size, Gender gender, String color, Integer stock, Double price, String photoUrl) {
        this.adminService.createProduct(
            name, size, gender, color, stock, price, photoUrl, this
        );
    }

    public void patchProduct(String name, Size size, Gender gender, String color, Integer stock, Double price, String photoUrl, Integer productId) {
        this.adminService.patchProduct(
            name, size, gender, color, stock, price, photoUrl, productId, this
        );
    }

    public void deleteProduct(Integer productId) {
        this.adminService.deleteProduct(productId, this);
    }
}
