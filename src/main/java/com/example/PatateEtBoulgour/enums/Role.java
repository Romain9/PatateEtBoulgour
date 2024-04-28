package com.example.PatateEtBoulgour.enums;

import lombok.Getter;

@Getter
public enum Role {
    // + en haut = les droits de celui du bas + ses propre droit.
    SUPER_ADMIN("SUPER_ADMIN"),
    ADMIN("ADMIN"),
    USER("USER");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    private boolean isHigherThan(Role roleToCompare) {
        return this.ordinal() < roleToCompare.ordinal();
    }

    private boolean isHigherOrEqualThan(Role roleToCompare) {
        return this.ordinal() <= roleToCompare.ordinal();
    }

    public boolean isHigherThan(String role) {
        Role roleToCompare = Role.valueOf(role.toUpperCase());
        return this.isHigherThan(roleToCompare);
    }

    public boolean isHigherOrEqualThan(String role) {
        Role roleToCompare = Role.valueOf(role.toUpperCase());
        return this.isHigherOrEqualThan(roleToCompare);
    }
}
