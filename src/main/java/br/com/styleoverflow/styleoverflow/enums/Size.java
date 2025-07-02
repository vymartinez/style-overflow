package br.com.styleoverflow.styleoverflow.enums;

public enum Size {
    PP,
    P,
    M,
    G,
    GG;

    public Boolean isPP() {
        return this == PP;
    }

    public Boolean isP() {
        return this == P;
    }

    public Boolean isM() {
        return this == M;
    }

    public Boolean isG() {
        return this == G;
    }

    public Boolean isGG() {
        return this == GG;
    }
}
