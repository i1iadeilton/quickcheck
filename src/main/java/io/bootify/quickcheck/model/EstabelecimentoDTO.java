package io.bootify.quickcheck.model;

import io.bootify.quickcheck.domain.Usuario;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EstabelecimentoDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String cnpj;

    @NotNull
    @Size(max = 255)
    private String latitude;

    @NotNull
    @Size(max = 255)
    private String longitude;

    @NotNull
    @Size(max = 500)
    private String horarioFuncionamento;

    @NotNull
    @Size(max = 500)
    private String descricao;

    @NotNull
    private Boolean assinante;

    @EstabelecimentoUsuarioUnique
    private Usuario usuario;

}
