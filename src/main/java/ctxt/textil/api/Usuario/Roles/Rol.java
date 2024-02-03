package ctxt.textil.api.Usuario.Roles;

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


