package io.bootify.quickcheck.repos;

import io.bootify.quickcheck.domain.Estabelecimento;
import io.bootify.quickcheck.domain.Funcionario;
import io.bootify.quickcheck.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Funcionario findFirstByEstabelecimento(Estabelecimento estabelecimento);

    Funcionario findFirstByUsuario(Usuario usuario);

    boolean existsByUsuarioId(Long id);

}
