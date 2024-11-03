package io.bootify.quickcheck.rest;

import io.bootify.quickcheck.model.FuncionarioDTO;
import io.bootify.quickcheck.service.FuncionarioService;
import io.bootify.quickcheck.util.ReferencedException;
import io.bootify.quickcheck.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping(value = "/api/funcionarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class FuncionarioResource {

    private final FuncionarioService funcionarioService;

    public FuncionarioResource(final FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> getAllFuncionarios() {
        return ResponseEntity.ok(funcionarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> getFuncionario(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(funcionarioService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFuncionario(
            @RequestBody @Valid final FuncionarioDTO funcionarioDTO) {
        final Long createdId = funcionarioService.create(funcionarioDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFuncionario(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FuncionarioDTO funcionarioDTO) {
        funcionarioService.update(id, funcionarioDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = funcionarioService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<FuncionarioDTO>> getAllByEspecialidadeAndEstabelecimentoNomeLikeAndEstabelecimentoTipo(
            @RequestParam String especialidade,
            @RequestParam String estabelecimentoNome,
            @RequestParam String estabelecimentoTipo
    ) {
        return ResponseEntity.ok(funcionarioService
                .findAllByEspecialidadeAndEstabelecimentoNomeLikeAndEstabelecimentoTipo(especialidade, estabelecimentoNome, estabelecimentoTipo));
    }
}
