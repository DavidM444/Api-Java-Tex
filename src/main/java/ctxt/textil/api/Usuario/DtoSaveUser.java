package ctxt.textil.api.Usuario;

import jakarta.validation.constraints.NotBlank;


public record DtoSaveUser(String nombre,String apellido, String email, String claveHash) {
}
