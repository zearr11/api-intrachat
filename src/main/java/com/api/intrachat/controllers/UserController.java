package com.api.intrachat.controllers;

import com.api.intrachat.services.interfaces.user.IUserService;
import com.api.intrachat.utils.constants.ValuesPaginated;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import com.api.intrachat.utils.dto.request.ok.UserRequest;
import com.api.intrachat.utils.dto.response.general.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.prefix}/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<GeneralResponse<?>> findAllUsersPaginated(
                    @RequestParam(defaultValue = ValuesPaginated.PAGE_DEFAULT) int page,
                    @RequestParam(defaultValue = ValuesPaginated.SIZE_DEFAULT) int size) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseConstruct.success(userService.findAllUsersPaginated(page, size))
        );
    }

    @PostMapping
    public ResponseEntity<GeneralResponse<?>> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseConstruct.success(userService.createUser(userRequest))
        );
    }

}
