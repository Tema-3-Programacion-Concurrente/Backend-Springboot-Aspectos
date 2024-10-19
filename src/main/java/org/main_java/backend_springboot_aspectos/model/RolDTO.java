package org.main_java.backend_springboot_aspectos.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RolDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String nombre;

}