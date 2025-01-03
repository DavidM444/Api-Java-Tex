package ctxt.textil.api.controller;

import ctxt.textil.api.application.dto.base.DataUser;
import ctxt.textil.api.application.dto.request.DatosAutenticarUsuario;
import ctxt.textil.api.domain.user.usuario.UserRepository;
import ctxt.textil.api.domain.user.usuario.Usuario;
import ctxt.textil.api.infraestructure.security.TokenService;
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
-- Controlador api, para registro de usuarios a traves de autenticacion JWT.
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
