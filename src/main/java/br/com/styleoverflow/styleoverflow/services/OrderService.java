package br.com.styleoverflow.styleoverflow.services;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.styleoverflow.styleoverflow.ConnectionFactory;
import br.com.styleoverflow.styleoverflow.DTO.CreateOrderDTO;
import br.com.styleoverflow.styleoverflow.DTO.ProductOrderDTO;
import br.com.styleoverflow.styleoverflow.DTO.UpdateOrderDTO;
import br.com.styleoverflow.styleoverflow.classes.CartProduct;
import br.com.styleoverflow.styleoverflow.classes.Order;
import br.com.styleoverflow.styleoverflow.dao.OrderDAO;
import br.com.styleoverflow.styleoverflow.enums.Payment;
import br.com.styleoverflow.styleoverflow.enums.Status;

public class OrderService {

    private final ConnectionFactory factory;

    public OrderService(ConnectionFactory factory) {
        this.factory = new ConnectionFactory();
    }

    public void createOrder(Integer userId, Payment paymentType, List<CartProduct> cartItems) {
        Connection connection = factory.getConnection();

        // implementar tratamentos de erro

        List<ProductOrderDTO> productOrderDTOList = new ArrayList<>();
        cartItems.forEach(cartProduct -> {
            productOrderDTOList.add(new ProductOrderDTO(cartProduct.getProduct().getId(), cartProduct.getQuantity()));
        });
        new OrderDAO(connection).createOrder(new CreateOrderDTO(userId, paymentType, productOrderDTOList));
    }

    public List<Order> getOrdersByCustomerId(Integer userId) {
        Connection connection = factory.getConnection();

        return new OrderDAO(connection).getOrdersByCustomerId(userId);
    }

    public void updateOrder(Status status, Integer orderId) {
        Connection connection = factory.getConnection();

        // implementar tratamentos de erro

        new OrderDAO(connection).updateOrder(new UpdateOrderDTO(status), orderId);
    }
}
