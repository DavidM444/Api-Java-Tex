package ctxt.textil.api.domain.user.usuario.Roles;

import ctxt.textil.api.application.dto.base.DtoRol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol;
    private Long idUserRol;
    private String rolName;


    public Rol(DtoRol rol){
        this.idUserRol = rol.idUser();
        this.rolName = rol.rolName();
    }


}


