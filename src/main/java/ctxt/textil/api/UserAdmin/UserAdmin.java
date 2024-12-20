package ctxt.textil.api.UserAdmin;

import ctxt.textil.api.Usuario.DataUser;
import ctxt.textil.api.Usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Getter
@Entity
@Table(name = "admin")
@EqualsAndHashCode(of = "adId")
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
    public UserAdmin() {}
    public String getAdEmail() {
        return adEmail;
    }

    public Long getAdId() {
        return adId;
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
        return adEmail;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
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
