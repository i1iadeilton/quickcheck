package io.bootify.quickcheck.service;

import io.bootify.quickcheck.domain.Estabelecimento;
import io.bootify.quickcheck.domain.Funcionario;
import io.bootify.quickcheck.domain.Horario;
import io.bootify.quickcheck.domain.Usuario;
import io.bootify.quickcheck.model.EstabelecimentoDTO;
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
public class EstabelecimentoService {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final HorarioRepository horarioRepository;
    private final FuncionarioRepository funcionarioRepository;

    public EstabelecimentoService(final EstabelecimentoRepository estabelecimentoRepository,
            final UsuarioRepository usuarioRepository, final HorarioRepository horarioRepository,
            final FuncionarioRepository funcionarioRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.horarioRepository = horarioRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<EstabelecimentoDTO> findAll() {
        final List<Estabelecimento> estabelecimentoes = estabelecimentoRepository.findAll(Sort.by("id"));
        return estabelecimentoes.stream()
                .map(estabelecimento -> mapToDTO(estabelecimento, new EstabelecimentoDTO()))
                .toList();
    }

    public EstabelecimentoDTO get(final Long id) {
        return estabelecimentoRepository.findById(id)
                .map(estabelecimento -> mapToDTO(estabelecimento, new EstabelecimentoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EstabelecimentoDTO estabelecimentoDTO) {
        final Estabelecimento estabelecimento = new Estabelecimento();
        mapToEntity(estabelecimentoDTO, estabelecimento);
        return estabelecimentoRepository.save(estabelecimento).getId();
    }

    public void update(final Long id, final EstabelecimentoDTO estabelecimentoDTO) {
        final Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(estabelecimentoDTO, estabelecimento);
        estabelecimentoRepository.save(estabelecimento);
    }

    public void delete(final Long id) {
        estabelecimentoRepository.deleteById(id);
    }

    private EstabelecimentoDTO mapToDTO(final Estabelecimento estabelecimento,
            final EstabelecimentoDTO estabelecimentoDTO) {
        estabelecimentoDTO.setId(estabelecimento.getId());
        estabelecimentoDTO.setCnpj(estabelecimento.getCnpj());
        estabelecimentoDTO.setLatitude(estabelecimento.getLatitude());
        estabelecimentoDTO.setLongitude(estabelecimento.getLongitude());
        estabelecimentoDTO.setHorarioFuncionamento(estabelecimento.getHorarioFuncionamento());
        estabelecimentoDTO.setDescricao(estabelecimento.getDescricao());
        estabelecimentoDTO.setAssinante(estabelecimento.getAssinante());
        estabelecimentoDTO.setTipo(estabelecimento.getTipo());
        estabelecimentoDTO.setUsuario(estabelecimento.getUsuario() == null ? null : estabelecimento.getUsuario());
        return estabelecimentoDTO;
    }

    private Estabelecimento mapToEntity(final EstabelecimentoDTO estabelecimentoDTO,
            final Estabelecimento estabelecimento) {
        estabelecimento.setCnpj(estabelecimentoDTO.getCnpj());
        estabelecimento.setLatitude(estabelecimentoDTO.getLatitude());
        estabelecimento.setLongitude(estabelecimentoDTO.getLongitude());
        estabelecimento.setHorarioFuncionamento(estabelecimentoDTO.getHorarioFuncionamento());
        estabelecimento.setDescricao(estabelecimentoDTO.getDescricao());
        estabelecimento.setAssinante(estabelecimentoDTO.getAssinante());
        estabelecimento.setTipo(estabelecimentoDTO.getTipo());
        final Usuario usuario = estabelecimentoDTO.getUsuario() == null ? null : usuarioRepository.findById(estabelecimentoDTO.getUsuario().getId())
                .orElseThrow(() -> new NotFoundException("usuario not found"));
        estabelecimento.setUsuario(usuario);
        return estabelecimento;
    }

    public boolean usuarioExists(final Long id) {
        return estabelecimentoRepository.existsByUsuarioId(id);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Horario estabelecimentoHorario = horarioRepository.findFirstByEstabelecimento(estabelecimento);
        if (estabelecimentoHorario != null) {
            referencedWarning.setKey("estabelecimento.horario.estabelecimento.referenced");
            referencedWarning.addParam(estabelecimentoHorario.getId());
            return referencedWarning;
        }
        final Funcionario estabelecimentoFuncionario = funcionarioRepository.findFirstByEstabelecimento(estabelecimento);
        if (estabelecimentoFuncionario != null) {
            referencedWarning.setKey("estabelecimento.funcionario.estabelecimento.referenced");
            referencedWarning.addParam(estabelecimentoFuncionario.getId());
            return referencedWarning;
        }
        return null;
    }

    public EstabelecimentoDTO getEstabelecimentoByUsuario(Usuario usuario) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findFirstByUsuario(usuario);
        return mapToDTO(estabelecimento, new EstabelecimentoDTO());
    }

    public List<EstabelecimentoDTO> findEstabelecimentoByTipo(final String tipo) {
        final List<Estabelecimento> estabelecimentoes = estabelecimentoRepository.findAllByTipo(tipo);
        return estabelecimentoes.stream()
                .map(estabelecimento -> mapToDTO(estabelecimento, new EstabelecimentoDTO()))
                .toList();
    }
}
