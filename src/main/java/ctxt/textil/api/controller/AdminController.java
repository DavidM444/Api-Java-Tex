package ctxt.textil.api.controller;

import ctxt.textil.api.UserAdmin.UserAdmin;
import ctxt.textil.api.UserAdmin.UserAdminRepository;
import ctxt.textil.api.Usuario.DatosAutenticarUsuario;
import ctxt.textil.api.Usuario.UserRepository;
import ctxt.textil.api.Usuario.Usuario;
import ctxt.textil.api.infra.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserAdminRepository userRep;

    @Autowired
    private UserRepository userRepo;

    @Transactional
    @PostMapping
    public ResponseEntity<String> autenticarAdmin(@RequestBody @Valid DatosAutenticarUsuario datosAdmin){
        System.out.println("datos usuario admin: "+datosAdmin);
        Authentication auth = new UsernamePasswordAuthenticationToken(datosAdmin.email(),datosAdmin.clave());
        System.out.println("auth "+auth);
        var authenticationResult = authenticationManager.authenticate(auth);

        System.out.println("user "+authenticationResult.toString()+ " ->auth: "+auth);
        var jwtToken = tokenService.generarAdminToken((UserAdmin) authenticationResult.getPrincipal());
        System.out.println("tok "+jwtToken);

        /* For find and return data user created:
        Integer id = tokenService.getIdClaim(jwtToken);
        UserAdmin user = userRep.findByAdId(id);
        */
        return ResponseEntity.ok("User Admin Logeado");
    }
    @GetMapping
    public LinkedList<UserList> getUsers(){
        List<UserList> lista2 = userRepo.findAll().stream().map(
                user-> new UserList(user.getUsId(),user.getUsNombre(),user.getUsApellido(), user.getUsEmail())).toList();
        return new LinkedList<>(lista2);
    }
    protected record UserList(Long id,String nombre, String apellido, String email){}
}
