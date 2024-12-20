package ctxt.textil.api.infra.security;

import ctxt.textil.api.Usuario.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/** Filtro que realiza la autenticacion de un usurio a traves de un token JWT(Json Web Token).
 * @params Request Http
 * @return Autenticicaion de usuario
 * @author Rodrigo David Muñoz
 */

@Component
public class SecurityFilter extends OncePerRequestFilter{

    @Autowired
    AutenticacionService autenticacionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRespository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenRequest = request.getHeader("authorization");
        String requestURI = request.getRequestURI();
        System.out.println("token request: "+ tokenRequest + " uri "+requestURI);
        if (tokenRequest !=null){
            var token = tokenRequest.replace("Bearer ", "");
            String iss = tokenService.getIssClaim(token, requestURI);
            Boolean uri = iss.equals("qualityAdmin");
            if(iss.equals("qualityUser")){
                Integer Id = tokenService.getIdClaim(token);
                request.setAttribute("Idcl",Id);
            }
            var emailUser = tokenService.getSubject(token,uri);
            if (emailUser!= null){
                var usuario = autenticacionService.findUserWithIss(uri, emailUser);
                var autenticacion = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(autenticacion);
            }
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        filterChain.doFilter(request,response);
    }
}
