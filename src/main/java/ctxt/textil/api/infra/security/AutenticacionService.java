package ctxt.textil.api.infra.security;

import ctxt.textil.api.UserAdmin.UserAdminRepository;
import ctxt.textil.api.Usuario.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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
        UserDetails user;
        user = userRepository.findByUsEmail(username);
        try {
            if (user==null){
                System.out.println("entrando en user null "+username);
                user = userRep.findByAdEmail(username);
            }
        }catch (Exception ex){
            System.out.println("!Excepcion!" + ex.getMessage());
            throw new RuntimeException(ex);

        }
        Boolean d = user == null;
        return user;
    }

    public UserDetails findUserWithIss(Boolean isUserAdmin, String emailUser) {
        if(isUserAdmin){
            return userRep.findByAdEmail(emailUser);
        }
        return userRepository.findByUsEmail(emailUser);
    }
}
