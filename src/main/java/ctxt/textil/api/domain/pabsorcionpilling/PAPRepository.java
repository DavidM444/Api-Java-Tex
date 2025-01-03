package ctxt.textil.api.domain.pabsorcionpilling;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PAPRepository extends JpaRepository<PAbsorcionPilling,Long> {

    Integer countBypConsideracion(String pConsideracion);
}
