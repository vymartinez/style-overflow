package br.com.styleoverflow.styleoverflow.DTO;

import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Size;

public record ProductDTO(
        String name,
        Size size,
        Double price,
        Gender gender,
        String color,
        Integer stock,
        String photoUrl
) {}
