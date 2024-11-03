package io.bootify.quickcheck.model;

import io.bootify.quickcheck.domain.Estabelecimento;
import io.bootify.quickcheck.domain.Usuario;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FuncionarioDTO {

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
    private String especialidade;

    @NotNull
    @Size(max = 255)
    private String crm;

    private Estabelecimento estabelecimento;

    @FuncionarioUsuarioUnique
    private Usuario usuario;

}
