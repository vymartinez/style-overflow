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
}
