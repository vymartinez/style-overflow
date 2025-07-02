package br.com.styleoverflow.styleoverflow.enums;

public enum Payment {
    PIX,
    CARD;

    public Boolean isPix() {
        return this == PIX;
    }

    public Boolean isCard() {
        return this == CARD;
    }

    public String toPortgueseString() {
        switch (this) {
            case PIX:
                return "Pix";
            case CARD:
                return "Cartão";
            default:
                return "";
        }
    }
}
