package br.com.styleoverflow.styleoverflow.classes;

import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Role;

public class Admin extends User {

    public Admin(int id, String name, String email, String cellphone, String cpf, String cep, String address, Gender gender, Role role) {

        super(id, name, email, cellphone, cpf, cep, address, gender, role);
    }
}