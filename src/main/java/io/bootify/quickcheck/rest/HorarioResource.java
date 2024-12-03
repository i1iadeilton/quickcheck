package io.bootify.quickcheck.rest;

import io.bootify.quickcheck.model.HorarioDTO;
import io.bootify.quickcheck.service.HorarioService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = {
        "Authorization",
        "Origin",
        "Id"
})
@RestController
@RequestMapping(value = "/api/horarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class HorarioResource {

    private final HorarioService horarioService;

    public HorarioResource(final HorarioService horarioService) {
        this.horarioService = horarioService;
    }

    @GetMapping
    public ResponseEntity<List<HorarioDTO>> getAllHorarios() {
        return ResponseEntity.ok(horarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioDTO> getHorario(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(horarioService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createHorario(@RequestBody @Valid final HorarioDTO horarioDTO) {
        final Long createdId = horarioService.create(horarioDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateHorario(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final HorarioDTO horarioDTO) {
        horarioService.update(id, horarioDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteHorario(@PathVariable(name = "id") final Long id) {
        horarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/clientes")
    public ResponseEntity<List<HorarioDTO>> getHorarioByClientes(
            @RequestParam Long clienteId,
            @RequestParam Optional<String> status,
            @RequestParam Optional<String> especialidade,
            @RequestParam Optional<String> nomeFuncionario,
            @RequestParam Optional<String> nomeEstabelecimento
            ) {
            return ResponseEntity.ok(horarioService.findAllByClienteIdAndStatus(
                    clienteId,
                    status,
                    especialidade,
                    nomeFuncionario,
                    nomeEstabelecimento
            ));
        }

    @GetMapping("/search/funcionarios")
    public ResponseEntity<List<HorarioDTO>> getHorarioByFuncionarios(
            @RequestParam Long funcionarioId,
            @RequestParam Optional<String>  status,
            @RequestParam Optional<String>  nomeEstabelecimento,
            @RequestParam Optional<String> nomeCliente
    ) {
        return ResponseEntity.ok(horarioService.findAllByFuncionarioIdAndStatus(
                funcionarioId,
                status,
                nomeEstabelecimento,
                nomeCliente
        ));
    }

    @GetMapping("/search/estabelecimentos")
    public ResponseEntity<List<HorarioDTO>> getHorarioByEstabelecimentos(
            @RequestParam Long estabelecimentoId,
            @RequestParam Optional<String> status,
            @RequestParam Optional<String> nomeFuncionario,
            @RequestParam Optional<String> especialidade,
            @RequestParam Optional<String> nomeCliente
    ) {
        return ResponseEntity.ok(horarioService.findAllByEstabelecimentoIdAndStatus(
                estabelecimentoId,
                status,
                nomeFuncionario,
                especialidade,
                nomeCliente
        ));
    }

    @GetMapping("/search/horarios")
    public ResponseEntity<List<HorarioDTO>> getHorarioByParams(
            @RequestParam LocalDateTime horarioAtendimento,
            @RequestParam String status,
            @RequestParam String especialidade,
            @RequestParam Optional<String> nomeFuncionario,
            @RequestParam Optional<String> nomeEstabelecimento) {
            return ResponseEntity.ok(horarioService.findAllByHorarioAndEstabelecimentoAndFuncionario(horarioAtendimento, status, especialidade, nomeFuncionario, nomeEstabelecimento));
    }

}
