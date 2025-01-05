package ctxt.textil.api.controller;

import ctxt.textil.api.domain.user.useradmin.UserAdmin;
import ctxt.textil.api.domain.user.useradmin.UserAdminRepository;
import ctxt.textil.api.application.dto.base.DataUser;
import ctxt.textil.api.application.dto.request.DatosNewUser;
import ctxt.textil.api.application.dto.base.DtoRol;
import ctxt.textil.api.domain.user.usuario.Roles.Rol;
import ctxt.textil.api.domain.user.usuario.Roles.RolRepository;
import ctxt.textil.api.domain.user.usuario.UserRepository;
import ctxt.textil.api.domain.user.usuario.Usuario;
import ctxt.textil.api.infraestructure.security.EncriptKey;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/signup")
public class UserController {
    @Value("api.security.secret")
    private String apiSecret;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAdminRepository userAdminRepository;
    @Autowired
    private RolRepository repositoryRol;

    private final static String RoleUser = "ROLE_USER";
    private final static String RoleAdmin = "ROLE_ADMIN";
    @PostMapping
    public ResponseEntity<DataUser> registroUser(@RequestBody @Valid DatosNewUser datosNewUser)  {
        log.debug("Datos nuevo usuario {}",datosNewUser);
        String clave = datosNewUser.clave();
        String claveSave = EncriptKey.BycriptKeydd(clave);
        DataUser dataUser = new DataUser(datosNewUser.nombre(), datosNewUser.apellido(), datosNewUser.email(), claveSave);
        Usuario usuario = userRepository.save(new Usuario(dataUser));
        saveAuthorities(new DtoRol(usuario.getUsId(),usuario.getAuthorities().toString()));
        return ResponseEntity.ok(dataUser);
    }

    @Transactional
    @PostMapping("/admin")
    public ResponseEntity<String> registroUserAdmin(@RequestBody @Valid DatosNewUser dtUser){
        String clave = EncriptKey.BycriptKeydd(dtUser.clave());
        DataUser dataUser = new DataUser(dtUser.nombre(),dtUser.apellido(), dtUser.email(),clave);
        UserAdmin usuario = userAdminRepository.save(new UserAdmin(dataUser));
        saveAuthorities(new DtoRol( (usuario.getAdId()),usuario.getAuthorities().toString()));
        return ResponseEntity.ok("Usuario administrador creado con èxito");
    }

    private void saveAuthorities(DtoRol rol) {
        log.debug("Rol Info: ID {}, Name: {} ", rol.idUser(),rol.rolName());
        try {
            Boolean validacion = verifyAutorityAdmin(rol.rolName());
        } catch (Exception e) {
            throw new RuntimeException("Rol invalido: "+e.getMessage());
        }
        Rol rolData = new Rol(rol);
        Rol rol2 = repositoryRol.save(rolData);
    }

    private Boolean verifyAutorityAdmin(String rolAutority) {
        log.info("verifying rol {}",rolAutority);
        return rolAutority.equals(RoleAdmin)||rolAutority.equals(RoleUser);
    }
}


