package ctxt.textil.api.controller;

import ctxt.textil.api.domain.user.useradmin.UserAdmin;
import ctxt.textil.api.domain.user.useradmin.UserAdminRepository;
import ctxt.textil.api.application.dto.base.DataUser;
import ctxt.textil.api.application.dto.request.DatosNewUser;
import ctxt.textil.api.application.dto.base.DtoRol;
import ctxt.textil.api.domain.user.usuario.Roles.Rol;
import ctxt.textil.api.domain.user.usuario.Roles.RolRepository;
import ctxt.textil.api.domain.user.usuario.Roles.RolService;
import ctxt.textil.api.domain.user.usuario.UserRepository;
import ctxt.textil.api.domain.user.usuario.Usuario;
import ctxt.textil.api.infraestructure.security.EncriptKey;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    private final RolService rolService;
    public UserController(RolService rolService){
        this.rolService = rolService;
    }

    @PostMapping
    public ResponseEntity<DataUser> registroUser(@RequestBody @Valid DatosNewUser datosNewUser)  {
        System.out.println(datosNewUser);
        String clave = datosNewUser.clave();
        String claveSave = EncriptKey.BycriptKeydd(clave);
        DataUser dataUser = new DataUser(datosNewUser.nombre(), datosNewUser.apellido(), datosNewUser.email(), claveSave);
        Usuario usuario = userRepository.save(new Usuario(dataUser));
        rolService.saveRolAuthority(new DtoRol(usuario.getUsId(),usuario.getAuthorities().toString()));
        return ResponseEntity.ok(dataUser);
    }
}


