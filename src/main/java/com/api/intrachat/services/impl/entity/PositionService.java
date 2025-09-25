package com.api.intrachat.services.impl.entity;

import com.api.intrachat.exceptions.errors.ErrorException404;
import com.api.intrachat.models.entity.Position;
import com.api.intrachat.repositories.entity.PositionRepository;
import com.api.intrachat.services.interfaces.entity.IPositionService;
import com.api.intrachat.utils.constants.ValuesMessage;
import com.api.intrachat.utils.dto.response.PositionResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PositionService implements IPositionService {

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public Position findPositionById(Long id) {
        return positionRepository.findById(id).orElseThrow(
                () -> new ErrorException404(
                        ValuesMessage.notFoundMessage("Position", id.toString())
                )
        );
    }

    @Override
    public List<PositionResponse> findAllPositions() {

        List<Position> positions = positionRepository.findAll();

        if (positions.isEmpty()) {
            throw new ErrorException404(ValuesMessage.GENERIC_NOT_FOUND_MESSAGE);
        }

        return positions.stream()
                .map(obj -> new PositionResponse(
                        obj.getIdPosition(), obj.getPositionName()
                )).toList();

    }

}
