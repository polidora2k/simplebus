package com.polidoraian.simplebus.shared.security;

public enum RoleEnum {
    PARENT("Parent"),
    DRIVER("Driver");
    
    private String value;
    
    RoleEnum(String value) {
        this.value = value;
    }
}
