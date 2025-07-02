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

    public String toPortugueseString() {
        switch (this) {
            case MALE:
                return "Masculino";
            case FEMALE:
                return "Feminino";
            default:
                return "";
        }
    }
}
