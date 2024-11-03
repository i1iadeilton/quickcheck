package io.bootify.quickcheck.rest;

import io.bootify.quickcheck.model.EstabelecimentoDTO;
import io.bootify.quickcheck.service.EstabelecimentoService;
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
@RequestMapping(value = "/api/estabelecimentos", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstabelecimentoResource {

    private final EstabelecimentoService estabelecimentoService;

    public EstabelecimentoResource(final EstabelecimentoService estabelecimentoService) {
        this.estabelecimentoService = estabelecimentoService;
    }

    @GetMapping
    public ResponseEntity<List<EstabelecimentoDTO>> getAllEstabelecimentos() {
        return ResponseEntity.ok(estabelecimentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstabelecimentoDTO> getEstabelecimento(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(estabelecimentoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEstabelecimento(
            @RequestBody @Valid final EstabelecimentoDTO estabelecimentoDTO) {
        final Long createdId = estabelecimentoService.create(estabelecimentoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateEstabelecimento(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final EstabelecimentoDTO estabelecimentoDTO) {
        estabelecimentoService.update(id, estabelecimentoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEstabelecimento(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = estabelecimentoService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        estabelecimentoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<EstabelecimentoDTO>> getEstabelecimentosByTipo(
            @RequestParam String tipo) {
        return ResponseEntity.ok(estabelecimentoService.findEstabelecimentoByTipo(tipo));
    }
}
