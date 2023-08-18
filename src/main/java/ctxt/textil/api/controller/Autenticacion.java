package ctxt.textil.api.controller;

import ctxt.textil.api.Usuario.DatosAutenticarUsuario;
import ctxt.textil.api.Usuario.Usuario;
import ctxt.textil.api.infra.security.DatosJwtToken;
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

/*
@Autor: David Muñoz
@Spring-API
-- Controlador api, para logeo de usuarios a traves de autenticacion JWT.
-- Respuesta Http con el token de usuario generado en caso de autenticacion exitosa.
 */
@RestController
@RequestMapping("login")
public class Autenticacion {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticarUsuario datosAutenticarUsuario){
        Authentication token = new UsernamePasswordAuthenticationToken(datosAutenticarUsuario.email(),datosAutenticarUsuario.clave());
        var usuarioAutenticado = authenticationManager.authenticate(token);
        var jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJwtToken(jwtToken));


    }
}
