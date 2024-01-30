package ctxt.textil.api.controller;

import ctxt.textil.api.Security.Encript.EncriptKey;
import ctxt.textil.api.UserAdmin.UserAdmin;
import ctxt.textil.api.UserAdmin.UserAdminRepository;
import ctxt.textil.api.Usuario.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Autowired
    private UserAdminRepository userAdminRepository;

    private final static String RoleUser = "ROLE_USER";
    private final static String RoleAdmin = "ROLE_USER";

    @PostMapping
    public ResponseEntity<String> registroUser(@RequestBody @Valid DatosNewUser datosNewUser)  {
        System.out.println(datosNewUser);
        String clave = datosNewUser.clave();
        String claveSave = EncriptKey.BycriptKeydd(clave);
        DataUser dataUser = new DataUser(datosNewUser.nombre(), datosNewUser.apellido(), datosNewUser.email(), claveSave);
        System.out.println("datos guarda: "+dataUser);
        Usuario usuario = userRepository.save(new Usuario(dataUser));

        return ResponseEntity.ok("Registro exitoso");
    }

    @Transactional
    @PostMapping("/admin")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> registroUserAdmin(@RequestBody @Valid DatosNewUser dtUser){
        String clave = EncriptKey.BycriptKeydd(dtUser.clave());
        DataUser dataUser = new DataUser(dtUser.nombre(),dtUser.apellido(), dtUser.email(),clave);

        UserAdmin usuario = userAdminRepository.save(new UserAdmin(dataUser));
        System.out.println("dta: "+dtUser+ " role: "+usuario.getAuthorities());
        return ResponseEntity.ok("Usuario administrador creado");
    }
}
