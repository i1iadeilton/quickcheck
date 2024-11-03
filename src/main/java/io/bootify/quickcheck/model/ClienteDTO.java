package io.bootify.quickcheck.model;

import io.bootify.quickcheck.domain.Usuario;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClienteDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String cpf;

    @NotNull
    private LocalDate nascimento;

    @NotNull
    @Size(max = 1)
    private String sexo;

    @NotNull
    @Size(max = 255)
    private String latitude;

    @NotNull
    @Size(max = 255)
    private String longitude;

    @Size(max = 255)
    private String numeroCartaoSUS;

    private List<@Size(max = 255) String> comorbidades;

    @ClienteUsuarioUnique
    private Usuario usuario;

}
