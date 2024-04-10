package com.darwgom.candidatecontrolapi.domain.enums;

public enum RoleEnum {

    ROLE_ADMIN("role_admin");

    private final String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

