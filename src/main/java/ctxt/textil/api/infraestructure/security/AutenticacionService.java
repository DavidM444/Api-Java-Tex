package ctxt.textil.api.infraestructure.security;

import ctxt.textil.api.domain.user.useradmin.UserAdminRepository;
import ctxt.textil.api.domain.user.usuario.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AutenticacionService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAdminRepository userRep;

    @Override
    public UserDetails loadUserByUsername(String username){
        log.info("Autenticando Usario: "+username);
        UserDetails user = userRepository.findByUsEmail(username);
        try {
            if (user==null){
                user = userRep.findByAdEmail(username);
            }
        }catch (UsernameNotFoundException ex){
            log.error("Usuarion no encontrado: {}", username);
            throw new RuntimeException(ex);
        }
        return user;
    }

    public UserDetails findUserWithIss(Boolean isUserAdmin, String emailUser) {
        if(isUserAdmin) return userRep.findByAdEmail(emailUser);
        return userRepository.findByUsEmail(emailUser);
    }
}
