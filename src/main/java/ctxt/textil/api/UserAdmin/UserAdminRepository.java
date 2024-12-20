package ctxt.textil.api.UserAdmin;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserAdminRepository extends JpaRepository<UserAdmin,Long> {
    UserDetails findByAdEmail(String adEmail);
    UserAdmin findByAdId(Integer id);
}
