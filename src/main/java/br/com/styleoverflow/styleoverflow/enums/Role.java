package br.com.styleoverflow.styleoverflow.enums;

public enum Role {
    CLIENT,
    ADMIN;

    public Boolean isClient() {
        return this == CLIENT;
    }

    public Boolean isAdmin() {
        return this == ADMIN;
    }
}
