package io.bootify.quickcheck.rest;

import io.bootify.quickcheck.model.ClienteDTO;
import io.bootify.quickcheck.service.ClienteService;
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
@RequestMapping(value = "/api/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteResource {

    private final ClienteService clienteService;

    public ClienteResource(final ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getCliente(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(clienteService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCliente(@RequestBody @Valid final ClienteDTO clienteDTO) {
        final Long createdId = clienteService.create(clienteDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCliente(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ClienteDTO clienteDTO) {
        clienteService.update(id, clienteDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCliente(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = clienteService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
