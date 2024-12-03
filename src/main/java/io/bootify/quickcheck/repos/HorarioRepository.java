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
            @Param("nomeEstabelecimento")  Optional<String>  nomeEstabelecimento
    );

    @Query("SELECT h FROM Horario h " +
            "JOIN h.funcionario f " +
            "JOIN h.estabelecimento est " +
            "JOIN est.usuario uest " +
            "LEFT JOIN h.cliente cliente " +
            "LEFT JOIN cliente.usuario ucliente " +
            "WHERE f.id = :funcionarioId " +
            "AND (:status IS NULL OR h.status = :status) " +
            "AND (:nomeEstabelecimento IS NULL OR uest.nome LIKE lower(concat('%', :nomeEstabelecimento,'%'))) " +
            "AND (:nomeCliente IS NULL OR ucliente.nome IS NOT NULL AND ucliente.nome LIKE lower(concat('%', :nomeCliente,'%'))) "
    )
    List<Horario> findAllByFuncionarioIdAndOptionalFields(
            @Param("funcionarioId") Long funcionarioId,
            @Param("status")  Optional<String>  status,
            @Param("nomeEstabelecimento")  Optional<String>  nomeEstabelecimento,
            @Param("nomeCliente")  Optional<String>  nomeCliente
    );

    @Query("SELECT h FROM Horario h " +
            "JOIN h.estabelecimento est " +
            "JOIN h.funcionario func " +
            "JOIN func.usuario ufunc " +
            "LEFT JOIN h.cliente cliente " +
            "LEFT JOIN cliente.usuario ucliente " +
            "WHERE est.id = :estabelecimentoId " +
            "AND (:status IS NULL OR h.status = :status) " +
            "AND (:nomeFuncionario IS NULL OR ufunc.nome LIKE lower(concat('%', :nomeFuncionario,'%'))) " +
            "AND (:especialidade IS NULL OR func.especialidade LIKE lower(concat('%', :especialidade,'%'))) " +
            "AND (:nomeCliente IS NULL OR ucliente.nome IS NOT NULL AND ucliente.nome LIKE lower(concat('%', :nomeCliente,'%')))"
    )
    List<Horario> findAllByEstabelecimentoIdAndOptionalFields(
            @Param("estabelecimentoId") Long estabelecimentoId,
            @Param("status")  Optional<String>  status,
            @Param("nomeFuncionario")  Optional<String>  nomeFuncionario,
            @Param("especialidade")  Optional<String>  especialidade,
            @Param("nomeCliente")  Optional<String>  nomeCliente
    );

    List<Horario> findAllByHorarioAtendimentoGreaterThanEqualAndStatusAndFuncionarioEspecialidadeAndFuncionarioUsuarioNomeContainingAndFuncionarioEstabelecimentoUsuarioNomeContaining(
            LocalDateTime horario, String status, String especialidade, Optional<String> nomeFuncionario, Optional<String> nomeEstabelecimento);
}
