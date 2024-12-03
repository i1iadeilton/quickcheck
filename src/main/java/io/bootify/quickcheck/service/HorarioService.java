package io.bootify.quickcheck.service;

import io.bootify.quickcheck.domain.Cliente;
import io.bootify.quickcheck.domain.Estabelecimento;
import io.bootify.quickcheck.domain.Funcionario;
import io.bootify.quickcheck.domain.Horario;
import io.bootify.quickcheck.model.HorarioDTO;
import io.bootify.quickcheck.repos.ClienteRepository;
import io.bootify.quickcheck.repos.EstabelecimentoRepository;
import io.bootify.quickcheck.repos.FuncionarioRepository;
import io.bootify.quickcheck.repos.HorarioRepository;
import io.bootify.quickcheck.util.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


@Service
public class HorarioService {

    private final HorarioRepository horarioRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final ClienteRepository clienteRepository;

    public HorarioService(final HorarioRepository horarioRepository,
            final FuncionarioRepository funcionarioRepository,
            final EstabelecimentoRepository estabelecimentoRepository,
            final ClienteRepository clienteRepository) {
        this.horarioRepository = horarioRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<HorarioDTO> findAll() {
        final List<Horario> horarios = horarioRepository.findAll(Sort.by("id"));
        return horarios.stream()
                .map(horario -> mapToDTO(horario, new HorarioDTO()))
                .toList();
    }

    public HorarioDTO get(final Long id) {
        return horarioRepository.findById(id)
                .map(horario -> mapToDTO(horario, new HorarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final HorarioDTO horarioDTO) {
        final Horario horario = new Horario();
        mapToEntity(horarioDTO, horario);
        return horarioRepository.save(horario).getId();
    }

    public void update(final Long id, final HorarioDTO horarioDTO) {
        final Horario horario = horarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(horarioDTO, horario);
        horarioRepository.save(horario);
    }

    public void delete(final Long id) {
        horarioRepository.deleteById(id);
    }

    private HorarioDTO mapToDTO(final Horario horario, final HorarioDTO horarioDTO) {
        horarioDTO.setId(horario.getId());
        horarioDTO.setHorarioAtendimento(horario.getHorarioAtendimento());
        horarioDTO.setHorarioAgendamento(horario.getHorarioAgendamento());
        horarioDTO.setStatus(horario.getStatus());
        horarioDTO.setDescricao(horario.getDescricao());
        horarioDTO.setProntuario(horario.getProntuario());
        horarioDTO.setFuncionario(horario.getFuncionario() == null ? null : horario.getFuncionario());
        horarioDTO.setEstabelecimento(horario.getEstabelecimento() == null ? null : horario.getEstabelecimento());
        horarioDTO.setCliente(horario.getCliente() == null ? null : horario.getCliente());
        return horarioDTO;
    }

    private Horario mapToEntity(final HorarioDTO horarioDTO, final Horario horario) {
        horario.setHorarioAtendimento(horarioDTO.getHorarioAtendimento());
        horario.setHorarioAgendamento(horarioDTO.getHorarioAgendamento());
        horario.setStatus(horarioDTO.getStatus());
        horario.setDescricao(horarioDTO.getDescricao());
        horario.setProntuario(horarioDTO.getProntuario());
        final Funcionario funcionario = horarioDTO.getFuncionario() == null ? null : funcionarioRepository.findById(horarioDTO.getFuncionario().getId())
                .orElseThrow(() -> new NotFoundException("Funcionario not found"));
        horario.setFuncionario(funcionario);
        final Estabelecimento estabelecimento = horarioDTO.getEstabelecimento() == null ? null : estabelecimentoRepository.findById(horarioDTO.getEstabelecimento().getId())
                .orElseThrow(() -> new NotFoundException("Estabelecimento not found"));
        horario.setEstabelecimento(estabelecimento);
        final Cliente cliente = horarioDTO.getCliente() == null ? null : clienteRepository.findById(horarioDTO.getCliente().getId())
                .orElseThrow(() -> new NotFoundException("Cliente not found"));
        horario.setCliente(cliente);
        return horario;
    }

    public List<HorarioDTO> findAllByClienteIdAndStatus(
            Long clienteId,
            Optional<String> status,
            Optional<String> especialidade,
            Optional<String> nomeFuncionario,
            Optional<String> nomeEstabelecimento
    ) {
        final List<Horario> horarios = horarioRepository.findAllByClienteIdAndOptionalFields(
                clienteId,
                status,
                especialidade,
                nomeFuncionario,
                nomeEstabelecimento
        );
        return horarios.stream()
                .map(horario -> mapToDTO(horario, new HorarioDTO()))
                .toList();
    }

    public List<HorarioDTO> findAllByFuncionarioIdAndStatus(
            Long funcionarioId,
            Optional<String>  status,
            Optional<String>  nomeEstabelecimento,
            Optional<String>  nomeCliente
    ) {
        final List<Horario> horarios = horarioRepository.findAllByFuncionarioIdAndOptionalFields(
                funcionarioId,
                status,
                nomeEstabelecimento,
                nomeCliente
        );
        return horarios.stream()
                .map(horario -> mapToDTO(horario, new HorarioDTO()))
                .toList();
    }

    public List<HorarioDTO> findAllByEstabelecimentoIdAndStatus(
            Long estabelecimentoId,
            Optional<String>  status,
            Optional<String>  nomeFuncionario,
            Optional<String>  especialidade,
            Optional<String>  nomeCliente
    ) {
        final List<Horario> horarios = horarioRepository.findAllByEstabelecimentoIdAndOptionalFields(
                estabelecimentoId,
                status,
                nomeFuncionario,
                especialidade,
                nomeCliente
        );
        return horarios.stream()
                .map(horario -> mapToDTO(horario, new HorarioDTO()))
                .toList();
    }

    public List<HorarioDTO> findAllByHorarioAndEstabelecimentoAndFuncionario(LocalDateTime horarioAtendimento, String status, String especialidade, Optional<String> nomeFuncionario, Optional<String> nomeEstabelecimento) {
        final List<Horario> horarios = horarioRepository.findAllByHorarioAtendimentoGreaterThanEqualAndStatusAndFuncionarioEspecialidadeAndFuncionarioUsuarioNomeContainingAndFuncionarioEstabelecimentoUsuarioNomeContaining(
                horarioAtendimento,
                status,
                especialidade,
                nomeFuncionario,
                nomeEstabelecimento
        );
        return horarios.stream()
                .map(horario -> mapToDTO(horario, new HorarioDTO()))
                .toList();
    }
}
