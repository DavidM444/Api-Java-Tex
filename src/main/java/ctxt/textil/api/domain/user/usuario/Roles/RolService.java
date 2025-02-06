package ctxt.textil.api.domain.user.usuario.Roles;

import ctxt.textil.api.application.dto.base.DtoRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {

    @Autowired
    private RolRepository repositoryRol;

    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    public void saveRolAuthority(DtoRol rol) {
        System.out.println("roles "+ rol.idUser()+" "+rol.rolName());
        try {
            Boolean validacion = verifyRolExists(rol.rolName());
        } catch (Exception e) {
            throw new RuntimeException("Rol invalido: "+e.getMessage());
        }
        Rol rolData = new Rol(rol);
        repositoryRol.save(rolData);
    }

    private Boolean verifyRolExists(String rolAutority) {
        System.out.println("aut "+rolAutority);
        return rolAutority.equals(ROLE_ADMIN)||rolAutority.equals(ROLE_USER);
    }
}
