package com.api.intrachat.utils.converts;

import com.api.intrachat.utils.dto.response.UserResponse;
import com.api.intrachat.models.entity.User;

public class UserConvert {

    public static UserResponse returnUserResponse(User user) {
        return new UserResponse(
                user.getIdUser(), user.getUrlPhoto(),
                user.getNames(), user.getLastnames(), user.getEmail(), user.getPosition().getPositionName(),
                user.getRole().name(), user.getCreationDate(), user.getLastModification()
        );
    }

}
