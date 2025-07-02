package br.com.styleoverflow.styleoverflow.DTO;

import br.com.styleoverflow.styleoverflow.enums.Gender;

public record CreateUserDTO(
        String name,
        String email,
        String password,
        String cellphone,
        String cpf,
        String cep,
        String address,
        Gender gender
) {}
