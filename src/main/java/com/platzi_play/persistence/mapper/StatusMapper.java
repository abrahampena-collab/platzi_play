package com.platzi_play.persistence.mapper;

import org.mapstruct.Named;

public class StatusMapper {

    @Named("stringToBoolean")
    public static boolean stringToBoolean(String status) {
        if (status == null) return false;

        return switch (status.toUpperCase()) {
            case "D" -> true;
            case "ND" -> false;
            default -> false;
        };
    }

    @Named("booleanToString")
    public static String booleanToString(boolean status) {
        // A simple ternary operator replaces the unsupported switch
        return status ? "D" : "ND";
    }
}