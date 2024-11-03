package io.bootify.quickcheck.repos;

import io.bootify.quickcheck.domain.Estabelecimento;
import io.bootify.quickcheck.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {

    Estabelecimento findFirstByUsuario(Usuario usuario);

    boolean existsByUsuarioId(Long id);

    List<Estabelecimento> findAllByTipo(String tipo);
}
