package ctxt.textil.api.controller;

import ctxt.textil.api.Security.Encript.EncriptKey;
import ctxt.textil.api.UserAdmin.UserAdmin;
import ctxt.textil.api.UserAdmin.UserAdminRepository;
import ctxt.textil.api.Usuario.*;
import ctxt.textil.api.Usuario.Roles.DtoRol;
import ctxt.textil.api.Usuario.Roles.Rol;
import ctxt.textil.api.Usuario.Roles.RolRepository;
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
    @Autowired
    private RolRepository repositoryRol;

    private final static String RoleUser = "ROLE_USER";
    private final static String RoleAdmin = "ROLE_ADMIN";
    @PostMapping
    public ResponseEntity<DataUser> registroUser(@RequestBody @Valid DatosNewUser datosNewUser)  {
        System.out.println(datosNewUser);
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
        System.out.println("roles "+ rol.idUser()+" "+rol.rolName());
        try {
            Boolean validacion = verifyAutorityAdmin(rol.rolName());
        } catch (Exception e) {
            throw new RuntimeException("Rol invalido: "+e.getMessage());
        }
        Rol rolData = new Rol(rol);
        repositoryRol.save(rolData);
    }

    private Boolean verifyAutorityAdmin(String rolAutority) {
        System.out.println("aut "+rolAutority);
        return rolAutority.equals(RoleAdmin)||rolAutority.equals(RoleUser);
    }

    public static void main(String[] args) {
        System.out.println(hi("hi no soy hi"));

    }
    public  static int hi( String str){

        if(str.length()<2){
            return 0;
        }
        int count = 0;
        char[] val = str.toCharArray();


        for(int i = 0; i < val.length; i++){
            if(String.valueOf(val[i]).equals("h") && String.valueOf(val[i + 1]).equals("i")){
                count+= 1;
            }


        }
        return count;


    }
}


