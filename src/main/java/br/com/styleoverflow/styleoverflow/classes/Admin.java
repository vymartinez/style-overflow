package br.com.styleoverflow.styleoverflow.classes;

import java.util.List;

import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Role;

public class Admin extends User {

    public Admin(int id, String name, String email, String cellphone, String cpf, String cep, String address, Gender gender, Role role, List<Order> orders) {

        super(id, name, email, cellphone, cpf, cep, address, gender, role, orders);
    }
}
