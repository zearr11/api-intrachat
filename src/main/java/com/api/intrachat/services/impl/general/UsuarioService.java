package com.api.intrachat.services.impl.general;

import com.api.intrachat.dto.request.UsuarioRequest2;
import com.api.intrachat.models.general.Archivo;
import com.api.intrachat.services.interfaces.general.IArchivoService;
import com.api.intrachat.services.interfaces.other.IEmailService;
import com.api.intrachat.utils.constructs.RandomConstruct;
import com.api.intrachat.utils.enums.Cargo;
import com.api.intrachat.utils.exceptions.errors.ErrorException400;
import com.api.intrachat.utils.exceptions.errors.ErrorException404;
import com.api.intrachat.models.CustomUserDetails;
import com.api.intrachat.models.general.Persona;
import com.api.intrachat.models.general.Usuario;
import com.api.intrachat.repositories.general.PersonaRepository;
import com.api.intrachat.repositories.general.UsuarioRepository;
import com.api.intrachat.services.interfaces.general.IUsuarioService;
import com.api.intrachat.utils.constants.PaginatedConstants;
import com.api.intrachat.utils.constants.GeneralConstants;
import com.api.intrachat.utils.constants.UsuarioConstants;
import com.api.intrachat.dto.request.UsuarioRequest;
import com.api.intrachat.dto.response.UsuarioResponse;
import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.utils.exceptions.errors.ErrorException409;
import com.api.intrachat.utils.mappers.UsuarioMapper;
import com.api.intrachat.utils.helpers.DNIValidacion;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;

@Service
public class UsuarioService implements IUsuarioService {

    private final PasswordEncoder passwordEncoder;

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;

    private final IArchivoService archivoService;
    private final IEmailService emailService;

    public UsuarioService(PasswordEncoder passwordEncoder,
                          UsuarioRepository usuarioRepository,
                          PersonaRepository personaRepository,
                          IArchivoService archivoService,
                          IEmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
        this.archivoService = archivoService;
        this.emailService = emailService;
    }

