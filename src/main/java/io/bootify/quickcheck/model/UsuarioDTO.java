package io.bootify.quickcheck.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 255)
    private String senha;

    @NotNull
    @Size(max = 255)
    private String role;

    @NotNull
    @Size(max = 255)
    private String nome;

    @NotNull
    @Size(max = 255)
    private String telefone;

    @NotNull
    @Size(max = 255)
    private String endereco;

    @Size(max = 255)
    private String imagem;

}
