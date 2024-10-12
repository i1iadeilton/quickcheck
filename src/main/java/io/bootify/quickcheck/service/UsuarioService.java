package io.bootify.quickcheck.service;

import io.bootify.quickcheck.domain.Cliente;
import io.bootify.quickcheck.domain.Estabelecimento;
import io.bootify.quickcheck.domain.Funcionario;
import io.bootify.quickcheck.domain.Usuario;
import io.bootify.quickcheck.model.UsuarioDTO;
import io.bootify.quickcheck.repos.ClienteRepository;
import io.bootify.quickcheck.repos.EstabelecimentoRepository;
import io.bootify.quickcheck.repos.FuncionarioRepository;
import io.bootify.quickcheck.repos.UsuarioRepository;
import io.bootify.quickcheck.util.NotFoundException;
import io.bootify.quickcheck.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;

    public UsuarioService(final UsuarioRepository usuarioRepository,
            final ClienteRepository clienteRepository,
            final FuncionarioRepository funcionarioRepository,
            final EstabelecimentoRepository estabelecimentoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    public List<UsuarioDTO> findAll() {
        final List<Usuario> usuarios = usuarioRepository.findAll(Sort.by("id"));
        return usuarios.stream()
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .toList();
    }

    public UsuarioDTO get(final Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> mapToDTO(usuario, new UsuarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UsuarioDTO usuarioDTO) {
        final Usuario usuario = new Usuario();
        mapToEntity(usuarioDTO, usuario);
        return usuarioRepository.save(usuario).getId();
    }

    public void update(final Long id, final UsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(usuarioDTO, usuario);
        usuarioRepository.save(usuario);
    }

    public void delete(final Long id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO mapToDTO(final Usuario usuario, final UsuarioDTO usuarioDTO) {
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setSenha(usuario.getSenha());
        usuarioDTO.setRole(usuario.getRole());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setTelefone(usuario.getTelefone());
        usuarioDTO.setEndereco(usuario.getEndereco());
        usuarioDTO.setImagem(usuario.getImagem());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO, final Usuario usuario) {
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setRole(usuarioDTO.getRole());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setTelefone(usuarioDTO.getTelefone());
        usuario.setEndereco(usuarioDTO.getEndereco());
        usuario.setImagem(usuarioDTO.getImagem());
        return usuario;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Cliente usuarioCliente = clienteRepository.findFirstByUsuario(usuario);
        if (usuarioCliente != null) {
            referencedWarning.setKey("usuario.cliente.usuario.referenced");
            referencedWarning.addParam(usuarioCliente.getId());
            return referencedWarning;
        }
        final Funcionario usuarioFuncionario = funcionarioRepository.findFirstByUsuario(usuario);
        if (usuarioFuncionario != null) {
            referencedWarning.setKey("usuario.funcionario.usuario.referenced");
            referencedWarning.addParam(usuarioFuncionario.getId());
            return referencedWarning;
        }
        final Estabelecimento usuarioEstabelecimento = estabelecimentoRepository.findFirstByUsuario(usuario);
        if (usuarioEstabelecimento != null) {
            referencedWarning.setKey("usuario.estabelecimento.usuario.referenced");
            referencedWarning.addParam(usuarioEstabelecimento.getId());
            return referencedWarning;
        }
        return null;
    }

}
