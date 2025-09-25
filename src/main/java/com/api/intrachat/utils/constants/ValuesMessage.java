package com.api.intrachat.utils.constants;

public class ValuesMessage {

    public static final String GENERIC_NOT_FOUND_MESSAGE = "No results found";

    public static String notFoundMessage(String entity, String code) {
        return entity + " with code " + code + " not found.";
    }

    public static String entityCreatedMessage(String entity) {
        return entity + " created successfully.";
    }

}
