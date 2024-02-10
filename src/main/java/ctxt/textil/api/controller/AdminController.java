package ctxt.textil.api.controller;

import ctxt.textil.api.UserAdmin.UserAdmin;
import ctxt.textil.api.UserAdmin.UserAdminRepository;
import ctxt.textil.api.Usuario.DatosAutenticarUsuario;
import ctxt.textil.api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserAdminRepository userRep;


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
}
