package br.com.styleoverflow.styleoverflow.services;

import java.sql.Connection;
import java.util.List;

import br.com.styleoverflow.styleoverflow.ConnectionFactory;
import br.com.styleoverflow.styleoverflow.DTO.CreateUserDTO;
import br.com.styleoverflow.styleoverflow.DTO.UpdateUserDTO;
import br.com.styleoverflow.styleoverflow.classes.Order;
import br.com.styleoverflow.styleoverflow.classes.User;
import br.com.styleoverflow.styleoverflow.dao.UserDAO;
import br.com.styleoverflow.styleoverflow.enums.Gender;

public class UserService {

    private final ConnectionFactory factory = new ConnectionFactory();
    private final OrderService orderService = new OrderService(factory);

    public void createUser(String name, String email, String password, String cellphone, String cpf, String cep, String address, Gender gender) {
        Connection connection = factory.getConnection();

        // implementar tratamentos de erro + hashing de senha

        new UserDAO(connection).createUser(new CreateUserDTO(name, email, password, cellphone, cpf, cep, address, gender));
    }

    public User getUserById(int userId) {
        Connection connection = factory.getConnection();

        User user = new UserDAO(connection).getUserById(userId);
        return getUserWithOrders(user);
    }

    public void updateUser(String email, String password, String cellphone, String cep, String address, Integer userId) {
        Connection connection = factory.getConnection();

        // implementar tratamentos de erro + hashing de senha

        new UserDAO(connection).updateUser(new UpdateUserDTO(email, password, cellphone, cep, address), userId);
    }

    private User getUserWithOrders(User user) {
        List<Order> orders = orderService.getOrdersByCustomerId(user.getId());
        for (Order order : orders) {
            user.getOrders().add(order);
        }
        return user;
    }
}
