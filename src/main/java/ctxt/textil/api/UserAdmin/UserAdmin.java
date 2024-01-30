package ctxt.textil.api.UserAdmin;

import ctxt.textil.api.Usuario.DataUser;
import ctxt.textil.api.Usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@Table(name = "admin")
public class UserAdmin implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adId;
    private String adNombre;
    private String adApellido;
    @NotBlank
    private String adEmail;
    @NotBlank
    private String adClave;
    public UserAdmin(DataUser dataUser) {
        this.adNombre = dataUser.nombre();
        this.adApellido = dataUser.apellido();
        this.adEmail = dataUser.email();
        this.adClave = dataUser.clave();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return adClave;
    }

    @Override
    public String getUsername() {
        return adNombre;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
