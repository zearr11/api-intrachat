package com.api.intrachat.utils.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponse<T> {

    private Integer page; // página actual
    private Integer size; // tamaño solicitado
    private Integer itemsOnPage; // items en esta página
    private Long count; // total de registros
    private Integer totalPages; // total de páginas
    private T result; // result

}
