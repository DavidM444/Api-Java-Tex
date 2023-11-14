package ctxt.textil.api.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByUsEmail(String usEmail);
    Usuario findByUsId(Integer usId);
}