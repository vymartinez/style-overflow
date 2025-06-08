package br.com.styleoverflow.styleoverflow.DTO;

import br.com.styleoverflow.styleoverflow.enums.Payment;

import java.util.List;

public record CreateOrderDTO(
        Integer userId,
        Payment paymentType,
        List<ProductOrderDTO> productOrder
) {}
