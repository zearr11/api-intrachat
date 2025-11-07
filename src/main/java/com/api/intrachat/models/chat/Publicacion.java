package com.api.intrachat.models.chat;

import com.api.intrachat.models.general.Archivo;
import com.api.intrachat.models.general.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "publicaciones")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @ManyToOne
    @JoinColumn(name = "fk_id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "fk_id_grupo")
    private Grupo grupo;

    @ManyToMany
    @JoinTable(
            name = "publicacion_archivos",
            joinColumns = @JoinColumn(name = "id_publicacion"),
            inverseJoinColumns = @JoinColumn(name = "id_archivo")
    )
    private Set<Archivo> publicacionArchivos = new HashSet<>();

    public void addPublicacionArchivos(Archivo archivo) {
        publicacionArchivos.add(archivo);
    }

}
