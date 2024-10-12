package io.bootify.quickcheck.service;

import io.bootify.quickcheck.domain.Cliente;
import io.bootify.quickcheck.domain.Horario;
import io.bootify.quickcheck.domain.Usuario;
import io.bootify.quickcheck.model.ClienteDTO;
import io.bootify.quickcheck.repos.ClienteRepository;
import io.bootify.quickcheck.repos.HorarioRepository;
import io.bootify.quickcheck.repos.UsuarioRepository;
import io.bootify.quickcheck.util.NotFoundException;
import io.bootify.quickcheck.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final HorarioRepository horarioRepository;

    public ClienteService(final ClienteRepository clienteRepository,
            final UsuarioRepository usuarioRepository, final HorarioRepository horarioRepository) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.horarioRepository = horarioRepository;
    }

    public List<ClienteDTO> findAll() {
        final List<Cliente> clientes = clienteRepository.findAll(Sort.by("id"));
        return clientes.stream()
                .map(cliente -> mapToDTO(cliente, new ClienteDTO()))
                .toList();
    }

    public ClienteDTO get(final Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> mapToDTO(cliente, new ClienteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ClienteDTO clienteDTO) {
        final Cliente cliente = new Cliente();
        mapToEntity(clienteDTO, cliente);
        return clienteRepository.save(cliente).getId();
    }

    public void update(final Long id, final ClienteDTO clienteDTO) {
        final Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(clienteDTO, cliente);
        clienteRepository.save(cliente);
    }

    public void delete(final Long id) {
        clienteRepository.deleteById(id);
    }

    private ClienteDTO mapToDTO(final Cliente cliente, final ClienteDTO clienteDTO) {
        clienteDTO.setId(cliente.getId());
        clienteDTO.setCpf(cliente.getCpf());
        clienteDTO.setIdade(cliente.getIdade());
        clienteDTO.setNascimento(cliente.getNascimento());
        clienteDTO.setSexo(cliente.getSexo());
        clienteDTO.setLatitude(cliente.getLatitude());
        clienteDTO.setLongitude(cliente.getLongitude());
        clienteDTO.setNumeroCartaoSUS(cliente.getNumeroCartaoSUS());
        clienteDTO.setComorbidades(cliente.getComorbidades());
        clienteDTO.setUsuario(cliente.getUsuario() == null ? null : cliente.getUsuario().getId());
        return clienteDTO;
    }

    private Cliente mapToEntity(final ClienteDTO clienteDTO, final Cliente cliente) {
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setIdade(clienteDTO.getIdade());
        cliente.setNascimento(clienteDTO.getNascimento());
        cliente.setSexo(clienteDTO.getSexo());
        cliente.setLatitude(clienteDTO.getLatitude());
        cliente.setLongitude(clienteDTO.getLongitude());
        cliente.setNumeroCartaoSUS(clienteDTO.getNumeroCartaoSUS());
        cliente.setComorbidades(clienteDTO.getComorbidades());
        final Usuario usuario = clienteDTO.getUsuario() == null ? null : usuarioRepository.findById(clienteDTO.getUsuario())
                .orElseThrow(() -> new NotFoundException("usuario not found"));
        cliente.setUsuario(usuario);
        return cliente;
    }

    public boolean usuarioExists(final Long id) {
        return clienteRepository.existsByUsuarioId(id);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Horario clienteHorario = horarioRepository.findFirstByCliente(cliente);
        if (clienteHorario != null) {
            referencedWarning.setKey("cliente.horario.cliente.referenced");
            referencedWarning.addParam(clienteHorario.getId());
            return referencedWarning;
        }
        return null;
    }

}
