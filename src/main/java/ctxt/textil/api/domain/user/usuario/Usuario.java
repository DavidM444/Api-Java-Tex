package ctxt.textil.api.domain.user.usuario;


import ctxt.textil.api.application.dto.base.DataUser;
import ctxt.textil.api.application.dto.request.DatosNewUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "usuarios")
@EqualsAndHashCode(of = "usId")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usId;
    private String usNombre;
    private String usApellido;
    @NotBlank
    private String usEmail;
    @NotBlank
    private String usClave;
    public Usuario(DatosNewUser datosNewUser) {
    }

    public Usuario(DataUser dataUser) {
        this.usNombre = dataUser.nombre();
        this.usApellido = dataUser.apellido();
        this.usEmail = dataUser.email();
        this.usClave = dataUser.clave();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return usClave;
    }

    @Override
    public String getUsername() {
        return usEmail;
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
