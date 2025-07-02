package br.com.styleoverflow.styleoverflow.services;

import java.sql.Connection;
import java.util.List;

import br.com.styleoverflow.styleoverflow.classes.Admin;
import br.com.styleoverflow.styleoverflow.enums.Role;
import org.mindrot.jbcrypt.BCrypt;

import br.com.styleoverflow.styleoverflow.ConnectionFactory;
import br.com.styleoverflow.styleoverflow.DTO.CreateUserDTO;
import br.com.styleoverflow.styleoverflow.DTO.UpdateUserDTO;
import br.com.styleoverflow.styleoverflow.DomainException;
import br.com.styleoverflow.styleoverflow.classes.Order;
import br.com.styleoverflow.styleoverflow.classes.User;
import br.com.styleoverflow.styleoverflow.dao.UserDAO;
import br.com.styleoverflow.styleoverflow.enums.Gender;

public class UserService {

    private final ConnectionFactory factory = new ConnectionFactory();
    private final OrderService orderService = new OrderService();

    public void createUser(String name, String email, String password, String cellphone, String cpf, String cep, String address, Gender gender) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        CreateUserDTO userDTO = new CreateUserDTO(name, email, hashedPassword, cellphone, cpf, cep, address, gender);

        Connection connection = factory.getConnection();
        new UserDAO(connection).create(userDTO);
    }

    public User login(String email, String plainTextPassword) {
        Connection connection = factory.getConnection();

        User user = new UserDAO(connection).getByEmail(email);

        if (user == null) throw new DomainException("Email ou senha inválidos");

        boolean valid = BCrypt.checkpw(plainTextPassword, user.getPassword());
        if (!valid) throw new DomainException("Email ou senha inválidos");

        return getUserWithOrders(user);
    }

    public User getUserById(int userId) {
        Connection connection = factory.getConnection();

        return getUserWithOrders(new UserDAO(connection).getById(userId));
    }

    public void updateUser(String email, String password, String cellphone, String cep, String address, Integer userId) {
        String hashedPassword;
        if (password == null || password.trim().isEmpty()) {
            User existingUser = getUserById(userId);
            if (existingUser != null) {
                hashedPassword = existingUser.getPassword();
            } else {
                throw new DomainException("Usuário não encontrado para atualização de senha.");
            }
        } else {
            hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        }

        UpdateUserDTO userDto = new UpdateUserDTO(email, hashedPassword, cellphone, cep, address);

        Connection connection = factory.getConnection();
        new UserDAO(connection).update(userDto, userId);
    }

    private User getUserWithOrders(User user) { 
        if (user == null) return null;

        List<Order> orders = orderService.getOrdersByCustomerId(user.getId());

        if (user.getRole() == Role.ADMIN) return new Admin(
                user.getId(), user.getName(), user.getEmail(), user.getPassword(),
                user.getCellphone(), user.getCpf(), user.getCep(), user.getAddress(),
                user.getGender(), user.getRole(), orders
        );

        return new User(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
                        user.getCellphone(), user.getCpf(), user.getCep(), user.getAddress(),
                        user.getGender(), user.getRole(), orders);
    }

    public void deleteUser(Integer userId) {
        Connection connection = factory.getConnection();

        new UserDAO(connection).deleteUser(userId);
    }
}