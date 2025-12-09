package com.api.intrachat.services.interfaces.other;

import com.api.intrachat.dto.response.dashboard.AlertaResponse;
import com.api.intrachat.dto.response.dashboard.InfoDiariaResponse;
import com.api.intrachat.dto.response.dashboard.InfoGeneralResponse;
import com.api.intrachat.dto.response.dashboard.UsuariosAltaPorMesResponse;
import com.api.intrachat.dto.response.dashboard.MensajesPromedioPorMesResponse;
import java.util.List;

public interface IMetricaService {

    AlertaResponse alertaUsuariosSinAsignar();
    InfoGeneralResponse infoGeneralEntidades();
    InfoDiariaResponse infoDiariaAreasImportantes();

    List<UsuariosAltaPorMesResponse> obtenerUsuariosDadosDeAltaAnual(int anio);
    List<MensajesPromedioPorMesResponse> obtenerMensajesPromedio(int anio);
}