    @Override
    public Usuario obtenerUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUsuario();
    }

    @Override
    public Usuario obtenerUsuarioPorID(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new ErrorException404(
                        GeneralConstants.mensajeEntidadNoExiste("Usuario", id.toString())
                )
        );
    }

    @Override
    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ErrorException404(
                        UsuarioConstants.mensajeEmailNoExiste(email)
                )
        );
    }

    @Override
    public Usuario obtenerUsuarioPorCelular(String celular) {
        Persona persona = personaRepository.findByCelular(celular).orElseThrow(
                () -> new ErrorException404(
                        UsuarioConstants.mensajeCelularNoExiste(celular)
                ));

        return usuarioRepository.findByPersona(persona);
    }

    @Override
    public List<Usuario> buscarContactosPorCampania(String filtro, Long idCampania, Long idUsuarioAExcluir) {
        return usuarioRepository.buscarContactosPorCampania(
                filtro, idCampania, idUsuarioAExcluir
        );
    }

    @Override
    public PaginatedResponse<List<UsuarioResponse>> obtenerUsuariosDeEquipoYSinCampania(int page, int size,
                                                                                        boolean estado, Cargo cargo,
                                                                                        String filtro, Long idEquipo) {
        if (page < 1 || size < 1) {
            throw new ErrorException400(PaginatedConstants.ERROR_PAGINA_LONGITUD_INVALIDO);
        }

        Usuario usuarioActual = obtenerUsuarioActual();

        Pageable pageable = PageRequest.of(
                page - 1, size, Sort.by("persona.nombres")
                        .ascending().and(Sort.by("persona.apellidos").ascending())
        );

        Page<Usuario> listado = usuarioRepository.buscarUsuariosDeEquipoYsinCampania(
                estado,
                cargo,
                filtro,
                usuarioActual.getId(),
                idEquipo,
                pageable
        );

        List<UsuarioResponse> usuarios = listado.getContent()
                .stream()
                .map(UsuarioMapper::usuarioResponse)
                .toList();

        return new PaginatedResponse<>(
                page,
                size,
                usuarios.size(),
                listado.getTotalElements(),
                listado.getTotalPages(),
                usuarios
        );
    }

    @Override
    public PaginatedResponse<List<UsuarioResponse>> obtenerUsuariosPaginado(int page, int size,
                                                                            boolean estado, String filtro,
                                                                            Cargo cargo, Boolean enCampania) {
        if (page < 1 || size < 1) {
            throw new ErrorException400(PaginatedConstants.ERROR_PAGINA_LONGITUD_INVALIDO);
        }

        Usuario usuarioActual = obtenerUsuarioActual();

        Pageable pageable = PageRequest.of(
                page - 1, size, Sort.by("persona.nombres")
                        .ascending().and(Sort.by("persona.apellidos").ascending())
        );

        Page<Usuario> listado = usuarioRepository.buscarUsuariosConPaginacion(
                estado,
                usuarioActual.getId(),
                filtro,
                cargo,
                enCampania,
                pageable
        );

        List<UsuarioResponse> usuarios = listado.getContent()
                .stream()
                .map(UsuarioMapper::usuarioResponse)
                .toList();

        return new PaginatedResponse<>(
                page,
                size,
                usuarios.size(),
                listado.getTotalElements(),
                listado.getTotalPages(),
                usuarios
        );
    }

    @Transactional
    @Override
    public String crearUsuario(UsuarioRequest usuarioRequest) {

        LocalDateTime fechaActual = LocalDateTime.now();
        String txtPassword = RandomConstruct.generarCadenaAleatoria();

        // Validacion documento valido
        DNIValidacion.documentoIdentidadValido(usuarioRequest.getNumeroDoc(),
                usuarioRequest.getTipoDoc());

        // Validacion documento con base de datos
        if (personaRepository.findByTipoDocAndNumeroDoc(
                usuarioRequest.getTipoDoc(), usuarioRequest.getNumeroDoc()).isPresent())
            throw new ErrorException409(UsuarioConstants.ERROR_TIPO_DOC_NUM_DOC_REGISTRADO);

        // Validacion celular con base de datos
        if (personaRepository.findByCelular(usuarioRequest.getCelular()).isPresent())
            throw new ErrorException409(UsuarioConstants.ERROR_CELULAR_REGISTRADO);

        // Validacion email con base de datos
        if (usuarioRepository.findByEmail(usuarioRequest.getEmail()).isPresent())
            throw new ErrorException409(UsuarioConstants.ERROR_EMAIL_REGISTRADO);

        // Temporal
        System.out.println("Contraseña: " + txtPassword);

        // Búsqueda archivo default
        Archivo imagenPerfil = archivoService.obtenerArchivoPorID(
                UsuarioConstants.ID_ARCHIVO_DEFAULT);

        // Creacion de persona
        Persona nuevaPersona = Persona.builder()
                .nombres(usuarioRequest.getNombres())
                .apellidos(usuarioRequest.getApellidos())
                .tipoDoc(usuarioRequest.getTipoDoc())
                .numeroDoc(usuarioRequest.getNumeroDoc())
                .genero(usuarioRequest.getGenero())
                .celular(usuarioRequest.getCelular())
                .informacion(UsuarioConstants.INFO_DEFAULT)
                .build();

        // Inserción en tabla persona
        nuevaPersona = personaRepository.save(nuevaPersona);

        // Creación de usuario
        Usuario nuevoUsuario = Usuario.builder()
                .email(usuarioRequest.getEmail())
                .password(passwordEncoder.encode(txtPassword))
                .rol(usuarioRequest.getRol())
                .cargo(usuarioRequest.getCargo())
                .estado(GeneralConstants.ESTADO_DEFAULT)
                .fechaCreacion(fechaActual)
                .ultimaModificacion(fechaActual)
                .imagenPerfil(imagenPerfil)
                .persona(nuevaPersona)
                .build();

        // Inserción en tabla usuario
        usuarioRepository.save(nuevoUsuario);

        // Envío de credenciales
        /*
        emailService.enviarCredencialesACorreo(
                nuevoUsuario.getEmail(), UsuarioConstants.ASUNTO_DEFAULT_BIENVENIDA_EMAIL,
                nuevoUsuario.getPersona().getNombres(), nuevoUsuario.getPersona().getApellidos(),
                txtPassword
        ); */

        // Retorno mensaje string
        return GeneralConstants.mensajeEntidadCreada("Usuario");
    }

    @Transactional
    @Override
    public String modificarUsuario(Long id, UsuarioRequest2 usuarioRequest) {

        Usuario usuarioModificar = obtenerUsuarioPorID(id);
        // Nombres
        if (usuarioRequest.getNombres() != null
                && !usuarioRequest.getNombres().isBlank()) {
            usuarioModificar.getPersona().setNombres(usuarioRequest.getNombres());
        }
        // Apellidos
        if (usuarioRequest.getApellidos() != null
                && !usuarioRequest.getApellidos().isBlank()) {
            usuarioModificar.getPersona().setApellidos(usuarioRequest.getApellidos());
        }
        // Tipo de documento y numero de documento
        if (usuarioRequest.getTipoDoc() != null &&
                usuarioRequest.getNumeroDoc() != null
                && !usuarioRequest.getNumeroDoc().isBlank()) {

            // Validacion documento valido
            DNIValidacion.documentoIdentidadValido(
                    usuarioRequest.getNumeroDoc(), usuarioRequest.getTipoDoc()
            );

            // Encontrar en bd persona con tipo y número de documento igual
            Optional<Persona> personaCoincidente = personaRepository.findByTipoDocAndNumeroDoc(
                    usuarioRequest.getTipoDoc(), usuarioRequest.getNumeroDoc());

            // Si existe y es diferente del id de la persona actual, genera error
            if (personaCoincidente.isPresent()
                    && !usuarioModificar.getPersona().getId().equals(personaCoincidente.get().getId()))
                throw new ErrorException409(UsuarioConstants.ERROR_TIPO_DOC_NUM_DOC_REGISTRADO);

            usuarioModificar.getPersona().setTipoDoc(usuarioRequest.getTipoDoc());
            usuarioModificar.getPersona().setNumeroDoc(usuarioRequest.getNumeroDoc());
        }
        // Genero
        if (usuarioRequest.getGenero() != null) {
            usuarioModificar.getPersona().setGenero(usuarioRequest.getGenero());
        }
        // Celular
        if (usuarioRequest.getCelular() != null
                && !usuarioRequest.getCelular().isBlank()) {

            // Encontrar en bd persona con celular igual
            Optional<Persona> personaCoincidente = personaRepository.findByCelular(usuarioRequest.getCelular());

            // Si existe y es diferente del id de la persona actual, genera error
            if (personaCoincidente.isPresent()
                    && !usuarioModificar.getPersona().getId().equals(personaCoincidente.get().getId()))
                throw new ErrorException409(UsuarioConstants.ERROR_CELULAR_REGISTRADO);

            usuarioModificar.getPersona().setCelular(usuarioRequest.getCelular());
        }
        // Informacion
        if (usuarioRequest.getInformacion() != null
                && !usuarioRequest.getInformacion().isBlank()) {
            usuarioModificar.getPersona().setInformacion(usuarioRequest.getInformacion());
        }
        // Email
        if (usuarioRequest.getEmail() != null
                && !usuarioRequest.getEmail().isBlank()) {

            // Encontrar en bd usuario con email igual
            Optional<Usuario> usuarioCoincidente = usuarioRepository.findByEmail(usuarioRequest.getEmail());

            // Si existe y es diferente del id del usuario actual, genera error
            if (usuarioCoincidente.isPresent()
                    && !usuarioModificar.getId().equals(usuarioCoincidente.get().getId())) {
                throw new ErrorException409(UsuarioConstants.ERROR_EMAIL_REGISTRADO);
            }

            usuarioModificar.setEmail(usuarioRequest.getEmail());
        }
        // Password
        if (usuarioRequest.getPassword() != null
                && !usuarioRequest.getPassword().isBlank()) {
            usuarioModificar.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
        }
        // Rol
        if (usuarioRequest.getRol() != null) {
            usuarioModificar.setRol(usuarioRequest.getRol());
        }
        // Cargo
        if (usuarioRequest.getCargo() != null) {
            usuarioModificar.setCargo(usuarioRequest.getCargo());
        }
        // Estado
        if (usuarioRequest.getEstado() != null) {
            usuarioModificar.setEstado(usuarioRequest.getEstado());
        }

        // Falta implementar validación para el cambio de rol no permitiéndolo en caso tenga campaña activa

        // Actualizar ultima modificación de entidad
        usuarioModificar.setUltimaModificacion(LocalDateTime.now());

        // Actualizar entidad en tabla
        usuarioRepository.save(usuarioModificar);

        return GeneralConstants.mensajeEntidadActualizada("Usuario");
    }

    @Transactional
    @Override
    public String modificarImagenUsuario(Long id, MultipartFile archivo) {

        // Validacion: Lanza excepción si el archivo no es una imagen
        archivoService.validarImagen(archivo);

        // Búsqueda de usuario a modificar
        Usuario usuarioModificar = obtenerUsuarioPorID(id);

        // Asignación de archivo actual
        Archivo archivoActual = usuarioModificar.getImagenPerfil();

        // Actualizar ultima modificación de entidad
        usuarioModificar.setUltimaModificacion(LocalDateTime.now());

        // Si es un archivo compartido, se crea un nuevo archivo y se asigna al usuario
        if (archivoActual.getId().equals(UsuarioConstants.ID_ARCHIVO_DEFAULT)) {
            Archivo nuevoArchivo = archivoService.crearArchivo(archivo);
            usuarioModificar.setImagenPerfil(nuevoArchivo);
            usuarioRepository.save(usuarioModificar);
        }
        // Si es un archivo personal solo se actualiza el archivo
        else {
            archivoService.modificarArchivo(archivoActual, archivo);
        }

        return GeneralConstants.mensajeEntidadActualizada("Usuario");
    }

}
