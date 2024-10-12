package io.bootify.quickcheck.repos;

import io.bootify.quickcheck.domain.Cliente;
import io.bootify.quickcheck.domain.Estabelecimento;
import io.bootify.quickcheck.domain.Funcionario;
import io.bootify.quickcheck.domain.Horario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HorarioRepository extends JpaRepository<Horario, Long> {

    Horario findFirstByFuncionario(Funcionario funcionario);

    Horario findFirstByEstabelecimento(Estabelecimento estabelecimento);

    Horario findFirstByCliente(Cliente cliente);

}
