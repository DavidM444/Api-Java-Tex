package med.voll.api.usuario;

import org.springframework.security.core.userdetails.UserDetails;

public record DatosAutenticarUsuario(String email, String clave) {
}
