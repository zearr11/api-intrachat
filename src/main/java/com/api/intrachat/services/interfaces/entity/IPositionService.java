package com.api.intrachat.services.interfaces.entity;

import com.api.intrachat.models.entity.Position;
import com.api.intrachat.utils.dto.response.PositionResponse;
import java.util.List;

public interface IPositionService {

    Position findPositionById(Long id);
    List<PositionResponse> findAllPositions();

}
