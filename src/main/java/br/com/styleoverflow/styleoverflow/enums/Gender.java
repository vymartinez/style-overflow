package br.com.styleoverflow.styleoverflow.enums;

public enum Gender {
    MALE,
    FEMALE;

    public Boolean isMale() {
        return this == MALE;
    }

    public Boolean isFemale() {
        return this == FEMALE;
    }
}
