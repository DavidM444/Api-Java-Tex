package ctxt.textil.api.infra.security;

import ctxt.textil.api.Usuario.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRespository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenRequest = request.getHeader("authorization");
        System.out.println("token reqeuss: "+ tokenRequest);
        if (tokenRequest !=null){
            var token = tokenRequest.replace("Bearer ", "");
            var nombreUsuario = tokenService.getSubject(token);
            Integer Id = tokenService.getIdClaim(token);
            request.setAttribute("Idcl",Id);
            if (nombreUsuario!= null){
                var usuario = userRespository.findByUsEmail(nombreUsuario);
                var autenticacion = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(autenticacion);
            }
        }else {
            String requestURI = request.getRequestURI();
            System.out.println(requestURI);

            int res =response.getStatus();
            System.out.println(res);
            //forbbiden, solicitud correcta con restriccion.
            //response.setStatus(HttpStatus.FORBIDDEN.value());
            //response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            //response.getWriter().write("{\"message\": \"Usuario no logueado\" ->\"Credenciales incorrectas\"}");
            //return;
        }filterChain.doFilter(request,response);
    }
}
