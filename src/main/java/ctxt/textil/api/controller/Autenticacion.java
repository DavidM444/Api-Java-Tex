package ctxt.textil.api.controller;

import ctxt.textil.api.Security.Encript.EncriptKey;

import ctxt.textil.api.UserAdmin.UserAdmin;
import ctxt.textil.api.UserAdmin.UserAdminRepository;
import ctxt.textil.api.Usuario.DataUser;
import ctxt.textil.api.Usuario.DatosAutenticarUsuario;
import ctxt.textil.api.Usuario.UserRepository;
import ctxt.textil.api.Usuario.Usuario;
import ctxt.textil.api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.apache.catalina.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
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
@RequestMapping("/login")
public class Autenticacion {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<DataUser> autenticarUsuario(@RequestBody @Valid DatosAutenticarUsuario datosAutenticarUsuario){
        System.out.println("autenticacion: "+datosAutenticarUsuario);
        Authentication token = new UsernamePasswordAuthenticationToken(datosAutenticarUsuario.email(),datosAutenticarUsuario.clave());
        var usuarioAutenticado = authenticationManager.authenticate(token);
        System.out.println("22222 "+usuarioAutenticado.toString());
        var jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        Integer userId = tokenService.getIdClaim(jwtToken);
        System.out.println("token: "+jwtToken + "id: "+userId);
        Usuario user = userRepository.findByUsId(userId);

        DataUser data = new DataUser(user.getUsNombre(), user.getUsApellido(), user.getUsEmail(), jwtToken);
        return ResponseEntity.ok(data);
    }



}
