package ctxt.textil.api.UserAdmin;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserAdminRepository extends JpaRepository<UserAdmin,Long> {

    //para implementar el AuthenticationManager, debo implemenetar en el service de autenticar(AutenticationService) que en el repository usado
    // por findUserByUsername, metodo de UserDetailssService, retorne un UserDetails y no un La entidad. La entidad se usa para JPA.
    UserDetails findByAdEmail(String adEmail);
    UserAdmin findByAdId(Integer id);
}
