package ctxt.textil.api.controller;

import ctxt.textil.api.domain.user.useradmin.UserAdmin;
import ctxt.textil.api.domain.user.useradmin.UserAdminRepository;
import ctxt.textil.api.application.dto.request.DatosAutenticarUsuario;
import ctxt.textil.api.domain.user.usuario.UserRepository;
import ctxt.textil.api.infraestructure.security.TokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;


@Slf4j
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

        log.debug("datos usuario admin: {}",datosAdmin);

        Authentication auth = new UsernamePasswordAuthenticationToken(datosAdmin.email(),datosAdmin.clave());
        var authenticationResult = authenticationManager.authenticate(auth);

        log.debug("user {}   ->auth: {}",authenticationResult.toString(),auth);
        var jwtToken = tokenService.generarAdminToken((UserAdmin) authenticationResult.getPrincipal());
        log.debug("token {}", jwtToken);

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
