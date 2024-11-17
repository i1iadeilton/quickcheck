package io.bootify.quickcheck.repos;

import io.bootify.quickcheck.domain.Cliente;
import io.bootify.quickcheck.domain.Estabelecimento;
import io.bootify.quickcheck.domain.Funcionario;
import io.bootify.quickcheck.domain.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface HorarioRepository extends JpaRepository<Horario, Long> {

    Horario findFirstByFuncionario(Funcionario funcionario);

    Horario findFirstByEstabelecimento(Estabelecimento estabelecimento);

    Horario findFirstByCliente(Cliente cliente);

    @Query("SELECT h FROM Horario h " +
            "JOIN h.funcionario f " +
            "JOIN f.usuario ufunc " +
            "JOIN h.cliente c " +
            "JOIN h.estabelecimento est " +
            "JOIN est.usuario uest " +
            "WHERE c.id = :clienteId " +
            "AND (:status IS NULL OR h.status = :status) " +
            "AND (:especialidade IS NULL OR f.especialidade LIKE lower(concat('%', :especialidade,'%'))) " +
            "AND (:nomeFuncionario IS NULL OR ufunc.nome LIKE lower(concat('%', :nomeFuncionario,'%'))) " +
            "AND (:nomeEstabelecimento IS NULL OR uest.nome LIKE lower(concat('%', :nomeEstabelecimento,'%')))")
    List<Horario> findAllByClienteIdAndOptionalFields(
            @Param("clienteId") Long clienteId,
            @Param("status")  Optional<String>  status,
            @Param("especialidade")  Optional<String>  especialidade,
            @Param("nomeFuncionario")  Optional<String>  nomeFuncionario,
            @Param("nomeEstabelecimento")  Optional<String>  nomeEstabelecimento);

    List<Horario> findAllByHorarioAtendimentoGreaterThanEqualAndStatusAndFuncionarioEspecialidadeAndFuncionarioUsuarioNomeContainingAndFuncionarioEstabelecimentoUsuarioNomeContaining(
            LocalDateTime horario, String status, String especialidade, Optional<String> nomeFuncionario, Optional<String> nomeEstabelecimento);
}
