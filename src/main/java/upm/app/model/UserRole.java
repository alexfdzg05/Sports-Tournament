package upm.app.model;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum UserRole {
    ADMIN("ADMIN,admin,administrator,administrador"),
    PLAYER("PLAYER,player,jugador"),
    USER("USER,user,usuario");

    private String values;
    UserRole(String values) {
        this.values = values;
    }

    public String getValues() {
        return values;
    }

    public static List<UserRole> role(){
        return Arrays.asList(UserRole.values());
    }

}
