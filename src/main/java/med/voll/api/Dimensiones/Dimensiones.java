package med.voll.api.Dimensiones;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Dimensiones {

    private double altura;
    private double ancho;
    private double area;

    private double conocerArea(){
        return this.area=this.altura*this.ancho;
    }

}
