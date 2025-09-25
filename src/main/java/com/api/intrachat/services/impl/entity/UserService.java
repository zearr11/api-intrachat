package com.api.intrachat.services.impl.entity;

import com.api.intrachat.exceptions.errors.ErrorException400;
import com.api.intrachat.exceptions.errors.ErrorException404;
import com.api.intrachat.models.entity.Position;
import com.api.intrachat.services.interfaces.entity.IPositionService;
import com.api.intrachat.utils.constants.ValuesMessage;
import com.api.intrachat.utils.constants.ValuesPaginated;
import com.api.intrachat.utils.constructs.PasswordRandomConstruct;
import com.api.intrachat.utils.dto.request.UserRequest;
import com.api.intrachat.utils.dto.response.MessageResponse;
import com.api.intrachat.utils.dto.response.PaginatedResponse;
import com.api.intrachat.utils.dto.response.UserResponse;
import com.api.intrachat.models.entity.User;
import com.api.intrachat.repositories.entity.UserRepository;
import com.api.intrachat.services.interfaces.entity.IUserService;
import com.api.intrachat.utils.constants.ValuesUser;
import com.api.intrachat.utils.converts.UserConvert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final IPositionService positionService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       IPositionService positionService,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.positionService = positionService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ErrorException404(
                        ValuesMessage.notFoundMessage("User", id.toString())
                )
        );
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ErrorException404(
                        ValuesUser.emailNotFoundMessage(email)
                )
        );
    }

    @Override
    public PaginatedResponse<List<UserResponse>> findAllUsersPaginated(int page, int size) {

        if (page < 1 || size < 1) {
            throw new ErrorException400(ValuesPaginated.MESSAGE_ERROR_LENGTH_PAGE_SIZE);
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponse> users = userPage.getContent()
                .stream()
                .map(UserConvert::returnUserResponse)
                .toList();

        return new PaginatedResponse<>(
                page,
                size,
                users.size(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                users
        );

    }

    @Override
    public MessageResponse createUser(UserRequest userRequest) {

        Position position = positionService.findPositionById(userRequest.getIdPosition());
        String password = PasswordRandomConstruct.generatePassword();

        // Temporal
        System.out.println("Contraseña: " + password);

        User newUser = User.builder()
                .urlPhoto(ValuesUser.URL_PHOTO_DEFAULT)
                .names(userRequest.getNames())
                .lastnames(userRequest.getLastnames())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(password))
                .isEnabled(ValuesUser.IS_ENABLED_DEFAULT)
                .creationDate(LocalDateTime.now())
                .lastModification(LocalDateTime.now())
                .role(userRequest.getRole())
                .position(position)
                .build();

        userRepository.save(newUser);

        return MessageResponse.builder()
                .result(ValuesMessage.entityCreatedMessage("User"))
                .build();

    }

}
