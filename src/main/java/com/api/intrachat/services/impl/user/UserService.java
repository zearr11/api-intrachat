package com.api.intrachat.services.impl.user;

import com.api.intrachat.exceptions.errors.ErrorException400;
import com.api.intrachat.exceptions.errors.ErrorException404;
import com.api.intrachat.models.user.CustomUserDetails;
import com.api.intrachat.models.user.User;
import com.api.intrachat.repositories.user.UserRepository;
import com.api.intrachat.services.interfaces.user.IPositionService;
import com.api.intrachat.services.interfaces.user.IUserService;
import com.api.intrachat.utils.constants.ValuesMessage;
import com.api.intrachat.utils.constants.ValuesPaginated;
import com.api.intrachat.utils.constants.ValuesUser;
import com.api.intrachat.utils.dto.request.ok.UserRequest;
import com.api.intrachat.utils.dto.response.general.PaginatedResponse;
import com.api.intrachat.utils.dto.response.general.ResultResponse;
import com.api.intrachat.utils.dto.response.entities.ok.UserResponse;
import com.api.intrachat.utils.mappers.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public User getUserCurrent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
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
    public User findUserByNumberPhone(String numberPhone) {
        return userRepository.findByNumberPhone(numberPhone).orElseThrow(
                () -> new ErrorException404(
                        ValuesUser.numberNotFoundMessage(numberPhone)
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
                .map(UserMapper::userToUserResponse)
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
    public ResultResponse createUser(UserRequest userRequest) {
        /*
        Position position = positionService.findPositionById(userRequest.getIdPosition());
        String password = PasswordRandomConstruct.generatePassword();

        if (userRepository.findByNumberPhone(userRequest.getNumberPhone()).isPresent())
            throw new ErrorException409(ValuesUser.CELL_PHONE_ALREADY_REGISTERED);
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent())
            throw new ErrorException409(ValuesUser.EMAIL_ALREADY_REGISTERED);

        // Temporal
        System.out.println("Contraseña: " + password);

        User newUser = User.builder()
                .urlPhoto(ValuesUser.URL_PHOTO_DEFAULT)
                .names(userRequest.getNames())
                .lastnames(userRequest.getLastnames())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(password))
                .isEnabled(ValuesUser.IS_ENABLED_DEFAULT)
                .numberPhone(userRequest.getNumberPhone())
                .info(ValuesUser.INFO_DEFAULT)
                .creationDate(LocalDateTime.now())
                .lastModification(LocalDateTime.now())
                .role(userRequest.getRole())
                .position(position)
                .build();

        userRepository.save(newUser);

        return ResultResponse.builder()
                .result(ValuesMessage.entityCreatedMessage("User"))
                .build();
        */
        return null;
    }
}
