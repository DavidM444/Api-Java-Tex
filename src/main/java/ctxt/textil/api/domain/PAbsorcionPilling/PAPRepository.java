package ctxt.textil.api.domain.PAbsorcionPilling;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PAPRepository extends JpaRepository<PAbsorcionPilling,Long> {

    Integer countBypConsideracion(String pConsideracion);
}
