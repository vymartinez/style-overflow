package br.com.styleoverflow.styleoverflow.services;

import java.sql.Connection;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

import br.com.styleoverflow.styleoverflow.ConnectionFactory;
import br.com.styleoverflow.styleoverflow.DTO.CreateUserDTO;
import br.com.styleoverflow.styleoverflow.DTO.UpdateUserDTO;
import br.com.styleoverflow.styleoverflow.DomainException;
import br.com.styleoverflow.styleoverflow.classes.Order;
import br.com.styleoverflow.styleoverflow.classes.User;
import br.com.styleoverflow.styleoverflow.dao.UserDAO;
import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.screens.LoginAndRegister;

public class UserService {

    private final ConnectionFactory factory = new ConnectionFactory();
    private final OrderService orderService = new OrderService(factory);

    public void createUser(String name, String email, String password, String cellphone, String cpf, String cep, String address, Gender gender) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        CreateUserDTO userDTO = new CreateUserDTO(name, email, hashedPassword, cellphone, cpf, cep, address, gender);

        Connection connection = factory.getConnection();
        new UserDAO(connection).createUser(userDTO);

    }

    public User login(String email, String plainTextPassword) {
        User user = null;
        try (Connection connection = factory.getConnection()) {
            UserDAO userDAO = new UserDAO(connection);
            user = userDAO.getUserByEmail(email); 
            
            if (user != null) {
                if (BCrypt.checkpw(plainTextPassword, user.getPassword())) {
                    return user;
                }
            }
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("erro inesperado ao tentar fazer login: " + e.getMessage(), e);
        }
        return null;
    }

    public User getUserById(int userId) {
        User user = null;

        try (Connection connection = factory.getConnection()) {
            UserDAO userDAO = new UserDAO(connection);
            user = userDAO.getUserById(userId);
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("erro inesperado ao buscar usuário por ID: " + e.getMessage(), e);
        }
        return user;
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

        try (Connection connection = factory.getConnection()) {
            new UserDAO(connection).updateUser(userDto, userId);
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("erro inesperado ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    private User getUserWithOrders(User user) { 
        if (user == null) {
            return null;
        }
        List<Order> orders = orderService.getOrdersByCustomerId(user.getId());
        return new User(user.getId(), user.getName(), user.getEmail(), user.getPassword(),
                        user.getCellphone(), user.getCpf(), user.getCep(), user.getAddress(),
                        user.getGender(), user.getRole(), orders);
    }

    public void deleteUser(Integer userId) {
        try (Connection connection = factory.getConnection()) {
            // Talvez terá que excluir pedidos, etc
            new UserDAO(connection).deleteUser(userId);
        } catch (DomainException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao excluir usuário: " + e.getMessage(), e);
        }
    }

    public User getLoggedInUser() {
        User user = null;
        return LoginAndRegister.loggedInUser;
    }
}