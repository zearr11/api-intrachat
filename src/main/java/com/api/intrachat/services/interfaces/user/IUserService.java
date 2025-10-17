package com.api.intrachat.services.interfaces.user;

import com.api.intrachat.models.user.User;
import com.api.intrachat.utils.dto.request.ok.UserRequest;
import com.api.intrachat.utils.dto.response.general.ResultResponse;
import com.api.intrachat.utils.dto.response.general.PaginatedResponse;
import com.api.intrachat.utils.dto.response.entities.ok.UserResponse;
import java.util.List;

public interface IUserService {

    User getUserCurrent();
    User findUserById(Long id);
    User findUserByEmail(String email);
    User findUserByNumberPhone(String numberPhone);
    PaginatedResponse<List<UserResponse>> findAllUsersPaginated(int page, int size);
    ResultResponse createUser(UserRequest userRequest);

}
