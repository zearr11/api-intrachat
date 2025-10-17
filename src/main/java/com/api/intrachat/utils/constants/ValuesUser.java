package com.api.intrachat.utils.constants;

public class ValuesUser {

    public final static Boolean IS_ENABLED_DEFAULT = true;
    public final static String URL_PHOTO_DEFAULT =
            "https://res.cloudinary.com/dgsgtffmx/image/upload/v1758739956/icon-7797704_640_uzbata.png";
    public final static String INFO_DEFAULT = "Disponible";

    public static String emailNotFoundMessage(String email) {
        return "User with email " + email + " not found.";
    }

    public static String numberNotFoundMessage(String numberPhone) {
        return "User with number " + numberPhone + " not found.";
    }

    public final static String CELL_PHONE_ALREADY_REGISTERED = "Cell phone number already registered";
    public final static String EMAIL_ALREADY_REGISTERED = "Email already registered";

}
