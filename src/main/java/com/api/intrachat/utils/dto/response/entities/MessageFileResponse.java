package com.api.intrachat.utils.dto.response.entities;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageFileResponse {

    private String fileName;
    private String fileType;
    private Integer size;
    private String urlFile;

}
