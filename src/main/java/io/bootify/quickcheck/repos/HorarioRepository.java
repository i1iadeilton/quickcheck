package io.bootify.quickcheck.repos;

import io.bootify.quickcheck.domain.Cliente;
import io.bootify.quickcheck.domain.Estabelecimento;
import io.bootify.quickcheck.domain.Funcionario;
import io.bootify.quickcheck.domain.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface HorarioRepository extends JpaRepository<Horario, Long> {

    Horario findFirstByFuncionario(Funcionario funcionario);

    Horario findFirstByEstabelecimento(Estabelecimento estabelecimento);

    Horario findFirstByCliente(Cliente cliente);

    List<Horario> findAllByEstabelecimentoIdAndStatus(Long estabelecimentoId, String status);

    List<Horario> findAllByEstabelecimentoIdAndStatusAndFuncionarioEspecialidade(Long estabelecimentoId, String status, Optional<String> especialidade);
}
