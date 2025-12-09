package com.api.intrachat.services.interfaces.general;

import com.api.intrachat.dto.request.UsuarioRequest2;
import com.api.intrachat.models.general.Usuario;
import com.api.intrachat.dto.request.UsuarioRequest;
import com.api.intrachat.dto.response.UsuarioResponse;
import com.api.intrachat.dto.generics.PaginatedResponse;
import com.api.intrachat.utils.enums.Cargo;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface IUsuarioService  {

    Usuario obtenerUsuarioActual();
    Usuario obtenerUsuarioPorID(Long id);
    Usuario obtenerUsuarioPorEmail(String email);
    Usuario obtenerUsuarioPorCelular(String celular);
    List<Usuario> buscarContactosPorCampania(String filtro, Long idCampania, Long idUsuarioAExcluir);

    PaginatedResponse<List<UsuarioResponse>> obtenerUsuariosDeOperacionRegular(int page, int size,
                                                                               String filtro);
    PaginatedResponse<List<UsuarioResponse>> obtenerUsuariosDeOperacionEdit(int page, int size,
                                                                            String filtro,
                                                                            Long idOperacion);

    PaginatedResponse<List<UsuarioResponse>> obtenerUsuariosDeEquipoYSinCampania(int page, int size,
                                                                                 boolean estado, Cargo cargo,
                                                                                 String filtro, Long idEquipo);

    PaginatedResponse<List<UsuarioResponse>> obtenerUsuariosPaginado(int page, int size,
                                                                     boolean estado, String filtro,
                                                                     Cargo cargo, Boolean enCampania);

    String crearUsuario(UsuarioRequest usuarioRequest);
    String modificarUsuario(Long id, UsuarioRequest2 usuarioRequest);
    String modificarImagenUsuario(Long id, MultipartFile archivo);

}
