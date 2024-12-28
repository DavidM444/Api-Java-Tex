package ctxt.textil.api.domain.user.useradmin;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserAdminRepository extends JpaRepository<UserAdmin,Long> {
    UserDetails findByAdEmail(String adEmail);
    UserAdmin findByAdId(Integer id);
}
