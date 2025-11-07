package com.api.intrachat.services.impl.campania;

import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.models.campania.Equipo;
import com.api.intrachat.repositories.campania.EquipoRepository;
import com.api.intrachat.services.interfaces.campania.IEquipoService;
import com.api.intrachat.utils.constants.GeneralConstants;
import org.springframework.stereotype.Service;

@Service
public class EquipoService implements IEquipoService {

    private final EquipoRepository equipoRepository;

    public EquipoService(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    @Override
    public Equipo obtenerEquipoPorID(Long id) {
        return equipoRepository.findById(id).orElseThrow(
                () -> new ErrorException404(
                        GeneralConstants.mensajeEntidadNoExiste("Equipo", id.toString())
                )
        );
    }

}
