package com.api.intrachat.controllers;

import com.api.intrachat.services.interfaces.entity.IPositionService;
import com.api.intrachat.utils.constructs.ResponseConstruct;
import com.api.intrachat.utils.dto.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.prefix}/positions")
public class PositionController {

    private final IPositionService positionService;

    public PositionController(IPositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public ResponseEntity<GeneralResponse<?>> findAllPositions() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseConstruct.success(
                positionService.findAllPositions()
        ));
    }

}
