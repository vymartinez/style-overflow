package br.com.styleoverflow.styleoverflow.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.styleoverflow.styleoverflow.DTO.CreateOrderDTO;
import br.com.styleoverflow.styleoverflow.DTO.UpdateOrderDTO;
import br.com.styleoverflow.styleoverflow.DomainException;
import br.com.styleoverflow.styleoverflow.enums.Status;

public class OrderDAO {

    private final Connection connection;

    public OrderDAO(Connection connection) {
        this.connection = connection;
    }

    public void createOrder(CreateOrderDTO orderDto) {

        String query = "INSERT INTO orders (client_id, date, status, payment_type) VALUES (?, ?, ?, ?)";
        String midQuery = "INSERT INTO product_orders (order_id, product_id, quantity) VALUES (?, ?, ?)";

        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderDto.userId());
            statement.setDate(2, new Date(System.currentTimeMillis()));
            statement.setString(3, Status.PENDING.toString());
            statement.setString(4, orderDto.paymentType().toString());
            statement.execute();

            ResultSet keys = statement.getGeneratedKeys();
            if (!keys.next()) throw new DomainException("Erro ao obter id do pedido.");

            Integer orderId = keys.getInt(1);
            PreparedStatement midStatement = connection.prepareStatement(midQuery);
            orderDto.productOrder().forEach(order -> {
                try {
                    midStatement.setInt(1, orderId);
                    midStatement.setInt(2, order.productId());
                    midStatement.setInt(3, order.quantity());
                    midStatement.execute();
                } catch (Exception e) {
                    throw new DomainException("Erro ao relacionar o pedido.");
                }
            });

            connection.commit();

            statement.close();
            midStatement.close();
            keys.close();
            connection.close();

        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception error) {
                throw new DomainException("Erro ao solicitar pedido.");
            }
        }
    }

    public void updateOrder(UpdateOrderDTO orderDto, Integer orderId) {

        String query = "UPDATE orders SET status = ? WHERE order_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, orderDto.status().toString());
            statement.setInt(2, orderId);
            statement.execute();

            statement.close();
            connection.close();

        } catch (Exception e) {
            throw new DomainException("Erro ao editar status do pedido.");
        }

    }
}
