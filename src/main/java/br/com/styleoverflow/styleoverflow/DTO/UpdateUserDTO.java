package br.com.styleoverflow.styleoverflow.DTO;

public record UpdateUserDTO (
        String email,
        String password,
        String cellphone,
        String cep,
        String address
) {}