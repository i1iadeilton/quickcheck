package io.bootify.quickcheck.service;

import io.bootify.quickcheck.domain.*;
import io.bootify.quickcheck.model.FuncionarioDTO;
import io.bootify.quickcheck.repos.EstabelecimentoRepository;
import io.bootify.quickcheck.repos.FuncionarioRepository;
import io.bootify.quickcheck.repos.HorarioRepository;
import io.bootify.quickcheck.repos.UsuarioRepository;
import io.bootify.quickcheck.util.NotFoundException;
import io.bootify.quickcheck.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final HorarioRepository horarioRepository;

    public FuncionarioService(final FuncionarioRepository funcionarioRepository,
            final EstabelecimentoRepository estabelecimentoRepository,
            final UsuarioRepository usuarioRepository, final HorarioRepository horarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.horarioRepository = horarioRepository;
    }

    public List<FuncionarioDTO> findAll() {
        final List<Funcionario> funcionarios = funcionarioRepository.findAll(Sort.by("id"));
        return funcionarios.stream()
                .map(funcionario -> mapToDTO(funcionario, new FuncionarioDTO()))
                .toList();
    }

    public FuncionarioDTO get(final Long id) {
        return funcionarioRepository.findById(id)
                .map(funcionario -> mapToDTO(funcionario, new FuncionarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FuncionarioDTO funcionarioDTO) {
        final Funcionario funcionario = new Funcionario();
        mapToEntity(funcionarioDTO, funcionario);
        return funcionarioRepository.save(funcionario).getId();
    }

    public void update(final Long id, final FuncionarioDTO funcionarioDTO) {
        final Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(funcionarioDTO, funcionario);
        funcionarioRepository.save(funcionario);
    }

    public void delete(final Long id) {
        funcionarioRepository.deleteById(id);
    }

    private FuncionarioDTO mapToDTO(final Funcionario funcionario,
            final FuncionarioDTO funcionarioDTO) {
        funcionarioDTO.setId(funcionario.getId());
        funcionarioDTO.setCpf(funcionario.getCpf());
        funcionarioDTO.setNascimento(funcionario.getNascimento());
        funcionarioDTO.setSexo(funcionario.getSexo());
        funcionarioDTO.setEspecialidade(funcionario.getEspecialidade());
        funcionarioDTO.setCrm(funcionario.getCrm());
        funcionarioDTO.setEstabelecimento(funcionario.getEstabelecimento() == null ? null : funcionario.getEstabelecimento());
        funcionarioDTO.setUsuario(funcionario.getUsuario() == null ? null : funcionario.getUsuario());
        return funcionarioDTO;
    }

    private Funcionario mapToEntity(final FuncionarioDTO funcionarioDTO,
            final Funcionario funcionario) {
        funcionario.setCpf(funcionarioDTO.getCpf());
        funcionario.setNascimento(funcionarioDTO.getNascimento());
        funcionario.setSexo(funcionarioDTO.getSexo());
        funcionario.setEspecialidade(funcionarioDTO.getEspecialidade());
        funcionario.setCrm(funcionarioDTO.getCrm());
        final Estabelecimento estabelecimento = funcionarioDTO.getEstabelecimento() == null ? null : estabelecimentoRepository.findById(funcionarioDTO.getEstabelecimento().getId())
                .orElseThrow(() -> new NotFoundException("estabelecimento not found"));
        funcionario.setEstabelecimento(estabelecimento);
        final Usuario usuario = funcionarioDTO.getUsuario() == null ? null : usuarioRepository.findById(funcionarioDTO.getUsuario().getId())
                .orElseThrow(() -> new NotFoundException("usuario not found"));
        funcionario.setUsuario(usuario);
        return funcionario;
    }

    public boolean usuarioExists(final Long id) {
        return funcionarioRepository.existsByUsuarioId(id);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Horario funcionarioHorario = horarioRepository.findFirstByFuncionario(funcionario);
        if (funcionarioHorario != null) {
            referencedWarning.setKey("funcionario.horario.funcionario.referenced");
            referencedWarning.addParam(funcionarioHorario.getId());
            return referencedWarning;
        }
        return null;
    }

    public FuncionarioDTO getFuncionarioByUsuario(Usuario usuario) {
        Funcionario funcionario = funcionarioRepository.findFirstByUsuario(usuario);
        return mapToDTO(funcionario, new FuncionarioDTO());
    }

    public List<FuncionarioDTO> findAllByEspecialidadeAndEstabelecimentoNomeLikeAndEstabelecimentoTipo(String especialidade,
                                                                                                       String estabelecimentoNome,
                                                                                                       String estabelecimentoTipo) {
        final List<Funcionario> funcionarios = funcionarioRepository
                .findAllByEspecialidadeAndEstabelecimentoUsuarioNomeContainingAndEstabelecimentoTipo(especialidade, estabelecimentoNome, estabelecimentoTipo);
        return funcionarios.stream()
                .map(funcionario -> mapToDTO(funcionario, new FuncionarioDTO()))
                .toList();
    }
}
