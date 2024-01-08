package ctxt.textil.api.controller;

import ctxt.textil.api.Security.Encript.EncriptKey;
import ctxt.textil.api.Usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
Registro de usuarios nuevos del sistema.
Cuenta con generacion de codigo bycript para manejo de contraseñas de usuario.
 */
@RestController
@RequestMapping("/signup")
public class UserController {
    @Value("api.security.secret")
    private String apiSecret;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> registroUser(@RequestBody @Valid DatosNewUser datosNewUser)  {
        System.out.println(datosNewUser);
        String clave = datosNewUser.clave();
        String claveSave = EncriptKey.BycriptKeydd(clave);
        DtoSaveUser dtoSaveUser = new DtoSaveUser(datosNewUser.nombre(), datosNewUser.apellido(), datosNewUser.email(), claveSave);
        System.out.println("datos guarda: "+dtoSaveUser);
        Usuario usuario = userRepository.save(new Usuario(dtoSaveUser));
        return ResponseEntity.ok("Registro exitoso");
    }
}
