package com.api.intrachat.utils.mappers;

import com.api.intrachat.utils.dto.response.entities.ok.UserResponse;
import com.api.intrachat.models.user.User;

public class UserMapper {

    public static UserResponse userToUserResponse(User user) {
        return new UserResponse(
                user.getIdUser(), user.getFile().getUrl(),
                user.getNames(), user.getLastnames(), user.getEmail(),
                user.getNumberPhone(), user.getRole(), user.getPosition().getPositionName(),
                user.getHeadquarters().getHeadquarters(), user.getCampaign().getCampaignName(),
                user.getArea().getAreaName(), user.getCreationDate(), user.getLastModification()
        );
    }

}
