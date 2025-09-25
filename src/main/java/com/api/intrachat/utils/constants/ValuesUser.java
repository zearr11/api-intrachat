package com.api.intrachat.utils.constants;

public class ValuesUser {

    public final static Boolean IS_ENABLED_DEFAULT = true;
    public final static String URL_PHOTO_DEFAULT = "https://res.cloudinary.com/dgsgtffmx/image/upload/v1758739956/icon-7797704_640_uzbata.png";

    public static String emailNotFoundMessage(String email) {
        return "User with email " + email + " not found.";
    }

}
