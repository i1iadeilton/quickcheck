package io.bootify.quickcheck.repos;

import io.bootify.quickcheck.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findFirstByEmailAndSenhaAndRole(String email, String senha, String role);
}
