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

    @GetMapping("/search")
    public ResponseEntity<List<HorarioDTO>> getHorarioByEstabelecimentoIdAndStatusAndFuncionarioEspecialidade(
            @RequestParam Long estabelecimentoId,
            @RequestParam String status,
            @RequestParam Optional<String> especialidade) {
        if (especialidade.isPresent()) {
            return ResponseEntity.ok(horarioService.findAllByEstabelecimentoIdAndStatusAndFuncionarioEspecialidade(estabelecimentoId, status, especialidade));
        } else {
        return ResponseEntity.ok(horarioService.findAllByEstabelecimentoIdAndStatus(estabelecimentoId, status));
        }
    }

    @GetMapping("/search/estabelecimentos")
    public ResponseEntity<List<HorarioDTO>> getHorarioByStatusAndEspecialidadeAndNomeAndTipo(
            @RequestParam LocalDateTime horarioAtendimento,
            @RequestParam String status,
            @RequestParam String especialidade,
            @RequestParam Optional<String> nomeFuncionario,
            @RequestParam Optional<String> nomeEstabelecimento) {
            return ResponseEntity.ok(horarioService.findAllByHorarioAndEstabelecimentoAndFuncionario(horarioAtendimento, status, especialidade, nomeFuncionario, nomeEstabelecimento));
    }

}
