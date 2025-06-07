package br.com.styleoverflow.styleoverflow.services;

import java.sql.Connection;

import br.com.styleoverflow.styleoverflow.ConnectionFactory;
import br.com.styleoverflow.styleoverflow.DTO.CreateUserDTO;
import br.com.styleoverflow.styleoverflow.DTO.UpdateUserDTO;
import br.com.styleoverflow.styleoverflow.classes.User;
import br.com.styleoverflow.styleoverflow.dao.UserDAO;
import br.com.styleoverflow.styleoverflow.enums.Gender;

public class UserService {

    private final ConnectionFactory factory;

    public UserService(ConnectionFactory factory) {
        this.factory = new ConnectionFactory();
    }

    public void createUser(String name, String email, String password, String cellphone, String cpf, String cep, String address, Gender gender) {
        Connection connection = factory.getConnection();

        // implementar tratamentos de erro + hashing de senha

        new UserDAO(connection).createUser(new CreateUserDTO(name, email, password, cellphone, cpf, cep, address, gender));
    }

    public User getUserById(int userId) {
        Connection connection = factory.getConnection();

        return new UserDAO(connection).getUserById(userId);
    }

    public void updateUser(String email, String password, String cellphone, String cep, String address, Integer userId) {
        Connection connection = factory.getConnection();

        // implementar tratamentos de erro + hashing de senha

        new UserDAO(connection).updateUser(new UpdateUserDTO(email, password, cellphone, cep, address), userId);
    }
}
