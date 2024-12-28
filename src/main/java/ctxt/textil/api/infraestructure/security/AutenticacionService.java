package ctxt.textil.api.infraestructure.security;

import ctxt.textil.api.domain.user.useradmin.UserAdminRepository;
import ctxt.textil.api.domain.user.usuario.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAdminRepository userRep;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("autenticando user: "+username);
        UserDetails user = userRepository.findByUsEmail(username);
        try {
            if (user==null){
                user = userRep.findByAdEmail(username);
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return user;
    }

    public UserDetails findUserWithIss(Boolean isUserAdmin, String emailUser) {
        if(isUserAdmin) return userRep.findByAdEmail(emailUser);
        return userRepository.findByUsEmail(emailUser);
    }
}
