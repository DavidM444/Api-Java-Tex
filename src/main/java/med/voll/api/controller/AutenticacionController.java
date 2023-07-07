package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.infra.security.DatosJwtoken;
import med.voll.api.infra.security.TokenService;
import med.voll.api.usuario.DatosAutenticarUsuario;
import med.voll.api.usuario.Usuario;
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
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticarUsuario datosAutenticarUsuario){
        System.out.println("llego a autenticar: "+ datosAutenticarUsuario);
        Authentication token = new UsernamePasswordAuthenticationToken(datosAutenticarUsuario.email(),datosAutenticarUsuario.clave());
        var usuarioAutenticado = authenticationManager.authenticate(token);
        var jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJwtoken(jwtToken));

    }

}
