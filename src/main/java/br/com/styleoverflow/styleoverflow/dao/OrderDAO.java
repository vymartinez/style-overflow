package br.com.styleoverflow.styleoverflow.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.styleoverflow.styleoverflow.DTO.CreateOrderDTO;
import br.com.styleoverflow.styleoverflow.DTO.UpdateOrderDTO;
import br.com.styleoverflow.styleoverflow.DomainException;
import br.com.styleoverflow.styleoverflow.classes.Order;
import br.com.styleoverflow.styleoverflow.classes.Product;
import br.com.styleoverflow.styleoverflow.classes.ProductOrder;
import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Payment;
import br.com.styleoverflow.styleoverflow.enums.Size;
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

            int orderId = keys.getInt(1);
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

    public List<Order> getOrdersByCustomerId(Integer userId) {

        String query = (
            "SELECT o.id AS order_id, o.client_id, o.date, o.status, o.payment_type, " +
            "po.product_id, po.quantity, " +
            "p.id AS product_id, p.name, p.size, p.gender, p.photo_url, p.color, p.stock, p.price " +
            "FROM orders o " +
            "INNER JOIN product_orders po ON o.id = po.order_id " +
            "INNER JOIN products p ON po.product_id = p.id " +
            "WHERE o.client_id = ?"
        );

        Map<Integer, Order> ordersMap = new HashMap<>();
        List<ProductOrder> productOrders = new ArrayList<>();
        int lastOrderId = -1;

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product(
                    resultSet.getInt("product_id"),
                    resultSet.getString("name"),
                    Size.valueOf(resultSet.getString("size")),
                    resultSet.getDouble("price"),
                    Gender.valueOf(resultSet.getString("gender")),
                    resultSet.getString("color"),
                    resultSet.getInt("stock"),
                    resultSet.getString("photo_url")
                );

                ProductOrder productOrder = new ProductOrder(
                    product,
                    resultSet.getInt("quantity")
                );

                productOrders.add(productOrder);

                int orderId = resultSet.getInt("order_id");
                if (lastOrderId == -1) lastOrderId = orderId;

                if (orderId != lastOrderId) {
                    lastOrderId = orderId;
                    Order order = new Order(
                        orderId,
                        productOrders,
                        resultSet.getDate("date").toLocalDate(),
                        Status.valueOf(resultSet.getString("status")),
                        Payment.valueOf(resultSet.getString("payment_type"))
                    );
                    ordersMap.put(orderId, order);
                    productOrders.clear();
                } else {
                    productOrders.add(productOrder);
                }
            }

            statement.close();
            connection.close();

            return new ArrayList<>(ordersMap.values());

        } catch (Exception e) {
            throw new DomainException("Erro ao buscar pedidos do cliente.");
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
