package io.bootify.quickcheck.rest;

import io.bootify.quickcheck.model.HorarioDTO;
import io.bootify.quickcheck.service.HorarioService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

}
