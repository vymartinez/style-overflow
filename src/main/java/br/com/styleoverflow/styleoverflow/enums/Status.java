package br.com.styleoverflow.styleoverflow.enums;

public enum Status {
    PENDING,
    DELIVERED;

    public Boolean isPending() {
        return this == PENDING;
    }

    public Boolean isDelivered() {
        return this == DELIVERED;
    }

    public String toPortgueseString() {
        switch (this) {
            case PENDING:
                return "Pendente";
            case DELIVERED:
                return "Entregue";
            default:
                return "";
        }
    }
}
