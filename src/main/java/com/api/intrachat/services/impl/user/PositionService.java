package com.api.intrachat.services.impl.user;

import com.api.intrachat.exceptions.errors.ErrorException404;
import com.api.intrachat.models.user.Position;
import com.api.intrachat.repositories.user.PositionRepository;
import com.api.intrachat.services.interfaces.user.IPositionService;
import com.api.intrachat.utils.dto.response.entities.ok.PositionResponse;
import com.api.intrachat.utils.constants.ValuesMessage;
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
    };

    @Override
    public List<PositionResponse> findAllPositions() {
        List<Position> positions = positionRepository.findAll();

        if (positions.isEmpty())
            throw new ErrorException404(ValuesMessage.GENERIC_NOT_FOUND_MESSAGE);

        return positions.stream()
                .map(obj -> new PositionResponse(
                        obj.getIdPosition(), obj.getPositionName()
                )).toList();
    }

}
