package com.api.intrachat.services.interfaces.user;

import com.api.intrachat.models.user.Position;
import com.api.intrachat.utils.dto.response.entities.ok.PositionResponse;
import java.util.List;

public interface IPositionService {

    Position findPositionById(Long id);
    List<PositionResponse> findAllPositions();

}
