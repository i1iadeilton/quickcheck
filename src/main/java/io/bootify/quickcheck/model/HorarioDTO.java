package io.bootify.quickcheck.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class HorarioDTO {

    private Long id;

    @NotNull
    private LocalDateTime horarioAtendimento;

    @NotNull
    private LocalDateTime horarioAgendamento;

    @NotNull
    @Size(max = 255)
    private String status;

    @Size(max = 500)
    private String descricao;

    @Size(max = 500)
    private String prontuario;

    private Long funcionario;

    private Long estabelecimento;

    private Long cliente;

}
