package com.api.intrachat.services.impl.other;

import com.api.intrachat.dto.response.UsuarioResponse;
import com.api.intrachat.dto.response.dashboard.AlertaResponse;
import com.api.intrachat.dto.response.dashboard.InfoDiariaResponse;
import com.api.intrachat.dto.response.dashboard.InfoGeneralResponse;
import com.api.intrachat.dto.response.dashboard.UsuariosAltaPorMesResponse;
import com.api.intrachat.models.campania.*;
import com.api.intrachat.models.chat.Mensaje;
import com.api.intrachat.models.general.Usuario;
import com.api.intrachat.dto.response.dashboard.MensajesPromedioPorMesResponse;
import com.api.intrachat.repositories.chat.MensajeRepository;
import com.api.intrachat.repositories.chat.projection.MensajesPorMesProjection;
import com.api.intrachat.repositories.general.UsuarioRepository;
import com.api.intrachat.repositories.general.projections.UsuariosPorMesProjection;
import com.api.intrachat.services.interfaces.campania.*;
import com.api.intrachat.services.interfaces.chat.IMensajeService;
import com.api.intrachat.services.interfaces.other.IMetricaService;
import com.api.intrachat.utils.enums.Cargo;
import com.api.intrachat.utils.mappers.UsuarioMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MetricaService implements IMetricaService {

    private final UsuarioRepository usuarioRepository;
    private final MensajeRepository mensajeRepository;

    private final IEmpresaService empresaService;
    private final ISedeService sedeService;
    private final ICampaniaService campaniaService;
    private final IOperacionService operacionService;
    private final IEquipoService equipoService;
    private final IMensajeService mensajeService;

    public MetricaService(UsuarioRepository usuarioRepository,
                          MensajeRepository mensajeRepository,
                          IEmpresaService empresaService,
                          ISedeService sedeService,
                          ICampaniaService campaniaService,
                          IOperacionService operacionService,
                          IEquipoService equipoService,
                          IMensajeService mensajeService) {
        this.usuarioRepository = usuarioRepository;
        this.mensajeRepository = mensajeRepository;
        this.empresaService = empresaService;
        this.sedeService = sedeService;
        this.campaniaService = campaniaService;
        this.operacionService = operacionService;
        this.equipoService = equipoService;
        this.mensajeService = mensajeService;
    }

    @Override
    public AlertaResponse alertaUsuariosSinAsignar() {
        List<UsuarioResponse> jefesOperacion = usuarioRepository.findJefesOperacionSinActivas()
                .stream().map(UsuarioMapper::usuarioResponse).toList();

        List<UsuarioResponse> supervisores = usuarioRepository.findSinEquipoActivo(Cargo.SUPERVISOR.toString())
                .stream().map(UsuarioMapper::usuarioResponse).toList();

        List<UsuarioResponse> colaboradores = usuarioRepository.findSinEquipoActivo(Cargo.COLABORADOR.toString())
                .stream().map(UsuarioMapper::usuarioResponse).toList();

        return new AlertaResponse(jefesOperacion, supervisores, colaboradores);
    }

    @Override
    public InfoGeneralResponse infoGeneralEntidades() {
        // Usuarios
        List<Usuario> usuarios = usuarioRepository.findAll();
        final int usuariosActivos = usuarios.stream().filter(Usuario::getEstado).toList().size();
        final int usuariosInactivos = usuarios.stream().filter(u -> !u.getEstado()).toList().size();

        // Empresas
        List<Empresa> empresas = empresaService.obtenerEmpresas();
        final int empresasActivas = empresas.stream().filter(Empresa::getEstado).toList().size();
        final int empresasInactivas = empresas.stream().filter(emp -> !emp.getEstado())
                .toList().size();

        // Sedes
        List<Sede> sedes = sedeService.obtenerSedes();
        final int sedesActivas = sedes.stream().filter(Sede::getEstado).toList().size();
        final int sedesInactivas = sedes.stream().filter(sede -> !sede.getEstado())
                .toList().size();

        // Campañas
        List<Campania> campanias = campaniaService.obtenerCampaniasNormal();
        final int campaniasActivas = campanias.stream().filter(Campania::getEstado).toList().size();
        final int campaniasInactivas = campanias.stream().filter(c -> !c.getEstado()).toList().size();

        // Operaciones
        List<Operacion> operaciones = operacionService.obtenerOperacionesRegular();
        final int operacionesActivas = operaciones.stream().filter(op -> op.getFechaFinalizacion() == null)
                .toList().size();
        final int operacionesInactivas = operaciones.stream().filter(op -> op.getFechaFinalizacion() != null)
                .toList().size();

        // Equipos
        List<Equipo> equipos = equipoService.obtenerEquiposRegular();
        final int equiposActivos = equipos.stream().filter(eq -> eq.getFechaCierre() == null).toList().size();
        final int equiposInactivos = equipos.stream().filter(eq -> eq.getFechaCierre() != null).toList().size();

        return new InfoGeneralResponse(
                usuariosActivos,
                usuariosInactivos,
                empresasActivas,
                empresasInactivas,
                sedesActivas,
                sedesInactivas,
                campaniasActivas,
                campaniasInactivas,
                operacionesActivas,
                operacionesInactivas,
                equiposActivos,
                equiposInactivos
        );
    }

    @Override
    public InfoDiariaResponse infoDiariaAreasImportantes() {
        LocalDate hoy = LocalDate.now();

        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Operacion> operaciones = operacionService.obtenerOperacionesRegular();
        List<Equipo> equipos = equipoService.obtenerEquiposRegular();
        List<Mensaje> mensajes = mensajeService.obtenerMensajesRegular();

        List<Usuario> usuariosDeshabilitadosHoy = usuarios.stream()
                .filter(u -> Boolean.FALSE.equals(u.getEstado()))
                .filter(u -> u.getUltimaModificacion().toLocalDate().isEqual(hoy))
                .toList();

        List<Operacion> operacionesFinalizadasHoy = operaciones.stream()
                .filter(op -> op.getFechaFinalizacion() != null)
                .filter(op -> op.getFechaFinalizacion().toLocalDate().equals(hoy))
                .toList();

        List<Equipo> equiposCerradosHoy = equipos.stream()
                .filter(eq -> eq.getFechaCierre() != null)
                .filter(eq -> eq.getFechaCierre().toLocalDate().equals(hoy))
                .toList();

        List<Mensaje> mensajesDeHoy = mensajes.stream()
                .filter(m -> m.getFechaCreacion().toLocalDate().equals(hoy))
                .toList();

        return new InfoDiariaResponse(
                usuariosDeshabilitadosHoy.size(),
                operacionesFinalizadasHoy.size(),
                equiposCerradosHoy.size(),
                mensajesDeHoy.size()
        );
    }

    @Override
    public List<UsuariosAltaPorMesResponse> obtenerUsuariosDadosDeAltaAnual(int anio) {
        List<UsuariosPorMesProjection> datos = usuarioRepository.obtenerUsuariosPorMes(anio);

        Map<Integer, Long> mapaMesCantidad = datos.stream()
                .collect(Collectors.toMap(
                        UsuariosPorMesProjection::getMes,
                        UsuariosPorMesProjection::getCantidad
                ));

        return IntStream.rangeClosed(1, 12)
                .mapToObj(mes -> {
                    String nombreMes = Month.of(mes)
                            .getDisplayName(TextStyle.FULL, new Locale("es", "ES"))
                            .toUpperCase();

                    Long cantidad = mapaMesCantidad.getOrDefault(mes, 0L);

                    return new UsuariosAltaPorMesResponse(nombreMes, cantidad);
                })
                .toList();
    }

    @Override
    public List<MensajesPromedioPorMesResponse> obtenerMensajesPromedio(int anio) {
        List<MensajesPorMesProjection> datos = mensajeRepository.obtenerMensajesPorMes(anio);

        Map<Integer, Long> mapaMesCantidad = datos.stream()
                .collect(Collectors.toMap(
                        MensajesPorMesProjection::getMes,
                        MensajesPorMesProjection::getCantidad
                ));

        return IntStream.rangeClosed(1, 12)
                .mapToObj(mes -> {
                    YearMonth ym = YearMonth.of(anio, mes);
                    int diasMes = ym.lengthOfMonth();

                    Long cantidad = mapaMesCantidad.getOrDefault(mes, 0L);
                    double promedio = diasMes == 0 ? 0.0 : (double) cantidad / diasMes;

                    String nombreMes = Month.of(mes)
                            .getDisplayName(TextStyle.FULL, new Locale("es", "ES"))
                            .toUpperCase();

                    return new MensajesPromedioPorMesResponse(
                            nombreMes,
                            cantidad,
                            promedio
                    );
                })
                .toList();
    }

}
