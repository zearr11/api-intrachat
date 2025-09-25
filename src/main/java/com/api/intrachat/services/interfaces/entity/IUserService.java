package com.api.intrachat.services.interfaces.entity;

import com.api.intrachat.models.entity.User;
import com.api.intrachat.utils.dto.request.UserRequest;
import com.api.intrachat.utils.dto.response.MessageResponse;
import com.api.intrachat.utils.dto.response.PaginatedResponse;
import com.api.intrachat.utils.dto.response.UserResponse;
import java.util.List;

public interface IUserService {

    User findUserById(Long id);
    User findUserByEmail(String email);
    PaginatedResponse<List<UserResponse>> findAllUsersPaginated(int page, int size);
    MessageResponse createUser(UserRequest userRequest);

}
