package io.bootify.quickcheck.rest;

import io.bootify.quickcheck.domain.Login;
import io.bootify.quickcheck.domain.Usuario;
import io.bootify.quickcheck.model.ClienteDTO;
import io.bootify.quickcheck.model.EstabelecimentoDTO;
import io.bootify.quickcheck.model.FuncionarioDTO;
import io.bootify.quickcheck.model.UsuarioDTO;
import io.bootify.quickcheck.service.ClienteService;
import io.bootify.quickcheck.service.EstabelecimentoService;
import io.bootify.quickcheck.service.FuncionarioService;
import io.bootify.quickcheck.service.UsuarioService;
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
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioResource {
    private final UsuarioService usuarioService;
    private final ClienteService clienteService;
    private final FuncionarioService funcionarioService;
    private final EstabelecimentoService estabelecimentoService;

    public UsuarioResource(final UsuarioService usuarioService,
                           ClienteService clienteService,
                           FuncionarioService funcionarioService,
                           EstabelecimentoService estabelecimentoService) {
        this.usuarioService = usuarioService;
        this.clienteService = clienteService;
        this.funcionarioService = funcionarioService;
        this.estabelecimentoService = estabelecimentoService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(usuarioService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUsuario(@RequestBody @Valid final UsuarioDTO usuarioDTO) {
        final Long createdId = usuarioService.create(usuarioDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateUsuario(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final UsuarioDTO usuarioDTO) {
        usuarioService.update(id, usuarioDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUsuario(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = usuarioService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Login login) {
        Usuario usuario = usuarioService.login(login.getEmail(), login.getSenha(), login.getRole());
        return switch (usuario.getRole()) {
            case "CLIENTE" -> {
                ClienteDTO cliente = clienteService.getClienteByUsuario(usuario);
                yield ResponseEntity.ok(cliente);
            }
            case "FUNCIONARIO" -> {
                FuncionarioDTO funcionario = funcionarioService.getFuncionarioByUsuario(usuario);
                yield ResponseEntity.ok(funcionario);
            }
            case "ESTABELECIMENTO" -> {
                EstabelecimentoDTO estabelecimento = estabelecimentoService.getEstabelecimentoByUsuario(usuario);
                yield ResponseEntity.ok(estabelecimento);
            }
            default -> ResponseEntity.ok(null);
        };
    }
}
