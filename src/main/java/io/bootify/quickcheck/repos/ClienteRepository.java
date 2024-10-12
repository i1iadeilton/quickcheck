package io.bootify.quickcheck.repos;

import io.bootify.quickcheck.domain.Cliente;
import io.bootify.quickcheck.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findFirstByUsuario(Usuario usuario);

    boolean existsByUsuarioId(Long id);

}
